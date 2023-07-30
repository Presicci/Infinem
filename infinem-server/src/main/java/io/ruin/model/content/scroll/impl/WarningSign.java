package io.ruin.model.content.scroll.impl;

import io.ruin.cache.Color;
import io.ruin.model.content.scroll.Scroll;
import io.ruin.model.content.scroll.ScrollLine;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/30/2023
 */
public class WarningSign {
    static {
        Scroll scroll = new Scroll(Arrays.asList(
                new ScrollLine(2, Color.DARK_RED.wrap("~-~-~ WARNING ~-~-~")),
                new ScrollLine(4, Color.DARK_RED.wrap("Darkness envelops this cave.")),
                new ScrollLine(5, Color.DARK_RED.wrap("A light source is required to explore.")),
                new ScrollLine(7, Color.DARK_RED.wrap("Beware of vicious head-grabbing beasts!")),
                new ScrollLine(8, Color.DARK_RED.wrap("Contact a Slayer Master for protective headgear."))
        ));
        ObjectAction.register(15566, "read", ((player, obj) -> scroll.open(player)));
    }
}
