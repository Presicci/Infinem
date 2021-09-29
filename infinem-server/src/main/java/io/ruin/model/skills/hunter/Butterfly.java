package io.ruin.model.skills.hunter;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.cache.NpcID;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.ButterflyNet;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Butterfly {

    RUBY_HARVEST(NpcID.RUBY_HARVEST, 15, 24, Items.RUBY_HARVEST),
    SAPPHIRE_GLACIALIS(NpcID.SAPPHIRE_GLACIALIS, 25, 34, Items.SAPPHIRE_GLACIALIS),
    SNOWY_KNIGHT(NpcID.SNOWY_KNIGHT, 35, 44, Items.SNOWY_KNIGHT),
    BLACK_WARLOCK(NpcID.BLACK_WARLOCK, 45, 54, Items.BLACK_WARLOCK);

    public final int npcId, levelReq;
    public final double experience;
    public final int jarId;

    protected static void attemptCatch(Player player, NPC npc, Butterfly butterfly) {
        if (!hasButterflyNet(player) && !hasMagicButterflyNet(player)) {
            player.sendMessage("You need a butterfly net to catch butterflies.");
            return;
        }
        Item butterflyJar = player.getInventory().findItem(Items.BUTTERFLY_JAR);
        if (butterflyJar == null) {
            player.sendFilteredMessage("You don't have an empty butterfly jar in which to keep an butterfly.");
            return;
        }
        int hunterLevel = player.getStats().get(StatType.Hunter).currentLevel;
        int levelReq = butterfly.levelReq;
        if (hunterLevel < levelReq) {
            player.sendFilteredMessage("You need a Hunter level of at least " + levelReq + " to catch this butterfly.");
            return;
        }
        if (!player.getPosition().isWithinDistance(npc.getPosition(), 1))
            return;
        attemptCatchEvent(player, npc, butterfly, butterflyJar);
    }

    private static void recursiveAttemptCatch(Player player, NPC npc, Butterfly butterfly, Item impJar) {
        TargetRoute.set(player, npc, () -> {
            player.face(npc);
            attemptCatchEvent(player, npc, butterfly, impJar);
        });
    }

    private static void attemptCatchEvent(Player player, NPC npc, Butterfly butterfly, Item impJar) {
        player.startEvent(event -> {
            player.lock();
            player.animate(hasMagicButterflyNet(player) ? 6605 : 6606);
            event.delay(2);
            if (npc.isRemoved()) {  // Check if the npc has been removed
                return;
            }
            if (isCatch(player, butterfly)) {
                impJar.setId(butterfly.jarId);
                npc.remove();
                player.getStats().addXp(StatType.Hunter, butterfly.experience, true);
                PlayerCounter.BUTTERFLIES_CAUGHT.increment(player, 1);
                player.unlock();
            } else {
                event.delay(1);
                player.unlock();
                recursiveAttemptCatch(player, npc, butterfly, impJar);
            }
        });
    }

    private static boolean isCatch(Player player, Butterfly butterfly) {
        int hunterLevel = player.getStats().get(StatType.Hunter).currentLevel;
        return Random.rollDie(4, Math.min(3, Math.max(1 , (int) ((hunterLevel - butterfly.levelReq) / 5))));
    }

    private static boolean hasButterflyNet(Player player) {
        return player.getEquipment().hasId(ButterflyNet.BUTTERFLY_NET);
    }

    private static boolean hasMagicButterflyNet(Player player) {
        return player.getEquipment().hasId(ButterflyNet.MAGIC_BUTTERFLY_NET);
    }

    static {
        for (Butterfly butterfly : values()) {
            NPCAction.register(butterfly.npcId, "catch", (player, npc) -> attemptCatch(player, npc, butterfly));
            NPCDef.get(butterfly.npcId).flightClipping = true;
        }
    }
}
