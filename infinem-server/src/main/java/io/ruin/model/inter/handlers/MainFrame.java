package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;

public class MainFrame {

    static {
        InterfaceHandler.register(Interface.ORBS, h -> {
            h.actions[5] = (OptionAction) XpCounter::select;
            h.actions[19] = (OptionAction) (p, option) -> {
                if(option == 1)
                    p.getPrayer().toggleQuickPrayers();
                else
                    TabPrayer.setupQuickPrayers(p, true);
            };
            h.actions[27] = (SimpleAction) p -> p.getMovement().toggleRunning();
            h.actions[53] = (SimpleAction) p -> {
                p.getPacketSender().sendClientScript(1749, "c", p.getPosition().getTileHash());
                p.openInterface(InterfaceType.WORLD_MAP, Interface.WORLD_MAP);
                p.getPacketSender().sendAccessMask(595, 17, 0, 4, 2);
            };
            h.actions[35] = (SimpleAction) p -> p.getCombat().toggleSpecial();
        });
        InterfaceHandler.register(Interface.CHAT_BAR, h -> {
            h.actions[7] = (OptionAction) (player, option) -> {
                if(option == 2)
                    Config.GAME_FILTER.toggle(player);
                else if(option == 3) {
                    player.dialogue(
                            new MessageDialogue("Would you like to filter yells from non-staff members?"),
                            new OptionsDialogue(
                                    new Option("Yes", () -> {
                                        player.yellFilter = true;
                                        player.sendMessage("Yells from non-staff members will now be hidden when your game chat is set to filtered.");
                                    }),
                                    new Option("No", () -> {
                                        player.yellFilter = false;
                                        player.sendMessage("Yells from non-staff members will now show even when your game chat is set to filtered.");
                                    })
                            )
                    );
                }
            };
            h.actions[31] = (OptionAction) (player, option) -> {
                if (option == 2) {
                    player.openInterface(InterfaceType.MAIN, 553);
                    player.getPacketSender().sendClientScript(1104, "iis", 1, 0, "");
                } else if (option == 3) {
                    if (player.getAction(4) == PlayerAction.REPORT) {
                        player.setAction(4, null);
                    } else {
                        player.setAction(4, PlayerAction.REPORT);
                    }
                } else if (option == 4) {  // Report a bug
                    player.openInterface(InterfaceType.MAIN, 156);
                    player.getPacketSender().sendClientScript(1095, "s", "Please try to be as detailed as possible when you submit a bug report!  Thank you for helping improve the game. <3");
                }
            };
            for (int index = 57; index < 557; index++) {
                h.actions[index] = (OptionAction) (player, option) -> {
                    if (option == 8) {
                        player.openInterface(InterfaceType.MAIN, 553);
                        player.getPacketSender().sendClientScript(1104, "iis", 1, 0, "");
                    }
                };
            }
        });
        InterfaceHandler.register(Interface.FIXED_SCREEN, actions -> {
            actions.actions[62] = (DefaultAction) (player, option, slot, itemId) -> {
                if(option == 2)
                    Config.DISABLE_SPELL_FILTERING.toggle(player);
            };
//            actions.actions[50] = (SimpleAction) player -> {
//                player.journal = Journal.PRESETS;
//                for(int childId = 9; childId <= 47; childId++) {
//                    player.getPacketSender().sendClientScript(135, "ii",Interface.SERVER_TAB << 16 | childId, 494);
//                    player.getPacketSender().sendString(Interface.SERVER_TAB, childId, "");
//                }
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 48, false);
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 49, false);
//                player.getPacketSender().sendString(Interface.SERVER_TAB, 1, player.journal.name());
//                player.journal.send(player);
//            };
//            actions.actions[32] = (SimpleAction) player -> {
//                player.journal = Journal.MAIN;
//                for(int childId = 9; childId <= 47; childId++) {
//                    player.getPacketSender().sendClientScript(135, "ii",Interface.SERVER_TAB << 16 | childId, 494);
//                    player.getPacketSender().sendString(Interface.SERVER_TAB, childId, "");
//                }
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 48, true);
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 49, true);
//                player.getPacketSender().sendString(Interface.SERVER_TAB, 1, "Noticeboard");
//                player.journal.send(player);
//            };
        });
        InterfaceHandler.register(Interface.RESIZED_SCREEN, actions -> {
            actions.actions[68] = (DefaultAction) (player, option, slot, itemId) -> {
                if(option == 2)
                    Config.DISABLE_SPELL_FILTERING.toggle(player);
            };
//            actions.actions[53] = (SimpleAction) player -> {
//                player.journal = Journal.PRESETS;
//                for(int childId = 9; childId <= 47; childId++) {
//                    player.getPacketSender().sendClientScript(135, "ii",Interface.SERVER_TAB << 16 | childId, 494);
//                    player.getPacketSender().sendString(Interface.SERVER_TAB, childId, "");
//                }
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 48, false);
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 49, false);
//                player.getPacketSender().sendString(Interface.SERVER_TAB, 1, player.journal.name());
//                player.journal.send(player);
//            };
//            actions.actions[36] = (SimpleAction) player -> {
//                player.journal = Journal.MAIN;
//                for(int childId = 9; childId <= 47; childId++) {
//                    player.getPacketSender().sendClientScript(135, "ii",Interface.SERVER_TAB << 16 | childId, 494);
//                    player.getPacketSender().sendString(Interface.SERVER_TAB, childId, "");
//                }
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 48, true);
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 49, true);
//                player.getPacketSender().sendString(Interface.SERVER_TAB, 1, "Noticeboard");
//                player.journal.send(player);
//            };
        });
        InterfaceHandler.register(Interface.RESIZED_STACKED_SCREEN, actions -> {
            actions.actions[65] = (DefaultAction) (player, option, slot, itemId) -> {
                if(option == 2)
                    Config.DISABLE_SPELL_FILTERING.toggle(player);
            };
//            actions.actions[52] = (SimpleAction) player -> {
//                player.journal = Journal.PRESETS;
//                for(int childId = 9; childId <= 47; childId++) {
//                    player.getPacketSender().sendClientScript(135, "ii",Interface.SERVER_TAB << 16 | childId, 494);
//                    player.getPacketSender().sendString(Interface.SERVER_TAB, childId, "");
//                }
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 48, false);
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 49, false);
//                player.getPacketSender().sendString(Interface.SERVER_TAB, 1, player.journal.name());
//                player.journal.send(player);
//            };
//            actions.actions[36] = (SimpleAction) player -> {
//                player.journal = Journal.MAIN;
//                for(int childId = 9; childId <= 47; childId++) {
//                    player.getPacketSender().sendClientScript(135, "ii",Interface.SERVER_TAB << 16 | childId, 494);
//                    player.getPacketSender().sendString(Interface.SERVER_TAB, childId, "");
//                }
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 48, true);
//                player.getPacketSender().setHidden(Interface.SERVER_TAB, 49, true);
//                player.getPacketSender().sendString(Interface.SERVER_TAB, 1, "Noticeboard");
//                player.journal.send(player);
//            };
        });
    }

}
