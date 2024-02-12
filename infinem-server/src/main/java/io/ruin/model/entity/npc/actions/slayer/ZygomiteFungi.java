package io.ruin.model.entity.npc.actions.slayer;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/11/2024
 */
public class ZygomiteFungi {

    private static Map<Integer, Integer> FUNGI = new HashMap<Integer, Integer>() {{
        put(533, 537);
        put(538, 1024);
        put(8690, 7797);
        put(8691, 7797);
    }};

    private static void pick(Player player, NPC npc) {
        player.lock();
        player.startEvent(e -> {
            e.delay(1);
            player.animate(3335);
            player.unlock();
        });
        npc.lock();
        npc.startEvent(e -> {
            e.delay(1);
            int id = npc.getId();
            npc.putTemporaryAttribute("ORIG_ID", id);
            npc.transform(FUNGI.get(id));
            npc.animate(3330);
            e.delay(1);
            npc.getCombat().setTarget(player);
            npc.getCombat().faceTarget();
            npc.doIfIdle(player, () -> {
                npc.startEvent(event -> {
                    npc.lock();
                    npc.animate(3327);
                    event.delay(1);
                    npc.transform(npc.getTemporaryAttribute("ORIG_ID"));
                });
            });
            npc.unlock();
        });
    }

    static {
        for (int mushroom : FUNGI.keySet()) {
            NPCAction.register(mushroom, "pick", ZygomiteFungi::pick);
        }
    }
}
