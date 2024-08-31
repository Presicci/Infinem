package io.ruin.model.content.tasksystem.areas;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/6/2024
 */
@Getter
public enum AreaShopItem {
    /**
     * Elite in any tier lets you access shop from bank?
     */
    /*
     * General
     */
    // Key ring
    // cullinaromancer
    // dwarven rock cake

    /*
     * Asgarnia
     */
    FALADOR_SHIELD_1(Items.FALADOR_SHIELD_1, TaskArea.ASGARNIA, AreaTaskTier.EASY, 50),
    LAW_TALISMAN(Items.LAW_TALISMAN, TaskArea.ASGARNIA, AreaTaskTier.EASY, 1000),
    SLED(Items.SLED_2, TaskArea.ASGARNIA, AreaTaskTier.EASY, 50),
    INITIATE_SALLET(Items.INITIATE_SALLET, TaskArea.ASGARNIA, AreaTaskTier.EASY, 6000),
    INITIATE_HAUBERK(Items.INITIATE_HAUBERK, TaskArea.ASGARNIA, AreaTaskTier.EASY, 10000),
    INITIATE_CUISSE(Items.INITIATE_CUISSE, TaskArea.ASGARNIA, AreaTaskTier.EASY, 8000),
    FALADOR_SHIELD_2(Items.FALADOR_SHIELD_2, TaskArea.ASGARNIA, AreaTaskTier.MEDIUM, 250),
    PROSELYTE_SALLET(Items.PROSELYTE_SALLET, TaskArea.ASGARNIA, AreaTaskTier.MEDIUM, 8000),
    PROSELYTE_HAUBERK(Items.PROSELYTE_HAUBERK, TaskArea.ASGARNIA, AreaTaskTier.MEDIUM, 12000),
    PROSELYTE_CUISSE(Items.PROSELYTE_CUISSE, TaskArea.ASGARNIA, AreaTaskTier.MEDIUM, 10000),
    PROSELYTE_TASSET(Items.PROSELYTE_TASSET, TaskArea.ASGARNIA, AreaTaskTier.MEDIUM, 10000),
    FALADOR_SHIELD_3(Items.FALADOR_SHIELD_3, TaskArea.ASGARNIA, AreaTaskTier.HARD, 500),
    FALADOR_SHIELD_4(Items.FALADOR_SHIELD_4, TaskArea.ASGARNIA, AreaTaskTier.ELITE, 1000),
    // Dwarven helmet
    // soul bearer
    // bomber jacket costume

    /*
     * Desert
     */
    DESERT_AMULET_1(Items.DESERT_AMULET_1, TaskArea.DESERT, AreaTaskTier.EASY, 50),
    CATSPEAK_AMULET(Items.CATSPEAK_AMULET, TaskArea.DESERT, AreaTaskTier.EASY, 100),
    DESERT_AMULET_2(Items.DESERT_AMULET_2, TaskArea.DESERT, AreaTaskTier.MEDIUM, 250),
    KERIS(Items.KERIS, TaskArea.DESERT, AreaTaskTier.MEDIUM, 20000),
    GOLDSMITH_GAUNTLETS(Items.GOLDSMITH_GAUNTLETS, TaskArea.DESERT, AreaTaskTier.MEDIUM, 25000),
    ANCIENT_STAFF(Items.ANCIENT_STAFF, TaskArea.DESERT, AreaTaskTier.MEDIUM, 80000),
    CAMULET(Items.CAMULET, TaskArea.DESERT, AreaTaskTier.MEDIUM, 1000),
    DESERT_AMULET_3(Items.DESERT_AMULET_3, TaskArea.DESERT, AreaTaskTier.HARD, 500),
    DESERT_AMULET_4(Items.DESERT_AMULET_4, TaskArea.DESERT, AreaTaskTier.ELITE, 1000),
    //KERIS_PARTISAN(25979, TaskArea.DESERT, AreaTaskTier.ELITE),    Update model first
    // Goldsmith gauntlets
    // mouse toy
    // wrought iron key
    // ring of visibility
    // catspeak amulet - e
    // doctor's hat, nurse hat
    // camel mask
    // robes of elidinis
    // slave robes

    /*
     * Fremennik
     */
    SEA_BOOTS_1(Items.FREMENNIK_SEA_BOOTS_1, TaskArea.FREMENNIK, AreaTaskTier.EASY, 50),
    BEARHEAD(Items.BEARHEAD, TaskArea.FREMENNIK, AreaTaskTier.EASY, 2500),
    GOLD_HELMET(Items.GOLD_HELMET, TaskArea.FREMENNIK, AreaTaskTier.EASY, 2500),
    SEA_BOOTS_2(Items.FREMENNIK_SEA_BOOTS_2, TaskArea.FREMENNIK, AreaTaskTier.MEDIUM, 250),
    HELM_OF_NEITIZNOT(Items.HELM_OF_NEITIZNOT, TaskArea.FREMENNIK, AreaTaskTier.MEDIUM, 50000),
    ENCHANTED_LYRE(3690, TaskArea.FREMENNIK, AreaTaskTier.MEDIUM, 5000),
    VS_SHIELD(24266, TaskArea.FREMENNIK, AreaTaskTier.MEDIUM, 50000),
    SEA_BOOTS_3(Items.FREMENNIK_SEA_BOOTS_3, TaskArea.FREMENNIK, AreaTaskTier.HARD, 500),
    LUNAR_HELM(Items.LUNAR_HELM, TaskArea.FREMENNIK, AreaTaskTier.HARD, 15000),
    LUNAR_TORSO(Items.LUNAR_TORSO, TaskArea.FREMENNIK, AreaTaskTier.HARD, 120000),
    LUNAR_LEGS(Items.LUNAR_LEGS, TaskArea.FREMENNIK, AreaTaskTier.HARD, 80000),
    LUNAR_GLOVES(Items.LUNAR_GLOVES, TaskArea.FREMENNIK, AreaTaskTier.HARD, 10000),
    LUNAR_BOOTS(Items.LUNAR_BOOTS, TaskArea.FREMENNIK, AreaTaskTier.HARD, 10000),
    LUNAR_CAPE(Items.LUNAR_CAPE, TaskArea.FREMENNIK, AreaTaskTier.HARD, 12000),
    LUNAR_AMULET(Items.LUNAR_AMULET, TaskArea.FREMENNIK, AreaTaskTier.HARD, 4000),
    LUNAR_RING(Items.LUNAR_RING, TaskArea.FREMENNIK, AreaTaskTier.HARD, 2000),
    LUNAR_STAFF(Items.LUNAR_STAFF, TaskArea.FREMENNIK, AreaTaskTier.HARD, 30000),
    SEA_BOOTS_4(Items.FREMENNIK_SEA_BOOTS_4, TaskArea.FREMENNIK, AreaTaskTier.ELITE, 1000),
    // seal fo passge
    // jester costume

    /*
     * Kandarin
     */
    KANDARIN_HEADGEAR_1(Items.KANDARIN_HEADGEAR_1, TaskArea.KANDARIN, AreaTaskTier.EASY, 50),
    ARDOUGNE_CLOAK_1(Items.ARDOUGNE_CLOAK_1, TaskArea.KANDARIN, AreaTaskTier.EASY, 50),
    GLARIALS_AMULET(Items.GLARIALS_AMULET, TaskArea.KANDARIN, AreaTaskTier.EASY, 250),
    KANDARIN_HEADGEAR_2(Items.KANDARIN_HEADGEAR_2, TaskArea.KANDARIN, AreaTaskTier.MEDIUM, 250),
    ARDOUGNE_CLOAK_2(Items.ARDOUGNE_CLOAK_2, TaskArea.KANDARIN, AreaTaskTier.MEDIUM, 250),
    COOKING_GAUNTLETS(Items.COOKING_GAUNTLETS, TaskArea.KANDARIN, AreaTaskTier.MEDIUM, 25000),
    IBANS_STAFF(Items.IBANS_STAFF, TaskArea.KANDARIN, AreaTaskTier.MEDIUM, 200000),
    MONKEY_SPEAK_AMULET(Items.MSPEAK_AMULET, TaskArea.KANDARIN, AreaTaskTier.MEDIUM, 5000),
    KANDARIN_HEADGEAR_3(Items.KANDARIN_HEADGEAR_3, TaskArea.KANDARIN, AreaTaskTier.HARD, 500),
    ARDOUGNE_CLOAK_3(Items.ARDOUGNE_CLOAK_3, TaskArea.KANDARIN, AreaTaskTier.HARD, 500),
    ROYAL_SEED_POD(Items.ROYAL_SEED_POD, TaskArea.KANDARIN, AreaTaskTier.HARD, 10000),
    KANDARIN_HEADGEAR_4(Items.KANDARIN_HEADGEAR_4, TaskArea.KANDARIN, AreaTaskTier.ELITE, 1000),
    ARDOUGNE_CLOAK_4(Items.ARDOUGNE_CLOAK_4, TaskArea.KANDARIN, AreaTaskTier.ELITE, 1000),
    // Klank's gauntlets, gnome amulet, carnillean armour, hazeel's mark
    // Khazard armour set
    // Cooking gauntlets
    // Glarial's amulet
    // pendant of lucien, armadyl pendant
    // 10th squad sigil - teleport player to jungle demons?
    // fixed device
    // crystal saw seed
    // enchanted key - easy
    // fixed device - hard - make a visual upgrade to rpg?
    // medical gown
    // ghostly robes
    // fake beak, eagle cape
    // mourner's gear, gas mask
    // cattleprod
    // builder's costume

    /*
     * Karamja
     */
    KARAMJA_GLOVES_1(Items.KARAMJA_GLOVES_1, TaskArea.KARAMJA, AreaTaskTier.EASY, 50),
    KARAMJA_GLOVES_2(Items.KARAMJA_GLOVES_2, TaskArea.KARAMJA, AreaTaskTier.MEDIUM, 250),
    BULL_ROARER(Items.BULL_ROARER, TaskArea.KARAMJA, AreaTaskTier.MEDIUM, 500),
    KARAMJA_GLOVES_3(Items.KARAMJA_GLOVES_3, TaskArea.KARAMJA, AreaTaskTier.HARD, 500),
    KARAMJA_GLOVES_4(Items.KARAMJA_GLOVES_4, TaskArea.KARAMJA, AreaTaskTier.ELITE, 1000),
    // Beads of the dead
    // bone key

    /*
     * Misthalin
     */
    EXPLORERS_RING_1(Items.EXPLORERS_RING_1, TaskArea.MISTHALIN, AreaTaskTier.EASY, 50),
    VARROCK_ARMOUR_1(Items.VARROCK_ARMOUR_1, TaskArea.MISTHALIN, AreaTaskTier.EASY, 50),
    GHOSTSPEAK_AMULET(Items.GHOSTSPEAK_AMULET, TaskArea.MISTHALIN, AreaTaskTier.EASY, 250),
    SILVERLIGHT(Items.SILVERLIGHT, TaskArea.MISTHALIN, AreaTaskTier.EASY, 500),
    EXPLORERS_RING_2(Items.EXPLORERS_RING_2, TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, 250),
    VARROCK_ARMOUR_2(Items.VARROCK_ARMOUR_2, TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, 250),
    CHAOS_GAUNTLETS(Items.CHAOS_GAUNTLETS, TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, 25000),
    DARKLIGHT(Items.DARKLIGHT, TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, 1000),
    ANCIENT_MACE(Items.ANCIENT_MACE, TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, 1000),
    MAGIC_SECATEURS(Items.MAGIC_SECATEURS, TaskArea.MISTHALIN, AreaTaskTier.MEDIUM, 40000),
    EXPLORERS_RING_3(Items.EXPLORERS_RING_3, TaskArea.MISTHALIN, AreaTaskTier.HARD, 500),
    VARROCK_ARMOUR_3(Items.VARROCK_ARMOUR_3, TaskArea.MISTHALIN, AreaTaskTier.HARD, 500),
    EXPLORERS_RING_4(Items.EXPLORERS_RING_4, TaskArea.MISTHALIN, AreaTaskTier.ELITE, 1000),
    VARROCK_ARMOUR_4(Items.VARROCK_ARMOUR_4, TaskArea.MISTHALIN, AreaTaskTier.ELITE, 1000),
    // Bonesack, ram skull helm
    // Chaos gauntlets
    // dramen staff? prolly not
    // rat pole
    // beacon ring

    /*
     * Morytania
     */
    MORYTANIA_LEGS_1(Items.MORYTANIA_LEGS_1, TaskArea.MORYTANIA, AreaTaskTier.EASY, 50),
    WOLFBANE(Items.WOLFBANE, TaskArea.MORYTANIA, AreaTaskTier.EASY, 2500),
    ECTOPHIAL(Items.ECTOPHIAL, TaskArea.MORYTANIA, AreaTaskTier.EASY, 5000),
    MORYTANIA_LEGS_2(Items.MORYTANIA_LEGS_2, TaskArea.MORYTANIA, AreaTaskTier.MEDIUM, 250),
    GADDERHAMMER(Items.GADDERHAMMER, TaskArea.MORYTANIA, AreaTaskTier.MEDIUM, 3000),
    IVANDIS_FLAIL(22398, TaskArea.MORYTANIA, AreaTaskTier.MEDIUM, 20000),
    DRAKANSS_MEDALION(22400, TaskArea.MORYTANIA, AreaTaskTier.MEDIUM, 5000),
    SALVE_AMULET(Items.SALVE_AMULET, TaskArea.MORYTANIA, AreaTaskTier.MEDIUM, 10000),
    MORYTANIA_LEGS_3(Items.MORYTANIA_LEGS_3, TaskArea.MORYTANIA, AreaTaskTier.HARD, 500),
    BLISTERWOOD_FLAIL(24699, TaskArea.MORYTANIA, AreaTaskTier.HARD, 40000),
    BARRELCHEST_ANCHOR(Items.BARRELCHEST_ANCHOR, TaskArea.MORYTANIA, AreaTaskTier.HARD, 230000),
    MORYTANIA_LEGS_4(Items.MORYTANIA_LEGS_4, TaskArea.MORYTANIA, AreaTaskTier.ELITE, 1000),
    // Rod of ivandis, blessed axe, silver sickle (b)
    // ring of charos
    // prayer book - medium
    // holy wrench - medium

    /*
     * Tirannwn
     */
    WESTERN_BANNER_1(Items.WESTERN_BANNER_1, TaskArea.TIRANNWN, AreaTaskTier.EASY, 50),
    WESTERN_BANNER_2(Items.WESTERN_BANNER_2, TaskArea.TIRANNWN, AreaTaskTier.MEDIUM, 250),
    WESTERN_BANNER_3(Items.WESTERN_BANNER_3, TaskArea.TIRANNWN, AreaTaskTier.HARD, 500),
    WESTERN_BANNER_4(Items.WESTERN_BANNER_4, TaskArea.TIRANNWN, AreaTaskTier.ELITE, 1000),
    // Crystal shield/bow

    /*
     * Wilderness
     */
    WILDERNESS_SWORD_1(Items.WILDERNESS_SWORD_1, TaskArea.WILDERNESS, AreaTaskTier.EASY, 50),
    WILDERNESS_SWORD_2(Items.WILDERNESS_SWORD_2, TaskArea.WILDERNESS, AreaTaskTier.MEDIUM, 250),
    WILDERNESS_SWORD_3(Items.WILDERNESS_SWORD_3, TaskArea.WILDERNESS, AreaTaskTier.HARD, 500),
    WILDERNESS_SWORD_4(Items.WILDERNESS_SWORD_4, TaskArea.WILDERNESS, AreaTaskTier.ELITE, 1000),

    /*
     * Zeah
     */
    // Book of the dead, kharedst's memoirs
    // lunch by the lancalliums
    // jewellery of jubilation
    // a dark disposition
    // the fisher's flute
    // history and hearsay
    ;

    private final int itemId;
    private final TaskArea area;
    private final AreaTaskTier tier;
    private final int stock, cost;

    AreaShopItem(int itemId, TaskArea area, AreaTaskTier tier, int cost) {
        this(itemId, area, tier, 1, cost);
    }

    AreaShopItem(int itemId, TaskArea area, AreaTaskTier tier, int stock, int cost) {
        this.itemId = itemId;
        this.area = area;
        this.tier = tier;
        this.stock = stock;
        this.cost = cost;
        ItemDefinition.get(itemId).custom_values.put("EQUIP_CHECK", (Predicate<Player>) player -> {
            if (!area.hasTierUnlocked(player, tier)) {
                player.sendMessage("You need " + area.getPointsTillTier(player, tier) + " more task points from " + area + " tasks to equip this item.");
                return false;
            }
            return true;
        });
    }
}
