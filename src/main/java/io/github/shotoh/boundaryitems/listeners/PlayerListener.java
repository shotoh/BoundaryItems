package io.github.shotoh.boundaryitems.listeners;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.guis.UpgradeGui;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    private final BoundaryItems plugin;

    public PlayerListener(BoundaryItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        if (event.isCancelled()) return;
        ItemStack is = event.getItemDrop().getItemStack();
        BoundaryItem item = ItemUtils.getItem(is);
        if (is == null || item == null || is.getAmount() != 1) return;
        event.setCancelled(true);
        GuiUtils.openInventory(plugin, event.getPlayer(), new UpgradeGui(plugin, event.getPlayer(), is));
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        GuiUtils.closeInventory(plugin, event.getPlayer());
    }
}
