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

/**
 * Class that handles catching butterflies, very similar to imp catching.
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
@AllArgsConstructor
public enum Butterfly {

    RUBY_HARVEST(NpcID.RUBY_HARVEST, 15, 24, Items.RUBY_HARVEST),
    SAPPHIRE_GLACIALIS(NpcID.SAPPHIRE_GLACIALIS, 25, 34, Items.SAPPHIRE_GLACIALIS),
    SNOWY_KNIGHT(NpcID.SNOWY_KNIGHT, 35, 44, Items.SNOWY_KNIGHT),
    BLACK_WARLOCK(NpcID.BLACK_WARLOCK, 45, 54, Items.BLACK_WARLOCK);

    public final int npcId, levelReq;
    public final double experience;
    public final int jarId;

    /**
     * Test whether the player is capable of catching the butterfly.
     * @param player The player.
     * @param npc The butterfly npc.
     * @param butterfly The butterfly object.
     */
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

    /**
     * Keep attempting to catch butterfly until it is caught.
     * @param player The player catching the butterfly.
     * @param npc The butterfly npc.
     * @param butterfly The butterfly object.
     * @param jar The jar item.
     */
    private static void recursiveAttemptCatch(Player player, NPC npc, Butterfly butterfly, Item jar) {
        TargetRoute.set(player, npc, () -> {
            player.face(npc);
            attemptCatchEvent(player, npc, butterfly, jar);
        });
    }

    /**
     * Attempt to catch the butterfly.
     * @param player The player catching the butterfly.
     * @param npc The butterfly npc.
     * @param butterfly The butterfly object.
     * @param jar The jar item.
     */
    private static void attemptCatchEvent(Player player, NPC npc, Butterfly butterfly, Item jar) {
        player.startEvent(event -> {
            player.lock();
            player.animate(hasMagicButterflyNet(player) ? 6605 : 6606);
            event.delay(2);
            if (npc.isRemoved()) {  // Check if the npc has been removed
                return;
            }
            if (isCatch(player, butterfly)) {
                jar.setId(butterfly.jarId);
                removeButterfly(npc);
                player.getStats().addXp(StatType.Hunter, butterfly.experience, true);
                PlayerCounter.BUTTERFLIES_CAUGHT.increment(player, 1);
                player.unlock();
            } else {
                event.delay(1);
                player.unlock();
                recursiveAttemptCatch(player, npc, butterfly, jar);
            }
        });
    }

    /**
     * Remove the butterfly npc
     * @param npc The butterfly npc.
     */
    private static void removeButterfly(NPC npc) {
        npc.addEvent(event -> {
            npc.setHidden(true);
            event.delay(10);
            npc.getMovement().teleport(npc.getSpawnPosition());
            npc.setHidden(false);
        });
    }

    /**
     * Rolls for a successful catch.
     * @param player The player.
     * @param butterfly The butterfly object.
     * @return True if the butterfly is caught.
     */
    private static boolean isCatch(Player player, Butterfly butterfly) {
        int hunterLevel = player.getStats().get(StatType.Hunter).currentLevel;
        return Random.rollDie(4, Math.min(3, Math.max(1 , (int) ((hunterLevel - butterfly.levelReq) / 5))));
    }

    /**
     * Tests if the player has a butterfly net equipped.
     * @param player The player.
     * @return True if a net is equipped.
     */
    private static boolean hasButterflyNet(Player player) {
        return player.getEquipment().hasId(ButterflyNet.BUTTERFLY_NET);
    }

    /**
     * Test if the player has a magic butterfly net equipped.
     * @param player The player.
     * @return True if a magic net is equipped.
     */
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
