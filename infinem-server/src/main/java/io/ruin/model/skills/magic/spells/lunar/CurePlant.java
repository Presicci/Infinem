package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.World;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.impl.CompostBin;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/27/2022
 */
public class CurePlant extends Spell {

    public CurePlant() {
        Item[] runes = {
                Rune.ASTRAL.toItem(1),
                Rune.EARTH.toItem(8)
        };
        registerObject(66, runes, (p, obj) -> {
            Patch patch = p.getFarming().getPatch(obj);
            if (patch == null) {
                p.sendMessage("Umm... this spell won't cure that!");
                return false;
            }
            if (patch instanceof CompostBin) {
                p.sendMessage("Bins don't often get diseased.");
                return false;
            }
            if (patch.getPlantedCrop() == null) {
                p.sendMessage("There's nothing there to cure.");
                return false;
            }
            if (!patch.isRaked()) {
                p.sendMessage("The weeds are healthy enough already.");
                return false;
            }
            if (patch.isFullyGrown()) {
                p.sendMessage("That's not diseased.");
                return false;
            }
            if (!patch.isDiseased()) {
                p.sendMessage("It is growing just fine.");
                return false;
            }
            if (patch.isDead()) {
                p.sendMessage("It says 'Cure' not 'Resurrect'. Although death may arise from disease, it is not in itself a disease and hence cannot be cured. So there.");
                return false;
            }
            p.animate(4413);
            p.getStats().addXp(StatType.Magic, 60, true);
            World.sendGraphics(724, 0, 0, obj.x, obj.y, obj.z);
            patch.setDiseaseStage(0);
            patch.update();
            p.sendMessage("The cure plant spell completely cures the disease. Your crops are now perfectly healthy.");
            return true;

        });
    }
}

