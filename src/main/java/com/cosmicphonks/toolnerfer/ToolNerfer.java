package com.cosmicphonks.toolnerfer;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class ToolNerfer extends JavaPlugin implements Listener {
    private static final double MAX_DAMAGE = 12.0; // 6 hearts

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ToolNerfer enabled. Mace and spear damage capped at 6 hearts.");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        Entity victim = event.getEntity();
        if (!(victim instanceof Player)) return;
        if (!(event.getDamager() instanceof Player attacker)) return;

        ItemStack weapon = attacker.getInventory().getItemInMainHand();
        if (!isLimitedWeapon(weapon.getType())) {
            // Also support a spear held in the offhand.
            ItemStack offhand = attacker.getInventory().getItemInOffHand();
            if (!isSpear(offhand.getType())) return;
        }

        double finalDamage = event.getFinalDamage();
        if (finalDamage <= MAX_DAMAGE || finalDamage <= 0.0) return;

        // Scale the raw damage so armor and Protection still apply normally,
        // while the resulting damage after those reductions cannot exceed 12 HP.
        double baseDamage = event.getDamage();
        event.setDamage(baseDamage * (MAX_DAMAGE / finalDamage));

        // Guard against small rounding differences in modifier calculations.
        if (event.getFinalDamage() > MAX_DAMAGE) {
            event.setDamage(event.getDamage() * (MAX_DAMAGE / event.getFinalDamage()));
        }
    }

    private boolean isLimitedWeapon(Material material) {
        return material == Material.MACE || isSpear(material);
    }

    private boolean isSpear(Material material) {
        return material.name().endsWith("_SPEAR");
    }
}