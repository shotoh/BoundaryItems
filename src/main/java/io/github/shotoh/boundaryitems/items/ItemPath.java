package io.github.shotoh.boundaryitems.items;

public enum ItemPath {
    WEAPON,
    PICKAXE,
    ROD,
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS;

    public boolean isArmor() {
        return this == ItemPath.HELMET || this == ItemPath.CHESTPLATE || this == ItemPath.LEGGINGS || this == ItemPath.BOOTS;
    }
}
