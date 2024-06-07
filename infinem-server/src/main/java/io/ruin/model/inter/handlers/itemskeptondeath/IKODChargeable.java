package io.ruin.model.inter.handlers.itemskeptondeath;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.attributes.AttributeExtensions;
import lombok.Getter;

import java.util.HashMap;
import java.util.function.BiPredicate;
import java.util.function.IntFunction;

@Getter
public enum IKODChargeable {
    VIGGORAS_CHAINMACE((p, k) -> p.wildernessLevel > 0, 22545, 22542, 21820),
    CRAWS_BOW((p, k) -> p.wildernessLevel > 0, 22550, 22547, 21820),
    THAMMARONS_SCEPTRE((p, k) -> p.wildernessLevel > 0, 22555, 22552, 21820),
    BRACELET_OF_ETHEREUM(21816, 21817, 21820),
    SERPENTINE_HELM(12931, 12929, 12934),
    TANZANITE_HELM(13197, 13196, 12934),
    MAGMA_HELM(13199, 13198, 12934),
    TOXIC_BLOWPIPE(12926, 12924, 12934),
    TOXIC_STAFF_OF_THE_DEAD(12904, 12902, 12934),
    TOME_OF_FIRE(IKODChargeable::isPlayerDeath, 20714, 20716, 20718, i -> Math.max(1, i / 20)),
    FEROCIOUS_GLOVES(IKODChargeable::isPlayerDeath, 22981, 22983),
    SARADOMINS_BLESSED_SWORD(12809, 12804),
    ABYSSAL_TENTACLE(12006, 12004),
    DRAGONFIRE_SHIELD(11283, 11284),
    ANCIENT_WYVERN_SHIELD(21633, 21634),
    DRAGONFIRE_WARD(22002, 22003),
    RING_OF_SUFFERING_R(IKODChargeable::isPlayerDeath, 20655, 19550),
    RING_OF_SUFFERING_RI(IKODChargeable::isPlayerDeath, 20657, 19550),
    SLAYER_HELM(IKODChargeable::isPlayerDeath, Items.SLAYER_HELMET, Items.BLACK_MASK),
    BLACK_SLAYER_HELM(IKODChargeable::isPlayerDeath, Items.BLACK_SLAYER_HELMET, Items.BLACK_MASK),
    GREEN_SLAYER_HELM(IKODChargeable::isPlayerDeath, Items.GREEN_SLAYER_HELMET, Items.BLACK_MASK),
    RED_SLAYER_HELM(IKODChargeable::isPlayerDeath, Items.RED_SLAYER_HELMET, Items.BLACK_MASK),
    PURPLE_SLAYER_HELM(IKODChargeable::isPlayerDeath, Items.PURPLE_SLAYER_HELMET, Items.BLACK_MASK),
    TURQUOISE_SLAYER_HELM(IKODChargeable::isPlayerDeath, 21888, Items.BLACK_MASK),
    HYDRA_SLAYER_HELM(IKODChargeable::isPlayerDeath, 23073, Items.BLACK_MASK),
    TWISTED_SLAYER_HELM(IKODChargeable::isPlayerDeath, 24370, Items.BLACK_MASK),
    TZTOK_SLAYER_HELM(IKODChargeable::isPlayerDeath, 25898, Items.BLACK_MASK),
    VAMPYRIC_SLAYER_HELM(IKODChargeable::isPlayerDeath, 25904, Items.BLACK_MASK),
    TZKAL_SLAYER_HELM(IKODChargeable::isPlayerDeath, 25910, Items.BLACK_MASK),
    TRIDENT_OF_THE_SEAS(IKODChargeable::isPlayerDeath, Items.TRIDENT_OF_THE_SEAS, Items.UNCHARGED_TRIDENT),
    TRIDENT_OF_THE_SEAS_E(IKODChargeable::isPlayerDeath, 22288, 22290),
    TRIDENT_OF_THE_SWAMP(IKODChargeable::isPlayerDeath, Items.TRIDENT_OF_THE_SWAMP, Items.UNCHARGED_TOXIC_TRIDENT),
    TRIDENT_OF_THE_SWAMP_E(IKODChargeable::isPlayerDeath, 22292, 22294),
    ;

    private final BiPredicate<Player, Boolean> condition;
    private final int chargedId, unchargedId, chargeItem;
    private final IntFunction<Integer> chargeMultiplier;

    IKODChargeable(int chargedId, int unchargedId) {
        this(null, chargedId, unchargedId, -1, null);
    }

    IKODChargeable(int chargedId, int unchargedId, int chargeItem) {
        this(null, chargedId, unchargedId, chargeItem, null);
    }

    IKODChargeable(BiPredicate<Player, Boolean> condition, int chargedId, int unchargedId) {
        this(condition, chargedId, unchargedId, -1, null);
    }

    IKODChargeable(BiPredicate<Player, Boolean> condition, int chargedId, int unchargedId, int chargeItem) {
        this(condition, chargedId, unchargedId, chargeItem, null);
    }

    IKODChargeable(int chargedId, int unchargedId, int chargeItem, IntFunction<Integer> chargeMultiplier) {
        this(null, chargedId, unchargedId, chargeItem, chargeMultiplier);
    }

    IKODChargeable(BiPredicate<Player, Boolean> condition, int chargedId, int unchargedId, int chargeItem, IntFunction<Integer> chargeMultiplier) {
        this.condition = condition;
        this.chargedId = chargedId;
        this.unchargedId = unchargedId;
        this.chargeItem = chargeItem;
        this.chargeMultiplier = chargeMultiplier;
    }

    public boolean test(Player player, boolean playerKill) {
        if (condition == null) return true;
        return condition.test(player, playerKill);
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

    private static boolean isPlayerDeath(Player player, boolean playerKill) {
        if (player == null) return playerKill;
        return (player.wildernessLevel > 0 || player.pvpAttackZone) && playerKill;
    }

    protected static final HashMap<Integer, IKODChargeable> CHARGEABLES = new HashMap<>();

    protected static IKODChargeable getChargeable(int itemId) {
        if (!isChargeable(itemId)) return null;
        return CHARGEABLES.get(itemId);
    }

    protected static boolean isChargeable(int itemId) {
        return CHARGEABLES.containsKey(itemId);
    }

    static {
        for (IKODChargeable chargeable : values()) {
            CHARGEABLES.put(chargeable.chargedId, chargeable);
        }
    }
}
