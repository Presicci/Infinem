package io.ruin.model.skills.construction.mahoganyhomes;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.impl.storage.PlankSack;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2024
 */
@AllArgsConstructor
@Getter
public class MahoganyObject {
    private final int objectId;
    private final MahoganyHotspotType type;

    public MahoganyHotspot getHotspot() {
        return MahoganyHotspot.getByObjectId(objectId);
    }

    public Config getVarbit() {
        return MahoganyHotspot.getByObjectId(objectId).getVarbit();
    }

    public boolean isRepairable() {
        return getType().isRepairable();
    }

    public boolean isBuilt(Player player) {
        if (isRepairable()) return getVarbit().get(player) == 2;
        return getVarbit().get(player) > 4;
    }

    public void sendBuildInterface(Player player) {
        type.sendBuildInterface(player);
    }

    public void remove(Player player) {
        player.animate(Construction.REMOVE_OBJECT);
        getVarbit().set(player, 4);
    }

    public void repair(Player player) {
        if (type.getRepairItems() == null || type.getRepairExperience() == null) return;
        if (!MahoganyHomes.hasContract(player)) {
            player.sendMessage("You do not currently have a contract, visit Amy to get one.");
            return;
        }
        if (!SmithBar.hasHammer(player) || !Tool.SAW.hasTool(player)) {
            player.dialogue(new MessageDialogue("You will need a hammer and a saw to build that."));
            return;
        }
        MahoganyDifficulty difficulty = MahoganyHomes.getDifficulty(player);
        int requiredItem = type.getRepairItems()[difficulty.ordinal()];
        if (!PlankSack.hasMaterials(player, requiredItem, 1) && !(player.isAdmin() && player.debug)) {
            player.sendMessage("You need " + ItemDefinition.get(requiredItem).descriptiveName + " to repair this.");
            return;
        }
        Config varbit = getVarbit();
        if (varbit.get(player) > 1) {
            player.dialogue(new MessageDialogue("You have already repaired this."));
            return;
        }
        double experience = type.getRepairExperience()[difficulty.ordinal()];
        player.startEvent(event -> {
            player.lock();
            player.animate(Construction.MID_BUILD);
            event.delay(1);
            varbit.set(player, 2);
            PlankSack.removeMaterials(player, requiredItem, 1);
            player.getStats().addXp(StatType.Construction, experience, true);
            event.delay(1);
            MahoganyHomes.checkCompletion(player);
            player.unlock();
        });
    }

    public void build(Player player, int slot) {
        if (type.getBuildables() == null) return;
        Buildable buildable = type.getBuildables()[slot - 1];
        if (!MahoganyHomes.hasContract(player)) {
            player.sendMessage("You do not currently have a contract, visit Amy to get one.");
            return;
        }
        MahoganyDifficulty difficulty = MahoganyHomes.getDifficulty(player);
        if (difficulty.ordinal() + 1 != slot) {
            player.sendMessage("For a contract of " + difficulty.toString().toLowerCase() + " difficulty, you must build " + difficulty.getPlankName() + " furniture.");
            return;
        }
        if (Construction.getEffectiveLevel(player, buildable) < buildable.getLevelReq()) {
            player.dialogue(new MessageDialogue("You need a Construction level of at least " + buildable.getLevelReq() + " to build that."));
            return;
        }
        if (!buildable.hasTools(player)) {
            player.dialogue(new MessageDialogue("You will need a hammer and a saw to build that."));
            return;
        }
        if (!buildable.hasAllMaterials(player) && !(player.isAdmin() && player.debug)) {
            player.sendMessage("You do not have all the required materials to build that.");
            return;
        }
        Config varbit = getVarbit();
        if (varbit.get(player) > 4) {
            player.dialogue(new MessageDialogue("You already have something built in this hotspot."));
            return;
        }
        player.closeInterfaces();
        player.startEvent(event -> {
            player.lock();
            player.animate(buildable.getAnimation());
            event.delay(1);
            varbit.set(player, 4 + slot);
            buildable.removeMaterials(player);
            player.getStats().addXp(StatType.Construction, buildable.getXP(), true);
            event.delay(1);
            MahoganyHomes.checkCompletion(player);
            player.unlock();
        });
    }
}
