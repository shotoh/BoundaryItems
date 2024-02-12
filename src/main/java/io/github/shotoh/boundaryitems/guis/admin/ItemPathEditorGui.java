package io.github.shotoh.boundaryitems.guis.admin;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.guis.ListGui;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.items.ItemManager;
import io.github.shotoh.boundaryitems.items.ItemPath;
import io.github.shotoh.boundaryitems.items.UpgradeInfo;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemPathEditorGui extends ListGui<BoundaryItem> {
    private final ItemPath path;

    public ItemPathEditorGui(BoundaryItems plugin, ItemPath path) {
        super(plugin, "item_path_editor", "Item Path Editor",
                ItemManager.getInstance().getPaths().get(path), BoundaryItem::createShowcase);
        this.path = path;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        if (event.getInventory().equals(event.getClickedInventory())) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack is = event.getCurrentItem();
        if (is == null) return;
        GuiUtils.askInput(plugin, this, player, "&6Enter ID:", (s) -> {
            ItemManager.getInstance().addItem(new BoundaryItem(s, "NULL", is.getType(), path, 0, 0,
                    new UpgradeInfo(0, 0, 0)));
        });
    }

    @Override
    public void onListClick(InventoryClickEvent event, int index) {
        Player player = (Player) event.getWhoClicked();
        if (event.isRightClick()) {
            GuiUtils.openInventory(plugin, player, new SpecificItemEditorGui(plugin, list.get(index)));
        } else if (event.isShiftClick()) {
            list.remove(index);
            update(event.getInventory());
        }
    }
}
