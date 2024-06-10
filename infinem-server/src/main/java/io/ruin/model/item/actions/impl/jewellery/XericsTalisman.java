package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/10/2024
 */
public class XericsTalisman {

    private static final int MAX_CHARGES = 1000;
    public static final String HONOUR_KEY = "XERICS_HONOUR";

    private static void charge(Player player, Item talisman, Item fangs) {
        int currentCharges = AttributeExtensions.getCharges(talisman);
        int addAmt = Math.min(MAX_CHARGES - currentCharges, fangs.getAmount());
        if (addAmt == 0) {
            player.sendMessage("The talisman is already holding the maximum amount of charges.");
            return;
        }
        if (talisman.getId() == Items.XERICS_TALISMAN_INERT) talisman.setId(Items.XERICS_TALISMAN);
        fangs.remove(addAmt);
        int newAmt = AttributeExtensions.incrementCharges(talisman, addAmt);
        player.sendMessage("You add " + addAmt + " charges to the talisman. It now has " + newAmt + " charges.");
    }

    private static void uncharge(Player player, Item item) {
        player.dialogue(new YesNoDialogue("Uncharge the talisman?", "Uncharging the talisman will return all fangs used to charge it.", item, () -> {
            int charges = AttributeExtensions.getCharges(item);
            if (!player.getInventory().hasRoomFor(Items.LIZARDMAN_FANG, charges)) {
                player.dialogue(new MessageDialogue("You don't have enough inventory space to hold the lizardman fangs."));
                return;
            }
            AttributeExtensions.clearCharges(item);
            item.setId(Items.XERICS_TALISMAN_INERT);
            player.getInventory().add(Items.LIZARDMAN_FANG, charges);
            player.sendMessage("You uncharge the talisman.");
        }));
    }

    private static void check(Player player, Item item) {
        int charges = AttributeExtensions.getCharges(item);
        player.sendMessage("Your Xeric's talisman has " + charges + " charges.");
    }

    private static void dismantle(Player player, Item item) {
        player.dialogue(new YesNoDialogue("Dismantle the talisman?", "Dismantling the talisman will give you 100 lizardman fangs.", item, () -> {
            item.remove();
            player.getInventory().add(Items.LIZARDMAN_FANG, 100);
        }));
    }

    private static void teleport(Player player, Item item, JewelleryTeleportBounds teleportBounds) {
        int charges = AttributeExtensions.getCharges(item);
        if (charges <= 0) {
            item.setId(Items.XERICS_TALISMAN_INERT);
            player.sendMessage("Your talisman is out of charges.");
            return;
        }
        if (teleportBounds == JewelleryTeleportBounds.XERICS_HONOUR && !player.hasAttribute(HONOUR_KEY)) {
            player.sendMessage("You must first find an Ancient tablet from Xeric's Chamber to use this teleport.");
            return;
        }
        int newAmt = AttributeExtensions.deincrementCharges(item, 1);
        if (newAmt <= 0) {
            player.sendMessage("Your talisman has run out of charges.");
            item.setId(Items.XERICS_TALISMAN_INERT);
        }
        ModernTeleport.teleport(player, teleportBounds.getBounds());
    }

    private static void combineAncientTablet(Player player, Item ancientTablet) {
        ancientTablet.remove();
        player.putAttribute(HONOUR_KEY, 1);
        player.dialogue(new MessageDialogue("As the talisman and tablet are held together, a surge of power connects the two. After a brief moment, " +
                "the tablet crumbles, and the talisman feels somehow different."));
    }

    static {
        // Charged
        ItemAction.registerInventory(Items.XERICS_TALISMAN, "rub", (player, item) -> {
            List<Option> optionList = new ArrayList<>();
            optionList.add(new Option("Xeric's Lookout", () -> teleport(player, item, JewelleryTeleportBounds.XERICS_LOOKOUT)));
            optionList.add(new Option("Xeric's Glade", () -> teleport(player, item, JewelleryTeleportBounds.XERICS_GLADE)));
            optionList.add(new Option("Xeric's Inferno", () -> teleport(player, item, JewelleryTeleportBounds.XERICS_INFERNO)));
            optionList.add(new Option("Xeric's Heart", () -> teleport(player, item, JewelleryTeleportBounds.XERICS_HEART)));
            if (player.hasAttribute(HONOUR_KEY)) optionList.add(new Option("Xeric's Honour", () -> teleport(player, item, JewelleryTeleportBounds.XERICS_HONOUR)));
            player.dialogue(new OptionsDialogue(optionList));
        });
        ItemAction.registerInventory(Items.XERICS_TALISMAN, "check", XericsTalisman::check);
        ItemAction.registerEquipment(Items.XERICS_TALISMAN, "check", XericsTalisman::check);
        ItemAction.registerInventory(Items.XERICS_TALISMAN, "uncharge", XericsTalisman::uncharge);
        ItemAction.registerEquipment(Items.XERICS_TALISMAN, "xeric's lookout", (player, item) -> teleport(player, item, JewelleryTeleportBounds.XERICS_LOOKOUT));
        ItemAction.registerEquipment(Items.XERICS_TALISMAN, "xeric's glade", (player, item) -> teleport(player, item, JewelleryTeleportBounds.XERICS_GLADE));
        ItemAction.registerEquipment(Items.XERICS_TALISMAN, "xeric's inferno", (player, item) -> teleport(player, item, JewelleryTeleportBounds.XERICS_INFERNO));
        ItemAction.registerEquipment(Items.XERICS_TALISMAN, "xeric's heart", (player, item) -> teleport(player, item, JewelleryTeleportBounds.XERICS_HEART));
        ItemAction.registerEquipment(Items.XERICS_TALISMAN, "xeric's honour", (player, item) -> teleport(player, item, JewelleryTeleportBounds.XERICS_HONOUR));
        // Inert
        ItemAction.registerInventory(Items.XERICS_TALISMAN_INERT, "check", XericsTalisman::check);
        ItemAction.registerEquipment(Items.XERICS_TALISMAN_INERT, "check", XericsTalisman::check);
        ItemAction.registerInventory(Items.XERICS_TALISMAN_INERT, "dismantle", XericsTalisman::dismantle);
        // Charging
        ItemItemAction.register(Items.XERICS_TALISMAN, Items.LIZARDMAN_FANG, XericsTalisman::charge);
        ItemItemAction.register(Items.XERICS_TALISMAN_INERT, Items.LIZARDMAN_FANG, XericsTalisman::charge);
        // Ancient tablet
        ItemAction.registerInventory(Items.ANCIENT_TABLET, "inspect", (player, item) -> player.dialogue(
                new MessageDialogue("You inspect the tablet, and sense a magical power, running your fingers over the inscriptions, they tingle as you get a " +
                        "feel for Xeric's power. He must have stored one of his best spells in this tablet, perhaps he has some sort of device which could utilise it?")));
        ItemItemAction.register(Items.XERICS_TALISMAN, Items.ANCIENT_TABLET, (player, primary, secondary) -> combineAncientTablet(player, secondary));
        ItemItemAction.register(Items.XERICS_TALISMAN_INERT, Items.ANCIENT_TABLET, (player, primary, secondary) -> combineAncientTablet(player, secondary));
    }
}
