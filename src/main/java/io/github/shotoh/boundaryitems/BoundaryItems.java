package io.github.shotoh.boundaryitems;

import io.github.shotoh.boundaryitems.integrations.VaultIntegration;
import org.bukkit.plugin.java.JavaPlugin;

public class BoundaryItems extends JavaPlugin {
    @Override
    public void onEnable() {
        VaultIntegration.register(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
