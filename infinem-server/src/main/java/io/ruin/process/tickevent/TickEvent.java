package io.ruin.process.tickevent;

import com.google.gson.annotations.Expose;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/21/2024
 */
public class TickEvent {

    @Expose @Getter private final TickEventType type;
    @Expose private int durationTicks;
    private final TickEventRunnable[] onTick;
    private final Runnable onComplete;

    public TickEvent(TickEventType type, int durationTicks, Runnable onComplete, TickEventRunnable... onTick) {
        this.type = type;
        this.durationTicks = durationTicks;
        this.onComplete = onComplete;
        this.onTick = onTick;
    }

    public TickEvent(TickEventType type, int durationTicks, Runnable onComplete) {
        this(type, durationTicks, onComplete, new TickEventRunnable[0]);
    }

    public TickEvent(TickEventType type, int durationTicks) {
        this(type, durationTicks, null);
    }

    public int tick() {
        if (--durationTicks <= 0 && onComplete != null) {
            onComplete.run();
        }
        if (onTick != null) {
            for (TickEventRunnable runnable : onTick) {
                if (runnable != null && runnable.getTick() == durationTicks) runnable.getRunnable().run();
            }
        }
        return durationTicks;
    }

    public int getDuration() {
        return durationTicks;
    }
}
