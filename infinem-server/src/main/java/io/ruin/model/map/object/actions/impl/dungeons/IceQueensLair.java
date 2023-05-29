package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/29/2023
 */
public class IceQueensLair {

    static {
        // East ladders
        ObjectAction.register(17385, 2857, 9917, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2858, 3517, 0), true, true, false)));
        ObjectAction.register(17385, 2859, 9919, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2860, 3519, 0), true, true, false)));
        ObjectAction.register(16680, 2857, 3517, 0, "climb-down", ((player, obj) -> Ladder.climb(player, new Position(2858, 9917), false, true, false)));
        ObjectAction.register(16680, 2859, 3519, 0, "climb-down", ((player, obj) -> Ladder.climb(player, new Position(2860, 9919), false, true, false)));
        // Middle ladders
        ObjectAction.register(16680, 2848, 3519, 0, "climb-down", ((player, obj) -> Ladder.climb(player, new Position(2849, 9919, 0), false, true, false)));
        ObjectAction.register(16680, 2845, 3516, 0, "climb-down", ((player, obj) -> Ladder.climb(player, new Position(2844, 9916, 0), false, true, false)));
        ObjectAction.register(16680, 2848, 3513, 0, "climb-down", ((player, obj) -> Ladder.climb(player, new Position(2847, 9913, 0), false, true, false)));
        ObjectAction.register(17385, 2848, 9919, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2847, 3519, 0), true, true, false)));
        ObjectAction.register(17385, 2845, 9916, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2844, 3516, 0), true, true, false)));
        ObjectAction.register(17385, 2848, 9913, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2847, 3513, 0), true, true, false)));
        // West ladders
        ObjectAction.register(16680, 2823, 3513, 0, "climb-down", ((player, obj) -> Ladder.climb(player, new Position(2823, 9914, 0), false, true, false)));
        ObjectAction.register(16680, 2827, 3510, 0, "climb-down", ((player, obj) -> Ladder.climb(player, new Position(2826, 9910, 0), false, true, false)));
        ObjectAction.register(16680, 2824, 3507, 0, "climb-down", ((player, obj) -> Ladder.climb(player, new Position(2823, 9907, 0), false, true, false)));
        ObjectAction.register(17385, 2823, 9913, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2822, 3513, 0), true, true, false)));
        ObjectAction.register(17385, 2827, 9910, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2826, 3510, 0), true, true, false)));
        ObjectAction.register(17385, 2824, 9907, 0, "climb-up", ((player, obj) -> Ladder.climb(player, new Position(2823, 3507, 0), true, true, false)));
        // Trapdoor ladders (blocked for now)
        ObjectAction.register(17385, 2845, 9925, 0, "climb-up", ((player, obj) -> player.dialogue(new PlayerDialogue("The trapdoor at the top won't budge."))));
        ObjectAction.register(17385, 2837, 9927, 0, "climb-up", ((player, obj) -> player.dialogue(new PlayerDialogue("The trapdoor at the top won't budge."))));
        ObjectAction.register(17385, 2823, 9930, 0, "climb-up", ((player, obj) -> player.dialogue(new PlayerDialogue("The trapdoor at the top won't budge."))));
    }
}
