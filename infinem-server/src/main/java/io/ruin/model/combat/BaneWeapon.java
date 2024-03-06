package io.ruin.model.combat;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.Entity;
import io.ruin.model.item.Items;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/6/2024
 */
public enum BaneWeapon {
    SILVERLIGHT(Items.SILVERLIGHT, "DEMON", AttackStyle::isMelee, 0, 0.6),
    DARKLIGHT(Items.DARKLIGHT, "DEMON", AttackStyle::isMelee, 0, 0.6),
    DRAGON_HUNTER_CROSSBOW(Items.DRAGON_HUNTER_CROSSBOW, "DRAGON", AttackStyle::isRanged, 0.3, 0.25),
    DRAGON_HUNTER_LANCE(22978, "DRAGON", AttackStyle::isMelee, 0.2, 0.2),
    BARRONITE_MACE(25641, "GOLEM", AttackStyle::isMelee, 0, 0.15)
    ;

    private final int itemId;
    private final String tagKey;
    private final Predicate<AttackStyle> predicate;
    private final double attackBoost, damageBoost;
    private final HitStageOutgoing otherEffectStage;
    private final Consumer<Hit> otherEffects;

    BaneWeapon(int itemId, String tagKey, Predicate<AttackStyle> predicate, double attackBoost, double damageBoost) {
        this(itemId, tagKey, predicate, attackBoost, damageBoost, null, null);
    }

    BaneWeapon(int itemId, String tagKey, Predicate<AttackStyle> predicate, double attackBoost, double damageBoost, HitStageOutgoing otherEffectStage, Consumer<Hit> otherEffects) {
        this.itemId = itemId;
        this.tagKey = tagKey;
        this.predicate = predicate;
        this.attackBoost = attackBoost;
        this.damageBoost = damageBoost;
        this.otherEffectStage = otherEffectStage;
        this.otherEffects = otherEffects;
    }

    static {
        for (BaneWeapon weapon : values()) {
            ItemDef.get(weapon.itemId).addPreTargetDefendListener((player, item, hit, target) -> onHit(hit, target, weapon));
            if (weapon.otherEffectStage == null) continue;
            switch (weapon.otherEffectStage) {
                case PRE_TARGET_DEFEND:
                    ItemDef.get(weapon.itemId).addPreTargetDefendListener((player, item, hit, target) -> onHitOtherEffect(hit, target, weapon));
                    break;
                case POST_TARGET_DEFEND:
                    ItemDef.get(weapon.itemId).addPostTargetDefendListener((player, item, hit, target) -> onHitOtherEffect(hit, target, weapon));
                    break;
                case POST_TARGET_DAMAGE:
                    ItemDef.get(weapon.itemId).addPostTargetDamageListener((player, item, hit, target) -> onHitOtherEffect(hit, target, weapon));
                    break;
            }
        }
    }

    private static void onHit(Hit hit, Entity target, BaneWeapon weapon) {
        if (target == null || target.isPlayer() || !target.npc.getDef().hasCustomValue(weapon.tagKey) || hit.attackStyle == null || !weapon.predicate.test(hit.attackStyle))
            return;
        if (weapon.attackBoost > 0) {
            System.out.println(weapon.name() + " boosting attack by " + weapon.attackBoost);
            hit.boostAttack(weapon.attackBoost);
        }
        if (weapon.damageBoost > 0) {
            System.out.println(weapon.name() + " boosting damage by " + weapon.damageBoost);
            hit.boostDamage(weapon.damageBoost);
        }
    }

    private static void onHitOtherEffect(Hit hit, Entity target, BaneWeapon weapon) {
        if (target == null || target.isPlayer() || !target.npc.getDef().hasCustomValue(weapon.tagKey) || hit.attackStyle == null || !weapon.predicate.test(hit.attackStyle))
            return;
        if (weapon.otherEffects != null) {
            weapon.otherEffects.accept(hit);
        }
    }
}
