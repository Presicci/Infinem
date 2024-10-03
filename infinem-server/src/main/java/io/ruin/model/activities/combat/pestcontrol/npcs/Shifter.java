package io.ruin.model.activities.combat.pestcontrol.npcs;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.routes.ProjectileRoute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The combat script for the Shifter pest.
 * @author Heaven
 */
public class Shifter extends NPCCombat {

	@Override
	public void init() {
		npc.hitListener = new HitListener().postDamage((hit)-> {
			Entity attacker = hit.attacker;
			if (attacker != null && attacker.isPlayer()) {
				Player player = attacker.player;
				if (player.pestGame != null && hit.damage > 0) {
					player.pestActivityScore += hit.damage;
				}
				if (player.pestGame != null && Config.PEST_CONTROL_ACTIVITY.get(player) < 50)
					Config.PEST_CONTROL_ACTIVITY.increment(player, 2);
			}
		});
	}

	@Override
	public void follow() {
		follow(1);
	}

	@Override
	public boolean attack() {
		if (teleport())
			return true;
		if (withinDistance(1)) {
			basicAttack();
			return true;
		}
		return false;
	}

	private boolean teleport() {
		if (target != null && Random.rollDie(4, 1)) {
			Bounds b = new Bounds(target.getPosition(), 1);
			List<Position> tiles = new ArrayList<>();
			b.forEachPos(p -> ProjectileRoute.allow(npc, p) && !p.equals(target.getPosition()), tiles::add);
			Collections.shuffle(tiles);
			if (tiles.size() <= 0) return false;
			Position dstTile = Random.get(tiles);
			npc.getMovement().teleport(dstTile);
			basicAttack();
			return true;
		}

		return false;
	}
}
