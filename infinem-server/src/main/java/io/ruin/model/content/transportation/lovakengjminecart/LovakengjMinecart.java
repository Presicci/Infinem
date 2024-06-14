package io.ruin.model.content.transportation.lovakengjminecart;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/14/2024
 */
@Getter
@AllArgsConstructor
public enum LovakengjMinecart {
    ARCEUUS(new Position(1670, 3832), new Position(1670, 3833, 0), 7231),
    FARMING_GUILD(new Position(1218, 3738), new Position(1218, 3737, 0), 12702),
    HOSIDIUS_SOUTH(new Position(1808, 3480), new Position(1808, 3479, 0), 12703),
    HOSIDIUS_WEST(new Position(1655, 3542), new Position(1655, 3543, 0), 7228),
    KINGSTOWN(new Position(1698, 3660), new Position(1699, 3660, 0), 12704),
    KOUREND_WOODLAND(new Position(1571, 3466), new Position(1572, 3466, 0), 12705),
    LOVAKENGJ(new Position(1518, 3732), new Position(1518, 3733, 0), 7227),
    MOUNT_QUIDAMORTEM(new Position(1254, 3548), new Position(1255, 3548, 0), 7616),
    NORTHERN_TUNDRAS(new Position(1648, 3930), new Position(1648, 3931, 0), 12706),
    PORT_PISCARILIUS(new Position(1760, 3710), new Position(1761, 3710, 0), 7229),
    SHAYZIEN_EAST(new Position(1590, 3621), new Position(1590, 3620, 0), 7230),
    SHAYZIEN_WEST(new Position(1414, 3577), new Position(1415, 3577, 0), 12707);

    private final Position objectPosition, destination;
    private final int conductorId;

    protected void travel(Player player, int cost) {
        if (cost > 0) {
            player.getInventory().remove(995, cost);
        }
        Traveling.fadeTravel(player, destination);
        player.getTaskManager().doLookupByUUID(991);    // Travel Using the Lovakengj Minecart Network
    }

    protected int getBit() {
        return 1 << ordinal();
    }

    public boolean hasUnlocked(Player player) {
        int bit = getBit();
        int currentValue = player.getAttributeIntOrZero(LovakengjMinecartNetwork.KEY);
        return (currentValue & bit) == bit;
    }

    public void unlock(Player player) {
        int bit = getBit();
        int currentValue = player.getAttributeIntOrZero(LovakengjMinecartNetwork.KEY);
        if ((currentValue & bit) == bit) return;
        player.putAttribute(LovakengjMinecartNetwork.KEY, currentValue | bit);
        player.sendMessage("" + (currentValue | bit));
    }

    public String getName() {
        return StringUtils.initialCaps(name().toLowerCase().replace("_", " "));
    }
}
