package io.github.shotoh.boundaryitems.enchants;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.block.BlockManager;
import io.github.shotoh.boundaryitems.block.BoundaryBlock;
import io.github.shotoh.boundaryitems.integrations.VaultIntegration;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.items.ItemPath;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import io.leangen.geantyref.TypeToken;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantManager {
    private static final EnchantManager INSTANCE = new EnchantManager();
    private Map<Enchantment, BoundaryEnchant> enchants;
    private Map<ItemPath, List<Enchantment>> paths;

    private EnchantManager() {
        //
    }

    public void register(BoundaryItems plugin) {
        try (FileReader reader = new FileReader(Utils.getFile(plugin, "enchants.json"))) {
            Map<Enchantment, BoundaryEnchant> tempEnchants = BoundaryItems.GSON.fromJson(reader, new TypeToken<Map<Enchantment, BoundaryEnchant>>(){}.getType());
            if (tempEnchants == null || tempEnchants.isEmpty()) {
                BoundaryItems.LOGGER.warning("Enchants could not be found, prevented override!");
            } else {
                enchants = tempEnchants;
            }
        } catch (IOException ignored) {
        }
        if (enchants == null) enchants = new HashMap<>();
    }

    public Map<Enchantment, BoundaryEnchant> getEnchants() {
        return enchants;
    }

    public BoundaryEnchant getEnchant(Enchantment enchant) {
        return enchants.get(enchant);
    }

    public void addEnchant(BoundaryEnchant enchant) {
        enchants.put(enchant.getEnchant(), enchant);
    }

    public void removeBlock(BoundaryEnchant enchant) {
        if (enchant == null) return;
        enchants.remove(enchant.getEnchant());
    }

    public static EnchantManager getInstance() {
        return INSTANCE;
    }
}
