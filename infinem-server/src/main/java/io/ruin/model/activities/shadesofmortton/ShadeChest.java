package io.ruin.model.activities.shadesofmortton;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.route.RouteFinder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/22/2024
 */
@Getter
@AllArgsConstructor
public enum ShadeChest {
    BRONZE_RED(4111, Items.BRONZE_KEY_RED, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 5, 10, 0),
                    new LootItem(Items.ECTOTOKEN, 0, 1, 0)
            )
            .addTable(155752,
                    new LootItem(25442, 1, 1, 15900) // Bronze locks
            )
            .addTable(5273556,
                    new LootItem(1233, 1, 1, 50700), // Black dagger(p)
                    new LootItem(1353, 1, 1, 60899), // Steel axe
                    new LootItem(1225, 1, 1, 40600), // Mithril dagger(p)
                    new LootItem(1389, 1, 1, 60899), // Magic staff
                    new LootItem(1241, 1, 1, 40600), // Steel spear
                    new LootItem(1255, 1, 1, 40600), // Steel spear(p)
                    new LootItem(1424, 1, 1, 30400), // Steel mace
                    new LootItem(1325, 1, 1, 30400), // Steel scimitar
                    new LootItem(1281, 1, 1, 40600), // Steel sword
                    new LootItem(1361, 1, 1, 10100), // Black axe
                    new LootItem(1217, 1, 1, 60899), // Black dagger
                    new LootItem(1426, 1, 1, 20300), // Black mace
                    new LootItem(1209, 1, 1, 50700), // Mithril dagger
                    new LootItem(1295, 1, 1, 327), // Steel longsword
                    new LootItem(4580, 1, 1, 327) // Black spear
            )
            .addTable(4570691,
                    new LootItem(1141, 1, 1, 50700), // Steel med helm
                    new LootItem(562, 12, 29, 142000), // Chaos rune
                    new LootItem(1729, 1, 1, 71000), // Amulet of defence
                    new LootItem(1637, 1, 1, 20300), // Sapphire ring
                    new LootItem(2355, 3, 3, 60899), // Silver bar
                    new LootItem(2357, 3, 3, 50700), // Gold bar
                    new LootItem(2359, 3, 3, 50700), // Mithril bar
                    new LootItem(24362, 1, 1, 20300) // Clue scroll (easy)
            )
    ),
    BRONZE_BROWN(4112, Items.BRONZE_KEY_BROWN, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 5, 10, 0),
                    new LootItem(Items.ECTOTOKEN, 0, 1, 0)
            )
            .addTable(160863,
                    new LootItem(25442, 1, 1, 16100) // Bronze locks
            )
            .addTable(5378937,
                    new LootItem(1233, 1, 1, 50700), // Black dagger(p)
                    new LootItem(1353, 1, 1, 60899), // Steel axe
                    new LootItem(1225, 1, 1, 40600), // Mithril dagger(p)
                    new LootItem(1389, 1, 1, 60899), // Magic staff
                    new LootItem(1241, 1, 1, 40600), // Steel spear
                    new LootItem(1255, 1, 1, 40600), // Steel spear(p)
                    new LootItem(1424, 1, 1, 30400), // Steel mace
                    new LootItem(1325, 1, 1, 30400), // Steel scimitar
                    new LootItem(1281, 1, 1, 40600), // Steel sword
                    new LootItem(1361, 1, 1, 10100), // Black axe
                    new LootItem(1217, 1, 1, 60899), // Black dagger
                    new LootItem(1426, 1, 1, 20300), // Black mace
                    new LootItem(1209, 1, 1, 50700), // Mithril dagger
                    new LootItem(1295, 1, 1, 327), // Steel longsword
                    new LootItem(4580, 1, 1, 327) // Black spear
            )
            .addTable(4460198,
                    new LootItem(1141, 1, 1, 50700), // Steel med helm
                    new LootItem(562, 12, 29, 132000), // Chaos rune
                    new LootItem(1729, 1, 1, 71000), // Amulet of defence
                    new LootItem(1637, 1, 1, 10100), // Sapphire ring
                    new LootItem(2355, 3, 3, 60899), // Silver bar
                    new LootItem(2357, 3, 3, 50700), // Gold bar
                    new LootItem(2359, 3, 3, 50700), // Mithril bar
                    new LootItem(24362, 1, 1, 20300) // Clue scroll (easy)
            )
    ),
    BRONZE_CRIMSON(4113, Items.BRONZE_KEY_CRIMSON, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 5, 10, 0),
                    new LootItem(Items.ECTOTOKEN, 0, 1, 0)
            )
            .addTable(162213,
                    new LootItem(25442, 1, 1, 16399) // Bronze locks
            )
            .addTable(5322245,
                    new LootItem(1233, 1, 1, 50700), // Black dagger(p)
                    new LootItem(1353, 1, 1, 60800), // Steel axe
                    new LootItem(1225, 1, 1, 40600), // Mithril dagger(p)
                    new LootItem(1389, 1, 1, 60800), // Magic staff
                    new LootItem(1241, 1, 1, 40600), // Steel spear
                    new LootItem(1255, 1, 1, 40600), // Steel spear(p)
                    new LootItem(1424, 1, 1, 30400), // Steel mace
                    new LootItem(1325, 1, 1, 30400), // Steel scimitar
                    new LootItem(1281, 1, 1, 40600), // Steel sword
                    new LootItem(1361, 1, 1, 10100), // Black axe
                    new LootItem(1217, 1, 1, 60800), // Black dagger
                    new LootItem(1426, 1, 1, 20300), // Black mace
                    new LootItem(1209, 1, 1, 50700), // Mithril dagger
                    new LootItem(1295, 1, 1, 327), // Steel longsword
                    new LootItem(4580, 1, 1, 327) // Black spear
            )
            .addTable(4515541,
                    new LootItem(1141, 1, 1, 50700), // Steel med helm
                    new LootItem(562, 12, 29, 132000), // Chaos rune
                    new LootItem(1729, 1, 1, 71000), // Amulet of defence
                    new LootItem(1637, 1, 1, 20300), // Sapphire ring
                    new LootItem(2355, 3, 3, 60800), // Silver bar
                    new LootItem(2357, 3, 3, 50700), // Gold bar
                    new LootItem(2359, 3, 3, 50700), // Mithril bar
                    new LootItem(24362, 1, 1, 20300) // Clue scroll (easy)
            )
    ),
    BRONZE_BLACK(4114, Items.BRONZE_KEY_BLACK, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 5, 10, 0),
                    new LootItem(Items.ECTOTOKEN, 0, 1, 0)
            )
            .addTable(163508,
                    new LootItem(25442, 1, 1, 16700) // Bronze locks
            )
            .addTable(5268046,
                    new LootItem(1233, 1, 1, 50700), // Black dagger(p)
                    new LootItem(1353, 1, 1, 60800), // Steel axe
                    new LootItem(1225, 1, 1, 40600), // Mithril dagger(p)
                    new LootItem(1389, 1, 1, 60800), // Magic staff
                    new LootItem(1241, 1, 1, 40600), // Steel spear
                    new LootItem(1255, 1, 1, 40600), // Steel spear(p)
                    new LootItem(1424, 1, 1, 30400), // Steel mace
                    new LootItem(1325, 1, 1, 30400), // Steel scimitar
                    new LootItem(1281, 1, 1, 40600), // Steel sword
                    new LootItem(1361, 1, 1, 10100), // Black axe
                    new LootItem(1217, 1, 1, 60800), // Black dagger
                    new LootItem(1426, 1, 1, 20300), // Black mace
                    new LootItem(1209, 1, 1, 50700), // Mithril dagger
                    new LootItem(1295, 1, 1, 327), // Steel longsword
                    new LootItem(4580, 1, 1, 327) // Black spear
            )
            .addTable(4568445,
                    new LootItem(1141, 1, 1, 50700), // Steel med helm
                    new LootItem(562, 12, 29, 132000), // Chaos rune
                    new LootItem(1729, 1, 1, 71000), // Amulet of defence
                    new LootItem(1637, 1, 1, 30400), // Sapphire ring
                    new LootItem(2355, 3, 3, 60800), // Silver bar
                    new LootItem(2357, 3, 3, 50700), // Gold bar
                    new LootItem(2359, 3, 3, 50700), // Mithril bar
                    new LootItem(24362, 1, 1, 20300) // Clue scroll (easy)
            )
    ),
    BRONZE_PURPLE(4115, Items.BRONZE_KEY_PURPLE, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 5, 10, 0),
                    new LootItem(Items.ECTOTOKEN, 0, 1, 0)
            )
            .addTable(168974,
                    new LootItem(25442, 1, 1, 16900) // Bronze locks
            )
            .addTable(5375717,
                    new LootItem(1233, 1, 1, 50700), // Black dagger(p)
                    new LootItem(1353, 1, 1, 60800), // Steel axe
                    new LootItem(1225, 1, 1, 40500), // Mithril dagger(p)
                    new LootItem(1389, 1, 1, 60800), // Magic staff
                    new LootItem(1241, 1, 1, 40500), // Steel spear
                    new LootItem(1255, 1, 1, 40500), // Steel spear(p)
                    new LootItem(1424, 1, 1, 30400), // Steel mace
                    new LootItem(1325, 1, 1, 30400), // Steel scimitar
                    new LootItem(1281, 1, 1, 40500), // Steel sword
                    new LootItem(1361, 1, 1, 10100), // Black axe
                    new LootItem(1217, 1, 1, 60800), // Black dagger
                    new LootItem(1426, 1, 1, 20300), // Black mace
                    new LootItem(1209, 1, 1, 50700), // Mithril dagger
                    new LootItem(1295, 1, 1, 327), // Steel longsword
                    new LootItem(4580, 1, 1, 327) // Black spear
            )
            .addTable(4455308,
                    new LootItem(1141, 1, 1, 50700), // Steel med helm
                    new LootItem(562, 12, 29, 100999), // Chaos rune
                    new LootItem(1729, 1, 1, 70900), // Amulet of defence
                    new LootItem(1637, 1, 1, 40500), // Sapphire ring
                    new LootItem(2355, 3, 3, 60800), // Silver bar
                    new LootItem(2357, 3, 3, 50700), // Gold bar
                    new LootItem(2359, 3, 3, 50700), // Mithril bar
                    new LootItem(24362, 1, 1, 20300) // Clue scroll (easy)
            )
    ),
    STEEL_RED(4116, Items.STEEL_KEY_RED, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 15, 30, 0),
                    new LootItem(Items.ECTOTOKEN, 1, 2, 0)
            )
            .addTable(157917,
                    new LootItem(25445, 1, 1, 15900) // Steel locks
            )
            .addTable(4288212,
                    new LootItem(1311, 1, 1, 7440), // Steel 2h sword
                    new LootItem(1339, 1, 1, 44700), // Steel warhammer
                    new LootItem(1365, 1, 1, 37200), // Steel battleaxe
                    new LootItem(1341, 1, 1, 44700), // Black warhammer
                    new LootItem(4580, 1, 1, 7440), // Black spear
                    new LootItem(1327, 1, 1, 74400), // Black scimitar
                    new LootItem(1283, 1, 1, 22300), // Black sword
                    new LootItem(1297, 1, 1, 14900), // Black longsword
                    new LootItem(4580, 1, 1, 7440), // Black spear
                    new LootItem(1428, 1, 1, 52100), // Mithril mace
                    new LootItem(1243, 1, 1, 29800), // Mithril spear
                    new LootItem(1257, 1, 1, 29800), // Mithril spear(p)
                    new LootItem(1285, 1, 1, 22300), // Mithril sword
                    new LootItem(1211, 1, 1, 29800), // Adamant dagger
                    new LootItem(1227, 1, 1, 7440) // Adamant dagger(p)
            )
            .addTable(1625857,
                    new LootItem(1097, 1, 1, 37200), // Studded chaps
                    new LootItem(1105, 1, 1, 37200), // Steel chainbody
                    new LootItem(1193, 1, 1, 14900), // Steel kiteshield
                    new LootItem(1151, 1, 1, 52100), // Black med helm
                    new LootItem(1143, 1, 1, 22300) // Mithril med helm
            )
            .addTable(3928011,
                    new LootItem(2361, 3, 3, 44700), // Adamantite bar
                    new LootItem(1639, 1, 1, 14900), // Emerald ring
                    new LootItem(1725, 1, 1, 44700), // Amulet of strength
                    new LootItem(562, 10, 29, 81900), // Chaos rune
                    new LootItem(561, 10, 29, 111999), // Nature rune
                    new LootItem(1520, 5, 14, 74400), // Willow logs (noted)
                    new LootItem(24363, 1, 1, 22300), // Clue scroll (medium)
                    new LootItem(3470, 1, 1, 594) // Fine cloth
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 1170),
                    new LootItem(Items.FLAMTAER_BAG, 1, 48),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    STEEL_BROWN(4117, Items.STEEL_KEY_BROWN, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 15, 30, 0),
                    new LootItem(Items.ECTOTOKEN, 1, 2, 0)
            )
            .addTable(160119,
                    new LootItem(25445, 1, 1, 16100) // Steel locks
            )
            .addTable(4291994,
                    new LootItem(1311, 1, 1, 7440), // Steel 2h sword
                    new LootItem(1339, 1, 1, 44600), // Steel warhammer
                    new LootItem(1365, 1, 1, 37200), // Steel battleaxe
                    new LootItem(1341, 1, 1, 44600), // Black warhammer
                    new LootItem(4580, 1, 1, 7440), // Black spear
                    new LootItem(1327, 1, 1, 74400), // Black scimitar
                    new LootItem(1283, 1, 1, 22300), // Black sword
                    new LootItem(1297, 1, 1, 14900), // Black longsword
                    new LootItem(4580, 1, 1, 7440), // Black spear
                    new LootItem(1428, 1, 1, 52100), // Mithril mace
                    new LootItem(1243, 1, 1, 29800), // Mithril spear
                    new LootItem(1257, 1, 1, 29800), // Mithril spear(p)
                    new LootItem(1285, 1, 1, 22300), // Mithril sword
                    new LootItem(1211, 1, 1, 29800), // Adamant dagger
                    new LootItem(1227, 1, 1, 7440) // Adamant dagger(p)
            )
            .addTable(1628045,
                    new LootItem(1097, 1, 1, 37200), // Studded chaps
                    new LootItem(1105, 1, 1, 37200), // Steel chainbody
                    new LootItem(1193, 1, 1, 14900), // Steel kiteshield
                    new LootItem(1151, 1, 1, 52100), // Black med helm
                    new LootItem(1143, 1, 1, 22300) // Mithril med helm
            )
            .addTable(3919840,
                    new LootItem(3470, 1, 1, 7440), // Fine cloth
                    new LootItem(2361, 3, 3, 44600), // Adamantite bar
                    new LootItem(1639, 1, 1, 14900), // Emerald ring
                    new LootItem(1725, 1, 1, 44600), // Amulet of strength
                    new LootItem(562, 10, 29, 81900), // Chaos rune
                    new LootItem(561, 10, 29, 104000), // Nature rune
                    new LootItem(1520, 5, 14, 74400), // Willow logs (noted)
                    new LootItem(24363, 1, 1, 22300) // Clue scroll (medium)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 1170),
                    new LootItem(Items.FLAMTAER_BAG, 1, 48),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    STEEL_CRIMSON(4118, Items.STEEL_KEY_CRIMSON, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 15, 30, 0),
                    new LootItem(Items.ECTOTOKEN, 1, 2, 0)
            )
            .addTable(163034,
                    new LootItem(25445, 1, 1, 16399) // Steel locks
            )
            .addTable(4290462,
                    new LootItem(1311, 1, 1, 7440), // Steel 2h sword
                    new LootItem(1339, 1, 1, 44600), // Steel warhammer
                    new LootItem(1365, 1, 1, 37200), // Steel battleaxe
                    new LootItem(1341, 1, 1, 44600), // Black warhammer
                    new LootItem(4580, 1, 1, 7440), // Black spear
                    new LootItem(1327, 1, 1, 74400), // Black scimitar
                    new LootItem(1283, 1, 1, 22300), // Black sword
                    new LootItem(1297, 1, 1, 14900), // Black longsword
                    new LootItem(4580, 1, 1, 7440), // Black spear
                    new LootItem(1428, 1, 1, 52100), // Mithril mace
                    new LootItem(1243, 1, 1, 29800), // Mithril spear
                    new LootItem(1257, 1, 1, 29800), // Mithril spear(p)
                    new LootItem(1285, 1, 1, 22300), // Mithril sword
                    new LootItem(1211, 1, 1, 29800), // Adamant dagger
                    new LootItem(1227, 1, 1, 7440) // Adamant dagger(p)
            )
            .addTable(1627464,
                    new LootItem(1097, 1, 1, 37200), // Studded chaps
                    new LootItem(1105, 1, 1, 37200), // Steel chainbody
                    new LootItem(1193, 1, 1, 14900), // Steel kiteshield
                    new LootItem(1151, 1, 1, 52100), // Black med helm
                    new LootItem(1143, 1, 1, 22300) // Mithril med helm
            )
            .addTable(3919038,
                    new LootItem(3470, 1, 1, 14900), // Fine cloth
                    new LootItem(2361, 3, 3, 44600), // Adamantite bar
                    new LootItem(1639, 1, 1, 14900), // Emerald ring
                    new LootItem(1725, 1, 1, 44600), // Amulet of strength
                    new LootItem(562, 10, 29, 81800), // Chaos rune
                    new LootItem(561, 10, 29, 96700), // Nature rune
                    new LootItem(1520, 5, 14, 74400), // Willow logs (noted)
                    new LootItem(24363, 1, 1, 22300) // Clue scroll (medium)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 1170),
                    new LootItem(Items.FLAMTAER_BAG, 1, 48),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    STEEL_BLACK(4119, Items.STEEL_KEY_BLACK, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 15, 30, 0),
                    new LootItem(Items.ECTOTOKEN, 1, 2, 0)
            )
            .addTable(166043,
                    new LootItem(25445, 1, 1, 16700) // Steel locks
            )
            .addTable(4287901,
                    new LootItem(1311, 1, 1, 7440), // Steel 2h sword
                    new LootItem(1339, 1, 1, 44600), // Steel warhammer
                    new LootItem(1365, 1, 1, 37200), // Steel battleaxe
                    new LootItem(1341, 1, 1, 44600), // Black warhammer
                    new LootItem(4580, 1, 1, 7440), // Black spear
                    new LootItem(1327, 1, 1, 74400), // Black scimitar
                    new LootItem(1283, 1, 1, 22300), // Black sword
                    new LootItem(1297, 1, 1, 14900), // Black longsword
                    new LootItem(4580, 1, 1, 7440), // Black spear
                    new LootItem(1428, 1, 1, 52100), // Mithril mace
                    new LootItem(1243, 1, 1, 29700), // Mithril spear
                    new LootItem(1257, 1, 1, 29700), // Mithril spear(p)
                    new LootItem(1285, 1, 1, 22300), // Mithril sword
                    new LootItem(1211, 1, 1, 29700), // Adamant dagger
                    new LootItem(1227, 1, 1, 7440) // Adamant dagger(p)
            )
            .addTable(1627624,
                    new LootItem(1097, 1, 1, 37200), // Studded chaps
                    new LootItem(1105, 1, 1, 37200), // Steel chainbody
                    new LootItem(1193, 1, 1, 14900), // Steel kiteshield
                    new LootItem(1151, 1, 1, 52100), // Black med helm
                    new LootItem(1143, 1, 1, 22300) // Mithril med helm
            )
            .addTable(3918429,
                    new LootItem(3470, 1, 1, 22300), // Fine cloth
                    new LootItem(2361, 3, 3, 44600), // Adamantite bar
                    new LootItem(1639, 1, 1, 14900), // Emerald ring
                    new LootItem(1725, 1, 1, 44600), // Amulet of strength
                    new LootItem(562, 10, 29, 81800), // Chaos rune
                    new LootItem(561, 10, 29, 89200), // Nature rune
                    new LootItem(1520, 5, 14, 74400), // Willow logs (noted)
                    new LootItem(24363, 1, 1, 22300) // Clue scroll (medium)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 1170),
                    new LootItem(Items.FLAMTAER_BAG, 1, 48),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    STEEL_PURPLE(4120, Items.STEEL_KEY_PURPLE, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 15, 30, 0),
                    new LootItem(Items.ECTOTOKEN, 1, 2, 0)
            )
            .addTable(168072,
                    new LootItem(25445, 1, 1, 16900) // Steel locks
            )
            .addTable(4286536,
                    new LootItem(1311, 1, 1, 7430), // Steel 2h sword
                    new LootItem(1339, 1, 1, 44600), // Steel warhammer
                    new LootItem(1365, 1, 1, 37200), // Steel battleaxe
                    new LootItem(1341, 1, 1, 44600), // Black warhammer
                    new LootItem(4580, 1, 1, 7430), // Black spear
                    new LootItem(1327, 1, 1, 74299), // Black scimitar
                    new LootItem(1283, 1, 1, 22300), // Black sword
                    new LootItem(1297, 1, 1, 14900), // Black longsword
                    new LootItem(4580, 1, 1, 7430), // Black spear
                    new LootItem(1428, 1, 1, 52000), // Mithril mace
                    new LootItem(1243, 1, 1, 29700), // Mithril spear
                    new LootItem(1257, 1, 1, 29700), // Mithril spear(p)
                    new LootItem(1285, 1, 1, 22300), // Mithril sword
                    new LootItem(1211, 1, 1, 29700), // Adamant dagger
                    new LootItem(1227, 1, 1, 7430) // Adamant dagger(p)
            )
            .addTable(1627022,
                    new LootItem(1097, 1, 1, 37200), // Studded chaps
                    new LootItem(1105, 1, 1, 37200), // Steel chainbody
                    new LootItem(1193, 1, 1, 14900), // Steel kiteshield
                    new LootItem(1151, 1, 1, 52000), // Black med helm
                    new LootItem(1143, 1, 1, 22300) // Mithril med helm
            )
            .addTable(3918368,
                    new LootItem(3470, 1, 1, 29700), // Fine cloth
                    new LootItem(2361, 3, 3, 44600), // Adamantite bar
                    new LootItem(1639, 1, 1, 14900), // Emerald ring
                    new LootItem(1725, 1, 1, 44600), // Amulet of strength
                    new LootItem(562, 10, 29, 81800), // Chaos rune
                    new LootItem(561, 10, 29, 81800), // Nature rune
                    new LootItem(1520, 5, 14, 74299), // Willow logs (noted)
                    new LootItem(24363, 1, 1, 22300) // Clue scroll (medium)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 1170),
                    new LootItem(Items.FLAMTAER_BAG, 1, 48),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    BLACK_RED(4121, Items.BLACK_KEY_RED, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 25, 40, 0),
                    new LootItem(Items.ECTOTOKEN, 2, 3, 0)
            )
            .addTable(159746,
                    new LootItem(25448, 1, 1, 15900) // Black locks
            )
            .addTable(3066848,
                    new LootItem(1381, 1, 1, 7090), // Staff of air
                    new LootItem(1383, 1, 1, 7090), // Staff of water
                    new LootItem(1385, 1, 1, 7090), // Staff of earth
                    new LootItem(1387, 1, 1, 7090), // Staff of fire
                    new LootItem(1315, 1, 1, 14199), // Mithril 2h sword
                    new LootItem(1343, 1, 1, 14199), // Mithril warhammer
                    new LootItem(1369, 1, 1, 14199), // Mithril battleaxe
                    new LootItem(1299, 1, 1, 42600), // Mithril longsword
                    new LootItem(1329, 1, 1, 14199), // Mithril scimitar
                    new LootItem(1331, 1, 1, 14199), // Adamant scimitar
                    new LootItem(1430, 1, 1, 42600), // Adamant mace
                    new LootItem(1287, 1, 1, 14199), // Adamant sword
                    new LootItem(1357, 1, 1, 42600), // Adamant axe
                    new LootItem(4580, 1, 1, 28399), // Black spear
                    new LootItem(1367, 1, 1, 21300), // Black battleaxe
                    new LootItem(1313, 1, 1, 14199) // Black 2h sword
            )
            .addTable(3351568,
                    new LootItem(1119, 1, 1, 14199), // Steel platebody
                    new LootItem(1107, 1, 1, 35500), // Black chainbody
                    new LootItem(1195, 1, 1, 42600), // Black kiteshield
                    new LootItem(1179, 1, 1, 63799), // Black sq shield
                    new LootItem(1077, 1, 1, 14199), // Black platelegs
                    new LootItem(1089, 1, 1, 14199), // Black plateskirt
                    new LootItem(1165, 1, 1, 21300), // Black full helm
                    new LootItem(1197, 1, 1, 14199), // Mithril kiteshield
                    new LootItem(1181, 1, 1, 28399), // Mithril sq shield
                    new LootItem(1159, 1, 1, 42600), // Mithril full helm
                    new LootItem(1071, 1, 1, 14199), // Mithril platelegs
                    new LootItem(1109, 1, 1, 14199), // Mithril chainbody
                    new LootItem(1145, 1, 1, 14199) // Adamant med helm
            )
            .addTable(3421836,
                    new LootItem(3470, 1, 1, 49699), // Fine cloth
                    new LootItem(1641, 1, 1, 49699), // Ruby ring
                    new LootItem(1727, 1, 1, 14199), // Amulet of magic
                    new LootItem(561, 10, 29, 46799), // Nature rune
                    new LootItem(560, 10, 29, 31200), // Death rune
                    new LootItem(1520, 5, 14, 68100), // Willow logs (noted)
                    new LootItem(1516, 5, 14, 45400), // Yew logs (noted)
                    new LootItem(24363, 1, 1, 28399), // Clue scroll (medium)
                    new LootItem(3678, 1, 1, 7090) // Flamtaer hammer
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 360),
                    new LootItem(Items.FLAMTAER_BAG, 1, 19),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    BLACK_BROWN(4122, Items.BLACK_KEY_BROWN, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 25, 40, 0),
                    new LootItem(Items.ECTOTOKEN, 2, 3, 0)
            )
            .addTable(161853,
                    new LootItem(25448, 1, 1, 16100) // Black locks
            )
            .addTable(3065679,
                    new LootItem(1381, 1, 1, 7090), // Staff of air
                    new LootItem(1383, 1, 1, 7090), // Staff of water
                    new LootItem(1385, 1, 1, 7090), // Staff of earth
                    new LootItem(1387, 1, 1, 7090), // Staff of fire
                    new LootItem(1315, 1, 1, 14199), // Mithril 2h sword
                    new LootItem(1343, 1, 1, 14199), // Mithril warhammer
                    new LootItem(1369, 1, 1, 14199), // Mithril battleaxe
                    new LootItem(1299, 1, 1, 42500), // Mithril longsword
                    new LootItem(1329, 1, 1, 14199), // Mithril scimitar
                    new LootItem(1331, 1, 1, 14199), // Adamant scimitar
                    new LootItem(1430, 1, 1, 42500), // Adamant mace
                    new LootItem(1287, 1, 1, 14199), // Adamant sword
                    new LootItem(1357, 1, 1, 42500), // Adamant axe
                    new LootItem(4580, 1, 1, 28399), // Black spear
                    new LootItem(1367, 1, 1, 21300), // Black battleaxe
                    new LootItem(1313, 1, 1, 14199) // Black 2h sword
            )
            .addTable(3351576,
                    new LootItem(1119, 1, 1, 14199), // Steel platebody
                    new LootItem(1107, 1, 1, 35500), // Black chainbody
                    new LootItem(1195, 1, 1, 42500), // Black kiteshield
                    new LootItem(1179, 1, 1, 63799), // Black sq shield
                    new LootItem(1077, 1, 1, 14199), // Black platelegs
                    new LootItem(1089, 1, 1, 14199), // Black plateskirt
                    new LootItem(1165, 1, 1, 21300), // Black full helm
                    new LootItem(1197, 1, 1, 14199), // Mithril kiteshield
                    new LootItem(1181, 1, 1, 28399), // Mithril sq shield
                    new LootItem(1159, 1, 1, 42500), // Mithril full helm
                    new LootItem(1071, 1, 1, 14199), // Mithril platelegs
                    new LootItem(1109, 1, 1, 14199), // Mithril chainbody
                    new LootItem(1145, 1, 1, 14199) // Adamant med helm
            )
            .addTable(3420891,
                    new LootItem(3470, 1, 1, 56700), // Fine cloth
                    new LootItem(1641, 1, 1, 49600), // Ruby ring
                    new LootItem(1727, 1, 1, 14199), // Amulet of magic
                    new LootItem(561, 10, 29, 46799), // Nature rune
                    new LootItem(560, 10, 29, 31200), // Death rune
                    new LootItem(1520, 5, 14, 63799), // Willow logs (noted)
                    new LootItem(1516, 5, 14, 42500), // Yew logs (noted)
                    new LootItem(24363, 1, 1, 28399), // Clue scroll (medium)
                    new LootItem(3678, 1, 1, 7090) // Flamtaer hammer
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 360),
                    new LootItem(Items.FLAMTAER_BAG, 1, 19),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    BLACK_CRIMSON(4123, Items.BLACK_KEY_CRIMSON, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 25, 40, 0),
                    new LootItem(Items.ECTOTOKEN, 2, 3, 0)
            )
            .addTable(164825,
                    new LootItem(25448, 1, 1, 16399) // Black locks
            )
            .addTable(3065066,
                    new LootItem(1381, 1, 1, 7090), // Staff of air
                    new LootItem(1383, 1, 1, 7090), // Staff of water
                    new LootItem(1385, 1, 1, 7090), // Staff of earth
                    new LootItem(1387, 1, 1, 7090), // Staff of fire
                    new LootItem(1315, 1, 1, 14199), // Mithril 2h sword
                    new LootItem(1343, 1, 1, 14199), // Mithril warhammer
                    new LootItem(1369, 1, 1, 14199), // Mithril battleaxe
                    new LootItem(1299, 1, 1, 42500), // Mithril longsword
                    new LootItem(1329, 1, 1, 14199), // Mithril scimitar
                    new LootItem(1331, 1, 1, 14199), // Adamant scimitar
                    new LootItem(1430, 1, 1, 42500), // Adamant mace
                    new LootItem(1287, 1, 1, 14199), // Adamant sword
                    new LootItem(1357, 1, 1, 42500), // Adamant axe
                    new LootItem(4580, 1, 1, 28399), // Black spear
                    new LootItem(1367, 1, 1, 21300), // Black battleaxe
                    new LootItem(1313, 1, 1, 14199) // Black 2h sword
            )
            .addTable(3349900,
                    new LootItem(1119, 1, 1, 14199), // Steel platebody
                    new LootItem(1107, 1, 1, 35400), // Black chainbody
                    new LootItem(1195, 1, 1, 42500), // Black kiteshield
                    new LootItem(1179, 1, 1, 63799), // Black sq shield
                    new LootItem(1077, 1, 1, 14199), // Black platelegs
                    new LootItem(1089, 1, 1, 14199), // Black plateskirt
                    new LootItem(1165, 1, 1, 21300), // Black full helm
                    new LootItem(1197, 1, 1, 14199), // Mithril kiteshield
                    new LootItem(1181, 1, 1, 28399), // Mithril sq shield
                    new LootItem(1159, 1, 1, 42500), // Mithril full helm
                    new LootItem(1071, 1, 1, 14199), // Mithril platelegs
                    new LootItem(1109, 1, 1, 14199), // Mithril chainbody
                    new LootItem(1145, 1, 1, 14199) // Adamant med helm
            )
            .addTable(3420207,
                    new LootItem(3470, 1, 1, 63799), // Fine cloth
                    new LootItem(1641, 1, 1, 49600), // Ruby ring
                    new LootItem(1727, 1, 1, 14199), // Amulet of magic
                    new LootItem(561, 10, 29, 46799), // Nature rune
                    new LootItem(560, 10, 29, 31200), // Death rune
                    new LootItem(1520, 5, 14, 59500), // Willow logs (noted)
                    new LootItem(1516, 5, 14, 39700), // Yew logs (noted)
                    new LootItem(24363, 1, 1, 28399), // Clue scroll (medium)
                    new LootItem(3678, 1, 1, 7090) // Flamtaer hammer
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 360),
                    new LootItem(Items.FLAMTAER_BAG, 1, 19),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    BLACK_BLACK(4124, Items.BLACK_KEY_BLACK, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 25, 40, 0),
                    new LootItem(Items.ECTOTOKEN, 2, 3, 0)
            )
            .addTable(167833,
                    new LootItem(25448, 1, 1, 16700) // Black locks
            )
            .addTable(3063747,
                    new LootItem(1381, 1, 1, 7090), // Staff of air
                    new LootItem(1383, 1, 1, 7090), // Staff of water
                    new LootItem(1385, 1, 1, 7090), // Staff of earth
                    new LootItem(1387, 1, 1, 7090), // Staff of fire
                    new LootItem(1315, 1, 1, 14199), // Mithril 2h sword
                    new LootItem(1343, 1, 1, 14199), // Mithril warhammer
                    new LootItem(1369, 1, 1, 14199), // Mithril battleaxe
                    new LootItem(1299, 1, 1, 42500), // Mithril longsword
                    new LootItem(1329, 1, 1, 14199), // Mithril scimitar
                    new LootItem(1331, 1, 1, 14199), // Adamant scimitar
                    new LootItem(1430, 1, 1, 42500), // Adamant mace
                    new LootItem(1287, 1, 1, 14199), // Adamant sword
                    new LootItem(1357, 1, 1, 42500), // Adamant axe
                    new LootItem(4580, 1, 1, 28300), // Black spear
                    new LootItem(1367, 1, 1, 21300), // Black battleaxe
                    new LootItem(1313, 1, 1, 14199) // Black 2h sword
            )
            .addTable(3348552,
                    new LootItem(1119, 1, 1, 14199), // Steel platebody
                    new LootItem(1107, 1, 1, 35400), // Black chainbody
                    new LootItem(1195, 1, 1, 42500), // Black kiteshield
                    new LootItem(1179, 1, 1, 63799), // Black sq shield
                    new LootItem(1077, 1, 1, 14199), // Black platelegs
                    new LootItem(1089, 1, 1, 14199), // Black plateskirt
                    new LootItem(1165, 1, 1, 21300), // Black full helm
                    new LootItem(1197, 1, 1, 14199), // Mithril kiteshield
                    new LootItem(1181, 1, 1, 28300), // Mithril sq shield
                    new LootItem(1159, 1, 1, 42500), // Mithril full helm
                    new LootItem(1071, 1, 1, 14199), // Mithril platelegs
                    new LootItem(1109, 1, 1, 14199), // Mithril chainbody
                    new LootItem(1145, 1, 1, 14199) // Adamant med helm
            )
            .addTable(3419866,
                    new LootItem(3470, 1, 1, 70900), // Fine cloth
                    new LootItem(1641, 1, 1, 49600), // Ruby ring
                    new LootItem(1727, 1, 1, 14199), // Amulet of magic
                    new LootItem(561, 10, 29, 46799), // Nature rune
                    new LootItem(560, 10, 29, 31200), // Death rune
                    new LootItem(1520, 5, 14, 55300), // Willow logs (noted)
                    new LootItem(1516, 5, 14, 36900), // Yew logs (noted)
                    new LootItem(24363, 1, 1, 28300), // Clue scroll (medium)
                    new LootItem(3678, 1, 1, 7090) // Flamtaer hammer
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 360),
                    new LootItem(Items.FLAMTAER_BAG, 1, 19),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    BLACK_PURPLE(4125, Items.BLACK_KEY_PURPLE, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 25, 40, 0),
                    new LootItem(Items.ECTOTOKEN, 2, 3, 0)
            )
            .addTable(169853,
                    new LootItem(25448, 1, 1, 16900) // Black locks
            )
            .addTable(3063474,
                    new LootItem(1381, 1, 1, 7079), // Staff of air
                    new LootItem(1383, 1, 1, 7079), // Staff of water
                    new LootItem(1385, 1, 1, 7079), // Staff of earth
                    new LootItem(1387, 1, 1, 7079), // Staff of fire
                    new LootItem(1315, 1, 1, 14199), // Mithril 2h sword
                    new LootItem(1343, 1, 1, 14199), // Mithril warhammer
                    new LootItem(1369, 1, 1, 14199), // Mithril battleaxe
                    new LootItem(1299, 1, 1, 42500), // Mithril longsword
                    new LootItem(1329, 1, 1, 14199), // Mithril scimitar
                    new LootItem(1331, 1, 1, 14199), // Adamant scimitar
                    new LootItem(1430, 1, 1, 42500), // Adamant mace
                    new LootItem(1287, 1, 1, 14199), // Adamant sword
                    new LootItem(1357, 1, 1, 42500), // Adamant axe
                    new LootItem(4580, 1, 1, 28300), // Black spear
                    new LootItem(1367, 1, 1, 21300), // Black battleaxe
                    new LootItem(1313, 1, 1, 14199) // Black 2h sword
            )
            .addTable(3348737,
                    new LootItem(1119, 1, 1, 14199), // Steel platebody
                    new LootItem(1107, 1, 1, 35400), // Black chainbody
                    new LootItem(1195, 1, 1, 42500), // Black kiteshield
                    new LootItem(1179, 1, 1, 63799), // Black sq shield
                    new LootItem(1077, 1, 1, 14199), // Black platelegs
                    new LootItem(1089, 1, 1, 14199), // Black plateskirt
                    new LootItem(1165, 1, 1, 21300), // Black full helm
                    new LootItem(1197, 1, 1, 14199), // Mithril kiteshield
                    new LootItem(1181, 1, 1, 28300), // Mithril sq shield
                    new LootItem(1159, 1, 1, 42500), // Mithril full helm
                    new LootItem(1071, 1, 1, 14199), // Mithril platelegs
                    new LootItem(1109, 1, 1, 14199), // Mithril chainbody
                    new LootItem(1145, 1, 1, 14199) // Adamant med helm
            )
            .addTable(3417934,
                    new LootItem(3470, 1, 1, 77900), // Fine cloth
                    new LootItem(1641, 1, 1, 49600), // Ruby ring
                    new LootItem(1727, 1, 1, 14199), // Amulet of magic
                    new LootItem(561, 10, 29, 46799), // Nature rune
                    new LootItem(560, 10, 29, 31200), // Death rune
                    new LootItem(1520, 5, 14, 51000), // Willow logs (noted)
                    new LootItem(1516, 5, 14, 34000), // Yew logs (noted)
                    new LootItem(24363, 1, 1, 28300), // Clue scroll (medium)
                    new LootItem(3678, 1, 1, 7079) // Flamtaer hammer
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 360),
                    new LootItem(Items.FLAMTAER_BAG, 1, 19),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    SILVER_RED(4126, Items.SILVER_KEY_RED, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 25, 40, 0),
                    new LootItem(Items.ECTOTOKEN, 3, 4, 0)
            )
            .addTable(156894,
                    new LootItem(25451, 1, 1, 15900) // Silver locks
            )
            .addTable(3562790,
                    new LootItem(1245, 1, 1, 72200), // Adamant spear
                    new LootItem(1259, 1, 1, 72200), // Adamant spear(p)
                    new LootItem(4580, 1, 1, 39400), // Black spear
                    new LootItem(1289, 1, 1, 19700), // Rune sword
                    new LootItem(1391, 1, 1, 59100), // Battlestaff
                    new LootItem(1345, 1, 1, 19700), // Adamant warhammer
                    new LootItem(1371, 1, 1, 19700), // Adamant battleaxe
                    new LootItem(1317, 1, 1, 19700), // Adamant 2h sword
                    new LootItem(1301, 1, 1, 19700), // Adamant longsword
                    new LootItem(1333, 1, 1, 13100), // Rune scimitar
                    new LootItem(1303, 1, 1, 6560) // Rune longsword
            )
            .addTable(1941546,
                    new LootItem(1125, 1, 1, 26200), // Black platebody
                    new LootItem(1085, 1, 1, 26200), // Mithril plateskirt
                    new LootItem(1147, 1, 1, 19700), // Rune med helm
                    new LootItem(1111, 1, 1, 19700), // Adamant chainbody
                    new LootItem(1073, 1, 1, 19700), // Adamant platelegs
                    new LootItem(1091, 1, 1, 19700), // Adamant plateskirt
                    new LootItem(1199, 1, 1, 19700), // Adamant kiteshield
                    new LootItem(1123, 1, 1, 13100), // Adamant platebody
                    new LootItem(1183, 1, 1, 13100), // Adamant sq shield
                    new LootItem(1121, 1, 1, 13100), // Mithril platebody
                    new LootItem(1113, 1, 1, 6560) // Rune chainbody
            )
            .addTable(4338768,
                    new LootItem(3470, 1, 1, 105000), // Fine cloth
                    new LootItem(12851, 1, 1, 65599), // Amulet of the damned (full)
                    new LootItem(1643, 1, 1, 39400), // Diamond ring
                    new LootItem(1731, 1, 1, 13100), // Amulet of power
                    new LootItem(560, 10, 30, 59100), // Death rune
                    new LootItem(565, 10, 30, 39400), // Blood rune
                    new LootItem(3678, 1, 1, 72200), // Flamtaer hammer
                    new LootItem(1516, 5, 14, 19700), // Yew logs (noted)
                    new LootItem(1514, 5, 14, 13100), // Magic logs (noted)
                    new LootItem(24364, 1, 1, 13100) // Clue scroll (hard)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 145),
                    new LootItem(Items.FLAMTAER_BAG, 1, 10),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    SILVER_BROWN(4127, Items.SILVER_KEY_BROWN, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 25, 40, 0),
                    new LootItem(Items.ECTOTOKEN, 3, 4, 0)
            )
            .addTable(158805,
                    new LootItem(25451, 1, 1, 16100) // Silver locks
            )
            .addTable(3560402,
                    new LootItem(1245, 1, 1, 72200), // Adamant spear
                    new LootItem(1259, 1, 1, 72200), // Adamant spear(p)
                    new LootItem(4580, 1, 1, 39400), // Black spear
                    new LootItem(1289, 1, 1, 19700), // Rune sword
                    new LootItem(1391, 1, 1, 59000), // Battlestaff
                    new LootItem(1345, 1, 1, 19700), // Adamant warhammer
                    new LootItem(1371, 1, 1, 19700), // Adamant battleaxe
                    new LootItem(1317, 1, 1, 19700), // Adamant 2h sword
                    new LootItem(1301, 1, 1, 19700), // Adamant longsword
                    new LootItem(1333, 1, 1, 13100), // Rune scimitar
                    new LootItem(1303, 1, 1, 6560) // Rune longsword
            )
            .addTable(1940782,
                    new LootItem(1125, 1, 1, 26200), // Black platebody
                    new LootItem(1085, 1, 1, 26200), // Mithril plateskirt
                    new LootItem(1147, 1, 1, 19700), // Rune med helm
                    new LootItem(1111, 1, 1, 19700), // Adamant chainbody
                    new LootItem(1073, 1, 1, 19700), // Adamant platelegs
                    new LootItem(1091, 1, 1, 19700), // Adamant plateskirt
                    new LootItem(1199, 1, 1, 19700), // Adamant kiteshield
                    new LootItem(1123, 1, 1, 13100), // Adamant platebody
                    new LootItem(1183, 1, 1, 13100), // Adamant sq shield
                    new LootItem(1121, 1, 1, 13100), // Mithril platebody
                    new LootItem(1113, 1, 1, 6560) // Rune chainbody
            )
            .addTable(4340009,
                    new LootItem(3470, 1, 1, 111999), // Fine cloth
                    new LootItem(12851, 1, 1, 65599), // Amulet of the damned (full)
                    new LootItem(1643, 1, 1, 39400), // Diamond ring
                    new LootItem(1731, 1, 1, 13100), // Amulet of power
                    new LootItem(560, 10, 30, 59000), // Death rune
                    new LootItem(565, 10, 30, 39400), // Blood rune
                    new LootItem(3678, 1, 1, 72200), // Flamtaer hammer
                    new LootItem(1516, 5, 10, 15700), // Yew logs (noted)
                    new LootItem(1514, 5, 10, 10500), // Magic logs (noted)
                    new LootItem(24364, 1, 1, 13100) // Clue scroll (hard)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 145),
                    new LootItem(Items.FLAMTAER_BAG, 1, 10),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    SILVER_CRIMSON(4128, Items.SILVER_KEY_CRIMSON, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 25, 40, 0),
                    new LootItem(Items.ECTOTOKEN, 3, 4, 0)
            )
            .addTable(161887,
                    new LootItem(25451, 1, 1, 16399) // Silver locks
            )
            .addTable(3560358,
                    new LootItem(1245, 1, 1, 72100), // Adamant spear
                    new LootItem(1259, 1, 1, 72100), // Adamant spear(p)
                    new LootItem(4580, 1, 1, 39300), // Black spear
                    new LootItem(1289, 1, 1, 19700), // Rune sword
                    new LootItem(1391, 1, 1, 59000), // Battlestaff
                    new LootItem(1345, 1, 1, 19700), // Adamant warhammer
                    new LootItem(1371, 1, 1, 19700), // Adamant battleaxe
                    new LootItem(1317, 1, 1, 19700), // Adamant 2h sword
                    new LootItem(1301, 1, 1, 19700), // Adamant longsword
                    new LootItem(1333, 1, 1, 13100), // Rune scimitar
                    new LootItem(1303, 1, 1, 6560) // Rune longsword
            )
            .addTable(1942372,
                    new LootItem(1125, 1, 1, 26200), // Black platebody
                    new LootItem(1085, 1, 1, 26200), // Mithril plateskirt
                    new LootItem(1147, 1, 1, 19700), // Rune med helm
                    new LootItem(1111, 1, 1, 19700), // Adamant chainbody
                    new LootItem(1073, 1, 1, 19700), // Adamant platelegs
                    new LootItem(1091, 1, 1, 19700), // Adamant plateskirt
                    new LootItem(1199, 1, 1, 19700), // Adamant kiteshield
                    new LootItem(1123, 1, 1, 13100), // Adamant platebody
                    new LootItem(1183, 1, 1, 13100), // Adamant sq shield
                    new LootItem(1121, 1, 1, 13100), // Mithril platebody
                    new LootItem(1113, 1, 1, 6560) // Rune chainbody
            )
            .addTable(4335382,
                    new LootItem(3470, 1, 1, 118000), // Fine cloth
                    new LootItem(12851, 1, 1, 65599), // Amulet of the damned (full)
                    new LootItem(1643, 1, 1, 39300), // Diamond ring
                    new LootItem(1731, 1, 1, 13100), // Amulet of power
                    new LootItem(560, 10, 30, 59000), // Death rune
                    new LootItem(565, 10, 30, 39300), // Blood rune
                    new LootItem(3678, 1, 1, 72100), // Flamtaer hammer
                    new LootItem(1516, 5, 10, 11800), // Yew logs (noted)
                    new LootItem(1514, 5, 10, 7870), // Magic logs (noted)
                    new LootItem(24364, 1, 1, 13100) // Clue scroll (hard)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 145),
                    new LootItem(Items.FLAMTAER_BAG, 1, 10),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    SILVER_BLACK(4129, Items.SILVER_KEY_BLACK, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 25, 40, 0),
                    new LootItem(Items.ECTOTOKEN, 3, 4, 0)
            )
            .addTable(164759,
                    new LootItem(25451, 1, 1, 16700) // Silver locks
            )
            .addTable(3558109,
                    new LootItem(1245, 1, 1, 72100), // Adamant spear
                    new LootItem(1259, 1, 1, 72100), // Adamant spear(p)
                    new LootItem(4580, 1, 1, 39300), // Black spear
                    new LootItem(1289, 1, 1, 19700), // Rune sword
                    new LootItem(1391, 1, 1, 59000), // Battlestaff
                    new LootItem(1345, 1, 1, 19700), // Adamant warhammer
                    new LootItem(1371, 1, 1, 19700), // Adamant battleaxe
                    new LootItem(1317, 1, 1, 19700), // Adamant 2h sword
                    new LootItem(1301, 1, 1, 19700), // Adamant longsword
                    new LootItem(1333, 1, 1, 13100), // Rune scimitar
                    new LootItem(1303, 1, 1, 6550) // Rune longsword
            )
            .addTable(1941101,
                    new LootItem(1125, 1, 1, 26200), // Black platebody
                    new LootItem(1085, 1, 1, 26200), // Mithril plateskirt
                    new LootItem(1147, 1, 1, 19700), // Rune med helm
                    new LootItem(1111, 1, 1, 19700), // Adamant chainbody
                    new LootItem(1073, 1, 1, 19700), // Adamant platelegs
                    new LootItem(1091, 1, 1, 19700), // Adamant plateskirt
                    new LootItem(1199, 1, 1, 19700), // Adamant kiteshield
                    new LootItem(1123, 1, 1, 13100), // Adamant platebody
                    new LootItem(1183, 1, 1, 13100), // Adamant sq shield
                    new LootItem(1121, 1, 1, 13100), // Mithril platebody
                    new LootItem(1113, 1, 1, 6550) // Rune chainbody
            )
            .addTable(4336029,
                    new LootItem(3470, 1, 1, 125000), // Fine cloth
                    new LootItem(12851, 1, 1, 65500), // Amulet of the damned (full)
                    new LootItem(1643, 1, 1, 39300), // Diamond ring
                    new LootItem(1731, 1, 1, 13100), // Amulet of power
                    new LootItem(560, 10, 30, 59000), // Death rune
                    new LootItem(565, 10, 30, 39300), // Blood rune
                    new LootItem(3678, 1, 1, 72100), // Flamtaer hammer
                    new LootItem(1516, 5, 10, 7860), // Yew logs (noted)
                    new LootItem(1514, 5, 10, 5240), // Magic logs (noted)
                    new LootItem(24364, 1, 1, 13100) // Clue scroll (hard)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 145),
                    new LootItem(Items.FLAMTAER_BAG, 1, 10),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    SILVER_PURPLE(4130, Items.SILVER_KEY_PURPLE, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 25, 40, 0),
                    new LootItem(Items.ECTOTOKEN, 3, 4, 0)
            )
            .addTable(166790,
                    new LootItem(25451, 1, 1, 16900) // Silver locks
            )
            .addTable(3559338,
                    new LootItem(1245, 1, 1, 72100), // Adamant spear
                    new LootItem(1259, 1, 1, 72100), // Adamant spear(p)
                    new LootItem(4580, 1, 1, 39300), // Black spear
                    new LootItem(1289, 1, 1, 19700), // Rune sword
                    new LootItem(1391, 1, 1, 59000), // Battlestaff
                    new LootItem(1345, 1, 1, 19700), // Adamant warhammer
                    new LootItem(1371, 1, 1, 19700), // Adamant battleaxe
                    new LootItem(1317, 1, 1, 19700), // Adamant 2h sword
                    new LootItem(1301, 1, 1, 19700), // Adamant longsword
                    new LootItem(1333, 1, 1, 13100), // Rune scimitar
                    new LootItem(1303, 1, 1, 6550) // Rune longsword
            )
            .addTable(1941771,
                    new LootItem(1125, 1, 1, 26200), // Black platebody
                    new LootItem(1085, 1, 1, 26200), // Mithril plateskirt
                    new LootItem(1147, 1, 1, 19700), // Rune med helm
                    new LootItem(1111, 1, 1, 19700), // Adamant chainbody
                    new LootItem(1073, 1, 1, 19700), // Adamant platelegs
                    new LootItem(1091, 1, 1, 19700), // Adamant plateskirt
                    new LootItem(1199, 1, 1, 19700), // Adamant kiteshield
                    new LootItem(1123, 1, 1, 13100), // Adamant platebody
                    new LootItem(1183, 1, 1, 13100), // Adamant sq shield
                    new LootItem(1121, 1, 1, 13100), // Mithril platebody
                    new LootItem(1113, 1, 1, 6550) // Rune chainbody
            )
            .addTable(4332099,
                    new LootItem(3470, 1, 1, 131000), // Fine cloth
                    new LootItem(12851, 1, 1, 65500), // Amulet of the damned (full)
                    new LootItem(1643, 1, 1, 39300), // Diamond ring
                    new LootItem(1731, 1, 1, 13100), // Amulet of power
                    new LootItem(560, 10, 30, 59000), // Death rune
                    new LootItem(565, 10, 30, 39300), // Blood rune
                    new LootItem(3678, 1, 1, 72100), // Flamtaer hammer
                    new LootItem(1516, 5, 10, 3930), // Yew logs (noted)
                    new LootItem(1514, 5, 10, 2620), // Magic logs (noted)
                    new LootItem(24364, 1, 1, 13100) // Clue scroll (hard)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 145),
                    new LootItem(Items.FLAMTAER_BAG, 1, 10),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1)   // Bloody notes
            )
    ),
    GOLD_RED(41214, 25424, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 40, 70, 0),
                    new LootItem(Items.ECTOTOKEN, 5, 0)
            )
            .addTable(161982,
                    new LootItem(25454, 1, 1, 15900) // Gold locks
            )
            .addTable(2970693,
                    new LootItem(1245, 1, 1, 48600), // Adamant spear
                    new LootItem(1259, 1, 1, 41700), // Adamant spear(p)
                    new LootItem(1289, 1, 1, 20800), // Rune sword
                    new LootItem(1301, 1, 1, 20800), // Adamant longsword
                    new LootItem(1392, 3, 3, 76400), // Battlestaff (noted)
                    new LootItem(1333, 1, 1, 20800), // Rune scimitar
                    new LootItem(1303, 1, 1, 34700), // Rune longsword
                    new LootItem(1215, 1, 1, 13900), // Dragon dagger
                    new LootItem(1305, 1, 1, 6950), // Dragon longsword
                    new LootItem(1434, 1, 1, 6950) // Dragon mace
            )
            .addTable(2758792,
                    new LootItem(1161, 1, 1, 55600), // Adamant full helm
                    new LootItem(1085, 1, 1, 34700), // Mithril plateskirt
                    new LootItem(1147, 1, 1, 20800), // Rune med helm
                    new LootItem(1111, 1, 1, 27800), // Adamant chainbody
                    new LootItem(1073, 1, 1, 20800), // Adamant platelegs
                    new LootItem(1091, 1, 1, 20800), // Adamant plateskirt
                    new LootItem(1199, 1, 1, 13900), // Adamant kiteshield
                    new LootItem(1123, 1, 1, 20800), // Adamant platebody
                    new LootItem(1113, 1, 1, 13900), // Rune chainbody
                    new LootItem(1127, 1, 1, 13900), // Rune platebody
                    new LootItem(1079, 1, 1, 13900), // Rune platelegs
                    new LootItem(1093, 1, 1, 13900) // Rune plateskirt
            )
            .addTable(4108532,
                    new LootItem(3470, 1, 1, 146000), // Fine cloth
                    new LootItem(12851, 1, 1, 41700), // Amulet of the damned (full)
                    new LootItem(1615, 1, 1, 13900), // Dragonstone
                    new LootItem(1645, 1, 1, 20800), // Dragonstone ring
                    new LootItem(560, 10, 29, 62500), // Death rune
                    new LootItem(566, 10, 29, 41700), // Soul rune
                    new LootItem(3678, 1, 1, 27800), // Flamtaer hammer
                    new LootItem(1516, 5, 14, 25000), // Yew logs (noted)
                    new LootItem(19670, 5, 14, 16700), // Redwood logs (noted)
                    new LootItem(24365, 1, 1, 7189) // Clue scroll (elite)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 98),
                    new LootItem(Items.FLAMTAER_BAG, 1, 11),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1),   // Bloody notes
                    new LootItem(Items.BONECRUSHER, 1, 1)
            )
    ),
    GOLD_BROWN(41213, 25426, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 40, 70, 0),
                    new LootItem(Items.ECTOTOKEN, 5, 0)
            )
            .addTable(163990,
                    new LootItem(25454, 1, 1, 16100) // Gold locks
            )
            .addTable(2969930,
                    new LootItem(1245, 1, 1, 48600), // Adamant spear
                    new LootItem(1259, 1, 1, 41700), // Adamant spear(p)
                    new LootItem(1289, 1, 1, 20800), // Rune sword
                    new LootItem(1301, 1, 1, 20800), // Adamant longsword
                    new LootItem(1392, 3, 3, 76400), // Battlestaff (noted)
                    new LootItem(1333, 1, 1, 20800), // Rune scimitar
                    new LootItem(1303, 1, 1, 34700), // Rune longsword
                    new LootItem(1215, 1, 1, 13900), // Dragon dagger
                    new LootItem(1305, 1, 1, 6939), // Dragon longsword
                    new LootItem(1434, 1, 1, 6939) // Dragon mace
            )
            .addTable(2758291,
                    new LootItem(1161, 1, 1, 55600), // Adamant full helm
                    new LootItem(1085, 1, 1, 34700), // Mithril plateskirt
                    new LootItem(1147, 1, 1, 20800), // Rune med helm
                    new LootItem(1111, 1, 1, 27800), // Adamant chainbody
                    new LootItem(1073, 1, 1, 20800), // Adamant platelegs
                    new LootItem(1091, 1, 1, 20800), // Adamant plateskirt
                    new LootItem(1199, 1, 1, 13900), // Adamant kiteshield
                    new LootItem(1123, 1, 1, 20800), // Adamant platebody
                    new LootItem(1113, 1, 1, 13900), // Rune chainbody
                    new LootItem(1127, 1, 1, 13900), // Rune platebody
                    new LootItem(1079, 1, 1, 13900), // Rune platelegs
                    new LootItem(1093, 1, 1, 13900) // Rune plateskirt
            )
            .addTable(4107787,
                    new LootItem(3470, 1, 1, 153000), // Fine cloth
                    new LootItem(12851, 1, 1, 41700), // Amulet of the damned (full)
                    new LootItem(1615, 1, 1, 13900), // Dragonstone
                    new LootItem(1645, 1, 1, 20800), // Dragonstone ring
                    new LootItem(560, 10, 29, 62500), // Death rune
                    new LootItem(566, 10, 29, 41700), // Soul rune
                    new LootItem(3678, 1, 1, 27800), // Flamtaer hammer
                    new LootItem(1516, 5, 14, 20800), // Yew logs (noted)
                    new LootItem(19670, 5, 14, 13900), // Redwood logs (noted)
                    new LootItem(24365, 1, 1, 7189) // Clue scroll (elite)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 98),
                    new LootItem(Items.FLAMTAER_BAG, 1, 11),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1),   // Bloody notes
                    new LootItem(Items.BONECRUSHER, 1, 1)
            )
    ),
    GOLD_CRIMSON(41212, 25428, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 40, 70, 0),
                    new LootItem(Items.ECTOTOKEN, 5, 0)
            )
            .addTable(167052,
                    new LootItem(25454, 1, 1, 16399) // Gold locks
            )
            .addTable(2968198,
                    new LootItem(1245, 1, 1, 48600), // Adamant spear
                    new LootItem(1259, 1, 1, 41600), // Adamant spear(p)
                    new LootItem(1289, 1, 1, 20800), // Rune sword
                    new LootItem(1301, 1, 1, 20800), // Adamant longsword
                    new LootItem(1392, 3, 3, 76300), // Battlestaff (noted)
                    new LootItem(1333, 1, 1, 20800), // Rune scimitar
                    new LootItem(1303, 1, 1, 34700), // Rune longsword
                    new LootItem(1215, 1, 1, 13900), // Dragon dagger
                    new LootItem(1305, 1, 1, 6939), // Dragon longsword
                    new LootItem(1434, 1, 1, 6939) // Dragon mace
            )
            .addTable(2757557,
                    new LootItem(1161, 1, 1, 55500), // Adamant full helm
                    new LootItem(1085, 1, 1, 34700), // Mithril plateskirt
                    new LootItem(1147, 1, 1, 20800), // Rune med helm
                    new LootItem(1111, 1, 1, 27800), // Adamant chainbody
                    new LootItem(1073, 1, 1, 20800), // Adamant platelegs
                    new LootItem(1091, 1, 1, 20800), // Adamant plateskirt
                    new LootItem(1199, 1, 1, 13900), // Adamant kiteshield
                    new LootItem(1123, 1, 1, 20800), // Adamant platebody
                    new LootItem(1113, 1, 1, 13900), // Rune chainbody
                    new LootItem(1127, 1, 1, 13900), // Rune platebody
                    new LootItem(1079, 1, 1, 13900), // Rune platelegs
                    new LootItem(1093, 1, 1, 13900) // Rune plateskirt
            )
            .addTable(4107191,
                    new LootItem(3470, 1, 1, 160000), // Fine cloth
                    new LootItem(12851, 1, 1, 41600), // Amulet of the damned (full)
                    new LootItem(1615, 1, 1, 13900), // Dragonstone
                    new LootItem(1645, 1, 1, 20800), // Dragonstone ring
                    new LootItem(560, 10, 29, 62500), // Death rune
                    new LootItem(566, 10, 29, 41600), // Soul rune
                    new LootItem(3678, 1, 1, 27800), // Flamtaer hammer
                    new LootItem(1516, 5, 14, 16700), // Yew logs (noted)
                    new LootItem(19670, 5, 14, 11100), // Redwood logs (noted)
                    new LootItem(24365, 1, 1, 7189) // Clue scroll (elite)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 98),
                    new LootItem(Items.FLAMTAER_BAG, 1, 11),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1),   // Bloody notes
                    new LootItem(Items.BONECRUSHER, 1, 1)
            )
    ),
    GOLD_BLACK(41215, 25430, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 40, 70, 0),
                    new LootItem(Items.ECTOTOKEN, 5, 0)
            )
            .addTable(170288,
                    new LootItem(25454, 1, 1, 16700) // Gold locks
            )
            .addTable(2971162,
                    new LootItem(1245, 1, 1, 48600), // Adamant spear
                    new LootItem(1259, 1, 1, 41600), // Adamant spear(p)
                    new LootItem(1289, 1, 1, 20800), // Rune sword
                    new LootItem(1301, 1, 1, 20800), // Adamant longsword
                    new LootItem(1392, 3, 3, 76300), // Battlestaff (noted)
                    new LootItem(1333, 1, 1, 20800), // Rune scimitar
                    new LootItem(1303, 1, 1, 34700), // Rune longsword
                    new LootItem(1215, 1, 1, 13900), // Dragon dagger
                    new LootItem(1305, 1, 1, 6939), // Dragon longsword
                    new LootItem(1434, 1, 1, 6939) // Dragon mace
            )
            .addTable(2759290,
                    new LootItem(1161, 1, 1, 55500), // Adamant full helm
                    new LootItem(1085, 1, 1, 34700), // Mithril plateskirt
                    new LootItem(1147, 1, 1, 20800), // Rune med helm
                    new LootItem(1111, 1, 1, 27700), // Adamant chainbody
                    new LootItem(1073, 1, 1, 20800), // Adamant platelegs
                    new LootItem(1091, 1, 1, 20800), // Adamant plateskirt
                    new LootItem(1199, 1, 1, 13900), // Adamant kiteshield
                    new LootItem(1123, 1, 1, 20800), // Adamant platebody
                    new LootItem(1113, 1, 1, 13900), // Rune chainbody
                    new LootItem(1127, 1, 1, 13900), // Rune platebody
                    new LootItem(1079, 1, 1, 13900), // Rune platelegs
                    new LootItem(1093, 1, 1, 13900) // Rune plateskirt
            )
            .addTable(4099258,
                    new LootItem(3470, 1, 1, 166000), // Fine cloth
                    new LootItem(12851, 1, 1, 41600), // Amulet of the damned (full)
                    new LootItem(1615, 1, 1, 13900), // Dragonstone
                    new LootItem(1645, 1, 1, 20800), // Dragonstone ring
                    new LootItem(560, 10, 29, 62400), // Death rune
                    new LootItem(566, 10, 29, 41600), // Soul rune
                    new LootItem(3678, 1, 1, 27700), // Flamtaer hammer
                    new LootItem(1516, 5, 14, 12500), // Yew logs (noted)
                    new LootItem(19670, 5, 14, 8320), // Redwood logs (noted)
                    new LootItem(24365, 1, 1, 7189) // Clue scroll (elite)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 98),
                    new LootItem(Items.FLAMTAER_BAG, 1, 11),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1),   // Bloody notes
                    new LootItem(Items.BONECRUSHER, 1, 1)
            )
    ),
    GOLD_PURPLE(41216, 25432, new LootTable()
            .guaranteedItems(
                    new LootItem(Items.SWAMP_PASTE, 40, 70, 0),
                    new LootItem(Items.ECTOTOKEN, 5, 0)
            )
            .addTable(172342,
                    new LootItem(25454, 1, 1, 16900) // Gold locks
            )
            .addTable(2969153,
                    new LootItem(1245, 1, 1, 48499), // Adamant spear
                    new LootItem(1259, 1, 1, 41600), // Adamant spear(p)
                    new LootItem(1289, 1, 1, 20800), // Rune sword
                    new LootItem(1301, 1, 1, 20800), // Adamant longsword
                    new LootItem(1392, 3, 3, 76200), // Battlestaff (noted)
                    new LootItem(1333, 1, 1, 20800), // Rune scimitar
                    new LootItem(1303, 1, 1, 34700), // Rune longsword
                    new LootItem(1215, 1, 1, 13900), // Dragon dagger
                    new LootItem(1305, 1, 1, 6929), // Dragon longsword
                    new LootItem(1434, 1, 1, 6929) // Dragon mace
            )
            .addTable(2758498,
                    new LootItem(1161, 1, 1, 55400), // Adamant full helm
                    new LootItem(1085, 1, 1, 34700), // Mithril plateskirt
                    new LootItem(1147, 1, 1, 20800), // Rune med helm
                    new LootItem(1111, 1, 1, 27700), // Adamant chainbody
                    new LootItem(1073, 1, 1, 20800), // Adamant platelegs
                    new LootItem(1091, 1, 1, 20800), // Adamant plateskirt
                    new LootItem(1199, 1, 1, 13900), // Adamant kiteshield
                    new LootItem(1123, 1, 1, 20800), // Adamant platebody
                    new LootItem(1113, 1, 1, 13900), // Rune chainbody
                    new LootItem(1127, 1, 1, 13900), // Rune platebody
                    new LootItem(1079, 1, 1, 13900), // Rune platelegs
                    new LootItem(1093, 1, 1, 13900) // Rune plateskirt
            )
            .addTable(4100005,
                    new LootItem(3470, 1, 1, 173000), // Fine cloth
                    new LootItem(12851, 1, 1, 41600), // Amulet of the damned (full)
                    new LootItem(1615, 1, 1, 13900), // Dragonstone
                    new LootItem(1645, 1, 1, 20800), // Dragonstone ring
                    new LootItem(560, 10, 29, 62400), // Death rune
                    new LootItem(566, 10, 29, 41600), // Soul rune
                    new LootItem(3678, 1, 1, 27700), // Flamtaer hammer
                    new LootItem(1516, 5, 14, 8320), // Yew logs (noted)
                    new LootItem(19670, 5, 14, 5540), // Redwood logs (noted)
                    new LootItem(24365, 1, 1, 7189) // Clue scroll (elite)
            )
            .addTable("tertiary", 10000000,
                    new LootItem(-1, 1, 98),
                    new LootItem(Items.FLAMTAER_BAG, 1, 11),
                    new LootItem(25474, 1, 1),  // Tree wizards' journal
                    new LootItem(25476, 1, 1),   // Bloody notes
                    new LootItem(Items.BONECRUSHER, 1, 1)
            )
    );

    private final int chestId, keyId;
    private final LootTable lootTable;

    private void open(Player player, GameObject object) {
        Item key = player.getInventory().findItem(keyId);
        if (key == null) {
            player.sendMessage("You need " + ItemDefinition.get(keyId).descriptiveName + " to open this chest.");
            return;
        }
        player.startEvent(e -> {
            player.lock();
            player.animate(832);
            e.delay(1);
            key.remove();
            List<Item> loot = lootTable.rollItems(true);
            if ((this == GOLD_BROWN || this == GOLD_BLACK || this == GOLD_CRIMSON || this == GOLD_RED || this == GOLD_PURPLE) && Random.rollDie(128)) {
                int pieceId = getNextZealotPiece(player);
                if (pieceId != -1) {
                    loot = lootTable.rollGuaranteed();
                    loot.add(new Item(pieceId, 1));
                }
            }
            for (Item item : loot) {
                addLoot(player, item);
            }
            if (ordinal() <= 4) {
                player.getTaskManager().doLookupByUUID(711);    // Open a Bronze Chest
            } else if (ordinal() >= 10 && ordinal() <= 14) {
                player.getTaskManager().doLookupByUUID(723);    // Open a Black Chest
            } else if (ordinal() >= 20) {
                player.getTaskManager().doLookupByUUID(742);    // Open a Gold Chest
            }
            trySpawnUndeadZealot(player);
            player.unlock();
        });
    }

    private void addLoot(Player player, Item item) {
        if (item.getAmount() == 0) return;
        if (item.getId() == Items.FLAMTAER_BAG && player.hasItem(Items.FLAMTAER_BAG, true)) return;
        // Replace Tree wizards' journal with runescroll
        if (item.getId() == 25474 && player.getCollectionLog().hasCollected(item.getId())) {
            item.setId(25478);
        }
        // Replace Bloody notes with runescroll
        if (item.getId() == 25476 && player.getCollectionLog().hasCollected(item.getId())) {
            item.setId(25481);
        }
        // Double clues with Treasure Hunter
        if (player.getRelicManager().hasRelic(Relic.TREASURE_HUNTER) && Clue.SCROLL_BOXES.contains(item.getId())) {
            item.setAmount(item.getAmount() * 2);
        }
        player.getInventory().addOrDrop(item);
        player.getCollectionLog().collect(item);
    }

    private void trySpawnUndeadZealot(Player player) {
        if (!Random.rollDie(20)) return;
        NPC monster = new NPC(Random.get(10591, 10592));
        Position pos = RouteFinder.findWalkable(player.getPosition());
        monster.spawn(pos);
        monster.putTemporaryAttribute("CAN_PJ", 1);
        monster.removeIfIdle(player);
        monster.removeOnDeath();
        monster.targetPlayer(player, false); // Targets player so no one can steal
        monster.attackTargetPlayer(() -> !player.getPosition().isWithinDistance(player.getPosition()));
    }

    private static final int[] ZEALOT_PIECES = { 25440, 25438, 25436, 25434 };

    private static int getNextZealotPiece(Player player) {
        for (int id : ZEALOT_PIECES) {
            if (player.hasItem(id, true)) continue;
            return id;
        }
        return -1;
    }

    static {
        for (ShadeChest chest : values()) {
            ObjectAction.register(chest.chestId, "open", chest::open);
            ItemObjectAction.register(chest.keyId, chest.chestId, (player, item, obj) -> chest.open(player, obj));
        }
    }
}
