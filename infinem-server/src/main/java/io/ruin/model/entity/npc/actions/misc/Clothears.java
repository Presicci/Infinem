package io.ruin.model.entity.npc.actions.misc;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.shared.listeners.SpawnListener;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/14/2024
 */
public class Clothears {

    static {
        SpawnListener.register(5381, npc -> {
            npc.addEvent(e -> {
                while(true) {
                    e.delay(Random.get(30, 100));
                    npc.forceText(Random.get(Arrays.asList("Zzzzz...", "Huh, what? where?")));
                }
            });
        });
    }
}
