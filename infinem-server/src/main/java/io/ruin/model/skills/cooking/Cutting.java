package io.ruin.model.skills.cooking;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2023
 */
public class Cutting {

    public static void cook(Player player, Cuttable cuttable) {
        SkillItem i = new SkillItem(cuttable.getItemId()).name(ItemDef.get(cuttable.getItemId()).name).
                addAction((p, amount, event) -> startCutting(p, cuttable));
        SkillDialogue.cook(player, i);
    }

    public static void startCutting(Player player, Cuttable cuttable) {

    }
}
