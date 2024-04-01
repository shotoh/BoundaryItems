package io.github.shotoh.boundaryitems.enchants;

import io.github.shotoh.boundaryitems.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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

    public ItemStack createShowcase(int level) {
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
}
