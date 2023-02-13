package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.stat.StatType;

public class Nurses {

    public static void heal(Player player, NPC npc) {
        if (player.getStats().get(StatType.Hitpoints).currentLevel >= player.getStats().get(StatType.Hitpoints).fixedLevel) {
            if (npc != null)
                player.dialogue(new NPCDialogue(npc, "You look healthy to me!"));
            return;
        }
        player.getStats().get(StatType.Hitpoints).restore();

        if (npc != null) {
            npc.faceTemp(player);
            player.dialogue(new NPCDialogue(npc, "There you go, you should be all set. Stay safe out there."));
            npc.animate(1161);
        }
        player.graphics(436, 48, 0);
        player.privateSound(958);
    }

    static {
        NPCAction.register(3343, "heal", Nurses::heal); // Master surgeon tafani
        NPCAction.register(3342, "heal", Nurses::heal); // Sabreen
        NPCAction.register(3341, "heal", Nurses::heal); // A'abla
        NPCAction.register(3344, "heal", Nurses::heal); // Jaraah
    }

}
