package io.github.shotoh.boundaryitems.utils;

import io.github.shotoh.boundaryitems.BoundaryItems;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

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
}
