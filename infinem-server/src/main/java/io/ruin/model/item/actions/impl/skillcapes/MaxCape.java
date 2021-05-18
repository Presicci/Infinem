package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/17/2021
 */
public class MaxCape {
    private static final int CAPE = 13342;

    private static void teleports(Player player, Item item) {
        player.dialogue(new OptionsDialogue("Choose a location:",
                new Option("Warrior's Guild", () -> StrengthSkillCape.strengthTeleport(player, item)),
                new Option("Fishing Teleports", () -> fishingTeleports(player, item)),
                new Option("Crafting Guild", () -> CraftingSkillCape.craftingTeleport(player, item)),
                new Option("Teleport to POH", () -> ConstructionSkillCape.telePOH(player, item)),
                new Option("More...", () -> teleportsPageTwo(player, item))
        ));
    }

    private static void teleportsPageTwo(Player player, Item item) {
        player.dialogue(new OptionsDialogue("Choose a location:",
                new Option("POH Portals", () -> ConstructionSkillCape.selectTeleport(player, item)),
                new Option("Farming Guild", () -> FarmingSkillCape.teleportToFarmingGuild(player, item)),
                new Option("Chinchompas", () -> HunterSkillCape.hunterTeleport(player, item)),
                new Option("Back...", () -> teleports(player, item))
                ));
    }

    private static void features(Player player, Item item) {
        player.dialogue(new OptionsDialogue("Choose an option:",
                new Option("Search", () -> player.dialogue(new OptionsDialogue("Choose an option:",
                        new Option("Pestle and mortar", () -> HerbloreSkillCape.herbloreSearch(player, item)),
                        new Option("Mithril grapple", () -> FletchingSkillCape.search(player, item))))),
                new Option("Stamina boost", () -> AgilitySkillCape.staminaBoost(player, item))
        ));
    }

    private static void otherTeleports(Player player, Item item) {
        player.dialogue(new OptionsDialogue("Choose a location:",
                new Option("Farming Guild", () -> FarmingSkillCape.teleportToFarmingGuild(player, item)),
                new Option("Chinchompas", () -> HunterSkillCape.hunterTeleport(player, item))
        ));
    }

    private static void fishingTeleports(Player player, Item item) {
        player.dialogue(new OptionsDialogue("Choose a location:",
                new Option("Fishing Guild", () -> ModernTeleport.teleport(player, new Bounds(2493,3414,2595,3416,0))),
                new Option("Otto's Grotto", () -> ModernTeleport.teleport(player, new Bounds(2501,3493,2502,3495,0)))
        ));
    }

    static {
        ItemAction.registerEquipment(CAPE, "Warriors' Guild", StrengthSkillCape::strengthTeleport);
        ItemAction.registerEquipment(CAPE, "Crafting Guild", CraftingSkillCape::craftingTeleport);
        ItemAction.registerEquipment(CAPE, "Tele to POH", ConstructionSkillCape::telePOH);
        ItemAction.registerEquipment(CAPE, "POH Portals", ConstructionSkillCape::selectTeleport);
        ItemAction.registerEquipment(CAPE, "Fishing Teleports", MaxCape::fishingTeleports);
        ItemAction.registerEquipment(CAPE, "spellbook", MagicSkillcape::swapSelection);
        ItemAction.registerEquipment(CAPE, "Features", MaxCape::features);
        ItemAction.registerEquipment(CAPE, "Other Teleports", MaxCape::otherTeleports);

        ItemAction.registerInventory(CAPE, "Features", MaxCape::features);
        ItemAction.registerInventory(CAPE, "Teleports", MaxCape::teleports);
    }
}
