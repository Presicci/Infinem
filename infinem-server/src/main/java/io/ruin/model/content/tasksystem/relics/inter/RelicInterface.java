package io.ruin.model.content.tasksystem.relics.inter;

import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.RelicManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/19/2024
 */
public class RelicInterface {

    /**
     * VB 10032 - 1 = twisted, 2 = trailblazer, 3 = infinem, 4 = trailblazer II
     * struct 3771 - Infinem 'league'
     * enum 9000 - Infinem relic tiers
     * struct 9000 - tier 1
     * struct 9001 - tier 2
     *      1019 - Passive struct
     *      877 - points required int
     *      878 - relics enum\
     * enum 9001 - tier 1 relics
     * struct 9002 - tier 1 passives
     * enum 9002 - tier 2 relics
     * struct 9003 - tier 2 passives
     *
     * varp 2615 - points
     *
     * relic container enum - i, J, -1, 1 starting index
     *
     * relic structs
     * 1722 - endless harvest
     * 1723 - production master
     * 4730 - trickster
     *
     * 1725 - fairy's flight
     * 4717 - globetrotter
     *
     * 730 - eye of the artisan
     * 731 - gift of the gatherer
     * 732 - way of the warrior
     */

    public static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.RELICS);
        player.getPacketSender().sendAccessMask(Interface.RELICS, 12, 0, 5, AccessMasks.ClickOp1);
    }

    private static int getRelicStatus(Player player, Relic relic) {
        RelicManager rm = player.getRelicManager();
        if (rm.hasRelicInTier(relic)) return 1;
        if (!rm.hasPointsForRelic(relic)) return 0;
        if (!rm.hasRelicBefore(relic)) return 3;
        return 2;
    }

    private static void clickRelic(Player player, int slot) {
        InterfaceRelic[] relics = InterfaceRelic.values();
        if (slot >= relics.length) return;
        InterfaceRelic relic = relics[slot];
        int relicStruct = relic.getStruct();
        player.getPacketSender().sendClientScript(
                3193,
                "iiiiiiiiiiiiiiiiiii",
                Interface.RELICS << 16 | 6,  // widget0 - hide then dont unhide
                Interface.RELICS << 16 | 16, // widget1 - parent of overlay
                Interface.RELICS << 16 | 13,   // widget2 - hide
                Interface.RELICS << 16 | 28, // widget3 - sprite
                Interface.RELICS << 16 | 17,   // widget4 - relic name
                Interface.RELICS << 16 | 22,   // widget5 - Relic Effect:
                Interface.RELICS << 16 | 23,   // widget6 - relic description
                Interface.RELICS << 16 | 26,   // widget7 - select button
                Interface.RELICS << 16 | 27,   // widget8 - back button
                Interface.RELICS << 16 | 33,   // arg9 - confirmation window component?
                Interface.RELICS << 16 | 36,   // widget10 - confirmation title?
                Interface.RELICS << 16 | 37,   // widget11 - confirmation window text
                Interface.RELICS << 16 | 38,   // widget12 - confirm button
                Interface.RELICS << 16 | 39,   // widget13 - cancel button
                Interface.RELICS << 16 | 44,   // widget14 - Passive Effect:
                Interface.RELICS << 16 | 45,   // widget15 - passive
                getRelicStatus(player, relic.getRelic()),   // arg16 - 2 if player can take the relic, 0 if cant, 1 if already have relic, 3 if need tier before
                relicStruct,   // arg17 - relic struct
                1740   // arg18 - passive effect struct
        );
        player.putTemporaryAttribute("SEL_RELIC", relic.getRelic());
    }

    private static void confirm(Player player) {
        Relic relic = player.getTemporaryAttribute("SEL_RELIC");
        player.getRelicManager().takeRelic(relic);
        player.closeInterface(InterfaceType.MAIN);
    }

    static {
        InterfaceHandler.register(Interface.RELICS, h -> {
            h.actions[12] = (SlotAction) RelicInterface::clickRelic;
            h.actions[38] = (SimpleAction) RelicInterface::confirm;
        });
    }
}
