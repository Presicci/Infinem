package io.ruin.model.skills.fletching;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;

public enum Bolt {

    /**
     * Gem-tipped bolts
     */
    OPAL(11, 1.6, 877, 45, 879, 8472),    // bronze
    JADE(26, 2.4, 9139, 9187, 9335, 8474),    // blurite
    PEARL(41, 3.0, 9140, 46, 880, 8473),  // iron
    RED_TOPAZ(48, 4.0, 9141, 9188, 9336, 8475),   // steel
    SAPPHIRE(56, 4.0, 9142, 9189, 9337, 8476),    // mithril
    EMERALD(55, 4.0, 9142, 9190, 9338, 8476), // mithril
    RUBY(63, 6.3, 9143, 9191, 9339, 8477),    // addy
    DIAMOND(65, 7.0, 9143, 9192, 9340, 8477), // addy
    DRAGONSTONE(71, 8.2, 9144, 9193, 9341, 8478), // rune
    ONYX(73, 10.0, 9144, 9194, 9342, 8478), // rune

    /**
     * Dragon gem-tipped bolts
     */
    OPAL_DRAGON(84, 12.0, 21905, 45, 21955, 8479),
    JADE_DRAGON(84, 12.0, 21905, 9187, 21957, 8479),
    PEARL_DRAGON(84, 12.0, 21905, 46, 21959, 8479),
    RED_TOPAZ_DRAGON(84, 12.0, 21905, 9188, 21961, 8479),
    SAPPHIRE_DRAGON(84, 12.0, 21905, 9189, 21963, 8479),
    EMERALD_DRAGON(84, 12.0, 21905, 9190, 21965, 8479),
    RUBY_DRAGON(84, 12.0, 21905, 9191, 21967, 8479),
    DIAMOND_DRAGON(84, 12.0, 21905, 9192, 21969, 8479),
    DRAGONSTONE_DRAGON(84, 12.0, 21905, 9193, 21971, 8479),
    ONYX_DRAGON(84, 12.0, 21905, 9194, 21973, 8479),

    /**
     * Other
     */
    GRAPPLE_UNF(59, 11.0, 9142, 9416, 9418, 8476),
    GRAPPLE(59, 0, 9418, 954, 9419, 8476),
    AMETHYST(76, 10.6, 11875, 21338, 21316, 8473);

    public final int levelRequirement;
    public final double experience;
    public final int id;
    public final int tip;
    public final int tipped;
    public final String tippedName;
    public final int emote;

    Bolt(int levelRequirement, double experience, int id, int tip, int tipped, int emote) {
        this.levelRequirement = levelRequirement;
        this.experience = experience;
        this.id = id;
        this.tip = tip;
        this.tipped = tipped;
        this.tippedName = ItemDefinition.get(tipped).name;
        this.emote = emote;
    }

    private void make(Player player, Item boltItem, Item tipItem, int amount) {
        RandomEvent.attemptTrigger(player);
        boltItem.remove(amount);
        tipItem.remove(amount);
        player.getInventory().add(tipped, amount);
        player.getStats().addXp(StatType.Fletching, experience * amount, true);
        player.animate(emote);
        if (tipItem.getId() == 954)
            player.sendFilteredMessage("You make a mithril grapple.");
        else if (tipItem.getId() == 9416)
            player.sendFilteredMessage("You make an unfinished mithril grapple.");
        else if (amount == 1)
            player.sendFilteredMessage("You fletch a bolt.");
        else
            player.sendFilteredMessage("You fletch " + amount + " bolts");
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.FLETCH_AMMO, ItemDefinition.get(tipped).name, amount);
    }

    static {
        for (Bolt bolt : values()) {
            SkillItem item = new SkillItem(bolt.tipped).addAction((player, amount, event) -> {
                while (amount-- > 0) {
                    Item boltItem = player.getInventory().findItem(bolt.id);
                    if (boltItem == null)
                        return;
                    Item tipItem = player.getInventory().findItem(bolt.tip);
                    if (tipItem == null)
                        return;
                    int maxAmount = Math.min(boltItem.getAmount(), tipItem.getAmount());
                    int maxPossible = player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER) ? 100 : 10;
                    if (maxAmount > maxPossible) {
                        bolt.make(player, boltItem, tipItem, maxPossible);
                        event.delay(2);
                        continue;
                    }
                    bolt.make(player, boltItem, tipItem, maxAmount);
                    break;
                }
            });
            ItemItemAction.register(bolt.id, bolt.tip, (player, boltItem, tipItem) -> {
                if (!player.getStats().check(StatType.Fletching, bolt.levelRequirement, bolt.tipped, "make " + bolt.tippedName))
                    return;
                int maxAmount = Math.min(boltItem.getAmount(), tipItem.getAmount());
                if (maxAmount > 10) {
                    SkillDialogue.make(player, item);
                    return;
                }
                bolt.make(player, boltItem, tipItem, maxAmount);
            });
        }
    }
}