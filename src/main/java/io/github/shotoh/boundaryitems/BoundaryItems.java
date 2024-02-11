package io.github.shotoh.boundaryitems;

import io.github.shotoh.boundaryitems.integrations.VaultIntegration;
import io.github.shotoh.boundaryitems.listeners.InventoryListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BoundaryItems extends JavaPlugin {
    @Override
    public void onEnable() {
        VaultIntegration.register(this);
        registerEvents(new InventoryListener());
    }

    @Override
    public void onDisable() {
        //
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
