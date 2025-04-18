package io.ruin.model.skills.hunter.creature.impl;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.skills.hunter.creature.Creature;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.hunter.traps.impl.NetTrap;
import io.ruin.model.stat.StatType;
import io.ruin.process.event.Event;
import kilim.Pausable;

import java.util.Collections;
import java.util.List;

import static io.ruin.model.skills.hunter.Hunter.destroyTrap;
import static io.ruin.model.skills.hunter.Hunter.isOwner;

public class Salamander extends Creature {

    private static void release(Player player, Item item) {
        item.remove();
        player.sendMessage("You release the " + (item.getId() == 10149 ? "lizard" : "salamander") + ".");
    }

    static {
        Hunter.registerCreature(new Salamander("swamp lizard", NetTrap.SWAMP, 2906, 10149, 29, 152, 0.5, 20, PlayerCounter.CAUGHT_SWAMP_LIZARD));
        Hunter.registerCreature(new Salamander("orange salamander", NetTrap.DESERT, 2903, 10146, 47, 224, 0.55, 20, PlayerCounter.CAUGHT_ORANGE_SALAMANDER));
        Hunter.registerCreature(new Salamander("red salamander", NetTrap.RED, 2904, 10147, 59, 272, 0.55, 20, PlayerCounter.CAUGHT_RED_SALAMANDER));
        Hunter.registerCreature(new Salamander("black salamander", NetTrap.BLACK, 2905, 10148, 67, 319.2, 0.6, 20, PlayerCounter.CAUGHT_BLACK_SALAMANDER));
        Hunter.registerCreature(new Salamander("tecu salamander", NetTrap.TECU, 12769, 28831, 79, 344, 0.6, 20, PlayerCounter.CAUGHT_TECU_SALAMANDER));
        for (int i = 10146; i <= 10149; i++) {
            ItemAction.registerInventory(i, "release", Salamander::release);
        }
        ItemAction.registerInventory(28834, "release", Salamander::release);
    }

    public Salamander(String creatureName, NetTrap trapType, int npcId, int itemId, int levelReq, double catchXP, double baseCatchChance, int respawnTicks, PlayerCounter counter) {
        super(creatureName, npcId, levelReq, catchXP, baseCatchChance, respawnTicks, counter);
        this.loot = Collections.singletonList(new Item(itemId, 1));
        this.trapType = trapType;
    }

    private final NetTrap trapType;
    private final List<Item> loot;

    @Override
    public NetTrap getTrapType() {
        return trapType;
    }

    @Override
    public List<Item> getLoot() {
        // Tecu salamander
        if (getNpcId() == 12769) {
            if (Random.rollDie(1000)) return Collections.singletonList(new Item(28834));
        }
        return loot;
    }

    @Override
    public void check(Player player, GameObject obj) {
        if (!hasRoomForLoot(player)) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            Trap trap = obj.getTemporaryAttribute(AttributeKey.OBJECT_TRAP);
            player.animate(trap.getTrapType().getDismantleAnimation());
            event.delay(2);
            addLoot(player);
            player.getStats().addXp(StatType.Hunter, getCatchXP(), true);
            if (getCounter() != null)
                getCounter().increment(player, 1);
            player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.NETTRAP, NPCDefinition.get(getNpcId()).name);
            player.getTaskManager().doLookupByCategory(TaskCategory.HUNTER_CATCH, 1, true);
            trap.getTrapType().onRemove(player, obj);
            if (obj.direction >= 2) {
                destroyTrap(obj);
            } else {
                trap.getOwner().traps.remove(trap);
                obj.removeTemporaryAttribute(AttributeKey.OBJECT_TRAP);
            }
            player.unlock();
        });

    }

    @Override
    protected void prepareForCatchAttempt(NPC npc, Trap trap, Event event) throws Pausable {
        //nothing required
    }

    @Override
    protected void succeedCatch(NPC npc, Trap trap, Event event) throws Pausable {
        npc.setHidden(true);

        GameObject tree = NetTrap.getTreeObject(trap.getObject());
        GameObject toReplace = tree;
        if (trap.getObject().direction >= 2)
            toReplace = trap.getObject();
        if (toReplace == tree)
            trap.getObject().remove();
        else
            tree.remove();
        toReplace.setId(trapType.getSucceedingId());
        event.delay(1);
        if (!trap.getOwner().isOnline()) {
            return;
        }
        toReplace.setId(trapType.getSuccessId()[0]);
        if (toReplace == tree)
            swap(trap);
    }

    @Override
    protected void failCatch(NPC npc, Trap trap, Event event) throws Pausable {
        npc.getRouteFinder().routeSelf();
        GameObject tree = NetTrap.getTreeObject(trap.getObject());
        GameObject toReplace = tree;
        if (trap.getObject().direction >= 2)
            toReplace = trap.getObject();
        if (toReplace == tree)
            trap.getObject().remove();
        else
            tree.remove();
        toReplace.setId(trapType.getFailingId());
        event.delay(1);
        if (!trap.getOwner().isOnline()) {
            return;
        }
        toReplace.setId(trapType.getFailedObjectId());
        if (toReplace == tree)
            swap(trap);
    }

    private void swap(Trap trap) { // need to do this because it's initially split into 2 different objects..
        GameObject other = NetTrap.getTreeObject(trap.getObject());
        trap.setObject(other);
        other.putTemporaryAttribute(AttributeKey.OBJECT_TRAP, trap);
    }

    @Override
    public boolean hasRoomForLoot(Player player) {
        return player.getInventory().hasFreeSlots(3); // sally + net + rope
    }


    public static void dismantleTrap(Player player, GameObject obj) {
        if (!isOwner(player, obj)) {
            player.sendMessage("This isn't your trap.");
            return;
        }
        Trap trap = obj.getTemporaryAttribute(AttributeKey.OBJECT_TRAP);
        if (trap == null) {
            destroyTrap(obj);
            return;
        }
        if (!player.getInventory().hasRoomFor(trap.getTrapType().getItemId())) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        if (trap.isBusy()) {
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(trap.getTrapType().getDismantleAnimation());
            event.delay(2);
            player.getInventory().add(trap.getTrapType().getItemId(), 1);
            trap.getTrapType().onRemove(player, obj);
            if (obj.direction >= 2) {
                destroyTrap(obj);
            } else {
                trap.getOwner().traps.remove(trap);
                obj.removeTemporaryAttribute(AttributeKey.OBJECT_TRAP);
            }
            player.unlock();
        });
    }

}
