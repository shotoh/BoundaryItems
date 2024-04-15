package io.github.shotoh.boundaryitems.utils;

import io.github.shotoh.boundaryitems.BoundaryItems;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;

public class Utils {
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void sendMessage(CommandSender sender, String... messages) {
        for (String s : messages) {
            sender.sendMessage(color(s));
        }
    }

    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void playSound(Location loc, Sound sound, float volume, float pitch) {
        loc.getWorld().playSound(loc, sound, volume, pitch);
    }

    public static File getFile(BoundaryItems plugin, String path) {
        File file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) plugin.saveResource(path, false);
        return file;
    }

    public static boolean isShotoh(Player player) {
        return player.getUniqueId().equals(UUID.fromString("7e5ad159-3b46-46df-a698-54be6bf907a2"));
    }

    public static boolean checkCooldown(Map<UUID, Long> cooldowns, UUID uuid, double abilityCooldown) {
        Long cooldown = cooldowns.get(uuid);
        if (cooldown == null || cooldown < System.currentTimeMillis()) {
            setCooldown(cooldowns, uuid, abilityCooldown);
            return true;
        }
        return false;
    }

    public static String formatCooldown(Map<UUID, Long> cooldowns, UUID uuid) {
        DecimalFormat df = new DecimalFormat("0.##");
        return df.format((cooldowns.get(uuid) - System.currentTimeMillis()) / 1000);
    }

    public static void setCooldown(Map<UUID, Long> cooldowns, UUID uuid, double abilityCooldown) {
        cooldowns.put(uuid, (long) (System.currentTimeMillis() + (abilityCooldown * 1000L)));
    }
}
