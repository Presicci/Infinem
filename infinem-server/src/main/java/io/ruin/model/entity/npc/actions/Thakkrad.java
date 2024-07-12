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
 * Created on 7/12/2024
 */
public class Thakkrad {

    private static final int COST = 5;

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Hey there, do you need anything?"),
                new OptionsDialogue(
                        new Option("Can you cure my yak-hides?",
                                new PlayerDialogue("Can you cure my yak-hides?"),
                                new NPCDialogue(npc, "Sure, It'll cost you 5 coins for each hide you want cured."),
                                new ActionDialogue(() -> cure(player, npc))),
                        new Option("Not right now.", new PlayerDialogue("Not right now."))
                )
        );
    }

    private static void cure(Player player, NPC npc) {
        if (!player.getInventory().hasId(Items.YAKHIDE)) {
            player.dialogue(new NPCDialogue(npc, "You don't have any hide for me to cure."));
            return;
        }
        SkillDialogue.make(player, new SkillItem(Items.CURED_YAKHIDE).addAction((p, amount, event) -> cure(player, npc, amount)));
    }

    private static void cure(Player player, NPC npc, int amount) {
        if (player.getInventory().getAmount(995) < COST) {
            player.dialogue(new NPCDialogue(npc, "I'll need 5 coins for each hide you want cured."));
            return;
        }
        List<Item> hides = player.getInventory().collectItems(Items.YAKHIDE);
        int coins = player.getInventory().getAmount(995);
        int amountToCure = Math.min(hides.size(), amount);
        amountToCure = Math.min(amountToCure, coins / (amountToCure * COST));
        for (int index = 0; index < amountToCure; index++) {
            Item seed = hides.get(index);
            if (seed == null) break;
            seed.setId(Items.CURED_YAKHIDE);
            player.getInventory().remove(995, COST);
        }
        player.dialogue(new NPCDialogue(npc, "Here you are."));
    }

    static {
        NPCAction.register(1881, "talk-to", Thakkrad::dialogue);
        NPCAction.register(1881, "craft-goods", Thakkrad::cure);
    }
}
