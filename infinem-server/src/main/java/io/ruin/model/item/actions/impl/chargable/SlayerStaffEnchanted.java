package io.ruin.model.item.actions.impl.chargable;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.slayer.Slayer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/6/2024
 */
public class SlayerStaffEnchanted {

    private static final int SLAYER_STAFF_E = 21255;

    private static void check(Player player, Item item) {
        player.sendMessage("Your Slayer's staff (e) has " + item.getCharges() + " charges left.");
    }

    private static void enchant(Player player, Item staff, Item scroll) {
        player.dialogue(
                new YesNoDialogue("Are you sure you want to do this?", "You will enchant your staff, making it untradable.", staff, () -> {
                    staff.setId(SLAYER_STAFF_E);
                    staff.setCharges(2500);
                    scroll.remove(1);
                    new ItemDialogue().one(SLAYER_STAFF_E, "You have enchanted your Slayer's staff.");
                })
        );
    }

    private static void revert(Player player, Item staff) {
        player.dialogue(
                new YesNoDialogue("Are you sure you want to do this?", "You will revert your staff and not receive the enchantment back.", staff, () -> {
                    staff.setId(Items.SLAYERS_STAFF);
                    staff.removeCharges();
                    new ItemDialogue().one(SLAYER_STAFF_E, "You have reverted your Slayer's staff.");
                })
        );
    }

    private static void removeCharge(Player player, Item staff) {
        if (staff != null && staff.getId() == SLAYER_STAFF_E) {
            int currentCharges = staff.getCharges();
            if (currentCharges <= 0) {
                System.err.println("Tried to remove charge with no available charges! player: " + player.getName() + ", item: " + staff);
                return;
            }
            staff.setCharges(currentCharges - 1);
            if (currentCharges - 1 <= 0) {
                staff.setId(Items.SLAYERS_STAFF);
                player.sendMessage("Your Slayer's staff (e) has run out of charges.");
            }
        }
    }

    static {
        ItemAction.registerInventory(SLAYER_STAFF_E, "revert", SlayerStaffEnchanted::revert);
        ItemAction.registerInventory(SLAYER_STAFF_E, "check", SlayerStaffEnchanted::check);
        ItemAction.registerEquipment(SLAYER_STAFF_E, "check", SlayerStaffEnchanted::check);
        ItemItemAction.register(Items.SLAYERS_STAFF, 21257, SlayerStaffEnchanted::enchant);
        ItemDefinition.get(SLAYER_STAFF_E).addPreTargetDefendListener((player, item, hit, target) -> {
            if (hit.attackStyle != null
                    && hit.attackStyle.isMagic()
                    && target.npc != null
                    && !hit.keepCharges
                    && Slayer.isTask(player, target.npc)) {
                removeCharge(player, item);
            }
        });
    }
}
