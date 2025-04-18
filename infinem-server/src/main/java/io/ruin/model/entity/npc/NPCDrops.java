package io.ruin.model.entity.npc;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.activities.shadesofmortton.Coffin;
import io.ruin.model.activities.shadesofmortton.FuneralPyre;
import io.ruin.model.content.bestiary.BestiaryDef;
import io.ruin.model.content.bestiary.perks.impl.GoldPickupPerk;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.impl.storage.LootingBag;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.loot.ConditionalNPCLootTable;
import io.ruin.process.tickevent.TickEvent;
import io.ruin.process.tickevent.TickEventType;
import io.ruin.utility.Color;
import io.ruin.cache.Icon;
import io.ruin.cache.def.NPCDefinition;
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
        NPCDefinition def = npc.getDef();
        Position dropPosition = npcCombat.getDropPosition();
        Player pKiller = killer == null ? null : killer.player;
        if (pKiller == null) {
            return;
        }

        /*
         * RDT roll
         */
        rollRareDropTable(killer, pKiller, dropPosition);

        /*
         * Drop table loots
         */
        LootTable t = ConditionalNPCLootTable.testAndApplyAllModifications(pKiller, npc);
        if(t != null) {
            int rolls = DoubleDrops.getRolls(killer.player, npc);
            if (npc.isSuperior) {
                rolls += 3; // Superiors drop minimum of 4 items
            }
            if (npc.getId() == 8059 || npc.getId() == 8061) {
                rolls += 1;
            }
            if (BestiaryDef.isBoss(npc.getDef().bestiaryEntry)) {
                rolls += 1;
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
                                item.setId(part);
                            }
                            break;
                        }
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
         * Handle the chaos elemental minor drops
         */
        handleChaosEleDrops(killer, pKiller, dropPosition);

        /*
         * Handle the kalphite queen consumable drops
         */
        handleKalphiteQueenDrops(killer, pKiller, dropPosition);

        handleLootingBagDrop(killer, pKiller, dropPosition);

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

        handleEcumenicalKeyDrop(killer, pKiller, dropPosition);

        /*
         * Gives players PVM Points
         */
        //PvmPoints.addPoints(pKiller, npc);
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

        handleRelicResetTabletDrop(killer, pKiller, dropPosition);

        // Christmas event
        rollGiftDrop(killer, pKiller, dropPosition);
    }

    private void handleDrop(Killer killer, Position dropPosition, Player pKiller, Item item) {
        List<Item> items = new ArrayList<>();
        items.add(item);
        handleDrop(killer, dropPosition, pKiller, items);
    }

    private void handleDrop(Killer killer, Position dropPosition, Player pKiller, List<Item> items) {
        for(Item item : items) {
            if (item.getId() <= 0) continue;
            Item ring = pKiller.getEquipment().get(Equipment.SLOT_RING);
            if (ring != null && ring.getId() == 32003 && Random.rollDie(1000)) {
                pKiller.sendMessage(Color.ORANGE_RED.wrap("The power of Hazelmere blesses your drop and doubles it before your very eyes: " + item.getDef().name) + "!");
                item.setAmount(item.getAmount() * 2);
            }
            if (RingOfWealth.wearingRingOfWealthImbued(pKiller) && pKiller.wildernessLevel > 0 && ClueType.isClueBox(item)) {
                item.setAmount(item.getAmount() * 2);
            }

            /*
             * Convert clue scrolls into clue boxes
             */
            if (item.getDef().clueType != null) {
                item.setId(item.getDef().clueType.boxId);
            }

            if (pKiller.getRelicManager().hasRelic(Relic.TREASURE_HUNTER) && Clue.SCROLL_BOXES.contains(item.getId())) {
                item.setAmount(item.getAmount() * 2);
            }

            // Coffins picking up shade remains
            if (FuneralPyre.REMAINS.contains(item.getId())) {
                Coffin coffin = Coffin.getCoffin(pKiller);
                if (coffin != null && coffin.addRemains(pKiller, item.getId())) continue;
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
                    && pKiller.getEquipment().contains(22557) && item.getDef().notedId > 0
                    && !item.getDef().name.toLowerCase().contains("blighted") && item.getId() != 22557
                    && item.getId() != 22547 && item.getId() != 22542 && item.getId() != 22552  // Doesn't note weapons/amulet
                    && !item.getDef().isNote()) {
                item.setId(item.getDef().notedId);
            }

            /*
             * Coin auto collect
             */
            if (item.getId() == COINS_995) {
                if (RingOfWealth.check(pKiller, item) || pKiller.getBestiary().getBestiaryEntry(npc.getDef()).getPerkMultiplier(GoldPickupPerk.class, 0) > 0) {
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
                getRareDropAnnounce(pKiller, item, npc);
            }

            double chance = pKiller.getBestiary().getBestiaryEntry(npc.getDef()).getPerkMultiplier(NotedDropPerk.class, 0);
            if (item.getDef().notedId > -1 && chance > 0 && !item.getDef().isNote() && Random.get() < chance) {
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

            // Attempt a task unlock for each item dropped
            pKiller.getTaskManager().doDropLookup(item);

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
        if (name.toLowerCase().contains("goat") && AreaReward.NOTED_GOAT_HORN.hasReward(pKiller) && item.getId() == Items.DESERT_GOAT_HORN)
            return true;
        if (name.toLowerCase().contains("aviansie") && AreaReward.AVIANSIE_NOTED_ADDY_BARS.hasReward(pKiller) && item.getId() == Items.ADAMANTITE_BAR)
            return true;
        if (AreaReward.NOTED_DAG_BONES.hasReward(pKiller) && item.getId() == Items.DAGANNOTH_BONES)
            return true;
        return false;
    }

    public static void getRareDropAnnounce(Player pKiller, Item item, NPC npc) {
        int amount = item.getAmount();
        String message = pKiller.getName() + " just received ";
        if(amount > 1)
            message += NumberUtils.formatNumber(amount) + " x " + item.getDef().name;
        else
            message += item.getDef().descriptiveName;
        Broadcast.DROP.sendNews(pKiller, Icon.INVENTORY_BAG, message + " from " + npc.getDef().descriptiveName + "!");
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

    private void handleLootingBagDrop(Killer killer, Player pKiller, Position dropPosition) {
        if (npc.wildernessSpawnLevel <= 0) return;
        int chance = Math.max(3, Math.min(15, 40 - npc.getDef().combatLevel));
        if (Random.rollDie(chance)) {
            if (!LootingBag.hasLootingBag(pKiller)) {
                handleDrop(killer, dropPosition, pKiller, new Item(Items.LOOTING_BAG));
            }
        }
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
        int least = Math.min(eyes, Math.min(fangs, hearts));
        if (eyes == least) {
            return 22973;
        } else if (fangs == least) {
            return 22971;
        } else {
            return 22969;
        }
    }

    private void handleEcumenicalKeyDrop(Killer killer, Player player, Position pos) {
        if (MapArea.WILDERNESS_GODWARS_DUNGEON.inArea(pos)) {
            if (Random.rollDie(60)) {
                handleDrop(killer, pos, player, Collections.singletonList(new Item(Items.ECUMENICAL_KEY)));
            }
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
        if (player.getRelicManager().hasRelic(Relic.MONSTER_HUNTER)) {
            int slayerLevel = npc.getCombat().getInfo().slayer_level;
            int points = Math.max(1, (int) Math.ceil(slayerLevel / 10D));
            Config.SLAYER_POINTS.increment(player, points);
            player.sendMessage("Slaying the superior has rewarded you with " + points + " slayer points.");
        }
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

    private void handleRelicResetTabletDrop(Killer killer, Player player, Position pos) {
        int combatLevel = npc.getDef().combatLevel;
        if (combatLevel < 50) return;
        int roll = Math.max(2000, 7500 - (combatLevel / 20) * 150);
        if (Random.rollDie(roll)) handleDrop(killer, pos, player, new Item(32041));
    }

    // Christmas event
    public void rollGiftDrop(Killer killer, Player player, Position pos) {
        if (player.isTickEventActive(TickEventType.GIFT_BAG)) return;
        int combatLevel = npc.getDef().combatLevel;
        int roll = Math.max(30, 50 - (combatLevel / 5));
        if (Random.rollDie(roll)) {
            player.addTickEvent(new TickEvent(TickEventType.GIFT_BAG, 300));
            handleDrop(killer, pos, player, new Item(32042));
        }
    }
}
