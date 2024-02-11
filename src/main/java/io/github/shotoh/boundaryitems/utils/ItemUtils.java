package io.github.shotoh.boundaryitems.utils;

import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemUtils {
    public static ItemStack createItem(String id) {
        BoundaryItem item = ItemManager.getInstance().getItem(id);
        if (item == null) return null;
        return item.create();
    }

    public static BoundaryItem getItem(ItemStack is) {
        String id = NBTUtils.getNBTString(is, BoundaryItem.ID_KEY);
        return ItemManager.getInstance().getItem(id);
    }

    public static boolean isItem(ItemStack is) {
        return getItem(is) != null;
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
    }
}
