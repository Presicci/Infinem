package io.ruin.model.entity.npc.actions.misc;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.Inventory;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/26/2021
 */
public class Solztun {

    private static final String textColor = "<col=000080>";

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc.getId(), textColor + "Can you hear me?"),
                new PlayerDialogue("...I can, but I'm not wearing an amulet of ghostspeak... A-am I dead?"),
                new NPCDialogue(npc.getId(), textColor + "I'm no ghost! I'm a spirit, I'm injecting my thoughts into your head."),
                new PlayerDialogue("Oh, that's... interesting. Also rather disturbing..."),
                new NPCDialogue(npc.getId(), textColor + "Try not to think about it."),
                new ActionDialogue(() -> { options(player, npc); })
        );
    }

    private static void options(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                    new Option("Could you imbue my sceptre?", () -> { imbue(player, npc); }),
                    new Option("Who are you?", () -> { whoAreYou(player, npc); }),
                    new Option("Why are you here?", () -> { whyAreYouHere(player, npc); }),
                    new Option("Bye!", () -> { bye(player, npc); })
        ));
    }

    private static void imbue(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Could you imbue my sceptre?"),
                new NPCDialogue(npc.getId(), textColor + "Of course I can... But first, you must show me whatever treasure lies within that cradle."),
                new ActionDialogue(() -> {
                    ItemContainerG<? extends Item> fancyBootsContainer = player.findItem(Items.FANCY_BOOTS);
                    ItemContainerG<? extends Item> fightingBootsContainer = player.findItem(Items.FIGHTING_BOOTS);
                    if (fancyBootsContainer instanceof Inventory || fancyBootsContainer instanceof Equipment) {
                        imbueFinish(player, npc, Items.FANCY_BOOTS);
                    } else if (fightingBootsContainer instanceof Inventory || fightingBootsContainer instanceof Equipment) {
                        imbueFinish(player, npc, Items.FIGHTING_BOOTS);
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Oh, I don't have the treasure on me."),
                                new NPCDialogue(npc.getId(), textColor + "Sorry, but I wont imbue the sceptre for free!")
                        );
                    }
                })
        );
    }

    private static void imbueFinish(Player player, NPC npc, int itemId) {
        player.dialogue(
                new PlayerDialogue("Is this the treasure?"),
                new ItemDialogue().one(itemId, "You show the fancy boots to Solztun."),
                new NPCDialogue(npc.getId(), textColor + "Oh marvellous! If only I had a corporeal form I'd be able to wear them, but alas..."),
                new PlayerDialogue("So could you imbue my sceptre?"),
                new NPCDialogue(npc.getId(), textColor + "Of course, of course!"),
                new ActionDialogue(() -> {
                    Item sceptre = player.getInventory().findItemIgnoringAttributes(Items.SKULL_SCEPTRE, false);
                    if (sceptre != null) {
                        npc.face(player);
                        npc.graphics(763);
                        npc.animate(1818);
                        sceptre.setId(21276);
                        player.dialogue(
                                new NPCDialogue(npc.getId(), textColor + "There you go, now that I've imbued it, once it runs out of charges it will no longer break and it can be charged with any piece of the sceptre you find."),
                                new PlayerDialogue("Thank you very much! Good bye Solztun!"),
                                new NPCDialogue(npc.getId(), textColor + "Stay safe, adventurer.")
                        );
                    } else {
                        player.dialogue(
                                new NPCDialogue(npc.getId(), textColor + "Oh, it doesn't appear that you have a sceptre for me to imbue, come back when you do!")
                        );
                    }
                })
        );
    }

    private static void whoAreYou(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Who are you?"),
                new NPCDialogue(npc.getId(), textColor + "As I said, I am Solztun, the greatest barbarian explorer! I came to explore this place, but unfortunately I died long before I made it to the treasure."),
                new PlayerDialogue("Nobody came to help?!"),
                new NPCDialogue(npc.getId(), textColor + "Oh no, they had no idea I was hurt, I guess they assumed I'd found the treasure and made a new life for myself."),
                new PlayerDialogue("Were you not a fan of the barbarian life style?"),
                new NPCDialogue(npc.getId(), textColor + "It was all death and pillaging, I prefer the finer things in life, like exploration and discovery!"),
                new PlayerDialogue("Ah, I too enjoy those."),
                new ActionDialogue(() -> { options(player, npc); })
        );
    }

    private static void whyAreYouHere(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Why are you here?"),
                new NPCDialogue(npc.getId(), textColor + "I came for treasure!"),
                new PlayerDialogue("Oh right, you were looking for what's in that cradle?"),
                new NPCDialogue(npc.getId(), textColor + "Yes, but alas, I'm dead."),
                new PlayerDialogue("I guess I could show you... for a price."),
                new NPCDialogue(npc.getId(), textColor + "Hmm... I don't have much to offer, but if you happen upon a skull sceptre, I could make it stronger."),
                new PlayerDialogue("Stronger you say... In what way?"),
                new NPCDialogue(npc.getId(), textColor + "Currently the sceptre is fragile, it breaks once all charges have been used. Show me the treasure and I could imbue it so it can be recharged with any sceptre piece."),
                new PlayerDialogue("You, my dead friend, have a deal!"),
                new ActionDialogue(() -> { options(player, npc); })
        );
    }

    private static void bye(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Bye!"),
                new NPCDialogue(npc.getId(), textColor + "Stay safe, adventurer.")
        );
    }

    static {
        NPCAction.register(7673, "talk-to", Solztun::dialogue);
        ItemNPCAction.register(Items.SKULL_SCEPTRE, 7673, (player, item, npc) -> {
            imbue(player, npc);
        });
    }
}
