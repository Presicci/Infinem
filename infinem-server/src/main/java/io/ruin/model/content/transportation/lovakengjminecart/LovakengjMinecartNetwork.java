package io.ruin.model.content.transportation.lovakengjminecart;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/14/2024
 */
public class LovakengjMinecartNetwork {

    protected static final String KEY = "LOV_MC";

    private static void conductorDialogue(Player player, NPC npc, LovakengjMinecart fromMinecart) {
        player.dialogue(
                new NPCDialogue(npc, "Can I help you today?"),
                new OptionsDialogue(
                        new Option("Can I go for a ride?",
                                new PlayerDialogue("Can I go for a ride?"),
                                new ActionDialogue(() -> open(player, fromMinecart))),
                        new Option("I'm fine, thanks.",
                                new PlayerDialogue("I'm fine, thanks."))
                )
        );
    }

    private static void open(Player player, LovakengjMinecart fromMinecart) {
        if (!fromMinecart.hasUnlocked(player)) {
            fromMinecart.unlock(player);
        }
        final int cost = 20;
        Option[] options = Arrays.stream(LovakengjMinecart.values())
                .map(cart -> new Option((cart.hasUnlocked(player) ? "" : "<str>") + cart.getName(),
                        () -> {
                            if (cart == fromMinecart) {
                                player.dialogue(new NPCDialogue(fromMinecart.getConductorId(), "That's where we are. Pick a different location human."));
                                return;
                            }
                            if (player.getInventory().getAmount(995) < cost) {
                                player.dialogue(new NPCDialogue(fromMinecart.getConductorId(), "Oi, pocket a little light? I'm not running this service for free now."));
                                return;
                            }
                            cart.travel(player, cost);
                        })).toArray(Option[]::new);
        int bit = fromMinecart.getBit();
        if (player.getAttributeIntOrZero(KEY) <= bit) {
            player.dialogue(new NPCDialogue(fromMinecart.getConductorId(), "You haven't unlocked any other locations yet."));
            return;
        }
        OptionScroll.open(player, "Minecart rides: " + cost + "gp", true, options);
    }

    static {
        for (LovakengjMinecart minecart : LovakengjMinecart.values()) {
            ObjectAction.register(28835, minecart.getObjectPosition(), "travel", (player, obj) -> open(player, minecart));
            NPCAction.register(minecart.getConductorId(), "talk-to", (player, npc) -> conductorDialogue(player, npc, minecart));
            NPCAction.register(minecart.getConductorId(), "travel", (player, npc) -> open(player, minecart));
        }
    }
}
