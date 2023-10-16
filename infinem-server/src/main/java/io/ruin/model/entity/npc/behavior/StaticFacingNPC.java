package io.ruin.model.entity.npc.behavior;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/18/2023
 */
public class StaticFacingNPC {

    private static final Integer[] VALUES = new Integer[] {
            2542,   // Sitting H.A.M. member
            3596,   // 'Black-eye'
            4766    // Tegid
    };
    public static final Set<Integer> NPCS = new HashSet<>(Arrays.asList(VALUES));

}
