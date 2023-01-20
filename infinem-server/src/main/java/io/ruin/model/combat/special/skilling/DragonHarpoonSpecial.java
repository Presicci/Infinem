package io.ruin.model.combat.special.skilling;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

//Fishstabber: Increase your Fishing level by 3. (100%)
public class DragonHarpoonSpecial implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon harpoon") || name.contains("infernal harpoon") || name.contains("crystal harpoon");
    }

    @Override
    public boolean handleActivation(Player player) {
        if(!player.getCombat().useSpecialEnergy(100))
            return false;
        player.animate(7393);
        player.graphics(246);
        player.forceText("Here fishy fishies!");
        player.getStats().get(StatType.Fishing).boost(3, 0.0);
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 100;
    }
}