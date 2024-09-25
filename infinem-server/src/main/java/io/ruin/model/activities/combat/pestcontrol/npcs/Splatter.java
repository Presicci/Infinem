package io.ruin.model.activities.combat.pestcontrol.npcs;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.utility.Misc;

import java.util.Arrays;

/**
 * The combat script for the Splatter pest.
 * @author Heaven
 */
public class Splatter extends NPCCombat {

	@Override
	public void init() {
		//npc.deathStartListener = (entity, killer, killHit) -> splat();
		npc.hitListener = new HitListener().postDamage((hit)-> {
			Entity attacker = hit.attacker;
			if (attacker != null && attacker.isPlayer()) {
				Player player = attacker.player;
				if (player.pestGame != null && hit.damage > 0) {
					player.pestActivityScore += hit.damage / 2;
				}
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

	private void splat() {
		npc.addEvent(e -> {
			npc.publicSound(847);
			e.delay(4);
			npc.graphics(npc.getId() - 1039);
			npc.localPlayers().forEach(p -> {
				if (Misc.getEffectiveDistance(npc, p) <= 1) {
					p.hit(new Hit(npc).fixedDamage(Random.get(5, 24)));
				}
			});
			npc.localNpcs().forEach(n -> {
				if (!Arrays.asList(2950, 2951, 2952, 2953).contains(n.getId()) // Can't hit the void knight
						&& Misc.getEffectiveDistance(npc, n) <= 1) {
					n.hit(new Hit(npc).fixedDamage(Random.get(5, 24)));
				}
			});
		});

	}
}
