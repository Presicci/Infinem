package io.ruin.model.skills.smithing.blastfurnace;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Region;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/13/2024
 */
public class BlastFurnace {

    protected static Config DISPENSER = Config.varpbit(936, true);

    protected static int getPrimaryOreAmount(Player player) {
        int amt = 0;
        for (BlastFurnaceOre ore : BlastFurnaceOre.values()) {
            if (ore == BlastFurnaceOre.COAL || ore == BlastFurnaceOre.TIN) continue;
            amt += getOre(player, ore);
        }
        return amt;
    }

    public static int getTotalBars(Player player) {
        int amount = 0;
        for (SmithBar bar : SmithBar.values()) {
            amount += getBar(player, bar);
        }
        return amount;
    }

    protected static void addOre(Player player, BlastFurnaceOre ore, int amt) {
        player.incrementNumericAttribute("BF_" + ore.name(), amt);
    }

    protected static void removeOre(Player player, BlastFurnaceOre ore, int amt) {
        int newAmt = player.incrementNumericAttribute("BF_" + ore.name(), -amt);
        if (newAmt <= 0) player.removeAttribute("BF_" + ore.name());
    }

    protected static int getOre(Player player, BlastFurnaceOre ore) {
        return player.getAttributeIntOrZero("BF_" + ore.name());
    }

    protected static void addBar(Player player, SmithBar bar, int amt) {
        player.incrementNumericAttribute("BFBAR_" + bar.name(), amt);
    }

    protected static void removeBar(Player player, SmithBar bar, int amt) {
        int newAmt = player.incrementNumericAttribute("BFBAR_" + bar.name(), -amt);
        if (newAmt <= 0) player.removeAttribute("BFBAR_" + bar.name());
    }

    protected static int getBar(Player player, SmithBar bar) {
        return player.getAttributeIntOrZero("BFBAR_" + bar.name());
    }

    protected static boolean hasBars(Player player) {
        for (SmithBar bar : SmithBar.values()) {
            if (getBar(player, bar) > 0) return true;
        }
        return false;
    }

    protected static Item[] constructBarArray(Player player) {
        List<Item> list = new ArrayList<>();
        for (SmithBar bar : SmithBar.values()) {
            int amt = getBar(player, bar);
            if (amt > 0) {
                list.add(new Item(bar.itemId, amt));
            }
        }
        return list.toArray(new Item[0]);
    }

    protected static final List<SmithBar> PROCESSED_BARS = Arrays.asList(
            SmithBar.BRONZE, SmithBar.STEEL, SmithBar.IRON, SmithBar.SILVER, SmithBar.GOLD, SmithBar.MITHRIL, SmithBar.ADAMANT, SmithBar.RUNITE
    );

    protected static void processBars(Player player) {
        boolean barsMade = false;
        barLoop : for(SmithBar bar : PROCESSED_BARS) {
            if (getBar(player, bar) == 28) {
                continue;
            }
            for (Item oreItem : bar.smeltItems) {
                BlastFurnaceOre ore = BlastFurnaceOre.getOreById(oreItem.getId());
                if (ore.equals(BlastFurnaceOre.COAL) && getOre(player, ore) < (oreItem.getAmount() / 2)) {
                    continue barLoop;
                }

                if (!ore.equals(BlastFurnaceOre.COAL) && getOre(player, ore) < oreItem.getAmount()) {
                    continue barLoop;
                }
            }
            int cost = player.getTaskManager().hasCompletedTask(524) ? 75 : 100;    // Smelt 5,000 Bars at the Blast Furnace
            if (bar.smeltItems.size() > 1) {
                Item primary = bar.smeltItems.get(0);
                Item secondary = bar.smeltItems.get(1);
                BlastFurnaceOre primaryOre = BlastFurnaceOre.getOreById(primary.getId());
                BlastFurnaceOre secondaryOre = BlastFurnaceOre.getOreById(secondary.getId());
                int secondaryAmount = secondaryOre.equals(BlastFurnaceOre.COAL) ? getOre(player, secondaryOre) / (secondary.getAmount() / 2) : getOre(player, secondaryOre);
                int amount = Math.min(getOre(player, primaryOre), secondaryAmount);
                amount = (getBar(player, bar) + amount) > 28 ? 28 - getBar(player, bar) : amount;
                amount = Math.min(BlastFurnaceCoffer.getAmount(player) / cost, amount);
                removeOre(player, primaryOre, amount);
                if (bar.equals(SmithBar.BRONZE)) {
                    removeOre(player, secondaryOre, amount);
                } else {
                    removeOre(player, secondaryOre, amount * (secondary.getAmount() / 2));
                }
                addBar(player, bar, amount);
                player.getStats().addXp(StatType.Smithing, (bar.smeltXp * amount), true);
                BlastFurnaceCoffer.decrementCofferAmount(player, amount * cost);
            } else {
                Item ore = bar.smeltItems.get(0);
                BlastFurnaceOre oreEnum = BlastFurnaceOre.getOreById(ore.getId());
                int amount = getOre(player, oreEnum);
                amount = (getBar(player, bar) + amount) > 28 ? 28 - getBar(player, bar) : amount;
                amount = Math.min(BlastFurnaceCoffer.getAmount(player) / cost, amount);
                addBar(player, bar, amount);
                removeOre(player, oreEnum, amount);
                boolean hasGoldsmithGloves = player.getEquipment().hasId(Items.GOLDSMITH_GAUNTLETS);
                if (oreEnum.equals(BlastFurnaceOre.GOLD) && hasGoldsmithGloves) {
                    player.getStats().addXp(StatType.Smithing, (bar.smeltXp + 33.7) * amount, true);
                } else {
                    player.getStats().addXp(StatType.Smithing, bar.smeltXp * amount, true);
                }
                BlastFurnaceCoffer.decrementCofferAmount(player, amount * cost);
            }
            barsMade = true;
        }
        if (barsMade) {
            processBarDispenser(player);
        }
    }

    private static void processBarDispenser(Player player) {
        // setEarlyCool(false);
        World.startEvent(e -> {
            int ticks = 0;
            while (true) {
                if (!player.isOnline()) break;
                if (player.hasTemporaryAttribute("BF_EARLY_COOL") || getTotalBars(player) == 0) {
                    player.removeTemporaryAttribute("BF_EARLY_COOL");
                    break;
                }
                if (ticks == 0) {
                    DISPENSER.set(player, 1);
                    player.getPacketSender().sendObjectAnimation(1940, 4963, 0, 10, 0, 2440);
                } else if (ticks == 2) {
                    DISPENSER.set(player, 2);
                } else if (ticks == 18) {
                    DISPENSER.set(player, 3);
                    break;
                }
                ticks++;
                e.delay(1);
            }
        });
    }

    private static void entered(Player player) {
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, 474);
        player.getPacketSender().sendObjectAnimation(1943, 4967, 0, 10, 0, 2435);   // Conveyer main
        player.getPacketSender().sendObjectAnimation(1943, 4966, 0, 10, 0, 2435);   // Conveyer 2
        player.getPacketSender().sendObjectAnimation(1943, 4965, 0, 10, 0, 2435);   // Conveyer 3
        player.getPacketSender().sendObjectAnimation(1945, 4967, 0, 10, 0, 2436);   // Cogs north
        player.getPacketSender().sendObjectAnimation(1945, 4965, 0, 10, 0, 2436);   // Cogs south
        player.getPacketSender().sendObjectAnimation(1944, 4967, 0, 10, 0, 2436);   // Drive belt north
        player.getPacketSender().sendObjectAnimation(1944, 4965, 0, 10, 0, 2436);   // Drive belt south
        player.getPacketSender().sendObjectAnimation(1945, 4966, 0, 10, 0, 2436);   // Gear box

        int dispenser = DISPENSER.get(player);
        if (dispenser == 1 || dispenser == 2) {
            processBarDispenser(player);
        }
        DISPENSER.forceSend();
    }

    private static void exited(Player player, boolean logout) {
        if (logout) //no need to do anything
            return;
        if (player.isVisibleInterface(474))
            player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
    }

    static  {
        MapListener.registerRegion(7757)
                .onEnter(BlastFurnace::entered)
                .onExit(BlastFurnace::exited);
        // Object animations
        World.startEvent(e -> {
            e.delay(15);
            Tile.get(1943, 4967, 0).getObject(9100, 10, 0).animate(2435);   // Conveyer main
            Tile.get(1943, 4966, 0).getObject(9101, 10, 0).animate(2435);   // Conveyer 2
            Tile.get(1943, 4965, 0).getObject(9101, 10, 0).animate(2435);   // Conveyer 3
            Tile.get(1945, 4967, 0).getObject(9104, 10, 0).animate(2436);   // Cogs north
            Tile.get(1945, 4965, 0).getObject(9108, 10, 0).animate(2436);   // Cogs south
            Tile.get(1944, 4967, 0).getObject(9102, 10, 0).animate(2436);   // Drive belt north
            Tile.get(1944, 4965, 0).getObject(9107, 10, 0).animate(2436);   // Drive belt south
            Tile.get(1945, 4966, 0).getObject(9106, 10, 0).animate(2436);   // Gear box
        });
        // Temp gauge
        ObjectAction.register(9089, "read", (player, obj) -> player.openInterface(InterfaceType.MAIN, 30));
    }
}
