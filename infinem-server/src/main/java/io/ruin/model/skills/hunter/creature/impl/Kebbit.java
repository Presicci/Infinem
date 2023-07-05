package io.ruin.model.skills.hunter.creature.impl;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.skills.hunter.creature.Creature;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.hunter.traps.TrapType;
import io.ruin.model.skills.hunter.traps.impl.DeadfallTrap;
import io.ruin.process.event.Event;
import kilim.Pausable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2023
 */
public class Kebbit extends Creature {

    static {
        Hunter.registerCreature(new Kebbit("wild kebbit", 1349, 23, 128, new int[] { 20131, 20647 }, 20651, Items.KEBBIT_CLAWS, 0.55, 30, PlayerCounter.CAUGHT_WILD_KEBBIT));
        Hunter.registerCreature(new Kebbit("barb-tailed kebbit", 1348, 33, 168, new int[] { 20129, 20130 }, 20650, Items.BARBTAIL_HARPOON, 0.55, 30, PlayerCounter.CAUGHT_BARB_KEBBIT));
        Hunter.registerCreature(new Kebbit("prickly kebbit", 1346, 37, 204, new int[] { 19218, 19219 }, 20648, Items.KEBBIT_SPIKE, 0.55, 30, PlayerCounter.CAUGHT_PRICKLY_KEBBIT));
        Hunter.registerCreature(new Kebbit("sabre-toothed kebbit", 1347, 51, 200, new int[] { 19851, 20128 }, 20649, Items.KEBBIT_TEETH, 0.55, 30, PlayerCounter.CAUGHT_SABRE_KEBBIT));
    }

    private final int caughtObject, itemId;
    private final int[] trappingObjects;

    public Kebbit(String creatureName, int npcId, int levelReq, double catchXP, int[] trappingObjects, int caughtObject, int itemId, double baseCatchChance, int respawnTicks, PlayerCounter counter) {
        super(creatureName, npcId, levelReq, catchXP, baseCatchChance, respawnTicks, counter);
        this.trappingObjects = trappingObjects;
        this.caughtObject = caughtObject;
        this.itemId = itemId;
    }

    @Override
    public TrapType getTrapType() {
        return DeadfallTrap.INSTANCE;
    }

    @Override
    public List<Item> getLoot() {
        return Arrays.asList(new Item(itemId, 1), new Item(Items.BONES, 1));
    }

    @Override
    protected void prepareForCatchAttempt(NPC npc, Trap trap, Event event) throws Pausable {
        npc.face(trap.getObject());
        event.delay(1);
    }

    @Override
    public void pathToTrap(NPC npc, GameObject trap) {
        int x = trap.direction == 0 ? trap.x + 1 : trap.x;
        npc.getRouteFinder().routeAbsolute(x, trap.y);
    }

    private static int getDirection(NPC npc, Trap trap) {
        int diffX = npc.getPosition().getX() - trap.getObject().x;
        int diffY = npc.getPosition().getY() - trap.getObject().y;
        if (diffX == 1 && diffY == -1)
            return 1;
        if (diffY == 1 || diffX == 1)
            return 0;
        else
            return 1;

    }

    @Override
    protected void succeedCatch(NPC npc, Trap trap, Event event) throws Pausable {
        npc.animate(5275);
        npc.publicSound(2631);
        npc.publicSound(524);
        trap.getObject().setId(trappingObjects[getDirection(npc, trap)]);
        npc.setHidden(true);
        if (!trap.getOwner().isOnline()) {
            return;
        }
        event.delay(1);
        trap.getObject().setId(caughtObject);
    }

    @Override
    protected void failCatch(NPC npc, Trap trap, Event event) throws Pausable {
        npc.animate(5185);
        trap.getObject().setId(20652);
        event.delay(2);
        if (!trap.getOwner().isOnline()) {
            return;
        }
        trap.getObject().setId(trap.getTrapType().getFailedObjectId());
    }
}
