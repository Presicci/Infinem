package io.ruin.model.entity.player

import io.ruin.Server
import io.ruin.api.filestore.FileStore
import io.ruin.cache.def.IdentityKitDefinition
import io.ruin.cache.def.VarpbitDefinition
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

enum class Style(
    val appearanceIndex: Int,
    val childNext: Int, val childPrevious: Int
) {
    HAIR(0,  15, 16),
    JAW(1,  19, 20),
    TORSO(2,  23, 24),
    ARMS(3,  27, 28),
    HANDS(4, 31, 32),
    LEGS(5,  35, 36),
    FEET(6,  39, 40);

    fun bodyPart(male: Boolean): Int? {
        return if (male) {
            this.appearanceIndex
        } else {
            7 + this.appearanceIndex
        }
    }

    fun change(player: Player, increment: Int) {
        val currentIdentityKitIndex = this.selectedAvailableIdentityKitIndex(player) ?: return

        val availableIdentityKits = this.availableIdentityKits(player.appearance.isMale)

        var nextIdentityKitIndex = currentIdentityKitIndex + increment;
        if (nextIdentityKitIndex >= availableIdentityKits.size) {
            nextIdentityKitIndex = 0;
        } else if (nextIdentityKitIndex < 0) {
            nextIdentityKitIndex = availableIdentityKits.size - 1;
        }

        updateAppearance(player, nextIdentityKitIndex);
        player.appearance.update()
    }

    fun updateAppearance(player: Player, identityKitIndex: Int) {
        player.appearance.styles[appearanceIndex] =
            this.availableIdentityKits(player.appearance.isMale)[identityKitIndex].id
    }

    fun markAppearanceEmpty(player: Player) {
        player.appearance.styles[appearanceIndex] = 255;
    }

    fun selectedAvailableIdentityKitIndex(player: Player): Int? {
        val male = player.appearance.isMale
        if (!this.isAvailable(male)) {
            return null
        }

        val availableIdentityKits = this.availableIdentityKits(male);
        if (availableIdentityKits.isEmpty()) {
            return null
        }

        val currentIdentityKitID = player.appearance.styles[appearanceIndex]
        // TODO: instead of storing the identity kit id on appearance, store the index only
        // so the search and lookup is not required here, and also when ids change of identity kits,
        // the index can be used as an actual order.
        val indexOfFirst = availableIdentityKits.indexOfFirst { it.id == currentIdentityKitID }
        if (indexOfFirst == -1) {
            return null
        }
        return indexOfFirst
    }

    fun availableIdentityKits(male: Boolean): List<IdentityKitDefinition> {
        val bodyPart = this.bodyPart(male) ?: return emptyList()
        val allAvailable = this.allAvailableKits(bodyPart)
        return allAvailable;
    }

    fun allAvailableKits(bodyPart: Int): List<IdentityKitDefinition> {
        return ArrayList(bodyParts[bodyPart])
    }

    fun isAvailable(male: Boolean): Boolean {
        return true
    }

    fun next(player: Player) = change(player, 1)
    fun previous(player: Player) = change(player, -1)

    fun getIdAtIndex(male: Boolean, index: Int): Int {
        val kits = availableIdentityKits(male)
        if (kits.isEmpty())
            return -1;
        val part = kits[index]
        return part.id
    }

    fun getIndexById(male: Boolean, style: Int): Int {
        val kits = availableIdentityKits(male)
        if (kits.isEmpty())
            return -1;
        val part = kits.find { it.id == style } ?: return -1
        return kits.indexOf(part)
    }

    companion object {
        val bodyParts by lazy {
            val result = Array(14) { mutableListOf<IdentityKitDefinition>() }
            for (kit in IdentityKitDefinition.LOADED) {
                if(kit.selectable) {
                    result[kit.bodyPartId].add(kit);
                }
            }
            return@lazy result
        }


        @JvmField
        val values = values()

        @JvmStatic
        fun main(args: Array<String>) {
            Server.fileStore = FileStore("Cache/")
            VarpbitDefinition.load()
            IdentityKitDefinition.load()
            exitProcess(0)
        }

        fun updateAll(player: Player) {
            val male = player.appearance.isMale
            for (value in values) {
                if (value.isAvailable(male)) {
                    // when switches male -> female available index might not be found
                    var index = value.selectedAvailableIdentityKitIndex(player) ?: 0
                    if (value == JAW) index = 4;
                    if (value == HAIR) index = 3;
                    value.updateAppearance(player, index)
                } else {
                    value.markAppearanceEmpty(player)
                }
            }
        }
    }
}