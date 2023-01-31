package io.ruin.model.content.tasksystem.tasks;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/17/2021
 */
public enum TaskArea {
     GENERAL("General/Multiple Regions"),
     MISTHALIN,
     KARAMJA,
     ASGARNIA,
     FREMENNIK("Fremennik Provinces"),
     KANDARIN,
     DESERT("Kharidian Desert"),
     MORYTANIA,
     TIRANNWN,
     WILDERNESS,
     ZEAH;

     public final String toString;

     TaskArea() {
          this.toString = super.name();
     }

     TaskArea(String toString) {
          this.toString = toString;
     }

     @Override
     public String toString() {
          return toString;
     }

     public static TaskArea getTaskArea(String name) {
          for (TaskArea area : TaskArea.values()) {
               if (area.toString.equalsIgnoreCase(name))
                    return area;
          }
          return null;
     }
}
