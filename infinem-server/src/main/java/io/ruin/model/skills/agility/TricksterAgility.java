package io.ruin.model.skills.agility;

import io.ruin.api.utils.Random;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.network.incoming.handlers.ObjectActionHandler;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/22/2024
 */
public class TricksterAgility {

    public static void attemptNext(Player player, GameObject object) {
        if (!player.getRelicManager().hasRelicEnalbed(Relic.TRICKSTER)) return;
        if (Random.rollDie(4)) return;
        ObjectActionHandler.handleAction(player, 1, object.id, object.getPosition().getX(), object.getPosition().getY(), 0);
    }
}
