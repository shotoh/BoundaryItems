package io.github.shotoh.boundaryitems.guis;

import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class BoundaryGui implements InventoryHolder {
    protected final String id;
    protected final String name;
    protected final int size;

    protected BoundaryGui(String id, String name) {
        this(id, name, 54);
    }

    protected BoundaryGui(String id, String name, int size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(this, size, Utils.color(name));
        update(inv);
        return inv;
    }

    protected abstract void update(Inventory inv);

    public void onOpen(InventoryOpenEvent event) {
        update(event.getInventory());
    }

    public void onClick(InventoryClickEvent event) {
        update(event.getInventory());
    }

    public void onClose(InventoryCloseEvent event) {
        //
    }
}