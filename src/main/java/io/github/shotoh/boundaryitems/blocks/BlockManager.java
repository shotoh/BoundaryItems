package io.github.shotoh.boundaryitems.blocks;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.integrations.VaultIntegration;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.items.ItemPath;
import io.github.shotoh.boundaryitems.utils.ExperienceUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import io.leangen.geantyref.TypeToken;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockManager {
    private static final BlockManager INSTANCE = new BlockManager();
    private Map<Material, BoundaryBlock> blocks;

    private BlockManager() {
        //
    }

    public void register(BoundaryItems plugin) {
        try (FileReader reader = new FileReader(Utils.getFile(plugin, "blocks.json"))) {
            Map<Material, BoundaryBlock> tempBlocks = BoundaryItems.GSON.fromJson(reader, new TypeToken<Map<Material, BoundaryBlock>>(){}.getType());
            if (tempBlocks == null || tempBlocks.isEmpty()) {
                BoundaryItems.LOGGER.warning("Blocks could not be found, prevented override!");
            } else {
                blocks = tempBlocks;
            }
        } catch (IOException ignored) {
        }
        if (blocks == null) blocks = new HashMap<>();
    }

    public Map<Material, BoundaryBlock> getBlocks() {
        return blocks;
    }

    public BoundaryBlock getBlock(Material material) {
        return blocks.get(material);
    }

    public void addBlock(BoundaryBlock block) {
        blocks.put(block.getMaterial(), block);
    }

    public void removeBlock(BoundaryBlock block) {
        if (block == null) return;
        blocks.remove(block.getMaterial());
    }

    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if (player.hasPermission("bi.admin")) return;
        BoundaryBlock block = getBlock(event.getBlock().getType());
        if (block == null) return;
        BoundaryItem item = ItemManager.getInstance().getItem(player.getItemInHand());
        if (item == null || (item.getPath() == ItemPath.PICKAXE && item.getItemStat() < block.getBreakingPower())) return;
        ExperienceUtils.changeExp(player, block.getBlockExp());
        double multiplier = 1 + (player.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * 0.1);
        VaultIntegration.ECONOMY.depositPlayer(player, (int) (block.getBlockPrice() * multiplier));
    }

    public static BlockManager getInstance() {
        return INSTANCE;
    }
}
