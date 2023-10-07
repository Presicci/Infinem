package io.ruin.model.content.tasksystem.tasks.inter;

import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.content.tasksystem.tasks.TaskDifficulty;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/31/2023
 */
public class TaskSQLBuilder {

    public static final String REGION_FIELD = "FIELD(region, 'General/Multiple Regions', 'Asgarnia', 'Fremennik Provinces', 'Kandarin', 'Karamja', 'Kharidian Desert', 'Misthalin', 'Morytania', 'Tirannwn', 'Wilderness')";
    public static final String DIFFICULTY_FIELD = "FIELD(difficulty, 'Easy', 'Medium', 'Hard', 'Elite', 'Master')";

    public static String getSelectQuery(Player player, String search) {
        if (search == null || search.trim().isEmpty())
            return getSortQuery(player);
        System.out.println(getSearch(player, search));
        return getSearch(player, search);
    }

    private static String getSearch(Player player, String search) {
        String query = getSelect(player);
        return query + (query.endsWith("task_list") ? " WHERE " : "AND ") + "(name LIKE '%" + search + "%' OR region LIKE '%" + search + "%' OR difficulty LIKE '%" + search + "%')" + getOrder(player);
    }

    private static String getSortQuery(Player player) {
        return getSelect(player) + getOrder(player);
    }

    /**
     * Get the task order for the player.
     * 0 (default) - region,difficulty,name
     * 1 - difficulty,region,name
     * 2 - name,region,difficulty
     * 3 - skill?
     * @param player Player
     * @return ORDER BY string
     */
    private static String getOrder(Player player) {
        int sort = Config.TASK_INTERFACE_SORT.get(player);
        String order = sort == 0 ? REGION_FIELD + "," + DIFFICULTY_FIELD
                : sort == 1 ? DIFFICULTY_FIELD + "," + REGION_FIELD + ",name"
                : "name," + REGION_FIELD + "," + DIFFICULTY_FIELD;
        return " ORDER BY " + order;
    }

    private static String getSelect(Player player) {
        String filters = getFilters(player);
        return "SELECT * FROM task_list" + (filters.trim().isEmpty() ? "" : " WHERE " + filters);
    }

    /**
     * Processes task filters and returns a completed string
     * @param player Player
     * @return Filter string that should follow WHERE/OR
     */
    private static String getFilters(Player player) {
        int tier = Config.TASK_INTERFACE_TIER.get(player);
        String tierString = tier == 0 ? "" : "difficulty = '" + TaskDifficulty.values()[tier - 1].name() + "' ";
        int area = Config.TASK_INTERFACE_AREA.get(player);
        String areaString = area == 0 ? "" : "region = '" + TaskArea.values()[area - 1].toString() + "' ";
        int skill = Config.TASK_INTERFACE_SKILL.get(player); // TODO process this
        String filterString = "";
        if (!tierString.trim().isEmpty()) {
            filterString = filterString + tierString;
        }
        if (!areaString.trim().isEmpty()) {
            if (!filterString.trim().isEmpty())
                filterString += "AND ";
            filterString = filterString + areaString;
        }
        return filterString;
    }
}
