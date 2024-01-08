package io.ruin.content.items

import io.ruin.api.event
import io.ruin.api.messageBox
import io.ruin.api.options
import io.ruin.api.whenInvClick
import io.ruin.model.entity.player.Player
import io.ruin.model.item.Item

/**
 * @author Leviticus
 */
object SkinScrolls {
    private const val GREEN_SKIN_SCROLL = 30322
    private const val BLUE_SKIN_SCROLL = 30323
    private const val PURPLE_SKIN_SCROLL = 30324
    private val scrolls = intArrayOf(GREEN_SKIN_SCROLL, BLUE_SKIN_SCROLL, PURPLE_SKIN_SCROLL)

    init {
        scrolls.forEach {
            //whenInvClick(it, 1) { player, item ->
                //player.redeem(item)
            //}
        }
    }

    private fun Player.redeem(item: Item) = event {
        if (options("Redeem Scroll", "No, I don't want to do that") == 1) {
            if (item.id == GREEN_SKIN_SCROLL && hasAttribute("GREEN_SKIN") ||
                    item.id == BLUE_SKIN_SCROLL && hasAttribute("BLUE_SKIN") ||
                    item.id == PURPLE_SKIN_SCROLL && hasAttribute("PURPLE_SKIN")) {
                player.messageBox("You already have this skin unlocked.")
                return@event
            }
            when(item.id) {
                GREEN_SKIN_SCROLL -> putAttribute("GREEN_SKIN", 1)
                BLUE_SKIN_SCROLL -> putAttribute("BLUE_SKIN", 1)
                PURPLE_SKIN_SCROLL -> putAttribute("PURPLE_SKIN", 1)
            }
            item.remove()
        }
    }
}