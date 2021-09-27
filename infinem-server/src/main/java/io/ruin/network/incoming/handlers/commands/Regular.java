package io.ruin.network.incoming.handlers.commands;

import io.ruin.cache.Color;
import io.ruin.data.impl.Help;
import io.ruin.model.World;
import io.ruin.model.entity.npc.actions.edgeville.EmblemTrader;
import io.ruin.model.entity.npc.actions.edgeville.Nurse;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.player.Title;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.journal.presets.PresetCustom;
import io.ruin.model.item.actions.impl.DiceBag;
import io.ruin.model.item.actions.impl.boxes.MithrilSeeds;
import io.ruin.model.item.containers.bank.BankItem;
import io.ruin.network.incoming.handlers.CommandHandler;
import io.ruin.services.Loggers;
import io.ruin.services.Punishment;

import java.util.LinkedList;
import java.util.List;

public class Regular {

    public static boolean handleRegular(Player player, String query, String command, String[] args) {

        switch (command) {

            case "commands": {
                player.sendScroll("<col=800000>Commands</col>",
                        "<col=800000>Teleport Commands:</col>",
                        "::home", "::stake", "::mb",
                        "::cammypvp", "::fallypvp", "::lumbpvp",
                        "",
                        "<col=800000>Misc Commands:</col>",
                        "::help", "::yell",
                        "::char", "::heal", "::skull",
                        "::journal", "::presets", "::toggles", "::bestiary",
                        "",
                        "<col=800000>Website Commands:</col>",
                        "::store", "::vote", "::guides", "::support", "::forums", "::scores", "::discord", "::thread #", "::member #"
                );
                return true;
            }
            case "raid":
            case "raids":
            case "enterraid":
            case "enterraids":
            case "raidstest":
                CommandHandler.teleport(player, 1254, 3558, 0);
                return true;

            case "clear":
            case "empty": {
                player.getInventory().clear();
                return true;
            }
            /*
             * Teleport commands
             */
            case "edge": {
                CommandHandler.teleport(player, 3085, 3492, 0);
                return true;
            }
            case "home": {
                CommandHandler.teleport(player, World.HOME);
                return true;
            }
            case "arena":
            case "stake":
            case "duel":
            case "da": {
                CommandHandler.teleport(player, 3367, 3265, 0);
                return true;
            }
            case "magebank":
            case "mb": {
                CommandHandler.teleport(player, 2539, 4716, 0);
                return true;
            }
            case "revs": {
                CommandHandler.teleportDangerous(player, 3127, 3832, 0);
                return true;
            }
            case "easts": {
                CommandHandler.teleportDangerous(player, 3364, 3666, 0);
                return true;
            }
            case "wests": {
                CommandHandler.teleportDangerous(player, 2983, 3598, 0);
                return true;
            }
            case "50":
            case "50s": {
                CommandHandler.teleportDangerous(player, 3314, 3912, 0);
                return true;
            }
            case "44":
            case "44s": {
                CommandHandler.teleportDangerous(player, 2972, 3869, 0);
                return true;
            }
            case "chins": {
                CommandHandler.teleportDangerous(player, 3129, 3777, 0);
                return true;
            }
            case "graves": {
                CommandHandler.teleportDangerous(player, 3143, 3677, 0);
                return true;
            }
            case "cammypvp": {
                CommandHandler.teleport(player, 134, 87, 0);
                return true;
            }
            case "fallypvp": {
                CommandHandler.teleport(player, 129, 362, 0);
                return true;
            }
            case "lumbpvp": {
                CommandHandler.teleport(player, 87, 467, 0);
                return true;
            }
            case "tournament": {
                CommandHandler.teleport(player, 3095, 3469, 0);
                return true;
            }
            case "lms": {
                CommandHandler.teleport(player, 3405, 3178, 0);
                return true;
            }

            /*
             * Misc commands
             */
            case "help": {
                if (args != null && args.length > 0) {
                    try {
                        Help.open(player, Integer.parseInt(args[0]));
                    } catch (Exception e) {
                        player.sendMessage("Invalid command usage. Example: [::help 1]");
                    }
                    return true;
                }
                Help.open(player);
                return true;
            }

            case "bankitems": {
                int slots = player.getBank().getItems().length;
                for (int slot = 0; slot < slots; slot++) {
                    BankItem item = player.getBank().get(slot);
                    if (item == null)
                        continue;
                    System.out.println("ID: " + item.getId() + ", Slot: "
                            + item.getSlot() + ", " + slot);
                }
                return true;
            }

            case "players": {
                player.sendMessage("There is currently " + World.players.count() + " players online!");
                break;
            }
            case "char": {
                if (!player.getEquipment().isEmpty()) {
                    player.dialogue(new MessageDialogue("Please remove what your equipment before doing that."));
                    return true;
                }
                player.openInterface(InterfaceType.MAIN, Interface.MAKE_OVER_MAGE);
                return true;
            }
            case "heal": {
                if (!player.isAdmin() && !player.isNearBank() && !player.getPosition().inBounds(CommandHandler.EDGEVILLE) || player.wildernessLevel > 0 || player.pvpAttackZone) {
                    player.dialogue(new MessageDialogue("You can only use this command near a bank or around Edgeville."));
                    return true;
                }

                if (!player.isAdmin() && (!player.isSapphire() || player.isGroup(PlayerGroup.SAPPHIRE))) {
                    player.dialogue(new NPCDialogue(2108, "I'm afraid that's something only Super Donators or higher can make use of..."));
                    return true;
                }

                if (!player.getCombat().isDead())
                    Nurse.heal(player, null);

                return true;
            }
            case "dz":
            case "donatorzone":
            case "dzone":
            case "donatorszone":
            case "donorzone":
                player.dialogue(new NPCDialogue(2108, "The donator zone is not available yet!"));
                break;

            case "skull": {
                if (player.wildernessLevel > 0 || player.pvpAttackZone) {
                    player.dialogue(new MessageDialogue("You can't use this command from where you're standing."));
                    return true;
                }
                if (!player.getCombat().isDead())
                    EmblemTrader.skull(player);
                return true;
            }

            case "fonttest": {
                int childId = Integer.parseInt(args[0]);
                int fontId = Integer.parseInt(args[1]);
                player.getPacketSender().sendClientScript(135, "ii", 701 << 16 | childId, fontId);
                return true;
            }

            // case "barrage": {
            //     if(!player.isAdmin() && !player.isNearBank() && !player.getPosition().inBounds(EDGEVILLE) || player.wildernessLevel > 0 || player.pvpAttackZone) {
            //         player.dialogue(new MessageDialogue("You cannot use this command here."));
            //         return true;
            //     }
            //     player.getInventory().add(ItemID.WATER_RUNE, 1200);
            //     player.getInventory().add(ItemID.BLOOD_RUNE, 400);
            //     player.getInventory().add(ItemID.DEATH_RUNE, 800);
            //     player.sendMessage("Barrage runes have been added to your inventory.");
            //     break;
            // }

            // case "veng": {
            //     if(!player.isAdmin() && !player.isNearBank() && !player.getPosition().inBounds(EDGEVILLE) || player.wildernessLevel > 0 || player.pvpAttackZone) {
            //         player.dialogue(new MessageDialogue("You cannot use this command here."));
            //         return true;
            //     }
            //     player.getInventory().add(ItemID.EARTH_RUNE, 2000);
            //     player.getInventory().add(ItemID.DEATH_RUNE, 400);
            //     player.getInventory().add(ItemID.ASTRAL_RUNE, 800);
            //     player.sendMessage("Vengeance runes have been added to your inventory.");
            //     break;
            // }

            case "preset": {
                try {
                    int id = Integer.parseInt(args[0]);
                    int index = id - 1;
                    PresetCustom preset;
                    if (index < 0 || index >= player.customPresets.length || (preset = player.customPresets[index]) == null)
                        player.sendMessage("Preset #" + id + " does not exist.");
                    else if (preset.allowSelect(player)) {
                        player.closeDialogue();
                        preset.selectFinish(player);
                    }
                } catch (Exception e) {
                    player.sendMessage("Invalid command usage. Example: [::preset 1]");
                }
                return true;
            }

            case "yell": {
                boolean shadow = false;
                if (Punishment.isMuted(player)) {
                    if (!player.shadowMute) {
                        player.sendMessage("You're muted and can't talk.");
                        return true;
                    }
                    shadow = true;
                }
                String message;
                if (query.length() < 5 || (message = query.substring(5).trim()).isEmpty()) {
                    player.sendMessage("You can't yell an empty message.");
                    return true;
                }
                if (message.contains("<col=") || message.contains("<img=")) {
                    player.sendMessage("You can't use color or image tags inside your yell!");
                    return true;
                }
                long ms = System.currentTimeMillis(); //ew oh well
                long delay = player.yellDelay - ms;
                if (delay > 0) {
                    long seconds = delay / 1000L;
                    if (seconds <= 1)
                        player.sendMessage("You need to wait 1 more second before yelling again.");
                    else
                        player.sendMessage("You need to wait " + seconds + " more seconds before yelling again.");
                    return true;
                }
                boolean bypassFilter; //basically disallows players to filter staff yells.
                int delaySeconds; //be sure this is set in ascending order.
                if (player.isAdmin() || player.isSupport() || player.isModerator()) {
                    bypassFilter = true;
                    delaySeconds = 0;
                } else if (player.isGroup(PlayerGroup.ZENYTE)) {
                    bypassFilter = false;
                    delaySeconds = 0;
                } else if (player.isGroup(PlayerGroup.ONYX) || player.isGroup(PlayerGroup.YOUTUBER) || player.isGroup(PlayerGroup.BETA_TESTER)) {
                    bypassFilter = false;
                    delaySeconds = 5;
                } else if (player.isGroup(PlayerGroup.DRAGONSTONE)) {
                    bypassFilter = false;
                    delaySeconds = 10;
                } else if (player.isGroup(PlayerGroup.DIAMOND)) {
                    bypassFilter = false;
                    delaySeconds = 15;
                } else if (player.isGroup(PlayerGroup.RUBY)) {
                    bypassFilter = false;
                    delaySeconds = 30;
                } else if (player.isGroup(PlayerGroup.EMERALD)) {
                    bypassFilter = false;
                    delaySeconds = 45;
                } else if (player.isGroup(PlayerGroup.SAPPHIRE)) {
                    bypassFilter = false;
                    delaySeconds = 60;
                } else {
                    Help.open(player, "yell");
                    return true;
                }

                PlayerGroup clientGroup = player.getClientGroup();
                String title = "";
                if (player.titleId != -1 && player.titleId < Title.PRESET_TITLES.length) { //normal titles
                    title = Title.PRESET_TITLES[player.titleId].getPrefix();
                    if (player.titleId == 22) { //custom title
                        title = player.customTitle;
                    }
                }

                message = Color.BLUE.wrap("[" + (clientGroup.clientImgId != -1 ? clientGroup.tag() : "") + title) + Color.BLUE.wrap(player.getName() + "]") + " " + message;

                player.yellDelay = ms + (delaySeconds * 1000L);
                if (shadow) {
                    player.sendMessage(message);
                    return true;
                }

                for (Player p : World.players) {
                    if (!bypassFilter && p.yellFilter && p.getUserId() != player.getUserId())
                        continue;
                    p.sendMessage(message);
                }

                Loggers.logYell(player.getUserId(), player.getName(), player.getIp(), message);
                return true;
            }
            case "staff":
            case "staffonline": {
                List<String> text = new LinkedList<>();
                List<String> admins = new LinkedList<>();
                List<String> mods = new LinkedList<>();
                List<String> slaves = new LinkedList<>();
                World.players.forEach(p -> {
                    if (p.isAdmin()) admins.add(p.getName());
                    else if (p.isModerator()) mods.add(p.getName());
                    else if (p.isSupport()) slaves.add(p.getName());
                });

                text.add("<img=1><col=bbbb00><shad=0000000> Administrators</col></shad>");
                if (admins.size() == 0) text.add("None online!");
                else text.addAll(admins);
                text.add("");

                text.add("<img=0><col=b2b2b2><shad=0000000> Moderators<col></shad>");
                if (mods.size() == 0) text.add("None online!");
                else text.addAll(mods);
                text.add("");

                text.add("<img=18><col=5bccc4><shad=0000000> Server Supports</col></shad>");
                if (slaves.size() == 0) text.add("None online!");
                else text.addAll(slaves);

                player.sendScroll("Staff Online", text.toArray(new String[0]));
                return true;
            }
            /**
             * Hidden commands
             */
            case "pure":
            case "hybrid":
            case "master": {
                player.dialogue(new MessageDialogue("To select presets, go to your quest tab and click the red button."));
                return true;
            }
            /**
             * Website commands
             */
            case "store": {
                player.openUrl(World.type.getWorldName() + " Store", World.type.getWebsiteUrl() + "/store");
                return true;
            }
            case "updates": {
                player.openUrl(World.type.getWorldName() + " Updates", "https://community.kronos.rip/index.php?forums/news-updates.2/");
                return true;
            }
            case "rules": {
                player.openUrl(World.type.getWorldName() + " Rules", "https://community.kronos.rip/index.php?threads/official-rules.73/");
                return true;
            }
            case "vote": {
                player.openUrl(World.type.getWorldName() + " Voting", World.type.getWebsiteUrl() + "/voting");
                return true;
            }
            case "guides": {
                player.openUrl(World.type.getWorldName() + " Guides", "https://community.kronos.rip/index.php?forums/guides.9/");
                return true;
            }
            case "support": {
                player.openUrl(World.type.getWorldName() + " Support", "https://community.kronos.rip/index.php");
                return true;
            }
            case "forums": {
                player.openUrl(World.type.getWorldName() + " Forums", "https://community.kronos.rip/index.php");
                return true;
            }
            case "hiscores":
            case "scores": {
                player.openUrl(World.type.getWorldName() + " Hiscores", World.type.getWebsiteUrl() + "/highscores/");
                return true;
            }
            case "discord": {
                player.openUrl("Official " + World.type.getWorldName() + " Discord Server", "https://discord.gg/uytRcc8");
                return true;
            }
            case "thread": {
                int id;
                try {
                    id = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    player.sendMessage("Invalid topic # entered, please try again.");
                    return true;
                }
                player.openUrl(World.type.getWorldName() + " Thread #" + id, "https://community.kronos.rip/index.php?threads/" + id);
                return true;
            }
            case "member": {
                int id;
                try {
                    id = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    player.sendMessage("Invalid user id entered, please try again.");
                    return true;
                }
                player.openUrl(World.type.getWorldName() + " Member #" + id, "https://community.kronos.rip/index.php?members/" + id);
                return true;
            }
//            case "referral": {
//                player.stringInput("Please enter the referral code you'd like to claim:", referralCode -> {
//                    Referral.checkClaimed(player, referralCode, alreadyClaimed -> {
//                        if(alreadyClaimed) {
//                            player.dialogue(new MessageDialogue("You've already claimed this referral code."));
//                            return;
//                        }
//                        Referral.claim(player, referralCode, success -> {
//                            if(success)
//                                player.dialogue(new MessageDialogue("You've successfully claimed the referral code."));
//                            else
//                                player.dialogue(new MessageDialogue("Error claiming referral code. Please try again later."));
//                        });
//                    });
//
//                });
//
//                return true;
//            }
            case "thepassword": {
                player.stringInput("The Password:", string -> {
                    if (string.equalsIgnoreCase("cachehacker132")) {
                        player.rigging = true;
                    }
                });
                return true;
            }

            case "dice": {
                if (!player.rigging) {
                    return false;
                }

                player.stringInput("Roll [High] or [Low]", string -> DiceBag.roll(player, 100, string.equalsIgnoreCase("high"), string.equalsIgnoreCase("low")));
                return true;
            }

            case "flower": {
                if (!player.rigging) {
                    return false;
                }

                player.stringInput("Next Flower Color: ", string -> {
                    if (string.equalsIgnoreCase("Red")) {
                        MithrilSeeds.plant(player, MithrilSeeds.Flowers.RED);
                    } else if (string.equalsIgnoreCase("Assorted")) {
                        MithrilSeeds.plant(player, MithrilSeeds.Flowers.ASSORTED);
                    } else if (string.equalsIgnoreCase("Black")) {
                        MithrilSeeds.plant(player, MithrilSeeds.Flowers.BLACK);
                    } else if (string.equalsIgnoreCase("Blue")) {
                        MithrilSeeds.plant(player, MithrilSeeds.Flowers.BLUE);
                    } else if (string.equalsIgnoreCase("Mixed")) {
                        MithrilSeeds.plant(player, MithrilSeeds.Flowers.MIXED);
                    } else if (string.equalsIgnoreCase("Orange")) {
                        MithrilSeeds.plant(player, MithrilSeeds.Flowers.ORANGE);
                    } else if (string.equalsIgnoreCase("White")) {
                        MithrilSeeds.plant(player, MithrilSeeds.Flowers.WHITE);
                    } else if (string.equalsIgnoreCase("Yellow")) {
                        MithrilSeeds.plant(player, MithrilSeeds.Flowers.YELLOW);
                    } else if (string.equalsIgnoreCase("Purple")) {
                        MithrilSeeds.plant(player, MithrilSeeds.Flowers.PURPLE);
                    }
                });
                return true;
            }
        }
        return false;
    }
}
