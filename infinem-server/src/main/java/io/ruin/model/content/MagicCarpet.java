package io.ruin.model.content;

import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.RouteType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/26/2021
 */
public class MagicCarpet {

    private static int ANIM = 6936;

    private static Position[] gateToPoll = {
            new Position(3308, 3110),
            new Position(3349, 3004)
    };

    private static void carpetRide(Player player) {
        /*player.startEvent(e -> {
            player.lock();
            //RouteType route = player.getRouteFinder().routeAbsolute(gateToPoll[0].getX(), gateToPoll[0].getY());
            e.path(player, gateToPoll[0]);
            //player.animate();
            //e.delay(2);
            player.animate(ANIM);
            e.path(player, gateToPoll[1]);
            player.resetAnimation();
            player.unlock();
        });*/
    }


    // 20 Shanty pass
    // 17 uzer, camp, pollniv
    // 22 nardah, soph, menaphos
    // 21 Pollniv
    private static final Position northPollniv = new Position(3349, 3003);
    private static final Position southPollniv = new Position(3351, 2942);
    private static final Position camp = new Position(3180, 3045);
    private static final Position uzer = new Position(3469, 3113);
    private static final Position shantyPass = new Position(3308, 3110);
    private static final Position menaphos = new Position(3245, 2813);
    private static final Position sophanem = new Position(3285, 2813);
    private static final Position nardah = new Position(3401, 2916);


    static {
        NPCAction.register(17, "travel", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Pollnivneach", () -> Traveling.fadeTravel(player, northPollniv)),
                            new Option("Bedabin Camp", () -> Traveling.fadeTravel(player, camp)),
                            new Option("Uzer", () -> Traveling.fadeTravel(player, uzer))
                    ));
        });

        NPCAction.register(17, "talk-to", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Pollnivneach", () -> Traveling.fadeTravel(player, northPollniv)),
                            new Option("Bedabin Camp", () -> Traveling.fadeTravel(player, camp)),
                            new Option("Uzer", () -> Traveling.fadeTravel(player, uzer))
                    ));
        });

        NPCAction.register(20, "travel", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Shanty Pass", () -> Traveling.fadeTravel(player, shantyPass))
                    ));
        });

        NPCAction.register(20, "talk-to", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Shanty Pass", () -> Traveling.fadeTravel(player, shantyPass))
                    ));
        });

        NPCAction.register(18, "travel", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Pollnivneach", () -> Traveling.fadeTravel(player, southPollniv))
                    ));
        });

        NPCAction.register(18, "talk-to", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Pollnivneach", () -> Traveling.fadeTravel(player, southPollniv))
                    ));
        });

        NPCAction.register(22, "travel", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Nardah", () -> Traveling.fadeTravel(player, nardah)),
                            new Option("Sophanem", () -> Traveling.fadeTravel(player, sophanem)),
                            new Option("Menaphos", () -> Traveling.fadeTravel(player, menaphos))
                    ));
        });

        NPCAction.register(22, "talk-to", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Where do you want to travel?",
                            new Option("Nardah", () -> Traveling.fadeTravel(player, nardah)),
                            new Option("Sophanem", () -> Traveling.fadeTravel(player, sophanem)),
                            new Option("Menaphos", () -> Traveling.fadeTravel(player, menaphos))
                    ));
        });
    }
}
