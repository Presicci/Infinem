package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.Dialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Region;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/29/2023
 */
public enum BossLairPeek {
    KING_BLACK_DRAGON(1816, "commune", "feel", 9033),
    DAG_KINGS(30169, "peek", new OptionsDialogue("Peek which lair?",
            new Option("Normal lair", (player) -> peek(player, "peek down and see", 11589)),
            new Option("Slayer lair", (player) -> peek(player, "peek down and see", 11588))
    ).keepOpenWhenHit()),
    DAG_KINGS_LADDER(3831, 4, new OptionsDialogue("Peek which lair?",
            new Option("Normal lair", (player) -> peek(player, "peek down and see", 11589)),
            new Option("Slayer lair", (player) -> peek(player, "peek down and see", 11588))
    ).keepOpenWhenHit()),
    MOLE_LAIR(12202, "look-inside", "look inside the hole and see", 6992, 6993),
    KALPHITE_LAIR(23609, 3, 0, "peek down and see", 13972),
    KALPHITE_LAIR_CRACK(29705, "peek", 0, "peek down and see", 13972),
    KALPHITE_LAIR_CREVICE(16465, "listen", 0, "listen through the crack and can hear", 13972);

    private static void peek(Player player, String actionText, int... regions) {
        int count = 0;
        for (int region : regions) {
            count += Region.get(region).players.size();
        }
        peekMessage(player, actionText, count);
    }

    private static void peekMessage(Player player, String actionText, int count) {
        if (count == 0)
            player.sendMessage("You " + actionText + " nobody inside.");
        else if (count == 1)
            player.sendMessage("You " + actionText + " one adventurer inside.");
        else
            player.sendMessage("You " + actionText + " " + count + " adventurers inside.");
    }

    BossLairPeek(int objectId, String option, String actionText, int... regions) {
        ObjectAction.register(objectId, option, (player, obj) -> peek(player, actionText, regions));
    }

    BossLairPeek(int objectId, int option, String actionText, int... regions) {
        ObjectAction.register(objectId, option, (player, obj) -> peek(player, actionText, regions));
    }

    BossLairPeek(int objectId, String option, Dialogue dialogue) {
        ObjectAction.register(objectId, option, (player, obj) -> player.dialogue(dialogue));
    }

    BossLairPeek(int objectId, int option, Dialogue dialogue) {
        ObjectAction.register(objectId, option, (player, obj) -> player.dialogue(dialogue));
    }

    BossLairPeek(int objectId, String option, int z, String actionText, int region) {
        ObjectAction.register(objectId, option, (player, obj) -> peekMessage(player, actionText, (int) Region.get(region).players.stream().filter(p -> p.getHeight() == z).count()));
    }

    BossLairPeek(int objectId, int option, int z, String actionText, int region) {
        ObjectAction.register(objectId, option, (player, obj) -> peekMessage(player, actionText, (int) Region.get(region).players.stream().filter(p -> p.getHeight() == z).count()));
    }
}
