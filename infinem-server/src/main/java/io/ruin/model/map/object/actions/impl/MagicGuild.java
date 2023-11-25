package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/24/2023
 */
public class MagicGuild {

    private static boolean cantEnter(Player player) {
        // If the player is in the guild, let them out regardless
        if (player.getAbsX() == 2596 || player.getAbsX() == 2585) return false;
        if (player.getStats().get(StatType.Magic).currentLevel < 66) {
            player.dialogue(new NPCDialogue(3248, "You need a magic level of 66. The magical energy in here is unsafe for those below that level."));
            return true;
        }
        return false;
    }

    static {
        // Entrances
        ObjectAction.register(1732, 2584, 3088, 0, "open", (player, obj) -> {
            if (cantEnter(player)) return;
            DoubleDoor.passThroughLateral(player, obj, 1732, 1733, new Position(2584, 3088), new Position(2584, 3087), true);
        });
        ObjectAction.register(1733, 2584, 3087, 0, "open", (player, obj) -> {
            if (cantEnter(player)) return;
            DoubleDoor.passThroughLateral(player, obj, 1732, 1733, new Position(2584, 3088), new Position(2584, 3087), true);
        });
        ObjectAction.register(1733, 2597, 3088, 0, "open", (player, obj) -> {
            if (cantEnter(player)) return;
            DoubleDoor.passThroughLateral(player, obj, 1733, 1732, new Position(2597, 3088), new Position(2597, 3087), false);
        });
        ObjectAction.register(1732, 2597, 3087, 0, "open", (player, obj) -> {
            if (cantEnter(player)) return;
            DoubleDoor.passThroughLateral(player, obj, 1733, 1732, new Position(2597, 3088), new Position(2597, 3087), false);
        });
        // Basement gates
        ObjectAction.register(2155, 2592, 9490, 0, "open", (player, obj) -> player.dialogue(new NPCDialogue(3246, "You can't attack the Zombies in the room, my Zombies are for magic target practice only and should be attacked from the other side of the fence.")));
        ObjectAction.register(2154, 2593, 9490, 0, "open", (player, obj) -> player.dialogue(new NPCDialogue(3246, "You can't attack the Zombies in the room, my Zombies are for magic target practice only and should be attacked from the other side of the fence.")));
        // Bell
        ObjectAction.register(6847, 2598, 3085, 0, "ring", (player, obj) -> {
            player.dialogue(
                    new MessageDialogue("You ring the bell..."),
                    new MessageDialogue("...no one answers."),
                    new MessageDialogue("You ring the bell again..."),
                    new NPCDialogue(3248, "I think someone is at the door. Can someone get that?"),
                    new NPCDialogue(881, "I'm busy, Imblewyn, can you get the door?"),
                    new NPCDialogue(2561, "I can't reach the handle."),
                    new NPCDialogue(3248, "Can someone get the door?"),
                    new MessageDialogue("You ring the bell again..."),
                    new NPCDialogue(3248, "CAN SOMEONE GET THE DOOR?"),
                    new NPCDialogue(881, "I'm busy."),
                    new NPCDialogue(2561, "Whoever is there, is it too much to ask you to open the door yourself?"),
                    new MessageDialogue("You ring the bell again..."),
                    new NPCDialogue(3248, "I wont stand for this."),
                    new NPCDialogue(3248, "Senventior Disthine Molenko!"),
                    new ActionDialogue(() -> {
                        if (player.getStats().get(StatType.Magic).currentLevel < 66) {
                            ModernTeleport.teleport(player, 2551, 3099, 0, p -> player.dialogue(
                                    new NPCDialogue(5815, "Hey! How did you get in my house?"),
                                    new PlayerDialogue("I'm not sure. I'll be leaving now.")
                            ));
                        } else {
                            ModernTeleport.teleport(player, 2595, 3087, 0, p -> player.dialogue(
                                    new NPCDialogue(3248, "You could have done that yourself..."),
                                    new PlayerDialogue("Is that any way to talk to your guest?")
                            ));
                        }
                    })
            );
        });
    }
}
