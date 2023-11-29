package io.ruin.model.entity.shared;

import io.ruin.utility.TickDelay;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/29/2023
 */
public class BreakableRoot extends TickDelay {

    @Getter private final String successMessage, failureMessage, progressMessage;
    @Getter private int clicks;

    public BreakableRoot() {
        this.successMessage = "";
        this.failureMessage = "";
        this.progressMessage = "";
    }

    public BreakableRoot(int ticks, String successMessage, String failureMessage, String progressMessage) {
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.progressMessage = progressMessage;
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
