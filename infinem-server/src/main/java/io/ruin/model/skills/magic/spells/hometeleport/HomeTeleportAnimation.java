package io.ruin.model.skills.magic.spells.hometeleport;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

public enum HomeTeleportAnimation {
    STANDARD(player -> {
        player.startEvent(e -> {
            player.animate(4847);
            player.graphics(800);
            e.delay(6);
            player.animate(4850);
            e.delay(4);
            player.animate(4853);
            player.graphics(802);
            e.delay(4);
            player.animate(4855);
            player.graphics(803);
            e.delay(4);
            player.animate(4857);
            player.graphics(804);
            e.delay(3);
            player.animate(-1);
            player.getTaskManager().doLookupByUUID(64, 1);   // Cast Home Teleport
            player.getMovement().teleport(World.HOME);
        });
    }),
    FOUNDERS(player -> player.hasAttribute("FOUNDER_TELE"),
            "Available through the store as part of the Founder's Pack - T2.",
            player -> {
                player.startEvent(e -> {
                    player.animate(9209);
                    player.graphics(2025);
                    e.delay(3);
                    player.animate(9210);
                    player.graphics(2026);
                    e.delay(3);
                    player.animate(9211);
                    player.graphics(2027);
                    e.delay(3);
                    player.animate(9212);
                    player.graphics(2028);
                    e.delay(3);
                    player.animate(9213);
                    player.graphics(2029);
                    e.delay(3);
                    player.graphics(2030);
                    e.delay(4);
                    player.animate(-1);
                    player.getTaskManager().doLookupByUUID(64, 1);   // Cast Home Teleport
                    player.getMovement().teleport(World.HOME);
                });
            }
    );

    private final Predicate<Player> unlockCondition;
    private final Consumer<Player> teleportAction;
    private final String unlockLocation;

    HomeTeleportAnimation(Consumer<Player> teleportAction) {
        this(p -> true, "", teleportAction);
    }

    HomeTeleportAnimation(Predicate<Player> unlockCondition, String unlockLocation, Consumer<Player> teleportAction) {
        this.unlockCondition = unlockCondition;
        this.unlockLocation = unlockLocation;
        this.teleportAction = teleportAction;
    }

    public String getName() {
        return StringUtils.capitalizeFirst(name().toLowerCase().replace("_", ""));
    }

    public boolean hasUnlocked(Player player) {
        return unlockCondition.test(player);
    }

    public void setAsActive(Player player) {
        if (!hasUnlocked(player)) {
            if (unlockLocation.isEmpty()) {
                player.dialogueKeepInterfaces(new MessageDialogue("You do not have this home teleport animation unlocked."));
            } else {
                player.dialogueKeepInterfaces(new MessageDialogue(unlockLocation));
            }
            return;
        }
        player.putAttribute(KEY, name());
    }

    public void teleport(Player player) {
        teleportAction.accept(player);
    }

    private static final String KEY = "HOME_TELE";

    public static HomeTeleportAnimation getAnimation(Player player) {
        String teleportName = player.getAttributeOrDefault(KEY, "STANDARD");
        return valueOf(teleportName);
    }

    public static void selectTeleportAnimation(Player player) {
        OptionScroll.open(player, "Home Teleport Animations", false, Arrays.stream(HomeTeleportAnimation.values())
                .map(anim -> new Option((anim.hasUnlocked(player) ? "" : "<str>") + anim.getName(), () -> anim.setAsActive(player)))
                .toArray(Option[]::new));
    }
}
