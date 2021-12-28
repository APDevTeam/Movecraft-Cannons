package net.tylers1066.movecraftcannons.listener;

import at.pavlov.cannons.cannon.Cannon;
import net.countercraft.movecraft.MovecraftLocation;
import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.PlayerCraft;
import net.countercraft.movecraft.events.CraftTranslateEvent;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Set;


public class TranslationListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void translateListener(CraftTranslateEvent e) {
        if (e.isCancelled())
            return;

        Craft craft = e.getCraft();
        if (!(craft instanceof PlayerCraft))
            return;

        Set<Cannon> cannons = MovecraftCannons.getInstance().getCannons(
                craft.getHitBox(), craft.getWorld(),
                ((PlayerCraft) craft).getPilot().getUniqueId()
        );

        Vector v = delta(e);
        for (Cannon c : cannons) {
            c.move(v);
        }
    }

    @NotNull
    private Vector delta(@NotNull CraftTranslateEvent e) {
        MovecraftLocation oldMid = e.getOldHitBox().getMidPoint();
        MovecraftLocation newMid = e.getNewHitBox().getMidPoint();

        int dx = newMid.getX() - oldMid.getX();
        int dy = newMid.getY() - oldMid.getY();
        int dz = newMid.getZ() - oldMid.getZ();

        return new Vector(dx, dy, dz);
    }
}
