package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.Region;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/29/2023
 */
public enum BossLairPeek {
    MOLE_LAIR(12202, "look-inside", "look inside the hole", 6992, 6993),
    KALPHITE_LAIR(23609, "look-inside", "peek down", 12690, 12691, 12946, 12947);

    BossLairPeek(int objectId, String option, String actionText, int... regions) {
        ObjectAction.register(objectId, option, (player, obj) -> {
            int count = 0;
            for (int region : regions) {
                count += Region.get(region).players.size();
            }
            if (count == 0)
                player.sendMessage("You " + actionText + " and see nobody inside.");
            else if (count == 1)
                player.sendMessage("You " + actionText + " and see one adventurer inside.");
            else
                player.sendMessage("You " + actionText + " and see " + count + " adventurers inside.");
        });
    }
}
