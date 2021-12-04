package net.tylers1066.movecraftcannons.type;

import net.countercraft.movecraft.craft.type.CraftType;
import net.countercraft.movecraft.craft.type.TypeData;
import net.countercraft.movecraft.craft.type.property.ObjectPropertyImpl;
import net.countercraft.movecraft.util.Pair;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static net.countercraft.movecraft.craft.type.TypeData.NUMERIC_PREFIX;

public class MaxCannonsProperty {
    public static NamespacedKey MAX_CANNONS = new NamespacedKey("movecraft-cannons", "max_cannons");

    private static @NotNull Pair<Boolean, ? extends Number> parseLimit(@NotNull Object input) {
        if (input instanceof String) {
            String str = (String) input;
            if (str.contains(NUMERIC_PREFIX)) {
                String[] parts = str.split(NUMERIC_PREFIX);
                int val = Integer.parseInt(parts[1]);
                return new Pair<>(true, val);
            }
            else
                return new Pair<>(false, Double.valueOf(str));
        }
        else if (input instanceof Integer) {
            return new Pair<>(false, (double) input);
        }
        else
            return new Pair<>(false, (double) input);
    }

    public static void register() {
        CraftType.registerProperty(new ObjectPropertyImpl("maxCannons", MAX_CANNONS, (data, type, fileKey, namespacedKey) -> {
            var map = data.getData(fileKey).getBackingData();
            if(map.isEmpty())
                throw new TypeData.InvalidValueException("Value for " + fileKey + " must not be an empty map");

            Set<MaxCannonsEntry> maxCannons = new HashSet<>();
            for(var entry : map.entrySet()) {
                if(entry.getKey() == null)
                    throw new TypeData.InvalidValueException("Keys for " + fileKey + " must be a string cannon name.");

                String name = entry.getKey();
                var limit = parseLimit(entry.getValue());
                maxCannons.add(new MaxCannonsEntry(name, limit));
            }
            return maxCannons;
        }, (type -> new HashSet<>())));
    }
}
