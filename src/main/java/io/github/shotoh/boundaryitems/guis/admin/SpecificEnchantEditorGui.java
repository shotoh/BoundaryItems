package io.github.shotoh.boundaryitems.guis.admin;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.enchants.BoundaryEnchant;
import io.github.shotoh.boundaryitems.guis.BoundaryGui;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class SpecificEnchantEditorGui extends BoundaryGui {
    private final BoundaryEnchant enchant;

    public SpecificEnchantEditorGui(BoundaryItems plugin, BoundaryEnchant enchant) {
        super(plugin, "specific_enchant_editor", "Specific Enchant Editor");
        this.enchant = enchant;
    }

    @Override
    protected void update(Inventory inv) {
        for (int i = 0; i < 54; i++) {
            if (i == 4) {
                inv.setItem(i, enchant.createAdminShowcase());
            } else if (i == 22) {
                inv.setItem(i, ItemUtils.createMiscItem("Name", new String[] {
                        enchant.getName()
                }, Material.NAME_TAG, null, 1));
            } else if (i == 29) {
                inv.setItem(i, ItemUtils.createMiscItem("Material", new String[] {
                        "" + enchant.getMaterial()
                }, Material.IRON_INGOT, null, 1));
            } else if (i == 33) {
                inv.setItem(i, ItemUtils.createMiscItem("Costs", enchant.getCostsShowcase(),
                        Material.GOLD_INGOT, null, 1));
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
            GuiUtils.askInput(plugin, this, player, "&6Enter Name:", enchant::setName);
        } else if (slot == 29) {
            GuiUtils.askInput(plugin, this, player, "&6Enter Material:", (s) -> {
                try {
                    Material m = Material.valueOf(s);
                    enchant.setMaterial(m);
                } catch (IllegalArgumentException e) {
                    Utils.sendMessage(player, "&cInvalid material!");
                }
            });
        } else if (slot == 33) {
            GuiUtils.askInput(plugin, this, player, "&6Enter Costs:", (s) -> {
                enchant.setCosts(Arrays.stream(s.split(", ")).map((s1) -> {
                    try {
                        return Integer.parseInt(s1);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }).toList());
            });
        } else if (slot == 49) {
            GuiUtils.closeInventory(plugin, player);
        }
    }
}
