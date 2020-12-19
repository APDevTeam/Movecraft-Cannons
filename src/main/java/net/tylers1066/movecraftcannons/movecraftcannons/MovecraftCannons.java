package net.tylers1066.movecraftcannons.movecraftcannons;

import at.pavlov.cannons.Cannons;
import net.countercraft.movecraft.combat.movecraftcombat.MovecraftCombat;
import net.tylers1066.movecraftcannons.movecraftcannons.listener.ProjectileImpactListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;


public final class MovecraftCannons extends JavaPlugin {
    private static MovecraftCannons instance;
    private static Cannons cannonsPlugin = null;

    public static MovecraftCannons getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Load cannons plugin
        Plugin cannons = getServer().getPluginManager().getPlugin("Cannons");
        if(!(cannons instanceof Cannons)) {
            getLogger().log(Level.SEVERE, "Cannons not found");
        }
        cannonsPlugin = (Cannons) cannons;
        getLogger().info("Cannons found");

        Plugin mcc = getServer().getPluginManager().getPlugin("Movecraft-Combat");
        if(mcc instanceof MovecraftCombat) {
            getLogger().info("Movecraft-Combat found, enabling integration");
            getServer().getPluginManager().registerEvents(new ProjectileImpactListener(), this);
        }

        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Cannons getCannonsPlugin() {
        return cannonsPlugin;
    }
}
