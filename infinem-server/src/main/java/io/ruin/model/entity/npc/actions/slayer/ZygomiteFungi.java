package io.ruin.model.entity.npc.actions.slayer;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/11/2024
 */
public class ZygomiteFungi {

    private static final int[] FUNGI = { 533, 538 };

    private static void pick(Player player, NPC npc) {
        player.lock();
        player.startEvent(e -> {
            e.delay(1);
            player.animate(3335);
            player.unlock();
        });
        npc.lock();
        npc.startEvent(e -> {
            int id = npc.getId();
            e.delay(1);
            npc.putTemporaryAttribute("ORIG_ID", npc.getId());
            npc.transform(id == 533 ? 537 : 1024);
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
        for (int mushroom : FUNGI) {
            NPCAction.register(mushroom, "pick", ZygomiteFungi::pick);
        }
    }
}
