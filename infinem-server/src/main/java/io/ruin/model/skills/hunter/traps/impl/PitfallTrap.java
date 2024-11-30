package io.ruin.model.skills.hunter.traps.impl;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.skills.hunter.creature.impl.PitfallCreature;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.hunter.traps.TrapType;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.ruin.model.skills.hunter.Hunter.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/28/2024
 */
public class PitfallTrap implements TrapType {

    private static final int[] LOGS = { Items.LOGS, Items.OAK_LOGS, Items.WILLOW_LOGS, Items.TEAK_LOGS, Items.MAPLE_LOGS, Items.MAHOGANY_LOGS, Items.ARCTIC_PINE_LOGS, Items.YEW_LOGS, Items.MAGIC_LOGS, Items.REDWOOD_LOGS };
    private static final PitfallTrap LARUPIA = new PitfallTrap(31, 180, new int[] { Items.BIG_BONES, 29122, Items.TATTY_LARUPIA_FUR }, 19259, 19260, 19261, 19262, 19263);
    private static final PitfallTrap GRAAHK = new PitfallTrap(41, 240, new int[] { Items.BIG_BONES, 29119, Items.TATTY_GRAAHK_FUR }, 19264, 19265, 19266, 19267, 19268);
    private static final PitfallTrap KYATT = new PitfallTrap(55, 300, new int[] { Items.BIG_BONES, 29125, Items.TATTY_KYATT_FUR }, 19253, 19254, 19255, 19256, 19257, 19258);

    static {
        for (PitfallTrap trap : Arrays.asList(LARUPIA, GRAAHK, KYATT)) {
            for (int objId : trap.objectIds) {
                ObjectAction.register(objId, 1, trap::jumpTrap);
                ObjectAction.register(objId, 2, Hunter::dismantleTrap);
                ObjectAction.register(objId, 3, trap::layTrap);
            }
        }
    }

    private final int levelReq;
    private final double experience;
    private final int[] loot, objectIds;

    public PitfallTrap(int levelReq, double experience, int[] loot, int... objectIds) {
        this.levelReq = levelReq;
        this.experience = experience;
        this.loot = loot;
        this.objectIds = objectIds;
    }

    private void jumpTrap(Player player, GameObject obj) {
        int px = player.getAbsX();
        int py = player.getAbsY();
        Direction dir = (obj.direction & 1) == 0 ? (py > obj.y ? Direction.SOUTH : Direction.NORTH) : (px > obj.x ? Direction.WEST : Direction.EAST);
        Position initialPos = player.getPosition().copy();
        Position destination = new Position(dir == Direction.WEST ? (px - 3) : dir == Direction.EAST ? (px + 3) : px, dir == Direction.SOUTH ? (py - 3) : dir == Direction.NORTH ? (py + 3) : py, player.getHeight());
        player.startEvent(e -> {
            player.lock();
            player.face(dir);
            e.delay(1);
            player.animate(3067, 20);
            player.privateSound(2635);
            player.getMovement().force(destination.getX() - px, destination.getY() - py, 0, 0, 35, 90, dir);
            e.delay(4);
            if (player.getCombat().lastAttacker != null && player.getCombat().lastAttacker.isNpc() && player.getCombat().lastAttacker.getCombat() instanceof PitfallCreature) {
                NPC creature = player.getCombat().lastAttacker.npc;
                Trap trap = obj.getTemporaryAttributeOrDefault(AttributeKey.OBJECT_TRAP, null);
                if (creature.getPosition().distance(initialPos) <= 1) {
                    if (creature.getTemporaryAttributeOrDefault("ATTEMPTED_TRAPS", new ArrayList<>()).contains(trap)) {
                        player.sendMessage("Doesn't look like it's falling for it twice.");
                    } else {
                        if (rollCatch(player, creature)) {
                            ((PitfallCreature) creature.getCombat()).fallIn(player, trap);
                        } else {
                            ((PitfallCreature) creature.getCombat()).jump(trap);
                        }
                    }
                }
            }
            player.unlock();
        });
    }

    public void layTrap(Player player, GameObject obj) {
        if (!canPlaceTrap(player, this))
            return;
        if (!player.getInventory().containsAny(false, LOGS) || !player.getInventory().contains(Items.KNIFE, 1)) {
            player.sendMessage("You'll need a knife and some logs to place a trap here.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(getPlaceAnimation());
            event.delay(1);
            player.getInventory().findFirst(LOGS).remove(1);
            player.privateSound(2645);
            Trap trap = new Trap(player, this, obj);
            trap.setVarpbit(player, 1);
            player.traps.add(trap);
            addTimeoutEvent(player, trap);
            player.unlock();
        });
    }

    private boolean rollCatch(Player owner, NPC npc) {
        if (owner == null)
            return false;
        double chance = 0.6;
        int levelDiff = owner.getStats().get(StatType.Hunter).currentLevel - getLevelReq();
        if (levelDiff < 0) {
            owner.sendMessage("You must have a Hunter level of at least " + getLevelReq() + " to catch " + npc.getDef().name.toLowerCase() + ".");
            return false;
        }
        chance *= 1 + (levelDiff * 0.02); // relative 2% increase per level
        return Random.get() <= Math.min(0.90, chance);
    }

    private List<Item> rollLoot(Player player) {
        return Arrays.stream(loot).map(id -> {
            int hunterLevel = player.getStats().get(StatType.Hunter).currentLevel;
            if (id == Items.TATTY_LARUPIA_FUR && Random.rollDie(20, hunterLevel / 30)) {
                return Items.LARUPIA_FUR;
            } else if (id == Items.TATTY_GRAAHK_FUR && Random.rollDie(20, hunterLevel / 30)) {
                return Items.GRAAHK_FUR;
            } else if (id == Items.TATTY_KYATT_FUR && Random.rollDie(20, hunterLevel / 30)) {
                return Items.KYATT_FUR;
            }
            return id;
        }).mapToObj(Item::new).collect(Collectors.toList());
    }

    @Override
    public void collapse(Player player, Trap trap, boolean remove) {
        if (trap.isRemoved() || trap.getOwner() == null) {
            return;
        }
        trap.setVarpbit(player, 0);
        if (remove)
            trap.getOwner().traps.remove(trap);
    }

    @Override
    public int getItemId() {
        return -1;
    }

    @Override
    public int getLevelReq() {
        return levelReq;
    }

    @Override
    public int getActiveObjectId() {
        return -1;
    }

    @Override
    public int getFailedObjectId() {
        return -1;
    }

    @Override
    public int getPlaceAnimation() {
        return 5212;
    }

    @Override
    public int getDismantleAnimation() {
        return 5212;
    }

    @Override
    public int[] getSuccessObjects() {
        return new int[] {  };
    }

    @Override
    public void onPlace(Player player, GameObject object) {}

    @Override
    public void onRemove(Player player, GameObject object) {
        int vb = object.getDef().varpBitId;
        if (vb == -1) return;
        int value = Config.varpbit(vb, false).get(player);
        if (value >= 3) {
            rollLoot(player).forEach(item -> Hunter.processLoot(player, item));
            player.getStats().addXp(StatType.Hunter, experience, true);
            player.getCombat().lastAttacker = null;
        }
    }
}
