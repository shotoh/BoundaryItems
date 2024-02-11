package io.github.shotoh.boundaryitems.integrations;

import io.github.shotoh.boundaryitems.BoundaryItems;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultIntegration {
    public static Economy ECONOMY;
    public static Permission PERMISSION;

    public static void register(BoundaryItems plugin) {
        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            RegisteredServiceProvider<Economy> economyService = plugin.getServer().getServicesManager().getRegistration(Economy.class);
            RegisteredServiceProvider<Permission> permissionService = plugin.getServer().getServicesManager().getRegistration(Permission.class);
            if (economyService != null && permissionService != null) {
                ECONOMY = economyService.getProvider();
                PERMISSION = permissionService.getProvider();
                plugin.getLogger().info("Vault integration complete!");
                return;
            }
        }
        plugin.getLogger().severe("This plugin requires Vault to function properly!");
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
}