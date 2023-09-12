package io.ruin.model.skills.construction.room.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.skills.construction.room.Room;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SuperiorGardenRoom extends Room {

    enum Topiary {
        KRAKEN(29231, p -> KillCounter.getKillCount(p, BossKillCounter.KRAKEN)),
        ZULRAH(29232, p -> KillCounter.getKillCount(p, BossKillCounter.ZULRAH)),
        KALPHITE_QUEEN(29233, p-> KillCounter.getKillCount(p, BossKillCounter.KALPHITE_QUEEN)),
        CERBERUS(29234, p -> KillCounter.getKillCount(p, BossKillCounter.CERBERUS)),
        ABYSSAL_SIRE(29235, p -> KillCounter.getKillCount(p, BossKillCounter.ABYSSAL_SIRE)),
        SKOTIZO(29236, p -> KillCounter.getKillCount(p, BossKillCounter.SKOTIZO)),
        VORKATH(31985, p -> KillCounter.getKillCount(p, BossKillCounter.VORKATH));

        int objectId;
        Function<Player, Integer> killsFunction;
        String name;

        Topiary(int objectId, Function<Player, Integer> killCounterFunction) {
            this.objectId = objectId;
            this.killsFunction = killCounterFunction;
            this.name = StringUtils.fixCaps(name().replace('_', ' '));
        }
    }

    @Expose
    private Topiary topiary = null;

    @Override
    protected void onBuild() {
        if (topiary != null && hasBuildable(Buildable.TOPIARY_BUSH)) {
            getHotspotObjects(Hotspot.TOPIARY_SPACE).forEach(obj -> obj.setId(topiary.objectId));
        }
        getHotspotObjects(Hotspot.TOPIARY_SPACE).forEach(obj -> ObjectAction.register(obj, 4, (player, unused) -> openSelection(player)));
    }

    private void openSelection(Player player) {
        if (getHouse().getOwner() != player) {
            player.dialogue(new PlayerDialogue("I don't think the house owner would appreciate me clipping their bush..."));
            return;
        }
        if (!hasBuildable(Buildable.TOPIARY_BUSH))
            return;
        OptionScroll.open(player, "Choose a monster",
                Arrays.stream(Topiary.values())
                        .map(t -> new Option(t.name, () -> clip(player, t)))
                        .collect(Collectors.toList()));
    }

    private void clip(Player player, Topiary type) {
        int secateurs = -1;
        if (player.getEquipment().hasId(7409) || player.getInventory().hasId(7409)) {
            secateurs = 1;
        } else if (player.getInventory().hasId(5329)) {
            secateurs = 0;
        }
        if (secateurs == -1) {
            player.dialogue(new MessageDialogue("You'll need some secateurs to do that."));
            return;
        }
        if (type.killsFunction.apply(player) < 1) {
//            player.dialogue(new MessageDialogue("You must have killed " + type.name + " at least once before you can do that."));
//            return;
        }

        topiary = type;
        int anim = secateurs == 1 ? 3342 : 2279;
        player.startEvent(event -> {
            player.lock();
            player.animate(anim);
            event.delay(2);
            onBuild();
            player.unlock();

        });
    }

}
