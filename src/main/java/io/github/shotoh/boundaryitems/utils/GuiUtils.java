package io.github.shotoh.boundaryitems.utils;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.guis.BoundaryGui;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class GuiUtils {
    public static void openInventory(BoundaryItems plugin, Player player, BoundaryGui gui) {
        if (player == null || gui == null) return;
        GuiUtils.openInventory(plugin, player, gui.getInventory());
    }

    public static void openInventory(BoundaryItems plugin, Player player, Inventory inv) {
        if (player == null || inv == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                player.openInventory(inv);
            }
        }.runTaskLater(plugin, 1);
    }

    public static void closeInventory(BoundaryItems plugin, Player player) {
        if (player == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                player.closeInventory();
            }
        }.runTaskLater(plugin, 1);
    }

    public static ItemStack getGuiGlass() {
        return getGuiGlass(DyeColor.GRAY.getData());
    }

    public static ItemStack getGuiGlass(byte color) {
        ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, color);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("");
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack getGuiClose() {
        return ItemUtils.createMiscItem("&cClose", null, Material.BARRIER, null, 1);
    }

    public static ItemStack getGuiPrevious() {
        return ItemUtils.createMiscItem("&aPrevious", null, Material.ARROW, null, 1);
    }

    public static ItemStack getGuiNext() {
        return ItemUtils.createMiscItem("&aNext", null, Material.ARROW, null, 1);
    }

    public static boolean notBorder(int slot) {
        return slot % 9 > 0 && slot % 9 < 8 && slot > 8 && slot < 45;
    }

    public static int convertSlotToIndex(int slot) {
        return switch (slot) {
            case 10, 11, 12, 13, 14, 15, 16 -> slot - 10;
            case 19, 20, 21, 22, 23, 24, 25 -> slot - 12;
            case 28, 29, 30, 31, 32, 33, 34 -> slot - 14;
            case 37, 38, 39, 40, 41, 42, 43 -> slot - 16;
            default -> -1;
        };
    }

    public static int getMaxPage(List<?> list) {
        return list.size() / 28;
    }
}
