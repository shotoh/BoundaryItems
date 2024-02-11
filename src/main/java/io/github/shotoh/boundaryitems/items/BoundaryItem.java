package io.github.shotoh.boundaryitems.items;

import io.github.shotoh.boundaryitems.core.BoundaryDamage;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public record BoundaryItem(String id, String name, Material material, ItemPath path, int pathWeight,
                           double itemStat, UpgradeInfo upgradeInfo) {
    public static final String ID_KEY = "bi_id";
    public static final String MONEY_KEY = "bi_money";

    public ItemStack create() {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color(name));
        is.setItemMeta(im);
        is = NBTUtils.setNBTString(is, ID_KEY, id);
        is = NBTUtils.setNBTInteger(is, MONEY_KEY, 0);
        return is;
    }

    public void onDamage(ItemStack is, BoundaryDamage damage) {
        if (is == null) return;
        if (path == ItemPath.SWORD) {
            damage.setDamage(itemStat);
            int sharpness = is.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            damage.addAdditive(sharpness * 0.1);
        } else if (path == ItemPath.HELMET || path == ItemPath.CHESTPLATE || path == ItemPath.LEGGINGS || path == ItemPath.BOOTS) {
            damage.addDamage(itemStat * -0.25);
            int protection = is.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            damage.addAdditive(protection * -0.025);
        }
    }
}
