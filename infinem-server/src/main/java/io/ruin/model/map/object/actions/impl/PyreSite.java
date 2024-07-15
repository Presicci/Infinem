package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.woodcutting.Hatchet;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/14/2024
 */
public class PyreSite {

    public static final String PYRE_KEY = "PYRE_BONES";

    public static final LootTable CHEWED_BONES_LOOT = new LootTable().addTable(1,
            new LootItem(Items.RANARR_POTION_UNF_NOTE, 2, 16),
            new LootItem(Items.ANTIPOISON_SUPERMIX_2, 1, 8),
            new LootItem(Items.ANTIFIRE_MIX_1, 1, 8),
            new LootItem(Items.ANTIFIRE_MIX_2, 1, 8),
            new LootItem(Items.FISHING_MIX_2, 1, 8),
            new LootItem(Items.PRAYER_MIX_1, 1, 8),
            new LootItem(Items.PRAYER_MIX_2, 1, 8),
            new LootItem(Items.SUPERATTACK_MIX_1, 1, 8),
            new LootItem(Items.SUPERATTACK_MIX_2, 1, 8),
            new LootItem(Items.SUPER_STR_MIX_1, 1, 8),
            new LootItem(Items.SUPER_STR_MIX_2, 1, 8),
            new LootItem(Items.SUPER_DEF_MIX_1, 1, 8),
            new LootItem(Items.SUPER_DEF_MIX_2, 1, 8),
            new LootItem(Items.DEATH_RUNE, 8, 15, 36),
            new LootItem(Items.BLOOD_RUNE, 4, 7, 20),
            new LootItem(Items.SILVER_BOLTS, 5, 12),
            new LootItem(Items.RUNE_ARROW, 10, 12),
            new LootItem(Items.RUNITE_BOLTS, 10, 12),
            new LootItem(Items.DIAMOND_NOTE, 2, 16),
            new LootItem(Items.MITH_GRAPPLE_2, 2, 12),
            new LootItem(Items.ADAMANT_DART_P, 20, 12),
            new LootItem(Items.ADAMANT_KNIFE, 20, 12),
            new LootItem(Items.DRAGON_FULL_HELM, 1, 1)
    );

    private static PyreSiteLog findUsableLogs(Player player) {
        for (PyreSiteLog log : PyreSiteLog.values()) {
            Item item = player.getInventory().findItem(log.logId, false);
            if (item != null
                    && player.getStats().get(StatType.Crafting).currentLevel >= log.levelRequirement
                    && player.getStats().get(StatType.Firemaking).currentLevel >= log.levelRequirement)
                return log;
        }
        return null;
    }

    private static PyreSiteLog findLogs(Player player) {
        for (PyreSiteLog log : PyreSiteLog.values()) {
            Item item = player.getInventory().findItem(log.logId, false);
            if (item != null) return log;
        }
        return null;
    }

    private static final Position POSITION_ONE = new Position(2503, 3498, 0);
    private static final Position POSITION_TWO = new Position(2506, 3516, 0);
    private static final Position POSITION_THREE = new Position(2518, 3517, 0);

    private static void construct(Player player, GameObject object, Position position) {
        if (Tile.get(position).hasAnyObject(25288, 25289, 25290, 25291, 25292, 25293, 25294, 25295)) {
            player.sendMessage("Someone is currently constructing a pyre ship here.");
            player.privateSound(2277);
            return;
        }
        Item bones = player.getInventory().findItem(Items.CHEWED_BONES, false);
        if (bones == null) {
            player.sendMessage("You do not have any chewed bones to offer.");
            player.privateSound(2277);
            return;
        }
        PyreSiteLog log = findUsableLogs(player);
        if (log == null) {
            PyreSiteLog unusableLog = findLogs(player);
            player.privateSound(2277);
            if (unusableLog != null) {
                player.dialogue(new MessageDialogue("You need a crafting and firemaking level of "
                        + unusableLog.levelRequirement + " to create a pyre ship with "
                        + ItemDefinition.get(unusableLog.logId).name
                        + "."));
            } else {
                player.sendMessage("You do not have any logs to create a pyre ship with.");
            }
            return;
        }
        Hatchet hatchet = Hatchet.find(player);
        if (hatchet == null) {
            player.sendMessage("You do not have an axe which you have the woodcutting level to use.");
            player.privateSound(2277);
            return;
        }
        player.startEvent(e -> {
            player.lock();
            GameObject ship = new GameObject(25288, position, 10, position == POSITION_ONE ? 0 : 1).spawn();
            player.animate(hatchet.canoeAnimationId);
            e.delay(3);
            ship.setId(25289);  // Shaping ship
            e.delay(3);
            player.animate(hatchet.canoeAnimationId);
            e.delay(3);
            ship.setId(25290);  // Ship
            e.delay(3);
            ship.setId(25291);  // Add bones
            player.getInventory().remove(log.logId, 1);
            bones.remove();
            e.delay(3);
            ship.setId(25292);  // Lit ship
            // TODO spirit gfx https://oldschool.runescape.wiki/w/Chewed_bones
            Item loot = CHEWED_BONES_LOOT.rollItem();
            player.getInventory().add(loot);
            player.getCollectionLog().collect(loot);
            player.getTaskManager().doDropLookup(loot);
            player.getStats().addXp(StatType.Crafting, log.craftingExperience, true);
            player.getStats().addXp(StatType.Firemaking, log.firemakingExperience, true);
            player.sendMessage("Total spirits laid to rest: " + PlayerCounter.PYRE_SHIPS.increment(player, 1));
            player.sendMessage("The ancient barbarian is laid to rest. Your future prayer training is blessed, as his spirit ascends to a glorious afterlife. " +
                    "Spirits drop an object into your pack");
            player.incrementNumericAttribute(PYRE_KEY, log.boostedBones);
            e.delay(3);
            ship.setId(25293);  // Push ship
            e.delay(3);
            ship.setId(25294);  // Floating ship
            e.delay(2);
            ship.setId(25295);  // Sinking ship
            e.delay(1);
            ship.remove();
            player.unlock();
        });
    }

    static {
        ObjectAction.register(25286, 2503, 3498, 0, "construct", (player, obj) -> construct(player, obj, POSITION_ONE));
        ObjectAction.register(25286, 2503, 3499, 0, "construct", (player, obj) -> construct(player, obj, POSITION_ONE));
        ObjectAction.register(25286, 2506, 3518, 0, "construct", (player, obj) -> construct(player, obj, POSITION_TWO));
        ObjectAction.register(25286, 2507, 3518, 0, "construct", (player, obj) -> construct(player, obj, POSITION_TWO));
        ObjectAction.register(25286, 2518, 3519, 0, "construct", (player, obj) -> construct(player, obj, POSITION_THREE));
        ObjectAction.register(25286, 2519, 3519, 0, "construct", (player, obj) -> construct(player, obj, POSITION_THREE));
    }

    @Getter
    @AllArgsConstructor
    private enum PyreSiteLog {
        REDWOOD(Items.REDWOOD_LOGS, 5, 95, 87, 350),
        MAGIC(Items.MAGIC_LOGS, 5, 85, 75.9, 303.8),
        YEW(Items.YEW_LOGS, 4, 70, 50.6, 202.5),
        MAHOGANY(Items.MAHOGANY_LOGS, 4, 60, 39.3, 157.5),
        MAPLE(Items.MAPLE_LOGS, 3, 55, 33.7, 135),
        ARCTIC_PINE(Items.ARCTIC_PINE_LOGS, 3, 52, 31.2, 125),
        TEAK(Items.TEAK_LOGS, 3, 45, 26.2, 105),
        WILLOW(Items.WILLOW_LOGS, 2, 40, 22.5, 90),
        OAK(Items.OAK_LOGS, 2, 25, 15, 60),
        ACHEY(Items.ACHEY_TREE_LOGS, 1, 11, 10, 40),
        NORMAL(Items.LOGS, 1, 11, 10, 40);

        private final int logId, boostedBones, levelRequirement;
        private final double craftingExperience, firemakingExperience;
    }
}
