package io.ruin.model.combat.npc;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/18/2024
 */
public class ThrowerTroll extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(304, 30, 26, 30, 51, 0, 10, 5);

    @Override
    public void init() {}

    private boolean areaCheck(Entity target) {
        Position pos = target.getPosition();
        return (target.getCombat().getTarget() != npc
                && !(pos.inBounds(DEATH_PLATEAU_BOUNDS_1)
                || pos.inBounds(DEATH_PLATEAU_BOUNDS_2)
                || pos.inBounds(DEATH_PLATEAU_BOUNDS_3)
                || pos.inBounds(TROLLHEIM_BOUNDS_1)
                || pos.inBounds(TROLLHEIM_BOUNDS_2)
                || pos.inBounds(TROLLHEIM_BOUNDS_3)
                || pos.inBounds(TROLLHEIM_BOUNDS_4)));
    }

    @Override
    protected boolean playerAggroExtraCheck(Player player) {
        return !areaCheck(player);
    }

    @Override
    public void follow() {
        if (areaCheck(target)) {
            reset();
        } else {
            follow(8);
        }
    }

    private static final Bounds DEATH_PLATEAU_BOUNDS_1 = new Bounds(2837, 3596, 2847, 3604, 0);
    private static final Bounds DEATH_PLATEAU_BOUNDS_2 = new Bounds(2848, 3601, 2852, 3606, 0);
    private static final Bounds DEATH_PLATEAU_BOUNDS_3 = new Bounds(2853, 3604, 2873, 3606, 0);
    private static final Bounds TROLLHEIM_BOUNDS_1 = new Bounds(2902, 3697, 2909, 3701, 0);
    private static final Bounds TROLLHEIM_BOUNDS_2 = new Bounds(2899, 3698, 2901, 3704, 0);
    private static final Bounds TROLLHEIM_BOUNDS_3 = new Bounds(2895, 3700, 2898, 3703, 0);
    private static final Bounds TROLLHEIM_BOUNDS_4 = new Bounds(2882, 3701, 2894, 3706, 0);

    @Override
    public boolean attack() {
        /*Position pos = target.getPosition();
        if (target.getCombat().getTarget() != npc
                && !(pos.inBounds(DEATH_PLATEAU_BOUNDS_1)
                || pos.inBounds(DEATH_PLATEAU_BOUNDS_2)
                || pos.inBounds(DEATH_PLATEAU_BOUNDS_3)
                || pos.inBounds(TROLLHEIM_BOUNDS_1)
                || pos.inBounds(TROLLHEIM_BOUNDS_2)
                || pos.inBounds(TROLLHEIM_BOUNDS_3)
                || pos.inBounds(TROLLHEIM_BOUNDS_4))) {
            setTarget(null);
            npc.face(Direction.NORTH);
            return false;
        }*/
        if (withinDistance(8)) {
            projectileAttack(PROJECTILE, info.attack_animation, info.attack_style, info.max_damage);
            return true;
        }
        return false;
    }
}
