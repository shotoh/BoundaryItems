package io.github.shotoh.boundaryitems.guis;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.enchants.BoundaryEnchant;
import io.github.shotoh.boundaryitems.enchants.EnchantManager;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class UpgradeGui extends BoundaryGui {
    private final Player player;
    private ItemStack is;
    private List<BoundaryEnchant> possibleEnchants;

    public UpgradeGui(BoundaryItems plugin, Player player, ItemStack is) {
        super(plugin, "upgrade", "Upgrade");
        this.player = player;
        this.is = is;
        this.possibleEnchants = new ArrayList<>();
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
                        "&7previous materials, and will reset enchants.",
                        "",
                        "&8*Click this item to confirm &bAscension&8*"
                }, Material.QUARTZ_STAIRS, null, 1));
            } else if (i == 49) {
                inv.setItem(i, GuiUtils.getGuiClose());
            } else if (!GuiUtils.notBorder(i)) {
                inv.setItem(i, GuiUtils.getGuiGlass(DyeColor.PURPLE.getData()));
            }
        }
        possibleEnchants = EnchantManager.getInstance().getPossibleEnchants(is);
        if (possibleEnchants == null) return;
        switch (possibleEnchants.size()) {
            case 1 -> inv.setItem(31, possibleEnchants.get(0).createShowcase(is));
            case 2 -> {
                inv.setItem(30, possibleEnchants.get(0).createShowcase(is));
                inv.setItem(32, possibleEnchants.get(1).createShowcase(is));
            }
            case 3 -> {
                inv.setItem(29, possibleEnchants.get(0).createShowcase(is));
                inv.setItem(31, possibleEnchants.get(1).createShowcase(is));
                inv.setItem(33, possibleEnchants.get(2).createShowcase(is));
            }
        }
        // todo fix not removing
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        if (!event.getInventory().equals(event.getClickedInventory())) return;
        update(event.getInventory());
        int slot = event.getSlot();
        BoundaryItem item = ItemManager.getInstance().getItem(is);
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
            is = upgradeIs;
            GuiUtils.closeInventory(plugin, player);
        } else if (slot == 49) {
            GuiUtils.closeInventory(plugin, player);
        } else {
            if (possibleEnchants == null) return;
            BoundaryEnchant enchant = getBoundaryEnchant(slot);
            if (enchant == null) return;
            if (enchant.canUpgrade(player, is)) {
                Utils.playSound(player, Sound.VILLAGER_HAGGLE, 1f, 1f);
                is = enchant.upgrade(player, is);
                BoundaryItem.setLore(item, is);
                update(event.getInventory());
            } else {
                Utils.playSound(player, Sound.ENDERMAN_HIT, 1f, 1f);
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);
        ItemUtils.addItem(player, is, 1);
    }

    private BoundaryEnchant getBoundaryEnchant(int slot) {
        BoundaryEnchant enchant = null;
        if (slot == 29 && possibleEnchants.size() == 3) {
            enchant = possibleEnchants.get(0);
        } else if (slot == 30 && possibleEnchants.size() == 2) {
            enchant = possibleEnchants.get(0);
        } else if (slot == 31) {
            if (possibleEnchants.size() == 1) {
                enchant = possibleEnchants.get(0);
            } else if (possibleEnchants.size() == 3) {
                enchant = possibleEnchants.get(1);
            }
        } else if (slot == 32 && possibleEnchants.size() == 2) {
            enchant = possibleEnchants.get(1);
        } else if (slot == 33 && possibleEnchants.size() == 3) {
            enchant = possibleEnchants.get(2);
        }
        return enchant;
    }
}
