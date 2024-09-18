package io.ruin.model.skills.smithing.blastfurnace;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.Dialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/17/2024
 */
public class BlastFurnaceMeltingPot {

    private static void check(Player player) {
        int counter = 0;
        StringBuilder page1 = new StringBuilder();
        StringBuilder page2 = new StringBuilder();
        for(final BlastFurnaceOre ore : BlastFurnaceOre.values()) {
            String string = StringUtils.capitalizeFirst(ore.toString().toLowerCase().replace("_", " ")) + ": " + BlastFurnace.getOre(player, ore) + "<br>";
            (counter < 5 ? page1 : page2).append(string);
            counter++;
        }
        List<Dialogue> dialogues = new ArrayList<>();
        if (page1.length() > 0) dialogues.add(new MessageDialogue(page1.toString()));
        if (page2.length() > 0) dialogues.add(new MessageDialogue(page2.toString()));
        if (dialogues.isEmpty()) dialogues.add(new MessageDialogue("You don't have any ore in the melting pot."));
        player.dialogue(dialogues.toArray(new Dialogue[0]));
    }

    static {
        ObjectAction.register(9098, "check", (player, obj) -> check(player));
    }
}
