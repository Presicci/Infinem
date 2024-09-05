package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.services.Store;


public class DonationManager {

    public static final String STORE_URL = World.type.getWebsiteUrl() + "/store";

    private static void talkTo(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("How do I donate?", () -> player.dialogue(
                                new PlayerDialogue("How do I donate?").animate(588),
                                new NPCDialogue(npc, "Donations are currently being taken through the website on the Store page. Simply visit " + World.type.getWebsiteUrl() + " and head to the store."),
                                new NPCDialogue(npc, "As it stands, you can make payments through Paypal!"),
                                new NPCDialogue(npc, "Would you like me to open the page for you?"),
                                new OptionsDialogue(
                                        new Option("Yes", () -> player.openUrl(World.type.getWorldName() + " Store", STORE_URL)),
                                        new Option("No", player::closeDialogue)
                                )
                        )),
                        new Option("What are the different tiers of donators?", () -> player.dialogue(
                                new PlayerDialogue("What are the different available tiers of donation?"),
                                new NPCDialogue(npc, "Ahh, yes! You want to know about the different titles. Of course, of course."),
                                new NPCDialogue(npc, "Right now, there's 4 available tiers, ranging from all different amounts. As you spend more, you'll automatically increase in title. I am in the highest possible tier, but that's to be expected."),
                                new NPCDialogue(npc, "There's the Sapphire Donator: " + PlayerGroup.SAPPHIRE.tag() + "."),
                                new NPCDialogue(npc, "Next comes the Emerald Donator: " + PlayerGroup.EMERALD.tag() + "."),
                                new NPCDialogue(npc, "Following that, there's the Ruby Donator: " + PlayerGroup.RUBY.tag() + "."),
                                new NPCDialogue(npc, "And finally, Diamond Donator: " + PlayerGroup.DIAMOND.tag() + "."),
                                new NPCDialogue(npc, "There may be more ranks to come."),
                                new PlayerDialogue("Awesome, thanks!"))
                        ),
                        new Option("How many credits have I purchased?", () -> player.dialogue(
                                new PlayerDialogue("How many credits have I purchased?"),
                                new NPCDialogue(npc, "You have purchased a total of " + NumberUtils.formatNumber(player.storeAmountSpent) + " credits.")
                        )),
                        new Option("Claim rewards", () -> Store.claim(player, npc))
                )
        );
    }

    static {
        NPCAction.register(15001, "talk-to", DonationManager::talkTo);
        NPCAction.register(15001, "claim-rewards", Store::claim);
    }

    public static PlayerGroup getGroup(Player player) {
        if(player.storeAmountSpent >= 1000)
            return  PlayerGroup.ZENYTE;
        if(player.storeAmountSpent >= 500)
            return PlayerGroup.ONYX;
        if(player.storeAmountSpent >= 250)
            return PlayerGroup.DRAGONSTONE;
        if(player.storeAmountSpent >= 100)
            return PlayerGroup.DIAMOND;
        if(player.storeAmountSpent >= 50)
            return PlayerGroup.RUBY;
        if(player.storeAmountSpent >= 25)
            return PlayerGroup.EMERALD;
        if(player.storeAmountSpent >= 5)
            return PlayerGroup.SAPPHIRE;
        return null;
    }

}