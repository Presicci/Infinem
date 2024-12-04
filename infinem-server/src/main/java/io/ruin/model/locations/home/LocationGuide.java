package io.ruin.model.locations.home;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/11/2023
 */
public class LocationGuide {

    public static NPC GUIDE, WOODSMAN_TUTOR, MELEE_COMBAT_TUTOR, TURAEL, DONATION_MANAGER, VOTE_MANAGER;

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
        OptionScroll.open(player, "Location Guide",
                new Option("Bank", () -> locate(player, new Position(1657, 3132))),
                new Option("Turael - Slayer", () -> locate(player, TURAEL)),
                new Option("Grand Exchange", () -> locate(player, new Position(1646, 3114))),
                new Option("Thieving Market", () -> locate(player, new Position(1680, 3107))),
                new Option("Vote Manager", () -> locate(player, VOTE_MANAGER)),
                new Option("Donation Manager", () -> locate(player, DONATION_MANAGER)),
                new Option("Combat Tutors", () -> locate(player, MELEE_COMBAT_TUTOR)),
                new Option("Woodsman Tutor", () -> locate(player, WOODSMAN_TUTOR)),
                new Option("Fishing Tutor", () -> player.dialogue(new NPCDialogue(306,
                        "You can find the Fishing tutor and lots of shrimp south of Civitas illa Fortis, by the mine."))),
                new Option("Mine", () -> player.dialogue(new NPCDialogue(306,
                        "You can find a mine to the south of Civitas illa Fortis.")))

        );
    }

    private static final Bounds HOME_BOUNDS = new Bounds(1438, 2867, 1818, 3200, 0);

    static {
        NPCAction.register(306, "location guide", (player, npc) -> open(player));
        SpawnListener.register(3226, npc -> {
            if (!HOME_BOUNDS.inBounds(npc.getPosition())) return;
            WOODSMAN_TUTOR = npc;
        });
        SpawnListener.register(306, npc -> {
            if (!HOME_BOUNDS.inBounds(npc.getPosition())) return;
            GUIDE = npc;
        });
        SpawnListener.register(3216, npc -> {
            if (!HOME_BOUNDS.inBounds(npc.getPosition())) return;
            MELEE_COMBAT_TUTOR = npc;
        });
        SpawnListener.register(15000, npc -> {
            if (!HOME_BOUNDS.inBounds(npc.getPosition())) return;
            VOTE_MANAGER = npc;
        });
        SpawnListener.register(15001, npc -> {
            if (!HOME_BOUNDS.inBounds(npc.getPosition())) return;
            DONATION_MANAGER = npc;
        });
        SpawnListener.register(13618, npc -> {
            if (!HOME_BOUNDS.inBounds(npc.getPosition())) return;
            TURAEL = npc;
        });
    }
}
