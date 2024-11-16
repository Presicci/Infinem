package io.ruin.model.skills.agility.courses;

import io.ruin.PersistentData;
import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.shop.omnishop.OmniShop;
import io.ruin.model.skills.agility.TricksterAgility;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Color;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/15/2024
 */
public class ColossalWyrmRemains {

    private static final Config TERMITES = Config.varpbit(11412, false);
    private static final String UPPER_KEY = "CWR_UPPER";

    private static void attemptPickupTermites(Player player, int testedValue) {
        int value = TERMITES.get(player);
        if (value == 0) return;
        if (testedValue == value) {
            int termiteAmount = value >= 8 ? Random.get(12, 15) : Random.get(8, 10);
            if (!player.getInventory().hasRoomFor(30038, termiteAmount)) {
                player.sendMessage("You don't have enough inventory space to pickup any termites.");
                return;
            }
            player.getInventory().addOrDrop(30038, termiteAmount);
            player.getCollectionLog().collect(30038, termiteAmount);
            player.sendMessage("You managed to scoop up " + Color.RED.tag() + termiteAmount + " termites" + "</col>!");
            if (Random.rollDie(3)) {
                int boneShardAmount = Random.get(22, 28);
                if (player.getInventory().hasRoomFor(29381, boneShardAmount)) {
                    player.getInventory().addOrDrop(29381, boneShardAmount);
                    player.sendMessage("As well as " + Color.RED.tag() + boneShardAmount + " piles of bone shards" + "</col> they chipped off!");
                }
            }
            //player.graphics(2967);
            TERMITES.set(player, 0);
        }
    }

    private static void attemptSpawnTermites(Player player, boolean adv) {
        if (Random.rollDie(2)) return;
        if (adv && Random.rollDie(3)) {
            TERMITES.set(player, Random.get(8, 9, 10));
        } else {
            TERMITES.set(player, Random.get(1, 2, 3, 4, 5));
        }
    }

    private static void completeLap(Player player) {
        boolean adv = player.hasTemporaryAttribute(UPPER_KEY);
        if (adv) {
            player.getStats().addXp(StatType.Agility, 358, true);
            PersistentData.INSTANCE.colossalWyrmAdvancedLaps++;
        } else {
            player.getStats().addXp(StatType.Agility, 243.7, true);
            PersistentData.INSTANCE.colossalWyrmBasicLaps++;
        }
        AgilityPet.rollForPet(player, adv ? 25406 : 28503);
        int laps = adv ? PlayerCounter.COLOSSAL_WYRM_ADVANCED_LAPS.increment(player, 1) : PlayerCounter.COLOSSAL_WYRM_LAPS.increment(player, 1);
        if (!player.hasAttribute(AttributeKey.HIDE_AGILITY_COUNT))
            player.sendFilteredMessage("Your Colossal Wyrm Agility Course (" + (adv ? "Advanced" : "Basic") + ") lap count is: " + Color.RED.wrap(laps + "") + ".");
        attemptSpawnTermites(player, adv);
        player.removeTemporaryAttribute(UPPER_KEY);
    }

    public static void statsDialogue(Player player, NPC npc) {
        player.dialogue(new OptionsDialogue(
                new Option("Colossal Wyrm Basic Course.",
                        new NPCDialogue(npc, "You've completed " + NumberUtils.formatNumber(PlayerCounter.COLOSSAL_WYRM_LAPS.get(player))
                                + " laps of the basic agility course. There's been " + NumberUtils.formatNumber(PersistentData.INSTANCE.colossalWyrmBasicLaps) + " global completions.")),
                new Option("Colossal Wyrm Advanced Course.",
                        new NPCDialogue(npc, "You've completed " + NumberUtils.formatNumber(PlayerCounter.COLOSSAL_WYRM_ADVANCED_LAPS.get(player))
                                + " laps of the advanced agility course. There's been " + NumberUtils.formatNumber(PersistentData.INSTANCE.colossalWyrmAdvancedLaps) + " global completions."))
        ));
    }

    private static final GameObject[] OBSTACLES = {
            Tile.get(new Position(1655, 2925, 1), true).getObject(55180, 22, 1),    // Tightrope
            Tile.get(new Position(1646, 2910, 1), true).getObject(55184, 22, 2),    // Tightrope
            Tile.get(new Position(1631, 2910, 1), true).getObject(55186, 10, 3),    // Rope
            Tile.get(new Position(1626, 2932, 1), true).getObject(55190, 0, 0),    // Ladder
            Tile.get(new Position(1626, 2933, 2), true).getObject(55179, 10, 3),    // Zipline
            // Advanced
            Tile.get(new Position(1648, 2909, 1), true).getObject(55191, 0, 3),    // Ladder
            Tile.get(new Position(1646, 2907, 2), true).getObject(55192, 10, 1),    // Edge
            Tile.get(new Position(1633, 2908, 2), true).getObject(55194, 22, 2),    // Tightrope
    };

    static {
        NPCAction.register(13982, "stats", ColossalWyrmRemains::statsDialogue);
        NPCAction.register(13982, "shop", (player, npc) -> OmniShop.WORM_TONGUES_WARES.open(player));

        /*
         * Start ladder
         */
        ObjectAction.register(55178, 1652, 2931, 0, "climb", (player, obj) -> {
            if (!player.getStats().check(StatType.Agility, 50, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(828);
                e.delay(1);
                player.getMovement().teleport(1653, 2931, 1);
                player.getStats().addXp(StatType.Agility, 37.2, true);
                player.removeTemporaryAttribute(UPPER_KEY);
                player.unlock();
                e.delay(1);
                TricksterAgility.attemptNext(player, OBSTACLES[0]);
            });
        });
        /*
         * Tightrope
         */
        ObjectAction.register(55180, 1655, 2925, 1,  "cross", (player, obj) -> player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            player.stepAbs(1655, 2918, StepType.FORCE_WALK);
            e.waitForMovement(player);
            player.getAppearance().removeCustomRenders();
            player.getStats().addXp(StatType.Agility, 37.2, true);
            attemptPickupTermites(player, 1);
            player.stepAbs(1655, 2916, StepType.FORCE_RUN);
            e.waitForMovement(player);
            e.delay(2);
            // First jump
            player.animate(1603);
            player.privateSound(2461);
            player.getMovement().force(0, -2, 0, 0, 0, 45, Direction.SOUTH);
            e.delay(2);
            player.getStats().addXp(StatType.Agility, 6.2, true);
            player.face(Direction.WEST);
            e.delay(1);
            // Second jump
            player.animate(1603);
            player.privateSound(2461);
            player.getMovement().force(-2, 0, 0, 0, 0, 45, Direction.WEST);
            e.delay(2);
            player.getStats().addXp(StatType.Agility, 6.2, true);
            player.face(Direction.SOUTH);
            e.delay(1);
            // Third jump
            player.animate(1603);
            player.privateSound(2461);
            player.getMovement().force(0, -2, 0, 0, 0, 45, Direction.SOUTH);
            e.delay(2);
            player.getStats().addXp(StatType.Agility, 6.2, true);
            player.face(Direction.WEST);
            e.delay(1);
            // Fourth jump
            player.animate(1603);
            player.privateSound(2461);
            player.getMovement().force(-2, 0, 0, 0, 0, 50, Direction.WEST);
            e.delay(2);
            player.getStats().addXp(StatType.Agility, 6.2, true);
            player.face(Direction.SOUTH);
            e.delay(1);
            // Fifth jump
            player.animate(1603);
            player.privateSound(2461);
            player.getMovement().force(0, -2, 0, 0, 0, 50, Direction.SOUTH);
            e.delay(2);
            player.getStats().addXp(StatType.Agility, 6.2, true);
            player.face(Direction.WEST);
            e.delay(1);
            // Sixth jump
            player.animate(1603);
            player.privateSound(2461);
            player.getMovement().force(-2, 0, 0, 0, 0, 50, Direction.WEST);
            e.delay(2);
            player.getStats().addXp(StatType.Agility, 6.2, true);
            attemptPickupTermites(player, 2);
            player.unlock();
            e.delay(1);
            if (player.getStats().get(StatType.Agility).currentLevel >= 62) {
                TricksterAgility.attemptNext(player, OBSTACLES[5]);
            } else {
                TricksterAgility.attemptNext(player, OBSTACLES[1]);
            }
        }));
        /*
         * Second tightrope
         */
        ObjectAction.register(55184, 1646, 2910, 1,  "cross", (player, obj) -> player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            player.stepAbs(1641, 2910, StepType.FORCE_WALK);
            e.waitForMovement(player);
            e.delay(1);
            player.animate(11665);
            e.delay(4);
            player.stepAbs(1635, 2910, StepType.FORCE_WALK);
            e.waitForMovement(player);
            player.getAppearance().removeCustomRenders();
            player.getStats().addXp(StatType.Agility, 37.2, true);
            attemptPickupTermites(player, 3);
            player.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(player, OBSTACLES[2]);
        }));
        /*
         * Rope
         */
        ObjectAction.register(55186, 1631, 2910, 1,  "climb", (player, obj) -> player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.stepAbs(1631, 2910, StepType.FORCE_WALK);
            e.waitForMovement(player);
            e.delay(1);
            player.face(Direction.NORTH_WEST);
            player.animate(11655);
            e.delay(1);
            player.getAppearance().setCustomRenders(Renders.WYRM_TIGHTROPE);
            player.stepAbs(1627, 2914, StepType.FORCE_WALK);
            e.waitForMovement(player);
            player.animate(11656);
            e.delay(1);
            player.getAppearance().removeCustomRenders();
            player.getStats().addXp(StatType.Agility, 37.2, true);
            attemptPickupTermites(player, 4);
            // First jump
            player.stepAbs(1627, 2918, StepType.FORCE_RUN);
            e.waitForMovement(player);
            player.animate(11657);
            player.getMovement().force(0, 5, 0, 0, 0, 60, Direction.NORTH);
            e.delay(1);
            // Second jump
            player.stepAbs(1627, 2926, StepType.FORCE_RUN);
            e.waitForMovement(player);
            player.animate(11657);
            player.getMovement().force(0, 5, 0, 0, 0, 60, Direction.NORTH);
            player.getStats().addXp(StatType.Agility, 37.2, true);
            attemptPickupTermites(player, 4);
            e.delay(1);
            player.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(player, OBSTACLES[3]);
        }));
        /*
         * ladder
         */
        ObjectAction.register(55190, 1626, 2932, 1, "climb", (player, obj) -> {
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(828);
                e.delay(1);
                player.getMovement().teleport(1625, 2932, 2);
                player.getStats().addXp(StatType.Agility, 37.2, true);
                player.unlock();
                e.delay(1);
                TricksterAgility.attemptNext(player, OBSTACLES[4]);
            });
        });
        /*
         * Zipline
         */
        ObjectAction.register(55179, 1626, 2933, 2, "slide", (player, obj) -> {
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.face(obj);
                e.delay(1);
                player.animate(11650);
                player.stepAbs(1626, 2933, StepType.FORCE_RUN);
                e.delay(1);
                player.animate(11659);
                player.getMovement().force(17, 0, 0, 0, 0, 160, Direction.EAST);
                e.delay(5);
                player.animate(11666);
                e.delay(1);
                player.getMovement().teleport(1644, 2933, 0);
                player.animate(11667);
                e.delay(2);
                player.getMovement().teleport(1645, 2933, 0);
                player.animate(534);
                completeLap(player);
                player.unlock();
            });
        });

        /**
         * Upper area
         */
        /*
         * Ladder
         */
        ObjectAction.register(55191, 1648, 2909, 1, "climb", (player, obj) -> {
            if (!player.getStats().check(StatType.Agility, 62, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(828);
                e.delay(1);
                player.getMovement().teleport(1648, 2908, 2);
                player.getStats().addXp(StatType.Agility, 70, true);
                player.putTemporaryAttribute(UPPER_KEY, 1);
                player.unlock();
                e.delay(1);
                TricksterAgility.attemptNext(player, OBSTACLES[6]);
            });
        });
        /*
         * Edge
         */
        ObjectAction.register(55192, 1646, 2907, 2,  "jump", (player, obj) -> player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            // First jump
            player.animate(1603);
            player.privateSound(2461);
            player.getMovement().force(-4, 0, 0, 0, 0, 45, Direction.WEST);
            e.delay(2);
            // Second jump
            player.animate(1603);
            player.privateSound(2461);
            player.getMovement().force(-4, 0, 0, 0, 0, 45, Direction.WEST);
            e.delay(2);
            // Third jump
            player.animate(1603);
            player.privateSound(2461);
            player.getMovement().force(-4, 0, 0, 0, 0, 45, Direction.WEST);
            e.delay(2);
            player.getStats().addXp(StatType.Agility, 70, true);
            attemptPickupTermites(player, 8);
            player.putTemporaryAttribute(UPPER_KEY, 1);
            player.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(player, OBSTACLES[7]);
        }));
        /*
         * Tightrope
         */
        ObjectAction.register(55194, 1633, 2908, 2,  "cross", (player, obj) -> player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.stepAbs(1633, 2908, StepType.FORCE_WALK);
            e.waitForMovement(player);
            e.delay(1);
            player.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            player.stepAbs(1630, 2911, StepType.FORCE_WALK);
            e.waitForMovement(player);
            player.animate(11665);
            e.delay(4);
            player.stepAbs(1625, 2916, StepType.FORCE_WALK);
            e.waitForMovement(player);
            player.getAppearance().removeCustomRenders();
            player.getStats().addXp(StatType.Agility, 70, true);
            attemptPickupTermites(player, 9);
            player.stepAbs(1624, 2918, StepType.FORCE_RUN);
            e.waitForMovement(player);
            e.delay(1);
            player.animate(11655);
            e.delay(1);
            player.getAppearance().setCustomRenders(Renders.WYRM_TIGHTROPE);
            player.stepAbs(1624, 2931, StepType.FORCE_WALK);
            e.waitForMovement(player);
            e.delay(1);
            player.animate(11656);
            player.getAppearance().removeCustomRenders();
            player.getStats().addXp(StatType.Agility, 70, true);
            attemptPickupTermites(player, 10);
            player.putTemporaryAttribute(UPPER_KEY, 1);
            player.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(player, OBSTACLES[4]);
        }));
    }
}
