package io.ruin.model.skills.woodcutting;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.stat.StatType;

public class Ent {
    private static void summonTrunk(NPC npc, Player killer) {
        NPC trunk = new NPC(9474);
        trunk.ownerId = killer.getUserId();
        trunk.spawn(npc.getPosition());
        World.startEvent(e -> {
            e.delay(100);   // One minute
            trunk.remove();
        });
    }

    protected static int getEntLog(Player player, Hatchet axe) {
        int level = Woodcutting.getEffectiveLevel(player, Tree.ENTTRUNK, null);
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
        NPCAction.register(9474, "chop", (player, npc) -> player.startEvent(event -> {
            Hatchet axe = Hatchet.find(player);

            if (axe == null) {
                player.sendMessage("You do not have an axe which you have the woodcutting level to use.");
                player.privateSound(2277);
                return;
            }
            while (!npc.isRemoved()) {
                player.animate(axe.canoeAnimationId);
                event.delay(3);

                if (Woodcutting.successfullyCutTree(Woodcutting.getEffectiveLevel(player, Tree.ENTTRUNK, null), Tree.ENTTRUNK, axe)) {
                    int amt = Wilderness.players.contains(player) ? 2 : 1;
                    if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST))
                        amt *= 2;
                    player.getStats().addXp(StatType.Woodcutting, Tree.ENTTRUNK.experience * amt, true);
                    player.getInventory().add(Ent.getEntLog(player, axe), amt);
                    Woodcutting.rollBirdNest(player, Tree.ENTTRUNK);
                    if (Random.rollDie(10, 1)) { // 10% chance per harvest of trunk leaving
                        npc.remove();
                    }
                }
            }
        }));
    }
}
