package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/23/2023
 */
public enum InstantMovementObject {
    ANCIENT_CAVERN_DRAGON_FORGE_UP(32212, new Position(1744, 5323), new Position(1744, 5321, 1)),
    ANCIENT_CAVERN_DRAGON_FORGE_DOWN(32211, new Position(1744, 5322, 1), new Position(1745, 5325, 0)),

    ELEMENTAL_WORKSHOP_DOWN(3415, new Position(2710, 3497), new Position(2715, 9889, 0)),
    ELEMENTAL_WORKSHOP_UP(3416, new Position(2714, 9887), new Position(2709, 3498, 0)),

    LIGHTHOUSE_BASEMENT_DOWN_1(4543, new Position(2514, 10003), new Position(2515, 10007, 0)),
    LIGHTHOUSE_BASEMENT_DOWN_2(4544, new Position(2515, 10003), new Position(2515, 10007, 0)),
    LIGHTHOUSE_BASEMENT_UP_1(4413, new Position(2515, 10007), new Position(2515, 10002, 0), 828),
    LIGHTHOUSE_BASEMENT_UP_2(4485, new Position(2515, 10006), new Position(2515, 10002, 0), 828),

    YANILLE_DUNGEON_RUBBLE(23564, new Position(2615, 9504), new Position(2616, 9571, 0), 827),
    YANILLE_DUNGEON_SALARIN_UP(15657, new Position(2620, 9562), new Position(2620, 9496, 0)),
    YANILLE_DUNGEON_SALARIN_DOWN(15656, new Position(2620, 9497), new Position(2620, 9565, 0)),
    YANILLE_DUNGEON_NORTH_UP(16665, new Position(2569, 9522), new Position(2570, 3121, 0)),
    YANILLE_DUNGEON_NORTH_DOWN(16664, new Position(2569, 3122), new Position(2570, 9525, 0)),
    YANILLE_DUNGEON_SOUTH_UP(16665, new Position(2603, 9478), new Position(2606, 3079, 0)),
    YANILLE_DUNGEON_SOUTH_DOWN(16664, new Position(2603, 3078), new Position(2602, 9479, 0)),

    // https://oldschool.runescape.wiki/w/Tarn%27s_Lair#/media/File:Tarn's_Lair_map.png
    TARNS_LAIR_A_UP(16132, new Position(3158, 4553), new Position(3158, 4557, 1)),
    TARNS_LAIR_A_DOWN(17098, new Position(3158, 4556, 1), new Position(3158, 4552, 0)),
    TARNS_LAIR_B_UP(16132, new Position(3184, 4553), new Position(3184, 4557, 1)),
    TARNS_LAIR_B_DOWN(17098, new Position(3184, 4556, 1), new Position(3184, 4552, 0)),
    TARNS_LAIR_C_UP(20515, new Position(3178, 4561), new Position(3174, 4561, 1)),
    TARNS_LAIR_C_DOWN(20516, new Position(3175, 4561, 1), new Position(3179, 4561, 0)),
    TARNS_LAIR_D_UP(20517, new Position(3195, 4571), new Position(3195, 4575, 1)),
    TARNS_LAIR_D_DOWN(20518, new Position(3195, 4574, 1), new Position(3195, 4570, 0)),
    TARNS_LAIR_E_UP(20519, new Position(3193, 4577, 1), new Position(3189, 4577, 2)),
    TARNS_LAIR_E_DOWN(20520, new Position(3190, 4577, 2), new Position(3194, 4577, 1)),
    TARNS_LAIR_F_UP(20522, new Position(3186, 4584, 1), new Position(3186, 4578, 2)),
    TARNS_LAIR_F_DOWN(20521, new Position(3186, 4579, 2), new Position(3186, 4585, 1)),
    TARNS_LAIR_G_UP(20511, new Position(3184, 4580), new Position(3184, 4585, 1)),
    TARNS_LAIR_G_DOWN(20512, new Position(3184, 4584, 1), new Position(3184, 4579, 0)),
    TARNS_LAIR_H_UP(18308, new Position(3177, 4577, 1), new Position(3181, 4577, 2)),
    TARNS_LAIR_H_DOWN(19029, new Position(3180, 4577, 2), new Position(3176, 4577, 1)),
    TARNS_LAIR_I_UP(20510, new Position(3184, 4570), new Position(3184, 4566, 1)),
    TARNS_LAIR_I_DOWN(20509, new Position(3184, 4567, 1), new Position(3184, 4571, 0)),
    TARNS_LAIR_J_UP(18308, new Position(3158, 4564, 1), new Position(3158, 4568, 2)),
    TARNS_LAIR_J_DOWN(18973, new Position(3158, 4567, 2), new Position(3158, 4563, 1)),
    TARNS_LAIR_K_UP(16132, new Position(3168, 4569), new Position(3172, 4569, 1)),
    TARNS_LAIR_K_DOWN(17098, new Position(3171, 4569, 1), new Position(3167, 4569, 0)),
    TARNS_LAIR_L_UP(16132, new Position(3147, 4548), new Position(3143, 4548, 1)),
    TARNS_LAIR_L_DOWN(17098, new Position(3144, 4548, 1), new Position(3148, 4548, 0)),
    TARNS_LAIR_M_UP(18308, new Position(3141, 4551, 1), new Position(3141, 4555, 2)),
    TARNS_LAIR_M_DOWN(18973, new Position(3141, 4554, 2), new Position(3141, 4550, 1)),
    TARNS_LAIR_N_UP(20498, new Position(3141, 4564, 1), new Position(3141, 4560, 2)),
    TARNS_LAIR_N_DOWN(20497, new Position(3141, 4561, 2), new Position(3141, 4565, 1)),
    TARNS_LAIR_O_UP(20499, new Position(3144, 4567, 1), new Position(3144, 4571, 2)),
    TARNS_LAIR_O_DOWN(20500, new Position(3144, 4570, 2), new Position(3144, 4566, 1)),
    TARNS_LAIR_P_UP(20502, new Position(3144, 4581, 1), new Position(3144, 4577, 2)),
    TARNS_LAIR_P_DOWN(20501, new Position(3144, 4578, 2), new Position(3144, 4582, 1)),
    TARNS_LAIR_Q_UP(20504, new Position(3150, 4583, 0), new Position(3151, 4583, 0)),
    TARNS_LAIR_Q_DOWN(20503, new Position(3147, 4583, 1), new Position(3146, 4583, 1)),
    TARNS_LAIR_R_UP(20528, new Position(3145, 4593), new Position(3145, 4589, 1)),
    TARNS_LAIR_R_DOWN(20527, new Position(3145, 4590, 1), new Position(3145, 4594, 0)),
    TARNS_LAIR_S_UP(20514, new Position(3171, 4577), new Position(3175, 4577, 1)),
    TARNS_LAIR_S_DOWN(20513, new Position(3174, 4577, 1), new Position(3170, 4577, 0)),
    TARNS_LAIR_T_UP(20525, new Position(3165, 4589), new Position(3161, 4589, 1)),
    TARNS_LAIR_T_DOWN(20526, new Position(3162, 4589, 1), new Position(3166, 4589, 0)),
    TARNS_LAIR_U_UP(16132, new Position(3190, 4598), new Position(3194, 4598, 1)),
    TARNS_LAIR_U_DOWN(17098, new Position(3193, 4598, 1), new Position(3189, 4598, 0)),
    TARNS_LAIR_PASSAGE_11(20533, new Position(3154, 4597, 1), new Position(3151, 4597, 1)),
    TARNS_LAIR_PASSAGE_12(20534, new Position(3152, 4597, 1), new Position(3155, 4597, 1)),
    TARNS_LAIR_PASSAGE_21(20535, new Position(3176, 4598, 1), new Position(3179, 4598, 1)),
    TARNS_LAIR_PASSAGE_22(20536, new Position(3178, 4598, 1), new Position(3175, 4598, 1)),
    TARNS_LAIR_PASSAGE_31(20505, new Position(3162, 4577), new Position(3166, 4577, 0)),
    TARNS_LAIR_PASSAGE_32(20506, new Position(3165, 4577), new Position(3161, 4577, 0)),
    TARNS_LAIR_PASSAGE_41(20523, new Position(3168, 4580), new Position(3168, 4586, 0)),
    TARNS_LAIR_PASSAGE_42(20524, new Position(3168, 4585), new Position(3168, 4579, 0)),
    TARNS_LAIR_PASSAGE_51(20532, new Position(3168, 4593), new Position(3168, 4597, 0)),
    TARNS_LAIR_PASSAGE_52(20531, new Position(3168, 4596), new Position(3168, 4592, 0)),
    TARNS_LAIR_PASSAGE_61(20530, new Position(3159, 4598), new Position(3149, 4598, 0)),
    TARNS_LAIR_PASSAGE_62(20529, new Position(3150, 4598), new Position(3160, 4598, 0)),
    TARNS_LAIR_PASSAGE_71(20492, new Position(3156, 4559), new Position(3161, 4559, 0)),
    TARNS_LAIR_PASSAGE_72(20491, new Position(3160, 4559), new Position(3155, 4559, 0)),
    TARNS_LAIR_PASSAGE_81(20490, new Position(3163, 4561), new Position(3163, 4565, 0)),
    TARNS_LAIR_PASSAGE_82(20489, new Position(3163, 4564), new Position(3163, 4560, 0)),
    TARNS_LAIR_TARN_ENTRANCE(20539, new Position(3185, 4602), new Position(3149, 4644, 0)),
    TARNS_LAIR_TARN_EXIT(15771, new Position(3149, 4643), new Position(3185, 4601, 0)),
    TARNS_LAIR_DIARY_ENTRANCE(16129, new Position(3149, 4659), new Position(3149, 4664, 0)),
    TARNS_LAIR_DIARY_EXIT(16130, new Position(3149, 4663), new Position(3149, 4658, 0)),

    SHAYZIEN_CRYPT_STAIRS_NW_UP(32393, new Position(1494, 9978, 1), new Position(1498, 9978, 2)),
    SHAYZIEN_CRYPT_STAIRS_NW_DOWN(32394, new Position(1496, 9978, 2), new Position(1493, 9978, 1)),
    SHAYZIEN_CRYPT_STAIRS_NE_UP(32393, new Position(1510, 9978, 1), new Position(1509, 9978, 2)),
    SHAYZIEN_CRYPT_STAIRS_NE_DOWN(32394, new Position(1510, 9978, 2), new Position(1514, 9978, 1)),
    SHAYZIEN_CRYPT_STAIRS_S_UP(32393, new Position(1503, 9925, 2), new Position(1503, 9929, 3)),
    SHAYZIEN_CRYPT_STAIRS_S_DOWN(32394, new Position(1503, 9927, 3), new Position(1503, 9924, 2)),
    SHAYZIEN_PRISON_ENTRANCE(41922, new Position(1464, 3568), new Position(1444, 9960)),
    SHAYZIEN_PRISON_EXIT(41923, new Position(1445, 9960), new Position(1466, 3568)),

    KOUREND_CASTLE_STAIRS_ENTRY_BOTTOM_1(11807, new Position(1615, 3665, 0), new Position(1614, 3665, 1)),
    KOUREND_CASTLE_STAIRS_ENTRY_BOTTOM_2(11807, new Position(1615, 3680, 0), new Position(1614, 3681, 1)),
    KOUREND_CASTLE_STAIRS_DOWN_1(11799, new Position(1615, 3665, 1), new Position(1618, 3665, 0)),
    KOUREND_CASTLE_STAIRS_DOWN_2(11799, new Position(1615, 3680, 1), new Position(1618, 3680, 0)),
    KOUREND_CASTLE_STAIRS_NORTH(12536, new Position(1616, 3687, 1), new Position(1616, 3686, 2)),
    KOUREND_CASTLE_STAIRS_NORTH_DOWN(12538, new Position(1616, 3687, 2), new Position(1615, 3687, 1)),
    KOUREND_CASTLE_STAIRS_SOUTH(12536, new Position(1616, 3658, 1), new Position(1617, 3660, 2)),
    KOUREND_CASTLE_STAIRS_SOUTH_DOWN(12538, new Position(1617, 3659, 2), new Position(1618, 3659, 1)),

    WITCHHAVEN_DUNGEON_ENTRANCE(18270, "climb-down", new Position(2696, 3283, 0), new Position(2696, 9683, 0)),
    WITCHHAVEN_DUNGEON_EXIT(18354, "climb-up", new Position(2696, 9682, 0), new Position(2697, 3283, 0)),
    WITCHHAVEN_DUNGEON_WALL(19124, new Position(2701, 9688, 0), new Position(2323, 5104, 0)),
    WITCHHAVEN_DUNGEON_PASSAGE(18412, new Position(2322, 5104, 0), new Position(2700, 9688, 0)),

    TROLL_STRONGHOLD_ENTRACE(3771, "enter", new Position(2839, 3689, 0), new Position(2837, 10090, 2)),
    TROLL_STRONGHOLD_EXIT(3772, "use", new Position(2838, 10091, 2), new Position(2840, 3690, 0)),
    TROLL_STRONGHOLD_EXIT2(3773, "leave", new Position(2838, 10089, 2), new Position(2840, 3690, 0)),
    TROLL_STRONGHOLD_EXIT3(3774, "leave", new Position(2838, 10090, 2), new Position(2840, 3690, 0)),
    TROLL_STRONGHOLD_ENTRANCE2(3762, "open", new Position(2827, 3647, 0), new Position(2824, 10050, 0)),
    TROLL_STRONGHOLD_EXIT4(3761, "open", new Position(2823, 10048, 0), new Position(2827, 3646, 0)),
    TROLL_STRONGHOLD_STAIRS_DOWN1(3789, "climb-down", new Position(2843, 10108, 2), new Position(2841, 10108, 1)),
    TROLL_STRONGHOLD_STAIRS_UP1(3788, "climb-up", new Position(2842, 10108, 1), new Position(2845, 10108, 2)),
    TROLL_STRONGHOLD_STAIRS_DOWN2(3789, "climb-down", new Position(2843, 10051, 2), new Position(2841, 10052, 1)),
    TROLL_STRONGHOLD_STAIRS_UP2(3788, "climb-up", new Position(2842, 10051, 1), new Position(2845, 10052, 2)),
    TROLL_STRONGHOLD_STAIRS_DOWN3(3789, "climb-down", new Position(2852, 10107, 1), new Position(2852, 10105, 0)),
    TROLL_STRONGHOLD_STAIRS_UP3(3788, "climb-up", new Position(2852, 10106, 0), new Position(2852, 10109, 1)),

    DEATH_PLATEAU_PATHWAY_DUNGEON_SOUTH_EXIT(3758, "exit", new Position(2906, 10017, 0), new Position(2904, 3643, 0)),
    DEATH_PLATEAU_PATHWAY_DUNGEON_SOUTH_ENTRANCE(3757, "enter", new Position(2903, 3644, 0), new Position(2907, 10019, 0)),
    DEATH_PLATEAU_PATHWAY_DUNGEON_NORTH_EXIT(3758, "exit", new Position(2906, 10036, 0), new Position(2908, 3654, 0)),
    DEATH_PLATEAU_PATHWAY_DUNGEON_NORTH_ENTRANCE(3757, "enter", new Position(2907, 3652, 0), new Position(2907, 10035, 0));

    private final int id;
    private final String option;
    private final Position objectPos, destinationPos;
    private int emote = -1, optionInt = -1;

    InstantMovementObject(int id, String option, Position objectPos, Position destinationPos) {
        this.id = id;
        this.option = option;
        this.objectPos = objectPos;
        this.destinationPos = destinationPos;
    }

    InstantMovementObject(int id, Position objectPos, Position destinationPos) {
        this(id, 1, objectPos, destinationPos);
    }

    InstantMovementObject(int id, Position objectPos, Position destinationPos, int emote) {
        this(id, 1, objectPos, destinationPos, emote);
    }

    InstantMovementObject(int id, int optionInt, Position objectPos, Position destinationPos) {
        this(id, optionInt, objectPos, destinationPos, -1);
    }

    InstantMovementObject(int id, int optionInt, Position objectPos, Position destinationPos, int emote) {
        this.id = id;
        this.option = "";
        this.optionInt = optionInt;
        this.objectPos = objectPos;
        this.emote = emote;
        this.destinationPos = destinationPos;
    }

    private static void movement(Player player, InstantMovementObject entry) {
        if (entry.emote > -1) {
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(entry.emote);
                e.delay(1);
                player.getMovement().teleport(entry.destinationPos);
                player.unlock();
            });
        } else {
            player.getMovement().teleport(entry.destinationPos);
        }
    }

    static {
        for (InstantMovementObject entry : values()) {
            if (entry.optionInt > -1) {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.optionInt, ((player, obj) -> movement(player, entry)));
            } else {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.option, ((player, obj) -> movement(player, entry)));
            }
        }
    }
}
