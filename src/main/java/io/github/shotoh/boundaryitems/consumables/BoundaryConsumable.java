package io.github.shotoh.boundaryitems.consumables;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public abstract class BoundaryConsumable {
    protected final BoundaryItems plugin;
    protected final String id;
    protected String name;
    protected String[] lore;
    protected Material material;
    protected double cooldown;
    protected PotionType type;

    public BoundaryConsumable(BoundaryItems plugin, String id, String name, String[] lore, Material material, double cooldown, PotionType type) {
        this.plugin = plugin;
        this.id = id;
        this.name = name;
        this.lore = new String[] {};
        if (lore != null) this.lore = lore;
        this.material = material;
        this.cooldown = cooldown;
        this.type = type;
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

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public PotionType getType() {
        return type;
    }

    public void setType(PotionType type) {
        this.type = type;
    }

    public ItemStack create() {
        ItemStack is = new Potion(type).toItemStack(1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color(name));
        im.setLore(Arrays.stream(lore).map(Utils::color).toList());
        is.setItemMeta(im);
        is = NBTUtils.setNBTString(is, BoundaryItem.ID_KEY, id);
        return is;
    }

    public abstract void onConsume(PlayerItemConsumeEvent event);
}
