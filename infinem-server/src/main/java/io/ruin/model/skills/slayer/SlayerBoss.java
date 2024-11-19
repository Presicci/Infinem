package io.ruin.model.skills.slayer;

import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public enum SlayerBoss {
    KREE_ARRA((p) -> p.getStats().get(StatType.Ranged).fixedLevel >= 70, 3162, 6492, 12443),
    COMMANDER_ZILYANA((p) -> p.getStats().get(StatType.Agility).fixedLevel >= 70, 2205, 6493, 12445),
    GENERAL_GRAARDOR((p) -> p.getStats().get(StatType.Strength).fixedLevel >= 70, 2215, 6494, 12444),
    KRIL_TSUTSAROTH((p) -> p.getStats().get(StatType.Hitpoints).fixedLevel >= 70, 3129, 6495, 12446),
    DAGANNOTH_KINGS(2265, 2266, 2267, 6496, 6497, 6498, 12439, 12441, 12442),
    THE_GIANT_MOLE(5779, 6499),
    THE_KALPHITE_QUEEN(963, 965, 4303, 4304, 6500, 6501),
    THE_KING_BLACK_DRAGON(239, 6502, 12440),
    CALLISTO(6503, 6609),
    VENENATIS(6504, 6610),
    VET_ION(6611, 6612),
    THE_CHAOS_ELEMENTAL(2054, 6505),
    THE_CHAOS_FANATIC(6619),
    CRAZY_ARCHAEOLOGIST(6618),
    SCORPIA(6615),
    ZULRAH(2042, 2043, 2044),
    BARROWS_BROTHERS(1672, 1673, 1674, 1675, 1676, 1677),
    THE_CAVE_KRAKEN((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 87, 492),
    THE_THERMONUCLEAR_SMOKE_DEVIL((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 93, 499),
    CERBERUS((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 91, 5862),
    THE_ABYSSAL_SIRE((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 85, 5886, 5887, 5888, 5889, 5890, 5891, 5908),    // 21
    THE_GROTESQUE_GUARDIANS((p) -> false),//p.getStats().get(StatType.Slayer).fixedLevel >= 75), // TODO Roof unlock too
    VORKATH(8060, 8061),
    THE_ALCHEMICAL_HYDRA((p) -> p.getStats().get(StatType.Slayer).fixedLevel >= 95, 8615, 8616, 8617, 8618, 8619, 8620, 8621, 8622),
    SARACHNIS(8713);
    // the Phantom Muspah
    // Vardorvis
    // Duke Sucellus
    // the Whisperer
    // the Leviathan
    // Araxxor

    public final int[] ids;
    public final Function<Player, Boolean> canAssign;

    SlayerBoss(int... ids) {
        this.ids = ids;
        this.canAssign = null;
    }

    SlayerBoss(Function<Player, Boolean> canAssign, int... ids) {
        this.ids = ids;
        this.canAssign = canAssign;
    }

    private static Map<Integer, SlayerBoss> lookup = null;

    /**
     * Gets a slayer creature by uid.
     *
     * @param uid The uid of the creature.
     * @return The SlayerCreature.
     */
    public static SlayerBoss lookup(int uid) {
        if (lookup == null) {
            Map<Integer, SlayerBoss> temp = new HashMap<>();
            for (SlayerBoss boss : values()) {
                for (int id : boss.ids) {
                    temp.put(id, boss);
                }
            }
            lookup = temp;
        }
        return lookup.get(uid);
    }
}
