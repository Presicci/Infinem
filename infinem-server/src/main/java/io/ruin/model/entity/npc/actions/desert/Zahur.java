package io.ruin.model.entity.npc.actions.desert;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.misc.Decanter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.impl.skillcapes.HerbloreSkillCape;
import io.ruin.model.skills.herblore.Herb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/29/2024
 */
public class Zahur {

    private static void clean(Player player, NPC npc) {
        int coinAmt = player.getInventory().getAmount(995);
        for (Herb herb : Herb.values()) {
            if (player.getInventory().hasId(herb.grimyId)) {
                clean(player, npc, herb.grimyId, herb.cleanId, false);
            }
        }
        //if (AreaReward.ZAHUR_CLEAN_HERB.hasReward(player)) {
            for (Herb herb : Herb.values()) {
                if (player.getInventory().hasId(ItemDefinition.get(herb.grimyId).notedId)) {
                    clean(player, npc, ItemDefinition.get(herb.grimyId).notedId, ItemDefinition.get(herb.cleanId).notedId, false);
                }
            }
        //}
        if (player.getInventory().getAmount(995) < coinAmt) {
            player.dialogue(new NPCDialogue(npc, "There ya go, come back if you need anything else."));
        } else {
            player.dialogue(new NPCDialogue(npc, "You don't have any grimy herbs for me to clean."));
        }
    }

    private static void clean(Player player, NPC npc, int herbId, int cleanId, boolean message) {
        int herbAmt = player.getInventory().getAmount(herbId);
        int affordableAmt = player.getInventory().getAmount(995) / 200;
        int possibleAmt = Math.min(herbAmt, affordableAmt);
        if (affordableAmt == 0) {
            if (message)
                player.dialogue(new NPCDialogue(npc, "I charge 200 coins per clean. Come back with some money."));
            return;
        }
        if (herbAmt == 0) {
            if (message)
                player.dialogue(new NPCDialogue(npc, "You don't have any " + ItemDefinition.get(herbId).name + " for me to clean."));
            return;
        }
        if (message) {
            player.dialogue(
                    new NPCDialogue(npc, "Would you like me to clean " + NumberUtils.formatNumber(possibleAmt) + " " + ItemDefinition.get(herbId).name + " for you? It'll cost you " + possibleAmt * 200 + " coins."),
                    new OptionsDialogue(
                            new Option("Yes", () -> {
                                if (!cleanHerb(player, possibleAmt, herbId, cleanId)) return;
                                player.dialogue(
                                        new ItemDialogue().one(cleanId, "Zahur cleans the herbs for you."),
                                        new NPCDialogue(npc, "There ya go, come back if you need anything else.")
                                );
                            }),
                            new Option("No")
                    )
            );
        } else {
            cleanHerb(player, possibleAmt, herbId, cleanId);
        }
    }

    private static boolean cleanHerb(Player player, int possibleAmt, int herbId, int cleanId) {
        if (player.getInventory().getAmount(herbId) < possibleAmt
                || player.getInventory().getAmount(995) / 200 < possibleAmt) return false;
        player.getInventory().remove(995, possibleAmt * 200);
        player.getInventory().remove(herbId, possibleAmt);
        player.getInventory().add(cleanId, possibleAmt);
        player.getTaskManager().doLookupByUUID(924, possibleAmt);   // Have Zahur Clean an Herb
        return true;
    }

    private static void makeUnfinished(Player player, NPC npc) {
        if (!HerbloreSkillCape.wearingHerbloreCape(player) && !AreaReward.ZAHUR_UNFINISHED_POTIONS.checkReward(player, "have Zahur make unfinished potions for you."))
            return;
        int vialAmt = player.getInventory().getAmount(Items.VIAL_OF_WATER_NOTE);
        if (vialAmt <= 0) {
            player.dialogue(new NPCDialogue(npc, "You'll need noted vials of water and herbs if you want me to make unfinished potions for you."));
            return;
        }
        for (Herb herb : Herb.values()) {
            if (player.getInventory().hasId(ItemDefinition.get(herb.cleanId).notedId)) {
                makeUnfinished(player, npc, herb, false);
            }
        }
        if (player.getInventory().getAmount(Items.VIAL_OF_WATER_NOTE) < vialAmt) {
            player.dialogue(new NPCDialogue(npc, "There ya go, come back if you need anything else."));
        } else {
            if (player.getInventory().freeSlot() == 0) {
                player.dialogue(new NPCDialogue(npc, "You don't have enough inventory space to carry the noted unfinished potions."));
            } else {
                player.dialogue(new NPCDialogue(npc, "You don't have any noted herbs I can make unfinished potions with."));
            }
        }
    }

    private static void makeUnfinished(Player player, NPC npc, Herb herb, boolean message) {
        if (message && !HerbloreSkillCape.wearingHerbloreCape(player) && !AreaReward.ZAHUR_UNFINISHED_POTIONS.checkReward(player, "have Zahur make unfinished potions for you."))
            return;
        int herbId = ItemDefinition.get(herb.cleanId).notedId;
        int unfId = ItemDefinition.get(herb.unfId).notedId;
        int vialAmt = player.getInventory().getAmount(Items.VIAL_OF_WATER_NOTE);
        int herbAmt = player.getInventory().getAmount(herbId);
        int affordableAmt = player.getInventory().getAmount(995) / 200;
        int possibleAmt = Math.min(vialAmt, Math.min(herbAmt, affordableAmt));
        if (affordableAmt == 0) {
            if (message)
                player.dialogue(new NPCDialogue(npc, "I charge 200 coins per clean. Come back with some money."));
            return;
        }
        if (herbAmt == 0 || vialAmt == 0) {
            if (message)
                player.dialogue(new NPCDialogue(npc, "You'll need noted vials of water and herbs if you want me to make unfinished potions for you."));
            return;
        }
        if (!player.getInventory().hasRoomFor(unfId, possibleAmt) && (vialAmt > possibleAmt && herbAmt > possibleAmt)) {
            if (message)
                player.dialogue(new NPCDialogue(npc, "You don't have enough inventory space to carry the noted unfinished potions."));
            return;
        }
        if (message) {
            player.dialogue(
                    new NPCDialogue(npc, "Would you like me to make " + NumberUtils.formatNumber(possibleAmt) + " " + ItemDefinition.get(herb.unfId).name + " for you? It'll cost you " + possibleAmt * 200 + " coins."),
                    new OptionsDialogue(
                            new Option("Yes", () -> {
                                if (!mixUnfinished(player, possibleAmt, herbId, unfId)) return;
                                player.dialogue(
                                        new ItemDialogue().one(herb.unfId, "Zahur prepares the unfinished potions for you."),
                                        new NPCDialogue(npc, "There ya go, come back if you need anything else.")
                                );
                            }),
                            new Option("No")
                    )
            );
        } else {
            mixUnfinished(player, possibleAmt, herbId, unfId);
        }
    }

    private static boolean mixUnfinished(Player player, int possibleAmt, int herbId, int unfId) {
        if (player.getInventory().getAmount(Items.VIAL_OF_WATER_NOTE) < possibleAmt
                || player.getInventory().getAmount(herbId) < possibleAmt
                || player.getInventory().getAmount(995) / 200 < possibleAmt) return false;
        player.getInventory().remove(995, possibleAmt * 200);
        player.getInventory().remove(Items.VIAL_OF_WATER_NOTE, possibleAmt);
        player.getInventory().remove(herbId, possibleAmt);
        player.getInventory().add(unfId, possibleAmt);
        player.getTaskManager().doIncrementalLookupByUUID(683, possibleAmt);   // Have Zahur Make 1,000 Unfinished Potions
        return true;
    }

    static {
        NPCAction.register(4753, "talk-to", (player, npc) -> {
            List<Option> options = new ArrayList<>();
            options.add(new Option("Decant", () -> Decanter.decantPotions(player, npc)));
            options.add(new Option("Clean herbs", () -> clean(player, npc)));
            if (HerbloreSkillCape.wearingHerbloreCape(player) || AreaReward.ZAHUR_CLEAN_HERB.hasReward(player))
                options.add(new Option("Make unfinished potions", () -> makeUnfinished(player, npc)));
            player.dialogue(
                    new NPCDialogue(npc, "Hello, how can I help you?"),
                    new OptionsDialogue(options)
            );
        });
        NPCAction.register(4753, "clean", Zahur::clean);
        NPCAction.register(4753, "make unfinished potion(s)", Zahur::makeUnfinished);
        for (Herb herb : Herb.values()) {
            ItemNPCAction.register(ItemDefinition.get(herb.cleanId).notedId, 4753, (player, item, npc) -> makeUnfinished(player, npc, herb, true));
            ItemNPCAction.register(herb.grimyId, 4753, (player, item, npc) -> clean(player, npc, herb.grimyId, herb.cleanId, true));
            ItemNPCAction.register(ItemDefinition.get(herb.grimyId).notedId, 4753, (player, item, npc) -> {
                //if (AreaReward.ZAHUR_CLEAN_HERB.checkReward(player, "have Zahur clean noted herbs for you."))
                    clean(player, npc, herb.grimyId, herb.cleanId, true);
            });
        }
    }
}