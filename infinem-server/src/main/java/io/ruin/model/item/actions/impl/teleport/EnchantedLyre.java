package io.ruin.model.item.actions.impl.teleport;

import io.ruin.cache.ItemDef;
import io.ruin.model.content.tasksystem.tasks.areas.rewards.FremennikReward;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/31/2023
 */
public class EnchantedLyre {
    private static final int IMBUED_LYRE = 23458;
    private static final List<Integer> LYRES = Arrays.asList(3690, 3691, 6125, 6126, 6127, 13079, IMBUED_LYRE);
    private static final int FESSEGRIMEN = 808;
    private static final int ALTAR = 4141;
    private static final Position RELLEKKA = new Position(2662, 3645, 0);
    private static final Position WATERBIRTH_ISLAND = new Position(2548, 3757, 0);
    private static final Position JATIZSO = new Position(2410, 3809, 0);
    private static final Position NEITIZNOT = new Position(2336, 3803, 0);
    private static final Map<Integer, Integer> FISH_CHARGES = new HashMap<Integer, Integer>() {{
        put(Items.RAW_BASS, 2);
        put(Items.RAW_SHARK, 2);
        put(Items.RAW_MANTA_RAY, 3);
        put(Items.RAW_SEA_TURTLE, 4);
    }};

    private static void imbuedOffering(Player player) {
        int currentBass = player.getAttributeIntOrZero("LYRE_BASS");
        int currentShark = player.getAttributeIntOrZero("LYRE_SHARK");
        int currentManta = player.getAttributeIntOrZero("LYRE_MANTA");
        int currentTurtle = player.getAttributeIntOrZero("LYRE_TURTLE");
        List<Item> bass = player.getInventory().collectNumberOfItems(1000 - currentBass, Items.RAW_BASS, Items.RAW_BASS_NOTE);
        List<Item> shark = player.getInventory().collectNumberOfItems(1000 - currentShark, Items.RAW_SHARK, Items.RAW_SHARK_NOTE);
        List<Item> manta = player.getInventory().collectNumberOfItems(1000 - currentManta, Items.RAW_MANTA_RAY, Items.RAW_MANTA_RAY_NOTE);
        List<Item> turtle = player.getInventory().collectNumberOfItems(1000 - currentTurtle, Items.RAW_SEA_TURTLE, Items.RAW_SEA_TURTLE_NOTE);
        if (bass == null && shark == null && manta == null && turtle == null) {
            player.dialogue(
                    new NPCDialogue(FESSEGRIMEN, "While I appreciate the thought of offering me fish, I am afraid I am unable to use the air you have offered me.")
            );
            return;
        }
        if (bass != null) for (Item b : bass) {
            currentBass += b.getAmount();
            player.getInventory().remove(b);
        }
        if (shark != null) for (Item s : shark) {
            currentShark += s.getAmount();
            player.getInventory().remove(s);
        }
        if (manta != null) for (Item m : manta) {
            currentManta += m.getAmount();
            player.getInventory().remove(m);
        }
        if (turtle != null) for (Item t : turtle) {
            currentTurtle += t.getAmount();
            player.getInventory().remove(t);
        }
        if (currentBass >= 1000 && currentShark >= 1000 && currentManta >= 1000 && currentTurtle >= 1000) {
            player.removeAttribute("LYRE_BASS");
            player.removeAttribute("LYRE_SHARK");
            player.removeAttribute("LYRE_MANTA");
            player.removeAttribute("LYRE_TURTLE");
            player.putAttribute("IMBUED_LYRE", 1);
            player.dialogue(
                    new NPCDialogue(FESSEGRIMEN, "You have given me a worthy offering, and I will bestow my enchantment whenever you bring me a suitable lyre."),
                    new ActionDialogue(() -> {
                        Item lyre = player.getInventory().findItem(LYRES.get(0));
                        if (lyre != null) {
                            lyre.setId(IMBUED_LYRE);
                            player.dialogue(new NPCDialogue(FESSEGRIMEN, "I offer you this enchantment as you have earned my favour."));
                        }
                    })
            );
            return;
        }
        player.putAttribute("LYRE_BASS", currentBass);
        player.putAttribute("LYRE_SHARK", currentShark);
        player.putAttribute("LYRE_MANTA", currentManta);
        player.putAttribute("LYRE_TURTLE", currentTurtle);
        player.dialogue(
                new NPCDialogue(FESSEGRIMEN, "You have given me a sizeable offering."),
                new NPCDialogue(FESSEGRIMEN, "You will need to offer me " + (1000 - player.getAttributeIntOrZero("LYRE_SHARK")) + " raw shark, " +
                        "" + (1000 - player.getAttributeIntOrZero("LYRE_MANTA")) + " raw manta ray, " +
                        "" + (1000 - player.getAttributeIntOrZero("LYRE_TURTLE")) + " raw sea turtle and " +
                        "" + (1000 - player.getAttributeIntOrZero("LYRE_BASS")) + " raw bass.")
        );
    }

    private static void offerFish(Player player, int fishId) {
        if (!player.getInventory().contains(fishId)) {
            player.dialogue(new NPCDialogue(FESSEGRIMEN, "You do not have a " + ItemDef.get(fishId).name + " to offer."));
            return;
        }
        Item lyre = player.getInventory().findItem(LYRES.get(0));
        if (lyre == null) {
            player.dialogue(new NPCDialogue(FESSEGRIMEN, "You do not have a lyre to enchant."));
            return;
        }
        player.getInventory().remove(fishId, 1);
        lyre.setId(LYRES.get(FISH_CHARGES.get(fishId) + (FremennikReward.LYRE_EXTRA_CHARGE.hasUnlocked(player) ? 1 : 0)));
        player.dialogue(new NPCDialogue(FESSEGRIMEN, "I offer you this enchantment for your worthy offering."));
    }

    private static void fossegrimenDialogue(Player player) {
        List<Option> fishOptions = new ArrayList<>();
        fishOptions.add(new Option("Raw shark.", () -> offerFish(player, Items.RAW_SHARK)));
        fishOptions.add(new Option("Raw manta ray.", () -> offerFish(player, Items.RAW_MANTA_RAY)));
        fishOptions.add(new Option("Raw sea turtle.", () -> offerFish(player, Items.RAW_SEA_TURTLE)));
        if (player.getEquipment().contains(Items.RING_OF_CHAROS) || player.getEquipment().contains(Items.RING_OF_CHAROS_A))
            fishOptions.add(new Option("Raw bass.", () -> offerFish(player, Items.RAW_BASS)));
        player.dialogue(
                new NPCDialogue(FESSEGRIMEN, "What can I do for you?"),
                new PlayerDialogue("I hear that I can present you with an offering and you will enchant my lyre for me."),
                new NPCDialogue(FESSEGRIMEN, "This is true. For a simple enchantment, you may place an offering on my Altar. Can I help you with anything else?"),
                new OptionsDialogue(
                        new Option("Present offering of a single fish.", new OptionsDialogue(fishOptions)),
                        new Option("Ask about a permanent lyre enchantment.",
                                new PlayerDialogue("Can you enchant my lyre so that the enchantment will never fade?"),
                                new ActionDialogue(() -> {
                                    if (player.hasAttribute("IMBUED_LYRE")) {
                                        Item lyre = player.getInventory().findItem(LYRES.get(0));
                                        if (lyre != null) {
                                            lyre.setId(IMBUED_LYRE);
                                            player.dialogue(new NPCDialogue(FESSEGRIMEN, "I offer you this enchantment as you have earned my favour."));
                                        } else {
                                            player.dialogue(new NPCDialogue(FESSEGRIMEN, "You have given me a worthy offering, and I will bestow my enchantment whenever you bring me a suitable lyre."));
                                        }
                                    } else {
                                        player.dialogue(
                                                new NPCDialogue(FESSEGRIMEN, "If the offering is great enough, I would be happy to offer you such a powerful enchantment."),
                                                new NPCDialogue(FESSEGRIMEN, "You will need to offer me " + (1000 - player.getAttributeIntOrZero("LYRE_SHARK")) + " raw shark, " +
                                                        "" + (1000 - player.getAttributeIntOrZero("LYRE_MANTA")) + " raw manta ray, " +
                                                        "" + (1000 - player.getAttributeIntOrZero("LYRE_TURTLE")) + " raw sea turtle and " +
                                                        "" + (1000 - player.getAttributeIntOrZero("LYRE_BASS")) + " raw bass."),
                                                new OptionsDialogue(
                                                        new Option("Present offering.", () -> imbuedOffering(player)),
                                                        new Option("Goodbye.", new PlayerDialogue("I will come back when I have an offering worthy of this power. Goodbye."), new NPCDialogue(FESSEGRIMEN, "Farewell then."))
                                                )
                                        );
                                    }
                                })
                        ),
                        new Option("Nothing.", new PlayerDialogue("Nothing, sorry."), new NPCDialogue(FESSEGRIMEN, "Do not summon me for idle conversation! Come back when you have an offering."))
                )
        );
    }

    private static void degradeLyre(Item item) {
        if (item.getId() == IMBUED_LYRE) return;
        for (int index = 0; index < LYRES.size(); index++) {
            if (LYRES.get(index) == item.getId()) {
                item.setId(LYRES.get(index-1));
                return;
            }
        }
    }

    private static void teleport(Player player, Item item, Position destination) {
        player.getMovement().startTeleport(20, event -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            event.delay(3);
            player.getMovement().teleport(destination);
            degradeLyre(item);
        });
    }

    private static void teleportDialogue(Player player, Item item) {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Rellekka", () -> teleport(player, item, RELLEKKA)));
        if (FremennikReward.LYRE_WATERBIRTH_TELEPORT.hasUnlocked(player))
            options.add(new Option("Waterbirth Island", () -> teleport(player, item, WATERBIRTH_ISLAND)));
        if (FremennikReward.LYRE_JATIZSO_NEITIZNOT.hasUnlocked(player)) {
            options.add(new Option("Neitiznot", () -> teleport(player, item, NEITIZNOT)));
            options.add(new Option("Jatizso", () -> teleport(player, item, JATIZSO)));
        }
        if (options.size() <= 1) {
            teleport(player, item, RELLEKKA);
            return;
        }
        player.dialogue(new OptionsDialogue("Where would you like to teleport to?", options));
    }

    static {
        ObjectAction.register(ALTAR, "summon", (player, npc) -> fossegrimenDialogue(player));
        ItemObjectAction.register(Items.RAW_BASS, ALTAR, ((player, item, obj) -> offerFish(player, item.getId())));
        ItemObjectAction.register(Items.RAW_SHARK, ALTAR, ((player, item, obj) -> offerFish(player, item.getId())));
        ItemObjectAction.register(Items.RAW_MANTA_RAY, ALTAR, ((player, item, obj) -> offerFish(player, item.getId())));
        ItemObjectAction.register(Items.RAW_SEA_TURTLE, ALTAR, ((player, item, obj) -> offerFish(player, item.getId())));
        for (int id : LYRES) {
            if (id == 3690) {
                ItemAction.registerInventory(id, "play", (player, item) -> player.dialogue(new MessageDialogue("Your enchanted lyre has run out of charges. Bring an offering to Fossegrimen to recharge it.")));
                continue;
            }
            ItemAction.registerInventory(id, "play", EnchantedLyre::teleportDialogue);
            ItemAction.registerEquipment(id, "rellekka", ((player, item) -> teleport(player, item, RELLEKKA)));
            ItemAction.registerEquipment(id, "waterbirth island", ((player, item) -> {
                if (FremennikReward.LYRE_WATERBIRTH_TELEPORT.hasUnlocked(player))
                    teleport(player, item, RELLEKKA);
                else
                    player.dialogue(new MessageDialogue(FremennikReward.LYRE_WATERBIRTH_TELEPORT.notUnlockedMessage()));
            }));
            ItemAction.registerEquipment(id, "jatiszo", ((player, item) -> {
                if (FremennikReward.LYRE_JATIZSO_NEITIZNOT.hasUnlocked(player))
                    teleport(player, item, JATIZSO);
                else
                    player.dialogue(new MessageDialogue(FremennikReward.LYRE_JATIZSO_NEITIZNOT.notUnlockedMessage()));
            }));
            ItemAction.registerEquipment(id, "neitiznot", ((player, item) -> {
                if (FremennikReward.LYRE_JATIZSO_NEITIZNOT.hasUnlocked(player))
                    teleport(player, item, NEITIZNOT);
                else
                    player.dialogue(new MessageDialogue(FremennikReward.LYRE_JATIZSO_NEITIZNOT.notUnlockedMessage()));
            }));
        }
    }
}
