package io.ruin.model.entity.shared;

import io.ruin.model.entity.Entity;
import io.ruin.utility.TickDelay;
import lombok.Getter;

import java.util.function.BiConsumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/29/2023
 */
public class BreakableRoot extends TickDelay {

    @Getter private final String successMessage, failureMessage, progressMessage;
    @Getter private final BiConsumer<Entity, Boolean> breakoutAction;
    @Getter private int clicks;

    public BreakableRoot() {
        this.successMessage = "";
        this.failureMessage = "";
        this.progressMessage = "";
        this.breakoutAction = null;
    }

    public BreakableRoot(int ticks, String successMessage, String failureMessage, String progressMessage, BiConsumer<Entity, Boolean> breakoutAction) {
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.progressMessage = progressMessage;
        this.breakoutAction = breakoutAction;
        delay(ticks);
    }

    public void reset() {
        clicks = 0;
        super.reset();
    }

    public void click() {
        clicks++;
    }
}
