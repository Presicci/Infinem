package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.farming.farmer.Farmer;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;
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

        NPCAction.register(8536, 1, (player, npc) -> {
            Patch patch = player.getFarming().getPatch(PatchData.FARMING_GUILD_REDWOOD.getObjectId());
            if (patch.isFullyGrown()) {
                player.dialogue(new NPCDialogue(8536, "Would you like me to clear your redwood tree?"),
                        new OptionsDialogue("Want me to clear your tree?",
                                new Option("Yes", p -> {
                                    patch.reset(false);
                                    player.dialogue(new NPCDialogue(8536, "The patch has been cleared up!"));
                                }),
                                new Option("No")
                        ));
            } else {
                player.dialogue(new NPCDialogue(8536, "Would you like me to watch over your redwood tree for you?"),
                        new OptionsDialogue("Want me to watch over your tree?",
                                new Option("Yes", p -> Farmer.attemptPayment(p, npc, PatchData.FARMING_GUILD_REDWOOD)),
                                new Option("No")
                        ));
            }
        });
    }
}
