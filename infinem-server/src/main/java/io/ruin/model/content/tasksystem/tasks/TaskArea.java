package io.ruin.model.content.tasksystem.tasks;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/17/2021
 */
public enum TaskArea {
     TIRANNWN, KANDARIN, FREMENNIK("fremennik provinces"), KARAMJA, ASGARNIA, MISTHALIN, WILDERNESS, DESERT, MORYTANIA, ZEAH, GENERAL("general/multiple regions");

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
}
