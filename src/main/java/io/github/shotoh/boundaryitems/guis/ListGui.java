package io.github.shotoh.boundaryitems.guis;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Function;

public class ListGui<T> extends BoundaryGui {
    protected final List<T> list;
    protected final Function<T, ItemStack> function;
    protected int page;

    public ListGui(BoundaryItems plugin, String id, String name, List<T> list, Function<T, ItemStack> function) {
        super(plugin, id, name, 54);
        this.list = list;
        this.function = function;
        this.page = 0;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    protected void update(Inventory inv) {
        for (int i = 0; i < 54; i++) {
            if (GuiUtils.notBorder(i)) {
                int index = GuiUtils.convertSlotToIndex(i);
                index += page * 28;
                if (index < list.size()) {
                    inv.setItem(i, function.apply(list.get(index)));
                } else {
                    inv.setItem(i, null);
                }
            } else if (i == 48) {
                if (page > 0) {
                    inv.setItem(i, GuiUtils.getGuiPrevious());
                } else {
                    inv.setItem(i, GuiUtils.getGuiGlass());
                }
            } else if (i == 49) {
                inv.setItem(i, GuiUtils.getGuiClose());
            } else if (i == 50) {
                if (page < GuiUtils.getMaxPage(list)) {
                    inv.setItem(i, GuiUtils.getGuiNext());
                } else {
                    inv.setItem(i, GuiUtils.getGuiGlass());
                }
            } else {
                inv.setItem(i, GuiUtils.getGuiGlass());
            }
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (event.getClickedInventory() == null || event.getClickedInventory().equals(player.getInventory())) return;
        if (GuiUtils.notBorder(slot)) {
            int index = GuiUtils.convertSlotToIndex(slot);
            if (index >= 0 && index < 28) {
                index += page * 28;
                if (index < list.size()) onListClick(event, index);
            }
        } else if (slot == 48) {
            if (page > 0) {
                page--;
                update(event.getClickedInventory());
                Utils.playSound(player, Sound.CLICK, 0.5f, 1f);
            }
        } else if (slot == 49) {
            GuiUtils.closeInventory(plugin, player);
        } else if (slot == 50) {
            if (page < GuiUtils.getMaxPage(list)) {
                page++;
                update(event.getClickedInventory());
                Utils.playSound(player, Sound.CLICK, 0.5f, 1f);
            }
        }
    }

    public void onListClick(InventoryClickEvent event, int index) {
        //
    }
}
