package io.ruin.model.content.tasksystem.relics.inter;

import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.RelicManager;
import io.ruin.model.content.tasksystem.tasks.inter.TaskInterface;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.journal.JournalTab;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/19/2024
 */
public class RelicInterface {

    /**
     * Adding new relic?
     * - enum 9000 contains the struct references for the tiers
     * - make a struct for new tier
     *
     *
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
     * enum 9003 - tier 3 relics
     * struct 9004 - tier 3 relics
     * struct 9005 - tier 3 passives
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
     * 729 - dungeon hub premium (old natural selection)
     *
     * 730 - eye of the artisan
     * 731 - gift of the gatherer
     * 732 - way of the warrior
     */

    public static void open(Player player) {
        player.closeInterface(InterfaceType.WORLD_MAP); // Temp fix until i add resizable support for relics
        player.setInterfaceUnderlay(-1, -1);
        player.openInterface(InterfaceType.MAIN, Interface.RELICS);
        // Relics
        player.getPacketSender().sendAccessMask(Interface.RELICS, 22, 0, 100, AccessMasks.ClickOp1);
        // Navigation drop down
        player.getPacketSender().sendAccessMask(Interface.RELICS, 6, 9, 16, AccessMasks.ClickOp1);
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
                "iiiiiiiiiiiiiiiiiiiii",
                Interface.RELICS << 16 | 14,  // widget0 - hide then dont unhide
                Interface.RELICS << 16 | 52,   // unhides when clicking back button
                Interface.RELICS << 16 | 27,   // widget1 - parent of overlay
                Interface.RELICS << 16 | 24,   // widget2 - hide
                Interface.RELICS << 16 | 43,   // widget3 - sprite
                Interface.RELICS << 16 | 33,   // widget4 - relic name
                Interface.RELICS << 16 | 38,   // widget5 - Relic Effect:
                Interface.RELICS << 16 | 39,   // widget6 - relic description
                Interface.RELICS << 16 | 44,   // widget7 - select button
                Interface.RELICS << 16 | 46,   // widget8 - back button
                Interface.RELICS << 16 | 12,   // arg9 - confirmation window component?
                Interface.RELICS << 16 | 48,   // widget10 - confirmation title?
                Interface.RELICS << 16 | 49,   // widget11 - confirmation window text
                Interface.RELICS << 16 | 50,   // widget12 - confirm button
                Interface.RELICS << 16 | 51,   // widget13 - cancel button
                Interface.RELICS << 16 | 55,   // widget14 - Passive Effect:
                Interface.RELICS << 16 | 56,   // widget15 - passive
                Interface.RELICS << 16 | 23,   // unhides when clicking back button
                getRelicStatus(player, relic.getRelic()),   // arg16 - 2 if player can take the relic, 0 if cant, 1 if already have relic, 3 if need tier before
                relicStruct,   // arg17 - relic struct
                RelicManager.getPassiveStruct(relic.getRelic().getTier())   // arg18 - passive effect struct
        );
        player.putTemporaryAttribute("SEL_RELIC", relic.getRelic());
    }

    private static void navigation(Player player, int slot) {
        if (slot == 10) {   // Info

        } else if (slot == 12) {
            TaskInterface.openTaskInterface(player);
        } else if (slot == 14) {
            JournalTab.setTab(player, JournalTab.Tab.ACHIEVEMENT);
        }
    }

    private static void confirm(Player player) {
        Relic relic = player.getTemporaryAttribute("SEL_RELIC");
        player.getRelicManager().takeRelic(relic);
        player.closeInterface(InterfaceType.MAIN);
    }

    static {
        InterfaceHandler.register(Interface.RELICS, h -> {
            h.actions[22] = (SlotAction) RelicInterface::clickRelic;
            h.actions[6] = (SlotAction) RelicInterface::navigation;
            h.actions[50] = (SimpleAction) RelicInterface::confirm;
        });
    }
}
