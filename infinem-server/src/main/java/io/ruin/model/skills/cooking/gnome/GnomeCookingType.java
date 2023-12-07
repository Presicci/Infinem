package io.ruin.model.skills.cooking.gnome;

import io.ruin.model.item.Items;
import lombok.Getter;

public enum GnomeCookingType {
    COCKTAIL(Items.COCKTAIL_SHAKER, "mix-cocktail",
            GnomeRecipe.FRUIT_BLAST, GnomeRecipe.PINEAPPLE_PUNCH, GnomeRecipe.WIZARD_BLIZZARD, GnomeRecipe.SHORT_GREEN_GUY,
            GnomeRecipe.DRUNK_DRAGON, GnomeRecipe.CHOCOLATE_SATURDAY, GnomeRecipe.BLURBERRY_SPECIAL),
    BATTA(Items.HALF_BAKED_BATTA, "prepare",
            GnomeRecipe.FRUIT_BATTA, GnomeRecipe.TOAD_BATTA, GnomeRecipe.WORM_BATTA, GnomeRecipe.VEGETABLE_BATTA,
            GnomeRecipe.CHEESE_AND_TOMATO_BATTA),
    CRUNCHY(Items.HALF_BAKED_CRUNCHY, "prepare",
            GnomeRecipe.TOAD_CRUNCHIES, GnomeRecipe.SPICY_CRUNCHIES, GnomeRecipe.WORM_CRUNCHIES,
            GnomeRecipe.CHOCCHIP_CRUNCHIES),
    BOWL(Items.HALF_BAKED_BOWL, "prepare",
            GnomeRecipe.WORM_HOLE, GnomeRecipe.VEGETABLE_BALL, GnomeRecipe.TANGLED_TOADS_LEGS, GnomeRecipe.CHOCOLATE_BOMB);

    @Getter private final int prepareItem;
    @Getter private final String prepareOption;
    private final GnomeRecipe[] recipes;

    GnomeCookingType(int prepareItem, String prepareOption, GnomeRecipe... recipes) {
        this.prepareItem = prepareItem;
        this.prepareOption = prepareOption;
        this.recipes = recipes;
        for (GnomeRecipe recipe : recipes) {
            recipe.setType(this);
        }
    }

    public boolean containsRecipeIndex(int index) {
        for (GnomeRecipe recipe : recipes) {
            if (recipe.ordinal() == index) return true;
        }
        return false;
    }

    public int getLowestRecipeIndex() {
        return recipes[0].ordinal();
    }

    public int getRecipeIndexBySlot(int slot) {
        return recipes[slot].ordinal();
    }
}
