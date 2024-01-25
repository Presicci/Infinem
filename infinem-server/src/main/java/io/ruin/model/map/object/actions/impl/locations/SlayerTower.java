package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.combat.Hit;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.StatType;

public class SlayerTower {

    static {
        /**
         * Ladder to basement
         */
        ObjectAction.register(30191, 3417, 3535, 0, "climb-down", (player, obj) -> Ladder.climb(player, 3412, 9932, 3, false, true, false));
        ObjectAction.register(30192, 3412, 9931, 3, "climb-up", (player, obj) -> Ladder.climb(player, 3417, 3536, 0, true, true, false));

        /**
         * Stairs
         */
        ObjectAction.register(2114, 3434, 3537, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3433, player.getAbsY(), player.getHeight() + 1));
        ObjectAction.register(2118, 3434, 3537, 1, "climb-down", (player, obj) -> player.getMovement().teleport(3438, player.getAbsY(), player.getHeight() - 1));
        ObjectAction.register(2119, 3413, 3540, 1, "climb-up", (player, obj) -> player.getMovement().teleport(3417, player.getAbsY(), player.getHeight() + 1));
        ObjectAction.register(2120, 3415, 3540, 2, "climb-down", (player, obj) -> player.getMovement().teleport(3412, player.getAbsY(), player.getHeight() - 1));

        /**
         * Shortcuts
         */
        ObjectAction.register(16537, 1, (player, obj) -> {
            if (obj.x == 3422 && obj.y == 3550) {
                if (!player.getStats().check(StatType.Agility, 61, "climb the chain")) return;
                if (player.getEquipment().hasId(4168) || Slayer.hasSlayerHelmEquipped(player)) {
                    Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() + 1, true, true, false);
                    player.getStats().addXp(StatType.Agility, 3.0, true);
                } else {
                    player.dialogue(
                            new MessageDialogue("A foul stench seems to be seeping down from the floor above... it could be dangerous up there without a nosepeg."),
                            new OptionsDialogue("Go up anyway?",
                                    new Option("Yes", () -> {
                                        Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() + 1, true, true, false);
                                        player.getStats().addXp(StatType.Agility, 4.0, false);
                                    }),
                                    new Option("No", player::closeDialogue))
                    );
                }
            } else if(obj.x == 3447 && obj.y == 3576) {
                if (!player.getStats().check(StatType.Agility, 71, "climb the chain")) return;
                Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() + 1, true, true, false);
                player.getStats().addXp(StatType.Agility, 3.0, true);
            }
        });
        ObjectAction.register(16538, 1, (player, obj) -> {
            if(obj.x == 3422 && obj.y == 3550) {
                if (!player.getStats().check(StatType.Agility, 61, "climb the chain")) return;
                player.hit(new Hit().randDamage(5));
                Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() - 1, false, true, false);
            } else if(obj.x == 3447 && obj.y == 3576) {
                if (!player.getStats().check(StatType.Agility, 71, "climb the chain")) return;
                player.hit(new Hit().randDamage(5));
                Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() - 1, false, true, false);
            }
        });
    }
}
