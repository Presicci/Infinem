package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.PassableDoor;
import io.ruin.model.stat.StatList;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/27/2023
 */
public class LegendsGuild {

    private static void guildGate(Player player, GameObject obj, int offset) {
        StatList stats = player.getStats();
        if (player.getPosition().getY() == 3349
                && (stats.get(StatType.Agility).currentLevel < 50 || stats.get(StatType.Crafting).currentLevel < 50
                || stats.get(StatType.Herblore).currentLevel < 45 || stats.get(StatType.Magic).currentLevel < 56
                || stats.get(StatType.Mining).currentLevel < 52 || stats.get(StatType.Prayer).currentLevel < 42
                || stats.get(StatType.Smithing).currentLevel < 50 || stats.get(StatType.Strength).currentLevel < 50
                || stats.get(StatType.Thieving).currentLevel < 50 || stats.get(StatType.Woodcutting).currentLevel < 50)) {
            player.dialogue(new MessageDialogue("You need 45 herblore, 56 magic, 52 mining, 42 prayer, as well as level 50 in agility, crafting, smithing, strength, thieving, and woodcutting to enter the Heroes guild."));
            return;
        }
        PassableDoor.passDoor(player, obj, Direction.NORTH, offset);
    }

    static {
        // Guild front gate
        ObjectAction.register(2392, 2729, 3349, 0, "open", ((player, obj) -> guildGate(player, obj, 0)));
        ObjectAction.register(2391, 2728, 3349, 0, "open", ((player, obj) -> guildGate(player, obj, -2)));
        // Guild front door
        ObjectAction.register(2897, 2729, 3373, 0, "open", ((player, obj) -> PassableDoor.passDoor(player, obj, Direction.NORTH, 0)));
        ObjectAction.register(2896, 2728, 3373, 0, "open", ((player, obj) -> PassableDoor.passDoor(player, obj, Direction.NORTH, -2)));
        // Stairs up
        ObjectAction.register(15653, 2732, 3377, 0, "climb-up", ((player, obj) -> player.getMovement().teleport(2732, 3380, 1)));
        ObjectAction.register(15654, 2732, 3378, 1, "climb-down", ((player, obj) -> player.getMovement().teleport(2732, 3376, 0)));
        // Stairs basement
        ObjectAction.register(16665, 2724, 9774, 0, "climb-up", ((player, obj) -> player.getMovement().teleport(2723, 3375, 0)));
        ObjectAction.register(16664, 2724, 3374, 0, "climb-down", ((player, obj) -> player.getMovement().teleport(2727, 9774, 0)));
        // Totem pole
        ObjectAction.register(2638, 2729, 3378, 0, "look", ((player, obj) -> {
            player.dialogue(new MessageDialogue("A magical totem capable of charging skills necklaces and combat bracelets."));
        }));
    }
}
