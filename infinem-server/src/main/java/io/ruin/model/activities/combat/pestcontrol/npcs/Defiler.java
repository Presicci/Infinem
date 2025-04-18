package io.ruin.model.activities.combat.pestcontrol.npcs;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Projectile;

/**
 * The combat script for the Defiler pest.
 * @author Heaven
 */
public class Defiler extends NPCCombat {

	private static final Projectile RANGED_PROJ = new Projectile(657, 50, 30, 50, 40, 8, 16, 0);

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
		if (withinDistance(8)) {
			rangedAttack();
			return true;
		}
		return false;
	}

	private void rangedAttack() {
		projectileAttack(RANGED_PROJ, info.attack_animation, AttackStyle.RANGED, info.max_damage);
	}
}
