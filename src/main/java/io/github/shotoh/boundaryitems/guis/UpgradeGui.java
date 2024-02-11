package io.github.shotoh.boundaryitems.guis;

import io.github.shotoh.boundaryitems.utils.GuiUtils;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpgradeGui extends BoundaryGui {
    private final Player player;
    private final ItemStack is;

    public UpgradeGui(Player player, ItemStack is) {
        super("upgrade", "Upgrade");
        this.player = player;
        this.is = is.clone();
        is.setType(Material.AIR);
    }

    @Override
    protected void update(Inventory inv) {
        for (int i = 0; i < 54; i++) {
            if (i == 4) {
                inv.setItem(i, is);
            } else if (i == 22) {
                // ascension stairs
            } else if (i == 49) {
                // close
            } else if (!GuiUtils.notBorder(i)) {
                // purple glass
            }
        }
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        super.onOpen(event);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        super.onClick(event);
        event.setCancelled(true);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);
    }
}
