package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;

import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/7/2024
 */
public class Eluned {

    private static final int COST = 500;

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Hey there, do you need anything?"),
                new OptionsDialogue(
                        new Option("Can you enchant my crystal teleport crystals?",
                                new PlayerDialogue("Can you enchant my crystal teleport seeds?"),
                                new NPCDialogue(npc, "Sure, It'll cost you 500 coins for each crystal you want enchanted."),
                                new ActionDialogue(() -> enchant(player, npc))),
                        new Option("Not right now.", new PlayerDialogue("Not right now."))
                )
        );
    }

    private static void enchant(Player player, NPC npc) {
        if (!player.getInventory().hasId(6103)) {
            player.dialogue(new NPCDialogue(npc, "You don't have any teleport crystals for me to enchant."));
            return;
        }
        SkillDialogue.make(player, new SkillItem(Items.TELEPORT_CRYSTAL_5).addAction((p, amount, event) -> enchant(player, npc, amount)));
    }

    private static void enchant(Player player, NPC npc, int amount) {
        if (player.getInventory().getAmount(995) < COST) {
            player.dialogue(new NPCDialogue(npc, "I'll need 500 coins for each crystal you want enchanted."));
            return;
        }
        player.sendMessage(amount +  "");
        List<Item> seeds = player.getInventory().collectItems(6103);
        player.sendMessage(seeds.size() +  "");
        int coins = player.getInventory().getAmount(995);
        int amountToEnchant = Math.min(seeds.size(), amount);
        player.sendMessage((coins / (amountToEnchant * COST)) +  "");
        amountToEnchant = Math.min(amountToEnchant, coins / (amountToEnchant * COST));
        for (int index = 0; index < amountToEnchant; index++) {
            Item seed = seeds.get(index);
            if (seed == null) break;
            seed.setId(Items.TELEPORT_CRYSTAL_5);
            player.getInventory().remove(995, COST);
        }
        player.dialogue(new NPCDialogue(npc, "Here you are!"));
    }

    static {
        NPCAction.register(9145, "talk-to", Eluned::dialogue);
        NPCAction.register(9145, "enchant", Eluned::enchant);
    }
}
