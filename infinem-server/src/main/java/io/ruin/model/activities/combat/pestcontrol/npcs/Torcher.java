package io.ruin.model.activities.combat.pestcontrol.npcs;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Graphic;
import io.ruin.model.map.Projectile;

/**
 * The combat script for the Torcher pest.
 * @author Heaven
 */
public class Torcher extends NPCCombat {

	private static final Projectile PROJECTILE = new Projectile(647, 50, 30, 15, 40, 8, 16, 0);

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
		follow(6);
	}

	@Override
	public boolean attack() {
		if (withinDistance(10)) {
			npc.graphics(646);
			projectileAttack(PROJECTILE, 3882, Graphic.builder().id(648).height(89).build(), AttackStyle.MAGIC, info.max_damage);
			return true;
		}
		return false;
	}
}
