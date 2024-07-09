package io.ruin.model.item.containers.collectionlog;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.entity.player.killcount.SlayerKillCounter;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/8/2024
 */
@Getter
public enum CollectionLogEntry {
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
     * 2292 - My Note
     * Script 4103 controls green names for categories
     */

    // Bosses
    ABYSSAL_SIRE(11963, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.ABYSSAL_SIRE)),
    ALCHEMICAL_HYDRA(12030, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.ALCHEMICAL_HYDRA)),
    BARROWS(11964, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.BARROWS)),
    BRYOPHYTA(11965, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.BRYOPHYTA)),
    CALLISTO(11966, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CALLISTO)),
    CERBERUS(11967, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CERBERUS)),
    CHAOS_ELEMENTAL(11968, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CHAOS_ELE)),
    CHAOS_FANATIC(11969, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CHAOS_FANATIC)),
    COMMANDER_ZILYANA(11970, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.COMMANDER_ZILYANA)),
    CORPOREAL_BEAST(11971, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CORP_BEAST)),
    CRAZY_ARCHAEOLOGIST(11972, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CRAZY_ARCHAEOLOGIST)),
    DAGANNOTH_KINGS(11973, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.DAG_SUPREME),
            player -> KillCounter.getKillCount(player, BossKillCounter.DAG_PRIME),
            player -> KillCounter.getKillCount(player, BossKillCounter.DAG_REX)),
    JAD(11987, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.JAD)),
    GAUNTLET(12038, CollectionLogInfo.BOSS),
    GENERAL_GRAARDOR(11974, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.GENERAL_GRAARDOR)),
    GIANT_MOLE(11975, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.GIANT_MOLE)),
    GROTESQUE_GUARDIANS(11976, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.GROTESQUE_GUARDIANS)),
    HESPORI(12033, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.HESPORI)),
    ZUK(11986, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.ZUK)),
    KALPHITE_QUEEN(11977, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KALPHITE_QUEEN)),
    KING_BLACK_DRAGON(11978, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KING_BLACK_DRAGON)),
    KRAKEN(11979, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KRAKEN)),
    KREEARRA(11980, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KREEARRA)),
    KRIL(11981, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KRIL_TSUTSAROTH)),
    NIGHTMARE(12039, CollectionLogInfo.BOSS),
    OBOR(11982, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.OBOR)),
    SARACHNIS(12031, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.SARACHNIS)),
    SCORPIA(11983, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.SCORPIA)),
    SKOTIZO(11984, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.SKOTIZO)),
    TEMPOROSS(12049, CollectionLogInfo.BOSS),
    THERMONUCLEAR_SMOKE_DEVIL(11985, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.THERMONUCLEAR_SMOKE_DEVIL)),
    VENENATIS(11988, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.VENENATIS)),
    VETION(11989, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.VETION)),
    VORKATH(11990, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.VORKATH)),
    WINTERTODT(11991, CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.WINTERTODT)),
    ZALCANO(11992, CollectionLogInfo.BOSS),
    ZULRAH(11993, CollectionLogInfo.BOSS),
    // Raids
    CHAMBERS_OF_XERIC(11995, CollectionLogInfo.RAIDS, player -> KillCounter.getKillCount(player, BossKillCounter.COX)),
    THEATRE_OF_BLOOD(11994, CollectionLogInfo.RAIDS, player -> KillCounter.getKillCount(player, BossKillCounter.TOB)),
    // Clues
    BEGINNER(11996, CollectionLogInfo.CLUES, PlayerCounter.BEGINNER_CLUES_COMPLETED::get),
    EASY(11997, CollectionLogInfo.CLUES, PlayerCounter.EASY_CLUES_COMPLETED::get),
    MEDIUM(11998, CollectionLogInfo.CLUES, PlayerCounter.MEDIUM_CLUES_COMPLETED::get),
    HARD(11999, CollectionLogInfo.CLUES, PlayerCounter.HARD_CLUES_COMPLETED::get),
    ELITE(12000, CollectionLogInfo.CLUES, PlayerCounter.ELITE_CLUES_COMPLETED::get),
    MASTER(12001, CollectionLogInfo.CLUES, PlayerCounter.MASTER_CLUES_COMPLETED::get),
    HARD_RARE(12044, CollectionLogInfo.CLUES, PlayerCounter.HARD_CLUES_COMPLETED::get),
    ELITE_RARE(12045, CollectionLogInfo.CLUES, PlayerCounter.ELITE_CLUES_COMPLETED::get),
    MASTER_RARE(12046, CollectionLogInfo.CLUES, PlayerCounter.MASTER_CLUES_COMPLETED::get),
    SHARED(12002, CollectionLogInfo.CLUES, CollectionLog::addSumMultipleClues),
    // Minigames
    BARABARIAN_ASSAULT(12005, CollectionLogInfo.MINIGAMES),
    BRIMHAVEN_AGILITY(12043, CollectionLogInfo.MINIGAMES),
    CASTLE_WARS(12003, CollectionLogInfo.MINIGAMES),
    FISHING_TRAWLER(12009, CollectionLogInfo.MINIGAMES),
    GNOME_RESTAURANT(12010, CollectionLogInfo.MINIGAMES),
    HALLOWED_SEPULCHRE(12011, CollectionLogInfo.MINIGAMES),
    LAST_MAN_STANDING(12041, CollectionLogInfo.MINIGAMES),
    MAGIC_TRAINING_ARENA(12004, CollectionLogInfo.MINIGAMES),
    MAHOGANY_HOMES(12015, CollectionLogInfo.MINIGAMES, PlayerCounter.MAHOGANY_HOMES_CONTRACTS::get),
    PEST_CONTROL(12007, CollectionLogInfo.MINIGAMES),
    ROGUES_DEN(12012, CollectionLogInfo.MINIGAMES),
    SHADES_OF_MORTON(12006, CollectionLogInfo.MINIGAMES),
    SOUL_WARS(12047, CollectionLogInfo.MINIGAMES),
    TEMPLE_TREKKING(12008, CollectionLogInfo.MINIGAMES),
    TITHE_FARM(12014, CollectionLogInfo.MINIGAMES),
    TROUBLE_BREWING(12013, CollectionLogInfo.MINIGAMES),
    VOLANIC_MINE(12042, CollectionLogInfo.MINIGAMES),
    // Other
    AERIAL_FISHING(12032, CollectionLogInfo.OTHER),
    ALL_PETS(12026, CollectionLogInfo.OTHER),
    CAMDOZAAL(12129, CollectionLogInfo.OTHER),
    CHAMPIONS_CHALLENGE(12019, CollectionLogInfo.OTHER),
    CHAOS_DRUIDS(12024, CollectionLogInfo.OTHER),
    CHOMP_BIRD_HUNTING(12028, CollectionLogInfo.OTHER),
    CREATURE_CREATION(12034, CollectionLogInfo.OTHER),
    CYCLOPES(12023, CollectionLogInfo.OTHER),
    FOSSIL_ISLAND_NOTES(12036, CollectionLogInfo.OTHER),
    GLOUGHS_EXPERIMENTS(12017, CollectionLogInfo.OTHER),
    MONKEY_BACKPACKS(12040, CollectionLogInfo.OTHER),
    MOTHERLODE_MINE(12021, CollectionLogInfo.OTHER),
    MY_NOTES(12037, CollectionLogInfo.OTHER),
    RANDOM_EVENTS(12029, CollectionLogInfo.OTHER),
    REVENANTS(12016, CollectionLogInfo.OTHER, player -> player.killCounterMap.get(SlayerKillCounter.REVENANTS.name()).getKills()),
    ROOFTOP_AGILITY(12035, CollectionLogInfo.OTHER),
    SHAYZIEN_ARMOUR(12022, CollectionLogInfo.OTHER),
    SHOOTING_STARS(12048, CollectionLogInfo.OTHER),
    SKILLING_PETS(12020, CollectionLogInfo.OTHER),
    SLAYER(12018, CollectionLogInfo.OTHER),
    TZHAAR(12027, CollectionLogInfo.OTHER),
    MISCELLANEOUS(12025, CollectionLogInfo.OTHER);

    private final int greenLogVarbit;
    private final CollectionLogInfo category;
    private final Function<Player, Integer>[] killcounts;

    CollectionLogEntry(int greenLogVarbit, CollectionLogInfo category, Function<Player, Integer>... killcounts) {
        this.greenLogVarbit = greenLogVarbit;
        this.category = category;
        this.killcounts = killcounts;
    }

    static {
        for (CollectionLogEntry entry : CollectionLogEntry.values()) {
            entry.getCategory().getEntries().add(entry);
        }
    }
}
