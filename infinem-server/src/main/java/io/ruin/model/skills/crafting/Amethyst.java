package io.ruin.model.skills.crafting;

import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.ProductionMaster;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

public enum Amethyst {

    AMETHYST_BOLT_TIPS(21338, 83, 60.0, 15),
    AMETHYST_ARROW_TIPS(21350, 85, 60.0, 15),
    AMETHYST_JAVELIN_HEADS(21352, 87, 60.0, 5),
    AMETHYST_DART_TIPS(25853, 89, 60.0, 8);

    public final int itemID, levelReq, amount;
    public final double exp;

    Amethyst(int itemID, int levelReq, double exp, int amount) {
        this.itemID = itemID;
        this.levelReq = levelReq;
        this.exp = exp;
        this.amount = amount;
    }

    private static final int AMETHYST = 21347;

    private static void craft(Player player, Amethyst crystal, int amount) {
        player.closeInterfaces();
        if (!player.getStats().check(StatType.Crafting, crystal.levelReq, "make this"))
            return;
        int maxAmount = player.getInventory().getAmount(AMETHYST);
        if (maxAmount < amount)
            amount = maxAmount;
        RandomEvent.attemptTrigger(player);
        final int amt = amount;
        player.startEvent(event -> {
            int made = 0;
            int prodCount = 0;
            while (made++ < amt) {
                Item item = player.getInventory().findItem(AMETHYST);
                if(item == null)
                    break;
                item.remove();
                player.getInventory().add(crystal.itemID, crystal.amount);
                player.animate(6295);
                player.privateSound(2586);
                player.getStats().addXp(StatType.Crafting, crystal.exp, true);
                if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER))
                    event.delay(2);
                if (ProductionMaster.roll(player))
                    prodCount++;
            }
            ProductionMaster.extra(player, prodCount * crystal.amount, crystal.itemID, StatType.Crafting, crystal.exp * prodCount);
        });
    }

    static {
        ItemItemAction.register(AMETHYST, Tool.CHISEL, (player, primary, secondary) -> {
            SkillDialogue.make(player,
                    new SkillItem(AMETHYST_BOLT_TIPS.itemID).addAction((p, amount, event) -> craft(p, AMETHYST_BOLT_TIPS, amount)),
                    new SkillItem(AMETHYST_ARROW_TIPS.itemID).addAction((p, amount, event) -> craft(p, AMETHYST_ARROW_TIPS, amount)),
                    new SkillItem(AMETHYST_JAVELIN_HEADS.itemID).addAction((p, amount, event) -> craft(p, AMETHYST_JAVELIN_HEADS, amount)),
                    new SkillItem(AMETHYST_DART_TIPS.itemID).addAction((p, amount, event) -> craft(p, AMETHYST_DART_TIPS, amount)));
        });
    }
}
