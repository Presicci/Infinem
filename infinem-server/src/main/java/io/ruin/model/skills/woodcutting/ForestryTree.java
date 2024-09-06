package io.ruin.model.skills.woodcutting;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.process.event.EventConsumer;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2024
 */
public class ForestryTree {
    protected static final String PLAYERS_KEY = "TREE_PLAYERS";
    private static final String HEALTH_KEY = "TREE_HEALTH";
    private static final String TYPE_KEY = "TREE_TYPE";
    private static final String DEAD_ACTION_KEY = "TREE_DEAD";
    private static final List<GameObject> ACTIVE_TREES = new ArrayList<>();

    protected static int getTreePlayers(GameObject tree) {
        if (!tree.hasTemporaryAttribute(PLAYERS_KEY)) return 1;
        Map<Player, Integer> activePlayers = tree.getTemporaryAttribute(PLAYERS_KEY);
        if (activePlayers.isEmpty() || activePlayers.size() == 1) return 1;
        return activePlayers.size();
    }

    private static void setupTree(GameObject tree, Tree treeType, EventConsumer treeDeadAction) {
        tree.putTemporaryAttribute(TYPE_KEY, treeType);
        tree.putTemporaryAttribute(DEAD_ACTION_KEY, treeDeadAction);
        tree.putTemporaryAttribute(PLAYERS_KEY, Collections.synchronizedMap(new HashMap<Player, Integer>()));
        ACTIVE_TREES.add(tree);
    }

    protected static void pingIfActive(Player player, GameObject tree) {
        if (!tree.hasTemporaryAttribute(PLAYERS_KEY)) return;
        Map<Player, Integer> activePlayers = tree.getTemporaryAttribute(PLAYERS_KEY);
        activePlayers.put(player, 6);
    }

    protected static void pingTree(Player player, GameObject tree, Tree treeType, EventConsumer treeDeadAction) {
        if (!tree.hasTemporaryAttribute(PLAYERS_KEY)) setupTree(tree, treeType, treeDeadAction);
        Map<Player, Integer> activePlayers = tree.getTemporaryAttribute(PLAYERS_KEY);
        activePlayers.put(player, 6);
    }

    private static void damageTree(GameObject tree) {
        int health = tree.incrementTemporaryNumericAttribute(HEALTH_KEY, 5);
        Tree treeType = tree.getTemporaryAttribute(TYPE_KEY);
        if (health >= treeType.fellTime) {
            tree.removeTemporaryAttribute(PLAYERS_KEY);
            tree.removeTemporaryAttribute(HEALTH_KEY);
            EventConsumer deadAction = tree.getTemporaryAttribute(DEAD_ACTION_KEY);
            World.startEvent(deadAction);
        }
    }

    private static void healTree(GameObject tree) {
        int health = tree.incrementTemporaryNumericAttribute(HEALTH_KEY, -3);
        if (health <= 0) {
            tree.removeTemporaryAttribute(PLAYERS_KEY);
            tree.removeTemporaryAttribute(HEALTH_KEY);
        }
    }

    static {
        World.startEvent(e -> {
            while (true) {
                e.delay(5);
                for (GameObject tree : ACTIVE_TREES) {
                    // If tree is dormant
                    if (!tree.hasTemporaryAttribute(PLAYERS_KEY)) {
                        healTree(tree);
                        continue;
                    }
                    Map<Player, Integer> activePlayers = tree.getTemporaryAttribute(PLAYERS_KEY);
                    if (activePlayers.isEmpty()) {
                        healTree(tree);
                        continue;
                    }
                    damageTree(tree);
                    for (Player player : activePlayers.keySet()) {
                        activePlayers.merge(player, -5, Integer::sum);
                    }
                    activePlayers.entrySet().removeIf(entry -> entry.getValue() <= 0);
                }
                ACTIVE_TREES.removeIf(tree -> {
                    int health = tree.getTemporaryAttributeIntOrZero(HEALTH_KEY);
                    Tree treeType = tree.getTemporaryAttribute(TYPE_KEY);
                    return health >= treeType.fellTime || health <= 0;
                });
            }
        });
    }
}
