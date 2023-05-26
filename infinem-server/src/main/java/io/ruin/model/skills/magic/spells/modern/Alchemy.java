package io.ruin.model.skills.magic.spells.modern;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.journal.presets.Preset;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfEthereum;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.COINS_995;

public class Alchemy extends Spell {

    private enum Level {
        LOW(712, 763, 21, 31, 3, Rune.NATURE.toItem(1), Rune.FIRE.toItem(3)),
        HIGH(713, 764, 55, 65, 5, Rune.NATURE.toItem(1), Rune.FIRE.toItem(5))
        ;
        private int anim, gfx, levelReq;
        private double xp;
        private Item[] runes;
        private int delay;

        Level(int anim, int gfx, int levelReq, double xp, int delay, Item... runes) {
            this.anim = anim;
            this.gfx = gfx;
            this.levelReq = levelReq;
            this.xp = xp;
            this.runes = runes;
            this.delay = delay;
        }
    }

    public Alchemy(boolean low) {
        Level level = low ? Level.LOW : Level.HIGH;
        itemAction = (p, i) -> {
            if (!p.getStats().check(StatType.Magic, level.levelReq, "cast this spell"))
                return;
            RuneRemoval r = null;
            if (level.runes != null && (r = RuneRemoval.get(p, level.runes)) == null) {
                p.sendMessage("You don't have enough runes to cast this spell.");
                return;
            }
            if (p.alchDelay.isDelayed()) {
                // queue next alch
                p.startEvent(event -> {
                    event.delay(p.alchDelay.remaining());
                    itemAction.accept(p, i);
                });
                return;
            }
            tryAlch(p, i, r, level);
        };
        clickAction = (p, i) -> {
            p.sendMessage("" + i);
            if (i == 9) {
                alchemyWarningDialogue(p);
            }
        };
    }

    private static void alchemyWarningDialogue(Player player) {
        player.dialogue(
                new ItemDialogue().two(Items.NATURE_RUNE, Items.FIRE_RUNE, "A warning will be shown if the item is worth at least: " + Color.RED.wrap("" + NumberUtils.formatNumber(Config.ALCH_THRESHOLD.get(player)))
                        + "<br>Untradeable items " + (Config.ALCH_UNTRADEABLES.get(player) == 0 ? (Color.RED.wrap("may not") + " trigger such a") : "will " + Color.RED.wrap("always") + " trigger a") + " warning."),
                new OptionsDialogue(
                        new Option("Set value threshold", () -> {
                            player.integerInput("Set value threshold for alchemy warnings:", value -> {
                                Config.ALCH_THRESHOLD.set(player, value);
                                alchemyWarningDialogue(player);
                            });
                        }),
                        new Option((Config.ALCH_UNTRADEABLES.get(player) == 0 ? "Enable" : "Disable") + " untradeable item warning", () -> {
                            Config.ALCH_UNTRADEABLES.toggle(player);
                            alchemyWarningDialogue(player);
                        }),
                        new Option("Cancel")
                )
        );
    }

    private static void tryAlch(Player player, Item item, RuneRemoval runes, Level level) {
        if (item.getId() == 7462 || item.getId() == Preset.DRAGON_2H_SWORD || item.getId() == BraceletOfEthereum.REVENANT_ETHER) {
            player.sendMessage("You can't alchemize this item.");
            return;
        }

        int value = level == Level.LOW ? item.getDef().lowAlchValue : item.getDef().highAlchValue;
        if(item.getDef().isNote())
            value = item.getDef().fromNote().highAlchValue;
        if (value == 0 || item.getId() == COINS_995) {
            player.sendMessage("You can't alchemize this item.");
            return;
        }
        if (item.getAmount() == 1 && item.getDef().stackable && !player.getInventory().hasRoomFor(COINS_995)) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        if ((item.getId() == Rune.NATURE.getId() && item.getAmount() < level.runes[0].getAmount() + 1)
                || (item.getId() == Rune.FIRE.getId() && item.getAmount() < level.runes[1].getAmount() + 1)) {
            player.sendMessage("You don't have the required runes to cast this spell.");
            return;
        }
        if(item.getDef().free) {
            player.sendMessage("You can't alchemize this item.");
            return;
        }
        if (item.getDef().value > Config.ALCH_THRESHOLD.get(player)) {
            int finalValue = value;
            player.dialogue(new YesNoDialogue("Are you sure you want to alchemize that?",
                    item.getDef().name + " is above your alchemy warning threshold.", item, () -> {
                alch(player, item, runes, level, finalValue);
            }));
            return;
        }
        if (Config.ALCH_UNTRADEABLES.get(player) == 1 && !item.getDef().tradeable) {
            int finalValue = value;
            player.dialogue(new YesNoDialogue("Are you sure you want to alchemize that?",
                    item.getDef().name + " is untradeable.", item, () -> {
                alch(player, item, runes, level, finalValue);
            }));
            return;
        }
        alch(player, item, runes, level, value);
    }


    private static void alch(Player player, Item item, RuneRemoval runes, Level level, int value) {
        if (item.getDef().stackable)
            item.remove(1);
        else
            item.remove();
        player.animate(level.anim);
        player.graphics(level.gfx, 46, 0);
        player.getInventory().add(COINS_995, value);
        player.getPacketSender().sendClientScript(915, "i", 6);
        player.alchDelay.delay(level.delay);
        if (runes != null)
            runes.remove();
        player.getStats().addXp(StatType.Magic, level.xp, true);
        if (level == Level.HIGH)
            player.getTaskManager().doLookupByUUID(148, 1); // Cast the High Level Alchemy spell
    }
}
