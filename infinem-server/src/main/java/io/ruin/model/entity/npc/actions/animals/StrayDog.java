package io.ruin.model.entity.npc.actions.animals;

import io.ruin.api.utils.ArrayUtils;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.routes.DumbRoute;

import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/14/2024
 */
public class StrayDog {

    private static final String KEY = "FOLLOWING";
    private static final String LAST_KEY = "LAST_TARGET";

    static {
        NPCAction.register("stray dog", "pet", (player, npc) -> {
            player.animate(827);
            player.sendMessage("You pet the dog...");
            player.forceText("Who's a good doggie?");
            npc.forceText("Woof!");
            if (npc.getId() == 2922 || npc.getId() == 2902)
                player.getTaskManager().doLookupByUUID(334);    // Pet a Stray Dog in Varrock
        });
        SpawnListener.register(ArrayUtils.of("stray dog"), npc -> {
            npc.addEvent(e -> {
                int followCycles = 0;
                while(true) {
                    if (npc.hasTemporaryAttribute(KEY)) {
                        Player followingPlayer = npc.getTemporaryAttributeOrDefault(KEY, null);
                        if (followingPlayer == null || !followingPlayer.isOnline() || followingPlayer.getPosition().distance(npc.getPosition()) > 10) {
                            npc.removeTemporaryAttribute(KEY);
                            npc.faceNone(false);
                            followCycles = 0;
                            continue;
                        }
                        if (followCycles > 25 && Random.rollDie(4)) {
                            npc.removeTemporaryAttribute(KEY);
                            npc.faceNone(false);
                            followCycles = 0;
                            continue;
                        }
                        int destX, destY;
                        if (followingPlayer.getMovement().hasMoved()) {
                            destX = followingPlayer.getMovement().lastFollowX;
                            destY = followingPlayer.getMovement().lastFollowY;
                        } else {
                            destX = followingPlayer.getMovement().followX;
                            destY = followingPlayer.getMovement().followY;
                        }
                        if (destX == -1 || destY == -1)
                            DumbRoute.step(npc, followingPlayer, 1);
                        else if (!npc.isAt(destX, destY))
                            DumbRoute.step(npc, destX, destY);
                        followCycles++;
                        e.delay(1);
                    } else {
                        e.delay(15);
                        List<Player> players = npc.localPlayers();
                        if (players.isEmpty()) continue;
                        for (Player player : players) {
                            Player lastPlayer = npc.getTemporaryAttributeOrDefault(LAST_KEY, null);
                            if (player == lastPlayer) continue;
                            if (player.getPosition().distance(npc.getPosition()) < 8) {
                                npc.putTemporaryAttribute(KEY, player);
                                npc.putTemporaryAttribute(LAST_KEY, player);
                                npc.face(player);
                                break;
                            }
                        }
                    }
                }
            });
        });
    }
}
