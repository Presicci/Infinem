package io.ruin.model.item.actions.impl;

import com.google.gson.annotations.Expose;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.prayer.Bone;
import io.ruin.model.stat.StatType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
@Getter
@Setter
public class BoneCrusher {

    private static final int BONE_CRUSHER = 13116;
    private static final int ECTO_TOKEN = 4278;

    @Expose
    public boolean active = true;
    @Expose public int charges;
    private Player player;

    public void init(Player player){
        this.player = player;
    }

    public boolean handleBury(Item item) {
        if (player.getBoneCrusher().isActive() && player.getInventory().contains(BONE_CRUSHER) && hasCharges()) {
            Bone bone = Bone.get(item.getId());
            if (Objects.nonNull(bone)) {
                player.getStats().addXp(StatType.Prayer, bone.exp, true);
                charges--;
                return true;
            }
        }
        return false;
    }

    public void toggleActive() {
        setActive(!isActive());
        player.sendMessage("Your bonecrusher is now " + ((isActive()) ? "active" : "deactivated") + ".");
    }

    public boolean hasCharges() {
        return charges > 0;
    }

    public void checkCharges() {
        player.sendMessage("Your bonecrusher has " + getCharges() + " charges remaining.");
    }

    static {
        ItemAction.registerInventory(BONE_CRUSHER, 2, (player, item) -> player.getBoneCrusher().checkCharges());
        ItemAction.registerInventory(BONE_CRUSHER, 3, (player, item) -> player.getBoneCrusher().toggleActive());
        ItemAction.registerInventory(BONE_CRUSHER, 4, (player, item) -> {
            if (player.getBoneCrusher().getCharges() <= 0) {
                player.sendMessage("Your bonecrusher does not have any charges.");
                return;
            }
            player.getInventory().addOrDrop(ECTO_TOKEN, (player.getBoneCrusher().getCharges() / 25));
            player.getBoneCrusher().setCharges(0);
            player.sendMessage("You remove all the charges from your bonecrusher.");
        });
        ItemItemAction.register(BONE_CRUSHER, ECTO_TOKEN, (player, primary, secondary) -> {
            int charges = player.getBoneCrusher().getCharges();
            player.getBoneCrusher().setCharges(charges + (secondary.getAmount() * 25));
            player.getInventory().remove(secondary.getId(), secondary.getAmount());
            player.getBoneCrusher().checkCharges();
        });
    }

}