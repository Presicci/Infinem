package io.ruin.model.content.bestiary;

import io.ruin.api.utils.Tuple;
import io.ruin.cache.NPCDef;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/17/2023
 */
public class BestiaryDef {
    /*
    Factions:
    Goblins vs. H.A.M.

    White knights vs. Black knights
        Lord Daquarius in Black Knights' Base
        White/Black equipment
        Initiate
        Proselyte

    Ogres for Yanille
    Trolls for Burthorpe
    Elves for Ardougne
    Pests for Void knights
    Tzhaar for
    Ice Trolls for Neitiznot
        Helm of Neitiznot
    Desert mining camp for
    Dagannoths for Fremennik?
        Fremennik equipment
        Helms
    Vampyres for Burgh de rott
    */
    /*
    Look into when get internet:
    Air elemental
    Alomone - Hazeel cult leader, wizard?
    Armadylean guard
     */
    // Dwarfs->Dwarves
    public static final Set<String> ENTRIES = new HashSet<>();

    private static final String[] INFERNO_MONSTERS = {
            "jal-zek", "jal-akrek-xil", "jal-xil", "jal-akrek-ket", "jal-akrek-mej", "jal-mejjak", "jal-mejrah", "jal-ak", "jal-nib", "jal-imkot", "jaltok-jad"
    };

    private static final String[] FIGHT_CAVE_MONSTERS = {
            "tz-kek", "ket-zek", "tz-kih", "yt-hurkot", "yt-mejkot", "tok-xil"
    };

    private static final String[] TZHAAR = {
            "tzhaar-hur", "tzhaar-mej", "tzhaar-ket", "tzhaar-xil"
    };

    private static final String[] FREMENNIK = {
            "market guard", "borrokar", "jennella", "freidir", "lanzig"
    };

    private static final String[] THUGS = {
            "jonny the beard", "menaphite thug", "cuffs"
    };

    private static final String[] DESERT_MINING_CAMP = {
            "mercenary"
    };

    private static final String[] ZMI = {
            "zamorak crafter", "zamorak warrior", "zamorak ranger"
    };

    private static final String[] MENAPHITES = {
            "tough guy"
    };

    private static final String[] ABYSSAL_MONSTERS = {
            "abyssal leech", "abyssal walker"
    };

    private static final String[] BOSS_BODYGUARDS = {
            "tstanon karlak", "zakl'n gritch", "balfrug kreeyath",
            "sergeant steelwill", "sergeant strongstack", "sergeant grimspike",
            "bree", "growler", "starlight",
            "flight kilisa", "wingman skree", "flockleader geerin"
    };

    public static Map<String, String[]> CATEGORIES = new HashMap<String, String[]>() {{
        put("zombie", new String[] { "sorebones", "zombie pirate", "zombie swab", "zombies champion", "summoned zombie" });
        put("dagannoth", new String[] { "dagannoth spawn", "dagannoth fledgeling" });
        put("kourend guard", new String[] { "soldier (tier 1)", "soldier (tier 2)", "soldier (tier 3)", "soldier (tier 4)", "soldier (tier 5)", "kourend head guard" });
        put("dwarf", new String[] { "dwarf gang member", "black guard berserker", "black guard" });
        put("kalphite", new String[] { "kalphite guardian", "kalphite soldier", "kalphite worker" });
        put("snail", new String[] { "bark blamish snail", "blood blamish snail", "myre blamish snail", "bruise blamish snail", "giant snail", "ochre blamish snail" });
        put("barbarian", new String[] { "gunthor the brave" });
        put("wizard", new String[] { "earth wizard", "fire wizard", "air wizard", "water wizard", "dark wizard", "necromancer", "invrigar the necromancer", "ancient wizard" });
        put("vampyre", new String[] { "vampyre juvenile", "vampyre juvinate", "feral vampyre" });
        put("goblin", new String[] { "wormbrain", "goblin guard" });
        put("gnome", new String[] { "gnome guard", "gnome child", "gnome mage", "gnome driver", "mounted terrorbird gnome", "gnome archer", "gnome troop", "gnome woman" });
        put("pest", new String[] { "ravager", "shifter", "defiler", "spinner", "torcher", "splatter", "brawler" });
        put("civilian", new String[] { "woman", "man", "svetlana", "boris", "broddi", "thorhild", "joseph", "ksenia", "milla", "lev", "imre", "galina", "drunken man", "shipyard worker",
                "narf", "zoja", "yadviga", "rusty", "irina", "alexis", "eduard", "nikolai", "penda", "nikita", "vera", "ocga", "sofiya", "liliya", "yuri", "georgy",
                "jeff" });
        put("nightmares", new String[] { "khazard warlord (hard)", "kamil (hard)", "corsair traitor", "fareed (hard)", "agrith naar (hard)", "witch's experiment (hard)", "arrg (hard)",
                "glod (hard)", "king roald (hard)", "evil chicken (hard)", "dad (hard)", "witch's experiment (third form) (hard)", "the inadequacy (hard)",
                "dessous (hard)", "flambeed (hard)", "black demon (hard)", "the kendal (hard)", "the untouchable (hard)", "nazastarool (hard)",
                "gelatinnoth mother (hard)", "damis (hard)", "corsair traitor (hard)", "dagannoth mother (hard)", "sand snake (hard)", "tanglefoot (hard)",
                "dessourt (hard)", "agrith-na-na (hard)", "chronozon (hard)", "slagilith (hard)", "count draynor (hard)", "giant roc (hard)",
                "karamel (hard)", "skeleton hellhound (hard)", "witch's experiment (fourth form) (hard)", "treus dayth (hard)", "ice troll king (hard)",
                "barrelchest (hard)", "trapped soul (hard)", "bouncer (hard)", "culinaromancer (hard)", "witch's experiment (second form) (hard)",
                "me (hard)", "jungle demon (hard)", "giant scarab (hard)", "black knight titan (hard)", "the everlasting (hard)", "agrith naar", "agrith-na-na",
                "arrg" });
        put("revenant", new String[] { "revenant imp", "revenant pyrefiend", "revenant knight", "revenant cyclops", "revenant hellhound", "revenant dragon", "revenant demon",
                "revenant dark beast", "revenant ork" });
        put("bandit", new String[] { "black heather", "speedy keith", "bandit champion", "guard bandit" });
        put("troll", new String[] { "troll general", "kraka", "mountain troll", "pee hat", "thrower troll", "troll spectator", "stick", "berry", "twig", "kob" });
        put("ice troll", new String[] { "ice troll", "ice troll grunt", "ice troll male", "ice troll female", "ice troll runt" });
        put("ogre", new String[] { "city guard", "keef", "enclave guard", "gorad", "ogre chieftain" });
        put("black knight", new String[] { "fortress guard", "jailer" });
        put("elf", new String[] { "iorwerth warrior", "elf warrior", "elf archer", "iorwerth archer" });
        put("skeleton", new String[] { "skeletal miner" });
        put("pirate", new String[] { "jake", "pirate guard", "wilson", "palmer" });
        put("guard", new String[] { "tower guard", "jail guard", "radat", "poltenip" });
        put("h.A.M. member", new String[] { "h.a.m. guard", "h.a.m. archer", "h.a.m. mage" });
        put("abyss monster", new String[] { "abyssal leech", "abyssal guardian", "abyssal walker" });
        put("barrows brothers", new String[] { "verac the defiled", "guthan the infested", "torag the corrupted", "ahrim the blighted", "dharok the wretched", "karil the tainted" });
        put("animated armour", new String[] { "animated adamant armour", "animated bronze armour", "animated iron armour", "animated mithril armour", "animated rune armour",
                "animated steel armour"});
    }};

    public static Map<String, String> REPLACEMENTS = new HashMap<String,String>() {{
        // Misc replacers
        put("tekton (enraged)", "tekton");
        put("vet'ion reborn", "vet'ion");
        put("head thief", "thief");
        // Champions
        put("lesser demon champion", "lesser demon");
        put("earth warrior champion", "earth warrior");
        put("ghoul champion", "ghoul");
        put("jogre champion", "jogre");
        put("imp champion", "imp");
        put("giant champion", "hill giant");
        put("goblin champion", "goblin");
        // Superiors
        put("king kurask", "kurask");
        put("greater nechryael", "nechryael");
        put("greater abyssal demon", "abyssal demon");
        put("repugnant spectre", "deviant spectre");
        put("chaotic death spawn", "death spawn");
        put("flaming pyrelord", "pyrefiend");
        put("screaming banshee", "banshee");
        put("infernal pyrelord", "pyrelord");
        put("insatiable mutated bloodveld", "mutated bloodveld");
        put("cave abomination", "cave horror");
        put("abhorrent spectre", "aberrant spectre");
        put("cockathrice", "cockatrice");
        put("giant rockslug", "rockslug");
        put("malevolent mage", "infernal mage");
        put("insatiable bloodveld", "bloodveld");
        put("vitreous warped jelly", "warped jelly");
        put("chasm crawler", "cave crawler");
        put("monstrous basilisk", "basilisk");
        put("marble gargoyle", "gargoyle");
        put("choke devil", "smoke devil");
        put("nechryarch", "nechryael");
        put("crushing hand", "crawling hand");
        put("screaming twisted banshee", "twisted banshee");
        put("deviant spectre", "aberrant spectre");
        put("vitreous jelly", "jelly");
        put("albino bat", "bat");
    }};

    private static final String[] BOSS = {
            "general graardor",
            "commander zilyana",
            "k'ril tsutsaroth",
            "kree'arra",
            "kaal-ket-jor",
            "dagannoth supreme",
            "dagannoth prime",
            "dagannoth rex",
            "brutal lava dragon",
            "hespori",
            "abyssal sire",
            "callisto",
            "king black dragon",
            "tekton",
            "muttadile",
            "great olm",
            "vespula",
            "vasa nistirio",
            "alchemical hydra",
            "corrupted nechryarch",
            "the maiden of sugadinti",
            "tztok-jad",
            "obor",
            "galvek",
            "scorpia",
            "vet'ion",
            "venenatis",
            "chaos fanatic",
            "chaos elemental",
            "sarachnis",
            "vorkath",
            "giant mole",
            "bryophyta",
            "ket'ian",
            "kraken",
            "thermonuclear smoke devil",
            "corporeal beast",
            "elvarg",
            "cerberus",
            "skotizo",
            "kalphite queen",
            "tzkal-zuk",
            "crazy archaeologist",
            "zulrah"
    };

    private static final String[] IGNORED = {
            "angry bear",
            "angry giant rat",
            "angry goblin",
            "angry unicorn",
            "reanimated kalphite",
            "reanimated demon",
            "reanimated imp",
            "reanimated ogre",
            "reanimated troll",
            "reanimated bear",
            "reanimated minotaur",
            "reanimated elf",
            "reanimated bloodveld",
            "reanimated monkey",
            "reanimated dagannoth",
            "reanimated chaos druid",
            "reanimated abyssal",
            "reanimated horror",
            "reanimated unicorn",
            "reanimated scorpion",
            "reanimated dog",
            "reanimated dragon",
            "reanimated tzhaar",
            "reanimated giant",
            "pyromancer",
            "incapacitated pyromancer",
            "count check",
            "foreman",
            "piglet",
            "pig",
            "great olm (left claw)",
            "great olm (right claw)",
            "<col=00ffff>combat dummy</col>",
            "<col=00ffff>ornate combat dummy</col>",
            "<col=00ffff>ornate kurask combat dummy</col>",
            "<col=00ffff>ornate wilderness combat dummy</col>",
            "<col=00ffff>ornate kalphite combat dummy</col>",
            "<col=00ffff>ornate undead combat dummy</col>",
            "<col=00ffff>undead combat dummy</col>",
            "<col=00ffff>abyssal portal</col>",
            "<col=00ffff>glowing crystal</col>",
            "<col=00ffff>rocks</col>",
            "<col=ff9040>energy ball</col>",
            "<col=00ffff>rocky support</col>",
            "<col=00ffff>ancestral glyph</col>",
            "<col=00ffff>guardian</col>",
            "awakened altar",
            "whirlpool",
            "bardur",
            "altar",
            "suspicious water",
            "cecilia",
            "apmeken",
            "shop assistant",
            "respiratory system",
            "valaine",
            "sraracha",
            "lieve mccracken",
            "einar",
            "alrik",
            "me",
            "null",
            "scavvo",
            "ragnvald",
            "valgerd",
            "corrupted scavenger",
            "rannveig",
            "fishing spot",
            "bologa",
            "mary",
            "a doubt",
            "flower",
            "spawn of sarachnis",
            "enormous tentacle",
            "reanimated demon spawn",
            "growthling"
    };

    private static final String[] TRIM = {
            "superior"
    };

    static {
        System.out.println("Loading Bestiary..." + System.currentTimeMillis());
        NPCDef.forEach(e -> {
            String name = e.name.toLowerCase();
            for (String ignored : IGNORED) {
                if (name.equalsIgnoreCase(ignored))
                    return;
            }
            for (String trim : TRIM) {
                name = name.replace(trim, "");
            }
            // Replace entry name for some outliers
            for (Map.Entry<String, String> replacement : REPLACEMENTS.entrySet()) {
                if (name.equalsIgnoreCase(replacement.getKey())) {
                    name = replacement.getValue();
                }
            }
            // Replace entry names for members of categories with their category name
            cat: for (Map.Entry<String, String[]> category : CATEGORIES.entrySet()) {
                for (String member : category.getValue()) {
                    if (name.equalsIgnoreCase(member)) {
                        name = category.getKey();
                        break cat;
                    }
                }
            }
            e.bestiaryEntry = name;
            if (e.combatInfo != null)
                ENTRIES.add(name);
        });
        System.out.println("Done loading bestiary." + System.currentTimeMillis());
    }
}
