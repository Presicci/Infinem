package io.ruin.model.entity.player;

import com.google.gson.annotations.Expose;
import io.netty.channel.Channel;
import io.ruin.PersistentData;
import io.ruin.Server;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.utils.*;
import io.ruin.api.utils.Random;
import io.ruin.cache.def.AnimationDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.inter.handlers.NotificationInterface;
import io.ruin.model.skills.slayer.PartnerSlayer;
import io.ruin.network.central.CentralSender;
import io.ruin.services.Store;
import io.ruin.utility.Color;
import io.ruin.cache.def.InterfaceDefinition;
import io.ruin.cache.def.VarpDefinition;
import io.ruin.event.GameEventProcessor;
import io.ruin.model.World;
import io.ruin.model.activities.cluescrolls.impl.EmoteClue;
import io.ruin.model.activities.cluescrolls.puzzles.LightBox;
import io.ruin.model.activities.duelarena.Duel;
import io.ruin.model.activities.pyramidplunder.PyramidPlunder;
import io.ruin.model.activities.wilderness.BountyHunter;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.content.poll.PollManager;
import io.ruin.model.content.upgrade.UpgradeMachine;
import io.ruin.model.content.bestiary.Bestiary;
import io.ruin.model.content.tasksystem.relics.RelicManager;
import io.ruin.model.content.tasksystem.tasks.TaskManager;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.Entity;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.gamemode.Tileman;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.UpdateMask;
import io.ruin.model.entity.shared.listeners.*;
import io.ruin.model.entity.shared.masks.*;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.handlers.MusicPlayer;
import io.ruin.model.inter.journal.presets.PresetCustom;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.actions.impl.AshSanctifier;
import io.ruin.model.item.actions.impl.BoneCrusher;
import io.ruin.model.item.actions.impl.Lightables;
import io.ruin.model.item.actions.impl.boxes.mystery.SuperMysteryBox;
import io.ruin.model.item.actions.impl.chargable.SerpentineHelm;
import io.ruin.model.item.actions.impl.storage.DeathStorage;
import io.ruin.model.item.actions.impl.storage.LootingBag;
import io.ruin.model.item.actions.impl.storage.RunePouch;
import io.ruin.model.item.actions.impl.tradepost.TradePost;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.item.containers.SeedVault;
import io.ruin.model.item.containers.Trade;
import io.ruin.model.item.containers.bank.Bank;
import io.ruin.model.item.containers.bank.BankPin;
import io.ruin.model.item.containers.collectionlog.CollectionLog;
import io.ruin.model.map.*;
import io.ruin.model.map.environment.Desert;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.shop.Shop;
import io.ruin.model.skills.construction.House;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.skills.farming.Farming;
import io.ruin.model.skills.hunter.Birdhouse;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.stat.StatList;
import io.ruin.model.stat.StatType;
import io.ruin.network.PacketSender;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.IncomingDecoder;
import io.ruin.process.tickevent.TickEventType;
import io.ruin.services.Hiscores;
import io.ruin.services.Loggers;
import io.ruin.services.XenGroup;
import io.ruin.utility.CS2Script;
import io.ruin.utility.TickDelay;
import io.ruin.process.tickevent.TickEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;

import static io.ruin.cache.ItemID.*;

public class Player extends PlayerAttributes {

    public Shop viewingShop;
    /**
     * Session
     */

    @Getter private Channel channel;

    private String ipAddress;

    private int ipAddressInt;

    public String getIp() {
        return ipAddress;
    }

    public int getIpInt() {
        return ipAddressInt;
    }

    private String macAddress;

    public String getMACAddress() {
        return macAddress;
    }

    private String uuid;

    public String getUUID() {
        return uuid;
    }

    /**
     * Collection Log
     */
    @Getter @Expose private CollectionLog collectionLog;

    /**
     * Info
     */

    private int userId;

    public int getUserId() {
        return userId;
    }

    private String name;

    public String getName() {
        return name;
    }

    @Getter @Expose private String fremennikName;

    @Setter @Getter @Expose private String password;

    public void setName(String name) {
        this.name = name;
        // Send updated name to central server
        CentralSender.sendUsernameUpdate(userId, name);
        getAppearance().update();
    }

    /**
     * Pyramid plunder
     */
    @Getter private PyramidPlunder pyramidPlunder;

    public int[][] lootedPlunderObjects = {
            {26580, 0},
            {26600, 0},
            {26601, 0},
            {26603, 0},
            {26604, 0},
            {26606, 0},
            {26607, 0},
            {26608, 0},
            {26609, 0},
            {26610, 0},
            {26611, 0},
            {26612, 0},
            {26613, 0},
            {26616, 0},
            {26626, 0}
    };

    /**
     * Groups & Rank (Used to display client icons)
     */
    @Getter private boolean[] groups;

    @Getter @Expose private PlayerGroup primaryGroup;

    private PlayerGroup clientGroup; //the group displayed on clients

    public void setGroups(List<Integer> groupIds) {
        groups = new boolean[PlayerGroup.GROUPS_BY_ID.length];
        for(Integer id : groupIds) {
            PlayerGroup group = PlayerGroup.GROUPS_BY_ID[id];
            if(group != null)
                groups[group.id] = true;
        }
        primaryGroup = PlayerGroup.GROUPS_BY_ID[groupIds.get(0)];
        updateClientGroup();
    }


    public void updateClientGroup() {
        if (primaryGroup != null && primaryGroup.clientImgId != -1) {
            clientGroup = primaryGroup;
            return;
        }
        for (PlayerGroup group : PlayerGroup.values()) {
            if (groups[group.id] && group.clientImgId != -1) {
                clientGroup = group;
                return;
            }
        }
        clientGroup = PlayerGroup.REGISTERED;
    }

    public void join(PlayerGroup g) {
        groups[g.id] = true;
        updateClientGroup();
        if (g.id >= 11 && g.id <= 17) {
            Config.DONATOR_RANK.set(player, g.id - 10);
        }
    }

    public void leave(PlayerGroup g) {
        groups[g.id] = false;
        updateClientGroup();
        if (g.id >= 11 && g.id <= 17) {
            for (int index = 17; index >= 11; index--) {
                if (isGroup(PlayerGroup.GROUPS_BY_ID[index])) {
                    Config.DONATOR_RANK.set(player, index - 10);
                    break;
                }
            }
        }
    }

    public boolean isGroup(PlayerGroup g) {
        return groups[g.id];
    }

    public boolean isModerator() {
        return isGroup(PlayerGroup.MODERATOR);
    }

    public boolean isSupport() {
        return isGroup(PlayerGroup.SUPPORT);
    }

    public boolean isAdmin() {
        return isGroup(PlayerGroup.OWNER) || isGroup(PlayerGroup.DEVELOPER) || isGroup(PlayerGroup.COMMUNITY_MANAGER)
                || isGroup(PlayerGroup.ADMINISTRATOR);
    }

    public boolean isStaff() {
        return  isGroup(PlayerGroup.MODERATOR) ||  isGroup(PlayerGroup.SUPPORT)
                || isGroup(PlayerGroup.ADMINISTRATOR) || isGroup(PlayerGroup.OWNER) || isGroup(PlayerGroup.DEVELOPER)
                 || isGroup(PlayerGroup.COMMUNITY_MANAGER) || isGroup(PlayerGroup.ADMINISTRATOR);
    }

    public boolean isSapphire() {
        return isGroup(PlayerGroup.SAPPHIRE) || isEmerald();
    }

    public boolean isEmerald() {
        return isGroup(PlayerGroup.EMERALD) || isRuby();
    }

    public boolean isRuby() {
        return isGroup(PlayerGroup.RUBY) || isDiamond();
    }

    public boolean isDiamond() {
        return isGroup(PlayerGroup.DIAMOND) || isDragonStone();
    }

    public boolean isDragonStone() {
        return isGroup(PlayerGroup.DRAGONSTONE) || isOnyx();
    }

    public boolean isOnyx() {
        return isGroup(PlayerGroup.ONYX) || isZenyte();
    }

    public boolean isZenyte() {
        return isGroup(PlayerGroup.ZENYTE);
    }

    public PlayerGroup getClientGroup() {
        return hidePlayerIcon ? PlayerGroup.REGISTERED : clientGroup;
    }

    public int getClientGroupId() {
        if (hidePlayerIcon)
            return 0;
        if (clientGroup.ordinal() > PlayerGroup.SUPPORT.ordinal() && getGameMode() != GameMode.STANDARD) { // tough call here - what do we prioritize, ironman or custom ranks like donator/youtuber?
            return getGameMode().ordinal() + 2;
        }
        return clientGroup.clientId;
    }

    /**
     * Private message information
     */
    @Getter private int unreadPMs;

    /**
     * Incoming packets
     */
    @Getter private IncomingDecoder decoder;

    /**
     * Outgoing packets
     */
    private PacketSender packetSender;

    public PacketSender getPacketSender() {
        return packetSender;
    }

    public void sendMessage(String message) {
        packetSender.sendMessage(message, null, 0);
    }

    public void sendMessage(Color color, String message) {
        packetSender.sendMessage(color.wrap(message), null, 0);
    }

    public void sendURL(String message) {
        packetSender.sendMessage(message, World.type.getWebsiteUrl(), 4);
    }

    public void sendNotification(String message) {
        packetSender.sendMessage(message, "", 14);
    }

    public void sendFilteredMessage(String message) {
        packetSender.sendMessage(message, null, 105);
    }

    public void openUrl(String title, String url) {
        dialogue(new MessageDialogue("Opening " + title + "...<br>If this page fails to open please navigate your browser to:<br><col=880088>" + url));
        packetSender.sendUrl(url, false);
    }

    public void openUrl(String url) {
        packetSender.sendUrl(url, false);
    }

    public void sendScroll(String title, String... lines) {
        if(isVisibleInterface(119))
            closeInterface(InterfaceType.MAIN);
        packetSender.sendString(119, 2, title);
        int childId = 4;
        packetSender.sendString(119, childId++, "");
        for(String s : lines)
            packetSender.sendString(119, childId++, s);
        //packetSender.sendClientScript(917, "ii", -1, -1);
        openInterface(InterfaceType.MAIN, 119);
        packetSender.sendClientScript(2523, "1i", 1, lines.length);
    }

    public void sendHintArrow(Entity target) {
        packetSender.sendHintIcon(target);
    }

    public void sendHintArray(Position tile) {
        packetSender.sendHintIcon(tile);
    }

    public void clearHintArrow() {
        packetSender.resetHintIcon(false);
    }

    /**
     * Online
     */

    @Setter private boolean online;

    public boolean isOnline() {
        return online;
    }

    /**
     * Display
     */
    @Getter @Setter private int displayMode = -1;

    public boolean hasDisplay() {
        return displayMode != -1;
    }

    public boolean isFixedScreen() {
        return displayMode < 2;
    }

    /**
     * Game frame
     */
    @Getter @Setter private int gameFrameId;

    /**
     * Interface visibility
     */
    private final boolean[] visibleInterfaces = new boolean[InterfaceDefinition.COUNTS.length];

    private final Integer[][] visibleInterfaceIds = new Integer[InterfaceDefinition.COUNTS.length][];

    public void setVisibleInterface(int interfaceId, int parentId, int childId) {
        if(visibleInterfaceIds[parentId] == null)
            visibleInterfaceIds[parentId] = new Integer[InterfaceDefinition.COUNTS[parentId]];
        else if(visibleInterfaceIds[parentId] != null) {
            Integer id = visibleInterfaceIds[parentId][childId];
            if(id != null)
                visibleInterfaces[id] = false;
        }
        visibleInterfaces[interfaceId] = true;
        visibleInterfaceIds[parentId][childId] = interfaceId;
    }

    public void removeVisibleInterface(int parentId, int childId) {
        if (visibleInterfaceIds[parentId][childId] != null)
            visibleInterfaces[visibleInterfaceIds[parentId][childId]] = false;
        visibleInterfaceIds[parentId][childId] = null;
    }

    public void moveVisibleInterface(int fromParentId, int fromChildId, int toParentId, int toChildId) {
        if(visibleInterfaceIds[fromParentId] == null)
            visibleInterfaceIds[fromParentId] = new Integer[InterfaceDefinition.COUNTS[fromParentId]];
        Integer interfaceId = visibleInterfaceIds[fromParentId][fromChildId];
        if(interfaceId == null)
            return;
        setVisibleInterface(interfaceId, toParentId, toChildId);
        visibleInterfaceIds[fromParentId][fromChildId] = null;
    }

    public boolean isVisibleInterface(int interfaceId) {
        return interfaceId == gameFrameId || visibleInterfaces[interfaceId];
    }

    public boolean hasOpenInterface(InterfaceType type) {
        return activeInterfaceHandlers[type.ordinal()] != null;
    }

    /**
     * Interfaces - Opening, Closing & Handling
     */

    private final InterfaceHandler[] activeInterfaceHandlers = new InterfaceHandler[InterfaceType.values().length];

    public void openInterface(int containerParent, int containerChild, int interfaceId) {
        closeChatbox(false);
        closeInterface(containerParent, containerChild);
        InterfaceType.open(player, containerParent, containerChild, interfaceId);
        putTemporaryAttribute("INT_PARENT", containerParent);
        putTemporaryAttribute("INT_CHILD", containerChild);
    }

    public void closeInterface(int containerParent, int containerChild) {
        if (getTemporaryAttributeIntOrZero("INT_PARENT") == containerParent && getTemporaryAttributeIntOrZero("INT_CHILD") == containerChild)
            InterfaceType.close(player, containerParent, containerChild);
    }

    public void openInterface(InterfaceType type, int interfaceId, InterfaceHandler handler) {
        if (type != InterfaceType.PRIMARY_OVERLAY && type != InterfaceType.SECONDARY_OVERLAY && type != InterfaceType.POPUP_NOTIFICATION_OVERLAY)
            closeChatbox(type == InterfaceType.CHATBOX); //dupe prevention
        InterfaceHandler activeHandler = activeInterfaceHandlers[type.ordinal()];
        if(activeHandler != null && activeHandler.closedAction != null)
            activeHandler.closedAction.accept(this, interfaceId);
        if (type == InterfaceType.MAIN || type == InterfaceType.MAIN_STRETCHED) {
            closeInterface(InterfaceType.MAIN);
            closeInterface(InterfaceType.MAIN_STRETCHED);
        }
        type.open(this, interfaceId);
        activeInterfaceHandlers[type.ordinal()] = handler == null ? InterfaceHandler.EMPTY_HANDLER : handler;
    }

    public void openInterface(InterfaceType type, int interfaceId) {
        openInterface(type, interfaceId, InterfaceHandler.HANDLERS[interfaceId]);
    }

    public void setInterfaceUnderlay(int color, int transparency) {
        CS2Script.TOPLEVEL_MAINMODAL_OPEN.sendScript(this, color, transparency);
    }

    public void closeInterface(InterfaceType type) {
        InterfaceHandler activeHandler = activeInterfaceHandlers[type.ordinal()];
        if(activeHandler == null)
            return;
        if(activeHandler.closedAction != null)
            activeHandler.closedAction.accept(this, -1);
        type.close(this);
        activeInterfaceHandlers[type.ordinal()] = null;
    }

    public void closeInterfaces() {
        closeInterfaces(false);
    }

    public void closeInterfacesExcluding(InterfaceType type) {
        for(InterfaceType t : InterfaceType.values()) {
            if(t.overlaySetting != 1 && type != t)
                closeInterface(t);
        }
        if(trade != null)
            trade.close();
        if(duel != null)
            duel.close();
        if (type != InterfaceType.CHATBOX)
            closeChatbox(false);
    }

    public void closeInterfaces(boolean skipDialogues) {
        for(InterfaceType type : InterfaceType.values()) {
            if(type.overlaySetting != 1)
                closeInterface(type);
        }
        if(trade != null)
            trade.close();
        if(duel != null)
            duel.close();
        closeChatbox(skipDialogues);
    }

    public void closeChatbox(boolean skipDialogues) {
        if(!skipDialogues && dialogues != null)
            closeDialogue();
        if(consumerInt != null || consumerString != null) {
            consumerInt = null;
            consumerString = null;
            packetSender.sendClientScript(299, "ii", 1, 1);
        }
    }

    /**
     * Music
     */

    @Getter
    private MusicPlayer music = new MusicPlayer(this);

    /**
     * Dialogue
     */
    @Getter private NPC dialogueNPC;

    private int dialogueStage;

    private Dialogue[] dialogues;

    public Dialogue lastDialogue;

    public OptionsDialogue optionsDialogue;

    public SkillDialogue skillDialogue;

    public YesNoDialogue yesNoDialogue;

    private void openDialogue(boolean closeInterfaces, Dialogue... dialogues) {
        if(closeInterfaces) //important to be true in almost every case to prevent dupes!
            closeInterfaces(true);
        if (dialogues == null || dialogues.length == 0)
            return;
        this.dialogueStage = 1;
        this.dialogues = dialogues;
        this.optionsDialogue = null;
        this.skillDialogue = null;
        this.yesNoDialogue = null;
        (lastDialogue = dialogues[0]).open(this);
    }

    public void dialogue(NPC npc, Dialogue... dialogues) {
        dialogueNPC = npc;
        openDialogue(true, dialogues);
    }

    public void dialogue(Dialogue... dialogues) {
        openDialogue(true, dialogues);
    }

    public void dialogueKeepInterfaces(Dialogue... dialogues) {
        openDialogue(false, dialogues);
    }

    public void dialogue(boolean closeInterfaces, Dialogue... dialogues) {
        openDialogue(closeInterfaces, dialogues);
    }

    public void unsafeDialogue(Dialogue... dialogues) {
        openDialogue(false, dialogues);
    }

    public void continueDialogue() {
        onDialogueContinued();
        if (dialogues == null || dialogueStage >= dialogues.length) {
            lastDialogue.continueDialogue(this);
            closeDialogue();
        } else {
            lastDialogue.continueDialogue(this);
            (lastDialogue = dialogues[dialogueStage++]).open(this);
        }
    }

    public void closeDialogue() {
        dialogueNPC = null;
        dialogues = null;
        if(lastDialogue != null) {
            lastDialogue.closed(this);
            lastDialogue.continueDialogue(this);
            lastDialogue = null;
        }
        optionsDialogue = null;
        skillDialogue = null;
        yesNoDialogue = null;
        closeInterface(InterfaceType.CHATBOX);
    }

    public boolean hasDialogue() {
        return dialogues != null || optionsDialogue != null || skillDialogue != null || yesNoDialogue != null;
    }

    /**
     * Input
     */

    public Consumer<Integer> consumerInt;

    public boolean retryIntConsumer;

    public Consumer<String> consumerString;

    public boolean retryStringConsumer;

    /**
     * Integer input (Whole numbers!!)
     */

    public void integerInput(String message, Consumer<Integer> consumer) {
        consumerInt = consumer;
        retryIntConsumer = false;
        packetSender.sendClientScript(108, "s", message);
    }

    public void retryIntegerInput(String message) {
        integerInput(message, consumerInt);
        retryIntConsumer = true;
    }

    /**
     * Item input
     */

    public void itemSearch(String message, boolean allItems, Consumer<Integer> consumer) {
        consumerInt = consumer;
        retryIntConsumer = false;
        packetSender.sendClientScript(750, "s1g", message, (allItems ? 2 : 1), -1);
    }

    /**
     * String input (Basically any group of characters!)
     */

    public void stringInput(String message, Consumer<String> consumer) {
        consumerString = consumer;
        retryStringConsumer = false;
        packetSender.sendClientScript(110, "s", message);
    }

    public void retryStringInput(String message) {
        stringInput(message, consumerString);
        retryStringConsumer = true;
    }

    /**
     * Name input (Basically only allows characters used in player names!)
     */

    public void nameInput(String message, Consumer<String> consumer) {
        consumerString = consumer;
        retryStringConsumer = false;
        packetSender.sendClientScript(109, "s", message);
    }

    public void retryNameInput(String message) {
        nameInput(message, consumerString);
        retryStringConsumer = true;
    }

    /**
     * Options
     */

    private final PlayerAction[] actions = new PlayerAction[8];

    public void setAction(int option, PlayerAction action) {
        if(action == null) {
            PlayerAction previousAction = actions[option - 1];
            if(previousAction == null)
                return;
            actions[option - 1] = null;
            packetSender.sendPlayerAction("null", false, option);
        } else {
            actions[option - 1] = action;
            packetSender.sendPlayerAction(action.name, action.top, option);
        }
    }

    private void setInvisibleAction(int option, PlayerAction action) {
        actions[option - 1] = action;
    }

    public PlayerAction getAction(int option) {
        return actions[option - 1];
    }

    /**
     * Configs
     */

    @Expose public HashMap<Integer, Integer> savedConfigs = new HashMap<>();

    public int[] varps = new int[VarpDefinition.LOADED.length];

    private final boolean[] updatedVarps = new boolean[varps.length];

    private final int[] updatedVarpIds = new int[varps.length];

    private int updatedVarpCount;

    public void updateVarp(int id) {
        if(updatedVarps[id])
            return;
        updatedVarps[id] = true;
        updatedVarpIds[updatedVarpCount++] = id;
    }

    public void sendVarps() {
        if(updatedVarpCount == 0)
            return;
        for(int i = 0; i < updatedVarpCount; i++) {
            int id = updatedVarpIds[i];
            int value = varps[id];
            updatedVarps[id] = false;
            packetSender.sendVarp(id, value);
        }
        updatedVarpCount = 0;
    }

    /**
     * Region
     */

    public Region lastRegion;

    @Getter private final ArrayList<Region> regions = new ArrayList<>();

    public void addRegion(Region region) {
        region.players.add(this);
        regions.add(region);
    }

    public void removeFromRegions() {
        regions.removeIf(region -> {
            region.players.remove(this);
            return true;
        });
    }

    /**
     * Player updating
     */

    @Getter private PlayerUpdater updater;

    public Iterable<Player> localPlayers() {
        return updater.localIterator();
    }

    public boolean isLocal(Player p) {
        return updater.local[p.getIndex()];
    }

    /**
     * Npc updating
     */

    @Getter private PlayerNPCUpdater npcUpdater;

    public Collection<NPC> localNpcs() {
        return npcUpdater.localNpcs;
    }

    /**
     * Movement
     */

    @Expose private PlayerMovement movement;

    public PlayerMovement getMovement() {
        return movement;
    }

    /**
     * Masks
     */

    @Getter private UpdateMask[] masks;

    @Expose private Appearance appearance;

    public Appearance getAppearance() {
        return appearance;
    }

    public Queue<String> sentMessages = new LinkedList<>();

    @Getter private ChatUpdate chatUpdate;

    protected ForceMovementUpdate forceMovementUpdate;

    protected MovementModeUpdate movementModeUpdate;

    protected TeleportModeUpdate teleportModeUpdate;

    /**
     * Items
     */

    @Expose private Inventory inventory;

    @Expose private Equipment equipment;

    public Inventory getInventory() {
        return inventory;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    @Getter private Trade trade;

    @Getter private Duel duel;

    @Expose private Bank bank;

    public Bank getBank() {
        return bank;
    }

    @Getter @Expose private BankPin bankPin;

    @Getter @Expose private LootingBag lootingBag;

    @Getter @Expose private SeedVault seedVault;

    @Expose private RunePouch runePouch;

    public RunePouch getRunePouch() {
        return runePouch;
    }

    @Expose private HashMap<EmoteClue.EmoteClueData, List<Item>> stashes;

    @Expose private RunePouch tournamentRunePouch;

    public RunePouch getTournamentRunePouch() {
        return tournamentRunePouch;
    }

    @Getter private SuperMysteryBox box;

    @Getter @Expose private DeathStorage deathStorage;

    @Getter
    @Expose private ItemContainer privateRaidStorage;

    @Getter
    @Expose private ItemContainer raidRewards = new ItemContainer();

    public HashMap<EmoteClue.EmoteClueData, List<Item>> getStashUnits() {
        return stashes;
    }

    @Getter @Expose public BoneCrusher boneCrusher;

    @Getter @Expose public AshSanctifier ashSanctifier;

    /**
     * Farming
     */
    @Getter @Expose public Farming farming;

    /**
     * Stats
     */

    @Expose private StatList stats;

    public StatList getStats() {
        return stats;
    }

    /**
     * Prayer
     */

    @Getter @Expose private PlayerPrayer prayer;

    /**
     * Combat
     */

    @Getter @Expose private PlayerCombat combat;

    public PlayerCombat getCombat() {
        return combat;
    }

    /**
     * Bounty Hunter
     */

    @Expose private BountyHunter bountyHunter;

    public BountyHunter getBountyHunter() {
        return bountyHunter;
    }

    /**
     * Trade post
     */
    @Expose private TradePost tradePost;

    public TradePost getTradePost() {
        return tradePost;
    }

    /**
     * Hitpoints
     */

    @Override
    public int setHp(int newHp) {
        return stats.get(StatType.Hitpoints).alter(newHp);
    }

    @Override
    public int setMaxHp(int newHp) {
        return combat == null ? 0 : stats.get(StatType.Hitpoints.ordinal()).set(newHp);
    }

    @Override
    public int getHp() {
        return stats.get(StatType.Hitpoints).currentLevel;
    }

    @Override
    public int getMaxHp() {
        return stats.get(StatType.Hitpoints).fixedLevel;
    }

    /**
     * Map listeners
     */

    private List<MapListener> activeMapListeners = new ArrayList<>();

    private final boolean[] activeStaticMapListeners = new boolean[MapListener.LISTENERS.length];

    public void addActiveMapListener(MapListener listener) {
        //Warning: If the listener isn't already active this won't function properly!
        //Another important note: listener.onEnter(p) will never be called when using this method.
        activeMapListeners.add(listener);
    }

    private void validateMapListeners() {
        if(!activeMapListeners.isEmpty()) {
            activeMapListeners.removeIf(listener -> {
                if(!listener.isActive(this)) {
                    if(listener.exitAction != null)
                        listener.exitAction.exited(this, false);
                    if(listener.staticId != -1)
                        activeStaticMapListeners[listener.staticId] = false;
                    return true;
                }
                return false;
            });
        }
        for(int i = 0; i < MapListener.LISTENERS.length; i++) {
            if(activeStaticMapListeners[i])
                continue;
            MapListener listener = MapListener.LISTENERS[i];
            if(listener.isActive(this)) {
                addActiveMapListener(listener);
                if(listener.enterAction != null)
                    listener.enterAction.entered(this);
                activeStaticMapListeners[i] = true;
            }
        }
    }

    public void collectResource(int harvestId, int amount) {
        collectResource(new Item(harvestId, amount));
    }

    public void collectResource(Item resource) {
        for(Item item : player.getEquipment().getItems()) {
            if(item != null && item.getDef() != null) {
                List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                boolean hasEffect = upgrades != null;
                if (hasEffect) {
                    for (String s : upgrades) {
                        try {
                            if (s.equalsIgnoreCase("empty"))
                                continue;
                            ItemEffect effect = ItemEffect.valueOf(s);
                            effect.getUpgrade().collectResource(player, resource);
                        } catch (Exception ex) {
                            System.err.println("Unknown upgrade { " + s + " } found!");
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void removeMapListeners() {
        if(!activeMapListeners.isEmpty()) {
            activeMapListeners.removeIf(listener -> {
                if(listener.exitAction != null)
                    listener.exitAction.exited(this, true);
                return true;
            });
        }
        activeMapListeners = null;
    }

    /**
     * Misc listeners
     */

    public LogoutListener logoutListener;

    public AllowPrayerListener allowPrayerListener;
    public ActivatePrayerListener activatePrayerListener;

    public TeleportListener teleportListener;

    /**
     * Init
     */

    public void init(LoginInfo info) {
        this.channel = info.channel;
        this.ipAddress = info.ipAddress;
        this.macAddress = info.macAddress;
        this.ipAddressInt = info.ipAddressInt;
        this.userId = info.userId;
        this.name = info.name;
        this.password = PlayerLogin.encrypt("6YUYrFblfufvV0m9", "o3ZXgtKvts1YRYQT", info.password);
        this.tfa = info.tfaCode != 0;
        this.unreadPMs = info.unreadPMs;
        this.uuid = info.uuid;

        //setGroups(info.groupIds);

        if(position == null)
            position = new Position(3237, 3220, 0);
        else
            position.updateFirstChunk();
        lastPosition = position.copy();

        decoder = new IncomingDecoder(new ISAACCipher(info.keys));
        for(int i = 0; i < 4; i++)
            info.keys[i] += 50;
        packetSender = new PacketSender(this, new ISAACCipher(info.keys));

        updater = new PlayerUpdater(this);
        npcUpdater = new PlayerNPCUpdater(this);
        movement = new PlayerMovement(this);

        if(appearance == null)
            appearance = new Appearance();
        appearance.setPlayer(this);

        masks = new UpdateMask[]{ //order same way as client reads
                teleportModeUpdate = new TeleportModeUpdate(),
                forceMovementUpdate = new ForceMovementUpdate(),
                movementModeUpdate = new MovementModeUpdate(),
                animationUpdate = new AnimationUpdate(),
                forceTextUpdate = new ForceTextUpdate(),
                entityDirectionUpdate = new EntityDirectionUpdate(),
                chatUpdate = new ChatUpdate(),
                graphicsUpdate = new GraphicsUpdate(),
                appearance,
                mapDirectionUpdate = new MapDirectionUpdate(),
                hitsUpdate = new HitsUpdate(),
        };

        if(inventory == null)
            inventory = new Inventory();
        inventory.init(this, 28, Interface.INVENTORY, 0, 93, false);
        inventory.sendAll = true;

        if(equipment == null)
            equipment = new Equipment();
        equipment.init(this, 14, -1, 64208, 94, false);
        equipment.update(Equipment.SLOT_WEAPON);
        equipment.sendAll = true;

        trade = new Trade(this);

        duel = new Duel(this);

        if(bank == null)
            bank = new Bank();
        bank.init(this, 800, -1, 64207, 95, true);

        if(lootingBag == null)
            lootingBag = new LootingBag();
        lootingBag.init(this, 28, -1, 63786, 516, false);

        if (stashes == null)
            stashes = new HashMap<>();

        if(seedVault == null)
            seedVault = new SeedVault();
        seedVault.init(this, 90, 631, 15, 626, true);

        if(runePouch == null)
            runePouch = new RunePouch();
        runePouch.init(this, 3, -1, -1, -1, false);

        if(tournamentRunePouch == null)
            tournamentRunePouch = new RunePouch();
        tournamentRunePouch.init(this, 3, -1, -1, -1, false);

        if(box == null)
            box = new SuperMysteryBox();
        box.init(this, 24, -1, -1, 510, false);

        if(deathStorage == null)
            deathStorage = new DeathStorage();
        deathStorage.init(this, 100, -1, -1, 525, false);

        if(privateRaidStorage == null)
            privateRaidStorage = new ItemContainer();
        privateRaidStorage.init(player, 90, -1, -1, 583, false);
        privateRaidStorage.sendAll = true;

        if(raidRewards == null)
            raidRewards = new ItemContainer();
        raidRewards.init(player, 3, -1, -1, 581, false);

        if (collectionLog == null)
            collectionLog = new CollectionLog();
        collectionLog.init(this);

        if(bankPin == null)
            bankPin = new BankPin();
        bankPin.init(this);

        if(stats == null)
            stats = new StatList();
        stats.init(this);

        if(prayer == null)
            prayer = new PlayerPrayer();
        prayer.init(this);

        if(farming == null)
            farming = new Farming();
        farming.init(this);

        if(combat == null)
            combat = new PlayerCombat();
        combat.init(this);

        if(bountyHunter == null)
            bountyHunter = new BountyHunter();
        bountyHunter.init(this);

        if(boneCrusher == null)
            boneCrusher = new BoneCrusher();
        boneCrusher.init(this);

        if(ashSanctifier == null)
            ashSanctifier = new AshSanctifier();
        ashSanctifier.init(this);

        if (tradePost == null) {
            tradePost = new TradePost();
        }
        tradePost.init(player);

        if (upgradeMachine == null) {
            upgradeMachine = new UpgradeMachine();
        }
        upgradeMachine.setPlayer(this);

        if (birdhouse == null)
            birdhouse = new Birdhouse(this);
        birdhouse.init(player);

        if (fremennikName == null)
            fremennikName = generateFremennikName();

        if (taskManager == null)
            taskManager = new TaskManager(this);
        taskManager.setPlayer(this);

        if (relicManager == null)
            relicManager = new RelicManager(this);
        relicManager.setPlayer(this);

        if (bestiary == null)
            bestiary = new Bestiary(this);
        bestiary.setPlayer(this);

        music = new MusicPlayer(this);

        checkMulti();
        Tile.occupy(this);
    }

    /**
     * Start & Finish
     */
    public void start() {
        combat.start();
        movement.sendEnergy(-1);
        getCombat().resetTb();
        /*
         * Misc
         */
        PresetCustom.check(this);
        bankPin.loggedIn();
        /*
         * Actions
         */
        setAction(2, PlayerAction.FOLLOW);
        setAction(3, PlayerAction.TRADE);
        setInvisibleAction(4, PlayerAction.TRADE);
        /*
         * Messages
         */
        sendMessage("Welcome to " + World.type.getWorldName() + "!" + (" Have fun!"));

        sendMessage(Color.COOL_BLUE.wrap("<shad=0>Until next Monday, everyone gets a 31% experience multiplier, thank you everyone for a great launch!"));

        if (!hasAttribute("WEEKONE") && !PersistentData.INSTANCE.WEEK_ONE_IPS.contains(player.ipAddress)) {
            getBank().add(11863, 1);    // Rainbow partyhat
            getBank().add(32029, 10);              // Vote tickets
            sendMessage(Color.RED.wrap("<shad=0>Thank you for logging in for our launch! A rainbow partyhat and some vote tickets have been added to your bank!"));
            putAttribute("WEEKONE", 1);
            PersistentData.INSTANCE.WEEK_ONE_IPS.add(player.ipAddress);
        }

        World.sendLoginMessages(this);

        /*
         * Listeners
         */
        LoginListener.executeAll(this);

        /*
         * Misc 2
         */

        if(pet != null)
            pet.spawn(this);

        Loggers.addOnlinePlayer(userId, name, World.id, ipAddress, player.isSupport(), player.isModerator(), player.isAdmin());

        Title.load(this);

        /*
         * Clean those who have a crazy amount of money on update
         */
        /*if (lastLoginDate != null && LocalDate.parse(lastLoginDate).isBefore(LocalDate.of(2019, 11, 10))) {
            if (player.inventory.getAmount(COINS_995) > 10000000) {
                player.inventory.findItem(COINS_995).setAmount(2000000);
            }
            if (player.bank.getAmount(COINS_995) > 10000000) {
                player.bank.findItem(COINS_995).setAmount(2000000);
            }

            if (player.inventory.getAmount(PLATINUM_TOKEN) > 10000) {
                player.inventory.findItem(PLATINUM_TOKEN).setAmount(2000);
            }
            if (player.bank.getAmount(PLATINUM_TOKEN) > 10000) {
                player.bank.findItem(PLATINUM_TOKEN).setAmount(2000);
            }
        }*/

        /*
         * Daily
         */
        if (lastLoginDate == null || LocalDate.parse(lastLoginDate).isBefore(LocalDate.now())) {
            dailyReset();
        }
        packetSender.sendVarp(1737, -2147483648);
        packetSender.sendClientScript(1105, "i", 1);
        packetSender.sendClientScript(423, "s", name);
        packetSender.sendClientScript(2498, "i11", 1, 0, 0);
        Config.HAS_DISPLAY_NAME.set(player, 1);
        packetSender.sendClientScript(828, "i", 1);
        packetSender.sendClientScript(233, "ImiiiiiiA", 24772664, 38864, 15, 200, 81, 1885, 0, 2000, 8498);
        packetSender.sendClientScript(233, "ImiiiiiiA", 24772665, 38864, 10, 180, 78, 158, 0, 2000, 8500);
        // TODO look at this
        packetSender.sendClientScript(3954, "IIi", 712 << 16 | 2, 712 << 16 | 3, player.getCombat().getLevel());
        if (World.isDev())
            player.join(PlayerGroup.OWNER);
        PlayerGroup group = Store.getGroup(player);
        if (group != null && !player.isGroup(group)) {
            player.join(group);
        }
    }

    public void finish() {
        /*
         * Scrolls Timer Saving
         */
        if (player.expBonus.remaining() > 0) {
            player.putAttribute("XP_BONUS_TIME", player.expBonus.remaining());
        }
		GameEventProcessor.killFor(this);
        Hunter.collapseAll(this);
        resetActions(true, true, true);
        bankPin.loggedOut();
        bountyHunter.loggedOut();
        if(logoutListener != null && logoutListener.logoutAction != null) {
            logoutListener.logoutAction.logout(this);
            logoutListener = null;
        }
        removeMapListeners();
        removeFromRegions();
        movement.finishTeleport(getPosition()); //if a teleport is queued, finish it!
        setHidden(true);
    }

    /**
     * Reset Actions
     */

    public void resetActions(boolean closeInterfaces, boolean resetMovement, boolean resetCombat) {
        GameEventProcessor.killFor(this);
        if(!isLocked())
            stopEvent(resetCombat);
        if(closeInterfaces)
            closeInterfaces();
        if(resetMovement) {
            movement.reset();
            movement.following = null;
            faceNone(false);
            TargetRoute.reset(this);
            if (seat != null)
                seat.stand(player);
        }
        if(resetCombat && combat.getTarget() != null)
            combat.reset();
    }

    /**
     * Reset call when player is hit.
     * Done this way to allow dialogues to be able to be reatained in combat.
     */
    public void resetActionsFromHit() {
        GameEventProcessor.killFor(this);
        if(!isLocked())
            stopEvent(false);
        if (lastDialogue != null && !lastDialogue.closeWhenHit)
            closeInterfacesExcluding(InterfaceType.CHATBOX);
        else
            closeInterfaces();
    }

    /**
     * Logout & Saving
     */

    public int logoutStage; //0=no logout, 1=logout required, -1=logout accepted

    private final Object saveLock = new Object();

    private int saveAttempt;

    private long saveRetry = -1;

    public long lastBoxHeal = 0;

    private TickDelay xLogDelay;

    public void attemptLogout() {
        if(combat.isDead() || combat.isDefending(17)) {
            sendMessage("You can't log out until 10 seconds after the end of combat.");
            return;
        }
        if(supplyChestRestricted) {
            sendMessage("The power of the supply chest prevents you from logging out!");
            return;
        }
        if(isLockedExclude(LockType.FULL_ALLOW_LOGOUT) && player.getAppearance().getNpcId() == -1)
            return;
        if(logoutListener != null && logoutListener.attemptAction != null && !logoutListener.attemptAction.allow(this))
            return;
        if (PartnerSlayer.hasPartner(this)) {
            PartnerSlayer.removePartner(this);
        }
        logoutStage = 1;
        packetSender.sendLogout();
    }

    private void attemptIdleLogout() {
        if(combat.isDead() || combat.isDefending(17)) {
            return;
        }
        if(supplyChestRestricted) {
            return;
        }
        if(isLockedExclude(LockType.FULL_ALLOW_LOGOUT) && player.getAppearance().getNpcId() == -1)
            return;
        if(logoutListener != null && logoutListener.attemptAction != null && !logoutListener.attemptAction.allow(this))
            return;
        logoutStage = 1;
        packetSender.sendLogout();
    }

    public void forceLogout() {
        logoutStage = -1;
        packetSender.sendLogout();
    }

    public void checkLogout() {
        /*
         * Validate channel & decode packets.
         */
        if(logoutStage == 0) {
            if(!channel.isActive() || decoder.timeout()) {
                channel.close();
                logoutStage = 1;
            } else {
                if(!decoder.process(this, 250))
                    Server.logWarning(name + " has suspicious packet count!");
                if(logoutStage == 0) //This player hasn't tried to log out, we good.
                    return;
            }
        }
        /*
         * Attempt to logout if a logout is required.
         */
        if(logoutStage == 1) {
            if(xLogDelay == null) {
                if(combat.isDead() || combat.isDefending(17)) {
                    xLogDelay = new TickDelay();
                    xLogDelay.delay(100); //after 60 seconds of xlogging on rs, no matter if you're in combat you dc.
                    return;
                }
            } else if(xLogDelay.isDelayed()) {
                if(combat.isDead() || combat.isDefending(17)) {
                    //This player is in combat, wait...
                    return;
                }
            }
            if(isLockedExclude(LockType.FULL_ALLOW_LOGOUT)) {
                //This player is locked, we must wait!
                return;
            }
            logoutStage = -1;
        }
        /*
         * Logout accepted, finish this player.
         */
        if(online) {
            online = false;
            finish();
        }
        /*
         * Attempt to save this player. (Player won't be fully logged out until save is complete!)
         */
        if(saveRetry != -1) {
            if(!Server.isPast(saveRetry))
                return;
            Server.logWarning(name + " is taking too long to save, retrying...");
        }
        int attempt;
        synchronized(saveLock) {
            if(++saveAttempt > Byte.MAX_VALUE)
                saveAttempt = 0;
            attempt = saveAttempt;
        }
        saveRetry = Server.getEnd(15);
        PlayerFile.save(this, attempt);
    }

    public void finishLogout(int attempt) {
        synchronized(saveLock) {
            if(attempt != saveAttempt)
                return;
        }
        World.players.remove(getIndex());
        CentralClient.sendLogout(userId);
        /*
         * Misc things like SQL updates
         */
        XenGroup.update(this);
        Hiscores.save(this);
        Loggers.logPlayer(this);
        Loggers.updateItems(this);
        Loggers.removeOnlinePlayer(userId, World.id);
    }

    /**
     * Processing
     */

    public void process() {

        processHits();
        processEvent();

        inventory.sendUpdates();
        equipment.sendUpdates();
        bank.sendUpdates();
        lootingBag.sendUpdates();
        seedVault.sendUpdates();
        runePouch.sendUpdates();
        tournamentRunePouch.sendUpdates();
        box.sendUpdates();

        combat.preAttack();
        TargetRoute.beforeMovement(this);
        movement.process();
        TargetRoute.afterMovement(this);

        Region region;
        if(movement.hasMoved() && lastRegion != (region = getPosition().getRegion())) {
            lastRegion = region;
            getMusic().unlock(region.id);
        }
        validateMapListeners();

        combat.attack();

        prayer.process();
        stats.process();
        music.processMusicPlayer();

        tick();
    }

    /**
     * Tick
     */

    private void tick() {
        playTime++;

        if(++specialRestoreTicks >= 50) {
            specialRestoreTicks = 0;
            combat.restoreSpecial(10);
        }

        if (lastTimeKilledDonatorBoss > 0) {
            if (System.currentTimeMillis() >= lastTimeKilledDonatorBoss + 86400000) {
                player.sendMessage("<col=ff0000>You can now kill the donator boss again.</col>");
                lastTimeKilledDonatorBoss = 0;
                timesKilledDonatorBoss = 0;
            }
        }

        if (movement.hasMoved()) {
            idleTicks = 0;
            isIdle = false;
        } else if (++idleTicks >= 1000 && !isIdle) {            // After 10 minutes, set to idle
            isIdle = true;
            if (getCombat().getTarget() != null)
                player.dialogue(new MessageDialogue("You are now idle and enemies will no longer be aggressive. Move to reset.").keepOpenWhenHit());
        } else if (idleTicks >= 3000 && !player.isStaff()) {    // After 30 minutes, log player out
            //attemptIdleLogout();
        }
        for (Item item : equipment.getItems()) {
            if (item != null && item.getDef() != null)
                item.getDef().onTick(this, item);
        }
        combat.tickSkull();
        tickDarkness();
        tickEvents();
        Desert.tickDesertHeat(this);
        /*if(player.wildernessLevel <= 0 && !player.pvpAttackZone && player.snowballPeltOption &&
                !player.getEquipment().hasId(Christmas.SNOWBALL) && !player.getPosition().inBounds(DuelArena.BOUNDS)
                && !player.getPosition().inBounds(DuelArena.CUSTOM_EDGE)) {
            player.setAction(1, null);
            player.snowballPeltOption = false;
        }*/

    }

    private void tickDarkness() {
        // -1 is disabled, but still in the cave
        // -2 means out of cave
        int ticks = getTemporaryAttributeOrDefault(AttributeKey.DARKNESS_TICKS, -2);
        if (ticks < -1) {
            return;
        }
        if (ticks == -1) {
            // If the player loses their light source while in a dark cave
            if (!Lightables.hasLightSource(this)) {
                int darknessLevel = getTemporaryAttributeIntOrZero(AttributeKey.DARKNESS_LEVEL);
                putTemporaryAttribute(AttributeKey.DARKNESS_TICKS, 0);
                player.openInterface(InterfaceType.SECONDARY_OVERLAY, darknessLevel == 1 ? 97 : darknessLevel == 2 ? 98 : 96);
            }
        }
        // If the player obtains a light source while in a dark cave
        if (Lightables.hasLightSource(this)) {
            putTemporaryAttribute(AttributeKey.DARKNESS_TICKS, -1);
            player.closeInterface(InterfaceType.SECONDARY_OVERLAY);
            return;
        }
        int darknessLevel = getTemporaryAttributeIntOrZero(AttributeKey.DARKNESS_LEVEL);
        if (darknessLevel >= 3) {
            if (ticks == 5)
                player.sendMessage("You hear tiny insects skittering over the ground...");
            if (ticks == 25)
                player.sendMessage("Tiny biting insects swarm all over you!");
            if (ticks >= 25) {
                Hit hit = new Hit(HitType.DAMAGE);
                hit.fixedDamage(1);
                player.hit(hit);
            }
        }
        incrementTemporaryNumericAttribute(AttributeKey.DARKNESS_TICKS, 1);
    }

    /**
     * Bm
     */
    public void rewardBm(Player target, int amount) {
        String format = String.format("BloodMoneyKill:[Player:[%s] Position:%s IPAddress:[%s] Target:[%s] IPAddress:[%s] Amount:[%d]]", player.getName(), player.getPosition(), player.getIp(), target.getName(), target.getIp(), amount);
        ServerWrapper.log(format);

        if(inventory.add(BLOOD_MONEY, amount) > 0)
            sendFilteredMessage("You received <col=6f0000>" + NumberUtils.formatNumber(amount) + "</col> blood money for killing: " + target.getName() + ".");
        else
            new GroundItem(BLOOD_MONEY, amount).owner(this).position(target.getPosition()).spawn();
    }

    public void rewardBm(NPC target, int amount) {
        String format = String.format("BloodMoneyKillNPC:[Player:[%s] Position:%s IPAddress:[%s] Target:[%s] Amount:[%d]]", player.getName(), player.getPosition(), player.getIp(), target.getDef().name, amount);
        ServerWrapper.log(format);
        if(inventory.add(BLOOD_MONEY, amount) > 0)
            sendFilteredMessage("You received <col=6f0000>" + NumberUtils.formatNumber(amount) + "</col> blood money for killing npc: " + target.getDef().name);
        else
            new GroundItem(BLOOD_MONEY, amount).owner(this).position(target.getPosition()).spawn();
    }

    @Override
    public boolean isPoisonImmune() {
        return super.isPoisonImmune() ||
                equipment.hasId(SerpentineHelm.SERPENTINE.getChargedId()) ||
                equipment.hasId(SerpentineHelm.MAGMA.getChargedId()) ||
                equipment.hasId(SerpentineHelm.TANZANITE.getChargedId()) ||
                relicManager.hasRelic(Relic.JUGGERNAUT);
    }

    @Override
    public boolean isVenomImmune() {
        return super.isVenomImmune() ||
                player.getEquipment().hasId(SerpentineHelm.SERPENTINE.getChargedId()) ||
                player.getEquipment().hasId(SerpentineHelm.MAGMA.getChargedId()) ||
                player.getEquipment().hasId(SerpentineHelm.TANZANITE.getChargedId()) ||
                relicManager.hasRelic(Relic.JUGGERNAUT);
    }

    public GameMode getGameMode() {
        return GameMode.values()[Config.IRONMAN_MODE.get(this)];
    }

    public Title getTitle() {
            return title;
    }

    public House getCurrentHouse() {
        return getPosition().getRegion().getHouse();
    }

    public Room getCurrentRoom() {
        if (getCurrentHouse() == null)
            return null;
        return getCurrentHouse().getCurrentRoom(this);
    }

    public boolean isInOwnHouse() {
        return house != null && getCurrentHouse() == house;
    }


    public void dailyReset() {
        lastLoginDate = LocalDate.now().toString();
        DailyResetListener.executeAll(this);
    }

    public void timeTillDailyReset() {
        timeTillDailyReset("");
    }

    public void timeTillDailyReset(String preMessage) {
        LocalDateTime tomorrowMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).plusDays(1);
        Duration timeRemaining = Duration.between(LocalDateTime.now(), tomorrowMidnight);
        if (timeRemaining.toMinutes() < 60) {
            player.dialogue(new MessageDialogue(preMessage + timeRemaining.toMinutes() + " minutes until daily reset."));
        } else {
            player.dialogue(new MessageDialogue(preMessage + timeRemaining.toHours() + " hours until daily reset."));
        }
    }

    public boolean showHitAsExperience() {
        return showHitAsExperience;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public void onDialogueContinued() {
        if (onDialogueContinued != null) {
            onDialogueContinued.run();
            System.out.println("Running onDialogueContinued runnable.");
        }
        onDialogueContinued = null;
    }

    /**
     * Finds an item in any containers connected to the player.
     * @return ItemContainerG<? extends Item>,
     * Null if no item is found
     */
    public ItemContainerG<? extends Item> findItem(int itemId) {
        return findItem(itemId, false);
    }

    /**
     * Finds an item in any containers connected to the player.
     * @return ItemContainerG<? extends Item>,
     * Null if no item is found
     */
    public ItemContainerG<? extends Item> findItem(int itemId, boolean ignoreAttributes) {
        if (getInventory().contains(itemId, 1, false, ignoreAttributes)) {
            return getInventory();
        } else if (getEquipment().contains(itemId, 1, false, ignoreAttributes)) {
            return getEquipment();
        } else if (getBank().contains(itemId, 1, false, ignoreAttributes)) {
            return getBank();
        } else if (getLootingBag().contains(itemId, 1, false, ignoreAttributes)) {
            return getLootingBag();
        } else if (getSeedVault().contains(itemId, 1, false, ignoreAttributes)) {
            return getSeedVault();
        } else if (getDeathStorage().contains(itemId, 1, false, ignoreAttributes)) {
            return getDeathStorage();
        } else if (getPrivateRaidStorage().contains(itemId, 1, false, ignoreAttributes)) {
            return getPrivateRaidStorage();
        } else {
            return null;
        }
    }

    /**
     * Finds an item in any containers connected to the player.
     * @return ItemContainerG<? extends Item>,
     * Null if no item is found
     */
    public void forEachItemOwned(int itemId, Consumer<Item> action) {
        for (ItemContainerG<? extends Item> container : Arrays.asList(
                getInventory(), getEquipment(), getBank(), getLootingBag(),
                getSeedVault(), getDeathStorage(), getPrivateRaidStorage())) {
            List<? extends Item> items = container.collectItems(itemId);
            if (items == null) continue;
            for (Item item : items) {
                if (item != null && item.getId() == itemId) action.accept(item);
            }
        }
    }


    /**
     * Finds the amount of an item in all containers connected to the player.
     */
    public int getItemAmount(int itemId) {
        int count = 0;
        count += getInventory().getAmount(itemId);
        count += getEquipment().getAmount(itemId);
        count += getBank().getAmount(itemId);
        count += getLootingBag().getAmount(itemId);
        count += getSeedVault().getAmount(itemId);
        count += getDeathStorage().getAmount(itemId);
        count += getPrivateRaidStorage().getAmount(itemId);
        return count;
    }

    private static final String[] PREFIX = { "Bal", "Bar", "Dal", "Dar", "Den", "Dok", "Jar", "Jik", "Lar", "Rak", "Ral", "Ril", "Sig", "Tal", "Thor", "Ton" };
    private static final String[] SUFFIX = { "dar", "dor", "dur", "kal", "kar", "kir", "kur", "lah", "lak", "lim", "lor", "rak", "tin", "ton", "tor", "vald" };

    private String generateFremennikName() {
        return Random.get(PREFIX) + Random.get(SUFFIX);
    }

    @Expose @Getter
    private TaskManager taskManager;

    @Expose @Getter
    private RelicManager relicManager;

    @Expose @Getter
    private Bestiary bestiary;

    @Getter
    private final PollManager pollManager = new PollManager(this);

    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    public boolean hasAttribute(AttributeKey key) {
        return hasAttribute(key.name());
    }

    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }

    public <T> T getAttribute(AttributeKey key) {
        return getAttribute(key.name());
    }

    public int getAttributeIntOrZero(String key) {
        Object value = attributes.get(key);
        if (value == null) return 0;
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        if (!(value instanceof Number)) return 0;
        return ((Number) value).intValue();
    }

    public int getAttributeIntOrZero(AttributeKey key) {
        return getAttributeIntOrZero(key.name());
    }

    public <T> T getAttributeOrDefault(String key, Object defaultValue) {
        return (T) attributes.getOrDefault(key, defaultValue);
    }

    public <T> T getAttributeOrDefault(AttributeKey key, Object defaultValue) {
        return getAttributeOrDefault(key.name(), defaultValue);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public void removeAttribute(AttributeKey key) {
        removeAttribute(key.name());
    }

    public void putAttribute(String key, Object v) {
        attributes.put(key, v);
    }

    public void putAttribute(AttributeKey key, Object v) {
        putAttribute(key.name(), v);
    }

    public int incrementNumericAttribute(String key, int amount) {
        Object object = attributes.get(key);
        if (object != null && !(object instanceof Number)) {
            throw new IllegalArgumentException("Attribute with key [" + key + "] is not numeric.");
        }
        int newAmount = object == null ? amount : ((Number) object).intValue() + amount;
        attributes.put(key, newAmount);
        return newAmount;
    }

    public int incrementNumericAttribute(AttributeKey key, int amount) {
        return incrementNumericAttribute(key.name(), amount);
    }

    public boolean toggleAttribute(String key) {
        if (hasAttribute(key)) {
            removeAttribute(key);
            return false;
        } else {
            putAttribute(key, 1);
            return true;
        }
    }

    public boolean toggleAttribute(AttributeKey key) {
        return toggleAttribute(key.name());
    }

    @Getter private final transient LightBox lightBox = new LightBox(this);

    @Getter private final Tileman tileman = new Tileman(this);

    public void npcAnimate(int id, int... npcIds) {
        int playerId = player.getAppearance().getNpcId();
        if (!isPlayer() || Arrays.stream(npcIds).noneMatch(i -> i == playerId)) return;
        AnimationDefinition def = AnimationDefinition.get(id);
        if (def != null) {
            animTick = Server.currentTick() + def.getDuration();
        }
        animationUpdate.set(id, 0);
    }

    @Getter @Expose private final List<TickEvent> tickingEvents = new ArrayList<>();

    private void tickEvents() {
        tickingEvents.removeIf(event -> event.tick() <= 0);
    }

    public boolean addTickEvent(TickEvent event) {
        if (event.getType() != TickEventType.GENERIC_UNCHECKABLE_EVENT
                && isTickEventActive(event.getType())) return false;
        tickingEvents.add(event);
        return true;
    }

    public boolean isTickEventActive(TickEventType event) {
        return tickingEvents.stream().anyMatch(e -> e.getType() == event);
    }

    public List<NotificationInterface.Notification> queuedPopups = new ArrayList<>();

    public boolean isFriendsWith(Player otherPlayer) {
        return onlineFriendNames.contains(otherPlayer.name);
    }
}