package io.ruin.model.entity.player;

import com.google.gson.annotations.Expose;
import io.ruin.content.activities.lms.LastManStandingQueue;
import io.ruin.content.activities.lms.LastManStandingSession;
import io.ruin.content.activities.tournament.Tournament;
import io.ruin.content.activities.tournament.TournamentFightPair;
import io.ruin.content.objects.Cannon;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.achievements.AchievementStage;
import io.ruin.model.activities.ActivityTimer;
import io.ruin.model.activities.combat.barrows.BarrowsBrother;
import io.ruin.model.activities.cluescrolls.ClueSave;
import io.ruin.model.activities.combat.fightcaves.FightCaves;
import io.ruin.model.activities.combat.inferno.Inferno;
import io.ruin.model.activities.combat.pestcontrol.PestControlGame;
import io.ruin.model.activities.combat.pvminstance.PVMInstance;
import io.ruin.model.activities.combat.raids.xeric.party.Party;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.activities.wilderness.WildernessObelisk;
import io.ruin.model.combat.WildernessRating;
import io.ruin.model.content.upgrade.UpgradeMachine;
import io.ruin.model.content.transportation.waystones.Waystone;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.handlers.TeleportInterface;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.journal.presets.Preset;
import io.ruin.model.inter.journal.presets.PresetCustom;
import io.ruin.model.inter.journal.toggles.EdgevilleBlacklist;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.item.actions.impl.storage.EssencePouch;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.impl.transportation.CanoeStation;
import io.ruin.model.map.object.actions.impl.transportation.FairyRing;
import io.ruin.model.skills.construction.House;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.skills.construction.seat.Seat;
import io.ruin.model.skills.farming.farming_contracts.FarmingContracts;
import io.ruin.model.skills.hunter.Birdhouse;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.magic.spells.lunar.TeleGroup;
import io.ruin.model.skills.magic.spells.modern.Teleother;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.stat.StatType;
import io.ruin.utility.TickDelay;
import io.ruin.utility.TimedList;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public abstract class PlayerAttributes extends Entity {

    public boolean debug;
    @Expose @Getter @Setter
    public int tutorialStage;
    @Setter protected Runnable onDialogueContinued;

    @Expose public Journal journal = Journal.MAIN;

    @Expose public int targetOverlaySetting = 0;
    public Entity targetOverlayTarget;
    public int targetOverlayResetTicks;

    @Expose public XpMode xpMode = XpMode.MEDIUM;

    @Expose public int wildernessSlayerPoints;

    @Expose public int theatreOfBloodCompleted;
    public int viewingTheatreSlot;
    @Expose @Setter @Getter public boolean acceptedTheatreRisk;
    @Expose @Setter @Getter public boolean acceptedTheatreCrystals;

    /**
     * NMZ
     */
    @Expose public int absorptionPoints = 0;

    @Expose public int nmzRewardPoints;

    @Expose public int nmzCofferCoins;

    /** blast furnace variables */
    @Getter @Setter @Expose
    public int blastFurnaceCofferAmount = 0;
    @Getter @Setter @Expose
    public int blastFurnaceOres = 0;
    @Getter @Setter @Expose
    public int blastFurnaceCoalAmount;
    @Getter @Setter @Expose
    public int blastFurnaceBars = 0;

    /**
     * 2FA
     */

    public boolean tfa;

    /**
     * Agility
     */
    @Expose public int lastAgilityObjId = -1;

    /**
     * Cooking
     */
    @Expose public int cookedFood;
    public int cookStreak = 0;

    @Expose public int wallSafesCracked;

    public TickDelay boneBuryDelay = new TickDelay();


    /**
     * Construction
     */
    public int houseBuildPointX, houseBuildPointY, houseBuildPointZ;
    public Room[] houseViewerRooms;
    public Room houseViewerRoom;

    /**
     * Clue scrolls
     */

    @Expose public ClueSave beginnerClue;
    @Expose public ClueSave easyClue;
    @Expose public ClueSave medClue;
    @Expose public ClueSave hardClue;
    @Expose public ClueSave eliteClue;
    @Expose public ClueSave masterClue;


    /**
     * Motherlode mine
     */
    @Expose public int cleanedPaydirt;

    @Expose public int paydirtInWater;
    @Expose public boolean motherlodeBiggerSackUnlocked;

    @Expose public long lastTimeEnteredPlunder = 0;
    public boolean disarmedPlunderRoomTrap = false;
    public boolean inPyramidPlunder = false;
    @Expose public int nextPlunderRoomId;
    @Expose public int totalPyramidPlunderGames = 0;

    /**
     * Misc
     */

    @Expose public long playTime;

    @Expose public int staminaTicks;

    public int specialRestoreTicks;

    public Config selectedKeybindConfig;

    public int selectedSettingMenu = -1;
    public int selectedSettingChild = -1;

    public TickDelay eatDelay = new TickDelay();

    public TickDelay karamDelay = new TickDelay();

    public TickDelay potDelay = new TickDelay();

    public SmithBar smithBar;

    @Expose public int darkEssFragments;

    @Expose public int baggedCoal;

    @Expose public boolean miningGuildMinerals;

    public TickDelay yesDelay = new TickDelay();

    public TickDelay noDelay = new TickDelay();

    public TickDelay emoteDelay = new TickDelay();

    @Expose public boolean beginnerParkourEnergyBoost;

    public Teleother teleotherActive;
    public TeleGroup telegroupActive;


    @Expose public boolean kylieMinnowDialogueStarted;

    @Expose public boolean fairyAerykaDialogueStarted;

    @Expose public boolean elnockInquisitorDialogueStarted;

    @Expose public boolean elnockInquisitorGivenEquipment;

    public NPC examineMonster;

    @Expose public BarrowsBrother barrowsChestBrother;

    @Expose public int barrowsChestsOpened;

    public TickDelay magicImbueEffect = new TickDelay();

    @Expose public int teleportCategoryIndex, teleportSubcategoryIndex;

    @Expose public boolean canEnterMorUlRek;

    @Expose public FairyRing[] unlockedFairyRingTeleports = new FairyRing[FairyRing.values().length];

    public GameObject fairyRing;

    public CanoeStation canoeStation;

    public boolean npcTarget; //just so we can check if a player has a target or not.

    @Expose public boolean edgevilleLeverWarning = true;

    @Expose public int mageArenaPoints;

    @Expose public int previousTeleportX = -1, previousTeleportY, previousTeleportZ;

    @Expose public int previousTeleportPrice;

    public TickDelay imbueHeartCooldown = new TickDelay();

    @Expose public boolean newPlayer = true;

    public boolean inTutorial = false;

    @Expose public boolean krakenWarning = true;

    @Expose public boolean smokeBossWarning = true;

    @Expose public boolean wyvernWarning = true;

    @Expose public int dragSetting = 5;

    @Expose public int switchGrading;

    @Expose public boolean showTimers = true;

    @Expose public boolean colorValuedGroundItems = false;

    @Expose public boolean swapRangePrayerPosition;

    @Expose public boolean swapMagePrayerPosition;

    @Expose public long yellDelay;

    @Expose public boolean yellFilter;

    @Expose public boolean hideTitles = false;

    /**
     * PVP
     */

    public boolean pvpAttackZone;

    public int pvpCombatLevel;

    @Expose public int recoilDamageRemaining = 40;

    /**
     * Dueling stuff
     */

    @Expose public int presetDuelVarp, lastDuelVarp;

    @Expose public int duelWins, duelLosses;

    public TickDelay acceptDelay = new TickDelay();

    @Expose public boolean experienceLock;


    /**
     * Wilderness stuff
     */

    public int wildernessLevel = -1;

    @Expose public boolean mageArena, resourceArea;

    @Expose public boolean obeliskRedirectionScroll;

    @Expose public WildernessObelisk obeliskDestination;

    /**
     * Achievements
     */

    public AchievementStage[] achievementStages = new AchievementStage[Achievement.values().length];

    /**
     * Gem bag
     */
    @Expose public int[] gemBagContents = new int[5];

    /**
     * Herb sack
     */
    @Expose public int herbSackGuamLeaf;
    @Expose public int herbSackMarrentill;
    @Expose public int herbSackTarromin;
    @Expose public int herbSackHarralander;
    @Expose public int herbSackRanarrWeed;
    @Expose public int herbSackToadflax;
    @Expose public int herbSackIritLeaf;
    @Expose public int herbSackAvantoe;
    @Expose public int herbSackKwuarm;
    @Expose public int herbSackSnapdragon;
    @Expose public int herbSackCadantine;
    @Expose public int herbSackLantadyme;
    @Expose public int herbSackDwarfWeed;
    @Expose public int herbSackTorstol;

    /**
     * Fight caves
     */
    @Expose public FightCaves fightCaves;
    @Expose public long fightCavesBestTime;

    /**
     * Inferno
     */
    @Expose public boolean talkedToTzHaarKetKeh;
    @Expose public Inferno inferno;
    @Expose public long infernoBestTime;

    /**
     * Warriors guild
     */
    public TickDelay tokenEvent = new TickDelay();
    public int nextDefenderId = -1;

    /**
     * Wintertodt
     */
    public int wintertodtPoints = 0;
    @Expose public int lifetimeWintertodtPoints;
    @Expose public int wintertodtSubdued;
    @Expose public int wintertodtHighscore;
    @Expose public boolean talkedToIgnisia;

    /**
     * Hunter
     */
    public ArrayList<Trap> traps = new ArrayList<>(5);

    /**
     * Godwars
     */
    public TickDelay godwarsAltarCooldown = new TickDelay();

    /**
     * Slayer
     */
    @Expose public int slayerTasksCompleted;
    @Expose public int slayerSpree = 0;
    @Expose public boolean slayerCombatCheck;
    @Expose public int slayerLocation = 0;

    public TickDelay blackChinchompaBoost = new TickDelay();
    @Expose public int blackChinchompaBoostTimeLeft;
    public TickDelay darkCrabBoost = new TickDelay();
    @Expose public int darkCrabBoostTimeLeft;

    /**
     * Farming contracts
     */
    @Expose public FarmingContracts farmingContract;
    @Expose public boolean contractCompleted;

    /**
     * Antifire
     */
    @Expose public int antifireTicks;
    @Expose public int superAntifireTicks;

    /**
     * Alch
     */
    public TickDelay alchDelay = new TickDelay();

    /**
     * Jail
     */

    @Expose public String jailerName;

    @Expose public int jailOresAssigned, jailOresCollected;

    /**
     * Vengeance
     */

    @Expose public boolean vengeanceActive;

    /**
     * Superheat delay
     */
    public TickDelay superheatDelay = new TickDelay();

    /**
     * Runecrafting pouch
     */
    @Expose public Map<EssencePouch, Integer> runeEssencePouches = new HashMap<>();

    /**
     * Mute
     */

    @Expose public long muteEnd;

    @Expose public boolean shadowMute;

    /**
     * Kill spree/shutdown
     */
    @Expose public int currentKillSpree;
    @Expose public int highestKillSpree;
    @Expose public int highestShutdown;
    @Expose public long nextWildernessBonus;

    /**
     * Farm prevention
     */
    @Expose public TimedList bmIpLogs = new TimedList();
    @Expose public TimedList bmUserLogs = new TimedList();

    /**
     * Runecrafting achievement thingies
     */
    @Expose public boolean enteredAbyss;

    @Expose public boolean morytaniaFarmAchievement;

    public OptionScroll optionScroll;

    /**
     * Kill counters
     */
    @Expose public Map<String, KillCounter> killCounterMap = new HashMap<>();


    public ActivityTimer zulrahTimer;
    @Expose public long zulrahBestTime;

    /**
     * Staff of the dead
     */
    public TickDelay sotdDelay = new TickDelay();

    /**
     * Rock cake
     */
    public TickDelay rockCakeDelay = new TickDelay();

    /**
     * new Make-X interface last chosen amount
     */
    @Expose public int lastMakeXAmount;

    /**
     * Hide donator icons
     */
    @Expose public boolean hidePlayerIcon = false;

    /**
     * Pet
     */

    @Expose public Pet pet;

    public NPC petNPC;

    public boolean callPet;

    public boolean hidePet;

    public boolean showPet;


    /**
     * Presets
     */

    @Expose public PresetCustom[] customPresets = new PresetCustom[PresetCustom.ENTRIES.length];

    /**
     * Elo rating
     */
    @Expose public int pkRating = WildernessRating.DEFAULT_RATING;

    /**
     * PVP Instance position
     */
    @Expose public Position pvpInstancePosition;

    /**
     * Loyalty Chest
     */
    @Expose public long loyaltyTimer = System.currentTimeMillis();
    @Expose public long loyaltySpreeTimer = System.currentTimeMillis();
    @Expose public int loyaltyChestReward = 1;
    @Expose public int loyaltyChestCount;
    @Expose public int loyaltyChestSpree;
    @Expose public int highestLoyaltyChestSpree;

    /*
     * first3 stuff
     */
    public TickDelay first3 = new TickDelay();
    @Expose public int first3TimeLeft;

    /*
     * Custom Points
     */
    @Expose public int PvmPoints;

    /**
     * Store
     */

    @Expose public int storeAmountSpent;

    /**
     * Resting
     */

    /**
     * Elder chaos druid teleport
     */
    public TickDelay elderChaosDruidTeleport = new TickDelay();

    /**
     * Tournament system attributes
     */
    public boolean tempUseRaidPrayers = false;
    @Expose public boolean joinedTournament = false; // Save this in case the server disconnects/restarts during the waiting lobby
    public Position viewingOrbLocation;
    public boolean usingTournamentOrb = false;
    public boolean usingTournamentOrbFromHome = false;
    public int cachedRunePouchTypes;
    public int cachedRunePouchAmounts;
    public boolean tournamentPouch = false;
    public boolean tournamentAugury = false;
    public boolean tournamentRigour = false;
    public boolean tournamentPreserve = false;
    @Expose public int tournamentWins = 0;
    @Expose public int[] preTournyAttack, preTournyStrength, preTournyDefence, preTournyRanged, preTournyPrayer, preTournyMagic, preTournyHitpoints;

    /**
     * Login date
     */
    public long sessionStart = System.currentTimeMillis();

    /**
     * Bestiary
     */
    public List<JournalEntry> bestiarySearchResults;

    /**
     * Active valcano
     */
    public int bloodyFragments;

    /**
     * Raids party settings
     */
    @Expose public int partyAdvertisementsRemaining = 15;
    public boolean advertisingParty = false;
    public long advertisementStartTick = 0L;
    public Party raidsParty;
    public Party viewingParty;
    @Expose public boolean raidsEntranceWarning = false;

    /**
     * Ring of suffering
     */
    @Expose public boolean ringOfSufferingEffect = true;

    /**
     * Buy Credits
     */

    /**
     *
     */
    @Expose public long rejuvenationPool = 0;

    /**
     * Title
     */
    @Expose public int titleId = -1;
    public Title title;
    @Expose public String customTitle;
    @Expose public boolean hasCustomTitle;

    /*
     * Summer Event
     */
    @Expose public long lastSacrifice;

    /**
     * Intro achievements fields
     */
    @Expose public boolean bestiaryIntro = false;

    /**
     * New con code
     */
    @Expose
    public House house = null;

    public Seat seat;

    /**
     * Boost scrolls
     */
    public TickDelay expBonus = new TickDelay();
    @Expose public int expBonusTimeLeft;
    public TickDelay petDropBonus = new TickDelay();
    @Expose public int petDropBonusTimeLeft;
    public TickDelay rareDropBonus = new TickDelay();
    @Expose public int rareDropBonusTimeLeft;

    /**
     * Wilderness points
     */
    @Expose public int wildernessPoints;

    /**
     * Toggle for exp counter to show hit
     */
    @Expose public boolean showHitAsExperience = false;

    /**
     * Mystery box incentives
     */
    @Expose public boolean firstMysteryBoxReward = true;
    @Expose public int guaranteedMysteryBoxLoot = 1;

    /**
     * Equip timer to prevent animations
     */
    public TickDelay recentlyEquipped = new TickDelay();

    /**
     * Nurse special attack refill cooldown
     */
    public TickDelay nurseSpecialRefillCooldown = new TickDelay();

    /**
     * Used when a player has a bounty target to ensure he doesn't switch presets
     */
    public Preset lastPresetUsed;

    /**
     * Edgeville blacklisted users
     */
    @Expose public EdgevilleBlacklist[] edgevilleBlacklistedUsers = new EdgevilleBlacklist[EdgevilleBlacklist.ENTRIES.length];

    /**
     * Used to determine if a player is an official dice host or not (set when the player claims the dice bag)
     */
    @Expose public boolean diceHost = false;


    /**
     * Daily reset
     */
    @Expose public String lastLoginDate; // would have used a LocalDate object for this but it doesn't serialize properly with the @Expose annotation

    @Expose public DailyTask[] dailyTasks = new DailyTask[3];
    @Expose public int[] dailyTaskProgress = new int[3];
    @Expose public int dailyTaskPoints;

    /**
     * PvP weapon specials
     */
    public TickDelay vestasSpearSpecial = new TickDelay();
    public TickDelay morrigansAxeSpecial = new TickDelay();

    /**
     * Supply chest wilderness event
     */
    public boolean supplyChestRestricted = false;

    /**
     * Blood money key wilderness event
     */
    @Expose public boolean bloodyKeyWarning = true;

    /**
     * Attribute used to hide free items on the PVP world
     */
    @Expose public boolean hideFreeItems = false;

    /**
     * Used for the dragonfire shield special attack
     */
    public boolean dragonfireShieldSpecial = false;
    public TickDelay dragonfireShieldCooldown = new TickDelay();

    /**
     * Ring of wealth attributes for features
     */
    @Expose public boolean ROWAutoCollectBloodMoney = false;

    @Expose public boolean ROWAutoCollectEther = false;

    @Expose public boolean ROWAutoCollectGold = false;

    public boolean insideWildernessAgilityCourse = false;

    public TickDelay presetDelay = new TickDelay();

    public StatType selectedSkillLampSkill;

    /**
     * Risk protection toggle
     */
    @Expose public int riskProtectionTier = 10;
    @Expose public long riskedBloodMoney = 0;
    public TickDelay riskProtectionExpirationDelay = new TickDelay();

    /**
     * Magic skillcape attributes
     */
    @Expose public long mageSkillcapeSpecial = System.currentTimeMillis();
    @Expose public int magicSkillcapeUses = 0;

    /**
     * PvM Instances
     */
    public PVMInstance currentInstance;

    public boolean claimedBox = true;
    public boolean easterEgg = false;

    /**
     * Idle timer
     */
    public int idleTicks;
    public boolean isIdle = false;

    /**
     * Christmas 2018
     */
    @Expose public boolean gotChristmasPresent = false;
    @Expose public boolean snowballPeltOption = false;
    @Expose public int snowballPoints = 0;
    public TickDelay snowballCooldown = new TickDelay();

    /**
     * Giveaway
     */
    @Expose public int giveawayId = -1;
    @Expose public int giveawayEntries = 0;

    public TickDelay specTeleportDelay = new TickDelay();

    @Expose public boolean bloodyMerchantTradeWarning = true;

    @Expose public boolean broadcastBossEvent = true;
    @Expose public boolean broadcastActiveVolcano = true;
    @Expose public boolean broadcastHotspot = true;
    @Expose public boolean broadcastSupplyChest = true;
    @Expose public boolean broadcastBloodyMechant = true;
    @Expose public boolean broadcastAnnouncements = true;
    @Expose public boolean broadcastTournaments = true;


    @Expose public boolean bootsOfLightnessTaken = false;

    /**
     * Nest boxes
     */
    @Expose public int nestBoxSeeds;
    @Expose public int nestBoxRings;

    public TickDelay edgevilleStallCooldown = new TickDelay();

    @Expose public double chaosAltarBoneChance = 50;

    /**
     * Appreciation points
     */
    @Expose public int appreciationTicks = 0;
    @Expose public int appreciationPoints = 0;

    @Expose public double energyUnits = 10000;


    @Expose public int refundedCredits = 0;


    public NPC botPreventionNPC;
    public boolean dismissBotPreventionNPC;
    public TickDelay botPreventionJailDelay = new TickDelay();
    public TickDelay botPreventionNpcShoutDelay = new TickDelay();

    @Expose public int claimedVotes;

    @Expose public int voteMysteryBoxReward;

    @Expose public boolean preventSkippingCourse = false;

    // Skill cape dailies
    @Expose public long lastAgilityCapeBoost;

    @Expose public int timesKilledDonatorBoss;
    @Expose public long lastTimeKilledDonatorBoss;

    // Raids / NMZ pots
    public boolean overloadBoostActive = false;
    public boolean prayerEnhanceBoostActive = false;

    // Divine pots
    public boolean divineAttackBoostActive = false;
    public boolean divineStrBoostActive = false;
    public boolean divineDefBoostActive = false;
    public boolean divineSuperCmbBoostActive = false;
    public boolean divineMagicBoostActive = false;
    public boolean divineRangingBoostActive = false;
    public boolean divineBastionBoostActive = false;
    public boolean divineBattlemageBoostActive = false;

    public PestControlGame pestGame;
    @Expose public int pestPoints;
    @Expose public int pestNoviceWins;
    @Expose public int pestIntermediateWins;
    @Expose public int pestVeteranWins;
    public int pestActivityScore;
    public int selectedWidgetId;

    @Expose public boolean autoCollectEther = false;

    @Expose public boolean unlockedGreenSkin;
    @Expose public boolean unlockedBlueSkin;
    @Expose public boolean unlockedPurpleSkin;
    @Expose public boolean unlockedWhiteSkin;
    @Expose public boolean unlockedBlackSkin;

    public LastManStandingSession lmsSession;
    public LastManStandingQueue lmsQueue;
    public int lmsSessionKills;
    @Expose public int lmsKills;
    @Expose public int lmsWins;
    @Expose public int lmsGamesPlayed;

    public Tournament tournament;
    public TournamentFightPair tournamentFight;

    @Expose public int mysteriousStrangerVarp;

    @Expose public Cannon cannon;
    @Expose public Position cannonPosition;
    @Expose public int cannonBallsLoaded;

    @Expose public boolean bountyHunterOverlay = true;

    @Expose @Getter
    public TeleportInterface teleports;

    @Expose @Getter
    public UpgradeMachine upgradeMachine;

    @Expose public boolean cerberusMetamorphisis;
    @Expose public boolean infernalJadMetamorphisis;
    @Expose public boolean abyssalSireMetamorphisis;

    /*  Stronghold of Security  */
    @Expose public boolean[] strongholdRewards = { false, false, false, false };

    @Expose @Getter @Setter public int collection_log_current_entry = 0;
    @Expose @Getter @Setter public int collection_log_current_struct = 471;//471-475
    @Expose @Getter @Setter public int collection_log_current_tab = 0;
    @Expose @Getter @Setter public int[] collection_log_current_params = player.getCollectionLog().bossParams;

    @Expose @Getter @Setter protected Birdhouse birdhouse;

    @Expose @Getter @Setter private boolean tanSoftLeather = true;

    @Getter @Setter private boolean dreaming = false;

    @Expose @Getter private final Set<Integer> SpokenToNPCSet = new HashSet<>();
    @Expose @Getter private final EnumSet<Waystone> UnlockedWaystones = EnumSet.noneOf(Waystone.class);

    @Expose protected Map<String, Object> attributes = Collections.synchronizedMap(new HashMap<>());

    @Expose @Getter @Setter private RespawnPoint respawnPoint = RespawnPoint.LUMBRIDGE;
}