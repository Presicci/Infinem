package io.ruin.model.inter.handlers.itemskeptondeath;

import io.ruin.model.combat.Killer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.attributes.AttributeExtensions;
import lombok.Getter;

import java.util.function.BiPredicate;
import java.util.function.IntFunction;

@Getter
public enum IKODChargeable {
    VIGGORAS_CHAINMACE((p, k) -> p.wildernessLevel > 0, 22545, 22542, 21820),
    CRAWS_BOW((p, k) -> p.wildernessLevel > 0, 22550, 22547, 21820),
    THAMMARONS_SCEPTRE((p, k) -> p.wildernessLevel > 0, 22555, 22552, 21820),
    BRACELET_OF_ETHEREUM(21816, 21817, 21820),
    SERPENTINE_HELM(12931, 12929, 12934),
    TOXIC_BLOWPIPE(12926, 12924, 12934),
    TOXIC_STAFF_OF_THE_DEAD(12904, 12902, 12934),
    TOME_OF_FIRE(IKODInterface::isPlayerDeath, 20714, 20716, 20718, i -> Math.max(1, i / 20)),
    FEROCIOUS_GLOVES(IKODInterface::isPlayerDeath, 22981, 22983),
    SARADOMINS_BLESSED_SWORD(12809, 12804),
    ABYSSAL_TENTACLE(12006, 12004),
    DRAGONFIRE_SHIELD(11283, 11284),
    ANCIENT_WYVERN_SHIELD(21633, 21634),
    DRAGONFIRE_WARD(22002, 22003),
    ;

    private final BiPredicate<Player, Killer> condition;
    private final int chargedId, unchargedId, chargeItem;
    private final IntFunction<Integer> chargeMultiplier;

    IKODChargeable(int chargedId, int unchargedId) {
        this(null, chargedId, unchargedId, -1, null);
    }

    IKODChargeable(int chargedId, int unchargedId, int chargeItem) {
        this(null, chargedId, unchargedId, chargeItem, null);
    }

    IKODChargeable(BiPredicate<Player, Killer> condition, int chargedId, int unchargedId) {
        this(condition, chargedId, unchargedId, -1, null);
    }

    IKODChargeable(BiPredicate<Player, Killer> condition, int chargedId, int unchargedId, int chargeItem) {
        this(condition, chargedId, unchargedId, chargeItem, null);
    }

    IKODChargeable(int chargedId, int unchargedId, int chargeItem, IntFunction<Integer> chargeMultiplier) {
        this(null, chargedId, unchargedId, chargeItem, chargeMultiplier);
    }

    IKODChargeable(BiPredicate<Player, Killer> condition, int chargedId, int unchargedId, int chargeItem, IntFunction<Integer> chargeMultiplier) {
        this.condition = condition;
        this.chargedId = chargedId;
        this.unchargedId = unchargedId;
        this.chargeItem = chargeItem;
        this.chargeMultiplier = chargeMultiplier;
    }

    public boolean test(Player player, Killer killer) {
        if (condition == null) return true;
        return condition.test(player, killer);
    }

    public boolean isCharged(Item item) {
        return item.getId() == chargedId;
    }

    public int uncharge(Item item) {
        if (item.getId() != chargedId) {
            System.err.println(name() + ": attempting to uncharge item with id " + item.getId());
            return 0;
        }
        int chargeAmt = AttributeExtensions.getCharges(item);
        item.setId(unchargedId);
        AttributeExtensions.setCharges(item, 0);
        if (chargeMultiplier != null) chargeAmt = chargeMultiplier.apply(chargeAmt);
        return chargeAmt;
    }
}
