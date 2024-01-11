package io.ruin.model.activities.combat.nightmarezone;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * A class that handles Nightmare Zone pregame functionalities.
 *
 */
public final class NightmareZonePregame {

    public static boolean enabled = true;

    private static final Bounds ENCLOSURE = new Bounds(2601, 3113, 2606, 3118, 0);

    private static void prepareDream(Player player, NightmareZoneDreamDifficulty difficulty) {

        Tile.getObject(26291, 2605, 3117, 0, 10, 0).setId(26269);
    }

    private static void depositCoins(Player player, int numCoins) {
        int inventoryCoins = player.getInventory().getAmount(995);
        if (inventoryCoins == 0) {
            player.dialogue(new MessageDialogue("You do not have any coins to deposit into the coffer."));
            return;
        }
        if (numCoins > inventoryCoins) {
            numCoins = inventoryCoins;
        }
        int cofferCoins = Config.NMZ_COFFER_AMT.get(player);
        if ((long) cofferCoins + inventoryCoins > 262_143_000) {
            numCoins = Integer.MAX_VALUE - cofferCoins;
            if (numCoins == 0) {
                player.dialogue(new MessageDialogue("Your coffer is full."));
                return;
            }
        }
        numCoins = (numCoins / 1000);    // Round it down to nearest thousand
        if (numCoins <= 0) {
            player.dialogue(new MessageDialogue("You can only deposit in increments of 1,000 coins."));
            return;
        }
        player.getInventory().remove(995, numCoins);
        Config.NMZ_COFFER_AMT.increment(player, numCoins);
    }

    private static void withdrawCoins(Player player, int numCoins) {
        int coinsStored = Config.NMZ_COFFER_AMT.get(player) * 1000;
        int amountToWithdraw = Math.min(coinsStored, numCoins);
        int inventoryAmount = player.getInventory().getAmount(995);
        if ((long) inventoryAmount + amountToWithdraw > Integer.MAX_VALUE) {
            amountToWithdraw = Integer.MAX_VALUE - inventoryAmount;
            if (amountToWithdraw < 1000) {
                player.dialogue(new MessageDialogue("You have too many coins in your inventory to withdraw from the coffer."));
                return;
            }
        }
        player.getInventory().addOrDrop(995, amountToWithdraw);
        Config.NMZ_COFFER_AMT.set(player, Math.min(0, (coinsStored - amountToWithdraw) / 1000));
    }

    private static OptionsDialogue depositWithdraw(Player player) {
        int coins = Config.NMZ_COFFER_AMT.get(player) * 1000;
        return new OptionsDialogue("Dominic's Coffer (" + NumberUtils.formatNumber(coins) + " coins)",
                new Option("Deposit Money.", () -> {
                    player.integerInput("How much do you wish to deposit? (increments of 1,000)", (p) -> {
                        depositCoins(player, p);
                    });
                }),
                new Option("Withdraw Money.", () -> {
                    player.integerInput("How much do you wish to withdraw? (" + NumberUtils.formatNumber(coins) + ")", (p) -> {
                        withdrawCoins(player, p);
                    });
                }),
                new Option("Cancel.")
        );
    }

    private static OptionsDialogue depositOnly(Player player) {
        int coins = Config.NMZ_COFFER_AMT.get(player) * 1000;
        return new OptionsDialogue("Dominic's Coffer (" + NumberUtils.formatNumber(coins) + " coins)",
                new Option("Deposit Money.", () -> {
                    player.integerInput("How much do you wish to deposit? (" + NumberUtils.formatNumber(coins) + ")", (p) -> {
                        depositCoins(player, p);
                    });
                }),
                new Option("Cancel.")
        );
    }

    private static OptionsDialogue withdrawOnly(Player player) {
        int coins = Config.NMZ_COFFER_AMT.get(player) * 1000;
        return new OptionsDialogue("Dominic's Coffer (" + NumberUtils.formatNumber(coins) + " coins)",
                new Option("Withdraw Money.", () -> {
                    player.integerInput("How much do you wish to withdraw? (" + NumberUtils.formatNumber(coins) + ")", (p) -> {
                        withdrawCoins(player, p);
                    });
                }),
                new Option("Cancel.")
        );
    }

    static {
        NPCAction.register(1120, "talk-to", (player, npc) -> {
            if (!enabled) {
                player.dialogue(new MessageDialogue("Nightmare Zone is currently under maintenance"));
                return;
            }
            player.dialogue(new NPCDialogue(1120, "Welcome to The Nightmare Zone! Would you like me to create a dream for you?"),
                    new OptionsDialogue("Select a dream difficulty.",
                            new Option("Normal", () -> {
                                NightmareZoneDream dream = new NightmareZoneDream(player, NightmareZoneDreamDifficulty.NORMAL);
                                dream.enter();
                            }),
                            new Option("Hard", () -> {
                                NightmareZoneDream dream = new NightmareZoneDream(player, NightmareZoneDreamDifficulty.HARD);
                                dream.enter();
                            })));
        });

        NPCAction.register(1120, "dream", (player, npc) -> {
            if (!enabled) {
                player.dialogue(new MessageDialogue("Nightmare Zone is currently under maintenance"));
                return;
            }
            player.dialogue(new OptionsDialogue("Select a dream difficulty.",
                    new Option("Normal", () -> {
                        NightmareZoneDream dream = new NightmareZoneDream(player, NightmareZoneDreamDifficulty.NORMAL);
                        dream.enter();
                    }),
                    new Option("Hard", () -> {
                        NightmareZoneDream dream = new NightmareZoneDream(player, NightmareZoneDreamDifficulty.HARD);
                        dream.enter();
                    })
            ));
        });

        /* Coffer */
//        ObjectAction.register(26292, 1, (player, obj) -> {
//            if (player.nmzCofferCoins > 0) {
//                player.dialogue(
//                        new ItemDialogue().one(995, "You have " + NumberUtils.formatNumber(player.nmzCofferCoins) + " coins stored in the coffer.<br>"
//                                + (player.getInventory().contains(995, 1_000) ? "" : "You need at least 1,000 coins to make a deposit")),
//                        player.getInventory().contains(995, 1_000) ? depositWithdraw(player) : withdrawOnly(player)
//                );
//            } else {
//                if (player.getInventory().contains(995, 1_000)) {
//                    player.dialogue(
//                            new ItemDialogue().one(995, "The coffer is empty.<br>"),
//                            depositOnly(player));
//
//                } else {
//                    player.dialogue(
//                            new ItemDialogue().one(995, "The coffer is empty.<br>"
//                                    + (player.getInventory().contains(995, 1_000) ? "" : "You need at least 1,000 coins to make a deposit"))
//                    );
//                }
//            }
//
//
//        });

        /*  Filled vial */
        ObjectAction.register(26269, "drink", (player, obj) -> {

        });

        /* Rewards chest */
/*        ObjectAction.register(26273, 2, (player, obj) -> {
            if (NPCDef.get(1120) != null) {
                NPCDef.get(1120).shop.open(player);
            }
//            Config.NMZ_REWARD_POINTS_TOTAL.set(player, player.nmzRewardPoints);
//            player.openInterface(InterfaceType.MAIN, Interface.NIGHTMARE_ZONE_REWARDS);
//            for (int i = 0; i < 200; i++) {
//                player.getPacketSender().sendClientScript(310, "Ii", i, 500);
//            }
        });

        ObjectAction.register(26273, 1, (player, obj) -> {
            if (NPCDef.get(1120) != null) {
                NPCDef.get(1120).shop.open(player);
            }
         //   player.dialogue(new MessageDialogue("You currently have " + player.nmzRewardPoints + " Nightmare Zone rewards points."));
        });

        ObjectAction.register(26273, 3, (player, obj) -> {
            if (NPCDef.get(1120) != null) {
                NPCDef.get(1120).shop.open(player);
            }
       //     player.dialogue(new MessageDialogue("You currently have " + player.nmzRewardPoints + " Nightmare Zone rewards points."));
        });*/

        /* Lobby enclosure map listener */
        MapListener.registerBounds(ENCLOSURE).onEnter(player -> {
            Config.NMZ_COFFER_STATUS.set(player, 1); // 1 means unlocked coffer
            player.openInterface(InterfaceType.PRIMARY_OVERLAY, 207);
            player.getPacketSender().sendClientScript(264, "i", 0);
        }).onExit((player, logout) -> {
            player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
        });
    }

}

