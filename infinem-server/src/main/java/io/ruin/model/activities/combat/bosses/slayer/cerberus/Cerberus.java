package io.ruin.model.activities.combat.bosses.slayer.cerberus;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class Cerberus extends NPCCombat { // todo - only allow attacking if on a slayer task

    private boolean ATTACK_STARTED = false;
    private static final int FIRE_WALL = 23105;
    private static final int NORTH_REGION = 5140;
    private static final int EAST_REGION = 5395;
    private static final int WEST_REGION = 4883;
    private FireWall[] FIRE_WALLS;
    private static final FireWall[] NORTH_CERB = {new FireWall(FIRE_WALL, 1305, 1306, 0, 10, 0), new FireWall(FIRE_WALL, 1304, 1306, 0, 10, 0), new FireWall(FIRE_WALL, 1303, 1306, 0, 10, 0)}; //5140
    private static final FireWall[] EAST_CERB = {new FireWall(FIRE_WALL, 1369, 1242, 0, 10, 0), new FireWall(FIRE_WALL, 1368, 1242, 0, 10, 0), new FireWall(FIRE_WALL, 1367, 1242, 0, 10, 0)}; //5395
    private static final FireWall[] WEST_CERB = {new FireWall(FIRE_WALL, 1241, 1242, 0, 10, 0), new FireWall(FIRE_WALL, 1240, 1242, 0, 10, 0), new FireWall(FIRE_WALL, 1239, 1242, 0, 10, 0)}; //4883

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1242, 65, 31, 25, 86, 20, 15, 220);
    private static final Projectile RANGED_PROJECTILE = new Projectile(1245, 65, 31, 25, 86, 20, 15, 220);
    private static final Projectile LAVA_PROJECTILE = new Projectile(1247, 65, 0, 25, 86, 0, 15, 220);

    private static final Projectile SOUL_MAGIC_PROJECTILE = new Projectile(100, 31, 31, 15, 50, 0, 12, 11);
    private static final Projectile SOUL_MELEE_PROJECTILE = new Projectile(1248, 31, 31, 15, 50, 0, 12, 11);
    private static final List<Soul> souls = new ArrayList<>(3);

    private Position[] soulSpawns;

    private TickDelay comboAttackCooldown = new TickDelay();
    private TickDelay summonSoulsCooldown = new TickDelay();
    private TickDelay spreadLavaCooldown = new TickDelay();
    @Override
    public void init() {
        soulSpawns = new Position[3];
        //west -> east
        soulSpawns[0] = new Position(npc.spawnPosition.getX() + 2 - 1, npc.spawnPosition.getY() + 2 + 16, npc.spawnPosition.getZ());
        soulSpawns[1] = new Position(npc.spawnPosition.getX() + 2, npc.spawnPosition.getY() + 2 + 16, npc.spawnPosition.getZ());
        soulSpawns[2] = new Position(npc.spawnPosition.getX() + 2 + 1, npc.spawnPosition.getY() + 2 + 16, npc.spawnPosition.getZ());
        npc.deathEndListener = (entity, killer, killHit) -> {
          comboAttackCooldown.reset();
          summonSoulsCooldown.reset();
          spreadLavaCooldown.reset();
        };
        npc.addEvent(event -> {
            while (true) {
                if (ATTACK_STARTED && !npc.getCombat().isDead() && !npc.isHidden() && !npc.isRemoved()
                        && (npc.localPlayers().isEmpty())) {// no players in sight, reset
                    npc.getMovement().teleport(npc.spawnPosition);
                    npc.resetActions(true, true);
                    npc.getCombat().restore();
                    removeFireWallSpawns();
                }
                event.delay(5);
            }
        });
    }

    @Override
    public void follow() {
        follow(10);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (!ATTACK_STARTED) {
            setFireWallSpawns();
        }
        if (!comboAttackCooldown.isDelayed()) {
            comboAttack();
            return true;
        }
        if (npc.getHp() <= 400 && !summonSoulsCooldown.isDelayed()) {
            summonSouls();
            return true;
        }
        if (npc.getHp() <= 200 && !spreadLavaCooldown.isDelayed()) {
            spreadLava();
            return true;
        }
        if (withinDistance(1) && Random.rollPercent(25)) {
            meleeAttack(true);
        } else if (Random.rollPercent(50)) {
            magicAttack();
        } else {
            rangedAttack();
        }
        return true;
    }

    private void rangedAttack() {
        int delay = RANGED_PROJECTILE.send(npc, target);
        npc.animate(4492);
        target.hit(new Hit(npc, AttackStyle.RANGED, null).randDamage(info.max_damage).clientDelay(delay));
        target.graphics(1244, 100, delay);
    }

    private void magicAttack() {
        int delay = MAGIC_PROJECTILE.send(npc, target);
        npc.animate(4492);
        Hit hit = new Hit(npc, AttackStyle.MAGIC, null)
                .randDamage(info.max_damage)
                .clientDelay(delay);
        hit.postDamage(t -> {
            if(hit.damage > 0)
                t.graphics(1243, 100, 0);
        });
        target.hit(hit);
    }

    private void meleeAttack(boolean animate) {
        if (animate)
            npc.animate(info.attack_animation);
        target.hit(new Hit(npc, AttackStyle.SLASH, null).randDamage(info.max_damage));
    }

    private void comboAttack() {
        npc.animate(4490); // triple attack
        delayAttack(3);
        magicAttack();
        npc.addEvent(event -> {
            event.delay(2);
            if (!canAttack(target))
                return;
            rangedAttack();
            event.delay(2);
            if (!canAttack(target))
                return;
            meleeAttack(false);
        });
        comboAttackCooldown.delay(66); // ~40 seconds cooldown
    }

    private void spreadLava() {
        spreadLavaCooldown.delay(36);
        npc.animate(4493);
        npc.forceText("Grrrrrrrrrr");
        Position[] positions = {target.getPosition().copy(),
                new Position(npc.getAbsX() + Random.get(-4, npc.getSize() + 4), npc.getAbsY() + Random.get(-4, npc.getSize() + 4), npc.getHeight()),
                new Position(npc.getAbsX() + Random.get(-4, npc.getSize() + 4), npc.getAbsY() + Random.get(-4, npc.getSize() + 4), npc.getHeight())};
        for (Position pos : positions) {
            LAVA_PROJECTILE.send(npc.getAbsX(), npc.getAbsY(), pos.getX(), pos.getY());
            npc.addEvent(event -> {
                World.sendGraphics(1246, 0, 0, pos.getX(), pos.getY(), pos.getZ());
                event.delay(2);
                for (int i = 0; i < 6; i++) {
                    if (target == null)
                        return;
                    if (target.getPosition().equals(pos)) {
                        target.hit(new Hit().randDamage(10, 15));
                    } else if (Misc.getDistance(target.getPosition(), pos) == 1) {
                        target.hit(new Hit().fixedDamage(7));
                    }
                    event.delay(2);
                }
                World.sendGraphics(1247, 0, 0, pos.getX(), pos.getY(), pos.getZ());
                event.delay(1);
                if (target == null)
                    return;
                if (target.getPosition().equals(pos)) {
                    target.hit(new Hit().randDamage(15, 18));
                } else if (Misc.getDistance(target.getPosition(), pos) == 1) {
                    target.hit(new Hit().fixedDamage(10));
                }
            });
        }
    }

    private void summonSouls() {
        summonSoulsCooldown.delay(60);
        npc.forceText("Aaarrrooooooo");
        npc.animate(4494);
        Collections.shuffle(souls);
        for (int i = 0; i < 3; i++) {
            Soul s = souls.get(i);
            NPC soul = new NPC(s.npcId).spawn(soulSpawns[i].getX(), soulSpawns[i].getY(), soulSpawns[i].getZ(), Direction.SOUTH, 0);
            int soulIndex = i;
            soul.addEvent(event -> { // ah this was such a pain in the ass to make work on osrune... this is much easier
                soul.step(0, -12, StepType.FORCE_WALK); // walk to position
                event.waitForMovement(soul); // wait
                event.delay(1 + (soulIndex * 2));
                if (target == null || isDead()) { // dont attack if cerberus is dead or target is gone
                    soul.remove();
                    return;
                }
                s.attack(soul, target.player);
                event.delay(3);
                soul.faceNone(false);
                soul.step(0, 10, StepType.FORCE_WALK);
                event.waitForMovement(soul);
                soul.remove();
            });
        }
    }

    @Override
    public void startDeath(Hit killHit) {
        removeFireWallSpawns();
        super.startDeath(killHit);
    }

    private void setFireWallSpawns() {
        switch (npc.getPosition().getRegion().id) {
            case NORTH_REGION:
                FIRE_WALLS = NORTH_CERB;
                break;
            case EAST_REGION:
                FIRE_WALLS = EAST_CERB;
                break;
            case WEST_REGION:
                FIRE_WALLS = WEST_CERB;
                break;
            default:
                ATTACK_STARTED = true;
                return;
        }
        if (FIRE_WALLS != null) {
            for (FireWall fireWall : FIRE_WALLS) {
                fireWall.spawn();
                fireWall.startFirewallCheck();
                fireWall.cerberusCombat = this;
            }
        }
        ATTACK_STARTED = true;
    }

    private void removeFireWallSpawns() {
        if (FIRE_WALLS != null) {
            for (FireWall fireWall : FIRE_WALLS) {
                fireWall.remove();
            }
        }
        FIRE_WALLS = null;
        ATTACK_STARTED = false;
    }

    protected void restartCerb(Player player) {
        if (npc.getCombat() != null && !npc.getCombat().isDead() && npc.getCombat().getTarget() != null && npc.getCombat().getTarget().equals(player)) {
            npc.getMovement().teleport(npc.spawnPosition);
            npc.resetActions(true, true);
            npc.getCombat().restore();
        }
        removeFireWallSpawns();
    }



    private enum Soul {
        RANGED(5867, AttackStyle.RANGED, (npc, p) -> {
            npc.animate(8530);
        }),
        MAGIC(5868, AttackStyle.MAGIC, (npc, p) -> {
            npc.animate(8529);
            SOUL_MAGIC_PROJECTILE.send(npc, p);
        }),
        MELEE(5869, AttackStyle.SLASH, (npc, p) -> {
            npc.animate(8528);
            SOUL_MELEE_PROJECTILE.send(npc, p);
        })
        ;
        int npcId;
        AttackStyle style;
        BiConsumer<NPC, Player> animate;


        Soul(int npcId, AttackStyle style, BiConsumer<NPC, Player> animate) {
            this.npcId = npcId;
            this.style = style;
            this.animate = animate;
            souls.add(this);
        }


        void attack(NPC npc, Player player) {
            if (player == null || Misc.getDistance(npc.getPosition(), player.getPosition()) > 20)
                return;
            npc.face(player);
            animate.accept(npc, player);
            boolean block = (style.isRanged() && player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES))
                    || (style.isMagic() && player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC))
                    || (style.isMelee() && player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE));
            Hit hit = new Hit(null, style, null).delay(2);
            if (block) {
                hit.block();
                player.getPrayer().drain(player.getEquipment().getId(Equipment.SLOT_SHIELD) == 12821 ? 15 : 30); // spectral shield halves prayer drain
            } else {
                hit.fixedDamage(30);
                hit.ignoreDefence();
                hit.ignorePrayer();
            }
            player.hit(hit);
        }
    }
}
