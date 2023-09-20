package io.ruin.model.entity.npc.behavior;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/18/2023
 */
public class FightingNPC {

    public static final Map<Integer, Set<Integer>> TARGETS = new HashMap<>();

    private static final Integer[] LIZARDMAN = { 8563, 8564 };
    private static final Integer[] SHAYZIEN = { 8566, 8567, 8568 };

    private static void registerTargets(int id, Integer[] newTargets) {
        registerTargets(id, new HashSet<>(Arrays.asList(newTargets)));
    }

    private static void registerTargets(int id, Set<Integer> newTargets) {
        if (TARGETS.containsKey(id)) {
            Set<Integer> targets = TARGETS.get(id);
            targets.addAll(newTargets);
            TARGETS.put(id, targets);
        } else {
            TARGETS.put(id, newTargets);
        }
    }

    static {
        for (Integer id : LIZARDMAN) {
            registerTargets(id, SHAYZIEN);
        }
        for (Integer id : SHAYZIEN) {
            registerTargets(id, LIZARDMAN);
        }
    }
}
