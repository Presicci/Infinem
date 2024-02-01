package io.ruin.model.activities.combat.godwars;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.process.tickevent.TickEvent;
import io.ruin.process.tickevent.TickEventType;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum GodwarsAltars {

    BANDOS(26366, new Position(2862, 5354, 2), GodwarsFollower.BANDOS_ITEM_NAMES),
    ZAMORAK(26363, new Position(2925, 5333, 2), GodwarsFollower.ZAMORAK_ITEM_NAMES),
    SARADOMIN(26364, new Position(2909, 5265, 0), GodwarsFollower.SARADOMIN_ITEM_NAMES),
    ARMADYL(26365, new Position(2839, 5294, 2), GodwarsFollower.ARMADYL_ITEM_NAMES);

    private final int altarId;
    private final Position teleportPosition;
    private final String[] godEquipment;

    static {
        for (GodwarsAltars altar : values()) {
            ObjectAction.register(altar.altarId, "pray", (player, obj) -> {
                if (player.getCombat().isDefending(5) | player.getCombat().isAttacking(5)) {
                    player.sendFilteredMessage("You cannot use this altar while in combat.");
                    return;
                }
                if (player.isTickEventActive(TickEventType.GODWARS_ALTAR_COOLDOWN)) {
                    player.sendFilteredMessage("You cannot use this altar yet!");
                    return;
                }
                int equipmentCount = (int) Arrays.stream(player.getEquipment().getItems()).filter(i -> i != null && Arrays.stream(altar.godEquipment).anyMatch(e -> i.getDef().name.toLowerCase().contains(e))).count();
                player.animate(645);
                player.sendFilteredMessage("You pray to the gods...");
                player.getStats().get(StatType.Prayer).restore();
                player.getStats().get(StatType.Prayer).boost(equipmentCount, 0);
            });
            ObjectAction.register(altar.altarId, "teleport", (player, obj) -> {
                player.getMovement().teleport(altar.teleportPosition);
            });
        }
    }
}
