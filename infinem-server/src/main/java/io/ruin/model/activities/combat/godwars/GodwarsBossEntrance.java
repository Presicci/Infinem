package io.ruin.model.activities.combat.godwars;

import io.ruin.model.activities.combat.pvminstance.InstanceDialogue;
import io.ruin.model.activities.combat.pvminstance.InstanceType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;

public class GodwarsBossEntrance {

    private static final int ECUMENICAL_KEY = 11942;

    private static int kcRequirement(Player player) {
        if (player.isGroup(PlayerGroup.DIAMOND)) {
            return 25;
        } else if (player.isGroup(PlayerGroup.RUBY)) {
            return 30;
        } else if (player.isGroup(PlayerGroup.EMERALD)) {
            return 35;
        } else {
            return 40;
        }
    }

    public static boolean canEnter(Player player, Config config, String name) {
        Item key = player.getInventory().findItem(ECUMENICAL_KEY);
        if (key != null) {
            return true;
        }
        int requiredKC = kcRequirement(player);
        if (config.get(player) >= requiredKC) {
            return true;
        }
        return false;
    }

    public static boolean enter(Player player, Config config, String name) {
        Item key = player.getInventory().findItem(ECUMENICAL_KEY);
        if (key != null) {
            key.remove();
            player.sendFilteredMessage("Your ecumenical key melts into the door.");
            return true;
        }

        int requiredKC = kcRequirement(player);
        if (config.get(player) >= requiredKC) {
            player.sendFilteredMessage("The door devours the life-force of " + requiredKC + " followers of " + name + " that you have slain.");
            config.set(player, config.get(player) - requiredKC);
            return true;
        }

        player.sendFilteredMessage("This door is locked by the power of " + name + "! You need to collect the essence of at least " + requiredKC +
                " followers before the door will open.");
        return false;
    }

    static {
        /**
         * Zamorak
         */
        ObjectAction.register(26505, 1, (player, obj) -> {
            if (player.isAt(2925, 5333)) {
                InstanceDialogue.open(player, InstanceType.ZAMORAK_GWD);
            } else if (player.getAbsY() < obj.y) {
                player.getMovement().teleport(2925, 5333, 2);
            }
        });

        /**
         * Bandos
         */
        ObjectAction.register(26503, 1, (player, obj) -> {
            if (player.isAt(2862, 5354)) {
                InstanceDialogue.open(player, InstanceType.BANDOS_GWD);
            } else if (player.getAbsX() > obj.x) {
                player.getMovement().teleport(2862, 5354, 2);
            }
        });


        /**
         * Saradomin
         */
        ObjectAction.register(26504, 1, (player, obj) -> {
            if (player.isAt(2909, 5265)) {
                InstanceDialogue.open(player, InstanceType.SARADOMIN_GWD);
            } else if (player.getAbsX() < obj.x) {
                player.getMovement().teleport(2909, 5265, 0);
            }
        });

        /**
         * Armadyl
         */
        ObjectAction.register(26502, 1, (player, obj) -> {
            if (player.isAt(2839, 5294)) {
                InstanceDialogue.open(player, InstanceType.ARMADYL_GWD);
            } else if (player.getAbsY() > obj.y) {
                player.getMovement().teleport(2839, 5294, 2);
            }
        });
    }
}
