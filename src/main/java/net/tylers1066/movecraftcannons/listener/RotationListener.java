package net.tylers1066.movecraftcannons.listener;

import at.pavlov.cannons.cannon.Cannon;
import net.countercraft.movecraft.MovecraftRotation;
import net.countercraft.movecraft.events.CraftRotateEvent;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.Set;

public class RotationListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void rotateListener(CraftRotateEvent e) {
        if (e.isCancelled())
            return;

        Set<Cannon> cannons = MovecraftCannons.getInstance().getCannons(e.getCraft());
        if (cannons.isEmpty())
            return;

        Vector v = e.getOriginPoint().toBukkit(e.getCraft().getWorld()).toVector();
        for (Cannon c : cannons) {
            if (e.getRotation() == MovecraftRotation.CLOCKWISE)
                c.rotateRight(v);
            else if (e.getRotation() == MovecraftRotation.ANTICLOCKWISE)
                c.rotateLeft(v);
        }
    }
}
