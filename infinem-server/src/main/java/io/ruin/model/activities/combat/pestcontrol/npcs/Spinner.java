package io.ruin.model.activities.combat.pestcontrol.npcs;

import com.google.common.collect.Streams;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.utility.Misc;

/**
 * The combat script for the Spinner pest.
 * @author Heaven
 */
public class Spinner extends NPCCombat {

	@Override
	public void init() {
		npc.deathStartListener = (entity, killer, killHit) -> entity.animate(6876);
		npc.deathEndListener = (entity, killer, hit) -> poison();
		npc.hitListener = new HitListener().postDamage((hit)-> {
			Entity attacker = hit.attacker;
			if (attacker != null && attacker.isPlayer()) {
				Player player = attacker.player;
				if (player.pestGame != null && hit.damage > 0) {
					player.pestActivityScore += hit.damage / 2;
				}
			}
		});
		delayAttack(10);
		NPC portal = npc.getTemporaryAttribute("PORTAL");
		if (portal != null) setTarget(portal);
	}

	@Override
	public void follow() {
		follow(1);
	}

	@Override
	public boolean attack() {
        return heal();
    }

	public boolean heal() {
		NPC portal = Streams.stream(npc.localNpcs())
				.filter(i -> i.getId() >= 1747 && i.getId() <= 1750)
				.findAny().orElse(null);
		if (portal != null) {
			npc.face(portal);
			npc.animate(3909);
			npc.graphics(658);
			portal.incrementHp(Random.get(5, 10));
			delayAttack(10);
			return true;
		}

		return false;
	}

	private void poison() {
		Entity victim = Streams.stream(npc.localNpcs()).filter(i -> Misc.getEffectiveDistance(npc, i) <= 1).findAny().orElse(null);
		if (victim != null && !victim.isPoisonImmune() && !victim.isPoisoned()) {
			victim.poison(5);
		}
	}
}
