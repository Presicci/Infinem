package io.ruin.data.impl.dialogue;

import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public enum DialogueLoaderAction {
    FRITZ_SELL((player -> {
        player.dialogue(
                new NPCDialogue(2053, "Fantastic! Let's see, for that much glass I could pay you ..."),
                new NPCDialogue(2053, "... " + player.getInventory().getAmount(Items.MOLTEN_GLASS) * 20 + " gold pieces!"),
                new OptionsDialogue(
                        new Option("Are you mad? I've run all over the island to make this glass.", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Are you mad? I've run all over the island to make this glass."),
                                    new NPCDialogue(2053, "Pity. Well you can find me here if you change your mind.")
                            );
                        }),
                        new Option("Sure, that sounds like a fair price.", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Sure, that sounds like a fair price."),
                                    new NPCDialogue(2053, "Here you are then."),
                                    new ItemDialogue().two(995, Items.MOLTEN_GLASS, "You trade the glass for some gold.").action(
                                            () -> {
                                                int amt = player.getInventory().getAmount(Items.MOLTEN_GLASS);
                                                player.getInventory().remove(Items.MOLTEN_GLASS, amt);
                                                player.getInventory().add(995, amt * 20);
                                            }
                                    ),
                                    new NPCDialogue(2053, "Thanks very much, [player name]. I'll buy any more that you bring.")
                            );
                        })
                )
        );
    })),
    FISHING_HELP((player -> {
        int fishingLevel = player.getStats().get(StatType.Fishing).fixedLevel;
        int dialogueSize = NPCDef.get(3221).optionDialogues.size();
        Dialogue dialogue = NPCDef.get(3221).optionDialogues.get(dialogueSize - 1);
        if (fishingLevel > 50)
            player.dialogue(new NPCDialogue(3221, "Tuna and Swordfish can be harpooned - if you're good enough - from the thrivin' fishing village of Catherby, or if you can get in try the Fishin' Guild. Level 35 for Tuna and 50 for Swordfish."), dialogue);
        else if (fishingLevel > 46)
            player.dialogue(new NPCDialogue(3221, "Bass can be caught at level 46 in your big net if you wander along to Catherby."), dialogue);
        else if (fishingLevel > 38)
            player.dialogue(new NPCDialogue(3221, "You can use your fishin' rod and some bait to catch Cave Eel in the caves below Lumbridge Swamp at level 38."), dialogue);
        else if (fishingLevel > 28)
            player.dialogue(new NPCDialogue(3221, "You can use your fishin' rod and some bait to catch Slimy Eel in the swamps at level 28."), dialogue);
        else if (fishingLevel > 23)
            player.dialogue(new NPCDialogue(3221, "Cod can be fished from Catherby and some other places once you reach level 23."), dialogue);
        else if (fishingLevel > 16)
            player.dialogue(new NPCDialogue(3221, "You can use a big net to catch Mackerel from Catherby when you reach level 16."), dialogue);
        else if (fishingLevel > 10)
            player.dialogue(new NPCDialogue(3221, "Herrin' can be fished from Catherby and some other places when you reach level 10."), dialogue);
    })),
    ATTACK((player) -> {
        NPC npc = player.getDialogueNPC();
        if (npc != null) {
            NPCCombat npcCombat = npc.getCombat();
            if (npcCombat == null) {
                System.err.println("NPC:" + npc.getDef().name + " has ATTACK action in dialogue but no combat data.");
                return;
            }
            npcCombat.setTarget(player);
            //npc.attackTargetPlayer();
        }
    }),
    HEAL((player) -> {
        NPC npc = player.getDialogueNPC();
        if (player.getStats().get(StatType.Hitpoints).currentLevel >= player.getStats().get(StatType.Hitpoints).fixedLevel) {
            if (npc != null)
                player.dialogue(new NPCDialogue(npc, "You look healthy to me!"));
            return;
        }
        player.getStats().get(StatType.Hitpoints).restore();

        if (npc != null) {
            npc.faceTemp(player);
            player.dialogue(new NPCDialogue(npc, "There you go, you should be all set. Stay safe out there."));
            npc.animate(1161);
        }
        player.graphics(436, 48, 0);
        player.privateSound(958);
    }),
    DUNG((player) -> {
        player.startEvent(e -> {
            player.lock();
            player.getPacketSender().fadeOut();
            GameObject obj = new GameObject(6257, player.getPosition(), 0, 0);
            obj.spawn();
            e.delay(5);
            player.getPacketSender().fadeIn();
            player.unlock();
            player.dialogue(player.getDialogueNPC(),
                    new NPCDialogue(player.getDialogueNPC(), "I hope that's what you wanted!"),
                    new PlayerDialogue("Ohhh yes. Lovely.")
            );
            // Despawn dung after 1 minute
            World.startEvent(we -> {
                we.delay(100);
                if (!obj.isRemoved())
                    obj.remove();
            });
        });
    }),
    KEBAB((player) -> {
        if (player.getInventory().getAmount(995) > 0) {
            player.getInventory().remove(995, 1);
            player.getInventory().add(Items.KEBAB);
            player.dialogue(new ItemDialogue().one(Items.KEBAB, "You pay a coin for the kebab."));
        } else {
            NPC npc = player.getDialogueNPC();
            player.dialogue(
                    new PlayerDialogue("Oops, I forgot to bring any money with me."),
                    new NPCDialogue(npc.getId(), "Come back when you have some.")
            );
        }
    }),
    ITEMDIALOGUE(null),     // Opens an item dialogue with supplied itemId and message
    TWOITEMDIALOGUE(null),  // Opens a two item dialogue with supplied itemIds and message
    LASTOPTIONS(null),  // Reopens the last option dialogue
    FIRSTOPTIONS(null), // Reopens the first option dialogue
    MESSAGE(null),      // Message dialogue
    ITEM(null),         // Gives the player an item
    SHOP(null),         // Opens the npcs shop
    ;

    private final Consumer<Player> action;

    DialogueLoaderAction(Consumer<Player> action) {
        this.action = action;
    }


}
