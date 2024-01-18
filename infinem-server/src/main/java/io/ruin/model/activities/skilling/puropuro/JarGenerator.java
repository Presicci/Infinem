package io.ruin.model.activities.skilling.puropuro;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/18/2024
 */
public class JarGenerator {

    private static void removeCharges(Player player, Item item, int removedCharges) {
        int charges = item.getCharges() - removedCharges;
        if (charges <= 0) {
            player.sendMessage("Your jar generator has run out of charges and disappears.");
            item.remove();
        } else {
            item.setCharges(charges);
        }
    }

    private static void takeJar(Player player, Item item, int jarId) {
        if (!player.getInventory().hasFreeSlots(1)) {
            player.sendMessage("You need a free inventory slot to take a jar.");
            return;
        }
        player.getInventory().addOrDrop(jarId, 1);
        removeCharges(player, item, (jarId == Items.IMPLING_JAR ? 3 : 1));
    }

    private static void check(Player player, Item item) {
        player.sendMessage("Your jar generator has " + item.getCharges() + " charges remaining.");
    }

    static {
        ItemAction.registerInventory(Items.JAR_GENERATOR, "impling-jar", (player, item) -> takeJar(player, item, Items.IMPLING_JAR));
        ItemAction.registerInventory(Items.JAR_GENERATOR, "butterfly-jar", (player, item) -> takeJar(player, item, Items.BUTTERFLY_JAR));
        ItemAction.registerInventory(Items.JAR_GENERATOR, "check", JarGenerator::check);
    }
}
