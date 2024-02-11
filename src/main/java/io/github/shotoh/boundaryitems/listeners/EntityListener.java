package io.github.shotoh.boundaryitems.listeners;

import io.github.shotoh.boundaryitems.BoundaryItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityListener implements Listener {
    private final BoundaryItems plugin;

    public EntityListener(BoundaryItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            //
        }
    }
}
