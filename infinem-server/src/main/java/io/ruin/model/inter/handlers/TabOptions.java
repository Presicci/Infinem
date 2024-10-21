package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.handlers.settings.Settings;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.skills.construction.HouseViewer;
import io.ruin.network.incoming.handlers.DisplayHandler;

public class TabOptions {

    static {
        InterfaceHandler.register(Interface.OPTIONS, h -> {
            //h.actions[5] = pkskullprevent
            h.actions[32] = (SimpleAction) Settings::open;
            h.actions[44] = (SimpleAction) Config.ZOOMING_DISABLED::toggle;

            /**
             * Display
             */
            h.actions[41] = (DefaultAction) (p, option, slot, itemId) -> DisplayHandler.updateGameframe(p, slot);

            /**
             * Brightness
             */
            h.actions[23] = (SlotAction) (p, slot) -> Config.BRIGHTNESS.set(p, slot*5);

            /**
             * Controls
             */
            h.actions[29] = (SimpleAction) Config.ACCEPT_AID::toggle;
            h.actions[30] = (SimpleAction) p -> p.getMovement().toggleRunning();
            h.actions[31] = (SimpleAction) HouseOptions::open;
            h.actions[33] = (SimpleAction) p -> p.sendMessage("Bonds");
            h.actions[38] = (SlotAction) (p, slot) -> Config.PLAYER_ATTACK_OPTION.set(p, slot - 1);
            h.actions[39] = (SlotAction) (p, slot) -> Config.NPC_ATTACK_OPTION.set(p, slot - 1);

            /**
             * Audio
             */
            h.actions[104] = (SlotAction) (player, i) -> {
                if (i != 0) {
                    if (Config.MUSIC_VOLUME.get(player) == 0) {
                        player.getMusic().restartCurrent();
                    }
                }
                Config.MUSIC_VOLUME.set(player, i);
            };
            h.actions[121] = (SimpleAction) Config.MUSIC_UNLOCK_MESSAGE::toggle;
        });
    }

    /**
     * House
     */

    private static final class HouseOptions {

        static {
            InterfaceHandler.register(Interface.HOUSE_OPTIONS, h -> {
                h.actions[1] = (SimpleAction) HouseViewer::open;
                h.actions[5] = (SimpleAction) p -> {
                    if (p.getBankPin().requiresVerification(player -> setBuildingMode(player, 1)))
                        return;
                    setBuildingMode(p, 1);
                };
                h.actions[6] = (SimpleAction) p -> setBuildingMode(p, 0);
                h.actions[8] = (SimpleAction) p -> Config.TELEPORT_INSIDE.set(p, 0);
                h.actions[9] = (SimpleAction) p -> Config.TELEPORT_INSIDE.set(p, 1);
                //h.actions[11] = (SimpleAction) p -> DEFAULT_BUILD_MODE TRUE
                //h.actions[12] = (SimpleAction) p -> DEFAULT_BUILD_MODE FALSE
                h.actions[14] = (SimpleAction) p -> Config.RENDER_DOORS_MODE.set(p, 0);
                h.actions[17] = (SimpleAction) p -> Config.RENDER_DOORS_MODE.set(p, 1);
                h.actions[19] = (SimpleAction) p -> Config.RENDER_DOORS_MODE.set(p, 2);
                h.actions[20] = (SimpleAction) p -> {
                    if (!p.isInOwnHouse())
                        p.sendMessage("You're not in your house.");
                    else
                        p.house.expelGuests();
                };
                h.actions[21] = (SimpleAction) p -> {
                    if (p.getCurrentHouse() == null)
                        p.sendMessage("You're not in a house.");
                    else
                        p.getCurrentHouse().leave(p);
                };
                h.actions[22] = (SimpleAction) p -> {
                    if (!p.isInOwnHouse()) {
                        p.sendMessage("You're not in your house.");
                        return;
                    }
                    if (!p.house.isHasBellPull()) {
                        p.sendMessage("Your house must have a bell-pull in order to use this feature.");
                        return;
                    }
                    p.house.callServant();
                };
                h.actions[24] = (SimpleAction) p -> p.closeInterface(InterfaceType.INVENTORY_OVERLAY);
            });
        }

        private static void setBuildingMode(Player player, int value) {
            if(Config.BUILDING_MODE.get(player) != value) {
                Config.BUILDING_MODE.set(player, value);
                if (player.isInOwnHouse()) {
                    player.house.expelGuests();
                    player.house.buildAndEnter(player, player.getPosition().localPosition(), value == 1);
                }
            }
        }

        private static void open(Player player) {
            player.openInterface(InterfaceType.INVENTORY, Interface.HOUSE_OPTIONS);
            player.getPacketSender().sendString(370, 16, "Number of rooms: " + (player.house == null ? "0" : player.house.getRoomCount()));
        }

    }

}