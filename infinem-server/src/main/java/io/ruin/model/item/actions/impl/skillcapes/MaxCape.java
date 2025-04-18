package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.MaxCapeVariants;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/17/2021
 */
public class MaxCape {
    private static final int CAPE = 13342;

    private static void teleports(Player player) {
        player.dialogue(new OptionsDialogue("Choose a location:",
                new Option("Warrior's Guild", () -> StrengthSkillCape.teleport(player)),
                new Option("Fishing Teleports", () -> fishingTeleports(player)),
                new Option("Crafting Guild", () -> CraftingSkillCape.teleport(player)),
                new Option("Teleport to POH", () -> ConstructionSkillCape.teleport(player)),
                new Option("More...", () -> teleportsPageTwo(player))
        ));
    }

    private static void teleportsPageTwo(Player player) {
        player.dialogue(new OptionsDialogue("Choose a location:",
                new Option("POH Portals", () -> ConstructionSkillCape.selectTeleport(player)),
                new Option("Farming Guild", () -> FarmingSkillCape.teleport(player)),
                new Option("Chinchompas", () -> HunterSkillCape.teleport(player)),
                new Option("Back...", () -> teleports(player))
                ));
    }

    private static void features(Player player) {
        player.dialogue(new OptionsDialogue("Choose an option:",
                new Option("Search", () -> player.dialogue(new OptionsDialogue("Choose an option:",
                        new Option("Pestle and mortar", () -> HerbloreSkillCape.search(player)),
                        new Option("Mithril grapple", () -> FletchingSkillCape.search(player))))),
                new Option("Stamina boost", () -> AgilitySkillCape.staminaBoost(player))
        ));
    }

    private static void otherTeleports(Player player) {
        player.dialogue(new OptionsDialogue("Choose a location:",
                new Option("Farming Guild", () -> FarmingSkillCape.teleport(player)),
                new Option("Chinchompas", () -> HunterSkillCape.teleport(player))
        ));
    }

    private static void fishingTeleports(Player player) {
        player.dialogue(new OptionsDialogue("Choose a location:",
                new Option("Fishing Guild", () -> ModernTeleport.teleport(player, new Bounds(2493,3414,2595,3416,0))),
                new Option("Otto's Grotto", () -> ModernTeleport.teleport(player, new Bounds(2501,3493,2502,3495,0)))
        ));
    }

    public static void selectPerk(Player player) {
        player.dialogue(new OptionsDialogue("Choose an option:",
                new Option("Features", () -> features(player)),
                new Option("Teleports", () -> teleports(player))
        ));
    }

    private static void registerCapeOptions(int itemId) {
        ItemAction.registerEquipment(itemId, "Warriors' Guild", (player, item) -> StrengthSkillCape.teleport(player));
        ItemAction.registerEquipment(itemId, "Crafting Guild", (player, item) -> CraftingSkillCape.teleport(player));
        ItemAction.registerEquipment(itemId, "Tele to POH", (player, item) -> ConstructionSkillCape.teleport(player));
        ItemAction.registerEquipment(itemId, "POH Portals", (player, item) -> ConstructionSkillCape.selectTeleport(player));
        ItemAction.registerEquipment(itemId, "Fishing Teleports", (player, item) -> fishingTeleports(player));
        ItemAction.registerEquipment(itemId, "spellbook", (player, item) -> MagicSkillcape.swapSelection(player));
        ItemAction.registerEquipment(itemId, "Features", (player, item) -> features(player));
        ItemAction.registerEquipment(itemId, "Other Teleports", (player, item) -> otherTeleports(player));
        ItemAction.registerInventory(itemId, "Features", (player, item) -> features(player));
        ItemAction.registerInventory(itemId, "Teleports", (player, item) -> teleports(player));
    }

    static {
        registerCapeOptions(CAPE);
        for (MaxCapeVariants.MaxCapes variants : MaxCapeVariants.MaxCapes.values()) {
            registerCapeOptions(variants.newCapeId);
        }
    }
}
