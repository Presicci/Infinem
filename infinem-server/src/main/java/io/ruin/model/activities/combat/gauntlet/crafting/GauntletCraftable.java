/**
 *
 */
package io.ruin.model.activities.combat.gauntlet.crafting;

/**
 * @author Greco
 * @since 04/08/2021
 */
public interface GauntletCraftable<T> {

    public int[][] getMaterials();

    public int getProduct();

}
