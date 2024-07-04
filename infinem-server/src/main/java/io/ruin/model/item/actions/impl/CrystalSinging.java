package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
@Getter
public enum CrystalSinging {
    CRYSTAL_HELM(70, 2500, 23971, 50, new Item(Items.CRYSTAL_ARMOUR_SEED)),
    CRYSTAL_LEGS(72, 5000, 23979, 100, new Item(Items.CRYSTAL_ARMOUR_SEED)),
    CRYSTAL_BODY(74, 7500, 23975, 100, new Item(Items.CRYSTAL_ARMOUR_SEED)),
    CRYSTAL_AXE(76, 6000, 23673, 120, new Item(Items.DRAGON_AXE), new Item(Items.CRYSTAL_TOOL_SEED)),
    CRYSTAL_HARPOON(76, 6000, 23762, 120, new Item(Items.DRAGON_HARPOON), new Item(Items.CRYSTAL_TOOL_SEED)),
    CRYSTAL_PICKAXE(76, 6000, 23680, 120, new Item(Items.DRAGON_PICKAXE), new Item(Items.CRYSTAL_TOOL_SEED)),
    CRYSTAL_BOW(78, 2000, 23983, 40, new Item(Items.CRYSTAL_WEAPON_SEED)),
    CRYSTAL_HALBERD(78, 2000, 23987, 40, new Item(Items.CRYSTAL_WEAPON_SEED)),
    CRYSTAL_SHIELD(78, 2000, 23991, 40, new Item(Items.CRYSTAL_WEAPON_SEED)),
    ENHANCED_CKEY(80, 500, 23951, 10, new Item(Items.CRYSTAL_KEY)),
    ETERNAL_CRYSTAL(80, 5000, 23946, 100, new Item(Items.ENHANCED_CRYSTAL_TELEPORT_SEED)),
    //BLADE_OF_SAELDOR(82, 5000, 23995, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item() }),
    //BOW_OF_FAERDHINEN(82, 5000, 23946, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item(Items.ENHANCED_CRYSTAL_TELEPORT_SEED) }),
    BLADE_OF_SAELDOR_C(82, 0, 24551, 1000, new Item(Items.BLADE_OF_SAELDOR_INACTIVE)),
    //BOW_OF_FAERDHINEN_C(82, 0, 23946, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item(Items.ENHANCED_CRYSTAL_TELEPORT_SEED) })
    ;

    private final int levelRequirement, productId, requiredShards;
    private final double experience;
    private final Item[] requiredItems;

    CrystalSinging(int levelRequirement, double experience, int productId, int requiredShards, Item... requiredItems) {
        this.levelRequirement = levelRequirement;
        this.experience = experience;
        this.productId = productId;
        this.requiredShards = requiredShards;
        this.requiredItems = requiredItems;
    }

    public boolean hasRequiredItems(Player player) {
        if (player.getInventory().getAmount(Items.CRYSTAL_SHARD) < requiredShards) {
            return false;
        }
        for (Item item : requiredItems) {
            if (!player.getInventory().hasItem(item.getId(), item.getAmount())) {
                return false;
            }
        }
        return true;
    }

    public String getRequiredItemsString() {
        StringBuilder sb = new StringBuilder("You need ");
        sb.append(requiredShards);
        sb.append(" crystal shards, ");
        for (Item item : requiredItems) {
            if (item.getAmount() > 1) {
                sb.append(item.getAmount());
                sb.append(" ");
                sb.append(item.getDef().name);
            } else {
                sb.append(item.getDef().descriptiveName);
            }
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 2);
        int lastCommaIndex = sb.lastIndexOf(",");;
        sb.replace(lastCommaIndex, lastCommaIndex + 1, " and");
        return sb.toString();
    }

    public static CrystalSinging getByProductID(int id) {
        for (CrystalSinging sb : values()) {
            if (sb.getProductId() == id) {
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
        CrystalSinging sing = getByProductID(item.getId());
        if (sing == null) {
            return;
        }
        player.startEvent(event -> {
            if (!player.getStats().check(StatType.Crafting, sing.levelRequirement)) {
                player.sendMessage("<col=880000>You need a crafting level of " + sing.levelRequirement + " to make this");
                return;
            }
            if (!player.getStats().check(StatType.Smithing, sing.levelRequirement)) {
                player.sendMessage("<col=880000>You need a smithing level of " + sing.levelRequirement + " to make this");
                return;
            }
            if (player.getInventory().getAmount(Items.CRYSTAL_SHARD) < sing.requiredShards) {
                return;
            }
            for (Item requiredItem : sing.getRequiredItems()) {
                if (!player.getInventory().contains(requiredItem.getId(), requiredItem.getAmount(), false, true)) {
                    player.sendMessage(sing.getRequiredItemsString() + "to make this.");
                    return;
                }
            }
            player.animate(899);    // TODO get proper singing animation
            player.getInventory().remove(Items.CRYSTAL_SHARD, sing.requiredShards);
            for (int index = 0; index < sing.requiredItems.length; index++) {
                if (index == 0) {
                    Item toReplace = player.getInventory().findItem(sing.requiredItems[index].getId());
                    toReplace.setId(sing.productId);
                } else {
                    player.getInventory().remove(sing.requiredItems[index]);
                }
            }
            player.getStats().addXp(StatType.Crafting, sing.getExperience(), false);
            player.getStats().addXp(StatType.Smithing, sing.getExperience(), false);
            event.delay(2);
        });
    }

    static {
        ObjectAction.register(36552, "sing-crystal", (player, obj) -> open(player));
    }

}
