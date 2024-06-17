package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public enum TransformationRing {

    STONE(6583, 2188),
    NATURE(20005, 7314),
    COINS(20017, 7315),
    EASTER(7927, 5538, 5539, 5540, 5541, 5542, 5543);

    public final int itemId;
    public final int[] npcIds;

    TransformationRing(int itemId, int... npcIds) {
        this.itemId = itemId;
        this.npcIds = npcIds;
    }

    private static void morph(Player player, TransformationRing ring) {
        if (player.wildernessLevel > 0) {
            player.dialogue(new MessageDialogue("You can't use morphs in the wilderness."));
            return;
        }
        player.lock();
        player.getMovement().reset();
        player.getAppearance().setNpcId(ring.npcIds[Random.get(ring.npcIds.length - 1)]);
        player.openInterface(InterfaceType.INVENTORY, Interface.TRANSFORMATION_RING);
        player.getAppearance().setCustomRenders(-1, -1, -1, -1, -1, -1, -1);
    }

    public static void unmorph(Player player) {
        player.getAppearance().setNpcId(-1);
        player.closeInterface(InterfaceType.INVENTORY);
        player.getAppearance().removeCustomRenders();
        player.unlock();
    }

    public static void check(Player player) {
        if(player.getAppearance().getNpcId() != -1)
            unmorph(player);
    }

    public static void thirdAgeMorphList(Player player) {
        OptionScroll.open(player, "What would you like to be?", true,
                new Option("Wand", () -> thirdAgeMorph(player,8639)),
                new Option("Bow", () -> thirdAgeMorph(player, 8640)),
                new Option("Longsword", () -> thirdAgeMorph(player, 8641)),
                new Option("Cloak", () -> thirdAgeMorph(player, 8642)),
                new Option("Range top", () -> thirdAgeMorph(player, 8645)),
                new Option("Range legs", () -> thirdAgeMorph(player, 8646)),
                new Option("Range coif", () -> thirdAgeMorph(player, 8647)),
                new Option("vambraces", () -> thirdAgeMorph(player, 8648)),
                new Option("Robe top", () -> thirdAgeMorph(player, 8649)),
                new Option("Robe leggings", () -> thirdAgeMorph(player, 8650)),
                new Option("Mage hat", () -> thirdAgeMorph(player, 8651)),
                new Option("Amulet", () -> thirdAgeMorph(player, 8652)),
                new Option("Platebody", () -> thirdAgeMorph(player, 8653)),
                new Option("Plateskirt", () -> thirdAgeMorph(player, 8655)),
                new Option("Platelegs", () -> thirdAgeMorph(player, 8654)),
                new Option("Full helmet", () -> thirdAgeMorph(player, 8656)),
                new Option("Kiteshield", () -> thirdAgeMorph(player, 8657)),
                new Option("Druidic top", () -> thirdAgeMorph(player, 8660)),
                new Option("Druidic robe bottoms", () -> thirdAgeMorph(player, 8661)),
                new Option("Druidic staff", () -> thirdAgeMorph(player, 8662)),
                new Option("Druidic cloak", () -> thirdAgeMorph(player, 8663)),
                new Option("Axe", () -> thirdAgeMorph(player, 8658)),
                new Option("Pickaxe", () -> thirdAgeMorph(player, 8659)),
                new Option("Ring", () -> thirdAgeMorph(player, 8664))
        );
    }

    public static void thirdAgeMorph(Player player, int npcId) {
        if (player.wildernessLevel > 0) {
            player.dialogue(new MessageDialogue("You can't use morphs in the wilderness."));
            return;
        }
        player.lock();
        player.getMovement().reset();
        player.getAppearance().setNpcId(npcId);
        player.openInterface(InterfaceType.INVENTORY, Interface.TRANSFORMATION_RING);
        player.getAppearance().setCustomRenders(-1, -1, -1, -1, -1, -1, -1);
    }

    static {
        ItemAction.registerInventory(23185, "Wear", (player, item) -> TransformationRing.thirdAgeMorphList(player));
        for (TransformationRing ring : values())
            ItemAction.registerInventory(ring.itemId, 2, (player, item) -> morph(player, ring));
        InterfaceHandler.register(Interface.TRANSFORMATION_RING, h -> h.actions[0] = (SimpleAction) TransformationRing::unmorph);
    }

}
