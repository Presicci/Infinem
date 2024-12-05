package io.ruin.model.map.object.actions.impl.varlamore;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/5/2024
 */
public class BirdBath {

    private static void drink(Player player, GameObject object) {
        player.lock();
        player.startEvent(e -> {
            player.animate(832);
            e.delay(1);
            player.getTaskManager().doLookupByUUID(1120);   // Drink from a Bird Bath
            switch (Random.get(4)) {
                case 4:
                    player.poison(3);
                    player.dialogue(new PlayerDialogue("Eugh! I don't feel too good..."));
                    break;
                case 3:
                    player.getStats().get(StatType.Defence).boost(2, 0);
                    player.dialogue(new PlayerDialogue("Well that was disgusting! It had dirt, sticks and leaves in it! I do feel oddly invigorated though..."));
                    break;
                case 2:
                    player.hit(new Hit().fixedDamage(1));
                    player.dialogue(new PlayerDialogue("Well I'm not really sure what I expected. I hope I didn't contract any horrible diseases..."));
                    break;
                default:
                    player.dialogue(new PlayerDialogue("I don't know why I did that..."));
                    break;
            }
            player.unlock();
        });

    }

    static {
        ObjectAction.register(52394, "drink-from", BirdBath::drink);
    }
}
