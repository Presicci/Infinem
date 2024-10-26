package io.ruin.model.activities.wilderness.bosses.venenatis;

import io.ruin.cache.NpcID;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.npc.NPC;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/24/2024
 */
public class Spiderling extends NPC {
    public final Venenatis venenatis;

    public Spiderling(int id, Venenatis venenatis) {
        super(id);
        this.venenatis = venenatis;
        venenatis.spiderlingsAlive++;
        init();
    }

    @Override
    public SpiderlingCombat getCombat() {
        if (!(super.getCombat() instanceof SpiderlingCombat)) {
            initCombat();
        }
        return (SpiderlingCombat) super.getCombat();
    }

    private void initCombat() {
        combat = new SpiderlingCombat(this);
        combat.init(this, getDef().combatInfo);
    }

    static {
        NPCDefinition.get(NpcID.SPINDELS_SPIDERLING).occupyTiles = false;
        NPCDefinition.get(NpcID.VENENATIS_SPIDERLING_12000).occupyTiles = false;
    }
}