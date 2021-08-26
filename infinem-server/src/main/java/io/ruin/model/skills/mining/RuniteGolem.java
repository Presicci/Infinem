package io.ruin.model.skills.mining;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/26/2021
 */
public class RuniteGolem {
    private static void summonRock(NPC npc, Player killer) {
        NPC rock = new NPC(6601);
        rock.ownerId = killer.getUserId();
        rock.spawn(npc.getPosition());
        World.startEvent(e -> {
            e.delay(100);   // One minute
            rock.remove();
        });
    }

    static {
        SpawnListener.register(6600, npc -> {
            npc.deathEndListener = (DeathListener.SimplePlayer) killer -> summonRock(npc, killer);
        });
    }
}
