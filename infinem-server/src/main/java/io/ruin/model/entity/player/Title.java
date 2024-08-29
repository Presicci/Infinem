package io.ruin.model.entity.player;

import io.ruin.api.protocol.world.WorldType;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.utility.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Title {

    /**
     * !!! BE CAREFUL EDITING THIS! DO NOT REMOVE ENTRIES FROM THIS ARRAY !!!
     * Player files save an index to it. If you want to remove a title, set its entry to null.
     * ALWAYS add new ones at the BOTTOM!
     * Remember to close tags and add spaces where necessary :)
     */
    public static final Title[] PRESET_TITLES = {
            // Staff
            prefixTitle("<col=0da8af><shad=000000>Owner </col></shad>").setPredicate(p -> p.isGroup(PlayerGroup.OWNER)).setHidden(),
            prefixTitle("<col=ffff00><shad=000000>Admin </col></shad>").setPredicate(p -> p.isGroup(PlayerGroup.ADMINISTRATOR)).setHidden(),
            prefixTitle("<col=a239db><shad=000000><img=31>Developer </shad></col>").setPredicate(p -> p.isGroup(PlayerGroup.ADMINISTRATOR)).setHidden(),
            prefixTitle(colorAndShadow("d6daff", "000000", "Moderator ")).setPredicate(p -> p.isGroup(PlayerGroup.MODERATOR)).setHidden(),
            prefixTitle(colorAndShadow("0033cc", "000000", "Support ")).setPredicate(p -> p.isGroup(PlayerGroup.SUPPORT)).setHidden(),
            // Donator
            prefixTitle(colorAndShadow("E79B3A", "000000",  "Zenyte ")).setPredicate(p -> p.isGroup(PlayerGroup.ZENYTE)).setHidden(),
            prefixTitle(colorAndShadow("444444", "000000",  "Onyx ")).setPredicate(p -> p.isGroup(PlayerGroup.ONYX)).setHidden(),
            prefixTitle(colorAndShadow("aa00aa", "000000",  "Dragonstone ")).setPredicate(p -> p.isGroup(PlayerGroup.DRAGONSTONE)).setHidden(),
            prefixTitle(colorAndShadow("dddddd", "000000",  "Diamond ")).setPredicate(p -> p.isGroup(PlayerGroup.DIAMOND)).setHidden(),
            prefixTitle(colorAndShadow("ff5555", "000000",  "Ruby ")).setPredicate(p -> p.isGroup(PlayerGroup.RUBY)).setHidden(),
            prefixTitle(colorAndShadow("55ff55", "000000",  "Emerald ")).setPredicate(p -> p.isGroup(PlayerGroup.EMERALD)).setHidden(),
            prefixTitle(colorAndShadow("5555ff", "000000",  "Sapphire ")).setPredicate(p -> p.isGroup(PlayerGroup.SAPPHIRE)).setHidden(),
            // Ironman
            prefixTitle(colorAndShadow("9e9e9e", "000000", "Ironman ")).setPredicate(p -> p.getGameMode() == GameMode.IRONMAN).setHidden(),
            prefixTitle(colorAndShadow("681212", "000000", "Hardcore Ironman ")).setPredicate(p -> p.getGameMode().isHardcoreIronman()).setHidden(),
            prefixTitle(colorAndShadow("bfbfbf", "000000",  "Ultimate Ironman ")).setPredicate(p -> p.getGameMode().isUltimateIronman()).setHidden(),

            prefixTitle(color("13a5f9", "Newbie ")).setUnlockDescription(""),
            prefixTitle(colorAndShadow("b36b00", "000000", "JalYt ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(429)).setUnlockDescription("Equip an Infernal cape"),
            prefixTitle(colorAndShadow("ff0000", "000000", "Final Boss ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1022)).setUnlockDescription("Log 10,000 boss kills"),
            prefixTitle(colorAndShadow("5380d4", "000000", "Angler ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1034)).setUnlockDescription("Catch 100,000 fish"),
            prefixTitle(colorAndShadow("a12409", "000000", "Lumberjack ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1038)).setUnlockDescription("Chop 100,000 logs"),
            prefixTitle(colorAndShadow("ebd338", "000000", "Prospector ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1042)).setUnlockDescription("Mine 100,000 ore"),
            prefixTitle(colorAndShadow("990505", "000000", "Slayer ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1043)).setUnlockDescription("Complete 500 slayer tasks"),
            prefixTitle(colorAndShadow("eddddd", "000000", "Agile ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1089)).setUnlockDescription("Complete 5,000 agility laps"),
            prefixTitle(colorAndShadow("696464", "000000", "Blacksmith ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1049)).setUnlockDescription("Smith 100,000 bars"),
            prefixTitle(colorAndShadow("0e961b", "000000", "Herbalist ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1053)).setUnlockDescription("Make 25,000 potions"),
            prefixTitle(colorAndShadow("e3c254", "000000", "Gourmet ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1057)).setUnlockDescription("Cook 100,000 food"),
            prefixTitle(colorAndShadow("40403f", "000000", "Thief ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1061)).setUnlockDescription("Steal 100,000 times"),
            prefixTitle(colorAndShadow("bf5821", "000000", "Pyromancer ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1065)).setUnlockDescription("Burn 25,000 logs"),
            prefixTitle(colorAndShadow("db21d5", "000000", "Jeweler ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1069)).setUnlockDescription("Cut 100,000 gems"),
            prefixTitle(colorAndShadow("b55409", "000000", "Fletcher ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1073)).setUnlockDescription("Fletch 100,000 logs"),
            prefixTitle(colorAndShadow("8fd132", "000000", "Farmer ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1077)).setUnlockDescription("Plant seeds in 1,500 patches"),
            prefixTitle(colorAndShadow("9c8b83", "000000", "Runed ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1081)).setUnlockDescription("Craft 250,000 runes"),
            prefixTitle(colorAndShadow("7a3e20", "000000", "Hunter ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1086)).setUnlockDescription("Catch 50,000 hunter creatures"),
            prefixTitle(colorAndShadow("cfa45b", "000000", "Clue Hunter ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1104)).setUnlockDescription("Complete 5,000 clues"),
            prefixTitle(colorAndShadow("db4425", "000000", "Crab Wrangler ")).setPredicate(p -> p.getTaskManager().hasCompletedTask(1030)).setUnlockDescription("Kill 5,000 crabs"),
            prefixTitle(colorAndShadow("bfbfbf", "000000", "<img=119>Dice Host ")).setPredicate(p -> p.diceHost).setUnlockDescription("Purchase a Dice Bag"),
            prefixTitle(colorAndShadow("FF2d00", "000000", "Custom Title ")).setPredicate(p -> p.hasCustomTitle).setUnlockDescription("Purchase Custom Title from the Store")
    };

    public static String color(String color, String text) {
        return "<col=" + color + ">" + text + "</col>";
    }

    public static String shadow(String color, String text) {
        return "<shad=" + color + ">" + text + "</shad>";
    }

    public static String colorAndShadow(String color, String shadow, String text) {
        return color(color, shadow(shadow, text));
    }

    private static List<Title> sortedList = new ArrayList<>();
    static {
        for (int i = 0; i < PRESET_TITLES.length; i++) {
            if (PRESET_TITLES[i] == null)
                continue;
            PRESET_TITLES[i].id = i;
            sortedList.add(PRESET_TITLES[i]);
        }
        sortedList.sort(Title::compare);
    }

    public static void openSelection(Player player, boolean showLocked) {
        List<Option> options = sortedList.stream()
                .filter(t -> (showLocked && !t.hidden) || t.canUse(player))
                .filter(Title::activeOnThisWorld)
                .map(t -> new Option((t.getPrefix() == null ? "" : t.getPrefix())
                        + player.getName()
                        + (t.getSuffix() == null ? "" : t.getSuffix())
                        + ((t.unlockDescription != null && !t.unlockDescription.isEmpty()) ? (" (" + t.unlockDescription + ")") : ""), () -> {
                    select(player, t);
                })).collect(Collectors.toList());
        if (options.size() == 0) {
            player.dialogue(new NPCDialogue(1307, "You haven't unlocked any titles yet!"));
            return;
        }
        OptionScroll.open(player, "Choose your title", false, options);
    }

    private static String[] bannedTitles = {"admin", "administrator", "owner", "mod"};
    private static void select(Player player, Title title) {
        if (!title.canUse(player)) {
            player.dialogue(new MessageDialogue("You don't have access to this title.<br><br>Unlock condition: " + title.unlockDescription));
            return;
        }
        if (title.unlockDescription.contains("Custom")) {
            player.dialogue(new MessageDialogue("Unlock condition:<br>" + title.unlockDescription));
            player.stringInput("Enter your custom title:<br>12 character max!", newTitle -> {
                if (newTitle.length() > 12)
                    player.retryStringInput("Your title cannot be more than 12 characters in length");
                else if (containsBanned(newTitle))
                    player.retryStringInput("You may not use that title.<br>Try again:");
                else
                    setCustomTitle(player, title, newTitle);
            });
            return;
        }
        player.titleId = title.id;
        player.getAppearance().update();
        player.dialogue(new MessageDialogue("Your title has been updated."));
    }
    private static boolean containsBanned(String newTitle) {
        for (String banned : bannedTitles) {
            banned = banned.toLowerCase();
            if (newTitle.contains(banned))
                return true;
        }
        return false;
    }
    private static void setCustomTitle(Player player, Title title, String newTitle) {
        player.titleId = title.id;
        player.customTitle = "<col=FF2d00><shad=000000>"+ newTitle+ " </col></shad>";
        player.getAppearance().update();
        player.dialogue(new MessageDialogue("Your title has been updated."));

    }
    public static void clearTitle(Player player) {
        player.titleId = -1;
        player.title = null;
        player.getAppearance().update();
    }

    private static int compare(Title t1, Title t2) {
        try {
            if (t1.getPrefix() != null && t2.getPrefix() == null)
                return -1;
            else if (t2.getPrefix() != null && t1.getPrefix() == null)
                return 1;
            else if (t1.getPrefix() != null && t2.getPrefix() != null)
                return t1.strippedPrefix.compareToIgnoreCase(t2.strippedPrefix);
            else
                return t1.strippedSuffix.compareToIgnoreCase(t2.strippedSuffix);
        } catch (Exception e) {
            ServerWrapper.logError("Can't compare titles: ", e);
            return 0;
        }
    }

    public static Title prefixTitle(String prefix) {
        Title title = new Title();
        return title.prefix(prefix);
    }


    public static Title suffixTitle(String suffix) {
        Title title = new Title();
        return title.suffix(suffix);
    }

    public Title prefix(String prefix) {
        this.prefix = prefix;
        strippedPrefix = prefix.replaceAll("<(.*)>", "");
        return this;
    }

    public Title suffix(String suffix) {
        this.suffix = suffix;
        strippedSuffix = suffix.replaceAll("<(.*)>", "");
        return this;
    }

    private String prefix, suffix;
    private String strippedPrefix, strippedSuffix;

    private int id;

    private boolean hidden;

    public Title pvpWorld() {
        //this.worldType = WorldType.PVP;
        return this;
    }

    public Title ecoWorld() {
        this.worldType = WorldType.ECO;
        return this;
    }

    private WorldType worldType;

    public Title setPredicate(Predicate<Player> predicate) {
        this.predicate = predicate;
        return this;
    }

    private Predicate<Player> predicate;

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public Predicate<Player> getPredicate() {
        return predicate;
    }

    public String getUnlockDescription() {
        return unlockDescription;
    }

    public Title setUnlockDescription(String unlockDescription) {
        this.unlockDescription = unlockDescription;
        return this;
    }

    private String unlockDescription = "";

    public boolean canUse(Player player) {
        return activeOnThisWorld() && (predicate == null || predicate.test(player));
    }

    public boolean activeOnThisWorld() {
        return worldType == null || worldType == World.type;
    }

    public static Title get(int titleId) {
        if (titleId < 0 || titleId >= PRESET_TITLES.length)
            return null;
        return PRESET_TITLES[titleId];
    }

    public static void load(Player player) {
        if (player.titleId == -1) {
            return;
        }
        Title title = get(player.titleId);
        if (title != null && !title.canUse(player)) {
            player.sendMessage(Color.RED.wrap("Your title has been removed as you no longer meet the requirements to use it."));
        } else {
            player.title = title;
        }
    }

    /**
     * Hidden titles only show if unlocked, even if the player selects to show locked titles
     */
    public Title setHidden() {
        hidden = true;
        return this;
    }
}
