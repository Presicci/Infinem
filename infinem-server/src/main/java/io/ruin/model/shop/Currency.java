package io.ruin.model.shop;

import io.ruin.cache.ItemID;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Currency {
    COINS(new ItemCurrencyHandler(ItemID.COINS_995)),
    BLOOD_MONEY(new ItemCurrencyHandler(ItemID.BLOOD_MONEY)),
    TOKKUL(new ItemCurrencyHandler(ItemID.TOKKUL)),
    MARKS_OF_GRACE(new ItemCurrencyHandler(ItemID.MARK_OF_GRACE, true)),
    EASTER_EGGS(new ItemCurrencyHandler(ItemID.EASTER_EGG)),
    MOLCH_PEARLS(new ItemCurrencyHandler(ItemID.MOLCH_PEARL, true)),
    SURVIVAL_TOKENS(new ItemCurrencyHandler(ItemID.SURVIVAL_TOKEN)),
    GOLDEN_NUGGETS(new ItemCurrencyHandler(ItemID.GOLDEN_NUGGET, true)),
    WARRIOR_GUILD_TOKEN(new ItemCurrencyHandler(ItemID.WARRIOR_GUILD_TOKEN)),
    VOTE_TICKETS(new ItemCurrencyHandler(32029)),
    UNIDENTIFIED_MINERALS(new ItemCurrencyHandler(ItemID.UNIDENTIFIED_MINERALS, true)),
    STARDUST(new ItemCurrencyHandler(25527, true)),
    PIECES_OF_EIGHT(new ItemCurrencyHandler(Items.PIECES_OF_EIGHT)),
    ARCHERY_TICKETS(new ItemCurrencyHandler(1464)),
    TASK_POINTS(new CurrencyHandler("daily task points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.dailyTaskPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if(amount > player.dailyTaskPoints){
                return 0;
            }
            player.dailyTaskPoints -= amount;
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if((long) player.dailyTaskPoints + (long) amount > Integer.MAX_VALUE){
                player.dailyTaskPoints = Integer.MAX_VALUE;
            } else {
                player.dailyTaskPoints += amount;
            }
            return amount;
        }
    }),
    WILDERNESS_SLAYER_POINTS(new CurrencyHandler("wilderness slayer points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.getAttributeIntOrZero("WILDY_SLAYER_POINTS");
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if(amount > player.getAttributeIntOrZero("WILDY_SLAYER_POINTS")){
                return 0;
            }
            player.incrementNumericAttribute("WILDY_SLAYER_POINTS", -amount);
            return amount;
        }

        @Override
        public int addCurrency(Player player, int amount) {
            if((long) player.getAttributeIntOrZero("WILDY_SLAYER_POINTS") + (long) amount > Integer.MAX_VALUE){
                player.putAttribute("WILDY_SLAYER_POINTS", Integer.MAX_VALUE);
            } else {
                player.incrementNumericAttribute("WILDY_SLAYER_POINTS", amount);
            }
            return amount;
        }
    }),
    PVM_POINTS(new CurrencyHandler("pvm points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.PvmPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if(amount > player.PvmPoints){
                return 0;
            }
            player.PvmPoints -= amount;
            return amount;
        }
        @Override
        public int addCurrency(Player player, int amount) {
            if((long) player.PvmPoints + (long) amount > Integer.MAX_VALUE){
                player.PvmPoints = Integer.MAX_VALUE;
            } else {
                player.PvmPoints += amount;
            }
            return amount;
        }
    }),
    PEST_CONTROL_POINTS(new CurrencyHandler("pest control points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.getAttributeIntOrZero("PEST_POINTS");
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if(amount > player.getAttributeIntOrZero("PEST_POINTS")){
                return 0;
            }
            player.incrementNumericAttribute("PEST_POINTS", -amount);
            return amount;
        }
        @Override
        public int addCurrency(Player player, int amount) {
            if((long) player.getAttributeIntOrZero("PEST_POINTS") + (long) amount > Integer.MAX_VALUE){
                player.putAttribute("PEST_POINTS", Integer.MAX_VALUE);
            } else {
                player.incrementNumericAttribute("PEST_POINTS", amount);
            }
            return amount;
        }
    }),
    WILDERNESS_POINTS(new CurrencyHandler("wilderness points") {

        @Override
        public int getCurrencyCount(Player player) {
            return player.wildernessPoints;
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            if(amount > player.wildernessPoints){
                return 0;
            }
            player.wildernessPoints -= amount;
            return amount;
        }
        @Override
        public int addCurrency(Player player, int amount) {
            if((long) player.wildernessPoints + (long) amount > Integer.MAX_VALUE){
                player.wildernessPoints = Integer.MAX_VALUE;
            } else {
                player.wildernessPoints += amount;
            }
            return amount;
        }
    }),
    TREASURE_TRAIL_POINTS(new CurrencyHandler("treasure trail points") {
        @Override
        public int getCurrencyCount(Player player) {
            return player.getAttributeIntOrZero("CLUE_POINTS");
        }

        @Override
        public int removeCurrency(Player player, int amount) {
            return player.incrementNumericAttribute("CLUE_POINTS", -amount);
        }

        @Override
        public int addCurrency(Player player, int amount) {
            return player.incrementNumericAttribute("CLUE_POINTS", amount);
        }
    }),
    ;

    Currency(CurrencyHandler currencyHandler) {
        this.currencyHandler = currencyHandler;
    }

    private final CurrencyHandler currencyHandler;


    public static Stream<ItemCurrencyHandler> itemCurrencyStream() {
        return Stream.of(values()).filter(currency -> currency.currencyHandler instanceof ItemCurrencyHandler).map(currency -> (ItemCurrencyHandler)currency.currencyHandler);
    }
}
