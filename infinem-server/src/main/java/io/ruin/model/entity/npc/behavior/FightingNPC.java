package io.ruin.model.entity.npc.behavior;

import io.ruin.model.activities.combat.godwars.GodwarsFollower;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/18/2023
 */
public class FightingNPC {

    private static final Map<Set<Integer>, Set<Integer>> PAIRINGS = new HashMap<Set<Integer>, Set<Integer>>() {{
        put(
                constructSet(8563, 8564), // Lizardmen
                constructSet(8566, 8567, 8568)// Shayzien warriors
        );
        put(
                constructSet(3114, 3243), // Farmers
                constructSet(3029, 3030, 3031, 3033, 3034, 3035, 3036)  // Goblins
        );
        // Godwars Dungeon
        List<Integer> armadylEnemies = new ArrayList<>();
        armadylEnemies.addAll(GodwarsFollower.SARADOMIN_FOLLOWER);
        armadylEnemies.addAll(GodwarsFollower.BANDOS_FOLLOWER);
        armadylEnemies.addAll(GodwarsFollower.ZAMORAK_FOLLOWER);
        put(
                constructSet(GodwarsFollower.ARMADYL_FOLLOWER.toArray(new Integer[0])),
                constructSet(armadylEnemies)
        );
        List<Integer> zamorakEnemies = new ArrayList<>();
        zamorakEnemies.addAll(GodwarsFollower.SARADOMIN_FOLLOWER);
        zamorakEnemies.addAll(GodwarsFollower.BANDOS_FOLLOWER);
        zamorakEnemies.addAll(GodwarsFollower.ARMADYL_FOLLOWER);
        put(
                constructSet(GodwarsFollower.ZAMORAK_FOLLOWER.toArray(new Integer[0])),
                constructSet(zamorakEnemies)
        );
        List<Integer> saradominEnemies = new ArrayList<>();
        saradominEnemies.addAll(GodwarsFollower.ARMADYL_FOLLOWER);
        saradominEnemies.addAll(GodwarsFollower.BANDOS_FOLLOWER);
        saradominEnemies.addAll(GodwarsFollower.ZAMORAK_FOLLOWER);
        put(
                constructSet(GodwarsFollower.SARADOMIN_FOLLOWER.toArray(new Integer[0])),
                constructSet(saradominEnemies)
        );
        List<Integer> bandosEnemies = new ArrayList<>();
        bandosEnemies.addAll(GodwarsFollower.SARADOMIN_FOLLOWER);
        bandosEnemies.addAll(GodwarsFollower.ARMADYL_FOLLOWER);
        bandosEnemies.addAll(GodwarsFollower.ZAMORAK_FOLLOWER);
        put(
                constructSet(GodwarsFollower.BANDOS_FOLLOWER.toArray(new Integer[0])),
                constructSet(bandosEnemies)
        );
    }};

    private static Set<Integer> constructSet(List<Integer> ids) {
        return new HashSet<Integer>() {{ addAll(ids); }};
    }

    private static Set<Integer> constructSet(Integer... ids) {
        return new HashSet<Integer>() {{ addAll(Arrays.asList(ids)); }};
    }

    public static Set<Integer> getTargets(int npcId) {
        for (Map.Entry<Set<Integer>, Set<Integer>> pair : PAIRINGS.entrySet()) {
            if (pair.getKey().contains(npcId)) {
                return pair.getValue();
            }
        }
        return null;
    }
}
