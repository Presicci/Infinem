package io.ruin.model.skills.smithing;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/29/2022
 */
public class RustyWeapons {

    private static final int RUSTY_SCIMITAR = Items.RUSTY_SCIMITAR;
    private static final int RUSTY_SWORD = Items.RUSTY_SWORD;

    private static final int TINDEL_MARCHANT = 1358;

    private static final LootTable SCIMITARS = new LootTable().addTable(0,
            new LootItem(Items.BRONZE_SCIMITAR, 1, 750),
            new LootItem(Items.IRON_SCIMITAR, 1, 650),
            new LootItem(Items.STEEL_SCIMITAR, 1, 500),
            new LootItem(Items.WHITE_SCIMITAR, 1, 100),
            new LootItem(Items.MITHRIL_SCIMITAR, 1, 250),
            new LootItem(Items.ADAMANT_SCIMITAR, 1, 150),
            new LootItem(Items.RUNE_SCIMITAR, 1, 50),
            new LootItem(Items.DRAGON_SCIMITAR, 1, 3)
    );

    private static final LootTable SWORDS = new LootTable().addTable(0,
            new LootItem(Items.BRONZE_SWORD, 1, 750),
            new LootItem(Items.BRONZE_LONGSWORD, 1, 750),
            new LootItem(Items.IRON_SWORD, 1, 650),
            new LootItem(Items.IRON_LONGSWORD, 1, 650),
            new LootItem(Items.STEEL_SWORD, 1, 500),
            new LootItem(Items.STEEL_LONGSWORD, 1, 500),
            new LootItem(Items.WHITE_SWORD, 1, 100),
            new LootItem(Items.WHITE_LONGSWORD, 1, 100),
            new LootItem(Items.MITHRIL_SWORD, 1, 250),
            new LootItem(Items.MITHRIL_LONGSWORD, 1, 250),
            new LootItem(Items.ADAMANT_SWORD, 1, 150),
            new LootItem(Items.ADAMANT_LONGSWORD, 1, 150),
            new LootItem(Items.RUNE_SWORD, 1, 50),
            new LootItem(Items.RUNE_LONGSWORD, 1, 50),
            new LootItem(Items.DRAGON_LONGSWORD, 1, 6)
    );

    private static void refineWeapon(Player player, Item item, LootTable lootTable, boolean atNPC) {
        if (atNPC && !player.getInventory().contains(995, 100)) {
            player.dialogue(
                    new NPCDialogue(TINDEL_MARCHANT, "*cough cough* You're going to pay me for this, right?")
            );
            return;
        }
        Item loot = lootTable.rollItem();
        item.setId(loot.getId());
        if (!atNPC) {
            player.animate(898);
            player.getStats().addXp(StatType.Smithing, 25, true);
            player.dialogue(
                    new ItemDialogue().one(loot.getId(), "You break down the rust and find yourself with "
                            + loot.getDef().descriptiveName + ". Good as new!")
            );
        } else {
            player.getInventory().remove(995, 100);
            player.dialogue(
                    new ItemDialogue().one(loot.getId(), "He hands you back "
                            + loot.getDef().descriptiveName + ". Good as new!")
            );
        }
    }

    private static void giveSword(Player player, NPC npc) {
        if (player.getInventory().contains(RUSTY_SWORD)) {
            refineWeapon(player, player.getInventory().collectItems(RUSTY_SWORD).get(0), SWORDS, true);
        } else if (player.getInventory().contains(RUSTY_SCIMITAR)) {
            refineWeapon(player, player.getInventory().collectItems(RUSTY_SCIMITAR).get(0), SCIMITARS, true);
        } else {
            player.dialogue(
                    new NPCDialogue(TINDEL_MARCHANT, "You do not seem to have anything I am interested in.")
            );
        }
    }

    private static final int[] OBJECTS = {6802, 6801};

    static {
        for (int objectId : OBJECTS) {
            ItemObjectAction.register(RUSTY_SCIMITAR, objectId, ((player, item, obj) -> refineWeapon(player, item, SCIMITARS, false)));
            ItemObjectAction.register(RUSTY_SWORD, objectId, ((player, item, obj) -> refineWeapon(player, item, SWORDS, false)));
        }
        ItemNPCAction.register(RUSTY_SWORD, TINDEL_MARCHANT, ((player, item, npc) -> refineWeapon(player, item, SWORDS, true)));
        ItemNPCAction.register(RUSTY_SCIMITAR, TINDEL_MARCHANT, ((player, item, npc) -> refineWeapon(player, item, SCIMITARS, true)));

        NPCAction.register(TINDEL_MARCHANT, "talk-to", ((player, npc) -> player.dialogue(
                new PlayerDialogue("Hello there."),
                new NPCDialogue(TINDEL_MARCHANT, "Hello there friend, if you happen to find any rusty or discarded armaments, I would happily repair them for a price."),
                new PlayerDialogue("I'll keep that in mind...")
        )));

        NPCAction.register(TINDEL_MARCHANT, "give-sword", RustyWeapons::giveSword);
    }
}
