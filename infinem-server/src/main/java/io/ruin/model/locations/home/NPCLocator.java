package io.ruin.model.locations.home;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/11/2023
 */
public class NPCLocator {

    public static NPC GUIDE, WOODSMAN_TUTOR, MELEE_COMBAT_TUTOR, MINING_TUTOR, TURAEL;

    /**
     * Add a hint arrow towards the npc's location,
     * then when the player gets in range, add a hint arrow on the npc.
     */
    public static void locate(Player player, NPC npc) {
        Position npcPos = npc.getPosition();
        player.sendHintArray(npcPos);
        player.putTemporaryAttribute("LOCATE", npcPos);
        World.startEvent(e -> {
            int loops = 25;
            boolean targettingNPC = false;
            while(loops > 0) {
                if (!player.getTemporaryAttributeOrDefault("LOCATE", null).equals(npcPos))
                    return;
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

    public static void locate(Player player, Position position) {
        player.sendHintArray(position);
        player.putTemporaryAttribute("LOCATE", position);
        World.startEvent(e -> {
            int loops = 50;
            while(loops > 0) {
                if (!player.getTemporaryAttributeOrDefault("LOCATE", null).equals(position))
                    return;
                if (player.getPosition().distance(position) < 3) {
                    break;
                }
                e.delay(1);
                --loops;
            }
            player.clearHintArrow();
        });
    }

    public static void open(Player player) {
        OptionScroll.open(player, "Locate an NPC",
                new Option("Bank", () -> locate(player, new Position(3209, 3219))),
                new Option("Combat Tutors", () -> locate(player, MELEE_COMBAT_TUTOR)),
                new Option("Turael", () -> locate(player, TURAEL)),
                new Option("Woodsman Tutor", () -> locate(player, WOODSMAN_TUTOR)),
                new Option("Mining Tutor", () -> locate(player, MINING_TUTOR)),
                new Option("Thieving Market", () -> locate(player, new Position(3296, 3216)))
        );
    }

    static {
        NPCAction.register(306, "find npc", (player, npc) -> open(player));
        SpawnListener.register(3226, npc -> WOODSMAN_TUTOR = npc);
        SpawnListener.register(306, npc -> GUIDE = npc);
        SpawnListener.register(3216, npc -> MELEE_COMBAT_TUTOR = npc);
        SpawnListener.register(401, npc -> {
            if (npc.getAbsY() < 3196)
                TURAEL = npc;
        });
    }
}
