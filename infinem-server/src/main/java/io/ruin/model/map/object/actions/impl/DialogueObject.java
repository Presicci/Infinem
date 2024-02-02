package io.ruin.model.map.object.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.data.impl.dialogue.DialogueLoaderAction;
import io.ruin.model.entity.npc.NPC;
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
    BROKEN_MACHINERY(34587, new Position(1347, 10263),
            new MessageDialogue("This machine doesn't look like it's in working order... I wonder if there's a fixed one, anywhere.")),
    ELEMENTAL_WORKSHOP_HATCH(3413, new Position(2719, 9890),
            new PlayerDialogue("Doesn't smell good down there, I think I'll just stay here.")),
    AWOWOGEI(4771, new Position(2802, 2765),
            new ActionDialogue((player) -> {
                if (player.getEquipment().hasId(4021)) {
                    player.dialogue(
                            new NPCDialogue(2972, "Greetings, [player name].")
                    );
                } else {
                    String[] phrases = {
                            "Ah Ah!",
                            "Ah Uh Ah!",
                            "Ah!",
                            "Ook Ah Ook!",
                            "Ook Ah Uh!",
                            "Ook Ook!",
                            "Ook!",
                            "Ook."
                    };
                    StringBuilder sb = new StringBuilder();
                    int count = 0;
                    int max = Random.get(8, 10);
                    while (count < max) {
                        sb.append(Random.get(phrases));
                        sb.append(" ");
                        count++;
                    }
                    player.dialogue(new NPCDialogue(2972, sb.toString()));
                }
            })),
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
    KALEEFS_BODY(44597, new Position(2284, 4315),
            new MessageDialogue("He's dead.")),
    // They fixed it ;(
    //LUMBRIDGE_COW_PEN_CRATES(358, new Position(3244, 3279),
    //        new MessageDialogue("You thoroughly search the crates, yet you fail to find any potential reason for why the fence was built around them. Cheap labor potentially.")),
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
