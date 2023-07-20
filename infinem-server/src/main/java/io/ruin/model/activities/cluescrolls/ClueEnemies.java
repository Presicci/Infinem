package io.ruin.model.activities.cluescrolls;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.shared.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.routes.ProjectileRoute;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/24/2022
 */
public class ClueEnemies {

    public static void spawnSingleEnemyOnDig(Clue clue, Player player, int... possibleIds) {
        int killedWizard = player.getTemporaryAttributeOrDefault(AttributeKey.KILLED_WIZARD, 0);
        if (killedWizard == 1) {
            player.removeTemporaryAttribute(AttributeKey.KILLED_WIZARD);
            clue.advance(player);
        } else {
            boolean spawnedWizard = player.getTemporaryAttributeOrDefault(AttributeKey.SPAWNED_WIZARD, false);
            if (!spawnedWizard) {
                player.putTemporaryAttribute(AttributeKey.SPAWNED_WIZARD, true);
                NPC npc = new NPC(Random.get(possibleIds));
                npc.targetPlayer(player, true);
                npc.removeOnDeath();
                npc.removeIfIdle(player);
                npc.removalAction = (p -> {
                    p.removeTemporaryAttribute(AttributeKey.SPAWNED_WIZARD);
                });
                npc.deathStartListener = (DeathListener.SimpleKiller) killer -> {
                    player.putTemporaryAttribute(AttributeKey.KILLED_WIZARD, 1);
                };
                Bounds bounds = new Bounds(player.getPosition(), 3);
                boolean hasSpawned = false;
                while (!hasSpawned) {
                    Position pos = bounds.randomPosition();
                    if (ProjectileRoute.allow(player, pos) && !pos.equals(player.getPosition())) {
                        npc.spawn(pos);
                        npc.attackTargetPlayer(() -> !player.getPosition().isWithinDistance(npc.getPosition()));
                        hasSpawned = true;
                    }
                }
            }
        }
    }

    public static void ancientOrBrassicanDig(Clue clue, Player player) {
        int killedWizard = player.getTemporaryAttributeOrDefault(AttributeKey.KILLED_WIZARD, 0);
        if (killedWizard == 3 || clue.type != ClueType.MASTER) {
            player.removeTemporaryAttribute(AttributeKey.KILLED_WIZARD);
            clue.advance(player);
        } else {
            boolean spawnedWizard = player.getTemporaryAttributeOrDefault(AttributeKey.SPAWNED_WIZARD, false);
            if (!spawnedWizard) {
                player.putTemporaryAttribute(AttributeKey.SPAWNED_WIZARD, true);
                // Spawn a brassican mage in singles, ancient wizards in multi
                if (player.inMulti()) {
                    NPC melee = new NPC(7309);
                    NPC ranged = new NPC(7308);
                    NPC magic = new NPC(7307);
                    NPC[] wizards = new NPC[] { melee, ranged, magic };
                    for (NPC wizard : wizards) {
                        wizard.targetPlayer(player, true);
                        wizard.removeOnDeath();
                        wizard.removeIfIdle(player);
                        wizard.removalAction = (p -> {
                            p.removeTemporaryAttribute(AttributeKey.SPAWNED_WIZARD);
                        });
                        wizard.deathStartListener = (DeathListener.SimpleKiller) killer -> {
                            int killed = player.getTemporaryAttributeOrDefault(AttributeKey.KILLED_WIZARD, 0);
                            player.putTemporaryAttribute(AttributeKey.KILLED_WIZARD, killed + 1);
                        };
                    }
                    Position[] positions = new Position[2];
                    for (int index = 0; index < 3; index++) {
                        NPC wizard = wizards[index];
                        Bounds bounds = new Bounds(player.getPosition(), 5);
                        boolean hasSpawned = false;
                        while (!hasSpawned) {
                            Position pos = bounds.randomPosition();
                            if (ProjectileRoute.allow(player, pos) && !pos.equals(player.getPosition())
                                    && (positions[0] == null || !pos.equals(positions[0]))
                                    && (positions[1] == null || !pos.equals(positions[1]))) {
                                wizard.spawn(pos);
                                wizard.attackTargetPlayer(() -> !player.getPosition().isWithinDistance(wizard.getPosition()));
                                if (index < 2)
                                    positions[index] = pos;
                                hasSpawned = true;
                            }
                        }
                    }
                } else {
                    NPC npc = new NPC(7310);
                    npc.targetPlayer(player, true);
                    npc.removeOnDeath();
                    npc.removeIfIdle(player);
                    npc.removalAction = (p -> {
                        p.removeTemporaryAttribute(AttributeKey.SPAWNED_WIZARD);
                    });
                    npc.deathStartListener = (DeathListener.SimpleKiller) killer -> {
                        player.putTemporaryAttribute(AttributeKey.KILLED_WIZARD, 3);
                    };
                    Bounds bounds = new Bounds(player.getPosition(), 3);
                    boolean hasSpawned = false;
                    while (!hasSpawned) {
                        Position pos = bounds.randomPosition();
                        if (ProjectileRoute.allow(player, pos) && !pos.equals(player.getPosition())) {
                            npc.spawn(pos);
                            npc.attackTargetPlayer(() -> !player.getPosition().isWithinDistance(npc.getPosition()));
                            hasSpawned = true;
                        }
                    }
                }
            }
        }
    }
}
