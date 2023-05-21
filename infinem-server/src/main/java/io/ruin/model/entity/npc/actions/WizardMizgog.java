package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Inventory;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/21/2023
 */
public class WizardMizgog {

    private static final int WIZARD_MIZGOG = 7746;

    private static void talkTo(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Do you know any interesting spells you could teach me?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Do you know any interesting spells you could teach me?"),
                                    new NPCDialogue(WIZARD_MIZGOG, "I don't think so, the type of magic I study involves years of meditation and research.")
                            );
                        }),
                        new Option("I heard you have some fancy schmancy amulets. Can I have one?", () -> amuletOption(player, npc))
                )
        );
    }

    private static void amuletOption(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("I heard you have some fancy schmancy amulets. Can I have one?"),
                new NPCDialogue(WIZARD_MIZGOG, "I have a few spare. I'd like one of each coloured bead in return, though! Black, white, yellow and red."),
                new ActionDialogue(() -> {
                    Inventory inventory = player.getInventory();
                    if (inventory.hasId(Items.BLACK_BEAD) && inventory.hasId(Items.WHITE_BEAD) && inventory.hasId(Items.YELLOW_BEAD) && inventory.hasId(Items.RED_BEAD)) {
                        player.dialogue(
                                new OptionsDialogue(
                                        new Option("I have them with me!", () -> {
                                            player.dialogue(
                                                    new PlayerDialogue("I have them with me! Here you go."),
                                                    new ActionDialogue(() -> {
                                                        player.getInventory().remove(Items.BLACK_BEAD, 1);
                                                        player.getInventory().remove(Items.WHITE_BEAD, 1);
                                                        player.getInventory().remove(Items.YELLOW_BEAD, 1);
                                                        player.getInventory().remove(Items.RED_BEAD, 1);
                                                        player.getInventory().add(Items.AMULET_OF_ACCURACY);
                                                        player.dialogue(
                                                                new ItemDialogue().one(Items.AMULET_OF_ACCURACY, "Mizgog removes the beads from your backpack and gives you an Amulet of accuracy."),
                                                                new PlayerDialogue("Thanks, Mizgog!"),
                                                                new PlayerDialogue("What are you going to do with all of these beads, anyway?"),
                                                                new NPCDialogue(WIZARD_MIZGOG, "You don't want to know. Take care!")
                                                        );
                                                    })
                                            );
                                        }),
                                        new Option("Maybe later.", () -> {
                                            player.dialogue(
                                                    new PlayerDialogue("Maybe later."),
                                                    new NPCDialogue(WIZARD_MIZGOG, "Perhaps some other time, then.")
                                            );
                                        })
                                )
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("I don't have them all on me at the moment. I'll come back when I have them!"),
                                new NPCDialogue(WIZARD_MIZGOG, "Very well. See you soon!")
                        );
                    }
                })
        );
    }

    static {
        NPCAction.register(WIZARD_MIZGOG, "talk-to", WizardMizgog::talkTo);
    }
}
