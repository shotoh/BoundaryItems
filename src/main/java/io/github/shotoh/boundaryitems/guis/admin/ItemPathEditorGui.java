package io.github.shotoh.boundaryitems.guis.admin;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.guis.ListGui;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.items.ItemPath;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ItemPathEditorGui extends ListGui<BoundaryItem> {
    public ItemPathEditorGui(BoundaryItems plugin, ItemPath path) {
        super(plugin, "item_path_editor", "Item Path Editor",
                ItemManager.getInstance().getPaths().get(path), BoundaryItem::createShowcase);
    }

    @Override
    public void onListClick(InventoryClickEvent event, int index) {
        Player player = (Player) event.getWhoClicked();
        if (event.isRightClick()) {
            GuiUtils.openInventory(plugin, player, new SpecificItemEditorGui(plugin, list.get(index)));
        } else if (event.isShiftClick()) {
            list.remove(index);
        }
        update(event.getInventory());
    }
}
