package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;

public class ShantayPass {

    private static void enter(Player player) {
        player.getInventory().remove(Items.SHANTAY_PASS, 1);
        player.sendMessage("The guard takes your Shantay Pass as you go through the gate.");
        if (player.getEquipment().hasId(Items.DESERT_SHIRT) && player.getEquipment().hasId(Items.DESERT_ROBE) && player.getEquipment().hasId(Items.DESERT_BOOTS))
            player.getTaskManager().doLookupByUUID(642);    // Enter the Desert with a Set of Desert Robes Equipped
    }

    static {
        ObjectAction.register(4031, 1, (player, obj) -> {
            if (player.getAbsY() > obj.y) { // Entering the desert
                if (player.getInventory().getAmount(Items.SHANTAY_PASS) < 1) {
                    player.dialogue(new NPCDialogue(4648, "You'll need a Shantay pass to go through the gate into the desert. See Shantay, he'll sell you one for a very reasonable price."));
                    return;
                }
                enter(player);
            }
            player.step(0, player.getAbsY() > obj.y ? -2 : 2, StepType.FORCE_WALK);
        });
        NPCAction.register(4648, "pass", (player, npc) -> {
            if (player.getInventory().getAmount(Items.SHANTAY_PASS) < 1) {
                player.dialogue(new NPCDialogue(4648, "You'll need a Shantay pass to go through the gate into the desert. See Shantay, he'll sell you one for a very reasonable price."));
                return;
            }
            player.startEvent(e -> {
                player.lock();
                enter(player);
                player.faceNone(false);
                player.stepAbs(3304, 3115, StepType.FORCE_WALK);
                e.delay(3);
                player.unlock();
            });
        });
    }
}
