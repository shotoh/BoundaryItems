package io.github.shotoh.boundaryitems.listeners;

import io.github.shotoh.boundaryitems.core.BoundaryDamage;
import io.github.shotoh.boundaryitems.items.BoundaryItem;
import io.github.shotoh.boundaryitems.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        BoundaryDamage damage = new BoundaryDamage(event.getDamage());
        if (!(event.getDamager() instanceof Player damager) || !(event.getEntity() instanceof Player damagee)) return;
        ItemStack weapon = damager.getItemInHand();
        BoundaryItem weaponItem = ItemUtils.getItem(weapon);
        if (weaponItem != null) weaponItem.onDamage(weapon, damage);
        for (ItemStack armor : damagee.getInventory().getArmorContents()) {
            BoundaryItem armorItem = ItemUtils.getItem(armor);
            if (armorItem != null) armorItem.onDamage(armor, damage);
        }
        event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
        event.setDamage(damage.calculate());
    }
}
