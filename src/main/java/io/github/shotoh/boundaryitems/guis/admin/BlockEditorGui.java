package io.github.shotoh.boundaryitems.guis.admin;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.block.BlockManager;
import io.github.shotoh.boundaryitems.block.BoundaryBlock;
import io.github.shotoh.boundaryitems.guis.ListGui;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BlockEditorGui extends ListGui<BoundaryBlock> {
    public BlockEditorGui(BoundaryItems plugin) {
        super(plugin, "block_editor", "Block Editor",
                BlockManager.getInstance().getBlockList(), BoundaryBlock::createShowcase);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        super.onClick(event);
        if (event.getInventory().equals(event.getClickedInventory())) return;
        ItemStack is = event.getCurrentItem();
        if (is == null) return;
        Player player = (Player) event.getWhoClicked();
        if (BlockManager.getInstance().getBlocks().containsKey(is.getType())) {
            Utils.sendMessage(player, "&cThis material already exists!");
            return;
        }
        BlockManager.getInstance().addBlock(new BoundaryBlock(is.getType(), 0, 0, 0));
    }

    @Override
    public void onListClick(InventoryClickEvent event, int index) {
        Player player = (Player) event.getWhoClicked();
        if (event.isRightClick()) {
            GuiUtils.openInventory(plugin, player, new SpecificBlockEditorGui(plugin, list.get(index)));
        } else if (event.isShiftClick()) {
            BlockManager.getInstance().removeBlock(list.remove(index));
            update(event.getInventory());
        }
    }
}
