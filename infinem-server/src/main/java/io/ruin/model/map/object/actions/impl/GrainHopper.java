package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/16/2021
 */
public class GrainHopper {

    private static final int[] HOPPERS = { 24961, 24962, 24963, 2586, 36591 };
    private static final int[] HOPPER_CONTROLS = { 2484, 2607, 24966, 24965, 24964, 36592 };
    private static final int[] FLOUR_BINS = { 1781, 14960, 37343 };
    private static final int HOPPER_CONTROLS_PUSHED = 24967;

    private static final int GRAIN = 1947;
    private static final int POT = 1931;
    private static final int POT_OF_FLOUR = 1933;

    static {
        for (int controls : HOPPER_CONTROLS) {
            ObjectAction.register(controls, "Operate", (player, obj) -> {
                if (Config.FLOUR_BIN.get(player) == 30) {
                    player.sendMessage("The flour bin downstairs is full, I should empty it first.");
                } else {
                    player.startEvent(e -> {
                        player.lock();
                        player.animate(3571);
                        player.privateSound(2400);
                        if (controls == 24966 || controls == 24965 || controls == 24964 || controls == 2484) {
                            World.startEvent(event -> {
                                obj.setId(HOPPER_CONTROLS_PUSHED);
                                event.delay(2);
                                obj.setId(obj.originalId);
                            });
                        }
                        if (player.getTemporaryAttributeOrDefault(AttributeKey.GRAIN_IN_HOPPER, false)) {
                            Config.FLOUR_BIN.increment(player, 1);
                            Config.FLOUR_BIN_FULL.set(player, 1);
                            player.sendMessage("You operate the hopper. The grain slides down the chute.");
                            player.removeTemporaryAttribute(AttributeKey.GRAIN_IN_HOPPER);
                        } else {
                            player.sendMessage("You operate the empty hopper. Nothing interesting happens.");
                        }
                        e.delay(2);
                        player.unlock();
                    });
                }
            });
        }

        for (int hopper : HOPPERS) {
            ItemObjectAction.register(GRAIN, hopper, (player, item, obj) -> {
                if (!player.<Boolean>getTemporaryAttributeOrDefault(AttributeKey.GRAIN_IN_HOPPER, false)) {
                    if (player.getInventory().remove(GRAIN, 1) > 0) {
                        player.lock();
                        player.sendMessage("You put grain in the hopper.");
                        player.animate(3572);
                        player.privateSound(2567);
                        player.putTemporaryAttribute(AttributeKey.GRAIN_IN_HOPPER, true);
                        player.unlock();
                    }
                } else {
                    player.sendMessage("There is already grain in the hopper.");
                }
            });
            ObjectAction.register(hopper, "Fill", (player, obj) -> {
                if (player.getInventory().contains(GRAIN, 1)) {
                    if (!player.<Boolean>getTemporaryAttributeOrDefault(AttributeKey.GRAIN_IN_HOPPER, false)) {
                        if (player.getInventory().remove(GRAIN, 1) > 0) {
                            player.lock();
                            player.sendMessage("You put grain in the hopper.");
                            player.animate(3572);
                            player.privateSound(2567);
                            player.putTemporaryAttribute(AttributeKey.GRAIN_IN_HOPPER, true);
                            player.unlock();
                        }
                    } else {
                        player.sendMessage("There is already grain in the hopper.");
                    }
                } else {
                    player.sendMessage("You do not have any grain to put in the hopper.");
                }
            });
        }

        for (int flourBin : FLOUR_BINS) {
            ItemObjectAction.register(POT, flourBin, (player, item, obj) -> {
                if (Config.FLOUR_BIN.get(player) == 0) {
                    player.sendMessage("There is no flour in the bin.");
                    return;
                }
                if (player.getInventory().remove(POT, 1) > 0) {
                    player.startEvent(e -> {
                        player.lock();
                        player.animate(3572);
                        player.getInventory().add(POT_OF_FLOUR, 1);
                        if (Config.FLOUR_BIN.decrement(player, 1) == 0) {
                            Config.FLOUR_BIN_FULL.set(player, 0);
                        }
                        player.sendMessage("You fill a pot with flour from the bin.");
                        e.delay(1);
                        player.unlock();
                    });
                }
            });
            ObjectAction.register(flourBin, 1, (player, obj) -> {
                if (Config.FLOUR_BIN.get(player) == 0) {
                    player.sendMessage("There is no flour in the bin.");
                    return;
                }
                if (player.getInventory().contains(POT)) {
                    if (player.getInventory().remove(POT, 1) > 0) {
                        player.startEvent(e -> {
                            player.lock();
                            player.animate(3572);
                            player.getInventory().add(POT_OF_FLOUR, 1);
                            if (Config.FLOUR_BIN.decrement(player, 1) == 0) {
                                Config.FLOUR_BIN_FULL.set(player, 0);
                            }
                            player.sendMessage("You fill a pot with flour from the bin.");
                            player.getTaskManager().doLookupByUUID(339, 1); // Make Some Flour in Lumbridge
                            e.delay(1);
                            player.unlock();
                        });
                    }
                } else {
                    player.sendMessage("You need an empty pot to hold the flour in.");
                }
            });
        }
    }
}
