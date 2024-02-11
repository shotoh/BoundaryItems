package io.github.shotoh.boundaryitems.items;

import net.minecraft.server.v1_8_R3.Material;

public record BoundaryItem(String id, String name, String[] lore, Material material, ItemPath path,
                           UpgradeInfo upgradeInfo, double itemStat) {
}
