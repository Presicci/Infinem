package io.ruin.model.activities.combat.ChampionChallenge;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/26/2024
 * Wiki: https://oldschool.runescape.wiki/w/Champions%27_Challenge
 */
public class ChampionChallenge {

    private static void victory(Player player, Champion champion) {
        player.getStats().addXp(StatType.Slayer, champion.getExperience(), true);
        player.getStats().addXp(StatType.Hitpoints, champion.getExperience(), true);
    }

    private static void victoryScroll(Player player, Champion champion) {
        player.openInterface(InterfaceType.MAIN, Interface.CHAMPION_DEFEATED_SCROLL);
        player.getPacketSender().sendString(Interface.CHAMPION_DEFEATED_SCROLL, 2, "Well done, you defeated the " + StringUtils.initialCaps(champion.name()) +  " Champion!");
        player.getPacketSender().sendItem(Interface.CHAMPION_DEFEATED_SCROLL, 3, champion.getScrollId(), 1);
        player.getPacketSender().sendString(Interface.CHAMPION_DEFEATED_SCROLL, 6, champion.getExperience() + " Slayer Xp");
        player.getPacketSender().sendString(Interface.CHAMPION_DEFEATED_SCROLL, 7, champion.getExperience() + " Hitpoint Xp");
    }

    static {
        MapListener.registerBounds(Bounds.fromRegion(12696))
                .onEnter(player -> {
                    player.teleportListener = p -> {
                        p.dialogue(new NPCDialogue(3321, "There's a ladder for a reason."));
                        return false;
                    };
                })
                .onExit((player, logout) -> {
                    player.teleportListener = null;
                });
    }
}
