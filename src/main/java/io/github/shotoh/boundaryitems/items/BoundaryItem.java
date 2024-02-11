package io.github.shotoh.boundaryitems.items;

import io.github.shotoh.boundaryitems.core.BoundaryDamage;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class BoundaryItem {
    public static final String ID_KEY = "bi_id";
    public static final String MONEY_KEY = "bi_money";

    private final String id;
    private String name;
    private Material material;
    private ItemPath path;
    private int pathWeight;
    private int itemStat;
    private UpgradeInfo upgradeInfo;

    public BoundaryItem(String id, String name, Material material, ItemPath path, int pathWeight,
                        int itemStat, UpgradeInfo upgradeInfo) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.path = path;
        this.pathWeight = pathWeight;
        this.itemStat = itemStat;
        this.upgradeInfo = upgradeInfo;
    }

    public String getId() {
        return id;
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

    public ItemPath getPath() {
        return path;
    }

    public void setPath(ItemPath path) {
        this.path = path;
    }

    public int getPathWeight() {
        return pathWeight;
    }

    public void setPathWeight(int pathWeight) {
        this.pathWeight = pathWeight;
    }

    public int getItemStat() {
        return itemStat;
    }

    public void setItemStat(int itemStat) {
        this.itemStat = itemStat;
    }

    public UpgradeInfo getUpgradeInfo() {
        return upgradeInfo;
    }

    public void setUpgradeInfo(UpgradeInfo upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
    }

    public ItemStack create() {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color(name));
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        is = NBTUtils.setNBTString(is, ID_KEY, id);
        is = NBTUtils.setNBTInteger(is, MONEY_KEY, 0);
        is = NBTUtils.addAttributes(is, path, itemStat);
        setLore(this, is);
        return is;
    }

    public static void setLore(BoundaryItem item, ItemStack is) {
        List<String> lore = new ArrayList<>();
        if (item.getPath() == ItemPath.PICKAXE) {
            lore.add("&6Breaking Power: &e" + item.getItemStat());
            lore.add("");
        }
        lore.add("&6Money spend on item");
        int moneySpent = Math.max(NBTUtils.getNBTInteger(is, MONEY_KEY), 0);
        lore.add("&e$" + moneySpent);
        lore.add("");
        lore.add("&6Ascension requirements:");
        if (ItemManager.getInstance().isEndOfPath(item)) {
            lore.add("&e&lMAX ASCENSION");
        } else {
            lore.add("&eLevel " + item.getUpgradeInfo().getLevelCost());
            lore.add("&e$" + item.getUpgradeInfo().getMoneyCost() + " spent on item");
            lore.add("&eA level " + item.getUpgradeInfo().getEnchantCost() + " enchant");
        }
        ItemMeta im = is.getItemMeta();
        im.setLore(lore.stream().map(Utils::color).toList());
        is.setItemMeta(im);
    }
}
