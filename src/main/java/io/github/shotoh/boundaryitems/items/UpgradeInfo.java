package io.github.shotoh.boundaryitems.items;

import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public record UpgradeInfo(int moneyCost, int levelCost, int enchantCost) {
    public boolean canUpgrade(Player player, ItemStack is) {
        int moneySpent = NBTUtils.getNBTInteger(is, BoundaryItem.MONEY_KEY);
        if (moneySpent < moneyCost) return false;
        if (player.getExpToLevel() < levelCost) return false;
        for (int enchantLevel : is.getEnchantments().values()) {
            if (enchantLevel >= enchantCost) return true;
        }
        return false;
    }

    public ItemStack upgrade(BoundaryItem item) {
        return ItemUtils.createItem(ItemManager.getInstance().getNextInPath(item));
    }
}
