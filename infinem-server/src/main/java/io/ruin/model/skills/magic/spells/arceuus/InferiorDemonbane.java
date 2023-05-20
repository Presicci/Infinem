package io.ruin.model.skills.magic.spells.arceuus;

import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/16/2023
 */
public class InferiorDemonbane extends TargetSpell {

    public InferiorDemonbane() {
        setLvlReq(44);
        setBaseXp(27);
        setMaxDamage(16);
        setAnimationId(8977);
        setCastGfx(1865, 0, 0);
        setHitGfx(1866, 0);
        setRunes(Rune.CHAOS.toItem(1), Rune.FIRE.toItem(4));
        setAutoCast(53);
        setCastCheck((p, t) -> {
            if (t.isNpc() && t.npc.getDef().demon) {
                return true;
            }
            if (!p.isPlayer()) return false;
            p.player.sendMessage("Demonbane can only be cast on demons.");
            return false;
        });
    }
}