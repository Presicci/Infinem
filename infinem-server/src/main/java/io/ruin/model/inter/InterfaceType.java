package io.ruin.model.inter;

import io.ruin.cache.def.EnumDefinition;
import io.ruin.model.entity.player.Player;

import static io.ruin.network.incoming.handlers.DisplayHandler.DEFAULT_SCREEN_CHILD_OFFSET;

public enum InterfaceType {
    /**
     * Basic types
     */
    MAIN(
            16,
            false
    ),
    INVENTORY(
            74,
            false
    ),
    INVENTORY_OVERLAY(
            74,
            true
    ),
    SOCIAL_TAB(
            85,
            true
    ),
    SIDE_PRAYER(
            81,
            true
    ),
    CHATBOX(
            162,
            565,
            false
    ),
    /**
     * Not sure how the following values effect the overlays, but they're the only difference in client..
     *   xAlignment = 0
     *   yAlignment = 0
     */
    WILDERNESS_OVERLAY( //unique type required for bounty hunter
            3,
            true
    ),
    UNUSED_OVERLAY1(
            2,
            true
    ),
    POPUP_NOTIFICATION_OVERLAY(
            13,
            true
    ),
    /**
     * Not sure how the following values effect the overlays, but they're the only difference in client..
     *   xAlignment = 1
     *   yAlignment = 1
     */
    SECONDARY_OVERLAY( //fading, castle wars game, snow falling
            6,
            true
    ),
    PRIMARY_OVERLAY( //castle wars wait lobby, duel challenge area, gnomeball, puro puro imp view, mage training arena, lms lobby, tut
            1,
            true
    ),
    TARGET_OVERLAY( //kinda guessing it uses this, doesn't really matter, as long as it has it's own type..
            3,
            true
    ),

    POH_LOADING(
            1,
            false
    ),

    WORLD_MAP(
            18,
            true
    ),
    ;

    public final int interfaceId;

    public final int resizableComponent;

    public final int overlaySetting;

    InterfaceType(int resizableComponent, boolean overlay) {
        this.interfaceId = -1;
        this.resizableComponent = resizableComponent;
        this.overlaySetting = overlay ? 1 : 0;
    }

    InterfaceType(int interfaceId, int resizableComponent, boolean overlay) {
        this.interfaceId = interfaceId;
        this.resizableComponent = resizableComponent;
        this.overlaySetting = overlay ? 1 : 0;
    }

    public void open(Player player, int id) {
        Component component = getComponent(player);
        player.getPacketSender().sendInterface(id, component.parentId, component.childId, overlaySetting);
    }

    public void close(Player player) {
        Component component = getComponent(player);
        player.getPacketSender().removeInterface(component.parentId, component.childId);
    }

    public static final int getComponent(int gameframe, int resizableComponent) {
        DisplayEnum en = DisplayEnum.getEnum(gameframe);

        if (en == null)
            return -1;

        EnumDefinition e = EnumDefinition.get(en.enumId);

        int bitpacked = e.getValuesAsInts().get((161 << 16) | resizableComponent);
        return bitpacked == 0 ? -1 : bitpacked & 0xFFFF;
    }

    private Component getComponent(Player player) {
        if (interfaceId != -1) {
            return new Component(interfaceId, resizableComponent);
        }

        return new Component(player.getGameFrameId(), getComponent(player.getGameFrameId(), resizableComponent));
    }

    public static void open(Player player, int containerParent, int containerChild, int interfaceId) {
        player.getPacketSender().sendInterface(interfaceId, containerParent, containerChild, 0);
    }

    public static void close(Player player, int containerParent, int containerChild) {
        player.getPacketSender().removeInterface(containerParent, containerChild);
    }

    public enum DisplayEnum {
        FIXED_MODE(548, 1129),
        RESIZABLE_MODE(161, 1130),
        RESIZABLE_PANELS(164, 1131),
        FULL_SCREEN(165, 1132),
        ORB_OF_OCULUS(16, -1),
        CHATBOX(162, -1),
        MOBILE(601, 1745);

        public final int gameframe;
        public final int enumId;

        DisplayEnum(int gameframe, int enumId) {
            this.gameframe = gameframe;
            this.enumId = enumId;
        }

        public static DisplayEnum getEnum(int gameframe) {
            for (DisplayEnum e : values()) {
                if (e.gameframe == gameframe)
                    return e;
            }
            return null;
        }
    }

    private static final class Component {

        final int parentId, childId;

        Component(int parentId, int childId) {
            this.parentId = parentId;
            this.childId = childId;
        }

    }
}