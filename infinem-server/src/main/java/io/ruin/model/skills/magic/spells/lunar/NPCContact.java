package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.item.Item;
import io.ruin.model.skills.construction.mahoganyhomes.Amy;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.skills.slayer.Master;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/28/2022
 */
public class NPCContact extends Spell {

    private static final Item[] runes = {
            Rune.ASTRAL.toItem(1),
            Rune.COSMIC.toItem(1),
            Rune.AIR.toItem(2)
    };

    public NPCContact() {
        clickAction = (player, i) -> {
            if(!player.getStats().check(StatType.Magic, 67, "cast this spell"))
                return;
            RuneRemoval r = RuneRemoval.get(player, runes);
            if (r == null) {
                player.sendMessage("You don't have enough runes to cast this spell.");
                return;
            }
            player.openInterface(InterfaceType.MAIN, Interface.NPC_CONTACT);
        };
    }

    private static void defaultDialogue(Player player) {
        player.sendMessage("This NPC can't be contacted.");
    }

    private static void randomDialogue(Player player) {
        Random.get(DIALOGUES).handle(player);
    }

    private static final SimpleAction[] DIALOGUES = new SimpleAction[] {
            (player) -> Master.TURAEL.handleInteraction(player, 1),
            (player) -> Master.MAZCHNA.handleInteraction(player, 1),
            (player) -> Master.VANNAKA.handleInteraction(player, 1),
            (player) -> Master.CHAELDAR.handleInteraction(player, 1),
            (player) -> Master.NIEVE.handleInteraction(player, 1),
            (player) -> Master.DURADEL.handleInteraction(player, 1),
            (player) -> Master.KRYSTILIA.handleInteraction(player, 1),
            (player) -> Master.KONAR.handleInteraction(player, 1),
    };

    private static void startDialogue(Player player, SimpleAction action) {
        RuneRemoval r = RuneRemoval.get(player, runes);
        if (r == null) {
            player.sendMessage("You don't have enough runes to cast this spell.");
            return;
        }
        r.remove();
        player.getStats().addXp(StatType.Magic, 63, true);
        player.openInterface(InterfaceType.MAIN, Interface.NPC_CONTACT);
        player.animate(4413);
        player.graphics(728, 92, 0);
        player.privateSound(3618);
        action.handle(player);
    }

    static {
        InterfaceHandler.register(Interface.NPC_CONTACT, h -> {
            h.actions[5] = (SimpleAction) NPCContact::defaultDialogue;  // Honest Jimmy
            h.actions[8] = (SimpleAction) NPCContact::defaultDialogue;  // Bert the Sandman
            h.actions[11] = (SimpleAction) NPCContact::defaultDialogue;  // Advisor Ghrim
            h.actions[14] = (SimpleAction) NPCContact::defaultDialogue; // Dark Mage
            h.actions[17] = (SimpleAction) NPCContact::defaultDialogue; // Lanthus
            h.actions[20] = (SimpleAction) NPCContact::defaultDialogue; // Spria
            h.actions[21] = (SimpleAction) player -> startDialogue(player, DIALOGUES[0]); // Turael
            h.actions[24] = (SimpleAction) player -> startDialogue(player, DIALOGUES[1]); // Mazchna
            h.actions[27] = (SimpleAction) player -> startDialogue(player, DIALOGUES[2]); // Vannaka
            h.actions[30] = (SimpleAction) player -> startDialogue(player, DIALOGUES[3]); // Chaeldar
            h.actions[33] = (SimpleAction) player -> startDialogue(player, DIALOGUES[4]); // Nieve
            h.actions[36] = (SimpleAction) player -> startDialogue(player, DIALOGUES[5]); // Duradel
            h.actions[40] = (SimpleAction) player -> startDialogue(player, DIALOGUES[6]); // Krystilia
            h.actions[39] = (SimpleAction) player -> startDialogue(player, DIALOGUES[7]); // Konar
            h.actions[43] = (SimpleAction) NPCContact::defaultDialogue; // Murphy
            h.actions[46] = (SimpleAction) NPCContact::defaultDialogue; // Cyrisus
            h.actions[49] = (SimpleAction) NPCContact::defaultDialogue; // Smoggy
            h.actions[52] = (SimpleAction) NPCContact::defaultDialogue; // Ginea
            h.actions[55] = (SimpleAction) NPCContact::defaultDialogue; // Watson
            h.actions[58] = (SimpleAction) NPCContact::defaultDialogue; // Barbarian Guard
            h.actions[64] = (SimpleAction) player -> startDialogue(player, Amy::npcContactDialogue);
            h.actions[61] = (SimpleAction) NPCContact::randomDialogue;
        });
    }
}
