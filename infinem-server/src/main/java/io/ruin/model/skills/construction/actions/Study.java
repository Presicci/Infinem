package io.ruin.model.skills.construction.actions;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.TeleportTab;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.skills.magic.spells.modern.*;
import io.ruin.model.skills.mining.ShootingStar;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.construction.Buildable.*;

public class Study {

    enum Lectern {
        OAK(OAK_LECTERN, 0,0),

        OAK_EAGLE(EAGLE_LECTERN, 1,0),
        TEAK_EAGLE(TEAK_EAGLE_LECTERN, 2, 0),
        MAHOGANY_EAGLE(MAHOGANY_EAGLE_LECTERN, 3, 0),

        OAK_DEMON(DEMON_LECTERN, 0, 1),
        TEAK_DEMON(TEAK_DEMON_LECTERN, 0, 2),
        MAHOGANY_DEMON(MAHOGANY_DEMON_LECTERN, 0, 3);

        final Buildable buildable;
        final int eagleSetting, demonSetting;

        Lectern(Buildable buildable, int eagleSetting, int demonSetting) {
            this.buildable = buildable;
            this.eagleSetting = eagleSetting;
            this.demonSetting = demonSetting;
        }

        public void open(Player player) {
            player.startEvent(event -> {
                //player.animate(Construction.READ_LECTERN); TODO Find proper reading animation
                //event.delay(1);
                Config.LECTERN_EAGLE.set(player, eagleSetting);
                Config.LECTERN_DEMON.set(player, demonSetting);
                Config.IQ.update(player);
                player.openInterface(InterfaceType.MAIN, Interface.TABLET_MAKING);
            });
        }
    }

    static {
        for (Lectern l : Lectern.values()) {
            ObjectAction.register(l.buildable.getBuiltObjects()[0], "study", (player, obj) -> l.open(player));
        }

        /*
         * Telescope
         */
        ObjectAction.register(Buildable.WOODEN_TELESCOPE.getBuiltObjects()[0], "observe", (player, obj) -> player.dialogue(new MessageDialogue(ShootingStar.getTelescopeString(24))));
        ObjectAction.register(Buildable.TEAK_TELESCOPE.getBuiltObjects()[0], "observe", (player, obj) -> player.dialogue(new MessageDialogue(ShootingStar.getTelescopeString(9))));
        ObjectAction.register(Buildable.MAHOGANY_TELESCOPE.getBuiltObjects()[0], "observe", (player, obj) -> player.dialogue(new MessageDialogue(ShootingStar.getTelescopeString(2))));
    }

    public enum Tablet {
        ENCHANT_SAPPHIRE(17, 8016, 0, 0, JewelleryEnchant.EnchantLevel.ONE),
        ENCHANT_EMERALD(16, 8017, 0, 1, JewelleryEnchant.EnchantLevel.TWO),
        ENCHANT_RUBY(22, 8018, 0, 2, JewelleryEnchant.EnchantLevel.THREE),
        ENCHANT_DIAMOND(13, 8019, 0, 2, JewelleryEnchant.EnchantLevel.FOUR),
        ENCHANT_DRAGONSTONE(21, 8020, 0, 3, JewelleryEnchant.EnchantLevel.FIVE),
        ENCHANT_ONYX(11, 8021, 0, 3, JewelleryEnchant.EnchantLevel.SIX),

        VARROCK_TELEPORT(23, TeleportTab.VARROCK.id, 0, 0, ModernTeleport.VARROCK_TELEPORT),
        LUMBRIDGE_TELEPORT(12, TeleportTab.LUMBRIDGE.id, 1, 0, ModernTeleport.LUMBRIDGE_TELEPORT),
        FALADOR_TELEPORT(18, TeleportTab.FALADOR.id, 1, 0, ModernTeleport.FALADOR_TELEPORT),
        CAMELOT_TELEPORT(24, TeleportTab.CAMELOT.id, 2, 0, ModernTeleport.CAMELOT_TELEPORT),
        ARDOUGNE_TELEPORT(19, TeleportTab.ARDOUGNE.id, 2, 0, ModernTeleport.ARDOUGNE_TELEPORT),
        WATCHTOWER_TELEPORT(14, TeleportTab.WATCHTOWER.id, 3, 0, ModernTeleport.WATCHTOWER_TELEPORT),

        HOUSE_TELEPORT(15, 8013, TeleportToHouse.LVL_REQ, TeleportToHouse.XP,  3, 0, TeleportToHouse.RUNES),

        BONES_TO_BANANAS(20, 8014, BonesBananas.LVL_REQ, BonesBananas.XP, 0, 1, BonesBananas.RUNES),
        BONES_TO_PEACHES(25, 8015, BonesPeaches.LVL_REQ, BonesPeaches.XP, 0, 3, BonesPeaches.RUNES);

        final int childId, tabId, magicLevel, eagleLevel, demonLevel;
        final double xp;
        final Item[] runes;

        Tablet(int childId, int tabId, int eagleLevel, int demonLevel, JewelleryEnchant.EnchantLevel enchant) {
            this(childId, tabId, enchant.levelReq, enchant.exp, eagleLevel, demonLevel, enchant.runes);
        }

        Tablet(int childId, int tabId, int eagleLevel, int demonLevel, ModernTeleport tele) {
            this(childId, tabId, tele.getLvlReq(), tele.getXp(), eagleLevel, demonLevel, tele.getRunes());
        }

        Tablet(int childId, int tabId, int magicLevel, double xp, int eagleLevel, int demonLevel, Item... runes) {
            this.childId = childId;
            this.tabId = tabId;
            this.magicLevel = magicLevel;
            this.xp = xp;
            this.eagleLevel = eagleLevel;
            this.demonLevel = demonLevel;
            this.runes = runes;
        }

        public void make(Player player, int amount) {
            player.startEvent(event -> {
               int left = amount;
               player.closeInterfaces();
               while (left > 0) {
                   if (!player.getStats().check(StatType.Magic, magicLevel, "create this tablet")) {
                       return;
                   }
                   if (!player.getInventory().contains(1761, 1)) {
                       player.sendMessage("A piece of soft clay is required to create a tablet.");
                       return;
                   }
                   if (Config.LECTERN_DEMON.get(player) < demonLevel || Config.LECTERN_EAGLE.get(player) < eagleLevel) {
                       player.sendMessage("Your lectern cannot create that type of tablet.");
                       return;
                   }
                   RuneRemoval r = RuneRemoval.get(player, runes);
                   if (r == null) {
                       player.sendMessage("You don't have the required runes to create that tablet.");
                       return;
                   }
                   player.animate(Construction.MAKE_TABLET);
                   player.getStats().addXp(StatType.Magic, xp, true);
                   player.getInventory().remove(1761, 1);
                   r.remove();
                   player.getInventory().add(tabId, 1);
                   left--;
                   event.delay(5);
               }
            });
        }
    }

    static {
        InterfaceHandler.register(Interface.TABLET_MAKING, h -> {
            for (Tablet tab : Tablet.values()) {
                h.actions[tab.childId] = (OptionAction) (p, option) -> tab.make(p, Config.IQ.get(p));
            }
            h.actions[5] = (SimpleAction) (p) -> Config.IQ.set(p, 1);
            h.actions[6] = (SimpleAction) (p) -> Config.IQ.set(p, 5);
            h.actions[7] = (SimpleAction) (p) -> Config.IQ.set(p, 10);
            h.actions[8] = (DefaultAction) (p, option, slot, itemId) -> p.integerInput("Enter amount:", amt -> Config.IQ.set(p, amt));
            h.actions[9] = (SimpleAction) (p) -> Config.IQ.set(p, 28);
        });
    }

}
