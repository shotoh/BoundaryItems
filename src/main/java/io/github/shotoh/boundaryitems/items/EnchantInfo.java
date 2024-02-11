package io.github.shotoh.boundaryitems.items;

import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class EnchantInfo {
    private final Enchantment enchantment;
    private final List<Integer> costs;

    public EnchantInfo(Enchantment enchantment) {
        this.enchantment = enchantment;
        this.costs = new ArrayList<>();
        for (int i = 0; i < enchantment.getMaxLevel(); i++) {
            costs.add(0);
        }
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public List<Integer> getCosts() {
        return costs;
    }
}
