package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.Entity;
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
import lombok.AllArgsConstructor;

import java.util.function.BiPredicate;

@AllArgsConstructor
public enum ThammaronsSceptre {
    THAMMARONS_SCEPTRE(22555, 27788, 22552, 27785),
    ACCURSED_SPECTRE(27665, 27679, 27662, 27676);

    private final int chargedId;
    private final int attunedChargedId;
    private final int unchargedId;
    private final int attunedUnchargedId;

    private static final int MAX_AMOUNT = 16000;
    private static final int REVENANT_ETHER = 21820;
    private static final int ACTIVATION_AMOUNT = 1000;

    static {
        for (ThammaronsSceptre sceptre : values()) {
            ItemItemAction.register(sceptre.chargedId, REVENANT_ETHER, (player, primary, secondary) -> charge(player, primary, secondary, sceptre, false));
            ItemItemAction.register(sceptre.unchargedId, REVENANT_ETHER, (player, primary, secondary) -> charge(player, primary, secondary, sceptre, false));
            ItemItemAction.register(sceptre.attunedChargedId, REVENANT_ETHER, (player, primary, secondary) -> charge(player, primary, secondary, sceptre, true));
            ItemItemAction.register(sceptre.attunedUnchargedId, REVENANT_ETHER, (player, primary, secondary) -> charge(player, primary, secondary, sceptre, true));

            ItemAction.registerInventory(sceptre.chargedId, "check", ThammaronsSceptre::check);
            ItemAction.registerInventory(sceptre.attunedChargedId, "check", ThammaronsSceptre::check);
            ItemAction.registerEquipment(sceptre.chargedId, "check", ThammaronsSceptre::check);
            ItemAction.registerEquipment(sceptre.attunedChargedId, "check", ThammaronsSceptre::check);

            ItemAction.registerInventory(sceptre.chargedId, "swap", (player, item) -> swap(item, sceptre, false, true));
            ItemAction.registerInventory(sceptre.unchargedId, "swap", (player, item) -> swap(item, sceptre, false, false));
            ItemAction.registerInventory(sceptre.attunedChargedId, "swap", (player, item) -> swap(item, sceptre, true, true));
            ItemAction.registerInventory(sceptre.attunedUnchargedId, "swap", (player, item) -> swap(item, sceptre, true, false));
            ItemAction.registerEquipment(sceptre.chargedId, "swap", (player, item) -> swap(item, sceptre, false, true));
            ItemAction.registerEquipment(sceptre.unchargedId, "swap", (player, item) -> swap(item, sceptre, false, false));
            ItemAction.registerEquipment(sceptre.attunedChargedId, "swap", (player, item) -> swap(item, sceptre, true, true));
            ItemAction.registerEquipment(sceptre.attunedUnchargedId, "swap", (player, item) -> swap(item, sceptre, true, false));

            ItemDefinition.get(sceptre.chargedId).addPreTargetDefendListener(ThammaronsSceptre::boost);
            ItemDefinition.get(sceptre.attunedChargedId).addPreTargetDefendListener(ThammaronsSceptre::boost);

            ItemAction.registerInventory(sceptre.chargedId, "uncharge", (player, item) -> uncharge(player, item, sceptre, false));
            ItemAction.registerInventory(sceptre.attunedChargedId, "uncharge", (player, item) -> uncharge(player, item, sceptre, true));

            ItemAction.registerInventory(sceptre.unchargedId, "dismantle", ThammaronsSceptre::dismantle);
            ItemAction.registerInventory(sceptre.attunedUnchargedId, "dismantle", ThammaronsSceptre::dismantle);

            ItemDefinition.get(sceptre.chargedId).custom_values.put("CAN_ATTACK", (BiPredicate<Player, Item>) ThammaronsSceptre::canAttack);
            ItemDefinition.get(sceptre.attunedChargedId).custom_values.put("CAN_ATTACK", (BiPredicate<Player, Item>) ThammaronsSceptre::canAttack);
            ItemDefinition.get(sceptre.unchargedId).custom_values.put("CAN_ATTACK", (BiPredicate<Player, Item>) (player, item) -> {
                player.sendMessage("Your Thammaron's sceptre has no charges remaining.");
                return false;
            });
            ItemDefinition.get(sceptre.attunedUnchargedId).custom_values.put("CAN_ATTACK", (BiPredicate<Player, Item>) (player, item) -> {
                player.sendMessage("Your Thammaron's sceptre has no charges remaining.");
                return false;
            });
        }
    }

    private static void swap(Item item, ThammaronsSceptre sceptreType, boolean attuned, boolean charged) {
        item.setId(attuned ? (charged ? sceptreType.chargedId : sceptreType.unchargedId) : (charged ? sceptreType.attunedChargedId : sceptreType.attunedUnchargedId));
    }

    private static void boost(Player player, Item item, Hit hit, Entity target) {
        if (hit.attackStyle != null && hit.attackStyle.isMagic() && target.npc != null && player.wildernessLevel > 0) {
            hit.boostAttack(0.5);               // 50% accuracy increase
            hit.boostDamage(0.5);    // 50% damage increase
        }
        if (hit.attackWeapon != null) consumeCharge(player, item);
    }

    private static boolean canAttack(Player player, Item item) {
        int currentCharges = AttributeExtensions.getCharges(item);
        if (currentCharges > 1000) {
            return true;
        } else {
            player.sendMessage("Your Thammaron's sceptre has no charges remaining.");
            return false;
        }
    }

    private static void check(Player player, Item sceptre) {
        int etherAmount = AttributeExtensions.getCharges(sceptre) - ACTIVATION_AMOUNT;
        player.sendMessage("Your sceptre has " + (etherAmount) + " charge" + (etherAmount <= 1 ? "" : "s") + " left powering it.");
    }

    private static void charge(Player player, Item sceptre, Item etherItem, ThammaronsSceptre sceptreType, boolean attuned) {
        int etherAmount = AttributeExtensions.getCharges(sceptre);
        int allowedAmount = MAX_AMOUNT - etherAmount;
        if (allowedAmount == 0) {
            player.sendMessage(sceptre.getDef().name + " can't hold any more revenant ether.");
            return;
        }
        if(etherAmount == 0 && etherItem.getAmount() < ACTIVATION_AMOUNT) {
            player.sendMessage("You require at least 1,000 revenant ether to activate this weapon.");
            return;
        }
        int addAmount = Math.min(allowedAmount, etherItem.getAmount());
        etherItem.incrementAmount(-addAmount);
        AttributeExtensions.addCharges(sceptre, addAmount);
        etherAmount = AttributeExtensions.getCharges(sceptre);
        sceptre.setId(attuned ? sceptreType.attunedChargedId : sceptreType.chargedId);
        if(etherAmount == 0)
            player.sendMessage("You use 1,000 ether to activate the weapon.");
        player.sendMessage("You add a further " + (etherAmount == 0 ? addAmount - ACTIVATION_AMOUNT: addAmount)
                + " revenant ether to your weapon giving it a total of " + (sceptre.getAttributeInt(AttributeTypes.CHARGES) - ACTIVATION_AMOUNT) + " charges.");
    }

    private static void uncharge(Player player, Item sceptre, ThammaronsSceptre sceptreType, boolean attuned) {
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge this weapon, all the revenant ether will be returned to your inventory.", sceptre, () -> {
            if (!player.getInventory().contains(sceptre.getId(), sceptre.getAmount(), false, true))
                return;
            int etherAmount = AttributeExtensions.getCharges(sceptre);
            if (etherAmount > 0)
                player.getInventory().add(REVENANT_ETHER, etherAmount);
            AttributeExtensions.setCharges(sceptre, 0);
            sceptre.setId(attuned ? sceptreType.attunedUnchargedId : sceptreType.unchargedId);
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
            if (!player.getInventory().contains(item))
                return;
            item.remove();
            player.getInventory().add(Items.REVENANT_ETHER, 7500);
            player.closeDialogue();
        }));
    }
}
