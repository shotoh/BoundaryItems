package io.github.shotoh.boundaryitems.items;

import io.github.shotoh.boundaryitems.BoundaryItems;
import net.minecraft.server.v1_8_R3.Material;
import org.bukkit.inventory.ItemStack;

public record BoundaryItem(String id, String name, String[] lore, Material material, double itemStat,
                           ItemPath path, int pathWeight, UpgradeInfo upgradeInfo) {
    public ItemStack create(BoundaryItems plugin) {
        return null; // todo
    }
}
