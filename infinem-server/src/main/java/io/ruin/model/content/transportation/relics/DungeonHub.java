package io.ruin.model.content.transportation.relics;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2024
 */
public class DungeonHub {

    private static void teleport(Player player, DungeonHubTeleport dungeon) {
        if (!player.getRelicManager().hasRelicEnalbed(Relic.DUNGEON_HUB_PREMIUM)) {
            ModernTeleport.teleport(player, dungeon.getDestination());
            return;
        }
        Position markedTile = dungeon.getMarkedTile(player);
        if (markedTile == null) {
            ModernTeleport.teleport(player, dungeon.getDestination());
            return;
        }
        player.dialogueKeepInterfaces(
                new OptionsDialogue("Where would you like to teleport?",
                    new Option("Entrance", () -> ModernTeleport.teleport(player, dungeon.getDestination())),
                    new Option("Marked Tile", () -> ModernTeleport.teleport(player, markedTile)),
                    new Option("Nowhere")
                )
        );
    }

    public static void open(Player player) {
        if (!player.getRelicManager().hasRelicInTier(Relic.DUNGEON_HUB_PREMIUM)) {
            player.dialogue(new MessageDialogue("You need to unlock a tier 3 relic to use the Dungeon Hub."));
            return;
        }
        List<Option> options = new ArrayList<>();
        for (DungeonHubTeleport dungeon : DungeonHubTeleport.values()) {
            options.add(new Option(StringUtils.initialCaps(dungeon.name()), () -> teleport(player, dungeon)));
        }
        OptionScroll.open(player, "Dungeon Hub", true, options);
    }

    protected static Position stringToPosition(String string) {
        String[] coords = string.split(",");
        return new Position(
                Integer.parseInt(coords[0]),
                Integer.parseInt(coords[1]),
                Integer.parseInt(coords[2])
        );
    }

    private static String positionToString(Position position) {
        return position.getX() + "," + position.getY() + "," + position.getZ();
    }

    private static void markTile(Player player, Item item) {
        DungeonHubTeleport dungeon = DungeonHubTeleport.getDungeon(player);
        if (dungeon == null) {
            player.sendMessage("You are not currently in a Dungeon Hub linked dungeon.");
            return;
        }
        if (!player.getRelicManager().hasRelicEnalbed(Relic.DUNGEON_HUB_PREMIUM)) {
            player.dialogue(new MessageDialogue("You need Dungeon Hub Premium to mark dungeon tiles."));
            item.remove();
            return;
        }
        if (player.hasAttribute(dungeon.getKey())) {
            Position currentTile = stringToPosition(player.getAttribute(dungeon.getKey()));
            if (currentTile.equals(player.getPosition())) {
                player.sendMessage("That tile is already marked for this dungeon.");
                return;
            }
            player.dialogue(
                    new OptionsDialogue("You already have a tile marked. Overwrite?",
                            new Option("Yes", () -> dungeon.markTile(player, positionToString(player.getPosition()))),
                            new Option("No")
                    ).keepOpenWhenHit()
            );
        } else {
            dungeon.markTile(player, positionToString(player.getPosition()));
        }
    }

    static {
        ObjectAction.register(33037, "access", (player, object) -> open(player));
        ItemAction.registerInventory(24545, "mark", DungeonHub::markTile);
        ItemAction.registerInventory(24545, "hub", (player, item) -> open(player));
    }
}
