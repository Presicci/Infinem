package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/24/2023
 */
public class Leon {

    public static void craftAmmo(Player player) {
        SkillDialogue.make(player,
                new SkillItem(Items.KEBBIT_BOLTS).addAction((p, amount, event) -> {
                    if (!player.getInventory().contains(Items.KEBBIT_SPIKE) || player.getInventory().getAmount(995) < 20) {
                        player.dialogue(new ItemDialogue().two(Items.KEBBIT_SPIKE, 617, "You need 1 kebbit spike and 20 coins to make 6 kebbit bolts."));
                        return;
                    }
                    int amt = Math.min(amount, Math.min(player.getInventory().getAmount(Items.KEBBIT_SPIKE), player.getInventory().getAmount(995) / 20));
                    player.getInventory().remove(Items.KEBBIT_SPIKE, amt);
                    player.getInventory().remove(995, amt * 20);
                    player.getInventory().add(Items.KEBBIT_BOLTS, amt * 6);
                    if (amt == 1)
                        player.dialogue(new ItemDialogue().one(Items.KEBBIT_BOLTS, "You hand the weapon designer one spike and 20 coins. In return he presents you with 6 bolts."));
                    else
                        player.dialogue(new ItemDialogue().one(Items.KEBBIT_BOLTS, "You hand the weapon designer " + amt + " spikes and " + amt * 20 + " coins. In return he presents you with " + amt * 6 + " bolts."));
                }),
                new SkillItem(Items.LONG_KEBBIT_BOLTS).addAction((p, amount, event) -> {
                    if (!player.getInventory().contains(Items.LONG_KEBBIT_SPIKE) || player.getInventory().getAmount(995) < 40) {
                        player.dialogue(new ItemDialogue().two(Items.LONG_KEBBIT_SPIKE, 617, "You need 1 long kebbit spike and 40 coins to make 6 long kebbit bolts."));
                        return;
                    }
                    int amt = Math.min(amount, Math.min(player.getInventory().getAmount(Items.LONG_KEBBIT_SPIKE), player.getInventory().getAmount(995) / 40));
                    player.getInventory().remove(Items.LONG_KEBBIT_SPIKE, amt);
                    player.getInventory().remove(995, amt * 40);
                    player.getInventory().add(Items.LONG_KEBBIT_BOLTS, amt * 6);
                    if (amt == 1)
                        player.dialogue(new ItemDialogue().one(Items.LONG_KEBBIT_BOLTS, "You hand the weapon designer one spike and 40 coins. In return he presents you with 6 bolts."));
                    else
                        player.dialogue(new ItemDialogue().one(Items.LONG_KEBBIT_BOLTS, "You hand the weapon designer " + amt + " spikes and " + amt * 40 + " coins. In return he presents you with " + amt * 6 + " bolts."));
                }));
    }

    static {
        NPCAction.register(1502, "ammo", (player, npc) -> craftAmmo(player));
    }
}
