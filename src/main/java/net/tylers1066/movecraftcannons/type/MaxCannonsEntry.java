package net.tylers1066.movecraftcannons.type;

import net.countercraft.movecraft.util.Pair;
import org.jetbrains.annotations.NotNull;

public class MaxCannonsEntry {
    private final String name;
    private final double max;
    private final boolean numericMax;

    public MaxCannonsEntry(String name, @NotNull Pair<Boolean, ? extends Number> max) {
        this.name = name;
        this.max = max.getRight().doubleValue();
        this.numericMax = max.getLeft();
    }

    public String getName() {
        return name;
    }

    public boolean check(int count, int size) {
        if(numericMax)
            return !(count > max);
        else {
            double percent = 100D * count / size;
            return !(percent > max);
        }
    }

    public enum DetectionResult {
        TOO_MUCH,
        SUCCESS
    }

    public Pair<DetectionResult, String> detect(int count, int size) {
        if(numericMax) {
            if(count > max)
                return new Pair<>(DetectionResult.TOO_MUCH, String.format("%d > %d", count, (int) max));
        }
        else {
            double blockPercent = 100D * count / size;
            if(blockPercent > max)
                return new Pair<>(DetectionResult.TOO_MUCH, String.format("%.2f%% > %.2f%%", blockPercent, max));
        }

        return new Pair<>(DetectionResult.SUCCESS, "");
    }
}
