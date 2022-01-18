package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class LizardmanTemple {

    private static void BarrierNSRoomOne(Player player, GameObject barrier) {
        player.startEvent(event -> {
            if(player.getAbsY() == 10091) {
                player.stepAbs(player.getAbsX(), 10093, StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Barrier.");
            } else {
                player.stepAbs(player.getAbsX(), 10091, StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Barrier.");
            }
        });
    }

    private static void BarrierCorridorOne(Player player, GameObject barrier) {
        player.startEvent(event -> {
            if(player.getAbsX() == 1296) {
                player.stepAbs(player.getAbsX()+2, player.getAbsY(), StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Barrier.");
            } else {
                player.stepAbs(player.getAbsX()-2, player.getAbsY(), StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Barrier.");
            }
        });
    }

    private static void BarrierRoomTwo(Player player, GameObject barrier) {
        player.startEvent(event -> {
            if(player.getAbsX() == 1307) {
                player.stepAbs(player.getAbsX()+2, player.getAbsY(), StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Barrier.");
            } else {
                player.stepAbs(player.getAbsX()-2, player.getAbsY(), StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Barrier.");
            }
        });
    }

    private static void BarrierCorridorTwo(Player player, GameObject barrier) {
        player.startEvent(event -> {
            if(player.getAbsX() == 1316) {
                player.stepAbs(player.getAbsX()+2, player.getAbsY(), StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Barrier.");
            } else {
                player.stepAbs(player.getAbsX()-2, player.getAbsY(), StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Barrier.");
            }
        });
    }

    private static void BarrierRoomThree(Player player, GameObject barrier) {
        player.startEvent(event -> {
            if(player.getAbsX() == 1324) {
                player.stepAbs(player.getAbsX()+2, player.getAbsY(), StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Barrier.");
            } else {
                player.stepAbs(player.getAbsX()-2, player.getAbsY(), StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Barrier.");
            }
        });
    }

    static {
        // not working? TODO
        ObjectAction.register(34642,"pass", LizardmanTemple::BarrierNSRoomOne);
        ObjectAction.register(34643,"pass", LizardmanTemple::BarrierCorridorOne);
        ObjectAction.register(34644,"pass", LizardmanTemple::BarrierRoomTwo);
        ObjectAction.register(34645,"pass", LizardmanTemple::BarrierCorridorTwo);
        ObjectAction.register(34646,"pass", LizardmanTemple::BarrierRoomThree);

        // Entrance / exit
        ObjectAction.register(34405, "enter", (player, obj) -> Traveling.fadeTravel(player, new Position(1312, 10086, 0)));
        ObjectAction.register(34404, "enter", (player, obj) -> Traveling.fadeTravel(player, new Position(1330, 10070, 0)));
        ObjectAction.register(34403, "enter", (player, obj) -> Traveling.fadeTravel(player, new Position(1314, 10064, 0)));
        ObjectAction.register(34402, "enter", (player, obj) -> Traveling.fadeTravel(player, new Position(1292, 10058, 0)));
        ObjectAction.register(34422, "jump-in", (player, obj) -> Traveling.fadeTravel(player, new Position(1284, 3678, 0)));
    }
}

