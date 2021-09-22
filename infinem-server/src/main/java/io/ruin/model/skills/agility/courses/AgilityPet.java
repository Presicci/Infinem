package io.ruin.model.skills.agility.courses;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.stat.StatType;

public class AgilityPet {

    public static void rollForPet(Player player, int chance) {
        if (Random.rollDie(chance - (player.getStats().get(StatType.Agility).currentLevel * 25)))
            Pet.GIANT_SQUIRREL.unlock(player);
    }
}
