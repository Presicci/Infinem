package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/14/2023
 */
public class BasaltCombine {

    private enum BasaltType {
        ICY_BASALT(22599, new Item(22603), new Item(22595, 3), new Item(22593, 1)),
        STONY_BASALT(22601, new Item(22603), new Item(22597, 3), new Item(22593, 1));

        private static final BasaltType[] values = values();
        private final int basaltId;
        private final Item[] requirements;

        BasaltType(final int basaltId, final Item... requirements) {
            this.basaltId = basaltId;
            this.requirements = requirements;
        }
    }

    private static void empower(Player player, Item from, Item to) {

    }
}
