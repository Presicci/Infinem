package io.ruin.model.skills.slayer;

import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public enum SlayerBoss {
    KREE_ARRA((p) -> p.getStats().get(StatType.Ranged).fixedLevel >= 70),
    COMMANDER_ZILYANA((p) -> p.getStats().get(StatType.Agility).fixedLevel >= 70),
    GENERAL_GRAARDOR((p) -> p.getStats().get(StatType.Strength).fixedLevel >= 70),
    KRIL_TSUTSAROTH((p) -> p.getStats().get(StatType.Hitpoints).fixedLevel >= 70),
    DAGANNOTH_KINGS,
    THE_GIANT_MOLE,
    THE_KALPHITE_QUEEN,
    THE_KING_BLACK_DRAGON,
    CALLISTO,
    VENENATIS,
    VET_ION,
    THE_CHAOS_ELEMENTAL,
    THE_CHAOS_FANATIC,
    CRAZY_ARCHAEOLOGIST,
    SCORPIA,
    ZULRAH,
    BARROWS_BROTHERS,
    THE_CAVE_KRAKEN((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 87),
    THE_THERMONUCLEAR_SMOKE_DEVIL((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 93),
    CERBERUS((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 91),
    THE_ABYSSAL_SIRE((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 85),    // 21
    THE_GROTESQUE_GUARDIANS((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 75), // TODO Roof unlock too
    VORKATH,
    THE_ALCHEMICAL_HYDRA((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 95),
    SARACHNIS;

    public final int[] ids;
    public final Function<Player, Boolean> canAssign;

    SlayerBoss(int... ids){
        this.ids = ids;
        this.canAssign = null;
    }

    SlayerBoss(Function<Player, Boolean> canAssign, int... ids){
        this.ids = ids;
        this.canAssign = canAssign;
    }
}
