package io.ruin.model.inter.handlers

import io.ruin.model.entity.player.Style
import io.ruin.model.inter.InterfaceHandler
import io.ruin.model.inter.actions.DefaultAction
import io.ruin.model.inter.utils.Config

object CharacterCreator {
    const val INTERFACE_ID = 679

    init {
        InterfaceHandler.register(INTERFACE_ID) { h ->
            for (value in Style.values) {
                h.simpleAction(value.childNext, value::next)
                h.simpleAction(value.childPrevious, value::previous)
            }

            // Hair color
            h.actions[43] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[0]--
                if (p.appearance.colors[0] == 0) {
                    p.appearance.colors[0] = 25
                }
                p.appearance.update()
            }
            h.actions[44] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[0]++
                if (p.appearance.colors[0] == 25) {
                    p.appearance.colors[0] = 0
                }
                p.appearance.update()

            }

            // Torso color
            h.actions[47] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[1]--
                if (p.appearance.colors[1] == 0) {
                    p.appearance.colors[1] = 29
                }
                p.appearance.update()
            }
            h.actions[48] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[1]++
                if (p.appearance.colors[1] == 29) {
                    p.appearance.colors[1] = 0
                }
                p.appearance.update()
            }

            // Leg color
            h.actions[51] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[2]--
                if (p.appearance.colors[2] == 0) {
                    p.appearance.colors[2] = 29
                }
                p.appearance.update()
            }
            h.actions[52] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[2]++
                if (p.appearance.colors[2] == 29) {
                    p.appearance.colors[2] = 0
                }
                p.appearance.update()
            }

            // Feet color
            h.actions[55] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[3]--
                if (p.appearance.colors[3] == 0) {
                    p.appearance.colors[3] = 7
                }
                p.appearance.update()
            }
            h.actions[56] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[3]++
                if (p.appearance.colors[3] == 7) {
                    p.appearance.colors[3] = 0
                }
                p.appearance.update()
            }

            // Skin color
            h.actions[59] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[4]--
                if (p.appearance.colors[4] == 0) {
                    p.appearance.colors[4] = 8
                }
                p.appearance.update()
            }
            h.actions[60] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[4]++
                if (p.appearance.colors[4] == 8) {
                    p.appearance.colors[4] = 0
                }
                p.appearance.update()
            }

            h.actions[65] = DefaultAction { p, _, _, _ ->
                Config.CHARACTER_CREATOR_GENDER.set(p, 0)
                p.packetSender.sendVarp(261, 0)
                p.appearance.setGender(0)
                Style.updateAll(p)
                p.appearance.update()
            }

            h.actions[66] = DefaultAction { p, _, _, _ ->
                Config.CHARACTER_CREATOR_GENDER.set(p, 1)
                p.packetSender.sendVarp(261, 1)
                p.appearance.setGender(1)
                Style.updateAll(p)
                p.appearance.update()
            }

            h.actions[68] = DefaultAction { p, _, _, _ ->
                p.appearance.update()
                p.closeInterfaces()
            }
        }
    }


}