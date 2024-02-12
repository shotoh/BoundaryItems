package io.github.shotoh.boundaryitems.guis.admin;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.guis.BoundaryGui;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SpecificItemEditorGui extends BoundaryGui {
    private final BoundaryItem item;

    public SpecificItemEditorGui(BoundaryItems plugin, BoundaryItem item) {
        super(plugin, "specific_item_editor", "Specific Item Editor");
        this.item = item;
    }

    @Override
    protected void update(Inventory inv) {
        for (int i = 0; i < 54; i++) {
            if (i == 4) {
                inv.setItem(i, item.createShowcase());
            } else if (i == 19) {
                inv.setItem(i, ItemUtils.createMiscItem("Name", new String[] {
                        item.getName()
                }, Material.NAME_TAG, null, 1));
            } else if (i == 21) {
                inv.setItem(i, ItemUtils.createMiscItem("Material", new String[] {
                        "" + item.getMaterial()
                }, Material.IRON_INGOT, null, 1));
            } else if (i == 23) {
                inv.setItem(i, ItemUtils.createMiscItem("Weight", new String[] {
                        "" + item.getPathWeight()
                }, Material.DIAMOND_BOOTS, null, 1));
            } else if (i == 25) {
                inv.setItem(i, ItemUtils.createMiscItem("Stat", new String[] {
                        "" + item.getItemStat()
                }, Material.EMERALD, null, 1));
            } else if (i == 29) {
                inv.setItem(i, ItemUtils.createMiscItem("Money Cost", new String[] {
                        "" + item.getUpgradeInfo().getMoneyCost()
                }, Material.GOLD_INGOT, null, 1));
            } else if (i == 31) {
                inv.setItem(i, ItemUtils.createMiscItem("Level Cost", new String[] {
                        "" + item.getUpgradeInfo().getLevelCost()
                }, Material.EXP_BOTTLE, null, 1));
            } else if (i == 33) {
                inv.setItem(i, ItemUtils.createMiscItem("Enchant Cost", new String[] {
                        "" + item.getUpgradeInfo().getEnchantCost()
                }, Material.ENCHANTED_BOOK, null, 1));
            } else if (i == 49) {
                inv.setItem(i, GuiUtils.getGuiClose());
            } else if (i < 9 || i > 35) {
                inv.setItem(i, GuiUtils.getGuiGlass(DyeColor.RED.getData()));
            }
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        if (!event.getClickedInventory().equals(event.getInventory())) return;
        update(event.getInventory());
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (slot == 19) {
            GuiUtils.askInput(plugin, this, player, "&6Enter Name:", item::setName);
        } else if (slot == 21) {
            GuiUtils.askInput(plugin, this, player, "&6Enter Material:", (s) -> {
                try {
                    Material m = Material.valueOf(s);
                    item.setMaterial(m);
                } catch (IllegalArgumentException e) {
                    Utils.sendMessage(player, "&cInvalid material!");
                }
            });
        } else if (slot == 23) {
            GuiUtils.askNumber(plugin, this, player, "&6Enter Weight:", item::setPathWeight);
        } else if (slot == 25) {
            GuiUtils.askNumber(plugin, this, player, "&6Enter Stat:", item::setItemStat);
        } else if (slot == 29) {
            GuiUtils.askNumber(plugin, this, player, "&6Enter Money Cost:", (i) -> item.getUpgradeInfo().setMoneyCost(i));
        } else if (slot == 31) {
            GuiUtils.askNumber(plugin, this, player, "&6Enter Level Cost:", (i) -> item.getUpgradeInfo().setLevelCost(i));
        } else if (slot == 33) {
            GuiUtils.askNumber(plugin, this, player, "&6Enter Enchant Cost:", (i) -> item.getUpgradeInfo().setEnchantCost(i));
        } else if (slot == 49) {
            GuiUtils.closeInventory(plugin, player);
        }
    }
}
