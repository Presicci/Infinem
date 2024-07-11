package io.ruin.model.combat.npc.magic;

import io.ruin.api.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/11/2024
 */
public class EvilChicken extends WizardCombat {

    private static final List<String> DIALOGUE = new ArrayList<>(Arrays.asList(
            "Bwaaaaaauk bwuk bwuk",
            "Bwuk",
            "Bwuk bwuk bwuk",
            "Flee from me, [player name]!",
            "Begone, [player name]!",
            "MUAHAHAHAHAAA!"
    ));

    @Override
    protected int getCastAnimation() {
        return 2302;
    }

    @Override
    protected void onCast(boolean standard) {
        if (target != null && target.isPlayer()) {
            npc.forceText(Random.get(DIALOGUE).replace("[player name]", target.player.getName()));
        }
    }

    @Override
    public void init() {
        standardCast = NPCCombatSpells.WIND_STRIKE;
    }
}
