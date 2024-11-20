package io.ruin.model.activities.wilderness.bosses.vetion;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemID;
import io.ruin.cache.NpcID;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.npc.NPCDrops;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/23/2024
 */
public class Vetion extends NPCCombat {

    // 9969 - spawn or some shit, same as 9970
    // 9971, 9972
    // 9973, 9974 - shields
    // 9975 - idfk
    // 9980 - death

    // blue lighting 2346
    // orange lighting 2347
    // sword thing 2348

    private static final int LIGHTNING_ANIMATION_SMITE = 9969;

    private static final int LIGHTNING_ANIMATION_STRIKE = 9971;

    private static final int SHIELD_BASH_ANIMATION = 9973;

    private static final int HELLHOUND_SUMMON_ANIMATION = 9975;

    private static final int REGULAR_LIGHTNING = 2346;

    private static final int ENRAGED_LIGHTNING = 2347;
    private static final int SHIELD_BASH_SHADOW = 1446;
    private static final int SHIELD_BASH_SMOKE = 2184;


    private static final String TRANSFORM_FORCE_TALK = "Now... DO IT AGAIN!!!";

    private boolean calvarion;

    private boolean spawnedHellhounds = false;

    private final Object2ObjectArrayMap<Player, Integer> damageMap = new Object2ObjectArrayMap<>();

    private Set<NPC> hellhounds = new HashSet<>();

    private List<Position> generateBashAttackTiles = new ArrayList<>();

    private enum Phase {
        REGULAR,
        ENRAGED
    }

    private final String[] FORCE_CHATS = {
            "I will smite you!",
            "I've got you now!",
            "Stand still, rat!",
            "You can't escape!",
            "For the lord!",
            "You call that a weapon?!",
            "Dodge this!",
            "Perish, fool!",
            "You are powerless to me!",
            "Time to die, mortal!",
            "Filthy whelps!"
    };

    private final String[] SHIELD_BASH = {
            "Now I've got you!",
            "Hands off, wretch!",
            "Grrrah!",
            "Defend yourself!",
            "You're not blocking this one!"
    };

    private final String[] HOUND_SPAWN_CHATS = {
            "Time to feast, hounds!",
            "Hounds! Dispose of these trespassers"
    };

    private final String[] HOUND_DEATH_CHATS = {
            "I'll kill you for killing my pets!",
            "My hounds! I'll make you pay for that!",
            "Must I do everything around here?!",
            "Fine! I'll deal with you myself!"
    };

    private final String[] DEATH_CHATS = {
            "This isn't... the last... of me...",
            "Urk! I... failed...",
            "I'll get you... next... time...",
            "My lord... I'm... sorry...",
            "Urg... not... again...",
            "I'll... be... back..."
    };

    private Phase phase = Phase.REGULAR;

    @Override
    public void init() {
        calvarion = npc.getId() == NpcID.CALVARION;
        npc.hitListener = new HitListener().preDefend(this::block).postDefend(this::postDefend);
        npc.deathEndListener = (entity, killer, killHit) -> {
            entity.npc.transform(calvarion ? NpcID.CALVARION : NpcID.VETION);
            if (!calvarion) {
                drop(npc.getCentrePosition());
            }
        };
        npc.setIgnoreMulti(true);
        NPCDefinition.get(NpcID.SKELETON_HELLHOUND).occupyTiles = false;
        NPCDefinition.get(NpcID.GREATER_SKELETON_HELLHOUND).occupyTiles = false;
    }

    private void postDefend(Hit hit) {
        if (npc.getCombat().getHitpointsAsPercentage() <= 50 && !spawnedHellhounds && !isDead()) {
            spawnHellhounds();
            spawnedHellhounds = true;
        }
        if (hit.attacker != null && hit.attacker.isPlayer() && hit.damage > 0 && !hit.isBlocked())
            damageMap.compute(hit.attacker.player, (k, v) -> Integer.sum(v == null ? 0 : v, hit.damage));
    }

    @Override
    public void startDeath(Hit killHit) {
        if (npc.getCombat().getStat(StatType.Hitpoints).currentLevel == 0 && phase == Phase.REGULAR) {
            // on first death it will transform
            npc.transform(calvarion ? NpcID.CALVARION_11994 : 6612);
            restore();
            lightningRing();
            npc.forceText(TRANSFORM_FORCE_TALK);
            phase = Phase.ENRAGED;
        } else {
            // "official" death
            forceTalk(Random.get(DEATH_CHATS));
            super.startDeath(killHit);
            phase = Phase.REGULAR;
        }
        hellhounds.stream()
                .filter(Objects::nonNull)
                .forEach(NPC::remove);
        spawnedHellhounds = false;
        hellhounds.clear();
        generateBashAttackTiles.clear();
    }

    private void block(Hit hit) {
        for (NPC dog : hellhounds) {
            if (dog != null && !dog.isRemoved() && !dog.getCombat().isDead()) {
                hit.block();
                break;
            }
        }
    }

    @Override
    public void follow() {
        follow(2);
    }

    @Override
    public boolean attack() {
        if (withinDistance(2) && Random.rollPercent(10)) {
            shieldBashAttack();
            return true;
        }
        // Default lightning attack
        forceTalk(Random.get(FORCE_CHATS));
        lightningAttack();
        return true;
    }

    private void shieldBashAttack() {
        npc.lock();
        generateBashAttackTiles.clear();
        faceTarget();
        forceTalk(Random.get(SHIELD_BASH));
        shieldBashLogic();

        World.startEvent(e -> {
            generateBashAttackTiles.forEach(position -> World.sendGraphics(SHIELD_BASH_SHADOW, 0, 0, position));
            e.delay(3);

            npc.animate(SHIELD_BASH_ANIMATION);

            generateBashAttackTiles.forEach(position -> {
                World.sendGraphics(SHIELD_BASH_SMOKE, 0, 0, position);
                for (Player player : npc.localPlayers()) {
                    if (player.getPosition().equals(position)) {
                        player.hit(new Hit().randDamage(20, 35));
                        player.getCombat().delayAttack(8);
                    }
                }

            });

            npc.unlock();
        });
    }


    private void shieldBashLogic() {
        Position position = new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getZ());
        Direction direction = Direction.getDirection(npc.getCentrePosition(), target.getPosition());
        int startX = getOffsetX(direction, position.getX());
        int startY = getOffsetY(direction, position.getY());
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            for (int i = 0; i < 5; i++) {
                generateBashAttackTiles.add(new Position(startX - 1 + i, direction == Direction.NORTH ? startY : startY + 2, 1));
            }
            for (int i = 0; i < 7; i++) {
                generateBashAttackTiles.add(new Position(startX - 2 + i, startY + 1, 1));
            }
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            for (int i = 0; i < 5; i++) {
                generateBashAttackTiles.add(new Position(direction == Direction.EAST ? startX : startX + 2, startY - 1 + i, 1));
            }
            for (int i = 0; i < 7; i++) {
                generateBashAttackTiles.add(new Position(startX + 1, startY - 2 + i, 1));
            }
        } else if (direction == Direction.NORTH_EAST || direction == Direction.NORTH_WEST) {
            for (int i = 0; i < 3; i++) {
                generateBashAttackTiles.add(new Position(direction == Direction.NORTH_WEST ? startX - 1 + i : startX + 1 + i, startY, 1));
            }
            for (int i = 0; i < 2; i++) {
                generateBashAttackTiles.add(new Position(direction == Direction.NORTH_WEST ? startX - 1 : startX + 3, startY - 1 - i, 1));
            }
            for (int i = 0; i < 4; i++) {
                generateBashAttackTiles.add(new Position(direction == Direction.NORTH_WEST ? startX - 2 + i : startX + 1 + i, startY + 1, 1));
            }
            for (int i = 0; i < 3; i++) {
                generateBashAttackTiles.add(new Position(direction == Direction.NORTH_WEST ? startX - 2 : startX + 4, startY - i, 1));
            }
        } else if (direction == Direction.SOUTH_EAST || direction == Direction.SOUTH_WEST) {
            for (int i = 0; i < 3; i++) {
                generateBashAttackTiles.add(new Position(direction == Direction.SOUTH_WEST ? startX - 1 + i : startX + 1 + i, startY + 2, 1));
            }
            for (int i = 0; i < 2; i++) {
                generateBashAttackTiles.add(new Position(direction == Direction.SOUTH_WEST ? startX - 1 : startX + 3, startY + 3 + i, 1));
            }
            for (int i = 0; i < 4; i++) {
                generateBashAttackTiles.add(new Position(direction == Direction.SOUTH_WEST ? startX - 2 + i : startX + 1 + i, startY + 1, 1));
            }
            for (int i = 0; i < 3; i++) {
                generateBashAttackTiles.add(new Position(direction == Direction.SOUTH_WEST ? startX - 2 : startX + 4, startY + 2 + i, 1));
            }
        }
    }

    private int getOffsetX(Direction direction, int x) {
        switch (direction) {
            case EAST:
                return x + 3;
            case WEST:
                return x - 3;
            default:
                return x;
        }
    }

    private int getOffsetY(Direction direction, int y) {
        switch (direction) {
            case NORTH:
            case NORTH_WEST:
            case NORTH_EAST:
                return y + 3;
            case SOUTH:
            case SOUTH_EAST:
            case SOUTH_WEST:
                return y - 3;
            default:
                return y;
        }
    }

    private void lightningAttack() {
        int animation = Random.rollDie(50) ? LIGHTNING_ANIMATION_SMITE : LIGHTNING_ANIMATION_STRIKE;
        npc.animate(animation);

        ArrayList<Entity> possibleTargets = npc.getPossibleTargets();
        int rad = possibleTargets.size() == 1 ? 5 : 7;

        // Find list of eligible tiles based on the radius around the npc
        List<Position> radius = new ArrayList<>(rad * rad);
        for (int x = -rad; x <= rad; x++) {
            for (int y = -rad; y <= rad; y++) {
                radius.add(
                        possibleTargets.size() == 1
                        ? target.getPosition().copy().relative(x, y)
                        : npc.getPosition().copy().relative(x, y));
            }
        }

        Set<Position> tiles = new HashSet<>();
        for (Entity target : possibleTargets) {
            // Find the tile that we want to spawn "adjacent" to the player
            Position adjacent = target.getPosition().copy().relative(Misc.random(-1, 1), Misc.random(-1, 1));
            if (Tile.get(adjacent) != null && !Tile.get(adjacent).isFloorFree()) {
                // if the adjacent tile is in a wall just fallback to the player's tile
                adjacent = target.getPosition().copy();
            }

            tiles.add(adjacent);
        }

        int players = tiles.size();
        // Randomly find 3-4 more tiles
        Collections.shuffle(radius);
        Iterator<Position> iter = radius.iterator();
        // Continue to randomly add tiles until we have our 5 selected
        while (iter.hasNext() && tiles.size() < 4 + players) {
            Position tile = iter.next();
            if (Tile.get(tile) != null && Tile.get(tile).isFloorFree()) {
                tiles.add(tile);
            }
            iter.remove();
        }

        for (Position tile : tiles) {
            int lightning = phase == Phase.ENRAGED ? ENRAGED_LIGHTNING : REGULAR_LIGHTNING;
            lightningHit(tile, lightning);
        }
    }

    private void lightningHit(Position tile, int graphics) {
        World.sendGraphics(graphics, 0, 0, tile);
        World.startEvent(e -> {
            e.delay(3);
            for (Player player : npc.localPlayers()) {
                int distance = player.getPosition().distance(tile);
                if (distance <= 1) {
                    player.hit(new Hit().fixedDamage(Misc.random(distance == 0 ? (calvarion ? 20 : 30) : (calvarion ? 10 : 15))));
                }
            }
        });
    }

    private void lightningRing() {
        Position middle = npc.getCentrePosition();
        Position[] ring = new Position[]{
                middle.relative(1, 3),
                middle.relative(2, 2),
                middle.relative(3, 1),

                middle.relative(3, -1),
                middle.relative(2, -2),
                middle.relative(1, -3),

                middle.relative(-1, -3),
                middle.relative(-2, -2),
                middle.relative(-3, -1),

                middle.relative(-3, 1),
                middle.relative(-2, 2),
                middle.relative(-1, 3)
        };

        npc.animate(LIGHTNING_ANIMATION_SMITE);
        npc.getCombat().delayAttack(6);
        npc.freeze(6, npc);

        for (Position tile : ring) {
            if (!Tile.get(tile).isFloorFree()) {
                continue;
            }
            lightningHit(tile, ENRAGED_LIGHTNING);
        }
    }

    private void spawnHellhounds() {
        final List<Player> players = npc.localPlayers();
        // Two hellhound for the "main target" and one for each additional player in the room, up to 25 max
        int numHellhounds = calvarion ? 2 : Math.min(2 + players.size() - 1, 25);

        Stack<Position> coords = new Stack<>();
        while (coords.size() < numHellhounds) {
            final int x = calvarion ? Misc.random(1876, 1894) : Misc.random(3288, 3302);
            final int y = calvarion ? Misc.random(11540, 11552) : Misc.random(10196, 10208);
            final Position coord = new Position(x, y, 1);
            if (coords.contains(coord) || !Tile.get(x, y, 1).isFloorFree()) {
                continue;
            }
            coords.add(coord);
        }

        if (!coords.isEmpty()) {
            String talk = Random.get(HOUND_SPAWN_CHATS);
            if (phase == Phase.ENRAGED) {
                talk = talk.toUpperCase();
            }
            npc.forceText(talk);
            npc.animate(HELLHOUND_SUMMON_ANIMATION);
            npc.getCombat().delayAttack(6);
        }
        int id = (calvarion ? (phase == Phase.ENRAGED ? 12108 : 12107) : (phase == Phase.ENRAGED ? 6614 : 6613));
        spawnHellhound(id, coords.pop(), npc.getCombat().getTarget());
        spawnHellhound(id, coords.pop(), npc.getCombat().getTarget());

        // Spawn one hellhound for each of the remaining players in the room
        if (!calvarion) {
            for (Player player : players) {
                if (player == npc.getCombat().getTarget()) {
                    continue;
                }
                if (!coords.isEmpty()) {
                    spawnHellhound(id, coords.pop(), player);
                }
            }
        }
    }

    private void spawnHellhound(int npcId, Position tile, Entity target) {
        NPC hellhound = new NPC(npcId);
        hellhound.setIgnoreMulti(true);
        hellhounds.add(hellhound);
        hellhound.spawn(tile);
        if (target != null) {
            hellhound.getCombat().setTarget(target);
            hellhound.face(target);
        }
    }

    private void forceTalk(String text) {
        String talk = text;
        if (phase == Phase.ENRAGED) {
            talk = talk.toUpperCase();
        }
        npc.forceText(talk);
    }

    @Override
    public void dropItems(Killer killer) {
        if (calvarion) new NPCDrops(this).dropItems(killer);
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
            int killCount = BossKillCounter.VETION.getCounter().increment(p);

            if (playerLoop == 0) {
                new GroundItem(ItemID.BIG_BONES, 1).owner(p).position(tile).spawn();
            }
            int playerDamage = damageMap.getOrDefault(p, 0);
            if (!uniqueGiven && provideUnique) { //only bother checking the unique trigger if we need to
                if (trigger <= playerDamage) { // if the trigger value is inside of the damage dealt by the current player he gets unique
                    Item item = VetionDropTable.rollUnique();
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
                Item item = VetionDropTable.rollRegular();
                item.setAmount((int) Math.ceil((double) item.getAmount() * quantityMultiplier));
                new GroundItem(item).owner(p).position(tile).spawn();
                p.getCollectionLog().collect(item);
            }

            // Pet chance
            int dropAverage = (int) (1500 * (2D - quantityMultiplier));
            int threshold = 500;
            int numerator = (killCount / threshold) + 1;
            if (Random.rollDie(dropAverage, numerator)) {
                Pet.VETION_JR_PURPLE.unlock(p);
            }
        }

        //just in case the kill had more than 10 people and the 11th+ person was supposed to get unique, give it to most dmg
        if (!uniqueGiven && provideUnique) {
            final Player p = players.get(0);
            Item item = VetionDropTable.rollUnique();
            new GroundItem(item).owner(p).position(tile).spawn();
            p.getCollectionLog().collect(item);
            NPCDrops.getRareDropAnnounce(p, item, npc);
        }

        damageMap.clear();
    }
}
