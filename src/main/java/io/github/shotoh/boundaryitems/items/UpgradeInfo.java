package io.github.shotoh.boundaryitems.items;

import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradeInfo {
    private int moneyCost;
    private int levelCost;
    private int enchantCost;

    public UpgradeInfo(int moneyCost, int levelCost, int enchantCost) {
        this.moneyCost = moneyCost;
        this.levelCost = levelCost;
        this.enchantCost = enchantCost;
    }

    public int getMoneyCost() {
        return moneyCost;
    }

    public void setMoneyCost(int moneyCost) {
        this.moneyCost = moneyCost;
    }

    public int getLevelCost() {
        return levelCost;
    }

    public void setLevelCost(int levelCost) {
        this.levelCost = levelCost;
    }

    public int getEnchantCost() {
        return enchantCost;
    }

    public void setEnchantCost(int enchantCost) {
        this.enchantCost = enchantCost;
    }

    public boolean canUpgrade(Player player, ItemStack is) {
        int moneySpent = NBTUtils.getNBTInteger(is, BoundaryItem.MONEY_KEY);
        if (moneySpent < moneyCost) return false;
        if (player.getLevel() < levelCost) return false;
        for (int enchantLevel : is.getEnchantments().values()) {
            if (enchantLevel >= enchantCost) return true;
        }
        return false;
    }

    public ItemStack upgrade(BoundaryItem item) {
        return ItemUtils.createItem(ItemManager.getInstance().getNextInPath(item));
    }
}
