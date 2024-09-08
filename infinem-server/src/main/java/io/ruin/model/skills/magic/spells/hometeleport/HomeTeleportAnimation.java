package io.ruin.model.skills.magic.spells.hometeleport;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

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
    FOUNDERS(26500,
            player -> player.hasAttribute("FOUNDER_TELE"),
            player -> player.putAttribute("FOUNDER_TELE", 1),
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
    private final Consumer<Player> teleportAction, unlockAction;
    private final String unlockLocation;
    private final int unlockItem;

    HomeTeleportAnimation(Consumer<Player> teleportAction) {
        this(p -> true, null, "", teleportAction);
    }

    HomeTeleportAnimation(Predicate<Player> unlockCondition, Consumer<Player> unlockAction, String unlockLocation, Consumer<Player> teleportAction) {
        this(-1, unlockCondition, unlockAction, unlockLocation, teleportAction);
    }

    HomeTeleportAnimation(int unlockItem, Predicate<Player> unlockCondition, Consumer<Player> unlockAction, String unlockLocation, Consumer<Player> teleportAction) {
        this.unlockItem = unlockItem;
        this.unlockCondition = unlockCondition;
        this.unlockAction = unlockAction;
        this.unlockLocation = unlockLocation;
        this.teleportAction = teleportAction;
    }

    public String getName() {
        return StringUtils.capitalizeFirst(name().toLowerCase().replace("_", ""));
    }

    public boolean hasUnlocked(Player player) {
        return unlockCondition.test(player);
    }

    public void unlock(Player player) {
        if (unlockAction == null) return;
        unlockAction.accept(player);
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

    static {
        for (HomeTeleportAnimation anim : values()) {
            if (anim.unlockItem > 0) {
                ItemAction.registerInventory(anim.unlockItem, "read", (player, item) -> {
                    if (anim.hasUnlocked(player)) {
                        player.dialogue(new MessageDialogue("You already have this home teleport animation unlocked. You can activate it by right-clicking the home teleport spell in your spellbook."));
                        return;
                    }
                    item.remove();
                    anim.unlockAction.accept(player);
                    player.dialogue(new MessageDialogue("You've unlocked the " + anim.getName() + " home teleport animation. You can activate it by right-clicking the home teleport spell in your spellbook."));
                });
            }
        }
    }
}
