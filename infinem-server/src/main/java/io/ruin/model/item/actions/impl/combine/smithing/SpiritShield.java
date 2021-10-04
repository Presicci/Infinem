package io.ruin.model.item.actions.impl.combine.smithing;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

public class SpiritShield {

    private static final int HOLY_ELIXIR = 12833;
    private static final int SPIRIT_SHIELD = 12829;
    private static final int BLESSED_SPIRIT_SHIELD = 12831;

    private static final int ARCANE_SIGIL = 12827;
    private static final int SPECTRAL_SIGIL = 12823;
    private static final int ELYSIAN_SIGIL = 12819;
    private static final int DIVINE_SIGIL = 30187;

    private static void attachSigil(Player player, Item item, Object object) {
        if(player.getStats().get(StatType.Prayer).currentLevel < 90 || player.getStats().get(StatType.Smithing).currentLevel < 85) {
            player.sendMessage("You don't have the skills required to make this. You need 85 Smithing and 90 Prayer.");
            return;
        }
        if(!player.getInventory().hasId(BLESSED_SPIRIT_SHIELD)) {
            player.sendMessage("You don't have a spirit shield to attach your " + item.getDef().name + " to.");
            return;
        }

        Item hammer = player.getInventory().findItem(Tool.HAMMER);
        if(hammer == null) {
            player.sendMessage("You need a hammer to do this.");
            return;
        }

        player.startEvent(event -> {
            player.lock();
            player.animate(898);
            event.delay(6);
            player.animate(898);
            event.delay(6);
            player.getInventory().remove(BLESSED_SPIRIT_SHIELD, 1);
            player.dialogue(new ItemDialogue().one(item.getId() == DIVINE_SIGIL ? 30191 : item.getId() - 2, "You successfully combine the " + item.getDef().name + " with the shield."));
            player.getStats().addXp(StatType.Smithing, 1800, true);
            item.setId(item.getId() == DIVINE_SIGIL ? 30191 : item.getId() - 2);
            player.unlock();
        });
    }

    private static void attachSigilNPC(Player player, NPC npc, Item sigil) {
        int shieldId = sigil.getId() == DIVINE_SIGIL ? 30191 : sigil.getId() - 2;
        player.dialogue(
                new NPCDialogue(npc.getId(), "Would you like me to make " + ItemDef.get(shieldId).descriptiveName
                        + " for you? It is a lot of work and would cost you 1,500,000 coins."),
                new OptionsDialogue(
                        new Option("Yes! (pay 1,500,000 coins)", () -> {
                            if (player.getInventory().getAmount(995) < 1500000) {
                                player.dialogue(new NPCDialogue(npc.getId(), "It appears you do not have enough money to pay me for my work."));
                                return;
                            } else {
                                if (player.getInventory().hasId(sigil.getId()) && player.getInventory().hasId(BLESSED_SPIRIT_SHIELD)) {
                                    player.getInventory().remove(sigil.getId(), 1);
                                    player.getInventory().remove(BLESSED_SPIRIT_SHIELD, 1);
                                    player.getInventory().remove(995, 1500000);
                                    player.getInventory().add(shieldId);
                                    player.dialogue(
                                            new ItemDialogue().one(shieldId, "Abbot Langley carefully attaches the sigil to the spirit shield.."),
                                            new NPCDialogue(npc.getId(), "There you go adventurer, may it serve you well in battle."),
                                            new PlayerDialogue("Thank you!"));

                                } else {
                                    player.dialogue(
                                            new NPCDialogue(npc.getId(), "You do not have all the parts that are required to make the shield, come back when you do.")
                                    );
                                }
                            }
                        }),
                        new Option("Nope.")
                )
        );
    }

    static {
        /**
         * Holy spirit shield
         */
        ItemItemAction.register(HOLY_ELIXIR, SPIRIT_SHIELD, (player, primary, secondary) -> {
            if(player.getStats().get(StatType.Prayer).currentLevel < 85) {
                player.sendMessage("You need 85 prayer to bless the spirit shield.");
                return;
            }
            primary.remove();
            secondary.remove();
            player.getInventory().add(BLESSED_SPIRIT_SHIELD, 1);
            player.dialogue(new ItemDialogue().one(BLESSED_SPIRIT_SHIELD, "The spirit shield glows an eerie holy glow."));
        });

        /*
         * Sigils attaching to spirit shield
         */
        int[] sigils = {ARCANE_SIGIL, SPECTRAL_SIGIL, ELYSIAN_SIGIL, DIVINE_SIGIL};
        for (int itemID : sigils) {
            ItemObjectAction.register(itemID, "anvil", SpiritShield::attachSigil);
        }
    }
}
