package io.ruin.model.skills;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.item.loot.RareDropTable;
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
                    new LootItem(Items.FLAX_NOTE, 20, 55, 1)
            ),
            true
    ),
    DRILL_DEMON(
            6743,
            new String[]{"Private [player name], atten-SHUN!", "Stop day-dreaming, Private [player name]!", "Get your rear in gear, Private [player name]!", "I want you for my corps, Private [player name]!"},
            new Item[]{new Item(6654), new Item(6655), new Item(6656)},
            new LootTable().addTable(1,
                    new LootItem(Items.BOOK_OF_KNOWLEDGE, 1, 1)
            ),
            true
    ),
    FREAKY_FORESTER(
            6748,
            new String[]{"[player name]!", "Hello? [player name]?", "Talk to me, [player name]!", "[player name]? Are you there?!", "I'd like to talk to you, [player name]!"},
            new Item[]{new Item(6180), new Item(6181), new Item(6182)},
            new LootTable().addTable(1,
                    new LootItem(Items.BOOK_OF_KNOWLEDGE, 1, 1)
            ),
            true
    ),
    GRAVEDIGGER(
            6746,
            new String[]{"Hello, [player name]?", "Please help me, [player name].", "I need your help, [player name]!", "Can I borrow you for a minute, [player name]?"},
            new Item[]{new Item(7594), new Item(7592), new Item(7593), new Item(7595), new Item(7596)},
            new LootTable().addTable(1,
                    new LootItem(Items.BOOK_OF_KNOWLEDGE, 1, 1)
            ),
            true
    ),
    MIME(
            6750,
            new String[]{"[player name]!", "Hello? [player name]?", "Talk to me, [player name]!", "[player name]? Are you there?", "I'd like to talk to you, [player name]!"},
            new Item[]{new Item(3057), new Item(3058), new Item(3059), new Item(3060), new Item(3061)},
            new LootTable().addTable(1,
                    new LootItem(Items.BOOK_OF_KNOWLEDGE, 1, 1)
            ),
            true
    ),
    FROG(
            5430,
            new String[]{"Hello, [player name]", "[player name], the frog desires your attention!"},
            new Item[]{},
            new LootTable().addTable(1,
                    new LootItem(Items.FROG_TOKEN, 1, 1)
            ),
            false
    ),
    QUIZ_MASTER(
            6755,
            new String[]{"Hey [player name]! It's your lucky day!", "Wakey-wakey, [player name]!", "Come along with me, [player name]!", "You're in for a treat, [player name]!", "Hey [player name]! It's your lucky day!"},
            new Item[]{},
            new LootTable().addTable(1,
                    new LootItem(6199, 1, 1)
            ),
            true
    ),
    GENIE(
            326,
            new String[]{"Greetings, [Master/Mistress] [player name]!", "Hello, [Master/Mistress] [player name]?", "Ehem... [Master/Mistress] [player name]?", "I'm waiting for you, [Master/Mistress] [player name]!"},
            new Item[]{},
            new LootTable().addTable(1,
                    new LootItem(Items.BOOK_OF_KNOWLEDGE, 1, 1)
            ),
            false
    ),
    CERTERS(
            5436,
            new String[]{"Greetings [player name], I need your help.", "Talk to me, [player name]!", "Hello, [player name], are you there?", "I'd like to talk to you, [player name]!", "ehem... Hello [player name], please talk to me!"},
            new Item[]{},
            new LootTable().addTable(1,
                    new LootItem(Items.UNCUT_SAPPHIRE, 1, 30),
                    new LootItem(Items.KEBAB, 1, 15),
                    new LootItem(Items.UNCUT_EMERALD, 1, 15),
                    new LootItem(Items.SPINACH_ROLL, 1, 14),
                    new LootItem(995, 80, 10),
                    new LootItem(995, 160, 10),
                    new LootItem(995, 320, 10),
                    new LootItem(995, 480, 10),
                    new LootItem(995, 640, 10),
                    new LootItem(Items.UNCUT_RUBY, 1, 8),
                    new LootItem(995, 240, 6),
                    new LootItem(Items.COSMIC_TALISMAN, 1, 4),
                    new LootItem(Items.UNCUT_DIAMOND, 1, 2),
                    new LootItem(Items.TOOTH_HALF_OF_KEY, 1, 1),
                    new LootItem(Items.LOOP_HALF_OF_KEY, 1, 1)
            ),
            true
    ),
    DUNCE(
            6749,
            new String[]{"Surprise exam, [player name]!", "Surprise exam, [player name]!", "Come to school, [player name]!", "You're getting tested, [player name]!", "The teacher wants you, [player name]!", "Teacher gets angry when he's kept waiting, [player name]!"},
            new Item[]{},
            new LootTable().addTable(1,
                    new LootItem(Items.BOOK_OF_KNOWLEDGE, 1, 1)
            ),
            false
    ),
    RICK_TURPENTINE(
            375,
            new String[]{"Good day to you, [player name].", "I seek a moment of your time!", "[player name], please speak to me!"},
            new Item[]{},
            new LootTable().addTable(1,
                    new LootItem(Items.UNCUT_SAPPHIRE, 1, 30),
                    new LootItem(Items.KEBAB, 1, 15),
                    new LootItem(Items.UNCUT_EMERALD, 1, 15),
                    new LootItem(Items.SPINACH_ROLL, 1, 14),
                    new LootItem(995, 80, 10),
                    new LootItem(995, 160, 10),
                    new LootItem(995, 320, 10),
                    new LootItem(995, 480, 10),
                    new LootItem(995, 640, 10),
                    new LootItem(Items.UNCUT_RUBY, 1, 8),
                    new LootItem(995, 240, 6),
                    new LootItem(Items.COSMIC_TALISMAN, 1, 4),
                    new LootItem(Items.UNCUT_DIAMOND, 1, 2),
                    new LootItem(Items.TOOTH_HALF_OF_KEY, 1, 1),
                    new LootItem(Items.LOOP_HALF_OF_KEY, 1, 1)
            ),
            true
    ),
    ;

    private final int npcId;
    private final String[] overheadText;
    private final Item[] costumePieces;
    private final LootTable rewards;
    private final boolean animate;

    private String randomOverhead(Player player) {
        return Random.get(overheadText).replace("[player name]", player.getName()).replace("[Master/Mistress]", player.getAppearance().isMale() ? "Master" : "Mistress");
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
                player.getTaskManager().doLookupByUUID(1108);   // Get a Random Event
                PlayerCounter.RANDOM_EVENTS.increment(player, 1);
                player.getPacketSender().resetHintIcon(true);
                return;
            }
        }
        Item randomReward = rewards.rollItem();
        if (player.getInventory().hasFreeSlots(1)) {
            player.getInventory().add(randomReward);
            player.sendMessage("The stranger hands you a reward as he disappears...");
        } else {
            player.getBank().add(randomReward.getId());
            player.sendMessage("The stranger adds a reward to your bank as he disappears...");
        }
        player.getTaskManager().doLookupByUUID(1108);   // Get a Random Event
        PlayerCounter.RANDOM_EVENTS.increment(player, 1);
        player.getPacketSender().resetHintIcon(true);
        if (randomReward.getId() == Items.FROG_TOKEN) player.getCollectionLog().collect(randomReward);
    }

    private void spawn(Player player, double durationMultiplier) {
        NPC npc = new NPC(npcId);
        player.randomEventNPC = npc;
        player.randomEventJailDelay.delaySeconds((int) (360 * durationMultiplier));
        player.randomEventNpcShoutDelay.delaySeconds(10);
        player.sendMessage(Color.COOL_BLUE.wrap("Someone appears interested in your actions..."));
        npc.ownerId = player.getUserId();
        npc.spawn(player.getAbsX(), player.getAbsY(), player.getHeight());
        npc.face(player);
        npc.forceText(randomOverhead(player));
        player.getPacketSender().sendHintIcon(npc);
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
                    if (System.currentTimeMillis() - player.getTemporaryAttributeIntOrZero("LAST_XP") < 120000) {
                        npc.forceText("Guards! Lets go, " + player.getName() + ".");
                        npc.lock();
                        player.lock();
                        if (animate) npc.animate(864);
                        player.face(npc);
                        resetBlock(player);
                        e.delay(3);
                        World.sendGraphics(86, 60, 0, npc.getPosition());
                        World.sendGraphics(86, 60, 0, player.getPosition());
                        Punishment.jail(player, npc, 100);
                        e.delay(1);
                        npc.remove();
                        player.unlock();
                    } else {
                        npc.forceText("Aghh, nevermind.");
                        npc.lock();
                        if (animate) npc.animate(864);
                        resetBlock(player);
                        e.delay(3);
                        World.sendGraphics(86, 60, 0, npc.getPosition());
                        e.delay(1);
                        npc.remove();
                    }
                    break;
                }
                if (player.dismissRandomEvent) {
                    npc.lock();
                    if (animate) npc.animate(863);
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
        attemptTrigger(player, 50, 1D);
    }

    public static void attemptTrigger(Player player, int chance) {
        attemptTrigger(player, chance, 1D);
    }

    public static void attemptTrigger(Player player, int chance, double durationMultiplier) {
        player.putTemporaryAttribute("LAST_XP", System.currentTimeMillis());
        if (player.randomEventNPC != null) return;    // Already spawned
        if (!Random.rollDie(chance)) return;
        if (!player.addTickEvent(new TickEvent(TickEventType.RANDOM_EVENT, 2500))) return;      // 25 minute cooldown
        Random.get(RandomEvent.values()).spawn(player, durationMultiplier);
    }

    static {
        for (RandomEvent re : values()) {
            NPCAction.register(re.npcId, "talk-to", (player, npc) -> {
                if(npc.ownerId != player.getUserId()) {
                    player.dialogue(new NPCDialogue(re.npcId, "I'm not interested in talking with you, " + player.getName() + "."));
                } else {
                    player.dialogue(
                            new NPCDialogue(re.npcId, re.randomOverhead(player)).keepOpenWhenHit().setOnClose(p -> player.putTemporaryAttribute("LAST_XP", System.currentTimeMillis())),
                            new OptionsDialogue(
                                    new Option("What is it?",
                                            new PlayerDialogue("What is it?").keepOpenWhenHit().setOnClose(p -> player.putTemporaryAttribute("LAST_XP", System.currentTimeMillis())),
                                            new NPCDialogue(npc, "Well actually, I've forgotten... Carry on.").keepOpenWhenHit(),
                                            new ActionDialogue(() -> {
                                                if(player.randomEventNPC != null) {
                                                    player.dismissRandomEvent = true;
                                                    re.reward(player);
                                                }
                                            }).keepOpenWhenHit().setOnClose(p -> player.putTemporaryAttribute("LAST_XP", System.currentTimeMillis()))
                                    ),
                                    new Option("*Ignore him* (bad idea)")
                            ).keepOpenWhenHit().setOnClose(p -> player.putTemporaryAttribute("LAST_XP", System.currentTimeMillis()))
                    );
                }
            });
            NPCAction.register(re.npcId, "dismiss", ((player, npc) ->  {
                if(npc.ownerId != player.getUserId()) {
                    player.dialogue(new NPCDialogue(re.npcId, "You're no use to me, " + player.getName() + "."));
                    return;
                }
                player.getPacketSender().resetHintIcon(true);
                player.dismissRandomEvent = true;
            }));
        }
    }

    /*
     * Mystery box
     */
    public static final LootTable MYSTERY_BOX = new LootTable()
            .addTable(204,
                    new LootItem(Items.BOOK_OF_KNOWLEDGE, 1, 1),
                    new LootItem(Items.CABBAGE, 1, 1),
                    new LootItem(Items.DIAMOND, 1, 1),
                    new LootItem(Items.BUCKET, 1, 1),
                    new LootItem(956, 1, 1),
                    new LootItem(Items.OLD_BOOT, 1, 1),
                    new LootItem(Items.BODY_RUNE, 1, 1),
                    new LootItem(Items.ONION, 1, 1),
                    new LootItem(Items.MITHRIL_SCIMITAR, 1, 1),
                    new LootItem(Items.CASKET, 1, 1),
                    new LootItem(Items.STEEL_PLATEBODY, 1, 1),
                    new LootItem(Items.NATURE_RUNE, 20, 1)
            )
            .addTable(34,
                    new LootItem(24362, 1, 200),
                    new LootItem(24363, 1, 150),
                    new LootItem(24364, 1, 100)
            )
            .addTable(17,
                    new LootItem(-1, 1, 1)
            )
            .addTable(1,
                    new LootItem(Items.STALE_BAGUETTE, 1, 1)
            );

    static {
        ItemAction.registerInventory(6199, "open", (player, item) -> {
            item.remove();
            Item loot = MYSTERY_BOX.rollItem();
            if (loot == null || loot.getId() == -1) {
                loot = RareDropTable.RARE_DROP_TABLE.rollItem();
            }
            player.getInventory().add(loot);
            player.getCollectionLog().collect(loot);
        });
    }
}
