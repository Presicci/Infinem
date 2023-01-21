package io.ruin.model.entity.npc.actions.misc;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.shared.listeners.SpawnListener;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/21/2023
 */
public class TheGuns {

    private static int count = 1;

    static {
        SpawnListener.register(3599, npc -> {
            npc.addEvent(e -> {
                while(true) {
                    e.delay(3);
                    if (count > 5000 && Random.rollDie(250, 1)) {
                        npc.forceText((count + 1) + "..." + "15?");
                        e.delay(2);
                        npc.forceText("Huh?");
                        e.delay(2);
                        npc.forceText("Bah");
                        e.delay(2);
                        npc.forceText("Start again!");
                        count = 1;
                    } else {
                        npc.forceText("" + count++);
                    }
                }
            });
        });
    }
}
