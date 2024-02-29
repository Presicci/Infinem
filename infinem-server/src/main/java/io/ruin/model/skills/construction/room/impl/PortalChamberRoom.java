package io.ruin.model.skills.construction.room.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.ObjectDef;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.teleport.BasaltTeleport;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.skills.construction.RoomDefinition;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.skills.magic.MagicTeleportBounds;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.ruin.model.skills.magic.rune.Rune.*;

public class PortalChamberRoom extends Room {

    public enum PortalDestination {
        ARCEUUS_LIBRARY(6, MagicTeleportBounds.ARCEUUS_LIBRARY.getBounds(), new int[]{41416, 41417, 41418}, EARTH.toItem(200), LAW.toItem(100)),
        DRAYNOR_MANOR(17, MagicTeleportBounds.DRAYNOR_MANOR.getBounds(), new int[]{37583, 37595, 37607}, EARTH.toItem(100), WATER.toItem(100), LAW.toItem(100)),
        BATTLEFRONT(23, MagicTeleportBounds.BATTLEFRONT.getBounds(), new int[]{37584, 37595, 37608}, EARTH.toItem(100), WATER.toItem(100), LAW.toItem(100)),
        VARROCK(25, MagicTeleportBounds.VARROCK.getBounds(), new int[]{13615, 13622, 13629}, LAW.toItem(100), AIR.toItem(300), FIRE.toItem(100)),
        MIND_ALTAR(28, MagicTeleportBounds.MIND_ALTAR.getBounds(), new int[]{37585, 37597, 37609}, LAW.toItem(100), MIND.toItem(200)),
        LUMBRIDGE(31, MagicTeleportBounds.LUMBRIDGE.getBounds(), new int[]{13616, 13623, 13630}, LAW.toItem(100), EARTH.toItem(100), AIR.toItem(300)),
        FALADOR(37, MagicTeleportBounds.FALADOR.getBounds(), new int[]{13617, 13624, 13631}, LAW.toItem(100), WATER.toItem(100), AIR.toItem(300)),
        SALVE_GRAVEYARD(40, MagicTeleportBounds.SALVE_GRAVEYARD.getBounds(), new int[]{37586, 37598, 37610}, LAW.toItem(100), SOUL.toItem(200)),
        CAMELOT(45, MagicTeleportBounds.CAMELOT.getBounds(), new int[]{13618, 13625, 13632}, LAW.toItem(100), AIR.toItem(500)),
        FENKENSTRAINS_CASTLE(48, MagicTeleportBounds.FENKENSTRAINS_CASTLE.getBounds(), new int[]{37587, 37599, 37611}, EARTH.toItem(100), LAW.toItem(100), SOUL.toItem(100)),
        ARDOUGNE(51, MagicTeleportBounds.ARDOUGNE.getBounds(), new int[]{13619, 13626, 13633}, LAW.toItem(200), WATER.toItem(200)),
        WATCHTOWER(58, MagicTeleportBounds.WATCHTOWER.getBounds(), new int[]{13620, 13627, 13634}, LAW.toItem(200), EARTH.toItem(200)),
        SENNTISTEN(60, MagicTeleportBounds.SENNTISTEN.getBounds(), new int[]{29340, 29348, 29356}, SOUL.toItem(100), LAW.toItem(200)),
        WEST_ARDOUGNE(61, MagicTeleportBounds.WEST_ARDOUGNE.getBounds(), new int[]{37588, 37600, 37612}, LAW.toItem(200), SOUL.toItem(200)),
        MARIM(64, MagicTeleportBounds.APE_ATOLL.getBounds(), new int[]{29344, 29352, 29360}, LAW.toItem(200), FIRE.toItem(200), WATER.toItem(200), new Item(1963, 100)),
        HARMONY_ISLAND(65, MagicTeleportBounds.HARMONY_ISLAND.getBounds(), new int[]{37589, 37601, 37613}, LAW.toItem(100), NATURE.toItem(100), SOUL.toItem(100)),
        KHARYRLL(66, MagicTeleportBounds.KHARYLL.getBounds(), new int[]{29338, 29346, 29354}, BLOOD.toItem(100), LAW.toItem(200)),
        LUNAR_ISLE(69, MagicTeleportBounds.MOONCLAN.getBounds(), new int[]{29339, 29347, 29355}, LAW.toItem(100), ASTRAL.toItem(200), EARTH.toItem(200)),
        CEMETERY(71, MagicTeleportBounds.CEMETARY.getBounds(), new int[]{37590, 37602, 37614}, BLOOD.toItem(100), LAW.toItem(100), SOUL.toItem(100)),
        KOUREND(69, MagicTeleportBounds.KOUREND.getBounds(), new int[]{29345, 29353, 29361}, SOUL.toItem(200), LAW.toItem(200), FIRE.toItem(500), WATER.toItem(400)),
        WATERBIRTH_ISLAND(72, MagicTeleportBounds.WATERBIRTH.getBounds(), new int[]{29342, 29350, 29358}, LAW.toItem(100), ASTRAL.toItem(200), WATER.toItem(100)),
        BARROWS(83, MagicTeleportBounds.BARROWS.getBounds(), new int[]{37591, 37603, 37615}, BLOOD.toItem(100), LAW.toItem(200), SOUL.toItem(200)),
        CARRALLANGER(84, MagicTeleportBounds.CARRALLANGAR.getBounds(), new int[]{33434, 33437, 33440}, LAW.toItem(200), SOUL.toItem(200)),
        FISHING_GUILD(85, MagicTeleportBounds.FISHING_GUILD.getBounds(), new int[]{29343, 29351, 29359}, LAW.toItem(300), ASTRAL.toItem(300), WATER.toItem(1000)),
        CATHERBY(87, MagicTeleportBounds.CATHERBY.getBounds(), new int[]{33432, 33435, 33438}, WATER.toItem(1000), ASTRAL.toItem(300), LAW.toItem(300)),
        ANNAKARL(90, MagicTeleportBounds.ANNAKARL.getBounds(), new int[]{29341, 29349, 29357}, BLOOD.toItem(200), LAW.toItem(200)),
        APE_ATOLL_DUNGEON(90, MagicTeleportBounds.APE_ATOLL_DUNGEON.getBounds(), new int[]{37592, 37604, 37616}, BLOOD.toItem(200), LAW.toItem(200), SOUL.toItem(200)),
        GHORROCK(96, MagicTeleportBounds.GHORROCK.getBounds(), new int[]{33433, 33436, 33439}, WATER.toItem(800), LAW.toItem(200)),
        TROLL_STRONGHOLD(1, BasaltTeleport.TROLL_STRONGHOLD.toBounds(), new int[]{33179, 33180, 33181}, new Item(22604, 100), new Item(22593, 100), new Item(22597, 300)),
        WEISS(1, BasaltTeleport.WEISS.toBounds(), new int[]{37581, 37593, 37605}, new Item(22604, 100), new Item(22593, 100), new Item(22595, 300)),

        GRAND_EXCHANGE(VARROCK, MagicTeleportBounds.VARROCK_GE.getBounds(), Config.varpbit(4585, true), player -> AreaReward.GRAND_EXCHANGE_TELEPORT.checkReward(player, NOT_ENOUGH_TASKS_MESSAGE)),
        SEERS_VILLAGE(CAMELOT, MagicTeleportBounds.CAMELOT_SEERS.getBounds(), Config.varpbit(4560, true), player -> AreaReward.SEERS_TELEPORT.checkReward(player, NOT_ENOUGH_TASKS_MESSAGE)),
        YANILLE(WATCHTOWER, MagicTeleportBounds.WATCHTOWER_YANILLE.getBounds(), Config.varpbit(4548, true), player -> AreaReward.YANILLE_TELEPORT.checkReward(player, NOT_ENOUGH_TASKS_MESSAGE));

        final int levelReq;
        final Item[] runes;
        final int[] portalIds; //teak, mahog, marble
        final Predicate<Player> focusReq;
        final String name;
        boolean hidden;
        Predicate<Player> alternateReq;
        PortalDestination alternate;
        Config config;
        public final Bounds bounds;

        PortalDestination(int levelReq, Bounds bounds, int[] portalIds, Item... runes) {
            this.levelReq = levelReq;
            this.bounds = bounds;
            this.runes = runes;
            this.portalIds = portalIds;
            name = StringUtils.fixCaps(name().replace('_', ' '));
            this.focusReq = p -> true;
        }

        PortalDestination(PortalDestination other, Bounds bounds, Config config, Predicate<Player> alternateReq) {
            this(other.levelReq, bounds, other.portalIds, other.runes);
            this.alternateReq = alternateReq;
            this.config = config;
            other.alternate = this;
            other.alternateReq = alternateReq;
            alternate = other;
            hidden = true;
        }
    }

    private static final String NOT_ENOUGH_TASKS_MESSAGE = "use this teleport.";

    @Expose
    private PortalDestination[] portalDestinations = new PortalDestination[3];

    @Override
    protected void onBuild() {
        for (int i = 0; i < portalDestinations.length; i++) { // render our selected portals
            renderPortal(i);
        }
        getHotspotObjects(Hotspot.TELEPORT_FOCUS).forEach(o -> ObjectAction.register(o, 1, (player, gameObject) -> openFocusSelection(player)));
        getHotspotObjects(Hotspot.TELEPORT_FOCUS).forEach(o -> ObjectAction.register(o, 2, (player, gameObject) -> openScrySelection(player)));

        getHotspotObjects(Hotspot.PORTAL_1).forEach(obj -> ObjectAction.register(obj, 1, (p, o) -> teleport(p, 0)));
        getHotspotObjects(Hotspot.PORTAL_2).forEach(obj -> ObjectAction.register(obj, 1, (p, o) -> teleport(p, 1)));
        getHotspotObjects(Hotspot.PORTAL_3).forEach(obj -> ObjectAction.register(obj, 1, (p, o) -> teleport(p, 2)));

        getHotspotObjects(Hotspot.PORTAL_1).forEach(obj -> ObjectAction.register(obj, 2, (p, o) -> altTeleport(p, 0)));
        getHotspotObjects(Hotspot.PORTAL_2).forEach(obj -> ObjectAction.register(obj, 2, (p, o) -> altTeleport(p, 1)));
        getHotspotObjects(Hotspot.PORTAL_3).forEach(obj -> ObjectAction.register(obj, 2, (p, o) -> altTeleport(p, 2)));

        getHotspotObjects(Hotspot.PORTAL_1).forEach(obj -> ObjectAction.register(obj, 3, (p, o) -> toggle(p, 0)));
        getHotspotObjects(Hotspot.PORTAL_2).forEach(obj -> ObjectAction.register(obj, 3, (p, o) -> toggle(p, 1)));
        getHotspotObjects(Hotspot.PORTAL_3).forEach(obj -> ObjectAction.register(obj, 3, (p, o) -> toggle(p, 2)));
    }

    private void openScrySelection(Player player) {
        if (getBuilt(Hotspot.TELEPORT_FOCUS) != Buildable.SCRYING_POOL)
            return;
        player.dialogue(new OptionsDialogue("Choose an option",
                new Option("Portal 1: " + (portalDestinations[0] == null ? "Not focused" : portalDestinations[0].name), () -> scry(player, 0)),
                new Option("Portal 2: " + (portalDestinations[1] == null ? "Not focused" : portalDestinations[1].name), () -> scry(player, 1)),
                new Option("Portal 3: " + (portalDestinations[2] == null ? "Not focused" : portalDestinations[2].name), () -> scry(player, 2))
        ));
    }

    private void scry(Player player, int portalIndex) {
        int count = portalDestinations[portalIndex].bounds.getRegion().players.size();
        player.dialogue(new MessageDialogue("There are " + count + " players at the destination."));
        /*PortalDestination dest = portalDestinations[portalIndex];
        if (dest == null) {
            player.dialogue(new MessageDialogue("That portal is not focused."));
            return;
        }
        //this makes me very nervous
        player.addEvent(event -> {
            Position returnPosition = player.getPosition().localPosition();
            player.getMovement().teleport(dest.bounds.randomX(), dest.bounds.randomY(), dest.bounds.z);
            player.setHidden(true);
            player.getAppearance().setNpcId(21); // invis
            player.openInterface(InterfaceType.SECONDARY_OVERLAY, Interface.SCRYING_POOL);
            player.lock();
            event.delay(1);
            player.logoutListener = new LogoutListener().onAttempt(p -> {
                p.putTemporaryAttribute("SCRY_LOGOUT", 1);
                p.closeDialogue();
                p.getMovement().teleport(getHouse().getLocation().getPosition());
                return true;
            });
            player.dialogue(new MessageDialogue("You view " + dest.name + "...") {
                @Override
                public void closed(Player player) {
                    if (player.hasTemporaryAttribute("SCRY_LOGOUT")) return;
                    if (getHouse().getOwner() == player) {
                        player.house.buildAndEnter(player, returnPosition, false);
                    } else {
                        getHouse().enterOrPortal(player, returnPosition);
                    }
                }
            });
            event.waitForDialogue(player);
            player.setHidden(false);
            player.getAppearance().setNpcId(-1);
            player.logoutListener = null;
            player.unlock();
        });*/
    }

    private void teleport(Player p, int portalIndex) {
        PortalDestination dest = portalDestinations[portalIndex];
        if (dest == null) {
            p.sendMessage("Invalid portal?");
            return;
        }
        if (dest == PortalDestination.TROLL_STRONGHOLD) {
            if (BasaltTeleport.canTeleportUpper.test(p)) {
                p.getMovement().teleport(BasaltTeleport.TROLL_STRONGHOLD_UPPER);
            } else {
                p.getMovement().teleport(dest.bounds.randomX(), dest.bounds.randomY(), dest.bounds.z);
            }
        } else {
            if (dest.alternate != null) {
                if (dest.alternate.config.get(p) == 1) {
                    if (dest.alternateReq.test(p))
                        p.getMovement().teleport(dest.alternate.bounds.randomX(), dest.alternate.bounds.randomY(), dest.alternate.bounds.z);
                } else {
                    p.getMovement().teleport(dest.bounds.randomX(), dest.bounds.randomY(), dest.bounds.z);
                }
            } else {
                p.getMovement().teleport(dest.bounds.randomX(), dest.bounds.randomY(), dest.bounds.z);
            }
        }
    }

    private void altTeleport(Player p, int portalIndex) {
        PortalDestination dest = portalDestinations[portalIndex];
        if (dest == null) {
            p.sendMessage("Invalid portal?");
            return;
        }
        if (dest.alternate == null) {
            p.dialogue(new MessageDialogue("This portal doesn't have an alternate destination."));
            return;
        }
        if (dest.alternate.config.get(p) == 0) {
            if (dest.alternateReq.test(p))
                p.getMovement().teleport(dest.alternate.bounds.randomX(), dest.alternate.bounds.randomY(), dest.alternate.bounds.z);
        } else {
            p.getMovement().teleport(dest.bounds.randomX(), dest.bounds.randomY(), dest.bounds.z);
        }
    }

    private void toggle(Player p, int portalIndex) {
        PortalDestination dest = portalDestinations[portalIndex];
        if (dest == null) {
            p.sendMessage("Invalid portal?");
            return;
        }
        if (!p.isInOwnHouse()) {
            p.dialogue(new MessageDialogue("Only the house owner can do that."));
            return;
        }
        if (dest.alternate == null) {
            p.dialogue(new MessageDialogue("This portal doesn't have an alternate destination."));
            return;
        }
        if (!dest.alternateReq.test(p)) {
            return;
        }
        int toAlt = dest.alternate.config.toggle(p);
        p.dialogue(new MessageDialogue("Left-click changed to " + (toAlt == 1 ? dest.alternate.name : dest.name) + "."));
    }

    private void openFocusSelection(Player player) {
        Consumer<Integer> scroll = slot -> OptionScroll.open(player, "Choose a destination", true, Arrays.stream(PortalDestination.values())
                .filter(pd -> !pd.hidden)
                .map(pd -> new Option(pd.name, () -> focus(player, slot, pd)))
                .collect(Collectors.toList()));
        player.dialogue(new OptionsDialogue("Choose an option",
                new Option("Portal 1: " + (portalDestinations[0] == null ? "Not focused" : portalDestinations[0].name), () -> scroll.accept(0)),
                new Option("Portal 2: " + (portalDestinations[1] == null ? "Not focused" : portalDestinations[1].name), () -> scroll.accept(1)),
                new Option("Portal 3: " + (portalDestinations[2] == null ? "Not focused" : portalDestinations[2].name), () -> scroll.accept(2))
        ));
    }

    private void renderPortal(int i) {
        Buildable portalFrame = getBuilt(3 + i);
        if (portalDestinations[i] == null || portalFrame == null)
            return;
        int portalId = -1;
        if (portalFrame == Buildable.TEAK_PORTAL_FRAME)
            portalId = portalDestinations[i].portalIds[0];
        else if (portalFrame == Buildable.MAHOGANY_PORTAL_FRAME)
            portalId = portalDestinations[i].portalIds[1];
        else if (portalFrame == Buildable.MARBLE_PORTAL_FRAME)
            portalId = portalDestinations[i].portalIds[2];
        int finalPortalId = portalId;
        getHotspotObjects(RoomDefinition.PORTAL_CHAMBER.getHotspots()[3 + i]).forEach(obj -> obj.setId(finalPortalId));
    }

    @Override
    protected void onBuildableChanged(Player player, Hotspot hotspot, Buildable newBuildable) {
        if (newBuildable == null) {
            if (hotspot == Hotspot.PORTAL_1)
                portalDestinations[0] = null;
            else if (hotspot == Hotspot.PORTAL_2)
                portalDestinations[1] = null;
            else if (hotspot == Hotspot.PORTAL_3)
                portalDestinations[2] = null;
        }
    }

    private void focus(Player player, int index, PortalDestination dest) {
        if (getBuilt(3 + index) == null) {
            player.dialogue(new MessageDialogue("You need to build a portal frame there first."));
            return;
        }
        if (!player.getStats().check(StatType.Magic, dest.levelReq)) {
            player.dialogue(new MessageDialogue("You'll need a Magic level of at least " + dest.levelReq + " to direct a portal to " + dest.name + "."));
            return;
        }
        if (!player.getInventory().containsAll(true, dest.runes)) {
            StringBuilder items = new StringBuilder();
            for (int i = 0; i < dest.runes.length; i++) {
                items.append(dest.runes[i].getAmount()).append(" x ").append(dest.runes[i].getDef().name);
                if (i != dest.runes.length - 1)
                    items.append(", ");
            }
            player.dialogue(new MessageDialogue("You will need the following items to direct the portal to " + dest.name + ":<br>" + items));
            return;
        }
        if (!dest.focusReq.test(player)) {
            return;
        }
        portalDestinations[index] = dest;
        renderPortal(index);
        player.animate(722);
        player.dialogue(new MessageDialogue("The portal now leads to " + dest.name + "."));
        player.getInventory().removeAll(true, dest.runes);
        player.getStats().addXp(StatType.Magic, 100, true);
    }

    public static void find() {
        for (PortalDestination pd : PortalDestination.values()) {
            String nameSearch = pd.name().replace('_', ' ') + " Portal";
            int[] portals = new int[3];
            for (ObjectDef def : ObjectDef.LOADED.values()) {
                if (def == null || def.name == null || def.modelIds == null)
                    continue;
                if (def.name.equalsIgnoreCase(nameSearch)) {
                    if (def.modelIds[1] == 11240)
                        portals[0] = def.id;
                    else if (def.modelIds[1] == 11235)
                        portals[1] = def.id;
                    else if (def.modelIds[1] == 11237)
                        portals[2] = def.id;
                }
            }
            System.out.println(pd.name() + "(new int[]" + Arrays.toString(portals).replace('[', '{').replace(']', '}') + "),");
        }
    }

}
