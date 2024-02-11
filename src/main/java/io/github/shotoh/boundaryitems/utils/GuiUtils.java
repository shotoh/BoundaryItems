package io.github.shotoh.boundaryitems.utils;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.guis.BoundaryGui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

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
}
