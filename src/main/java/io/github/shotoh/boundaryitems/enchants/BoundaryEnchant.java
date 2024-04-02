package io.github.shotoh.boundaryitems.enchants;

import io.github.shotoh.boundaryitems.integrations.VaultIntegration;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoundaryEnchant {
    private final Enchantment enchant;
    private String name;
    private Material material;
    private List<Integer> costs;

    public BoundaryEnchant(Enchantment enchant) {
        this.enchant = enchant;
        this.name = "&cGeneric Enchant";
        this.material = Material.ENCHANTED_BOOK;
        this.costs = Collections.emptyList();
    }

    public Enchantment getEnchant() {
        return enchant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<Integer> getCosts() {
        return costs;
    }

    public void setCosts(List<Integer> costs) {
        this.costs = costs;
    }

    public ItemStack createShowcase(ItemStack enchantedIs) {
        int level = enchantedIs.getEnchantmentLevel(enchant);
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color(name));
        im.spigot().setUnbreakable(true);

        List<String> lore = new ArrayList<>();
        lore.add("&6&lCurrent Level");
        lore.add("&e" + StringUtils.capitalize(enchant.toString()) + " " + level);
        lore.add("");
        if (level < costs.size()) {
            lore.add("&6&lNext Level");
            lore.add("&e" + StringUtils.capitalize(enchant.toString()) + " " + level + 1);
            lore.add("");
            lore.add("&6&lCost:");
            lore.add("&e$" + costs.get(level));
        } else {
            lore.add("&6&lMax Level");
        }
        im.setLore(lore.stream().map(s -> Utils.color("&7" + s)).toList());

        is.setItemMeta(im);
        return is;
    }

    public ItemStack createAdminShowcase() {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color(name));
        im.spigot().setUnbreakable(true);

        List<String> lore = new ArrayList<>();
        lore.add("&7Enchant: &c" + enchant.toString());
        lore.add("&7Costs:");
        for (int i = 0; i < costs.size(); i++) {
            lore.add("&7" + i + " -> " + i + 1 + ": $" + costs.get(i));
        }
        if (costs.isEmpty()) {
            lore.add("&cNone");
        }
        im.setLore(lore.stream().map(s -> Utils.color("&7" + s)).toList());

        is.setItemMeta(im);
        return is;
    }

    public boolean canUpgrade(Player player, ItemStack is) {
        int nextLevel = is.getEnchantmentLevel(enchant) + 1;
        if (nextLevel >= costs.size()) return false;
        return VaultIntegration.ECONOMY.has(player, costs.get(nextLevel - 1));
    }

    public ItemStack upgrade(Player player, ItemStack is) {
        int nextLevel = is.getEnchantmentLevel(enchant) + 1;
        VaultIntegration.ECONOMY.withdrawPlayer(player, costs.get(nextLevel - 1));
        is.getEnchantments().put(enchant, nextLevel);
        return NBTUtils.setNBTInteger(is, BoundaryItem.MONEY_KEY, NBTUtils.getNBTInteger(is, BoundaryItem.MONEY_KEY) + costs.get(nextLevel - 1));
    }
}
