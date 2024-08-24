package io.ruin.model.entity.npc.actions.tzhaar;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.pet.Pet;

public class TzHaarKetKeh {

    private static void sacrifice(Player player, NPC npc) {
        if (!player.getInventory().hasId(Items.FIRE_CAPE)) return;
        player.dialogue(
                new OptionsDialogue("Sacrifice your fire cape?",
                        new Option("No."),
                        new Option("Yes.", () -> {
                            player.getInventory().remove(Items.FIRE_CAPE, 1);
                            player.putAttribute("INFERNO_OPEN", 1);
                            player.getTaskManager().doLookupByUUID(420);    // Sacrifice a Fire Cape to Access the Inferno
                            player.dialogue(
                                    new ItemDialogue().one(Items.FIRE_CAPE, "You hand over your cape to TzHaar-Ket-Keh."),
                                    new NPCDialogue(npc, "Thank you, JalYt-Ket-Xo-[player name]. You can enter Inferno now."),
                                    new PlayerDialogue("What should I do down there?"),
                                    new NPCDialogue(npc, "The creatures down there are dangerous. If you can kill some of them, it would be very helpful to TzHaar."),
                                    new NPCDialogue(npc, "But now you can be trusted, I must also give warning. Another being lives in the Inferno, one more powerful than any TzHaar."),
                                    new NPCDialogue(npc, "We do not know where he came from. All we know is that TzKal-Zuk is hungry. Hungry for memories, memories beyond the Inferno."),
                                    new NPCDialogue(npc, "We think his goal is the city itself. Lots of TzHaar memories here for him. Luckily, he is trapped in prison down there, and we are keeping him at bay by offering sacrifices."),
                                    new NPCDialogue(npc, "We fear he can't be defeated, but maybe we can hold back his creatures and keep city safe."),
                                    new PlayerDialogue("Well I definitely think I can help with that."),
                                    new NPCDialogue(npc, "Thank you, JalYt-Ket-Xo-[player name].")
                            );
                        })
                )
        );
    }

    private static void fireCape(Player player, NPC npc) {
        if (player.getInventory().hasId(Items.FIRE_CAPE)) {
            sacrifice(player, npc);
        } else if (player.getEquipment().hasId(Items.FIRE_CAPE)) {
            player.dialogue(new PlayerDialogue("I'll need to take it off first."));
        } else {
            player.dialogue(new PlayerDialogue("I don't have a fire cape on me unfortunately."));
        }
    }

    private static void help(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Can I help here in some way?"),
                new ActionDialogue(() -> {
                    if (player.hasAttribute("INFERNO_OPEN")) {
                        player.dialogue(
                                new NPCDialogue(npc, "If you dare, brave The Inferno. Any creatures you kill will be very helpful to TzHaar.")
                        );
                    } else {
                        player.dialogue(
                                new NPCDialogue(npc, "I think maybe you can, but first you need to prove I can trust you."),
                                new NPCDialogue(npc, "If you sacrifice your fire cape to me, I can take your word."),
                                new ActionDialogue(() -> fireCape(player, npc))
                        );
                    }
                })
        );
    }

    private static void tellAboutInferno(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about the Inferno."),
                new NPCDialogue(npc, "TzHaar are born from eggs incubated in lava pools. When hatched, we retain memories. Memories and knowledge passed on from ancestors who returned to the lava."),
                new NPCDialogue(npc, "When TzHaar hatch, some memories are lost. Those about earliest ancestors are not retained. We experiment."),
                new NPCDialogue(npc, "This here was once our largest birthing pool. We use it for experiment. We increased the depth at which eggs are incubated. Over time, newly hatched TzHaar had more memories of lost history!"),
                new NPCDialogue(npc, "But we kept pushing. We wanted answers, but we went too deep."),
                new NPCDialogue(npc, "Eventually the pool collapsed into a sinkhole. At the bottom we found some sort of ancient incubation chamber, from before earliest memories. We now call it the Inferno."),
                new NPCDialogue(npc, "We decide to experiment more. We take eggs from creatures in Fight Cave and incubate them down there."),
                new NPCDialogue(npc, "When eggs hatched, we see that it was not good decision. They were bigger and stronger, and fought each other for dominance."),
                new ActionDialogue(() -> {
                    if (player.hasAttribute("INFERNO_OPEN")) {
                        player.dialogue(
                                new NPCDialogue(npc, "Instead of knowledge, we now have chamber of dangerous creatures, and they're not alone. Another being lives in the Inferno, one more powerful than any TzHaar."),
                                new NPCDialogue(npc, "We do not know where he came from. All we know is that TzKal-Zuk is hungry. Hungry for memories, memories beyond the Inferno."),
                                new NPCDialogue(npc, "We think his goal is the city itself. Lots of TzHaar memories here for him. Luckily, he is trapped in prison down there, and we are keeping him at bay by offering sacrifices."),
                                new NPCDialogue(npc, "We fear he can't be defeated, but maybe we can hold back his creatures and keep city safe."),
                                new ActionDialogue(() -> options(player, npc))
                        );
                    } else {
                        player.dialogue(
                                new NPCDialogue(npc, "Instead of knowledge, we now have chamber of dangerous creatures. It gets worse, but I don't know if I can trust JalYt-Ket-Xo-[player name] with this knowledge."),
                                new PlayerDialogue("I like to think I'm pretty trustworthy. If you tell me, maybe I can help in some way."),
                                new NPCDialogue(npc, "I have one idea. If you sacrifice your fire cape to me, I can take your word."),
                                new ActionDialogue(() -> fireCape(player, npc))
                        );
                    }
                })
        );
    }

    private static void options(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Tell me about the Inferno.", () -> tellAboutInferno(player, npc)),
                        new Option("Can I help here in some way?", () -> help(player, npc)),
                        new Option("Never mind.", new PlayerDialogue("Never mind."))
                )
        );
    }

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Yes, JalYt-Ket-Xo-[player name]?"),
                new ActionDialogue(() -> options(player, npc))
        );
    }

    private static void gambleCape(Player player, NPC npc) {
        if(player.getInventory().hasId(Pet.TZREK_ZUK.itemId) || player.getBank().hasId(Pet.TZREK_ZUK.itemId) || (player.pet != null && player.pet == Pet.TZREK_ZUK)) {
            player.dialogue(new NPCDialogue(npc, "You already have TzRek-Zuk!"));
            return;
        }
        Item cape = player.getInventory().findItem(21295);
        if(cape == null) {
            player.dialogue(new NPCDialogue(npc, "You no have cape!"));
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Sacrifice your infernal cape for a chance at TzRek-Zuk?", cape, () -> {
            cape.remove();
            if (Random.rollDie(25, 1)) {
                Pet.TZREK_ZUK.unlock(player);
                player.dialogue(new NPCDialogue(npc, "You lucky. Better train him good else TzKal-Zuk find you, JalYt."));
            } else {
                player.dialogue(new NPCDialogue(npc, "You not lucky. Maybe next time, JalYt."));
            }
        }));
    }

    static {
        NPCAction.register(7690, "talk to", TzHaarKetKeh::dialogue);
        ItemNPCAction.register(Items.FIRE_CAPE, 7690, (player, item, npc) -> {
            if (player.hasAttribute("INFERNO_OPEN")) {
                player.dialogue(new NPCDialogue(npc, "You've already brought me a fire cape, no need to bring me any more."));
            } else {
                sacrifice(player, npc);
            }
        });
        NPCAction.register(7690, "exchange", TzHaarKetKeh::gambleCape);
    }
}
