package io.ruin.model.entity.npc;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.combat.Killer;
import io.ruin.model.content.PvmPoints;
import io.ruin.model.content.bestiary.perks.impl.NotedDropPerk;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.player.DoubleDrops;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.WildernessKey;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfEthereum;
import io.ruin.model.item.actions.impl.jewellery.RingOfWealth;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.item.loot.RareDropTable;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapArea;
import io.ruin.model.map.Position;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.actions.impl.dungeons.KourendCatacombs;
import io.ruin.services.discord.impl.RareDropEmbedMessage;
import io.ruin.utility.Broadcast;

import java.util.*;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.ItemID.VORKATHS_HEAD;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/29/2024
 */
public class NPCDrops {

    private final NPCCombat npcCombat;
    private final NPC npc;

    public NPCDrops(NPCCombat npcCombat) {
        this.npcCombat = npcCombat;
        this.npc = npcCombat.npc;
    }

    public void dropItems(Killer killer) {
        NPCDef def = npc.getDef();
        Position dropPosition = npcCombat.getDropPosition();
        Player pKiller = killer == null ? null : killer.player;
        if (pKiller == null) {
            return;
        }

        /*
         * Drop table loots
         */
        LootTable t = def.lootTable;
        if(t != null) {
            int rolls = DoubleDrops.getRolls(killer.player, npc);
            if (npc.isSuperior) {
                rolls += 3; // Superiors drop minimum of 4 items
            }
            for(int i = 0; i < rolls; i++) {
                List<Item> items = t.rollItems(i == 0);
                List<Item> toAdd = new ArrayList<Item>();
                Item seedItem = null;
                if (items != null) {
                    for (Item item : items) {
                        /*
                         * Handles common table drops, denoted in the item list as an id of 0
                         */
                        if (item.getId() == 0) {
                            if (item.getAmount() == 0) {
                                handleGeneralSeedDrop(killer, dropPosition, pKiller);
                            } else {
                                handleTableDrop(killer, dropPosition, pKiller, LootTable.CommonTables.values()[item.getAmount() + 5]);
                            }
                            seedItem = item;
                        }

                        /*
                         * Checks if an item is part of a drop group
                         */
                        List<Item> gItems = getGroupDrop(item);
                        if (gItems.size() > 0) {
                            toAdd.addAll(gItems);
                        }
                    }
                    /*
                     * Checks if a Hydra's Eye is in the loot, and makes sure the player
                     * gets the correct part they need.
                     */
                    for (Item item : items) {
                        if (item.getId() == 22973) {
                            int part = generateBrimstoneRingPart(killer.player);
                            if (part != 22973) {
                                items.remove(item);
                                items.add(new Item(part));
                            }
                            break;
                        }
                    }
                    if (seedItem != null) {
                        items.remove(seedItem);
                    }
                    if (toAdd.size() > 0) {
                        items.addAll(toAdd);
                    }
                    handleDrop(killer, dropPosition, pKiller, items);
                }
            }
        } else {    // Prevents all these other rolls on npcs with no drops
            return;
        }

        /*
         * RDT roll
         */
        rollRareDropTable(killer, pKiller, dropPosition);

        /*
         * Handle the chaos elemental minor drops
         */
        handleChaosEleDrops(killer, pKiller, dropPosition);

        /*
         * Handle the kalphite queen consumable drops
         */
        handleKalphiteQueenDrops(killer, pKiller, dropPosition);

        /*
         * Crystal shard drops
         * Some monsters in Iorwerth Dungeon don't have exclusive drop polls,
         * so we add a crystal shard roll here
         */
        handCrystalShard(killer, pKiller, dropPosition);

        /*
         * Handle superior slayer monster unique drops
         */
        handleSuperiorDrops(killer, pKiller, dropPosition);

        // Forthos dungeon grubby key drops
        handleGrubbyDrops(killer, pKiller, dropPosition);

        /*
         * Handle giving player vorkaths head after 50 kills.
         */
        vorkathHead(dropPosition, pKiller);

        /*
         * Gives players PVM Points
         */
        PvmPoints.addPoints(pKiller, npc);
        /*
         * Casket loots
         */
        //GoldCasket.drop(pKiller, npc, dropPosition);
        /*
         * Summer Loot
         */
        //SummerTokens.npcKill(pKiller, npc, dropPosition);
        /*
         * Catacombs loot
         */
        KourendCatacombs.drop(pKiller, npc, dropPosition);

        /*
         * Roll for OSRS wilderness key
         */
        if(World.wildernessKeyEvent)
            WildernessKey.rollForWildernessBossKill(pKiller, npc);

        /*
         * PvP Item loots
         */
        Wilderness.rollPvPItemDrop(pKiller, npc, dropPosition);

        /*
         * Roll for wilderness clue key
         */
        Wilderness.rollClueKeyDrop(pKiller, npc, dropPosition);

        /*
         * Blood Money
         */
        Wilderness.bloodMoneyDrop(pKiller, npc);

        /*
         * Resource packs
         */
        Wilderness.resourcePackWithBoss(pKiller, npc);
    }

    private void handleDrop(Killer killer, Position dropPosition, Player pKiller, Item item) {
        List<Item> items = new ArrayList<>();
        items.add(item);
        handleDrop(killer, dropPosition, pKiller, items);
    }

    private void handleDrop(Killer killer, Position dropPosition, Player pKiller, List<Item> items) {
        for(Item item : items) {
            // Attempt a task unlock for each item dropped
            pKiller.getTaskManager().doUnlockItemLookup(item);
            pKiller.getTaskManager().doDropGroupLookup(item.getDef().name.toLowerCase());

            /*
             * Convert clue scrolls into clue boxes
             */
            if (item.getDef().clueType != null) {
                item.setId(item.getDef().clueType.boxId);
            }

            /*
             * Ethereum auto collect
             */
            if (item.getId() == 21820) {
                if (BraceletOfEthereum.handleEthereumDrop(pKiller, item)) {
                    continue;
                }
            }

            /*
             * Note drops in rev caves and wildy slayer cave while amulet of avarice is equipped.
             */
            if ((MapArea.REVENANT_CAVES.inArea(pKiller) || MapArea.WILDERNESS_SLAYER_CAVE.inArea(pKiller))
                    && pKiller.getEquipment().contains(22557) && item.getDef().notedId > 0) {
                item.setId(item.getDef().notedId);
            }

            /*
             * Coin auto collect
             */
            if (item.getId() == COINS_995) {
                if (RingOfWealth.check(pKiller, item)) {
                    pKiller.getInventory().addOrDrop(item);
                    continue;
                }
            }
            boolean dropItem = true;
            for(Item equipment : pKiller.getEquipment().getItems()) {
                if(equipment != null && equipment.getDef() != null) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(equipment);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                dropItem = effect.getUpgrade().modifyDroppedItem(pKiller, item);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
            // Bonecrusher
            if (pKiller.getBoneCrusher().handleBury(item)) {
                continue;
            }

            // Ash sanctifier
            if (pKiller.getAshSanctifier().handleScatter(item)) {
                continue;
            }

            /*
             * Donator Benefit: [Noted dragon bones in wilderness]
             */
            if (item.getId() == 534 || item.getId() == 536 || item.getId() == 6812 || item.getId() == 11943 || item.getId() == 22124) {
                if (pKiller.isSapphire() && pKiller.wildernessLevel > 0) {
                    item.setId(item.getDef().notedId);
                }
            }

            /*
             * Donator Benefit: [Noted herbs in wilderness]
             */
            if (item.getDef().name.toLowerCase().contains("grimy")) {
                if (pKiller.isDiamond() && pKiller.wildernessLevel > 0) {
                    if (item.getDef().notedId > -1)
                        item.setId(item.getDef().notedId);
                }
            }

            if (item.getDef().name.toLowerCase().contains("statius") ||
                    item.getDef().name.toLowerCase().contains("vesta") ||
                    item.getDef().name.toLowerCase().contains("zuriel")) {
                pKiller.sendMessage("You have been red skulled and tele-blocked because of your loot!");
                pKiller.getCombat().skullHighRisk();
                pKiller.getCombat().teleblock();
            }

            /*
             * Modify drop for specific npc
             */
            if(npc.dropListener != null)
                npc.dropListener.dropping(killer, item);

            /*
             * Global Broadcast
             */
            if(item.lootBroadcast != null || item.getDef().dropAnnounce) {
                getRareDropAnnounce(pKiller, item);
            }

            double chance = pKiller.getBestiary().getBestiaryEntry(npc.getDef()).getPerkMultiplier(NotedDropPerk.class);
            if (item.getDef().notedId > -1 && chance > 0 && Random.get() < chance) {
                item.setId(item.getDef().notedId);
            } else if (canNote(pKiller, item)) {
                item.setId(item.getDef().notedId);
            }

            /*
             * Local Broadcast!
             */
            if (npcCombat.info.local_loot) {
                getLocalAnnounce(pKiller, item);
            }

            pKiller.getCollectionLog().collect(item);

            /*
             * Spawn the item on the ground.
             */
            if (dropItem) {
                if (item.getAmount() > 1 && !item.getDef().stackable) {
                    for (int index = 0; index < item.getAmount(); index++) {
                        new GroundItem(new Item(item.getId())).position(dropPosition).owner(pKiller).spawn();
                    }
                } else {
                    new GroundItem(item).position(dropPosition).owner(pKiller).spawn();
                }
            }
        }
    }

    private boolean canNote(Player pKiller, Item item) {
        if (item.getDef().notedId < 0) return false;
        String name = npc.getDef().name;
        if (name.toLowerCase().contains("giant mole") && AreaReward.NOTED_GIANT_MOLE_PARTS.hasReward(pKiller) && (item.getId() == Items.MOLE_CLAW || item.getId() == Items.MOLE_SKIN))
            return true;
        return false;
    }

    private void getRareDropAnnounce(Player pKiller, Item item) {
        int amount = item.getAmount();
        String message = pKiller.getName() + " just received ";
        if(amount > 1)
            message += NumberUtils.formatNumber(amount) + " x " + item.getDef().name;
        else
            message += item.getDef().descriptiveName;
        if (item.lootBroadcast != null) {
            item.lootBroadcast.sendNews(pKiller, Icon.SILVER_STAR, "[Rare Drop] " + message + " from " + npc.getDef().descriptiveName + "!");
        } else {
            Broadcast.WORLD.sendNews(pKiller, Icon.SILVER_STAR, "[Rare Drop] " + message + " from " + npc.getDef().descriptiveName + "!");
        }
        RareDropEmbedMessage.sendDiscordMessage(message, npc.getDef().descriptiveName, item.getId());
    }

    private void getLocalAnnounce(Player pKiller, Item item) {
        npc.localPlayers().forEach(p -> p.sendMessage(Color.DARK_GREEN.wrap(pKiller.getName() + " received a drop: " + NumberUtils.formatNumber(item.getAmount()) + " x " + item.getDef().name)));
    }

    private void handleGeneralSeedDrop(Killer killer, Position dropPosition, Player pKiller) {
        int lvl = Random.get(this.npc.getDef().combatLevel * 10);
        LootTable.CommonTables ct = LootTable.CommonTables.GENERAL_SEED_0;
        if (lvl >= 485 && lvl < 728) {
            ct = LootTable.CommonTables.GENERAL_SEED_485;
        } else if (lvl >= 728 && lvl < 850) {
            ct = LootTable.CommonTables.GENERAL_SEED_728;
        } else if (lvl >= 850 && lvl < 947) {
            ct = LootTable.CommonTables.GENERAL_SEED_850;
        } else if (lvl >= 947 && lvl < 995) {
            ct = LootTable.CommonTables.GENERAL_SEED_947;
        } else if (lvl > 995) {
            ct = LootTable.CommonTables.GENERAL_SEED_995;
        }
        LootTable t = new LootTable().addTable(0, ct.items);
        handleDrop(killer, dropPosition, pKiller, t.rollItems(false));
    }

    private void handleTableDrop(Killer killer, Position dropPosition, Player pKiller, LootTable.CommonTables table) {
        LootTable t = new LootTable().addTable(0, table.items);
        handleDrop(killer, dropPosition, pKiller, t.rollItems(false));
    }

    private void rollRareDropTable(Killer killer, Player player, Position pos) {
        Optional<Item> item = RareDropTable.rollRareDropTable(npc, player);
        item.ifPresent(value -> handleDrop(killer, pos, player, value));
    }

    private void vorkathHead(Position dropPosition, Player pKiller) {
        if ((npc.getId() == 8059 || npc.getId() == 8061)
                && KillCounter.getKillCount(pKiller, BossKillCounter.VORKATH) >= 50
                && !pKiller.hasAttribute(AttributeKey.OBTAINED_VORKATH_HEAD)) {
            Item item = new Item(VORKATHS_HEAD);
            GroundItem groundItem = new GroundItem(item).position(dropPosition);
            groundItem.spawn();
            pKiller.putAttribute(AttributeKey.OBTAINED_VORKATH_HEAD, 1);
        }
    }

    public static final HashMap<String, Item[][]> groupedDrops = new HashMap<String, Item[][]>() {{
        put("alchemical hydra", new Item[][] {
                { new Item(1401), new Item(1403) }, // Mystic fire and water staff
                { new Item(1079), new Item(1127) }, // Rune platelegs and platebody
                { new Item(1093), new Item(1127) }, // Rune plateskirt and platebody
                { new Item(4111), new Item(4113) }, // Mystic robe bottom and top
                { new Item(169, 3), new Item(3026, 3) } // Super restores and super rangings
        });

        put("ammonite crab", new Item[][] {
                { new Item(21543), new Item(21545) }    // Calcite and pyrophosphite
        });

        put("ancient zygomite", new Item[][] {
                { new Item(21543), new Item(21545) }    // Calcite and pyrophosphite
        });

        put("bryophyta", new Item[][] {
                { new Item(1620, 5), new Item(1618, 5) }    // Uncut ruby and uncut diamond
        });

        put("callisto", new Item[][] {
                { new Item(1620, 20), new Item(1618, 10) },    // Uncut ruby and uncut diamond
                { new Item(11936, 8), new Item(3024, 3) }    // Dark crab and super restore (4)
        });

        put("chaos fanatic", new Item[][] {
                { new Item(1622, 6), new Item(1624, 4) },    // Uncut emerald and uncut sapphire
                { new Item(1035), new Item(1033) }    // Zamorak monk top and zamorak monk bottom
        });

        put("commander zilyana", new Item[][] {
                { new Item(6687, 3), new Item(3024, 3) },    // Saradomin brew and super restore
                { new Item(163, 3), new Item(3042, 3) }    // Super defence and magic
        });

        put("crazy archaeologist", new Item[][] {
                { new Item(1622, 6), new Item(1624, 4) }    // Uncut emerald and uncut sapphire
        });

        put("insatiable bloodveld", new Item[][] {
                { new Item(526), new Item(532, 3) }    // Bones nad big bones
        });

        put("jungle horror", new Item[][] {
                { new Item(526), new Item(532, 3) }    // Bones nad big bones
        });

        put("k'ril tsutsaroth", new Item[][] {
                { new Item(145, 3), new Item(157, 3) },    // Super attack and super strength
                { new Item(3026, 3), new Item(189, 3) }    // Super restore and zammy brew
        });

        put("obor", new Item[][] {
                { new Item(1620, 5), new Item(1618, 5) },    // Uncut ruby and uncut diamond
        });

        put("scorpia", new Item[][] {
                { new Item(1622, 6), new Item(1624, 4) }    // Uncut emerald and uncut sapphire
        });

        put("venenatis", new Item[][] {
                { new Item(1620, 20), new Item(1618, 10) },    // Uncut ruby and uncut diamond
                { new Item(11936, 8), new Item(3024, 3) }    // Dark crab and super restore (4)
        });

        put("vet'ion", new Item[][] {
                { new Item(1620, 20), new Item(1618, 10) },    // Uncut ruby and uncut diamond
                { new Item(11936, 8), new Item(3024, 3) }    // Dark crab and super restore (4)
        });
    }};

    private List<Item> getGroupDrop(Item item) {
        List<Item> finalList = new ArrayList<Item>();
        Item[][] groups = groupedDrops.get(this.npc.getDef().name.toLowerCase());
        if (groups == null) {
            return finalList;
        }
        for (Item[] group : groups) {
            if (item.getId() == group[0].getId()) {
                Item[] tempArray = new Item[group.length - 1];
                System.arraycopy(group, 1, tempArray, 0, group.length - 1);
                finalList.addAll(Arrays.asList(tempArray));
            }
        }
        return finalList;
    }

    private static final int[] brimstoneRingParts = { 22973, 22971, 22969 };

    /*
     * Drops the next part that the player needs to make the brimstone ring.
     */
    private int generateBrimstoneRingPart(Player player) {
        int eyes = player.getItemAmount(22973);
        int fangs = player.getItemAmount(22971);
        int hearts = player.getItemAmount(22969);
        if (fangs < eyes && fangs < hearts) {
            return 22971;
        } else if (hearts < eyes && hearts < fangs) {
            return 22969;
        } else {
            return 22973;
        }
    }

    private void handleGrubbyDrops(Killer killer, Player player, Position pos) {
        if (Bounds.fromRegion(7323).inBounds(pos)) {
            String name = npc.getDef().name;
            if (name.equalsIgnoreCase("red dragon") && Random.rollDie(50)) {
                handleDrop(killer, pos, player, Collections.singletonList(new Item(23499)));
            } else if (name.equalsIgnoreCase("baby red dragon") && Random.rollDie(80)) {
                handleDrop(killer, pos, player, Collections.singletonList(new Item(23499)));
            }
        }
    }

    private void handleSuperiorDrops(Killer killer, Player player, Position pos) {
        if (!npc.isSuperior)
            return;
        double multiplier = 200 - (Math.pow(npc.getCombat().getInfo().slayer_level + 55, 2)/125);
        int rareChance = (int) (8 * multiplier);
        int staffChance = (int) (8 * multiplier);
        if (Random.rollDie(rareChance)) {
            handleDrop(killer, pos, player, Collections.singletonList(new Item(21270)));    // Eternal Gem
        }
        if (Random.rollDie(rareChance)) {
            handleDrop(killer, pos, player, Collections.singletonList(new Item(20724)));    // Imbued Heart
        }
        if (Random.rollDie(staffChance)) {
            handleDrop(killer, pos, player, Collections.singletonList(new Item(20730)));    // Mist battlestaff
        }
        if (Random.rollDie(staffChance)) {
            handleDrop(killer, pos, player, Collections.singletonList(new Item(20736)));    // Dust battlestaff
        }
        player.getTaskManager().doLookupByCategory(TaskCategory.SUPERIORKILL, 1, true);
    }

    /*
     * Chaos elemental has a loot table of minor drops that drop alongside the main drops.
     */
    private static final LootTable chaosElementalMinorDrops = new LootTable()
            .addTable(1,
                    new LootItem(Items.ANCHOVY_PIZZA, 3, 3, 1),
                    new LootItem(Items.BABYDRAGON_BONES, 2, 2, 1),
                    new LootItem(Items.BAT_BONES, 5, 5, 1),
                    new LootItem(Items.BIG_BONES, 3, 3, 1),
                    new LootItem(Items.BONES, 4, 4, 1),
                    new LootItem(Items.DRAGON_BONES, 1, 1, 1),
                    new LootItem(Items.SUPER_ATTACK_4, 1, 1, 1),
                    new LootItem(Items.SUPER_DEFENCE_4, 1, 1, 1),
                    new LootItem(Items.SUPER_STRENGTH_4, 1, 1, 1),
                    new LootItem(Items.TUNA, 5, 5, 1)
            );

    /**
     * Handles the rolling of the minor drop table alongside the major drop table.
     * @param killer The entity that killed the npc.
     * @param player The player that killed the npc.
     * @param pos The position to place the drop.
     */
    private void handleChaosEleDrops(Killer killer, Player player, Position pos) {
        if (npc.getId() == 2054 || npc.getId() == 6505) {
            List<Item> items = chaosElementalMinorDrops.rollItems(false);
            handleDrop(killer, pos, player, items);
        }
    }

    /*
     * Kalphite queen has a loot table of consumables that drop alongside the main drops.
     */
    private static final LootTable kalphiteQueenConsumablesTable = new LootTable()
            .addTable(1,
                    new LootItem(Items.MONKFISH, 3, 3, 1),
                    new LootItem(Items.SHARK, 2, 2, 1),
                    new LootItem(Items.DARK_CRAB, 2, 2, 1),
                    new LootItem(Items.SARADOMIN_BREW_4, 1, 1, 1),
                    new LootItem(Items.PRAYER_POTION_4, 1, 1, 1),
                    new LootItem(Items.SUPER_RESTORE_4, 1, 1, 1),
                    new LootItem(Items.SUPER_COMBAT_POTION_2, 1, 1, 1),
                    new LootItem(Items.RANGING_POTION_3, 1, 1, 1),
                    new LootItem(Items.SUPERANTIPOISON_2, 1, 1, 1)
            );

    /**
     * Handles the rolling of the consumable table alongside the major drop table.
     * @param killer The entity that killed the npc.
     * @param player The player that killed the npc.
     * @param pos The position to place the drop.
     */
    private void handleKalphiteQueenDrops(Killer killer, Player player, Position pos) {
        if (npc.getId() == 963 || npc.getId() == 965) {
            List<Item> items = kalphiteQueenConsumablesTable.rollItems(false);
            handleDrop(killer, pos, player, items);
        }
    }

    private void handCrystalShard(Killer killer, Player player, Position pos) {
        if (!MapArea.IORWERTH_DUNGEON.inArea(pos)) return;
        if (Arrays.asList(410, 411, 2916, 7276, 4005, 7278).contains(npc.getId()) && Random.rollDie(24, 1)) {
            handleDrop(killer, pos, player, new Item(23962));
        }
    }
}
