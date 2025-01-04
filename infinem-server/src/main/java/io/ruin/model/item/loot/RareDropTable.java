package io.ruin.model.item.loot;

import io.ruin.api.utils.Random;
import io.ruin.model.content.bestiary.perks.impl.LuckPerk;
import io.ruin.model.content.bestiary.perks.impl.RespawnPerk;
import io.ruin.model.content.tasksystem.relics.impl.fragments.FragmentModifier;
import io.ruin.model.content.tasksystem.relics.impl.fragments.FragmentType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;
import io.ruin.utility.Color;

import java.util.Optional;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/8/2022
 */
public class RareDropTable {

    public static LootTable RARE_DROP_TABLE = new LootTable()
            .addTable(250,  // Coin table
                    new LootItem(Items.COINS, 2500, 10000, 3),
                    new LootItem(Items.COINS, 15000, 25000, 2),
                    new LootItem(Items.COINS, 25000, 100000, 1)
            )
            .addTable(1000,  // Runes table
                    new LootItem(Items.NATURE_RUNE, 25, 60, 3),
                    new LootItem(Items.LAW_RUNE, 25, 65,3),
                    new LootItem(Items.DEATH_RUNE, 25, 40,3),
                    new LootItem(Items.BLOOD_RUNE, 15, 30,2),
                    new LootItem(Items.WRATH_RUNE, 10, 25,1),
                    new LootItem(Items.LAVA_RUNE, 25, 50,5),
                    new LootItem(Items.MIST_RUNE, 25, 50,5),
                    new LootItem(Items.AIR_RUNE, 250, 500,10),
                    new LootItem(Items.WATER_RUNE, 250, 500,10),
                    new LootItem(Items.EARTH_RUNE, 250, 500, 10),
                    new LootItem(Items.FIRE_RUNE, 200, 400,10)
            )
            .addTable(600,  // Ammo table
                    new LootItem(Items.RUNE_ARROW, 30, 80, 5),
                    new LootItem(Items.ADAMANT_ARROW, 50, 125, 10),
                    new LootItem(Items.STEEL_ARROW, 100, 250, 15),
                    new LootItem(Items.RUNITE_BOLTS, 30, 80, 5),
                    new LootItem(Items.ADAMANT_BOLTS, 50, 125, 10),
                    new LootItem(Items.ONYX_BOLT_TIPS, 5, 15, 1)
            )
            .addTable(250,  // Potion table
                    new LootItem(Items.PRAYER_POTION_4_NOTE, 5, 15,2),
                    new LootItem(Items.SUPER_RESTORE_4_NOTE, 4, 13,1),
                    new LootItem(Items.SUPER_ATTACK_4_NOTE, 5, 15,5),
                    new LootItem(Items.SUPER_STRENGTH_4_NOTE, 5, 15,5),
                    new LootItem(Items.SUPER_DEFENCE_4_NOTE, 5, 15,5),
                    new LootItem(Items.RANGING_POTION_4_NOTE, 5, 15,5),
                    new LootItem(Items.MAGIC_POTION_4_NOTE, 5, 15,5)
            )
            .addTable(450,  // Gem table
                    new LootItem(Items.UNCUT_DRAGONSTONE_NOTE, 1, 2,1),
                    new LootItem(Items.UNCUT_DIAMOND_NOTE, 1, 5,5),
                    new LootItem(Items.UNCUT_RUBY_NOTE, 5, 15,10),
                    new LootItem(Items.UNCUT_EMERALD_NOTE, 10, 20,10),
                    new LootItem(Items.UNCUT_SAPPHIRE_NOTE, 15, 30, 10)
            )
            .addTable(200,  // Rune equipment table
                    new LootItem(Items.RUNE_FULL_HELM, 1,1),
                    new LootItem(Items.RUNE_MED_HELM, 1, 2),
                    new LootItem(Items.RUNE_PLATEBODY, 1, 1),
                    new LootItem(Items.RUNE_CHAINBODY, 1, 2),
                    new LootItem(Items.RUNE_KITESHIELD, 1, 1),
                    new LootItem(Items.RUNE_SQ_SHIELD, 1, 2),
                    new LootItem(Items.RUNE_PLATELEGS, 1, 1),
                    new LootItem(Items.RUNE_2H_SWORD, 1, 1),
                    new LootItem(Items.RUNE_BATTLEAXE, 1, 1),
                    new LootItem(Items.RUNE_SCIMITAR, 1, 1)
            )
            .addTable(50,  // Dragon equipment table
                    new LootItem(Items.DRAGON_MED_HELM, 1, 2),
                    new LootItem(Items.SHIELD_LEFT_HALF, 1, 1),
                    new LootItem(Items.DRAGON_SPEAR, 1, 1)
            )
            .addTable(500,  // Key table
                    new LootItem(Items.LOOP_HALF_OF_KEY, 1,1 ),
                    new LootItem(Items.TOOTH_HALF_OF_KEY, 1, 1)
            )
            .addTable(100,  // MEGA TABLE ROLL
                    new LootItem(Items.PALM_TREE_SEED, 7, 12,1),
                    new LootItem(Items.MAGIC_SEED, 3, 5, 2),
                    new LootItem(Items.UNCUT_DRAGONSTONE_NOTE, 45, 55, 1),
                    new LootItem(Items.UNCUT_DIAMOND_NOTE, 45, 55, 2),
                    new LootItem(Items.YEW_LOGS_NOTE, 165, 315, 1),
                    new LootItem(Items.MAPLE_LOGS_NOTE, 250, 450, 2),
                    new LootItem(Items.RAW_SHARK_NOTE, 225, 275, 1),
                    new LootItem(Items.RAW_LOBSTER_NOTE, 300, 450, 2),
                    new LootItem(Items.MAHOGANY_PLANK_NOTE, 45, 55, 1),
                    new LootItem(Items.TEAK_PLANK_NOTE, 45, 55, 2),
                    new LootItem(Items.BIG_BONES_NOTE, 68, 82, 1),
                    new LootItem(Items.COAL_NOTE, 75, 175, 2),
                    new LootItem(Items.MOLTEN_GLASS_NOTE, 130, 220, 2),
                    new LootItem(Items.PURE_ESSENCE_NOTE, 150, 225, 2),
                    new LootItem(Items.SOFT_CLAY_NOTE, 300, 450, 2),
                    new LootItem(Items.FLAX_NOTE, 450, 550, 3)
            )
            .addTable(1,  // GIGA TABLE ROLL
                    new LootItem(Items.BLURBERRY_SPECIAL, 1, 9),  // Blurberry Special
                    new LootItem(773, 1, 1)   // Hazelmere's signet ring
            );

    private static final int[] ROLL_CHANCE = { // 1 in x, index is luck tier
            1000, 500, 450, 425, 400
    };

    private static int getLuckTier(Player player) {
        Item ring = player.getEquipment().get(Equipment.SLOT_RING);
        if (ring == null)
            return 0;
        switch (ring.getId()) {
            case Items.RING_OF_WEALTH_I5:
            case Items.RING_OF_WEALTH_I4:
            case Items.RING_OF_WEALTH_I3:
            case Items.RING_OF_WEALTH_I2:
            case Items.RING_OF_WEALTH_I1:
            case Items.RING_OF_WEALTH_I:
            case Items.RING_OF_WEALTH_5:
            case Items.RING_OF_WEALTH_4:
            case Items.RING_OF_WEALTH_3:
            case Items.RING_OF_WEALTH_2:
            case Items.RING_OF_WEALTH_1:
            case Items.RING_OF_WEALTH:
                return 1;
            case 32000:   // Ring of fortune
            case 32003:   // Hazelmere's signet ring
                return 2;
            default:
                return 0;
        }
    }

    //Jason Was Here, Give me Hazelmere's Ring
    public static Optional<Item> rollRareDropTable(NPC npc, Player player) {
        // Combat level check
        if (npc.getDef().combatInfo == null || (npc.getDef().combatLevel > 50 && npc.getDef().combatInfo.hitpoints < 10)) {
            return Optional.empty();
        }
        int luckTier = Math.max(
                getLuckTier(player),
                (int) Math.round(player.getBestiary().getBestiaryEntry(npc.getDef()).getPerkMultiplier(LuckPerk.class, 0) * 100D));
        int chance = Math.max(ROLL_CHANCE[luckTier] - (int) Math.floor(npc.getDef().combatLevel * 0.35), 75);   // 1/75 is the lowest the rate can go
        if (Random.get(chance) != 1) {
            return Optional.empty();
        }
        Item item = RARE_DROP_TABLE.rollItem();
        player.getTaskManager().doLookupByUUID(905, 1); // Get a Drop from the Rare Drop Table
        return Optional.of(item);
    }

    public static void rollSkillingRareDropTable(Player player, FragmentType type, int levelRequirement) {
        if (!player.getRelicFragmentManager().hasModifier(type, FragmentModifier.RDT)) return;
        int luckTier = getLuckTier(player);
        int chance = Math.max(ROLL_CHANCE[luckTier] - levelRequirement * 3, 75);   // 1/75 is the lowest the rate can go
        if (Random.get(chance) != 1) {
            return;
        }
        Item item = RARE_DROP_TABLE.rollItem();
        player.getTaskManager().doLookupByUUID(905, 1); // Get a Drop from the Rare Drop Table
        if (player.getBank().hasRoomFor(item.getId(), item.getAmount())) {
            player.getBank().add(item.getId(), item.getAmount());
            player.sendMessage(Color.GREEN.wrap("<shad=0>You find " + item.getAmount() + "x " + item.getDef().name + " while " + type + ". " +
                    (item.getAmount() > 1 ? "They were " : "It was ") + "sent to your bank."));
        } else {
            player.getInventory().addOrDrop(item);
            player.sendMessage(Color.GREEN.wrap("<shad=0>You find " + item.getAmount() + "x " + item.getDef().name + " while " + type + "."));
        }
    }
}
