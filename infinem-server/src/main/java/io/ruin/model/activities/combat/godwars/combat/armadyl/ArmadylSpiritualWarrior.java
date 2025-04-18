package io.ruin.model.activities.combat.godwars.combat.armadyl;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.inter.utils.Config;

public class ArmadylSpiritualWarrior extends NPCCombat {
    @Override
    public void init() {
        npc.attackNpcListener = (player, n, message) -> {
            if (player.getCombat().getAttackStyle().isMelee() && player.getCombat().queuedSpell == null && Config.AUTOCAST.get(player) == 0) {
                if (message)
                    player.sendMessage("The aviansie is flying too high for you to hit with melee.");
                return false;
            }
            return true;
        };
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        basicAttack();
        return true;
    }
}
