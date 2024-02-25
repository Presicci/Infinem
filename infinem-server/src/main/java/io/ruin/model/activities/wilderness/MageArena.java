package io.ruin.model.activities.wilderness;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.*;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

public class MageArena {

    public static final Config CONFIG = Config.varp(267, true);

    private static void pullLever(Player player, GameObject lever, int teleportY, String message) {
        if (player.getCombat().checkTb())
            return;
        player.startEvent(event -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE);
            player.animate(2710);
            lever.animate(2711);
            player.sendMessage("You pull the lever...");
            event.delay(2);
            player.animate(714);
            player.graphics(111, 110, 0);
            event.delay(3);
            player.resetAnimation();
            player.getMovement().teleport(3105, teleportY, 0);
            player.sendMessage("...and get teleported " + message + " the arena!");
            player.unlock();
        });
    }

    private static final Position[] TELEPORT_POSITIONS = new Position[] {
            new Position(3102, 3926, 0),
            new Position(3114, 3933, 0),
            new Position(3097, 3930, 0),
            new Position(3107, 3942, 0),
            new Position(3105, 3934, 0)
    };

    private static void startFight(Player player) {
        Position destination = null;
        for (Position pos : TELEPORT_POSITIONS) {
            if (Tile.get(pos).playerCount == 0) {
                destination = pos;
                break;
            }
        }
        if (destination == null)
            destination = TELEPORT_POSITIONS[0];
        ModernTeleport.teleport(player, destination);
        NPC kolodion = new NPC(1605);
        player.getCombat().updateLastDefend(kolodion);
        Position npcDest = destination.relative(-2, 0);
        World.startEvent(e -> {
            e.delay(3);
            kolodion.spawn(npcDest);
            kolodion.removeIfIdle(player);
            kolodion.removeOnDeath();
            kolodion.targetPlayer(player, false);
            kolodion.attackTargetPlayer();
        });
    }

    private static void askToFight(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Yes indeedy.",
                                new PlayerDialogue("Yes indeedy."),
                                new NPCDialogue(npc, "Good, good. You have a healthy sense of competition."),
                                new NPCDialogue(npc, "Remember, traveller - in my arena, hand-to-hand combat is useless. Your strength will diminish as you enter the arena, but the spells you can learn are amongst the most powerful in all of Gielinor."),
                                new NPCDialogue(npc, "Before I can accept you in, we must duel."),
                                new OptionsDialogue(
                                        new Option("Okay, let's fight.",
                                                new PlayerDialogue("Okay, let's fight."),
                                                new NPCDialogue(npc, "I must first check that you are up to scratch."),
                                                new PlayerDialogue("You don't need to worry about that."),
                                                new NPCDialogue(npc, "Not just any magician can enter - only the most powerful and most feared. Before you can use the power of this arena, you must prove yourself against me."),
                                                new ActionDialogue(() -> startFight(player))
                                        ),
                                        new Option("No thanks.", new PlayerDialogue("No thanks."))
                                )
                        ),
                        new Option("No I don't.", new PlayerDialogue("No I don't."), new NPCDialogue(npc, "Your loss."))
                )
        );
    }

    private static void mainOptions(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Can I fight here?", () -> player.dialogue(
                                new PlayerDialogue("Can I fight here?"),
                                new NPCDialogue(npc, "My arena is open to any high level wizard, but this is no game. Many wizards fall in this arena, never to rise again. The strongest mages have been destroyed."),
                                new NPCDialogue(npc, "If you're sure you want in?"),
                                new ActionDialogue(() -> askToFight(player, npc))
                        )),
                        new Option("What's the point of that?", () -> player.dialogue(
                                new PlayerDialogue("CWhat's the point of that?"),
                                new NPCDialogue(npc, "We learn how to use our magic to its fullest and how to channel the forces of the cosmos into our world..."),
                                new NPCDialogue(npc, "But mainly, I just like blasting people into dust."),
                                new ActionDialogue(() -> mainOptions(player, npc))
                        )),
                        new Option("That's barbaric!",
                                new PlayerDialogue("That's barbaric!"),
                                new NPCDialogue(npc, "Nope, it's magic. But I know what you mean. So do you want to join us?"),
                                new ActionDialogue(() -> askToFight(player, npc))
                        )
                )
        );
    }

    private static void kolodionDialogue(Player player, NPC npc) {
        if (player.getStats().get(StatType.Magic).fixedLevel < 60) {
            player.dialogue(
                    new PlayerDialogue("Hello there. What is this place?"),
                    new NPCDialogue(npc, "Do not waste my time with trivial questions. I am the Great Kolodion, master of battle magic. I have an arena to run."),
                    new PlayerDialogue("Can I enter?"),
                    new NPCDialogue(npc, "Hah! A wizard of your level? Don't be absurd.")
            );
        } else {
            if (CONFIG.get(player) >= 8) {
                player.dialogue(
                        new PlayerDialogue("Hello, Kolodion."),
                        new NPCDialogue(npc, "Hello, young mage. You're a tough one."),
                        new PlayerDialogue("What now?"),
                        new NPCDialogue(1603, "Step into the magic pool. It will take you to a chamber. There, you must decide which god you will represent in the arena."),
                        new PlayerDialogue("Thanks, Kolodion."),
                        new NPCDialogue(1603, "That's what I'm here for.")
                );
            } else {
                player.dialogue(
                        new PlayerDialogue("Hello there. What is this place?"),
                        new NPCDialogue(npc, "I am the great Kolodion, master of battle magic, and this is my battle arena. Top wizards travel from all over Gielinor to fight here."),
                        new ActionDialogue(() -> mainOptions(player, npc))
                );
            }
        }
    }

    private static final int[] CAPES = new int[] { 2412, 2413, 2414 };

    private static void pickStaff(Player player, NPC npc, int itemId) {
        if (!player.getInventory().hasFreeSlots(1)) {
            player.dialogue(new NPCDialogue(npc, "You don't have enough space for me to give you the staff."));
            return;
        }
        CONFIG.set(player, 9);
        player.getInventory().add(itemId);
        player.dialogue(new ItemDialogue().one(itemId, "The guardian hands you an ornate magic staff."));
    }

    private static void guardianDialogue(Player player, NPC npc) {
        if (CONFIG.get(player) < 9) {
            if (player.getInventory().hasAtLeastOneOf(CAPES) || player.getEquipment().hasAtLeastOneOf(CAPES)) {
                player.dialogue(
                        new PlayerDialogue("Hi."),
                        new NPCDialogue(npc, "Hello adventurer, have you made your choice?"),
                        new OptionsDialogue(
                                new Option("Saradomin", () -> pickStaff(player, npc, 2415)),
                                new Option("Guthix", () -> pickStaff(player, npc, 2416)),
                                new Option("Zamorak", () -> pickStaff(player, npc, 2417))
                        )
                );
            } else {
                player.dialogue(
                        new PlayerDialogue("Hello my friend, Kolodion sent me down."),
                        new NPCDialogue(npc, "Sssshhh... the gods are talking. I can hear their whispers."),
                        new NPCDialogue(npc, "Can you hear them adventurer, they're calling you."),
                        new PlayerDialogue("Erm... ok!"),
                        new NPCDialogue(npc, "Go chant at the statue of the god you most wish to represent in this world, you will be rewarded."),
                        new NPCDialogue(npc, "Once you are done, come back to me. I shall supply you with a mage staff ready for battle.")
                );
            }
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "Hello, would you like to browse my shop?"),
                    new OptionsDialogue(
                            new Option("Yes please. What are you selling?", () -> npc.openShop(player)),
                            new Option("No thanks.", new PlayerDialogue("No thanks."))
                    )
            );
        }
    }

    static {
        /**
         * Levers
         */
        ObjectAction.register(9706, 3104, 3956, 0, "pull", (player, obj) -> {
            pullLever(player, obj, 3951, "into");
        });
        ObjectAction.register(9707, 3105, 3952, 0, "pull", (player, obj) -> {
            pullLever(player, obj, 3956, "out of");
        });

        /**
         * Odd unclipped areas around the circle which make you look
         * like you're floating on lava.
         */
        Tile.get(3093, 3939, 0).flagUnmovable();
        Tile.get(3094, 3941, 0).flagUnmovable();
        Tile.get(3110, 3946, 0).flagUnmovable();
        Tile.get(3098, 3945, 0).flagUnmovable();
        Tile.get(3100, 3946, 0).flagUnmovable();
        Tile.get(3112, 3945, 0).flagUnmovable();
        Tile.get(3116, 3941, 0).flagUnmovable();
        Tile.get(3117, 3939, 0).flagUnmovable();
        Tile.get(3117, 3928, 0).flagUnmovable();
        Tile.get(3116, 3926, 0).flagUnmovable();
        Tile.get(3112, 3922, 0).flagUnmovable();
        Tile.get(3110, 3921, 0).flagUnmovable();
        Tile.get(3100, 3921, 0).flagUnmovable();
        Tile.get(3098, 3922, 0).flagUnmovable();
        Tile.get(3094, 3926, 0).flagUnmovable();
        Tile.get(3093, 3928, 0).flagUnmovable();

       SpawnListener.register("battle mage", npc -> npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            if (MapArea.MAGE_ARENA.inArea(killer.player)) {

            }
        });

        NPCAction.register(1602, "talk-to", MageArena::guardianDialogue);
        NPCAction.register(1603, "talk-to", MageArena::kolodionDialogue);

        /**
         * Sack containing knife
         */
        ObjectAction.register(14743, 3093, 3956, 0, "search", (player, obj) -> {
            if(player.getInventory().isFull()) {
                player.sendFilteredMessage("Nothing interesting happens.");
                return;
            }

            player.getInventory().add(Tool.KNIFE, 1);
            player.sendFilteredMessage("You search the sack and find a knife.");
        });

        ObjectAction.register(2878, 2541, 4719, 0, "Step-into", (player, obj) -> {
            if (CONFIG.get(player) < 8) {
                player.dialogue(new NPCDialogue(1603, "If you wish to step into the magic pool, you must first prove yourself to me."));
                return;
            }
            player.dialogue(new MessageDialogue("You step into the pool of sparkling water. You feel energy rush through your veins."), new ActionDialogue(() -> {
                player.startEvent((event) -> {
                    player.lock(LockType.FULL_DELAY_DAMAGE);
                    event.path(player, Position.of(2542, 4718));
                    event.delay(1);
                    player.animate(741);
                    player.getMovement().force(0, 2, 0, 0, 5, 40, Direction.getDirection(player.getPosition(), Position.of(2542, 4720)));
                    event.delay(1);
                    player.animate(804);
                    player.graphics(68);
                    event.delay(1);
                    player.getMovement().teleport(2509, 4689, 0);
                    player.animate(-1);
                    player.unlock();
                });
            }));
        });

        ObjectAction.register(2879, 2508, 4686, 0, "Step-into", (player, obj) -> {
            player.dialogue(new MessageDialogue("You step into the pool of sparkling water. You feel energy rush through your veins."), new ActionDialogue(() -> {
                player.startEvent((event) -> {
                    player.lock(LockType.FULL_DELAY_DAMAGE);
                    event.path(player, Position.of(2509, 4689));
                    event.delay(1);
                    player.animate(741);
                    player.getMovement().force(0, -2, 0, 0, 5, 40, Direction.getDirection(player.getPosition(), Position.of(2509, 4687)));
                    event.delay(1);
                    player.animate(804);
                    player.graphics(68);
                    event.delay(1);
                    player.getMovement().teleport(2542, 4718, 0);
                    player.animate(-1);
                    player.unlock();
                });
            }));
        });

        ObjectAction.register(2873, 2500, 4720, 0, "Pray-at", (player, obj) -> {
            player.startEvent((event) -> {
                player.lock(LockType.MOVEMENT);
                player.animate(645);
                player.dialogue(new MessageDialogue("You kneel and chant to Saradomin...").hideContinue());
                event.delay(2);
                player.dialogue(new MessageDialogue("You kneel and chant to Saradomin...<br>" +
                        "You feel a rush of energy charge through your veins.<br>" +
                        "Suddenly a cape appears before you."));
                event.delay(1);
                World.sendGraphics(188, 50, 0, 2500, 4720, 0);
                new GroundItem(2412, 1).position(2500, 4720, 0).owner(player).spawnPrivate();
                player.unlock();
            });
        });

        ObjectAction.register(2875, 2507, 4723, 0, "Pray-at", (player, obj) -> {
            player.startEvent((event) -> {
                player.lock(LockType.MOVEMENT);
                player.animate(645);
                player.dialogue(new MessageDialogue("You kneel and chant to Guthix...").hideContinue());
                event.delay(3);
                World.sendGraphics(188, 0, 0, 2507, 4723, 0);
                new GroundItem(2413, 1).position(2507, 4723, 0).owner(player).spawnPrivate();
                player.dialogue(new MessageDialogue("You kneel and chant to Guthix...<br>" +
                        "You feel a rush of energy charge through your veins.<br>" +
                        "Suddenly a cape appears before you."));
                player.unlock();
            });
        });

        ObjectAction.register(2874, 2516, 4720, 0, "Pray-at", (player, obj) -> {
            player.startEvent((event) -> {
                player.lock(LockType.MOVEMENT);
                player.animate(645);
                player.dialogue(new MessageDialogue("You kneel and chant to Zamorak...").hideContinue());
                event.delay(3);
                World.sendGraphics(188, 0, 0, 2516, 4720, 0);
                new GroundItem(2414, 1).position(2516, 4720, 0).owner(player).spawnPrivate();
                player.dialogue(new MessageDialogue("You kneel and chant to Zamorak...<br>" +
                        "You feel a rush of energy charge through your veins.<br>" +
                        "Suddenly a cape appears before you."));
                player.unlock();
            });
        });
    }

}
