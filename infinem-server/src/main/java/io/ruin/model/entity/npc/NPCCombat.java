package io.ruin.model.entity.npc;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.experienced.DemonSlayer;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.combat.*;
import io.ruin.model.content.PvmPoints;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.DoubleDrops;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.WildernessKey;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfEthereum;
import io.ruin.model.item.actions.impl.jewellery.RingOfWealth;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Graphic;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.actions.impl.dungeons.KourendCatacombs;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.services.discord.impl.RareDropEmbedMessage;
import io.ruin.utility.Broadcast;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.ItemID.VORKATHS_HEAD;

public abstract class NPCCombat extends Combat {

    protected NPC npc;

    protected npc_combat.Info info;

    protected Stat[] stats;

    private int[] bonuses;

    private boolean allowRespawn = true;

    @Setter @Getter
    private boolean allowRetaliate = true;

    public final NPCCombat init(NPC npc, npc_combat.Info info) {
        this.npc = npc;
        this.info = info;
        this.stats = new Stat[] {
                new Stat(info.attack),      //0
                new Stat(info.defence),     //1
                new Stat(info.strength),    //2
                new Stat(info.hitpoints),   //3
                new Stat(info.ranged),      //4
                new Stat(1),           //5 (prayer, not used)
                new Stat(info.magic)        //6
        };
        this.bonuses = new int[] {
                /*
                 * Attack bonuses
                */
                info.stab_attack,
                info.slash_attack,
                info.crush_attack,
                info.magic_attack,
                info.ranged_attack,
                /*
                 * Defence bonuses
                 */
                info.stab_defence,
                info.slash_defence,
                info.crush_defence,
                info.magic_defence,
                info.ranged_defence,
        };
        init();
        return this;
    }

    public void updateInfo(npc_combat.Info newInfo) { // only used when transforming!
        info = newInfo;
        if (stats[0].fixedLevel != newInfo.attack) stats[0] = new Stat(newInfo.attack);
        if (stats[1].fixedLevel != newInfo.defence) stats[1] = new Stat(newInfo.defence);
        if (stats[2].fixedLevel != newInfo.strength) stats[2] = new Stat(newInfo.strength);
        if (stats[3].fixedLevel != newInfo.hitpoints) stats[3] = new Stat(newInfo.hitpoints);
        if (stats[4].fixedLevel != newInfo.ranged) stats[4] = new Stat(newInfo.ranged);
        if (stats[6].fixedLevel != newInfo.magic) stats[6] = new Stat(newInfo.magic);

        this.bonuses = new int[] { // bonuses cannot be changed so we can just set to the new ones
                /*
                 * Attack bonuses
                */
                info.stab_attack,
                info.slash_attack,
                info.crush_attack,
                info.magic_attack,
                info.ranged_attack,
                /*
                 * Defence bonuses
                */
                info.stab_defence,
                info.slash_defence,
                info.crush_defence,
                info.magic_defence,
                info.ranged_defence,
        };
    }

    /**
     * Following
     */
    public final void follow0() {
        checkAggression();
        if(target == null || npc.isLocked()) //why can an npc be locked but still have a target.. hmm..
            return;
        if(!canAttack(target)) {
            reset();
            return;
        }
        follow();
    }

    protected void follow(int distance) {
        DumbRoute.step(npc, target, distance);
    }

    protected boolean withinDistance(int distance) {
        return DumbRoute.withinDistance(npc, target, distance);
    }

    /**
     * Attacking
     */
    public final void attack0() {
        if(target == null || hasAttackDelay() || npc.isLocked() || !attack())
            return;
        updateLastAttack(info.attack_ticks);
    }

    public boolean canAttack(Entity target) {
        if(isDead() || npc.isRemoved())
            return false;
        if(target == null || target.isHidden())
            return false;
        if(target.getCombat() == null)
            return false;
        if(target.getCombat().isDead())
            return false;
        if (!multiCheck(target))
            return false;
        if(npc.targetPlayer == null) {
            if(!npc.getPosition().isWithinDistance(target.getPosition()))
                return false;
            Bounds attackBounds = npc.attackBounds;
            if(attackBounds != null && !npc.getPosition().inBounds(attackBounds)) {
                DumbRoute.route(npc, npc.spawnPosition.getX(), npc.spawnPosition.getY());
                //possibly consider resetting the monster to prevent abusing this mechanic
                return false;
            }
        }
        return true;
    }

    public boolean multiCheck(Entity target) {
        return npc.inMulti() || target.getCombat().allowPj(npc);
    }

    protected Hit basicAttack() {
        return basicAttack(info.attack_animation, info.attack_style, info.max_damage);
    }

    protected Hit basicAttack(int animation, AttackStyle attackStyle, int maxDamage) {
        npc.animate(animation);
        Hit hit = new Hit(npc, attackStyle, null).randDamage(maxDamage);
        target.hit(hit);
        return hit;
    }

    protected Hit projectileAttack(Projectile projectile, int animation, AttackStyle attackStyle, int maxDamage) {
        return projectileAttack(projectile, animation, Graphic.builder().id(-1).build(), attackStyle, maxDamage);
    }

    protected Hit projectileAttack(Projectile projectile, int animation, Graphic hitgfx, AttackStyle attackStyle, int maxDamage) {
        return projectileAttack(projectile, animation, hitgfx, Graphic.builder().id(-1).build(), attackStyle, maxDamage, false);
    }

    protected Hit projectileAttack(Projectile projectile, int animation, Graphic hitgfx, Graphic splashgfx, AttackStyle attackStyle, int maxDamage, boolean ignorePrayer) {
        if (animation != -1)
            npc.animate(animation);
        int delay = projectile.send(npc, target);
        Hit hit = new Hit(npc, attackStyle, null).randDamage(maxDamage).clientDelay(delay);
        hit.afterPostDamage(e -> {
            boolean splash = hit.isBlocked();
            if (target != null) {
                target.graphics(
                        splash ? splashgfx.getId() : hitgfx.getId(),
                        splash ? splashgfx.getHeight() : hitgfx.getHeight(),
                        splash ? splashgfx.getDelay() : hitgfx.getDelay()
                );
                if (splash ? splashgfx.getSoundId() != -1 : hitgfx.getSoundId() != -1) {
                    target.publicSound(
                            splash ? splashgfx.getSoundId() : hitgfx.getSoundId(),
                            splash ? splashgfx.getSoundType() : hitgfx.getSoundType(),
                            splash ? splashgfx.getSoundDelay() : hitgfx.getSoundDelay()
                    );
                }
            }
        });

        target.hit(hit);
        return hit;
    }

    /**
     * Reset
     */
    @Override
    public void reset() {
        super.reset();
        npc.faceNone(!isDead());
        TargetRoute.reset(npc);
    }

    /**
     * Death
     */
    @Override
    public void startDeath(Hit killHit) {
        setDead(true);

        if(target != null)
            reset();
        Killer killer = getKiller();
        if(npc.deathStartListener != null) {
            npc.deathStartListener.handle(npc, killer, killHit);
            if(npc.isRemoved())
                return;
        }
        npc.startEvent(event -> {
            npc.lock();
            event.delay(1);
            if(info.death_animation != -1)
                npc.animate(info.death_animation);
            if(info.death_ticks > 0)
                event.delay(info.death_ticks);
            dropItems(killer);

            if (killer != null && killer.player != null) {
                killer.player.getTaskManager().doKillLookup(npc.getId());
                Slayer.handleNPCKilled(killer.player, npc);
                if (npc.getDef().killCounter != null)
                    npc.getDef().killCounter.apply(killer.player).increment(killer.player);
                if(info.pet != null) {
                    int dropAverage = info.pet.dropAverage;
                    if (npc.getId() == 6619) {  // Manual override for chaos fanatic sharing boss pet w/ chaos ele
                        dropAverage = 1000;
                    }
                    if (Random.rollDie(dropAverage)) {
                        info.pet.unlock(killer.player);
                    }
                }
                DailyTask.checkNPCKill(killer.player, npc);
                DemonSlayer.check(killer.player, npc);
            }

            if(npc.deathEndListener != null) {
                npc.deathEndListener.handle(npc, killer, killHit);
                if(npc.isRemoved())
                    return;
            } else if(info.respawn_ticks < 0) {
                npc.remove();
                return;
            }

            if (info.respawn_ticks > 0)
                npc.setHidden(true);
            if (!allowRespawn())
                return;

            event.delay(info.respawn_ticks);
            respawn();
        });
    }

    public final void respawn() {
        npc.animate(info.spawn_animation);
        npc.getPosition().copy(npc.spawnPosition);
        resetKillers();
        restore();
        setDead(false);
        npc.setHidden(false);
        if(npc.respawnListener != null)
            npc.respawnListener.onRespawn(npc);
        npc.unlock();
    }

    public void setAllowRespawn(boolean allowRespawn) {
        this.allowRespawn = allowRespawn;
    }

    public boolean allowRespawn() {
        return allowRespawn;
    }

    private final HashMap<String, Item[][]> groupedDrops = new HashMap<String, Item[][]>() {{
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
        if (player.findItem(22973) != null) {   // Hydra's Eye
            if (player.findItem(22971) != null) {   // Hydra's Fang
                if (player.findItem(22969) != null) {   // Hydra's Heart
                    return brimstoneRingParts[Random.get(0, 2)];   // Player has all parts, so drop the first again
                } else {
                    return 22969;   // Player has first 2 parts, drop last
                }
            } else {
                return 22971;   // Player has first part, drop second
            }
        } else {
            return 22973;   // Player has no parts, drop first
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

    public void dropItems(Killer killer) {
        NPCDef def = npc.getDef();
        Position dropPosition = getDropPosition();
        Player pKiller = killer == null ? null : killer.player;
        if (pKiller == null) {
            return;
        }

        /*
         * Drop table loots
         */
        LootTable t = def.lootTable;
        if(t != null) {
            int rolls = DoubleDrops.getRolls(killer.player);
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
                            switch (item.getAmount()) {
                                case 0:
                                    handleGeneralSeedDrop(killer, dropPosition, pKiller);
                                    break;
                                case 1:
                                    handleTableDrop(killer, dropPosition, pKiller, LootTable.CommonTables.HERB);
                                    break;
                                case 2:
                                    handleTableDrop(killer, dropPosition, pKiller, LootTable.CommonTables.UNCOMMON_SEED);
                                    break;
                                case 3:
                                    handleTableDrop(killer, dropPosition, pKiller, LootTable.CommonTables.RARE_SEED);
                                    break;
                                case 4:
                                    handleTableDrop(killer, dropPosition, pKiller, LootTable.CommonTables.TREE_HERB_SEED);
                                    break;
                                case 5:
                                    handleTableDrop(killer, dropPosition, pKiller, LootTable.CommonTables.USEFUL_HERB);
                                    break;
                                case 6:
                                    handleTableDrop(killer, dropPosition, pKiller, LootTable.CommonTables.ALLOTMENT_SEED);
                                    break;
                                case 7:
                                    handleTableDrop(killer, dropPosition, pKiller, LootTable.CommonTables.TALISMAN);
                                    break;
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
         * Handle the chaos elemental minor drops
         */
        handleChaosEleDrops(killer, pKiller, dropPosition);

        /*
         * Handle the kalphite queen consumable drops
         */
        handleKalphiteQueenDrops(killer, pKiller, dropPosition);

        /*
         * Handle superior slayer monster unique drops
         */
        handleSuperiorDrops(killer, pKiller, dropPosition);

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

    private void vorkathHead(Position dropPosition, Player pKiller) {
        if (pKiller.vorkathKills.getKills() >= 50 && !pKiller.obtained50KCVorkathHead) {
            Item item = new Item(VORKATHS_HEAD);
            GroundItem groundItem = new GroundItem(item).position(dropPosition);
            groundItem.spawn();
            pKiller.obtained50KCVorkathHead = true;
        }
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

    private void handleDrop(Killer killer, Position dropPosition, Player pKiller, List<Item> items) {
        for(Item item : items) {
            /*
             * Ethereum auto collect
             */
            if (item.getId() == 21820) {
                if (BraceletOfEthereum.handleEthereumDrop(pKiller, item)) {
                    continue;
                }
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

            /*
             * Local Broadcast!
             */
            if (info.local_loot) {
                getLocalAnnounce(pKiller, item);
            }

            pKiller.getCollectionLog().collect(item);

            /*
             * Spawn the item on the ground.
             */
            if (dropItem)
            new GroundItem(item).position(dropPosition).owner(pKiller).spawn();
        }
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

    public Position getDropPosition() {
        return npc.getPosition();
    }

    public void restore() {
        for(Stat stat : stats)
            stat.restore();
        npc.resetFreeze();
        npc.cureVenom(0, 0);
    }

    /**
     * Misc
     */
    @Override
    public boolean allowRetaliate(Entity attacker) {
        if(npc.targetPlayer != null && attacker != npc.targetPlayer) //npc has a designated target
            return false;
        if (npc.isLocked())
            return false;
        if (!allowRetaliate)
            return false;
        if(target != null && target.getCombat().getTarget() == npc) { //npc is being attacked by target
            boolean prioritizePlayer = target.npc != null && attacker.player != null; //target is an npc and attacker is a player
            if(!prioritizePlayer)
                return false;
        }
        return true;
    }

    @Override
    public AttackStyle getAttackStyle() {
        return info.attack_style;
    }

    @Override
    public AttackType getAttackType() {
        return null;
    }

    @Override
    public double getLevel(StatType statType) {
        int i = statType.ordinal();
        return i >= stats.length ? 0 : stats[i].currentLevel;
    }

    public Stat getStat(StatType statType) {
        return stats[statType.ordinal()];
    }

    @Override
    public double getBonus(int bonusType) {
        return bonusType >= bonuses.length ? 0 : bonuses[bonusType];
    }

    @Override
    public Killer getKiller() {
        if(npc.targetPlayer != null) {
            Killer killer = new Killer();
            killer.player = npc.targetPlayer;
            return killer;
        }
        //Player's didn't like this mechanic so we removed it.
       /* if (killers.entrySet().stream().filter(e -> e.getValue().player != null).anyMatch(e -> e.getValue().player.getGameMode().isIronMan()) // ironman did damage
                && killers.entrySet().stream().filter(e -> e.getValue().player != null).anyMatch(e -> !e.getValue().player.getGameMode().isIronMan())) { // but so did a non-ironman :(
            killers.entrySet().removeIf(e -> e.getValue().player != null && e.getValue().player.getGameMode().isIronMan()); // remove all iron men from potential killer list
        }*/
        return super.getKiller();
    }

    @Override
    public int getDefendAnimation() {
        return info.defend_animation;
    }

    public int getMaxDamage() {
        return info.max_damage;
    }

    public npc_combat.Info getInfo() {
        return info;
    }

    @Override
    public double getDragonfireResistance() {
        return 0;
    }

    public final void checkAggression() {
        if (target == null && isAggressive()) {
            target = findAggressionTarget();
            if (target != null)
                faceTarget();
        }
    }

    protected Entity findAggressionTarget() {
        if (npc.localPlayers().isEmpty())
            return null;
        if (npc.hasTarget())
            return null;
        List<Player> targets = npc.localPlayers().stream()
                .filter(this::canAggro)
                .collect(Collectors.toList()); // i don't mind if this is done in a different way as long as it picks a RANDOM target that passes the canAggro check
        if (targets.isEmpty())
            return null;
        return Random.get(targets);
    }

    protected int getAggressiveLevel() {
        if (npc.wildernessSpawnLevel > 0)
            return Integer.MAX_VALUE;
        else if (info.aggressive_level == -1)
            return npc.getDef().combatLevel * 2;
        else
            return info.aggressive_level;
    }

    public boolean isAggressive() {
        return getInfo().aggressive_level != 0;
    }

    protected boolean canAggro(Player player) {
        int aggroLevel = getAggressiveLevel();
        return player.getCombat().getLevel() <= aggroLevel // in level range
                && canAttack(player) // can attack
                && !player.isIdle // player isn't idle
                && (player.inMulti() || !player.getCombat().isDefending(12))
                && DumbRoute.withinDistance(npc, player, getAggressionRange()) // distance and line of sight
                && (npc.inMulti() || (StreamSupport.stream(npc.localNpcs().spliterator(), false)
                .noneMatch(n -> n.getCombat() != null && n.getCombat().getTarget() == player && !n.getCombat().isAttacking(10) && !n.getMovement().isAtDestination()))) // not 100% sure how i feel about this check, but it ensures multiple npcs don't try to go for the same player at the same time in a single-way zone since they wouldn't be able to attack upon reaching
                && (npc.aggressionImmunity == null || !npc.aggressionImmunity.test(player));

    }

    public int getAggressionRange() {
        return npc.wildernessSpawnLevel > 0 ? 2 : 4;
    }

    public int getAttackBoundsRange() {
        return 12;
    }

    @Override
    public void faceTarget() {
        npc.face(target);
    }

    /**
     * Handler functions
     */
    public abstract void init();

    public abstract void follow();

    public abstract boolean attack();

    public int getRandomDropCount() {
        return info.random_drop_count;
    }
}