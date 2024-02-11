package io.github.shotoh.boundaryitems.listeners;

import io.github.shotoh.boundaryitems.guis.BoundaryGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        if (event.isCancelled()) return;
        if (event.getInventory().getHolder() instanceof BoundaryGui boundaryGui) boundaryGui.onOpen(event);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (event.getInventory().getHolder() instanceof BoundaryGui boundaryGui) boundaryGui.onClick(event);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof BoundaryGui boundaryGui) boundaryGui.onClose(event);
    }
}
