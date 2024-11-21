package io.ruin.model.map.object.actions.impl.varlamore;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.prayer.BlessedBoneShards;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/20/2024
 */
public class ExposedAltar {

    private static final Map<Integer, Integer> BLESSABLES = new HashMap<>();

    private static void blessAll(Player player) {
        boolean blessed = false;
        boolean bone = false;
        for (Item i : player.getInventory().getItems()) {
            if (i == null) continue;
            int productId = BLESSABLES.getOrDefault(i.getId(), -1);
            if (productId == -1) continue;
            if (productId != 52799) bone = true;
            i.setId(productId);
            blessed = true;
        }
        if (blessed) {
            player.animate(832);
            player.sendFilteredMessage("You bless the " + (bone ? "bones." : "wine."));
        }
    }

    private static void bless(Player player, int id) {
        int productId = BLESSABLES.getOrDefault(id, -1);
        if (productId == -1) {
            player.sendMessage("That item can't be blessed.");
            return;
        }
        boolean blessed = false;
        boolean bone = false;
        for (Item i : player.getInventory().getItems()) {
            if (i == null) continue;
            if (i.getId() == id) {
                if (productId != 52799) bone = true;
                i.setId(productId);
                blessed = true;
            }
        }
        if (blessed) {
            player.animate(832);
            player.sendFilteredMessage("You bless the " + (bone ? "bones." : "wine."));
        }
    }

    static {
        BLESSABLES.put(Items.JUG_OF_WINE, 29386);
        for (BlessedBoneShards.Type bone : BlessedBoneShards.Type.values()) {
            if (bone.getBone() != null) BLESSABLES.put(bone.getBone().id, bone.getBlessedBoneId());
        }
        for (int key : BLESSABLES.keySet()) {
            ItemObjectAction.register(key, 52799, (player, item, obj) -> bless(player, key));
        }
        ObjectAction.register(52799, "bless", (player, obj) -> blessAll(player));
    }
}
