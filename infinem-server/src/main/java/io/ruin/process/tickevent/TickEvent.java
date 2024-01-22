package io.ruin.process.tickevent;

import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/21/2024
 */
public class TickEvent {

    @Getter private final TickEventType type;
    private int durationTicks;
    private Runnable onRun;
    private final Runnable onComplete;

    public TickEvent(TickEventType type, int durationTicks, Runnable onRun, Runnable onComplete) {
        this.type = type;
        this.durationTicks = durationTicks;
        this.onRun = onRun;
        this.onComplete = onComplete;
    }

    public TickEvent(TickEventType type, int durationTicks) {
        this(type, durationTicks, null, null);
    }

    public int tick() {
        if (onRun != null) {    // Only perform onRun on first tick
            onRun.run();
            onRun = null;
        }
        if (--durationTicks >= 0 && onComplete != null) {
            onComplete.run();
        }
        System.out.println(durationTicks);
        return durationTicks;
    }
}
