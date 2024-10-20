package io.ruin.model.entity.npc.actions.varrock;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.handlers.makeover.MakeoverInterface;
import io.ruin.model.inter.handlers.makeover.MakeoverType;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/16/2023
 */
public class Thessalia {

    private static final int PRICE = 0;

    private static void giveFrogReward(Player player, NPC npc, int... itemIds) {
        if (!player.getInventory().hasId(Items.FROG_TOKEN)) {
            player.dialogue(new NPCDialogue(npc, "You need a frog token to purchase that."));
            return;
        }
        if (!player.getInventory().hasFreeSlots(itemIds.length - 1)) {
            player.dialogue(new NPCDialogue(npc, "You need " + (itemIds.length - 1) + " free slots to claim those items."));
            return;
        }
        player.getInventory().remove(Items.FROG_TOKEN, 1);
        for (int itemId : itemIds) {
            player.getInventory().add(itemId, 1);
        }
    }

    private static void dialogue(Player player, NPC npc) {
        if (player.getInventory().hasId(Items.FROG_TOKEN)) {
            player.dialogue(
                    new PlayerDialogue("I have a frog token..."),
                    new NPCDialogue(npc, "That entitles you to a free costume! Do you want a frog mask, a royal tunic & leggings or a royal blouse & skirt? I've also got some books a little red imp gave me."),
                    new OptionsDialogue(
                            new Option("A frog mask, please!", new PlayerDialogue("A frog mask, please!"), new ActionDialogue(() -> giveFrogReward(player, npc, Items.FROG_MASK))),
                            new Option("A royal tunic & leggings, please.", new PlayerDialogue("A royal tunic & leggings, please."), new ActionDialogue(() -> giveFrogReward(player, npc, 6184, 6185))),
                            new Option("A royal blouse & skirt, please.", new PlayerDialogue("A royal blouse & skirt, please."), new ActionDialogue(() -> giveFrogReward(player, npc, 6186, 6187))),
                            new Option("I'll take a book, thanks.", new PlayerDialogue("I'll take the book, thanks."), new ActionDialogue(() -> giveFrogReward(player, npc, Items.BOOK_OF_KNOWLEDGE))),
                            new Option("Maybe another time.", new PlayerDialogue("Maybe another time."))
                    )
            );
            return;
        }
        player.dialogue(
                new NPCDialogue(npc, "Do you want to buy any fine clothes?"),
                new OptionsDialogue(
                        new Option("What have you got?",
                                new PlayerDialogue("What have you got?"),
                                new NPCDialogue(npc, "Well, I have a number of fine pieces of clothing on sale or, if you prefer, I can offer you an exclusive, total-clothing makeover?"),
                                new OptionsDialogue(
                                        new Option("Tell me more about this makeover.",
                                                new PlayerDialogue("Tell me more about this makeover."),
                                                new NPCDialogue(npc, "Certainly!"),
                                                new NPCDialogue(npc, "Here at Thessalia's fine clothing boutique, we offer a unique service where we will totally revamp your outfit to your choosing."),
                                                new NPCDialogue(npc, (PRICE == 0 ? "It's on the house, completely free!" : "It'll cost you " + PRICE + " coins.") + " Tired of always wearing the same old outfit, day in, day out? This is the service for you!"),
                                                new NPCDialogue(npc, "So what do you say? Interested? We can change either your top or your legwear!"),
                                                new OptionsDialogue(
                                                        new Option("I'd like to change my top please.", () -> MakeoverInterface.open(player, MakeoverType.TOP, npc)),
                                                        new Option("I'd like to change my legwear please.", () -> MakeoverInterface.open(player, MakeoverType.LEGS, npc))
                                                )
                                        ),
                                        new Option("I'd just like to buy some clothes.", () -> npc.openShop(player)),
                                        new Option("No, thank you.", new PlayerDialogue("No, thank you."), new NPCDialogue(npc, "Well, please return if you change your mind."))
                                )
                        ),
                        new Option("No, thank you.", new PlayerDialogue("No, thank you."), new NPCDialogue(npc, "Well, please return if you change your mind."))
                )
        );
    }

    static {
        NPCAction.registerIncludeVariants(534, "talk-to", Thessalia::dialogue);
        NPCAction.registerIncludeVariants(534, "makeover", ((player, npc) -> player.dialogue(new OptionsDialogue("What would you like to change?",
                new Option("Topwear",  () -> MakeoverInterface.open(player, MakeoverType.TOP, npc)),
                new Option("Legwear", () -> MakeoverInterface.open(player, MakeoverType.LEGS, npc))
        ))));
    }
}
