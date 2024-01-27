package io.ruin.model.skills.farming.farmer;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.skills.farming.patch.impl.CelastrusPatch;
import io.ruin.model.skills.farming.patch.impl.HardWoodTreePatch;
import io.ruin.model.skills.farming.patch.impl.RedwoodPatch;
import io.ruin.model.skills.farming.patch.impl.WoodTreePatch;

import static io.ruin.cache.ItemID.COINS_995;

public enum Farmer {
    ELSTAN(2663, PatchData.FALADOR_NORTH, PatchData.FALADOR_SOUTH, "northwestern", "southeastern"),
    LYRA(2666, PatchData.CANIFIS_NORTH, PatchData.CANIFIS_SOUTH, "northwestern", "southeastern"),
    DANTAERA(2664, PatchData.CATHERBY_NORTH, PatchData.CATHERBY_SOUTH, "northern", "southern"),
    KRAGEN(2665, PatchData.ARDOUGNE_NORTH, PatchData.ARDOUGNE_SOUTH, "northern", "southern"),
    MARISI(6921, PatchData.ZEAH_NORTH, PatchData.ZEAH_SOUTH, "northeastern", "southwestern"),
    OSWALLT(9138, PatchData.PRIF_NORTH, PatchData.PRIF_SOUTH, "northern", "southern"),

    FAYETH(2681, PatchData.LUMBRIDGE_TREE),
    TREZNOR(2680, PatchData.VARROCK_TREE),
    HESKEL(2679, PatchData.FALADOR_TREE),
    ALAIN(2678, PatchData.TAVERLEY_TREE),
    PRISSY_SCILLA(2687, PatchData.GNOME_TREE),

    BOLONGO(2682, PatchData.GNOME_FRUIT),
    ELLENA(2670, PatchData.CATHERBY_FRUIT),
    GILETH(2683, PatchData.VILLAGE_FRUIT),
    GARTH(2669, PatchData.BRIMHAVEN_FRUIT),
    LILIWEN(2689, PatchData.LLETYA_FRUIT),

    DREVEN(2674, PatchData.VARROCK_BUSH),
    TARIA(2675, PatchData.RIMMINGTON_BUSH),
    RHAZIEN(2676, PatchData.ETCETERIA_BUSH),
    TORRELL(2677, PatchData.ARDOUGNE_BUSH),

    VASQUEN(2672, PatchData.LUMBRIDGE_HOPS),
    RHONEN(2673, PatchData.SEERS_HOPS),
    SELENA(2671, PatchData.YANILLE_HOPS),
    FRANCIS(2667, PatchData.ENTRANA_HOPS),

    IMIAGO(2688, PatchData.CALQUAT),
    AYESHA(310, PatchData.CACTUS),

    PRAISTAN_EBOLA(2686, PatchData.BRIMHAVEN_SPIRIT_TREE),
    FRIZZY_SKERNIP(2684, PatchData.PORT_SARIM_SPIRIT_TREE),
    YULF_SQUECKS(2685, PatchData.ETCETERIA_SPIRIT_TREE),
    LAMMY_LANGLE(6814, PatchData.ZEAH_SPIRIT_TREE),

    ROSIE(8534, PatchData.FARMING_GUILD_TREE),
    TAYLOR(8629, PatchData.FARMING_GUILD_CELASTRUS),
    LATLINK_FASTBELL(8537, PatchData.FARMING_GUILD_SPIRIT_TREE),
    NIKKIE(8533, PatchData.FARMING_GUILD_FRUIT),
    ALEXANDRA(8536, PatchData.FARMING_GUILD_REDWOOD),
    ALAN(8535, PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH, "northern", "southern"),

    SQUIRREL_1(7754, PatchData.FOSSIL_ISLAND_HARDWOOD2),
    SQUIRREL_2(7755, PatchData.FOSSIL_ISLAND_HARDWOOD1),
    SQUIRREL_3(7756, PatchData.FOSSIL_ISLAND_HARDWOOD),

    MERNIA(7758, PatchData.SEAWEED_PATCH1, PatchData.SEAWEED_PATCH2, "northern", "southern");

    Farmer(int npcId, PatchData patch1, PatchData patch2, String patchOneName, String patchTwoName) {
        this.npcId = npcId;
        this.patch1 = patch1;
        this.patch2 = patch2;
        this.patchOneName = patchOneName;
        this.patchTwoName = patchTwoName;
    }

    Farmer(int npcId, PatchData patch1) {
        this(npcId, patch1, null, "", "");
    }

    private final int npcId;

    private final PatchData patch1, patch2;
    private final String patchOneName, patchTwoName;

    public static void attemptPayment(Player player, NPC npc, PatchData pd) {
        Patch patch = player.getFarming().getPatch(pd.getObjectId());
        if (patch == null) {
            throw new IllegalArgumentException();
        }
        if (patch.getPlantedCrop() == null) {
            player.dialogue(new NPCDialogue(npc, "You don't have any " + pd.getType() + " planted in that patch. Plant " +
                    "some and I might agree to look after it for you."));
            return;
        }
        if (patch.isFarmerProtected()) {
            player.dialogue(new NPCDialogue(npc, "What do you mean? I'm already watching that for you."));
            return;
        }
        if (patch.getDiseaseStage() == 1) {
            player.dialogue(new NPCDialogue(npc, "Your patch is diseased, cure it first if you want me to watch it for you."));
            return;
        }
        if (patch.getDiseaseStage() == 2) {
            player.dialogue(new NPCDialogue(npc, "Your patch is already dead, there's nothing I can do about that."));
            return;
        }
        if (patch.getStage() >= patch.getPlantedCrop().getTotalStages()) {
            player.dialogue(new NPCDialogue(npc, "Your patch is already fully grown."));
            return;
        }
        Item payment = patch.getPlantedCrop().getPayment();
        if (payment == null) {
            player.dialogue(new NPCDialogue(npc, "Sorry, I'm not familiar with what you have planted on that patch."));
            return;
        }
        int id = payment.getId();
        if (!player.getInventory().contains(id, payment.getAmount())) {
            if (payment.getDef().notedId == -1 || !player.getInventory().contains(payment.getDef().notedId, payment.getAmount())) {
                player.dialogue(new NPCDialogue(npc, "I can watch over your patch for you if you pay me " + getItemName(payment) + "."));
                return;
            } else {
                id = payment.getDef().notedId;
            }
        }
        player.getInventory().remove(id, payment.getAmount());
        patch.setFarmerProtected(true);
        player.dialogue(new NPCDialogue(npc, "That'll do nicely, sir. Leave it with me - I'll make sure that patch grows for you."));
        player.getTaskManager().doLookupByUUID(15, 1);  // Protect Your Crops
    }

    private static void attemptClearTreePatch(Player player, NPC npc, PatchData pd) {
        Patch patch = player.getFarming().getPatch(pd.getObjectId());
        if (patch == null) {
            throw new IllegalArgumentException();
        }
        //  Nothing planted
        if (patch.getPlantedCrop() == null) {
            return;
        }
        //  Not a wood tree patch
        if (!(patch instanceof WoodTreePatch)) {
            return;
        }
        //  If tree isn't fully grown
        if (patch.getStage() < patch.getPlantedCrop().getTotalStages()) {
            player.dialogue(new NPCDialogue(npc, "The tree is just a baby, no way I am going to uproot it!"));
        }
        //  If tree hasn't been checked yet
        if (patch.getStage() == patch.getPlantedCrop().getTotalStages()) {
            player.dialogue(new NPCDialogue(npc, "You should check your fully grown tree before you try removing it!"));
        }
        if (patch.getStage() >= patch.getPlantedCrop().getTotalStages() + 1) {
            //TODO check if tree needs to be just be a root or it can also be fully grown and checked
            patch.reset(false);
            player.dialogue(new NPCDialogue(npc, "There you go, I've cleared the patch for you."));
        }
    }

    private static String getItemName(Item item) {
        String str = "";
        ItemDef def = item.getDef();
        if (item.getId() == COINS_995) {
            return NumberUtils.formatNumber(item.getAmount()) + " coins";
        } else {
            if (item.getAmount() > 1) {
                str += String.valueOf(item.getAmount());
            } else {
                str += StringUtils.vowelStart(def.name) && !def.name.contains("(") ? "an" : "a";
            }
            str += " ";
            if (def.name.endsWith("(5)")) {
                str += "basket";
                if (item.getAmount() > 1)
                    str += "s";
                str += " filled with 5 " + def.name.toLowerCase().substring(0, def.name.indexOf("("));
                if (item.getAmount() > 1)
                    str += " each";
            } else if (def.name.endsWith("(10)")) {
                str += "sack";
                if (item.getAmount() > 1)
                    str += "s";
                str += " filled with 10 " + def.name.toLowerCase().substring(0, def.name.indexOf("("));
                if (item.getAmount() > 1)
                    str += " each";
            } else {
                str += def.name;
            }
            return str;
        }
    }

    private static final String[] ADVICE = new String[] {
            "The only way to cure a bush or tree of disease is to prune away the diseased leaves with a pair of secateurs. For all other crops I would just apply some plant-cure.",
            "Tree seeds must be grown in a plantpot of soil into a sapling, and then transferred to a tree patch to continue growing to adulthood.",
            "You can put up to ten potatoes, cabbages or onions in vegetable sacks, although you can't have a mix in the same sack.",
            "You can buy all the farming tools from farming shops, which can be found close to the allotments.",
            "Bittercap mushrooms can only be grown in a special patch in Morytania, near the Mort Myre swamp. There the ground is especially dank and suited to growing poisonous fungii.",
            "Supercompost is far better than normal compost, but more expensive to make. You need to rot the right type of item; show me an item, and I'll tell you if it's super- compostable or not.",
            "There is a special patch for growing Belladonna - I believe that it is somewhere near Draynor Manor, where the ground is a tad 'unblessed'.",
            "Applying compost to a patch will not only reduce the chance that your crops will get diseased, but you will also grow more crops to harvest.",
            "If you want to grow fruit trees you could try a few places: Catherby, Brimhaven, Gnome Stronghold and the Farming Guild all have fruit tree patches.",
            "You can fill plantpots with soil from any empty patch, if you have a gardening trowel.",
            "Hops are good for brewing ales. I believe there's a brewery up in Keldagrim somewhere, and I've heard rumours that a place called Phasmatys used to be good for that type of thing. 'Fore they all died, of course.",
            "Vegetables, hops and flowers need constant watering - if you ignore my advice, you will sooner or later find yourself in possession of a dead farming patch.",
            "You can put up to five tomatoes, strawberries, apples, bananas or oranges into a fruit basket, although you can't have a mix in the same basket.",
            "Don't just throw away your weeds after you've raked a patch - put them in a compost bin and make some compost.",
            "There are five other Farming areas like this one - Elstan's area near Falador, Dantaera's area near Catherby, Lyra's area in Morytania, Marisi's area in Hosidius and Alan's area in the Farming Guild.",
            "If you want to make your own sacks and baskets you'll need to use the loom that's near the Farming shop in Falador. If you're a good enough [craftsman/craftswoman], that is.",
            "You don't have to buy all your plantpots you know, you can make them yourself on a pottery wheel. If you're a good enough [craftsman/craftswoman], that is."
    };

    private static void randomAdvice(Player player, NPC npc, Farmer farmer) {
        player.dialogue(
                new PlayerDialogue("Can you give me any farming advice?"),
                new NPCDialogue(npc, Random.get(ADVICE)),
                new ActionDialogue(() -> dialogue(player, npc, farmer))
        );
    }

    private static void lookAfterDialogue(Player player, NPC npc, Farmer farmer) {
        if (farmer.patch2 == null) {
            player.dialogue(
                    new PlayerDialogue("Would you look after my crops for me?"),
                    new ActionDialogue(() -> attemptPayment(player, npc, farmer.patch1))
            );
        } else if (farmer == ALAN) {
            player.dialogue(
                    new PlayerDialogue("Would you look after my crops for me?"),
                    new NPCDialogue(npc, "I might - which patch were you thinking of?"),
                    new OptionsDialogue(
                            new Option("The cactus patch", () -> attemptPayment(player, npc, PatchData.FARMING_GUILD_CACTUS)),
                            new Option("The " + farmer.patchOneName + " allotment", () -> attemptPayment(player, npc, farmer.patch1)),
                            new Option("The " + farmer.patchTwoName + " allotment", () -> attemptPayment(player, npc, farmer.patch2)),
                            new Option("The bush patch", () -> attemptPayment(player, npc, PatchData.FARMING_GUILD_BUSH))
                    ),
                    new ActionDialogue(() -> attemptPayment(player, npc, farmer.patch1))
            );
        } else {
            player.dialogue(
                    new PlayerDialogue("Would you look after my crops for me?"),
                    new NPCDialogue(npc, "I might - which patch were you thinking of?"),
                    new OptionsDialogue(
                            new Option("The " + farmer.patchOneName + " allotment", () -> attemptPayment(player, npc, farmer.patch1)),
                            new Option("The " + farmer.patchTwoName + " allotment", () -> attemptPayment(player, npc, farmer.patch2))
                    ),
                    new ActionDialogue(() -> attemptPayment(player, npc, farmer.patch1))
            );
        }
    }

    private static void clearDialogue(Player player, NPC npc, Farmer farmer) {
        Patch patch = player.getFarming().getPatch(farmer.patch1);
        if (patch == null) {
            throw new IllegalArgumentException();
        }
        if (patch.getPlantedCrop() == null) {
            player.dialogue(new NPCDialogue(npc, "You don't have anything planted in that patch."));
            return;
        }
        player.dialogue(new NPCDialogue(npc, "I can clear your patch for you if you pay me 200 coins."),
                new ActionDialogue(() -> {
                    if (!player.getInventory().contains(995, 200)) {
                        player.dialogue(new PlayerDialogue("My pockets are a little light at the moment, I'll take you up on that later."));
                        return;
                    }
                    player.dialogue(new OptionsDialogue("Pay 200 to clear patch?",
                            new Option("Clear my patch", () -> {
                                player.getInventory().remove(995, 200);
                                patch.reset(false);
                                player.dialogue(new NPCDialogue(npc, "There you are friend, good as new."));
                            }),
                            new Option("No")
                    ));
        }));
    }

    private static void dialogue(Player player, NPC npc, Farmer farmer) {
        Option patchOption = new Option("Would you look after my crops for me?", () -> lookAfterDialogue(player, npc, farmer));
        Patch patch = player.getFarming().getPatch(farmer.patch1);
        if (patch instanceof WoodTreePatch || patch instanceof HardWoodTreePatch || patch instanceof RedwoodPatch || patch instanceof CelastrusPatch) {
            if (patch.getPlantedCrop() != null && patch.getStage() >= patch.getPlantedCrop().getTotalStages() + 1) {  // Can clear
                patchOption = new Option("Could you clear the patch for me?", () -> clearDialogue(player, npc, farmer));
            } else if (patch.getPlantedCrop() != null && patch.getStage() == patch.getPlantedCrop().getTotalStages()) {
                patchOption = new Option("Could you clear the patch for me?",
                        new NPCDialogue(npc, "You should probably check its health first."),
                        new PlayerDialogue("Oh yeah, good idea."));
            }
        }
        player.dialogue(
                new OptionsDialogue(
                        patchOption,
                        new Option("Can you give me any farming advice?", () -> randomAdvice(player, npc, farmer)),
                        new Option("Do you have anything for sale?", new PlayerDialogue("Do you have anything for sale?"), new NPCDialogue(npc, "Sure, take a look."), new ActionDialogue(() -> npc.openShop(player))),
                        new Option("I'll come back another time.", new PlayerDialogue("I'll come back another time."))
                )
        );
    }

    static {
        for (Farmer farmer : values()) {
            NPCAction.register(farmer.npcId, 1, (player, npc) -> dialogue(player, npc, farmer));
            if (farmer.patch1 != null) {
                NPCAction.register(farmer.npcId, 3, (player, npc) -> {
                    if (farmer.npcId == 8535) { // Farming guild east wing farmer
                        player.dialogue(new OptionsDialogue("Which patch would you like me to watch?",
                                new Option("North Allotment", p -> attemptPayment(p, npc, farmer.patch1)),
                                new Option("South Allotment", p -> attemptPayment(p, npc, farmer.patch2)),
                                new Option("Bush", p -> attemptPayment(p, npc, PatchData.FARMING_GUILD_BUSH)),
                                new Option("Cactus", p -> attemptPayment(p, npc, PatchData.FARMING_GUILD_CACTUS))
                        ));
                    } else {
                        attemptPayment(player, npc, farmer.patch1);
                    }
                });
            }
            if (farmer.patch2 != null) {
                NPCAction.register(farmer.npcId, 4, (player, npc) -> attemptPayment(player, npc, farmer.patch2));
            }
        }
    }


}
