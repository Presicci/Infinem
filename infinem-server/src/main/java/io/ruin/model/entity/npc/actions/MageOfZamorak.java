package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.impl.jewellery.AbyssalBracelet;
import io.ruin.model.skills.runecrafting.Abyss;

public class MageOfZamorak {

    private static final int MAGE_OF_ZAMORAK = 2581;

    private static void teleport(Player player, NPC npc) {
        player.startEvent(event -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE);
            npc.lock();
            npc.faceTemp(player);
            npc.forceText("Veniens! Sallakar! Rinnesset!");
            npc.animate(1818, 1);
            npc.graphics(343, 100, 1);
            event.delay(2);
            player.unlock();
            // Abyssal bracelet only stops skull in osrs
            // https://oldschool.runescape.wiki/w/Abyssal_bracelet#(5)
            if (!AbyssalBracelet.test(player)) {
                player.getCombat().skullNormal(1000);
                player.getPrayer().drain(1000); //1000 is just a safe "drain all"
            }
            Abyss.randomize(player);
            player.getMovement().teleport(Random.get(Abyss.OUTER_TELEPORTS));
            player.resetAnimation();
            npc.unlock();
        });
    }

    static {
        NPCAction.register(MAGE_OF_ZAMORAK, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "This isn't the place to talk. Visit me in Varrock's Chaos Temple if you have something to discuss. Unless you're here to teleport or buy something?").animate(557),
                    new OptionsDialogue(
                            new Option("Let's see what you're selling.", new PlayerDialogue("Let's see what you're selling."), new ActionDialogue(() -> npc.openShop(player))),
                            new Option("Could you teleport me to the Abyss?", new PlayerDialogue("Could you teleport me to the Abyss?"), new ActionDialogue(() -> teleport(player, npc)))
                    )
            );
        });
        NPCAction.register(MAGE_OF_ZAMORAK, "teleport", MageOfZamorak::teleport);
    }
}
