package net.tylers1066.movecraftcannons.listener;

import at.pavlov.cannons.cannon.Cannon;
import net.countercraft.movecraft.MovecraftLocation;
import net.countercraft.movecraft.events.CraftPreTranslateEvent;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TranslationListener implements Listener {
    @EventHandler
    public void translateListener(CraftPreTranslateEvent e) {
        List<Location> shipLocations = new ArrayList<>();
        for(MovecraftLocation loc : e.getCraft().getHitBox()) {
            shipLocations.add(loc.toBukkit(e.getCraft().getW()));
        }
        HashSet<Cannon> cannons = MovecraftCannons.getInstance().getCannonsPlugin().getCannonsAPI().getCannons(shipLocations, e.getCraft().getNotificationPlayer().getUniqueId(), true);

        for(Cannon c : cannons) {
            c.move(new Vector(e.getDx(), e.getDy(), e.getDz()));
        }
    }
}
