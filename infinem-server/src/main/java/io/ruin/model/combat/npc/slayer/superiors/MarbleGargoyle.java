package io.ruin.model.combat.npc.slayer.superiors;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/16/2022
 */
public class MarbleGargoyle extends Superior {
    //TODO find gfx for spec attack and ranged attack, then code it
    //https://www.youtube.com/watch?v=Tm11cugG2gs
    //https://oldschool.runescape.wiki/w/Marble_gargoyle

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
