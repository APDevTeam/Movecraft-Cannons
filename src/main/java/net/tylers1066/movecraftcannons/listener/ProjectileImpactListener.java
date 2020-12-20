package net.tylers1066.movecraftcannons.listener;

import at.pavlov.cannons.event.ProjectileImpactEvent;
import net.countercraft.movecraft.combat.movecraftcombat.tracking.DamageManager;
import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.CraftManager;
import net.countercraft.movecraft.utils.MathUtils;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import net.tylers1066.movecraftcannons.config.Config;
import net.tylers1066.movecraftcannons.damagetype.ProjectileImpactDamage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ProjectileImpactListener implements Listener {
    @EventHandler
    public void impactListener(ProjectileImpactEvent e) {
        if(!Config.EnableCannonsTracking)
            return;


        Craft craft = CraftManager.getInstance().fastNearestCraftToLoc(e.getImpactLocation());
        if(craft == null)
            return;

        if(!MathUtils.locIsNearCraftFast(craft, MathUtils.bukkit2MovecraftLoc(e.getImpactLocation())))
            return;

        UUID shooter = e.getShooterUID();
        Player cause = MovecraftCannons.getInstance().getServer().getPlayer(shooter);
        if(cause == null || !cause.isOnline())
            return;

        DamageManager.getInstance().addDamageRecord(craft, cause, new ProjectileImpactDamage());
    }
}
