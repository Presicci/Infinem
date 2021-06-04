package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.cache.ItemDef;
import io.ruin.model.activities.warriorsguild.Cyclops;
import io.ruin.model.activities.warriorsguild.WarriorsGuild;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.skillcapes.AttackSkillCape;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/4/2021
 */
public class FarmingGuild {

    private static void enterGuild(Player player, GameObject object) {
        player.startEvent(event -> {
            /* if we're entering the guild, check for level requirements */
            if(player.getStats().get(StatType.Farming).fixedLevel < 45) {
                player.dialogue(new MessageDialogue("Your non-boosted <col=000080>Farming</col> level must be at least 45 to enter the Farming Guild."));
                return;
            }
            event.delay(1);
            player.getMovement().teleport(player.getAbsX(), player.getAbsY() + (player.getAbsY() >= object.y ? -2 : 2));
            //player.lock();
            /*GameObject opened = GameObject.spawn(object.id, object.x, object.y, object.z, object.type, 1);
            object.skipClipping(true).remove();
            player.step(0, player.getAbsY() == object.y ? -2 : 2, StepType.FORCE_WALK);
            event.delay(2);
            object.restore().skipClipping(false);
            opened.remove();
            object.spawn();
            player.unlock();*/
            //TODO Find proper door ids
        });
    }

    static {
        ObjectAction.register(34463, "open", FarmingGuild::enterGuild);
        ObjectAction.register(34464, "open", FarmingGuild::enterGuild);
    }
}
