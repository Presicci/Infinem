package io.ruin.model.skills.crafting;

import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/26/2022
 */
@AllArgsConstructor
public enum Loom {

    BASKET(new Item(Items.WILLOW_BRANCH, 6), new Item(Items.BASKET), 36, 56),
    EMPTY_SACK(new Item(Items.JUTE_FIBRE, 4), new Item(Items.EMPTY_SACK), 21, 38),
    DRIFT_NET(new Item(Items.JUTE_FIBRE, 2), new Item(21652), 26, 55),
    STRIP_OF_CLOTH(new Item(Items.BALL_OF_WOOL, 4), new Item(Items.STRIP_OF_CLOTH), 10, 12);

    private final Item before, after;
    private final int levelReq;
    private final double exp;

    private static void weave(Player player, Loom item, int amount) {
        player.closeInterfaces();
        if(!player.getStats().check(StatType.Crafting, item.levelReq, "make this"))
            return;
        if(player.getInventory().getAmount(item.before.getId()) < item.before.getAmount()) {
            player.sendMessage("You'll need " + item.before.getAmount() + " "
                    + (item == Loom.STRIP_OF_CLOTH ? "balls of wool" : (item.before.getDef().name.toLowerCase() + "s"))
                    + " to make that.");
            return;
        }

        player.startEvent(event -> {
            int amt = amount;
            while(amt --> 0) {
                player.lock();
                if(player.getInventory().getAmount(item.before.getId()) < item.before.getAmount()) {
                    player.unlock();
                    return;
                }
                weave(player, item);
                if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                    event.delay(2);
                }
                player.unlock();
            }
        });
        player.unlock();
    }

    private static void weave(Player player, Loom item) {
        if(player.getInventory().getAmount(item.before.getId()) < item.before.getAmount())
            return;
        player.animate(894);
        player.getInventory().remove(item.before);
        player.getInventory().add(item.after);
        player.getStats().addXp(StatType.Crafting, item.exp, true);
    }

    public static void weavingOptions(Player player) {
        SkillDialogue.make(player,
                new SkillItem(BASKET.after.getId()).addAction((p, amount, event) -> weave(p, BASKET, amount)),
                new SkillItem(EMPTY_SACK.after.getId()).addAction((p, amount, event) -> weave(p, EMPTY_SACK, amount)),
                new SkillItem(DRIFT_NET.after.getId()).addAction((p, amount, event) -> weave(p, DRIFT_NET, amount)),
                new SkillItem(STRIP_OF_CLOTH.after.getId()).addAction((p, amount, event) -> weave(p, STRIP_OF_CLOTH, amount))
        );
    }

    public static void registerLoom(int objectId) {
        /**
         * Object interaction
         */
        ObjectAction.register(objectId, "weave", (player, obj) -> weavingOptions(player));
        ObjectAction.register(objectId, 2, (player, obj) -> weavingOptions(player));

        /**
         * Using the materials on the spinning wheel
         */
        ItemObjectAction.register(BASKET.before.getId(), objectId, (player, item, obj) -> weave(player, BASKET));
        ItemObjectAction.register(EMPTY_SACK.before.getId(), objectId, (player, item, obj) -> weave(player, EMPTY_SACK));
        ItemObjectAction.register(DRIFT_NET.before.getId(), objectId, (player, item, obj) -> weave(player, DRIFT_NET));
        ItemObjectAction.register(STRIP_OF_CLOTH.before.getId(), objectId, (player, item, obj) -> weave(player, STRIP_OF_CLOTH));
    }

    static final int[] LOOMS = { 787, 8717 };

    static {
        for(int LOOM : LOOMS) {
            registerLoom(LOOM);
        }
    }
}
