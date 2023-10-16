package io.ruin.model.entity.npc.behavior;

import java.util.*;

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
    }};

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
