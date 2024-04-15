package io.github.shotoh.boundaryitems.listeners;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.blocks.BlockManager;
import io.github.shotoh.boundaryitems.consumables.BoundaryConsumable;
import io.github.shotoh.boundaryitems.consumables.ConsumableManager;
import io.github.shotoh.boundaryitems.features.LockChat;
import io.github.shotoh.boundaryitems.guis.UpgradeGui;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerListener implements Listener {
    private final BoundaryItems plugin;

    public PlayerListener(BoundaryItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = event.getPlayer();
                if (player == null) return;
                ItemStack is = player.getItemInHand();
                BoundaryItem item = ItemManager.getInstance().getItem(is);
                if (is == null || item == null || is.getAmount() != 1) return;
                is = is.clone();
                player.setItemInHand(null);
                GuiUtils.openInventory(plugin, player, new UpgradeGui(plugin, player, is));
            }
        }.runTaskLater(plugin, 1);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        GuiUtils.closeInventory(plugin, event.getPlayer());
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Map<UUID, Consumer<AsyncPlayerChatEvent>> inputs = BoundaryItems.INPUTS;
        if (inputs.containsKey(uuid)) {
            inputs.get(uuid).accept(event);
            inputs.remove(uuid);
            event.setCancelled(true);
        }
        LockChat.getInstance().onAsyncPlayerChat(event);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        BlockManager.getInstance().onBlockBreak(event);
    }

    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack is = event.getItem();
        String id = NBTUtils.getNBTString(is, BoundaryItem.ID_KEY);
        for (BoundaryConsumable consumable : ConsumableManager.getInstance().getConsumables().values()) {
            if (!consumable.getId().equals(id)) continue;
            if (Utils.isShotoh(player)) {
                consumable.onConsume(event);
                return;
            }
            UUID uuid = ItemUtils.getUUID(is);
            if (uuid != null && uuid.equals(player.getUniqueId())) {
                consumable.onConsume(event);
            } else {
                ItemUtils.removeItem(player, is, is.getAmount());
                Utils.sendMessage(player, "&c&lYOU ARE UNWORTHY.");
                Utils.playSound(player, Sound.WITHER_IDLE, 1f, 0.5f);
            }
        }
    }
}
