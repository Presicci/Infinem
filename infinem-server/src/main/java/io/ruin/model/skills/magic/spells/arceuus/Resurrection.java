package io.ruin.model.skills.magic.spells.arceuus;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.owned.impl.DwarfCannon;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.map.route.types.RouteAbsolute;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/30/2024
 */
public class Resurrection extends Spell {


    private static final int DESPAWN_SOUND = 5054;
    private static final int RESURRECT_GHOSTLY_THRALL_SOUND = 5061;
    private static final int RESURRECT_SKELETAL_THRALL_SOUND = 5025;
    private static final int RESURRECT_ZOMBIE_THRALL_SOUND = 5040;
    private static final int RESURRECT_GHOSTLY_THRALL_NPC_GFX = 1903;
    private static final int RESURRECT_SKELETAL_THRALL_NPC_GFX = 1904;
    private static final int RESURRECT_ZOMBIE_THRALL_NPC_GFX = 1905;
    private static final int RESURRECT_GHOSTLY_THRALL_PLAYER_GFX = 1873;
    private static final int RESURRECT_SKELETAL_THRALL_PLAYER_GFX = 1874;
    private static final int RESURRECT_ZOMBIE_THRALL_PLAYER_GFX = 1875;
    private static final int RESURRECT_GHOSTLY_THRALL_NPC_ANIMATION = 9047;
    private static final int RESURRECT_SKELETAL_THRALL_NPC_ANIMATION = 9048;
    private static final int RESURRECT_ZOMBIE_THRALL_NPC_ANIMATION = 9046;
    private static final int RESURRECT_THRALL_PLAYER_ANIMATION = 8973;
    private static final int GHOSTLY_THRALL_PROJECTILE = 1907;
    private static final int SKELETAL_THRALL_PROJECTILE = 1906;


    public Resurrection(Thralls thralls) {
        registerClick(thralls.getLevelReq(), thralls.getMageExp(), true, thralls.runes, (player, i) -> {
            if (!player.getInventory().hasId(25818) && !player.getEquipment().hasId(25818) // Book of the dead
                    && !player.getInventory().hasId(26551) && !player.getEquipment().hasId(26551)   // Arcane grimoire
            ) {
                player.sendMessage("You need a book of the dead to resurrect a thrall.");
                return false;
            }
            if (Stream.of(DwarfCannon.AREA_RESTRICTIONS).anyMatch(bounds -> player.getBounds().intersects(bounds))) {
                player.sendMessage("You can't spawn a thrall here.");
                return false;
            }
            if (player.thrallSpawnDelay.isDelayed()) {
                player.sendMessage("You must wait 10 seconds after resurrecting a thrall.");
                return false;
            }
            if (player.getStats().get(StatType.Prayer).currentLevel < thralls.prayerPointCost) {
                player.sendMessage("You do not have enough prayer points to resurrect this thrall.");
                return false;
            }
            if (player.thrall != null) {
                Config.RESURRECT_THRALL_COOLDOWN.set(player, 1);
                returnToGrave(player, player.thrall);
                spawn(player, thralls);
                return false;
            }
            Config.RESURRECT_THRALL_COOLDOWN.set(player, 1);
            spawn(player, thralls);
            return true;
        });
    }


    public void spawn(Player player, Thralls thralls) {
        Thrall thrall = new Thrall(thralls.getNpcId(), player);
        player.getStats().get(StatType.Prayer).drain(thralls.getPrayerPointCost());
        resurrect(player, thrall);
        thrall.addEvent(event -> {
            while (player.isOnline()) {
                if (player.getCombat().isDead() || player.getMovement().isTeleportQueued()) {
                    event.delay(1);
                    continue;
                }
                if (player.getThrallDespawnDelay().finished()) {
                    returnToGrave(player, thrall);
                    event.delay(1);
                    continue;
                }
                if (player.getThrallSpawnDelay().finished()) {
                    Config.RESURRECT_THRALL_COOLDOWN.set(player, 0);
                }
                event.delay(1);
            }
            returnToGrave(player, thrall);
        });
    }

    private void resurrect(Player player, Thrall thrall) {
        int thrallLifeTime = (int) (0.6 * player.getStats().get(StatType.Magic).currentLevel);
        Position randomPosition = getRandomFreePosition(player.getPosition());
        thrall.spawn(randomPosition.getX(), randomPosition.getY(), player.getPosition().getZ(), 3);
        player.thrall = thrall;
        thrall.ownerId = player.getUserId();
        if (!Thralls.forNpcId(thrall.getId()).isPresent())
            return;
        Thralls.forNpcId(thrall.getId()).get().getSpawn().start(player, thrall);
        thrall.setIgnoreMulti(true);
        thrall.getDef().occupyTiles = false;
        thrall.getDef().ignoreOccupiedTiles = true;
        player.getThrallSpawnDelay().delaySeconds(10);
        player.getThrallDespawnDelay().delaySeconds(thrallLifeTime);
        player.sendMessage("<col=a60380>You resurrect a " + String.valueOf(Thralls.forNpcId(thrall.getId()).get()).toLowerCase().replace("_", " ") + ".");
    }

    private void returnToGrave(Player player, Thrall thrall) {
        thrall.remove();
        player.thrall = null;
        player.publicSound(DESPAWN_SOUND);
        player.sendMessage("<col=a60380>Your " + String.valueOf(Thralls.forNpcId(thrall.getId()).get()).toLowerCase().replace("_", " ") + " has returned to the grave.");
    }

    private static Position getRandomFreePosition(Position pos) {
        int radius = 1;
        for (int i = 0; i < 10; i++) {
            int x = pos.getX() + (int) (Math.random() * (2 * radius + 1)) - radius; // generate a random x coordinate within the radius
            int y = pos.getY() + (int) (Math.random() * (2 * radius + 1)) - radius; // generate a random y coordinate within the radius
            Position animatedSpawn = new Position(x, y, 0);
            Tile tile = Tile.get(animatedSpawn, false);
            if (tile == null || tile.clipping == 0)
                return animatedSpawn;
        }
        return pos;
    }

    @Getter
    public enum Thralls {
        LESSER_GHOST("Lesser Ghost", 10878, new ThrallSpawn(RESURRECT_THRALL_PLAYER_ANIMATION, RESURRECT_GHOSTLY_THRALL_PLAYER_GFX, RESURRECT_GHOSTLY_THRALL_NPC_GFX, RESURRECT_GHOSTLY_THRALL_NPC_ANIMATION, RESURRECT_GHOSTLY_THRALL_SOUND), 2, 38, 55.0, Rune.AIR.toItem(2), Rune.COSMIC.toItem(1), Rune.MIND.toItem(5)),
        LESSER_SKELETON("Lesser Skeleton", 10881, new ThrallSpawn(RESURRECT_THRALL_PLAYER_ANIMATION, RESURRECT_SKELETAL_THRALL_PLAYER_GFX, RESURRECT_SKELETAL_THRALL_NPC_GFX, RESURRECT_SKELETAL_THRALL_NPC_ANIMATION, RESURRECT_SKELETAL_THRALL_SOUND), 2, 38, 55.0, Rune.AIR.toItem(2), Rune.COSMIC.toItem(1), Rune.MIND.toItem(5)),
        LESSER_ZOMBIE("Lesser Zombie", 10884, new ThrallSpawn(RESURRECT_THRALL_PLAYER_ANIMATION, RESURRECT_ZOMBIE_THRALL_PLAYER_GFX, RESURRECT_ZOMBIE_THRALL_NPC_GFX, RESURRECT_ZOMBIE_THRALL_NPC_ANIMATION, RESURRECT_ZOMBIE_THRALL_SOUND), 2, 38, 55.0, Rune.AIR.toItem(2), Rune.COSMIC.toItem(1), Rune.MIND.toItem(5)),

        SUPERIOR_GHOST("Superior Ghost", 10879, new ThrallSpawn(RESURRECT_THRALL_PLAYER_ANIMATION, RESURRECT_GHOSTLY_THRALL_PLAYER_GFX, RESURRECT_GHOSTLY_THRALL_NPC_GFX, RESURRECT_GHOSTLY_THRALL_NPC_ANIMATION, RESURRECT_GHOSTLY_THRALL_SOUND), 4, 57, 70.0, Rune.EARTH.toItem(2), Rune.COSMIC.toItem(1), Rune.DEATH.toItem(5)),
        SUPERIOR_SKELETON("Superior Skeleton", 10882, new ThrallSpawn(RESURRECT_THRALL_PLAYER_ANIMATION, RESURRECT_SKELETAL_THRALL_PLAYER_GFX, RESURRECT_SKELETAL_THRALL_NPC_GFX, RESURRECT_SKELETAL_THRALL_NPC_ANIMATION, RESURRECT_SKELETAL_THRALL_SOUND), 4, 57, 70.0, Rune.EARTH.toItem(2), Rune.COSMIC.toItem(1), Rune.DEATH.toItem(5)),
        SUPERIOR_ZOMBIE("Superior Zombie", 10885, new ThrallSpawn(RESURRECT_THRALL_PLAYER_ANIMATION, RESURRECT_ZOMBIE_THRALL_PLAYER_GFX, RESURRECT_ZOMBIE_THRALL_NPC_GFX, RESURRECT_ZOMBIE_THRALL_NPC_ANIMATION, RESURRECT_ZOMBIE_THRALL_SOUND), 4, 57, 70.0, Rune.EARTH.toItem(2), Rune.COSMIC.toItem(1), Rune.DEATH.toItem(5)),

        GREATER_GHOST("Greater Ghost", 10880, new ThrallSpawn(RESURRECT_THRALL_PLAYER_ANIMATION, RESURRECT_GHOSTLY_THRALL_PLAYER_GFX, RESURRECT_GHOSTLY_THRALL_NPC_GFX, RESURRECT_GHOSTLY_THRALL_NPC_ANIMATION, RESURRECT_GHOSTLY_THRALL_SOUND), 6, 76, 88.0, Rune.FIRE.toItem(2), Rune.COSMIC.toItem(1), Rune.BLOOD.toItem(5)),
        GREATER_SKELETON("Greater Skeleton", 10883, new ThrallSpawn(RESURRECT_THRALL_PLAYER_ANIMATION, RESURRECT_SKELETAL_THRALL_PLAYER_GFX, RESURRECT_SKELETAL_THRALL_NPC_GFX, RESURRECT_SKELETAL_THRALL_NPC_ANIMATION, RESURRECT_SKELETAL_THRALL_SOUND), 6, 76, 88.0, Rune.FIRE.toItem(2), Rune.COSMIC.toItem(1), Rune.BLOOD.toItem(5)),
        GREATER_ZOMBIE("Greater Zombie", 10886, new ThrallSpawn(RESURRECT_THRALL_PLAYER_ANIMATION, RESURRECT_ZOMBIE_THRALL_PLAYER_GFX, RESURRECT_ZOMBIE_THRALL_NPC_GFX, RESURRECT_ZOMBIE_THRALL_NPC_ANIMATION, RESURRECT_ZOMBIE_THRALL_SOUND), 6, 76, 88.0, Rune.FIRE.toItem(2), Rune.COSMIC.toItem(1), Rune.BLOOD.toItem(5));

        private final ThrallSpawn spawn;
        public final int prayerPointCost, levelReq, npcId;
        public final String thrallName;
        public final double mageExp;
        public final Item[] runes;

        Thralls(String thrallName, int npcId, ThrallSpawn spawn, int prayerPointCost, int levelReq, double mageExp, Item... runes) {
            this.thrallName = thrallName;
            this.spawn = spawn;
            this.prayerPointCost = prayerPointCost;
            this.levelReq = levelReq;
            this.mageExp = mageExp;
            this.npcId = npcId;
            this.runes = runes;
        }

        private static Optional<Thralls> forNpcId(int id) {
            return Arrays.stream(values()).filter(t -> t.npcId == id).findFirst();
        }

        static {
            for (Thralls def : values()) {
                NPCDefinition.get(def.getNpcId()).occupyTiles = false;
            }
        }
    }

    public static class Thrall extends NPC {
        private final Player owner;

        public Thrall(int id, Player owner) {
            super(id);
            this.owner = owner;
        }

        protected void follow(int distance) {
            //if (owner.getCombat().getTarget() == null) return;
            //Position pos = owner.getCombat().getTarget().getPosition();
            //getRouteFinder().routeAbsolute(pos.getX(), pos.getY());
            DumbRoute.step(npc, owner.getCombat().getTarget(), distance);
            /*if (getCombat().getTarget() != null) TargetRoute.set(npc, owner.getCombat().getTarget(), 1);
            TargetRoute.beforeMovement(npc);
            npc.getMovement().process();
            TargetRoute.afterMovement(npc);*/
        }

        @Override
        public void process() {
            super.process();
            if (getCombat() != null && (getCombat().getTarget() != null && getCombat().getTarget().getPosition().distance(getPosition()) > 28)) {
                getCombat().reset();
            }
            if (getCombat() != null && (getCombat().getTarget() == null || (owner.getCombat().getTarget() != null && getCombat().getTarget() != owner.getCombat().getTarget()))) {
                getCombat().setTarget(owner.getCombat().getTarget());
                //getCombat().attack();
            }
            if (getCombat() != null && getCombat().getTarget() != null) {
                face(getCombat().getTarget());
            }
        }
    }


    @AllArgsConstructor
    static class ThrallSpawn {

        int playerAnimation;
        int playerGraphic;
        int npcSpawnGraphic;
        int npcSpawnAnimation;

        int spawnSound;

        void start(Player player, Thrall thrall) {
            thrall.startEvent(e -> {
                thrall.lock();
                thrall.animate(npcSpawnAnimation);
                thrall.graphics(npcSpawnGraphic);
                player.animate(playerAnimation);
                player.graphics(playerGraphic);
                player.publicSound(spawnSound);
                e.delay(4);
                thrall.unlock();
            });
        }
    }

}