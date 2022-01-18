package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
@AllArgsConstructor @Getter
public enum SingingBowl {

    CRYSTAL_HELM(70, 2500, 23971, new Item[]{ new Item(Items.CRYSTAL_SHARD, 50), new Item(Items.CRYSTAL_ARMOUR_SEED) }),
    CRYSTAL_LEGS(72, 5000, 23979, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item(Items.CRYSTAL_ARMOUR_SEED) }),
    CRYSTAL_BODY(74, 7500, 23975, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item(Items.CRYSTAL_ARMOUR_SEED) }),
    CRYSTAL_AXE(76, 6000, 23673, new Item[]{ new Item(Items.CRYSTAL_SHARD, 120), new Item(Items.CRYSTAL_TOOL_SEED), new Item(Items.DRAGON_AXE) }),
    CRYSTAL_HARPOON(76, 6000, 23762, new Item[]{ new Item(Items.CRYSTAL_SHARD, 120), new Item(Items.CRYSTAL_TOOL_SEED), new Item(Items.DRAGON_HARPOON) }),
    CRYSTAL_PICKAXE(76, 6000, 23680, new Item[]{ new Item(Items.CRYSTAL_SHARD, 120), new Item(Items.CRYSTAL_TOOL_SEED), new Item(Items.DRAGON_PICKAXE) }),
    CRYSTAL_BOW(78, 2000, 23983, new Item[]{ new Item(Items.CRYSTAL_SHARD, 40), new Item(Items.CRYSTAL_WEAPON_SEED) }),
    CRYSTAL_HALBERD(78, 2000, 23987, new Item[]{ new Item(Items.CRYSTAL_SHARD, 40), new Item(Items.CRYSTAL_WEAPON_SEED) }),
    CRYSTAL_SHIELD(78, 2000, 23991, new Item[]{ new Item(Items.CRYSTAL_SHARD, 40), new Item(Items.CRYSTAL_WEAPON_SEED) }),
    ENHANCED_CKEY(80, 500, 23951, new Item[]{ new Item(Items.CRYSTAL_SHARD, 10), new Item(Items.CRYSTAL_KEY) }),
    ETERNAL_CRYSTAL(80, 5000, 23946, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item(Items.ENHANCED_CRYSTAL_TELEPORT_SEED) }),
    //BLADE_OF_SAELDOR(82, 5000, 23995, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item() }),
    //BOW_OF_FAERDHINEN(82, 5000, 23946, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item(Items.ENHANCED_CRYSTAL_TELEPORT_SEED) }),
    BLADE_OF_SAELDOR_C(82, 0, 24551, new Item[]{ new Item(Items.CRYSTAL_SHARD, 1000), new Item(Items.BLADE_OF_SAELDOR_INACTIVE) }),
    //BOW_OF_FAERDHINEN_C(82, 0, 23946, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item(Items.ENHANCED_CRYSTAL_TELEPORT_SEED) })
    ;

    private final int levelRequired;
    private final double experience;
    private final int product;
    private final Item[] mats;

    public static SingingBowl getByProductID(int id) {
        for (SingingBowl sb : values()) {
            if (sb.getProduct() == id) {
                return sb;
            }
        }
        return null;
    }

    private static void open(Player player) {
        SkillDialogue.make(
                player,
                (p, item) -> makeItem(player, item),
                new SkillItem(23971),
                new SkillItem(23979),
                new SkillItem(23975),
                new SkillItem(23673),
                new SkillItem(23762),
                new SkillItem(23680),
                new SkillItem(23983),
                new SkillItem(23987),
                new SkillItem(23991),
                new SkillItem(23951),
                new SkillItem(23946)
        );
    }

    private static void makeItem(Player player, Item item /*SingingBowl singingBowl*/) {
        player.closeInterface(InterfaceType.CHATBOX);

        SingingBowl singingBowl = getByProductID(item.getId());

        if (singingBowl == null) {
            return;
        }

        player.startEvent(event -> {
            if (!player.getStats().check(StatType.Crafting, singingBowl.levelRequired)) {
                player.sendMessage("<col=880000>You need a crafting level of " + singingBowl.levelRequired + " to continue");
                return;
            }
            if (!player.getStats().check(StatType.Smithing, singingBowl.levelRequired)) {
                player.sendMessage("<col=880000>You need a smithing level of " + singingBowl.levelRequired + " to continue");
                return;
            }
            for (Item mat : singingBowl.getMats()) {
                if (!player.getInventory().contains(mat.getId(), mat.getAmount())) {
                    player.sendMessage("<col=880000>You don't have the required items to continue.");
                    return;
                }
            }
            player.animate(899);    // TODO get proper singing animation
            player.getInventory().add(singingBowl.product, 1);
            for (Item mat : singingBowl.getMats()) {
                player.getInventory().remove(mat);
            }
            player.getStats().addXp(StatType.Crafting, singingBowl.getExperience(), false);
            player.getStats().addXp(StatType.Smithing, singingBowl.getExperience(), false);
            event.delay(2);
        });
    }

    static {
        ObjectAction.register(36552, "sing-crystal", (player, obj) -> open(player));
    }

}
