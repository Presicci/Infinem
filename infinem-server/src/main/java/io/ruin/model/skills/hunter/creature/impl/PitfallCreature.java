package io.ruin.model.skills.hunter.creature.impl;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.item.Items;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.utility.CollectionUtils;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/28/2024
 */
public class PitfallCreature extends NPCCombat {

    static {
        for (Creature creature : Creature.values()) {
            NPCAction.register(creature.npcId, "tease", PitfallCreature::tease);
        }
    }

    @Override
    public void init() {}

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        basicAttack();
        return true;
    }

    @Override
    protected Hit basicAttack() {
        npc.animate(info.attack_animation);
        Hit hit = new Hit(npc, AttackStyle.SLASH, null).randDamage(7);
        target.hit(hit);
        int hitSound = info.attack_sound;
        if (hitSound > 0) {
            target.privateSound(hitSound);
        }
        return hit;
    }

    public void jump(Trap trap) {
        int px = npc.getAbsX();
        int py = npc.getAbsY();
        GameObject object = trap.getObject();
        Direction dir = (object.direction & 1) == 0 ? (py > object.y ? Direction.SOUTH : Direction.NORTH) : (px > object.x ? Direction.WEST : Direction.EAST);
        Position destination = new Position(dir == Direction.WEST ? (px - 4) : dir == Direction.EAST ? (px + 4) : px, dir == Direction.SOUTH ? (py - 4) : dir == Direction.NORTH ? (py + 4) : py, npc.getHeight());
        Creature proj = Objects.requireNonNull(CollectionUtils.findMatching(Creature.values, value -> value.npcId == npc.getId()));
        Projectile firstProj = dir == Direction.EAST || dir == Direction.NORTH ? proj.right : proj.left;
        Projectile secondProj = dir == Direction.EAST || dir == Direction.NORTH ? proj.left : proj.right;
        Position mainProjectileStart = npc.getPosition().copy();
        Position mainProjectileEnd = destination.copy();
        Position secondProjectileStart = mainProjectileStart.copy();
        Position secondProjectileEnd = mainProjectileEnd.copy();
        int firstYOffset = dir == Direction.EAST || dir == Direction.WEST ? 1 : 0;
        int secondYOffset = dir == Direction.NORTH || dir == Direction.SOUTH ? 1 : 0;
        npc.startEvent(e -> {
            npc.lock();
            npc.face(object);
            npc.animate(5231, 20);
            npc.face(px + (dir == Direction.EAST ? 200 : dir == Direction.WEST ? -200 : 0), py + (dir == Direction.NORTH ? 200 : dir == Direction.SOUTH ? -200 : 0));
            mainProjectileStart.translate(0, firstYOffset, 0);
            mainProjectileEnd.translate(0, firstYOffset, 0);
            firstProj.send(mainProjectileStart, mainProjectileEnd);
            secondProjectileStart.translate(1, secondYOffset, 0);
            secondProjectileEnd.translate(1, secondYOffset, 0);
            secondProj.send(secondProjectileStart, secondProjectileEnd);
            e.delay(2);
            npc.getMovement().teleport(destination);
            List<Trap> attemptedTraps = npc.getTemporaryAttributeOrDefault("ATTEMPTED_TRAPS", new ArrayList<>());
            attemptedTraps.add(trap);
            npc.putTemporaryAttribute("ATTEMPTED_TRAPS", attemptedTraps);
            npc.unlock();
        });
    }

    public void fallIn(Player player, Trap trap) {
        GameObject object = trap.getObject();
        Direction dir = (object.direction & 1) == 0 ? (npc.getAbsY() > object.y ? Direction.SOUTH : Direction.NORTH) : (npc.getAbsX() > object.x ? Direction.WEST : Direction.EAST);
        int rot = object.direction;
        int nwVar = rot == 1 || rot == 2 ? 3 : 4;
        int seVar = rot == 1 || rot == 2 ? 4 : 3;
        npc.startEvent(e -> {
            npc.lock();
            npc.face(object);
            npc.stepAbs(object.x, object.y, StepType.FORCE_WALK);
            e.delay(2);
            npc.animate(npc.getCombat().getInfo().death_animation);
            trap.setVarpbit(player, 2);
            player.privateSound(2638);
            e.delay(2);
            npc.setHidden(true);
            npc.getCombat().reset();
            trap.setVarpbit(player, dir == Direction.NORTH || dir == Direction.WEST ? nwVar : seVar);
            player.privateSound(667);
            npc.unlock();
            e.delay(20);
            npc.getMovement().teleport(npc.getSpawnPosition());
            npc.removeTemporaryAttribute("ATTEMPTED_TRAPS");
            npc.setHidden(false);
        });
    }

    private static final String TEASE_KEY = "TEASED";

    private static void tease(Player player, NPC npc) {
        if (!player.getInventory().hasId(Items.TEASING_STICK)) {
            player.sendMessage("You need a teasing stick to tease the " + npc.getDef().name.toLowerCase() + ".");
            return;
        }
        if (npc.getCombat().getTarget() != null && npc.getCombat().getTarget() == player) {
            player.sendMessage("I think it's already mad at me.");
            return;
        }
        if (npc.getCombat().isAttacking(10)) {
            player.sendMessage("That creature seems to be mad at someone else.");
            return;
        }
        if (player.getCombat().isDefending(6)) {
            player.sendMessage("I'm already under attack!");
            return;
        }
        player.animate(5236);
        npc.animate(npc.getCombat().getDefendAnimation());
        player.privateSound(2524);
        npc.getCombat().setTarget(player);
        npc.getCombat().faceTarget();
        npc.doIfOutOfCombat(() -> {
            npc.getCombat().reset();
        });
    }

    private enum Creature {
        LARUPIA(2908, 939, 940),
        GRAAHK(2909, 941, 942),
        KYATT(2907, 937, 938),
        SUNLIGHT_ANTELOPE(13133, 2800, 2801);

        private static final List<Creature> values = Collections.unmodifiableList(Arrays.asList(values()));
        private final int npcId;
        private final Projectile left;
        private final Projectile right;

        Creature(int npcId, int left, int right) {
            this.npcId = npcId;
            this.left = new Projectile(left, 0, 0, 53, 69, 5, 0, 0);
            this.right = new Projectile(right, 0, 0, 53, 69, 5, 0, 0);
        }
    }
}
