package io.github.shotoh.boundaryitems.guis.admin;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.block.BoundaryBlock;
import io.github.shotoh.boundaryitems.guis.BoundaryGui;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SpecificBlockEditorGui extends BoundaryGui {
    private final BoundaryBlock block;

    public SpecificBlockEditorGui(BoundaryItems plugin, BoundaryBlock block) {
        super(plugin, "specific_block_editor", "Specific Block Editor");
        this.block = block;
    }

    @Override
    protected void update(Inventory inv) {
        for (int i = 0; i < 54; i++) {
            if (i == 4) {
                inv.setItem(i, block.createShowcase());
            } else if (i == 22) {
                inv.setItem(i, ItemUtils.createMiscItem("Breaking Power", new String[] {
                        "" + block.getBreakingPower()
                }, Material.DIAMOND_PICKAXE, null, 1));
            } else if (i == 29) {
                inv.setItem(i, ItemUtils.createMiscItem("Price", new String[] {
                        "" + block.getBlockPrice()
                }, Material.GOLD_INGOT, null, 1));
            } else if (i == 33) {
                inv.setItem(i, ItemUtils.createMiscItem("Experience", new String[] {
                        "" + block.getBlockExp()
                }, Material.EXP_BOTTLE, null, 1));
            } else if (i == 49) {
                inv.setItem(i, GuiUtils.getGuiClose());
            } else if (i < 9 || i > 44) {
                inv.setItem(i, GuiUtils.getGuiGlass(DyeColor.RED.getData()));
            }
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        if (!event.getInventory().equals(event.getClickedInventory())) return;
        update(event.getInventory());
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (slot == 22) {
            GuiUtils.askNumber(plugin, this, player, "&6Enter Breaking Power:", block::setBreakingPower);
        } else if (slot == 29) {
            GuiUtils.askNumber(plugin, this, player, "&6Enter Price:", block::setBlockPrice);
        } else if (slot == 33) {
            GuiUtils.askNumber(plugin, this, player, "&6Enter Experience:", block::setBlockExp);
        } else if (slot == 49) {
            GuiUtils.closeInventory(plugin, player);
        }
    }
}
