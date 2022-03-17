package io.ruin.model.activities.miscpvm.slayer.superiors;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/17/2022
 */
public class NightBeast extends Superior {
    //TODO proper combat

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            basicAttack(info.attack_animation, info.attack_style, info.max_damage);
            return true;
        }
        return false;
    }
}
