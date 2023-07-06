package io.ruin.model.skills.agility.pyramid;

import io.ruin.model.World;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2023
 */
public class AgilityPyramidArea {

    static {
        World.startEvent(e -> {
            while (true) {
                e.delay(16);
                MovingBlock.moveBlocks();
            }
        });
    }
}
