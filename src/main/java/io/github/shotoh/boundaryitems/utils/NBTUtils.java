package io.github.shotoh.boundaryitems.utils;

import io.github.shotoh.boundaryitems.items.ItemPath;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class NBTUtils {
    public static String getNBTString(ItemStack is, String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsIs = CraftItemStack.asNMSCopy(is);
        if (nmsIs != null && nmsIs.hasTag()) {
            NBTTagCompound tagCompound = nmsIs.getTag();
            return tagCompound.getString(key);
        } else {
            return null;
        }
    }

    public static ItemStack setNBTString(ItemStack is, String key, String value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsIs = CraftItemStack.asNMSCopy(is);
        if (nmsIs != null) {
            NBTTagCompound tagCompound = (nmsIs.hasTag()) ? nmsIs.getTag() : new NBTTagCompound();
            tagCompound.setString(key, value);
            nmsIs.setTag(tagCompound);
            return CraftItemStack.asBukkitCopy(nmsIs);
        } else {
            return null;
        }
    }

    public static int getNBTInteger(ItemStack is, String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsIs = CraftItemStack.asNMSCopy(is);
        if (nmsIs != null && nmsIs.hasTag()) {
            NBTTagCompound tagCompound = nmsIs.getTag();
            return tagCompound.getInt(key);
        } else {
            return -1;
        }
    }

    public static ItemStack setNBTInteger(ItemStack is, String key, int value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsIs = CraftItemStack.asNMSCopy(is);
        if (nmsIs != null) {
            NBTTagCompound tagCompound = (nmsIs.hasTag()) ? nmsIs.getTag() : new NBTTagCompound();
            tagCompound.setInt(key, value);
            nmsIs.setTag(tagCompound);
            return CraftItemStack.asBukkitCopy(nmsIs);
        } else {
            return null;
        }
    }

    public static ItemStack addAttributes(ItemStack is, ItemPath path, int itemStat) {
        String attributeName;
        if (path == ItemPath.WEAPON) {
            attributeName = "generic.attackDamage";
        } else if (path.isArmor()) {
            attributeName = "generic.armor";
        } else {
            return is;
        }
        net.minecraft.server.v1_8_R3.ItemStack nmsIs = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = nmsIs.getTag();
        if (compound == null) compound = new NBTTagCompound();
        NBTTagList modifiers = new NBTTagList();
        NBTTagCompound modifier = new NBTTagCompound();
        modifier.set("AttributeName", new NBTTagString(attributeName));
        modifier.set("Name", new NBTTagString(attributeName));
        modifier.set("Operation", new NBTTagInt(0));
        modifier.set("Amount", new NBTTagInt(itemStat));
        UUID uuid = UUID.randomUUID();
        modifier.set("UUIDLeast", new NBTTagLong(uuid.getLeastSignificantBits()));
        modifier.set("UUIDMost", new NBTTagLong(uuid.getMostSignificantBits()));
        modifiers.add(modifier);
        compound.set("AttributeModifiers", modifiers);
        nmsIs.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsIs);
    }
}
