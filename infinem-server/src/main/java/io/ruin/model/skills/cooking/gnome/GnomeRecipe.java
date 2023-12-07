package io.ruin.model.skills.cooking.gnome;

import io.ruin.model.entity.player.Player;

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
    FRUIT_BATTA,
    TOAD_BATTA,
    WORM_BATTA,
    VEGETABLE_BATTA,
    CHEESE_AND_TOMATO_BATTA,

    // Bowls
    WORM_HOLE,
    VEGETABLE_BALL,
    TANGLED_TOADS_LEGS,
    CHOCOLATE_BOMB,

    // Crunchies
    TOAD_CRUNCHIES,
    SPICY_CRUNCHIES,
    WORM_CRUNCHIES,
    CHOCCHIP_CRUNCHIES;

    private Consumer<Player> consumer;

    GnomeRecipe() {};

    GnomeRecipe(Consumer<Player> consumer) {
        this.consumer = consumer;
    }

    public static void create(Player player, int index) {
        GnomeRecipe recipe = values()[index];
        if (recipe.consumer != null) recipe.consumer.accept(player);
    }
}
