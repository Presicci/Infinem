package io.ruin.model.skills.magic.spells;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HomeTeleport extends Spell {

    public static final HomeTeleport MODERN = new HomeTeleport(p -> ModernTeleport.teleport(p, World.HOME));

    public static final HomeTeleport ANCIENT = new HomeTeleport(p -> ModernTeleport.teleport(p, World.HOME));

    public static final HomeTeleport LUNAR = new HomeTeleport(p -> ModernTeleport.teleport(p, World.HOME));

    public static final HomeTeleport ARCEUUS = new HomeTeleport(p -> ModernTeleport.teleport(p, World.HOME));

    private static final List<HomeTeleportOverride> OVERRIDES = new LinkedList<>();

    private HomeTeleport(Consumer<Player> consumer) {
        clickAction = (player, i) -> {
            if (player.getCombat().isDefending(16)) {
                player.sendMessage("You can't cast this spell while in combat.");
                return;
            }
            if (player.pvpAttackZone) {
                if (player.getCombat().isDefending(16)) {
                    player.sendMessage("You can't cast this spell while in combat in a pvp zone.");
                    return;
                }
            }
            if (player.isLocked())
                return;
            if (player.teleportListener != null && !player.teleportListener.allow(player))
                return;
            if (player.wildernessLevel > 20) {
                player.sendMessage("You cannot teleport from over " + 20 + " wilderness.");
                return;
            }
            if (player.getCombat().checkTb())
                return;
            Position override = getHomeTeleportOverride(player);
            player.getMovement().reset();
            player.startEvent(e -> {
                player.animate(4847);
                player.graphics(800);
                e.delay(6);
                player.animate(4850);
                e.delay(4);
                player.animate(4853);
                player.graphics(802);
                e.delay(4);
                player.animate(4855);
                player.graphics(803);
                e.delay(4);
                player.animate(4857);
                player.graphics(804);
                e.delay(3);
                player.animate(-1);
                player.getTaskManager().doLookupByUUID(64, 1);   // Cast Home Teleport
                player.getMovement().teleport(World.HOME);
            });
        };
    }

    private static class HomeTeleportOverride {
        Predicate<Player> condition;
        Position destination;

        public HomeTeleportOverride(Predicate<Player> condition, Position destination) {
            this.condition = condition;
            this.destination = destination;
        }
    }

    public static void registerHomeTeleportOverride(Predicate<Player> condition, Position destination) {
        OVERRIDES.add(new HomeTeleportOverride(condition, destination));
    }

    public static Position getHomeTeleportOverride(Player player) {
        for (HomeTeleportOverride teleportOverride : OVERRIDES) {
            if (teleportOverride.condition.test(player))
                return teleportOverride.destination;
        }
        return null;
    }
}