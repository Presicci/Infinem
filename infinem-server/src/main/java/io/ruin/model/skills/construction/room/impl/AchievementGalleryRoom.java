package io.ruin.model.skills.construction.room.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.MaxCapeVariants;
import io.ruin.model.item.actions.impl.skillcapes.*;
import io.ruin.model.item.actions.impl.teleport.MythicalCape;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.skills.construction.room.Room;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.ruin.model.item.actions.impl.MaxCapeVariants.MaxCapes.*;
import static io.ruin.model.skills.construction.Buildable.CAPE_HANGER;

public class AchievementGalleryRoom extends Room {

    /* Boss display */

    static {
        Buildable.BOSS_DISPLAY.setRemoveTest((player, room) -> {
           if (room instanceof AchievementGalleryRoom) {
               return ((AchievementGalleryRoom) room).removeBossLairDisplay(player);
           }
           return true;
        });
    }

    enum BossDisplay {
        KRAKEN(29158, 12007),
        ZULRAH(29159, 12936),
        KALPHITE_QUEEN(29160, 12885),
        CERBERUS(29161, 13245),
        ABYSSAL_SIRE(29162, 13277),
        SKOTIZO(29163, 19701),
        GROTESQUE_GUARDIANS(31689, 21745),
        VORKATH(31978, 22106),
        ALCHEMICAL_HYDRA(34653, 23064),
        SARACHNIS(34833, 23525),
        NIGHTMARE(37629, 24495),
        CORPOREAL_BEAST(41017, 25521),
        THERMONUCLEAR_SMOKE_DEVIL(41018, 25524);

        private final int objectId;
        private final int jarId;

        BossDisplay(int objectId, int jarId) {
            this.objectId = objectId;
            this.jarId = jarId;
        }

        static BossDisplay find(int jarId) {
            for (BossDisplay d : values()) {
                if (d.jarId == jarId)
                    return d;
            }
            return null;
        }

    }

    @Expose private BossDisplay bossDisplayed;

    @Expose private boolean[] storedJars = new boolean[BossDisplay.values().length];

    @Override
    protected void onBuild() {
        renderBossDisplay();
        getHotspotObjects(Hotspot.BOSS_LAIR_DISPLAY).forEach(obj -> {
            System.out.println(obj.id);
            ObjectAction.register(obj, 1, this::configureBossDisplay);
            ObjectAction.register(obj, 2, this::viewJars);
            ItemObjectAction.register(obj, this::addJar);
        });
        renderCapeDisplay();
    }

    private void renderCapeDisplay() {
        if (hasBuildable(CAPE_HANGER)) {
            getHotspotObjects(Hotspot.DISPLAY).forEach(obj -> obj.setId(cape != null ? cape.objId : CAPE_HANGER.getBuiltObjects()[0]));
        }
    }

    private void renderBossDisplay() {
        if (!hasBuildable(Buildable.BOSS_DISPLAY))
            return;
        if (storedJars.length < BossDisplay.values().length)
            storedJars = Arrays.copyOf(storedJars, BossDisplay.values().length);
        if (bossDisplayed != null) {
            getHotspotObjects(Hotspot.BOSS_LAIR_DISPLAY).forEach(obj -> obj.setId(bossDisplayed.objectId));
        } else {
            getHotspotObjects(Hotspot.BOSS_LAIR_DISPLAY).forEach(obj -> obj.setId(Buildable.BOSS_DISPLAY.getBuiltObjects()[0]));
        }
    }

    private void addJar(Player player, Item item, GameObject gameObject) {
        BossDisplay type = BossDisplay.find(item.getId());
        if (type == null) {
            player.sendMessage("Nothing interesting happens.");
            return;
        }
        if (storedJars[type.ordinal()]) {
            player.sendMessage("This display already has a jar of that type stored in it.");
            return;
        }
        item.remove();
        storedJars[type.ordinal()] = true;
        player.animate(833);
        player.sendMessage("You add the jar to the display.");
        bossDisplayed = type;
        player.getTaskManager().doLookupByUUID(307, 1); // Add a Jar to a Display Case
        renderBossDisplay();
    }

    private void configureBossDisplay(Player player, GameObject gameObject) {
        if (player != getHouse().getOwner()) {
            player.sendMessage("Only the house owner can do that.");
            return;
        }
        if (jarCount() == 0) {
            player.dialogue(new MessageDialogue("You haven't stored any jars in the display. Use jars on the display to store them."));
            return;
        }
        openJarSelection(player, selected -> {
            bossDisplayed = selected;
            renderBossDisplay();
        });

    }

    private int jarCount() {
        int count = 0;
        for (boolean b : storedJars)
            if (b) count++;
        return count;
    }

    private void viewJars(Player player, GameObject gameObject) {
        if (player != getHouse().getOwner()) {
            player.sendMessage("Only the house owner can do that.");
            return;
        }
        if (jarCount() == 0) {
            player.dialogue(new MessageDialogue("You haven't stored any jars in the display. Use jars on the display to store them."));
            return;
        }
        openJarSelection(player, selected -> {
            if (selected != null) {
                if (player.getInventory().isFull()) {
                    player.sendMessage("Not enough space in your inventory.");
                    return;
                }
                player.getInventory().add(selected.jarId, 1);
                if (selected == bossDisplayed) {
                    bossDisplayed = null;
                    onBuild();
                }
                storedJars[selected.ordinal()] = false;
                player.sendMessage("You take the jar back.");
            }
        });
    }

    private void openJarSelection(Player player, Consumer<BossDisplay> callback) {
        List<Option> options = Arrays.stream(BossDisplay.values())
                .filter(d -> storedJars[d.ordinal()])
                .map(d -> new Option(StringUtils.getFormattedEnumName(d), () -> callback.accept(d)))
                .collect(Collectors.toList());
        options.add(new Option("None", () -> callback.accept(null)));
        OptionScroll.open(player, "Select a boss", false, options);
    }

    private boolean removeBossLairDisplay(Player player) {
        int jars = jarCount();
        if (jars > 0) {
            player.dialogue(new MessageDialogue("You must remove all the jars from this display before you can remove it."));
            return false;
        }
        return true;
    }

    /* Cape hanger */

    public enum Cape {
        FIRE_CAPE(29169, 6570),
        INFERNAL_CAPE(26713, 21295),
        MYTHICAL_CAPE(31983, player -> MythicalCape.teleport(player), 22114),
        CHAMPIONS_CAPE(30403, 21439),
        XERIC_GUARD(32537, 22388),
        XERIC_WARRIOR(32538, 22390),
        XERIC_SENTINEL(32539, 22392),
        XERIC_GENERAL(32540, 22394),
        XERIC_CHAMPION(32541, 22396),

        ACHIVEMENT_DIARY(29167, 19476, 13070),
        ACHIVEMENT_DIARY_T(29168, 13069, 13070),
        QUEST_POINT(29178, 9813, 9814),
        QUEST_POINT_T(29179, 13068, 9814),
        MUSIC(29176, 13221, 13223),
        MUSIC_T(29177, 13222, 13223),

        MAX(29170, player -> MaxCape.selectPerk(player), 13342, 13281),
        INFERNAL_MAX(26714, player -> MaxCape.selectPerk(player), INFERNAL),
        FIRE_MAX(29171, player -> MaxCape.selectPerk(player), FIRE),
        SARADOMIN_MAX(29172, player -> MaxCape.selectPerk(player), SARADOMIN),
        ZAMORAK_MAX(29173, player -> MaxCape.selectPerk(player), ZAMORAK),
        GUTHIX_MAX(29174, player -> MaxCape.selectPerk(player), GUTHIX),
        AVAS_ACCUMULATOR_MAX(29175, player -> MaxCape.selectPerk(player), AVA),
        ARDOUGNE_MAX(29625, player -> MaxCape.selectPerk(player), ARDOUGNE),
        SARADOMIN_IMBUED_MAX(31979, player -> MaxCape.selectPerk(player), SARADOMIN_IMBUED),
        ZAMORAK_IMBUED_MAX(31980, player -> MaxCape.selectPerk(player), ZAMORAK_IMBUED),
        GUTHIX_IMBUED_MAX(31981, player -> MaxCape.selectPerk(player), GUTHIX_IMBUED),
        AVAS_ASSEMBLER_MAX(31982, player -> MaxCape.selectPerk(player), ASSEMBLER),

        ATTACK(29182, 9747, 9749),
        ATTACK_TRIMMED(29183, 9748, 9749),
        DEFENCE(29190, 9753, 9755),
        DEFENCE_TRIMMED(29191, 9754, 9755),
        STRENGTH(29220, player -> StrengthSkillCape.teleport(player), 9750, 9752),
        STRENGTH_TRIMMED(29221, player -> StrengthSkillCape.teleport(player), 9751, 9752),
        HITPOINTS(29202, 9768, 9770),
        HITPOINTS_TRIMMED(29203, 9769, 9770),
        RANGED(29212, 9756, 9758),
        RANGED_TRIMMED(29213, 9757, 9758),
        PRAYER(29210, 9759, 9761),
        PRAYER_TRIMMED(29211, 9760, 9761),
        MAGIC(29206, player -> MagicSkillcape.swapSelection(player), 9762, 9764),
        MAGIC_TRIMMED(29207, player -> MagicSkillcape.swapSelection(player), 9763, 9764),
        COOKING(29186, 9801, 9803),
        COOKING_TRIMMED(29187, 9802, 9803),
        WOODCUTTING(29224, 9807, 9809),
        WOODCUTTING_TRIMMED(29225, 9808, 9809),
        FLETCHING(29198, player -> FletchingSkillCape.search(player), 9783, 9785),
        FLETCHING_TRIMMED(29199, player -> FletchingSkillCape.search(player), 9784, 9785),
        FISHING(29196, player -> FishingSkillCape.teleport(player), 9798, 9800),
        FISHING_TRIMMED(29197, player -> FishingSkillCape.teleport(player), 9799, 9800),
        FIREMAKING(29194, 9804, 9806),
        FIREMAKING_TRIMMED(29195, 9805, 9806),
        CRAFTING(29188, player -> CraftingSkillCape.teleport(player), 9780, 9782),
        CRAFTING_TRIMMED(29189, player -> CraftingSkillCape.teleport(player), 9781, 9782),
        SMITHING(29218, 9795, 9797),
        SMITHING_TRIMMED(29219, 9796, 9797),
        MINING(29208, 9792, 9794),
        MINING_TRIMMED(29209, 9793, 9794),
        HERBLORE(29200, player -> HerbloreSkillCape.search(player), 9774, 9776),
        HERBLORE_TRIMMED(29201, player -> HerbloreSkillCape.search(player), 9775, 9776),
        AGILITY(29180, 9771, 9773),
        AGILITY_TRIMMED(29181, 9772, 9773),
        THIEVING(29222, 9777, 9779),
        THIEVING_TRIMMED(29223, 9778, 9779),
        SLAYER(29216, 9786, 9788),
        SLAYER_TRIMMED(29217, 9787, 9788),
        FARMING(29192, player -> FarmingSkillCape.teleport(player), 9810, 9812),
        FARMING_TRIMMED(29193, player -> FarmingSkillCape.teleport(player), 9811, 9812),
        RUNECRAFTING(29214, 9765, 9767),
        RUNECRAFTING_TRIMMED(29215, 9766, 9767),
        CONSTRUCTION(29184, player -> ConstructionSkillCape.selectTeleport(player), 9789, 9791),
        CONSTRUCTION_TRIMMED(29185, player -> ConstructionSkillCape.selectTeleport(player), 9790, 9791),
        HUNTER(29204, player -> HunterSkillCape.teleport(player), 9948, 9950),
        HUNTER_TRIMMED(29205, player -> HunterSkillCape.teleport(player), 9949, 9950);

        private final int objId;
        private final int[] items;
        private final Consumer<Player> action;

        Cape(int objId, Consumer<Player> action, int... items) {
            this.objId = objId;
            this.action = action;
            this.items = items;
        }

        Cape(int objId, int... items) {
            this(objId, null, items);
        }

        Cape(int objId, MaxCapeVariants.MaxCapes maxCapes) {
            this(objId, maxCapes.newCapeId, maxCapes.newHoodId);
        }

        Cape(int objId, Consumer<Player> action, MaxCapeVariants.MaxCapes maxCapes) {
            this(objId, action, maxCapes.newCapeId, maxCapes.newHoodId);
        }

        static {
            for (Cape cape : values()) {
                for (int id : cape.items) {
                    ItemObjectAction.register(id, CAPE_HANGER.getBuiltObjects()[0], (player, item, obj) -> {
                        if (!player.isInOwnHouse()) {
                            player.sendMessage("Only the house owner can do that.");
                            return;
                        }
                        Room room = player.getCurrentRoom();
                        if (room instanceof AchievementGalleryRoom) {
                            AchievementGalleryRoom gallery = (AchievementGalleryRoom) room;
                            gallery.mount(player, cape);
                        }
                    });
                    ObjectAction.register(cape.objId, "take", Construction.forHouseOwnerOnly((player, house) -> {
                        Room room = player.getCurrentRoom();
                        if (room instanceof AchievementGalleryRoom) {
                            ((AchievementGalleryRoom) room).unmount(player);
                        }
                    }));
                    ObjectAction.register(cape.objId, "admire", (player, obj) -> player.animate(6937));
                    ObjectAction.register(cape.objId, 2, (player, obj) -> {
                        if (cape.action != null) cape.action.accept(player);
                    });
                }
            }
        }
    }

    @Expose Cape cape = null;

    private void mount(Player player, Cape cape) {
        if (getHouse().getOwner() != player)
            return;
        for (int id : cape.items) {
            if (!player.getInventory().hasId(id)) {
                player.sendMessage("You will need both the cape and the matching hood to do that.");
                return;
            }
        }
        for (int id : cape.items) {
            player.getInventory().remove(id, 1);
        }
        this.cape = cape;
        player.animate(833);
        player.sendMessage("You hang the cape.");
        onBuild();
    }

    private boolean unmount(Player player) {
        if (cape == null || !hasBuildable(CAPE_HANGER))
            return true;
        if (!player.getInventory().hasFreeSlots(cape.items.length)) {
            player.sendMessage("You'll need at least " + cape.items.length + " free inventory spaces retrieve the cape.");
            return false;
        }
        for (int id : cape.items) {
            player.getInventory().add(id, 1);
        }
        cape = null;
        renderCapeDisplay();
        return true;
    }

    static {
        CAPE_HANGER.setRemoveTest((player, room) -> {
            if (room instanceof AchievementGalleryRoom) {
                return ((AchievementGalleryRoom) room).unmount(player);
            }
            return true;
        });
    }

}
