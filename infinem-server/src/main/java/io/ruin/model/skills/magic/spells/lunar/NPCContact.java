package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.skills.slayer.master.*;
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
            (player) -> Turael.handleInteraction(player, new NPC(Turael.TURAEL), 1),
            (player) -> Mazchna.handleInteraction(player, new NPC(Mazchna.MAZCHNA), 1),
            (player) -> Vannaka.handleInteraction(player, new NPC(Vannaka.VANNAKA), 1),
            (player) -> Chaeldar.handleInteraction(player, new NPC(Chaeldar.CHAELDAR), 1),
            (player) -> Nieve.handleInteraction(player, new NPC(Nieve.NIEVE), 1),
            (player) -> Duradel.handleInteraction(player, new NPC(Duradel.DURADEL), 1),
            (player) -> Krystilia.handleInteraction(player, new NPC(Krystilia.KRYSTILIA), 1),
            (player) -> Konar.handleInteraction(player, new NPC(Konar.KONAR), 1)
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
        player.graphics(430);
        action.handle(player);
    }

    static {
        InterfaceHandler.register(Interface.NPC_CONTACT, h -> {
            h.actions[3] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[6] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[9] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[12] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[15] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[18] = (SimpleAction) (player) -> startDialogue(player, DIALOGUES[0]);
            h.actions[21] = (SimpleAction) (player) -> startDialogue(player, DIALOGUES[1]);
            h.actions[24] = (SimpleAction) (player) -> startDialogue(player, DIALOGUES[2]);
            h.actions[27] = (SimpleAction) (player) -> startDialogue(player, DIALOGUES[3]);
            h.actions[30] = (SimpleAction) (player) -> startDialogue(player, DIALOGUES[4]);
            h.actions[33] = (SimpleAction) (player) -> startDialogue(player, DIALOGUES[5]);
            h.actions[37] = (SimpleAction) (player) -> startDialogue(player, DIALOGUES[6]);
            h.actions[36] = (SimpleAction) (player) -> startDialogue(player, DIALOGUES[7]);
            h.actions[40] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[43] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[46] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[49] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[52] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[55] = (SimpleAction) NPCContact::defaultDialogue;
            h.actions[58] = (SimpleAction) NPCContact::randomDialogue;
        });
    }
}
