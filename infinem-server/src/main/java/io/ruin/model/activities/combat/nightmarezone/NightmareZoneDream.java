package io.ruin.model.activities.combat.nightmarezone;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import java.util.List;

public final class NightmareZoneDream {

    // Dream vial:
    // Proceed.
    // Not just now.
    private static final Position START = new Position(2275, 4680, 0);
    private static final Position EXIT = new Position(2608, 3115, 0);

    /* As far as I know the NMZ monsters can spawn pretty much anywhere in the arena, although the arena is not a perfect square. */
    private static final Bounds SPAWN_BOUNDS = new Bounds(2256, 4680, 2287, 4711, 0);

    private Player player;

    private final NightmareZoneDreamDifficulty difficulty;

    private DynamicMap map;
    private final MapListener mapListener;

    private int npcsRemaining;

    private int rewardPointsGained;

    private final List<Integer> MONSTERS;

    public NightmareZoneDream(Player player, NightmareZoneDreamDifficulty difficulty) {
        this.player = player;
        this.difficulty = difficulty;
        map = new DynamicMap();
        map.build(9033, 0);
        mapListener = map.toListener().onExit(this::onExit);
        this.MONSTERS = NightmareZoneMonster.getAsList(difficulty == NightmareZoneDreamDifficulty.HARD);
        player.deathEndListener = (DeathListener.Simple) () -> player.getMovement().teleport(EXIT);
        player.teleportListener = p -> {
            p.sendMessage("Drink from the vial at the south of the arena to wake up.");
            return false;
        };
    }

    public void enter() {
        Config.NMZ_POINTS.set(player, 0);
        player.putTemporaryAttribute("nmz", this);
        prepareMap();

        World.startEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            event.delay(2);
            player.getMovement().teleport(map.convertPosition(START));
            player.addActiveMapListener(mapListener);
            event.delay(1);
            player.getPacketSender().fadeIn();
            prepareInterface();
            player.sendMessage("Welcome to The Nightmare Zone.");
            player.unlock();

            event.delay(30);
            spawnMonsters();
        });
    }

    private void prepareMap() {
        /* Remove KBD stalagmite, add dream potion */
        GameObject potion = new GameObject(26276, map.convertX(2276), map.convertY(4679), 0, 10, 0);
        Tile.getObject(12576, map.convertX(2276), map.convertY(4679), 0).setId(26267);
        Tile.get(map.convertX(2276), map.convertY(4679), 0).addObject(potion.spawn());

        /* Remove KBD lever */
        Tile.getObject(1817, map.convertX(2271), map.convertY(4680), 0).remove();
    }

    private void prepareInterface() {
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, 202);

        // This is a hash of the arena's southwestern-most tile. This is presumably used by the client to differentiate between KBD lair
        int hash = map.convertY(4680) + (map.convertX(2256) << 14);

        // [clientscript,nzone_game_overlay].cs2 -> tile hash does not seem to matter, empty string at end is some sort of unused in-game notification string
        player.getPacketSender().sendClientScript(255, "cs", hash, "");
    }

    private void spawnMonsters() {
        if (map == null) return;
        for (int i = 0; i < 4; i++) {
            NPC npc = new NPC(randomMonster());
            Position spawn = map.convertPosition(SPAWN_BOUNDS.randomPosition());
            npc.spawn(spawn);
            map.addNpc(npc);
            npc.face(player);
            npc.deathEndListener = (DeathListener.Simple) () -> {
                rewardPointsGained += Random.get(3000, 5000) * (difficulty == NightmareZoneDreamDifficulty.NORMAL ? 1.0 : 1.85);
                Config.NMZ_POINTS.set(player, rewardPointsGained);
                npcsRemaining--;
                map.removeNpc(npc);
                if (npcsRemaining == 0) {
                    spawnMonsters();
                }
            };
            npc.getCombat().setAllowRespawn(false);
            npc.targetPlayer(player, false);
            npc.attackTargetPlayer();
            npcsRemaining++;
        }
    }

    private int randomMonster() {
        return Random.get(MONSTERS);
    }

    private void onExit(Player player, boolean logout) {
        if (logout) {
            player.getMovement().teleport(EXIT);
        } else {
            player.getPacketSender().fadeIn();
            player.sendMessage("You wake up feeling refreshed.");
            player.sendMessage(Color.DARK_GREEN.wrap("You have earned " + NumberUtils.formatNumber(rewardPointsGained) + " reward points. New total: " + NumberUtils.formatNumber(Config.NMZ_REWARD_POINTS_TOTAL.get(player) + rewardPointsGained)));
        }
        Config.NMZ_REWARD_POINTS_TOTAL.increment(player, rewardPointsGained);
        player.putTemporaryAttribute("nmz", null);
        player.teleportListener = null;
        player.deathEndListener = null;
        potCleanup(player);
        destroy();
    }

    private void leave() {
        player.getMovement().teleport(EXIT);
    }

    private void potCleanup(Player player) {
        player.getStats().get(StatType.Attack).restore();
        player.getStats().get(StatType.Strength).restore();
        player.getStats().get(StatType.Defence).restore();
        player.getStats().get(StatType.Ranged).restore();
        player.getStats().get(StatType.Magic).restore();
        player.overloadBoostActive = false;
    }

    private void destroy() {
        player = null;
        map.destroy();
        map = null;
    }

    static {
        ObjectAction.register(26276, 1, (player, obj) -> {
            player.getMovement().teleport(EXIT);
            NightmareZoneDream dream = player.getTemporaryAttribute("nmz");
            dream.leave();
        });
    }
}