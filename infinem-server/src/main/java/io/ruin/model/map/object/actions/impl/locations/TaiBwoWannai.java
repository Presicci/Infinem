package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/29/2024
 */
public class TaiBwoWannai {

    static {
        ObjectAction.register(9038, "open", (player, obj) -> player.dialogue(new MessageDialogue("This whole minigame has yet to be implemented.")));
        ObjectAction.register(9039, "open", (player, obj) -> player.dialogue(new MessageDialogue("This whole minigame has yet to be implemented.")));
    }
}
