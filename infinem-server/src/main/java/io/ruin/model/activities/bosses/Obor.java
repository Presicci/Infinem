package io.ruin.model.activities.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.prayer.Prayer;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/21/2021
 */
public class Obor extends NPCCombat {   //WIP TODO find ranged gfx

    private static final int RANGED_EMOTE = 7183;

    @Override
    public boolean allowRespawn() {
        return false;
    }

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(2);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(2))
            return false;
        basicAttack();
        return true;
    }
}
