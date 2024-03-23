package io.ruin.model.item.actions.impl.combine.smithing;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.NpcID;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DraconicVisage {
    SHIELD(Items.DRAGONFIRE_SHIELD, Items.DRACONIC_VISAGE, Items.ANTIDRAGON_SHIELD),
    WARD(22003, 22006, Items.ANTIDRAGON_SHIELD);

    private final int product, primary, secondary;

    private void make(Player player) {
        Item leftHalf = player.getInventory().findItem(primary);
        Item rightHalf = player.getInventory().findItem(secondary);
        if (!player.getStats().check(StatType.Smithing, 90, "forge this")) return;
        if(leftHalf == null || rightHalf == null) {
            player.dialogue(new ItemDialogue().two(primary, secondary, "You need a draconic visage and " + ItemDefinition.get(secondary).descriptiveName + " to forge " + ItemDefinition.get(product).descriptiveName + "."));
            return;
        }
        if(!SmithBar.hasHammer(player)) {
            player.sendMessage("You need a hammer to forge the shield.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You start to hammer the shield...");
            player.animate(SmithBar.getHammerAnim(player));
            event.delay(6);
            if(player.getInventory().hasId(primary) && player.getInventory().hasId(secondary)) {
                player.getInventory().remove(primary, 1);
                player.getInventory().remove(secondary, 1);
                player.getInventory().add(product, 1);
                player.getStats().addXp(StatType.Smithing, 2000, true);
                player.sendMessage("You forge the visage to the shield.");
            }
            player.unlock();
        });
    }

    private static void makeStatic(Player player) {
        Item skeletal = player.getInventory().findItem(22006);
        Item draconic = player.getInventory().findItem(Items.DRACONIC_VISAGE);
        if (skeletal != null && draconic == null) {
            WARD.make(player);
        } else if (draconic != null && skeletal == null) {
            SHIELD.make(player);
        } else if (draconic != null) {
            player.dialogue(new MessageDialogue("You should use the visage for whichever shield you'd like to craft on the anvil."));
        } else {
            player.sendMessage("Nothing interesting happens.");
        }
    }

    private static void makeNPC(Player player, NPC npc, DraconicVisage shield) {
        player.dialogue(
                new NPCDialogue(npc.getId(), "Would you like me to forge " + ItemDefinition.get(shield.product).descriptiveName
                        + " for you? It is a lot of work and would cost you 1,250,000 coins."),
                new OptionsDialogue(
                        new Option("Yes! (pay 1,250,000 coins)", () -> {
                            if (player.getInventory().getAmount(995) < 1250000) {
                                player.dialogue(new NPCDialogue(npc.getId(), "It appears you do not have enough money to pay me for my work."));
                                return;
                            } else {
                                if (player.getInventory().hasId(shield.primary) && player.getInventory().hasId(shield.secondary)) {
                                    player.getInventory().remove(shield.primary, 1);
                                    player.getInventory().remove(shield.secondary, 1);
                                    player.getInventory().remove(995, 1250000);
                                    player.getInventory().add(shield.product, 1);
                                    player.dialogue(
                                            new ItemDialogue().one(shield.product, "Oziach takes his time carefully and expertly forging the visage and the shield together."),
                                            new NPCDialogue(npc.getId(), "There you go adventurer, may it serve you well in battle."),
                                            new PlayerDialogue("Thank you!"));

                                } else {
                                    player.dialogue(
                                            new NPCDialogue(npc.getId(), "You do not have all the parts that are required to forge the shield, come back when you do.")
                                    );
                                }
                            }
                        }),
                        new Option("Nope.")
                )
        );
    }

    static {
        ItemObjectAction.register(Items.ANTIDRAGON_SHIELD, "anvil", (player, item, obj) -> makeStatic(player));
        for (DraconicVisage shield : values()) {
            ItemObjectAction.register(shield.primary, "anvil", (player, item, obj) -> shield.make(player));
            ItemNPCAction.register(shield.primary, NpcID.OZIACH, (player, item, npc) -> makeNPC(player, npc, shield));
            ItemNPCAction.register(shield.secondary, NpcID.OZIACH, (player, item, npc) -> makeNPC(player, npc, shield));
        }
    }
}