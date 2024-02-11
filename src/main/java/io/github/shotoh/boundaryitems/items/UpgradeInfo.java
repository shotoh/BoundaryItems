package io.github.shotoh.boundaryitems.items;

import io.github.shotoh.boundaryitems.integrations.VaultIntegration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public record UpgradeInfo(int moneyCost, int levelCost, int enchantCost) {
    public boolean canUpgrade(Player player, ItemStack is) {
        if (!VaultIntegration.ECONOMY.has(player, moneyCost)) return false;
        if (player.getExpToLevel() < levelCost) return false;
        for (int enchantLevel : is.getEnchantments().values()) {
            if (enchantLevel >= enchantCost) return true;
        }
        return false;
    }

    public void upgrade(Player player, ItemStack is) {
        //
    }
}
