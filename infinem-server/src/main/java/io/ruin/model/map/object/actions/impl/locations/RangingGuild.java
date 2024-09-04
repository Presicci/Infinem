package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.World;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/21/2024
 */
public class RangingGuild {

    private static GameObject changeDoorState(GameObject obj) {
        if (obj.y == 3439) {    // Close
            obj.remove();
            return GameObject.spawn(11665, 2658, 3438, 0, obj.type, 0);
        } else {    // Open
            obj.remove();
            return GameObject.spawn(11665, 2658, 3439, 0, obj.type, 1);
        }
    }

    static {
        // Front entrance
        ClipUtils.addClipping(2658, 3438, 0, 0, 0);
        ObjectAction.register(11665, "open", (player, obj) -> {
            if (obj.x != 2658 || obj.y != 3438) return;
            World.startEvent(e -> {
                GameObject object = obj;
                // Entering
                if (player.getAbsX() < object.x || player.getAbsY() > object.y) {
                    if (player.getStats().get(StatType.Ranged).fixedLevel < 40) {
                        player.dialogue(new NPCDialogue(6057, "Hey you can't come in here. You must be a level 40 ranger to enter."));
                        return;
                    }
                    Position doorPos = new Position(object.x, object.y, object.z);
                    object = changeDoorState(object);
                    e.delay(1);
                    player.stepAbs(doorPos.getX(), doorPos.getY(), StepType.FORCE_WALK);
                    e.delay(1);
                    player.stepAbs(2659, 3437, StepType.FORCE_WALK);
                    e.delay(1);
                    player.getTaskManager().doLookupByUUID(581);    // Enter the Ranging Guild
                    changeDoorState(object);
                } else {    // Exiting
                    Position doorPos = new Position(object.x, object.y, object.z);
                    object = changeDoorState(object);
                    e.delay(1);
                    player.stepAbs(doorPos.getX(), doorPos.getY(), StepType.FORCE_WALK);
                    e.delay(1);
                    player.stepAbs(2657, 3439, StepType.FORCE_WALK);
                    e.delay(1);
                    changeDoorState(object);
                }
            });
        });
    }
}
