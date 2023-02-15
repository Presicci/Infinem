package io.ruin.model.skills.agility.courses;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/4/2022
 */
public class AgilityPyramid {

    private static boolean IsSuccessful(Player player, GameObject object) {
        int level = player.getStats().get(StatType.Agility).currentLevel;
        int baseReq = 30;
        int baseChance = 75;
        int neverFailLevel = 70;
        int adjustmentPercentage = 100 - baseChance;
        float successPerLevel = (float) adjustmentPercentage / ((float) neverFailLevel - baseReq);
        float successChance = baseChance + Math.min(0, (level - baseReq)) * successPerLevel;
        return Random.get(100) < successChance;
    }
}
