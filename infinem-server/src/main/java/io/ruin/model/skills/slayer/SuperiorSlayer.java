package io.ruin.model.skills.slayer;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public class SuperiorSlayer {

    /**
     * Called on npc death, rolls to spawn a superior.
     * @param player The play that killed the npc.
     * @param task The slayer task that the player is on.
     * @param npc The npc that the player killed.
     */
    static void trySpawn(Player player, SlayerCreature task, NPC npc) {
        if (Config.BIGGER_AND_BADDER.get(player) == 0) {
            return;
        }
        int superior = getSuperior(task, npc.getId());
        if (superior == -1 || superior == npc.getId()) {
            return;
        }
        int chance = 2;   // 1/200 chance of appearing by default
        if (Random.get(chance) == 1) {
            NPC monster = new NPC(superior);
            monster.targetPlayer(player, false); // Targets player so no one can steal
            monster.spawn(npc.getPosition());
            monster.startEvent(e -> {   // If the player leaves or ignores the superior for 2 minutes, remove npc
                int loops = 0;
                while (loops < 4) { // 4 30 second loops, or 2 minutes
                    e.delay(Server.toTicks(30));    // Check if the player is offline/out of range every 30 seconds
                    if (player == null || !player.isOnline() || player.getPosition() == null || npc.getPosition().distance(player.getPosition()) > 15) {
                        loops = 4;
                    } else {
                        ++loops;
                    }
                }
                monster.remove();
            });
            player.sendMessage("<col=ff0000>A superior foe has appeared...</col>");
        }
    }

    /**
     * Gets the superior for the provided slayer task.
     * @param task The slayer task that the superior is generating for.
     * @param npcID The ID of the npc killed.
     * @return NPC ID for the superior monster.
     */
    static int getSuperior(SlayerCreature task, int npcID) {
        switch(task) {
            case BASILISKS:
                return 7395;
            case COCKATRICE:
                return 7393;
            case BANSHEE:
                if(npcID == 7272)
                    return 7391;
                return 7390;
            case ABERRANT_SPECRES:
                if(npcID == 7279)
                    return 7403;
                return 7402;
            case ABYSSAL_DEMON:
                return 7410;
            case BLOODVELDS:
                if(npcID == 7276)
                    return 7398;
                return 7397;
            case CAVE_CRAWLER:
                return 7389;
            case CAVE_HORRORS:
                return 7401;
            case CRAWLING_HANDS:
                return 7388;
            case DUST_DEVILS:
                return 7404;
            case GARGOYLE:
                return 7407;
            case JELLIES:
                if(npcID == 7277)
                    return 7400;
                return 7399;
            case KURASK:
                return 7405;
            case NECHRYAEL:
                return 7411;
            case SMOKE_DEVILS:
                return 7406;
            case ROCKSLUG:
                return 7392;
            case DARK_BEASTS:
                return 7409;
            case PYREFIEND:
                return 7394;
            default:
                return -1;
        }
    }
}
