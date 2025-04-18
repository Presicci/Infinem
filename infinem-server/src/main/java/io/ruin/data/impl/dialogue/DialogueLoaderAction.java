package io.ruin.data.impl.dialogue;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.World;
import io.ruin.model.activities.shadesofmortton.Coffin;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.content.transportation.charterships.CharterShips;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.npc.actions.Leon;
import io.ruin.model.entity.npc.actions.guild.crafting.Tanner;
import io.ruin.model.entity.npc.actions.rellekka.PeerTheSeer;
import io.ruin.model.entity.npc.actions.traveling.Sandicrahb;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.shop.omnishop.OmniShop;
import io.ruin.model.skills.agility.courses.ColossalWyrmRemains;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.skills.mining.EssenceMine;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Getter
public enum DialogueLoaderAction {
    WORM_TONGUE_SHOP(OmniShop.WORM_TONGUES_WARES::open),
    WORM_TONGUE_STATS(player -> {
        NPC npc = player.getDialogueNPC();
        ColossalWyrmRemains.statsDialogue(player, npc);
    }),
    DAMPE_LOCKS(player -> {
        List<Coffin> REVERSED_LIST = Arrays.asList(Coffin.values());
        Collections.reverse(REVERSED_LIST);
        Coffin coffin = Arrays.stream(REVERSED_LIST.toArray(new Coffin[0])).filter(c -> player.getInventory().hasId(c.getLockId())).findFirst().orElse(null);
        NPC npc = player.getDialogueNPC();
        if (coffin == null) {
            player.dialogue(
                    new PlayerDialogue("You gave me a broken coffin, what am I supposed to do with it?"),
                    new NPCDialogue(npc, "Bring me locks, I fix coffin with locks..."),
                    new PlayerDialogue("Right... and where am I suppose to find such locks?"),
                    new NPCDialogue(npc, "In catacombs... in chests..."),
                    new PlayerDialogue("Great, thanks for the information.")
            );
        } else {
            player.dialogue(
                    new PlayerDialogue("I found one of the locks you were talking about, I think."),
                    new ItemDialogue().one(coffin.getLockId(), "You show Dampe the lock you found and his cold eyes light up."),
                    new NPCDialogue(npc, "That is it, give to me now and I repair the coffin."),
                    new OptionsDialogue("Give Dampe a lock?",
                            new Option("Yes", () -> {
                                Item coffinItem = player.getInventory().findItem(25457);
                                if (coffinItem != null) {
                                    if (player.getInventory().hasId(coffin.getLockId())) {
                                        player.getInventory().remove(coffin.getLockId(), 1);
                                        coffinItem.setId(coffin.getCoffinId());
                                        player.getTransmogCollection().addToCollection(coffin.getCoffinId(), true);
                                        player.dialogue(
                                                new ItemDialogue().two(coffin.getLockId(), 25457, "You hand over the coffin and lock to Dampe."),
                                                new MessageDialogue("You hear him clumsily attach the lock to the coffin."),
                                                new NPCDialogue(npc, "Finished, take it.")
                                        );
                                    }
                                } else {
                                    player.dialogue(new NPCDialogue(npc, "You ain't got no broken coffin for me to fix. If you need one, just ask."));
                                }
                            }),
                            new Option("No", new PlayerDialogue("No thanks, I'm going to hang on to it for now."), new NPCDialogue(npc, "*grunts*"))
                    )
            );
        }
    }),
    DAMPE_GIVECOFFIN(player -> {
        boolean hasCoffin = false;
        if (player.getInventory().hasId(25457)) hasCoffin = true;
        else for (Coffin coffin : Coffin.values()) {
            if (player.getInventory().hasId(coffin.getCoffinId())
                    || player.getEquipment().hasId(coffin.getCoffinId())
                    || player.getInventory().hasId(coffin.getOpenId())
                    || player.getEquipment().hasId(coffin.getOpenId())) {
                hasCoffin = true;
                break;
            }
        }
        NPC npc = player.getDialogueNPC();
        if (hasCoffin) {
            player.dialogue(
                    new NPCDialogue(npc, "Get lost, you've already got a coffin, I ain't givin' you anotha.")
            );
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "*grunts*"),
                    new ItemDialogue().one(25457, npc.getDef().name + " hands you " + ItemDefinition.get(25457).descriptiveName + ".").consumer((p) -> {
                        p.getInventory().addOrDrop(25457, 1);
                    }),
                    new PlayerDialogue("It's broken?"),
                    new NPCDialogue(npc, "Bring me locks, find in the catacombs... I repair coffin with locks.")
            );
        }
    }),
    DONDAKAN_CANNON(player -> {
        Traveling.fadeTravel(player, 2583, 4936, 0);
    }),
    STANKERS_COAL_TRUCK(player -> {
        NPC npc = player.getDialogueNPC();
        if (!AreaReward.COAL_TRUCKS.checkReward(player, "use the coal trucks.")) return;
        player.dialogue(
                new NPCDialogue(npc, "Oh no problem at all. Those carts be takin' trips to thee bank all day long."),
                new NPCDialogue(npc, "Only problem es I'm the only dwarf out 'ere minin' the coal."),
                new PlayerDialogue("I'll keep you company out here."),
                new NPCDialogue(npc, "Thank ye very much.")
        );
    }),
    PEER_FUTURE(player -> PeerTheSeer.tellFortune(player, player.getDialogueNPC())),
    PEER_DEPOSIT(PeerTheSeer::deposit),
    SANDICRAHB(player -> {
        NPC npc = player.getDialogueNPC();
        Sandicrahb.pay(player, npc);
    }),
    TAN(player -> {
        String arg = player.getTemporaryAttribute(AttributeKey.DIALOGUE_ACTION_ARGUMENTS);
        if (arg == null || arg.isEmpty())
            Tanner.leatherTanning(player, 1D);
        else {
            double multiplier = Double.parseDouble(arg);
            Tanner.leatherTanning(player, multiplier);
        }
    }),
    EODAN_TAN(Tanner::eodanTanning),
    COUNT_CHECK_TELE(player -> {
        NPC npc = player.getDialogueNPC();
        if (player.hasAttribute("SHOS")) {
            player.dialogue(new NPCDialogue(npc, "The Stronghold of Security, in Barbarian Village. There is much to learn there."));
            return;
        }
        player.dialogue(
                new NPCDialogue(npc, "The Stronghold of Security, in Barbarian Village. I see you have not visited it. Would you like to? I can send you straight there - but only once."),
                new OptionsDialogue(
                        new Option("Yes", () -> {
                            ModernTeleport.teleport(player, 3080, 3421, 0);
                            player.putAttribute("SHOS", 0);
                        }),
                        new Option("No")
                )
        );
    }),
    MONKEYSPEAK(player -> {
        String[] phrases = {
                "Ah Ah!",
                "Ah Uh Ah!",
                "Ah!",
                "Ook Ah Ook!",
                "Ook Ah Uh!",
                "Ook Ook!",
                "Ook!",
                "Ook."
        };
        NPC npc = player.getDialogueNPC();
        StringBuilder sb = new StringBuilder();
        int count = 0;
        int max = Random.get(8, 10);
        while (count < max) {
            sb.append(Random.get(phrases));
            sb.append(" ");
            count++;
        }
        player.dialogue(new NPCDialogue(npc, sb.toString()));
    }),
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
            case 11433:
                npcName = "SEDRIDOR";
                break;
            case 11400:
                npcName = "DISTENTOR";
                break;
            case 8480:
                npcName = "CROMPERTY";
                break;
            case 11431:
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
        int dialogueSize = NPCDefinition.get(7412).optionDialogues.size();
        Dialogue optionDialogue = NPCDefinition.get(7412).optionDialogues.get(dialogueSize - 1);
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
    SMITHING_HELP((player -> {
        int smithingLevel = player.getStats().get(StatType.Smithing).fixedLevel;
        int dialogueSize = NPCDefinition.get(3225).optionDialogues.size();
        Dialogue dialogue = NPCDefinition.get(3225).optionDialogues.get(dialogueSize - 1);
        if (smithingLevel >= 99)
            player.dialogue(
                    new NPCDialogue(3225, "Cor blimey, I don't think there is anything I can teach you, but I'll do my best."),
                    new NPCDialogue(3225, "As you get better you'll find you will be able to smith Mithril and eventually Adamantite and even Runite. This can be very lucrative but very expensive on the coal front."),
                    new NPCDialogue(3225, "It may be worth you stockpiling coal for a while before attempting these difficult metals or even sticking to good old reliable iron by the bucket load."),
                    new NPCDialogue(3225, "If you craft or purchase any Rings of Forging, they will help you to smith more bars for the same ore."),
                    //new NPCDialogue(3225, "If you want to save on coal, you could try smithing your bars over at the Blast Furnace in Keldagrim."),
                    new NPCDialogue(3225, "If you are looking for something more interesting to smith, you could talk to Otto Godblessed about smithing hastae."),
                    new NPCDialogue(3225, "You can find him south of the barbarian outpost and west of the whirlpool."),
                    new NPCDialogue(3225, "Is there anything else you would like to know?"),
                    dialogue
            );
        else if (smithingLevel >= 38)
            player.dialogue(
                    new NPCDialogue(3225, "As you get better you'll find you will be able to smith Mithril and eventually Adamantite and even Runite. This can be very lucrative but very expensive on the coal front."),
                    new NPCDialogue(3225, "It may be worth you stockpiling coal for a while before attempting these difficult metals or even sticking to good old reliable iron by the bucket load."),
                    new NPCDialogue(3225, "If you craft or purchase any Rings of Forging, they will help you to smith more bars for the same ore."),
                    //new NPCDialogue(3225, "If you want to save on coal, you could try smithing your bars over at the Blast Furnace in Keldagrim."),
                    new NPCDialogue(3225, "If you are looking for something more interesting to smith, you could talk to Otto Godblessed about smithing hastae."),
                    new NPCDialogue(3225, "You can find him south of the barbarian outpost and west of the whirlpool."),
                    new NPCDialogue(3225, "Is there anything else you would like to know?"),
                    dialogue
            );
        else if (smithingLevel >= 21)
            player.dialogue(
                    new NPCDialogue(3225, "You might find it beneficial to keep a nice stack of bars in your bank before you start smithing. Unless you like to jump from task to task of course."),
                    new NPCDialogue(3225, "If you want to smith something that you may not have the level for, you could try gaining some temporary knowledge in smithing from a boost."),
                    new NPCDialogue(3225, "You can buy a Dwarven stout at the Rising Sun Inn in Falador."),
                    new NPCDialogue(3225, "Is there anything else you would like to know?"),
                    dialogue
            );
        else
            player.dialogue(
                    new NPCDialogue(3225, "You're going to need to get your hand on some metal bars. You could do this by mining your own ores and smelting them at a furnace."),
                    new NPCDialogue(3225, "There is a furnace located in Lumbridge, just north of the castle opposite the general store."),
                    new NPCDialogue(3225, "If you are looking for some ore, there is a mine east of Varrock. There you can find some copper and tin ore. Don't forget to bring a pickaxe."),
                    new NPCDialogue(3225, "When you have your bars, bring them to an anvil to open the smithing interface. If the item name is in black, this means you do not have the level to smith the item."),
                    new NPCDialogue(3225, "If the item name is in white, this means you do have the level to smith the item. You will see the bars required to smith the item underneath the name of the item."),
                    new NPCDialogue(3225, "If the number of bars is in orange, this means you do not have enough bars to smith the item. If it is in green, this means you have enough bars for the item."),
                    new PlayerDialogue("Thanks for the advice."),
                    new NPCDialogue(3225, "Is there anything else you would like to know?"),
                    dialogue
            );
    })),
    FISHING_HELP((player -> {
        int fishingLevel = player.getStats().get(StatType.Fishing).fixedLevel;
        int dialogueSize = NPCDefinition.get(3221).optionDialogues.size();
        Dialogue dialogue = NPCDefinition.get(3221).optionDialogues.get(dialogueSize - 1);
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
    ANJA_HENGEL_ATTACK((player) -> {
        NPC npc = player.getDialogueNPC();
        if (npc == null) return;
        Optional<NPC> otherNpcOptional = npc.localNpcs().stream().filter(n -> n.getId() == (npc.getId() == 3285 ? 3284 : 3285)).findFirst();
        if (otherNpcOptional.isPresent()) {
            NPC otherNpc = otherNpcOptional.get();
            otherNpc.forceText(otherNpc.getId() == 3285 ? "Eeeek!" : "Aaaarrgh!");
        }
        npc.forceText(npc.getId() == 3285 ? "Eeeek!" : "Aaaarrgh!");
    }),
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
            GameObject obj = new GameObject(6257, player.getPosition(), 10, 0);
            Tile.get(player.getPosition()).addObject(obj.spawn());
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
