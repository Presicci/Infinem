package io.ruin.model.activities.cluescrolls;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.item.pet.Pet;

import java.util.Collections;
import java.util.List;

public enum ClueType {

    BEGINNER(23182, 23245, 24361, 1, 3, -1, 1, 3, -1, new LootTable()
            .addTable("beginnerclue", 2,    // Uniques
                    new LootItem(23285, 1, 1, 1),   // Mole slippers
                    new LootItem(23288, 1, 1, 1),   // Frog slippers
                    new LootItem(23291, 1, 1, 1),   // Bear feet
                    new LootItem(23294, 1, 1, 1),   // Demon feet
                    new LootItem(23297, 1, 1, 1),   // Jester cape
                    new LootItem(23300, 1, 1, 1),   // Shoulder parrot
                    new LootItem(23303, 1, 1, 1),   // Monk's robe top (t)
                    new LootItem(23306, 1, 1, 1),   // Monk's robe (t)
                    new LootItem(23309, 1, 1, 1),   // Amulet of defence (t)
                    new LootItem(23312, 1, 1, 1),   // Sandwich lady hat
                    new LootItem(23315, 1, 1, 1),   // Sandwich lady top
                    new LootItem(23318, 1, 1, 1),   // Sandwich lady bottom
                    new LootItem(23321, 1, 1, 1),   // Rune scimitar ornament kit(guthix)
                    new LootItem(23324, 1, 1, 1),   // Rune scimitar ornament kit(saradomin)
                    new LootItem(23327, 1, 1, 1)   // Rune scimitar ornament kit(zamorak)
            )

            .addTable(1,    // Black items
                    new LootItem(1313, 1, 1, 1), // Black 2h
                    new LootItem(1361, 1, 1, 1), // Black axe
                    new LootItem(1367, 1, 1, 1), // Black battleaxe
                    new LootItem(1107, 1, 1, 1), // Black chainbody
                    new LootItem(1217, 1, 1, 1), // Black dagger
                    new LootItem(1165, 1, 1, 1), // Black full helm
                    new LootItem(1195, 1, 1, 1), // Black kiteshield
                    new LootItem(1297, 1, 1, 1), // Black longsword
                    new LootItem(1426, 1, 1, 1), // Black mace
                    new LootItem(1151, 1, 1, 1), // Black med helm
                    new LootItem(12297, 1, 1, 1), // Black pickaxe
                    new LootItem(1125, 1, 1, 1), // Black platebody
                    new LootItem(1089, 1, 1, 1), // Black plateskirt
                    new LootItem(1077, 1, 1, 1), // Black platelegs
                    new LootItem(1179, 1, 1, 1), // Black sq shield
                    new LootItem(1327, 1, 1, 1), // Black scimitar
                    new LootItem(1283, 1, 1, 1), // Black sword
                    new LootItem(1341, 1, 1, 1) // Black warhammer
            )

            .addTable(21,    // Weapons and armour
                    new LootItem(841, 1, 1, 1),   //Shortbow
                    new LootItem(839, 1, 1, 1),   //Longbow
                    new LootItem(843, 1, 1, 1),   //Oak shortbow
                    new LootItem(845, 1, 1, 1),   //Oak longbow
                    new LootItem(1267, 1, 1, 1),   //Iron pickaxe
                    new LootItem(1381, 1, 1, 1),   //Staff of air
                    new LootItem(1383, 1, 1, 1),   //Staff of water
                    new LootItem(1385, 1, 1, 1),   //Staff of earth
                    new LootItem(1387, 1, 1, 1),   //Staff of fire
                    new LootItem(1157, 1, 1, 1),   //Steel full helm
                    new LootItem(1119, 1, 1, 1),   //Steel platebody
                    new LootItem(1069, 1, 1, 1),   //Steel platelegs
                    new LootItem(1083, 1, 1, 1),   //Steel plateskirt
                    new LootItem(1295, 1, 1, 1),   //Steel longsword
                    new LootItem(1207, 1, 1, 1),   //Steel dagger
                    new LootItem(1353, 1, 1, 1),   //Steel axe
                    new LootItem(1365, 1, 1, 1),   //Steel battleaxe
                    new LootItem(1167, 1, 1, 1),   //Leather cowl
                    new LootItem(1129, 1, 1, 1),   //Leather body
                    new LootItem(1095, 1, 1, 1),   //Leather chaps
                    new LootItem(1063, 1, 1, 1),   //Leather vambraces
                    new LootItem(1131, 1, 1, 1),   //Hardleather body
                    new LootItem(579, 1, 1, 1),   //Blue wizard hat
                    new LootItem(577, 1, 1, 1),   //Blue wizard robe
                    new LootItem(1017, 1, 1, 1),   //Wizard hat
                    new LootItem(581, 1, 1, 1)   //Black robe
            )

            .addTable(21,    // Runes and ammunition
                    new LootItem(556, 15, 35, 1),   //Air rune
                    new LootItem(558, 15, 35, 1),   //Mind rune
                    new LootItem(555, 15, 35, 1),   //Water rune
                    new LootItem(557, 15, 35, 1),   //Earth rune
                    new LootItem(554, 15, 35, 1),   //Fire rune
                    new LootItem(559, 15, 35, 1),   //Body rune
                    new LootItem(562, 2, 7, 1),   //Chaos rune
                    new LootItem(561, 2, 9, 1),   //Nature rune
                    new LootItem(563, 2, 7, 1),   //Law rune
                    new LootItem(882, 10, 25, 1),   //Bronze arrow
                    new LootItem(884, 7, 25, 1)   //Iron arrow
            )

            .addTable(2,    // Food
                    new LootItem(1966, 5, 9, 2),   //Cabbage (noted)
                    new LootItem(316, 5, 14, 1),   //Shrimps (noted)
                    new LootItem(326, 5, 17, 1),   //Sardine (noted)
                    new LootItem(348, 5, 9, 1)   //Herring (noted)
            )
    ),
    EASY(2677, 20546, 24362, 2, 4, 16728, 2, 4, 50, new LootTable()
                .addTable("easyclue", 8515,    // Uniques
                        new LootItem(20211, 1, 1, 17),    //Team cape zero
                        new LootItem(20217, 1, 1, 17),    //Team cape i
                        new LootItem(20214, 1, 1, 17),    //Team cape x
                        new LootItem(23351, 1, 1, 17),    //Cape of skulls
                        new LootItem(20205, 1, 1, 35),    //Golden chef's hat
                        new LootItem(20208, 1, 1, 35),    //Golden apron
                        new LootItem(20166, 1, 1, 71),    //Wooden shield (g)
                        new LootItem(2587, 1, 1, 71),    //Black full helm (t)
                        new LootItem(2583, 1, 1, 71),    //Black platebody (t)
                        new LootItem(2585, 1, 1, 71),    //Black platelegs (t)
                        new LootItem(3472, 1, 1, 71),    //Black plateskirt (t)
                        new LootItem(2589, 1, 1, 71),    //Black kiteshield (t)
                        new LootItem(2595, 1, 1, 71),    //Black full helm (g)
                        new LootItem(2591, 1, 1, 71),    //Black platebody (g)
                        new LootItem(2593, 1, 1, 71),    //Black platelegs (g)
                        new LootItem(3473, 1, 1, 71),    //Black plateskirt (g)
                        new LootItem(2597, 1, 1, 71),    //Black kiteshield (g)
                        new LootItem(7332, 1, 1, 71),    //Black shield (h1)
                        new LootItem(7338, 1, 1, 71),    //Black shield (h2)
                        new LootItem(7344, 1, 1, 71),    //Black shield (h3)
                        new LootItem(7350, 1, 1, 71),    //Black shield (h4)
                        new LootItem(7356, 1, 1, 71),    //Black shield (h5)
                        new LootItem(10306, 1, 1, 71),    //Black helm (h1)
                        new LootItem(10308, 1, 1, 71),    //Black helm (h2)
                        new LootItem(10310, 1, 1, 71),    //Black helm (h3)
                        new LootItem(10312, 1, 1, 71),    //Black helm (h4)
                        new LootItem(10314, 1, 1, 71),    //Black helm (h5)
                        new LootItem(23366, 1, 1, 71),    //Black platebody (h1)
                        new LootItem(23369, 1, 1, 71),    //Black platebody (h2)
                        new LootItem(23372, 1, 1, 71),    //Black platebody (h3)
                        new LootItem(23375, 1, 1, 71),    //Black platebody (h4)
                        new LootItem(23378, 1, 1, 71),    //Black platebody (h5)
                        new LootItem(20193, 1, 1, 71),    //Steel full helm (t)
                        new LootItem(20184, 1, 1, 71),    //Steel platebody (t)
                        new LootItem(20187, 1, 1, 71),    //Steel platelegs (t)
                        new LootItem(20190, 1, 1, 71),    //Steel plateskirt (t)
                        new LootItem(20196, 1, 1, 71),    //Steel kiteshield (t)
                        new LootItem(20178, 1, 1, 71),    //Steel full helm (g)
                        new LootItem(20169, 1, 1, 71),    //Steel platebody (g)
                        new LootItem(20172, 1, 1, 71),    //Steel platelegs (g)
                        new LootItem(20175, 1, 1, 71),    //Steel plateskirt (g)
                        new LootItem(20181, 1, 1, 71),    //Steel kiteshield (g)
                        new LootItem(12231, 1, 1, 71),    //Iron full helm (t)
                        new LootItem(12225, 1, 1, 71),    //Iron platebody (t)
                        new LootItem(12227, 1, 1, 71),    //Iron platelegs (t)
                        new LootItem(12229, 1, 1, 71),    //Iron plateskirt (t)
                        new LootItem(12233, 1, 1, 71),    //Iron kiteshield (t)
                        new LootItem(12241, 1, 1, 71),    //Iron full helm (g)
                        new LootItem(12235, 1, 1, 71),    //Iron platebody (g)
                        new LootItem(12237, 1, 1, 71),    //Iron platelegs (g)
                        new LootItem(12239, 1, 1, 71),    //Iron plateskirt (g)
                        new LootItem(12243, 1, 1, 71),    //Iron kiteshield (g)
                        new LootItem(12221, 1, 1, 71),    //Bronze full helm (t)
                        new LootItem(12215, 1, 1, 71),    //Bronze platebody (t)
                        new LootItem(12217, 1, 1, 71),    //Bronze platelegs (t)
                        new LootItem(12219, 1, 1, 71),    //Bronze plateskirt (t)
                        new LootItem(12223, 1, 1, 71),    //Bronze kiteshield (t)
                        new LootItem(12211, 1, 1, 71),    //Bronze full helm (g)
                        new LootItem(12205, 1, 1, 71),    //Bronze platebody (g)
                        new LootItem(12207, 1, 1, 71),    //Bronze platelegs (g)
                        new LootItem(12209, 1, 1, 71),    //Bronze plateskirt (g)
                        new LootItem(12213, 1, 1, 71),    //Bronze kiteshield (g)
                        new LootItem(7362, 1, 1, 71),    //Studded body (g)
                        new LootItem(7366, 1, 1, 71),    //Studded chaps (g)
                        new LootItem(7364, 1, 1, 71),    //Studded body (t)
                        new LootItem(7368, 1, 1, 71),    //Studded chaps (t)
                        new LootItem(23381, 1, 1, 71),    //Leather body (g)
                        new LootItem(23384, 1, 1, 71),    //Leather chaps (g)
                        new LootItem(7394, 1, 1, 71),    //Blue wizard hat (g)
                        new LootItem(7390, 1, 1, 71),    //Blue wizard robe (g)
                        new LootItem(7386, 1, 1, 71),    //Blue skirt (g)
                        new LootItem(7396, 1, 1, 71),    //Blue wizard hat (t)
                        new LootItem(7392, 1, 1, 71),    //Blue wizard robe (t)
                        new LootItem(7388, 1, 1, 71),    //Blue skirt (t)
                        new LootItem(12453, 1, 1, 71),    //Black wizard hat (g)
                        new LootItem(12449, 1, 1, 71),    //Black wizard robe (g)
                        new LootItem(12445, 1, 1, 71),    //Black skirt (g)
                        new LootItem(12455, 1, 1, 71),    //Black wizard hat (t)
                        new LootItem(12451, 1, 1, 71),    //Black wizard robe (t)
                        new LootItem(12447, 1, 1, 71),    //Black skirt (t)
                        new LootItem(20199, 1, 1, 7),    //Monk's robe top (g)
                        new LootItem(20202, 1, 1, 7),    //Monk's robe (g)
                        new LootItem(10458, 1, 1, 71),    //Saradomin robe top
                        new LootItem(10464, 1, 1, 71),    //Saradomin robe legs
                        new LootItem(10462, 1, 1, 71),    //Guthix robe top
                        new LootItem(10466, 1, 1, 71),    //Guthix robe legs
                        new LootItem(10460, 1, 1, 71),    //Zamorak robe top
                        new LootItem(10468, 1, 1, 71),    //Zamorak robe legs
                        new LootItem(12193, 1, 1, 71),    //Ancient robe top
                        new LootItem(12195, 1, 1, 71),    //Ancient robe legs
                        new LootItem(12253, 1, 1, 71),    //Armadyl robe top
                        new LootItem(12255, 1, 1, 71),    //Armadyl robe legs
                        new LootItem(12265, 1, 1, 71),    //Bandos robe top
                        new LootItem(12267, 1, 1, 71),    //Bandos robe legs
                        new LootItem(10316, 1, 1, 71),    //Bob's red shirt
                        new LootItem(10320, 1, 1, 71),    //Bob's green shirt
                        new LootItem(10318, 1, 1, 71),    //Bob's blue shirt
                        new LootItem(10322, 1, 1, 71),    //Bob's black shirt
                        new LootItem(10324, 1, 1, 71),    //Bob's purple shirt
                        new LootItem(2631, 1, 1, 71),    //Highwayman mask
                        new LootItem(2633, 1, 1, 71),    //Blue beret
                        new LootItem(2635, 1, 1, 71),    //Black beret
                        new LootItem(12247, 1, 1, 71),    //Red beret
                        new LootItem(2637, 1, 1, 71),    //White beret
                        new LootItem(10392, 1, 1, 71),    //A powdered wig
                        new LootItem(12245, 1, 1, 71),    //Beanie
                        new LootItem(12249, 1, 1, 71),    //Imp mask
                        new LootItem(12251, 1, 1, 71),    //Goblin mask
                        new LootItem(10398, 1, 1, 71),    //Sleeping cap
                        new LootItem(10394, 1, 1, 71),    //Flared trousers
                        new LootItem(10396, 1, 1, 71),    //Pantaloons
                        new LootItem(12375, 1, 1, 71),    //Black cane
                        new LootItem(23363, 1, 1, 71),    //Staff of bob the cat
                        new LootItem(10404, 1, 1, 35),    //Red elegant shirt
                        new LootItem(10424, 1, 1, 35),    //Red elegant blouse
                        new LootItem(10406, 1, 1, 35),    //Red elegant legs
                        new LootItem(10426, 1, 1, 35),    //Red elegant skirt
                        new LootItem(10412, 1, 1, 35),    //Green elegant shirt
                        new LootItem(10432, 1, 1, 35),    //Green elegant blouse
                        new LootItem(10414, 1, 1, 35),    //Green elegant legs
                        new LootItem(10434, 1, 1, 35),    //Green elegant skirt
                        new LootItem(10408, 1, 1, 35),    //Blue elegant shirt
                        new LootItem(10428, 1, 1, 35),    //Blue elegant blouse
                        new LootItem(10410, 1, 1, 35),    //Blue elegant legs
                        new LootItem(10430, 1, 1, 35),    //Blue elegant skirt
                        new LootItem(23354, 1, 1, 70),    //Amulet of power (t)
                        new LootItem(23360, 1, 1, 70),    //Ham joint
                        new LootItem(23357, 1, 1, 71),    //Rain bow
                        new LootItem(10366, 1, 1, 278)   //Amulet of magic (t)
                )
            .addTable(44185,    // Weapons and armour
                    new LootItem(1165, 1, 1, 2780),    //Black full helm
                    new LootItem(1125, 1, 1, 2780),    //Black platebody
                    new LootItem(1077, 1, 1, 2780),    //Black platelegs
                    new LootItem(1297, 1, 1, 2780),    //Black longsword
                    new LootItem(1367, 1, 1, 2780),    //Black battleaxe
                    new LootItem(1361, 1, 1, 2780),    //Black axe
                    new LootItem(1217, 1, 1, 2780),    //Black dagger
                    new LootItem(1269, 1, 1, 2780),    //Steel pickaxe
                    new LootItem(12297, 1, 1, 2780),    //Black pickaxe
                    new LootItem(1169, 1, 1, 2780),    //Coif
                    new LootItem(1133, 1, 1, 2780),    //Studded body
                    new LootItem(1097, 1, 1, 2780),    //Studded chaps
                    new LootItem(849, 1, 1, 2780),    //Willow shortbow
                    new LootItem(847, 1, 1, 2500),    //Willow longbow
                    new LootItem(10280, 1, 1, 278),    //Willow comp bow
                    new LootItem(1381, 1, 1, 2780),    //Staff of air
                    new LootItem(1727, 1, 1, 2500)   //Amulet of magic
            )
            .addTable(25012,    // Runes
                    new LootItem(556, 30, 50, 1),    //Air rune
                    new LootItem(558, 30, 50, 1),    //Mind rune
                    new LootItem(555, 30, 50, 1),    //Water rune
                    new LootItem(557, 30, 50, 1),    //Earth rune
                    new LootItem(554, 30, 50, 1),    //Fire rune
                    new LootItem(559, 30, 50, 1),    //Body rune
                    new LootItem(562, 5, 10, 1),    //Chaos rune
                    new LootItem(561, 5, 10, 1),    //Nature rune
                    new LootItem(563, 5, 10, 1)   //Law rune
            )
            .addTable(5558, // Food
                    new LootItem(334, 6, 10, 1),    //Trout (noted)
                    new LootItem(330, 6, 10, 1)   //Salmon (noted)
            )
    ),

    MEDIUM(
            2801, 20545, 24363, 3, 5, 17607, 3, 5, 30, new LootTable()
            .addTable("mediumclue", 9071, // Uniques
                    new LootItem(2577, 1, 1, 88),  //Ranger boots
                    new LootItem(2579, 1, 1, 88),  //Wizard boots
                    new LootItem(12598, 1, 1, 88),  //Holy sandals
                    new LootItem(23389, 1, 1, 88),  //Spiked manacles
                    new LootItem(23413, 1, 1, 88),  //Climbing boots (g)
                    new LootItem(2605, 1, 1, 88),  //Adamant full helm (t)
                    new LootItem(2599, 1, 1, 88),  //Adamant platebody (t)
                    new LootItem(2601, 1, 1, 88),  //Adamant platelegs (t)
                    new LootItem(3474, 1, 1, 88),  //Adamant plateskirt (t)
                    new LootItem(2603, 1, 1, 88),  //Adamant kiteshield (t)
                    new LootItem(2613, 1, 1, 88),  //Adamant full helm (g)
                    new LootItem(2607, 1, 1, 88),  //Adamant platebody (g)
                    new LootItem(2609, 1, 1, 88),  //Adamant platelegs (g)
                    new LootItem(3475, 1, 1, 88),  //Adamant plateskirt (g)
                    new LootItem(2611, 1, 1, 88),  //Adamant kiteshield (g)
                    new LootItem(7334, 1, 1, 88),  //Adamant shield (h1)
                    new LootItem(7340, 1, 1, 88),  //Adamant shield (h2)
                    new LootItem(7346, 1, 1, 88),  //Adamant shield (h3)
                    new LootItem(7352, 1, 1, 88),  //Adamant shield (h4)
                    new LootItem(7358, 1, 1, 88),  //Adamant shield (h5)
                    new LootItem(10296, 1, 1, 88),  //Adamant helm (h1)
                    new LootItem(10298, 1, 1, 88),  //Adamant helm (h2)
                    new LootItem(10300, 1, 1, 88),  //Adamant helm (h3)
                    new LootItem(10302, 1, 1, 88),  //Adamant helm (h4)
                    new LootItem(10304, 1, 1, 88),  //Adamant helm (h5)
                    new LootItem(23392, 1, 1, 88),  //Adamant platebody (h1)
                    new LootItem(23395, 1, 1, 88),  //Adamant platebody (h2)
                    new LootItem(23398, 1, 1, 88),  //Adamant platebody (h3)
                    new LootItem(23401, 1, 1, 88),  //Adamant platebody (h4)
                    new LootItem(23404, 1, 1, 88),  //Adamant platebody (h5)
                    new LootItem(12283, 1, 1, 88),  //Mithril full helm (g)
                    new LootItem(12277, 1, 1, 88),  //Mithril platebody (g)
                    new LootItem(12279, 1, 1, 88),  //Mithril platelegs (g)
                    new LootItem(12285, 1, 1, 88),  //Mithril plateskirt (g)
                    new LootItem(12281, 1, 1, 88),  //Mithril kiteshield (g)
                    new LootItem(12293, 1, 1, 88),  //Mithril full helm (t)
                    new LootItem(12287, 1, 1, 88),  //Mithril platebody (t)
                    new LootItem(12289, 1, 1, 88),  //Mithril platelegs (t)
                    new LootItem(12295, 1, 1, 88),  //Mithril plateskirt (t)
                    new LootItem(12291, 1, 1, 88),  //Mithril kiteshield (t)
                    new LootItem(7370, 1, 1, 88),  //Green d'hide body (g)
                    new LootItem(7372, 1, 1, 88),  //Green d'hide body (t)
                    new LootItem(7378, 1, 1, 88),  //Green d'hide chaps (g)
                    new LootItem(7380, 1, 1, 88),  //Green d'hide chaps (t)
                    new LootItem(10452, 1, 1, 88),  //Saradomin mitre
                    new LootItem(10446, 1, 1, 88),  //Saradomin cloak
                    new LootItem(10454, 1, 1, 88),  //Guthix mitre
                    new LootItem(10448, 1, 1, 88),  //Guthix cloak
                    new LootItem(10456, 1, 1, 88),  //Zamorak mitre
                    new LootItem(10450, 1, 1, 88),  //Zamorak cloak
                    new LootItem(12203, 1, 1, 88),  //Ancient mitre
                    new LootItem(12197, 1, 1, 88),  //Ancient cloak
                    new LootItem(12201, 1, 1, 88),  //Ancient stole
                    new LootItem(12199, 1, 1, 88),  //Ancient crozier
                    new LootItem(12259, 1, 1, 88),  //Armadyl mitre
                    new LootItem(12261, 1, 1, 88),  //Armadyl cloak
                    new LootItem(12257, 1, 1, 88),  //Armadyl stole
                    new LootItem(12263, 1, 1, 88),  //Armadyl crozier
                    new LootItem(12271, 1, 1, 88),  //Bandos mitre
                    new LootItem(12273, 1, 1, 88),  //Bandos cloak
                    new LootItem(12269, 1, 1, 88),  //Bandos stole
                    new LootItem(12275, 1, 1, 88),  //Bandos crozier
                    new LootItem(7319, 1, 1, 88),  //Red boater
                    new LootItem(7323, 1, 1, 88),  //Green boater
                    new LootItem(7321, 1, 1, 88),  //Orange boater
                    new LootItem(7327, 1, 1, 88),  //Black boater
                    new LootItem(7325, 1, 1, 88),  //Blue boater
                    new LootItem(12309, 1, 1, 88),  //Pink boater
                    new LootItem(12311, 1, 1, 88),  //Purple boater
                    new LootItem(12313, 1, 1, 88),  //White boater
                    new LootItem(2645, 1, 1, 88),  //Red headband
                    new LootItem(2647, 1, 1, 88),  //Black headband
                    new LootItem(2649, 1, 1, 88),  //Brown headband
                    new LootItem(12299, 1, 1, 88),  //White headband
                    new LootItem(12301, 1, 1, 88),  //Blue headband
                    new LootItem(12303, 1, 1, 88),  //Gold headband
                    new LootItem(12305, 1, 1, 88),  //Pink headband
                    new LootItem(12307, 1, 1, 88),  //Green headband
                    new LootItem(12319, 1, 1, 88),  //Crier hat
                    new LootItem(12377, 1, 1, 88),  //Adamant cane
                    new LootItem(12361, 1, 1, 88),  //Cat mask
                    new LootItem(12428, 1, 1, 88),  //Penguin mask
                    new LootItem(12359, 1, 1, 88),  //Leprechaun hat
                    new LootItem(20240, 1, 1, 88),  //Crier coat
                    new LootItem(20243, 1, 1, 88),  //Crier bell
                    new LootItem(20251, 1, 1, 88),  //Arceuus banner
                    new LootItem(20260, 1, 1, 88),  //Piscarilius banner
                    new LootItem(20254, 1, 1, 88),  //Hosidius banner
                    new LootItem(20263, 1, 1, 88),  //Shayzien banner
                    new LootItem(20257, 1, 1, 88),  //Lovakengj banner
                    new LootItem(20272, 1, 1, 88),  //Cabbage round shield
                    new LootItem(23407, 1, 1, 88),  //Wolf mask
                    new LootItem(23410, 1, 1, 88),  //Wolf cloak
                    new LootItem(20246, 1, 1, 88),  //Black leprechaun hat
                    new LootItem(20266, 1, 1, 44),  //Black unicorn mask
                    new LootItem(20269, 1, 1, 44),  //White unicorn mask
                    new LootItem(10416, 1, 1, 44),  //Purple elegant shirt
                    new LootItem(10436, 1, 1, 44),  //Purple elegant blouse
                    new LootItem(10418, 1, 1, 44),  //Purple elegant legs
                    new LootItem(10438, 1, 1, 44),  //Purple elegant skirt
                    new LootItem(10400, 1, 1, 44),  //Black elegant shirt
                    new LootItem(10420, 1, 1, 44),  //White elegant blouse
                    new LootItem(10402, 1, 1, 44),  //Black elegant legs
                    new LootItem(10422, 1, 1, 44),  //White elegant skirt
                    new LootItem(12315, 1, 1, 44),  //Pink elegant shirt
                    new LootItem(12339, 1, 1, 44),  //Pink elegant blouse
                    new LootItem(12317, 1, 1, 44),  //Pink elegant legs
                    new LootItem(12341, 1, 1, 44),  //Pink elegant skirt
                    new LootItem(12347, 1, 1, 44),  //Gold elegant shirt
                    new LootItem(12343, 1, 1, 44),  //Gold elegant blouse
                    new LootItem(12349, 1, 1, 44),  //Gold elegant legs
                    new LootItem(12345, 1, 1, 44)   //Gold elegant skirt
            )
            .addTable(41061,    // Weapons and armour
                    new LootItem(1161, 1, 1, 2930),  //Adamant full helm
                    new LootItem(1123, 1, 1, 2930),  //Adamant platebody
                    new LootItem(1073, 1, 1, 2930),  //Adamant platelegs
                    new LootItem(1301, 1, 1, 2930),  //Adamant longsword
                    new LootItem(1211, 1, 1, 2930),  //Adamant dagger
                    new LootItem(1371, 1, 1, 2930),  //Adamant battleaxe
                    new LootItem(1357, 1, 1, 2930),  //Adamant axe
                    new LootItem(1271, 1, 1, 2930),  //Adamant pickaxe
                    new LootItem(1135, 1, 1, 2930),  //Green d'hide body
                    new LootItem(1099, 1, 1, 2930),  //Green d'hide chaps
                    new LootItem(857, 1, 1, 2930),  //Yew shortbow
                    new LootItem(855, 1, 1, 2640),  //Yew longbow
                    new LootItem(10282, 1, 1, 293),  //Yew comp bow
                    new LootItem(1393, 1, 1, 2930),  //Fire battlestaff
                    new LootItem(1731, 1, 1, 2640),  //Amulet of power
                    new LootItem(10364, 1, 1, 293)   //Strength amulet (t)
            )
            .addTable(26393,    // Runes
                    new LootItem(556, 50, 100, 2930),  //Air rune
                    new LootItem(558, 50, 100, 2930),  //Mind rune
                    new LootItem(555, 50, 100, 2930),  //Water rune
                    new LootItem(557, 50, 100, 2930),  //Earth rune
                    new LootItem(554, 50, 100, 2930),  //Fire rune
                    new LootItem(562, 10, 20, 2930),  //Chaos rune
                    new LootItem(561, 10, 20, 2930),  //Nature rune
                    new LootItem(563, 10, 20, 2930),  //Law rune
                    new LootItem(560, 10, 20, 2930)   //Death rune
            )
            .addTable(5865, // Food
                    new LootItem(380, 8, 12, 2930),  //Lobster (noted)
                    new LootItem(374, 8, 12, 2930)   //Swordfish (noted)
            )
    ),

    HARD(2722, 20544, 24364, 4, 6, 22195, 4, 6, 15, new LootTable()
            .addTable("hardclue", 7575, // Uniques
                    new LootItem(10354, 1, 1, 61),   //Amulet of glory (t4)
                    new LootItem(2581, 1, 1, 61),   //Robin hood hat
                    new LootItem(7400, 1, 1, 61),   //Enchanted hat
                    new LootItem(7399, 1, 1, 61),   //Enchanted top
                    new LootItem(7398, 1, 1, 61),   //Enchanted robe
                    new LootItem(22231, 1, 1, 61),   //Dragon boots ornament kit
                    new LootItem(2627, 1, 1, 61),   //Rune full helm (t)
                    new LootItem(2623, 1, 1, 61),   //Rune platebody (t)
                    new LootItem(2625, 1, 1, 61),   //Rune platelegs (t)
                    new LootItem(3477, 1, 1, 61),   //Rune plateskirt (t)
                    new LootItem(2629, 1, 1, 61),   //Rune kiteshield (t)
                    new LootItem(2619, 1, 1, 61),   //Rune full helm (g)
                    new LootItem(2615, 1, 1, 61),   //Rune platebody (g)
                    new LootItem(2617, 1, 1, 61),   //Rune platelegs (g)
                    new LootItem(3476, 1, 1, 61),   //Rune plateskirt (g)
                    new LootItem(2621, 1, 1, 61),   //Rune kiteshield (g)
                    new LootItem(7336, 1, 1, 61),   //Rune shield (h1)
                    new LootItem(7342, 1, 1, 61),   //Rune shield (h2)
                    new LootItem(7348, 1, 1, 61),   //Rune shield (h3)
                    new LootItem(7354, 1, 1, 61),   //Rune shield (h4)
                    new LootItem(7360, 1, 1, 61),   //Rune shield (h5)
                    new LootItem(10286, 1, 1, 61),   //Rune helm (h1)
                    new LootItem(10288, 1, 1, 61),   //Rune helm (h2)
                    new LootItem(10290, 1, 1, 61),   //Rune helm (h3)
                    new LootItem(10292, 1, 1, 61),   //Rune helm (h4)
                    new LootItem(10294, 1, 1, 61),   //Rune helm (h5)
                    new LootItem(23209, 1, 1, 12),   //Rune platebody (h1)
                    new LootItem(23212, 1, 1, 12),   //Rune platebody (h2)
                    new LootItem(23215, 1, 1, 12),   //Rune platebody (h3)
                    new LootItem(23218, 1, 1, 12),   //Rune platebody (h4)
                    new LootItem(23221, 1, 1, 12),   //Rune platebody (h5)
                    new LootItem(2657, 1, 1, 61),   //Zamorak full helm
                    new LootItem(2653, 1, 1, 61),   //Zamorak platebody
                    new LootItem(2655, 1, 1, 61),   //Zamorak platelegs
                    new LootItem(3478, 1, 1, 61),   //Zamorak plateskirt
                    new LootItem(2659, 1, 1, 61),   //Zamorak kiteshield
                    new LootItem(2673, 1, 1, 61),   //Guthix full helm
                    new LootItem(2669, 1, 1, 61),   //Guthix platebody
                    new LootItem(2671, 1, 1, 61),   //Guthix platelegs
                    new LootItem(3480, 1, 1, 61),   //Guthix plateskirt
                    new LootItem(2675, 1, 1, 61),   //Guthix kiteshield
                    new LootItem(2665, 1, 1, 61),   //Saradomin full helm
                    new LootItem(2661, 1, 1, 61),   //Saradomin platebody
                    new LootItem(2663, 1, 1, 61),   //Saradomin platelegs
                    new LootItem(3479, 1, 1, 61),   //Saradomin plateskirt
                    new LootItem(2667, 1, 1, 61),   //Saradomin kiteshield
                    new LootItem(12466, 1, 1, 61),   //Ancient full helm
                    new LootItem(12460, 1, 1, 61),   //Ancient platebody
                    new LootItem(12462, 1, 1, 61),   //Ancient platelegs
                    new LootItem(12464, 1, 1, 61),   //Ancient plateskirt
                    new LootItem(12468, 1, 1, 61),   //Ancient kiteshield
                    new LootItem(12476, 1, 1, 61),   //Armadyl full helm
                    new LootItem(12470, 1, 1, 61),   //Armadyl platebody
                    new LootItem(12472, 1, 1, 61),   //Armadyl platelegs
                    new LootItem(12474, 1, 1, 61),   //Armadyl plateskirt
                    new LootItem(12478, 1, 1, 61),   //Armadyl kiteshield
                    new LootItem(12486, 1, 1, 61),   //Bandos full helm
                    new LootItem(12480, 1, 1, 61),   //Bandos platebody
                    new LootItem(12482, 1, 1, 61),   //Bandos platelegs
                    new LootItem(12484, 1, 1, 61),   //Bandos plateskirt
                    new LootItem(12488, 1, 1, 61),   //Bandos kiteshield
                    new LootItem(12327, 1, 1, 61),   //Red d'hide body (g)
                    new LootItem(12331, 1, 1, 61),   //Red d'hide body (t)
                    new LootItem(12329, 1, 1, 61),   //Red d'hide chaps (g)
                    new LootItem(12333, 1, 1, 61),   //Red d'hide chaps (t)
                    new LootItem(7374, 1, 1, 61),   //Blue d'hide body (g)
                    new LootItem(7376, 1, 1, 61),   //Blue d'hide body (t)
                    new LootItem(7382, 1, 1, 61),   //Blue d'hide chaps (g)
                    new LootItem(7384, 1, 1, 61),   //Blue d'hide chaps (t)
                    new LootItem(10390, 1, 1, 61),   //Saradomin coif
                    new LootItem(10386, 1, 1, 61),   //Saradomin d'hide body
                    new LootItem(10388, 1, 1, 61),   //Saradomin chaps
                    new LootItem(10384, 1, 1, 61),   //Saradomin bracers
                    new LootItem(19933, 1, 1, 61),   //Saradomin d'hide boots
                    new LootItem(23191, 1, 1, 10),   //Saradomin d'hide shield
                    new LootItem(10382, 1, 1, 61),   //Guthix coif
                    new LootItem(10378, 1, 1, 61),   //Guthix d'hide body
                    new LootItem(10380, 1, 1, 61),   //Guthix chaps
                    new LootItem(10376, 1, 1, 61),   //Guthix bracers
                    new LootItem(19927, 1, 1, 61),   //Guthix d'hide boots
                    new LootItem(23188, 1, 1, 10),   //Guthix d'hide shield
                    new LootItem(10374, 1, 1, 61),   //Zamorak coif
                    new LootItem(10370, 1, 1, 61),   //Zamorak d'hide body
                    new LootItem(10372, 1, 1, 61),   //Zamorak chaps
                    new LootItem(10368, 1, 1, 61),   //Zamorak bracers
                    new LootItem(19936, 1, 1, 61),   //Zamorak d'hide boots
                    new LootItem(23194, 1, 1, 10),   //Zamorak d'hide shield
                    new LootItem(12504, 1, 1, 61),   //Bandos coif
                    new LootItem(12500, 1, 1, 61),   //Bandos d'hide body
                    new LootItem(12502, 1, 1, 61),   //Bandos chaps
                    new LootItem(12498, 1, 1, 61),   //Bandos bracers
                    new LootItem(19924, 1, 1, 61),   //Bandos d'hide boots
                    new LootItem(23203, 1, 1, 10),   //Bandos d'hide shield
                    new LootItem(12512, 1, 1, 61),   //Armadyl coif
                    new LootItem(12508, 1, 1, 61),   //Armadyl d'hide body
                    new LootItem(12510, 1, 1, 61),   //Armadyl chaps
                    new LootItem(12506, 1, 1, 61),   //Armadyl bracers
                    new LootItem(19930, 1, 1, 61),   //Armadyl d'hide boots
                    new LootItem(23200, 1, 1, 10),   //Armadyl d'hide shield
                    new LootItem(12496, 1, 1, 61),   //Ancient coif
                    new LootItem(12492, 1, 1, 61),   //Ancient d'hide body
                    new LootItem(12494, 1, 1, 61),   //Ancient chaps
                    new LootItem(12490, 1, 1, 61),   //Ancient bracers
                    new LootItem(19921, 1, 1, 61),   //Ancient d'hide boots
                    new LootItem(23197, 1, 1, 10),   //Ancient d'hide shield
                    new LootItem(10470, 1, 1, 61),   //Saradomin stole
                    new LootItem(10440, 1, 1, 61),   //Saradomin crozier
                    new LootItem(10472, 1, 1, 61),   //Guthix stole
                    new LootItem(10442, 1, 1, 61),   //Guthix crozier
                    new LootItem(10474, 1, 1, 61),   //Zamorak stole
                    new LootItem(10444, 1, 1, 61),   //Zamorak crozier
                    new LootItem(19912, 1, 1, 61),   //Zombie head (Treasure Trails)
                    new LootItem(19915, 1, 1, 61),   //Cyclops head
                    new LootItem(2651, 1, 1, 61),   //Pirate's hat
                    new LootItem(12323, 1, 1, 61),   //Red cavalier
                    new LootItem(12321, 1, 1, 61),   //White cavalier
                    new LootItem(12325, 1, 1, 61),   //Navy cavalier
                    new LootItem(2639, 1, 1, 61),   //Tan cavalier
                    new LootItem(2641, 1, 1, 61),   //Dark cavalier
                    new LootItem(2643, 1, 1, 61),   //Black cavalier
                    new LootItem(12516, 1, 1, 61),   //Pith helmet
                    new LootItem(12514, 1, 1, 61),   //Explorer backpack
                    new LootItem(12518, 1, 1, 61),   //Green dragon mask
                    new LootItem(12520, 1, 1, 61),   //Blue dragon mask
                    new LootItem(12522, 1, 1, 61),   //Red dragon mask
                    new LootItem(12524, 1, 1, 61),   //Black dragon mask
                    new LootItem(19918, 1, 1, 61),   //Nunchaku
                    new LootItem(12379, 1, 1, 61),   //Rune cane
                    new LootItem(23206, 1, 1, 61),   //Dual sai
                    new LootItem(23224, 1, 1, 61),   //Thieving bag
                    new LootItem(23227, 1, 1, 61),   //Rune defender ornament kit
                    new LootItem(23237, 1, 1, 61),   //Berserker necklace ornament kit
                    new LootItem(23232, 1, 1, 61)   //Tzhaar-ket-om ornament kit
            )
            .addTable("hardclue", 72,    // Mega-rare
                    new LootItem(Items.SUPER_ENERGY_4_NOTE, 15, 15, 12),
                    new LootItem(Items.SUPER_RESTORE_4_NOTE, 15, 15, 12),
                    new LootItem(Items.ANTIFIRE_POTION_4_NOTE, 15, 15, 12),
                    new LootItem(Items.SUPER_ATTACK_4_NOTE, 5, 5, 12),
                    new LootItem(Items.SUPER_STRENGTH_4_NOTE, 5, 5, 12),
                    new LootItem(Items.SUPER_DEFENCE_4_NOTE, 5, 5, 12),
                    new LootItem(Items.GILDED_FULL_HELM, 1, 1, 6),
                    new LootItem(Items.GILDED_PLATEBODY, 1, 1, 6),
                    new LootItem(Items.GILDED_PLATELEGS, 1, 1, 6),
                    new LootItem(Items.GILDED_PLATESKIRT, 1, 1, 6),
                    new LootItem(Items.GILDED_KITESHIELD, 1, 1, 6),
                    new LootItem(Items.GILDED_MED_HELM, 1, 1, 6),
                    new LootItem(Items.GILDED_CHAINBODY, 1, 1, 6),
                    new LootItem(Items.GILDED_SQ_SHIELD, 1, 1, 6),
                    new LootItem(Items.GILDED_2H_SWORD, 1, 1, 6),
                    new LootItem(Items.GILDED_SPEAR, 1, 1, 6),
                    new LootItem(Items.GILDED_HASTA, 1, 1, 6),
                    new LootItem(Items.THIRD_AGE_FULL_HELMET, 1, 1, 1),
                    new LootItem(Items.THIRD_AGE_PLATEBODY, 1, 1, 1),
                    new LootItem(Items.THIRD_AGE_PLATELEGS, 1, 1, 1),
                    new LootItem(23242, 1, 1, 1),   // Third age plateskirt
                    new LootItem(Items.THIRD_AGE_KITESHIELD, 1, 1, 1),
                    new LootItem(Items.THIRD_AGE_RANGE_COIF, 1, 1, 1),
                    new LootItem(Items.THIRD_AGE_RANGE_TOP, 1, 1, 1),
                    new LootItem(Items.THIRD_AGE_RANGE_LEGS, 1, 1, 1),
                    new LootItem(Items.THIRD_AGE_VAMBRACES, 1, 1, 1),
                    new LootItem(Items.THIRD_AGE_MAGE_HAT, 1, 1, 1),
                    new LootItem(Items.THIRD_AGE_ROBE_TOP, 1, 1, 1),
                    new LootItem(Items.THIRD_AGE_ROBE, 1, 1, 1),
                    new LootItem(Items.THIRD_AGE_AMULET, 1, 1, 1)
            )
            .addTable(51748,    // Weapons and armour
                    new LootItem(1163, 1, 1, 3690),   //Rune full helm
                    new LootItem(1127, 1, 1, 3690),   //Rune platebody
                    new LootItem(1079, 1, 1, 3690),   //Rune platelegs
                    new LootItem(1093, 1, 1, 3690),   //Rune plateskirt
                    new LootItem(1201, 1, 1, 3690),   //Rune kiteshield
                    new LootItem(1303, 1, 1, 3690),   //Rune longsword
                    new LootItem(1213, 1, 1, 3690),   //Rune dagger
                    new LootItem(1373, 1, 1, 3690),   //Rune battleaxe
                    new LootItem(1359, 1, 1, 3690),   //Rune axe
                    new LootItem(1275, 1, 1, 3690),   //Rune pickaxe
                    new LootItem(2503, 1, 1, 3690),   //Black d'hide body
                    new LootItem(2497, 1, 1, 3690),   //Black d'hide chaps
                    new LootItem(861, 1, 1, 3690),   //Magic shortbow
                    new LootItem(859, 1, 1, 3320),   //Magic longbow
                    new LootItem(10284, 1, 1, 369)   //Magic comp bow
            )
            .addTable(11089,    // Runes
                    new LootItem(561, 30, 50, 3690),   //Nature rune
                    new LootItem(563, 30, 50, 3690),   //Law rune
                    new LootItem(565, 20, 30, 3690)   //Blood rune
            )
            .addTable(7392, // Food
                    new LootItem(380, 12, 15, 3690),   //Lobster (noted)
                    new LootItem(386, 12, 15, 3690)   //Shark (noted)
            )
    ),

    ELITE(12073, 20543, 24365, 10, 12, 21680, 4, 6, 5, new LootTable()
            .addTable("eliteclue", 3920, // Uniques
                    new LootItem(12538, 1, 1, 80),   //Dragon full helm ornament kit
                    new LootItem(12534, 1, 1, 80),   //Dragon chainbody ornament kit
                    new LootItem(12536, 1, 1, 80),   //Dragon legs/skirt ornament kit
                    new LootItem(12532, 1, 1, 80),   //Dragon sq shield ornament kit
                    new LootItem(20002, 1, 1, 80),   //Dragon scimitar ornament kit
                    new LootItem(12526, 1, 1, 80),   //Fury ornament kit
                    new LootItem(12530, 1, 1, 80),   //Light infinity colour kit
                    new LootItem(12528, 1, 1, 80),   //Dark infinity colour kit
                    new LootItem(12397, 1, 1, 80),   //Royal crown
                    new LootItem(12393, 1, 1, 80),   //Royal gown top
                    new LootItem(12395, 1, 1, 80),   //Royal gown bottom
                    new LootItem(12439, 1, 1, 80),   //Royal sceptre
                    new LootItem(12351, 1, 1, 80),   //Musketeer hat
                    new LootItem(12441, 1, 1, 80),   //Musketeer tabard
                    new LootItem(12443, 1, 1, 80),   //Musketeer pants
                    new LootItem(12381, 1, 1, 80),   //Black d'hide body (g)
                    new LootItem(12385, 1, 1, 80),   //Black d'hide body (t)
                    new LootItem(12383, 1, 1, 80),   //Black d'hide chaps (g)
                    new LootItem(12387, 1, 1, 80),   //Black d'hide chaps (t)
                    new LootItem(12596, 1, 1, 80),   //Rangers' tunic
                    new LootItem(19994, 1, 1, 80),   //Ranger gloves
                    new LootItem(19997, 1, 1, 80),   //Holy wraps
                    new LootItem(12363, 1, 1, 80),   //Bronze dragon mask
                    new LootItem(12365, 1, 1, 80),   //Iron dragon mask
                    new LootItem(12367, 1, 1, 80),   //Steel dragon mask
                    new LootItem(12369, 1, 1, 80),   //Mithril dragon mask
                    new LootItem(23270, 1, 1, 80),   //Adamant dragon mask
                    new LootItem(23273, 1, 1, 80),   //Rune dragon mask
                    new LootItem(19943, 1, 1, 80),   //Arceuus scarf
                    new LootItem(19946, 1, 1, 80),   //Hosidius scarf
                    new LootItem(19949, 1, 1, 80),   //Lovakengj scarf
                    new LootItem(19952, 1, 1, 80),   //Piscarilius scarf
                    new LootItem(19955, 1, 1, 80),   //Shayzien scarf
                    new LootItem(12357, 1, 1, 80),   //Katana
                    new LootItem(12373, 1, 1, 80),   //Dragon cane
                    new LootItem(19991, 1, 1, 80),   //Bucket helm
                    new LootItem(19988, 1, 1, 80),   //Blacksmith's helm
                    new LootItem(12540, 1, 1, 80),   //Deerstalker
                    new LootItem(12430, 1, 1, 80),   //Afro
                    new LootItem(12355, 1, 1, 80),   //Big pirate hat
                    new LootItem(12432, 1, 1, 80),   //Top hat
                    new LootItem(12353, 1, 1, 80),   //Monocle
                    new LootItem(12335, 1, 1, 80),   //Briefcase
                    new LootItem(12337, 1, 1, 80),   //Sagacious spectacles
                    new LootItem(19970, 1, 1, 8),   //Dark bow tie
                    new LootItem(19958, 1, 1, 8),   //Dark tuxedo jacket
                    new LootItem(19961, 1, 1, 8),   //Dark tuxedo cuffs
                    new LootItem(19964, 1, 1, 8),   //Dark trousers
                    new LootItem(19967, 1, 1, 8),   //Dark tuxedo shoes
                    new LootItem(19985, 1, 1, 8),   //Light bow tie
                    new LootItem(19973, 1, 1, 8),   //Light tuxedo jacket
                    new LootItem(19976, 1, 1, 8),   //Light tuxedo cuffs
                    new LootItem(19979, 1, 1, 8),   //Light trousers
                    new LootItem(19982, 1, 1, 8),   //Light tuxedo shoes
                    new LootItem(23249, 1, 1, 80),   //Rangers' tights
                    new LootItem(23255, 1, 1, 80),   //Uri's hat
                    new LootItem(23252, 1, 1, 80),   //Giant boot
                    new LootItem(23246, 1, 1, 80)   //Fremennik kilt
            )
            .addTable("eliteclue", 80,    // Mega-rare
                    new LootItem(Items.RING_OF_NATURE, 1, 1, 330),
                    new LootItem(Items.CRYSTAL_KEY, 1, 1, 330),
                    new LootItem(Items.LAVA_DRAGON_MASK, 1, 1, 330),
                    new LootItem(Items.BATTLESTAFF_NOTE, 100, 100, 330),
                    new LootItem(Items.EXTENDED_ANTIFIRE_4_NOTE, 30, 30, 330),
                    new LootItem(Items.SUPER_RESTORE_4_NOTE, 30, 30, 330),
                    new LootItem(Items.SARADOMIN_BREW_4_NOTE, 30, 30, 330),
                    new LootItem(Items.RANGING_POTION_4_NOTE, 30, 30, 330),
                    new LootItem(Items.GILDED_SCIMITAR, 1, 1, 330),
                    new LootItem(Items.GILDED_BOOTS, 1, 1, 330),
                    new LootItem(23258, 1, 1, 330),   // Gilded coif
                    new LootItem(23261, 1, 1, 330),   // Gilded d'hide vambs
                    new LootItem(23264, 1, 1, 330),   // Gilded d'hide body
                    new LootItem(23267, 1, 1, 330),   // Gilded d'hide chaps
                    new LootItem(23276, 1, 1, 330),   // Gilded pickaxe
                    new LootItem(23279, 1, 1, 330),   // Gilded axe
                    new LootItem(23282, 1, 1, 330),   // Gilded spade
                    new LootItem(Items.GILDED_FULL_HELM, 1, 1, 150),
                    new LootItem(Items.GILDED_PLATEBODY, 1, 1, 150),
                    new LootItem(Items.GILDED_PLATELEGS, 1, 1, 150),
                    new LootItem(Items.GILDED_PLATESKIRT, 1, 1, 150),
                    new LootItem(Items.GILDED_KITESHIELD, 1, 1, 150),
                    new LootItem(Items.GILDED_MED_HELM, 1, 1, 150),
                    new LootItem(Items.GILDED_CHAINBODY, 1, 1, 150),
                    new LootItem(Items.GILDED_SQ_SHIELD, 1, 1, 150),
                    new LootItem(Items.GILDED_2H_SWORD, 1, 1, 150),
                    new LootItem(Items.GILDED_SPEAR, 1, 1, 150),
                    new LootItem(Items.GILDED_HASTA, 1, 1, 150),
                    new LootItem(Items.THIRD_AGE_FULL_HELMET, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_PLATEBODY, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_PLATELEGS, 1, 1, 20),
                    new LootItem(23242, 1, 1, 20),   // Third age plateskirt
                    new LootItem(Items.THIRD_AGE_KITESHIELD, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_RANGE_COIF, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_RANGE_TOP, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_RANGE_LEGS, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_VAMBRACES, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_MAGE_HAT, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_ROBE_TOP, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_ROBE, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_AMULET, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_LONGSWORD, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_WAND, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_CLOAK, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_BOW, 1, 1, 20)
            )
            .addTable(27903,    // Weapons and armour
                    new LootItem(1127, 1, 1, 3100),   //Rune platebody
                    new LootItem(1079, 1, 1, 3100),   //Rune platelegs
                    new LootItem(1093, 1, 1, 3100),   //Rune plateskirt
                    new LootItem(1201, 1, 1, 3100),   //Rune kiteshield
                    new LootItem(9185, 1, 1, 3100),   //Rune crossbow
                    new LootItem(1215, 1, 1, 3100),   //Dragon dagger
                    new LootItem(1434, 1, 1, 3100),   //Dragon mace
                    new LootItem(1305, 1, 1, 3100),   //Dragon longsword
                    new LootItem(9194, 8, 12, 3100)   //Onyx bolt tips
            )
            .addTable(12401,    // Runes
                    new LootItem(563, 50, 75, 3100),   //Law rune
                    new LootItem(560, 50, 75, 3100),   //Death rune
                    new LootItem(565, 50, 75, 3100),   //Blood rune
                    new LootItem(566, 50, 75, 3100)   //Soul rune
            )
            .addTable(9301, // Jewellery
                    new LootItem(11115, 1, 1, 3100),   //Dragonstone bracelet
                    new LootItem(1664, 1, 1, 3100),   //Dragon necklace
                    new LootItem(1645, 1, 1, 3100)   //Dragonstone ring
            )
            .addTable(6200, // Food
                    new LootItem(7061, 15, 20, 3100),   //Tuna potato (noted)
                    new LootItem(7219, 15, 20, 3100)   //Summer pie (noted)
            )
            .addTable(18592,    // Resources
                    new LootItem(8779, 60, 80, 3100),   //Oak plank (noted)
                    new LootItem(8781, 40, 50, 3100),   //Teak plank (noted)
                    new LootItem(8783, 20, 30, 3100),   //Mahogany plank (noted)
                    new LootItem(2364, 1, 3, 3100),   //Runite bar (noted)
                    new LootItem(985, 1, 1, 1550),   //Tooth half of key
                    new LootItem(987, 1, 1, 1550),   //Loop half of key
                    new LootItem(5289, 1, 1, 1030),   //Palm tree seed
                    new LootItem(5315, 1, 1, 1030),   //Yew seed
                    new LootItem(5316, 1, 1, 1030)   //Magic seed
            )
    ),

    MASTER(19835, 19836, 24366, 6, 8, 23469, 5, 7, -1, new LootTable()
            .addTable("masterclue", 3828, // Uniques
                    new LootItem(20065, 1, 1, 117),   //Occult ornament kit
                    new LootItem(20062, 1, 1, 117),   //Torture ornament kit
                    new LootItem(22246, 1, 1, 117),   //Anguish ornament kit
                    new LootItem(23348, 1, 1, 117),   //Tormented ornament kit
                    new LootItem(20143, 1, 1, 117),   //Dragon defender ornament kit
                    new LootItem(22239, 1, 1, 3),   //Dragon kiteshield ornament kit
                    new LootItem(22236, 1, 1, 7),   //Dragon platebody ornament kit
                    new LootItem(20128, 1, 1, 117),   //Hood of darkness
                    new LootItem(20131, 1, 1, 117),   //Robe top of darkness
                    new LootItem(20134, 1, 1, 117),   //Gloves of darkness
                    new LootItem(20137, 1, 1, 117),   //Robe bottom of darkness
                    new LootItem(20140, 1, 1, 117),   //Boots of darkness
                    new LootItem(20035, 1, 1, 117),   //Samurai kasa
                    new LootItem(20038, 1, 1, 117),   //Samurai shirt
                    new LootItem(20041, 1, 1, 117),   //Samurai gloves
                    new LootItem(20044, 1, 1, 117),   //Samurai greaves
                    new LootItem(20047, 1, 1, 117),   //Samurai boots
                    new LootItem(20113, 1, 1, 117),   //Arceuus hood
                    new LootItem(20116, 1, 1, 117),   //Hosidius hood
                    new LootItem(20119, 1, 1, 117),   //Lovakengj hood
                    new LootItem(20122, 1, 1, 117),   //Piscarilius hood
                    new LootItem(20125, 1, 1, 117),   //Shayzien hood
                    new LootItem(20029, 1, 1, 117),   //Old demon mask
                    new LootItem(20020, 1, 1, 117),   //Lesser demon mask
                    new LootItem(20023, 1, 1, 117),   //Greater demon mask
                    new LootItem(20026, 1, 1, 117),   //Black demon mask
                    new LootItem(20032, 1, 1, 117),   //Jungle demon mask
                    new LootItem(19724, 1, 1, 117),   //Left eye patch
                    new LootItem(20110, 1, 1, 117),   //Bowl wig
                    new LootItem(20056, 1, 1, 117),   //Ale of the gods
                    new LootItem(20050, 1, 1, 117),   //Obsidian cape (r)
                    new LootItem(20008, 1, 1, 117),   //Fancy tiara
                    new LootItem(20053, 1, 1, 117),   //Half moon spectacles
                    new LootItem(20068, 1, 1, 29),   //Armadyl godsword ornament kit
                    new LootItem(20071, 1, 1, 29),   //Bandos godsword ornament kit
                    new LootItem(20074, 1, 1, 29),   //Saradomin godsword ornament kit
                    new LootItem(20077, 1, 1, 29),   //Zamorak godsword ornament kit
                    new LootItem(20095, 1, 1, 7),   //Ankou mask
                    new LootItem(20098, 1, 1, 7),   //Ankou top
                    new LootItem(20101, 1, 1, 7),   //Ankou gloves
                    new LootItem(20104, 1, 1, 7),   //Ankou's leggings
                    new LootItem(20107, 1, 1, 7),   //Ankou socks
                    new LootItem(20080, 1, 1, 7),   //Mummy's head
                    new LootItem(20083, 1, 1, 7),   //Mummy's body
                    new LootItem(20086, 1, 1, 7),   //Mummy's hands
                    new LootItem(20089, 1, 1, 7),   //Mummy's legs
                    new LootItem(20092, 1, 1, 7)   //Mummy's feet
            )
            .addTable("masterclue", 97,    // Mega-rare
                    new LootItem(Items.BUCKET_HELM_G, 1, 1, 350),
                    new LootItem(Items.RING_OF_COINS, 1, 1, 350),
                    new LootItem(Items.CABBAGE, 3, 3, 350),
                    new LootItem(12906, 15, 15, 350), // Anti-venom (4)
                    new LootItem(Items.TORSTOL_NOTE, 50, 50, 350),
                    new LootItem(Items.GILDED_SCIMITAR, 1, 1, 350),
                    new LootItem(Items.GILDED_BOOTS, 1, 1, 350),
                    new LootItem(23258, 1, 1, 350),   // Gilded coif
                    new LootItem(23261, 1, 1, 350),   // Gilded d'hide vambs
                    new LootItem(23264, 1, 1, 350),   // Gilded d'hide body
                    new LootItem(23267, 1, 1, 350),   // Gilded d'hide chaps
                    new LootItem(23276, 1, 1, 350),   // Gilded pickaxe
                    new LootItem(23279, 1, 1, 350),   // Gilded axe
                    new LootItem(23282, 1, 1, 350),   // Gilded spade
                    new LootItem(Items.GILDED_FULL_HELM, 1, 1, 40),
                    new LootItem(Items.GILDED_PLATEBODY, 1, 1, 40),
                    new LootItem(Items.GILDED_PLATELEGS, 1, 1, 40),
                    new LootItem(Items.GILDED_PLATESKIRT, 1, 1, 40),
                    new LootItem(Items.GILDED_KITESHIELD, 1, 1, 40),
                    new LootItem(Items.GILDED_MED_HELM, 1, 1, 40),
                    new LootItem(Items.GILDED_CHAINBODY, 1, 1, 40),
                    new LootItem(Items.GILDED_SQ_SHIELD, 1, 1, 40),
                    new LootItem(Items.GILDED_2H_SWORD, 1, 1, 40),
                    new LootItem(Items.GILDED_SPEAR, 1, 1, 40),
                    new LootItem(Items.GILDED_HASTA, 1, 1, 40),
                    new LootItem(Items.THIRD_AGE_FULL_HELMET, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_PLATEBODY, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_PLATELEGS, 1, 1, 20),
                    new LootItem(23242, 1, 1, 20),   // Third age plateskirt
                    new LootItem(Items.THIRD_AGE_KITESHIELD, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_RANGE_COIF, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_RANGE_TOP, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_RANGE_LEGS, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_VAMBRACES, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_MAGE_HAT, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_ROBE_TOP, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_ROBE, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_AMULET, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_LONGSWORD, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_WAND, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_CLOAK, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_BOW, 1, 1, 20),
                    new LootItem(23336, 1, 1, 20),   // Third age druidic robe top
                    new LootItem(23339, 1, 1, 20),   // Third age druidic robe bottoms
                    new LootItem(23345, 1, 1, 20),   // Third age druidic cloak
                    new LootItem(23342, 1, 1, 20),   // Third age druidic staff
                    new LootItem(Items.THIRD_AGE_PICKAXE, 1, 1, 20),
                    new LootItem(Items.THIRD_AGE_AXE, 1, 1, 20)
            )
            .addTable(19827,    // Weapons
                    new LootItem(1215, 1, 1, 3300),   //Dragon dagger
                    new LootItem(1434, 1, 1, 3300),   //Dragon mace
                    new LootItem(1305, 1, 1, 3300),   //Dragon longsword
                    new LootItem(4587, 1, 1, 3300),   //Dragon scimitar
                    new LootItem(1377, 1, 1, 3300),   //Dragon battleaxe
                    new LootItem(3204, 1, 1, 3300)   //Dragon halberd
            )
            .addTable(16523,    // Runes and ammo
                    new LootItem(561, 100, 200, 3300),   //Nature rune
                    new LootItem(560, 100, 200, 3300),   //Death rune
                    new LootItem(565, 100, 200, 3300),   //Blood rune
                    new LootItem(566, 100, 200, 3300),   //Soul rune
                    new LootItem(9245, 15, 25, 3300)   //Onyx bolts (e)
            )
            .addTable(3304, // Food
                    new LootItem(392, 15, 25, 1)   //Manta ray (noted)
            )
            .addTable(33046,    // Resources
                    new LootItem(246, 35, 50, 3300),   //Wine of zamorak (noted)
                    new LootItem(226, 40, 60, 3300),   //Limpwurt root (noted)
                    new LootItem(208, 5, 10, 3300),   //Grimy ranarr weed (noted)
                    new LootItem(3050, 25, 35, 3300),   //Grimy toadflax (noted)
                    new LootItem(3052, 5, 10, 3300),   //Grimy snapdragon (noted)
                    new LootItem(452, 5, 8, 3300),   //Runite ore (noted)
                    new LootItem(2364, 5, 7, 3300),   //Runite bar (noted)
                    new LootItem(1748, 5, 25, 3300),   //Black dragonhide (noted)
                    new LootItem(985, 1, 1, 1650),   //Tooth half of key
                    new LootItem(987, 1, 1, 1650),   //Loop half of key
                    new LootItem(5289, 1, 2, 1100),   //Palm tree seed
                    new LootItem(5315, 1, 2, 1100),   //Yew seed
                    new LootItem(5316, 1, 2, 1100)   //Magic seed
            )
    );

    public final int clueId, casketId, boxId;

    private final int minStages, maxStages, minRolls, maxRolls, masterChance;

    private final String descriptiveName;

    public final LootTable lootTable;

    ClueType(int clueId, int casketId, int boxId, int minStages, int maxStages, int sharedWeight, int minRolls, int maxRolls, int masterChance, LootTable lootTable) {
        this.clueId = clueId;
        this.casketId = casketId;
        this.boxId = boxId;
        this.minStages = minStages;
        this.maxStages = maxStages;
        this.minRolls = minRolls;
        this.maxRolls = maxRolls;
        this.masterChance = masterChance;
        this.descriptiveName = StringUtils.vowelStart(name()) ? ("an " + name().toLowerCase()) : ("a " + name().toLowerCase());
        this.lootTable = lootTable;

        // Add the shared table to each qualifying clue reward
        if (sharedWeight != -1) {
            lootTable.addTable(sharedWeight,
                    new LootItem(995, 20000, 35000, 3300),   //Coins
                    new LootItem(995, 10000, 15000, 1150),   //Coins
                    new LootItem(995, 15000, 30000, 262),   //Coins
                    new LootItem(4561, 14, 33, 3300),   //Purple sweets
                    new LootItem(4561, 8, 12, 1150),   //Purple sweets
                    new LootItem(7329, 20, 38, 660),   //Red firelighter
                    new LootItem(7330, 20, 38, 660),   //Green firelighter
                    new LootItem(7331, 20, 38, 660),   //Blue firelighter
                    new LootItem(10326, 20, 38, 660),   //Purple firelighter
                    new LootItem(10327, 20, 38, 660),   //White firelighter
                    new LootItem(20220, 1, 1, 165),   //Holy blessing
                    new LootItem(20223, 1, 1, 165),   //Unholy blessing
                    new LootItem(20226, 1, 1, 165),   //Peaceful blessing
                    new LootItem(20232, 1, 1, 165),   //War blessing
                    new LootItem(20229, 1, 1, 165),   //Honourable blessing
                    new LootItem(20235, 1, 1, 165),   //Ancient blessing
                    new LootItem(20238, 5, 15, 525),   //Charge dragonstone jewellery scroll
                    new LootItem(12402, 5, 15, 525),   //Nardah teleport
                    new LootItem(12411, 5, 15, 525),   //Mos le'harmless teleport
                    new LootItem(12406, 5, 15, 525),   //Mort'ton teleport
                    new LootItem(12404, 5, 15, 525),   //Feldip hills teleport
                    new LootItem(12405, 5, 15, 525),   //Lunar isle teleport
                    new LootItem(12403, 5, 15, 525),   //Digsite teleport
                    new LootItem(12408, 5, 15, 525),   //Piscatoris teleport
                    new LootItem(12407, 5, 15, 525),   //Pest control teleport
                    new LootItem(12409, 5, 15, 525),   //Tai bwo wannai teleport
                    new LootItem(12642, 5, 15, 525),   //Lumberyard teleport
                    new LootItem(12410, 5, 15, 525),   //Iorwerth camp teleport
                    new LootItem(21387, 1, 1, 300),   //Master scroll book (empty)
                    new LootItem(3827, 1, 1, 141),   //Saradomin page 1
                    new LootItem(3828, 1, 1, 141),   //Saradomin page 2
                    new LootItem(3829, 1, 1, 141),   //Saradomin page 3
                    new LootItem(3830, 1, 1, 141),   //Saradomin page 4
                    new LootItem(3831, 1, 1, 141),   //Zamorak page 1
                    new LootItem(3832, 1, 1, 141),   //Zamorak page 2
                    new LootItem(3833, 1, 1, 141),   //Zamorak page 3
                    new LootItem(3834, 1, 1, 141),   //Zamorak page 4
                    new LootItem(3835, 1, 1, 141),   //Guthix page 1
                    new LootItem(3836, 1, 1, 141),   //Guthix page 2
                    new LootItem(3837, 1, 1, 141),   //Guthix page 3
                    new LootItem(3838, 1, 1, 141),   //Guthix page 4
                    new LootItem(12613, 1, 1, 141),   //Bandos page 1
                    new LootItem(12614, 1, 1, 141),   //Bandos page 2
                    new LootItem(12615, 1, 1, 141),   //Bandos page 3
                    new LootItem(12616, 1, 1, 141),   //Bandos page 4
                    new LootItem(12617, 1, 1, 141),   //Armadyl page 1
                    new LootItem(12618, 1, 1, 141),   //Armadyl page 2
                    new LootItem(12619, 1, 1, 141),   //Armadyl page 3
                    new LootItem(12620, 1, 1, 141),   //Armadyl page 4
                    new LootItem(12621, 1, 1, 141),   //Ancient page 1
                    new LootItem(12622, 1, 1, 141),   //Ancient page 2
                    new LootItem(12623, 1, 1, 141),   //Ancient page 3
                    new LootItem(12624, 1, 1, 141)   //Ancient page 4
            );
        }
    }

    private static final LootTable shared = new LootTable().addTable(1,
            new LootItem(995, 20000, 35000, 3300),   //Coins
                    new LootItem(995, 10000, 15000, 1150),   //Coins
                    new LootItem(995, 15000, 30000, 262),   //Coins
                    new LootItem(4561, 14, 33, 3300),   //Purple sweets
                    new LootItem(4561, 8, 12, 1150),   //Purple sweets
                    new LootItem(7329, 20, 38, 660),   //Red firelighter
                    new LootItem(7330, 20, 38, 660),   //Green firelighter
                    new LootItem(7331, 20, 38, 660),   //Blue firelighter
                    new LootItem(10326, 20, 38, 660),   //Purple firelighter
                    new LootItem(10327, 20, 38, 660),   //White firelighter
                    new LootItem(20220, 1, 1, 165),   //Holy blessing
                    new LootItem(20223, 1, 1, 165),   //Unholy blessing
                    new LootItem(20226, 1, 1, 165),   //Peaceful blessing
                    new LootItem(20232, 1, 1, 165),   //War blessing
                    new LootItem(20229, 1, 1, 165),   //Honourable blessing
                    new LootItem(20235, 1, 1, 165),   //Ancient blessing
                    new LootItem(20238, 5, 15, 525),   //Charge dragonstone jewellery scroll
                    new LootItem(12402, 5, 15, 525),   //Nardah teleport
                    new LootItem(12411, 5, 15, 525),   //Mos le'harmless teleport
                    new LootItem(12406, 5, 15, 525),   //Mort'ton teleport
                    new LootItem(12404, 5, 15, 525),   //Feldip hills teleport
                    new LootItem(12405, 5, 15, 525),   //Lunar isle teleport
                    new LootItem(12403, 5, 15, 525),   //Digsite teleport
                    new LootItem(12408, 5, 15, 525),   //Piscatoris teleport
                    new LootItem(12407, 5, 15, 525),   //Pest control teleport
                    new LootItem(12409, 5, 15, 525),   //Tai bwo wannai teleport
                    new LootItem(12642, 5, 15, 525),   //Lumberyard teleport
                    new LootItem(12410, 5, 15, 525),   //Iorwerth camp teleport
                    new LootItem(21387, 1, 1, 300),   //Master scroll book (empty)
                    new LootItem(3827, 1, 1, 141),   //Saradomin page 1
                    new LootItem(3828, 1, 1, 141),   //Saradomin page 2
                    new LootItem(3829, 1, 1, 141),   //Saradomin page 3
                    new LootItem(3830, 1, 1, 141),   //Saradomin page 4
                    new LootItem(3831, 1, 1, 141),   //Zamorak page 1
                    new LootItem(3832, 1, 1, 141),   //Zamorak page 2
                    new LootItem(3833, 1, 1, 141),   //Zamorak page 3
                    new LootItem(3834, 1, 1, 141),   //Zamorak page 4
                    new LootItem(3835, 1, 1, 141),   //Guthix page 1
                    new LootItem(3836, 1, 1, 141),   //Guthix page 2
                    new LootItem(3837, 1, 1, 141),   //Guthix page 3
                    new LootItem(3838, 1, 1, 141),   //Guthix page 4
                    new LootItem(12613, 1, 1, 141),   //Bandos page 1
                    new LootItem(12614, 1, 1, 141),   //Bandos page 2
                    new LootItem(12615, 1, 1, 141),   //Bandos page 3
                    new LootItem(12616, 1, 1, 141),   //Bandos page 4
                    new LootItem(12617, 1, 1, 141),   //Armadyl page 1
                    new LootItem(12618, 1, 1, 141),   //Armadyl page 2
                    new LootItem(12619, 1, 1, 141),   //Armadyl page 3
                    new LootItem(12620, 1, 1, 141),   //Armadyl page 4
                    new LootItem(12621, 1, 1, 141),   //Ancient page 1
                    new LootItem(12622, 1, 1, 141),   //Ancient page 2
                    new LootItem(12623, 1, 1, 141),   //Ancient page 3
                    new LootItem(12624, 1, 1, 141)   //Ancient page 4
            );

    private void open(Player player) {
        ClueSave save;
        if(this == MASTER) {
            if(player.masterClue == null)
                player.masterClue = new ClueSave();
            save = player.masterClue;
        } else if(this == ELITE) {
            if(player.eliteClue == null)
                player.eliteClue = new ClueSave();
            save = player.eliteClue;
        } else if(this == HARD) {
            if(player.hardClue == null)
                player.hardClue = new ClueSave();
            save = player.hardClue;
        } else if(this == MEDIUM) {
            if(player.medClue == null)
                player.medClue = new ClueSave();
            save = player.medClue;
        } else if(this == BEGINNER) {
            if(player.beginnerClue == null)
                player.beginnerClue = new ClueSave();
            save = player.beginnerClue;
        } else {
            if(player.easyClue == null)
                player.easyClue = new ClueSave();
            save = player.easyClue;
        }
        if(save.id == -1 || Clue.CLUES[save.id] == null) {
            StepType stepType = Random.get(StepType.values());
            List<Clue> clues = Clue.CLUES_BY_CATEGORY.get(stepType).get(this.ordinal());
            save.id = Random.get(clues).id;
            if(save.remaining == 0)
                save.remaining = Random.get(minStages, maxStages);
        }
        Clue.CLUES[save.id].open(player);
    }

    public void loot(Player player) {
        ItemContainer container = new ItemContainer();
        container.init(player, 6, -1, 0, 141, true);
        container.sendAll = true;
        for(int i = 0; i < Random.get(minRolls, maxRolls); i++) {
            Item item = lootTable.rollItem();
            if(item.lootBroadcast != null)
               item.lootBroadcast.sendNews(player, player.getName() + " just received " + item.getDef().descriptiveName + " from " + descriptiveName + " clue scroll!");
            container.add(item);
        }
        if (masterChance != -1 && Random.get(masterChance) == 1) {
            container.add(24366);   // Add a master clue
        }
        if (this == MASTER && Random.get(1000) == 1) {
            Pet.BLOODHOUND.unlock(player);
        }
        for(Item item : container.getItems()) {
            if(item != null) {
                player.getInventory().addOrDrop(item.getId(), item.getAmount());
                if (player.getCollectionLog().collect(item) && shared.getLootItems().stream().noneMatch(i -> i.id == item.getId())) {
                    player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.CLUEUNIQUE, this.toString());
                }
            }
        }
        player.openInterface(InterfaceType.MAIN, 73);
        player.getPacketSender().sendAccessMask(73, 3, 0, 6, 1024);
        container.sendUpdates();
    }

    public ClueSave getSave(Player player) {
        if (this == MASTER)
            return player.masterClue;
        if (this == ELITE)
            return player.eliteClue;
        if (this == HARD)
            return player.hardClue;
        if (this == MEDIUM)
            return player.medClue;
        if (this == BEGINNER)
            return player.beginnerClue;
        return player.easyClue;
    }

    public static void showClueDrops(Player player, ClueType clue) {
        clue.lootTable.showDrops(player, "Clue Scroll");
    }

    private static void openClueBox(Player player, Item item, ClueType clueType) {
        if (player.getInventory().contains(clueType.clueId) || player.getBank().contains(clueType.clueId)) {
            player.dialogue(new ItemDialogue().one(clueType.clueId, "You already have a clue of this tier. Please complete that one before opening another."));
            return;
        }
        if (!player.getInventory().hasFreeSlots(1) && player.getInventory().getAmount(clueType.boxId) > 1) {
            player.dialogue(new MessageDialogue("You do not have enough inventory space to open a clue."));
            return;
        }
        // Reset clue save if player has one
        if (clueType.getSave(player) != null) {
            clueType.getSave(player).id = -1;
        }
        // Clear clue attributes
        player.removeAttribute(AttributeKey.FALO_SONG);
        player.removeTemporaryAttribute(AttributeKey.SPAWNED_WIZARD);
        player.removeTemporaryAttribute(AttributeKey.KILLED_WIZARD);
        player.removeTemporaryAttribute(AttributeKey.URI_SPAWNED);
        player.removeTemporaryAttribute(AttributeKey.URI_CLUE);

        player.getInventory().remove(clueType.boxId, 1);
        player.getInventory().add(clueType.clueId, 1);
    }

    static {
        for(ClueType type : values()) {
            /*
             * Clue scroll
             */
            if(type.clueId == -1)
                continue;
            ItemDef clueDef = ItemDef.get(type.clueId);
            clueDef.clueType = type;
            ItemAction.registerInventory(clueDef.id, "read", (player, item) -> {
                type.open(player);
            });
            ItemAction.registerInventory(clueDef.id, "check steps", (player, item) -> {
                ClueSave save = type.getSave(player);
                if(save == null)
                    player.sendMessage("You haven't started this clue yet.");
                else if(save.remaining == 1)
                    player.sendMessage("There is 1 step remaining in this clue.");
                else
                    player.sendMessage("There are " + save.remaining + " steps remaining in this clue.");
            });
            ItemAction.registerInventory(type.boxId, "open", (player, item) -> openClueBox(player, item, type));
            /*
             * Casket
             */
            ItemDef casketDef = ItemDef.get(type.casketId);
            ItemAction.registerInventory(casketDef.id, "open", (player, item) -> {
                item.remove(1);
                type.loot(player);
            });
        }
    }

}