package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/26/2021
 */
public class StrongholdSecurity {

    private static void teleportPlayer(Player player, int x, int y) {
        player.startEvent(event -> {
            player.lock();
            player.animate(4282);
            event.delay(1);
            player.getMovement().teleport(x, y);
            player.animate(4283);
            event.delay(1);
            player.unlock();
        });
    }

    private static void giftOfPeace(Player player) {
        if (player.strongholdRewards[0]) {
            player.sendMessage("You have already claimed this gift.");
        } else {
            if (player.getInventory().getCapacityFor(995) > 2000) {
                player.strongholdRewards[0] = true;
                player.getInventory().add(995, 2000);
                player.dialogue(
                        new MessageDialogue("The box hinges creak and appear to be forming audible words..."),
                        new MessageDialogue("...congratulations adventurer, you have been deemed worthy of this reward.")
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have enough inventory space to open this gift.")
                );
            }
        }
    }

    private static void grainOfPlenty(Player player) {
        if (player.strongholdRewards[1]) {
            player.sendMessage("You have already claimed this gift.");
        } else {
            if (player.getInventory().getCapacityFor(995) > 3000) {
                player.strongholdRewards[1] = true;
                player.getInventory().add(995, 3000);
                player.dialogue(
                        new MessageDialogue("The grain shifts in the sack, sighing audible words..."),
                        new MessageDialogue("...congratulations adventurer, you have been deemed worthy of this reward.")
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have enough inventory space to open this gift.")
                );
            }
        }
    }

    private static void boxOfHealth(Player player) {
        if (player.strongholdRewards[2]) {
            player.sendMessage("You have already claimed this gift.");
        } else {
            if (player.getInventory().getCapacityFor(995) > 5000) {
                player.strongholdRewards[2] = true;
                player.getInventory().add(995, 5000);
                player.dialogue(
                        new MessageDialogue("The box hinges creak and appear to be forming audible words..."),
                        new MessageDialogue("...congratulations adventurer, you have been deemed worthy of this reward.")
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have enough inventory space to open this gift.")
                );
            }
        }
    }

    private static void cradleOfLife(Player player) {
        if (player.strongholdRewards[3]) {
            ItemContainerG<? extends Item> fancyBootsContainer = player.findItem(Items.FANCY_BOOTS);
            ItemContainerG<? extends Item> fightingBootsContainer = player.findItem(Items.FIGHTING_BOOTS);
            //  Try to swap fancy boots
            if (fancyBootsContainer instanceof Inventory) {
                player.dialogue(
                        new MessageDialogue("Regretting your choice of boots?  Would you like to swap them?"),
                        new OptionsDialogue("Would you like to swap boot styles?",
                                new Option("Yes!", () -> {
                                    player.getInventory().remove(Items.FANCY_BOOTS, 1);
                                    player.getInventory().add(Items.FIGHTING_BOOTS, 1);
                                }),
                                new Option("No, I quite like the look of mine.")
                        )
                );
            //  Try to swap fighter boots
            } else if (fightingBootsContainer instanceof Inventory) {
                player.dialogue(
                        new MessageDialogue("Regretting your choice of boots?  Would you like to swap them?"),
                        new OptionsDialogue("Would you like to swap boot styles?",
                                new Option("Yes!", () -> {
                                    player.getInventory().remove(Items.FIGHTING_BOOTS, 1);
                                    player.getInventory().add(Items.FANCY_BOOTS, 1);
                                }),
                                new Option("No, I quite like the look of mine.")
                        )
                );
            //  Player lost their boots
            } else if (fancyBootsContainer == null && fightingBootsContainer == null) {
                if (player.getInventory().getFreeSlots() > 0) {
                    player.dialogue(
                            new MessageDialogue("It appears you have misplaced your pair of boots, try not to be so careless next time."),
                            new OptionsDialogue("Choose your style of boots",
                                    new Option("I'll take the colourful ones!", () -> {
                                        player.getInventory().add(Items.FANCY_BOOTS, 1);
                                    }),
                                    new Option("I'll take the fighting ones!", () -> {
                                        player.getInventory().add(Items.FIGHTING_BOOTS, 1);
                                    })
                            )
                    );
                } else {
                    player.dialogue(
                            new MessageDialogue("You do not have enough inventory space to claim your boots.")
                    );
                }
            //  Player has boots, just not in inventory
            } else {
                player.dialogue(
                        new MessageDialogue("You need to have your boots in your inventory to swap them.")
                );
            }
        } else {
            if (player.getInventory().getFreeSlots() > 0) {
                player.strongholdRewards[3] = true;
                player.dialogue(
                        new MessageDialogue("As your hand touches the cradle, you hear a voice in your head of a million dead adventurers..."),
                        new MessageDialogue("...welcome adventurer... you have a choice..."),
                        new ItemDialogue().two(Items.FANCY_BOOTS, Items.FIGHTING_BOOTS, "You can choose between these two pairs of boots."),
                        new MessageDialogue("They will both protect your feet exactly the same, however they look very different. " +
                                "You can always come back and get another pair if you lose them, or even swap them for the other style!"),
                        new OptionsDialogue("Choose your style of boots",
                                new Option("I'll take the colourful ones!", () -> {
                                    player.getInventory().add(Items.FANCY_BOOTS, 1);
                                }),
                                new Option("I'll take the fighting ones!", () -> {
                                    player.getInventory().add(Items.FIGHTING_BOOTS, 1);
                                })
                        )
                );
            } else {
                player.dialogue(
                        new MessageDialogue("You do not have enough inventory space to open this gift.")
                );
            }
        }
    }

    static {
        register();
    }

    public static void register() {
        /**
         * Entrance
         */
        ObjectAction.register(20790, "climb-down", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, false, false);
            player.dialogue(new MessageDialogue("You squeeze through the hole and find a ladder a few feet down leading into the Stronghold of Security."));
        });

        /**
         * First level
         */
        ObjectAction.register(20782, 1881, 5232, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3081, 3421, 0, true, true, false);
            player.sendFilteredMessage("You climb the ladder to the surface.");
        });
        ObjectAction.register(20784, 1913, 5226, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3081, 3421, 0, true, true, false);
            player.sendFilteredMessage("You climb the ladder to the surface.");
        });
        ObjectAction.register(20784, 1859, 5244, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3081, 3421, 0, true, true, false);
            player.sendFilteredMessage("You climb the ladder to the surface.");
        });
        ObjectAction.register(20786, 1863, 5238, 0, "use", (player, obj) -> {
            if (player.strongholdRewards[0]) {
                player.getMovement().teleport(1914, 5222, 0);
                player.sendFilteredMessage("You enter the portal to be whisked through to the treasure room.");
            } else {
                player.sendMessage("You must complete this floor before you can use this portal.");
            }
        });
        ObjectAction.register(20785, 1902, 5222, 0, "climb-down", (player, obj) -> {
            if (player.strongholdRewards[0]) {
                Ladder.climb(player, 2042, 5245, 0, false, true, false);
                player.sendFilteredMessage("You climb down the ladder to the next level.");
            } else {
                player.sendMessage("You should claim your gift before venturing further.");
            }
        });

        /**
         * Second level
         */
        ObjectAction.register(19003, 2042, 5246, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(19001, 2040, 5208, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(19001, 2031, 5189, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(19001, 2017, 5210, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(19001, 2011, 5192, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(19005, 2039, 5240, 0, "use", (player, obj) -> {
            if (player.strongholdRewards[1]) {
                player.getMovement().teleport(2021, 5223, 0);
                player.sendFilteredMessage("You enter the portal to be whisked through to the treasure room.");
            } else {
                player.sendMessage("You must complete this floor before you can use this portal.");
            }
        });
        ObjectAction.register(19004, 2026, 5218, 0, "climb-down", (player, obj) -> {
            if (player.strongholdRewards[1]) {
                Ladder.climb(player, 2123, 5252, 0, false, true, false);
                player.sendFilteredMessage("You climb down the ladder to the next level.");
            } else {
                player.sendMessage("You should claim your gift before venturing further.");
            }
        });

        /**
         * Third level
         */
        ObjectAction.register(23705, 2123, 5251, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 2042, 5245, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(23703, 2150, 5278, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 2042, 5245, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(23707, 2120, 5258, 0, "use", (player, obj) -> {
            if (player.strongholdRewards[2]) {
                player.getMovement().teleport(2146, 5287, 0);
                player.sendFilteredMessage("You enter the portal to be whisked through to the treasure room.");
            } else {
                player.sendMessage("You must complete this floor before you can use this portal.");
            }
        });
        ObjectAction.register(23706, 2148, 5284, 0, "climb-down", (player, obj) -> {
            if (player.strongholdRewards[2]) {
                Ladder.climb(player, 2358, 5215, 0, false, true, false);
                player.sendFilteredMessage("You climb down the ladder to the next level.");
            } else {
                player.sendMessage("You should claim your gift before venturing further.");
            }
        });

        /**
         * Forth level
         */
        ObjectAction.register(23921, 2358, 5216, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 2123, 5252, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(23922, 2365, 5212, 0, "use", (player, obj) -> {
            if (player.strongholdRewards[3]) {
                player.getMovement().teleport(2341, 5219, 0);
                player.sendFilteredMessage("You enter the portal to be whisked through to the treasure room.");
            } else {
                player.sendMessage("You must complete this floor before you can use this portal.");
            }
        });
        ObjectAction.register(23732, 2309, 5240, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 2123, 5252, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(23732, 2350, 5215, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 2123, 5252, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });


        /**
         * Double doors
         */
        final int[] DOORS = {19206, 19207, 17009, 17100, 23653, 23654, 23727, 23728};
        for (int door : DOORS) {
            ObjectAction.register(door, "open", (player, obj) -> {
                boolean atObjX = obj.x == player.getAbsX();
                boolean atObjY = obj.y == player.getAbsY();

                if (obj.direction == 0 && atObjX)
                    teleportPlayer(player, player.getAbsX() - 1, player.getAbsY());
                else if (obj.direction == 1 && atObjY)
                    teleportPlayer(player, obj.x, obj.y + 1);
                else if (obj.direction == 2 && atObjX)
                    teleportPlayer(player, obj.x + 1, obj.y);
                else if (obj.direction == 3 && atObjY)
                    teleportPlayer(player, obj.x, obj.y - 1);
                else
                    teleportPlayer(player, obj.x, obj.y);
            });
        }

        /**
         * Gifts
         */
        ObjectAction.register(20656, "open", (player, obj) -> giftOfPeace(player));
        ObjectAction.register(19000, "search", (player, obj) -> grainOfPlenty(player));
        ObjectAction.register(23709, "open", (player, obj) -> boxOfHealth(player));
        ObjectAction.register(23731, "search", (player, obj) -> cradleOfLife(player));

        // Notice
        ObjectAction.register(7054, "read", ((player, obj) -> player.sendMessage("You let out a long, deep sign, as you do not know how to read.")));
    }
}
