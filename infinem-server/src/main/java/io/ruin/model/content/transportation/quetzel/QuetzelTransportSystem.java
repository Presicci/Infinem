package io.ruin.model.content.transportation.quetzel;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/16/2024
 */
@Getter
@AllArgsConstructor
public class QuetzelTransportSystem {

    // 2=green, 3=gold, 4=blue, 5=cyan, 6=green and orange
    private static final Config RENU = Config.varpbit(9951, true).defaultValue(2);
    // Bitmask of all locked location indexes
    private static final Config INTERFACE_UNLOCKS = Config.varp(4182, false).defaultValue(2528);

    private static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, 874);
        player.getPacketSender().sendAccessMask(874, 15, 0, 15, AccessMasks.ClickOp1);
    }

    private static void click(Player player, int slot) {
        QuetzelDestination quetzelDestination = QuetzelDestination.values()[slot];
        Position destination = quetzelDestination.getDestination();
        if (player.getPosition().distance(destination) < 4) {
            player.sendMessage("You are already at that destination.");
            return;
        }
        player.closeInterface(InterfaceType.MAIN);
        Traveling.fadeTravel(player, destination, 5);
        player.privateSound(8501, 3, 55);
    }

    static {
        NPCAction.registerIncludeVariants(13350, 1, (player, npc) -> open(player));
        InterfaceHandler.register(874, h -> {
            h.actions[15] = (SlotAction) QuetzelTransportSystem::click;
        });
    }
}
