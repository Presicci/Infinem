package io.ruin.model.item.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.MagicTeleportBounds;
import io.ruin.model.skills.magic.spells.HomeTeleport;

public enum TeleportTab {

    //Normal
    VARROCK(8007, MagicTeleportBounds.VARROCK.getBounds()),
    LUMBRIDGE(8008, MagicTeleportBounds.LUMBRIDGE.getBounds()),
    FALADOR(8009, MagicTeleportBounds.FALADOR.getBounds()),
    CAMELOT(8010, MagicTeleportBounds.CAMELOT.getBounds()),
    ARDOUGNE(8011, MagicTeleportBounds.ARDOUGNE.getBounds()),
    WATCHTOWER(8012, MagicTeleportBounds.WATCHTOWER.getBounds()),

    //Ancient Magicks
    ANNAKARL(12775, MagicTeleportBounds.ANNAKARL.getBounds()),
    CARRALLANGAR(12776, MagicTeleportBounds.CARRALLANGAR.getBounds()),
    DAREEYAK(12777, MagicTeleportBounds.DAREEYAK.getBounds()),
    GHORROCK(12778, MagicTeleportBounds.GHORROCK.getBounds()),
    KHARYRLL(12779, MagicTeleportBounds.KHARYLL.getBounds()),
    LASSAR(12780, MagicTeleportBounds.LASSAR.getBounds()),
    PADDEWWA(12781, MagicTeleportBounds.PADDEWWA.getBounds()),
    SENNTISTEN(12782, MagicTeleportBounds.SENNTISTEN.getBounds()),

    // Wilderness crabs
    WILDERNESS_CRABS(24251, 3349, 3782, 0),

    //Arceuus
    ARCEUUS_LIBRARY(19613, MagicTeleportBounds.ARCEUUS_LIBRARY.getBounds()),
    DRAYNOR_MANOR(19615, MagicTeleportBounds.DRAYNOR_MANOR.getBounds()),
    MIND_ALTAR(19617, MagicTeleportBounds.MIND_ALTAR.getBounds()),
    SALVE_GRAVEYARD(19619, MagicTeleportBounds.SALVE_GRAVEYARD.getBounds()),
    FENKENSTRAIN(19621, MagicTeleportBounds.FENKENSTRAINS_CASTLE.getBounds()),
    WEST_ARDOUGNE(19623, MagicTeleportBounds.WEST_ARDOUGNE.getBounds()),
    HARMONY_ISLAND(19625, MagicTeleportBounds.HARMONY_ISLAND.getBounds()),
    CEMENTERY(19627, MagicTeleportBounds.CEMETARY.getBounds()),
    BARROWS(19629, MagicTeleportBounds.BARROWS.getBounds()),
    APE_ATOLL(19631, MagicTeleportBounds.APE_ATOLL_DUNGEON.getBounds()),

    //Redirected
    RIMMINGTON(11741, 2954, 3224, 0),
    TAVERLEY(11742, 2894, 3465, 0),
    POLLNIVNEACH(11743, 3340, 3004, 0),
    RELLEKA(11744, 2670, 3632, 0),
    BRIMHAVEN(11745, 2758, 3178, 0),
    YANILLE(11746, 2544, 3095, 0),
    TROLLHEIM(11747, 2910, 3612, 0),
    HOSIDIUS(19651, 1744, 3517, 0),
    PRIFDDINAS(23771, 3239, 6075, 0);

    public final int id;
    public final Bounds teleportBounds;

    TeleportTab(int id, Bounds teleportBounds) {
        this.id = id;
        this.teleportBounds = teleportBounds;
    }

    TeleportTab(int id, int x, int y, int z) {
        this.id = id;
        this.teleportBounds = new Bounds(new Position(x, y, z), 0);
    }

    public static void tabletTeleport(Player player, Item item, Position destination) {
        player.getMovement().startTeleport(event -> {
            player.animate(4069, 16);
            player.privateSound(965, 1, 15);
            event.delay(2);
            if (item != null) item.remove(1);
            player.animate(4071);
            player.graphics(678);
            event.delay(2);
            player.getMovement().teleport(destination);
        });
    }

    private void teleport(Player player, Item tab) {
        player.getMovement().startTeleport(event -> {
            player.animate(4069, 16);
            player.privateSound(965, 1, 15);
            event.delay(2);
            tab.remove(1);
            player.animate(4071);
            player.graphics(678);
            event.delay(2);
            player.getMovement().teleport(teleportBounds.randomPosition());
        });
    }

    public static void houseTab(Player player, Item tab, boolean inside) {
        if (player.house == null) {
            player.sendMessage("You don't have a house to teleport to.");
            return;
        }
        if (player.isInOwnHouse() && inside) {
            player.sendMessage("You're already in your house!");
            return;
        }
        player.getMovement().startTeleport(event -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE);
            player.animate(4069, 16);
            player.privateSound(965, 1, 15);
            event.delay(2);
            if(tab.getId() == 8013)
                tab.remove(1);
            player.animate(4071);
            player.graphics(678);
            event.delay(1);
            if (inside) {
                player.house.buildAndEnter(player, false);
                while (player.isLocked())
                    event.delay(1);
            } else {
                event.delay(1);
                player.getMovement().teleport(player.house.getLocation().getPosition());
            }
            player.unlock();
        });
    }

    static {
        for(TeleportTab tab : values())
            ItemAction.registerInventory(tab.id, "break", tab::teleport);
        ItemAction.registerInventory(8013, "break", (player, item) -> houseTab(player, item, Config.TELEPORT_INSIDE.get(player) == 0));
        ItemAction.registerInventory(8013, "inside", (player, item) -> houseTab(player, item, true));
        ItemAction.registerInventory(8013, "outside", (player, item) -> houseTab(player, item, false));
        ItemAction.registerInventory(4251, "empty", (player, item) -> {
            if (player.wildernessLevel <= 20) {
                player.startEvent(e -> {
                    player.lock(LockType.FULL_NULLIFY_DAMAGE);
                    player.animate(878);
                    player.graphics(1273);
                    e.delay(3);
                    player.getMovement().teleport(3659, 3522, 0);
                    player.animate(-1);
                    player.unlock();
                });
            } else {
                player.sendMessage("You may not use this past level 20 wilderness!");
            }
        });
    }

}