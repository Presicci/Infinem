package io.ruin.model.activities.bosses.bryophyta;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.route.routes.DumbRoute;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/22/2021
 */
public class Growthling extends NPCCombat {

    static {
        for (int i : Arrays.asList(1349, 1351, 1353, 1355, 1357, 1359, 6739, 1361, 23673, 23279, 20011, 13241, 5329, 7409)) // All axes and secateurs
            ItemNPCAction.register(i, 8194, (player, item, npc) -> chop(player, npc));
    }

    private static void chop(Player p, NPC npc) {
        if (npc.getHp() > 1) {
            p.sendMessage("The growthling is not weak enough to chop!");
            return;
        }
        p.addEvent(event -> {
            if (!DumbRoute.withinDistance(p, npc, 1)) {
                p.getRouteFinder().routeEntity(npc);
                event.waitForMovement(p);
            }
            p.animate(1665);
            event.delay(1);
            ((Growthling)npc.getCombat()).chopped = true;
            npc.getCombat().startDeath(null);
        });
    }

    private boolean chopped = false;

    @Override
    public boolean allowRespawn() {
        return false;
    }

    @Override
    public void init() {
        npc.setIgnoreMulti(true);
    }

    @Override
    public void startDeath(Hit killHit) {
        if (!chopped) {
            npc.setHp(1);
            return;
        }
        super.startDeath(null);
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
