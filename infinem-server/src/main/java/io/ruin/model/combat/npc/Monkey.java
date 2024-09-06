package io.ruin.model.combat.npc;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.MonkeyGreeGree;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/5/2024
 */
public class Monkey extends NPCCombat {

    @Override
    public void init() {}

    @Override
    protected boolean playerAggroExtraCheck(Player player) {
        int targetAppearance = player.getAppearance().getNpcId();
        if (targetAppearance > -1) {
            for (MonkeyGreeGree greeGree : MonkeyGreeGree.values()) {
                if (targetAppearance == greeGree.npcId) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void follow() {
        if (target.isPlayer()) {
            int targetAppearance = target.player.getAppearance().getNpcId();
            if (targetAppearance > -1) {
                for (MonkeyGreeGree greeGree : MonkeyGreeGree.values()) {
                    if (targetAppearance == greeGree.npcId) {
                        return;
                    }
                }
            }
        }
        follow(1);
    }

    @Override
    public boolean attack() {
        if (target.isPlayer()) {
            int targetAppearance = target.player.getAppearance().getNpcId();
            if (targetAppearance > -1) {
                for (MonkeyGreeGree greeGree : MonkeyGreeGree.values()) {
                    if (targetAppearance == greeGree.npcId) {
                        return false;
                    }
                }
            }
        }

        if(withinDistance(1)) {
            basicAttack();
            return true;
        }
        return false;
    }

}