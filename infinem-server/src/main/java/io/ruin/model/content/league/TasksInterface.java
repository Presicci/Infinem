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
        Config.LEAGUE_A.set(player, 0);
        Config.LEAGUE_THING.set(player, 0);
        player.openInterface(InterfaceType.MAIN, 657);
        player.getPacketSender().sendClientScript(3202,
                "iiiiiiiiiiiiiii",
                Interface.COLLECTION_LOG << 16 | 9, // 1
                Interface.COLLECTION_LOG << 16 | 9,     // 2
                Interface.COLLECTION_LOG << 16 | 10,    // 3
                Interface.COLLECTION_LOG << 16 | 11,    // 4
                Interface.COLLECTION_LOG << 16 | 12,    // 5
                Interface.COLLECTION_LOG << 16 | 13,    // 6
                Interface.COLLECTION_LOG << 16 | 14,    // 7
                Interface.COLLECTION_LOG << 16 | 15,    // 8
                Interface.COLLECTION_LOG << 16 | 16,    // 9
                Interface.COLLECTION_LOG << 16 | 8,     // 10
                Interface.COLLECTION_LOG << 16 | 7,     // 11 Scroll
                Interface.COLLECTION_LOG << 16 | 27,    // 12 Dropdown button?
                Interface.COLLECTION_LOG << 16 | 28,    // 13
                Interface.COLLECTION_LOG << 16 | 29,    // 14
                Interface.COLLECTION_LOG << 16 | 24     // 15 Close button
        );
    }



}
