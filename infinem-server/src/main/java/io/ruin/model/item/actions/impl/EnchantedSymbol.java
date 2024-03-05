package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.impl.HotColdClue;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/25/2024
 */
public class EnchantedSymbol {

    private static final Position[] SPAWN_ORIGINS = new Position[]{
            new Position(3333, 3903, 0),
            new Position(3025, 3830, 0),
            new Position(3170, 3792, 0)
    };

    private static void track(Player player, int index) {
        if (player.getInventory().hasId(21797 + index)) {
            player.sendMessage("You've already defeated that opponent. Return the component to Kolodion.");
            return;
        }
        Position origin = SPAWN_ORIGINS[index];
        boolean closeEnough = HotColdClue.feel(player, origin, "The symbol is ");
        player.hit(new Hit().fixedDamage(Random.get(3, 16)));
        player.sendFilteredMessage("The power of the symbol hurts you in the process.");
        if (closeEnough) {
            NPC monster = new NPC(7858 + index);
            monster.spawn(origin);
            monster.removeIfIdle(player);
            monster.removalAction = ((p) -> p.removeTemporaryAttribute("MA2_BOSS"));
            monster.removeOnDeath();
            monster.targetPlayer(player, false);
            monster.attackTargetPlayer();
            player.putTemporaryAttribute("MA2_BOSS", null);
        }
    }

    private static void activateSymbol(Player player) {
        if (player.hasTemporaryAttribute("MA2_BOSS")) {
            player.sendMessage("A divine presence blocks the magic of the symbol.");
            return;
        }
        int questProgress = player.getAttributeIntOrZero("MA2");
        List<Option> optionList = new ArrayList<>();
        if (questProgress >= 8) {
            optionList.add(new Option("Saradomin", () -> track(player, 0)));
            optionList.add(new Option("Guthix", () -> track(player, 1)));
            optionList.add(new Option("Zamorak", () -> track(player, 2)));
        } else {
            if ((questProgress & 1) != 1) {
                optionList.add(new Option("Saradomin", () -> track(player, 0)));
            }
            if ((questProgress & 2) != 2) {
                optionList.add(new Option("Guthix", () -> track(player, 1)));
            }
            if ((questProgress & 3) != 3) {
                optionList.add(new Option("Zamorak", () -> track(player, 2)));
            }
        }
        optionList.add(new Option("Cancel"));
        player.dialogue(new OptionsDialogue(optionList));
    }

    static {
        ItemAction.registerInventory(21800, "activate", (player, item) -> activateSymbol(player));
    }
}
