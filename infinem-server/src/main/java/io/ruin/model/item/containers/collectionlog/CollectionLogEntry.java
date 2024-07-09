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
    ABYSSAL_SIRE(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.ABYSSAL_SIRE)),
    ALCHEMICAL_HYDRA(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.ALCHEMICAL_HYDRA)),
    BARROWS(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.BARROWS)),
    BRYOPHYTA(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.BRYOPHYTA)),
    CALLISTO(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CALLISTO)),
    CERBERUS(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CERBERUS)),
    CHAOS_ELEMENTAL(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CHAOS_ELE)),
    CHAOS_FANATIC(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CHAOS_FANATIC)),
    COMMANDER_ZILYANA(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.COMMANDER_ZILYANA)),
    CORPOREAL_BEAST(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CORP_BEAST)),
    CRAZY_ARCHAEOLOGIST(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CRAZY_ARCHAEOLOGIST)),
    DAGANNOTH_KINGS(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.DAG_SUPREME),
            player -> KillCounter.getKillCount(player, BossKillCounter.DAG_PRIME),
            player -> KillCounter.getKillCount(player, BossKillCounter.DAG_REX)),
    JAD(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.JAD)),
    GAUNTLET(CollectionLogInfo.BOSS),
    GENERAL_GRAARDOR(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.GENERAL_GRAARDOR)),
    GIANT_MOLE(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.GIANT_MOLE)),
    GROTESQUE_GUARDIANS(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.GROTESQUE_GUARDIANS)),
    HESPORI(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.HESPORI)),
    ZUK(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.ZUK)),
    KALPHITE_QUEEN(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KALPHITE_QUEEN)),
    KING_BLACK_DRAGON(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KING_BLACK_DRAGON)),
    KRAKEN(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KRAKEN)),
    KREEARRA(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KREEARRA)),
    KRIL(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KRIL_TSUTSAROTH)),
    NIGHTMARE(CollectionLogInfo.BOSS),
    OBOR(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.OBOR)),
    SARACHNIS(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.SARACHNIS)),
    SCORPIA(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.SCORPIA)),
    SKOTIZO(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.SKOTIZO)),
    TEMPOROSS(CollectionLogInfo.BOSS),
    THERMONUCLEAR_SMOKE_DEVIL(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.THERMONUCLEAR_SMOKE_DEVIL)),
    VENENATIS(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.VENENATIS)),
    VETION(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.VETION)),
    VORKATH(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.VORKATH)),
    WINTERTODT(CollectionLogInfo.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.WINTERTODT)),
    ZALCANO(CollectionLogInfo.BOSS),
    ZULRAH(CollectionLogInfo.BOSS),
    // Raids
    CHAMBERS_OF_XERIC(CollectionLogInfo.RAIDS, player -> KillCounter.getKillCount(player, BossKillCounter.COX)),
    THEATRE_OF_BLOOD(CollectionLogInfo.RAIDS, player -> KillCounter.getKillCount(player, BossKillCounter.TOB)),
    // Clues
    BEGINNER(CollectionLogInfo.CLUES, PlayerCounter.BEGINNER_CLUES_COMPLETED::get),
    EASY(CollectionLogInfo.CLUES, PlayerCounter.EASY_CLUES_COMPLETED::get),
    MEDIUM(CollectionLogInfo.CLUES, PlayerCounter.MEDIUM_CLUES_COMPLETED::get),
    HARD(CollectionLogInfo.CLUES, PlayerCounter.HARD_CLUES_COMPLETED::get),
    ELITE(CollectionLogInfo.CLUES, PlayerCounter.ELITE_CLUES_COMPLETED::get),
    MASTER(CollectionLogInfo.CLUES, PlayerCounter.MASTER_CLUES_COMPLETED::get),
    HARD_RARE(CollectionLogInfo.CLUES, PlayerCounter.HARD_CLUES_COMPLETED::get),
    ELITE_RARE(CollectionLogInfo.CLUES, PlayerCounter.ELITE_CLUES_COMPLETED::get),
    MASTER_RARE(CollectionLogInfo.CLUES, PlayerCounter.MASTER_CLUES_COMPLETED::get),
    SHARED(CollectionLogInfo.CLUES, CollectionLog::addSumMultipleClues),
    // Minigames
    BARABARIAN_ASSAULT(CollectionLogInfo.MINIGAMES),
    BRIMHAVEN_AGILITY(CollectionLogInfo.MINIGAMES),
    CASTLE_WARS(CollectionLogInfo.MINIGAMES),
    FISHING_TRAWLER(CollectionLogInfo.MINIGAMES),
    GNOME_RESTAURANT(CollectionLogInfo.MINIGAMES),
    HALLOWED_SEPULCHRE(CollectionLogInfo.MINIGAMES),
    LAST_MAN_STANDING(CollectionLogInfo.MINIGAMES),
    MAGIC_TRAINING_ARENA(CollectionLogInfo.MINIGAMES),
    MAHOGANY_HOMES(CollectionLogInfo.MINIGAMES, PlayerCounter.MAHOGANY_HOMES_CONTRACTS::get),
    PEST_CONTROL(CollectionLogInfo.MINIGAMES),
    ROGUES_DEN(CollectionLogInfo.MINIGAMES),
    SHADES_OF_MORTON(CollectionLogInfo.MINIGAMES),
    SOUL_WARS(CollectionLogInfo.MINIGAMES),
    TEMPLE_TREKKING(CollectionLogInfo.MINIGAMES),
    TITHE_FARM(CollectionLogInfo.MINIGAMES),
    TROUBLE_BREWING(CollectionLogInfo.MINIGAMES),
    VOLANIC_MINE(CollectionLogInfo.MINIGAMES),
    // Other
    AERIAL_FISHING(CollectionLogInfo.OTHER),
    ALL_PETS(CollectionLogInfo.OTHER),
    CAMDOZAAL(CollectionLogInfo.OTHER),
    CHAMPIONS_CHALLENGE(CollectionLogInfo.OTHER),
    CHAOS_DRUIDS(CollectionLogInfo.OTHER),
    CHOMP_BIRD_HUNTING(CollectionLogInfo.OTHER),
    CREATURE_CREATION(CollectionLogInfo.OTHER),
    CYCLOPES(CollectionLogInfo.OTHER),
    FOSSIL_ISLAND_NOTES(CollectionLogInfo.OTHER),
    GLOUGHS_EXPERIMENTS(CollectionLogInfo.OTHER),
    MONKEY_BACKPACKS(CollectionLogInfo.OTHER),
    MOTHERLODE_MINE(CollectionLogInfo.OTHER),
    MY_NOTES(CollectionLogInfo.OTHER),
    RANDOM_EVENTS(CollectionLogInfo.OTHER),
    REVENANTS(CollectionLogInfo.OTHER, player -> player.killCounterMap.get(SlayerKillCounter.REVENANTS.name()).getKills()),
    ROOFTOP_AGILITY(CollectionLogInfo.OTHER),
    SHAYZIEN_ARMOUR(CollectionLogInfo.OTHER),
    SHOOTING_STARS(CollectionLogInfo.OTHER),
    SKILLING_PETS(CollectionLogInfo.OTHER),
    SLAYER(CollectionLogInfo.OTHER),
    TZHAAR(CollectionLogInfo.OTHER),
    MISCELLANEOUS(CollectionLogInfo.OTHER);

    private final CollectionLogInfo category;
    private final Function<Player, Integer>[] killcounts;

    CollectionLogEntry(CollectionLogInfo category, Function<Player, Integer>... killcounts) {
        this.category = category;
        this.killcounts = killcounts;
    }

    static {
        for (CollectionLogEntry entry : CollectionLogEntry.values()) {
            entry.getCategory().getEntries().add(entry);
        }
    }
}
