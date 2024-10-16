package io.ruin.model.skills.construction.actions;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.TeleportTab;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.skills.magic.spells.modern.*;
import io.ruin.model.skills.magic.tablets.MagicTablet;
import io.ruin.model.skills.magic.tablets.MagicTabletType;
import io.ruin.model.skills.magic.tablets.TabletCreation;
import io.ruin.model.skills.mining.ShootingStar;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.construction.Buildable.*;

public class Study {

    enum Lectern {
        OAK(OAK_LECTERN, MagicTabletType.OAK),

        OAK_EAGLE(EAGLE_LECTERN, MagicTabletType.OAK_EAGLE),
        TEAK_EAGLE(TEAK_EAGLE_LECTERN, MagicTabletType.TEAK_EAGLE),
        MAHOGANY_EAGLE(MAHOGANY_EAGLE_LECTERN, MagicTabletType.MAHOGANY_EAGLE),

        OAK_DEMON(DEMON_LECTERN, MagicTabletType.OAK_DEMON),
        TEAK_DEMON(TEAK_DEMON_LECTERN, MagicTabletType.TEAK_DEMON),
        MAHOGANY_DEMON(MAHOGANY_DEMON_LECTERN, MagicTabletType.MAHOGANY_DEMON);

        final Buildable buildable;
        final MagicTabletType tabletType;

        Lectern(Buildable buildable, MagicTabletType tabletType) {
            this.buildable = buildable;
            this.tabletType = tabletType;
        }

        public void open(Player player) {
            TabletCreation.open(player, tabletType);
        }
    }

    static {
        for (Lectern l : Lectern.values()) {
            ObjectAction.register(l.buildable.getBuiltObjects()[0], "study", (player, obj) -> l.open(player));
        }

        /*
         * Telescope
         */
        ObjectAction.register(Buildable.WOODEN_TELESCOPE.getBuiltObjects()[0], "observe", (player, obj) -> player.dialogue(new MessageDialogue(ShootingStar.getTelescopeString(24))));
        ObjectAction.register(Buildable.TEAK_TELESCOPE.getBuiltObjects()[0], "observe", (player, obj) -> player.dialogue(new MessageDialogue(ShootingStar.getTelescopeString(9))));
        ObjectAction.register(Buildable.MAHOGANY_TELESCOPE.getBuiltObjects()[0], "observe", (player, obj) -> player.dialogue(new MessageDialogue(ShootingStar.getTelescopeString(2))));
    }
}
