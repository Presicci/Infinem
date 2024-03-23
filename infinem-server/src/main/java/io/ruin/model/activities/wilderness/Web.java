package io.ruin.model.activities.wilderness;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class Web {

    private static final int KNIFE = 946;

    public static void slashWeb(Player player, GameObject web) {
        int sharpTier = sharpItemChance(player);
        if (sharpTier == 0) return;
        player.startEvent(event -> {
            player.animate(sharpTier == 1 ? 911 : player.getCombat().weaponType.attackAnimation);
            if (sharpTier == 3 || Random.rollDie(2, 1)) {
                player.lock();
                player.sendMessage("You slash the web apart.");
                event.delay(1);
                World.startEvent(e -> {
                    web.setId(734);
                    e.delay(100);
                    web.setId(733);
                });
                player.unlock();
                return;
            }
            player.sendMessage("You fail to cut through it.");
        });
    }

    /**
     * Gets the player's sharp item quality
     * @return
     * 0 - No sharp item
     * 1 - Knife
     * 2 - Normal sharp item
     * 3 - Wilderness sword (only in wilderness)
     */
    private static int sharpItemChance(Player player) {
        ItemDefinition weaponDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        if(weaponDef != null && weaponDef.sharpWeapon) {
            return (weaponDef.id >= 13108 && weaponDef.id <= 13111 && player.wildernessLevel > 0) ? 3 : 2;
        } else {
            for (Item item : player.getInventory().getItems()) {
                if (item != null && item.getDef() != null && item.getDef().sharpWeapon)
                    return (item.getDef().id >= 13108 && item.getDef().id <= 13111 && player.wildernessLevel > 0) ? 3 : 2;
            }
            if(!player.getInventory().hasId(KNIFE)) {
                player.sendMessage("Only a sharp blade can cut through this sticky web.");
                return 0;
            }
            return 1;
        }
    }

    static {
        ObjectAction.register(733, "slash", Web::slashWeb);
        ItemDefinition.forEach(def -> {
            if(def.equipSlot != Equipment.SLOT_WEAPON)
                return;
            String name = def.name.toLowerCase();
            if(name.contains("axe") || name.contains("claws") || name.contains("dagger") || name.contains("sword")
                    || name.contains("scimitar") || name.contains("halberd") || name.contains("whip") || name.contains("tentacle")
                    || name.contains("blade") || name.contains("machete") || name.contains("scythe") || name.contains("staff of the dead")
                    || name.contains("xil-ek") || name.contains("excalibur") || name.contains("spear") || name.contains("hasta")
                    || name.contains("rapier"))
                def.sharpWeapon = true;
        });
    }
}
