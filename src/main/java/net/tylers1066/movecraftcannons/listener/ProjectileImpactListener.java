package net.tylers1066.movecraftcannons.listener;

import at.pavlov.cannons.event.ProjectileImpactEvent;
import net.countercraft.movecraft.combat.features.tracking.DamageRecord;
import net.countercraft.movecraft.combat.features.tracking.events.CraftDamagedByEvent;
import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.CraftManager;
import net.countercraft.movecraft.craft.PlayerCraft;
import net.countercraft.movecraft.util.MathUtils;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import net.tylers1066.movecraftcannons.config.Config;
import net.tylers1066.movecraftcannons.damagetype.ProjectileImpactDamage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ProjectileImpactListener implements Listener {
    @EventHandler
    public void impactListener(ProjectileImpactEvent e) {
        if (!Config.EnableCannonsTracking)
            return;

        Craft craft = MathUtils.fastNearestCraftToLoc(CraftManager.getInstance().getCrafts(), e.getImpactLocation());
        if (!(craft instanceof PlayerCraft))
            return;
        if (!MathUtils.locIsNearCraftFast(craft, MathUtils.bukkit2MovecraftLoc(e.getImpactLocation())))
            return;

        UUID shooter = e.getShooterUID();
        Player cause = MovecraftCannons.getInstance().getServer().getPlayer(shooter);
        if (cause == null || !cause.isOnline())
            return;

        PlayerCraft playerCraft = (PlayerCraft) craft;
        DamageRecord record = new DamageRecord(cause, playerCraft.getPilot(), new ProjectileImpactDamage());
        CraftDamagedByEvent event = new CraftDamagedByEvent(playerCraft, record);
        Bukkit.getPluginManager().callEvent(event);
    }
}
