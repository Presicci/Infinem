package io.ruin.model.skills.hunter.traps.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.hunter.traps.TrapType;

import static io.ruin.model.skills.hunter.Hunter.addTimeoutEvent;
import static io.ruin.model.skills.hunter.Hunter.canPlaceTrap;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2023
 */
public class DeadfallTrap implements TrapType {

    private static final int[] LOGS = { Items.LOGS, Items.OAK_LOGS, Items.WILLOW_LOGS, Items.TEAK_LOGS, Items.MAPLE_LOGS, Items.MAHOGANY_LOGS, Items.ARCTIC_PINE_LOGS, Items.YEW_LOGS, Items.MAGIC_LOGS, Items.REDWOOD_LOGS };

    public static final DeadfallTrap INSTANCE = new DeadfallTrap();

    static {
        ObjectAction.register(INSTANCE.getFailedObjectId(), "set-trap", INSTANCE::layTrap);
        Hunter.registerTrap(INSTANCE, false);
    }

    @Override
    public int getItemId() {
        return -1;
    }

    @Override
    public int getLevelReq() {
        return 23;
    }

    @Override
    public int getActiveObjectId() {
        return 19217;
    }

    @Override
    public int getFailedObjectId() {
        return 19215;
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
        return new int[] { 20651 };
    }

    @Override
    public void onPlace(Player player, GameObject object) {

    }

    @Override
    public void onRemove(Player player, GameObject object) {
        object.setId(getFailedObjectId());
    }

    @Override
    public void collapse(Player player, Trap trap, boolean remove) {
        if (trap.isRemoved() || trap.getObject().id == -1 || trap.getOwner() == null) {
            return;
        }
        GameObject object = trap.getObject();
        object.setId(getFailedObjectId());
        trap.setRemoved(true);
        if (remove) {
            trap.getOwner().traps.remove(trap);
        }
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
            obj.setId(getFailedObjectId());
            Trap trap = new Trap(player, this, GameObject.spawn(getActiveObjectId(), obj.getPosition().getX(), obj.getPosition().getY(), obj.getPosition().getZ(), 10, obj.direction));
            player.traps.add(trap);
            addTimeoutEvent(player, trap);
            player.unlock();
        });
    }
}
