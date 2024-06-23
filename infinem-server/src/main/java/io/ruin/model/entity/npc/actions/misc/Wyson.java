package io.ruin.model.entity.npc.actions.misc;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/22/2024
 */
public class Wyson {

    // Taking the 8% for an empty and giving it to ring nests
    public static final LootTable NESTS = new LootTable().addTable(1,
            new LootItem(22800, 1, 28),  // Seed nest
            new LootItem(5074, 1, 72)    // Ring nest
    );

    private static final int[] PARTS = { Items.MOLE_SKIN, Items.MOLE_SKIN_NOTE, Items.MOLE_CLAW, Items.MOLE_CLAW_NOTE };

    private static void handleTurnin(Player player, NPC npc) {
        Inventory inventory = player.getInventory();
        int partAmt = 0;
        for (int id : PARTS) {
            int amt = inventory.getAmount(id);
            if (amt <= 0) continue;
            partAmt += inventory.remove(id, amt);
        }
        int addedAmt = partAmt;
        if (partAmt > inventory.getFreeSlots()) {
            int rollAmt = Math.max(partAmt / 20, 1);
            while (partAmt > 0) {
                Item nest = NESTS.rollItem();
                int amt = Math.min(partAmt, rollAmt);
                player.getBank().add(nest.getId(), amt);
                partAmt -= amt;
            }
            player.dialogue(new NPCDialogue(npc, "I've sent " + addedAmt + " nests to your bank, enjoy."));
        } else {
            for (int index = 0; index < partAmt; index++) {
                Item nest = NESTS.rollItem();
                inventory.add(nest);
            }
            player.dialogue(new NPCDialogue(npc, "Here is " + addedAmt + " nests, enjoy."));
        }
    }

    private static void purchaseLeaves(Player player, NPC npc, int cost) {
        int addAmt = cost == 20 ? 2 : 1;
        Inventory inventory = player.getInventory();
        if (inventory.getAmount(995) < cost) {
            player.dialogue(new PlayerDialogue("I don't have enough coins to buy the leaves. I'll come back later."));
            return;
        }
        if (inventory.getAmount(995) != cost && !inventory.hasRoomFor(Items.WOAD_LEAF, addAmt)) {
            player.dialogue(new NPCDialogue(npc, "You don't have enough inventory space to hold the leaves, come back later."));
            return;
        }
        inventory.remove(995, cost);
        inventory.add(Items.WOAD_LEAF, addAmt);
        if (addAmt == 2) {
            player.dialogue(
                    new NPCDialogue(npc, "Here, have two, you're a generous person."),
                    new PlayerDialogue("Thanks.")
            );
        } else {
            player.dialogue(
                    new PlayerDialogue("Thanks."),
                    new NPCDialogue(npc, "I'll be around if you have any more gardening needs.")
            );
        }
    }

    private static void woadLeaves(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Yes please, I need woad leaves."),
                new NPCDialogue(npc, "How much are you willing to pay?"),
                new OptionsDialogue(
                        new Option("How about 5 coins?",
                                new PlayerDialogue("How about 5 coins?"),
                                new NPCDialogue(npc, "No no, that's far too little. Woad leaves are hard to get. I used to have plenty but someone kept stealing them off me.")
                        ),
                        new Option("How about 10 coins?",
                                new PlayerDialogue("How about 10 coins?"),
                                new NPCDialogue(npc, "No no, that's far too little. Woad leaves are hard to get. I used to have plenty but someone kept stealing them off me.")
                        ),
                        new Option("How about 15 coins?",
                                new PlayerDialogue("How about 15 coins?"),
                                new NPCDialogue(npc, "Mmmm... okay, that sounds fair."),
                                new ActionDialogue(() -> purchaseLeaves(player, npc, 15))
                        ),
                        new Option("How about 20 coins?",
                                new PlayerDialogue("How about 20 coins?"),
                                new NPCDialogue(npc, "Okay, that's more than fair."),
                                new ActionDialogue(() -> purchaseLeaves(player, npc, 20))
                        )
                )
        );
    }

    private static void notInterestedDialogue(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Sorry, but I'm not interested."),
                new NPCDialogue(npc, "Fair enough.")
        );
    }

    private static void dialogue(Player player, NPC npc) {
        boolean hasMoleParts = false;
        for (int id : PARTS) {
            if (player.getInventory().getAmount(id) > 0) {
                hasMoleParts = true;
                break;
            }
        }
        if (hasMoleParts) {
            player.dialogue(
                    new NPCDialogue(npc, "If I'm not mistaken, you've got some body parts from a big mole there! I'll trade it for bird nests if ye likes. Or was ye wantin' some woad leaves instead?"),
                    new OptionsDialogue(
                            new Option("Okay, I will trade the mole parts.", () -> handleTurnin(player, npc)),
                            new Option("Yes please, I need woad leaves.", () -> woadLeaves(player, npc)),
                            new Option("Sorry, but I'm not interested.", () -> notInterestedDialogue(player, npc))
                    )
            );
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "I'm the head gardener around here. If you're looking for woad leaves, or if you need help with owt, I'm yer man."),
                    new OptionsDialogue(
                            new Option("Yes please, I need woad leaves.", () -> woadLeaves(player, npc)),
                            new Option("How about ME helping YOU instead?",
                                    new PlayerDialogue("How about ME helping YOU instead?"),
                                    new NPCDialogue(npc, "That's a nice thing to say. I do need a hand, now you mention it. You see, there's some stupid mole digging up my lovely garden."),
                                    new PlayerDialogue("A mole? Surely you've dealt with moles in the past?"),
                                    new NPCDialogue(npc, "Ah, well this is no ordinary mole! He's a big'un for sure. Ya see... I'm always relied upon to make the most of this 'ere garden - the faster and bigger I can grow plants the better!"),
                                    new NPCDialogue(npc, "In my quest for perfection I looked into 'Malignius- Mortifer's-Super-Ultra-Flora-Growth-Potion'. It worked well on my plants, no doubt about it! But it had the same effect on a nearby mole. Ya can imagine the"),
                                    new NPCDialogue(npc, "havoc he causes to my patches of sunflowers! Why, if any of the other gardeners knew about this mole, I'd be looking for a new job in no time!"),
                                    new PlayerDialogue("I see. What do you need me to do?"),
                                    new NPCDialogue(npc, "If ya are willing maybe yer wouldn't mind killing it for me? Take a spade and use it to shake up them mole hills. Be careful though, he really is big!"),
                                    new PlayerDialogue("Is there anything in this for me?"),
                                    new NPCDialogue(npc, "Well, if yer gets any mole skin or mole claws off 'un, I'd trade 'em for bird nests if ye brings 'em here to me."),
                                    new PlayerDialogue("Right, I'll bear it in mind.")
                            ),
                            new Option("Sorry, but I'm not interested.", () -> notInterestedDialogue(player, npc))
                    )
            );
        }
    }

    static {
        NPCAction.register(5422, "talk-to", Wyson::dialogue);
    }
}
