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
            4766,   // Tegid
            5381,   // Clothears
            1992, 1993,     // Miners
            7790,   // John
            7791,   // David
            7480,   // Rakkar
            4463,   // Calin
            4466,   // Simona
            7384,   // Stumpy
            5454,   // Thumpy
            7386,   // Dumpy
            13983,  // Construction worker
            13982,  // Worm tongue
            13355,  // Renu
            12888,  // Primio
            12889,  // Primio
    };
    public static final Set<Integer> NPCS = new HashSet<>(Arrays.asList(VALUES));

}
