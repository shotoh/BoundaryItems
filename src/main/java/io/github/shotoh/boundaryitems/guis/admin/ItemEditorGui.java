package io.github.shotoh.boundaryitems.guis.admin;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.guis.BoundaryGui;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.items.ItemPath;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class ItemEditorGui extends BoundaryGui {
    public ItemEditorGui(BoundaryItems plugin) {
        super(plugin, "item_editor", "Item Editor", 54);
    }

    @Override
    protected void update(Inventory inv) {
        for (int i = 0; i < 54; i++) {
            if (i == 20) {
                inv.setItem(i, ItemUtils.createMiscItem("&cWeapon Editor", null, Material.DIAMOND_SWORD, null, 1));
            } else if (i == 21) {
                inv.setItem(i, ItemUtils.createMiscItem("&cPickaxe Editor", null, Material.DIAMOND_PICKAXE, null, 1));
            } else if (i == 22) {
                inv.setItem(i, ItemUtils.createMiscItem("&cFishing Rod Editor", null, Material.FISHING_ROD, null, 1));
            } else if (i == 29) {
                inv.setItem(i, ItemUtils.createMiscItem("&cHelmet Editor", null, Material.DIAMOND_HELMET, null, 1));
            } else if (i == 30) {
                inv.setItem(i, ItemUtils.createMiscItem("&cChestplate Editor", null, Material.DIAMOND_CHESTPLATE, null, 1));
            } else if (i == 31) {
                inv.setItem(i, ItemUtils.createMiscItem("&cLeggings Editor", null, Material.DIAMOND_LEGGINGS, null, 1));
            } else if (i == 32) {
                inv.setItem(i, ItemUtils.createMiscItem("&cBoots Editor", null, Material.DIAMOND_BOOTS, null, 1));
            } else if (i == 49) {
                inv.setItem(i, GuiUtils.getGuiClose());
            } else if (!GuiUtils.notBorder(i)) {
                inv.setItem(i, GuiUtils.getGuiGlass(DyeColor.RED.getData()));
            }
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        if (!event.getClickedInventory().equals(event.getInventory())) return;
        update(event.getInventory());
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (slot == 20) {
            GuiUtils.openInventory(plugin, player, new ItemPathEditorGui(plugin, ItemPath.WEAPON));
        } else if (slot == 21) {
            GuiUtils.openInventory(plugin, player, new ItemPathEditorGui(plugin, ItemPath.PICKAXE));
        } else if (slot == 22) {
            GuiUtils.openInventory(plugin, player, new ItemPathEditorGui(plugin, ItemPath.ROD));
        } else if (slot == 29) {
            GuiUtils.openInventory(plugin, player, new ItemPathEditorGui(plugin, ItemPath.HELMET));
        } else if (slot == 30) {
            GuiUtils.openInventory(plugin, player, new ItemPathEditorGui(plugin, ItemPath.CHESTPLATE));
        } else if (slot == 31) {
            GuiUtils.openInventory(plugin, player, new ItemPathEditorGui(plugin, ItemPath.LEGGINGS));
        } else if (slot == 32) {
            GuiUtils.openInventory(plugin, player, new ItemPathEditorGui(plugin, ItemPath.BOOTS));
        } else if (slot == 49) {
            GuiUtils.closeInventory(plugin, player);
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        ItemManager.getInstance().sortPathItems();
    }
}
