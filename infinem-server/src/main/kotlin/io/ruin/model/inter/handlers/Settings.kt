package io.ruin.model.inter.handlers

import io.ruin.model.entity.player.Player
import io.ruin.model.inter.*
import io.ruin.model.inter.actions.DefaultAction
import io.ruin.model.inter.dialogue.OptionsDialogue
import io.ruin.model.inter.utils.Config
import io.ruin.model.inter.utils.Option
import io.ruin.model.skills.magic.spells.modern.Alchemy
import io.ruin.network.incoming.handlers.DisplayHandler
import java.util.function.BiConsumer
import java.util.function.Consumer

/**
 * @author Jire
 */
object Settings {

    const val INTERFACE_ID = WidgetID.SETTINGS_GROUP_ID

    @JvmStatic
    fun Player.openSettings() {
        if (isVisibleInterface(INTERFACE_ID)) return

        resetSettings()
        openInterface(InterfaceType.MAIN, INTERFACE_ID)

        packetSender.run {
            sendAccessMask(INTERFACE_ID, 23, 0, 7, AccessMasks.ClickOp1)
            sendAccessMask(INTERFACE_ID, 19, 0, 1000, AccessMasks.ClickOp1)
            sendAccessMask(INTERFACE_ID, 25, -1, -1, AccessMasks.ClickOp1)

            for (slot in 2..41 step 3)
                sendAccessMask(INTERFACE_ID, 28, slot, slot, AccessMasks.ClickOp1)
        }
    }

    @JvmStatic
    fun Player.resetSettings() {
        selectedSettingChild = -1
        restoreChatInput()
    }

    @JvmStatic
    fun Player.restoreChatInput() {
        packetSender.sendClientScript(2158)
        Config.SettingSearch.set(player, 0)
        Config.SettingSearch1.set(player, 0)
    }

    init {
        // settings_client_mode CS2 i, args [3998, 0]
        // settings_interface_scaling CS2 i, args [2358, 0]
        InterfaceHandler.register(INTERFACE_ID) { h ->
            h.closedAction = BiConsumer { p, _ -> p.resetSettings() }
            h.simpleAction(4) { p -> p.closeInterfaces() }
            h.simpleAction(25) { p ->
                p.selectedSettingChild = -1
            }

            h.actions[23] = DefaultAction { p, _, slot, _ ->
                //Config.SETTINGS_SEARCH[p] = 0 REMOVE SEARCH
                p.selectedSettingMenu = slot

            }

            h.actions[10] = DefaultAction { p, _, slot, _ ->
                //Config.SETTINGS_SEARCH[p] = 1 ADD SEARCH
            }

            h.actions[19] = DefaultAction { p, _, slot, _ ->
                if (Config.SETTINGS_SEARCH[p] == 0) {
                    p.selectedSettingChild = slot
                    when (p.selectedSettingMenu) {
                        2 -> {
                            when (slot) {
                                1 -> Config.PROFANITY_FILTER.toggle(p)
                                3 -> Config.CHAT_EFFECTS.toggle(p)
                                4 -> {
                                    Config.SPLIT_PRIVATE_CHAT.toggle(p)
                                    p.packetSender.sendClientScript(83, "")
                                }
                                5 -> Config.HIDE_PRIVATE_CHAT.toggle(p)
                                9 -> {
                                    val config = Config.COLLECTION_LOG_SETTINGS[p];
                                    if (config and 1 == 1) {
                                        Config.COLLECTION_LOG_SETTINGS[p] = config and 1.inv();
                                    } else {
                                        Config.COLLECTION_LOG_SETTINGS[p] = config or 1;
                                    }
                                }
                                19 -> {
                                    //Config.CHAT_COLOR[p] = 87;
                                    //Config.CHAT_COLOR1[p] = 87;
                                    //Config.CHAT_COLOR2[p] = 1;
                                    p.packetSender.sendClientScript(4185, "ii", 134 shl 16 or 8, 255)

                                }
                            }
                        }
                        3 -> {
                            when (slot) {
                                3 -> Config.MOUSE_BUTTONS.toggle(p)
                                4 -> Config.MOUSE_CAMERA.toggle(p)
                                5 -> {
                                    if (Config.SHIFT_DROP[p] == 0) {
                                        Config.SHIFT_DROP[p] = 1
                                    } else {
                                        Config.SHIFT_DROP[p] = 0
                                    }
                                }
                                7 -> Config.PET_OPTIONS.toggle(p)
                                26 -> p.dialogue(
                                    false, OptionsDialogue(
                                        Option("Use OSRS Keybinds", Runnable {
                                            for (c in Config.KEYBINDS) c.reset(p)
                                            Config.ESCAPE_CLOSES.reset(p)
                                        }),
                                        Option("Use Pre-EoC Keybinds", Runnable {
                                            for (i in Config.KEYBINDS.indices) {
                                                val c = Config.KEYBINDS[i]
                                                if (i == 0) c[p] = 5 else if (i in 3..6) c[p] = i - 2 else c[p] = 0
                                            }
                                            Config.ESCAPE_CLOSES.reset(p)
                                        }),
                                        Option("Keep Current Keybinds", p::closeDialogue)
                                    )
                                )
                                28 -> Config.ESCAPE_CLOSES.toggle(p)
                            }
                        }
                        4 -> {
                            when (slot) {
                                4 -> Config.HIDE_ROOFS.toggle(p)
                                5 -> Config.ZOOMING_DISABLED.toggle(p)
                            }
                        }
                        5 -> {
                            when (slot) {
                                1 -> Config.ACCEPT_AID.toggle(p)
                                //2 -> SUPPLYPILES
                            }
                        }
                        6 -> {
                            when (slot) {
                                8 -> Config.DATA_ORBS.toggle(p)
                                11 -> Config.STORE_ORB.toggle(p)
                                13 -> {
                                    val config = Config.COLLECTION_LOG_SETTINGS[p];
                                    if (config and 2 == 2) {
                                        Config.COLLECTION_LOG_SETTINGS[p] = config and 2.inv();
                                    } else {
                                        Config.COLLECTION_LOG_SETTINGS[p] = config or 2;
                                    }
                                }
                                16 -> Config.REMAINING_XP_TOOLTIP.toggle(p)
                                18 -> Config.PRAYER_TOOLTIPS.toggle(p)
                                19 -> Config.SPECIAL_ATTACK_TOOLTIPS.toggle(p)
                                21 -> {
                                    if (Config.TRANSPARENT_CHATBOX[p] == 1)
                                        Config.CLICK_THROUGH_CHATBOX.toggle(p)
                                }
                                22 -> Config.TRANSPARENT_CHATBOX.toggle(p)
                                23 -> Config.TRANSPARENT_SIDE_PANEL.toggle(p)
                            }
                        }
                        7 -> {
                            when (slot) {
                                23 -> Config.ALCH_UNTRADEABLES.toggle(p)
                                24 -> p.integerInput("Set value threshold for alchemy warnings:", Consumer { i ->
                                    Config.ALCH_THRESHOLD[p] = i
                                })
                            }
                        }
                    }
                } else {
                    p.sendMessage("search handling")
                }
            }

            h.actions[28] = DefaultAction { p, _, slot, _ ->
                val dropSlot = (slot - 2) / 3
                when (p.selectedSettingMenu) {
                    2 -> {
                        when (p.selectedSettingChild) {

                        }
                    }
                    3 -> {
                        when (p.selectedSettingChild) {
                            1 -> Config.PLAYER_ATTACK_OPTION[p] = dropSlot
                            2 -> Config.NPC_ATTACK_OPTION[p] = dropSlot
                            in 12..25 -> {
                                val configID = p.selectedSettingChild - 12
                                val selectedKeybindConfig = Config.KEYBINDS[configID]!!

                                if (dropSlot > 0) for (c in Config.KEYBINDS) {
                                    if (c !== selectedKeybindConfig && c[p] == dropSlot) {
                                        c[p] = 0
                                        break
                                    }
                                }
                                selectedKeybindConfig.set(p, dropSlot)
                            }
                        }
                    }
                    6 -> {
                        when (p.selectedSettingChild) {
                            1 -> DisplayHandler.setDisplayMode(p, dropSlot + 1)
                            24 -> Config.CHATBOX_SCROLLBAR[p] = dropSlot;
                            25 -> p.sendMessage("border")
                        }
                    }
                }

                p.selectedSettingChild = -1
            }

            h.simpleAction(26) { p ->
                when (p.selectedSettingMenu) {
                    3 -> {

                    }
                }
            }
        }
    }

}