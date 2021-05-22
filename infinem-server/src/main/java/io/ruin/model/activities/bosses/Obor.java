package io.ruin.model.activities.bosses;

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
    public void init() {
        npc.getCombat().setAllowRespawn(false);
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (target instanceof Player) {
            if (((Player) target).getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {

            } else {

            }
        }
        return true;
    }
}
