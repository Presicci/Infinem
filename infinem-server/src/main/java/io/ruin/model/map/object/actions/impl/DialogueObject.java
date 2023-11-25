package io.ruin.model.map.object.actions.impl;

import io.ruin.model.inter.dialogue.Dialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/1/2023
 */
public enum DialogueObject {
    SITHIK_INTS(6887, new Position(2591, 3103, 1),
            new NPCDialogue(883, "Leave me be, please.")),
    KALEEFS_BODY(20288, new Position(3239, 9243),
            new MessageDialogue("He's dead.")),
    LUMBRIDGE_COW_PEN_CRATES(358, new Position(3244, 3279),
            new MessageDialogue("You thoroughly search the crates, yet you fail to find any potential reason for why the fence was built around them. Cheap labor potentially.")),
    CRUMBLING_TOWER(1796, new Position(1936, 9444),
            new MessageDialogue("Someone has crudely carved a message into the wall:<br><br>'The Soul Stone was here. Where is it now?")),
    BRINE_RAT_CAVE_CHEST(14197, new Position(2740, 10164),
            new MessageDialogue("An etching on the chest reads 'Titan.' There is nothing here to indicate how the ship got here or what they were searching for."));

    private final int objectId, option;
    private final Position position;
    private final Dialogue[] dialogues;

    DialogueObject(int objectId, Position position, Dialogue... dialogues) {
        this(objectId, 1, position, dialogues);
    }

    DialogueObject(int objectId, int option, Position position, Dialogue... dialogues) {
        this.objectId = objectId;
        this.dialogues = dialogues;
        this.position = position;
        this.option = option;
    }

    static {
        for (DialogueObject obj : values()) {
            ObjectAction.register(obj.objectId, obj.position, obj.option, ((p, o) -> p.dialogue(obj.dialogues)));
        }
    }
}
