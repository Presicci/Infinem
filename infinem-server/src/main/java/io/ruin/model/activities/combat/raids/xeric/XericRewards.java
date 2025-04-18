package io.ruin.model.activities.combat.raids.xeric;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.killcount.KillCounterType;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.jewellery.XericsTalisman;
import io.ruin.model.item.pet.Pet;
import io.ruin.services.discord.impl.RareDropEmbedMessage;
import io.ruin.utility.Color;
import io.ruin.cache.Icon;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Unlock;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.services.Loggers;
import io.ruin.utility.Broadcast;

public class XericRewards {

    static {
        ObjectAction.register(30028, "search", (player, obj) -> { // reward chest
            openRewards(player);
        });
        InterfaceHandler.register(Interface.RAID_REWARDS, h -> {
            h.actions[5] = (DefaultAction) (p, option, slot, itemId) -> {
                if (slot < 0 || slot >= p.getRaidRewards().getItems().length)
                    return;
                if (option == 1)
                    withdrawReward(p, slot);
                else {
                    Item item = p.getRaidRewards().get(slot);
                    if (item != null)
                        item.examine(p);
                }
            };
        });
    }

    private static void withdrawReward(Player p, int slot) {
        if (slot < 0 || slot >= p.getRaidRewards().getItems().length)
            return;
        Item item = p.getRaidRewards().get(slot);
        if (item == null)
            return;
        if (item.move(item.getId(), item.getAmount(), p.getInventory()) > 0) {
            p.getCollectionLog().collect(item);
            p.getRaidRewards().sendUpdates();
            p.getTaskManager().doDropLookup(item);
        } else {
            p.sendMessage("Not enough space in your inventory.");
        }
    }

    private static void openRewards(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.RAID_REWARDS);
        player.getRaidRewards().sendUpdates();
        new Unlock(Interface.RAID_REWARDS, 5, 0, 2).unlockMultiple(player, 0, 9);
    }

    public static final LootTable UNIQUE_TABLE = new LootTable()
            .addTable(1,
                    new LootItem(21034, 1, 20), // dexterous scroll
                    new LootItem(21079, 1, 20), // arcane scroll
                    new LootItem(21000, 1, 4), // twisted buckler
                    new LootItem(21012, 1, 4), // dragon hunter crossbow
                    new LootItem(21015, 1, 3), // dinh's bulwark
                    new LootItem(21018, 1, 3), // ancestral hat
                    new LootItem(21021, 1, 3), // ancestral top
                    new LootItem(21024, 1, 3), // ancestral bottom
                    new LootItem(13652, 1,3), // dragon claws
                    new LootItem(21003, 1,2), // elder maul
                    new LootItem(21043, 1, 2), // kodai insignia
                    new LootItem(20997, 1, 2) // twisted bow
            );

    private static final LootTable REGULAR_TABLE = new LootTable() // regular table. the "amount" here is the number used to determine the amount given to players based on how many points they have, for example 1 soul rune per 20 points
            .addTable(1,
                    new LootItem(560, 40, 1), // death rune
                    new LootItem(565, 32, 1), // blood rune
                    new LootItem(566, 20, 1), // soul rune
                    new LootItem(892, 14, 1), // rune arrow
                    new LootItem(11212, 140, 1), // dragon arrow

                    new LootItem(3050, 370, 1), // grimy toadflax
                    new LootItem(208, 800, 1), // grimy ranarr weed
                    new LootItem(210, 196, 1), // grimy irit
                    new LootItem(212, 370, 1), // grimy avantoe
                    new LootItem(214, 405, 1), // grimy kwuarm
                    new LootItem(3052, 1000, 1), // grimy snapdragon
                    new LootItem(216, 400, 1), // grimy cadantine
                    new LootItem(2486, 293, 1), // grimy lantadyme
                    new LootItem(218, 212, 1), // grimy dwarf weed
                    new LootItem(220, 856, 1), // grimy torstol

                    new LootItem(443, 20, 1), // silver ore
                    new LootItem(454, 20, 1), // coal
                    new LootItem(445, 45, 1), // gold ore
                    new LootItem(448, 45, 1), // mithril ore
                    new LootItem(450, 180, 1), // adamantite ore
                    new LootItem(452, 1500, 1), // runite ore

                    new LootItem(1624, 205, 1), // uncut sapphire
                    new LootItem(1622, 140, 1), // uncut emerald
                    new LootItem(1620, 250, 1), // uncut ruby
                    new LootItem(1618, 520, 1), // uncut diamond

                    new LootItem(13391, 30, 1), // lizardman fang
                    new LootItem(7937, 2, 1), // pure essence
                    //new LootItem(13422, 24, 1), // saltpetre
                    new LootItem(8781, 100, 1), // teak plank
                    new LootItem(8783, 240, 1), // mahogany plank
                    //new LootItem(13574, 55, 1), // dynamite

                    new LootItem(21047, 131071, 1), // torn prayer scroll
                    new LootItem(21027, 131071, 1) // dark relic
                    );

    public static void giveRewards(ChambersOfXeric raid) {
        raid.getParty().forPlayers(p -> p.getRaidRewards().clear()); // Clear previous loot (if any)
        //uniques
        int uniqueBudget = raid.getParty().getPoints();
        int uniques = 0;
        for (int i = 0; i < 6; i++) { // Up to 6 uniques
            if (uniqueBudget <= 0)
                break;
            int pointsToUse = Math.min(570000, uniqueBudget); // Max of 570k points per unique attempt
            uniqueBudget -= pointsToUse;
            double chance = pointsToUse / 7676.0 / 100.0;   // 8676 - old
            if (Random.get() < chance) {
                uniques++;
                Player winner = getPlayerToReceiveUnique(raid);
                Item item = rollUnique();
                winner.getRaidRewards().add(item);
                rollPet(winner);
                Loggers.logRaidsUnique(winner.getName(), item.getDef().name, KillCounter.getKillCount(winner, BossKillCounter.COX));
                if (uniques == 1) {
                    raid.getParty().forPlayers(p -> p.sendMessage(Color.RAID_PURPLE.wrap("Special loot:")));
                }
                raid.getParty().forPlayers(p -> p.sendMessage(Color.RAID_PURPLE.wrap(winner.getName() + " - ") + Color.RED.wrap(item.getDef().name)));
                String message = winner.getName() + " just received " + item.getDef().descriptiveName;
                Broadcast.DROP.sendNews(winner, Icon.INVENTORY_BAG, message + " from Chambers of Xeric!");
                RareDropEmbedMessage.sendDiscordMessage(message, "Chambers of Xeric", item.getId());
            }
        }
        // Regular drops
        raid.getParty().getMembers().stream().filter(p -> p.getRaidRewards().isEmpty()).forEach(p -> {
            if (p.getRaidRewards().isEmpty() && Random.rollDie(12)) {
                p.getRaidRewards().add(24365);  // 1/12 of an elite clue if no uniques
            }
            int playerPoints = Math.min(131071, Config.RAIDS_PERSONAL_POINTS.get(p));
            for (int i = 0; i < 2; i++) {
                Item rolled = rollRegular();
                double pointsPerItem = rolled.getAmount();
                int amount = (int) Math.ceil(playerPoints / pointsPerItem) * 2;
                rolled.setAmount(amount);
                if (amount > 1 && !rolled.getDef().stackable && !rolled.getDef().isNote())
                    rolled.setId(rolled.getDef().notedId);
                p.getRaidRewards().add(rolled);
            }
            if (!p.hasAttribute(XericsTalisman.HONOUR_KEY) && Random.rollDie(10)) {
                if (p.findItem(Items.ANCIENT_TABLET) == null) {
                    p.getRaidRewards().add(Items.ANCIENT_TABLET);
                }
            }
        });
    }

    private static Item rollRegular() {
        return REGULAR_TABLE.rollItem();
    }

    private static Item rollUnique() {
        return UNIQUE_TABLE.rollItem();
    }

    private static void rollPet(Player player) {
        Pet pet = Pet.OLMLET;
        int average = pet.dropAverage;
        int threshold = pet.dropThreshold;
        int numerator = BossKillCounter.COX.getCounter().getKills(player) / threshold + 1;
        if (Random.rollDie(average, numerator)) {
            pet.unlock(player);
        }
    }


    private static Player getPlayerToReceiveUnique(ChambersOfXeric raid) {
        int roll = Random.get(raid.getParty().getPoints());
        for (Player player : raid.getParty().getMembers()) {
            roll -= Config.RAIDS_PERSONAL_POINTS.get(player);
            if (roll <= 0) {
                return player;
            }
        }
        return Random.get(raid.getParty().getMembers()); // shouldnt happen, but just in case
    }
}
