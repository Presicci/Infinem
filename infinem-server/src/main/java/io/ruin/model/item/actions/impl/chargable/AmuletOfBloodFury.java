package io.ruin.model.item.actions.impl.chargable;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.utility.Color;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/13/2024
 */
public class AmuletOfBloodFury {

    private static final int BLOOD_SHARD = 24777, AMULET_OF_BLOOD_FURY = 24780;

    private static void create(Player player, Item shard, Item amulet) {
        player.dialogue(
                new ItemDialogue().two(Items.AMULET_OF_FURY, BLOOD_SHARD, Color.RED.wrap("Warning!")
                        + "<br>The Amulet of fury will consume your Blood shard to make the Amulet of blood fury and you won't be able to get it back."),
                new ItemDialogue().two(Items.AMULET_OF_FURY, BLOOD_SHARD, "The Amulet of blood fury is untradeable."),
                new OptionsDialogue(Color.RED.wrap("Are you sure you wish to do this?"),
                        new Option("Yes, combine the Blood shard with the Amulet of fury.", () -> {
                            player.animate(713);
                            shard.remove();
                            amulet.setId(AMULET_OF_BLOOD_FURY);
                            amulet.setCharges(10000);
                            player.dialogue(new ItemDialogue().one(AMULET_OF_BLOOD_FURY, "You have successfully created an Amulet of blood fury."));
                        }),
                        new Option("No, I'll keep my Blood shard.")
                )
        );
    }

    private static void charge(Player player, Item shard, Item amulet) {
        int currentCharges = amulet.getCharges();
        if (currentCharges >= 3000) {
            player.sendMessage("Your amulet is already fully charged.");
            return;
        }
        player.dialogue(
                new ItemDialogue().two(Items.AMULET_OF_FURY, BLOOD_SHARD, Color.RED.wrap("Warning!")
                        + "<br>The Amulet of blood fury will consume your Blood shard to gain 1,000 charges and you won't be able to get it back."),
                new OptionsDialogue(Color.RED.wrap("Are you sure you wish to do this?"),
                        new Option("Yes, combine the Blood shard with the amulet.", () -> {
                            player.animate(713);
                            shard.remove();
                            int newCharges = currentCharges + 1000;
                            amulet.setCharges(newCharges);
                            player.sendMessage("You add 1,000 charges to your blood amulet of fury. It now has " + newCharges + " charges.");
                        }),
                        new Option("No, I'll keep my Blood shard.")
                )
        );
    }

    private static void check(Player player, Item amulet) {
        player.sendMessage("Your blood amulet of fury has " + amulet.getCharges() + " charges left.");
    }

    private static void revert(Player player, Item amulet) {
        player.dialogue(
                new ItemDialogue().one(AMULET_OF_BLOOD_FURY, Color.RED.wrap("Warning!")
                        + "<br>Are you sure you want to revert your amulet of blood fury? The blood shard and all charges will be lost."),
                new OptionsDialogue(Color.RED.wrap("Are you sure you wish to do this?"),
                        new Option("Yes, revert the Amulet of blood fury to an Amulet of fury.", () -> {
                            player.animate(713);
                            amulet.setId(Items.AMULET_OF_FURY);
                            amulet.removeCharges();
                            player.dialogue(new ItemDialogue().one(Items.AMULET_OF_FURY, "You revert your blood amulet of fury to an amulet of fury."));
                        }),
                        new Option("No, I'll keep my amulet.")
                )
        );
    }

    public static boolean test(Player player) {
        Item amulet = player.getEquipment().get(Equipment.SLOT_AMULET);
        if (amulet == null || amulet.getId() != AMULET_OF_BLOOD_FURY) return false;
        int charges = amulet.getCharges();
        if (charges <= 0) return false;
        amulet.setCharges(charges - 1);
        if (charges - 1 <= 0) {
            player.sendMessage(Color.RED.wrap("Your amulet of blood fury has run out of charges."));
        }
        return true;
    }

    static {
        ItemAction.registerInventory(AMULET_OF_BLOOD_FURY, "revert", AmuletOfBloodFury::revert);
        ItemAction.registerInventory(AMULET_OF_BLOOD_FURY, "check", AmuletOfBloodFury::check);
        ItemAction.registerEquipment(AMULET_OF_BLOOD_FURY, "check", AmuletOfBloodFury::check);
        ItemItemAction.register(BLOOD_SHARD, Items.AMULET_OF_FURY, AmuletOfBloodFury::create);
        ItemItemAction.register(BLOOD_SHARD, AMULET_OF_BLOOD_FURY, AmuletOfBloodFury::charge);
    }
}
