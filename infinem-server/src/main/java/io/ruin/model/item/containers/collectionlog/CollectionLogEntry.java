package io.ruin.model.item.containers.collectionlog;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.entity.player.killcount.SlayerKillCounter;
import io.ruin.model.inter.utils.Config;
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
    ABYSSAL_SIRE(11963, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.ABYSSAL_SIRE)),
    ALCHEMICAL_HYDRA(12030, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.ALCHEMICAL_HYDRA)),
    AMOXLIATL(-1, CollectionLogCategory.BOSS),
    ARAXXOR(-1, CollectionLogCategory.BOSS),
    BARROWS(11964, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.BARROWS)),
    BRYOPHYTA(11965, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.BRYOPHYTA)),
    CALLISTO_AND_ARTIO(11966, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.ARTIO),
            player -> KillCounter.getKillCount(player, BossKillCounter.CALLISTO)),
    CERBERUS(11967, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CERBERUS)),
    CHAOS_ELEMENTAL(11968, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CHAOS_ELE)),
    CHAOS_FANATIC(11969, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CHAOS_FANATIC)),
    COMMANDER_ZILYANA(11970, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.COMMANDER_ZILYANA)),
    CORPOREAL_BEAST(11971, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CORP_BEAST)),
    CRAZY_ARCHAEOLOGIST(11972, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CRAZY_ARCHAEOLOGIST)),
    DAGANNOTH_KINGS(11973, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.DAG_SUPREME),
            player -> KillCounter.getKillCount(player, BossKillCounter.DAG_PRIME),
            player -> KillCounter.getKillCount(player, BossKillCounter.DAG_REX)),
    DUKE_SUCELLUS(-1, CollectionLogCategory.BOSS),
    FIGHT_CAVES(11987, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.JAD)),
    FORTIS_COLOSSEUM(-1, CollectionLogCategory.BOSS),
    GAUNTLET(12038, CollectionLogCategory.BOSS),
    GENERAL_GRAARDOR(11974, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.GENERAL_GRAARDOR)),
    GIANT_MOLE(11975, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.GIANT_MOLE)),
    GROTESQUE_GUARDIANS(11976, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.GROTESQUE_GUARDIANS)),
    HESPORI(12033, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.HESPORI)),
    HUEYCOATL(-1, CollectionLogCategory.BOSS),
    INFERNO(11986, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.ZUK)),
    KALPHITE_QUEEN(11977, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KALPHITE_QUEEN)),
    KING_BLACK_DRAGON(11978, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KING_BLACK_DRAGON)),
    KRAKEN(11979, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KRAKEN)),
    KREEARRA(11980, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KREEARRA)),
    KRIL(11981, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.KRIL_TSUTSAROTH)),
    LEVIATHAN(-1, CollectionLogCategory.BOSS),
    MOONS_OF_PERIL(-1, CollectionLogCategory.BOSS),
    NEX(-1, CollectionLogCategory.BOSS),
    NIGHTMARE(12039, CollectionLogCategory.BOSS),
    OBOR(11982, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.OBOR)),
    PHANTOM_MUSPAH(-1, CollectionLogCategory.BOSS),
    SARACHNIS(12031, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.SARACHNIS)),
    SCORPIA(11983, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.SCORPIA)),
    SCURRIUS(-1, CollectionLogCategory.BOSS),
    SKOTIZO(11984, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.SKOTIZO)),
    TEMPOROSS(12049, CollectionLogCategory.BOSS),
    THERMONUCLEAR_SMOKE_DEVIL(11985, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.THERMONUCLEAR_SMOKE_DEVIL)),
    VARDORVIS(-1, CollectionLogCategory.BOSS),
    VENENATIS_AND_SPINDEL(11988, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.SPINDEL),
            player -> KillCounter.getKillCount(player, BossKillCounter.VENENATIS)),
    VETION_AND_CALVARION(11989, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.CALVARION),
            player -> KillCounter.getKillCount(player, BossKillCounter.VETION)),
    VORKATH(11990, CollectionLogCategory.BOSS, player -> KillCounter.getKillCount(player, BossKillCounter.VORKATH)),
    WHISPERER(-1, CollectionLogCategory.BOSS),
    WINTERTODT(11991, CollectionLogCategory.BOSS, PlayerCounter.WINTERTODT_SUBDUED::get),
    ZALCANO(11992, CollectionLogCategory.BOSS),
    ZULRAH(11993, CollectionLogCategory.BOSS),
    // Raids
    CHAMBERS_OF_XERIC(11995, CollectionLogCategory.RAIDS, player -> KillCounter.getKillCount(player, BossKillCounter.COX)),
    THEATRE_OF_BLOOD(11994, CollectionLogCategory.RAIDS, player -> KillCounter.getKillCount(player, BossKillCounter.TOB)),
    TOMBS_OF_AMASCUT(-1, CollectionLogCategory.RAIDS),
    // Clues
    BEGINNER(11996, CollectionLogCategory.CLUES, PlayerCounter.BEGINNER_CLUES_COMPLETED::get),
    EASY(11997, CollectionLogCategory.CLUES, PlayerCounter.EASY_CLUES_COMPLETED::get),
    MEDIUM(11998, CollectionLogCategory.CLUES, PlayerCounter.MEDIUM_CLUES_COMPLETED::get),
    HARD(11999, CollectionLogCategory.CLUES, PlayerCounter.HARD_CLUES_COMPLETED::get),
    ELITE(12000, CollectionLogCategory.CLUES, PlayerCounter.ELITE_CLUES_COMPLETED::get),
    MASTER(12001, CollectionLogCategory.CLUES, PlayerCounter.MASTER_CLUES_COMPLETED::get),
    HARD_RARE(12044, CollectionLogCategory.CLUES, PlayerCounter.HARD_CLUES_COMPLETED::get),
    ELITE_RARE(12045, CollectionLogCategory.CLUES, PlayerCounter.ELITE_CLUES_COMPLETED::get),
    MASTER_RARE(12046, CollectionLogCategory.CLUES, PlayerCounter.MASTER_CLUES_COMPLETED::get),
    SHARED(12002, CollectionLogCategory.CLUES, CollectionLog::addSumMultipleClues),
    // Minigames
    BARABARIAN_ASSAULT(12005, CollectionLogCategory.MINIGAMES, player -> KillCounter.getKillCount(player, BossKillCounter.PENANCE_QUEEN)),
    BRIMHAVEN_AGILITY(12043, CollectionLogCategory.MINIGAMES),
    CASTLE_WARS(12003, CollectionLogCategory.MINIGAMES),
    FISHING_TRAWLER(12009, CollectionLogCategory.MINIGAMES),
    GIANTS_FOUNDRY(-1, CollectionLogCategory.MINIGAMES),
    GNOME_RESTAURANT(12010, CollectionLogCategory.MINIGAMES),
    GUARDIANS_OF_THE_RIFT(-1, CollectionLogCategory.MINIGAMES),
    HALLOWED_SEPULCHRE(12011, CollectionLogCategory.MINIGAMES),
    LAST_MAN_STANDING(12041, CollectionLogCategory.MINIGAMES),
    MAGIC_TRAINING_ARENA(12004, CollectionLogCategory.MINIGAMES),
    MAHOGANY_HOMES(12015, CollectionLogCategory.MINIGAMES, PlayerCounter.MAHOGANY_HOMES_CONTRACTS::get),
    MASTER_MIXOLOGY(-1, CollectionLogCategory.MINIGAMES),
    PEST_CONTROL(12007, CollectionLogCategory.MINIGAMES),
    ROGUES_DEN(12012, CollectionLogCategory.MINIGAMES),
    SHADES_OF_MORTON(12006, CollectionLogCategory.MINIGAMES, PlayerCounter.PYRES_LIT::get),
    SOUL_WARS(12047, CollectionLogCategory.MINIGAMES),
    TEMPLE_TREKKING(12008, CollectionLogCategory.MINIGAMES),
    TITHE_FARM(12014, CollectionLogCategory.MINIGAMES),
    TROUBLE_BREWING(12013, CollectionLogCategory.MINIGAMES),
    VOLANIC_MINE(12042, CollectionLogCategory.MINIGAMES),
    // Other
    AERIAL_FISHING(12032, CollectionLogCategory.OTHER),
    ALL_PETS(12026, CollectionLogCategory.OTHER),
    CAMDOZAAL(12129, CollectionLogCategory.OTHER),
    CHAMPIONS_CHALLENGE(12019, CollectionLogCategory.OTHER),
    CHAOS_DRUIDS(12024, CollectionLogCategory.OTHER),
    CHOMP_BIRD_HUNTING(12028, CollectionLogCategory.OTHER),
    COLOSSAL_WYRM_AGILITY(-1, CollectionLogCategory.OTHER),
    CREATURE_CREATION(12034, CollectionLogCategory.OTHER),
    CYCLOPES(12023, CollectionLogCategory.OTHER),
    FORESTRY(-1, CollectionLogCategory.OTHER),
    FOSSIL_ISLAND_NOTES(12036, CollectionLogCategory.OTHER),
    GLOUGHS_EXPERIMENTS(12017, CollectionLogCategory.OTHER),
    HUNTER_GUILD(-1, CollectionLogCategory.OTHER),
    MONKEY_BACKPACKS(12040, CollectionLogCategory.OTHER),
    MOTHERLODE_MINE(12021, CollectionLogCategory.OTHER),
    MY_NOTES(12037, CollectionLogCategory.OTHER),
    RANDOM_EVENTS(12029, CollectionLogCategory.OTHER, PlayerCounter.RANDOM_EVENTS::get),
    REVENANTS(12016, CollectionLogCategory.OTHER, player -> KillCounter.getKillCount(player, SlayerKillCounter.REVENANTS)),
    ROOFTOP_AGILITY(12035, CollectionLogCategory.OTHER),
    SHAYZIEN_ARMOUR(12022, CollectionLogCategory.OTHER),
    SHOOTING_STARS(12048, CollectionLogCategory.OTHER),
    SKILLING_PETS(12020, CollectionLogCategory.OTHER),
    SLAYER(12018, CollectionLogCategory.OTHER),
    TORMENTED_DEMONS(-1, CollectionLogCategory.OTHER),
    TZHAAR(12027, CollectionLogCategory.OTHER),
    MISCELLANEOUS(12025, CollectionLogCategory.OTHER);

    private final Config greenLogVarbit;
    private final CollectionLogCategory category;
    private final Function<Player, Integer>[] killcounts;

    CollectionLogEntry(int greenLogVarbit, CollectionLogCategory category, Function<Player, Integer>... killcounts) {
        if (greenLogVarbit != -1) this.greenLogVarbit = Config.varpbit(greenLogVarbit, true);
        else this.greenLogVarbit = null;
        this.category = category;
        this.killcounts = killcounts;
    }

    static {
        for (CollectionLogEntry entry : CollectionLogEntry.values()) {
            entry.getCategory().getEntries().add(entry);
        }
    }
}
