package io.ruin.model.content.tasksystem.tasks;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/17/2021
 */
public enum TaskArea {
     GENERAL("general/multiple regions"),
     MISTHALIN,
     KARAMJA,
     ASGARNIA,
     FREMENNIK("fremennik provinces"),
     KANDARIN,
     DESERT("kharidian desert"),
     MORYTANIA,
     TIRANNWN,
     WILDERNESS,
     ZEAH;

     public final String toString;

     TaskArea() {
          this.toString = super.toString();
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
