package io.ruin.model.skills.construction.actions;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.MaxCapeVariants;
import io.ruin.model.stat.StatType;

import java.util.Arrays;

public enum Costume {
    //fancy dress box
    MIME_COSTUME(3057, 3058, 3059, 3060, 3061),
    FROG_COSTUME(single(6188), multiple(6184,6186), multiple(6185, 6187)),
    ZOMBIE_OUTFIT(7594, 7592, 7593, 7595, 7596),
    CAMO_OUTFIT(6656, 6654, 6655),
    LEDERHOSEN_OUTFIT(6180, 6181, 6182),
    SHADE_ROBES(546, 548),
    STALE_BAGUETTE(Items.STALE_BAGUETTE),
    BEEKEEPER_OUTFIT(25129, 25131, 25133, 25135, 25137),

    //armour case
    CASTLE_WARS_DECORATIVE_ARMOUR_1(4071, 25165, 4069, 4070, 4072, 25163),
    CASTLE_WARS_DECORATIVE_ARMOUR_2(4506, 25169, 4504, 4505, 4507, 25167),
    CASTLE_WARS_DECORATIVE_ARMOUR_3(4511, 25174, 4509, 4510, 4512, 25171),
    CASTLE_WARS_MAGE_ARMOUR(24163, 24164, 24165),
    CASTLE_WARS_RANGER_ARMOUR(24166, 24167, 24168),
    VOID_KNIGHT_ARMOUR(8839, 8840, 8842),
    ELITE_VOID_ARMOUR(13072, 13073, 8842),
    VOID_MELEE_HELM(11665),
    VOID_RANGER_HELM(11664),
    VOID_MAGE_HELM(11663),
    ROGUE_ARMOUR(5553, 5554, 5555, 5556, 5557),
    SPINED_ARMOUR(6131, 6133, 6135, 6143, 6149),
    ROCKSHELL_ARMOUR(6128, 6129, 6130, 6151, 6145),
    TRIBAL_MASK_POISON(6335),
    TRIBAL_MASK_DISEASE(6337),
    TRIBAL_MASK_COMBAT(6339),
    WHITE_KNIGHT_ARMOUR(single(6623), single(6617), multiple(6625, 6627), single(6629), single(6619), single(6633)),
    INITIATE_ARMOUR(5574, 5575, 5576),
    PROSELYTE_ARMOUR(single(9672), single(9674), multiple(9676, 9778)),
    MOURNER_GEAR(1506, 6065, 6067, 6068, 6069, 6070),
    GRAAHK_GEAR(10051, 10049, 10047),
    LARUPIA_GEAR(10045, 10043, 10041),
    KYATT_GEAR(10039, 10037, 10035),
    POLAR_CAMO(10065, 10067),
    JUNGLE_CAMO(10057, 10059),
    WOODLAND_CAMO(10053, 10055),
    DESERT_CAMO(10061, 10063),
    BUILDERS_COSTUME(10862, 10863, 10864, 10865),
    LUMBERJACK_COSTUME(10941, 10940, 10939, 10933),
    BOMBER_JACKET_COSTUME(single(9944), multiple(9945, 9946)),
    HAM_ROBES(4302, 4300, 4298, 4308, 4310, 4304, 4306),
    PROSPECTOR_KIT(12013, 12014, 12015, 12016),
    ANGLERS_OUTFIT(13258, 13259, 13260, 13261),
    SHAYZIEN_ARMOUR_1(13357, 13358, 13359, 13360, 13361),
    SHAYZIEN_ARMOUR_2(13362, 13363, 13364, 13365, 13366),
    SHAYZIEN_ARMOUR_3(13367, 13368, 13369, 13370, 13371),
    SHAYZIEN_ARMOUR_4(13372, 13373, 13374, 13375, 13376),
    SHAYZIEN_ARMOUR_5(13377, 13378, 13379, 13380, 13381),
    XERICIAN_ROBES(13385, 13387, 13389),
    FARMERS_OUTFIT(multiple(13640, 13641), multiple(13642, 13643), multiple(13644, 13645), multiple(13646, 13647)),
    CLUE_HUNTER_OUTFIT(19689, 19691, 19693, 19695, 19697),
    CORRUPTED_ARMOUR(single(20838), single(20840), multiple(20842, 20844), single(20846)),
    OBSIDIAN_ARMOUR(21298, 21301, 21304),
    HELM_OF_RAEDWALD(19687),
    FIGHTER_TORSO(10551),
    PENANCE_BOOTS(10552),
    PENANCE_GLOVES(10553),
    PENANCE_SKIRT(10555),
    PENANACE_FIGHTER_HAT(10548),
    PENANCE_RANGER_HAT(10550),
    PENANCE_RUNNER_HAT(10549),
    PENANCE_HEALER_HAT(10547),
    JUSTICIAR_ARMOUR(22326, 22327, 22328),
    ORNATE_ARMOUR(23101, 23097, 23095, 23099, 23091, 23093),
    DRAGONSTONE_ARMOUR(24034, 24037, 24040, 24046, 24043),
    ARDOUGNE_KNIGHT_ARMOUR(single(23785), single(23787), multiple(23789, Items.STEEL_PLATELEGS)),
    DEADMAN_ARMOUR(Items.DEADMANS_CHEST, Items.DEADMANS_LEGS, Items.DEADMANS_CAPE),
    HALO_SARADOMIN(Items.SARADOMIN_HALO),
    HALO_ZAMORAK(Items.ZAMORAK_HALO),
    HALO_GUTHIX(Items.GUTHIX_HALO),
    HALO_ARMADYL(24192),
    HALO_BANDOS(24195),
    HALO_SEREN(24198),
    HALO_ANCIENT(24201),
    HALO_BRASSICA(24204),
    TWISTED_RELICHUNTER_T1(24405, 24407, 24409, 24411),
    TWISTED_RELICHUNTER_T2(24397, 24399, 24401, 24403),
    TWISTED_RELICHUNTER_T3(24387, 24389, 24391, 24393),
    CANE_TWISTED(24395),
    TRAILBLAZER_RELICHUNTER_T1(25028, 25031, 25034, 25037),
    TRAILBLAZER_RELICHUNTER_T2(25016, 25019, 25022, 25025),
    TRAILBLAZER_RELICHUNTER_T3(25001, 25004, 25007, 25010),
    CANE_TRAILBLAZER(25013),
    INQUISITOR_ARMOUR(24419, 24420, 24421),
    VYRE_NOBLE_OUTFIT(24676, 24678, 24680),
    SNAKESKIN_ARMOUR(Items.SNAKESKIN_BANDANA, Items.SNAKESKIN_BODY, Items.SNAKESKIN_CHAPS, Items.SNAKESKIN_VAMBRACES, Items.SNAKESKIN_BOOTS),
    CASTLE_WARS_DECORATIVE_SWORD_1(Items.DECORATIVE_SWORD),
    CASTLE_WARS_DECORATIVE_SWORD_2(Items.DECORATIVE_SWORD_2),
    CASTLE_WARS_DECORATIVE_SWORD_3(Items.DECORATIVE_SWORD_3),
    FEDORA(Items.FEDORA),
    SWIFT_BLADE(24219),
    PROSPECTOR_KIT_GOLDEN(25549, 25551, 25553, 25555),
    ANGLER_OUTFIT_SPIRIT(25592, 25594, 25596, 25598),

    //magic wardrobe
    BLUE_MYSTIC (4089, 4091, 4093, 4095, 4097),
    DARK_MYSTIC (4099, 4101, 4103, 4105, 4107),
    LIGHT_MYSTIC(4109, 4111, 4113, 4115, 4117),
    DUSK_MYSTIC(23047, 23050, 23053, 23056, 23059),
    SKELETAL(6137, 6139, 6141, 6147, 6153),
    INFINITY(6916, 6918, 6920, 6922, 6924),
    INFINITY_LIGHT(12419, 12420, 12421, 6922, 6920),
    INFINITY_DARK(12457, 12458, 12459, 6922, 6920),
    SPLITBARK(3385, 3387, 3389, 3391, 3393),
    SWAMPBARK(25398, 25389, 25401, 25392, 25395),
    BLOODBARK(25413, 25404, 25416, 25407, 25410),
    GHOSTLY(6106, 6107, 6108, 6109, 6110, 6111),
    MOONCLAN_ROBES(9069, 9068, 9070, 9071, 9072, 9073, 9074),
    LUNAR_ROBES(9096, 9097, 9098, 9099, 9100, 9102, 9104, 9101),
    DAGONHAI_ROBES(24288, 24291, 24294),
    GRACEFUL(11850, 11854, 11856, 11858, 11860, 11852),
    GRACEFUL_ARCEUUS(13579, 13581, 13583, 13585, 13587, 13589),
    GRACEFUL_HOSIDIUS(13627, 13629, 13631, 13633, 13635, 13637),
    GRACEFUL_LOVAKENGJ(13603, 13605, 13607, 13609, 13611, 13613),
    GRACEFUL_PISCARILIUS(13591, 13593, 13595, 13597, 13599, 13601),
    GRACEFUL_SHAYZIEN(13615, 13617, 13619, 13621, 13623, 13625),
    GRACEFUL_KOUREND(13667, 13669, 13671, 13673, 13675, 13677),
    GRACEFUL_AGILITY_ARENA(21061, 21064, 21067, 21070, 21073, 21076),
    GRACEFUL_HALLOWED(24743, 24749, 24752, 24746, 24755, 24758),
    GRACEFUL_TRAILBLAZER(25069, 25075, 25078, 25072, 25081, 25084),
    BLUE_NAVAL(8959, 8952, 8991),
    GREEN_NAVAL(8960, 8953, 8992),
    RED_NAVAL(8961, 8954, 8993),
    BROWN_NAVAL(8962, 8955, 8994),
    BLACK_NAVAL(8963, 8956, 8995),
    PURPLE_NAVAL(8964, 8957, 8996),
    GREY_NAVAL(8965, 8958, 8997),
    ELDER_CHAOS_DRUID(20517, 20520, 20595),
    EVIL_CHICKEN_COSTUME(20433, 20436, 20439, 20442),
    PYROMANCER_OUTFIT(20704, 20706, 20708, 20710),
    GOLDEN_TENCH(22840),
    PEARL_FISHING_ROD(22846),
    PEARL_BARBARIAN_ROD(22842),
    PEARL_FLY_FISHING_ROD(22844),
    OILY_PEARL_FISHING_ROD(23122),
    FISH_SACK(multiple(22838, 25585)),
    MUDSKIPPER_KIT(6665, 6666),
    DARK_FLIPPERS(25557),
    BOUNTY_HUNTER_HAT_1(24338),
    BOUNTY_HUNTER_HAT_2(24340),
    BOUNTY_HUNTER_HAT_3(24342),
    BOUNTY_HUNTER_HAT_4(24344),
    BOUNTY_HUNTER_HAT_5(24346),
    BOUNTY_HUNTER_HAT_6(24348),
    ANCESTRAL_ROBES(21018, 21021, 21024),
    ANCESTRAL_ROBES_TWISTED(24664, 24666, 24668),
    FLAG_CUTTHROAT(Items.CUTTHROAT_FLAG),
    FLAG_GILDED_SMILE(Items.GUILDED_SMILE_FLAG),
    FLAG_BRONZE_FIST(Items.BRONZE_FIST_FLAG),
    FLAG_LUCKY_SHOT(Items.LUCKY_SHOT_FLAG),
    FLAG_TREASURE(Items.TREASURE_FLAG),
    FLAG_PHASMATYS(Items.PHASMATYS_FLAG),
    CARPENTERS_OUTFIT(24872, 24874, 24876, 24878),
    AMYS_SAW(24880),
    WARM_GLOVES(20712),
    TRIBAL_OUTFIT_BROWN(6345, 6341, 6343, 6347, 6349),
    TRIBAL_OUTFIT_BLUE(6355, 6351, 6353, 6359, 6357),
    TRIBAL_OUTFIT_YELLOW(6365, 6361, 6363, 6369, 6367),
    TRIBAL_OUTFIT_PINK(6375, 6371, 6373, 6379, 6377),
    BRUMA_TORCH(Items.BRUMA_TORCH),
    ZEALOT_PRAYER_ROBES(25438, 25434, 25436, 25440),
    IMCANDO_HAMMER(25644),

    //cape rack
    CAPE_OF_LEGENDS(1052),
    OBSIDIAN_CAPE(6568),
    FIRE_CAPE(6570),
    WILDERNESS_CAPES(
            multiple(4315,4317,4319,4321,4323,4325,4327,4329,4331,4333,4335,4337,4339,4341,4343,4345,4347,4349,4351,4353,4355,4357,4359,4361,4363,4365,4367,4369,4371,4373,4375,4377,4379,4381,4383,4385,4387,4389,4391,4393,4395,4397,4399,4401,4403,4405,4407,4409,4411,4413)
    ),
    WILDERNESS_CAPE_0(20211),
    WILDERNESS_CAPE_X(20214),
    WILDERNESS_CAPE_I(20217),
    SARADOMIN_CAPE(2412),
    GUTHIX_CAPE(2413),
    ZAMORAK_CAPE(2414),
    ATTACK_CAPE(StatType.Attack),
    DEFENCE_CAPE(StatType.Defence),
    STRENGTH_CAPE(StatType.Strength),
    HITPOINTS_CAPE(StatType.Hitpoints),
    AGILITY_CAPE(StatType.Agility),
    COOKING_CAPE(StatType.Cooking),
    CONSTRUCTION_CAPE(StatType.Construction),
    CRAFTING_CAPE(StatType.Crafting),
    FARMING_CAPE(StatType.Farming),
    FIREMAKING_CAPE(StatType.Firemaking),
    FISHING_CAPE(StatType.Fishing),
    FLETCHING_CAPE(StatType.Fletching),
    HERBLORE_CAPE(StatType.Herblore),
    MAGIC_CAPE(StatType.Magic),
    MINING_CAPE(StatType.Mining),
    PRAYER_CAPE(StatType.Prayer),
    RANGED_CAPE(StatType.Ranged),
    RUNECRAFTING_CAPE(StatType.Runecrafting),
    SLAYER_CAPE(StatType.Slayer),
    SMITHING_CAPE(StatType.Smithing),
    THIEVING_CAPE(StatType.Thieving),
    WOODCUTTING_CAPE(StatType.Woodcutting),
    HUNTER_CAPE(StatType.Hunter),
    QUEST_CAPE(single(9814), multiple(9813, 13068)),
    ACHIEVEMENT_CAPE(single(13070), multiple(13069, 19476)),
    MUSIC_CAPE(single(13223), multiple(13221, 13222)),
    SPOTTED_CAPE(10069),
    SPOTTIER_CAPE(10071),
    MAX_CAPE(MaxCapeVariants.MaxCapes.INFERNAL),
    INFERNAL_CAPE(21295),
    CHAMPIONS_CAPE(21439),
    IMBUED_SARADOMIN_CAPE(21791),
    IMBUED_GUTHIX_CAPE(21793),
    IMBUED_ZAMORAK_CAPE(21795),
    MYTHICAL_CAPE(22114),
    XERICS_GUARD(22388),
    XERICS_WARRIOR(22390),
    XERICS_SENTINEL(22392),
    XERICS_GENERAL(22394),
    XERICS_CHAMPION(22396),
    SINHAZA_SHROUD_1(22494),
    SINHAZA_SHROUD_2(22496),
    SINHAZA_SHROUD_3(22498),
    SINHAZA_SHROUD_4(22500),
    SINHAZA_SHROUD_5(22502),
    GAUNTLET_CAPE(23859),
    VICTOR_CAPE_1(24207),
    VICTOR_CAPE_10(24209),
    VICTOR_CAPE_50(24211),
    VICTOR_CAPE_100(24213),
    VICTOR_CAPE_500(24215),
    VICTOR_CAPE_1000(24520),
    SOUL_CAPE(multiple(25344, 25346)),

    // Beginner trasure trails
    MOLE_SLIPPERS(23285),
    FROG_SLIPPERS(23288),
    BEAR_FEET(23291),
    DEMON_FEET(23294),
    JESTER_CAPE(23297),
    SHOULDER_PARROT(23300),
    MONK_ROBES_TRIM(23303, 23306),
    AMULET_OF_DEFENCE_TRIM(23309),
    SANDWICH_LADY_COSTUME(23312, 23315, 23318),
    RUNE_SCIMITAR_GUTHIX(23330),
    RUNE_SCIMITAR_ZAMORAK(23334),
    RUNE_SCIMITAR_SARADOMIN(23332),

    //Easy treasure trails
    BLACK_HERALD_SHIELD_1(7332),
    BLACK_HERALD_SHIELD_2(7338),
    BLACK_HERALD_SHIELD_3(7344),
    BLACK_HERALD_SHIELD_4(7350),
    BLACK_HERALD_SHIELD_5(7356),
    GOLD_STUDDED_LEATHER(7362, 7366),
    TRIMMED_STUDDED_LEATHER(7364, 7368),
    GOLD_WIZARD_ROBES(7394, 7390, 7386),
    TRIMMED_WIZARD_ROBES(7396, 7392, 7388),
    TRIMMED_BLACK_ARMOUR(single(2587), single(2583), multiple(2585, 3472), single(2589)),
    GOLD_BLACK_ARMOUR(single(2595), single(2591), multiple(2593, 3473), single(2597)),
    HIGHWAYMAN_MASK(2631),
    BLUE_BERET(2633),
    BLACK_BERET(2635),
    WHITE_BERET(2637),
    BLACK_HERALD_HELM_1(10306),
    BLACK_HERALD_HELM_2(10308),
    BLACK_HERALD_HELM_3(10310),
    BLACK_HERALD_HELM_4(10312),
    BLACK_HERALD_HELM_5(10314),
    TRIMMED_AMULET_OF_MAGIC(10366),
    PANTALOONS(10396),
    WIG(10392),
    FLARED_TROUSERS(10394),
    SLEEPING_CAP(10398),
    BOBS_RED_SHIRT(10316),
    BOBS_BLUE_SHIRT(10318),
    BOBS_GREEN_SHIRT(10320),
    BOBS_BLACK_SHIRT(10322),
    BOBS_PURPLE_SHIRT(10324),
    RED_ELEGANT(multiple(10404, 10424), multiple(10406, 10426)),
    GREEN_ELEGANT(multiple(10412, 10432), multiple(10414, 10434)),
    BLUE_ELEGANT(multiple(10408, 10428), multiple(10410, 10430)),
    BERET_MASK(11282),
    TRIMMED_BRONZE(single(Items.BRONZE_FULL_HELM_T), single(Items.BRONZE_PLATEBODY_T),
            multiple(Items.BRONZE_PLATELEGS_T, Items.BRONZE_PLATESKIRT_T), single(Items.BRONZE_KITESHIELD_T)),
    GOLD_BRONZE(single(Items.BRONZE_FULL_HELM_G), single(Items.BRONZE_PLATEBODY_G),
            multiple(Items.BRONZE_PLATELEGS_G, Items.BRONZE_PLATESKIRT_G), single(Items.BRONZE_KITESHIELD_G)),
    TRIMMED_IRON(single(Items.IRON_FULL_HELM_T), single(Items.IRON_PLATEBODY_T),
            multiple(Items.IRON_PLATELEGS_T, Items.IRON_PLATESKIRT_T), single(Items.IRON_KITESHIELD_T)),
    GOLD_IRON(single(Items.IRON_FULL_HELM_G), single(Items.IRON_PLATEBODY_G),
            multiple(Items.IRON_PLATELEGS_G, Items.IRON_PLATESKIRT_G), single(Items.IRON_KITESHIELD_G)),
    TRIMMED_STEEL(single(Items.STEEL_FULL_HELM_T), single(Items.STEEL_PLATEBODY_T),
            multiple(Items.STEEL_PLATELEGS_T, Items.STEEL_PLATESKIRT_T), single(Items.STEEL_KITESHIELD_T)),
    GOLD_STEEL(single(Items.STEEL_FULL_HELM_G), single(Items.STEEL_PLATEBODY_G),
            multiple(Items.STEEL_PLATELEGS_G, Items.STEEL_PLATESKIRT_G), single(Items.STEEL_KITESHIELD_G)),
    BEANIE(12245),
    RED_BERET(12247),
    IMP_MASK(12249),
    GOBLIN_MASK(12251),
    BLACK_CANE(12375),
    BLACK_PICKAXE(12297),
    BLACK_WIZARD_GOLD(12453, 12449, 12445),
    BLACK_WIZARD_TRIM(12455, 12451, 12447),
    LARGE_SPADE(20164),
    WOODEN_SHIELD_G(20166),
    GOLDEN_CHEFS_HAT(20205),
    GOLDEN_APRON(20208),
    MONK_ROBES_GOLD(20199, 20202),
    CAPE_OF_SKULLS(23351),
    AMULET_OF_POWER_TRIMMED(23354),
    RAIN_BOW(23357),
    HAM_JOINT(23360),
    LEATHER_ARMOUR_GOLD(23381, 23384),
    STAFF_OF_BOB_THE_CAT(23363),
    BLACK_PLATEBODY_H1(23366),
    BLACK_PLATEBODY_H2(23369),
    BLACK_PLATEBODY_H3(23372),
    BLACK_PLATEBODY_H4(23375),
    BLACK_PLATEBODY_H5(23378),
    BLESSING_PEACEFUL(20226),
    BLESSING_HOLY(20220),
    BLESSING_UNHOLY(20223),
    BLESSING_HONOURABLE(20229),
    BLESSING_WAR(20232),
    BLESSING_ANCIENT(20235),
    VESTMENTS_ARMADYL(Items.ARMADYL_MITRE, Items.ARMADYL_ROBE_TOP, Items.ARMADYL_ROBE_LEGS, Items.ARMADYL_CLOAK,
            Items.ARMADYL_STOLE, Items.ARMADYL_CROZIER),
    VESTMENTS_ANCIENT(Items.ANCIENT_MITRE, Items.ANCIENT_ROBE_TOP, Items.ANCIENT_ROBE_LEGS, Items.ANCIENT_CLOAK,
            Items.ANCIENT_STOLE, Items.ANCIENT_CROZIER),
    VESTMENTS_BANDOS(Items.BANDOS_MITRE, Items.BANDOS_ROBE_TOP, Items.BANDOS_ROBE_LEGS, Items.BANDOS_CLOAK,
            Items.BANDOS_STOLE, Items.BANDOS_CROZIER),
    VESTMENTS_SARADOMIN(Items.SARADOMIN_MITRE, Items.SARADOMIN_ROBE_TOP, Items.SARADOMIN_ROBE_LEGS, Items.SARADOMIN_CLOAK,
            Items.SARADOMIN_STOLE, Items.SARADOMIN_CROZIER),
    VESTMENTS_GUTHIX(Items.GUTHIX_MITRE, Items.GUTHIX_ROBE_TOP, Items.GUTHIX_ROBE_LEGS, Items.GUTHIX_CLOAK,
            Items.GUTHIX_STOLE, Items.GUTHIX_CROZIER),
    VESTMENTS_ZAMORAK(Items.ZAMORAK_MITRE, Items.ZAMORAK_ROBE_TOP, Items.ZAMORAK_ROBE_LEGS, Items.ZAMORAK_CLOAK,
            Items.ZAMORAK_STOLE, Items.ZAMORAK_CROZIER),
    WILLOW_COMPOSITE_BOW(Items.WILLOW_COMP_BOW),

    //Medium treasure trails
    RED_STRAW_BOATER(7319),
    ORANGE_STRAW_BOATER(7321),
    GREEN_STRAW_BOATER(7323),
    BLUE_STRAW_BOATER(7325),
    BLACK_STRAW_BOATER(7327),
    ADAMANT_HERALDIC_KITESHIELD_1(7334),
    ADAMANT_HERALDIC_KITESHIELD_2(7340),
    ADAMANT_HERALDIC_KITESHIELD_3(7346),
    ADAMANT_HERALDIC_KITESHIELD_4(7352),
    ADAMANT_HERALDIC_KITESHIELD_5(7358),
    GREEN_DRAGONHIDE_GOLD(7370, 7378),
    GREEN_DRAGONHIDE_TRIM(7372, 7380),
    RANGER_BOOTS(2577),
    TRIMMED_ADAMANTITE_ARMOUR(single(2605), single(2599), multiple(2601, 3474), single(2603)),
    GOLD_TRIMMED_ADAMANTITE_ARMOUR(single(2613), single(2607), multiple(2609, 3482), single(2611)),
    RED_HEADBAND(2645),
    BLACK_HEADBAND(2647),
    BROWN_HEADBAND(2649),
    ADAMANT_HERALDIC_HELM_1(10296),
    ADAMANT_HERALDIC_HELM_2(10298),
    ADAMANT_HERALDIC_HELM_3(10300),
    ADAMANT_HERALDIC_HELM_4(10302),
    ADAMANT_HERALDIC_HELM_5(10304),
    TRIMMED_AMULET_OF_STRENGTH(10364),
    ELEGANT_CLOTHING_BLACK_WHITE(multiple(10400, 10420), multiple(10402, 10422)),
    ELEGANT_CLOTHING_PURPLE(multiple(10416, 10436), multiple(10418, 10438)),
    ELEGANT_CLOTHING_PINK(multiple(12315, 12339), multiple(12317, 12341)),
    ELEGANT_CLOTHING_GOLD(multiple(12343, 12347), multiple(12345, 12349)),
    WIZARD_BOOTS(2579),
    MITHRIL_ARMOUR_GOLD(single(12283), single(12277), multiple(12279, 12285), single(12281)),
    MITHRIL_ARMOUR_TRIM(single(12293), single(12287), multiple(12289, 12295), single(12291)),
    LEPRECHAUN_HAT(12359),
    BLACK_LEPRECHAUN_HAT(20246),
    WHITE_HEADBAND(12299),
    BLUE_HEADBAND(12301),
    GOLD_HEADBAND(12303),
    PINK_HEADBAND(12305),
    GREEN_HEADBAND(12307),
    PINK_BOATER(12309),
    PURPLE_BOATER(12311),
    WHITE_BOATER(12313),
    CAT_MASK(12361),
    PENGUIN_MASK(12428),
    BLACK_UNICORN_MASK(20266),
    WHITE_UNICORN_MASK(20269),
    TOWN_CRIER(12319, 20240, 20243),
    ARMADYL_VESTMENTS(12259, 12253, 12255, 12261, 12263, 12257),
    ANCIENT_VESTMENTS(12203, 12193, 12195, 12197, 12199, 12201),
    BANDOS_VESTMENTS(12271, 12265, 12267, 12273, 12275, 12269),
    ADAMANT_CANE(12377),
    HOLY_SANDALS(12598),
    CLUELESS_SCROLL(20249),
    ARCEUUS_BANNER(20251),
    HOSIDIUS_BANNER(20254),
    LOVAKENGJ_BANNER(20257),
    PISCARILIUS_BANNER(20260),
    SHAYZIEN_BANNER(20263),
    CABBAGE_ROUND_SHIELD(20272),

    //Hard treasure trails
    RUNE_HERALDIC_KITESHIELD_1(7336),
    RUNE_HERALDIC_KITESHIELD_2(7342),
    RUNE_HERALDIC_KITESHIELD_3(7348),
    RUNE_HERALDIC_KITESHIELD_4(7354),
    RUNE_HERALDIC_KITESHIELD_5(7360),
    BLUE_DRAGONHIDE_GOLD(7374, 7382),
    BLUE_DRAGONHIDE_TRIM(7376, 7384),
    ENCHANTED_ROBES(7400, 7399, 7398),
    ROBIN_HOOD_HAT(2581),
    GOLDTRIMMED_RUNE_ARMOUR(single(2619), single(2615), multiple(2617, 3476), single(2621)),
    TRIMMED_RUNE_ARMOUR(single(2627), single(2623), multiple(2625, 3477), single(2629)),
    TAN_CAVALIER(2639),
    DARK_CAVALIER(2641),
    BLACK_CAVALIER(2643),
    PIRATES_HAT(2651),
    ZAMORAK_RUNE_ARMOUR(single(2657), single(2653), multiple(2655, 3478), single(2659)),
    SARADOMIN_RUNE_ARMOUR(single(2665), single(2661), multiple(2663, 3479), single(2667)),
    GUTHIX_RUNE_ARMOUR(single(2673), single(2669), multiple(2671, 3480), single(2675)),
    GILDED_RUNE_ARMOUR(single(3486), single(3481), multiple(3483, 3485), single(3488)),
    RUNE_HERALDIC_HELM_1(10286),
    RUNE_HERALDIC_HELM_2(10288),
    RUNE_HERALDIC_HELM_3(10290),
    RUNE_HERALDIC_HELM_4(10292),
    RUNE_HERALDIC_HELM_5(10294),
    TRIMMED_AMULET_OF_GLORY(10362),
    SARADOMIN_VESTMENTS(10452, 10458, 10464, 10446, 10440, 10470),
    GUTHIX_VESTMENTS(10454, 10462, 10466, 10448, 10442, 10472),
    ZAMORAK_VESTMENT_SET(10456, 10460, 10468, 10450, 10444, 10474),
    SARADOMIN_BLESSED_DHIDE_ARMOUR(10390, 10386, 10388, 10384),
    GUTHIX_BLESSED_DHIDE_ARMOUR(10382, 10378, 10380, 10376),
    ZAMORAK_BLESSED_DHIDE_ARMOUR(10374, 10370, 10372, 10368),
    CAVALIER_MASK(11277),
    RED_DRAGONHIDE_GOLD(12327, 12329),
    RED_DRAGONHIDE_TRIM(12331, 12333),
    RED_CAVALIER(12323),
    NAVY_CAVALIER(12325),
    WHITE_CAVALIER(12321),
    PITH_HELMET(12516),
    EXPLORER_BACKPACK(12514),
    ARMADYL_RUNE_ARMOUR(single(12476), single(12470), multiple(12472, 12474), single(12478)),
    BANDOS_RUNE_ARMOUR(single(12486), single(12480), multiple(12482, 12484), single(12488)),
    ANCIENT_RUNE_ARMOUR(single(12466), single(12460), multiple(12462, 12464), single(12468)),
    ARMADYL_BLESSED_DHIDE_ARMOUR(12512, 12508, 12510, 12506),
    BANDOS_BLESSED_DHIDE_ARMOUR(12504, 12500, 12502, 12498),
    ANCIENT_BLESSED_DHIDE_ARMOUR(12496, 12492, 12494, 12490),
    GREEN_DRAGON_MASK(12518),
    BLUE_DRAGON_MASK(12520),
    RED_DRAGON_MASK(12522),
    BLACK_DRAGON_MASK(12524),
    RUNE_CANE(12379),
    ZOMBIE_HEAD_TT(19912),
    CYCLOPS_HEAD(19915),
    GILDED_RUNE_MED_HELM(20146),
    GILDED_RUNE_CHAINBODY(20149),
    GILDED_RUNE_SQ_SHIELD(20152),
    GILDED_RUNE_2H_SWORD(20155),
    GILDED_RUNE_SPEAR(20158),
    GILDED_RUNE_HASTA(20161),
    NUNCHAKU(19918),
    //PT2
    SARADOMIN_DHIDE_BOOTS(19933),
    BANDOS_DHIDE_BOOTS(19924),
    ARMADYL_DHIDE_BOOTS(19930),
    GUTHIX_DHIDE_BOOTS(19927),
    ZAMORAK_DHIDE_BOOTS(19930),
    ANCIENT_DHIDE_BOOTS(19921),
    THIRD_AGE_RANGER_ARMOUR(10330, 10332, 10334, 10336),
    THIRD_AGE_MAGE_ARMOUR(10338,10340,10342,10344),
    THIRD_AGE_MELEE_ARMOUR(10346,10348,10350,10352),

    //Elite
    DRAGON_CANE(12373),
    BRIEFCASE(12335),
    SAGACIOUS_SPECTACLES(12337),
    ROYAL_OUTFIT(12393, 12395, 12397, 12439),
    BRONZE_DRAGON_MASK(12363),
    IRON_DRAGON_MASK(12365),
    STEEL_DRAGON_MASK(12367),
    MITHRIL_DRAGON_MASK(12369),
    LAVA_DRAGON_MASK(12371),
    AFRO(12430),
    KATANA(12357),
    BIG_PIRATE_HAT(12355),
    TOP_HAT(12432),
    MONOCLE(12353),
    BLACK_DRAGONHIDE_GOLD(12381, 12383),
    BLACK_DRAGONHIDE_TRIM(12385, 12387),
    MUSKETEER_OUTFIT(12351, 12441, 12443),
    PARTYHAT_AND_SPECS(12399),
    PIRATE_HAT_AND_PATCH(12412),
    TOP_HAT_AND_MONOCLE(12434),
    DEERSTALKER(12540),
    HEAVY_CASKET(19941),
    ARCEUUS_HOUSE_SCARF(19943),
    HOSIDIUS_HOUSE_SCARF(19946),
    LOVAKENGJ_HOUSE_SCARF(19949),
    PISCARILIUS_HOUSE_SCARF(19952),
    SHAYZIEN_HOUSE_SCARF(19955),
    BLACKSMITHS_HELM(19988),
    BUCKET_HELM(19991),
    RANGER_GLOVES(19994),
    HOLY_WRAPS(19997),
    RING_OF_NATURE(20005),
    THIRD_AGE_WAND(12422),
    THIRD_AGE_BOW(12424),
    THIRD_AGE_LONGSWORD(12426),
    DARK_TUXEDO_OUTFIT(19958, 19961, 19967, 19964, 19970),
    LIGHT_TUXEDO_OUTFIT(19973, 19976, 19982, 19979, 19985),

    //ELITE_TREASURE_TRAILS
    FANCY_TIARA(20008),
    THIRD_AGE_AXE(20011),
    THIRD_AGE_PICKAXE(20014),
    RING_OF_COINS(20017),
    LESSER_DEMON_MASK(20020),
    GREATER_DEMON_MASK(20023),
    BLACK_DEMON_MASK(20026),
    OLD_DEMON_MASK(20029),
    JUNGLE_DEMON_MASK(20032),
    OBSIDIAN_CAPE_R(20050),
    HALF_MOON_SPECTACLES(20053),
    ALE_OF_THE_GODS(20056),
    BUCKET_HELM_G(20059),
    BOWL_WIG(20110),
    SHAYZIEN_HOUSE_HOOD(20125),
    HOSIDIUS_HOUSE_HOOD(20116),
    ARCEUUS_HOUSE_HOOD(20113),
    PISCARILIUS_HOUSE_HOOD(20122),
    LOVAKENGJ_HOUSE_HOOD(20119),
    SAMURAI_OUTFIT(20035, 20038, 20041, 20044, 20047),
    MUMMY_OUTFIT(20080,20083,20086,20089,20092),
    ANKOU_OUTFIT(20095,20098,20101,20104,20107),
    ROBES_OF_DARKNESS(20128,20131,20134,20137,20140),

    //Toy box 1
    BUNNY_EARS(1037),
    SCYTHE(1419),
    WAR_SHIP(795),
    YOYO(4079),
    RUBBER_CHICKEN(4566),
    ZOMBIE_HEAD(6722),
    BLUE_MARIONETTE(6865),
    RED_MARIONETTE(6867),
    GREEN_MARIONETTE(6866),
    BOBBLE_HAT(6856),
    BOBBLE_SCARF(6857),
    JESTER_HAT(6858),
    JESTER_SCARF(6859),
    TRIJESTER_HAT(6860),
    TRIJESTER_SCARF(6861),
    WOOLLY_HAT(6862),
    WOOLLY_SCARF(6863),
    EASTER_RING(7927),
    JACK_LANTERN_MASK(9920),
    SPOOKY_BOOTS(9921),
    SPOOKY_GLOVES(9922),
    SPOOKY_LEGS(9923),
    SPOOKY_BODY(9924),
    SPOOKY_HEAD(9925),
    REINDEER_HAT(10507),
    CHICKEN_HEAD(11015),
    CHICKEN_FEET(11016),
    CHICKEN_WINGS(11017),
    CHICKEN_LEGS(11018),
    BLACK_HWEEN_MASK(11847),
    BLACK_PARTYHAT(11862),
    RAINBOW_PARTYHAT(11863),
    COW_MASK(11919),
    COW_TOP(12956),
    COW_LEGS(12957),
    COW_GLOVES(12958),
    COW_SHOES(12959),
    EASTER_BASKET(4565),
    DRUIDIC_WREATH(12600),
    GRIM_REAPER_HOOD(12845),
    SANTA_MASK(12887),
    SANTA_JACKET(12888),
    SANTA_PANTALOONS(12889),
    SANTA_GLOVES(12890),
    SANTA_BOOTS(12891),
    ANTISANTA_MASK(12892),
    ANTISANTA_JACKET(12893),
    ANTISANTA_PANTALOONS(12894),
    ANTISANTA_GLOVES(12895),
    ANTISANTA_BOOTS(12896),
    BUNNY_FEET(13182),
    MASK_OF_BALANCE(13203),
    TIGER_TOY(13215),
    LION_TOY(13216),
    SNOW_LEOPARD_TOY(13217),
    AMUR_LEOPARD_TOY(13218),
    ANTIPANTIES(13288),
    //Page2
    GRAVEDIGGER_HAT(13283),
    GRAVEDIGGER_TOP(13284),
    GRAVEDIGGER_LEGS(13285),
    GRAVEDIGGER_BOOTS(13286),
    GRAVEDIGGER_GLOVES(13287),
    BLACK_SANTA_HAT(13343),
    INVERTED_SANTA_HAT(13344),
    GNOME_CHILD_HAT(13655),
    BUNNY_TOP(13663),
    BUNNY_LEGS(13664),
    BUNNY_PAWS(13665),
    CABBAGE_CAPE(13679),
    CRUCIFEROUS_CODEX(13681),
    HORNWOOD_HELM(19699),
    BANSHEE_MASK(20773),
    BANSHEE_TOP(20775),
    BANSHEE_ROBE(20777),
    HUNTING_KNIFE(20779),
    SNOW_GLOBE(20832),
    SACK_OF_PRESENTS(20834),
    GIANT_PRESENT(20836),
    FOURTH_BIRTHDAY_HAT(21211),
    BIRTHDAY_BALLOONS(21209),
    EASTER_EGG_HELM(21214),
    RAINBOW_SCARF(21314),
    HAND_FAN(21354),
    RUNEFEST_SHIELD(21695),
    JONAS_MASK(21719),
    SNOW_IMP_HEAD(21847),
    SNOW_IMP_BODY(21849),
    SNOW_IMP_LEGS(21851),
    SNOW_IMP_TAIL(21853),
    SNOW_IMP_GLOVES(21855),
    SNOW_IMP_FEET(21857),
    WISE_OLD_MANS_SANTA_HAT(21859),
    PROP_SWORD(22316),
    EGGSHELL_PLATEBODY(22351),
    EGGSHELL_PLATELEGS(22353)


    ;

    public Item[][] getPieces() {
        return pieces;
    }

    Item[][] pieces;

    Costume(int... pieces) { // for simple sets
        this(Arrays.stream(pieces).mapToObj(Costume::single).toArray(Item[][]::new));
    }

    Costume(Item[]... pieces) {
        this.pieces = pieces;
    }

    Costume(StatType skill) { // for skillcapes
        this(single(skill.hoodId), multiple(skill.trimmedCapeId, skill.regularCapeId));
    }

    Costume(MaxCapeVariants.MaxCapes c) { // max cape
        pieces = new Item[2][MaxCapeVariants.MaxCapes.values().length + 1];
        pieces[0][0] = new Item(13281);
        pieces[1][0] = new Item(13342);
        for (int i = 0; i < MaxCapeVariants.MaxCapes.values().length; i++) {
            c = MaxCapeVariants.MaxCapes.values()[i];
            pieces[0][i + 1] = new Item(c.newHoodId);
            pieces[1][i + 1] = new Item(c.newCapeId);
        }
    }

    private static Item[] single(int id) {
        return new Item[]{new Item(id)};
    }

    private static Item[] multiple(int... id) {
        return Arrays.stream(id).mapToObj(Item::new).toArray(Item[]::new);
    }

    public void sendRequiredItems(Player player) {
        player.sendMessage("You do not have all the items required to store this costume. You will need:");
        for (Item[] piece : pieces) {
            StringBuilder sb = new StringBuilder("    ");
            for (int i = 0; i < piece.length; i++) {
                sb.append(Color.RED.wrap(piece[i].getDef().name));
                if (i < piece.length - 1)
                    sb.append(" or ");
            }
            player.sendMessage(sb.toString());
        }
    }
}
