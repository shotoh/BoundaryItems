package io.github.shotoh.boundaryitems.enchants;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.items.ItemPath;
import io.github.shotoh.boundaryitems.utils.Utils;
import io.leangen.geantyref.TypeToken;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EnchantManager {
    private static final EnchantManager INSTANCE = new EnchantManager();
    private Map<Enchantment, BoundaryEnchant> enchants;
    private final Map<ItemPath, List<Enchantment>> paths = Map.of(
            ItemPath.WEAPON, List.of(Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT, Enchantment.KNOCKBACK),
            ItemPath.PICKAXE, List.of(Enchantment.DIG_SPEED, Enchantment.LOOT_BONUS_BLOCKS),
            ItemPath.ROD, List.of(Enchantment.LURE, Enchantment.LUCK),
            ItemPath.HELMET, List.of(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_FIRE, Enchantment.THORNS),
            ItemPath.CHESTPLATE, List.of(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_FIRE, Enchantment.THORNS),
            ItemPath.LEGGINGS, List.of(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_FIRE, Enchantment.THORNS),
            ItemPath.BOOTS, List.of(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_FIRE, Enchantment.THORNS)
    );

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

    public void removeEnchant(BoundaryEnchant enchant) {
        if (enchant == null) return;
        enchants.remove(enchant.getEnchant());
    }

    public List<BoundaryEnchant> getPossibleEnchants(ItemStack is) {
        BoundaryItem item = ItemManager.getInstance().getItem(is);
        if (item == null) return null;
        return paths.get(item.getPath()).stream().map(this::getEnchant).filter(Objects::nonNull).toList();
    }

    public static EnchantManager getInstance() {
        return INSTANCE;
    }
}
