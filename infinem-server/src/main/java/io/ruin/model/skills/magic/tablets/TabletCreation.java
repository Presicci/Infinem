package io.ruin.model.skills.magic.tablets;

import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/27/2024
 */
// Modern tablet creation is handled in skills.construction.actions.Study
public class TabletCreation {

    private static final Config TABLET_SET = Config.varp(4074, false);
    private static final Config LECTERN_TYPE = Config.varpbit(10599, false);
    private static final Config LECTERN_SELECTION = Config.varpbit(10600, false);
    private static final String LECTERN_KEY = "LECTERN";

    public static void open(Player player, MagicTabletType type) {
        LECTERN_TYPE.set(player, type.getVbIndex());
        player.putTemporaryAttribute(LECTERN_KEY, type);
        TABLET_SET.set(player, type.getTabletEnum());
        player.openInterface(InterfaceType.MAIN, 403);
    }

    private static void create(Player player) {
        MagicTabletType type = player.getTemporaryAttributeOrDefault(LECTERN_KEY, MagicTabletType.OAK);
        if (type == null) return;
        List<MagicTablet> tablets = MagicTablet.getTablets(type);
        int selection = LECTERN_SELECTION.get(player) - 1;
        if (selection < 0) return;
        MagicTablet tablet = tablets.get(selection);
        if (tablet == null) return;
        tablet.create(player, Config.IQ.get(player));
    }

    static {
        ObjectAction.register(40357, "study", (player, obj) -> {
            if (!AreaReward.ANCIENT_MAGIC.checkReward(player, "make ancient teleport tablets.")) return;
            open(player, MagicTabletType.ANCIENT);
        });
        ObjectAction.register(40358, "study", (player, obj) -> open(player, MagicTabletType.LUNAR));
        ObjectAction.register(28802, "study", (player, obj) -> open(player, MagicTabletType.ARCEUUS));
        InterfaceHandler.register(403, h -> {
            h.actions[5] = (SimpleAction) player -> Config.IQ.set(player, 1);
            h.actions[6] = (SimpleAction) player -> Config.IQ.set(player, 5);
            h.actions[7] = (SimpleAction) player -> Config.IQ.set(player, 10);
            h.actions[8] = (SimpleAction) player -> player.integerInput("Enter amount:", amt -> Config.IQ.set(player, amt));
            h.actions[9] = (SimpleAction) player -> Config.IQ.set(player, 28);
            h.actions[14] = (SimpleAction) TabletCreation::create;
            for (int index = 19; index <= 39; index++) {
                int slot = index - 18;
                h.actions[index] = (SimpleAction) player -> LECTERN_SELECTION.set(player, slot);
            }
        });
    }
}
