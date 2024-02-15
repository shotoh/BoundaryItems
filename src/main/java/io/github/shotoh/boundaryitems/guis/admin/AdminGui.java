package io.github.shotoh.boundaryitems.guis.admin;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.guis.BoundaryGui;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class AdminGui extends BoundaryGui {
    public AdminGui(BoundaryItems plugin) {
        super(plugin, "admin", "Admin", 45);
    }

    @Override
    protected void update(Inventory inv) {
        for (int i = 0; i < 45; i++) {
            if (i == 20) {
                inv.setItem(i, ItemUtils.createMiscItem("&cItem Editor", null, Material.DIAMOND_SWORD, null, 1));
            } else if (i == 22) {
                inv.setItem(i, ItemUtils.createMiscItem("&cEnchants Editor", null, Material.ENCHANTED_BOOK, null, 1));
            } else if (i == 24) {
                inv.setItem(i, ItemUtils.createMiscItem("&cBlock Editor", null, Material.DIAMOND_PICKAXE, null, 1));
            } else if (i == 40) {
                inv.setItem(i, GuiUtils.getGuiClose());
            } else if (i < 9 || i > 35) {
                inv.setItem(i, GuiUtils.getGuiGlass(DyeColor.RED.getData()));
            }
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        if (!event.getInventory().equals(event.getClickedInventory())) return;
        update(event.getInventory());
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (slot == 20) {
            GuiUtils.openInventory(plugin, player, new ItemEditorGui(plugin));
        } else if (slot == 40) {
            GuiUtils.closeInventory(plugin, player);
        }
    }
}
