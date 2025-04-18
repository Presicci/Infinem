package io.ruin.model.entity.player;

import com.google.gson.annotations.Expose;
import io.ruin.content.activities.lms.LastManStandingQueue;
import io.ruin.content.activities.lms.LastManStandingSession;
import io.ruin.content.activities.tournament.Tournament;
import io.ruin.content.activities.tournament.TournamentFightPair;
import io.ruin.content.objects.Cannon;
import io.ruin.model.activities.ActivityTimer;
import io.ruin.model.activities.cluescrolls.ClueSave;
import io.ruin.model.activities.combat.fightcaves.FightCaves;
import io.ruin.model.activities.combat.inferno.Inferno;
import io.ruin.model.activities.combat.pestcontrol.PestControlGame;
import io.ruin.model.activities.combat.pvminstance.PVMInstance;
import io.ruin.model.activities.combat.raids.xeric.party.Party;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.activities.wilderness.WildernessObelisk;
import io.ruin.model.combat.WildernessRating;
import io.ruin.model.content.tasksystem.relics.impl.fragments.RelicFragmentManager;
import io.ruin.model.content.upgrade.UpgradeMachine;
import io.ruin.model.content.transportation.waystones.Waystone;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.inter.handlers.OptionScroll;
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
import io.ruin.model.skills.magic.spells.arceuus.Resurrection;
import io.ruin.model.skills.magic.spells.lunar.TeleGroup;
import io.ruin.model.skills.magic.spells.modern.Teleother;
import io.ruin.utility.TickDelay;
import io.ruin.utility.TimedList;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public abstract class PlayerAttributes extends Entity {

    public boolean debug;

    @Setter protected Runnable onDialogueContinued;

    @Expose public Journal journal = Journal.MAIN;

    public Entity targetOverlayTarget;
    public int targetOverlayResetTicks;

    public int viewingTheatreSlot;

    /**
     * 2FA
     */

    public boolean tfa;

    /**
     * Cooking
     */
    public int cookStreak = 0;

    public TickDelay boneBuryDelay = new TickDelay();


    /**
     * Construction
     */
    public int houseBuildPointX, houseBuildPointY, houseBuildPointZ;
    public Room[] houseViewerRooms;
    public Room houseViewerRoom;

    /**
     * Thralls
     */
    public Resurrection.Thrall thrall;
    @Getter public TickDelay thrallSpawnDelay = new TickDelay();
    @Getter public TickDelay thrallDespawnDelay = new TickDelay();

    /**
     * Clue scrolls
     */

    @Expose public ClueSave beginnerClue;
    @Expose public ClueSave easyClue;
    @Expose public ClueSave medClue;
    @Expose public ClueSave hardClue;
    @Expose public ClueSave eliteClue;
    @Expose public ClueSave masterClue;

    // TODO redo these when working on pyramid plunder
    public long lastTimeEnteredPlunder = 0;
    public boolean disarmedPlunderRoomTrap = false;
    public boolean inPyramidPlunder = false;
    public int nextPlunderRoomId;

    /**
     * Misc
     */

    @Expose public long playTime;

    public int specialRestoreTicks;

    public Config selectedKeybindConfig;

    public TickDelay eatDelay = new TickDelay();

    public TickDelay karamDelay = new TickDelay();

    public TickDelay potDelay = new TickDelay();

    public TickDelay runDelay = new TickDelay();

    public TickDelay emoteDelay = new TickDelay();

    public Teleother teleotherActive;
    public TeleGroup telegroupActive;

    public NPC examineMonster;

    public TickDelay magicImbueEffect = new TickDelay();

    @Expose public FairyRing[] unlockedFairyRingTeleports = new FairyRing[FairyRing.values().length];

    public GameObject fairyRing;

    public CanoeStation canoeStation;

    public boolean npcTarget; //just so we can check if a player has a target or not.

    public TickDelay imbueHeartCooldown = new TickDelay();

    @Expose public boolean krakenWarning = true;

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

    @Expose public boolean obeliskRedirectionScroll;

    @Expose public WildernessObelisk obeliskDestination;

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
     * Slayer
     */
    @Expose public int slayerTasksCompleted;
    @Expose public int slayerSpree = 0;
    @Expose public boolean slayerCombatCheck;
    @Expose public int slayerLocation = 0;

    /**
     * Farming contracts
     */
    @Expose public FarmingContracts farmingContract;
    @Expose public boolean contractCompleted;

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
    @Expose public long lastLogin;
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

    /**
     * Ring of wealth attributes for features
     */
    public boolean insideWildernessAgilityCourse = false;

    public TickDelay presetDelay = new TickDelay();

    /**
     * Risk protection toggle
     */
    @Expose public int riskProtectionTier = 10;
    @Expose public long riskedBloodMoney = 0;
    public TickDelay riskProtectionExpirationDelay = new TickDelay();

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

    public TickDelay specTeleportDelay = new TickDelay();

    @Expose public boolean bloodyMerchantTradeWarning = true;

    @Expose public boolean broadcastBossEvent = true;
    @Expose public boolean broadcastActiveVolcano = true;
    @Expose public boolean broadcastHotspot = true;
    @Expose public boolean broadcastSupplyChest = true;
    @Expose public boolean broadcastBloodyMechant = true;
    @Expose public boolean broadcastTournaments = true;


    public TickDelay edgevilleStallCooldown = new TickDelay();

    @Expose public double energyUnits = 10000;


    @Expose public int refundedCredits = 0;


    public NPC randomEventNPC;
    public boolean dismissRandomEvent;
    public TickDelay randomEventJailDelay = new TickDelay();
    public TickDelay randomEventNpcShoutDelay = new TickDelay();

    @Expose public int claimedVotes;

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
    public int pestActivityScore;

    public LastManStandingSession lmsSession;
    public LastManStandingQueue lmsQueue;
    public int lmsSessionKills;

    public Tournament tournament;
    public TournamentFightPair tournamentFight;

    @Expose public Cannon cannon;
    @Expose public Position cannonPosition;
    @Expose public int cannonBallsLoaded;

    @Expose public boolean bountyHunterOverlay = true;

    @Expose @Getter
    public UpgradeMachine upgradeMachine;

    @Expose @Getter @Setter protected Birdhouse birdhouse;

    @Getter @Setter private boolean dreaming = false;

    @Expose @Getter private final Set<Integer> SpokenToNPCSet = new HashSet<>();
    @Expose @Getter private final EnumSet<Waystone> UnlockedWaystones = EnumSet.noneOf(Waystone.class);

    @Expose protected Map<String, Object> attributes = Collections.synchronizedMap(new HashMap<>());

    @Expose @Getter @Setter private RespawnPoint respawnPoint = RespawnPoint.CIVITAS_ILLA_FORTIS;

    public Set<String> onlineFriendNames = new HashSet<>();

    @Expose @Getter private RelicFragmentManager relicFragmentManager = new RelicFragmentManager();
}