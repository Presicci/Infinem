package io.ruin.model.item.actions.impl.chargable;

import io.ruin.model.combat.Hit;
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
import lombok.AllArgsConstructor;

import java.util.function.BiPredicate;

@AllArgsConstructor
public enum ViggorasChainmace {
    VIGGORAS_CHAINMACE(22545, 22542),
    URSINE_CHAINMACE(27660, 27657);

    private final int chargedId;
    private final int unchargedId;

    private static final int MAX_AMOUNT = 16000;
    private static final int REVENANT_ETHER = 21820;
    private static final int ACTIVATION_AMOUNT = 1000;

    static {
        for (ViggorasChainmace chainmace : ViggorasChainmace.values()) {
            ItemAction.registerInventory(chainmace.chargedId, "check", ViggorasChainmace::check);
            ItemAction.registerEquipment(chainmace.chargedId, "check", ViggorasChainmace::check);
            ItemAction.registerInventory(chainmace.chargedId, "uncharge", (player, item) -> uncharge(player, item, chainmace));
            ItemAction.registerInventory(chainmace.unchargedId, "dismantle", ViggorasChainmace::dismantle);
            ItemItemAction.register(chainmace.chargedId, REVENANT_ETHER, (player, primary, secondary) -> charge(player, primary, secondary, chainmace));
            ItemItemAction.register(chainmace.unchargedId, REVENANT_ETHER, (player, primary, secondary) -> charge(player, primary, secondary, chainmace));
            ItemDefinition.get(chainmace.chargedId).addPreTargetDefendListener(ViggorasChainmace::boost);
            ItemDefinition.get(chainmace.chargedId).custom_values.put("CAN_ATTACK", (BiPredicate<Player, Item>) ViggorasChainmace::canAttack);
            ItemDefinition.get(chainmace.unchargedId).custom_values.put("CAN_ATTACK", (BiPredicate<Player, Item>) (player, item) -> {
                player.sendMessage("Your chainmace has no charges remaining.");
                return false;
            });
        }
    }

    private static void boost(Player player, Item item, Hit hit, Entity target) {
        if (hit.attackStyle != null && hit.attackStyle.isMelee() && target.npc != null && player.wildernessLevel > 0) {
            hit.boostAttack(0.5);               // 50% accuracy increase
            hit.boostDamage(0.5);    // 50% damage increase
        }
        consumeCharge(player, item);
    }

    private static boolean canAttack(Player player, Item item) {
        int currentCharges = AttributeExtensions.getCharges(item);
        if (currentCharges > 1000) {
            return true;
        } else {
            player.sendMessage("Your chainmace has no charges remaining.");
            return false;
        }
    }

    private static void check(Player player, Item sceptre) {
        int etherAmount = AttributeExtensions.getCharges(sceptre) - ACTIVATION_AMOUNT;
        player.sendMessage("Your chainmace has " + (etherAmount) + " charge" + (etherAmount <= 1 ? "" : "s") + " left powering it.");
    }

    private static void charge(Player player, Item chainmace, Item etherItem, ViggorasChainmace chainmaceType) {
        int etherAmount = AttributeExtensions.getCharges(chainmace);
        int allowedAmount = MAX_AMOUNT - etherAmount;
        if (allowedAmount == 0) {
            player.sendMessage(chainmace.getDef().name + " can't hold any more revenant ether.");
            return;
        }
        if(etherAmount == 0 && etherItem.getAmount() < ACTIVATION_AMOUNT) {
            player.sendMessage("You require at least 1,000 revenant ether to activate this weapon.");
            return;
        }
        int addAmount = Math.min(allowedAmount, etherItem.getAmount());
        etherItem.incrementAmount(-addAmount);
        AttributeExtensions.addCharges(chainmace, addAmount);
        etherAmount = AttributeExtensions.getCharges(chainmace);
        chainmace.setId(chainmaceType.chargedId);
        if(etherAmount == 0)
            player.sendMessage("You use 1,000 ether to activate the weapon.");
        player.sendMessage("You add a further " + (etherAmount == 0 ? addAmount - ACTIVATION_AMOUNT: addAmount)
                + " revenant ether to your weapon giving it a total of " + (AttributeExtensions.getCharges(chainmace) - ACTIVATION_AMOUNT) + " charges.");
    }

    private static void uncharge(Player player, Item chainmace, ViggorasChainmace chainmaceType) {
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge this weapon, all the revenant ether will be returned to your inventory.", chainmace, () -> {
            if (!player.getInventory().contains(chainmace.getId(), chainmace.getAmount(), false, true))
                return;
            int etherAmount = AttributeExtensions.getCharges(chainmace);
            if (etherAmount > 0)
                player.getInventory().addOrDrop(REVENANT_ETHER, etherAmount);
            AttributeExtensions.setCharges(chainmace, 0);
            chainmace.setId(chainmaceType.unchargedId);
        }));
    }

    public static boolean consumeCharge(Player player, Item item) {
        if(AttributeExtensions.getCharges(item) <= 1000) {
            player.sendMessage(Color.DARK_RED.wrap("Your weapon has run out of revenant ether!"));
            return false;
        }
        AttributeExtensions.deincrementCharges(item, 1);
        return true;
    }

    private static void dismantle(Player player, Item item) {
        player.dialogue(new YesNoDialogue("Dismantle the chainmace?", "Dismantling the chainmace will give you 7,500 revenant ether.", item, () -> {
            item.remove();
            player.getInventory().add(Items.REVENANT_ETHER, 7500);
        }));
    }
}
