package net.tylers1066.movecraftcannons.listener;

import at.pavlov.cannons.cannon.Cannon;
import net.countercraft.movecraft.events.CraftPreTranslateEvent;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import java.util.HashSet;


public class TranslationListener implements Listener {
    @EventHandler
    public void translateListener(CraftPreTranslateEvent e) {
        if(e.getCraft().getNotificationPlayer() == null)
            return;

        HashSet<Cannon> cannons = MovecraftCannons.getInstance().getCannons(e.getCraft().getHitBox(), e.getCraft().getW(), e.getCraft().getNotificationPlayer().getUniqueId());

        for(Cannon c : cannons) {
            c.move(new Vector(e.getDx(), e.getDy(), e.getDz()));
        }
    }
}
