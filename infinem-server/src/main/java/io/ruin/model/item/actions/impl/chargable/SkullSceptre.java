package io.ruin.model.item.actions.impl.chargable;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/26/2021
 */
public class SkullSceptre {

    private static final int MAX_CHARGES = 10;

    private static final int[] sceptreIDS = { Items.SKULL_SCEPTRE, 21276 };

    private static void invoke(Player player, Item item, int itemId) {
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
    }

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
                invoke(player, item, itemId);
            });
            ItemAction.registerInventory(itemId, "divine", (player, item) -> {
                player.sendMessage("Your skull sceptre has " + item.getAttributeInt(AttributeTypes.CHARGES) + " charges left.");
            });
            ItemAction.registerEquipment(itemId, "invoke", (player, item) -> {
                invoke(player, item, itemId);
            });
            ItemAction.registerEquipment(itemId, "divine", (player, item) -> {
                player.sendMessage("Your skull sceptre has " + item.getAttributeInt(AttributeTypes.CHARGES) + " charges left.");
            });
        }

        /*  Sceptre charging    */
        for (SceptrePart part : SceptrePart.values()) {
            ItemItemAction.register(Tool.CHISEL, part.itemId, (player, item, item2) -> {
                player.getInventory().remove(item2);
                player.getInventory().add(25139, part.charges);
                player.sendFilteredMessage("You break the piece down into bone fragments.");
            });
            ItemItemAction.register(21276, part.itemId, (player, item, item2) -> {
                int charges = item.getAttributeInt(AttributeTypes.CHARGES);
                if (charges >= MAX_CHARGES) {
                    player.sendMessage("The sceptre is already fully charged.");
                    return;
                }
                player.getInventory().remove(item2);
                item.putAttribute(AttributeTypes.CHARGES, charges + part.charges);
                player.sendFilteredMessage("You add " + part.charges + " charges to your sceptre.");
            });
        }
        ItemItemAction.register(21276, 25139, (player, item, item2) -> {
            int charges = item.getAttributeInt(AttributeTypes.CHARGES);
            if (charges >= MAX_CHARGES) {
                player.sendMessage("The sceptre is already fully charged.");
                return;
            }
            int chargesToAdd = Math.min(player.getInventory().getAmount(25139), MAX_CHARGES - charges);
            player.getInventory().remove(item2.getId(), chargesToAdd);
            item.putAttribute(AttributeTypes.CHARGES, charges + chargesToAdd);
            player.sendFilteredMessage("You add " + chargesToAdd + " charges to your sceptre.");
        });
    }

    @AllArgsConstructor
    public enum SceptrePart {
        SKULL_LEFT(Items.LEFT_SKULL_HALF, 5),
        SKULL_RIGHT(Items.RIGHT_SKULL_HALF, 3),
        BOTTOM(Items.BOTTOM_OF_SCEPTRE, 3),
        TOP(Items.TOP_OF_SCEPTRE, 3),
        SKULL(Items.STRANGE_SKULL, 8),
        SCEPTRE(Items.RUNED_SCEPTRE, 6),
        SKULL_SCEPTRE(Items.SKULL_SCEPTRE, 14);

        public final int itemId, charges;
    }
}
