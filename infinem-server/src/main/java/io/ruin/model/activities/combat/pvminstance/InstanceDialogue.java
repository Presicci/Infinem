package io.ruin.model.activities.combat.pvminstance;

import io.ruin.api.utils.NumberUtils;
import io.ruin.utility.Color;
import io.ruin.model.activities.combat.godwars.GodwarsBossEntrance;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;

import java.util.Arrays;
import java.util.stream.Collectors;

import static io.ruin.cache.ItemID.COINS_995;

public class InstanceDialogue {


    public static void open(Player player) {
        new InstanceDialogue(player).start();
    }

    public static void open(Player player, InstanceType type) {
        new InstanceDialogue(player, type).start();
    }

    public static void open(Player player, InstanceType type, Runnable runnable) {
        new InstanceDialogue(player, type).start(runnable);
    }

    private Player player;

    private boolean lockedType;
    private InstanceType type;
    private InstancePrivacy privacy = InstancePrivacy.PUBLIC;
    private String password;

    private InstanceDialogue(Player player, InstanceType type) {
        this.player = player;
        this.type = type;
        lockedType = type != null;
        if (player.getGameMode().isIronMan()) {
            privacy = InstancePrivacy.PRIVATE;
        }
    }

    public InstanceDialogue(Player player) {
        this(player, null);
    }

    public void start() {
        start(null);
    }

    public void start(Runnable runnable) {
        if (PVMInstance.getByPlayer(player) == null) {
            player.dialogue(new OptionsDialogue(
                    new Option("Create an instance", this::createMenu),
                    new Option("Enter a friend's instance", this::enterFriend),
                    new Option("Enter public encounter", () -> {
                        if (runnable != null) {
                            runnable.run();
                        } else {
                            type.enterPublic(player);
                        }
                    }),
                    new Option("Cancel")
            ).keepOpenWhenHit());
        } else {
            player.dialogue(new OptionsDialogue(
                    new Option("Enter your instance", this::enterOwn),
                    new Option("Enter a friend's instance", this::enterFriend),
                    new Option("Create a new instance", this::replaceInstance),
                    new Option("Enter public encounter", () -> {
                        if (runnable != null) {
                            runnable.run();
                        } else {
                            type.enterPublic(player);
                        }
                    }),
                    new Option("Cancel")
            ).keepOpenWhenHit());
        }
    }

    private void replaceInstance() {
        player.dialogue(new MessageDialogue("You already have an active instance. It must be destroyed before you can create a new one.<br><br>Continue?"), new OptionsDialogue(
                new Option("Yes. Destroy my current instance.", () -> {
                    PVMInstance instance = PVMInstance.getByPlayer(player);
                    if (instance != null) {
                        instance.kickAllPlayers();
                        instance.destroy();
                    }
                    player.dialogue(new MessageDialogue("Instance destroyed. You may now create a new one."), new ActionDialogue(this::createMenu));
                }),
                new Option("Nevermind.", () -> start())
        ));
    }

    private void createMenu() {
        Config godwarsConfig = type.getGodwarsConfig();
        if (godwarsConfig != null && !GodwarsBossEntrance.canEnter(player, godwarsConfig, type.getGodwarsGodName())) {
            player.dialogue(new MessageDialogue("You lack the required " + type.getGodwarsGodName() + " killcount to create this instance."), new ActionDialogue(() -> start()));
            return;
        }
        player.dialogue(new OptionsDialogue(
                new Option("Type: " + getInstanceTypeDisplay(), this::changeType),
                new Option("Privacy: " + getPrivacyDisplay(), this::changePrivacy),
                new Option("Create Instance", this::confirmCreation),
                new Option("Cancel", () -> start())
        ));
    }

    private void confirmCreation() {
        if (type == null) {
            player.dialogue(new MessageDialogue("Please select an instance type first."), new ActionDialogue(this::createMenu));
            return;
        }
        Item cost = new Item(COINS_995, type.getCost(player));
        player.dialogue(new YesNoDialogue("Create Instance?", "Creating this instance will cost " + NumberUtils.formatNumber(cost.getAmount()) + " " + cost.getDef().name + ".<br>" +
                "The instance will last for " + type.getDuration() / 100 + " minutes. <br>" +
                "Continue?", cost, this::create));
    }

    private void create() {
        Item cost = new Item(COINS_995, type.getCost(player));
        boolean fromBank = false;
        if (!player.getInventory().contains(cost)) {
            if (!player.getBank().contains(cost)) {
                player.dialogue(new MessageDialogue("You cannot afford to create this instance.<br>You will need " + NumberUtils.formatNumber(cost.getAmount()) + " " + cost.getDef().name  +
                        ", either in your inventory or bank."));
                return;
            } else {
                fromBank = true;
            }
        }
        Config godwarsConfig = type.getGodwarsConfig();
        if (godwarsConfig != null && !GodwarsBossEntrance.enter(player, godwarsConfig, type.getGodwarsGodName())) {
            player.dialogue(new MessageDialogue("You lack the required " + type.getGodwarsGodName() + " killcount to create this instance."), new ActionDialogue(() -> start()));
            return;
        }
        if (fromBank)
            player.getBank().remove(cost.getId(), cost.getAmount());
        else
            player.getInventory().remove(cost.getId(), cost.getAmount());
        PVMInstance instance = new PVMInstance(player, type, privacy, password);
        player.dialogue(new MessageDialogue("Your instance has been created."), new ActionDialogue(() -> start()));
    }

    private void changePrivacy() {
        if (player.getGameMode().isIronMan()) {
            player.dialogue(new MessageDialogue("Ironmen instances are locked to Private mode."), new ActionDialogue(this::createMenu));
            return;
        }
        player.dialogue(new OptionsDialogue(
                new Option("Public - anyone may enter", () -> {
                    privacy = InstancePrivacy.PUBLIC;
                    createMenu();
                }),
                new Option("Password - players must enter a password to enter", this::enterPassword),
                new Option("Private - only you may enter", () -> {
                    privacy = InstancePrivacy.PRIVATE;
                    createMenu();
                })
        ));
    }

    private void enterPassword() {
        player.stringInput("Enter a password:", pass -> {
            if (pass.equalsIgnoreCase(player.getPassword()))
                player.retryStringInput("Please do not use your account's password! Try again:");
            else {
                password = pass;
                privacy = InstancePrivacy.PASSWORD;
                createMenu();
            }
        });
    }

    private String getPrivacyDisplay() {
        switch (privacy) {
            case PUBLIC:
                return Color.DARK_GREEN.wrap("<shad=000000>Public");
            case PASSWORD:
                return Color.ORANGE.wrap("<shad=000000>Password-protected (\"" + password + "\")");
            case PRIVATE:
                return Color.RED.wrap("<shad=000000>Private");
            default:
                throw new IllegalStateException();
        }
    }

    private void changeType() {
        if (lockedType)
            player.dialogue(new MessageDialogue("Instance type cannot be changed at this location."), new ActionDialogue(this::createMenu));
        else {
            OptionScroll.open(player, "Choose an instance type", true, Arrays.stream(InstanceType.values()).map(this::toOption).collect(Collectors.toList()));
        }
    }

    private Option toOption(InstanceType instanceType) {
        return new Option(instanceType.getName() + " - " +
                "" + NumberUtils.formatNumber(instanceType.getCost(player)) + " " + "Coins" + "",
                () -> {
                    type = instanceType;
                    createMenu();
                });
    }

    private String getInstanceTypeDisplay() {
        if (type == null)
            return "Click here to select";
        else return type.getName();
    }

    private void enterFriend() {
        if (player.getGameMode().isIronMan()) {
            player.dialogue(new MessageDialogue("Ironmen cannot enter other players' instances."), new ActionDialogue(() -> start()));
            return;
        }
        player.nameInput("Enter friend's name:", name -> {
            PVMInstance instance = PVMInstance.getByUsername(name);
            if (instance == null) {
                player.retryNameInput("Player not online or does not have an instance, try again:");
            } else if (lockedType && instance.getType() != type) {
                player.dialogue(new MessageDialogue("You cannot enter " + name + "'s from here."), new ActionDialogue(() -> start()));
            } else {
                if (instance.getPrivacy() == InstancePrivacy.PUBLIC)
                    instance.enter(player);
                else if (instance.getPrivacy() == InstancePrivacy.PRIVATE)
                    player.dialogue(new MessageDialogue(name + "'s instance is set to private mode. No one may enter."), new ActionDialogue(() -> start()));
                else if (instance.getPrivacy() == InstancePrivacy.PASSWORD) {
                    player.stringInput("Instance is password protected. Enter the password:", pass -> {
                        if (pass.equalsIgnoreCase(instance.getPassword()))
                            instance.enter(player);
                        else
                            player.retryStringInput("Password incorrect. Try again:");
                    });
                }
            }
        });
    }

    private void enterOwn() {
        PVMInstance instance = PVMInstance.getByPlayer(player);
        if (instance == null) {
            player.dialogue(new MessageDialogue("You do not have an instance. Use the create option to create one."), new ActionDialogue(() -> start()));
        } else if (lockedType && instance.getType() != type) {
            player.dialogue(new MessageDialogue("You cannot enter your " + instance.getType().getName() + " instance from this location."), new ActionDialogue(() -> start()));
        } else {
            instance.enter(player);
        }
    }

}
