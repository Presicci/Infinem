package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.TileTrigger;
import io.ruin.model.skills.slayer.Slayer;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/1/2023
 */
@AllArgsConstructor
public enum WallBeast {
    ONE(new Position(3162, 9574)),
    TWO(new Position(3164, 9556)),
    THREE(new Position(3161, 9547)),
    FOUR(new Position(3198, 9554)),
    FIVE(new Position(3215, 9560)),
    SIX(new Position(3198, 9572)),
    SEVEN(new Position(3216, 9588));

    final Position position;

    public void trigger(Player player) {
        NPC beast = null;
        for (NPC npc : player.localNpcs()) {
            if (npc.getId() == 475 && npc.getPosition().equals(position)) {
                beast = npc;
                break;
            }
        }
        if (beast == null)
            return;
        boolean protect = player.getEquipment().getId(Equipment.SLOT_HAT) == Items.SPINY_HELMET || Slayer.hasSlayerHelmEquipped(player);
        player.lock();
        player.getMovement().reset();
        NPC finalBeast = beast;
        player.startEvent(e -> {
            e.delay(1);
            player.face(finalBeast);
            e.delay(1);
            if (protect) {
                player.sendFilteredMessage("Your " + (player.getEquipment().getId(Equipment.SLOT_HAT) == Items.SPINY_HELMET ? "spiny helmet" : "slayer helmet") + " repels the wall beast!");
                e.delay(1);
            } else {
                player.animate(1810);
                player.stun(2, false);
                Hit hit = new Hit(HitType.DAMAGE);
                hit.fixedDamage(Random.get(13, 18));
                player.hit(hit);
                player.sendMessage("A giant hand appears and grabs your head!");
                e.delay(4);
                player.resetAnimation();
                e.delay(2);
            }
            player.unlock();
        });
        beast.lock();
        beast.startEvent(e -> {
            e.delay(1);
            if (protect) {
                e.delay(1);
                finalBeast.transform(476);
                finalBeast.targetPlayer(player, false);
                finalBeast.attackTargetPlayer();
                finalBeast.getCombat().delayAttack(4);
                finalBeast.animate(1805);
                finalBeast.publicSound(895, 10, 0);
                finalBeast.doIfIdle(player, 5, 3, () -> {
                    finalBeast.animate(1801);
                    finalBeast.transform(475);
                });
            } else {
                finalBeast.animate(1806);
                finalBeast.publicSound(893, 10, 0);
                e.delay(2);
                finalBeast.animate(1807);
                e.delay(3);
                finalBeast.animate(1801);
            }
            finalBeast.unlock();
        });
    }

    static {
        for (WallBeast wallBeast : values()) {
            TileTrigger.registerPlayerTrigger(wallBeast.position.relative(Direction.SOUTH, 1), wallBeast::trigger);
        }
    }
}
