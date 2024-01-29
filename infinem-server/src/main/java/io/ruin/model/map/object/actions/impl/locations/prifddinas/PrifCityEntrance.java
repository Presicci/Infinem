package io.ruin.model.map.object.actions.impl.locations.prifddinas;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class PrifCityEntrance {

    private static final int[] ELVES = {9151, 9084, 9114, 9109, 9116, 9085, 9115};
    private static final StatType[] SKILLS = {
            StatType.Agility, StatType.Construction, StatType.Farming, StatType.Herblore,
            StatType.Hunter, StatType.Mining, StatType.Smithing, StatType.Woodcutting
    };

    public static boolean prifSkillCheck(Player player) {
        for (StatType statType : SKILLS) {
            if (player.getStats().get(statType).currentLevel < 70) {
                player.dialogue(new NPCDialogue(ELVES[Random.get(ELVES.length - 1)], "You need level 70 in " + statType.name() + " to enter Prifddinas.").animate(588));
                return false;
            }
        }
        return true;
    }

    public static boolean prifSkillCheckNoNPC(Player player) {
        for (StatType statType : SKILLS) {
            if (player.getStats().get(statType).currentLevel < 70) {
                player.dialogue(new MessageDialogue("You need level 70 in " + statType.name() + " to enter Prifddinas."));
                return false;
            }
        }
        return true;
    }

    private static void entrance(Player player, Position pos) {
        player.startEvent(e -> {
            if (prifSkillCheck(player)) {
                Traveling.fadeTravel(player, pos);
            }
        });
    }

    static {
        // West entrance
        ObjectAction.register(36519, 2182, 3326, 0, "enter", (player, obj) -> entrance(player, new Position(3208, 6079, 0)));
        ObjectAction.register(36518, 2182, 3328, 0, "enter", (player, obj) -> entrance(player, new Position(3208, 6080, 0)));
        // West exit
        ObjectAction.register(36522, 3206, 6080, 0, "exit", (player, obj) -> Traveling.fadeTravel(player, new Position(2181, 3328, 0)));
        ObjectAction.register(36523, 3206, 6078, 0, "exit", (player, obj) -> Traveling.fadeTravel(player, new Position(2181, 3327, 0)));
        // South entrance
        ObjectAction.register(36518, 2238, 3270, 0, "enter", (player, obj) -> entrance(player, new Position(3263, 6024, 0)));
        ObjectAction.register(36519, 2240, 3270, 0, "enter", (player, obj) -> entrance(player, new Position(3264, 6024, 0)));
        // South exit
        ObjectAction.register(36522, 3262, 6022, 0, "exit", (player, obj) -> Traveling.fadeTravel(player, new Position(2239, 3269, 0)));
        ObjectAction.register(36523, 3264, 6022, 0, "exit", (player, obj) -> Traveling.fadeTravel(player, new Position(2240, 3269, 0)));
        // East entrance
        ObjectAction.register(36519, 2296, 3328, 0, "enter", (player, obj) -> entrance(player, new Position(3319, 6080, 0)));
        ObjectAction.register(36518, 2296, 3326, 0, "enter", (player, obj) -> entrance(player, new Position(3319, 6079, 0)));
        // East exit
        ObjectAction.register(36523, 3320, 6080, 0, "exit", (player, obj) -> Traveling.fadeTravel(player, new Position(2298, 3328, 0)));
        ObjectAction.register(36522, 3320, 6078, 0, "exit", (player, obj) -> Traveling.fadeTravel(player, new Position(2298, 3327, 0)));
        // North entrance
        ObjectAction.register(36518, 2240, 3384, 0, "enter", (player, obj) -> entrance(player, new Position(3264, 6135, 0)));
        ObjectAction.register(36519, 2238, 3384, 0, "enter", (player, obj) -> entrance(player, new Position(3263, 6135, 0)));
        // North exit
        ObjectAction.register(36523, 3262, 6136, 0, "exit", (player, obj) -> Traveling.fadeTravel(player, new Position(2239, 3386, 0)));
        ObjectAction.register(36522, 3264, 6136, 0, "exit", (player, obj) -> Traveling.fadeTravel(player, new Position(2240, 3386, 0)));
    }
}
