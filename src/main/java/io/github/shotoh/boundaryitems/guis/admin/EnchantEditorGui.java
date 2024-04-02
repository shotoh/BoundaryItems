package io.github.shotoh.boundaryitems.guis.admin;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.block.BlockManager;
import io.github.shotoh.boundaryitems.block.BoundaryBlock;
import io.github.shotoh.boundaryitems.enchants.BoundaryEnchant;
import io.github.shotoh.boundaryitems.enchants.EnchantManager;
import io.github.shotoh.boundaryitems.guis.ListGui;
import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;

public class EnchantEditorGui extends ListGui<BoundaryEnchant> {
    public EnchantEditorGui(BoundaryItems plugin) {
        super(plugin, "enchant_editor", "Enchant Editor",
                new ArrayList<>(EnchantManager.getInstance().getEnchants().values()), BoundaryEnchant::createAdminShowcase);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        super.onClick(event);
        if (event.getInventory().equals(event.getClickedInventory())) return;
        ItemStack is = event.getCurrentItem();
        if (is == null) return;
        if (!(is.getItemMeta() instanceof EnchantmentStorageMeta esm)) return;
        ArrayList<Enchantment> enchants = new ArrayList<>(esm.getStoredEnchants().keySet());
        if (enchants.size() != 1) return;
        Player player = (Player) event.getWhoClicked();
        if (EnchantManager.getInstance().getEnchants().containsKey(enchants.getFirst())) {
            Utils.sendMessage(player, "&cThis enchant already exists!");
            return;
        }
        BoundaryEnchant enchant = new BoundaryEnchant(enchants.getFirst());
        list.add(enchant);
        EnchantManager.getInstance().addEnchant(enchant);
        update(event.getInventory());
    }

    @Override
    public void onListClick(InventoryClickEvent event, int index) {
        Player player = (Player) event.getWhoClicked();
        if (event.isRightClick()) {
            GuiUtils.openInventory(plugin, player, new SpecificEnchantEditorGui(plugin, list.get(index)));
        } else if (event.isShiftClick()) {
            EnchantManager.getInstance().removeEnchant(list.remove(index));
            update(event.getInventory());
        }
    }
}
