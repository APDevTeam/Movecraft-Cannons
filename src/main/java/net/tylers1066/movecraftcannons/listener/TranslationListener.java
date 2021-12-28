package net.tylers1066.movecraftcannons.listener;

import at.pavlov.cannons.cannon.Cannon;
import net.countercraft.movecraft.MovecraftLocation;
import net.countercraft.movecraft.events.CraftTranslateEvent;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;


public class TranslationListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void translateListener(CraftTranslateEvent e) {
        if (e.isCancelled())
            return;

        if (e.getCraft().getNotificationPlayer() == null)
            return;

        HashSet<Cannon> cannons = MovecraftCannons.getInstance().getCannons(e.getCraft().getHitBox(), e.getCraft().getW(), e.getCraft().getNotificationPlayer().getUniqueId());

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
