package io.ruin.model.combat.npc.slayer.superiors.nechryarch;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.npc.slayer.superiors.Superior;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.route.routes.ProjectileRoute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/16/2022
 */
public class Nechryarch extends Superior {

    private NPC[] deathSpawns = new NPC[3];
    private final int[] SPAWNS = { 6716, 6723, 7649 };

    @Override
    public void follow() {
        follow(1);
    }


    @Override
    public void startDeath(Hit killHit) {
        for (NPC deathSpawn : deathSpawns) {
            if (deathSpawn != null && !deathSpawn.isRemoved() && !deathSpawn.getCombat().isDead())
                deathSpawn.getCombat().startDeath(killHit);
        }
        super.startDeath(killHit);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        if (Random.rollDie(3, 1))
            checkDeathSpawns();
        basicAttack();
        return true;
    }

    private void checkDeathSpawns() {
        npc.animate(7549);
        for (int i = 0; i < deathSpawns.length; i++) {
            if (deathSpawns[i] == null || deathSpawns[i].isRemoved()) {
                NPC spawn = deathSpawns[i] = new NPC(SPAWNS[i]).spawn(getSpawnPosition());
                spawn.face(target);
                spawn.getCombat().setTarget(target);
                spawn.addEvent(event -> {
                    event.delay(Random.get(15, 25));
                    if (!spawn.isRemoved() && !spawn.getCombat().isDead())
                        spawn.getCombat().startDeath(null);
                });
            }
        }
    }

    private Position getSpawnPosition() {
        List<Position> positions = new ArrayList<>(18);
        int radius = 1;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                Tile tile = Tile.get(target.getAbsX() + x, target.getAbsY() + y, npc.getHeight(), false);
                if (tile == null || (tile.npcCount == 0 && tile.playerCount == 0 && ProjectileRoute.allow(target, target.getAbsX() + x, target.getAbsY() + y)))
                    positions.add(new Position(target.getAbsX() + x, target.getAbsY(), target.getHeight()));
            }
        }

        // If no potential positions can be resolved then just default to death tile.
        if (positions.isEmpty()) {
            return getDropPosition();
        }

        return Random.get(positions);
    }
}
