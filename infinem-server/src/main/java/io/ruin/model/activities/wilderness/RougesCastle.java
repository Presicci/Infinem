package io.ruin.model.activities.wilderness;


import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class RougesCastle {

    static {

        /**
         * Ladders
         */
        ObjectAction.register(14735, 3280, 3936, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3282, 3936, player.getHeight() + 1, true, true, true);
        });
        /* first floor */
        ObjectAction.register(14736, 3280, 3936, 1, "climb", (player, obj) -> player.dialogue(
                new OptionsDialogue("Climb up or down the ladder?",
                        new Option("Climb Up.", () -> Ladder.climb(player, 3282, 3936, player.getHeight() + 1, true, true, true)),
                        new Option("Climb Down.", () -> Ladder.climb(player, 3281, 3935, player.getHeight() - 1, false, true, true))
                )));
        ObjectAction.register(14736, 3280, 3936, 1, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3282, 3936, player.getHeight() + 1, true, true, true);
        });
        ObjectAction.register(14736, 3280, 3936, 1, "climb-down", (player, obj) -> {
            Ladder.climb(player, 3281, 3935, player.getHeight() - 1, false, true, true);
        });
        /* second floor */
        ObjectAction.register(14736, 3280, 3936, 2, "climb", (player, obj) -> player.dialogue(
                new OptionsDialogue("Climb up or down the ladder?",
                        new Option("Climb Up.", () -> Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() + 1, true, true, true)),
                        new Option("Climb Down.", () -> Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() - 1, false, true, true))
                )));
        ObjectAction.register(14736, 3280, 3936, 2, "climb-up", (player, obj) -> {
            Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() + 1, true, true, true);
        });
        ObjectAction.register(14736, 3280, 3936, 2, "climb-down", (player, obj) -> {
            Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() - 1, false, true, true);
        });
        ObjectAction.register(14737, 3281, 3936, 3, "climb-down", (player, obj) -> {
            Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() - 1, false, true, true);
        });
    }
}
