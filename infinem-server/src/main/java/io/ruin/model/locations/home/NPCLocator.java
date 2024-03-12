package io.ruin.model.locations.home;

import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/11/2023
 */
public class NPCLocator {

    public static NPC GUIDE, WOODSMAN_TUTOR;

    /**
     * Add a hint arrow towards the npc's location,
     * then when the player gets in range, add a hint arrow on the npc.
     */
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
        NPCAction.register(306, "find npc", (player, npc) -> open(player));
        SpawnListener.register(3226, npc -> WOODSMAN_TUTOR = npc);
        SpawnListener.register(306, npc -> GUIDE = npc);
    }
}
