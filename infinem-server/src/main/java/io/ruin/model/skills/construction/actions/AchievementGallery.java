package io.ruin.model.skills.construction.actions;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.killcount.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.impl.jewellery.JewelleryTeleportBounds;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.PrayerAltar;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.magic.SpellBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.ruin.model.skills.construction.Buildable.*;
import static io.ruin.model.skills.construction.Construction.forHouseOwnerOnly;

public class AchievementGallery {

    private static final String[] BOX_NAMES = {"Basic", "Fancy", "Ornate"};

    public static void openJewelleryBox(Player player, int level) {
        if (level < 1 || level > BOX_NAMES.length) {
            return;
        }
        player.openInterface(InterfaceType.MAIN, 590);
        player.getPacketSender().sendClientScript(1685,"isi",
                level,
                BOX_NAMES[level - 1] + " Jewellery Box",
                15 // toggling bits in this parameter "unlocks" certain restricted teleports like tears of guthix, by default we just unlock them all
        );
        player.putTemporaryAttribute("JEWBOX_LEVEL", level);
    }

    static {
        // TODO Recent teleport handling, VB 2308
        for (Buildable b : Arrays.asList(BASIC_JEWELLERY_BOX, FANCY_JEWELLERY_BOX, ORNATE_JEWELLERY_BOX)) {
            ObjectAction.register(b.getBuiltObjects()[0], 2, (player, obj) -> {
                player.removeTemporaryAttribute("GLOBETROTTER_JEWELLERY");
                openJewelleryBox(player, b.getBuiltObjects()[0] - 29153);
            });
        }
    }

    public enum JewelleryTeleport { // order is the same as slots in the interface!
        //Ring of dueling
        DUEL_ARENA(JewelleryTeleportBounds.DUEL_ARENA.getBounds()),
        CASTLE_WARS(JewelleryTeleportBounds.CASTLE_WARS.getBounds()),
        FEROX_ENCLAVE(JewelleryTeleportBounds.FEROX_ENCLAVE.getBounds()),
        FORTIS_COLOSSEUM(JewelleryTeleportBounds.FORTIS_COLOSSEUM.getBounds()),

        //Games necklace
        BURTHORPE(JewelleryTeleportBounds.BURTHORPE.getBounds()),
        BARBARIAN_OUTPOST(JewelleryTeleportBounds.BARBARIAN_OUTPOST.getBounds()),
        CORPOREAL_BEAST(JewelleryTeleportBounds.CORPOREAL_BEAST.getBounds()),
        TEARS_OF_GUTHIX(JewelleryTeleportBounds.TEARS_OF_GUTHIX.getBounds()),
        WINTERTODT_CAMP(JewelleryTeleportBounds.WINTERTODT_CAMP.getBounds()),

        //Combat bracelet
        WARRIORS_GUILD(JewelleryTeleportBounds.WARRIORS_GUILD.getBounds()),
        CHAMPIONS_GUILD(JewelleryTeleportBounds.CHAMPIONS_GUILD.getBounds()),
        MONASTERY(JewelleryTeleportBounds.EDGEVILLE_MONASTERY.getBounds()),
        RANGING_GUILD(JewelleryTeleportBounds.RANGING_GUILD.getBounds()),

        //Skills necklace
        FISHING_GUILD(JewelleryTeleportBounds.FISHING_GUILD.getBounds()),
        MINING_GUILD(JewelleryTeleportBounds.MINING_GUILD.getBounds()),
        CRAFTING_GUILD(JewelleryTeleportBounds.CRAFTING_GUILD.getBounds()),
        COOKING_GUILD(JewelleryTeleportBounds.COOKING_GUILD.getBounds()),
        WOODCUTTING_GUILD(JewelleryTeleportBounds.WOODCUTTING_GUILD.getBounds()),
        FARMING_GUILD(JewelleryTeleportBounds.FARMING_GUILD.getBounds()),

        //Ring of wealth
        MISCELLANIA(JewelleryTeleportBounds.MISCELLANIA.getBounds()),
        GRAND_EXCHANGE(JewelleryTeleportBounds.GRAND_EXCHANGE.getBounds()),
        FALADOR_PARK(JewelleryTeleportBounds.FALADOR.getBounds()),
        DONDAKANS_ROCK(JewelleryTeleportBounds.DONDAKAN.getBounds()),

        //Amulet of glory
        EDGEVILLE(JewelleryTeleportBounds.EDGEVILLE.getBounds()),
        KARAMJA(JewelleryTeleportBounds.KARAMJA.getBounds()),
        DRAYNOR_VILLAGE(JewelleryTeleportBounds.DRAYNOR_VILLAGE.getBounds()),
        AL_KHARID(JewelleryTeleportBounds.AL_KHARID.getBounds());

        private final Bounds bounds;

        JewelleryTeleport(Bounds bounds) {
            this.bounds = bounds;
        }

        public void teleport(Player player) {
            if (this == FORTIS_COLOSSEUM) {
                player.sendMessage("You can't use this teleport.");
                return;
            }
            player.getMovement().startTeleport(event -> {
                player.animate(714);
                player.graphics(111, 92, 0);
                player.publicSound(200);
                event.delay(3);
                Position position = bounds.randomPosition();
                player.getMovement().teleport(position);
                if (player.hasTemporaryAttribute("GLOBETROTTER_JEWELLERY")) {
                    player.putTemporaryAttribute("LAST_TELE", position);
                    player.removeTemporaryAttribute("GLOBETROTTER_JEWELLERY");
                }
            });
        }
        static {
            InterfaceHandler.register(590, h -> {
                h.actions[0] = (SlotAction) (p, slot) -> {
                    p.closeInterfaces();
                    if (slot < 0 || slot >= values().length)
                        return;
                    int level = p.getTemporaryAttributeOrDefault("JEWBOX_LEVEL", 1);
                    p.removeTemporaryAttribute("JEWBOX_LEVEL");
                    if ((slot >= 8 && level < 2)
                            || (slot >= 17 && level < 3)) // anti-cheaters i guess...
                        return;
                    values()[slot].teleport(p);
                };
                h.closedAction = (player, integer) -> {
                    player.getPacketSender().sendClientScript(4079);
                };
            });
        }
    }

    static {
        for (Buildable b: Arrays.asList(MAHOGANY_ADVENTURE_LOG, MARBLE_ADVENTURE_LOG, GILDED_ADVENTURE_LOG)) {
            ObjectAction.register(b.getBuiltObjects()[0], "read", Construction.forCurrentHouse((player, house) -> {
                readAchievementLog(player, house.getOwner());
            }));
        }
    }

    private static void readAchievementLog(Player player, Player owner) {
        if (owner == null) {
            return;
        }
        player.dialogue(new OptionsDialogue(
                new Option("Boss Kill Log", () -> KillCounter.openBoss(player, owner)),
                new Option("Slayer Kill Log", () -> KillCounter.openSlayer(player, owner)),
                new Option("Cancel")
        ));
    }

    /* Altar */
    static {
        ObjectAction.register(ANCIENT_ALTAR.getBuiltObjects()[0], "upgrade", forHouseOwnerOnly((player, house) -> sendFurnitureCreation(player, player.getCurrentRoom(), 0, OCCULT_ALTAR_FROM_ANCIENT)));
        ObjectAction.register(LUNAR_ALTAR.getBuiltObjects()[0], "upgrade", forHouseOwnerOnly((player, house) -> sendFurnitureCreation(player, player.getCurrentRoom(), 0, OCCULT_ALTAR_FROM_LUNAR)));
        ObjectAction.register(DARK_ALTAR.getBuiltObjects()[0], "upgrade", forHouseOwnerOnly((player, house) -> sendFurnitureCreation(player, player.getCurrentRoom(), 0, OCCULT_ALTAR_FROM_DARK)));

        ObjectAction.register(ANCIENT_ALTAR.getBuiltObjects()[0], "venerate", (player, obj) -> switchDialogue(player, SpellBook.ANCIENT, SpellBook.MODERN));
        ObjectAction.register(LUNAR_ALTAR.getBuiltObjects()[0], "venerate", (player, obj) -> switchDialogue(player, SpellBook.LUNAR, SpellBook.MODERN));
        ObjectAction.register(DARK_ALTAR.getBuiltObjects()[0], "venerate", (player, obj) -> switchDialogue(player, SpellBook.ARCEUUS, SpellBook.MODERN));
        ObjectAction.register(OCCULT_ALTAR.getBuiltObjects()[0], 1, (player, obj) -> switchDialogue(player, SpellBook.ANCIENT, SpellBook.LUNAR, SpellBook.ARCEUUS, SpellBook.MODERN));
        ObjectAction.register(OCCULT_ALTAR.getBuiltObjects()[0], 2, (player, obj) -> occultOption(player, 0));
        ObjectAction.register(OCCULT_ALTAR.getBuiltObjects()[0], 3, (player, obj) -> occultOption(player, 1));
        ObjectAction.register(OCCULT_ALTAR.getBuiltObjects()[0], 4, (player, obj) -> occultOption(player, 2));
    }

    private static void occultOption(Player player, int index) {
        List<SpellBook> books = new ArrayList<>();
        for (SpellBook book : SpellBook.values()) {
            if (!book.isActive(player)) books.add(book);
        }
        PrayerAltar.switchBook(player, books.get(index), true);
    }

    private static void switchDialogue(Player player, SpellBook... books) {
        player.dialogue(new OptionsDialogue("Choose a spellbook",
                Arrays.stream(books).map(book -> new Option(StringUtils.getFormattedEnumName(book), () -> PrayerAltar.switchBook(player, book, true))).toArray(Option[]::new)
        ));
    }

    /* Admiring */
    static {
        ObjectAction.register(MOUNTED_EMBLEM.getBuiltObjects()[0], "admire", (player, obj) -> player.animate(6937));
        ObjectAction.register(MOUNTED_COINS.getBuiltObjects()[0], "admire", (player, obj) -> player.animate(6937));
    }

}
