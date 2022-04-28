package io.ruin.model.locations;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.map.object.actions.impl.PassableDoor;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/26/2022
 */
public class PortSarimPrison {

    private static void pickLock(Player player, GameObject obj) {
        if (player.getPosition().getY() >= obj.getPosition().getY()) {
            player.dialogue(
                    new MessageDialogue("No sense in breaking into prison.")
            );
            return;
        }
        player.startEvent(e -> {
           while (true) {
               player.animate(537);
               player.sendFilteredMessage("You attempt to pick the lock on the door.");
               e.delay(5);
               if (Random.rollPercent(50)) {
                   player.sendFilteredMessage("You pick the lock on the door.");
                   player.getStats().addXp(StatType.Thieving, 4, true);
                   PassableDoor.passDoor(player, obj, Direction.NORTH, -4, new Position(0, -2), 9566);
                   break;
               }
           }
        });
    }

    static {
        ObjectAction.register(9565, 3014, 3182, 0, "pick-lock", PortSarimPrison::pickLock);
        NPCAction.register(1551, "talk-to", ((player, npc) -> player.dialogue(
                new MessageDialogue("He is fast asleep. Better leave him to it.")
        )));
        // Trapdoor on the third story
        ObjectAction.register(9560, 3013, 3179, 2, "climb-down", ((player, obj) -> Ladder.climb(player, new Position(3014, 3179, 1), false, true, false)));
    }
}
