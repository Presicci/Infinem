package io.ruin.model.skills.hunter.traps;

import io.ruin.model.entity.player.Player;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.hunter.creature.Creature;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Trap {

    private final Player owner;
    private final TrapType trapType;
    @Setter private GameObject object;
    @Setter private Creature trappedCreature;
    @Setter private boolean busy;

    @Setter private boolean removed = false;

    public Trap(Player owner, TrapType trapType, GameObject object) {
        this.owner = owner;
        this.trapType = trapType;
        this.object = object;
        object.putTemporaryAttribute(AttributeKey.OBJECT_TRAP, this);
    }

    public Config getVarpbit() {
        int vbId = object.getDef().varpBitId;
        if (vbId == -1) return null;
        return Config.varpbit(vbId, false);
    }

    public void setVarpbit(Player player, int value) {
        int vbId = object.getDef().varpBitId;
        if (vbId == -1) return;
        Config.varpbit(vbId, false).set(player, value);
    }
}
