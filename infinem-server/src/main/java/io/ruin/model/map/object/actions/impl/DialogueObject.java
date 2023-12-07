package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/1/2023
 */
public enum DialogueObject {
    KITCHEN_CABINET(17132, new Position(2449, 3512, 1),
            new OptionsDialogue("What would you like to take?",
                    new Option("Knife", (player) -> takeItem(player, new Item(Items.KNIFE), "cabinet")),
                    new Option("Batta tin", (player) -> takeItem(player, new Item(Items.BATTA_TIN), "cabinet")),
                    new Option("Crunchy tray", (player) -> takeItem(player, new Item(Items.CRUNCHY_TRAY), "cabinet")),
                    new Option("Gnomebowl mould", (player) -> takeItem(player, new Item(Items.GNOMEBOWL_MOULD), "cabinet"))
            )),
    FRACTIONALISING_STILL(4026, new Position(2927, 3212),
            new PlayerDialogue("That is far too complicated for me to operate.")),
    TENT_DOOR(2700, new Position(3169, 3046),
            new MessageDialogue("It's locked."), new PlayerDialogue("Wait what?")),
    SITHIK_INTS(6887, new Position(2591, 3103, 1),
            new NPCDialogue(883, "Leave me be, please.")),
    KALEEFS_BODY(20288, new Position(3239, 9243),
            new MessageDialogue("He's dead.")),
    LUMBRIDGE_COW_PEN_CRATES(358, new Position(3244, 3279),
            new MessageDialogue("You thoroughly search the crates, yet you fail to find any potential reason for why the fence was built around them. Cheap labor potentially.")),
    CRUMBLING_TOWER(1796, new Position(1936, 9444),
            new MessageDialogue("Someone has crudely carved a message into the wall:<br><br>'The Soul Stone was here. Where is it now?")),
    BRINE_RAT_CAVE_CHEST(14197, new Position(2740, 10164),
            new MessageDialogue("An etching on the chest reads 'Titan.' There is nothing here to indicate how the ship got here or what they were searching for."));

    DialogueObject(int objectId, Position position, Dialogue... dialogues) {
        this(objectId, 1, position, dialogues);
    }

    DialogueObject(int objectId, int option, Position position, Dialogue... dialogues) {
        ObjectAction.register(objectId, position, option, ((p, o) -> p.dialogue(dialogues)));
    }

    DialogueObject(int objectId, Dialogue... dialogues) {
        this(objectId, 1, dialogues);
    }

    DialogueObject(int objectId, int option, Dialogue... dialogues) {
        ObjectAction.register(objectId, option, ((p, o) -> p.dialogue(dialogues)));
    }

    private static void takeItem(Player player, Item item, String containerName) {
        if (!player.getInventory().hasRoomFor(item)) {
            player.dialogue(new MessageDialogue("You do not have enough inventory space to take that."));
            return;
        }
        player.animate(3572);
        player.getInventory().add(item);
        player.sendMessage("You take a " + item.getDef().name.toLowerCase() + " from the " + containerName + ".");
    }
}
