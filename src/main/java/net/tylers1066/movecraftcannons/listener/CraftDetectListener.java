package net.tylers1066.movecraftcannons.listener;

import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.PilotedCraft;
import net.countercraft.movecraft.craft.PlayerCraft;
import net.countercraft.movecraft.events.CraftDetectEvent;
import net.tylers1066.movecraftcannons.MovecraftCannons;
import net.tylers1066.movecraftcannons.type.MaxCannonsEntry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static net.tylers1066.movecraftcannons.type.MaxCannonsEntry.MAX_CANNONS;

public class CraftDetectListener implements Listener {
    @EventHandler
    public void onCraftDetect(CraftDetectEvent e) {
        Craft craft = e.getCraft();
        if(!(craft instanceof PlayerCraft))
            return;

        var objectProperty = craft.getType().getObjectProperty(MAX_CANNONS);
        if(!(objectProperty instanceof Set<?>))
            throw new IllegalStateException("MAX_CANNONS must be a set.");

        Set<?> maxCannons = (Set<?>) objectProperty;
        if(maxCannons.size() == 0)
            return; // Return if empty set to improve performance

        // Sum up counts of each cannon design
        UUID pilotUUID = ((PlayerCraft) craft).getPlayer().getUniqueId();
        var cannons = MovecraftCannons.getInstance().getCannons(e.getCraft().getHitBox(), e.getCraft().getWorld(), pilotUUID);
        Map<String, Integer> cannonCount = new HashMap<>();
        for(var cannon : cannons) {
            String design = cannon.getCannonDesign().getDesignName().toLowerCase();
            if(!cannonCount.containsKey(design))
                cannonCount.put(design, 0);
            else
                cannonCount.put(design, cannonCount.get(design) + 1);
        }

        // Check designs against maxCannons
        int size = e.getCraft().getOrigBlockCount();
        for(var entry : maxCannons) {
            if(!(entry instanceof MaxCannonsEntry))
                throw new IllegalStateException("MAX_CANNONS must be a set of MaxCannonsEntry.");

            MaxCannonsEntry max = (MaxCannonsEntry) entry;
            var count = cannonCount.get(max.getName().toLowerCase());
            if(count == null)
                continue;

            var result = max.detect(count, size);
            switch (result.getLeft()) {
                case SUCCESS:
                    continue;
                case TOO_MUCH:
                    e.setCancelled(true);
                    e.setFailMessage("Detection Failed! You have too many cannons of the following type on this craft:" + max.getName() + ": " + result.getRight());
                    return;
            }
        }
    }
}
