package net.tylers1066.movecraftcannons;

import at.pavlov.cannons.Cannons;
import at.pavlov.cannons.cannon.Cannon;
import net.countercraft.movecraft.MovecraftLocation;
import net.countercraft.movecraft.combat.MovecraftCombat;
import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.PilotedCraft;
import net.countercraft.movecraft.craft.SubCraft;
import net.tylers1066.movecraftcannons.config.Config;
import net.tylers1066.movecraftcannons.listener.CraftDetectListener;
import net.tylers1066.movecraftcannons.listener.ProjectileImpactListener;
import net.tylers1066.movecraftcannons.listener.RotationListener;
import net.tylers1066.movecraftcannons.listener.TranslationListener;
import net.tylers1066.movecraftcannons.localisation.I18nSupport;
import net.tylers1066.movecraftcannons.type.MaxCannonsProperty;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public final class MovecraftCannons extends JavaPlugin {
    private static MovecraftCannons instance;
    private static Cannons cannonsPlugin = null;

    public static MovecraftCannons getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        MaxCannonsProperty.register();
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        Config.Debug = getConfig().getBoolean("Debug", false);

        // TODO other languages
        String[] languages = { "en" };
        for (String s : languages) {
            if (!new File(getDataFolder() + "/localisation/mc-cannonslang_" + s + ".properties").exists()) {
                this.saveResource("localisation/mc-cannonslang_" + s + ".properties", false);
            }
        }
        Config.Locale = getConfig().getString("Locale", "en");
        I18nSupport.init();

        Config.EnableCannonsTracking = getConfig().getBoolean("EnableCannonsTracking", true);

        // Load cannons plugin
        Plugin cannons = getServer().getPluginManager().getPlugin("Cannons");
        if (!(cannons instanceof Cannons)) {
            getLogger().log(Level.SEVERE, I18nSupport.getInternationalisedString("Cannons plugin not found"));
            this.setEnabled(false);
            return;
        }
        cannonsPlugin = (Cannons) cannons;
        getLogger().info(I18nSupport.getInternationalisedString("Cannons plugin found"));

        if (Config.EnableCannonsTracking) {
            // Load Movecraft-Combat plugin
            Plugin mcc = getServer().getPluginManager().getPlugin("Movecraft-Combat");
            if (mcc instanceof MovecraftCombat) {
                getLogger().info(I18nSupport.getInternationalisedString("Movecraft-Combat found"));
                getServer().getPluginManager().registerEvents(new ProjectileImpactListener(), this);
            } else {
                getLogger().info(I18nSupport.getInternationalisedString("Movecraft-Combat not found"));
            }
        }

        getServer().getPluginManager().registerEvents(new CraftDetectListener(), this);
        getServer().getPluginManager().registerEvents(new TranslationListener(), this);
        getServer().getPluginManager().registerEvents(new RotationListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private @Nullable UUID getUUID(@NotNull Craft craft) {
        if (craft instanceof PilotedCraft) {
            // If this is a piloted craft, return the pilot's UUID
            return ((PilotedCraft) craft).getPilot().getUniqueId();
        }

        if (craft instanceof SubCraft) {
            // If this is a subcraft, look for a parent
            Craft parent = ((SubCraft) craft).getParent();
            if (parent != null) {
                // If the parent is not null, recursively check it for a UUID
                return getUUID(parent);
            }
        }

        // Return null if all else fails
        return null;
    }

    public Set<Cannon> getCannons(@NotNull Craft craft) {
        List<Location> shipLocations = new ArrayList<>();
        for (MovecraftLocation loc : craft.getHitBox()) {
            shipLocations.add(loc.toBukkit(craft.getWorld()));
        }
        return cannonsPlugin.getCannonsAPI().getCannons(shipLocations, getUUID(craft), true);
    }
}
