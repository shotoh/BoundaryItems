package io.github.shotoh.boundaryitems.guis;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpgradeGui extends BoundaryGui {
    private final Player player;
    private ItemStack is;

    public UpgradeGui(BoundaryItems plugin, Player player, ItemStack is) {
        super(plugin, "upgrade", "Upgrade");
        this.player = player;
        this.is = is.clone();
        ItemUtils.removeItem(player, is, 1);
    }

    @Override
    protected void update(Inventory inv) {
        for (int i = 0; i < 54; i++) {
            if (i == 4) {
                inv.setItem(i, is);
            } else if (i == 22) {
                inv.setItem(i, ItemUtils.createMiscItem("&b&lASCEND ITEM", new String[] {
                        "&c&lIRREVERSIBLE",
                        "",
                        "&7Ascending this item will convert it to the",
                        "&7next &8rarity&7. Ascending this item will prevent you",
                        "&7from reverting it back to the current material or",
                        "&7previous materials, will reset enchants, but will",
                        "&7enable you to deal damage beyond your current",
                        "&7capabilities",
                        "",
                        "&8*Click this item to confirm &bAscension&8*"
                }, Material.QUARTZ_STAIRS, null, 1));
            } else if (i == 49) {
                inv.setItem(i, GuiUtils.getGuiClose());
            } else if (!GuiUtils.notBorder(i)) {
                inv.setItem(i, GuiUtils.getGuiGlass(DyeColor.PURPLE.getData()));
            }
        }
        // todo set enchantments
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        super.onOpen(event);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        if (!event.getInventory().equals(event.getClickedInventory())) return;
        update(event.getInventory());
        int slot = event.getSlot();
        BoundaryItem item = ItemUtils.getItem(is);
        if (item == null) return;
        if (slot == 22) {
            if (!item.getUpgradeInfo().canUpgrade(player, is)) {
                Utils.sendMessage(player, "&cYou do not meet the requirements!");
                return;
            }
            ItemStack upgradeIs = item.getUpgradeInfo().upgrade(item);
            if (upgradeIs == null) {
                Utils.sendMessage(player, "&cYou already have the max ascension!");
                return;
            }
            is = null;
            ItemUtils.addItem(player, upgradeIs, 1);
            GuiUtils.closeInventory(plugin, player);
        } else if (slot == 49) {
            GuiUtils.closeInventory(plugin, player);
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);
        ItemUtils.addItem(player, is, 1);
    }
}
