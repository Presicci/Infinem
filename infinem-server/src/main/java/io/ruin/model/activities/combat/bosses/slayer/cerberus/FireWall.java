package io.ruin.model.activities.combat.bosses.slayer.cerberus;

import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/13/2024
 */
public class FireWall extends GameObject {

    public Cerberus cerberusCombat;

    public FireWall(int id, int x, int y, int z, int type, int direction) {
        super(id, x, y, z, type, direction);
    }

    public void startFirewallCheck() {
        World.startEvent(event -> {
            while (!this.isRemoved()) {
                for (Player player : this.getPosition().getRegion().players) {
                    if (player.getPosition().equals(this.getPosition())) {
                        player.forceText("Ouch!");
                        player.hit(new Hit().fixedDamage(player.getStats().get(StatType.Hitpoints).fixedLevel / 10));
                    }
                }
                event.delay(3);
            }
        });
    }

    static {
        ObjectAction.register(23105, 1, (player, obj) -> {
            if (!(obj instanceof FireWall)) return;
            if (player.getPosition().getY() < obj.getPosition().getY()) {
                player.step(0, 2, StepType.FORCE_WALK);
            } else {
                player.dialogue(
                        new OptionsDialogue("Leaving will restart Cerberus",
                                new Option("Leave", () -> {
                                    player.step(0, -2, StepType.FORCE_WALK);
                                    ((FireWall) obj).cerberusCombat.restartCerb(player);
                                }),
                                new Option("Nevermind")
                        ).keepOpenWhenHit());
            }
        });
        ObjectAction.register(23105, 2, (player, obj) -> {
            if (!(obj instanceof FireWall)) return;
            if (player.getPosition().getY() < obj.getPosition().getY()) {
                player.step(0, 2, StepType.FORCE_WALK);
            } else {
                player.step(0, -2, StepType.FORCE_WALK);
                ((FireWall) obj).cerberusCombat.restartCerb(player);
            }
        });
    }
}
