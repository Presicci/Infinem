package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.cache.Color;
import io.ruin.model.activities.combat.pvminstance.InstanceDialogue;
import io.ruin.model.activities.combat.pvminstance.InstanceType;
import io.ruin.model.combat.Hit;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.slayer.Slayer;

public class SmokeDevilDungeon {
    private static final Bounds DUNGEON_BOUNDS = new Bounds(2317, 9418, 2431, 9471, -1);
    private static final Bounds DUNGEON_BOSS = new Bounds(2350, 9436, 2378, 9460, -1);

    private static int playerCount;

    static {
        /**
         * Entrance/exit
         */
        ObjectAction.register(30176, 2411, 3061, 0, "enter", (player, obj) -> {
            if (Slayer.hasFaceMask(player)) {
                player.getMovement().teleport(2404, 9415);
            } else {
                player.dialogue(new NPCDialogue(7654, "Hey you don't " + Color.COOL_BLUE.wrap("*cough*") + " wanna go in there without " + Color.COOL_BLUE.wrap("*cough*") +
                                " some kind of protection from the smoke. Your lungs aren't as tough as " + Color.COOL_BLUE.wrap("*wheeze*") + " mine."),
                        new OptionsDialogue(
                                new Option("Enter anyway.", () -> player.getMovement().teleport(2404, 9415)),
                                new Option("Stay outside.", player::closeDialogue)
                        ));
            }
        });
        ObjectAction.register(534, 2404, 9414, 0, "use", (player, obj) -> player.getMovement().teleport(2412, 3060));

        /**
         * Boss room
         */
        ObjectAction.register(535, 2378, 9452, 0, "use", (player, obj) -> InstanceDialogue.open(player, InstanceType.THERMONUCLEAR_SMOKE_DEVIL));
        ObjectAction.register(536, 2377, 9452, 0, "use", (player, obj) -> player.getMovement().teleport(2379, 9452));

        MapListener.registerBounds(DUNGEON_BOSS).onEnter(player -> playerCount++).onExit((player, logout) -> playerCount--);

        MapListener.registerBounds(DUNGEON_BOUNDS).onEnter(player -> player.addEvent(e -> {
            while(player.getPosition().inBounds(DUNGEON_BOUNDS)) {
                if(Slayer.hasFaceMask(player))
                    return;
                player.hit(new Hit().fixedDamage(20));
                e.delay(50);
            }
        }));
    }
}
