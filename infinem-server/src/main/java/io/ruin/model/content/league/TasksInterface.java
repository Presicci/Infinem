package io.ruin.model.content.league;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/27/2021
 */
public class TasksInterface {

    public static void open(Player player) {
        Config.LEAGUE_FILTER.set(player, 4);
        Config.LEAGUE_AREA_FILTER.set(player, 0);
        Config.LEAGUE_POINTS.set(player, 100);
        Config.LEAGUE_POINTS2.set(player, 100);
        Config.LEAGUE_TASKS_COMPLETED.set(player, 5);
        Config.LEAGUE_A.set(player, 1);
        Config.LEAGUE_THING.set(player, 0);
        player.openInterface(InterfaceType.MAIN, Interface.LEAGUE_TASK);
        player.getPacketSender().sendClientScript(3202,
                "iiiiiiiiiiiiiii",
                Interface.LEAGUE_TASK << 16 | 8, // 1 Scrollbar attach
                Interface.LEAGUE_TASK << 16 | 9,     // 2 Task background
                Interface.LEAGUE_TASK << 16 | 10,    // 3 Task name
                Interface.LEAGUE_TASK << 16 | 11,    // 4 Reward
                Interface.LEAGUE_TASK << 16 | 12,    // 5 Type
                Interface.LEAGUE_TASK << 16 | 13,    // 6 Area
                Interface.LEAGUE_TASK << 16 | 14,    // 7 Description
                Interface.LEAGUE_TASK << 16 | 15,    // 8 Plus sprite
                Interface.LEAGUE_TASK << 16 | 16,    // 9 Difficulty
                Interface.LEAGUE_TASK << 16 | 17,     // 10 No tasks message
                Interface.LEAGUE_TASK << 16 | 7,     // 11 Scroll
                Interface.LEAGUE_TASK << 16 | 25,    // 12 Dropdown button?
                Interface.LEAGUE_TASK << 16 | 26,    // 13 Dropdown menu
                Interface.LEAGUE_TASK << 16 | 1,    // 14 Dropdown hover?
                Interface.LEAGUE_TASK << 16 | 24     // 15 Close button
        );
    }



}
