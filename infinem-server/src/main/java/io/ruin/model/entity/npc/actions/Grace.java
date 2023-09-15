package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.AttributeKey;
import io.ruin.cache.Color;
import io.ruin.model.content.scroll.ScrollingScroll;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Grace {
    static {
        NPCAction.register(5919, "talk-to", (player, npc) -> player.dialogue(new NPCDialogue(npc, "Hello, " + player.getName() + ". Would you like to see the mark exchange, or learn more about marks of grace?"),
                new OptionsDialogue(
                        new Option("Open shop", () -> npc.openShop(player)),
                        new Option("Learn more", () -> {
                            if(!player.insideWildernessAgilityCourse) {
                                player.dialogue(new NPCDialogue(npc, "While practicing on rooftop agility courses, you will occasionally encounter marks of grace.<br>" +
                                        "Bring them to me and we can trade.<br>My outfit will allow you to run longer distances and recover more quickly."));
                            } else {
                                player.dialogue(new NPCDialogue(npc, "While completing this wilderness course, you'll be rewarded anywhere from 1 to 3 marks of grace along with a little blood money."),
                                        new NPCDialogue(npc, "You can spend those marks inside my shop for Graceful clothing. If you kill somebody in here, I'll reward you with 50k agility experience and"),
                                        new NPCDialogue(npc, "10 marks of grace. Be careful though, as this place gets quite dangerous!"),
                                        new PlayerDialogue("Thank you, Grace! I'll keep an eye out."));
                            }
                        }))
        ));
        NPCAction.register(5919, "toggle counter", ((player, npc) -> {
            if (player.hasAttribute(AttributeKey.HIDE_AGILITY_COUNT)) {
                player.removeAttribute(AttributeKey.HIDE_AGILITY_COUNT);
                player.dialogue(new MessageDialogue("Lap counters will now be displayed after completing an agility course lap."));
            } else {
                player.putAttribute(AttributeKey.HIDE_AGILITY_COUNT, 1);
                player.dialogue(new MessageDialogue("Lap counters will " + Color.RED.wrap("NOT") + " be displayed after completing an agility course lap."));
            }
        }));
        NPCAction.register(5919, "view laps", ((player, npc) -> {
            ScrollingScroll scroll = new ScrollingScroll("Agility Course Lap Counts",
                    "~~~Rooftop Agility Laps~~~",
                    "Draynor Village - " + PlayerCounter.DRAYNOR_ROOFTOP.get(player) + " Laps",
                    "Al Kharid - " + PlayerCounter.ALKHARID_ROOFTOP.get(player) + " Laps",
                    "Varrock - " + PlayerCounter.VARROCK_ROOFTOP.get(player) + " Laps",
                    "Canifis - " + PlayerCounter.CANIFIS_ROOFTOP.get(player) + " Laps",
                    "Falador - " + PlayerCounter.FALADOR_ROOFTOP.get(player) + " Laps",
                    "Seers' Village - " + PlayerCounter.SEERS_ROOFTOP.get(player) + " Laps",
                    "Pollnivneach - " + PlayerCounter.POLLNIVNEACH_ROOFTOP.get(player) + " Laps",
                    "Rellekka - " + PlayerCounter.RELLEKKA_ROOFTOP.get(player) + " Laps",
                    "Ardougne - " + PlayerCounter.ARDOUGNE_ROOFTOP.get(player) + " Laps",
                    "~~~Miscellaneous Courses~~~",
                    "Agility Pyramid - " + PlayerCounter.AGILITY_PYRAMID.get(player) + " Laps",
                    "Gnome Agility - " + PlayerCounter.GNOME_STRONGHOLD_COURSE.get(player) + " Laps",
                    "Barbarian Outpost Agility - " + PlayerCounter.BARBARIAN_COURSE.get(player) + " Laps",
                    "Wilderness Agility - " + PlayerCounter.WILDERNESS_COURSE.get(player) + " Laps",
                    "Prifddinas Agility - " + PlayerCounter.PRIFDDINAS_COURSE.get(player) + " Laps"
            );
            scroll.open(player);
        }));
    }
}
