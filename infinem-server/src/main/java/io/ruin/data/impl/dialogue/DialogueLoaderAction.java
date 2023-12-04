package io.ruin.data.impl.dialogue;

import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.content.transportation.charterships.CharterShips;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.npc.actions.Leon;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.mining.EssenceMine;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public enum DialogueLoaderAction {
    THURGO_PIE(player -> {
        NPC npc = player.getDialogueNPC();
        if (!player.getInventory().hasId(Items.REDBERRY_PIE)) {
            player.dialogue(new PlayerDialogue("Well I don't have one."), new NPCDialogue(npc, "Aww why would you tease me like that. Get out of here."));
        } else {
            player.getInventory().remove(Items.REDBERRY_PIE, 1);
            player.getTaskManager().doLookupByUUID(931);    // Feed Thurgo
            player.dialogue(
                    new MessageDialogue("You hand over the pie. Thurgo eats the pie. Thurgo pats his stomach."),
                    new NPCDialogue(npc, "By Guthix! THAT was good pie! Anyone who makes pie like THAT has got to be alright!")
            );
        }
    }),
    LEON_AMMO(Leon::craftAmmo),
    ESSENCE_MINE(player -> {
        NPC npc = player.getDialogueNPC();
        String npcName;
        switch (npc.getId()) {
            default:
                npcName = "AUBURY";
                break;
            case 5034:
                npcName = "SEDRIDOR";
                break;
            case 3248:
                npcName = "DISTENTOR";
                break;
            case 5314:
                npcName = "CROMPERTY";
                break;
            case 4913:
                npcName = "BRIMSTAIL";
                break;
        }
        EssenceMine.enterMine(player, npc, npcName);
    }),
    CHARTER((CharterShips::openInterface)),
    PET_ROCK((player -> {
        if (!player.getInventory().hasFreeSlots(1)) {
            player.dialogue(new NPCDialogue(player.getDialogueNPC(), "Sure thing, but you'd better free up some space first."));
            return;
        }
        player.getInventory().add(Items.PET_ROCK);
        if (player.getBank().contains(Items.PET_ROCK) || player.getInventory().contains(Items.PET_ROCK)) {
            player.dialogue(new NPCDialogue(player.getDialogueNPC(), "Sure, have as many as you like. I've plenty more!"));
        } else {
            player.dialogue(new NPCDialogue(player.getDialogueNPC(), "Sure thing buddy! I'd say take better care of this one, but it's just a rock! I have hundreds of them! Go wild!"));
        }
    })),
    NOTE_BASALT((player -> {
        if (!player.getInventory().contains(22603)) {
            player.dialogue(new NPCDialogue(player.getDialogueNPC(), "You aren't carrying any basalt."));
            return;
        }
        int amt = player.getInventory().getAmount(22603);
        player.getInventory().remove(22603, amt);
        player.getInventory().add(22604, amt);
        player.dialogue(new ItemDialogue().one(22603, "Snowflake converts your " + (amt > 1 ? "items" : "item") + " to " + (amt > 1 ? "banknotes" : "a banknote") + "."));
    })),
    BUY_SKILLCAPE((player -> {
        NPC npc = player.getDialogueNPC();
        if (player.getInventory().getAmount(995) < 99000) {
            player.dialogue(
                    new PlayerDialogue("But, unfortunately, I don't have enough money with me."),
                    new NPCDialogue(npc, "Well, come back and see me when you do.")
            );
            return;
        }
        if (!player.getInventory().hasFreeSlots(2) && !(player.getInventory().getAmount(995) == 99000 && player.getInventory().hasFreeSlots(1))) {
            player.dialogue(
                    new NPCDialogue(npc, "Come back when you have enough space for the cape and hood.")
            );
            return;
        }
        String substring = player.getTemporaryAttribute(AttributeKey.DIALOGUE_ACTION_ARGUMENTS);
        String dialogue = "";
        int trimmedId = -1;
        int capeId = -1;
        int hoodId = -1;
        switch (substring) {
            case "FARMING":
                dialogue = "That's true; us Master Farmers are a unique breed.";
                capeId = 9810;
                trimmedId = 9811;
                hoodId = 9812;
                break;
            case "SMITHING":
                dialogue = "Excellent! Wear that cape with pride my friend.";
                capeId = 9795;
                trimmedId = 9796;
                hoodId = 9797;
                break;
        }
        int finalHoodId = hoodId;
        int finalTrimmedId = trimmedId;
        int finalCapeId = capeId;
        String finalDialogue = dialogue;
        player.dialogue(
                new NPCDialogue(npc, finalDialogue),
                new NPCDialogue(npc, "Would you like the trimmed or untrimmed cape?"),
                new OptionsDialogue(
                        new Option("Trimmed", () -> {
                            player.getInventory().remove(995, 99000);
                            player.getInventory().add(finalTrimmedId, 1);
                            player.getInventory().add(finalHoodId, 1);
                            player.dialogue(new NPCDialogue(npc, "Here you are."));
                        }),
                        new Option("Untrimmed", () -> {
                            player.getInventory().remove(995, 99000);
                            player.getInventory().add(finalCapeId, 1);
                            player.getInventory().add(finalHoodId, 1);
                            player.dialogue(new NPCDialogue(npc, "Here you are."));
                        }),
                        new Option("Nevermind")
                )
        );
    })),
    TRAVEL((player) -> {
        int z = 0;
        try {
            String substring = player.getTemporaryAttribute(AttributeKey.DIALOGUE_ACTION_ARGUMENTS);
            String[] coords = substring.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            if (coords.length > 2)
                z = Integer.parseInt(coords[2]);
            Traveling.fadeTravel(player, x, y, z);
        } catch (Exception ignored) {
            System.out.println("Missing TRAVEL arguments");
        }
    }),
    LIEVE_TRIDENTS((player -> {
        int tentacles = player.getAttributeIntOrZero(AttributeKey.LIEVE_KRAKEN_TENTACLES);
        int dialogueSize = NPCDef.get(7412).optionDialogues.size();
        Dialogue optionDialogue = NPCDef.get(7412).optionDialogues.get(dialogueSize - 1);
        player.dialogue(
                new NPCDialogue(7412, "If ya brings me a trident, I can enhance it for ya. It'll store 20,000 charges, not just 2,500. "
                        + (tentacles == 0 ? "But I wants payin' fer the job."
                        : tentacles == 10 ? "Ya brought me " + tentacles + " tentacles; I'll use up 10 of 'em to do the job."
                        : "Ya brought me " + tentacles + " tentacles so far, but I'll want 10 of 'em to do the job.")),
                new NPCDialogue(7412, tentacles == 0 ? "Give me 10 kraken tentacles first. I'll hold onto 'em for ya. Then ya can bring me a trident to enhance, an' I'll get to work for ya."
                        : tentacles == 9 ? "Just pass me one more tentacle, then give me yer trident an' I'll get to work for ya."
                        : tentacles >= 10 ? "Just pass me yer trident an' I'll get to work for ya."
                        : "Just pass me " + (10 - tentacles) + " more tentacles, then give me yer trident an' I'll get to work for ya."),
                optionDialogue
        );
    })),
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
    CANOE_HELP((player -> {
        int woodcuttingLevel = player.getStats().get(StatType.Woodcutting).fixedLevel;
        if (woodcuttingLevel >= 57) {
            player.dialogue(
                    new NPCDialogue(1326, "Hoo! You look like you know which end of an axe is which!"),
                    new NPCDialogue(1326, "You can easily build one of those Wakas. Be careful if you travel into the Wilderness though."),
                    new NPCDialogue(1326, "I've heard tell of great evil in that blasted wasteland."),
                    new PlayerDialogue("Thanks for the warning Bill.")
            );
        } else if (woodcuttingLevel >= 42) {
            player.dialogue(
                    new NPCDialogue(1326, "The best canoe you can make is a Stable Dugout, one step beyond a normal Dugout."),
                    new NPCDialogue(1326, "With a Stable Dugout you can travel to any place on the river."),
                    new PlayerDialogue("Even into the Wilderness?"),
                    new NPCDialogue(1326, "Not likely! I've heard tell of a man up near Edgeville who claims he can use a Waka to get up into the Wilderness."),
                    new NPCDialogue(1326, "I can't think why anyone would wish to venture into that hellish landscape though.")
            );
        } else if (woodcuttingLevel >= 27) {
            player.dialogue(
                    new NPCDialogue(1326, "With your skill in woodcutting you could make my favourite canoe, the Dugout. They might not be the best canoe on the river, but they get you where you're going."),
                    new PlayerDialogue("How far will I be able to go in a Dugout canoe?"),
                    new NPCDialogue(1326, "You will be able to travel 2 stops on the river.")
            );
        } else if (woodcuttingLevel >= 12) {
            player.dialogue(
                    new NPCDialogue(1326, "Hah! I can tell just by looking that you lack talent in woodcutting."),
                    new PlayerDialogue("What do you mean?"),
                    new NPCDialogue(1326, "No Callouses! No Splinters! No camp fires littering the trail behind you."),
                    new NPCDialogue(1326, "Anyway, the only 'canoe' you can make is a log. You'll be able to travel 1 stop along the river with a log canoe.")
            );
        } else {
            player.dialogue(
                    new NPCDialogue(1326, "It's really quite simple. Just walk down to that tree on the bank and chop it down."),
                    new NPCDialogue(1326, "When you have done that you can shape the log further with your axe to make a canoe.")
            );
        }

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
            npcCombat.faceTarget();
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
    OTHERNPC(null),
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
