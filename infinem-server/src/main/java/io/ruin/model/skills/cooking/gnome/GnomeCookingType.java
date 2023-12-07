package io.ruin.model.skills.cooking.gnome;

public enum GnomeCookingType {
    COCKTAIL(GnomeRecipe.FRUIT_BLAST, GnomeRecipe.PINEAPPLE_PUNCH, GnomeRecipe.WIZARD_BLIZZARD, GnomeRecipe.SHORT_GREEN_GUY, GnomeRecipe.DRUNK_DRAGON, GnomeRecipe.CHOCOLATE_SATURDAY, GnomeRecipe.BLURBERRY_SPECIAL),
    BATTA(GnomeRecipe.FRUIT_BATTA, GnomeRecipe.TOAD_BATTA, GnomeRecipe.WORM_BATTA, GnomeRecipe.VEGETABLE_BATTA, GnomeRecipe.CHEESE_AND_TOMATO_BATTA),
    CRUNCHIES(GnomeRecipe.TOAD_CRUNCHIES, GnomeRecipe.SPICY_CRUNCHIES, GnomeRecipe.WORM_CRUNCHIES, GnomeRecipe.CHOCCHIP_CRUNCHIES),
    GNOME_BOWLS(GnomeRecipe.WORM_HOLE, GnomeRecipe.VEGETABLE_BALL, GnomeRecipe.TANGLED_TOADS_LEGS, GnomeRecipe.CHOCOLATE_BOMB);

    private final GnomeRecipe[] recipes;

    GnomeCookingType(GnomeRecipe... recipes) {
        this.recipes = recipes;
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
