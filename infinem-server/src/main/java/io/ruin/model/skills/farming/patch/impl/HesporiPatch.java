package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.bosses.hespori.HesporiLoot;
import io.ruin.model.activities.pvminstances.PVMInstance;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.inter.handlers.TabStats;
import io.ruin.model.item.Item;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.HesporiCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/29/2022
 */
public class HesporiPatch extends Patch {

    public NPC hespori;
    /*
     * stage 1 = 4
     * stage 2 = 5
     * stage 3 = 6
     * stage 4 = 7 * harvest
     * dead = 8 * clear (stage 5)
     */
    @Override
    public int getCropVarpbitValue() {
        return getPlantedCrop().getContainerIndex() + getStage();
    }

    @Override
    public void cropInteract() {
    }

    @Override
    public void update() {
        if (player.getFarming().getVisibleGroup() != null && player.getFarming().getVisibleGroup().getPatches().contains(data))
            send();
        if (hespori != null) {
            send();
            if (hespori.isRemoved()) {
                hespori = null;
            }
        }
    }

    public void objectAction(GameObject object, int option) {
        if (option == 1)
            if (isRaked()) {
                hesporiInteract(object);
            } else {
                interact();
            }
        else if (option == 2)
            inspect();
        else if (option == 3)
            clear();
        else if(option == 4)
            TabStats.openGuide(player, StatType.Farming, data.getGuideChildId());
    }

    public void hesporiInteract(GameObject object) {
        if (getStage() == 3) {
            player.startEvent(e -> {
                player.lock();
                // Spawn
                hespori = new NPC(8583);
                PVMInstance.enterHespori(player);
                player.unlock();
            });
        } else if (getStage() == 4) {
            if (!player.getInventory().contains(952, 1)) {
                player.sendMessage("You will need a spade to clear this patch.");
                return;
            }
            player.startEvent(event -> {
                player.animate(831);
                event.delay(2);
                player.animate(831);
                event.delay(2);
                player.resetAnimation();
                reset(false);
                List<Item> loot = new ArrayList<>();
                loot.add(HesporiLoot.ANIMA_SEEDS.rollItem());
                loot.add(HesporiLoot.NORMAL_SEEDS.rollItem());
                for (Item item : loot) {
                    player.getInventory().addOrDrop(item);
                }
                if (Random.rollDie(5000)) {
                    Pet.TANGLEROOT.unlock(player);
                }
            });
        }
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof HesporiCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return true;
    }

    @Override
    public int calculateProduceAmount() {
        return 0;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public String getPatchName() {
        return "a Hespori";
    }

    static {
        // Cave entrance / exit
        ObjectAction.register(34499,1231, 3728,0, "enter", (player, obj) -> Traveling.fadeTravel(player, new Position(1243, 10081)));
        ObjectAction.register(34435, "exit", (player, obj) ->  {
            HesporiPatch patch = (HesporiPatch) player.getFarming().getPatch(PatchData.HESPORI);
            player.getMovement().teleport(new Position(1230, 3729, 0));
            if (patch != null && patch.hespori != null) {
                patch.hespori.getCombat().reset();
            }
        });
    }
}
