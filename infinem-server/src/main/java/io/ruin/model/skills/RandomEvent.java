package io.ruin.model.skills;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.process.tickevent.TickEvent;
import io.ruin.process.tickevent.TickEventType;
import io.ruin.services.Punishment;
import io.ruin.utility.Color;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/15/2024
 */
@AllArgsConstructor
public enum RandomEvent {
    BEE_KEEPER(
            6747,
            new String[]{"Hello, [player name]?", "Please help me, [player name].", "I need your help, [player name]!", "Can I borrow you for a minute, [player name]?"},
            new Item[]{new Item(25129), new Item(25131), new Item(25133), new Item(25135), new Item(25137)},
            new LootTable().addTable(1,
                    new LootItem(995, 100, 300, 1),
                    new LootItem(Items.FLAX_NOTE, 1, 27, 1)
            )
    ),
    ;

    private final int npcId;
    private final String[] overheadText;
    private final Item[] costumePieces;
    private final LootTable rewards;

    private String randomOverhead(Player player) {
        return Random.get(overheadText).replace("[player name]", player.getName());
    }

    private void reward(Player player) {
        for (Item costumePiece : costumePieces) {
            if (!player.hasItem(costumePiece.getId(), true)) {
                if (player.getInventory().hasFreeSlots(1)) {
                    player.getInventory().add(costumePiece);
                    player.sendMessage("The stranger hands you a reward as he disappears...");
                } else {
                    player.getBank().add(costumePiece.getId(), 1);
                    player.sendMessage("The stranger adds a reward to your bank as he disappears...");
                }
                player.getCollectionLog().collect(costumePiece.getId());
                return;
            }
        }
        Item randomReward = rewards.rollItem();
        player.getInventory().addOrDrop(randomReward);
        player.sendMessage("The stranger hands you a reward as he disappears...");
    }

    private void spawn(Player player) {
        NPC npc = new NPC(npcId);
        player.randomEventNPC = npc;
        player.randomEventJailDelay.delaySeconds(120);
        player.randomEventNpcShoutDelay.delaySeconds(10);
        player.sendMessage(Color.COOL_BLUE.wrap("Someone appears interested in your actions..."));
        npc.ownerId = player.getUserId();
        npc.spawn(player.getAbsX(), player.getAbsY(), player.getHeight());
        npc.face(player);
        npc.forceText(randomOverhead(player));
        npc.addEvent(e -> {
            while (player.isOnline()) {
                npc.face(player);
                if (player.getCombat().isDead() || player.getMovement().isTeleportQueued()) {
                    e.delay(1);
                    continue;
                }
                if (!player.randomEventNpcShoutDelay.isDelayed()) {
                    int seconds = player.randomEventJailDelay.remaining() / 10 * 6;
                    if (seconds <= 40)
                        npc.forceText("This is your last chance " + player.getName() + "!");
                    else
                        npc.forceText(randomOverhead(player));
                    player.randomEventNpcShoutDelay.delaySeconds(10);
                    e.delay(1);
                    continue;
                }
                if (!player.randomEventJailDelay.isDelayed()) {
                    npc.forceText("Guards! Lets go, " + player.getName() + ".");
                    npc.lock();
                    player.lock();
                    npc.animate(864);
                    player.face(npc);
                    resetBlock(player);
                    e.delay(3);
                    World.sendGraphics(86, 60, 0, npc.getPosition());
                    World.sendGraphics(86, 60, 0, player.getPosition());
                    Punishment.jail(player, npc, 250);
                    e.delay(1);
                    npc.remove();
                    player.unlock();
                    break;
                }
                if (player.dismissRandomEvent) {
                    npc.lock();
                    npc.animate(863);
                    resetBlock(player);
                    e.delay(3);
                    World.sendGraphics(86, 60, 0, npc.getPosition());
                    e.delay(1);
                    npc.remove();
                    break;
                }
                if (!npc.getPosition().isWithinDistance(player.getPosition(), 14)) {
                    player.dismissRandomEvent = true;
                    e.delay(1);
                    continue;
                }
                if (!npc.getPosition().isWithinDistance(player.getPosition(), 7)) {
                    npc.getMovement().teleport(player.getAbsX(), player.getAbsY(), player.getHeight());
                    e.delay(1);
                    continue;
                }
                int destX, destY;
                if (player.getMovement().hasMoved()) {
                    destX = player.getMovement().lastFollowX;
                    destY = player.getMovement().lastFollowY;
                } else {
                    destX = player.getMovement().followX;
                    destY = player.getMovement().followY;
                }
                if (player.getPosition() == npc.getPosition())
                    DumbRoute.step(npc, player, 1);
                else if (destX == -1 || destY == -1)
                    DumbRoute.step(npc, player, 1);
                else if (!npc.isAt(destX, destY))
                    DumbRoute.step(npc, destX, destY);
                e.delay(1);
            }
            npc.remove();
            resetBlock(player);
        });
    }

    private static void resetBlock(Player player) {
        player.randomEventNPC = null;
        player.dismissRandomEvent = false;
        player.randomEventJailDelay.reset();
        player.randomEventNpcShoutDelay.reset();
    }

    public static void attemptTrigger(Player player) {
        if (player.randomEventNPC != null) return;    // Already spawned
        if (!Random.rollDie(50)) return;    // 1/50
        if (!player.addTickEvent(new TickEvent(TickEventType.RANDOM_EVENT, 3000))) return;      // 30 minute cooldown
        Random.get(RandomEvent.values()).spawn(player);
    }

    static {
        for (RandomEvent re : values()) {
            NPCAction.register(re.npcId, "talk-to", (player, npc) -> {
                if(npc.ownerId != player.getUserId()) {
                    player.dialogue(new NPCDialogue(re.npcId, "I'm not interested in talking with you, " + player.getName() + "."));
                } else {
                    player.dialogue(new NPCDialogue(re.npcId, re.randomOverhead(player)),
                            new OptionsDialogue(
                                    new Option("What is it?",
                                            new PlayerDialogue("What is it?"),
                                            new NPCDialogue(npc, "Well actually, I've forgotten... Carry on."),
                                            new ActionDialogue(() -> {
                                                if(player.randomEventNPC != null) {
                                                    player.dismissRandomEvent = true;
                                                    re.reward(player);
                                                }
                                            })
                                    ),
                                    new Option("*Ignore him* (bad idea)")
                            )
                    );
                }
            });
            NPCAction.register(re.npcId, "dismiss", ((player, npc) ->  {
                if(npc.ownerId != player.getUserId()) {
                    player.dialogue(new NPCDialogue(re.npcId, "You're no use to me, " + player.getName() + "."));
                    return;
                }
                player.dismissRandomEvent = true;
            }));
        }
    }
}
