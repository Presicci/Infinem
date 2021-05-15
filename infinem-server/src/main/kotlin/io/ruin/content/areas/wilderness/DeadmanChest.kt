package io.ruin.content.areas.wilderness

import io.ruin.api.globalEvent
import io.ruin.api.message
import io.ruin.api.utils.Random
import io.ruin.model.World
import io.ruin.model.entity.player.Player
import io.ruin.model.item.loot.LootItem
import io.ruin.model.item.loot.LootTable
import io.ruin.model.map.`object`.GameObject

/**
 * @author Heaven
 */
class DeadmanChest(val location: DeadmanChestLocation) {

    /**
     * The [GameObject] reference attached to this chest.
     */
    private var obj: GameObject? = null

    /**
     * The lifespan of the chest until it disappears from the world.
     */
    private var lifespan = 15 * 60 * 1000 / 600

    /**
     * The amount of times this chest has been looted.
     */
    private var lootCounter = 0

    /**
     * Spawns `this` chest within the world and decrements the lifespan until either forceably removed or lifespan has ended.
     */
    fun spawn() = globalEvent {
        val mapobj = GameObject(33116, location.tile, 10, 0).spawn()
        pause(2)
        mapobj.setId(33117)
        obj = mapobj
        while (lifespan-- > 0) {
            when (lifespan) {
                1000, 500, 200 -> World.sendSupplyChestBroadcast("A Deadman Supply Chest is active ${location.hint}")
            }
            pause(1)
        }

        destroy()
    }

    /**
     * Handles the looting of this [DeadmanChest].
     */
    fun loot(player: Player) {
        if (lifespan == 0) return
        val removedKey = player.inventory.remove(CHEST_KEY_ID, 1)
        if (removedKey == 0) {
            player.sendMessage("The chest requires a key of some sort in order to open.")
            return
        }

        val rdt = DROP_TABLE.rollItem()
        player.inventory.addOrDrop(13307, Random.get(150, 600))
        player.inventory.addOrDrop(rdt)
        player.message("You loot some items.")
        val message = "${player.name} has looted the Deadman Supply Chest ${location.hint}."
        World.sendSupplyChestBroadcast(message)
        if (++lootCounter == 10) {
            destroy()
        }
    }

    /**
     * Destroys this [DeadmanChest] and prepares all resources for cleanup.
     */
    fun destroy() {
        lifespan = 0
        obj?.remove()
        obj = null
        DeadmanChestEvent.currentChest = null
    }

    companion object {
        const val CHEST_KEY_ID = 2399

        private val DROP_TABLE = LootTable().addTable(1,    // Barrows pieces, noted food and resources
                LootItem(12877, 1, 15),     // DH Set
                LootItem(12881, 1, 15),     // Ahrims set
                LootItem(4153, 1, 180),     // Gmaul
                LootItem(1249, 1, 12),      // Dragon spear
                LootItem(12831, 1, 12),     // Blessed spirit shield
                LootItem(12846, 1, 12),     // Bounty teleport scroll
                LootItem(1505, 1, 12),      // Obelisk destination scroll
                LootItem(22804, 300, 12),   // Dragon knives
                //LootItem(11235, 1, 12), // Dark bow
                //LootItem(4151, 1, 15), // Abby whip
                LootItem(20517, 1, 15),     // Elder chaos robes top
                LootItem(20520, 1, 15),     // Elder chaos robes
                LootItem(6920, 1, 15)       // Infinity boots
        )
    }
}