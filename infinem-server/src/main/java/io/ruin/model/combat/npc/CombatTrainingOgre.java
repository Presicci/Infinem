package io.ruin.model.combat.npc;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Bounds;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/4/2024
 */
public class CombatTrainingOgre extends NPCCombat {

    private static final Bounds ogreCage = new Bounds(2523, 3373, 2533, 3377, 0);

    @Override
    public void init() {
        npc.attackNpcListener = (player, n, message) -> {
            if (ogreCage.inBounds(player)) {
                if (message)
                    player.sendMessage("These ogres are for ranged combat only.");
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
        if (withinDistance(1)) {
            basicAttack(info.attack_animation, info.attack_style, info.max_damage);
            return true;
        }
        return false;
    }

}