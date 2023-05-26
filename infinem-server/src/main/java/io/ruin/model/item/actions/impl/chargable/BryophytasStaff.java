package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/26/2023
 */
public class BryophytasStaff extends ChargeableItem {
    private static final int CHARGED = 22370;
    private static final int UNCHARGED = 22368;
    private static final int BRYOPHYTAS_ESSENCE = 22372;

    static {
        ItemItemAction.register(Items.BATTLESTAFF, BRYOPHYTAS_ESSENCE, BryophytasStaff::createStaff);

        ChargeableItem chargeableItem = new BryophytasStaff();
        register(chargeableItem);
    }

    public static boolean hasCharged(Player player) {
        Item staff = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (staff == null || staff.getId() != CHARGED)
            return false;
        if (!Random.rollDie(15, 1))
            staff.setCharges(staff.getCharges() - 1);
        return true;
    }

    private static void createStaff(Player player, Item staff, Item essence) {
        player.animate(7981);
        player.graphics(264, 0, 184);
        essence.remove();
        staff.setId(UNCHARGED);
    }

    @Override
    protected int getChargedId() {
        return 22370;
    }

    @Override
    protected int getUnchargedId() {
        return 22368;
    }

    @Override
    protected int getChargeItem() {
        return 561;
    }

    @Override
    protected int getMaxCharges() {
        return 1000;
    }

    @Override
    protected int getChargesPerItem() {
        return 1;
    }

    @Override
    protected boolean doesReturnItems() {
        return false;
    }
}
