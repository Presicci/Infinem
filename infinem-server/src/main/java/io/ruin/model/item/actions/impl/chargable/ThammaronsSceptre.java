package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.Random;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.item.Items;
import io.ruin.utility.Color;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;

import java.util.function.BiPredicate;

public class ThammaronsSceptre {

    private static final int CHARGED = 22555;
    private static final int UNCHARGED = 22552;

    private static final int MAX_AMOUNT = 16000;
    private static final int REVENANT_ETHER = 21820;
    private static final int ACTIVATION_AMOUNT = 1000;

    static {
        ItemAction.registerInventory(CHARGED, "check", ThammaronsSceptre::check);
        ItemAction.registerEquipment(CHARGED, "check", ThammaronsSceptre::check);
        ItemAction.registerInventory(CHARGED, "uncharge", ThammaronsSceptre::uncharge);
        ItemAction.registerInventory(UNCHARGED, "dismantle", ThammaronsSceptre::dismantle);
        ItemItemAction.register(CHARGED, REVENANT_ETHER, ThammaronsSceptre::charge);
        ItemItemAction.register(UNCHARGED, REVENANT_ETHER, ThammaronsSceptre::charge);
        ItemDefinition.get(CHARGED).addPreTargetDefendListener((player, item, hit, target) -> {
            if (hit.attackWeapon != null) consumeCharge(player, item);
            if (hit.attackStyle != null && hit.attackStyle.isMagic() && target.npc != null && player.wildernessLevel > 0) {
                hit.boostAttack(0.5);               // 50% accuracy increase
                hit.boostDamage(0.5);    // 50% damage increase
            }
        });
        ItemDefinition.get(CHARGED).custom_values.put("CAN_ATTACK", (BiPredicate<Player, Item>) (player, item) -> {
            int currentCharges = AttributeExtensions.getCharges(item);
            if (currentCharges > 1000) {
                return true;
            } else {
                player.sendMessage("Your Thammaron's sceptre has no charges remaining.");
                return false;
            }
        });
        ItemDefinition.get(UNCHARGED).custom_values.put("CAN_ATTACK", (BiPredicate<Player, Item>) (player, item) -> {
            player.sendMessage("Your Thammaron's sceptre has no charges remaining.");
            return false;
        });
    }

    private static void check(Player player, Item sceptre) {
        int etherAmount = AttributeExtensions.getCharges(sceptre) - ACTIVATION_AMOUNT;
        player.sendMessage("Your sceptre has " + (etherAmount) + " charge" + (etherAmount <= 1 ? "" : "s") + " left powering it.");
    }

    private static void charge(Player player, Item sceptre, Item etherItem) {
        int etherAmount = AttributeExtensions.getCharges(sceptre);
        int allowedAmount = MAX_AMOUNT - etherAmount;
        if (allowedAmount == 0) {
            player.sendMessage("Thammaron's sceptre can't hold any more revenant ether.");
            return;
        }
        if(etherAmount == 0 && etherItem.getAmount() < ACTIVATION_AMOUNT) {
            player.sendMessage("You require at least 1000 revenant ether to activate this weapon.");
            return;
        }
        int addAmount = Math.min(allowedAmount, etherItem.getAmount());
        etherItem.incrementAmount(-addAmount);
        AttributeExtensions.addCharges(sceptre, addAmount);
        etherAmount = AttributeExtensions.getCharges(sceptre);
        sceptre.setId(CHARGED);
        if(etherAmount == 0)
            player.sendMessage("You use 1000 ether to activate the weapon.");
        player.sendMessage("You add a further " + (etherAmount == 0 ? addAmount - ACTIVATION_AMOUNT: addAmount)
                + " revenant ether to your weapon giving it a total of " + (sceptre.getAttributeInt(AttributeTypes.CHARGES) - ACTIVATION_AMOUNT) + " charges");
    }

    private static void uncharge(Player player, Item sceptre) {
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge this weapon, all the revenant ether will be returned to your inventory.", sceptre, () -> {
            int etherAmount = AttributeExtensions.getCharges(sceptre);
            player.getInventory().add(REVENANT_ETHER, etherAmount);
            AttributeExtensions.setCharges(sceptre, 0);
            sceptre.setId(UNCHARGED);
        }));
    }

    public static boolean consumeCharge(Player player, Item item) {
        if (player.getRelicManager().hasRelicEnalbed(Relic.ARCHMAGE) && Random.rollDie(2)) return true;
        int charges = AttributeExtensions.getCharges(item);
        if(charges <= 1000) {
            player.sendMessage(Color.DARK_RED.wrap("Your weapon has run out of revenant ether!"));
            return false;
        }
        AttributeExtensions.deincrementCharges(item, 1);
        return true;
    }

    private static void dismantle(Player player, Item item) {
        player.dialogue(new YesNoDialogue("Dismantle the sceptre?", "Dismantling the sceptre will give you 7,500 revenant ether.", item, () -> {
            item.remove();
            player.getInventory().add(Items.REVENANT_ETHER, 7500);
        }));
    }
}
