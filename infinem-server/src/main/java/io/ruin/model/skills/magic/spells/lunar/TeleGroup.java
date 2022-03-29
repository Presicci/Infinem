package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/27/2022
 */
public enum TeleGroup {

    MOONCLAN(70, 67, 2113, 3915, "Lunar Isle",Rune.ASTRAL.toItem(2), Rune.LAW.toItem(1), Rune.EARTH.toItem(4)),
    WATERBIRTH(73, 72, 2546, 3755, "Waterbirth Island", Rune.ASTRAL.toItem(2), Rune.LAW.toItem(1), Rune.WATER.toItem(5)),
    BARBARIAN(76, 77, 2543, 3568, "Barbarian Outpost", Rune.ASTRAL.toItem(2), Rune.LAW.toItem(2), Rune.FIRE.toItem(6)),
    KHAZARD(79, 81, 2636, 3167, "Port Khazard", Rune.ASTRAL.toItem(2), Rune.LAW.toItem(2), Rune.WATER.toItem(8)),
    FISHING_GUILD(86, 90, 2612, 3391, "Fishing Guild", Rune.ASTRAL.toItem(3), Rune.LAW.toItem(3), Rune.WATER.toItem(14)),
    CATHERBY(88, 93, 2802, 3449, "Catherby", Rune.ASTRAL.toItem(3), Rune.LAW.toItem(3), Rune.WATER.toItem(15)),
    ICE_PLATEAU(90, 99, 2973, 3939, "Ice Plateau<br>WILDERNESS", Rune.ASTRAL.toItem(3), Rune.LAW.toItem(3), Rune.WATER.toItem(16));

    public final int levelReq, x, y;
    public final double exp;
    public final String location;
    public final Item[] runes;

    TeleGroup(int levelReq, double exp, int x, int y, String location, Item... runes) {
        this.levelReq = levelReq;
        this.exp = exp;
        this.x = x;
        this.y = y;
        this.location = location;
        this.runes = runes;
    }

    private boolean teleport(Player player, Integer i) {
        for (Entity target : player.localPlayers()) {
            if(target.player == null) {
                continue;
            }
            if (target.player == player) {
                continue;
            }
            if (target.player.getPosition().distance(player.getPosition()) > 1) {
                continue;
            }
            if (target.player.wildernessLevel > 0) {
                continue;
            }
            if(Config.ACCEPT_AID.get(target.player) == 0) {
                continue;
            }
            if(target.player.isVisibleInterface(Interface.TELEOTHER) || target.player.hasOpenInterface(InterfaceType.MAIN)) {
                continue;
            }

            target.player.openInterface(InterfaceType.MAIN, Interface.TELEOTHER);
            target.player.getPacketSender().sendString(Interface.TELEOTHER, 89, player.getName());
            target.player.getPacketSender().sendString(Interface.TELEOTHER, 91, location);
            target.player.telegroupActive = this;
        }
        player.getStats().addXp(StatType.Magic, exp, true);
        return LunarTeleport.teleport(player, x, y, 0);
    }

    public Spell toSpell() {
        Spell spell = new Spell();
        spell.registerClick(levelReq, exp, true, runes, this::teleport);

        return spell;
    }
}