package io.ruin.model.content.tasksystem.tasks;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.content.tasksystem.tasks.areas.AreaTaskTier;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/17/2021
 */
public enum TaskArea {
     GENERAL("General/Multiple Regions"),
     ASGARNIA,
     DESERT("Kharidian Desert"),
     FREMENNIK("Fremennik Provinces"),
     KANDARIN,
     KARAMJA,
     MISTHALIN,
     MORYTANIA,
     TIRANNWN,
     WILDERNESS,
     ZEAH;

     public final String toString;

     TaskArea() {
          this.toString = StringUtils.capitalizeFirst(super.name().toLowerCase());
     }

     TaskArea(String toString) {
          this.toString = toString;
     }

     @Override
     public String toString() {
          return toString;
     }

     public boolean checkTierUnlock(Player player, AreaTaskTier tier, String message) {
          int pointsTill = getPointsTillTier(player, tier);
          if (pointsTill <= 0) return true;
          player.dialogue(false, new MessageDialogue("You need " + pointsTill + " more task points from " + toString + " tasks to " + message));
          return false;
     }

     public int getPointsTillTier(Player player, AreaTaskTier tier) {
          return Math.max(0, tier.getPointThreshold() - player.getTaskManager().getAreaTaskPoints(this.ordinal()));
     }

     public boolean hasTierUnlocked(Player player, AreaTaskTier tier) {
          return player.getTaskManager().getAreaTaskPoints(this.ordinal()) >= tier.getPointThreshold();
     }

     public static TaskArea getTaskArea(String name) {
          for (TaskArea area : TaskArea.values()) {
               if (area.toString.equalsIgnoreCase(name))
                    return area;
          }
          return null;
     }
}
