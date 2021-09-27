package io.ruin.model.item.actions.impl.chargable;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/26/2021
 */
public class SkullSceptre {

    private static final int MAX_CHARGES = 10;

    private static final int[] sceptreIDS = { Items.SKULL_SCEPTRE, 21276 };

    static {
        ItemItemAction.register(Items.LEFT_SKULL_HALF, Items.RIGHT_SKULL_HALF, (player, item1, item2) -> {
            item1.setId(Items.STRANGE_SKULL);
            player.getInventory().remove(item2);
            player.dialogue(new ItemDialogue().one(Items.STRANGE_SKULL, "The two halves of the skull fit perfectly, they appear to have a fixing " +
                    "point, perhaps they are to be mounted on something?"));
        });
        ItemItemAction.register(Items.BOTTOM_OF_SCEPTRE, Items.TOP_OF_SCEPTRE, (player, item1, item2) -> {
            item1.setId(Items.RUNED_SCEPTRE);
            player.getInventory().remove(item2);
            player.dialogue(new ItemDialogue().one(Items.RUNED_SCEPTRE, "The two halves of the Sceptre fit perfectly. The Sceptre appears to be " +
                    "designed to have something on top."));
        });
        ItemItemAction.register(Items.RUNED_SCEPTRE, Items.STRANGE_SKULL, (player, item1, item2) -> {
            item1.setId(Items.SKULL_SCEPTRE);
            item1.putAttribute(AttributeTypes.CHARGES, MAX_CHARGES);
            player.getInventory().remove(item2);
            player.dialogue(new ItemDialogue().one(Items.SKULL_SCEPTRE, "The skull fits perfectly atop the sceptre. You feel there is great " +
                    "magical power at work here, and that the sceptre has " + MAX_CHARGES + " charges."));
        });

        for (int itemId : sceptreIDS) {
            ItemAction.registerInventory(itemId, "invoke", (player, item) -> {
                int currentCharges = item.getAttributeInt(AttributeTypes.CHARGES);
                if (currentCharges > 0) {
                    ModernTeleport.teleport(player, 3081, 3421, 0);
                    item.putAttribute(AttributeTypes.CHARGES, currentCharges - 1);
                    if (item.getAttributeInt(AttributeTypes.CHARGES) <= 0) {
                        if (itemId == Items.SKULL_SCEPTRE) {
                            player.getInventory().remove(item);
                            player.sendMessage("<col=7F00FF>Your skull sceptre has run out of charges and crumbles to dust.");
                        } else {
                            player.sendMessage("<col=7F00FF>Your skull sceptre has run out of charges, use bone fragments on it to recharge it.");
                        }
                    }
                } else {
                    player.sendMessage("Your skull sceptre has no charges left, use bone fragments on it to recharge it.");
                }
            });
            ItemAction.registerInventory(itemId, "divine", (player, item) -> {
                player.sendMessage("Your skull sceptre has " + item.getAttributeInt(AttributeTypes.CHARGES) + " charges left.");
            });
        }
    }
}
