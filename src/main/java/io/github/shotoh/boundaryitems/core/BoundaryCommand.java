package io.github.shotoh.boundaryitems.core;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.guis.admin.AdminGui;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.NBTUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.BukkitCommandManager;
import org.incendo.cloud.bukkit.parser.selector.SinglePlayerSelectorParser;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.parser.standard.IntegerParser;
import org.incendo.cloud.parser.standard.StringParser;
import org.incendo.cloud.suggestion.SuggestionProvider;

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
                .permission("bi.admin")
                .senderType(Player.class)
                .handler(ctx -> {
                    Player player = ctx.sender();
                    String id = ctx.get(CloudKey.of("id", String.class));
                    BoundaryItem item = ItemManager.getInstance().getItem(id);
                    if (item != null) {
                        ItemUtils.addItem(player, ItemUtils.createItem(id), 1);
                        Utils.sendMessage(player, "&bCreating &d" + item.getName());
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
                    } else {
                        Utils.sendMessage(player, "&cInvalid item");
                    }
                }));
        manager.command(builder.literal("remove")
                .permission("bi.admin")
                .senderType(CommandSender.class)
                .required("target", SinglePlayerSelectorParser.singlePlayerSelectorParser())
                .optional("amount", IntegerParser.integerComponent()
                        .parser(IntegerParser.integerParser(1, 64)))
                .handler(ctx -> {
                    Player target = ctx.get(CloudKey.of("target", Player.class));
                    int amount = ctx.getOrDefault(CloudKey.of("amount", Integer.class), 1);
                    ItemStack is = target.getItemInHand();
                    ItemUtils.removeItem(target, is, amount);
                }));
    }
}
