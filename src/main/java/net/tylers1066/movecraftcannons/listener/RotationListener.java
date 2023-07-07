package net.tylers1066.movecraftcannons.listener;

import at.pavlov.cannons.cannon.Cannon;
import net.countercraft.movecraft.MovecraftRotation;
import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.PilotedCraft;
import net.countercraft.movecraft.craft.PlayerCraft;
import net.countercraft.movecraft.craft.SubCraft;
import net.countercraft.movecraft.events.CraftRotateEvent;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.Set;
import java.util.UUID;


public class RotationListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void rotateListener(CraftRotateEvent e) {
        if (e.isCancelled())
            return;

        Craft craft = e.getCraft();
        UUID playerUUID = null;
        if (craft instanceof PilotedCraft) {
            playerUUID = ((PilotedCraft) craft).getPilot().getUniqueId();
        } else if (craft instanceof SubCraft) {
            if (((SubCraft) craft).getParent() instanceof PlayerCraft) {
                playerUUID = ((PlayerCraft) ((SubCraft) craft).getParent()).getPilot().getUniqueId();
            }
        }

        if (playerUUID == null) return;

        Set<Cannon> cannons = MovecraftCannons.getInstance().getCannons(
                craft.getHitBox(), e.getCraft().getWorld(), playerUUID
        );

        Vector v = e.getOriginPoint().toBukkit(craft.getWorld()).toVector();
        for (Cannon c : cannons) {
            if (e.getRotation() == MovecraftRotation.CLOCKWISE)
                c.rotateRight(v);
            else if (e.getRotation() == MovecraftRotation.ANTICLOCKWISE)
                c.rotateLeft(v);
        }
    }
}
