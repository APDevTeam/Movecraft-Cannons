package net.tylers1066.movecraftcannons;

import at.pavlov.cannons.Cannons;
import net.countercraft.movecraft.combat.movecraftcombat.MovecraftCombat;
import net.tylers1066.movecraftcannons.config.Config;
import net.tylers1066.movecraftcannons.listener.ProjectileImpactListener;
import net.tylers1066.movecraftcannons.listener.TranslationListener;
import net.tylers1066.movecraftcannons.localisation.I18nSupport;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;


public final class MovecraftCannons extends JavaPlugin {
    private static MovecraftCannons instance;
    private static Cannons cannonsPlugin = null;

    public static MovecraftCannons getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        Config.Debug = getConfig().getBoolean("Debug", false);

        // TODO other languages
        String[] languages = {"en"};
        for (String s : languages) {
            if (!new File(getDataFolder()  + "/localisation/mc-cannonslang_"+ s +".properties").exists()) {
                this.saveResource("localisation/mc-cannonslang_"+ s +".properties", false);
            }
        }
        Config.Locale = getConfig().getString("Locale", "en");
        I18nSupport.init();

        Config.EnableCannonsTracking = getConfig().getBoolean("EnableCannonsTracking", true);


        // Load cannons plugin
        Plugin cannons = getServer().getPluginManager().getPlugin("Cannons");
        if(!(cannons instanceof Cannons)) {
            getLogger().log(Level.SEVERE, I18nSupport.getInternationalisedString("Cannons plugin not found"));
        }
        cannonsPlugin = (Cannons) cannons;
        getLogger().info(I18nSupport.getInternationalisedString("Cannons plugin found"));


        if(Config.EnableCannonsTracking) {
            // Load Movecraft-Combat plugin
            Plugin mcc = getServer().getPluginManager().getPlugin("Movecraft-Combat");
            if (mcc instanceof MovecraftCombat) {
                getLogger().info(I18nSupport.getInternationalisedString("Movecraft-Combat found"));
                getServer().getPluginManager().registerEvents(new ProjectileImpactListener(), this);
            }
            else {
                getLogger().info(I18nSupport.getInternationalisedString("Movecraft-Combat not found"));
            }
        }

        getServer().getPluginManager().registerEvents(new TranslationListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Cannons getCannonsPlugin() {
        return cannonsPlugin;
    }
}
