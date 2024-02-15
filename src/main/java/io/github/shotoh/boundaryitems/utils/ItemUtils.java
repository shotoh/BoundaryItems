package io.github.shotoh.boundaryitems.utils;

import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemUtils {
    public static ItemStack createItem(String id) {
        return createItem(ItemManager.getInstance().getItem(id));
    }

    public static ItemStack createItem(BoundaryItem item) {
        if (item == null) return null;
        return item.create();
    }

    public static ItemStack createMiscItem(String name, String[] lore, Material material, Map<Enchantment, Integer> enchants, int amount) {
        ItemStack is = new ItemStack(material, amount);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color("&b" + name));
        if (lore != null) im.setLore(Arrays.stream(lore).map(s -> Utils.color("&7" + s)).toList());
        if (enchants != null) {
            for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                im.addEnchant(entry.getKey(), entry.getValue(), true);
            }
        }
        is.setItemMeta(im);
        return is;
    }

    public static boolean isItem(ItemStack is) {
        return ItemManager.getInstance().getItem(is) != null;
    }

    public static void addItem(Player player, ItemStack is, int amount) {
        if (is == null) return;
        ItemStack cloneIs = is.clone();
        cloneIs.setAmount(Math.min(Math.max(0, amount), 64));
        Map<Integer, ItemStack> failed = player.getInventory().addItem(cloneIs);
        if (failed.isEmpty()) return;
        for (ItemStack failedItem : failed.values()) {
            player.getWorld().dropItem(player.getLocation(), failedItem);
        }
    }

    public static void removeItem(Player player, ItemStack is, int amount) {
        List<ItemStack> list = new ArrayList<>();
        while (amount > 64) {
            ItemStack cloneIs = is.clone();
            cloneIs.setAmount(64);
            list.add(cloneIs);
            amount -= 64;
        }
        ItemStack cloneIs = is.clone();
        cloneIs.setAmount(amount);
        list.add(cloneIs);
        player.getInventory().removeItem(list.toArray(new ItemStack[]{}));
        player.updateInventory();
    }
}
