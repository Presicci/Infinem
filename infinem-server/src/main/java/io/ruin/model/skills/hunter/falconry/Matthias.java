package io.ruin.model.skills.hunter.falconry;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2023
 */
public class Matthias {

    private static final int FALCONERS_GLOVES = 10023;
    private static final int FALCONERS_GLOVES_BIRD = 10024;

    static {
        NPCAction.register(1340, "talk-to", (player, npc) -> {
            if (player.getEquipment().contains(FALCONERS_GLOVES_BIRD)) {
                dialogueWithGlove(player, npc);
            } else {
                dialogue(player, npc);
            }
        });
    }

    private static void getBird(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Could I have a go with your bird?"),
                new NPCDialogue(npc, "Training falcons is a lot of work and I doubt you're up to the task. However, I suppose I could let you try hunting with one."),
                new NPCDialogue(npc, "I have some tamer birds that I occasionally lend to rich noblemen who consider it a sufficiently refined sport for their tastes, and you look like the kind who might appreciate a good hunt."),
                new ActionDialogue(() -> {
                    if (Falconry.freeHands(player)) {
                        player.getEquipment().set(Equipment.SLOT_WEAPON, new Item(FALCONERS_GLOVES_BIRD, 1));
                        player.dialogue(
                                new MessageDialogue("The falconer gives you a large leather glove and brings one of the smaller birds over to land on it."),
                                new NPCDialogue(npc, "Don't worry; I'll keep an eye on you to make sure you don't upset it too much."));
                    } else {
                        player.dialogue(new NPCDialogue(npc, "Sorry, you really need both hands free for falconry. I'd suggest that you put away your weapons and gloves before we start."));
                    }
                })
        );
    }

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Hello there."),
                new NPCDialogue(npc, "Greetings. Can I help you at all?"),
                new OptionsDialogue(
                        new Option("What is this place?", () -> {
                            player.dialogue(
                                    new NPCDialogue(npc, "A good question; straight and to the point. My name is Matthias, I am a falconer, and this is where I train my birds."),
                                    new OptionsDialogue(
                                            new Option("That sounds like fun; could I have a go?", () -> getBird(player, npc)),
                                            new Option("That doesn't sound like my sort of thing.", () -> player.dialogue(
                                                    new PlayerDialogue("That doesn't sound like my sort of thing."),
                                                    new NPCDialogue(npc, "Fair enough; it does require a great deal of patience and skill, so I can understand if you might feel intimidated.")
                                            )),
                                            new Option("What's this falconry thing all about then?", () -> player.dialogue(
                                                    new PlayerDialogue("What's this falconry thing all about then?"),
                                                    new NPCDialogue(npc, "Well, some people see it as a sport, although such a term does not really convey the amount of patience and dedication required to be proficient at the task. Putting it simply, it is the training and use of birds of prey in hunting quarry."),
                                                    new PlayerDialogue("So it's like keeping a pet then?"),
                                                    new NPCDialogue(npc, "Not exactly, no. Such a bird can never really be considered tame in the same way that a dog can. They can be trained to associate people or places with food though, and, as such, a good falconer can get a trained bird to do as he wishes.")
                                            ))
                                    )
                            );
                        }),
                        new Option("Could I have a go with your bird?", () -> getBird(player, npc))
                )
        );
    }

    private static void dialogueWithGlove(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Hello again."),
                new NPCDialogue(npc, "Ah, you're back. How are you getting along with her then?"),
                new PlayerDialogue("It's certainly harder than it looks."),
                new NPCDialogue(npc, "Sorry, but I was talking to the falcon, not you. But yes it is. Have you had enough yet?"),
                new OptionsDialogue(
                        new Option("Actually, I'd like to keep trying a little longer", () -> player.dialogue(
                                new NPCDialogue(npc, "Ok then, just come talk to me when you're done.")
                        )),
                        new Option("I think I'll leave it for now.", () -> player.dialogue(
                                new ActionDialogue(() -> {
                                    player.getEquipment().remove(FALCONERS_GLOVES_BIRD, 1);
                                    player.dialogue(
                                            new MessageDialogue("You give the falcon and glove back to Matthias.")
                                    );
                                })
                        ))
                )
        );
    }
}
