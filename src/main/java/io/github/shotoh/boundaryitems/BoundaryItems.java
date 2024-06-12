package io.github.shotoh.boundaryitems;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.shotoh.boundaryitems.blocks.BlockManager;
import io.github.shotoh.boundaryitems.blocks.BoundaryBlock;
import io.github.shotoh.boundaryitems.consumables.ConsumableManager;
import io.github.shotoh.boundaryitems.core.BoundaryCommand;
import io.github.shotoh.boundaryitems.core.ChannelCommand;
import io.github.shotoh.boundaryitems.enchants.BoundaryEnchant;
import io.github.shotoh.boundaryitems.enchants.EnchantManager;
import io.github.shotoh.boundaryitems.enchants.EnchantmentTypeAdapter;
import io.github.shotoh.boundaryitems.integrations.VaultIntegration;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.listeners.InventoryListener;
import io.github.shotoh.boundaryitems.listeners.PlayerListener;
import io.github.shotoh.boundaryitems.utils.Utils;
import io.leangen.geantyref.TypeToken;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.bukkit.BukkitCommandManager;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class BoundaryItems extends JavaPlugin {
    public static final Logger LOGGER = Logger.getLogger("BoundaryItems");
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(Enchantment.class, new EnchantmentTypeAdapter()).create();
    public static final Map<UUID, Consumer<AsyncPlayerChatEvent>> INPUTS = new HashMap<>();

    private PaperCommandManager<CommandSender> commandManager;

    @Override
    public void onEnable() {
        this.commandManager = new PaperCommandManager<>(
                this,
                ExecutionCoordinator.simpleCoordinator(),
                SenderMapper.identity()
        );
        ItemManager.getInstance().register(this);
        BlockManager.getInstance().register(this);
        EnchantManager.getInstance().register(this);
        ConsumableManager.getInstance().register(this);
        VaultIntegration.register(this);
        new BoundaryCommand(this).register();
        new ChannelCommand(this).register();
        registerEvents(new InventoryListener());
        registerEvents(new PlayerListener(this));
    }

    @Override
    public void onDisable() {
        try (FileWriter writer = new FileWriter(Utils.getFile(this, "items.json"))) {
            GSON.toJson(ItemManager.getInstance().getItems(), new TypeToken<Map<String, BoundaryItem>>(){}.getType(), writer);
            LOGGER.info("Saved items to file!");
        } catch (IOException ignored) {
        }
        try (FileWriter writer = new FileWriter(Utils.getFile(this, "blocks.json"))) {
            GSON.toJson(BlockManager.getInstance().getBlocks(), new TypeToken<Map<Material, BoundaryBlock>>(){}.getType(), writer);
            LOGGER.info("Saved blocks to file!");
        } catch (IOException ignored) {
        }
        try (FileWriter writer = new FileWriter(Utils.getFile(this, "enchants.json"))) {
            GSON.toJson(EnchantManager.getInstance().getEnchants(), new TypeToken<Map<Enchantment, BoundaryEnchant>>(){}.getType(), writer);
            LOGGER.info("Saved enchants to file!");
        } catch (IOException ignored) {
        }
    }

    public BukkitCommandManager<CommandSender> getCommandManager() {
        return commandManager;
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
