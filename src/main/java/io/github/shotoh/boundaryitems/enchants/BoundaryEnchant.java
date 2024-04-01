package io.github.shotoh.boundaryitems.enchants;

import org.bukkit.enchantments.Enchantment;

import java.util.Collections;
import java.util.List;

public class BoundaryEnchant {
    private final Enchantment enchant;
    private List<Integer> costs;

    public BoundaryEnchant(Enchantment enchant) {
        this.enchant = enchant;
        this.costs = Collections.emptyList();
    }

    public Enchantment getEnchant() {
        return enchant;
    }

    public List<Integer> getCosts() {
        return costs;
    }

    public void setCosts(List<Integer> costs) {
        this.costs = costs;
    }
}
