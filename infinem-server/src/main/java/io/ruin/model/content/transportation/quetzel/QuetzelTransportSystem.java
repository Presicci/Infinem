package io.ruin.model.content.transportation.quetzel;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
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
    protected static final Config RENU = Config.varpbit(9951, true).defaultValue(2);
    // Bitmask of all locked location indexes
    private static final Config INTERFACE_UNLOCKS = Config.varp(4182, false).defaultValue(2528);
    private static final String LAST_DESTINATION_KEY = "LAST_QUETZEL";

    private static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, 874);
        player.getPacketSender().sendAccessMask(874, 15, 0, 15, AccessMasks.ClickOp1);
    }

    private static void lastDestination(Player player) {
        Position destination = player.getTemporaryAttributeOrDefault(LAST_DESTINATION_KEY, null);
        if (destination == null) {
            player.sendMessage("You don't have a last destination.");
            return;
        }
        if (player.getPosition().distance(destination) < 4) {
            player.sendMessage("You are already at that destination.");
            return;
        }
        teleport(player, destination);
    }

    protected static void teleport(Player player, Position destination) {
        Traveling.fadeTravel(player, destination, 5);
        player.privateSound(8501, 3, 55);
    }

    private static void click(Player player, int slot) {
        QuetzelDestination quetzelDestination = QuetzelDestination.values()[slot];
        Position destination = quetzelDestination.getDestination();
        if (player.getPosition().distance(destination) < 4) {
            player.sendMessage("You are already at that destination.");
            return;
        }
        player.closeInterface(InterfaceType.MAIN);
        player.putTemporaryAttribute(LAST_DESTINATION_KEY, destination);
        teleport(player, destination);
    }

    private static void pet(Player player, NPC npc) {
        Position dest = npc.getCentrePosition().translate(npc.spawnDirection, 2);
        player.lock();
        player.startEvent(e -> {
            player.stepAbs(dest.getX(), dest.getY(), StepType.FORCE_WALK);
            e.waitForMovement(player);
            e.delay(1);
            player.face(npc);
            player.animate(11122);
            npc.animate(11123);
            e.delay(2);
            player.unlock();
        });
    }

    static {
        NPCAction.registerIncludeVariants(13355, 1, (player, npc) -> open(player));
        NPCAction.registerIncludeVariants(13355, 3, (player, npc) -> lastDestination(player));
        NPCAction.registerIncludeVariants(13355, 4, QuetzelTransportSystem::pet);
        //NPCAction.registerIncludeVariants(12888, "travel", (player, npc) -> teleport(player, new Position(1703, 3140)));
        NPCAction.registerIncludeVariants(12889, "travel", (player, npc) -> teleport(player, new Position(3280, 3412)));
        InterfaceHandler.register(874, h -> {
            h.actions[15] = (SlotAction) QuetzelTransportSystem::click;
        });
    }
}
