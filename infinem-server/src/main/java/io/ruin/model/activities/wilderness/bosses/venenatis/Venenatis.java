package io.ruin.model.activities.wilderness.bosses.venenatis;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemID;
import io.ruin.cache.NpcID;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.npc.NPCDrops;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.map.*;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/24/2024
 */
public class Venenatis extends NPCCombat {

    private final int MELEE_ATTACK_ANIM = 9991;
    private final int RANGED_ATTACK_ANIM = 9989;
    private final Projectile RANGED_ATTACK_PROJ = new Projectile(2356, 43, 25, 30, 15, 10, 0, 64);
    private final int RANGED_HIT_GFX = 2357;
    private final int MAGIC_ATTACK_ANIM = 9990;
    private final Projectile MAGIC_ATTACK_PROJ = new Projectile(2358, 43, 25, 30, 15, 10, 0, 64);
    private final int MAGIC_HIT_GFX = 2359;
    private final int WEB_HURTING_GFX = 2361;
    private final Projectile WEB_ATTACK_PROJ = new Projectile(2360, 43, 25, 30, 15, 25, 5, 64);
    private final Position SPINDEL_MIDDLE = new Position(1630, 11547, 2);

    private final Position MIDDLE = new Position(3423, 10204, 2);

    private final Bounds VENENATIS_MOVE_AREA = new Bounds(3415, 10195, 3431, 10213, 2);
    private final Bounds SPINDEL_MOVE_AREA = new Bounds(1623, 11539, 1639, 11557, 2);

    private final RSPolygon SPINDEL_WEB_AREA = new RSPolygon(new int[][]{
            {1623, 11539},
            {1630, 11539},
            {1630, 11526},
            {1633, 11526},
            {1633, 11539},
            {1640, 11539},
            {1640, 11558},
            {1623, 11558}
    });

    private final RSPolygon WEB_AREA = new RSPolygon(new int[][]{
            {3415, 10195},
            {3422, 10195},
            {3422, 10182},
            {3425, 10182},
            {3425, 10195},
            {3432, 10195},
            {3432, 10214},
            {3415, 10214}
    });

    private boolean spindel;
    private int attacks;
    private boolean started;
    private int startTicks;
    public int spiderlingsAlive;
    private final Map<Player, Integer> damageMap = new HashMap<>();
    List<Spiderling> spiderlings = new ArrayList<>();
    List<GameObject> webs = new ArrayList<>();

    @Override
    public void init() {
        spindel = npc.getId() == NpcID.SPINDEL;
        npc.hitListener = new HitListener().postDefend(this::postDefend);
        npc.deathEndListener = (entity, killer, killHit) -> {
            if (!spindel) {
                drop(npc.getSpawnPosition());
            }
        };
        npc.setIgnoreMulti(true);
    }

    private void postDefend(Hit hit) {
        if (hit.attacker != null && hit.attacker.isPlayer() && hit.damage > 0 && !hit.isBlocked())
            damageMap.compute(hit.attacker.player, (k, v) -> Integer.sum(v == null ? 0 : v, hit.damage));
    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        if (!started) {
            startTicks++;
            if (startTicks == 3) {
                started = true;
            }
            return false;
        }
        if (attacks < 8) {
            rangedAttack();
        } else {
            magicAttack();
        }
        if (attacks == 0) {
            for (int i = 0; i < 2; i++) {
                Position position = spindel ? SPINDEL_MIDDLE.relative(Random.get(-5, 5), Random.get(-5, 5)) : MIDDLE.relative(Random.get(-5, 5), Random.get(-5, 5));
                Spiderling spiderling = new Spiderling(spindel ? NpcID.SPINDELS_SPIDERLING : NpcID.VENENATIS_SPIDERLING_12000, this);
                spiderling.spawn(position, Direction.NORTH, 10);
                spiderling.getCombat().setTarget(target);
                spiderling.setIgnoreMulti(true);
                spiderlings.add(spiderling);
            }
        }
        attacks++;
        if (attacks >= 16) {
            attacks = 0;
        }
        if (attacks % 4 == 0) {
            npc.lock();
            npc.faceNone(false);
            npc.addEvent(e -> {
                Position pos = spindel ? SPINDEL_MOVE_AREA.randomPosition() : VENENATIS_MOVE_AREA.randomPosition();
                npc.stepAbs(pos.getX(), pos.getY(), StepType.FORCE_WALK);
                e.waitForMovement(npc);
                npc.unlock();
                npc.face(target);
            });
        }
        return true;
    }

    private void meleeAttack(Entity target) {
        int maxHit = spindel ? 14 + (spiderlingsAlive * 2) : 21 + (spiderlingsAlive * 2);
        double prayerReduction = spiderlingsAlive * 0.2;
        boolean protection = target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE);
        npc.animate(MELEE_ATTACK_ANIM);
        target.hit(new Hit(npc, AttackStyle.STAB)
                .randDamage(protection
                        ? (int) (maxHit - (maxHit * (1D - prayerReduction)))    // Reduce prayer effectiveness by 20% per spiderling alive
                        : maxHit)
                .ignorePrayer()); // Ignoring prayer here as we handle it elsewhere
        System.out.println(protection
                ? (int) (maxHit - (maxHit * (1D - prayerReduction)))    // Reduce prayer effectiveness by 20% per spiderling alive
                : maxHit);
    }

    private void rangedAttack() {
        int maxHit = spindel ? 31 + (spiderlingsAlive * 2) : 35 + (spiderlingsAlive * 2);
        double prayerReduction = spiderlingsAlive * 0.2;
        for (Entity areaTarget : npc.getPossibleTargets(32, true, false)) {
            if (DumbRoute.withinDistance(npc, areaTarget, 1)) {
                meleeAttack(areaTarget);
                continue;
            }
            if (areaTarget == target) {
                npc.animate(RANGED_ATTACK_ANIM);
            }
            int delay = RANGED_ATTACK_PROJ.send(npc, areaTarget);
            boolean protection = areaTarget.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES);
            areaTarget.hit(new Hit(npc, AttackStyle.RANGED)
                    .randDamage(protection
                            ? (int) (maxHit - (maxHit * (1D - prayerReduction)))    // Reduce prayer effectiveness by 20% per spiderling alive
                            : maxHit)
                    .ignorePrayer() // Ignoring prayer here as we handle it elsewhere
                    .clientDelay(delay).onLand(hit -> areaTarget.graphics(RANGED_HIT_GFX)));
        }
    }

    private void magicAttack() {
        if (attacks == 10) {
            shootWebs(target.getPosition().relative(-4, -4, 0));
        }
        int maxHit = spindel ? 24 + (spiderlingsAlive * 2) : 30 + (spiderlingsAlive * 2);
        double prayerReduction = spiderlingsAlive * 0.2;
        for (Entity areaTarget : npc.getPossibleTargets(32, true, false)) {
            if (DumbRoute.withinDistance(npc, areaTarget, 1)) {
                meleeAttack(areaTarget);
                continue;
            }
            if (areaTarget == target) {
                npc.animate(MAGIC_ATTACK_ANIM);
            }
            int delay = MAGIC_ATTACK_PROJ.send(npc, areaTarget);
            boolean protection = areaTarget.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC);
            areaTarget.hit(new Hit(npc, AttackStyle.MAGIC)
                    .randDamage(protection
                            ? (int) (maxHit - (maxHit * (1D - prayerReduction)))    // Reduce prayer effectiveness by 20% per spiderling alive
                            : maxHit)
                    .ignorePrayer() // Ignoring prayer here as we handle it elsewhere
                    .clientDelay(delay).onLand(hit -> areaTarget.graphics(MAGIC_HIT_GFX, 0, 120)));
        }
    }

    @Override
    public void startDeath(Hit killHit) {
        spiderlings.stream()
                .filter(Objects::nonNull)
                .forEach(NPC::remove);
        webs.stream()
                .filter(Objects::nonNull)
                .forEach(GameObject::remove);
        attacks = 0;
        started = false;
        startTicks = 0;
        spiderlingsAlive = 0;
        spiderlings.clear();
        webs.clear();
        super.startDeath(killHit);
    }

    private void shootWebs(Position landing) {
        int delay = WEB_ATTACK_PROJ.send(npc, landing.relative(4, 4));
        World.startEvent(e -> {
            e.delay(Misc.clientDelayToTicks(delay));
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    GameObject web;
                    Position webPosition = new Position(landing.getX() + row, landing.getY() + column, 2);
                    if (Tile.get(webPosition) != null && !Tile.get(webPosition).isFloorFree()) {
                        continue;
                    }
                    if (isEmptySpot(row, column)) {
                        continue;
                    }
                    if (!spindel ? SPINDEL_WEB_AREA.contains(landing.getX() + row, landing.getY() + column) : WEB_AREA.contains(landing.getX() + row, landing.getY() + column)) {
                        continue;
                    }
                    if (row == 0 || row == 8 || column == 0 || column == 8) {
                        web = new GameObject(47085, webPosition, 10, row == 0 ? 2 : row == 8 ? 4 : column == 0 ? 1 : 3);
                    } else if ((row == 1 || row == 7) && (column == 1 || column == 7)) {
                        web = new GameObject(47086, webPosition, 10, row == 1 && column == 1 ? 1 : row == 1 && column == 7 ? 2 : row == 7 && column == 1 ? 4 : 3);
                    } else {
                        web = new GameObject(47084, webPosition, 10, 1);
                    }
                    web.spawn();
                    webs.add(web);
                    startTask(web, webPosition);
                }
            }
        });
    }

    private void startTask(GameObject web, Position webPosition) {
        World.startEvent(e -> {
            e.delay(20);
            removeWeb(web);
        });
        World.startEvent(e -> {
            while (webs.contains(web)) {
                e.delay(1);
                final List<Player> players = npc.localPlayers();
                for (Player p : players) {
                    if (p.getPosition().equals(webPosition)) {
                        p.hit(new Hit(npc).fixedDamage(3));
                        p.getMovement().drainEnergy(10);
                        p.getPrayer().drain(3);
                        p.graphics(WEB_HURTING_GFX);
                    }
                }
            }
        });
    }

    private boolean isEmptySpot(int row, int column) {
        if (row == 0 || row == 8) {
            return column < 2 || column > 6;
        } else if (row == 1 || row == 7) {
            return column < 1 || column > 7;
        }
        return false;
    }

    private void removeWeb(GameObject web) {
        if (webs.contains(web)) {
            web.remove();
            webs.remove(web);
        }
    }

    @Override
    public void dropItems(Killer killer) {
        if (spindel) new NPCDrops(this).dropItems(killer);
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
            int killCount = BossKillCounter.VENENATIS.getCounter().increment(p);

            if (playerLoop == 0) {
                new GroundItem(ItemID.BIG_BONES, 1).owner(p).position(tile).spawn();
            }
            int playerDamage = damageMap.getOrDefault(p, 0);
            if (!uniqueGiven && provideUnique) { //only bother checking the unique trigger if we need to
                if (trigger <= playerDamage) { // if the trigger value is inside of the damage dealt by the current player he gets unique
                    Item item = VenenatisDropTable.rollUnique();
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
            for (int index = 0; index < 2; index++) {
                Item item = VenenatisDropTable.rollRegular();
                item.setAmount((int) Math.ceil((double) item.getAmount() * quantityMultiplier));
                new GroundItem(item).owner(p).position(tile).spawn();
                p.getCollectionLog().collect(item);
            }

            // Pet chance
            int dropAverage = (int) (1500 * (2D - quantityMultiplier));
            int threshold = 500;
            int numerator = (killCount / threshold) + 1;
            if (Random.rollDie(dropAverage, numerator)) {
                Pet.VENENATIS_SPIDERLING.unlock(p);
            }
        }

        //just in case the kill had more than 10 people and the 11th+ person was supposed to get unique, give it to most dmg
        if (!uniqueGiven && provideUnique) {
            final Player p = players.get(0);
            Item item = VenenatisDropTable.rollUnique();
            new GroundItem(item).owner(p).position(tile).spawn();
            p.getCollectionLog().collect(item);
            NPCDrops.getRareDropAnnounce(p, item, npc);
        }

        damageMap.clear();
    }
}