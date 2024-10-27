package io.ruin.model.entity.npc.actions.wilderness;

import io.ruin.cache.ItemID;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemNPCAction;

import java.util.LinkedList;
import java.util.List;

public class MadamSikaro {

    private static final int VOIDWAKER = ItemID.VOIDWAKER;
    private static final int BLADE = ItemID.VOIDWAKER_BLADE;
    private static final int HILT = ItemID.VOIDWAKER_HILT;
    private static final int GEM = ItemID.VOIDWAKER_GEM;
    private static final int MADAM_SIKARO = 11991;

    static {
        NPCAction.register(MADAM_SIKARO, "talk-to", (player, npc) -> {
            npc.faceTemp(player);
            player.dialogue(
                    new NPCDialogue(npc, "Ah, greetings. What can I do for you?"),
                    new OptionsDialogue(getOptions(player, npc)
                    ));
        });
        ItemNPCAction.register(MADAM_SIKARO, (player, item, npc) -> {
            npc.faceTemp(player);
            if (item.getId() == VOIDWAKER) {
                player.dialogue(
                        new PlayerDialogue("So what exactly is this sword, anyway?"),
                        new NPCDialogue(npc, "Well, that largely depends on who you ask and when you ask it. The sword is not unique, but many like it have been referenced in folktales."),
                        new NPCDialogue(npc, "Some tell of great and prideful warriors roaming the lands, cutting down all who oppose them in great crashes of lightning."),
                        new NPCDialogue(npc, "Others tell of lovers, one bound by oath, the other free as the wind, cruely torn apart by fate."),
                        new NPCDialogue(npc, "Others still tell of three great beasts that alone were powerful, but combined could destroy Gielinor."),
                        new PlayerDialogue("Are we still talking about the sword?"),
                        new NPCDialogue(npc, "Amazingly, yes. In all of these tales, the sword is there, potential shining through the metal, thunder rolling in its wake."),
                        new PlayerDialogue("Are any of these tales... true?"),
                        new NPCDialogue(npc, "Oh, I'm sure there are elements of the truth buried in there, but folklore being what it is, I'm inclined to not believe any of it."),
                        new NPCDialogue(npc, "Aside from the bit where it can summon lightning. It can absolutely do that."),
                        new PlayerDialogue("What about the name? You called it Voidwaker?"),
                        new NPCDialogue(npc, "Well, in a lot of these tales, it's only ever reffered to as someone's sword. Like, " + player.getName() + "'s sword."),
                        new NPCDialogue(npc, "So, I chose a name for it."),
                        new PlayerDialogue("And you chose... Voidwaker? Why?"),
                        new NPCDialogue(npc, "There are multiple reasons. I suppose you could say the sword is so powerful that it leaves a void in its wake."),
                        new PlayerDialogue("That makes sense.")

                );
            } else {
                player.dialogue(
                        new NPCDialogue(npc, "No thank you, I'm currently on a diet.")
                );
            }
        });

    }

    private static void combineVoidwaker(Player player, NPC npc) {
        if (!player.getInventory().hasItem(995, 500_000)) {
            player.dialogue(
                    new PlayerDialogue("I don't have that much on me..."),
                    new NPCDialogue(npc, "Well, if you come across some more, come find me again.")
            );
        } else {
            player.dialogue(
                    new OptionsDialogue("Have Madam Sikaro assemble the Voidwaker for 5,000,000 coins?",
                            new Option("Yes", () -> {
                                player.getInventory().remove(BLADE, 1);
                                player.getInventory().remove(HILT, 1);
                                player.getInventory().remove(GEM, 1);
                                player.getInventory().remove(995, 500_000);
                                player.getInventory().add(VOIDWAKER, 1);
                                player.dialogue(new ItemDialogue().one(VOIDWAKER, "Madam Sikaro takes the components off you and, in a flash of white light, fuses them together to form the Voidwaker."));
                            }),
                            new Option("No", () -> player.dialogue(
                                    new PlayerDialogue("Actually, I've changed my mind."),
                                    new NPCDialogue(npc, "Curious, you come to me with all of the parts and the coin, yet you change your mind at the last second."),
                                    new NPCDialogue(npc, "I do rather get the feeling you're toying with me. Very well, I shall do my part in playing with you.\n"),
                                    new MessageDialogue("You feel the contents of your inventory shift slightly."),
                                    new NPCDialogue(npc, "Now, do talk to me again if you'd like to proceed more sensibly.")
                            ))
                    )
            );
        }
    }

    private static List<Option> getOptions(Player player, NPC npc) {
        List<Option> options = new LinkedList<>();
        options.add(new Option("Who are you?", () -> player.dialogue(
                new PlayerDialogue("Who are you?"),
                new NPCDialogue(npc, "I'm Madam Sikaro."),
                new PlayerDialogue("..."),
                new NPCDialogue(npc, "..."),
                new PlayerDialogue("...Is that it?"),
                new NPCDialogue(npc, "Why shouldn't it be? You asked who I was, and I answered."),
                new PlayerDialogue("I was hoping to learn something about you, like what you were doing here...!"),
                new NPCDialogue(npc, "Well, perhaps next time you'll ask a more direct question.")

        )));
        options.add(new Option("What are you doing here?", () -> player.dialogue(
                new PlayerDialogue("What are you doing here?"),
                new NPCDialogue(npc, "Making a wizard hideout."),
                new PlayerDialogue("A wizard... hideout?"),
                new NPCDialogue(npc, "A wizard hideout! You know how some wizards have their own towers? Think that but underground."),
                new PlayerDialogue("So why not just get a tower?"),
                new NPCDialogue(npc, "Land price. Kingdoms want you to pay an extortionate amount of money to build on 'their' land, it turns out."),
                new PlayerDialogue("So instead you ended up in the basement of a tavern."),
                new NPCDialogue(npc, "Yep. Camarst said I could have it as long as I don't hurt any of his customers too badly."),
                new NPCDialogue(npc, "Not that I actually plan to hurt any of his customers.")
        )));
        if (player.getInventory().hasAtLeastOneOf(BLADE, HILT, GEM)) {
            options.add(new Option("I have this part that looks like it's from a weapon...", () -> {
                if (!player.getInventory().containsAll(BLADE, HILT, GEM)) {
                    player.dialogue(
                            new PlayerDialogue("I have this part that looks like it's from a weapon..."),
                            new NPCDialogue(npc, "Ah yes, that does look like a part of a particularly powerful weapon."),
                            new PlayerDialogue("Can I do anything with it?"),
                            new NPCDialogue(npc, "You? Perhaps not, though if you could bring me all three pieces, I suspect I could put it back together for you."),
                            new NPCDialogue(npc, "There should be a hilt, a blade and a conduit gemstone. Oh, and I'll want 500,000 coins as payment as well. Good hunting!"),
                            new PlayerDialogue("Do you know where I could find the other parts?"),
                            new NPCDialogue(npc, "I suspect the Wilderness would be a good place to start. There are monsters out there that have grown immense over time."),
                            new NPCDialogue(npc, "Seeking out the strongest of them is where I'd start, anyway.")
                    );
                }
                if (player.getInventory().containsAll(BLADE, HILT, GEM)) {
                    player.dialogue(
                            new PlayerDialogue("I have these parts of a weapon, do you think you could assemble it for me?"),
                            new NPCDialogue(npc.getId(), "Hmm... yes, these are the parts of a particularly powerful weapon indeed. " +
                                    "I can reassemble it... for a fee, of course. 500,000 coins, no less."),
                            new ActionDialogue(() -> combineVoidwaker(player, npc))
                    );
                }
            }));
        }
        options.add(new Option("Goodbye", () -> player.dialogue(
                new PlayerDialogue("Goodbye."),
                new NPCDialogue(npc, "You walked up to me just to say goodbye? Ok, sure, goodbye.")
        )));
        return options;
    }

}
