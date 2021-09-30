package io.ruin.model.item.actions.impl.combine.smithing;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.MapArea;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/30/2021
 */
public class DragonForge {
    private static final int SHARD = 22097;
    private static final int SLICE = 22100;
    private static final int SQUARE = Items.DRAGON_SQ_SHIELD;
    private static final int KITE = Items.DRAGON_KITE_SHIELD;

    private static final int CHAIN = Items.DRAGON_CHAINBODY;
    private static final int LUMP = 22103;
    private static final int PLATE = Items.DRAGON_PLATEBODY;

    private static void forgeKiteshield(Player player) {
        Item shard = player.getInventory().findItem(SHARD);
        Item slice = player.getInventory().findItem(SLICE);
        Item square = player.getInventory().findItem(SQUARE);
        if(shard == null || slice == null || square == null) {
            player.dialogue(new ItemDialogue().two(SQUARE, SLICE, "You need a dragon square shield as well as a dragon metal slice and shard to forge a dragon kiteshield."));
            return;
        }
        if (!player.getPosition().inBounds(MapArea.DRAGON_FORGE.getBounds())) {
            player.dialogue(new MessageDialogue("You can only forge a dragon kiteshield at the dragon forge."));
            return;
        }
        Item hammer = player.getInventory().findItem(Tool.HAMMER);
        if(hammer == null) {
            player.dialogue(new MessageDialogue("You need a hammer to forge the shield."));
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You start to hammer the metal...");
            player.animate(898);
            event.delay(6);
            if(player.getInventory().hasId(SHARD) && player.getInventory().hasId(SLICE) && player.getInventory().hasId(SQUARE)) {
                player.getInventory().remove(SHARD, 1);
                player.getInventory().remove(SLICE, 1);
                player.getInventory().remove(SQUARE, 1);
                player.getInventory().add(KITE, 1);
                player.getStats().addXp(StatType.Smithing, 1000, true);
                player.sendMessage("You forge the metal to the shield.");
            }
            player.unlock();
        });
    }

    private static void forgePlatebody(Player player) {
        Item lump = player.getInventory().findItem(LUMP);
        Item shard = player.getInventory().findItem(SHARD);
        Item chain = player.getInventory().findItem(CHAIN);
        if(shard == null || lump == null || chain == null) {
            player.dialogue(new ItemDialogue().two(CHAIN, LUMP, "You need a dragon chainbody as well as a dragon metal lump and shard to forge a dragon platebody."));
            return;
        }
        if (!player.getPosition().inBounds(MapArea.DRAGON_FORGE.getBounds())) {
            player.dialogue(new MessageDialogue("You can only forge a dragon platebody at the dragon forge."));
            return;
        }
        Item hammer = player.getInventory().findItem(Tool.HAMMER);
        if(hammer == null) {
            player.dialogue(new MessageDialogue("You need a hammer to forge the platebody."));
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You start to hammer the metal...");
            player.animate(898);
            event.delay(6);
            if(player.getInventory().hasId(LUMP) && player.getInventory().hasId(SHARD) && player.getInventory().hasId(CHAIN)) {
                player.getInventory().remove(LUMP, 1);
                player.getInventory().remove(SHARD, 1);
                player.getInventory().remove(CHAIN, 1);
                player.getInventory().add(PLATE, 1);
                player.getStats().addXp(StatType.Smithing, 2000, true);
                player.sendMessage("You forge the metal to the chainbody.");
            }
            player.unlock();
        });
    }

    private static void attemptCraft(Player player) {
        SkillItem kite = new SkillItem(KITE).addAction((p, amount, event) -> {
            forgeKiteshield(player);
        });
        SkillItem plate = new SkillItem(PLATE).addAction((p, amount, event) -> {
            forgePlatebody(player);
        });
        SkillDialogue.make(player, kite, plate);
    }

    private static void error(Player player) {
        player.dialogue(new MessageDialogue("You can only forge this at the dragon forge."));
    }

    static {
        ItemObjectAction.register(SHARD, "anvil", (player, item, obj) -> attemptCraft(player));
        ItemObjectAction.register(SLICE, "anvil", (player, item, obj) -> forgeKiteshield(player));
        ItemObjectAction.register(LUMP, "anvil", (player, item, obj) -> forgePlatebody(player));
        ItemObjectAction.register(CHAIN, "anvil", (player, item, obj) -> forgePlatebody(player));
        ItemObjectAction.register(SQUARE, "anvil", (player, item, obj) -> forgeKiteshield(player));

        ItemItemAction.register(SHARD, SLICE, (player, primary, secondary) -> error(player));
        ItemItemAction.register(SHARD, SQUARE, (player, primary, secondary) -> error(player));
        ItemItemAction.register(SQUARE, SLICE, (player, primary, secondary) -> error(player));

        ItemItemAction.register(SHARD, LUMP, (player, primary, secondary) -> error(player));
        ItemItemAction.register(SHARD, CHAIN, (player, primary, secondary) -> error(player));
        ItemItemAction.register(CHAIN, LUMP, (player, primary, secondary) -> error(player));
    }
}
