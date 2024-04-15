package io.github.shotoh.boundaryitems.blocks;

import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BoundaryBlock {
    private final Material material;
    private int breakingPower;
    private int blockPrice;
    private int blockExp;

    public BoundaryBlock(Material material) {
        this.material = material;
        this.breakingPower = 0;
        this.blockPrice = 0;
        this.blockExp = 0;
    }

    public Material getMaterial() {
        return material;
    }

    public int getBreakingPower() {
        return breakingPower;
    }

    public void setBreakingPower(int breakingPower) {
        this.breakingPower = breakingPower;
    }

    public int getBlockPrice() {
        return blockPrice;
    }

    public void setBlockPrice(int blockPrice) {
        this.blockPrice = blockPrice;
    }

    public int getBlockExp() {
        return blockExp;
    }

    public void setBlockExp(int blockExp) {
        this.blockExp = blockExp;
    }

    public ItemStack createShowcase() {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color("&b" + material));
        im.spigot().setUnbreakable(true);

        List<String> lore = new ArrayList<>();
        lore.add("&7Breaking Power: &c" + breakingPower);
        lore.add("&7Price: &c$" + blockPrice);
        lore.add("&7Experience: &c" + blockExp);
        lore.add("");
        lore.add("&aRight click to edit");
        lore.add("&cShift click to destroy");
        im.setLore(lore.stream().map(s -> Utils.color("&7" + s)).toList());

        is.setItemMeta(im);
        return is;
    }
}
