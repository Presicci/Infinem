package io.ruin.model.inter.handlers.makeover;

import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.def.db.DBRowDefinition;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/19/2024
 */
public class MakeoverInterface {

    private static final Config INTERFACE_TYPE = Config.varpbit(4130, false);

    private static final String ORIGINAL_STYLE_KEY = "ORIGINAL_STYLE";
    private static final String ORIGINAL_COLOR_KEY = "ORIGINAL_COLOR";
    private static final String NPC_KEY = "MAKEOVER_NPC";
    private static final String TYPE_KEY = "MAKEOVER_TYPE";
    private static final String STYLE_KEY = "SELECTED_STYLE";
    private static final String COLOR_KEY = "SELECTED_COLOR";

    public static void open(Player player, MakeoverType type, NPC npc) {
        player.putTemporaryAttribute(NPC_KEY, npc);
        player.setInterfaceUnderlay(-1, -2);
        INTERFACE_TYPE.setInstant(player, type.ordinal());
        player.openInterface(InterfaceType.MAIN, 516);
        player.getPacketSender().sendAccessMask(516, 7, 0, 300, AccessMasks.ClickOp1);  // Styles
        player.getPacketSender().sendAccessMask(516, 24, 0, 59, AccessMasks.ClickOp1);  // Colors
        player.getPacketSender().sendAccessMask(516, 27, 9, 9, AccessMasks.ClickOp1);   // Apply
        player.getPacketSender().sendAccessMask(516, 28, 9, 9, AccessMasks.ClickOp1);   // Confirm
        player.getPacketSender().sendAccessMask(516, 31, 9, 24, AccessMasks.ClickOp1);  // Dropdown navigation
        player.putTemporaryAttribute(TYPE_KEY, type);
        player.putTemporaryAttribute(ORIGINAL_STYLE_KEY, player.getAppearance().styles[type.getStyle().getAppearanceIndex()]);
        player.putTemporaryAttribute(ORIGINAL_COLOR_KEY, player.getAppearance().colors[type.getColorIndex()]);
        player.removeTemporaryAttribute(STYLE_KEY);
        player.removeTemporaryAttribute(COLOR_KEY);
        player.startEvent(e -> {
            while (player.isVisibleInterface(516) || player.isVisibleInterface(205)) {
                World.sendGraphics(2372, 0, 0, player.getPosition());
                e.delay(1);
            }
        });
    }

    private static void resetAppearance(Player player) {
        MakeoverType type = player.getTemporaryAttributeOrDefault(TYPE_KEY, null);
        if (type == null) return;
        int originalStyle = player.getTemporaryAttributeOrDefault(ORIGINAL_STYLE_KEY, -1);
        int originalColor = player.getTemporaryAttributeOrDefault(ORIGINAL_COLOR_KEY, -1);
        if (originalStyle != -1) {
            player.getAppearance().styles[type.getStyle().getAppearanceIndex()] = originalStyle;
        }
        if (type.getColorIndex() != -1 && originalColor != -1) {
            player.getAppearance().colors[type.getColorIndex()] = originalColor;
        }
        player.getAppearance().update();
    }

    private static void clickStyle(Player player, int slot) {
        int index = slot / 5;
        player.getPacketSender().sendVarp(3788, index);
        player.putTemporaryAttribute(STYLE_KEY, index);
        MakeoverType type = MakeoverType.values()[INTERFACE_TYPE.get(player)];
        int styleIndex = type.getStyle().getAppearanceIndex();
        EnumDefinition enumDef = EnumDefinition.get(type.getEnumId());
        if (enumDef == null) return;
        int dbRowId = enumDef.getIntValuesArray()[index];
        DBRowDefinition rowDef = DBRowDefinition.get(dbRowId);
        if (rowDef == null) return;
        int styleId = (int) (player.getAppearance().isMale() ? rowDef.columnValues[1][0] : rowDef.columnValues[2][0]);
        if (styleIndex != -1) {
            player.getAppearance().styles[styleIndex] = styleId;
            player.getAppearance().update();
        }
        player.setInterfaceUnderlay(-1, -1);
    }

    private static void clickColor(Player player, int slot) {
        int index = slot / 2;
        player.getPacketSender().sendVarp(3789, index);
        player.putTemporaryAttribute(COLOR_KEY, index);
        MakeoverType type = MakeoverType.values()[INTERFACE_TYPE.get(player)];
        int colorIndex = type.getColorIndex();
        if (colorIndex != -1) {
            player.getAppearance().colors[colorIndex] = index;
            player.getAppearance().update();
        }
    }

    private static void apply(Player player) {
        MakeoverType type = player.getTemporaryAttributeOrDefault(TYPE_KEY, null);
        if (type == null) return;
        player.putTemporaryAttribute(ORIGINAL_STYLE_KEY, player.getAppearance().styles[type.getStyle().getAppearanceIndex()]);
        player.putTemporaryAttribute(ORIGINAL_COLOR_KEY, player.getAppearance().colors[type.getColorIndex()]);
        NPC npc = player.getTemporaryAttributeOrDefault(NPC_KEY, null);
        if (npc != null) {
            if (npc.getId() == 534 && (type == MakeoverType.TOP || type == MakeoverType.LEGS || type == MakeoverType.ARMS))
                player.getTaskManager().doLookupByUUID(908, 1); // Change Clothes at Thessalia's Makeovers in Varrock
            if (npc.getId() == 1305 && (type == MakeoverType.HAIR || type == MakeoverType.FACIAL_HAIR))
                player.getTaskManager().doLookupByUUID(435, 1); // Get a Haircut at the Barber in Falador
        }
    }

    private static void confirm(Player player) {
        apply(player);
        player.closeInterface(InterfaceType.MAIN);
    }

    private static void clickMakeoverType(Player player, int slot) {
        if (slot == 14) {
            BodyTypeInterface.open(player);
        } else {
            int index = (slot - 10) / 2;
            MakeoverType type = MakeoverType.values()[index];
            resetAppearance(player);
            open(player, type, null);
        }
    }

    static {
        InterfaceHandler.register(516, h -> {
            h.actions[7] = (SlotAction) MakeoverInterface::clickStyle;
            h.actions[24] = (SlotAction) MakeoverInterface::clickColor;
            h.actions[27] = (SimpleAction) MakeoverInterface::apply;
            h.actions[28] = (SimpleAction) MakeoverInterface::confirm;
            h.actions[31] = (SlotAction) MakeoverInterface::clickMakeoverType;
            h.closedAction = (player, integer) -> resetAppearance(player);
        });
    }
}
