package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2023
 */
public class KeldagrimFerry {

    public static void travel(Player player, NPC npc) {
        if (npc.getId() == 4896) {
            Traveling.fadeTravel(player, new Position(2854, 10142));
        } else if (npc.getId() == 4897) {
            Traveling.fadeTravel(player, new Position(2840, 10132));
        } else if (npc.getId() == 2433) {
            Traveling.fadeTravel(player, new Position(2840, 10132));
        } else {
            Traveling.fadeTravel(player, new Position(2887, 10224));
        }

    }

    static {
        NPCAction.register(4896, "travel", KeldagrimFerry::travel);
        NPCAction.register(4897, "travel", KeldagrimFerry::travel);
        NPCAction.register(7726, "travel", KeldagrimFerry::travel);
        NPCAction.register(2433, "travel", KeldagrimFerry::travel);
    }
}
