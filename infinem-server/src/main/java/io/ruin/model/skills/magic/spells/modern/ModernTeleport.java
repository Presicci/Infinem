package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.MagicTeleportBounds;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

import java.util.function.Consumer;

public class ModernTeleport extends Spell {

    public static final ModernTeleport VARROCK_TELEPORT = new ModernTeleport(25, 35.0, new Bounds[] { MagicTeleportBounds.VARROCK.getBounds(), MagicTeleportBounds.VARROCK_GE.getBounds() }, Rune.LAW.toItem(1), Rune.AIR.toItem(3), Rune.FIRE.toItem(1));
    public static final ModernTeleport LUMBRIDGE_TELEPORT = new ModernTeleport(31, 41.0, MagicTeleportBounds.LUMBRIDGE.getBounds(), Rune.LAW.toItem(1), Rune.AIR.toItem(3), Rune.EARTH.toItem(1));
    public static final ModernTeleport FALADOR_TELEPORT = new ModernTeleport(37, 48.0, MagicTeleportBounds.FALADOR.getBounds(), Rune.LAW.toItem(1), Rune.AIR.toItem(3), Rune.WATER.toItem(1));
    public static final ModernTeleport CAMELOT_TELEPORT = new ModernTeleport(45, 55.5, new Bounds[] { MagicTeleportBounds.CAMELOT.getBounds(), MagicTeleportBounds.CAMELOT_SEERS.getBounds() } , Rune.LAW.toItem(1), Rune.AIR.toItem(5));
    public static final ModernTeleport ARDOUGNE_TELEPORT = new ModernTeleport(51, 61.0, MagicTeleportBounds.ARDOUGNE.getBounds(), Rune.LAW.toItem(2), Rune.WATER.toItem(2));
    public static final ModernTeleport WATCHTOWER_TELEPORT = new ModernTeleport(58, 68.0, new Bounds[] { MagicTeleportBounds.WATCHTOWER.getBounds(), MagicTeleportBounds.WATCHTOWER_YANILLE.getBounds() }, Rune.LAW.toItem(2), Rune.EARTH.toItem(2));
    public static final ModernTeleport TROLLHEIM_TELEPORT = new ModernTeleport(61, 71.0, MagicTeleportBounds.TROLLHEIM.getBounds(), Rune.LAW.toItem(2), Rune.FIRE.toItem(2));
    public static final ModernTeleport APE_ATOLL_TELEPORT = new ModernTeleport(64, 74.0, MagicTeleportBounds.APE_ATOLL.getBounds(), Rune.LAW.toItem(2), Rune.FIRE.toItem(2), Rune.WATER.toItem(2));
    public static final ModernTeleport KOUREND_TELEPORT = new ModernTeleport(69, 82.0, MagicTeleportBounds.KOUREND.getBounds(), Rune.LAW.toItem(2), Rune.SOUL.toItem(2), Rune.WATER.toItem(4), Rune.FIRE.toItem(5));


    public int getLvlReq() {
        return lvlReq;
    }

    public double getXp() {
        return xp;
    }

    public Item[] getRunes() {
        return runes;
    }

    public ModernTeleport(int lvlReq, double xp, Bounds bounds, Item... runes) {
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.runes = runes;
        registerClick(lvlReq, xp, true, runes, (p, i) -> {
            if (teleport(p, bounds)) {
                p.getTaskManager().doLookupByUUID(147, 1); // Teleport Using Law Runes
                return true;
            }
            return false;
        });
    }

    public ModernTeleport(int lvlReq, double xp, Bounds[] bounds, Item... runes) {
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.runes = runes;
        registerClick(lvlReq, xp, true, runes, (p, i) -> {
            if (teleport(p, bounds[i])) {
                p.getTaskManager().doLookupByUUID(147, 1); // Teleport Using Law Runes
                return true;
            }
            return false;
        });
    }

    public ModernTeleport(int lvlReq, double xp, int x, int y, int z, Item... runes) {
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.runes = runes;
        registerClick(lvlReq, xp, true, runes, (p, i) -> {
            if (teleport(p, x, y, z)) {
                p.getTaskManager().doLookupByUUID(147, 1); // Teleport Using Law Runes
                return true;
            }
            return false;
        });
    }

    private final int lvlReq;
    private final double xp;
    private final Item[] runes;

    public static boolean teleport(Player player, Bounds bounds) {
        return teleport(player, bounds.randomX(), bounds.randomY(), bounds.z);
    }

    public static boolean teleport(Player player, Position position) {
        return teleport(player, position.getX(), position.getY(), position.getZ());
    }

    public static boolean teleport(Player player, int x, int y, int z) {
        return player.getMovement().startTeleport(e -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(x, y, z);
        });
    }

    public static boolean teleport(Player player, int x, int y, int z, Consumer<Player> onComplete) {
        return player.getMovement().startTeleport(e -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(x, y, z);
            onComplete.accept(player);
        });
    }

}
