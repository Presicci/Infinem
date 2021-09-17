package io.ruin.model.item.actions.impl;

import com.google.gson.annotations.Expose;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.prayer.Ashes;
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
public class AshSanctifier {
    private static final int ASH_SANCTIFIER = -1;   // TODO find id
    private static final int DEATH_RUNE = Items.DEATH_RUNE;

    @Expose
    public boolean active = true;
    @Expose public int charges;
    private Player player;

    public void init(Player player){
        this.player = player;
    }

    public boolean handleScatter(Item item) {
        if (player.getAshSanctifier().isActive() && player.getInventory().contains(ASH_SANCTIFIER) && hasCharges()) {
            Ashes ashes = Ashes.get(item.getId());
            if (Objects.nonNull(ashes)) {
                player.getStats().addXp(StatType.Prayer, ashes.getExperience(), true);
                charges--;
                return true;
            }
        }
        return false;
    }

    public void toggleActive() {
        setActive(!isActive());
        player.sendMessage("Your ash sanctifier is now " + ((isActive()) ? "active" : "deactivated") + ".");
    }

    public boolean hasCharges() {
        return charges > 0;
    }

    public void checkCharges() {
        player.sendMessage("Your ash sanctifier has " + getCharges() + " charges remaining.");
    }

    static {
        ItemAction.registerInventory(ASH_SANCTIFIER, 2, (player, item) -> player.getAshSanctifier().checkCharges());
        ItemAction.registerInventory(ASH_SANCTIFIER, 3, (player, item) -> player.getAshSanctifier().toggleActive());
        ItemAction.registerInventory(ASH_SANCTIFIER, 4, (player, item) -> {
            if (player.getAshSanctifier().getCharges() <= 0) {
                player.sendMessage("Your ash sanctifier does not have any charges.");
                return;
            }
            player.getInventory().addOrDrop(DEATH_RUNE, (player.getAshSanctifier().getCharges() / 10));
            player.getAshSanctifier().setCharges(0);
            player.sendMessage("You remove all the charges from your ash sanctifier.");
        });
        ItemItemAction.register(ASH_SANCTIFIER, DEATH_RUNE, (player, primary, secondary) -> {
            int charges = player.getAshSanctifier().getCharges();
            player.getAshSanctifier().setCharges(charges + (secondary.getAmount() * 10));
            player.getInventory().remove(secondary.getId(), secondary.getAmount());
            player.getAshSanctifier().checkCharges();
        });
    }
}
