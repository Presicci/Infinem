package io.ruin.model.combat.npc;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/23/2024
 */
public class Shade extends NPCCombat {

    private int originalId;

    private int getShadeId() {
        switch (originalId) {
            case 1276:  // Loar
                return 1277;
            case 1279:  // Phrin
                return 1280;
            case 1281:  // Riyl
                return 1282;
            case 1283:  // Asyn
                return 1284;
            case 1285:  // Fiyr
                return 1286;
            case 6143:  // Urium
                return 10589;
            default:
                return -1;
        }
    }

    @Override
    public void init() {
        originalId = npc.getId();
        npc.hitListener = new HitListener().preDefend(((hit) -> transform()));
        npc.deathEndListener = (entity, killer, killHit) -> npc.transform(originalId);
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (transform())
            return true;
        if (!withinDistance(1))
            return false;
        basicAttack();
        return true;
    }

    private boolean transform() {
        if (getShadeId() == -1) return false;
        if (npc.getId() == getShadeId()) return false;
        npc.transform(getShadeId());
        npc.animate(1288);
        npc.doIfOutOfCombat(() -> npc.transform(originalId));
        return true;
    }
}
