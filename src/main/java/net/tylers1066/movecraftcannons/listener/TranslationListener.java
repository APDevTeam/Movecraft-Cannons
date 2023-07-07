package net.tylers1066.movecraftcannons.listener;

import at.pavlov.cannons.cannon.Cannon;
import net.countercraft.movecraft.MovecraftLocation;
import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.PilotedCraft;
import net.countercraft.movecraft.events.CraftTranslateEvent;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


public class TranslationListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void translateListener(CraftTranslateEvent e) {
        if (e.isCancelled())
            return;

        Craft craft = e.getCraft();
        if (!(craft instanceof PilotedCraft))
            return;

        Set<Cannon> cannons = MovecraftCannons.getInstance().getCannons(
                craft.getHitBox(), craft.getWorld(),
                ((PilotedCraft) craft).getPilot().getUniqueId()
        );

        Vector v = delta(e);
        if (v == null)
            return;

        for (Cannon c : cannons) {
            c.move(v);
        }
    }

    @Nullable
    private Vector delta(@NotNull CraftTranslateEvent e) {
        if (e.getOldHitBox().isEmpty() || e.getNewHitBox().isEmpty())
            return null;

        MovecraftLocation oldMid = e.getOldHitBox().getMidPoint();
        MovecraftLocation newMid = e.getNewHitBox().getMidPoint();

        int dx = newMid.getX() - oldMid.getX();
        int dy = newMid.getY() - oldMid.getY();
        int dz = newMid.getZ() - oldMid.getZ();

        return new Vector(dx, dy, dz);
    }
}
