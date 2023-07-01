package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/30/2023
 */
@AllArgsConstructor
public enum MolchRowboat {
    MOLCH_ISLAND("Molch Island", new Position(1368, 3640), new Position(1369, 3639, 0)),
    MOLCH("Molch", new Position(1343, 3645), new Position(1342, 3646, 0)),
    BATTLEFRONT("Battlefront", new Position(1383, 3662), new Position(1384, 3665, 0)),
    SHAYZIEN("Shayzien", new Position(1405, 3611), new Position(1408, 3612, 0));

    private final String name;
    private final Position objPosition, destination;

    private void travel(Player player) {
        player.startEvent(e -> {
            player.lock();
            player.getPacketSender().fadeOut();
            player.dialogue(new MessageDialogue("You travel to " + name + ".").hideContinue());
            e.delay(3);
            player.getMovement().teleport(destination);
            player.getPacketSender().fadeIn();
            player.closeDialogue();
            player.unlock();
        });
    }

    static {
        ObjectAction.register(33614, "board", ((player, obj) -> {
            List<Option> optionList = new ArrayList<>();
            for (MolchRowboat rowboat : values()) {
                if (rowboat.objPosition.getX() == obj.getPosition().getX()) continue;
                optionList.add(new Option(rowboat.name, () -> rowboat.travel(player)));
            }
            player.dialogue(new OptionsDialogue("Where would you like to go?",
                    optionList.get(0),
                    optionList.get(1),
                    optionList.get(2),
                    new Option("Cancel")
            ));
        }));
    }
}
