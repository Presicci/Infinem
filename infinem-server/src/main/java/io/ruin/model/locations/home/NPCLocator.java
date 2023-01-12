package io.ruin.model.locations.home;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/11/2023
 */
public class NPCLocator {

    public static NPC WOODSMAN_TUTOR;

    public static void locate(Player player, NPC npc) {
        player.sendHintArray(npc.getPosition());
        World.startEvent(e -> {
            int loops = 25;
            boolean targettingNPC = false;
            while(loops > 0) {
                if (!targettingNPC && player.getPosition().distance(npc.getPosition()) < 15) {
                    player.sendHintArrow(npc);
                    targettingNPC = true;
                }
                e.delay(1);
                --loops;
            }
            player.clearHintArrow();
        });
    }

    public static void open(Player player) {
        OptionScroll.open(player, "Locate an NPC",
                new Option("Woodsman Tutor", () -> locate(player, WOODSMAN_TUTOR))
        );
    }

    static {
        NPCAction.register(306, "talk-to", (player, npc) -> open(player));
    }
}
