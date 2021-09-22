package io.ruin.model.skills.farming;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.stat.StatType;

public class Tangleroot {
    public static void rollPet(Player player, int petOdds) {
        if (Random.rollDie(petOdds - (player.getStats().get(StatType.Farming).currentLevel * 25)))
            Pet.TANGLEROOT.unlock(player);
    }
}
