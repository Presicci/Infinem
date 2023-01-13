package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.containers.Equipment;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/12/2023
 */
public class CelestialRing {

    public static final int UNCHARGED_RING = 25539;
    public static final int CHARGED_RING = 25541;
    public static final int UNCHARGED_SIGNET = 25543;
    public static final int CHARGED_SIGNET = 25545;
    public static final int STARDUST = 25527;
    private static final int MAX_CHARGES = 10000;

    private static void charge(Player player, Item ring) {
        int currentCharges = AttributeExtensions.getCharges(ring);
        if (currentCharges >= MAX_CHARGES) {
            player.sendMessage("The ring can't hold any more charges.");
            return;
        }
        int chargesInInventory = player.getInventory().getAmount(STARDUST);
        if (chargesInInventory == 0) {
            player.sendMessage("You require stardust to charge the ring.");
            return;
        }
        int chargesToAdd = Math.min(chargesInInventory, MAX_CHARGES - currentCharges);
        player.integerInput("How many charges do you want to add? (Up to " + NumberUtils.formatNumber(chargesToAdd) + ")", (input) -> {
            int allowed = MAX_CHARGES - currentCharges;
            int removed = player.getInventory().remove(STARDUST, Math.min(allowed, input));
            AttributeExtensions.addCharges(ring, removed);
            ring.setId(ring.getId() < 25543 ? CHARGED_RING : CHARGED_SIGNET);
            check(player, ring);
        });
    }

    private static void check(Player player, Item item) {
        player.sendMessage("Your Celestial " + (item.getId() == CHARGED_RING ? "ring" : "signet") + " currently has " + NumberUtils.formatNumber(item.getAttributeInt(AttributeTypes.CHARGES)) + " charges.");
    }

    public static boolean wearingChargedRing(Player player) {
        Item ring = player.getEquipment().get(Equipment.SLOT_RING);
        if (ring.getId() == CHARGED_RING || ring.getId() == CHARGED_SIGNET) {
            return AttributeExtensions.getCharges(ring) > 0;
        }
        return false;
    }

    public static void removeChargeIfEquipped(Player player) {
        Item ring = player.getEquipment().get(Equipment.SLOT_RING);
        if (ring.getId() == CHARGED_RING || ring.getId() == CHARGED_SIGNET) {
            AttributeExtensions.deincrementCharges(ring, 1);
        }
    }

    static {
        ItemAction.registerInventory(CHARGED_RING, "check", CelestialRing::check);
        ItemAction.registerInventory(CHARGED_RING, "uncharge", CelestialRing::uncharge);
        ItemAction.registerInventory(CHARGED_RING, "charge", CelestialRing::charge);
        ItemAction.registerInventory(UNCHARGED_RING, "charge", CelestialRing::charge);
        ItemAction.registerEquipment(CHARGED_RING, "check", CelestialRing::check);
        ItemItemAction.register(UNCHARGED_RING, STARDUST, ((player, primary, secondary) -> charge(player, primary)));
        ItemItemAction.register(CHARGED_RING, STARDUST, ((player, primary, secondary) -> charge(player, primary)));

        ItemAction.registerInventory(CHARGED_SIGNET, "check", CelestialRing::check);
        ItemAction.registerInventory(CHARGED_SIGNET, "uncharge", CelestialRing::uncharge);
        ItemAction.registerInventory(CHARGED_SIGNET, "charge", CelestialRing::charge);
        ItemAction.registerInventory(UNCHARGED_SIGNET, "charge", CelestialRing::charge);
        ItemAction.registerEquipment(CHARGED_SIGNET, "check", CelestialRing::check);
        ItemItemAction.register(UNCHARGED_SIGNET, STARDUST, ((player, primary, secondary) -> charge(player, primary)));
        ItemItemAction.register(CHARGED_SIGNET, STARDUST, ((player, primary, secondary) -> charge(player, primary)));
    }

    private static void uncharge(Player player, Item ring) {
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge this ring, all the stardust will be returned to your inventory.", ring, () -> {
            int amount = AttributeExtensions.getCharges(ring);
            player.getInventory().add(STARDUST, amount);
            AttributeExtensions.setCharges(ring, 0);
            ring.setId(ring.getId() < 25543 ? UNCHARGED_RING : UNCHARGED_SIGNET);
        }));
    }
}
