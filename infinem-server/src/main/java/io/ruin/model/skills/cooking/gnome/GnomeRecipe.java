package io.ruin.model.skills.cooking.gnome;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;
import lombok.Setter;

import java.util.function.Consumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/5/2023
 */
public enum GnomeRecipe {
    // Cocktails
    FRUIT_BLAST(GnomeCocktail.FRUIT_BLAST::mix),
    PINEAPPLE_PUNCH(GnomeCocktail.PINEAPPLE_PUNCH::mix),
    WIZARD_BLIZZARD(GnomeCocktail.WIZARD_BLIZZARD::mix),
    SHORT_GREEN_GUY(GnomeCocktail.SHORT_GREEN_GUY::mix),
    DRUNK_DRAGON(GnomeCocktail.DRUNK_DRAGON::mix),
    CHOCOLATE_SATURDAY(GnomeCocktail.CHOCOLATE_SATURDAY::mix),
    BLURBERRY_SPECIAL(GnomeCocktail.BLURBERRY_SPECIAL::mix),

    // Battas
    FRUIT_BATTA(25, 40.0, 80.0,
            new Item[] { new Item(Items.EQUA_LEAVES, 4), new Item(Items.LIME_CHUNKS), new Item(Items.ORANGE_CHUNKS), new Item(Items.PINEAPPLE_CHUNKS) }, Items.HALF_MADE_BATTA, Items.UNFINISHED_BATTA_12,
            new Item[] { new Item(Items.GNOME_SPICE) }, Items.FRUIT_BATTA),
    TOAD_BATTA(26, 40.0, 82.0,
            new Item[] { new Item(Items.EQUA_LEAVES), new Item(Items.GNOME_SPICE), new Item(Items.CHEESE), new Item(Items.TOADS_LEGS) }, Items.HALF_MADE_BATTA_3, Items.TOAD_BATTA,
            new Item[] {}, -1),
    WORM_BATTA(27, 40.0, 84.0,
            new Item[] { new Item(Items.KING_WORM), new Item(Items.CHEESE), new Item(Items.GNOME_SPICE) }, Items.HALF_MADE_BATTA_2, Items.UNFINISHED_BATTA_13,
            new Item[] { new Item(Items.EQUA_LEAVES) }, Items.WORM_BATTA),
    VEGETABLE_BATTA(28, 40.0, 86.0,
            new Item[] { new Item(Items.TOMATO, 2), new Item(Items.DWELLBERRIES), new Item(Items.ONION), new Item(Items.CABBAGE) }, Items.HALF_MADE_BATTA_5, Items.UNFINISHED_BATTA_15,
            new Item[] { new Item(Items.EQUA_LEAVES) }, Items.VEGETABLE_BATTA),
    CHEESE_AND_TOMATO_BATTA(29, 40.0, 88.0,
            new Item[] { new Item(Items.TOMATO), new Item(Items.CHEESE) }, Items.HALF_MADE_BATTA_4, Items.UNFINISHED_BATTA_14,
            new Item[] { new Item(Items.EQUA_LEAVES) }, Items.CHEESETOM_BATTA),

    // Bowls
    WORM_HOLE(30, 50.0, 90.0,
            new Item[] { new Item(Items.KING_WORM, 4), new Item(Items.ONION, 2),new Item(Items.GNOME_SPICE) }, Items.HALF_MADE_BOWL_2, Items.UNFINISHED_BOWL_6,
            new Item[] { new Item(Items.EQUA_LEAVES) }, Items.WORM_HOLE),
    VEGETABLE_BALL(35, 50.0, 95.0,
            new Item[] { new Item(Items.ONION, 2), new Item(Items.POTATO, 2), new Item(Items.GNOME_SPICE) }, Items.HALF_MADE_BOWL_3, Items.UNFINISHED_BOWL_7,
            new Item[] { new Item(Items.EQUA_LEAVES) }, Items.VEG_BALL),
    TANGLED_TOADS_LEGS(40, 50.0, 105.0,
            new Item[] { new Item(Items.TOADS_LEGS, 4), new Item(Items.GNOME_SPICE), new Item(Items.CHEESE, 2), new Item(Items.DWELLBERRIES), new Item(Items.EQUA_LEAVES, 2) }, Items.HALF_MADE_BOWL, Items.TANGLED_TOADS_LEGS,
            new Item[] {}, -1),
    CHOCOLATE_BOMB(42, 50.0, 110.0,
            new Item[] { new Item(Items.CHOCOLATE_BAR, 4), new Item(Items.EQUA_LEAVES) }, Items.HALF_MADE_BOWL_4, Items.UNFINISHED_BOWL_8,
            new Item[] { new Item(Items.CHOCOLATE_DUST), new Item(Items.POT_OF_CREAM, 2) }, Items.CHOCOLATE_BOMB),

    // Crunchies
    TOAD_CRUNCHIES(10, 30.0, 40.0,
            new Item[] { new Item(Items.TOADS_LEGS, 2), new Item(Items.GNOME_SPICE) }, Items.HALF_MADE_CRUNCHY_3, Items.UNFINISHED_CRUNCHY_6,
            new Item[] { new Item(Items.EQUA_LEAVES) }, Items.TOAD_CRUNCHIES),
    SPICY_CRUNCHIES(12, 30.0, 42.0,
            new Item[] { new Item(Items.EQUA_LEAVES), new Item(Items.GNOME_SPICE) }, Items.HALF_MADE_CRUNCHY_2, Items.UNFINISHED_CRUNCHY_5,
            new Item[] { new Item(Items.GNOME_SPICE) }, Items.SPICY_CRUNCHIES),
    WORM_CRUNCHIES(14, 30.0, 44.0,
            new Item[] { new Item(Items.KING_WORM, 2), new Item(Items.GNOME_SPICE), new Item(Items.EQUA_LEAVES) }, Items.HALF_MADE_CRUNCHY_4, Items.UNFINISHED_CRUNCHY_7,
            new Item[] { new Item(Items.GNOME_SPICE) }, Items.WORM_CRUNCHIES),
    CHOCCHIP_CRUNCHIES(16, 30.0, 46.0,
            new Item[] { new Item(Items.CHOCOLATE_BAR, 2), new Item(Items.GNOME_SPICE) }, Items.HALF_MADE_CRUNCHY, Items.UNFINISHED_CRUNCHY_4,
            new Item[] { new Item(Items.CHOCOLATE_DUST) }, Items.CHOCCHIP_CRUNCHIES);

    @Setter private GnomeCookingType type;
    private int levelRequirement, halfMadeProduct, unfinishedProduct, finalProduct;
    private double halfMadeExperience, garnishExperience;
    private Item[] halfMadeIngredients, garnishIngredients;
    private Consumer<Player> consumer;

    GnomeRecipe(int levelRequirement, double halfMadeExperience, double garnishExperience, Item[] halfMadeIngredients, int halfMadeProduct, int unfinishedProduct, Item[] garnishIngredients, int finalProduct) {
        this.levelRequirement = levelRequirement;
        this.halfMadeExperience = halfMadeExperience;
        this.garnishExperience = garnishExperience;
        this.halfMadeIngredients = halfMadeIngredients;
        this.halfMadeProduct = halfMadeProduct;
        this.unfinishedProduct = unfinishedProduct;
        this.garnishIngredients = garnishIngredients;
        this.finalProduct = finalProduct;
    }

    GnomeRecipe(Consumer<Player> consumer) {
        this.consumer = consumer;
    }

    static String getAmountString(int amount) {
        switch (amount) {
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
        }
        return "";
    }

    static String getIngredientString(Item[] ingredients) {
        return getIngredientString(ingredients, true);
    }

    static String getIngredientString(Item[] ingredients, boolean allowMultiple) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        int maxCount = ingredients.length;
        for (Item ing : ingredients) {
            if (ing.getAmount() == 1 || !allowMultiple) {
                sb.append(ing.getDef().descriptiveName.toLowerCase());
            } else {
                sb.append(getAmountString(ing.getAmount()));
                sb.append(" ");
                sb.append(ing.getDef().name.toLowerCase());
            }
            if (++count < maxCount) {
                if (count == maxCount - 1)
                    sb.append(", and ");
                else
                    sb.append(", ");
            }
        }
        return sb.toString();
    }

    private void create(Player player) {
        if (!player.getStats().check(StatType.Cooking, levelRequirement, "make this")) return;
        Item halfBaked = player.getInventory().findItem(type.getPrepareItem());
        if (halfBaked == null) {
            player.dialogue(new MessageDialogue("You need a half baked " + type.name().toLowerCase() + " to start working on this."));
            return;
        }
        for (Item req : halfMadeIngredients) {
            if (!player.getInventory().contains(req)) {
                String name = this.name().toLowerCase();
                player.dialogue(new MessageDialogue("To create " + (name.endsWith("s") ? "" : "a ") + name.replace("_", " ") + " you need " + getIngredientString(halfMadeIngredients) + "."));
                return;
            }
        }
        for (Item req : halfMadeIngredients) {
            player.getInventory().remove(req);
        }
        halfBaked.setId(halfMadeProduct);
        player.getStats().addXp(StatType.Cooking, halfMadeExperience, true);
        if (finalProduct == -1) {
            player.dialogue(new MessageDialogue("You just need to bake this to complete."));
        } else {
            player.dialogue(new MessageDialogue("You just need to bake this and garnish with " + GnomeRecipe.getIngredientString(garnishIngredients) + " to complete."));
        }
    }

    private void garnish(Player player) {
        String name = this.name().toLowerCase();
        Item halfMade = player.getInventory().findItem(unfinishedProduct);
        if (halfMade == null) {
            player.dialogue(new MessageDialogue("You need an unfinished " + this.name().toLowerCase() + " to start working on this."));
            return;
        }
        for (Item req : garnishIngredients) {
            if (!player.getInventory().contains(req)) {
                player.dialogue(new MessageDialogue("To garnish " + (name.endsWith("s") ? "" : "a ") + " you need " + getIngredientString(garnishIngredients) + "."));
                return;
            }
        }
        for (Item req : garnishIngredients) {
            player.getInventory().remove(req);
        }
        halfMade.setId(finalProduct);
        player.getStats().addXp(StatType.Cooking, garnishExperience, true);
        player.dialogue(new MessageDialogue("You garnish with " + getIngredientString(garnishIngredients, false) + " for that final touch. Mmmm."));
    }

    public static void create(Player player, int index) {
        GnomeRecipe recipe = values()[index];
        if (recipe.consumer != null) recipe.consumer.accept(player);
        else recipe.create(player);
    }

    static {
        for (GnomeRecipe recipe : values()) {
            if (recipe.finalProduct != -1 && recipe.garnishIngredients != null) {
                for (Item item : recipe.garnishIngredients) {
                    ItemItemAction.register(recipe.unfinishedProduct, item.getId(), (player, primary, secondary) -> recipe.garnish(player));
                }
            }
        }
    }
}
