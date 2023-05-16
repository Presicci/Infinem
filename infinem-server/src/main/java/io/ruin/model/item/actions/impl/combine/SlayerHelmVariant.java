package io.ruin.model.item.actions.impl.combine;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.KillCounter;
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
@AllArgsConstructor
public enum SlayerHelmVariant {
    BLACK(19639, 19641, Items.KBD_HEADS, Config.KING_BLACK_BONNET),
    GREEN(19643, 19645, Items.KQ_HEAD, Config.KALPHITE_KHAT),
    RED(19647, 19649, Items.ABYSSAL_HEAD, Config.UNHOLY_HELMET),
    PURPLE(21264, 21266, 21275, Config.DARK_MANTLE),
    TURQUOISE(21888, 21890, 2425, Config.UNDEAD_HEAD),
    HYDRA(23073, 23075, 23077, Config.USE_MORE_HEAD),
    TWISTED(24370, 24444, 24466, Config.TWISTED_VISION),
    TZTOK(25898, 25900, -1, null),
    VAMPYRIC(25904, 25906, -1, null),
    TZKAL(25910, 25912, -1, null);

    private final int helmId, imbuedHelmId, requiredId;
    private final Config configRequirement;

    private void colorize(Player player, Item helm, Item requiredItem, boolean imbued) {
        if (configRequirement != null && configRequirement.get(player) != 1) {
            player.sendMessage("You need to unlock the ability to do this from a slayer master.");
            return;
        }
        if (!isSlayerHelm(helm)) {
            player.sendMessage("Nothing interesting happens.");
            return;
        }
        player.dialogue(
                new YesNoDialogue("Are you sure you want to recolour your Slayer helm?",
                        "You can disassemble the helm for your " + requiredItem.getDef().name + " back.",
                        imbued ? imbuedHelmId : helmId, 1, () -> {
                    requiredItem.remove();
                    helm.setId(imbued ? imbuedHelmId : helmId);
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
        return item.getId() == Items.SLAYER_HELMET || item.getId() == Items.SLAYER_HELMET_I;
    }

    static {
        for (SlayerHelmVariant variant : SlayerHelmVariant.values()) {
            ItemAction.registerEquipment(variant.helmId, 4, (player, item) -> SlayerMaster.checkTask(player));
            ItemAction.registerEquipment(variant.helmId, "log", (player, item) -> KillCounter.openOwnSlayer(player));
            ItemAction.registerInventory(variant.helmId, "check", (player, item) -> SlayerMaster.checkTask(player));
            ItemAction.registerInventory(variant.helmId, "disassemble", (player, item) -> variant.disassemble(player, item, false));
            ItemAction.registerEquipment(variant.imbuedHelmId, 4, (player, item) -> SlayerMaster.checkTask(player));
            ItemAction.registerEquipment(variant.imbuedHelmId, "log", (player, item) -> KillCounter.openOwnSlayer(player));
            ItemAction.registerInventory(variant.imbuedHelmId, "check", (player, item) -> SlayerMaster.checkTask(player));
            ItemAction.registerInventory(variant.imbuedHelmId, "disassemble", (player, item) -> variant.disassemble(player, item, true));
            if (variant.requiredId != -1) {
                ItemItemAction.register(Items.SLAYER_HELMET, variant.requiredId, ((player, primary, secondary) -> variant.colorize(player, primary, secondary, false)));
                ItemItemAction.register(Items.SLAYER_HELMET_I, variant.requiredId, ((player, primary, secondary) -> variant.colorize(player, primary, secondary, true)));
            }
            ItemDef.get(variant.helmId).slayerBoostMelee = true;
            ItemDef.get(variant.imbuedHelmId).slayerBoostAll = true;
        }
    }
}
