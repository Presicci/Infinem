package io.ruin.model.skills.fishing.lakemolch;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.hunter.falconry.Falconry;

public class AlryTheAngler {

    private static final int ARLY_THE_ANGLER = 8521;
    private static final int CORMORANTS_GLOVE_BIRD = 22817;
    private static final int GOLDEN_TENCH = 22840;
    private static final int MOLCH_PEARL = 22820;

    static {
        NPCAction.register(ARLY_THE_ANGLER, "talk-to", (player, npc) -> {
            if (player.getEquipment().contains(CORMORANTS_GLOVE_BIRD)) {
                greetingWithGlove(player, npc);
            } else {
                greeting(player, npc);
            }
        });
        NPCAction.register(ARLY_THE_ANGLER, "get bird", (player, npc) -> {
            if (player.getEquipment().contains(CORMORANTS_GLOVE_BIRD)) {
                greetingWithGlove(player, npc);
            } else {
                getBird(player, npc);
            }
        });
    }

    private static void greeting(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Hello there."),
                new NPCDialogue(ARLY_THE_ANGLER, "What brings you to these parts, stranger?"),
                player.getInventory().contains(GOLDEN_TENCH)
                        ? new OptionsDialogue(
                        new Option("What is this place?", () -> player.dialogue(new PlayerDialogue("What is this place?"),
                                new NPCDialogue(ARLY_THE_ANGLER, "I suppose it is a little different to the places your type are used to..."),
                                new NPCDialogue(ARLY_THE_ANGLER, "This is my humble little hideaway! Plenty of fish around to sustain a man like me, with the help of my trusty cormorant."),
                                new PlayerDialogue("A bird helps you to fish?"),
                                new NPCDialogue(ARLY_THE_ANGLER, "Sure she does! I haven't used a rod in years, just send her out to the waters and she takes care of it for me!"),
                                new OptionsDialogue(
                                        new Option("Could I have a go with your bird?", () -> getBird(player, npc)),
                                        new Option("I don't think that's for me.", () -> player.dialogue(
                                                new PlayerDialogue("I don't think that's for me... I'll leave that to you."),
                                                new NPCDialogue(ARLY_THE_ANGLER, "No surprises there! Your type never have been cut out for it.")
                                        ))
                                ))),
                        new Option("Could I have a go with your bird?", () -> getBird(player, npc)),
                        new Option("What's in the sack?", () -> player.dialogue(new PlayerDialogue("What's in the sack?"),
                                new NPCDialogue(ARLY_THE_ANGLER, "Oh, this? This is my trusty old fish sack!"),
                                new PlayerDialogue("Fish sack?"),
                                new NPCDialogue(ARLY_THE_ANGLER, "Yeah, fish sack! I might sell you one if you've found any of those pearls around here."),
                                new OptionsDialogue(
                                        new Option("Let's take a look.", () -> npc.getDef().shops.get(0).open(player)),
                                        new Option("No thanks.")
                                ))),
                        new Option("I found this big fish...", () -> sellGoldenTench(player, npc))
                )
                        : new OptionsDialogue(
                        new Option("What is this place?", () -> player.dialogue(new PlayerDialogue("What is this place?"),
                                new NPCDialogue(ARLY_THE_ANGLER, "I suppose it is a little different to the places your type are used to..."),
                                new NPCDialogue(ARLY_THE_ANGLER, "This is my humble little hideaway! Plenty of fish around to sustain a man like me, with the help of my trusty cormorant."),
                                new PlayerDialogue("A bird helps you to fish?"),
                                new NPCDialogue(ARLY_THE_ANGLER, "Sure she does! I haven't used a rod in years, just send her out to the waters and she takes care of it for me!"),
                                new OptionsDialogue(
                                        new Option("Could I have a go with your bird?", () -> getBird(player, npc)),
                                        new Option("I don't think that's for me.", () -> player.dialogue(
                                                new PlayerDialogue("I don't think that's for me... I'll leave that to you."),
                                                new NPCDialogue(ARLY_THE_ANGLER, "No surprises there! Your type never have been cut out for it.")
                                        ))
                                ))),
                        new Option("Could I have a go with your bird?", () -> getBird(player, npc)),
                        new Option("What's in the sack?", () -> player.dialogue(new PlayerDialogue("What's in the sack?"),
                                new NPCDialogue(ARLY_THE_ANGLER, "Oh, this? This is my trusty old fish sack!"),
                                new PlayerDialogue("Fish sack?"),
                                new NPCDialogue(ARLY_THE_ANGLER, "Yeah, fish sack! I might sell you one if you've found any of those pearls around here."),
                                new OptionsDialogue(
                                        new Option("Let's take a look.", () -> npc.getDef().shops.get(0).open(player)),
                                        new Option("No thanks.")
                                )))
                )
        );
    }

    private static void greetingWithGlove(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Hello again."),
                new NPCDialogue(ARLY_THE_ANGLER, "Ah, it's you. How are you finding things?"),
                new PlayerDialogue("It certainly is a challenge..."),
                new NPCDialogue(ARLY_THE_ANGLER, "Yes, your type do often seem to underestimate it! Are you finished?"),
                new OptionsDialogue(
                        new Option("Not yet. I think I'll keep trying.", () -> player.dialogue(
                                new NPCDialogue(ARLY_THE_ANGLER, "Just come back and let me know when you get tired!")
                        )),
                        new Option("Yeah, I'll try again another time.", () -> player.dialogue(
                                new ActionDialogue(() -> {
                                    player.getEquipment().remove(CORMORANTS_GLOVE_BIRD, 1);
                                    player.dialogue(
                                            new MessageDialogue("Alry takes back the Cormorant's glove.")
                                    );
                                })
                        ))
                )
        );
    }

    private static void getBird(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Could I have a go with your bird?"),
                new ActionDialogue(() -> {
                    Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
                    if (weapon != null && weapon.getId() == CORMORANTS_GLOVE_BIRD || player.getInventory().hasId(CORMORANTS_GLOVE_BIRD)) {
                        player.dialogue(new NPCDialogue(ARLY_THE_ANGLER, "You.. you already have my bird."));
                    } else {
                        player.dialogue(new NPCDialogue(ARLY_THE_ANGLER, "I don't know, I doubt that your type's up to the task..."),
                                new NPCDialogue(ARLY_THE_ANGLER, "But it would be quite the amusing sight. Go on!"),
                                new ActionDialogue(() -> {
                                    if (Falconry.freeHands(player)) {
                                        player.getEquipment().set(Equipment.SLOT_WEAPON, new Item(CORMORANTS_GLOVE_BIRD, 1));
                                        player.dialogue(
                                                new MessageDialogue("Arly gives you a large leather glove and summons a cormorant who lands on it."),
                                                new NPCDialogue(ARLY_THE_ANGLER, "I'll keep an eye on you and make sure you don't have too much trouble with her!"));
                                    } else {
                                        player.dialogue(new NPCDialogue(ARLY_THE_ANGLER, "You're going to need to free up your hands first, though. Nothing in or on your hands."));
                                    }
                                })
                        );
                    }
                })
        );
    }

    private static void sellGoldenTench(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("I found this big fish..."),
                new NPCDialogue(ARLY_THE_ANGLER, "Now that is a biggun! How she managed to wrangle one like that I'll never know."),
                new NPCDialogue(ARLY_THE_ANGLER, "If you don't want it, I could take it off your hands for a few of those pearls!"),
                new PlayerDialogue("I'm interested... How many pearls are we talking?"),
                new NPCDialogue(ARLY_THE_ANGLER, "I'll give you a hundred pearls for it. What do you say?"),
                new OptionsDialogue("Trade in your golden tench for 100 molch pearls?",
                        new Option("It's a deal.", () -> player.dialogue(
                                new OptionsDialogue("Are you REALLY sure?",
                                        new Option("No, keep the tench.", () -> player.dialogue(
                                                new PlayerDialogue("On seconds thoughts, I'd rather keep it."),
                                                new NPCDialogue(ARLY_THE_ANGLER, "As you wish.")
                                        )),
                                        new Option("Yes, exchange the golden tench.", () -> player.dialogue(
                                                new PlayerDialogue("It's a deal."),
                                                new ActionDialogue(() -> {
                                                    player.getInventory().remove(GOLDEN_TENCH, 1);
                                                    player.getInventory().add(MOLCH_PEARL, 100);
                                                    player.dialogue(
                                                            new ItemDialogue().two(GOLDEN_TENCH, MOLCH_PEARL, "You trade the golden tench with Alry for a load of pearls."),
                                                            new NPCDialogue(ARLY_THE_ANGLER, "Pleasure doing business with you!")
                                                    );
                                                })
                                        )
                                        )
                                ))
                        ),
                        new Option("No thanks.", () -> player.dialogue(
                                new PlayerDialogue("No thanks."),
                                new NPCDialogue(ARLY_THE_ANGLER, "Well, let me know if you change your mind.")
                        ))
                )
        );
    }
}
