package io.ruin.model.skills.hunter.falconry;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2023
 */
public class Falconry {

    @Getter
    @AllArgsConstructor
    private enum FalconryCatch {

        SPOTTED(5531, 1342, 43, 104, 10125),
        DARK(5532, 1343, 57, 132, 10115),
        DASHING(5533, 1344, 69, 156, 10127);

        private final int npcId, falconId, level;
        private final double experience;
        private final int furId;
    }

    private static final int FALCONERS_GLOVES = 10023;
    private static final int FALCONERS_GLOVES_BIRD = 10024;

    static {
        for (FalconryCatch kebbit : FalconryCatch.values()) {
            NPCAction.register(kebbit.npcId, "catch", (player, npc) -> catchKebbit(player, npc, kebbit));
            NPCAction.register(kebbit.falconId, "retrieve", ((player, npc) -> retrieve(player, npc, kebbit)));
            SpawnListener.register(kebbit.npcId, npc -> {
                npc.skipMovementCheck = true;
            });
        }

        MapListener.registerRegions(9271, 9272, 9527, 9528)
                .onExit((player, logout) -> {
                    player.getInventory().remove(FALCONERS_GLOVES, Integer.MAX_VALUE);
                    player.getEquipment().remove(FALCONERS_GLOVES, Integer.MAX_VALUE);
                    player.getInventory().remove(FALCONERS_GLOVES_BIRD, Integer.MAX_VALUE);
                    player.getEquipment().remove(FALCONERS_GLOVES_BIRD, Integer.MAX_VALUE);
                });
    }

    private static boolean rollCatch(Player player, FalconryCatch kebbit) {
        double chance = 0.55;
        int levelDiff = player.getStats().get(StatType.Hunter).currentLevel - kebbit.getLevel();
        chance *= 1 + (levelDiff * 0.01); // relative 1% increase per level
        if (player.debug)
            player.sendFilteredMessage("Catch chance: " + chance);
        return Random.get() <= Math.min(0.90, chance);
    }

    private static void catchKebbit(Player player, NPC npc, FalconryCatch kebbit) {
        if (!player.getStats().check(StatType.Hunter, kebbit.level, "catch this"))
            return;
        if (npc.isLocked()) {
            player.sendMessage("That kebbit is already caught by a falcon.");
            return;
        }
        int weapon = player.getEquipment().get(Equipment.SLOT_WEAPON) == null ? -1 : player.getEquipment().get(Equipment.SLOT_WEAPON).getId();
        if (weapon != FALCONERS_GLOVES && weapon != FALCONERS_GLOVES_BIRD) {
            player.dialogue(new PlayerDialogue("I should speak with Matthias before attempting this.."));
            return;
        }
        if (weapon == FALCONERS_GLOVES) {
            player.sendMessage("You need to retrieve your falcon before trying to catch any more kebbits.");
            return;
        }
        npc.lock();
        Position npcPos = npc.getPosition();
        player.lock();
        World.startEvent(worldEvent -> {
            player.faceTemp(npc);
            player.animate(5162);
            worldEvent.delay(1);
            player.unlock();
            player.sendFilteredMessage("You send your falcon to try and catch the kebbit.");
            player.getEquipment().get(Equipment.SLOT_WEAPON).setId(FALCONERS_GLOVES);
            int playerTileDist = player.getPosition().distance(npcPos);
            int sendDelay = Math.max(1, (30 + (playerTileDist * 12)) / 35);
            new Projectile(918, 30, 0, 0, 31, playerTileDist, 5, 64).send(player, npcPos);
            worldEvent.delay(sendDelay);
            if (!rollCatch(player, kebbit)) {
                npc.unlock();
                int npcTileDistance = npcPos.distance(player.getPosition());
                int returnDelay = Math.max(1, (30 + (npcTileDistance * 12)) / 37);
                new Projectile(1632, 0, 30, 10, 31, npcTileDistance, 10, 64).send(npc, player);
                worldEvent.delay(returnDelay);
                player.sendFilteredMessage("The kebbit narrowly escapes your falcon.");
                if (player.getEquipment().get(Equipment.SLOT_WEAPON).getId() == FALCONERS_GLOVES)
                    player.getEquipment().get(Equipment.SLOT_WEAPON).setId(FALCONERS_GLOVES_BIRD);
                else {
                    Item cormorantGloves = player.getInventory().findItem(FALCONERS_GLOVES);
                    if (cormorantGloves != null)
                        cormorantGloves.setId(FALCONERS_GLOVES_BIRD);
                }
                return;
            }
            NPC falcon = new NPC(kebbit.falconId);
            falcon.doIfIdle(player, 15, 4, () -> {
                player.sendMessage("Your falcon has left its prey. You see it heading back toward the falconer.");
                falcon.remove();
                npc.unlock();
                npc.setHidden(false);
            });
            falcon.spawn(npcPos);
            player.getPacketSender().sendHintIcon(falcon);
            npc.addEvent(npcEvent -> {
                if (!npc.isHidden()) {
                    npc.setHidden(true);
                    npc.unlock();
                    npcEvent.delay(Random.get(15, 35));
                    npc.setHidden(false);
                }
            });
        });
    }

    private static void retrieve(Player player, NPC npc, FalconryCatch kebbit) {
        player.startEvent(e -> {
            player.lock();
            player.getPacketSender().resetHintIcon(false);
            player.animate(5212);
            e.delay(1);
            npc.remove();
            player.getStats().addXp(StatType.Hunter, kebbit.experience, true);
            player.getInventory().addOrDrop(kebbit.furId, 1);
            player.getInventory().addOrDrop(Items.BONES, 1);
            if (player.getEquipment().get(Equipment.SLOT_WEAPON).getId() == FALCONERS_GLOVES)
                player.getEquipment().get(Equipment.SLOT_WEAPON).setId(FALCONERS_GLOVES_BIRD);
            else {
                Item cormorantGloves = player.getInventory().findItem(FALCONERS_GLOVES);
                if (cormorantGloves != null)
                    cormorantGloves.setId(FALCONERS_GLOVES_BIRD);
            }
            player.unlock();
        });
    }

    public static boolean freeHands(Player player) {
        Item gloves = player.getEquipment().get(Equipment.SLOT_HANDS);
        Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
        Item shield = player.getEquipment().get(Equipment.SLOT_SHIELD);
        return gloves == null && shield == null && (weapon == null || weapon.getId() == FALCONERS_GLOVES || weapon.getId() == 22816);
    }
}
