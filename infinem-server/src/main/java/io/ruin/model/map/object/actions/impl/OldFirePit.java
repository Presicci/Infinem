package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/13/2023
 */
public class OldFirePit {

    private enum FireType {
        FIRE_OF_ETERNAL_LIGHT(35, new Item(2347, 1), new Item(8794, 1), new Item(590, 1),
                new Item(8782, 2), new Item(2353, 2), new Item(22593, 300), new Item(22595, 100), new Item(22597, 50)),
        FIRE_OF_NOURISHMENT(35, new Item(2347, 1), new Item(8794, 1), new Item(590, 1),
                new Item(8782, 2), new Item(2353, 2), new Item(22597, 150), new Item(22595, 100), new Item(22593, 50)),
        FIRE_OF_DEHUMIDIFICATION(50, new Item(2347, 1), new Item(8794, 1), new Item(590, 1),
                new Item(8782, 2), new Item(2353, 2), new Item(22595, 300), new Item(22597, 100), new Item(22593, 50)),
        FIRE_OF_UNSEASONAL_WARMTH(60, new Item(2347, 1), new Item(8794, 1), new Item(590, 1),
                new Item(8782, 2), new Item(2353, 2), new Item(22597, 300), new Item(22593, 100), new Item(22595, 50));

        private final int level;
        private final Item[] requiredItems;

        FireType(final int level, final Item... requiredItems) {
            this.level = level;
            this.requiredItems = requiredItems;
        }
    }

    @AllArgsConstructor
    public enum FirePit {
        WEISS_FIRE(33334, FireType.FIRE_OF_NOURISHMENT, null),
        GODWARS_DUNGEON_FIRE(33335, FireType.FIRE_OF_UNSEASONAL_WARMTH, player -> player.closeInterface(InterfaceType.PRIMARY_OVERLAY)),
        GIANT_MOLE_FIRE(33336, FireType.FIRE_OF_ETERNAL_LIGHT, player -> player.closeInterface(InterfaceType.PRIMARY_OVERLAY)),
        LUMBRIDGE_SWAMP_CAVES_FIRE(33337, FireType.FIRE_OF_ETERNAL_LIGHT, player -> player.closeInterface(InterfaceType.PRIMARY_OVERLAY)),
        MOS_LE_HARMLESS_FIRE(33338, FireType.FIRE_OF_ETERNAL_LIGHT, player -> player.closeInterface(InterfaceType.PRIMARY_OVERLAY)),
        MORT_MYRE_SWAMP_FIRE(33339, FireType.FIRE_OF_DEHUMIDIFICATION, null);

        private final int objectId;
        private final FireType type;
        private final Consumer<Player> onBuild;

        public boolean isBuilt(@NotNull final Player player) {
            return Config.varpbit(ObjectDef.get(objectId).varpBitId, false).get(player) == (this == WEISS_FIRE ? 205 : 1);
        }
    }

    private static void buildFire(Player player, GameObject object, FirePit pit) {
        if (pit.isBuilt(player)) {
            player.dialogue(new MessageDialogue("You've already built this fire pit."));
            return;
        }
        player.startEvent(e -> {
            player.lock();
            Item[] requiredItems = pit.type.requiredItems;
            if (!player.getInventory().containsAll(false, requiredItems)) {
                val builder = new StringBuilder();
                for (int i = 0; i < requiredItems.length; i++) {
                    val item = requiredItems[i];
                    if (item.getAmount() > 1)
                        builder.append(item.getAmount()).append(" x ").append(item.getDef().name).append(i == requiredItems.length - 2 ? " and " : ", ");
                }
                builder.delete(builder.length() - 2, builder.length());
                player.dialogue(new MessageDialogue("You need a hammer, saw, tinderbox, " + builder + " to build the fire pit."));
                player.unlock();
                return;
            }
            if (player.getStats().get(StatType.Construction).currentLevel < pit.type.level) {
                player.dialogue(new MessageDialogue("You need a Construction level of at least " + pit.type.level + " to build the fire pit."));
                player.unlock();
                return;
            }
            player.animate(733);
            e.delay(4);
            for (val item : pit.type.requiredItems) {
                if (item.getAmount() <= 1) {
                    continue;
                }
                player.getInventory().remove(item);
            }
            if (pit.onBuild != null) {
                pit.onBuild.accept(player);
            }
            Config.varpbit(object.getDef().varpBitId, true).set(player, pit == FirePit.WEISS_FIRE ? 205 : 1);
            player.getStats().addXp(StatType.Firemaking, 300, true);
            player.getStats().addXp(StatType.Construction, pit.type.level * 10, true);
            player.resetAnimation();
            player.unlock();
        });
    }

    static {
        for (FirePit pit : FirePit.values()) {
            ObjectAction.register(pit.objectId, 1, ((player, obj) -> buildFire(player, obj, pit)));
        }
    }
}
