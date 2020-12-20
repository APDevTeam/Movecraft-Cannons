package net.tylers1066.movecraftcannons.listener;

import at.pavlov.cannons.cannon.Cannon;
import net.countercraft.movecraft.Rotation;
import net.countercraft.movecraft.events.CraftRotateEvent;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import java.util.HashSet;


public class RotationListener implements Listener {
    @EventHandler
    public void rotateListener(CraftRotateEvent e) {
        if(e.getCraft().getNotificationPlayer() == null)
            return;

        HashSet<Cannon> cannons = MovecraftCannons.getInstance().getCannons(e.getCraft().getHitBox(), e.getCraft().getW(), e.getCraft().getNotificationPlayer().getUniqueId());

        Vector v = e.getOriginPoint().toBukkit(e.getCraft().getW()).toVector();
        for(Cannon c : cannons) {
            MovecraftCannons.getInstance().getLogger().info("Rotated cannon " + c + " by " + v.getX() + "," + v.getY() + "," + v.getZ());
            if(e.getRotation() == Rotation.CLOCKWISE) {
                c.rotateRight(v);
            }
            else if(e.getRotation() == Rotation.ANTICLOCKWISE) {
                c.rotateLeft(v);
            }
        }
    }
}
