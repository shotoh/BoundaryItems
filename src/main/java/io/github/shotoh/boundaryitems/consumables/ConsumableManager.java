package io.github.shotoh.boundaryitems.consumables;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConsumableManager {
    private static final ConsumableManager INSTANCE = new ConsumableManager();
    private BoundaryItems plugin;
    private Map<String, BoundaryConsumable> consumables;
    private Map<UUID, Long> cooldowns;

    private ConsumableManager() {
        //
    }

    public void register(BoundaryItems plugin) {
        this.plugin = plugin;
        this.consumables = new HashMap<>();
        this.cooldowns = new HashMap<>();

        addConsumable(new Frigidflask(plugin));
    }

    public Map<String, BoundaryConsumable> getConsumables() {
        return consumables;
    }

    public BoundaryConsumable getConsumable(String id) {
        return consumables.get(id);
    }

    public void addConsumable(BoundaryConsumable consumable) {
        consumables.put(consumable.getId(), consumable);
    }

    public void removeBlock(BoundaryConsumable consumable) {
        if (consumable == null) return;
        consumables.remove(consumable.getId());
    }

    public void checkConsumable(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack is = event.getItem();
        String id = NBTUtils.getNBTString(is, BoundaryItem.ID_KEY);
        for (BoundaryConsumable consumable : consumables.values()) {
            if (!consumable.getId().equals(id)) continue;
            if (Utils.isShotoh(player)) {
                consumable.onConsume(event);
                return;
            }
            UUID uuid = ItemUtils.getUUID(is);
            if (uuid != null && uuid.equals(player.getUniqueId())) {
                if (!Utils.checkCooldown(cooldowns, uuid, consumable.getCooldown())) {
                    Utils.sendMessage(player, "&cYou are still on cooldown! (" + Utils.formatCooldown(cooldowns, uuid) + "s)");
                    return;
                }
                consumable.onConsume(event);
            } else {
                Utils.sendMessage(player, "&c&lTHIS IS NOT YOURS TO USE.");
                Utils.playSound(player, Sound.WITHER_IDLE, 1f, 0.5f);
            }
            return;
        }
    }

    public static ConsumableManager getInstance() {
        return INSTANCE;
    }
}
