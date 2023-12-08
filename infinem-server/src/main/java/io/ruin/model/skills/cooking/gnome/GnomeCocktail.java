package io.ruin.model.skills.cooking.gnome;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/6/2023
 */
public enum GnomeCocktail {
    FRUIT_BLAST(6, 30.0, 20.0,
            new Item[] { new Item(Items.PINEAPPLE), new Item(Items.LEMON), new Item(Items.ORANGE) }, Items.MIXED_BLAST,
            new Item[] { new Item(Items.LEMON_SLICES) }, Items.FRUIT_BLAST),
    PINEAPPLE_PUNCH(8, 30.0, 40.0,
            new Item[] { new Item(Items.PINEAPPLE, 2), new Item(Items.LEMON), new Item(Items.ORANGE) }, Items.MIXED_PUNCH,
            new Item[] { new Item(Items.LIME_CHUNKS), new Item(Items.PINEAPPLE_CHUNKS), new Item(Items.ORANGE_SLICES) }, Items.PINEAPPLE_PUNCH),
    WIZARD_BLIZZARD(18, 30.0, 80.0,
            new Item[] { new Item(Items.VODKA, 2), new Item(Items.GIN), new Item(Items.LIME), new Item(Items.LEMON), new Item(Items.ORANGE) }, Items.MIXED_BLIZZARD,
            new Item[] { new Item(Items.PINEAPPLE_CHUNKS), new Item(Items.LIME_SLICES) }, Items.WIZARD_BLIZZARD),
    SHORT_GREEN_GUY(20, 30.0, 90.0,
            new Item[] { new Item(Items.VODKA), new Item(Items.LIME, 3) }, Items.MIXED_SGG,
            new Item[] { new Item(Items.LIME_SLICES), new Item(Items.EQUA_LEAVES) }, Items.SHORT_GREEN_GUY),
    DRUNK_DRAGON(32, 30.0, 0.0,
            new Item[] { new Item(Items.VODKA), new Item(Items.GIN), new Item(Items.DWELLBERRIES) }, Items.MIXED_DRAGON,
            new Item[] {}, Items.MIXED_DRAGON_2,
            new Item[] { new Item(Items.PINEAPPLE_CHUNKS), new Item(Items.POT_OF_CREAM) }, Items.MIXED_DRAGON_3),
    CHOCOLATE_SATURDAY(33, 30.0, 0.0, 140.0,
            new Item[] { new Item(Items.WHISKY), new Item(Items.CHOCOLATE_BAR), new Item(Items.EQUA_LEAVES), new Item(Items.BUCKET_OF_MILK) }, Items.MIXED_SATURDAY,
            new Item[] {}, Items.MIXED_SATURDAY_2,
            Items.MIXED_SATURDAY_3, new Item[] { new Item(Items.CHOCOLATE_DUST), new Item(Items.POT_OF_CREAM) }, Items.CHOC_SATURDAY),
    BLURBERRY_SPECIAL(37, 30.0, 150.0,
            new Item[] { new Item(Items.VODKA), new Item(Items.BRANDY), new Item(Items.GIN), new Item(Items.LEMON), new Item(Items.ORANGE) }, Items.MIXED_SPECIAL,
            new Item[] { new Item(Items.LEMON_CHUNKS), new Item(Items.ORANGE_CHUNKS), new Item(Items.EQUA_LEAVES), new Item(Items.LIME_SLICES) }, Items.BLURBERRY_SPECIAL);

    private final int levelRequirement, mixProduct, pourProduct;
    private final double mixExperience, pourExperience;
    private final Item[] mixIngredients, pourIngredients;
    private double extraExperience = 0.0;
    private Item[] extraIngredients = null;
    private int extraProduct = -1, extraRequiredMix = -1;

    GnomeCocktail(int levelRequirement, double mixExperience, double pourExperience, double extraExperience, Item[] mixIngredients, int mixProduct, Item[] pourIngredients, int pourProduct, int extraRequiredMix, Item[] extraIngredients, int extraProduct) {
        this(levelRequirement, mixExperience, pourExperience, mixIngredients, mixProduct, pourIngredients, pourProduct);
        this.extraIngredients = extraIngredients;
        this.extraProduct = extraProduct;
        this.extraRequiredMix = extraRequiredMix;
        this.extraExperience = extraExperience;
    }

    GnomeCocktail(int levelRequirement, double mixExperience, double pourExperience, Item[] mixIngredients, int mixProduct, Item[] pourIngredients, int pourProduct, Item[] extraIngredients, int extraProduct) {
        this(levelRequirement, mixExperience, pourExperience, mixIngredients, mixProduct, pourIngredients, pourProduct);
        this.extraIngredients = extraIngredients;
        this.extraProduct = extraProduct;
    }

    GnomeCocktail(int levelRequirement, double mixExperience, double pourExperience, Item[] mixIngredients, int mixProduct, Item[] pourIngredients, int pourProduct) {
        this.levelRequirement = levelRequirement;
        this.mixExperience = mixExperience;
        this.pourExperience = pourExperience;
        this.mixIngredients = mixIngredients;
        this.mixProduct = mixProduct;
        this.pourIngredients = pourIngredients;
        this.pourProduct = pourProduct;
    }

    public void mix(Player player) {
        if (!player.getStats().check(StatType.Cooking, levelRequirement, "mix this")) return;
        Item mixer = player.getInventory().findItem(Items.COCKTAIL_SHAKER);
        if (mixer == null) {
            player.dialogue(new MessageDialogue("You need a cocktail shaker to mix this cocktail."));
            return;
        }
        for (Item req : mixIngredients) {
            if (!player.getInventory().contains(req)) {
                player.dialogue(new MessageDialogue("To mix a " + this.name().toLowerCase() + " you need " + GnomeRecipe.getIngredientString(mixIngredients) + "."));
                return;
            }
        }
        for (Item req : mixIngredients) {
            player.getInventory().remove(req);
        }
        mixer.setId(mixProduct);
        player.getStats().addXp(StatType.Cooking, mixExperience, true);
        if (extraProduct == -1) {
            player.dialogue(new MessageDialogue("You just need to pour this into an empty cocktail glass and garnish with " + GnomeRecipe.getIngredientString(pourIngredients) + " before serving."));
        } else {
            if (extraRequiredMix != -1) {
                player.dialogue(new MessageDialogue("You just need to pour this into an empty cocktail glass, then heat before garnishing with " + GnomeRecipe.getIngredientString(extraIngredients) + "."));
            } else {
                player.dialogue(new MessageDialogue("You just need to pour this into an empty cocktail glass, then garnish with " + GnomeRecipe.getIngredientString(extraIngredients) + ". Make sure to heat before serving!"));
            }
        }
    }

    public void pour(Player player) {
        Item glass = player.getInventory().findItem(Items.COCKTAIL_GLASS);
        Item mix = player.getInventory().findItem(mixProduct);
        if (glass == null || mix == null) {
            player.dialogue(new MessageDialogue("You need a cocktail glass to pour this cocktail."));
            return;
        }
        for (Item req : pourIngredients) {
            if (!player.getInventory().contains(req)) {
                player.dialogue(new MessageDialogue("To pour this cocktail you need " + GnomeRecipe.getIngredientString(pourIngredients) + "."));
                return;
            }
        }
        for (Item req : pourIngredients) {
            player.getInventory().remove(req);
        }
        glass.setId(pourProduct);
        mix.setId(Items.COCKTAIL_SHAKER);
        player.getStats().addXp(StatType.Cooking, pourExperience, true);
        player.dialogue(new MessageDialogue("You pour the cocktail" + (extraProduct != -1 ? "." : " and add the finishing touches.")));
        if (extraProduct == -1) player.getTaskManager().doLookupByUUID(937);    // Make a Gnome Cocktail
    }

    public void garnish(Player player) {
        Item cocktail = player.getInventory().findItem(extraRequiredMix != -1 ? extraRequiredMix : pourProduct);
        if (cocktail == null) {
            player.dialogue(new MessageDialogue("You seem to be missing the cocktail you are trying to finish."));
            return;
        }
        for (Item req : extraIngredients) {
            if (!player.getInventory().contains(req)) {
                player.dialogue(new MessageDialogue("To " + (this == DRUNK_DRAGON ? "garnish" : "finish") + " this cocktail you need " + GnomeRecipe.getIngredientString(extraIngredients) + "."));
                return;
            }
        }
        for (Item req : extraIngredients) {
            player.getInventory().remove(req);
        }
        cocktail.setId(extraProduct);
        if (extraExperience > 0.0) {
            player.getStats().addXp(StatType.Cooking, extraExperience, true);
        }
        player.dialogue(new MessageDialogue("You add " + GnomeRecipe.getIngredientString(extraIngredients) + " for that final touch." + (this == DRUNK_DRAGON ? " Now you just need to heat it up." : "")));
        if (this != DRUNK_DRAGON) player.getTaskManager().doLookupByUUID(937);    // Make a Gnome Cocktail
    }

    static {
        for (GnomeCocktail cocktail : values()) {
            ItemAction.registerInventory(cocktail.mixProduct, "pour", (player, item) -> cocktail.pour(player));
            ItemItemAction.register(cocktail.mixProduct, Items.COCKTAIL_GLASS, (player, item, glass) -> cocktail.pour(player));
            if (cocktail.extraIngredients != null) {
                ItemAction.registerInventory(cocktail.extraRequiredMix != -1 ? cocktail.extraRequiredMix : cocktail.pourProduct, "add-ingreds", (player, item) -> cocktail.garnish(player));
                for (Item item : cocktail.extraIngredients) {
                    ItemItemAction.register(cocktail.extraRequiredMix != -1 ? cocktail.extraRequiredMix : cocktail.pourProduct, item.getId(), (player, primary, secondary) -> cocktail.garnish(player));
                }
            }
        }
    }
}
