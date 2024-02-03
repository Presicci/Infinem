package io.ruin.model.skills.herblore;

import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum BarbarianMix {
    ATTACK_MIX(4, 8, 3, Potion.ATTACK, 11431, 11429),
    ANTIPOISON_MIX(6, 12, 3, Potion.ANTIPOISON, 11435, 11433),
    RELICYMS_MIX(9, 14, 3, Potion.RELICYMS_BALM, 11439, 11437),
    STRENGTH_MIX(14, 17, 3, Potion.STRENGTH, 11441, 11443),
    RESTORE_MIX(24, 21, 3, Potion.RESTORE, 11451, 11449),
    ENERGY_MIX(29, 23, 3, Potion.ENERGY, 11455, 11453),
    DEFENCE_MIX(33, 25, 6, Potion.DEFENCE, 11459, 11457),
    AGILITY_MIX(37, 27, 6, Potion.AGILITY, 11463, 11461),
    COMBAT_MIX(40, 28, 3, Potion.COMBAT, 11447, 11445),
    PRAYER_MIX(42, 29, 6, Potion.PRAYER, 11467, 11465),
    SUPERATTACK_MIX(47, 33, 6, Potion.SUPER_ATTACK, 11471, 11469),
    ANTIPOISON_SUPERMIX(51, 35, 6, Potion.SUPER_ANTIPOISON, 11475, 11473),
    FISHING_MIX(53, 38, 6, Potion.FISHING, 11479, 11477),
    SUPER_ENERGY_MIX(56, 39, 6, Potion.SUPER_ENERGY, 11483, 11481),
    HUNTING_MIX(58, 40, 6, Potion.HUNTER, 11519, 11517),
    SUPER_STRENGTH_MIX(59, 42, 6, Potion.SUPER_STRENGTH, 11487, 11485),
    MAGIC_ESSENCE_MIX(61, 43, 6, Potion.MAGIC_ESSENCE, 11491, 11489),
    SUPER_RESTORE_MIX(67, 48, 6, Potion.SUPER_RESTORE, 11495, 11493),
    SUPER_DEFENCE_MIX(71, 50, 6, Potion.SUPER_DEFENCE, 11499, 11497),
    ANTIDOTE_PLUS_MIX(74, 52, 6, Potion.ANTIDOTE_PLUS, 11503, 11501),
    ANTIFIRE_MIX(75, 53, 6, Potion.ANTIFIRE, 11507, 11505),
    RANGING_MIX(80, 54, 6, Potion.RANGING, 11511, 11509),
    MAGIC_MIX(83, 57, 6, Potion.MAGIC, 11515, 11513),
    ZAMORAK_MIX(85, 58, 6, Potion.ZAMORAK_BREW, 11523, 11521),
    STAMINA_MIX(86, 60, 6, Potion.STAMINA, 12635, 12633),
    EXTENDED_ANTIFIRE_MIX(91, 61, 6, Potion.EXTENDED_ANTIFIRE, 11962, 11960),
    //ANCIENT_MIX(92, 63, 6, ),
    SUPER_ANTIFIRE_MIX(98, 70, 6, Potion.SUPER_ANTIFIRE, 21997, 21994),
    EXTENDED_SUPER_ANTIFIRE_MIX(99, 78, 6, Potion.EXTENDED_SUPER_ANTIFIRE, 22224, 22221);

    private final int levelReq, experience, heal;
    private final Potion basePotion;
    public final int[] vialIds;

    BarbarianMix(int levelReq, int experience, int heal, Potion basePotion, int... vialIds) {
        this.levelReq = levelReq;
        this.experience = experience;
        this.heal = heal;
        this.basePotion = basePotion;
        this.vialIds = vialIds;
    }

    private void mixAction(Player player, Item basePotion, Item fish) {
        fish.remove();
        basePotion.setId(vialIds[1]);
        player.getStats().addXp(StatType.Herblore, experience, true);
        player.getTaskManager().doSkillItemLookup(vialIds[1]);
        player.animate(363);
    }

    private void decant(Player player, Item potionOne, Item potionTwo) {
        if (potionOne.getId() == vialIds[0] && potionTwo.getId() == vialIds[0]) {
            potionTwo.setId(vialIds[1]);
            potionOne.setId(Items.VIAL);
        } else if (potionOne.getId() == vialIds[1] && potionTwo.getId() == Items.VIAL) {
            potionOne.setId(vialIds[0]);
            potionTwo.setId(vialIds[0]);
        }
    }

    private void register() {
        int primaryId = basePotion.vialIds[1];
        int[] secondaryIds = ordinal() <= 4 ? new int[] { Items.ROE, Items.CAVIAR } : new int[] { Items.CAVIAR };
        SkillItem item = new SkillItem(vialIds[1]).addAction((player, amount, event) -> {
            while (amount-- > 0) {
                Item primaryItem = player.getInventory().findItem(primaryId);
                if (primaryItem == null)
                    return;
                List<Item> secondaryItems = player.getInventory().collectItems(secondaryIds);
                if (secondaryItems == null || secondaryItems.isEmpty())
                    return;
                mixAction(player, primaryItem, secondaryItems.get(0));
                if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                    event.delay(2);
                }
            }
        });
        for (int secondaryId : secondaryIds) {
            ItemItemAction.register(primaryId, secondaryId, (player, primary, secondary) -> {
                if (!player.getStats().check(StatType.Herblore, levelReq, "make this potion"))
                    return;
                if (player.getInventory().hasMultiple(secondaryId) && player.getInventory().hasMultiple(primaryId)) {
                    SkillDialogue.make(player, item);
                    return;
                }
                Item primaryItem = player.getInventory().findItem(primaryId);
                Item secondaryItem = player.getInventory().findItem(secondaryId);
                if (primaryItem == null || secondaryItem == null) {
                    player.sendMessage("You need more ingredients to make this potion.");
                    return;
                }
                mixAction(player, primary, secondary);
            });
        }
        ItemItemAction.register(vialIds[0], vialIds[0], (this::decant));
    }

    public static final Map<Potion, BarbarianMix> MIXES = new HashMap<>();

    public static void registerMixes() {
        for (BarbarianMix mix : values()) {
            MIXES.put(mix.basePotion, mix);
            mix.register();
        }
    }
}
