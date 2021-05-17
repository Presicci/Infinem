package io.ruin.model.skills.woodcutting;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.stat.StatType;

public class Ent {
    private static void summonTrunk(NPC npc, Player killer) {
        NPC trunk = new NPC(6595);
        trunk.ownerId = killer.getUserId();
        trunk.spawn(npc.getPosition());
        World.startEvent(e -> {
            e.delay(100);   // One minute
            trunk.remove();
        });
    }

    protected static int getEntLog(Player player, Hatchet axe) {
        int level = Woodcutting.getEffectiveLevel(player, Tree.ENTTRUNK, axe);
        if (level >= 75 && Random.rollDie(10, 4)) {
            return 1514;
        } else if (level > 60 && Random.rollDie(10, 5)) { // NOT a typo, 61 gives you yews.
            return 1516;
        } else if (level >= 40 && Random.rollDie(10, 4)) {
            return 1518;
        } else if (Random.rollDie(10, 5)) {
            return 1520;
        }
        return 1522; // Oak
    }

    static {
        SpawnListener.register(6594, npc -> {
            npc.deathEndListener = (DeathListener.SimplePlayer) killer -> summonTrunk(npc, killer);
        });
        SpawnListener.register(7234, npc -> {
            npc.deathEndListener = (DeathListener.SimplePlayer) killer -> summonTrunk(npc, killer);
        });
    }
}
