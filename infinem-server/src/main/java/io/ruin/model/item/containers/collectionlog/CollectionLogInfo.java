package io.ruin.model.item.containers.collectionlog;

import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.StructDefinition;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.entity.player.killcount.SlayerKillCounter;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
public enum CollectionLogInfo {

    /**
     * Enum - Page
     * 2109 - Abyssal Sire
     * 2110 - Barrows
     * 2111 - Bryophyta
     * 2112 - Callisto
     * 2113 - Cerberus
     * 2114 - Chaos elemental
     * 2115 - Chaos fanatic
     * 2116 - Commander zilyanna
     * 2117 - Corp beast
     * 2118 - Crazy archaeologist
     * 2119 - Dag kings
     * 2120 - General graardor
     * 2121 - Giant mole
     * 2122 - Grotesque Guardians
     * 2123 - Kalphite queen
     * 2124 - King black dragon
     * 2125 - Kraken
     * 2126 - Kree'arra
     * 2127 - K'ril Tsutsaroth
     * 2128 - Obor
     * 2129 - Scorpia
     * 2130 - Skotizo
     * 2131 - Thermonuclear Smoke devil
     * 2132 - Inferno
     * 2133 - Fight cave
     * 2134 - Venenatis
     * 2135 - Vet'ion
     * 2136 - Vorkath
     * 2137 - Wintertodt
     * 2138 - Zulrah
     * 2173 - Alchemical hydra
     * 2175 - Hespori
     * 2344 - Sarachnis
     * 2389 - Zulcano
     * 2390 - The Gauntlet
     *
     * 2139 - Chambers of Xeric
     * 2140 - Theatre of Blood
     *
     * 2141 - Easy Treasure Trails
     * 2142 - Medium Treasure Trails
     * 2143 - Hard Treasure Trails
     * 2144 - Elite Treasure Trails
     * 2145 - Master Treasure Trails
     * 2146 - Shared Treasure Trails
     * 2322 - Beginner Treasure Trails
     *
     * 2147 - Castle Wars
     * 2148 - Mage Training Arena
     * 2149 - Barbarian Assault
     * 2150 - Gnome Restaurant
     * 2151 - Shades of Mort'ton
     * 2152 - Pest Control
     * 2153 - Rogues' Den
     * 2154 - Temple Trekking
     * 2155 - Tithe Farm
     * 2156 - Trouble Brewing
     * 2157 - Fishing Trawler
     *
     * 2158 - All Pets
     * 2159 - Chaos Druids
     * 2160 - Revenants
     * 2161 - Glough's Experiments
     * 2162 - Slayer
     * 2163 - Champion's Challenge
     * 2164 - Cyclopes
     * 2165 - Skilling pets
     * 2166 - Motherlode Mine
     * 2167 - Shayzien Armor
     * 2168 - TzHaar
     * 2169 - Miscellaneous
     * 2171 - Chompy bird hunting
     * 2172 - Random events
     * 2174 - Aerial Fishing
     * 2289 - Creature creation
     * 2290 - Rooftop agility
     * 2291 - Fossil Island Notes
     * 2292 - My Notes
     *
     *
     * Script 4103 controls green names for categories
     */

    BOSS(471, 36, new int[]{10, 11, 12, 29}, 40697866, 40697867, 40697868, 40697869) {
        @Override
        public int getKillCount(Player player, int slot) {

            switch (slot) {
                case 0://abyssal sire
                    return KillCounter.getKillCount(player, BossKillCounter.ABYSSAL_SIRE);
                case 1://alchemical hydra
                    return KillCounter.getKillCount(player, BossKillCounter.ALCHEMICAL_HYDRA);
                case 2://barrows
                    return KillCounter.getKillCount(player, BossKillCounter.BARROWS);
                case 3://bryophyta
                    return KillCounter.getKillCount(player, BossKillCounter.BRYOPHYTA);
                case 4://callisto
                    return KillCounter.getKillCount(player, BossKillCounter.CALLISTO);
                case 5://cerberus
                    return KillCounter.getKillCount(player, BossKillCounter.CERBERUS);
                case 6://chaos ele
                    return KillCounter.getKillCount(player, BossKillCounter.CHAOS_ELE);
                case 7://chaos fanatic
                    return KillCounter.getKillCount(player, BossKillCounter.CHAOS_FANATIC);
                case 8://commander zilyana
                    return KillCounter.getKillCount(player, BossKillCounter.COMMANDER_ZILYANA);
                case 9://corporeal beast
                    return KillCounter.getKillCount(player, BossKillCounter.CORP_BEAST);
                case 10://crazy archaeologist
                    return KillCounter.getKillCount(player, BossKillCounter.CRAZY_ARCHAEOLOGIST);
                case 11://dagannoth kings
                    return KillCounter.getKillCount(player, BossKillCounter.DAG_PRIME)
                            + KillCounter.getKillCount(player, BossKillCounter.DAG_REX)
                            + KillCounter.getKillCount(player, BossKillCounter.DAG_SUPREME);
                case 12://the fight caves
                    return KillCounter.getKillCount(player, BossKillCounter.JAD);
                case 13://the gauntlet
                    return 0;
                case 14://general graardor
                    return KillCounter.getKillCount(player, BossKillCounter.GENERAL_GRAARDOR);
                case 15://giant mole
                    return KillCounter.getKillCount(player, BossKillCounter.GIANT_MOLE);
                case 16://grotesque guardians
                    return KillCounter.getKillCount(player, BossKillCounter.GROTESQUE_GUARDIANS);
                case 17://hespori
                    return KillCounter.getKillCount(player, BossKillCounter.HESPORI);
                case 18://inferno
                    return KillCounter.getKillCount(player, BossKillCounter.ZUK);
                case 19://kalphite queen
                    return KillCounter.getKillCount(player, BossKillCounter.KALPHITE_QUEEN);
                case 20://king black dragon
                    return KillCounter.getKillCount(player, BossKillCounter.KING_BLACK_DRAGON);
                case 21://kraken
                    return KillCounter.getKillCount(player, BossKillCounter.KRAKEN);
                case 22://kree'ara
                    return KillCounter.getKillCount(player, BossKillCounter.KREEARRA);
                case 23://k'ril
                    return KillCounter.getKillCount(player, BossKillCounter.KRIL_TSUTSAROTH);
                case 24://nightmare
                    return 0;
                case 25://obor
                    return KillCounter.getKillCount(player, BossKillCounter.OBOR);
                case 26://sarachnis
                    return KillCounter.getKillCount(player, BossKillCounter.SARACHNIS);
                case 27://scorpia
                    return KillCounter.getKillCount(player, BossKillCounter.SCORPIA);
                case 28://skotizo
                    return KillCounter.getKillCount(player, BossKillCounter.SKOTIZO);
                case 29://thermo devil
                    return KillCounter.getKillCount(player, BossKillCounter.THERMONUCLEAR_SMOKE_DEVIL);
                case 30://venenatis
                    return KillCounter.getKillCount(player, BossKillCounter.VENENATIS);
                case 31://vet'ion
                    return KillCounter.getKillCount(player, BossKillCounter.VETION);
                case 32://vorkath
                    return KillCounter.getKillCount(player, BossKillCounter.VORKATH);
                case 33://wintertodt
                    return PlayerCounter.WINTERTODT_SUBDUED.get(player);
                case 34://zalcano
                    return 0;
                case 35://zulrah
                    return KillCounter.getKillCount(player, BossKillCounter.ZULRAH);
                default:
                    return 0;
            }
        }
    },
    RAIDS(472, 2, new int[]{14, 15, 16}, 40697870, 40697871, 40697872, 40697878) {
        @Override
        public int getKillCount(Player player, int slot) {
            switch (slot) {
                case 0://cox
                    return KillCounter.getKillCount(player, BossKillCounter.COX);
                case 1://tob
                    return KillCounter.getKillCount(player, BossKillCounter.TOB);
                default:
                    return 0;
            }
        }
    },
    CLUES(473, 9, new int[]{22, 30, 31}, 40697879, 40697887, 40697888, 40697880) {
        @Override
        public int getKillCount(Player player, int slot) {
            switch (slot) {
                case 0://beginner treasure trails
                    return PlayerCounter.BEGINNER_CLUES_COMPLETED.get(player);
                case 1://easy treasure trails
                    return PlayerCounter.EASY_CLUES_COMPLETED.get(player);
                case 2://medium treasure trails
                    return PlayerCounter.MEDIUM_CLUES_COMPLETED.get(player);
                case 3://hard treasure trails
                case 6://shared hard
                    return PlayerCounter.HARD_CLUES_COMPLETED.get(player);
                case 4://elite treasure trails
                case 7://shared elite
                    return PlayerCounter.ELITE_CLUES_COMPLETED.get(player);
                case 5://master treasure trails
                case 8://shared master
                    return PlayerCounter.MASTER_CLUES_COMPLETED.get(player);
                case 9://shared treasure trail rewards
                    return CollectionLog.addSumMultipleClues(player);
                default: return 0;
            }
        }
    },
    MINIGAMES(474, 17, new int[]{25, 26, 35}, 40697881, 40697882, 40697891, 40697883) {
        @Override
        public int getKillCount(Player player, int slot) {
            switch (slot) {
                case 8:
                    return PlayerCounter.MAHOGANY_HOMES_CONTRACTS.get(player);
                default:
                    return 0;
            }
        }
    },
    OTHER(475, 21, new int[]{27, 32, 33}, 40697884, 40697889, 40697890, 40697885) {
        @Override
        public int getKillCount(Player player, int slot) {
            switch (slot) {
                case 0:
                    return 0;
                case 8://glough's experiments kills
                    return 0;//;player.demonicGorillaKills.getKills() + player.torturedGorillaKills.getKills();
                case 14://revenant kills
                    return player.killCounterMap.get(SlayerKillCounter.REVENANTS.name()).getKills();//player.revenantKills.getKills();

                default:
                    return 0;
            }
        }
    };

    @Getter private int categoryStruct;
    private int count;
    private int[] childIds;
    @Getter private int[] params;
    private Map<Integer, int[]> items = new HashMap<>();
    private List<Integer> enums = new ArrayList<>();

    CollectionLogInfo(int categoryStruct, int count, int[] childIds, int...params) {
        this.categoryStruct = categoryStruct;
        this.count = count;
        this.childIds = childIds;
        this.params = params;
    }

    public void sendAccessMasks(Player player) {
        for (int i = 0; i < childIds.length; i++) {
            player.getPacketSender().sendAccessMask(Interface.COLLECTION_LOG, childIds[i], 0, count, 2);
        }
        player.getPacketSender().sendAccessMask(Interface.COLLECTION_LOG, 77, 0, 90, 2);
    }

    public void sendItems(Player player, int slot) {
        int enumId = enums.get(slot);

        int[] itemIds = getItems(enumId);

        Item[] container = new Item[itemIds.length];

        for (int index = 0; index < itemIds.length; index++) {
            int itemId = itemIds[index];
            int amount = player.getCollectionLog().getCollected(itemId);

            if (amount > 0) {
                container[index] = new Item(itemId, amount);
            }
        }

        player.getPacketSender().sendItems(-1, -1, 620, container);
    }

    public int[] getItems(int enumId) {
        return items.get(enumId);
    }

    public void sendKillCount(Player player, int slot) {
        Config.COLLECTION_LOG_KC.setInstant(player, getKillCount(player, slot));
    }

    public int getKillCount(Player player, int slot) {
        return 0;
    }

    private static final int STRUCT_LOG_SUBCATEGORY = 683;
    private static final int STRUCT_LOG_GROUP = 690;

    public static int TOTAL_COLLECTABLES;

    public static void collectAll(Player player) {
        for (CollectionLogInfo info : values()) {
            StructDefinition category = StructDefinition.get(info.getCategoryStruct());

            EnumDefinition subcategories = EnumDefinition.get(category.getInt(STRUCT_LOG_SUBCATEGORY));

            for (int slot = 0; slot < subcategories.size; slot++) {
                StructDefinition subcategory = StructDefinition.get(subcategories.getIntValuesArray()[slot]);

                EnumDefinition group = EnumDefinition.get(subcategory.getInt(STRUCT_LOG_GROUP));
                info.items.put(subcategory.getInt(STRUCT_LOG_GROUP), group.getIntValuesArray());
                info.enums.add(subcategory.getInt(STRUCT_LOG_GROUP));

                for (int index = 0; index < group.getIntValuesArray().length; index++) {
                    player.getCollectionLog().collect(group.getIntValuesArray()[index], 1);
                    ItemDefinition.get(group.getIntValuesArray()[index]).collectable = true;
                    TOTAL_COLLECTABLES++;
                }
            }
        }
    }

    static {
        for (CollectionLogInfo info : values()) {
            StructDefinition category = StructDefinition.get(info.getCategoryStruct());

            EnumDefinition subcategories = EnumDefinition.get(category.getInt(STRUCT_LOG_SUBCATEGORY));

            for (int slot = 0; slot < subcategories.size; slot++) {
                StructDefinition subcategory = StructDefinition.get(subcategories.getIntValuesArray()[slot]);

                EnumDefinition group = EnumDefinition.get(subcategory.getInt(STRUCT_LOG_GROUP));
                info.items.put(subcategory.getInt(STRUCT_LOG_GROUP), group.getIntValuesArray());
                info.enums.add(subcategory.getInt(STRUCT_LOG_GROUP));
                for (int index = 0; index < group.getIntValuesArray().length; index++) {
                    ItemDefinition.get(group.getIntValuesArray()[index]).collectable = true;
                    TOTAL_COLLECTABLES++;
                }
            }
        }
    }

}

