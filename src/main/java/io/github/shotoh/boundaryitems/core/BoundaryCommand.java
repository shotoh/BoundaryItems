package io.github.shotoh.boundaryitems.core;

import com.connorlinfoot.titleapi.TitleAPI;
import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.consumables.BoundaryConsumable;
import io.github.shotoh.boundaryitems.consumables.ConsumableManager;
import io.github.shotoh.boundaryitems.guis.admin.AdminGui;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.BukkitCommandManager;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.parser.standard.IntegerParser;
import org.incendo.cloud.parser.standard.StringParser;
import org.incendo.cloud.permission.PredicatePermission;
import org.incendo.cloud.suggestion.SuggestionProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoundaryCommand {
    private final BoundaryItems plugin;
    private final BukkitCommandManager<CommandSender> manager;
    private final Command.Builder<CommandSender> builder;

    public BoundaryCommand(BoundaryItems plugin) {
        this.plugin = plugin;
        this.manager = plugin.getCommandManager();
        this.builder = manager.commandBuilder("bi");
    }

    public void register() {
        manager.command(builder);
        manager.command(builder.literal("item")
                .required("id", StringParser.stringComponent()
                        .suggestionProvider(SuggestionProvider.suggestingStrings(ItemManager.getInstance().getItems().keySet())))
                .optional("target", PlayerParser.playerParser())
                .permission("bi.admin")
                .senderType(Player.class)
                .handler(ctx -> {
                    Player player = ctx.sender();
                    Player target = ctx.getOrDefault(CloudKey.of("target", Player.class), null);
                    if (target == null) target = player;
                    String id = ctx.get(CloudKey.of("id", String.class));
                    BoundaryItem item = ItemManager.getInstance().getItem(id);
                    if (item != null) {
                        ItemUtils.addItem(target, ItemUtils.createItem(id), 1);
                        Utils.sendMessage(player, "&bCreating &d" + item.getName());
                        target.updateInventory();
                    } else {
                        Utils.sendMessage(player, "&cUnknown item id: " + id);
                    }
                }));
        manager.command(builder.literal("gui")
                .permission("bi.admin")
                .senderType(Player.class)
                .handler(ctx -> GuiUtils.openInventory(plugin, ctx.sender(), new AdminGui(plugin))));
        manager.command(builder.literal("money")
                .permission("bi.admin")
                .senderType(Player.class)
                .required("amount", IntegerParser.integerParser())
                .handler(ctx -> {
                    Player player = ctx.sender();
                    int amount = ctx.get(CloudKey.of("amount", Integer.class));
                    BoundaryItem item = ItemManager.getInstance().getItem(player.getItemInHand());
                    if (item != null) {
                        ItemStack is = player.getItemInHand();
                        ItemStack editedIs = NBTUtils.setNBTInteger(is, BoundaryItem.MONEY_KEY, amount);
                        ItemUtils.removeItem(player, is, 1);
                        ItemUtils.addItem(player, editedIs, 1);
                        player.updateInventory();
                    } else {
                        Utils.sendMessage(player, "&cInvalid item");
                    }
                }));
        manager.command(builder.literal("remove")
                .permission("bi.admin")
                .senderType(CommandSender.class)
                .required("target", PlayerParser.playerParser())
                .optional("amount", IntegerParser.integerComponent()
                        .parser(IntegerParser.integerParser(1, 64)))
                .handler(ctx -> {
                    Player target = ctx.get(CloudKey.of("target", Player.class));
                    int amount = ctx.getOrDefault(CloudKey.of("amount", Integer.class), 1);
                    ItemStack is = target.getItemInHand();
                    ItemUtils.removeItem(target, is, amount);
                }));
        manager.command(builder.literal("lock")
                .permission(PredicatePermission.of(Utils::isShotoh))
                .senderType(Player.class)
                .required("target", PlayerParser.playerParser())
                .handler(ctx -> {
                    Player player = ctx.sender();
                    Player target = ctx.get(CloudKey.of("target", Player.class));
                    ItemStack is = player.getItemInHand();
                    ItemUtils.removeItem(target, is, 1);
                    player.getInventory().addItem(ItemUtils.setUUID(is, target.getUniqueId()));
                    Utils.sendMessage(player, "&bLOCKED&7 item");
                }));
        manager.command(builder.literal("consumable")
                .required("id", StringParser.stringComponent()
                        .suggestionProvider(SuggestionProvider.suggestingStrings(ConsumableManager.getInstance().getConsumables().keySet())))
                .permission(PredicatePermission.of(Utils::isShotoh))
                .senderType(Player.class)
                .handler(ctx -> {
                    Player player = ctx.sender();
                    String id = ctx.get(CloudKey.of("id", String.class));
                    BoundaryConsumable consumable = ConsumableManager.getInstance().getConsumable(id);
                    if (consumable != null) {
                        ItemUtils.addItem(player, consumable.create(), 1);
                        Utils.sendMessage(player, "&bCreating &d" + consumable.getName());
                        player.updateInventory();
                    } else {
                        Utils.sendMessage(player, "&cUnknown item id: " + id);
                    }
                }));
        manager.command(builder.literal("roulette")
                .permission("bi.admin")
                .senderType(Player.class)
                .handler(ctx -> new BukkitRunnable() {
                    int ticks = 0;
                    Player target = null;
                    @Override
                    public void run() {
                        if (ticks < 160 && ticks % 5 == 0) {
                            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                            int random = ThreadLocalRandom.current().nextInt(players.size());
                            target = players.get(random);
                            if (target == null) this.cancel();
                            for (Player p : players) {
                                TitleAPI.sendTitle(p, 0, 6, 0, Utils.color("&c" + target.getDisplayName()), "");
                                Utils.playSound(p, Sound.NOTE_PLING, 0.5f, 1f);
                            }
                        } else if (ticks == 160) {
                            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                            for (Player p : players) {
                                TitleAPI.sendTitle(p, 0, 20, 0, Utils.color("&a" + target.getDisplayName()), "");
                                Utils.playSound(p, Sound.NOTE_PLING, 0.5f, 2f);
                            }
                            Location loc = target.getLocation();
                            Utils.playSound(loc, Sound.EXPLODE, 0.5f, 0.5f);
                            Utils.playSound(loc, Sound.AMBIENCE_THUNDER, 0.5f, 1f);
                            loc.getWorld().strikeLightningEffect(loc);
                            Utils.execute(target);
                        }
                        ticks++;
                    }
                }.runTaskTimer(plugin, 0, 1)));
    }
}
