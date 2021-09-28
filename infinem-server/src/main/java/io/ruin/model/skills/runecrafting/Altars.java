package io.ruin.model.skills.runecrafting;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.item.actions.impl.storage.EssencePouch;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public enum Altars {
    //air = 133, 134
    //water = 136, 137
    //earth = 139, 140
    //fire = 130, 131
    AIR(1, 5.0, 1438, 5527, 556, 34760, 34813, new Position(2841, 4830, 0), 34748, new Position(2983, 3293, 0), Essence.REGULAR, 11, 1795758, Pet.RIFT_GUARDIAN_AIR, PlayerCounter.CRAFTED_AIR, 133, 134),
    // MIND ALTAR IS FUCKING BROKEN
    MIND(2, 5.5, 1448, 5529, 558, 34761, 34814, new Position(2792, 4827, 0), 34749, new Position(2984, 3512, 0), Essence.REGULAR, 14, 1795758, Pet.RIFT_GUARDIAN_MIND, PlayerCounter.CRAFTED_MIND, 133, 134),
    WATER(5, 6.0, 1444, 5531, 555, 34762, 34815, new Position(2726, 4832, 0), 34750, new Position(3183, 3167, 0), Essence.REGULAR, 19, 1795758, Pet.RIFT_GUARDIAN_WATER, PlayerCounter.CRAFTED_WATER, 136, 137),
    EARTH(9, 6.5, 1440, 5535, 557, 34763, 34816, new Position(2655, 4830, 0), 34751, new Position(3305, 3472, 0), Essence.REGULAR, 29, 1795758, Pet.RIFT_GUARDIAN_EARTH, PlayerCounter.CRAFTED_EARTH, 139, 140),
    FIRE(14, 7.0, 1442, 5537, 554, 34764, 34817, new Position(2574, 4849, 0), 34752, new Position(3312, 3253, 0), Essence.REGULAR, 35, 1795758, Pet.RIFT_GUARDIAN_FIRE, PlayerCounter.CRAFTED_FIRE, 130, 131),
    // BODY ALTAR IS FUCKING BROKEN
    BODY(20, 7.5, 1446, 5533, 559, 34765, 34818, new Position(2521, 4834, 0), 34753, new Position(3054, 3443, 0), Essence.REGULAR, 46, 1795758, Pet.RIFT_GUARDIAN_BODY, PlayerCounter.CRAFTED_BODY, 130, 131),
    // COSMIC ALTAR IS FUCKING BROKEN
    COSMIC(27, 8.0, 1454, 5539, 564, 34766, 34819, new Position(2162, 4833, 0), 34754, new Position(2410, 4377, 0), Essence.PURE, 59, 1795758, Pet.RIFT_GUARDIAN_COSMIC, PlayerCounter.CRAFTED_COSMIC, 133, 134),
    LAW(54, 9.5, 1458, 5545, 563, 34767, 34820, new Position(2464, 4818, 0), 34755, new Position(2860, 3381, 0), Essence.PURE, 200, 1795758, Pet.RIFT_GUARDIAN_LAW, PlayerCounter.CRAFTED_LAW, 133, 134),
    NATURE(44, 9.0, 1462, 5541, 561, 34768, 34821, new Position(2400, 4835, 0), 34756, new Position(2868, 3017, 0), Essence.PURE, 91, 1795758, Pet.RIFT_GUARDIAN_NATURE, PlayerCounter.CRAFTED_NATURE, 139, 140),
    CHAOS(35, 8.5, 1452, 5543, 562, 34769, 34822, new Position(2281, 4837, 0), 34757, new Position(3062, 3590, 0), Essence.PURE, 74, 1795758, Pet.RIFT_GUARDIAN_CHAOS, PlayerCounter.CRAFTED_CHAOS, 133, 134),
    DEATH(65, 10.0, 1456, 5547, 560, 34770, 34823, new Position(2208, 4830, 0), 34758, new Position(1862, 4639, 0), Essence.PURE, 99, 1795758, Pet.RIFT_GUARDIAN_DEATH, PlayerCounter.CRAFTED_DEATH, 130, 131),
    ASTRAL(40, 8.7, -1, -1, 9075, 34771, 0, new Position(2156, 3863, 0), 14895, new Position(2156, 3863, 0), Essence.PURE, 82, 1795758, Pet.RIFT_GUARDIAN_ASTRAL, PlayerCounter.CRAFTED_ASTRAL, -1, -1),
    BLOOD(77, 23.8, -1, -1, 565, 27978, 0, new Position(1727, 3825, 0), 0, new Position(1727, 3825, 0), Essence.DARK, -1, 804984, Pet.RIFT_GUARDIAN_BLOOD, PlayerCounter.CRAFTED_BLOOD, -1, -1),
    SOUL(90, 29.7, -1, -1, 566, 27980, 0, new Position(1820, 3862, 0), 0, new Position(1820, 3862, 0), Essence.DARK, -1, 782999, Pet.RIFT_GUARDIAN_SOUL, PlayerCounter.CRAFTED_SOUL, -1, -1),
    WRATH(95, 52.5, 22118, 22121, 21880, 34772, 34824, new Position(2335, 4827, 0), 34759, new Position(2447, 2823, 0), Essence.PURE, 99, 1795758, Pet.RIFT_GUARDIAN_WRATH, PlayerCounter.CRAFTED_WRATH, -1, -1);

    public final int levelRequirement, talisman, tiara, runeID, altarObj, entranceObj, exitObj, multiplier, petOdds, imbueProjectile, imbueExplosion;
    public final double experience;
    public final Position entranceTile, exitTile;
    public final Essence essence;
    public final PlayerCounter counter;
    public final String talismanName;
    public final Pet pet;

    Altars(int levelRequirement, double experience, int talisman, int tiara, int runeID, int altarObj, int entranceObj, Position entranceTile, int exitObj,
           Position exitTile, Essence essence, int multiplier, int petOdds, Pet pet, PlayerCounter counter, int imbueProjectile, int imbueExplosion) {
        this.levelRequirement = levelRequirement;
        this.experience = experience;
        this.talisman = talisman;
        this.talismanName = talisman != -1 ? ItemDef.get(talisman).descriptiveName : "";
        this.tiara = tiara;
        this.runeID = runeID;
        this.altarObj = altarObj;
        this.entranceObj = entranceObj;
        this.entranceTile = entranceTile;
        this.exitObj = exitObj;
        this.exitTile = exitTile;
        this.essence = essence;
        this.multiplier = multiplier;
        this.petOdds = petOdds;
        this.pet = pet;
        this.counter = counter;
        this.imbueProjectile = imbueProjectile;
        this.imbueExplosion = imbueExplosion;
    }

    private enum RuneCombination {
        /* Mist runes */
        MIST_RUNES_AIR_ALTAR(4695, 34760, 555, 1444, 6, 8.0, Pet.RIFT_GUARDIAN_AIR),
        MIST_RUNES_WATER_ALTAR(4695, 34762, 556, 1438, 8, 8.5, Pet.RIFT_GUARDIAN_WATER),

        /* Dust runes */
        DUST_RUNES_AIR_ALTAR(4696, 34760, 557, 1440, 10, 8.3, Pet.RIFT_GUARDIAN_AIR),
        DUST_RUNES_EARTH_ALTAR(4696, 34763, 556, 1438, 10, 9.0, Pet.RIFT_GUARDIAN_EARTH),

        /* Mud runes */
        MUD_RUNES_WATER_ALTAR(4698, 34762, 557, 1440, 13, 9.0, Pet.RIFT_GUARDIAN_WATER),
        MUD_RUNES_EARTH_ALTAR(4698, 34763, 555, 1444, 13, 9.5, Pet.RIFT_GUARDIAN_EARTH),

        /* Smoke runes */
        SMOKE_RUNES_AIR_ALTAR(4697, 34760, 554, 1442, 15, 8.5, Pet.RIFT_GUARDIAN_AIR),
        SMOKE_RUNES_FIRE_ALTAR(4697, 34764, 556, 1438, 15, 9.5, Pet.RIFT_GUARDIAN_FIRE),

        /* Steam runes */
        STEAM_RUNES_WATER_ALTAR(4694, 34762, 554, 1442, 19, 9.5, Pet.RIFT_GUARDIAN_WATER),
        STEAM_RUNES_FIRE_ALTAR(4694, 34764, 555, 1444, 19, 10.0, Pet.RIFT_GUARDIAN_FIRE),

        /* Lava runes */
        LAVA_RUNES_EARTH_ALTAR(4699, 34763, 554, 1442, 23, 10.0, Pet.RIFT_GUARDIAN_EARTH),
        LAVA_RUNES_FIRE_ALTAR(4699, 34764, 557, 1440, 23, 10.5, Pet.RIFT_GUARDIAN_FIRE);

        public final int combinationRuneId, altar, requiredRuneId, requiredTalismanId, levelReq;
        public final double exp;
        public final String runeName, runeNameLowercase, requiredTalismanName, requiredRuneName;
        public final Pet pet;

        RuneCombination(int combinationRuneId, int altar, int requiredRuneId, int requiredTalismanId, int levelReq, double exp, Pet pet) {
            this.combinationRuneId = combinationRuneId;
            this.altar = altar;
            this.requiredRuneId = requiredRuneId;
            this.requiredTalismanId = requiredTalismanId;
            this.levelReq = levelReq;
            this.exp = exp;
            this.runeName = ItemDef.get(combinationRuneId).name + "s";
            this.runeNameLowercase = ItemDef.get(combinationRuneId).name.toLowerCase() + "s";
            this.requiredTalismanName = ItemDef.get(requiredTalismanId).name.toLowerCase();
            this.requiredRuneName = ItemDef.get(requiredRuneId).name.toLowerCase() + "s";
            this.pet = pet;
        }
    }

    private void runeConversation(Player player, Altars altar) {
        if (!player.getStats().check(StatType.Runecrafting, altar.levelRequirement, "infuse these runes"))
            return;
        player.startEvent(e -> {
            int essenceCount = 0, fromPouches = 0;
            if (altar.essence == Essence.DARK) {
                Item fragments = player.getInventory().findItem(Essence.DARK.id);
                if (fragments != null) {
                    essenceCount = player.darkEssFragments;
                    player.darkEssFragments = 0;
                    fragments.remove();
                }
            } else {
                ArrayList<Item> essences;
                if (altar.essence == Essence.PURE) {
                    essences = player.getInventory().collectItems(Essence.PURE.id);
                    essenceCount += (fromPouches = essenceFromPouches(player));
                } else
                    essences = player.getInventory().collectItems(Essence.REGULAR.id, Essence.PURE.id);
                if (essences != null) {
                    for (Item ess : essences)
                        ess.remove();
                    essenceCount += essences.size();
                }
                if (fromPouches > 0)
                    clearPouches(player);
            }
            if (essenceCount == 0) {
                player.dialogue(new MessageDialogue("You do not have any " + altar.essence.name + " to bind."));
                return;
            }
            int runesPerEssence = 1;
            if (altar.multiplier != -1) {
                int addition = player.getStats().get(StatType.Runecrafting).currentLevel / altar.multiplier;
                if (addition > 0)
                    runesPerEssence += addition;
            }

            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(791);
            player.graphics(186, 100, 0);
            e.delay(4);
            //  If the player has a rift guardian pet active, set the appearance to that of the rune you crafted
            if (player.pet != null && Arrays.stream(player.pet.getVariantArray()).anyMatch(pet -> pet == player.pet)) {
                player.petNPC.transform(altar.pet.npcId);
            }
            if (Random.rollDie(altar.petOdds - (player.getStats().get(StatType.Runecrafting).currentLevel * 25), essenceCount))
                pet.unlock(player);

            int amount = essenceCount * runesPerEssence;
            player.getInventory().add(altar.runeID, amount);
            player.getStats().addXp(StatType.Runecrafting, essenceCount * altar.experience, true);
            counter.increment(player, amount);
            player.unlock();
        });
    }

    protected static int essenceFromPouches(Player player) {
        return player.runeEssencePouches.entrySet().stream()
                .filter(e -> player.getInventory().contains(e.getKey().getItemId(), 1))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    protected static void clearPouches(Player player) {
        player.runeEssencePouches.entrySet().stream()
                .filter(e -> player.getInventory().contains(e.getKey().getItemId(), 1))
                .forEach(e -> e.setValue(0));
    }

    private static int removeFromPouches(Player player, int amount) {
        int removed = 0;
        for (Map.Entry<EssencePouch, Integer> entry : player.runeEssencePouches.entrySet()) {
            if (!player.getInventory().contains(entry.getKey().getItemId(), 1) || entry.getValue() == 0)
                continue;
            int fromPouch = Math.min(amount - removed, entry.getValue());
            entry.setValue(entry.getValue() - fromPouch);
            removed += fromPouch;
            if (removed >= amount)
                return removed;
        }
        return removed;
    }

    private static void runeCombining(Player player, RuneCombination runeCombination) {
        boolean magicImbued = player.magicImbueEffect.isDelayed();
        if(!magicImbued) {
            Item requiredTalisman = player.getInventory().findItem(runeCombination.requiredTalismanId);
            if (requiredTalisman == null) {
                player.sendMessage("You need a " + runeCombination.requiredTalismanName + " to bind " + runeCombination.runeNameLowercase + ".");
                return;
            }
        }
        Item requiredRunes = player.getInventory().findItem(runeCombination.requiredRuneId);
        if (requiredRunes == null) {
            player.sendMessage("You need " + runeCombination.requiredRuneName + " to bind " + runeCombination.runeNameLowercase + ".");
            return;
        }
        Item pureEssence = player.getInventory().findItem(Essence.PURE.id);
        if (pureEssence == null) {
            player.sendMessage("You need pure essence to bind " + runeCombination.runeNameLowercase + ".");
            return;
        }

        int amountToCombine = Math.min(requiredRunes.getAmount(), pureEssence.count());

        player.startEvent(event -> {
            player.lock();
            requiredRunes.remove(amountToCombine);
            player.getInventory().remove(pureEssence.getId(), amountToCombine);
            player.animate(791);
            player.graphics(186, 100, 0);
            player.getStats().addXp(StatType.Runecrafting, runeCombination.exp * amountToCombine, true);
            if (Random.rollDie(1795758 - (player.getStats().get(StatType.Runecrafting).currentLevel * 25), amountToCombine))
                runeCombination.pet.unlock(player);

            /* 50% chance of success without binding necklace */
            if (Random.rollDie(2, 1) && !hasNecklace(player)) {
                player.sendMessage("You fail to combine the runes.");
            } else {
                player.getInventory().add(runeCombination.combinationRuneId, amountToCombine);
                player.sendMessage("You bind the Temple's power into " + runeCombination.runeName + ".");
            }

            player.unlock();
        });
    }

    private static boolean hasNecklace(Player player) {
        Item necklace = player.getEquipment().get(Equipment.SLOT_AMULET);
        if (necklace == null)
            return false;

        if (necklace.getId() == BINDING_NECKLACE) {
            //TODO remove one charge
            return true;
        }
        return false;
    }

    private void createTiara(Player player, Altars altar) {
        Item talisman = player.getInventory().findItem(altar.talisman);
        if (talisman == null) {
            player.sendMessage("You need " + altar.talismanName + " to bind a Tiara here.");
            return;
        }
        Item tiara = player.getInventory().findItem(TIARA);
        if (tiara == null) {
            player.sendMessage("You need a Tiara to bind here.");
            return;
        }

        talisman.remove();
        tiara.setId(altar.tiara);
        player.getStats().addXp(StatType.Runecrafting, 25.0, true);
        player.sendMessage("You bind the power of the talisman into your Tiara.");
    }

    private void enterAltar(Player player, Item talisman, Altars altar) {
        player.startEvent(event -> {
            player.lock();
            player.animate(827);
            player.sendMessage("You hold the " + talisman.getDef().name.toLowerCase() + " talisman towards the mysterious ruins.");
            event.delay(2);
            player.sendMessage("You feel a powerful force take hold of you...");
            event.delay(1);
            player.getMovement().teleport(altar.entranceTile);
            player.unlock();
        });
    }

    private void exitAltar(Player player, Altars altar) {
        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You step through the portal...");
            player.getMovement().teleport(altar.exitTile);
            event.delay(1);
            player.unlock();
        });
    }


    private static final int[] glowOffsetsX = {-3, 3, 3, -3};
    private static final int[] glowOffsetsY = {3, 3, -3, -3};

    private static final int[] deathOffsetsX = {-3, -1, 1, 3, 3, 1, -1, -3};
    private static final int[] deathOffsetsY = {1, 3, 3, 1, -1, -3, -3, 1};

    private static void imbueStaff(Player player, Altars type, GameObject altar) {
        if (!player.getStats().check(StatType.Runecrafting, 68, "create battlestaves")) {
            return;
        }
        int staves = player.getInventory().getAmount(1379);
        int invEss = player.getInventory().getAmount(7936);
        int pouchEss = essenceFromPouches(player);
        int toMake = Math.min(staves, (invEss + pouchEss) / 4);
        if (toMake == 0) {
            player.sendMessage("You'll need 4 pure essence for each staff you want to imbue.");
            return;
        }
        Projectile proj = new Projectile(type.imbueProjectile, 70, 55, 0, 150, 0, 16, 0);
        player.addEvent(event -> {
            player.lock();
            player.animate(832);
            player.getInventory().remove(1379, toMake);
            player.sendMessage("You place the " + (toMake > 1 ? "staves" : "staff") + " on the altar.");
            event.delay(2);
            player.animate(791);
            player.graphics(186, 100, 0);
            player.sendMessage("You start channeling the altar's energy through your essence...");
            int removed = removeFromPouches(player, toMake * 4);
            if (removed < toMake * 4) {
                player.getInventory().remove(7936, (toMake * 4) - removed);
            }
            int destX = altar.x + 1;
            int destY = altar.y + 1;
            int[] offsetsX = type == DEATH ? deathOffsetsX : glowOffsetsX;
            int[] offsetsY = type == DEATH ? deathOffsetsY : glowOffsetsY;
            for (int i = 0; i < offsetsX.length; i++) {
                proj.send(destX + offsetsX[i], destY + offsetsY[i], destX, destY);
            }
            event.delay(4);
            player.sendMessage("The energy improves the " + (toMake > 1 ? "staves" : "staff") +"!");
            World.sendGraphics(type.imbueExplosion, 200, 0, destX, destY, altar.z);
            player.getStats().addXp(StatType.Runecrafting, 6.0 * toMake, true);
            event.delay(1);
            player.animate(832);
            player.getInventory().add(1391, toMake);
            player.sendMessage("You take the imbued " + (toMake > 1 ? "staves" : "staff") + ".");
            player.unlock();
        });
    }

    private static final int TIARA = 5525;
    private static final int BINDING_NECKLACE = 5521;

    static {
        for (Altars altar : values()) {
            /**
             * Altar
             */
            ObjectAction.register(altar.altarObj, "craft-rune", (player, obj) -> altar.runeConversation(player, altar));
            ObjectAction.register(altar.altarObj, "bind", (player, obj) -> altar.runeConversation(player, altar));

            /**
             * Entrance & Exit
             */
            ObjectAction.register(altar.exitObj, "use", (player, obj) -> altar.exitAltar(player, altar));
            ItemObjectAction.register(altar.talisman, altar.entranceObj, (player, item, obj) -> altar.enterAltar(player, item, altar));

            /**
             * Tiara binding
             */
            if (altar.talisman != -1 && altar.altarObj != -1) {
                ItemObjectAction.register(altar.talisman, altar.altarObj, (player, item, obj) -> altar.createTiara(player, altar));
                ItemObjectAction.register(TIARA, altar.altarObj, (player, item, obj) -> altar.createTiara(player, altar));
            }

            if (altar.entranceObj > 0 && altar.altarObj != -1) {
                ItemObjectAction.register(1379, altar.altarObj, (player, item, obj) -> imbueStaff(player, altar, obj));
            }

            /**
             * Tiara binding & Rune combining
             */
            ItemObjectAction.register(altar.altarObj, (player, item, obj) -> {
                if (altar.talisman != -1 && altar.altarObj != -1 && item.getId() == TIARA)
                    altar.createTiara(player, altar);


                for (RuneCombination runeCombination : RuneCombination.values()) {
                    if (item.getId() == runeCombination.requiredRuneId || item.getId() == runeCombination.requiredTalismanId) {
                        if (altar.altarObj == runeCombination.altar)
                            runeCombining(player, runeCombination);
                    }
                }

                if(item.getId() == altar.talisman) {
                    altar.createTiara(player, altar);
                }
            });
            /**
             * Talisman locating
             */
            if (altar.talisman != -1) {
                ItemAction.registerInventory(altar.talisman, "locate", (player, item) -> {
                    Position altarPos = altar.exitTile;
                    Position playerPos = player.getPosition();
                    StringBuilder sb = new StringBuilder();
                    if (playerPos.getY() > altarPos.getY()) {
                        sb.append("south-");
                    }
                    if (playerPos.getY() < altarPos.getY()) {
                        sb.append("north-");
                    }
                    if (playerPos.getX() > altarPos.getX()) {
                        sb.append("west");
                    } else if (playerPos.getX() < altarPos.getX()) {
                        sb.append("east");
                    } else {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    player.sendMessage("The talisman pulls towards the " + sb.toString() + ".");
                });
            }
        }
    }
}
