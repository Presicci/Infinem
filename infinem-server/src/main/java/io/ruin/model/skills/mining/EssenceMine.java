package io.ruin.model.skills.mining;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public class EssenceMine {

    private static final Projectile PROJECTILE = new Projectile(109, 40, 20, 0, 30, 5, 2, 64);
    private static final Position[] positions = {
            new Position(2910, 4830, 0),
            new Position(2899, 4819, 0),
            new Position(2897, 4845, 0),
            new Position(2923, 4845, 0),
            new Position(2926, 4819, 0)
    };

    private static void leaveMine(Player player, GameObject obj) {
        player.getMovement().startTeleport(-1, event -> {
            String destination = player.getAttribute(AttributeKey.ESSENCE_TELEPORT);
            Position teleportDest;
            switch (destination) {
                case "SEDRIDOR":
                    teleportDest = new Position(3106, 9570, 0);
                    break;
                case "DISTENTOR":
                    teleportDest = new Position(2595, 3088, 0);
                    break;
                case "CROMPERTY":
                    teleportDest = new Position(2680, 3325, 0);
                    break;
                case "BRIMSTAIL":
                    teleportDest = new Position(2409, 9815, 0);
                    break;
                default:
                    teleportDest = new Position(3253, 3399, 0);
                    break;
            }
            PROJECTILE.send(obj.getPosition(), player);
            event.delay(2);
            player.graphics(110);
            player.getMovement().teleport(teleportDest);
            player.removeAttribute(AttributeKey.ESSENCE_TELEPORT);
        });
    }

    public static void enterMine(Player player, NPC npc, String npcName) {
        player.getMovement().startTeleport(-1, event -> {
            npc.faceTemp(player);
            npc.forceText("Senventior Disthine Molenko!");
            if (npc.getId() != 4913) npc.animate(1163);
            npc.graphics(108, 10, 0);
            PROJECTILE.send(npc.getPosition(), player);
            event.delay(2);
            player.graphics(110);
            player.getMovement().teleport(Random.get(positions));
            player.putAttribute(AttributeKey.ESSENCE_TELEPORT, npcName);
            player.getTaskManager().doLookupByUUID(44, 1);  // Visit the Rune Essence Mine
        });
    }

    private static void dialogue(Player player, NPC npc, String npcName) {
        player.dialogue(
                new NPCDialogue(npc, "Hello, would you like me to teleport you to the Essence mine?"),
                new OptionsDialogue(
                        new Option("I need to mine some rune essence.", () -> player.dialogue(
                                new PlayerDialogue("Can you teleport me to the Rune Essence?"),
                                new NPCDialogue(npc, "Okay. Hold onto your hat!"),
                                new ActionDialogue(() -> enterMine(player, npc, npcName))
                        )),
                        new Option("No, thank you.")
                )
        );
    }

    static {
        ObjectAction.register(34825, 1, EssenceMine::leaveMine);
        NPCAction.register(2886, "teleport", (player, npc) -> enterMine(player, npc, "AUBURY"));
        NPCAction.register(5034, "teleport", (player, npc) -> enterMine(player, npc, "SEDRIDOR"));
        NPCAction.register(3248, "teleport", (player, npc) -> enterMine(player, npc, "DISTENTOR"));
        NPCAction.register(5314, 3, (player, npc) -> enterMine(player, npc, "CROMPERTY"));
        NPCAction.register(4913, "teleport", (player, npc) -> enterMine(player, npc, "BRIMSTAIL"));
        NPCAction.register(4913, "talk-to", (player, npc) -> dialogue(player, npc, "BRIMSTAIL"));
    }
}
