package io.github.shotoh.boundaryitems.core;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.features.LockChat;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.BukkitCommandManager;

public class ChannelCommand {
    private final BoundaryItems plugin;
    private final BukkitCommandManager<CommandSender> manager;
    private final Command.Builder<CommandSender> builder;

    public ChannelCommand(BoundaryItems plugin) {
        this.plugin = plugin;
        this.manager = plugin.getCommandManager();
        this.builder = manager.commandBuilder("ch");
    }

    public void register() {
        manager.command(builder);
        manager.command(builder.literal("lock")
                .permission("bi.admin")
                .senderType(CommandSender.class)
                .handler(ctx -> {
                    LockChat.getInstance().setLocked(true);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Utils.sendMessage(p, "&7Chat is now &bLOCKED");
                    }
                }));
        manager.command(builder.literal("unlock")
                .permission("bi.admin")
                .senderType(CommandSender.class)
                .handler(ctx -> {
                    LockChat.getInstance().setLocked(false);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Utils.sendMessage(p, "&7Chat is now &bUNLOCKED");
                    }
                }));
    }
}
