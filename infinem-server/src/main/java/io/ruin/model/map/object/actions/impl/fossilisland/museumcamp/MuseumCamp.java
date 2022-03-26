package io.ruin.model.map.object.actions.impl.fossilisland.museumcamp;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.Containers;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Material;
import io.ruin.model.skills.cooking.Cooking;
import io.ruin.model.skills.cooking.Food;
import io.ruin.model.skills.crafting.Loom;
import io.ruin.model.skills.crafting.SpinningWheel;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public class MuseumCamp {

    private static void interract(Player player, GameObject obj, CampBuildable buildable) {
        if (buildable.getConfig().get(player) == 0) {
            build(player, buildable);
        } else {
            switch (buildable) {
                case BANK:
                    player.getBank().open();
                    break;
                case SPINNING_WHEEL:
                    SpinningWheel.spinningOptions(player);
                    break;
                case LOOM:  // TODO implement loom first
                    break;
                case COOKING_POT:
                    Cooking.findCookable(player, obj);
                    break;
                case CLEANING_TABLE:    // TODO implement cleaning table
                    break;
                default:
                    break;
            }
        }
    }

    private static void build(Player player, CampBuildable buildable) {
        if (!player.getStats().check(StatType.Construction, buildable.getLevel(), "build this")) {
            return;
        }
        for (Item item : buildable.getItems()) {
            if (!player.getInventory().contains(item)) {
                neededMaterialsMessage(player, buildable);
                return;
            }
        }
        for (int nail : Material.NAIL_TYPES) {
            if (player.getInventory().contains(nail, buildable.getNails())) {
                player.getInventory().removeAll(false, buildable.getItems());
                player.getInventory().remove(nail, buildable.getNails());
                player.animate(3683);
                player.getStats().addXp(StatType.Construction, buildable.getExperience(), true);
                buildable.getConfig().set(player, 1);
                return;
            }
        }
        neededMaterialsMessage(player, buildable);
    }

    private static void neededMaterialsMessage(Player player, CampBuildable buildable) {
        StringBuilder message = new StringBuilder();
        message.append("You need ");
        for (Item item : buildable.getItems()) {
            message.append(item.getAmount() > 1 ? item.getAmount() + " " + item.getDef().name + (item.getDef().name.contains("clay") || item.getDef().name.contains("logs") ? ", " : "s, ") : item.getDef().descriptiveName + ", ");
        }
        if (buildable.getNails() > 0) {
            message.append(buildable.getNails()).append(" nails, ");
        }
        message.append("a hammer and a saw to build this.");
        player.dialogue(new ItemDialogue().two(buildable.getItems()[0].getId(), Items.IRON_NAILS, message.toString()));
    }

    static {
        for (CampBuildable buildable : CampBuildable.values()) {
            ObjectAction.register(buildable.getObjectId(), 1, ((player, obj) -> interract(player, obj, buildable)));
            ObjectAction.register(buildable.getObjectId(), 2, ((player, obj) -> interract(player, obj, buildable)));
        }
        // Well registration
        for (Containers container : Containers.values()) {
            ItemObjectAction.register(container.empty, CampBuildable.WELL.getObjectId(), (player, item, obj) -> {
                if (CampBuildable.WELL.getConfig().get(player) == 1) {
                    Containers.fillContainer(player, item, container, Containers.WaterSource.WELL);
                }
            });
        }
        // Cooking pot registration
        for (Food food : Food.values()) {
            ItemObjectAction.register(food.rawID, 31430, (player, item, obj) -> {
                if (CampBuildable.COOKING_POT.getConfig().get(player) == 1) {
                    Cooking.cook(player, food, obj, 897, true);
                }
            });
        }
        Loom.registerLoom(31432);
    }
}
