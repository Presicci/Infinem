package io.ruin.model.content.tasksystem.tasks;

import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.cache.def.ItemDefinition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/2/2024
 */
public class TaskCache {

    static {
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                statement = connection.prepareStatement("SELECT * FROM task_list WHERE category LIKE 'UNLOCKITEMREGEX'");
                rs = statement.executeQuery();
                while (rs.next()) {
                    int uuid = rs.getInt("uuid");
                    String triggerString = rs.getString("required_object");
                    if (!triggerString.trim().isEmpty()) {
                        String[] triggers = triggerString.trim().split(",");
                        for (String s : triggers) {
                            Pattern pattern = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
                            ItemDefinition.forEach(e -> {
                                if (pattern.matcher(e.name.toLowerCase()).find())
                                    e.custom_values.put("UNLOCKITEMREGEX", uuid);
                            });
                        }
                    }
                }
            } finally {
                DatabaseUtils.close(statement, rs);
            }
        });
    }
}
