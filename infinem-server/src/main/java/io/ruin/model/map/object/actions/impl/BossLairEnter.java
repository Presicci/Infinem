package io.ruin.model.map.object.actions.impl;

import io.ruin.model.activities.combat.pvminstance.InstanceDialogue;
import io.ruin.model.activities.combat.pvminstance.InstanceType;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/30/2023
 */
public enum BossLairEnter {
    KALPHITE_LAIR(23609, 1, InstanceType.KALPHITE_QUEEN);

    BossLairEnter(int objectId, int optionIndex, InstanceType instanceType) {
        ObjectAction.register(objectId, optionIndex, (player, obj) -> InstanceDialogue.open(player, instanceType));
    }
}
