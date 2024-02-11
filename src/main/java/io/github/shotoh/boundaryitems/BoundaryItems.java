package io.github.shotoh.boundaryitems;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.shotoh.boundaryitems.core.BoundaryCommand;
import io.github.shotoh.boundaryitems.integrations.VaultIntegration;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.listeners.InventoryListener;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.bukkit.BukkitCommandManager;

public class BoundaryItems extends JavaPlugin {
    public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private BukkitCommandManager<CommandSender> commandManager;

    @Override
    public void onEnable() {
        ItemManager.getInstance().register(this);
        VaultIntegration.register(this);
        new BoundaryCommand(this).register();
        registerEvents(new InventoryListener());
    }

    @Override
    public void onDisable() {
        //
    }

    public BukkitCommandManager<CommandSender> getCommandManager() {
        return commandManager;
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
