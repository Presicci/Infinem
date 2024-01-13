package io.ruin.model.activities.combat.nightmarezone;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 11/27/2021
 */
@AllArgsConstructor @Getter
public enum NightmareZoneMonster {
    TRAPPED_SOUL(8528, 8529, 183, 2301),
    THE_KENDAL(6383, 6322, 998, 10127),
    COUNT_DRAYNOR(6393, 6332, 224, 7135),
    //CORSAIR_TRAITOR(7949, 7948, 244, 2301), // Corsair Traitor (dontwork)
    SAND_SNAKE(7895, 7894, 244, 5294),
    KING_ROALD(6389, 6328, 448, 8056),
    MOSS_GUARDIAN(6386, 6325, 1426, 7595),
    SKELETON_HELLHOUND(6387, 6326, 1915, 8976),
    DAD(6391, 6330, 2078, 9207),
    KHAZARD_WARLORD(6390, 6329, 2547, 8286),
    // ICE_TROLL_KING(6356, 6294, 3016, 10357),
    BOUNCER(6355, 6293, 3811, 13580),
    BLACK_DEMON(6357, 6295, 6012, 19564);

    private final int normalId, hardId, normalPoints, hardPoints;

    public static List<Integer> getAsList(boolean hardmode) {
        List<Integer> monsters = new ArrayList<>();
        for (NightmareZoneMonster mon : values()) {
            monsters.add(hardmode ? mon.getHardId() : mon.getNormalId());
        }
        return monsters;
    }

    public static int getPoints(int id) {
        for (NightmareZoneMonster mon : values())
            if (mon.getHardId() == id) {
                return mon.getHardPoints();
            } else if (mon.getNormalId() == id) {
                return mon.getNormalPoints();
            }
        return 0;
    }
}
