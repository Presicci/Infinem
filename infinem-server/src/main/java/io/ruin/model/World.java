package io.ruin.model;

import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.api.database.DatabaseStatement;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.protocol.world.WorldFlag;
import io.ruin.api.protocol.world.WorldSetting;
import io.ruin.api.protocol.world.WorldStage;
import io.ruin.api.protocol.world.WorldType;
import io.ruin.api.utils.IPAddress;
import io.ruin.api.utils.Tuple;
import io.ruin.model.activities.duelarena.Duel;
import io.ruin.utility.Color;
import io.ruin.cache.Icon;
import io.ruin.content.activities.event.TimedEventManager;
import io.ruin.data.impl.polls;
import io.ruin.model.activities.combat.pvminstance.PVMInstance;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.EntityList;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerFile;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.owned.OwnedObject;
import io.ruin.model.map.object.owned.impl.DwarfCannon;
import io.ruin.process.event.EventWorker;
import io.ruin.utility.Broadcast;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class World extends EventWorker {

    public static int id;

    public static String name;

    public static WorldStage stage;

    public static WorldType type;

    public static WorldFlag flag;

    public static int settings;

    public static String address;

    public static int port;

    private static String centralAddress;

    public static boolean isDev() {
        return stage == WorldStage.DEV;
    }

    public static boolean isBeta() {
        return stage == WorldStage.BETA;
    }

    public static boolean isLive() {
        return stage == WorldStage.LIVE;
    }

    public static boolean isPVP() {
        return type == WorldType.PVP;
    }

    public static boolean isEco() {
        return type == WorldType.ECO;
    }

    public static final int spawnableOffset = 100000;

    public static final Bounds HOME = new Bounds(1673, 3137, 1676, 3140, 0);

    /**
     * Players
     */
    public static final EntityList<Player> players = new EntityList<>(new Player[1000]);

    public static Stream<Player> getPlayerStream() {
        return StreamSupport.stream(players.spliterator(), false)
                .filter(Objects::nonNull);
    }

    public static Player getPlayer(int index) {
        return players.get(index);
    }

    public static Player getPlayer(String name) {
        for(Player player : players) {
            if(player.getName().equalsIgnoreCase(name))
                return player;
        }
        return null;
    }

    public static Player getPlayer(int userId, boolean onlineReq) {
        if(onlineReq) {
            for(Player player : players) {
                if(player != null && player.getUserId() == userId)
                    return player;
            }
        } else {
            for(Player player : players.entityList) {
                if(player != null && player.getUserId() == userId)
                    return player;
            }
        }
        return null;
    }

    public static void sendSupplyChestBroadcast(String message) {
        players.forEach(p -> {
            if (p.broadcastSupplyChest)
                p.sendNotification(message);
        });
    }

    public static void sendGraphics(int id, int height, int delay, Position dest) {
        sendGraphics(id, height, delay, dest.getX(), dest.getY(), dest.getZ());
    }

    public static void sendGraphics(int id, int height, int delay, int x, int y, int z) {
        for(Player p : Region.get(x, y).players)
            p.getPacketSender().sendGraphics(id, height, delay, x, y, z);
    }

    /**
     * Npcs
     */
    public static final EntityList<NPC> npcs = new EntityList<>(new NPC[1000]);

    public static NPC getNpc(int index) {
        return npcs.get(index);
    }

    /**
     * PLAYER SAVERS
     */
    public static boolean doubleDrops;

    public static boolean doubleSlayer;

    public static boolean doublePest;

    public static int xpMultiplier = 0;

    public static int playerModifier = 0;

    public static int bmMultiplier = 0;

    public static boolean weekendExpBoost = false;

    public static void toggleDoubleDrops() {
        doubleDrops = !doubleDrops;
        Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "[Server]", "Double drops has been " + (doubleDrops ? "enabled! Start killing!" : "disabled."));
    }

    public static void toggleDoubleSlayer() {
        doubleSlayer = !doubleSlayer;
        Broadcast.WORLD.sendNews(Icon.GOLD_STAR,"[Server]", "Double Slayer Points has been " + (doubleSlayer ? "enabled! Start slaying!" : "disabled."));
    }

    public static void toggleDoublePest() {
        doublePest = !doublePest;
        Broadcast.WORLD.sendNews(Icon.GOLD_STAR,"[Server]", "Double Pest Control Points has been " + (doublePest ? "enabled! Start controlling those pests!" : "disabled."));
    }

    public static void boostXp(int multiplier) {
        xpMultiplier = multiplier;
        if(xpMultiplier == 1)
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "Experience is now normal. (x1)");
        else if(xpMultiplier == 2)
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "Experience is now being doubled! (x2)");
        else if(xpMultiplier == 3)
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "Experience is now being tripled! (x3)");
        else if(xpMultiplier == 4)
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "Experience is now being quadrupled! (x4)");
        else
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "Experience is now boosted! (x" + multiplier + ")");
    }

    /*
     * Sets the base amount of blood money user can get per kill
     */
    public static void setBaseBloodMoney(int baseBloodMoney) {
        Killer.BASE_BM_REWARD = baseBloodMoney;
    }

    public static void toggleWeekendExpBoost() {
        weekendExpBoost = !weekendExpBoost;
        if(weekendExpBoost) {
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "The 25% weekend experience boost is now activated!");
        } else {
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "The 25% weekend experience boost is now deactivated!");
        }
    }

    public static void boostBM(int multiplier) {
        bmMultiplier = multiplier;
        if(bmMultiplier == 1)
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "Blood money drops from player kills are now normal. (x1)");
        else if(bmMultiplier == 2)
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "Blood money drops from player kills are now being doubled! (x2)");
        else if(bmMultiplier == 3)
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "Blood money drops from player kills are now being tripled! (x3)");
        else if(bmMultiplier == 4)
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "Blood money drops from player kills are now being quadrupled! (x4)");
        else
            Broadcast.WORLD.sendNews(Icon.GOLD_STAR, "Blood money drops from player kills are now boosted! (x" + multiplier + ")");
    }

    public static void sendLoginMessages(Player player) {
        if(doubleDrops)
            player.sendMessage(Color.ORANGE_RED.tag() + "Npc drops are currently being doubled!");
        if(xpMultiplier == 2)
            player.sendMessage(Color.ORANGE_RED.tag() + "Experience is currently being doubled! (x2)");
        else if(xpMultiplier == 3)
            player.sendMessage(Color.ORANGE_RED.tag() + "Experience is currently being tripled! (x3)");
        else if(xpMultiplier == 4)
            player.sendMessage(Color.ORANGE_RED.tag() + "Experience is currently being quadrupled! (x4)");
    }

    public static boolean wildernessDeadmanKeyEvent = false;

    public static void toggleDmmKeyEvent() {
        wildernessDeadmanKeyEvent = !wildernessDeadmanKeyEvent;
    }

    public static boolean wildernessKeyEvent = false;
    public static void toggleWildernessKeyEvent() {
        wildernessKeyEvent = !wildernessKeyEvent;
    }

    public static Optional<Player> getPlayerByUid(int userId) {
        return getPlayerStream().filter(plr -> plr.getUserId() == userId).findFirst();
    }

    public static Optional<Player> getPlayerByName(String name) {
        return getPlayerStream().filter(plr -> plr.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Getter
    protected static final Map<String, OwnedObject> ownedObjects = Maps.newConcurrentMap();

    public static void registerOwnedObject(OwnedObject object) {
        ownedObjects.put(object.getOwnerName() + ":" + object.getIdentifier(), object);
    }

    public static OwnedObject getOwnedObject(Player owner, String identifier) {
        return ownedObjects.get(owner.getName() + ":" + identifier);
    }

    public static void deregisterOwnedObject(OwnedObject object) {
        ownedObjects.remove(object.getOwnerName()  + ":" + object.getIdentifier());
    }

    public static void addCannonReclaim(String username, boolean isOrnament) {
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO lost_cannons (username, ornament) VALUES (?, ?)");
            statement.setString(1, username);
            statement.setBoolean(2, isOrnament);
            statement.executeUpdate();
        });
    }

    public static void doCannonReclaim(String username, Consumer<Tuple<Boolean, Boolean>> consumer) {
        Server.gameDb.execute(new DatabaseStatement() {

            private boolean result;

            @Override
            public void execute(Connection connection) throws SQLException {

                PreparedStatement statement = null;
                ResultSet rs = null;
                try {
                    statement = connection.prepareStatement("SELECT * FROM lost_cannons WHERE UPPER(username) = UPPER(?)");
                    statement.setString(1, username);
                    rs = statement.executeQuery();
                    boolean isOrnament = false;
                    if (rs.next()) {
                        result = rs.getString(1).equalsIgnoreCase(username);
                        isOrnament =  rs.getBoolean(2);
                    }
                    boolean finalIsOrnament = isOrnament;
                    Server.worker.execute(() -> consumer.accept(new Tuple<>(result, finalIsOrnament)));
                } finally {
                    DatabaseUtils.close(statement, rs);
                }

            }
        });
    }

    public static void removeCannonReclaim(String username) {
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM lost_cannons WHERE username = ?");
            statement.setString(1, username);
            statement.execute();
        });
    }

    /**
     * Updating
     */
    public static boolean updating = false;

    public static boolean update(int minutes) {
        if(minutes <= 0) {
            updating = false;
            for(Player player : players)
                player.getPacketSender().sendSystemUpdate(0);
            System.out.println("System Update Cancelled");
            return true;
        }
        if(updating)
            return false;
        updating = true;
        System.out.println("System Update: " + minutes + " minutes");
        for(Player player : players)
            player.getPacketSender().sendSystemUpdate(minutes * 60);
        startEvent(e -> {
            int ticks = minutes * 100;
            while(updating) {
                if(--ticks <= 0 && removeBots() && removePlayers()) {
                    shutdown();
                    return;
                }
                e.delay(1);
            }
        });
        return true;
    }

    public static boolean removePlayers() {
        int pCount = players.count();
        if(pCount > 0) {
            System.out.println("Attempting to remove " + pCount + " players...");
            for(Player player : players) {
                GameObject cannon = World.getOwnedObject(player, DwarfCannon.IDENTIFIER);
                if (cannon != null) {
                    addCannonReclaim(player.getName(), Arrays.stream(DwarfCannon.ORNAMENT_CANNON_OBJECTS).anyMatch(e -> e == cannon.id));
                }
                Duel duel = player.getDuel();
                if (duel.stage >= 3) {
                    duel.handleDraw();
                }
                player.forceLogout();
            }
            return false;
        }
        PVMInstance.destroyAll();
        System.out.println("Players removed from world successfully!");
        return true;
    }

    private static boolean removeBots() {
        for(Player p : World.players) {
            if(p.getChannel().id() == null)
                p.logoutStage = -1;
        }
        return true;
    }

    private static void shutdown() {
        // Save poll data
        polls.toJson();
        System.exit(0);
    }

    /**
     * Holiday themes
     */
    public static boolean halloween;

    public static boolean isHalloween() {
        return halloween;
    }

    public static boolean christmas;

    public static boolean isChristmas() {
        return christmas;
    }

    /*
     * Save event
     */
    static {
        startEvent(e -> {
            while(true) {
                e.delay(100); //every 1 minute just in case..
                for(Player player : players)
                    PlayerFile.save(player, -1);
            }
        });
        startEvent(e -> {
            while(true) {
                e.delay(1);
                TimedEventManager.INSTANCE.tick();
            }
        });
    }

    /*
     * Announcement event
     */
    static {
        Server.afterData.add(() -> {
            List<String> announcements;
            announcements = Arrays.asList(
                    "Make sure to ::vote to help support the server and get free loot!",
                    "Need help? Join the \"help\" clan chat!",
                    "Take the time to protect your account and set a bank pin!",
                    "Infinem is made possible by donations, visit the ::store to help out!",
                    "Join the ::discord to meet friends and share achievements!"
            );

            Collections.shuffle(announcements);
            startEvent(e -> {
                int offset = 0;
                while(true) {
                    e.delay(1500); // 15 minutes
                    Broadcast.INFORMATION.sendNews(Icon.INFO, "[Info]", announcements.get(offset));
                    if(++offset >= announcements.size())
                        offset = 0;
                }
            });
        });
    }

    public static String getCentralAddress() {
        return centralAddress;
    }

    public static void setCentralAddress(String centralAddress) {
        World.centralAddress = centralAddress;
    }

    @SneakyThrows
    public static void parse(Properties properties) {
        World.id = Integer.parseInt(properties.getProperty("world_id"));
        World.name = properties.getProperty("world_name");
        World.stage = WorldStage.valueOf(properties.getProperty("world_stage"));
        World.type = WorldType.valueOf(properties.getProperty("world_type"));
        World.flag = WorldFlag.valueOf(properties.getProperty("world_flag"));
        World.halloween = Boolean.parseBoolean(properties.getProperty("halloween"));
        World.christmas = Boolean.parseBoolean(properties.getProperty("christmas"));
        String worldSettings = properties.getProperty("world_settings");
        for (String s : worldSettings.split(",")) {
            if (s == null || (s = s.trim()).isEmpty())
                continue;
            WorldSetting setting;
            try {
                setting = WorldSetting.valueOf(s);
            } catch (Exception e) {
                log.error("INVALID WORLD SETTING: " + s, e);
                continue;
            }
            World.settings |= setting.mask;
        }
        String address = properties.getProperty("world_address");
        String[] split = address.split(":");
        String host = split[0].trim();
        port = Integer.valueOf(split[1]);
        if (host.isEmpty() || host.equals("127.0.0.1") || host.equals("localhost"))
            host = IPAddress.get();
        World.address = host + ":" + port;

        World.setCentralAddress(properties.getProperty("central_address"));
    }
}