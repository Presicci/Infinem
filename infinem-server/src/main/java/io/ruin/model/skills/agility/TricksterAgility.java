package io.ruin.model.skills.agility;

import io.ruin.api.utils.Random;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.fragments.FragmentModifier;
import io.ruin.model.content.tasksystem.relics.impl.fragments.FragmentType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.RandomEvent;
import io.ruin.network.incoming.handlers.ObjectActionHandler;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/22/2024
 */
public class TricksterAgility {

    public static final String KEY = "TRICKSTER_AGIL";

    public static void attemptNext(Player player, GameObject object) {
        RandomEvent.attemptTrigger(player);
        int obstacleMax = (player.getRelicManager().hasRelicEnalbed(Relic.TRICKSTER) ? 2 : 0) + (int) player.getRelicFragmentManager().getModifierValue(FragmentType.Agility, FragmentModifier.AUTOMATIC_LAPS);
        if (obstacleMax <= 0) return;
        if (player.incrementTemporaryNumericAttribute(KEY, 1) > obstacleMax) return;
        ObjectActionHandler.handleAction(player, 1, object.id, object.getPosition().getX(), object.getPosition().getY(), 0);
    }
}
