package io.ruin.model.item.actions.impl.combine;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.slayer.SlayerMaster;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/16/2023
 */
public enum SlayerHelmVariant {
    BLACK(19639, Items.KBD_HEADS, Config.KING_BLACK_BONNET, 19641, 25179, 26675),
    GREEN(19643, Items.KQ_HEAD, Config.KALPHITE_KHAT, 19645, 25181, 26676),
    RED(19647, Items.ABYSSAL_HEAD, Config.UNHOLY_HELMET, 19649, 25183, 26677),
    PURPLE(21264, 21275, Config.DARK_MANTLE, 21266, 25185, 26678),
    TURQUOISE(21888, 2425, Config.UNDEAD_HEAD, 21890, 25187, 26679),
    HYDRA(23073, 23077, Config.USE_MORE_HEAD, 23075, 25189, 26680),
    TWISTED(24370, 24466, Config.TWISTED_VISION, 24444, 25191, 26681),
    TZTOK(25898, -1, null, 25900, 25902, 26682),
    VAMPYRIC(25904, -1, null, 25906, 25908, 26683),
    TZKAL(25910, -1, null, 25912, 25914, 26684);

    private final int helmId, requiredId;
    private final Config configRequirement;
    private final int[] imbuedIds;

    SlayerHelmVariant(int helmId, int requiredId, Config configRequirement, int... imbuedIds) {
        this.helmId = helmId;
        this.imbuedIds = imbuedIds;
        this.requiredId = requiredId;
        this.configRequirement = configRequirement;
    }

    private void colorize(Player player, Item helm, Item requiredItem) {
        if (configRequirement != null && configRequirement.get(player) != 1) {
            player.sendMessage("You need to unlock the ability to do this from a slayer master.");
            return;
        }
        if (!isSlayerHelm(helm)) {
            player.sendMessage("Nothing interesting happens.");
            return;
        }
        int oldId = helm.getId();
        int newId = oldId == 11864 ? helmId : oldId == 11865 ? imbuedIds[0] : oldId == 25177 ? imbuedIds[1] : imbuedIds[2];
        player.dialogue(
                new YesNoDialogue("Are you sure you want to recolour your Slayer helm?",
                        "You can disassemble the helm for your " + requiredItem.getDef().name + " back.",
                        newId, 1, () -> {
                    requiredItem.remove();
                    helm.setId(newId);
                })
        );
    }

    private void disassemble(Player player, Item helm, boolean imbued) {
        if (player.getInventory().getFreeSlots() < 1) {
            player.sendMessage("You need a free inventory slot to do this.");
            return;
        }
        helm.setId(imbued ? Items.SLAYER_HELMET_I : Items.SLAYER_HELMET);
        if (requiredId != -1) {
            player.getInventory().add(requiredId, 1);
        }
    }

    private static boolean isSlayerHelm(Item item) {
        return item.getId() == Items.SLAYER_HELMET || item.getId() == Items.SLAYER_HELMET_I || item.getId() == 25177 || item.getId() == 26674;
    }

    static {
        for (SlayerHelmVariant variant : SlayerHelmVariant.values()) {
            ItemAction.registerEquipment(variant.helmId, 4, (player, item) -> SlayerMaster.checkTask(player));
            ItemAction.registerEquipment(variant.helmId, "log", (player, item) -> KillCounter.openOwnSlayer(player));
            ItemAction.registerInventory(variant.helmId, "check", (player, item) -> SlayerMaster.checkTask(player));
            ItemAction.registerInventory(variant.helmId, "disassemble", (player, item) -> variant.disassemble(player, item, false));
            for (int imbuedHelmId : variant.imbuedIds) {
                ItemAction.registerEquipment(imbuedHelmId, 4, (player, item) -> SlayerMaster.checkTask(player));
                ItemAction.registerEquipment(imbuedHelmId, "log", (player, item) -> KillCounter.openOwnSlayer(player));
                ItemAction.registerInventory(imbuedHelmId, "check", (player, item) -> SlayerMaster.checkTask(player));
                ItemAction.registerInventory(imbuedHelmId, "disassemble", (player, item) -> variant.disassemble(player, item, true));
                ItemDefinition.get(imbuedHelmId).slayerBoostAll = true;
            }
            if (variant.requiredId != -1) {
                ItemItemAction.register(Items.SLAYER_HELMET, variant.requiredId, variant::colorize);
                ItemItemAction.register(Items.SLAYER_HELMET_I, variant.requiredId, variant::colorize);
                ItemItemAction.register(25177, variant.requiredId, variant::colorize);
                ItemItemAction.register(26674, variant.requiredId, variant::colorize);
            }
            ItemDefinition.get(variant.helmId).slayerBoostMelee = true;

        }
    }
}
