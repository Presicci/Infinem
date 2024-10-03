package io.ruin.model.activities.combat.pestcontrol.npcs;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.utils.Config;

/**
 * The combat script for the Brawler pest.
 * @author Heaven
 */
public class Brawler extends NPCCombat {

	@Override
	public void init() {
		npc.clip = true;
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
		if (withinDistance(1)) {
			basicAttack();
			return true;
		}
		return false;
	}
}
