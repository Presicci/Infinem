package io.ruin.model.content.tasksystem.tasks;

import io.ruin.api.utils.StringUtils;
import io.ruin.cache.def.EnumDefinition;
import io.ruin.model.content.tasksystem.areas.AreaTaskTier;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;

import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/17/2021
 */
public enum TaskArea {
     GENERAL("General/Multiple Regions", 1, 2, 3, 4),
     ASGARNIA(5, 6, 7, 8),
     DESERT("Kharidian Desert", 9, 10, 11, 12),
     FREMENNIK("Fremennik Provinces", 13, 14, 15, 16),
     KANDARIN(17, 18, 19, 20),
     KARAMJA(21, 22, 23, 24),
     MISTHALIN(25, 26, 27, 28),
     MORYTANIA(29, 30, 31, 32),
     TIRANNWN(33, 34, 35, 36),
     WILDERNESS(37, 38, 39, 40),
     ZEAH(41, 42, 43, 44);

     public final String toString;

     private final int[] tierRequirements;

     TaskArea(String toString, int... tierRequirements) {
          this.toString = toString;
          this.tierRequirements = tierRequirements;
     }

     TaskArea(int... tierRequirements) {
          this.toString = StringUtils.capitalizeFirst(super.name().toLowerCase());
          this.tierRequirements = tierRequirements;
     }

     @Override
     public String toString() {
          return toString;
     }

     public boolean checkTierUnlock(Player player, AreaTaskTier tier, String message) {
          if (player.isStaff() && player.debug) return true;
          int pointsTill = getPointsTillTier(player, tier);
          if (pointsTill <= 0) return true;
          player.dialogue(false, new MessageDialogue("You need " + pointsTill + " more task points from " + toString + " tasks to " + message));
          return false;
     }

     public int getPointsTillTier(Player player, AreaTaskTier tier) {
          return Math.max(0, getPointThreshold(tier) - player.getTaskManager().getAreaTaskPoints(this.ordinal()));
     }

     public boolean hasTierUnlocked(Player player, AreaTaskTier tier) {
          if (player.isStaff() && player.debug) return true;
          return player.getTaskManager().getAreaTaskPoints(this.ordinal()) >= getPointThreshold(tier);
     }

     public int getPointThreshold(AreaTaskTier tier) {
          EnumDefinition tierEnum = EnumDefinition.get(9101);
          Map<Integer, Integer> tiers = tierEnum.getValuesAsInts();
          return tiers.get(tierRequirements[tier.ordinal()]);
     }

     public static TaskArea getTaskArea(String name) {
          for (TaskArea area : TaskArea.values()) {
               if (area.toString.equalsIgnoreCase(name))
                    return area;
          }
          return null;
     }
}
