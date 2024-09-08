package io.ruin.model.activities.combat.pestcontrol;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/7/2024
 */
public class EliteVoidKnight {

    private static void dialogue(Player player, NPC npc) {
        npc.faceTemp(player);
        player.dialogue(
                new NPCDialogue(npc, "Do you wish me to upgrade any Void Knight armour for you?"),
                new OptionsDialogue(
                        new Option("What does that do?",
                                new PlayerDialogue("What does that do?"),
                                new NPCDialogue(npc, "The top and robes can be upgraded, separately, to give each piece a +3 prayer bonus. It costs 200 Commendation points to upgrade each item.")),
                        new Option("Yes please.",
                                new PlayerDialogue("Yes please."),
                                new NPCDialogue(npc, "Very well. Hand me the top or robe you wish me to upgrade. It costs 200 Commendation points to upgrade each item.")),
                        new Option("No thanks.",
                                new PlayerDialogue("No thanks."))
                )
        );
    }

    private static void upgrade(Player player, Item item, NPC npc) {
        if (item.getId() != Items.VOID_KNIGHT_TOP && item.getId() != Items.VOID_KNIGHT_ROBE) return;
        if (player.getAttributeIntOrZero("PEST_POINTS") < 200) {
            player.dialogue(new NPCDialogue(npc, "You need 200 Commendation points to upgrade that."));
            return;
        }
        int newId = item.getId() == Items.VOID_KNIGHT_TOP ? Items.ELITE_VOID_TOP : Items.ELITE_VOID_ROBE;
        player.dialogue(
                new OptionsDialogue("Pay 200 Commendation points to upgrade it?",
                        new Option("Okay", () -> {
                            item.setId(newId);
                            player.incrementNumericAttribute("PEST_POINTS", -200);
                        }),
                        new Option("Cancel")
                )
        );
    }

    private static void useItemOn(Player player, Item item, NPC npc) {
        if (item.getId() == Items.VOID_KNIGHT_TOP || item.getId() == Items.VOID_KNIGHT_ROBE) {
            upgrade(player, item, npc);
        } else if (item.getId() == 26463 || item.getId() == 26465) {
            player.dialogue(new NPCDialogue(npc, "I'm afraid that cannot be upgraded."));
        } else {
            player.dialogue(new NPCDialogue(npc, "No thank you."));
        }
    }

    static {
        NPCAction.register(5513, "talk-to", EliteVoidKnight::dialogue);
        ItemNPCAction.register(5513, EliteVoidKnight::useItemOn);
    }
}
