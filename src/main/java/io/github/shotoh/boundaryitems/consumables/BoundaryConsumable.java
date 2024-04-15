package io.github.shotoh.boundaryitems.consumables;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class BoundaryConsumable {
    protected final BoundaryItems plugin;
    protected final String id;
    protected String name;
    protected String[] lore;
    protected Material material;

    public BoundaryConsumable(BoundaryItems plugin, String id, String name, String[] lore, Material material) {
        this.plugin = plugin;
        this.id = id;
        this.name = name;
        this.lore = new String[] {};
        if (lore != null) this.lore = lore;
        this.material = material;
    }

    public BoundaryItems getPlugin() {
        return plugin;
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

    public String[] getLore() {
        return lore;
    }

    public void setLore(String[] lore) {
        this.lore = lore;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ItemStack create(int amount) {
        ItemStack is = new ItemStack(material, amount);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color(name));
        im.setLore(Arrays.stream(lore).map(Utils::color).toList());
        is.setItemMeta(im);
        is = NBTUtils.setNBTString(is, BoundaryItem.ID_KEY, id);
        return is;
    }

    public abstract void onConsume(PlayerItemConsumeEvent event);
}
