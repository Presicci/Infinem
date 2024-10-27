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
import lombok.AllArgsConstructor;

import java.util.function.BiPredicate;

@AllArgsConstructor
public enum CrawsBow {
    CRAWS_BOW(22550, 22547),
    WEBWEAVER_BOW(27655, 27652);

    private final int chargedId;
    private final int unchargedId;

    private static final int MAX_AMOUNT = 16000;
    private static final int REVENANT_ETHER = 21820;
    private static final int ACTIVATION_AMOUNT = 1000;

    static {
        for (CrawsBow bow : CrawsBow.values()) {
            ItemAction.registerInventory(bow.chargedId, "check", CrawsBow::check);
            ItemAction.registerEquipment(bow.chargedId, "check", CrawsBow::check);
            ItemAction.registerInventory(bow.chargedId, "uncharge", (player, item) -> uncharge(player, item, bow));
            ItemAction.registerInventory(bow.unchargedId, "dismantle", CrawsBow::dismantle);
            ItemItemAction.register(bow.chargedId, REVENANT_ETHER, (player, primary, secondary) -> charge(player, primary, secondary, bow));
            ItemItemAction.register(bow.unchargedId, REVENANT_ETHER, (player, primary, secondary) -> charge(player, primary, secondary, bow));
            ItemDefinition.get(bow.chargedId).addPreTargetDefendListener(CrawsBow::boost);
            ItemDefinition.get(bow.chargedId).custom_values.put("CAN_ATTACK", (BiPredicate<Player, Item>) CrawsBow::canAttack);
            ItemDefinition.get(bow.unchargedId).custom_values.put("CAN_ATTACK", (BiPredicate<Player, Item>) (player, item) -> {
                player.sendMessage("Your bow has no charges remaining.");
                return false;
            });
        }
    }

    private static void boost(Player player, Item item, Hit hit, Entity target) {
        if (hit.attackStyle != null && hit.attackStyle.isRanged() && target.npc != null && player.wildernessLevel > 0) {
            hit.boostAttack(0.5);               // 50% accuracy increase
            hit.boostDamage(0.5);               // 50% damage increase
        }
        if (!hit.keepCharges) consumeCharge(player, item);
    }

    private static boolean canAttack(Player player, Item item) {
        int currentCharges = AttributeExtensions.getCharges(item);
        if (currentCharges > 1000) {
            return true;
        } else {
            player.sendMessage("Your bow has no charges remaining.");
            return false;
        }
    }

    private static void check(Player player, Item bow) {
        int etherAmount = AttributeExtensions.getCharges(bow) - ACTIVATION_AMOUNT;
        player.sendMessage("Your bow has " + (etherAmount) + " charge" + (etherAmount <= 1 ? "" : "s") + " left powering it.");
    }

    private static void charge(Player player, Item crawsBow, Item etherItem, CrawsBow crawsBowType) {
        int etherAmount = AttributeExtensions.getCharges(crawsBow);
        int allowedAmount = MAX_AMOUNT - etherAmount;
        if (allowedAmount == 0) {
            player.sendMessage(crawsBow.getDef().name + " can't hold any more revenant ether.");
            return;
        }
        if(etherAmount == 0 && etherItem.getAmount() < ACTIVATION_AMOUNT) {
            player.sendMessage("You require at least 1,000 revenant ether to activate this weapon.");
            return;
        }
        int addAmount = Math.min(allowedAmount, etherItem.getAmount());
        etherItem.incrementAmount(-addAmount);
        AttributeExtensions.addCharges(crawsBow, addAmount);
        etherAmount = AttributeExtensions.getCharges(crawsBow);
        crawsBow.setId(crawsBowType.chargedId);
        if(etherAmount == 0)
            player.sendMessage("You use 1,000 ether to activate the weapon.");
        player.sendMessage("You add a further " + (etherAmount == 0 ? addAmount - ACTIVATION_AMOUNT: addAmount)
                + " revenant ether to your weapon giving it a total of " + (AttributeExtensions.getCharges(crawsBow) - ACTIVATION_AMOUNT) + " charges.");
    }

    private static void uncharge(Player player, Item crawsBow, CrawsBow bow) {
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge this weapon, all the revenant ether will be returned to your inventory.", crawsBow, () -> {
            if (!player.getInventory().contains(crawsBow.getId(), crawsBow.getAmount(), false, true))
                return;
            int etherAmount = AttributeExtensions.getCharges(crawsBow);
            if (etherAmount > 0)
                player.getInventory().addOrDrop(REVENANT_ETHER, etherAmount);
            AttributeExtensions.setCharges(crawsBow, 0);
            crawsBow.setId(bow.unchargedId);
        }));
    }

    public static boolean consumeCharge(Player player, Item item) {
        if (player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) && Random.rollDie(2)) return true;
        int currentCharges = AttributeExtensions.getCharges(item);
        if (currentCharges <= 1000) {
            player.sendMessage(Color.DARK_RED.wrap("Your weapon has run out of revenant ether!"));
            return false;
        }
        AttributeExtensions.deincrementCharges(item, 1);
        return true;
    }

    private static void dismantle(Player player, Item item) {
        player.dialogue(new YesNoDialogue("Dismantle the bow?", "Dismantling the bow will give you 7,500 revenant ether.", item, () -> {
            item.remove();
            player.getInventory().add(Items.REVENANT_ETHER, 7500);
        }));
    }
}

