package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.Title;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.handlers.makeover.BodyTypeInterface;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class MakeoverMage {

    private static void open(Player player, NPC npc) {
        if(!player.getEquipment().isEmpty()) {
            player.dialogue(new NPCDialogue(npc, "Please remove what your equipment before we proceed with the makeover."));
            return;
        }
        player.openInterface(InterfaceType.MAIN, Interface.MAKE_OVER_MAGE);
    }

    private static List<Option> getOptions(Player player, NPC npc) {
        List<Option> options = new ArrayList<>();
        options.add(new Option(unlockedFormat(player, Skins.BLACK) + "Midnight Black - $50", () -> equipSkin(player, Skins.BLACK)));
        options.add(new Option(unlockedFormat(player, Skins.WHITE) + "Moonlight White - $50", () -> equipSkin(player, Skins.WHITE)));
        options.add(new Option(unlockedFormat(player, Skins.SWAMP_GREEN) + "Swamp Green - 20$", () -> equipSkin(player, Skins.SWAMP_GREEN)));
        options.add(new Option(unlockedFormat(player, Skins.ZOMBIE_BLUE) + "Zombie Blue - 20$", () -> equipSkin(player, Skins.ZOMBIE_BLUE)));
        options.add(new Option(unlockedFormat(player, Skins.PURPLE) + "Putrid Purple - 20$", () -> equipSkin(player, Skins.PURPLE)));
        return options;
    }

    public static void openSkinUnlocks(Player player, NPC npc) {
        OptionScroll.open(player, "Select a skin color you'd like to use", false, getOptions(player, npc));
    }

    public static void equipSkin(Player player, Skins skin) {
        if (unlocked(player, skin)) {
            player.getAppearance().colors[4] = skin.getColor();
            player.getAppearance().update();
            player.sendMessage("You are now using the " + skin.getName() + " skin!");
        } else {
            player.sendMessage("You have not yet unlocked the " + skin.getName() + " skin!");
        }
    }

    private static String unlockedFormat(Player player, Skins skin) {
        if(!unlocked(player, skin))
            return "<str>";
        return "";
    }

    @RequiredArgsConstructor
    @Getter
    public enum Skins {
        PURPLE("Putrid Purple", 12),
        SWAMP_GREEN("Swamp Green", 8),
        ZOMBIE_BLUE("Zombie Blue", 11),
        BLACK("Midnight Black", 9),
        WHITE("Moonlight White", 10);

        private final String name;
        private final int color;
    }
    private static boolean unlocked(Player player, Skins skin) {
        if (skin == Skins.PURPLE && player.hasAttribute("PURPLE_SKIN"))
            return true;
        if (skin == Skins.SWAMP_GREEN && player.hasAttribute("GREEN_SKIN"))
            return true;
        if (skin == Skins.ZOMBIE_BLUE && player.hasAttribute("BLUE_SKIN"))
            return true;
        if (skin == Skins.BLACK && player.hasAttribute("BLACK_SKIN"))
            return true;
        if (skin == Skins.WHITE && player.hasAttribute("WHITE_SKIN"))
            return true;
        return false;

    }

    private static final int PRICE = 0;

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Hello there! I am known as the Makeover Mage! I have spent many years researching magics that can change your physical appearance!"),
                new NPCDialogue(npc, "I can alter your physical form with my magic for " + (PRICE == 0 ? "free!" : NumberUtils.formatNumber(PRICE) + " coins.")
                        + " Would you like me to perform my magics upon you?"),
                new ActionDialogue(() -> options(player, npc))
        );
    }

    private static void options(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Tell me more about this 'make-over'.",
                                new PlayerDialogue("Player: Tell me more about this 'make-over'."),
                                new NPCDialogue(npc, "Why, of course! Basically, and I will try and explain this so that you will understand it correctly,"),
                                new NPCDialogue(npc, "I use my secret magical technique to melt your body down into a puddle of its elements."),
                                new NPCDialogue(npc, "When I have broken down all trace of your body, I then rebuild it into the form I am thinking of!"),
                                new NPCDialogue(npc, "Or, you know, somewhere vaguely close enough anyway."),
                                new PlayerDialogue("Uh... that doesn't sound particularly safe to me..."),
                                new NPCDialogue(npc, "It's as safe as houses! Why, I have only had thirty-six major accidents this month!"),
                                new NPCDialogue(npc, "So what do you say? Feel like a change? There's no fee."),
                                new ActionDialogue(() -> options(player, npc))
                        ),
                        new Option("Sure, I'll have a makeover.",
                                new PlayerDialogue("Sure, I'll have a makeover."),
                                new NPCDialogue(npc, "Good choice, good choice. You wouldn't want to carry on looking like that, I'm sure!"),
                                new ActionDialogue(() -> BodyTypeInterface.open(player))
                        ),
                        new Option("Cool amulet! Can I have one?",
                                new PlayerDialogue("Cool amulet! Can I have one?"),
                                new NPCDialogue(npc, "No problem, but please remember that the amulet I will sell you is only a copy of my own. It contains no magical powers, and as such will only cost you 100 coins."),
                                new ActionDialogue(() -> {
                                    if (player.getInventory().getAmount(995) < 100) {
                                        player.dialogue(
                                                new PlayerDialogue("Oh, I don't have enough money for that."),
                                                new NPCDialogue(npc, "Anyway, would you like me to alter your physical form?"),
                                                new ActionDialogue(() -> options(player, npc))
                                        );
                                    } else {
                                        if (!player.getInventory().hasFreeSlots(1) && player.getInventory().getAmount(995) != 100) {
                                            player.dialogue(
                                                    new PlayerDialogue("I don't have room to hold it. Maybe another time."),
                                                    new NPCDialogue(npc, "Anyway, would you like me to alter your physical form?"),
                                                    new ActionDialogue(() -> options(player, npc))
                                            );
                                        } else {
                                            player.dialogue(
                                                    new OptionsDialogue(
                                                            new Option("Sure, here you go.",
                                                                    new PlayerDialogue("Sure, here you go."),
                                                                    new ActionDialogue(() -> {
                                                                        player.getInventory().remove(995, 100);
                                                                        player.getInventory().add(Items.YIN_YANG_AMULET);
                                                                        player.getTaskManager().doLookupByUUID(436, 1); // Purchase a Yin Yang Amulet From the Make-Over Mage
                                                                        player.dialogue(
                                                                                new ItemDialogue().one(Items.YIN_YANG_AMULET, "You receive an amulet in exchange for 100 coins."),
                                                                                new NPCDialogue(npc, "Anyway, would you like me to alter your physical form?"),
                                                                                new ActionDialogue(() -> options(player, npc))
                                                                        );
                                                                    })
                                                            ),
                                                            new Option("No way! That's far too expensive.",
                                                                    new PlayerDialogue("No way! That's far too expensive."),
                                                                    new NPCDialogue(npc, "That's fair enough, my jewellery is not to everyone's taste."),
                                                                    new NPCDialogue(npc, "Anyway, would you like me to alter your physical form?"),
                                                                    new ActionDialogue(() -> options(player, npc))

                                                            )
                                                    )
                                            );
                                        }
                                    }
                                })
                        ),
                        new Option("No thanks.",
                                new PlayerDialogue("No thanks."),
                                new NPCDialogue(npc, "Ehhh... suit yourself.")
                        )
                )
        );
    }

    static {
        NPCAction.register(1307, "talk-to", MakeoverMage::dialogue);
        NPCAction.register(1307, "makeover", (player, npc) -> BodyTypeInterface.open(player));
        NPCAction.register(1306, "talk-to", MakeoverMage::dialogue);
        NPCAction.register(1306, "makeover", (player, npc) -> BodyTypeInterface.open(player));

        SpawnListener.register(1307, (npc -> {
            World.startEvent(e -> {
                while (true) {
                    e.delay(17);
                    npc.forceText(Random.rollDie(2, 1) ? "Ahah!" : "Ooh!");
                    npc.graphics(86);
                    npc.transform(npc.getId() == 1306 ? 1307 : 1306);
                }
            });
        }));
        //NPCAction.register(1307, "change-looks", MakeoverMage::open);
        //NPCAction.register(1307, "skin-unlocks", MakeoverMage::openSkinUnlocks);
        //NPCAction.register(1307, "title-unlocks", MakeoverMage::titles);
    }

    private static void titles(Player player, NPC npc) {
        player.dialogue(new OptionsDialogue(
                new Option("View Unlocked Titles", () -> Title.openSelection(player, false)),
                new Option("View All Titles", () -> Title.openSelection(player, true)),
                new Option("Remove my Title", () -> Title.clearTitle(player)),
                new Option("Cancel", () -> {})));
    }
}
