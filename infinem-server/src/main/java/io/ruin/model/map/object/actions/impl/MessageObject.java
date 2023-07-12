package io.ruin.model.map.object.actions.impl;

import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/1/2023
 */
public enum MessageObject {
    CRUMBLING_TOWER(1796, new Position(1936, 9444),
            "Someone has crudely carved a message into the wall:<br><br>'The Soul Stone was here. Where is it now?"),
    BRINE_RAT_CAVE_CHEST(14197, new Position(2740, 10164),
            "An etching on the chest reads 'Titan.' There is nothing here to indicate how the ship got here or what they were searching for.");

    private final int objectId, option;
    private final Position position;
    private final String messge;

    MessageObject(int objectId, Position position, String message) {
        this(objectId, 1, position, message);
    }

    MessageObject(int objectId, int option, Position position, String message) {
        this.objectId = objectId;
        this.messge = message;
        this.position = position;
        this.option = option;
    }

    static {
        for (MessageObject obj : values()) {
            ObjectAction.register(obj.objectId, obj.position, obj.option, ((p, o) -> p.dialogue(new MessageDialogue(obj.messge))));
        }
    }
}
