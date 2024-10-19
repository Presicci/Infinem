package io.ruin.model.inter.handlers

import io.ruin.model.entity.player.Style
import io.ruin.model.inter.InterfaceHandler
import io.ruin.model.inter.actions.DefaultAction
import io.ruin.model.inter.actions.SlotAction
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
            h.actions[46] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[0]--
                if (p.appearance.colors[0] < 0) {
                    p.appearance.colors[0] = 25
                }
                p.appearance.update()
            }
            h.actions[47] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[0]++
                if (p.appearance.colors[0] > 25) {
                    p.appearance.colors[0] = 0
                }
                p.appearance.update()

            }

            // Torso color
            h.actions[50] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[1]--
                if (p.appearance.colors[1] < 0) {
                    p.appearance.colors[1] = 29
                }
                p.appearance.update()
            }
            h.actions[51] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[1]++
                if (p.appearance.colors[1] > 29) {
                    p.appearance.colors[1] = 0
                }
                p.appearance.update()
            }

            // Leg color
            h.actions[54] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[2]--
                if (p.appearance.colors[2] < 0) {
                    p.appearance.colors[2] = 29
                }
                p.appearance.update()
            }
            h.actions[55] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[2]++
                if (p.appearance.colors[2] > 29) {
                    p.appearance.colors[2] = 0
                }
                p.appearance.update()
            }

            // Feet color
            h.actions[58] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[3]--
                if (p.appearance.colors[3] < 0) {
                    p.appearance.colors[3] = 6
                }
                p.appearance.update()
            }
            h.actions[59] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[3]++
                if (p.appearance.colors[3] > 7) {
                    p.appearance.colors[3] = 0
                }
                p.appearance.update()
            }

            // Skin color
            h.actions[62] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[4]--
                if (p.appearance.colors[4] < 0) {
                    p.appearance.colors[4] = 7
                }
                p.appearance.update()
            }
            h.actions[63] = DefaultAction { p, _, _, _ ->
                p.appearance.colors[4]++
                if (p.appearance.colors[4] > 7) {
                    p.appearance.colors[4] = 0
                }
                p.appearance.update()
            }

            h.actions[68] = DefaultAction { p, _, _, _ ->
                Config.CHARACTER_CREATOR_GENDER.set(p, 0)
                p.packetSender.sendVarp(261, 0)
                p.appearance.setGender(0)
                Style.updateAll(p)
                p.appearance.update()
            }

            h.actions[69] = DefaultAction { p, _, _, _ ->
                Config.CHARACTER_CREATOR_GENDER.set(p, 1)
                p.packetSender.sendVarp(261, 1)
                p.appearance.setGender(1)
                Style.updateAll(p)
                p.appearance.update()
            }

            h.actions[74] = DefaultAction { p, _, _, _ ->
                p.appearance.update()
                p.closeInterfaces()
            }

            h.actions[78] = SlotAction { p, i ->
                Config.PRONOUNS.set(p, i)
            }
        }
    }


}