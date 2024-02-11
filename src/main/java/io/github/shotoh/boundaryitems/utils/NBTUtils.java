package io.github.shotoh.boundaryitems.utils;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

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
}
