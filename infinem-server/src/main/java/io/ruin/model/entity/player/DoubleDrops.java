package io.ruin.model.entity.player;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.content.bestiary.perks.impl.DamagePerk;
import io.ruin.model.content.bestiary.perks.impl.ExtraDropPerk;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.item.Item;
import io.ruin.model.item.attributes.AttributeExtensions;

import java.util.List;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 6/11/2020
 */
public class DoubleDrops {

    /*
     * Math to retrieve loot rolls after a kill
     */
    public static int getRolls(Player player, NPC npc) {
        int rolls = 1;
        if (World.doubleDrops)
            rolls++;
        double bestiaryChance = player.getBestiary().getBestiaryEntry(npc.getDef()).getPerkMultiplier(ExtraDropPerk.class, 0);
        if (bestiaryChance > 1) {
            int bestiaryRolls = (int) bestiaryChance;
            bestiaryChance -= bestiaryRolls;
            rolls += bestiaryRolls;
        }
        if (bestiaryChance > 0 && Random.get() < bestiaryChance) {
            rolls++;
        }
        if (Random.get(1, 100) <= gearCount(player) * 2) {
            rolls++;
        }
        return rolls;
    }

    /*
     * Method to display a visual chance at rolling multiple drops
     */
    public static int getChance(Player player) {
        int chance = 0;
        if (World.doubleDrops)
            chance += 100;
        if (player.getEquipment().contains(new Item(12785)))  //20% chance to double roll with ROW i
            chance += 20;
        chance += (gearCount(player) * 2);
        return chance;
    }

    private static int gearCount(Player player) {
        int gearRolls = 0;
        for(Item item : player.getEquipment().getItems()) {
            if(item != null && item.getDef() != null) {
                List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                boolean hasEffect = upgrades != null;
                if (hasEffect) {
                    for (String s : upgrades) {
                        try {
                            if (s.equalsIgnoreCase("empty"))
                                continue;
                            ItemEffect effect = ItemEffect.valueOf(s);
                            gearRolls += effect.getUpgrade().addDoubleDropRolls();
                        } catch (Exception ex) {
                            System.err.println("Unknown upgrade { " + s + " } found!");
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        return gearRolls;
    }
}
