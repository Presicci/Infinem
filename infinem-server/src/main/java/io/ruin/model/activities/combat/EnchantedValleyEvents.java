package io.ruin.model.activities.combat;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/10/2023
 */
public class EnchantedValleyEvents {

    private static final int TREE = 16265, FISHING_SPOT = 6731;
    private static final int[] ROCKS = { 26873, 26874 };

    private static final int TREE_SPIRIT = 1861, RIVER_TROLL = 6732, ROCK_GOLEM = 6725;

    private static void spawnEvent(Player player, int baseNPCId) {
        if (player.hasTemporaryAttribute("ENCHANTED_GUARDIAN")) {
            NPC npc =  player.getTemporaryAttribute("ENCHANTED_GUARDIAN");
            if (npc.getDef().name.toLowerCase().contains("tree spirit"))
                npc.forceText("I said to leave these woods!");
            else if (npc.getDef().name.toLowerCase().contains("river troll"))
                npc.forceText("I said mine fishies!");
            else
                npc.forceText("Raarrrgghh!");
            return;
        }
        int combat = player.getCombat().getLevel();
        int idOffset = combat > 10 ? combat > 20 ? combat > 40 ? combat > 60 ? combat > 90 ? 5 : 4 : 3 : 2 : 1 : 0;
        int npcId = baseNPCId + idOffset;
        int tries = 0;
        Position destination = null;
        while (tries++ < 10) {
            int x = Random.get(-1, 1);
            destination = player.getPosition().copy().translate(x, x == 0 ? Random.get(-1, 1) : 0, 0);
            if (!destination.equals(player.getPosition()) && ProjectileRoute.allow(player, destination)) break;
        }
        Position finalDestination = destination;
        NPC guardian = new NPC(npcId);
        guardian.spawn(finalDestination);
        guardian.removeIfIdle(player);
        guardian.removalAction = ((p) -> p.removeTemporaryAttribute("ENCHANTED_GUARDIAN"));
        guardian.targetPlayer(player, false); // Targets player so no one can steal
        guardian.attackTargetPlayer(() -> !player.getPosition().isWithinDistance(finalDestination));
        guardian.removeOnDeath();
        if (baseNPCId == TREE_SPIRIT)
            guardian.forceText("Leave these woods and never return!");
        else if (baseNPCId == RIVER_TROLL)
            guardian.forceText("Fishies be mine, leave dem fishies!");
        else
            guardian.forceText("Raarrrgghh! Flee human!");
        player.putTemporaryAttribute("ENCHANTED_GUARDIAN", guardian);
    }

    static {
        ObjectAction.register(TREE, "chop-down", (player, obj) -> spawnEvent(player, TREE_SPIRIT));
        NPCAction.register(FISHING_SPOT, "small net", (player, obj) -> spawnEvent(player, RIVER_TROLL));
        for (int rock : ROCKS) {
            ObjectAction.register(rock, "mine", (player, obj) -> spawnEvent(player, ROCK_GOLEM));
            ObjectAction.register(rock, "prospect", (player, obj) -> {
                player.startEvent(event -> {
                    player.lock();
                    player.sendMessage("You examine the rock for ores...");
                    event.delay(4);
                    player.getStats().addXp(StatType.Mining, 1, false);
                    player.sendMessage("This rock contains rocks.");
                    player.unlock();
                });
            });
        }
    }
}
