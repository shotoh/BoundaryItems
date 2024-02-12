package io.github.shotoh.boundaryitems;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.shotoh.boundaryitems.core.BoundaryCommand;
import io.github.shotoh.boundaryitems.integrations.VaultIntegration;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.listeners.InventoryListener;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.bukkit.BukkitCommandManager;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class BoundaryItems extends JavaPlugin {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Map<UUID, Consumer<AsyncPlayerChatEvent>> INPUTS = new HashMap<>();

    private BukkitCommandManager<CommandSender> commandManager;

    @Override
    public void onEnable() {
        this.commandManager = new PaperCommandManager<>(
                this,
                ExecutionCoordinator.simpleCoordinator(),
                SenderMapper.identity()
        );
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
