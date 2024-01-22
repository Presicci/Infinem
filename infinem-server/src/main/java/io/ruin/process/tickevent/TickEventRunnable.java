package io.ruin.process.tickevent;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/22/2024
 */
@Getter
@AllArgsConstructor
public class TickEventRunnable {
    private final int tick;
    private final Runnable runnable;
}
