package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
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

    public static void removeCharges(Player player, int amount) {
        Item staff = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (staff == null) {
            System.err.println("Bryophyta's staff: Tried to remove charge with no available charges! player: " + player.getName());
            return;
        }
        if (!Random.rollDie(15, 1)) {
            if (amount >= staff.getCharges()) {
                staff.removeCharges();
                staff.setId(UNCHARGED);
                player.sendMessage(Color.RED.wrap("Your Bryophyta's staff has run out of charges."));
            } else {
                staff.setCharges(staff.getCharges() - amount);
            }

        }
    }

    public static int getCharges(Player player) {
        Item staff = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (staff == null)
            return 0;
        if (staff.getId() != CHARGED)
            return 0;
        return staff.getCharges();
    }

    private static void createStaff(Player player, Item staff, Item essence) {
        player.animate(7981);
        player.graphics(264, 184, 30);
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
