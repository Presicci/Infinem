package io.ruin.model.content.bestiary;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.shared.listeners.LoginListener;
import lombok.var;

import java.util.*;

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
        put("shade", new String[]{"loar shade", "phrin shade", "riyl shade", "asyn shade", "fiyr shade", "urium shade"});
        put("zombie", new String[] { "sorebones", "zombie pirate", "zombie swab", "zombies champion", "summoned zombie", "undead lumberjack" });
        put("dagannoth", new String[] { "dagannoth spawn", "dagannoth fledgeling" });
        put("kourend guard", new String[] { "soldier (tier 1)", "soldier (tier 2)", "soldier (tier 3)", "soldier (tier 4)", "soldier (tier 5)", "kourend head guard" });
        put("dwarf", new String[] { "dwarf gang member", "black guard berserker", "black guard" });
        put("kalphite", new String[] { "kalphite guardian", "kalphite soldier", "kalphite worker" });
        put("snail", new String[] { "bark blamish snail", "blood blamish snail", "myre blamish snail", "bruise blamish snail", "giant snail", "ochre blamish snail" });
        put("barbarian", new String[] { "gunthor the brave", "confused barbarian", "lost barbarian" });
        put("wizard", new String[] { "earth wizard", "fire wizard", "air wizard", "water wizard", "dark wizard", "necromancer", "invrigar the necromancer", "ancient wizard" });
        put("goblin", new String[] { "wormbrain", "goblin guard" });
        put("cave goblin", new String[] { "cave goblin guard", "cave goblin miner" });
        put("gnome", new String[] { "gnome guard", "gnome child", "gnome mage", "gnome driver", "mounted terrorbird gnome", "gnome archer", "gnome troop", "gnome woman" });
        put("pest", new String[] { "ravager", "shifter", "defiler", "spinner", "torcher", "splatter", "brawler" });
        put("rellekka denizen", new String[] { "freidir", "jennella", "borrokar" });
        put("canifis denizen", new String[] { "svetlana", "boris", "joseph", "ksenia", "lev", "milla", "imre", "zoja", "nikolai", "georgy", "yuri", "nikita", "vera",
                "alexis", "sofiya", "irina", "eduard", "galina", "liliya", "yadviga" });
        put("gielinor denizen", new String[] { "woman", "man", "broddi", "thorhild", "drunken man", "shipyard worker",
                "narf", "rusty", "penda", "ocga", "jeff", "hengel", "anja", "cuff" });
        put("nightmares", new String[] { "khazard warlord", "khazard warlord (hard)", "kamil", "kamil (hard)", "corsair traitor", "corsair traitor (hard)", "fareed", "fareed (hard)",
                "agrith naar", "agrith naar (hard)", "witch's experiment", "witch's experiment (hard)", "witch's experiment (second form)", "witch's experiment (second form) (hard)",
                "witch's experiment (third form)", "witch's experiment (third form) (hard)", "witch's experiment (fourth form)", "witch's experiment (fourth form) (hard)", "arrg", "arrg (hard)",
                "glod", "glod (hard)", "king roald", "king roald (hard)", "evil chicken", "evil chicken (hard)", "dad", "dad (hard)", "the inadequacy", "the inadequacy (hard)",
                "dessous", "dessous (hard)", "flambeed", "flambeed (hard)", "black demon (hard)", "the kendal", "the kendal (hard)", "the untouchable", "the untouchable (hard)", "nazastarool", "nazastarool (hard)",
                "gelatinnoth mother", "gelatinnoth mother (hard)", "damis", "damis (hard)", "dagannoth mother", "dagannoth mother (hard)", "sand snake", "sand snake (hard)", "tanglefoot", "tanglefoot (hard)",
                "dessourt", "dessourt (hard)", "agrith-na-na", "agrith-na-na (hard)", "chronozon", "chronozon (hard)", "slagilith", "slagilith (hard)", "count draynor", "count draynor (hard)", "giant roc", "giant roc (hard)",
                "karamel", "karamel (hard)", "treus dayth", "treus dayth (hard)", "ice troll king", "ice troll king (hard)",
                "barrelchest", "barrelchest (hard)", "trapped soul", "trapped soul (hard)", "bouncer", "bouncer (hard)", "culinaromancer", "culinaromancer (hard)",
                "me", "me (hard)", "jungle demon", "jungle demon (hard)", "giant scarab", "giant scarab (hard)", "black knight titan", "black knight titan (hard)", "the everlasting", "the everlasting (hard)", "moss guardian", "moss guardian (hard)",
        });
        put("bandit", new String[] { "black heather", "speedy keith", "bandit champion", "guard bandit" });
        put("troll", new String[] { "troll general", "kraka", "mountain troll", "pee hat", "thrower troll", "troll spectator", "stick", "berry", "twig", "kob" });
        put("ice troll", new String[] { "ice troll", "ice troll grunt", "ice troll male", "ice troll female", "ice troll runt" });
        put("ogre", new String[] { "city guard", "keef", "enclave guard", "gorad", "ogre chieftain" });
        put("black knight", new String[] { "fortress guard", "jailer" });
        put("elf warrior", new String[] { "iorwerth warrior", "elf warrior" });
        put("elf archer", new String[] { "elf archer", "iorwerth archer" });
        put("skeleton", new String[] { "skeletal miner" });
        put("pirate", new String[] { "jake", "pirate guard", "wilson", "palmer" });
        put("guard", new String[] { "tower guard", "jail guard", "radat", "poltenip" });
        put("h.A.M. Member", new String[] { "h.a.m. guard", "h.a.m. archer", "h.a.m. mage" });
        put("z.M.I. Member", new String[] { "zamorak crafter", "zamorak warrior", "zamorak ranger", "zamorak mage" });
        put("abyss monster", new String[] { "abyssal leech", "abyssal guardian", "abyssal walker" });
        put("barrows brother", new String[] { "verac the defiled", "guthan the infested", "torag the corrupted", "ahrim the blighted", "dharok the wretched", "karil the tainted" });
        put("animated armour", new String[] { "animated adamant armour", "animated bronze armour", "animated iron armour", "animated mithril armour", "animated rune armour",
                "animated steel armour"});
        put("elemental", new String[] { "fire elemental", "air elemental", "earth elemental", "water elemental" });
        put("wolf", new String[] { "white wolf", "jungle wolf", "ice wolf", "dire wolf", "desert wolf", "big wolf" });
        put("tzhaar", new String[] { "tzhaar-hur", "tzhaar-ket", "tzhaar-mej", "tzhaar-xil" });
        put("fight cave tzhaar", new String[] { "tz-kek", "ket-zek", "tz-kih", "yt-hurkot", "yt-mejkot", "tok-xil" });
        put("inferno tzhaar", new String[] { "jal-zek", "jal-akrek-xil", "jal-xil", "jal-akrek-ket", "jal-akrek-mej", "jal-mejjak", "jal-mejrah", "jal-ak", "jal-nib", "jal-imkot", "jaltok-jad" });
        put("colosseum adversary", new String[] { "fremennik warband berserker", "fremennik warband archer", "fremennik warband seer", "serpent shaman", "jaguar warrior", "javelin colossus",
                "manticore", "shockwave colossus" });
        put("barbarian spirit", new String[] { "angry barbarian spirit", "berserk barbarian spirit", "enraged barbarian spirit", "ferocious barbarian spirit" });
        put("bandosian sergeant", new String[] { "sergeant strongstack", "sergeant grimspike", "sergeant steelwill" });
    }};

    public static Map<String, String> REPLACEMENTS = new HashMap<String,String>() {{
        // Misc replacers
        put("grizzly bear", "bear");
        put("black bear", "bear");
        put("grizzly bear cub", "bear cub");
        put("tekton (enraged)", "tekton");
        put("vet'ion reborn", "vet'ion");
        put("head thief", "thief");
        put("mercenary captain", "mercenary");
        put("billy goat", "goat");
        // Champions
        put("lesser demon champion", "lesser demon");
        put("earth warrior champion", "earth warrior");
        put("ghoul champion", "ghoul");
        put("jogre champion", "jogre");
        put("imp champion", "imp");
        put("giant champion", "hill giant");
        put("goblin champion", "goblin");
        put("hobgoblin champion", "hobgoblin");
        // Superiors
        put("crushing hand", "crawling hand");
        put("chasm crawler", "cave crawler");
        put("screaming banshee", "banshee");
        put("screaming twisted banshee", "twisted banshee");
        put("giant rockslug", "rockslug");
        put("cockathrice", "cockatrice");
        put("flaming pyrelord", "pyrefiend");
        put("infernal pyrelord", "pyrelord");
        put("monstrous basilisk", "basilisk");
        put("malevolent mage", "infernal mage");
        put("insatiable bloodveld", "bloodveld");
        put("insatiable mutated bloodveld", "mutated bloodveld");
        put("vitreous jelly", "jelly");
        put("vitreous warped jelly", "warped jelly");
        put("bloodthirsty warped jelly", "warped jelly");
        put("spiked turoth", "turoth");                 //
        put("mutated terrorbird", "warped terrorbird"); //
        put("mutated tortoise", "warped tortoise");     //
        put("cave abomination", "cave horror");
        put("abhorrent spectre", "aberrant spectre");
        put("repugnant spectre", "deviant spectre");
        put("basilisk sentinel", "basilisk knight");
        put("shadow wyrm", "wyrm");                     //
        put("choke devil", "smoke devil");
        put("king kurask", "kurask");
        put("marble gargoyle", "gargoyle");
        put("nechryarch", "nechryael");
        put("chaotic death spawn", "death spawn");
        put("guardian drake", "drake");                 //
        put("greater abyssal demon", "abyssal demon");
        put("night beast", "dark beast");
        put("nuclear smoke devil", "smoke devil");
        put("colossal hydra", "hydra");                 //
        put("padulah", "");
        put("nylocas hagios", "nylocas");
        put("nylocas ischyros", "nylocas");
        put("nylocas prinkipas", "nylocas");
        put("nylocas toxobolos", "nylocas");
        put("nylocas matomenos", "nylocas");
        put("prifddinas guard", "elf warrior");
        put("tyras guard", "guard");
        put("glacies", "nex minion");
        put("fumus", "nex minion");
        put("umbra", "nex minion");
        put("cruor", "nex minion");
    }};

    public static Map<Integer, String> REPLACEMENTS_BY_ID = new HashMap<Integer,String>() {{
        put(5054, "nightmares");
        put(6326, "nightmares");
        put(12812, "colosseum adversary");  // Minotaur
        put(12813, "colosseum adversary");  // Minotaur
    }};

    private static final Set<String> BOSS = new HashSet<String>() {{
        addAll(Arrays.asList(
                "barrows brother",
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
                "deranged archaeologist",
                "zulrah",
                "penance queen"
        ));
    }};

    // mage arena 2 npcs
    private static final String[] IGNORED = {
            "scarab",
            "gadderanks",
            "acidic bloodveld",
            "colonel radick",
            "delrith",
            "evil spirit",
            "fire warrior of lesarkus", // Temple of ikov
            "forester",
            "fungi",
            "ancient fungi",
            "giant sea snake",
            "grip",             // Heroes' quest
            "head",
            "honour guard",
            "huge spider",      // PoH
            "irvig senay",      // Legends' quest
            "khazard ogre",
            "khazard scorpion",
            "khazard commander",
            "kolodion",         // Mage arena
            "leon d'cour",      // Champion's challenge
            "lucien",
            "melzard the mad",  // Melzar maze ???
            "possessed priest", // Icthlarin's little helper
            "rocnar",           //PoH
            "rowdy slave",
            "ranalph devere",   // Legends' quest
            "san tojalon",      // Legends' quest
            "sea troll queen",  // Swan song
            "shadow",           // Mourning's end part II
            "sir leye",         // Recruitment drive
            "slash bash",       // Zogre flesh eaters
            "slug prince",      // Slug menace
            "stranger",         // Desert treasure
            "suit of armour",   // Taverly dungeon door
            "swamp snake",      // Temple trekking
            "tentacle",         // Temple trekking
            "the draugen",
            "thrantax the mighty",
            "tough guy",
            "viyeldi",
            "ulfric",
            "ungadulu",
            "weaponsmaster",
            "white golem",
            "grey golem",
            "black golem",
            "alomone",
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

    public static boolean isBoss(String entry) {
        return BOSS.contains(entry);
    }

    static {
        NPCDefinition.forEach(e -> {
            if (e.combatInfo == null || e.combatLevel < 1) return;
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
            for (Map.Entry<Integer, String> replacement : REPLACEMENTS_BY_ID.entrySet()) {
                if (e.id == replacement.getKey()) {
                    name = replacement.getValue();
                }
            }
            // Replace entry names for members of categories with their category name
            cat: for (Map.Entry<String, String[]> category : CATEGORIES.entrySet()) {
                if (e.id == 6739) // Evil chicken
                    break cat;
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
        System.out.println("Loaded " + ENTRIES.size() + " bestiary entries");

        System.out.println(ENTRIES);

        LoginListener.register(player -> {
            var killcounts = player.getBestiary().getKillCounts();
            var keySet = new TreeSet<>(killcounts.keySet());
            kc : for (String entry : keySet) {
                int amt = killcounts.get(entry);
                if (!ENTRIES.contains(entry)) {
                    for (Map.Entry<String, String> replacement : REPLACEMENTS.entrySet()) {
                        if (entry.equalsIgnoreCase(replacement.getKey())) {
                            killcounts.remove(entry);
                            if (killcounts.containsKey(replacement.getValue())) {
                                amt += killcounts.get(replacement.getValue());
                            }
                            killcounts.put(replacement.getValue(), amt);
                            continue kc;
                        }
                    }
                    for (Map.Entry<String, String[]> category : CATEGORIES.entrySet()) {
                        for (String member : category.getValue()) {
                            if (entry.equalsIgnoreCase(member)) {
                                killcounts.remove(entry);
                                if (killcounts.containsKey(category.getKey())) {
                                    amt += killcounts.get(category.getKey());
                                }
                                killcounts.put(category.getKey(), amt);
                                continue kc;
                            }
                        }
                    }
                    killcounts.remove(entry);
                }
            }
        });
    }
}
