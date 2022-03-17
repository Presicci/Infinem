package io.ruin.data.impl.npcs;

import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.skills.slayer.SlayerCreature;
import io.ruin.model.skills.slayer.SlayerMaster;
import io.ruin.utility.Misc;

import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public class slayer_masters extends DataFile {
    @Override
    public String path() {
        return "npcs/slayermasters.json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<SlayerMaster> list = JsonUtils.fromJson(json, List.class, SlayerMaster.class);

        for (SlayerMaster master : list) {
            master.getDefs().forEach(task -> {
                if (task != null) {
                    if (SlayerCreature.lookup(task.getCreatureUid()) == null) {
                        throw new RuntimeException(Misc.format_string("could not load slayer task def %d: could not resolve uid; master: %d", task.getCreatureUid(), master.getNpcId()));
                    }
                }
            });
        }

        SlayerMaster.masters = list.toArray(new SlayerMaster[list.size()]);

        return list;
    }
}
