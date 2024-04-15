package io.github.shotoh.boundaryitems.consumables;

import io.github.shotoh.boundaryitems.BoundaryItems;
import io.github.shotoh.boundaryitems.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

public class Frigidflask extends BoundaryConsumable {
    public Frigidflask(BoundaryItems plugin) {
        super(plugin, "frigidflask", "&b&lFrigidflask", new String[] {
                "&7Infinity",
                "",
                "&7Gives the player &bSpeed II&7 for &b10",
                "&7seconds. Afterwards, creates a",
                "&7shockwave that gives &cSlowness I",
                "&7for &c5&7 seconds to nearby players.",
                "&7Hitting &b2+&7 players with the",
                "&7shockwave will refresh the &bSpeed",
                "&7duration."
        }, Material.POTION, 30);
    }

    @Override
    public ItemStack create(int amount) {
        ItemStack is = super.create(amount);
        PotionMeta pm = (PotionMeta) is.getItemMeta();
        pm.setMainEffect(PotionEffectType.SPEED);
        is.setItemMeta(pm);
        return is;
    }

    @Override
    public void onConsume(PlayerItemConsumeEvent event) {
        new BukkitRunnable() {
            int ticks = 0;
            final Player player = event.getPlayer();
            @Override
            public void run() {
                if (player == null) {
                    this.cancel();
                    return;
                }
                if (ticks < 200) {
                    if (ticks % 2 == 0) {
                        if (ticks == 0) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1));
                            Utils.playSound(player, Sound.PORTAL_TRAVEL, 1f, 2f);
                        }
                        Location loc = player.getLocation();
                        new ParticleBuilder(ParticleEffect.REDSTONE, loc)
                                .setParticleData(new RegularColor(85, 255, 255))
                                .display();
                    }
                } else if (ticks == 200 || ticks == 203 || ticks == 206) {
                    Location loc = player.getLocation();
                    Utils.playSound(loc, Sound.GLASS, 1f, 1.5f);
                } else if (ticks == 209) {
                    Location loc = player.getLocation();
                    Utils.playSound(loc, Sound.GLASS, 1f, 0.5f);
                    Utils.playSound(loc, Sound.EXPLODE, 1f, 0.5f);
                    Utils.playSound(loc, Sound.EXPLODE, 1f, 0.7f);
                    int count = 0;
                    for (Entity e : loc.getWorld().getNearbyEntities(loc, 3, 3, 3)) {
                        if (!(e instanceof Player p)) continue;
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 0));
                        count++;
                    }
                    if (count >= 2) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1));
                    }
                }
                ticks++;
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}
