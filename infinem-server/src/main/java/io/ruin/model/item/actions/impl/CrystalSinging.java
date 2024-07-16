package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 * <a href="https://oldschool.runescape.wiki/w/Crystal_singing">...</a>
 */
@Getter
public enum CrystalSinging {
    CELESTIAL_SIGNET(70, 5000, 25543, 100, new Item(23943), new Item(25539), new Item(25527, 1000)),
    CRYSTAL_HELM(70, 2500, 23971, 2500, 50, new Item(Items.CRYSTAL_ARMOUR_SEED)),
    CRYSTAL_LEGS(72, 5000, 23979, 2500, 100, new Item(Items.CRYSTAL_ARMOUR_SEED, 2)),
    CRYSTAL_BODY(74, 7500, 23975, 2500, 100, new Item(Items.CRYSTAL_ARMOUR_SEED, 3)),
    CRYSTAL_AXE(76, 6000, 23673, 10000, 120, new Item(Items.DRAGON_AXE), new Item(Items.CRYSTAL_TOOL_SEED)),
    CRYSTAL_HARPOON(76, 6000, 23762, 10000, 120, new Item(Items.DRAGON_HARPOON), new Item(Items.CRYSTAL_TOOL_SEED)),
    CRYSTAL_PICKAXE(76, 6000, 23680, 10000, 120, new Item(Items.DRAGON_PICKAXE), new Item(Items.CRYSTAL_TOOL_SEED)),
    CRYSTAL_BOW(78, 2000, 23983, 2500, 40, new Item(Items.CRYSTAL_WEAPON_SEED)),
    CRYSTAL_HALBERD(78, 2000, 23987, 2500, 40, new Item(Items.CRYSTAL_WEAPON_SEED)),
    CRYSTAL_SHIELD(78, 2000, 23991, 2500, 40, new Item(Items.CRYSTAL_WEAPON_SEED)),
    ENHANCED_CKEY(80, 500, 23951, 10, new Item(Items.CRYSTAL_KEY)),
    ETERNAL_CRYSTAL(80, 5000, 23946, 100, new Item(Items.ENHANCED_CRYSTAL_TELEPORT_SEED)),
    //BLADE_OF_SAELDOR(82, 5000, 23995, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item() }),
    //BOW_OF_FAERDHINEN(82, 5000, 23946, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item(Items.ENHANCED_CRYSTAL_TELEPORT_SEED) }),
    BLADE_OF_SAELDOR_C(82, 0, 24551, 1000, new Item(Items.BLADE_OF_SAELDOR_INACTIVE)),
    //BOW_OF_FAERDHINEN_C(82, 0, 23946, new Item[]{ new Item(Items.CRYSTAL_SHARD, 100), new Item(Items.ENHANCED_CRYSTAL_TELEPORT_SEED) })
    ;

    private final int levelRequirement, productId, requiredShards, initialCharges;
    private final double experience;
    private final Item[] requiredItems;

    CrystalSinging(int levelRequirement, double experience, int productId, int requiredShards, Item... requiredItems) {
        this(levelRequirement, experience, productId, 0, requiredShards, requiredItems);
    }

    CrystalSinging(int levelRequirement, double experience, int productId, int initialCharges, int requiredShards, Item... requiredItems) {
        this.levelRequirement = levelRequirement;
        this.experience = experience;
        this.productId = productId;
        this.initialCharges = initialCharges;
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

    private static void open(Player player) {
        List<SkillItem> items = new ArrayList<>();
        for (CrystalSinging sing : values()) {
            if (player.getInventory().getAmount(Items.CRYSTAL_SHARD) >= sing.getRequiredShards()
                    && player.getInventory().containsAll(false, sing.requiredItems)) {
                items.add(new SkillItem(sing.productId).addAction((p, integer, event) -> makeItem(player, sing, integer)));
            }
        }
        if (!items.isEmpty()) {
            SkillDialogue.make(player, items.toArray(new SkillItem[0]));
        } else {
            player.sendMessage("There is nothing you can make.");
        }

    }

    private static void makeItem(Player player, CrystalSinging sing, int amountToMake) {
        if (!player.getStats().check(StatType.Crafting, sing.levelRequirement)) {
            player.dialogue(new MessageDialogue("You need a crafting level of " + sing.levelRequirement + " to make this"));
            return;
        }
        if (!player.getStats().check(StatType.Smithing, sing.levelRequirement)) {
            player.dialogue(new MessageDialogue("You need a smithing level of " + sing.levelRequirement + " to make this"));
            return;
        }
        if (player.getInventory().getAmount(Items.CRYSTAL_SHARD) < sing.requiredShards) {
            player.sendMessage(sing.getRequiredItemsString() + "to make this.");
            return;
        }
        for (Item requiredItem : sing.getRequiredItems()) {
            if (!player.getInventory().contains(requiredItem.getId(), requiredItem.getAmount(), false, true)) {
                player.sendMessage(sing.getRequiredItemsString() + "to make this.");
                return;
            }
        }
        player.startEvent(event -> {
            int amount = amountToMake;
            while (amount-- > 0) {
                if (player.getInventory().getAmount(Items.CRYSTAL_SHARD) < sing.requiredShards) {
                    player.sendMessage("You've run out of crystal shards.");
                    return;
                }
                for (Item requiredItem : sing.getRequiredItems()) {
                    if (!player.getInventory().contains(requiredItem.getId(), requiredItem.getAmount(), false, true)) {
                        return;
                    }
                }
                player.animate(899);    // TODO get proper singing animation
                player.getInventory().remove(Items.CRYSTAL_SHARD, sing.requiredShards);
                for (int index = 0; index < sing.requiredItems.length; index++) {
                    if (index == 0) {
                        if (sing.requiredItems[index].getAmount() > 1) {
                            player.getInventory().remove(sing.requiredItems[index]);
                            Item item = new Item(sing.productId, 1);
                            if (sing.initialCharges > 0) {
                                item.setCharges(sing.initialCharges);
                            }
                            player.getInventory().add(item);
                        } else {
                            Item toReplace = player.getInventory().findItem(sing.requiredItems[index].getId());
                            toReplace.setId(sing.productId);
                            if (sing.initialCharges > 0) {
                                toReplace.setCharges(sing.initialCharges);
                            }
                        }
                    } else {
                        player.getInventory().remove(sing.requiredItems[index]);
                    }
                }
                player.getStats().addXp(StatType.Crafting, sing.getExperience(), false);
                player.getStats().addXp(StatType.Smithing, sing.getExperience(), false);
                if (sing == ETERNAL_CRYSTAL)
                    player.getTaskManager().doLookupByUUID(805);    // Craft an Eternal Teleport Crystal
                event.delay(2);
            }
        });
    }

    static {
        ObjectAction.register(36552, "sing-crystal", (player, obj) -> open(player));
    }

}
