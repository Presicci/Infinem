package io.ruin.model.combat.special.magic;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;

//Power of Death: Reduce all melee damage you receive by 50% for the next
//minute while the staff remains equipped. Stacks with Protect from Melee. (100%)
public class StaffOfTheDead implements Special {

    @Override
    public boolean accept(ItemDefinition def, String name) {
        return def.id == Items.STAFF_OF_THE_DEAD || def.id == Items.TOXIC_STAFF_OF_THE_DEAD || def.id == Items.TOXIC_STAFF_UNCHARGED || def.id == Items.STAFF_OF_LIGHT;
    }

    @Override
    public boolean handleActivation(Player player) {
        if(DuelRule.NO_MAGIC.isToggled(player)) {
            player.sendMessage("Magic attacks have been disabled for this duel!");
            return true;
        }
        if(player.sotdDelay.isDelayed()) {
            player.sendMessage("<col=3d5d2b>You're already protected by the spirits of deceased evildoers.");
            return false;
        }
        if(!player.getCombat().useSpecialEnergy(100))
            return false;
        player.sotdDelay.delay(100);
        int staff = player.getEquipment().getId(Equipment.SLOT_WEAPON);
        player.animate(staff == Items.STAFF_OF_THE_DEAD ? 7083
                : staff == Items.TOXIC_STAFF_OF_THE_DEAD || staff == Items.TOXIC_STAFF_UNCHARGED ? 1720
                : staff == Items.STAFF_OF_LIGHT ? 7967
                : 1720, 0);
        player.graphics(1228, 300, 0);
        player.publicSound(1595);
        player.sendMessage("<col=3d5d2b>Spirits of deceased evildoers offer you their protection.");
        return true;
    }
}