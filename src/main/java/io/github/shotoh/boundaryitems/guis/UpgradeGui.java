package io.github.shotoh.boundaryitems.guis;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
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
    private final ItemStack is;

    public UpgradeGui(BoundaryItems plugin, Player player, ItemStack is) {
        super(plugin, "upgrade", "Upgrade");
        this.player = player;
        this.is = is.clone();
        is.setType(Material.AIR);
    }

    @Override
    protected void update(Inventory inv) {
        for (int i = 0; i < 54; i++) {
            if (i == 4) {
                inv.setItem(i, is);
            } else if (i == 22) {
                inv.setItem(i, ItemUtils.createMiscItem("&6Ascension", new String[] {
                        "&eClick here to upgrade your weapon"
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
        super.onClick(event);
        event.setCancelled(true);
        // todo set clicks
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);
        ItemUtils.addItem(player, is, 1);
    }
}
