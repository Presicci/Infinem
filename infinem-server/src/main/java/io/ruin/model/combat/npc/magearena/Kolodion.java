package io.ruin.model.combat.npc.magearena;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.wilderness.MageArena;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/23/2024
 */
public class Kolodion extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(64, 0);
    private final int[] GFX_HEIGHTS = new int[] { 128, 96, 0 };
    private final int[] FORMS = new int[] { 1605, 1606, 1607, 1608, 1609 };
    private final String[] MESSAGES = new String[] {
            "You must prove yourself... now!",
            "This is only the beginning; you can't beat me!",
            "Foolish mortal; I am unstoppable.",
            "Now you feel it... The dark energy.",
            "Aaaaaaaarrgghhhh! The power!"
    };
    private int form = 0;

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
        npc.forceText(MESSAGES[0]);
    }

    @Override
    public void startDeath(Hit killHit) {
        if (form < 4) {
            npc.transform(FORMS[++form]);
            npc.forceText(MESSAGES[form]);
            npc.setHp(npc.getMaxHp());
            return;
        }
        super.startDeath(killHit);
        Entity killer = killHit.attacker;
        if (killer != null && killer.isPlayer()) {
            Player player = killer.player;
            player.lock();
            player.startEvent(e -> {
                player.animate(714);
                player.graphics(111, 92, 0);
                player.publicSound(200);
                e.delay(3);
                player.getMovement().teleport(2542, 4716, 0);
                MageArena.CONFIG.set(player, 8);
                player.dialogue(
                        new NPCDialogue(1603, "Well done, young adventurer; you truly are a worthy battle mage."),
                        new PlayerDialogue("What now?"),
                        new NPCDialogue(1603, "Step into the magic pool. It will take you to a chamber. There, you must decide which god you will represent in the arena."),
                        new PlayerDialogue("Thanks, Kolodion."),
                        new NPCDialogue(1603, "That's what I'm here for.")
                );
                player.resetAnimation();
                player.unlock();
            });
        }
    }

    public void preDefend(Hit hit) {
        if (hit.attackStyle != AttackStyle.MAGIC) {
            hit.block();
        }
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        mageAttack();
        return true;
    }

    private void mageAttack() {
        int gfxOffset = Random.get(0, 2);
        npc.animate(info.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                .randDamage(info.max_damage)
                .clientDelay(delay);
        hit.postDamage(t -> {
            if(hit.damage > 0) {
                t.graphics(76 + gfxOffset, GFX_HEIGHTS[gfxOffset], 0);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
    }
}