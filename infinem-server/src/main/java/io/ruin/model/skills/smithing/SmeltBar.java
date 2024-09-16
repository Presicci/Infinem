package io.ruin.model.skills.smithing;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemID;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.chargable.CelestialRing;
import io.ruin.model.item.actions.impl.skillcapes.MiningSkillCape;
import io.ruin.model.item.actions.impl.skillcapes.SmithingSkillCape;
import io.ruin.model.item.actions.impl.storage.CoalBag;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.skills.crafting.SilverCasting;
import io.ruin.model.skills.mining.Rock;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.List;

public class SmeltBar {

    private static final String[] FURNACE_NAMES = { "furnace", "small furnace", "lava forge", "clay forge", "lovakite furnace" };
    private static final int[] MOULDS = { Items.AMULET_MOULD, Items.NECKLACE_MOULD, Items.BRACELET_MOULD, Items.RING_MOULD };
    private static final int[] SILVER_ONLY_MOULDS = { Items.SICKLE_MOULD, Items.TIARA_MOULD, Items.BOLT_MOULD, Items.UNHOLY_MOULD, Items.HOLY_MOULD };
    private static final int[] SILVER_GEMS = { Items.OPAL, Items.JADE, Items.RED_TOPAZ };

    static {
        for (String name : FURNACE_NAMES) {
            ObjectAction.register(name, "smelt", (player, obj) -> open(player));
            ItemObjectAction.register(Items.SILVER_BAR, name, (player, item, object) -> SilverCasting.open(player));
            ItemObjectAction.register(ItemID.AMMO_MOULD, name, (player, item, object) -> makeCannonballs(player));
            ItemObjectAction.register(ItemID.STEEL_BAR, name, (player, item, object) -> makeCannonballs(player));
            for (SmithBar smithBar : SmithBar.values()) {
                for (Item item : smithBar.smeltItems) {
                    if (smithBar == SmithBar.STEEL || smithBar == SmithBar.IRON || smithBar == SmithBar.ELEMENTAL || smithBar == SmithBar.MIND) {
                        ItemObjectAction.register(item.getId(), name, (player, item1, obj) -> open(player));
                    } else {
                        ItemObjectAction.register(item.getId(), name, (player, item1, obj) -> smelt(player, smithBar, 1));
                    }
                }
            }
            for (int id : MOULDS) {
                ItemObjectAction.register(id, name, (player, item, object) -> openFromMould(player));
            }
            for (int id : SILVER_ONLY_MOULDS) {
                ItemObjectAction.register(id, name, (player, item, object) -> SilverCasting.open(player));
            }
        }
    }

    private static void openFromMould(Player player) {
        if (player.getInventory().contains(Items.SILVER_BAR)) {
            for (int id : SILVER_GEMS) {
                if (player.getInventory().contains(id)) {
                    SilverCasting.open(player);
                }
            }
        }
        player.openInterface(InterfaceType.MAIN, Interface.MOULD);
    }

    private static void open(Player player) {
        // Open mould interface if player has a mould and a gold bar or silver casting interface if player has silver bar, gem, and mould
        for (int id : MOULDS) {
            if (player.getInventory().contains(id) && player.getInventory().contains(Items.GOLD_BAR)) {
                player.openInterface(InterfaceType.MAIN, Interface.MOULD);
                return;
            }
            if (player.getInventory().contains(id) && player.getInventory().contains(Items.SILVER_BAR)) {
                for (int gemId : SILVER_GEMS) {
                    if (player.getInventory().contains(gemId)) {
                        SilverCasting.open(player);
                        return;
                    }
                }
            }
        }
        // Open silver casting interface if player has mould and silver bar
        for (int id : SILVER_ONLY_MOULDS) {
            if (player.getInventory().contains(id) && player.getInventory().contains(Items.SILVER_BAR)) {
                SilverCasting.open(player);
                return;
            }
        }
        // Open cannonball skill menu if player has ammo mould and steel bar
        if (player.getInventory().contains(Items.AMMO_MOULD) && player.getInventory().contains(Items.STEEL_BAR)) {
            makeCannonballs(player);
            return;
        }
        // Populate skill items list if player has ore to make bar
        List<SkillItem> items = new ArrayList<>();
        out: for (SmithBar bar : SmithBar.values()) {
            for (Item item : bar.smeltItems) {
                if (!player.getInventory().contains(item))
                    continue out;
            }
            items.add(new SkillItem(bar.itemId));
        }
        if (items.size() > 0) {
            SkillDialogue.make(player,
                    (p, item) -> smelt(player, item.getDef().smithBar, item.getAmount()),
                    items.toArray(new SkillItem[0]));
        } else {
            player.dialogue(new MessageDialogue("You don't have any ores you can smelt."));
        }
    }

    public static void smelt(Player player, SmithBar bar, int smeltAmount) {
        player.closeInterface(InterfaceType.CHATBOX);
        if (!player.getStats().check(StatType.Smithing, bar.smeltLevel, "smelt that bar"))
            return;
        RandomEvent.attemptTrigger(player);
        boolean useCoalBag = player.getInventory().hasId(CoalBag.COAL_BAG) || player.getInventory().hasId(CoalBag.OPEN_COAL_BAG);
        player.startEvent(event -> {
            int remaining = smeltAmount;
            while (remaining-- > 0) {
                int baggedCoalUsed = 0;
                for (Item item : bar.smeltItems) {
                    int id = item.getId();
                    int amount = item.getAmount();
                    if (id == CoalBag.COAL && useCoalBag) {
                        int baggedCoalRemaining = player.getAttributeIntOrZero("BAGGED_COAL");
                        if (baggedCoalRemaining >= amount) {
                            baggedCoalUsed += amount;
                            continue;
                        }
                        baggedCoalUsed = baggedCoalRemaining;
                    }
                    if (!player.getInventory().contains(id, amount)) {
                        if (remaining == (smeltAmount - 1))
                            player.sendMessage("You don't have enough ore to make this.");
                        else
                            player.sendMessage("You've ran out of ores to continue smelting.");
                        return;
                    }
                }
                player.animate(3243);
                player.privateSound(2725);
                for (Item item : bar.smeltItems) {
                    int id = item.getId();
                    int amount = item.getAmount();
                    if (id == CoalBag.COAL && baggedCoalUsed > 0) {
                        player.incrementNumericAttribute("BAGGED_COAL", -baggedCoalUsed);
                        amount -= baggedCoalUsed;
                        if (amount <= 0) {
                            /* all required coal came from bag */
                            continue;
                        }
                    }
                    player.getInventory().remove(id, amount);
                }
                if (bar.counter != null) bar.counter.increment(player, 1);
                player.getInventory().add(bar.itemId, 1);
                RingOfForging.onSmelt(player, bar);
                double xp = bar.smeltXp;
                if (bar == SmithBar.GOLD && (player.getEquipment().hasId(776) || SmithingSkillCape.wearingSmithingCape(player))) // goldsmith gauntlets
                    xp = 56.2;
                player.getStats().addXp(StatType.Smithing, xp, true);
                player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.SMELT_BAR, ItemDefinition.get(bar.itemId).name);
                if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER) && !Random.rollPercent(getExtraBarChance(player, bar))) {
                    event.delay(2);
                }
            }
        });
    }

    private static int getExtraBarChance(Player player, SmithBar bar) {
        int chance = 0;
        Item chest = player.getEquipment().get(Equipment.SLOT_CHEST);
        int chestId = chest != null ? chest.getId() : 0;
        if (bar.ordinal() <= SmithBar.STEEL.ordinal()) {
            if (chestId == Items.VARROCK_ARMOUR_1)
                chance += 10;
        }
        if (bar.ordinal() <= SmithBar.MITHRIL.ordinal()) {
            if (chestId == Items.VARROCK_ARMOUR_2)
                chance += 10;
        }
        if (bar.ordinal() <= SmithBar.ADAMANT.ordinal()) {
            if (chestId == Items.VARROCK_ARMOUR_3)
                chance += 10;
        }
        if (chestId == Items.VARROCK_ARMOUR_4)
            chance += 10;
        return chance;
    }

    private static void makeCannonballs(Player player) {
        SkillDialogue.make(player, new SkillItem(ItemID.CANNONBALL).addAction((p, amount, event) -> {
            if (player.getStats().get(StatType.Smithing).currentLevel < 35) {
                player.dialogue(new MessageDialogue("You need a Smithing level of at least 35 to smith cannonballs."));
                return;
            }
            if (!player.getInventory().hasId(ItemID.STEEL_BAR)) {
                player.sendMessage("You need a steel bar to make cannonballs.");
                return;
            }
            if (!player.getInventory().hasId(ItemID.AMMO_MOULD)) {
                player.sendMessage("You need an ammo mould to make cannonballs.");
                return;
            }
            RandomEvent.attemptTrigger(player);
            p.startEvent(e -> {
                int amt = amount;
                while (amt-- > 0) {
                    if (!player.getInventory().hasId(ItemID.STEEL_BAR)) {
                        player.sendFilteredMessage("You've run out of steel bars'.");
                        return;
                    }
                    player.animate(3243);
                    player.privateSound(2725);
                    player.sendFilteredMessage("You heat the steel bar into a liquid state.");
                    e.delay(1);
                    player.sendFilteredMessage("You pour the molten metal into your cannonball mould.");
                    e.delay(1);
                    player.sendFilteredMessage("The molten metal cools slowly to form 4 cannonballs.");
                    e.delay(2);
                    if (player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                        ++amt;
                        int bars = player.getInventory().getAmount(ItemID.STEEL_BAR);
                        if (bars < amt)
                            amt = bars;
                        player.getInventory().remove(ItemID.STEEL_BAR, amt);
                        player.getInventory().add(ItemID.CANNONBALL, 4 * amt);
                        player.getStats().addXp(StatType.Smithing, (6.4 * 4) * amt, true);
                        amt = 0;
                    } else {
                        player.getInventory().remove(ItemID.STEEL_BAR, 1);
                        player.getInventory().add(ItemID.CANNONBALL, 4);
                        player.getStats().addXp(StatType.Smithing, 6.4 * 4, true);
                    }
                    player.sendFilteredMessage("You remove the cannonballs from the mould.");
                }
            });
        }));
    }

}
