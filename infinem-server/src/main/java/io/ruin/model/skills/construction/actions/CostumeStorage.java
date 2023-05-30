package io.ruin.model.skills.construction.actions;

import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.skills.construction.Buildable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.ruin.model.skills.construction.actions.Costume.*;

public enum CostumeStorage {
    FANCY_DRESS_BOX(3291, MIME_COSTUME, FROG_COSTUME, ZOMBIE_OUTFIT, CAMO_OUTFIT, LEDERHOSEN_OUTFIT, SHADE_ROBES, STALE_BAGUETTE, BEEKEEPER_OUTFIT),

    ARMOUR_CASE(3290,
            CASTLE_WARS_DECORATIVE_ARMOUR_1,
            CASTLE_WARS_DECORATIVE_ARMOUR_2,
            CASTLE_WARS_DECORATIVE_ARMOUR_3,
            CASTLE_WARS_MAGE_ARMOUR,
            CASTLE_WARS_RANGER_ARMOUR,
            VOID_KNIGHT_ARMOUR,
            ELITE_VOID_ARMOUR,
            VOID_MELEE_HELM,
            VOID_RANGER_HELM,
            VOID_MAGE_HELM,
            ROGUE_ARMOUR,
            SPINED_ARMOUR,
            ROCKSHELL_ARMOUR,
            TRIBAL_MASK_POISON,
            TRIBAL_MASK_DISEASE,
            TRIBAL_MASK_COMBAT,
            WHITE_KNIGHT_ARMOUR,
            INITIATE_ARMOUR,
            PROSELYTE_ARMOUR,
            MOURNER_GEAR,
            GRAAHK_GEAR,
            LARUPIA_GEAR,
            KYATT_GEAR,
            POLAR_CAMO,
            JUNGLE_CAMO,
            WOODLAND_CAMO,
            DESERT_CAMO,
            BUILDERS_COSTUME,
            LUMBERJACK_COSTUME,
            BOMBER_JACKET_COSTUME,
            HAM_ROBES,
            PROSPECTOR_KIT,
            ANGLERS_OUTFIT,
            SHAYZIEN_ARMOUR_1,
            SHAYZIEN_ARMOUR_2,
            SHAYZIEN_ARMOUR_3,
            SHAYZIEN_ARMOUR_4,
            SHAYZIEN_ARMOUR_5,
            XERICIAN_ROBES,
            FARMERS_OUTFIT,
            CLUE_HUNTER_OUTFIT,
            CORRUPTED_ARMOUR,
            OBSIDIAN_ARMOUR,
            HELM_OF_RAEDWALD,
            FIGHTER_TORSO,
            PENANCE_BOOTS,
            PENANCE_GLOVES,
            PENANCE_SKIRT,
            PENANACE_FIGHTER_HAT,
            PENANCE_RANGER_HAT,
            PENANCE_RUNNER_HAT,
            PENANCE_HEALER_HAT,
            JUSTICIAR_ARMOUR,
            ORNATE_ARMOUR,
            DRAGONSTONE_ARMOUR,
            ARDOUGNE_KNIGHT_ARMOUR,
            DEADMAN_ARMOUR,
            HALO_SARADOMIN,
            HALO_ZAMORAK,
            HALO_GUTHIX,
            HALO_ARMADYL,
            HALO_BANDOS,
            HALO_SEREN,
            HALO_ANCIENT,
            HALO_BRASSICA,
            TWISTED_RELICHUNTER_T1,
            TWISTED_RELICHUNTER_T2,
            TWISTED_RELICHUNTER_T3,
            CANE_TWISTED,
            TRAILBLAZER_RELICHUNTER_T1,
            TRAILBLAZER_RELICHUNTER_T2,
            TRAILBLAZER_RELICHUNTER_T3,
            CANE_TRAILBLAZER,
            INQUISITOR_ARMOUR,
            VYRE_NOBLE_OUTFIT,
            SNAKESKIN_ARMOUR,
            CASTLE_WARS_DECORATIVE_SWORD_1,
            CASTLE_WARS_DECORATIVE_SWORD_2,
            CASTLE_WARS_DECORATIVE_SWORD_3,
            FEDORA,
            SWIFT_BLADE,
            PROSPECTOR_KIT_GOLDEN,
            ANGLER_OUTFIT_SPIRIT),

    MAGIC_WARDROBE(3289,
            BLUE_MYSTIC,
            DARK_MYSTIC,
            LIGHT_MYSTIC,
            DUSK_MYSTIC,
            SKELETAL,
            INFINITY,
            INFINITY_LIGHT,
            INFINITY_DARK,
            SPLITBARK,
            SWAMPBARK,
            BLOODBARK,
            GHOSTLY,
            MOONCLAN_ROBES,
            LUNAR_ROBES,
            DAGONHAI_ROBES,
            GRACEFUL,
            GRACEFUL_ARCEUUS,
            GRACEFUL_HOSIDIUS,
            GRACEFUL_LOVAKENGJ,
            GRACEFUL_PISCARILIUS,
            GRACEFUL_SHAYZIEN,
            GRACEFUL_KOUREND,
            GRACEFUL_AGILITY_ARENA,
            GRACEFUL_HALLOWED,
            GRACEFUL_TRAILBLAZER,
            BLUE_NAVAL,
            GREEN_NAVAL,
            RED_NAVAL,
            BROWN_NAVAL,
            BLACK_NAVAL,
            PURPLE_NAVAL,
            GREY_NAVAL,
            ELDER_CHAOS_DRUID,
            EVIL_CHICKEN_COSTUME,
            PYROMANCER_OUTFIT,
            GOLDEN_TENCH,
            PEARL_FISHING_ROD,
            PEARL_BARBARIAN_ROD,
            PEARL_FLY_FISHING_ROD,
            OILY_PEARL_FISHING_ROD,
            FISH_SACK,
            MUDSKIPPER_KIT,
            DARK_FLIPPERS,
            BOUNTY_HUNTER_HAT_1,
            BOUNTY_HUNTER_HAT_2,
            BOUNTY_HUNTER_HAT_3,
            BOUNTY_HUNTER_HAT_4,
            BOUNTY_HUNTER_HAT_5,
            BOUNTY_HUNTER_HAT_6,
            ANCESTRAL_ROBES,
            ANCESTRAL_ROBES_TWISTED,
            FLAG_CUTTHROAT,
            FLAG_GILDED_SMILE,
            FLAG_BRONZE_FIST,
            FLAG_LUCKY_SHOT,
            FLAG_TREASURE,
            FLAG_PHASMATYS,
            CARPENTERS_OUTFIT,
            AMYS_SAW,
            WARM_GLOVES,
            TRIBAL_OUTFIT_BROWN,
            TRIBAL_OUTFIT_BLUE,
            TRIBAL_OUTFIT_YELLOW,
            TRIBAL_OUTFIT_PINK,
            BRUMA_TORCH,
            ZEALOT_PRAYER_ROBES,
            IMCANDO_HAMMER
    ),

    CAPE_RACK(3292,
            CAPE_OF_LEGENDS,
            OBSIDIAN_CAPE,
            FIRE_CAPE,
            WILDERNESS_CAPES,
            WILDERNESS_CAPE_0,
            WILDERNESS_CAPE_X,
            WILDERNESS_CAPE_I,
            SARADOMIN_CAPE,
            GUTHIX_CAPE,
            ZAMORAK_CAPE,
            ATTACK_CAPE,
            DEFENCE_CAPE,
            STRENGTH_CAPE,
            HITPOINTS_CAPE,
            AGILITY_CAPE,
            COOKING_CAPE,
            CONSTRUCTION_CAPE,
            CRAFTING_CAPE,
            FARMING_CAPE,
            FIREMAKING_CAPE,
            FISHING_CAPE,
            FLETCHING_CAPE,
            HERBLORE_CAPE,
            MAGIC_CAPE,
            MINING_CAPE,
            PRAYER_CAPE,
            RANGED_CAPE,
            RUNECRAFTING_CAPE,
            SLAYER_CAPE,
            SMITHING_CAPE,
            THIEVING_CAPE,
            WOODCUTTING_CAPE,
            HUNTER_CAPE,
            QUEST_CAPE,
            ACHIEVEMENT_CAPE,
            MUSIC_CAPE,
            SPOTTED_CAPE,
            SPOTTIER_CAPE,
            MAX_CAPE,
            INFERNAL_CAPE,
            CHAMPIONS_CAPE,
            IMBUED_SARADOMIN_CAPE,
            IMBUED_GUTHIX_CAPE,
            IMBUED_ZAMORAK_CAPE,
            MYTHICAL_CAPE,
            XERICS_GUARD,
            XERICS_WARRIOR,
            XERICS_SENTINEL,
            XERICS_GENERAL,
            XERICS_CHAMPION,
            SINHAZA_SHROUD_1,
            SINHAZA_SHROUD_2,
            SINHAZA_SHROUD_3,
            SINHAZA_SHROUD_4,
            SINHAZA_SHROUD_5,
            GAUNTLET_CAPE,
            VICTOR_CAPE_1,
            VICTOR_CAPE_10,
            VICTOR_CAPE_50,
            VICTOR_CAPE_100,
            VICTOR_CAPE_500,
            VICTOR_CAPE_1000,
            SOUL_CAPE
    ),

    BEGINNER_TREASURE_TRAILS(3293,
            MOLE_SLIPPERS, FROG_SLIPPERS, BEAR_FEET, DEMON_FEET, JESTER_CAPE, SHOULDER_PARROT, MONK_ROBES_TRIM,
            AMULET_OF_DEFENCE_TRIM, SANDWICH_LADY_COSTUME, RUNE_SCIMITAR_GUTHIX, RUNE_SCIMITAR_ZAMORAK, RUNE_SCIMITAR_SARADOMIN
    ),

    EASY_TREASURE_TRAILS(3294,
            BLACK_HERALD_SHIELD_1,
            BLACK_HERALD_SHIELD_2,
            BLACK_HERALD_SHIELD_3,
            BLACK_HERALD_SHIELD_4,
            BLACK_HERALD_SHIELD_5,
            GOLD_STUDDED_LEATHER,
            TRIMMED_STUDDED_LEATHER,
            GOLD_WIZARD_ROBES,
            TRIMMED_WIZARD_ROBES,
            TRIMMED_BLACK_ARMOUR,
            GOLD_BLACK_ARMOUR,
            HIGHWAYMAN_MASK,
            BLUE_BERET,
            BLACK_BERET,
            WHITE_BERET,
            BLACK_HERALD_HELM_1,
            BLACK_HERALD_HELM_2,
            BLACK_HERALD_HELM_3,
            BLACK_HERALD_HELM_4,
            BLACK_HERALD_HELM_5,
            TRIMMED_AMULET_OF_MAGIC,
            PANTALOONS,
            WIG,
            FLARED_TROUSERS,
            SLEEPING_CAP,
            BOBS_RED_SHIRT,
            BOBS_BLUE_SHIRT,
            BOBS_GREEN_SHIRT,
            BOBS_BLACK_SHIRT,
            BOBS_PURPLE_SHIRT,
            RED_ELEGANT,
            GREEN_ELEGANT,
            BLUE_ELEGANT,
            BERET_MASK,
            TRIMMED_BRONZE,
            GOLD_BRONZE,
            TRIMMED_IRON,
            GOLD_IRON,
            TRIMMED_STEEL,
            GOLD_STEEL,
            BEANIE,
            RED_BERET,
            IMP_MASK,
            GOBLIN_MASK,
            BLACK_CANE,
            BLACK_PICKAXE,
            BLACK_WIZARD_GOLD,
            BLACK_WIZARD_TRIM,
            LARGE_SPADE,
            WOODEN_SHIELD_G,
            GOLDEN_CHEFS_HAT,
            GOLDEN_APRON,
            MONK_ROBES_GOLD,
            CAPE_OF_SKULLS,
            AMULET_OF_POWER_TRIMMED,
            RAIN_BOW,
            HAM_JOINT,
            LEATHER_ARMOUR_GOLD,
            STAFF_OF_BOB_THE_CAT,
            BLACK_PLATEBODY_H1,
            BLACK_PLATEBODY_H2,
            BLACK_PLATEBODY_H3,
            BLACK_PLATEBODY_H4,
            BLACK_PLATEBODY_H5,
            BLESSING_PEACEFUL,
            BLESSING_HOLY,
            BLESSING_UNHOLY,
            BLESSING_HONOURABLE,
            BLESSING_WAR,
            BLESSING_ANCIENT,
            VESTMENTS_ARMADYL,
            VESTMENTS_ANCIENT,
            VESTMENTS_BANDOS,
            VESTMENTS_SARADOMIN,
            VESTMENTS_GUTHIX,
            VESTMENTS_ZAMORAK,
            WILLOW_COMPOSITE_BOW
    ),

    MEDIUM_TREASURE_TRAILS(3295,
            RED_STRAW_BOATER,
            ORANGE_STRAW_BOATER,
            GREEN_STRAW_BOATER,
            BLUE_STRAW_BOATER,
            BLACK_STRAW_BOATER,
            ADAMANT_HERALDIC_KITESHIELD_1,
            ADAMANT_HERALDIC_KITESHIELD_2,
            ADAMANT_HERALDIC_KITESHIELD_3,
            ADAMANT_HERALDIC_KITESHIELD_4,
            ADAMANT_HERALDIC_KITESHIELD_5,
            GREEN_DRAGONHIDE_GOLD,
            GREEN_DRAGONHIDE_TRIM,
            RANGER_BOOTS,
            TRIMMED_ADAMANTITE_ARMOUR,
            GOLD_TRIMMED_ADAMANTITE_ARMOUR,
            RED_HEADBAND,
            BLACK_HEADBAND,
            BROWN_HEADBAND,
            ADAMANT_HERALDIC_HELM_1,
            ADAMANT_HERALDIC_HELM_2,
            ADAMANT_HERALDIC_HELM_3,
            ADAMANT_HERALDIC_HELM_4,
            ADAMANT_HERALDIC_HELM_5,
            TRIMMED_AMULET_OF_STRENGTH,
            ELEGANT_CLOTHING_BLACK_WHITE,
            ELEGANT_CLOTHING_PURPLE,
            ELEGANT_CLOTHING_PINK,
            ELEGANT_CLOTHING_GOLD,
            WIZARD_BOOTS,
            MITHRIL_ARMOUR_GOLD,
            MITHRIL_ARMOUR_TRIM,
            LEPRECHAUN_HAT,
            BLACK_LEPRECHAUN_HAT,
            WHITE_HEADBAND,
            BLUE_HEADBAND,
            GOLD_HEADBAND,
            PINK_HEADBAND,
            GREEN_HEADBAND,
            PINK_BOATER,
            PURPLE_BOATER,
            WHITE_BOATER,
            CAT_MASK,
            PENGUIN_MASK,
            BLACK_UNICORN_MASK,
            WHITE_UNICORN_MASK,
            TOWN_CRIER,
            ADAMANT_CANE,
            HOLY_SANDALS,
            CLUELESS_SCROLL,
            ARCEUUS_BANNER,
            HOSIDIUS_BANNER,
            LOVAKENGJ_BANNER,
            PISCARILIUS_BANNER,
            SHAYZIEN_BANNER,
            CABBAGE_ROUND_SHIELD,
            SPIKED_MANACLES,
            ADAMANT_PLATEBODY_H1,
            ADAMANT_PLATEBODY_H2,
            ADAMANT_PLATEBODY_H3,
            ADAMANT_PLATEBODY_H4,
            ADAMANT_PLATEBODY_H5,
            WOLF_MASK,
            WOLF_CLOAK,
            CLIMBING_BOOTS_GOLD,
            YEW_COMPOSITE_BOW
    ),

    HARD_TREASURE_TRAILS(3296,
            RUNE_HERALDIC_KITESHIELD_1,
            RUNE_HERALDIC_KITESHIELD_2,
            RUNE_HERALDIC_KITESHIELD_3,
            RUNE_HERALDIC_KITESHIELD_4,
            RUNE_HERALDIC_KITESHIELD_5,
            BLUE_DRAGONHIDE_GOLD,
            BLUE_DRAGONHIDE_TRIM,
            ENCHANTED_ROBES,
            ROBIN_HOOD_HAT,
            GOLDTRIMMED_RUNE_ARMOUR,
            TRIMMED_RUNE_ARMOUR,
            TAN_CAVALIER,
            DARK_CAVALIER,
            BLACK_CAVALIER,
            PIRATES_HAT,
            ZAMORAK_RUNE_ARMOUR,
            SARADOMIN_RUNE_ARMOUR,
            GUTHIX_RUNE_ARMOUR,
            GILDED_RUNE_ARMOUR,
            RUNE_HERALDIC_HELM_1,
            RUNE_HERALDIC_HELM_2,
            RUNE_HERALDIC_HELM_3,
            RUNE_HERALDIC_HELM_4,
            RUNE_HERALDIC_HELM_5,
            TRIMMED_AMULET_OF_GLORY,
            SARADOMIN_VESTMENTS,
            GUTHIX_VESTMENTS,
            ZAMORAK_VESTMENT_SET,
            SARADOMIN_BLESSED_DHIDE_ARMOUR,
            GUTHIX_BLESSED_DHIDE_ARMOUR,
            ZAMORAK_BLESSED_DHIDE_ARMOUR,
            CAVALIER_MASK,
            RED_DRAGONHIDE_GOLD,
            RED_DRAGONHIDE_TRIM,
            RED_CAVALIER,
            NAVY_CAVALIER,
            WHITE_CAVALIER,
            PITH_HELMET,
            EXPLORER_BACKPACK,
            ARMADYL_RUNE_ARMOUR,
            BANDOS_RUNE_ARMOUR,
            ANCIENT_RUNE_ARMOUR,
            ARMADYL_BLESSED_DHIDE_ARMOUR,
            BANDOS_BLESSED_DHIDE_ARMOUR,
            ANCIENT_BLESSED_DHIDE_ARMOUR,
            GREEN_DRAGON_MASK,
            BLUE_DRAGON_MASK,
            RED_DRAGON_MASK,
            BLACK_DRAGON_MASK,
            RUNE_CANE,
            ZOMBIE_HEAD_TT,
            CYCLOPS_HEAD,
            GILDED_RUNE_MED_HELM,
            GILDED_RUNE_CHAINBODY,
            GILDED_RUNE_SQ_SHIELD,
            GILDED_RUNE_2H_SWORD,
            GILDED_RUNE_SPEAR,
            GILDED_RUNE_HASTA,
            NUNCHAKU,
            SARADOMIN_DHIDE_BOOTS,
            BANDOS_DHIDE_BOOTS,
            ARMADYL_DHIDE_BOOTS,
            GUTHIX_DHIDE_BOOTS,
            ZAMORAK_DHIDE_BOOTS,
            ANCIENT_DHIDE_BOOTS,
            DRAGONHIDE_SHIELD_GUTHIX,
            DRAGONHIDE_SHIELD_SARADOMIN,
            DRAGONHIDE_SHIELD_ZAMORAK,
            DRAGONHIDE_SHIELD_ANCIENT,
            DRAGONHIDE_SHIELD_ARMADYL,
            DRAGONHIDE_SHIELD_BANDOS,
            RUNE_PLATEBODY_H1,
            RUNE_PLATEBODY_H2,
            RUNE_PLATEBODY_H3,
            RUNE_PLATEBODY_H4,
            RUNE_PLATEBODY_H5,
            DUAL_SAI,
            THIEVING_BAG,
            DRAGON_BOOTS_GOLD,
            MAGIC_COMPOSITE_BOW
    ),

    ELITE_TREASURE_TRAILS(3297,
            DRAGON_CANE,
            BRIEFCASE,
            SAGACIOUS_SPECTACLES,
            ROYAL_OUTFIT,
            BRONZE_DRAGON_MASK,
            IRON_DRAGON_MASK,
            STEEL_DRAGON_MASK,
            MITHRIL_DRAGON_MASK,
            LAVA_DRAGON_MASK,
            AFRO,
            KATANA,
            BIG_PIRATE_HAT,
            TOP_HAT,
            MONOCLE,
            BLACK_DRAGONHIDE_GOLD,
            BLACK_DRAGONHIDE_TRIM,
            MUSKETEER_OUTFIT,
            PARTYHAT_AND_SPECS,
            PIRATE_HAT_AND_PATCH,
            TOP_HAT_AND_MONOCLE,
            DEERSTALKER,
            HEAVY_CASKET,
            ARCEUUS_HOUSE_SCARF,
            HOSIDIUS_HOUSE_SCARF,
            LOVAKENGJ_HOUSE_SCARF,
            PISCARILIUS_HOUSE_SCARF,
            SHAYZIEN_HOUSE_SCARF,
            BLACKSMITHS_HELM,
            BUCKET_HELM,
            RANGER_KIT,
            HOLY_WRAPS,
            RING_OF_NATURE,
            THIRD_AGE_WAND,
            THIRD_AGE_BOW,
            THIRD_AGE_LONGSWORD,
            THIRD_AGE_CLOAK,
            DARK_TUXEDO_OUTFIT,
            LIGHT_TUXEDO_OUTFIT,
            FREMENNIK_KILT,
            ADAMANT_DRAGON_MASK,
            RUNE_DRAGON_MASK
    ),

    MASTER_TREASURE_TRAILS(3298,
            FANCY_TIARA,
            THIRD_AGE_AXE,
            THIRD_AGE_PICKAXE,
            RING_OF_COINS,
            LESSER_DEMON_MASK,
            GREATER_DEMON_MASK,
            BLACK_DEMON_MASK,
            OLD_DEMON_MASK,
            JUNGLE_DEMON_MASK,
            OBSIDIAN_CAPE_R,
            HALF_MOON_SPECTACLES,
            ALE_OF_THE_GODS,
            BUCKET_HELM_G,
            BOWL_WIG,
            SHAYZIEN_HOUSE_HOOD,
            HOSIDIUS_HOUSE_HOOD,
            ARCEUUS_HOUSE_HOOD,
            PISCARILIUS_HOUSE_HOOD,
            LOVAKENGJ_HOUSE_HOOD,
            SAMURAI_OUTFIT,
            MUMMY_OUTFIT,
            ANKOU_OUTFIT,
            ROBES_OF_DARKNESS
    ),

    TOY_BOX(3299,
            BUNNY_EARS,
            SCYTHE,
            WAR_SHIP,
            YOYO,
            RUBBER_CHICKEN,
            ZOMBIE_HEAD,
            BLUE_MARIONETTE,
            RED_MARIONETTE,
            GREEN_MARIONETTE,
            BOBBLE_HAT,
            BOBBLE_SCARF,
            JESTER_HAT,
            JESTER_SCARF,
            TRIJESTER_HAT,
            TRIJESTER_SCARF,
            WOOLLY_HAT,
            WOOLLY_SCARF,
            EASTER_RING,
            JACK_LANTERN_MASK,
            SPOOKY_BOOTS,
            SPOOKY_GLOVES,
            SPOOKY_LEGS,
            SPOOKY_BODY,
            SPOOKY_HEAD,
            REINDEER_HAT,
            CHICKEN_HEAD,
            CHICKEN_FEET,
            CHICKEN_WINGS,
            CHICKEN_LEGS,
            BLACK_HWEEN_MASK,
            BLACK_PARTYHAT,
            RAINBOW_PARTYHAT,
            COW_MASK,
            COW_TOP,
            COW_LEGS,
            COW_GLOVES,
            COW_SHOES,
            EASTER_BASKET,
            DRUIDIC_WREATH,
            GRIM_REAPER_HOOD,
            SANTA_MASK,
            SANTA_JACKET,
            SANTA_PANTALOONS,
            SANTA_GLOVES,
            SANTA_BOOTS,
            ANTISANTA_MASK,
            ANTISANTA_JACKET,
            ANTISANTA_PANTALOONS,
            ANTISANTA_GLOVES,
            ANTISANTA_BOOTS,
            BUNNY_FEET,
            MASK_OF_BALANCE,
            TIGER_TOY,
            LION_TOY,
            SNOW_LEOPARD_TOY,
            AMUR_LEOPARD_TOY,
            ANTIPANTIES,
            GRAVEDIGGER_HAT,
            GRAVEDIGGER_TOP,
            GRAVEDIGGER_LEGS,
            GRAVEDIGGER_BOOTS,
            GRAVEDIGGER_GLOVES,
            BLACK_SANTA_HAT,
            INVERTED_SANTA_HAT,
            GNOME_CHILD_HAT,
            BUNNY_TOP,
            BUNNY_LEGS,
            BUNNY_PAWS,
            CABBAGE_CAPE,
            CRUCIFEROUS_CODEX,
            HORNWOOD_HELM,
            BANSHEE_MASK,
            BANSHEE_TOP,
            BANSHEE_ROBE,
            HUNTING_KNIFE,
            SNOW_GLOBE,
            SACK_OF_PRESENTS,
            GIANT_PRESENT,
            FOURTH_BIRTHDAY_HAT,
            BIRTHDAY_BALLOONS,
            EASTER_EGG_HELM,
            RAINBOW_SCARF,
            HAND_FAN,
            RUNEFEST_SHIELD,
            JONAS_MASK,
            SNOW_IMP_HEAD,
            SNOW_IMP_BODY,
            SNOW_IMP_LEGS,
            SNOW_IMP_TAIL,
            SNOW_IMP_GLOVES,
            SNOW_IMP_FEET,
            WISE_OLD_MANS_SANTA_HAT,
            PROP_SWORD,
            EGGSHELL_PLATEBODY,
            EGGSHELL_PLATELEGS
    );


    private final int enumId;
    private Costume[] costumes;

    CostumeStorage(int enumId, Costume... costumes) {
        this.enumId = enumId;
        this.setCostumes(costumes);
    }

    public void open(Player player, int clueScrollLevel, Buildable buildable) {
        player.openInterface(InterfaceType.MAIN, 675);
        changeInventoryAccess(player);
        if (this == BEGINNER_TREASURE_TRAILS || this == EASY_TREASURE_TRAILS || this == MEDIUM_TREASURE_TRAILS
                || this == HARD_TREASURE_TRAILS || this == ELITE_TREASURE_TRAILS || this == MASTER_TREASURE_TRAILS) {
            player.getPacketSender().sendClientScript(3532, "iii", enumId, 1, 1);
        } else {
            player.getPacketSender().sendClientScript(3532, "iii", enumId, 1, 0);
        }
        player.getPacketSender().sendVarp(262, clueScrollLevel);
        player.getPacketSender().sendAccessMask(675, 4, 0, 5000, 1026);
        sendItems(player);
        player.putTemporaryAttribute(AttributeKey.COSTUME_STORAGE, this);
        player.putTemporaryAttribute(AttributeKey.COSTUME_BUILDABLE, buildable);
    }

    private void changeInventoryAccess(Player player) {
        if (player.isVisibleInterface(674)) {
            return;
        }
        player.openInterface(InterfaceType.INVENTORY, 674);
        player.getPacketSender().sendClientScript(149, "iiiiiisssss", 44171264, 93, 4, 7, 1, -1, "Store<col=ff9040>", "", "", "", "");
        player.getPacketSender().sendAccessMask(674, 0, 0, 27, 1086);
    }

    public void sendItems(Player player) {
        List<Item> items = new ArrayList<>();
        Map<Costume, Item[]> stored = getSets(player);
        for (Item[] costume : stored.values()) {
            items.addAll(Arrays.asList(costume));
        }
        player.getPacketSender().sendItems(-1,-1, 33405, items.toArray(new Item[0]));
    }

    public Costume getByItem(int id) {
        for (Costume costume : getCostumes()) {
            for (Item[] piece : costume.pieces) {
                for (Item option : piece) {
                    if (option.getId() == id) {
                        return costume;
                    }
                }
            }
        }
        return null;
    }

    public Map<Costume, Item[]> getSets(Player player) {
        switch (this) {
            case FANCY_DRESS_BOX:
                return player.house.getFancyDressStorage();
            case ARMOUR_CASE:
                return player.house.getArmourCaseStorage();
            case MAGIC_WARDROBE:
                return player.house.getMagicWardrobeStorage();
            case CAPE_RACK:
                return player.house.getCapeRackStorage();
            case BEGINNER_TREASURE_TRAILS:
                return player.house.getBeginnerTreasureTrailsStorage();
            case EASY_TREASURE_TRAILS:
                return player.house.getEasyTreasureTrailsStorage();
            case MEDIUM_TREASURE_TRAILS:
                return player.house.getMediumTreasureTrailsStorage();
            case HARD_TREASURE_TRAILS:
                return player.house.getHardTreasureTrailsStorage();
            case ELITE_TREASURE_TRAILS:
                return player.house.getEliteTreasureTrailsStorage();
            case MASTER_TREASURE_TRAILS:
                return player.house.getMasterTreasureTrailsStorage();
            case TOY_BOX:
                return player.house.getToyBoxStorage();
            default:
                throw new IllegalArgumentException();
        }
    }

    public int countSpaceUsed(Player player) {
        Map<Costume, Item[]> stored = getSets(player);
        switch (this) {
            case FANCY_DRESS_BOX:
            case ARMOUR_CASE:
            case MAGIC_WARDROBE:
            default:
                return stored.size();
            case CAPE_RACK:
                return (int) stored.keySet().stream().filter(costume -> costume.ordinal() >= ATTACK_CAPE.ordinal() && costume.ordinal() <= MUSIC_CAPE.ordinal()).count();
            case BEGINNER_TREASURE_TRAILS:
            case EASY_TREASURE_TRAILS:
            case MEDIUM_TREASURE_TRAILS:
            case HARD_TREASURE_TRAILS:
            case ELITE_TREASURE_TRAILS:
            case MASTER_TREASURE_TRAILS:
            case TOY_BOX:
                return 0;
        }
    }

    public Costume[] getCostumes() {
        return costumes;
    }

    public void setCostumes(Costume[] costumes) {
        this.costumes = costumes;
    }
}
