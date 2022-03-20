package io.ruin.model.skills.hunter;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.cache.ItemID;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatList;
import io.ruin.model.stat.StatType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/3/2022
 */
public class Birdhouse {

    private static final Logger logger = LoggerFactory.getLogger(Birdhouse.class);

    private Player player;
    @Expose
    private HashMap<Integer, Long> birdHouses; //Map of 4 times (system.currentmils) when state was started based on birdhouse (1626-> 1629)
    private Config config;

    public Birdhouse(Player player) {
        this.player = player;
        birdHouses = new HashMap<>();
    }

    public void init(Player player) {
        this.player = player;
        if (birdHouses == null)
            birdHouses = new HashMap<>();
        player.addEvent(event -> { //Todo not the best way but working on Login Listener if in bounds or Teleport Listener
            while (true) {
                event.delay(20);
                update();
            }
        });
    }

    /*static List<Integer> BIRD_HOUSE_REWARDS = Arrays.asList(ItemDef.get(ItemID.GRIMY_GUAM_LEAF).notedId, ItemDef.get(ItemID.GRIMY_MARRENTILL).notedId, ItemDef.get(ItemID.GRIMY_TARROMIN).notedId, ItemDef.get(ItemID.GRIMY_HARRALANDER).notedId, ItemDef.get(ItemID.GRIMY_RANARR_WEED).notedId, ItemDef.get(ItemID.GRIMY_IRIT_LEAF).notedId,
            ItemDef.get(ItemID.GRIMY_AVANTOE).notedId, ItemDef.get(ItemID.GRIMY_KWUARM).notedId, ItemDef.get(ItemID.GRIMY_SNAPDRAGON).notedId, ItemDef.get(ItemID.GRIMY_CADANTINE).notedId, ItemDef.get(ItemID.GRIMY_LANTADYME).notedId, ItemDef.get(ItemID.GRIMY_DWARF_WEED).notedId, ItemDef.get(ItemID.GRIMY_TORSTOL).notedId,
            ItemDef.get(ItemID.BIRD_NEST_5075).notedId, ItemID.ACORN, ItemID.APPLE_TREE_SEED, ItemID.BANANA_TREE_SEED, ItemID.ORANGE_TREE_SEED, ItemID.WILLOW_SEED, ItemID.TEAK_SEED, ItemID.CURRY_TREE_SEED, ItemID.MAPLE_SEED, ItemID.PINEAPPLE_SEED, ItemID.MAHOGANY_SEED, ItemID.PAPAYA_TREE_SEED, ItemID.PALM_TREE_SEED, ItemID.CALQUAT_TREE_SEED,
            ItemID.YEW_SEED, ItemID.MAGIC_SEED, ItemID.SPIRIT_SEED);*/
    static int[] SEEDS = { ItemID.BARLEY_SEED, ItemID.HAMMERSTONE_SEED, ItemID.ASGARNIAN_SEED, ItemID.JUTE_SEED, ItemID.YANILLIAN_SEED, ItemID.KRANDORIAN_SEED, ItemID.GUAM_SEED,
            ItemID.MARRENTILL_SEED, ItemID.TARROMIN_SEED, ItemID.HARRALANDER_SEED, ItemID.WILDBLOOD_SEED, ItemID.RANARR_SEED, ItemID.TOADFLAX_SEED, ItemID.IRIT_SEED,
            ItemID.AVANTOE_SEED, ItemID.KWUARM_SEED, ItemID.SNAPDRAGON_SEED, ItemID.CADANTINE_SEED, ItemID.LANTADYME_SEED, ItemID.DWARF_WEED_SEED, ItemID.TORSTOL_SEED };
    static int[] TOOLS = { ItemID.CLOCKWORK, ItemID.HAMMER, ItemID.CHISEL };

    private void handleObject(int obj, int option) {
        config = getVarbitId(obj);
        if (config == null) return;
        int varpbitValue = config.get(player);
        State state = getState(varpbitValue);
        if (state == null) return;
        handleClick(state, option);
    }

    private void update() {
        if (birdHouses == null || birdHouses.size() == 0) return;
        birdHouses.entrySet().forEach(b -> {
            Long savedTime = b.getValue();
            if (savedTime == -1) return;
            if (System.currentTimeMillis() - savedTime > TimeUnit.MINUTES.toMillis(50)) {
                Config varbitId = getConfigFromVarbitId(b.getKey());
                //This may error, but idk maybe entryset allows us to edit it
                varbitId.set(player, varbitId.get(player) + 1);
                b.setValue(-1L);
            }
        });
    }

    private State getState(int varpbitValue) {
        if (varpbitValue == 0) {
            return State.EMPTY;
        } else if (varpbitValue % 3 == 1) {
            return State.BUILT;
        } else if (varpbitValue % 3 == 2) {
            return State.SEEDED;
        } else if (varpbitValue % 3 == 0) {
            return State.CAUGHT;
        }
        return null;
    }

    private void handleClick(State state, int option) {
        switch (state) {
            case EMPTY:
                if (option == 1)
                    build();
                return;
            case BUILT:
                if (option == 2)
                    addSeeds();
                return;
            case SEEDED:
                if (option == 2)
                    addSeeds();
                if (option == 3)
                    dismantle();
                return;
            case CAUGHT:
                if (option == 1)
                    interact();
                if (option == 2)
                    addSeeds();
                if (option == 3)
                    empty();
        }
    }

    private void interact() {
        //Todo option dialogue of possible options - kinda useless in RSPS but its a simple dialogue - add seeds or empty since its stage 3 only - @coins
    }

    private void dismantle() {
        player.dialogue(new OptionsDialogue("<col=7f0000>You will lose any birds and seed in the birdhouse</col>",
                new Option("Yes - dismantle and <col=7f0000>discard the birdhouse and contents</col>", () -> {
                    config.set(player, 0);
                    birdHouses.put(config.id, -1L);
                    player.getInventory().addOrDrop(ItemID.CLOCKWORK, 1);
                }),
                new Option("No, leave it be", player::closeDialogue)));
    }

    private static final List<Integer> NESTS = Arrays.asList(5075, 5074, 5075, 5074, 5075, 5074, 5071, 5070, 5072);
    private static final List<Integer> CLUE_NESTS = Arrays.asList(23127, 19712, 19714, 19716, 19718);

    private void empty() {
        final BirdHouses birdHouse = getBirdHouseById(config.get(player));
        int hunterLevel = player.getStats().get(StatType.Hunter).currentLevel;
        player.startEvent(event -> {
            player.animate(827);
            player.getStats().addXp(StatType.Hunter, birdHouse.hunterExperience, false);
            player.getInventory().addOrDrop(Items.FEATHER, Random.get(30, 60));
            if (Random.rollPercent((int) (hunterLevel * 0.8))) {
                player.getInventory().addOrDrop(5073, 1);   // Seed nest
            }
            new GroundItem(Items.RAW_BIRD_MEAT, 10).owner(player).position(player.getPosition()).spawn();   // Spawn the raw meat
            for (int roll = 0; roll < 10; roll++) { // Roll nests 10 times
                if (Random.rollPercent((int) (birdHouse.baseNestPercentage + (Math.min(0, hunterLevel - 50) * 0.15)))) {
                    if (Random.get(10) == 1) {
                        player.getInventory().addOrDrop(Random.get(CLUE_NESTS), 1);
                    } else {
                        player.getInventory().addOrDrop(Random.get(NESTS), 1);
                    }
                }
            }

            config.set(player, 0);
            player.getInventory().addOrDrop(ItemID.CLOCKWORK, 1);
        });
    }

    private void addSeeds() {
        BirdHouses birdHouse = getBirdHouseById(config.get(player));
        if (config.get(player) == birdHouse.seeded) {
            player.dialogue(new MessageDialogue("The birdhouse is already full of seeds!"));
            return;
        }
        if (config.get(player) == birdHouse.caught) {
            player.dialogue(new MessageDialogue("The birdhouse looks like it has caught something! Empty it first."));
            return;
        }
        for (int i = 0; i < SEEDS.length; i++) {
            if (player.getInventory().hasId(SEEDS[i])) {
                int amount = i > 9 ? 5 : 10;
                if (player.getInventory().getAmount(SEEDS[i]) >= amount) {
                    player.lock();
                    player.animate(827);
                    player.getInventory().remove(SEEDS[i], amount);
                    config.set(player, birdHouse.seeded);
                    birdHouses.put(config.id, System.currentTimeMillis());
                    player.dialogue(new MessageDialogue("The birdhouse trap is now full of seed and will start to catch birds."));
                    player.unlock();
                    return;
                }
            }
        }
        player.dialogue(new MessageDialogue("You don't have enough seeds."));
    }

    private void addSeeds(Item seeds) {
        BirdHouses birdHouse = getBirdHouseById(config.get(player));
        if (config.get(player) == birdHouse.seeded) {
            player.dialogue(new MessageDialogue("The birdhouse is already full of seeds!"));
            return;
        }
        if (config.get(player) == birdHouse.caught) {
            player.dialogue(new MessageDialogue("The birdhouse looks like it has caught something! Empty it first."));
            return;
        }
        for (int i = 0; i < SEEDS.length; i++) {
            if (player.getInventory().hasId(SEEDS[i]) && SEEDS[i] == seeds.getId()) {
                int amount = i > 9 ? 5 : 10;
                if (player.getInventory().getAmount(SEEDS[i]) >= amount) {
                    player.lock();
                    player.animate(827);
                    player.getInventory().remove(SEEDS[i], amount);
                    config.set(player, birdHouse.seeded);
                    birdHouses.put(config.id, System.currentTimeMillis());
                    player.dialogue(new MessageDialogue("The birdhouse trap is now full of seed and will start to catch birds."));
                    player.unlock();
                    return;
                }
            }
        }
        player.dialogue(new MessageDialogue("You don't have enough seeds."));
    }

    private void build() {
        List<BirdHouses> birdHouses = Arrays.asList(BirdHouses.values());
        Collections.reverse(birdHouses);
        boolean message = false;
        for (BirdHouses b : birdHouses) {
            if (config.get(player) == b.built) return;
            if (hasRequirements(b)) {
                player.startEvent(event -> {
                    player.lock();
                    player.animate(827);
                    player.getInventory().remove(b.birdhouse, 1);
                    config.set(player, b.built);
                    player.unlock();
                });
                return;
            }
            message = true;
        }
        if (message)
            player.dialogue(new MessageDialogue("You do not have a birdhouse with the required hunter level to build here."));
    }

    private boolean hasRequirements(BirdHouses b) {
        StatList stats = player.getStats();
        if (player.getInventory().hasAtLeastOneOf(b.birdhouse) && stats.get(StatType.Hunter).currentLevel >= b.hunterLevel && stats.get(StatType.Crafting).currentLevel >= b.craftingLevel) {
            logger.info("Has requirements!");
            return true;
        }
        return false;
    }

    enum State {
        EMPTY,
        BUILT,
        SEEDED,
        CAUGHT
    }

    private Config getVarbitId(int obj) {
        switch (obj) {
            case 30565:
                return Config.BIRD_HOUSE_MEADOW_NORTH;
            case 30566:
                return Config.BIRD_HOUSE_MEADOW_SOUTH;
            case 30567:
                return Config.BIRD_HOUSE_VALLEY_NORTH;
            case 30568:
                return Config.BIRD_HOUSE_VALLEY_SOUTH;
        }
        return null;
    }

    private Config getConfigFromVarbitId(int obj) {
        switch (obj) {
            case 1626:
                return Config.BIRD_HOUSE_MEADOW_NORTH;
            case 1627:
                return Config.BIRD_HOUSE_MEADOW_SOUTH;
            case 1628:
                return Config.BIRD_HOUSE_VALLEY_NORTH;
            case 1629:
                return Config.BIRD_HOUSE_VALLEY_SOUTH;
        }
        return null;
    }

    private BirdHouses getBirdHouseById(int configValue) {
        for (BirdHouses house : BirdHouses.values()) {
            if (house.built == configValue || house.seeded == configValue || house.caught == configValue)
                return house;
        }
        return null;
    }

    @RequiredArgsConstructor
    enum BirdHouses {
        REGULAR(ItemID.BIRD_HOUSE, 1, 2, 3, ItemID.LOGS, 5, 5, 280, 15, 5.0),
        OAK(ItemID.OAK_BIRD_HOUSE, 4, 5, 6, ItemID.OAK_LOGS, 14, 15, 420, 20, 6.2),
        WILLOW(ItemID.WILLOW_BIRD_HOUSE, 7, 8, 9, ItemID.WILLOW_LOGS, 24, 25, 560, 25, 6.4),
        TEAK(ItemID.TEAK_BIRD_HOUSE, 10, 11, 12, ItemID.TEAK_LOGS, 34, 35, 700, 30, 6.5),
        MAPLE(ItemID.MAPLE_BIRD_HOUSE, 13, 14, 15, ItemID.MAPLE_LOGS, 44, 45, 820, 35, 7.0),
        MAHOGANY(ItemID.MAHOGANY_BIRD_HOUSE, 16, 17, 18, ItemID.MAHOGANY_LOGS, 49, 50, 960, 40, 7.5),
        YEW(ItemID.YEW_BIRD_HOUSE, 19, 20, 21, ItemID.YEW_LOGS, 59, 60, 1020, 45, 8.0),
        MAGIC(ItemID.MAGIC_BIRD_HOUSE, 22, 23, 24, ItemID.MAGIC_LOGS, 74, 75, 1140, 50, 8.5),
        REDWOOD(ItemID.REDWOOD_BIRD_HOUSE, 25, 26, 27, ItemID.REDWOOD_LOGS, 89, 90, 1200, 55, 8.7);

        public final int birdhouse;
        public final int built;
        public final int seeded;
        public final int caught;
        public final int log;
        public final int hunterLevel;
        public final int craftingLevel;
        public final double hunterExperience;
        public final double craftingExperience;
        public final double baseNestPercentage;
    }

    static {
        for (int i = 30565; i < 30569; i++) {
            for (int j = 1; j < 5; j++) {
                int finalI = i;
                int finalJ = j;
                ObjectAction.register(i, j, (player, obj) -> player.getBirdhouse().handleObject(finalI, finalJ));
            }
            for (BirdHouses house : BirdHouses.values()) {
                ItemObjectAction.register(house.birdhouse, i, (player, item, obj) -> {
                    player.getBirdhouse().config = player.getBirdhouse().getVarbitId(obj.id);
                    if (player.getBirdhouse().config == null) return;
                    player.getBirdhouse().build();
                });
            }
            for (int seed : SEEDS) {
                ItemObjectAction.register(seed, i, (player, item, obj) -> {
                    player.getBirdhouse().config = player.getBirdhouse().getVarbitId(obj.id);
                    if (player.getBirdhouse().config == null) return;
                   player.getBirdhouse().addSeeds();
                });
            }
        }

        for (BirdHouses birdHouseHandler : BirdHouses.values()) {
            for (int t : TOOLS) {
                ItemItemAction.register(birdHouseHandler.log, t, (player, primary, secondary) -> {
                    Stat stat = player.getStats().get(StatType.Crafting);
                    if (stat.currentLevel < birdHouseHandler.craftingLevel) {
                        player.dialogue(new MessageDialogue("You need a Crafting level of " + birdHouseHandler.craftingLevel + " to craft this bird house."));
                        player.privateSound(2277);
                        return;
                    }
                    for (int piece : TOOLS) {
                        if (!player.getInventory().contains(piece, 1)) {
                            player.dialogue(new MessageDialogue("You need a clockwork, hammer, chisel and log to make a bird house"));
                            return;
                        }
                    }
                    player.animate(7057);
                    player.getInventory().remove(ItemID.CLOCKWORK, 1);
                    player.getInventory().remove(birdHouseHandler.log, 1);
                    player.getInventory().add(birdHouseHandler.birdhouse, 1);
                    player.getStats().addXp(StatType.Crafting, birdHouseHandler.craftingExperience, true);
                });
            }
        }
    }
}
