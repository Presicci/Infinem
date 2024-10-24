package io.ruin.model.activities.wilderness.bosses.callisto;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.npc.NPCDrops;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.*;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.RouteFinder;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Color;
import io.ruin.utility.Misc;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/22/2024
 */
public class Callisto extends NPCCombat {

    private enum Phase {
        ONE,
        TWO,
        THREE
    }

    private static final int MELEE_ANIMATION = 10012;

    private static final int RANGE_ANIMATION = 10013;

    private static final int MAGIC_ANIMATION = 10014;

    private static final int HOWL_ANIMATION = 10016;

    public static final int KNOCKBACK_ANIMATION = 1157;

    private static final Projectile RANGED_PROJECTILE = new Projectile(2350, 5, 25, 35, 55, 0, 15, 64);

    private static final Projectile MAGIC_PROJECTILE = new Projectile(133, 30, 25, 40, 160, 0, 15, 0);

    private static final int STOMP_GRAPHICS = 2349;

    private static final int TRAP_SPAWN_GRAPHICS = 2343;

    public static final int KNOCKBACK_PLAYER_GRAPHICS = 1255;

    private final Set<GameObject> traps = new ObjectOpenHashSet<>();

    private final Map<Player, Integer> damageMap = new HashMap<>();

    private Phase phase = Phase.ONE;

    private boolean artio;

    @Override
    public void init() {
        artio = npc.getId() == 11992;
        magicCooldown = 0;
        npc.hitListener = new HitListener().postDefend(this::postDefend);
        npc.deathEndListener = (entity, killer, killHit) -> {
            if (!artio) {
                drop(npc.getCentrePosition());
            }
        };
    }

    private void postDefend(Hit hit) {
        if (hit.attacker != null && hit.attacker.isPlayer() && hit.damage > 0 && !hit.isBlocked())
            damageMap.compute(hit.attacker.player, (k, v) -> Integer.sum(v == null ? 0 : v, hit.damage));
    }

    @Override
    public void follow() {
        follow(1);
    }

    private int magicCooldown;

    @Override
    public boolean attack() {
        if (getHitpointsAsPercentage() < 66 && phase != Phase.TWO && phase != Phase.THREE) {
            howl();
            phase = Phase.TWO;
        } else if (getHitpointsAsPercentage() < 33 && phase != Phase.THREE) {
            howl();
            phase = Phase.THREE;
        } else if (withinDistance(1)) {
            npc.animate(MELEE_ANIMATION);
            for (Entity target : npc.getPossibleTargets(3, true, false)) {
                target.hit(new Hit(npc, AttackStyle.CRUSH).randDamage(this.target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE) ? 30 : 55).ignorePrayer());
            }
        } else if (magicCooldown <= 0 && Random.rollPercent(20)) {
            magicAttack();
        } else {
            rangedAttack();
        }
        --magicCooldown;
        return true;
    }

    private void rangedAttack() {
        npc.animate(RANGE_ANIMATION);
        npc.graphics(STOMP_GRAPHICS);
        for (Entity target : npc.getPossibleTargets(32, true, false)) {
            int delay = RANGED_PROJECTILE.send(npc, target);
            target.hit(new Hit(npc, AttackStyle.RANGED).randDamage(artio ? 40 : 55).clientDelay(delay));
        }
    }

    private void magicAttack() {
        npc.animate(MAGIC_ANIMATION);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC).fixedDamage(0).clientDelay(delay).hide().onLand(hit1 -> {
            if (!target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
                knockback(target);
            }
        }));
        delayAttack(1);
        magicCooldown = 3;
    }


    @Override
    public void startDeath(Hit killHit) {
        super.startDeath(killHit);
        phase = Phase.ONE;
        for (GameObject object : traps) {
            removeTrap(object);
        }
        traps.clear();
    }

    private void knockback(Entity target) {
        final Position middle = npc.getCentrePosition();
        double degrees = Math.toDegrees(Math.atan2(target.getPosition().getY() - middle.getY(), target.getPosition().getX() - middle.getX()));
        if (degrees < 0) {
            degrees += 360;
        }
        final double angle = Math.toRadians(degrees);
        final int px = (int) Math.round(middle.getX() + (npc.getSize() + 4) * Math.cos(angle));
        final int py = (int) Math.round(middle.getY() + (npc.getSize() + 4) * Math.sin(angle));

        final List<Position> tiles = Misc.calculateLine(target.getPosition().getX(), target.getPosition().getY(), px, py, target.getPosition().getZ());
        if (!tiles.isEmpty()) tiles.remove(0);
        Position destination = target.getPosition();
        int tilesMoved = 0;
        for (final Position tile : tiles) {
            final int dir = Misc.getMoveDirection(tile.getX() - destination.getX(), tile.getY() - destination.getY());
            if (dir == -1) {
                continue;
            }
            if (tilesMoved >= 3) break;
            if (tile.getTile().hasObject(47146)) {
                destination = tile;
                ++tilesMoved;
                break;
            }
            if (!tile.getTile().allowStandardEntrance()) break;
            destination = tile;
            ++tilesMoved;
        }
        Direction direction = Direction.getDirection(target.getPosition(), npc.getCentrePosition());
        Position finalDestination = destination;
        int damage = tilesMoved == 3 ? 5 : tilesMoved == 2 ? 20 : tilesMoved == 1 ? 35 : 50;
        target.startEvent(e -> {
            target.lock();
            target.face(direction);
            target.animate(KNOCKBACK_ANIMATION);
            target.hit(new Hit().fixedDamage(damage));
            target.stun(1, true);
            if (!finalDestination.equals(target.getPosition())) {
                target.player.getMovement().force(finalDestination.getX() - target.getPosition().getX(), finalDestination.getY() - target.getPosition().getY(), 0, 0, 0, 60, direction);
                e.delay(2);
                target.getMovement().teleport(finalDestination);
            }
            target.unlock();
        });
    }

    private Set<Position> getTrapCoords(int numTraps) {
        Set<Position> coords = new ObjectOpenHashSet<>();
        while (coords.size() < numTraps) {

            final Bounds artioBounds = new Bounds(1749, 11532, 1771, 11555, 0);
            final Bounds callistoBounds = new Bounds(3349, 10316, 3371, 10339, 0);
            final Position position = artio ? artioBounds.randomPosition() : callistoBounds.randomPosition();
            final Tile tile = Tile.get(position);
            if (tile != null && (coords.contains(position) || traps.contains(position) || !tile.isFloorFree())) {
                continue;
            }
            coords.add(position);
        }

        return coords;
    }

    private void addTrapsAtCoords(Set<Position> coords) {
        coords.forEach(coord -> {
            GameObject trap = new GameObject(47146, coord, 10, 0);
            trap.spawn();
            World.sendGraphics(TRAP_SPAWN_GRAPHICS, 0, 0, coord);
            traps.add(trap);
            trap.animate(10_000);
            World.startEvent(e -> {
                e.delay(45);
                removeTrap(trap);
            }).setCancelCondition(this::isDead);
            World.startEvent(e -> {
                while (traps.contains(trap)) {
                    e.delay(1);
                    final List<Player> players = npc.localPlayers();
                    for (Player player : players) {
                        if (player.getPosition().equals(coord)) {
                            activateTrap(trap);
                            player.hit(new Hit().fixedDamage(13));
                            player.freeze(3, npc);
                            player.sendMessage(Color.RED.wrap("The bear trap immobilizes you."));
                            return;
                        }
                    }
                }
            });
        });
    }

    private void activateTrap(GameObject trap) {
        trap.animate(9999);
        World.startEvent(e -> {
            e.delay(1);
            removeTrap(trap);
        });
    }

    private void howl() {
        npc.animate(HOWL_ANIMATION, 18);
        npc.graphics(artio ? 2353 : 2352);
        npc.resetFreeze();
        npc.getCombat().delayAttack(4);
        World.startEvent(e -> {
            int ticks = 0;
            while (++ticks < 4) {
                addTraps();
                e.delay(4);
            }
        }).setCancelCondition(() -> isDead() || target == null);
    }

    private void addTraps() {
        Set<Position> trapCoords = getTrapCoords(4);
        addTrapsAtCoords(trapCoords);
    }

    private void removeTrap(GameObject trap) {
        if (traps.contains(trap)) {
            trap.remove();
            traps.remove(trap);
        }
    }

    @Override
    public void dropItems(Killer killer) {
        if (artio) new NPCDrops(this).dropItems(killer);
    }

    private void drop(final Position tile) {
        List<Player> players = new ArrayList<>(damageMap.keySet());
        // Filter out null players and players not in npc.localPlayers() before sorting
        players.removeIf(p -> p == null || !npc.localPlayers().contains(p));

        // Sort the players based on damage
        players.sort((a, b) -> damageMap.getOrDefault(b, 0) - damageMap.getOrDefault(a, 0));

        // Calculate the total damage done by all valid players and set a trigger damage
        int totalDamage = players.stream().mapToInt(p -> damageMap.getOrDefault(p, 0)).sum();
        int trigger = (int) (Math.random() * totalDamage) + 1;

        boolean provideUnique = Random.rollDie(47, 1); // Simplified unique drop chance check
        boolean uniqueGiven = false;

        // Iterate over up to 10 valid players
        for (int playerLoop = 0; playerLoop < players.size() && playerLoop < 10; playerLoop++) {
            final Player p = players.get(playerLoop);
            BossKillCounter.CALLISTO.getCounter().increment(p);

            if (playerLoop == 0) {
                new GroundItem(Items.BIG_BONES, 1).owner(p).position(tile).spawn();
            }
            int playerDamage = damageMap.getOrDefault(p, 0);
            if (!uniqueGiven && provideUnique) { //only bother checking the unique trigger if we need to
                if (trigger <= playerDamage) { // if the trigger value is inside of the damage dealt by the current player he gets unique
                    Item item = CallistoDropTable.rollUnique();
                    new GroundItem(item).owner(p).position(tile).spawn();
                    p.getCollectionLog().collect(item);
                    NPCDrops.getRareDropAnnounce(p, item, npc);
                    uniqueGiven = true;
                } else {
                    trigger -= playerDamage; //remove the current player's damage contribution from the trigger value
                }
            }
            // Up to 10 players may obtain drops per kill in this manner, and each of the top 10 players will receive at least 25% of the solo loot quantity.
            double quantityMultiplier = Math.max(0.25, (double) playerDamage / totalDamage);

            // Roll regular tables for the players
            Item item = CallistoDropTable.rollRegular();
            item.setAmount((int) Math.ceil((double) item.getAmount() * quantityMultiplier));
            new GroundItem(item).owner(p).position(tile).spawn();
            p.getCollectionLog().collect(item);

            // Pet chance
            if (Random.rollDie(500, 1)) {
                //Pet.CALLISTO_CUB.unlockBossDrop(p, p.callistoKills.getKills());
            }
        }

        //just in case the kill had more than 10 people and the 11th+ person was supposed to get unique, give it to most dmg
        if (!uniqueGiven && provideUnique) {
            final Player p = players.get(0);
            Item item = CallistoDropTable.rollUnique();
            new GroundItem(item).owner(p).position(tile).spawn();
            p.getCollectionLog().collect(item);
            NPCDrops.getRareDropAnnounce(p, item, npc);
        }

        damageMap.clear();
    }
}