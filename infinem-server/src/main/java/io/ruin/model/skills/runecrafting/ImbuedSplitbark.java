package io.ruin.model.skills.runecrafting;

import io.ruin.api.utils.StringUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.ObjectDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/23/2024
 */
@Getter
@AllArgsConstructor
public enum ImbuedSplitbark {
    SWAMPBARK_GAUNTLETS(SplitbarkType.SWAMPBARK, 25392, Items.SPLITBARK_GAUNTLETS, 100, 42, 5),
    SWAMPBARK_BOOTS(SplitbarkType.SWAMPBARK, 25395, Items.SPLITBARK_BOOTS, 100, 42, 5),
    SWAMPBARK_HELM(SplitbarkType.SWAMPBARK, 25398, Items.SPLITBARK_HELM, 250, 46, 12.5),
    SWAMPBARK_LEGS(SplitbarkType.SWAMPBARK, 25401, Items.SPLITBARK_LEGS, 500, 48, 25),
    SWAMPBARK_BODY(SplitbarkType.SWAMPBARK, 25389, Items.SPLITBARK_BODY, 500, 48, 25),
    BLOODBARK_GAUNTLETS(SplitbarkType.BLOODBARK, 25407, Items.SPLITBARK_GAUNTLETS, 100, 77, 5),
    BLOODBARK_BOOTS(SplitbarkType.BLOODBARK, 25410, Items.SPLITBARK_BOOTS, 100, 77, 5),
    BLOODBARK_HELM(SplitbarkType.BLOODBARK, 25413, Items.SPLITBARK_HELM, 250, 79, 12.5),
    BLOODBARK_LEGS(SplitbarkType.BLOODBARK, 25416, Items.SPLITBARK_LEGS, 500, 81, 25),
    BLOODBARK_BODY(SplitbarkType.BLOODBARK, 25404, Items.SPLITBARK_BODY, 500, 81, 25);

    private final SplitbarkType type;
    private final int productId, requiredId, runesRequired, levelRequirement;
    private final double experience;

    private void imbue(Player player, Item item) {
        System.out.println("imbue");
        if (!player.hasAttribute(type.getAttributeKey())) {
            player.sendMessage("You don't have the knowledge to do this properly.");
            return;
        }
        if (!player.getStats().check(StatType.Runecrafting, levelRequirement, "imbue this")) {
            return;
        }
        if (player.getInventory().getAmount(type.runeId) < runesRequired) {
            player.sendMessage("You need " + runesRequired + " " + type.getRuneName() + " runes to imbue this with " + type.getRuneName() + " magic.");
            return;
        }
        player.lock();
        player.startEvent(e -> {
            player.animate(832);
            e.delay(1);
            item.setId(productId);
            player.getInventory().remove(type.runeId, runesRequired);
            player.getStats().addXp(StatType.Runecrafting, experience, true);
            player.sendMessage("You imbue the " + ItemDefinition.get(requiredId).name + " with " + type.getRuneName() + " magic.");
            player.unlock();
        });
    }

    @AllArgsConstructor
    private enum SplitbarkType {
        SWAMPBARK(34768, Items.NATURE_RUNE, 25478, 25474),
        BLOODBARK(27978, Items.BLOOD_RUNE, 25481, 25476);

        private final int altarId, runeId, scrollId, bookId;

        private String getRuneName() {
            return StringUtils.capitalizeFirst(name().toLowerCase().replace("bark", ""));
        }

        private String getAttributeKey() {
            return name() + "_SCROLL";
        }

        private void readBook(Player player, Item item) {
            item.setId(scrollId);
            player.sendMessage("As you open the ancient notes, a rough scroll falls out, and the book disintegrates.");
        }

        private void unlock(Player player, Item item) {
            if (player.hasAttribute(getAttributeKey())) {
                player.dialogue(new ItemDialogue().one(scrollId, "You've already gained the knowledge to infuse Splitbark armour with " + getRuneName() + " runes."));
                return;
            }
            item.remove();
            player.putAttribute(getAttributeKey(), 1);
            player.dialogue(new ItemDialogue().one(scrollId, "Reading through the scroll reveals the secret to infusing Splitbark armour with " + getRuneName() + " runes at a " + getRuneName() + " Altar to create " + getRuneName() + "bark armour!"));
            player.sendMessage("As you close the scroll it starts to disintegrate in your hands.");
        }
    }

    static {
        for (SplitbarkType type : SplitbarkType.values()) {
            ItemAction.registerInventory(type.scrollId, "read", type::unlock);
            ItemAction.registerInventory(type.bookId, "read", type::readBook);
        }
        for (ImbuedSplitbark splitbark : values()) {
            ItemObjectAction.register(splitbark.requiredId, splitbark.type.altarId, (player, item, obj) -> splitbark.imbue(player, item));
        }
    }
}
