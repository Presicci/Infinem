package io.ruin.model.item.pet;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.utility.Broadcast;

import java.util.Arrays;
import java.util.function.Consumer;

public enum Pet {

    /**
     * Boss pets
     */
    ABYSSAL_ORPHAN(13262, 5884, 15006, null, 1000),
    BABY_MOLE(12646, 6635, null, 3000),
    CALLISTO_CUB(13178, 5558, null, 2000),
    HELLPUPPY(13247, 3099, 15007, null, 3000),
    KALPHITE_PRINCESS(12647, 6638, 6637, null, 3000),
    KALPHITE_PRINCESS_2(12654, 6637, 6638, null),
    CHAOS_ELEMENTAL(11995, 2055, null, 300),
    DAGANNOTH_PRIME(12644, 6629, null, 5000),
    DAGANNOTH_REX(12645, 6630, null, 5000),
    DAGGANOTH_SUPREME(12643, 6628, null, 5000),
    DARK_CORE(12816, 318, 8010, null, 5000),
    GENERAL_GRAARDOR(12650, 6632, null, 5000),
    JAL_NIB_REK(21291, 7675, 8009, null),
    KRIL_TSUTSAROTH(12652, 6634, null, 5000),
    KRAKEN(12655, 6640, null, 3000),
    KREEARRA(12649, 6631, null, 5000),
    MIDNIGHT(21750, 7893, 7892, null, 1000),
    NOON(21748, 7892, 7893, null),
    SMOKE_DEVIL(12648, 6639, null, 3000),
    SNAKELING_GREEN(12921, 2130, 2131, null, 4000),
    SNAKELING_RED(12939, 2131, 2132, null),
    SNAKELING_BLUE(12940, 2132, 2130, null),
    ZILYANA(12651, 6633, null, 5000),
    PRINCE_BLACK_DRAGON(12653, 6636, null, 3000),
    SCORPIAS_OFFSPRING(13181, 5561, null, 2016),
    TZREK_JAD(13225, 5893, 15008, null, 100),
    VENENATIS_SPIDERLING(13177, 5557, null, 2000),
    VETION_JR_PURPLE(13179, 5559, 5560, null, 2000),
    VETION_JR_ORANGE(13180, 5560, 5559, null),
    SKOTOS(21273, 7671, null, 65),
    VORKI(21992, 8029, null, 3000),
    LIL_ZIK(22473, 8337, null, 500),
    TZREK_ZUK(22319, 8009, 7675, null, 100),
    CORPOREAL_CRITTER(22318, 8010, 318, null),
    IKKLE_HYDRA_GREEN(22746, 8492, 8493, null, 3000),
    IKKLE_HYDRA_BLUE(22748, 8493, 8494, null),
    IKKLE_HYDRA_RED(22750, 8494, 8495, null),
    IKKLE_HYDRA_BLACK(22752, 8495, 8492, null),
    SRARACHA(23495, 2143, null),
    ABYYSALSIRE(30047, 15006, 5884, null),
    CERBERUS(30045, 15007, 3099, null),
    JALTOKJAD(30044, 15008, 5893, null),
    Necromancer(30033, 15005, null),
    Nech(30018, 15002, null),
    LAVADRAGON(30131, 15017, null),
    KAAL_KET_JOR(30211, 15022, null, 2000),

    /**
     * Skilling pets
     */
    BABY_CHINCHOMPA_GREY(13324, 6757, PetVariants.BABY_CHIN),
    BABY_CHINCHOMPA_RED(13323, 6756, PetVariants.BABY_CHIN),
    BABY_CHINCHOMPA_BLACK(13325, 6758, PetVariants.BABY_CHIN),
    BABY_CHINCHOMPA_GOLD(13326, 6759, PetVariants.BABY_CHIN),
    BEAVER(13322, 6724, null),
    HERON(13320, 6722, null),
    ROCK_GOLEM(13321, 7451, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_TIN(21187, 7452, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_COPPER(21188, 7453, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_IRON(21189, 7454, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_BLUERITE(21190, 7455, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_SILVER(21191, 7642, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_COAL(21192, 7643, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_GOLD(21193, 7644, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_MITHRIL(21194, 7645, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_GRANITE(21195, 7646, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_ADDY(21196, 7647, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_RUNE(21197, 7648, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_AMETHYST(21340, 7711, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_LOVAKITE(21358, 7739, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_ELEMENTAL(21359, 7740, PetVariants.ROCK_GOLEM),
    ROCK_GOLEM_DAEYALT(21360, 7741, PetVariants.ROCK_GOLEM),
    GIANT_SQUIRREL(20659, 7351, null),
    TANGLEROOT(20661, 7352, PetVariants.TANGLEROOT),
    TANGLEROOT_CRYSTAL(24555, 9497, PetVariants.TANGLEROOT),
    TANGLEROOT_DRAGONFRUIT(24557, 9498, PetVariants.TANGLEROOT),
    TANGLEROOT_GUAM(24559, 9499, PetVariants.TANGLEROOT),
    TANGLEROOT_REDWOOD(24563, 9501, PetVariants.TANGLEROOT),
    TANGLEROOT_WHITELILY(24561, 9500, PetVariants.TANGLEROOT),
    ROCKY(20663, 7353, PetVariants.ROCKY),
    ROCKY_RED(24847, 9852, PetVariants.ROCKY),
    ROCKY_BROWN(24849, 9853, PetVariants.ROCKY),
    RIFT_GUARDIAN_FIRE(20665, 7354, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_AIR(20667, 7355, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_MIND(20669, 7356, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_WATER(20671, 7357, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_EARTH(20673, 7358, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_BODY(20675, 7359, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_COSMIC(20677, 7360, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_CHAOS(20679, 7361, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_NATURE(20681, 7362, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_LAW(20683, 7363, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_DEATH(20685, 7364, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_SOUL(20687, 7365, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_ASTRAL(20689, 7366, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_BLOOD(20691, 7367, PetVariants.RIFT_GUARDIAN),
    RIFT_GUARDIAN_WRATH(21990, 8028, PetVariants.RIFT_GUARDIAN),
    HERBI(21509, 7760, null),

    /**
     * Misc pets
     */
    PENANCE_QUEEN(12703, 6674, null),
    BLOODHOUND(19730, 7232, null),
    CHOMPY_CHICK(13071, 4002, null),
    PHOENIX(20693, 7370, null),
    OLMLET(20851, 7520, 8201, null),
    PUPPADILE(22376, 8201, 8202, null),
    TEKTINY(22378, 8202, 8203, null),
    VANGUARD(22380, 8203, 8204, null),
    VASA(22382, 8204, 8205, null),
    VESPINE(22384, 8205, 7520, null),
    FOUNDER(30016, 15000, null),
    KRONOS_MINION(30194, 15020, null),
    SUMMER_PET(30217, 15023, null),

    /**
     * Cats
     */
    KITTEN1(1555, 5591, 1619, null),
    CAT1(1561, 1619, 5598, null),
    OVERGROWN_CAT1(1567, 5598, 5585, null),
    WILY_CAT1(6556, 5585, 1627, null),
    LAZY_CAT1(6550, 1627, 5591, null),

    KITTEN2(1556, 5592, 1620, null),
    CAT2(1562, 1620, 5599, null),
    OVERGROWN_CAT2(1568, 5599, 5584, null),
    WILY_CAT2(6555, 5584, 1626, null),
    LAZY_CAT2(6549, 1626, 5592, null),

    KITTEN3(1557, 5593, 1621, null),
    CAT3(1563, 1621, 5600, null),
    OVERGROWN_CAT3(1569, 5600, 5586, null),
    WILY_CAT3(6557, 5586, 1628, null),
    LAZY_CAT3(6551, 1628, 5593, null),

    KITTEN4(1558, 5594, 1622, null),
    CAT4(1564, 1622, 5601, null),
    OVERGROWN_CAT4(1570, 5601, 5587, null),
    WILY_CAT4(6558, 5587, 1629, null),
    LAZY_CAT4(6552, 1629, 5594, null),

    KITTEN5(1559, 5595, 1623, null),
    CAT5(1565, 1623, 5602, null),
    OVERGROWN_CAT5(1571, 5602, 5588, null),
    WILY_CAT5(6559, 5588, 1630, null),
    LAZY_CAT5(6553, 1630, 5595, null),

    KITTEN6(1560, 5596, 1624, null),
    CAT6(1566, 1624, 5603, null),
    OVERGROWN_CAT6(1572, 5603, 5589, null),
    WILY_CAT6(6560, 5589, 1631, null),
    LAZY_CAT6(6554, 1631, 5596, null),

    HELL_KITTEN(7583, 5597, 1625, null),
    HELL_CAT(7582, 1625, 5604, null),
    HELL_OVERGROWN_CAT(7581, 5604, 5590, null),
    HELL_WILY_CAT(7585, 5590, 1632, null),
    HELL_LAZY_CAT(7584, 1632, 5597, null),
    SMOLCANO(23760, 8731, 8739, null),
    YOUNGLLEF(23757, 8729, 8737, null),
    CORRUPTED_YOUNGLLEF(23759, 8730, 8738, null);

    public final int itemId, npcId, metaId;

    public int roamId;

    public final PetVariants variant;

    public final int dropAverage; //1 out of X

    Pet(int itemId, int npcId, PetVariants variant) {
        this(itemId, npcId, -1, variant, 0);
    }

    Pet(int itemId, int npcId, int metaId, PetVariants variant) {
        this(itemId, npcId, metaId, variant, 0);
    }

    Pet(int itemId, int npcId, PetVariants variant, int dropAverage) {
        this(itemId, npcId, -1, variant, dropAverage);
    }

    Pet(int itemId, int npcId, int metaId, PetVariants variant, int dropAverage) {
        this.itemId = itemId;
        this.npcId = npcId;
        this.metaId = metaId;
        this.variant = variant;
        this.dropAverage = dropAverage;
        this.roamId = findRoamId();
    }

    public static final Pet[] BABY_CHIN = new Pet[] {
            Pet.BABY_CHINCHOMPA_BLACK, Pet.BABY_CHINCHOMPA_GOLD, Pet.BABY_CHINCHOMPA_GREY, Pet.BABY_CHINCHOMPA_RED
    };

    public static final Pet[] RIFT_GUARDIAN = new Pet[] {
            Pet.RIFT_GUARDIAN_FIRE, Pet.RIFT_GUARDIAN_AIR, Pet.RIFT_GUARDIAN_MIND, Pet.RIFT_GUARDIAN_WATER,
            Pet.RIFT_GUARDIAN_EARTH, Pet.RIFT_GUARDIAN_BODY, Pet.RIFT_GUARDIAN_COSMIC, Pet.RIFT_GUARDIAN_CHAOS,
            Pet.RIFT_GUARDIAN_NATURE, Pet.RIFT_GUARDIAN_LAW, Pet.RIFT_GUARDIAN_DEATH, Pet.RIFT_GUARDIAN_SOUL,
            Pet.RIFT_GUARDIAN_ASTRAL, Pet.RIFT_GUARDIAN_BLOOD, Pet.RIFT_GUARDIAN_WRATH
    };

    public static final Pet[] TANGLEROOTS = new Pet[] {
            Pet.TANGLEROOT_GUAM, Pet.TANGLEROOT_CRYSTAL, Pet.TANGLEROOT, Pet.TANGLEROOT_REDWOOD,
            Pet.TANGLEROOT_DRAGONFRUIT, Pet.TANGLEROOT_WHITELILY
    };

    public static final int[] TANGLEROOT_SEEDS = new int[] {
            Items.GUAM_SEED, 23661, Items.ACORN, 22871, 22877, 22887
    };

    public static final Pet[] ROCK_GOLEMS = new Pet[] {
            ROCK_GOLEM, ROCK_GOLEM_TIN, ROCK_GOLEM_COPPER, ROCK_GOLEM_IRON, ROCK_GOLEM_BLUERITE,
            ROCK_GOLEM_SILVER, ROCK_GOLEM_COAL, ROCK_GOLEM_GOLD, ROCK_GOLEM_MITHRIL,
            ROCK_GOLEM_GRANITE, ROCK_GOLEM_ADDY, ROCK_GOLEM_RUNE, ROCK_GOLEM_AMETHYST,
            ROCK_GOLEM_LOVAKITE, ROCK_GOLEM_ELEMENTAL, ROCK_GOLEM_DAEYALT,
    };

    public static final int[] ROCK_GOLEM_ORES = new int[] {
            1480, Items.TIN_ORE, Items.COPPER_ORE, Items.IRON_ORE, Items.BLURITE_ORE,
            Items.SILVER_ORE, Items.COAL, Items.GOLD_ORE, Items.MITHRIL_ORE,
            Items.GRANITE_2KG, Items.ADAMANTITE_ORE, Items.RUNITE_ORE, 21347,
            Items.LOVAKITE_ORE, Items.ELEMENTAL_ORE, Items.DAEYALT_ORE
    };

    public static final Pet[] ROCKY_VARIANTS = new Pet[] {
            ROCKY, ROCKY_RED, ROCKY_BROWN
    };

    public static final int[] ROCKY_BERRIES = new int[] {
            Items.WHITE_BERRIES, Items.REDBERRIES, Items.POISON_IVY_BERRIES
    };

    public Pet[] getVariantArray() {
        switch (variant) {
            case BABY_CHIN:
                return BABY_CHIN;
            case RIFT_GUARDIAN:
                return RIFT_GUARDIAN;
            case TANGLEROOT:
                return TANGLEROOTS;
            case ROCK_GOLEM:
                return ROCK_GOLEMS;
        }
        return new Pet[] { this };
    }

    private int findRoamId() {
        NPCDef baseDef = NPCDef.get(npcId);
        for (NPCDef def : NPCDef.cached.values()) {
            if (def == null || def.id == baseDef.id || def.name == null || def.options == null || !def.name.equalsIgnoreCase(baseDef.name)) {
                continue;
            }
            if (!Arrays.equals(def.options, baseDef.options) && !baseDef.hasOption("metamorphosis"))
                continue;
            if (!Arrays.equals(def.models, baseDef.models))
                continue;
            return def.id;
        }
        return npcId;
    }

    public void spawn(Player player) {
        NPC npc = new NPC(npcId);
        npc.ownerId = player.getUserId();
        npc.spawn(player.getAbsX(), player.getAbsY(), player.getHeight());
        npc.face(player);

        player.petNPC = npc;
        player.pet = this;
        Config.PET_NPC_INDEX.set(player, npc.getIndex());

        npc.addEvent(e -> {
            while (player.isOnline()) {
                if (player.getCombat().isDead() || player.getMovement().isTeleportQueued()) {
                    e.delay(1);
                    continue;
                }
                if (player.callPet || !npc.getPosition().isWithinDistance(player.getPosition())) {
                    player.callPet = false;
                    npc.getMovement().teleport(player.getAbsX(), player.getAbsY(), player.getHeight());
                    e.delay(1);
                    continue;
                }
                if (player.hidePet) {
                    player.hidePet = false;
                    npc.setHidden(true);
                    e.delay(1);
                    continue;
                }
                if (player.showPet) {
                    player.showPet = false;
                    npc.setHidden(false);
                    e.delay(1);
                    continue;
                }
                int destX, destY;
                if (player.getMovement().hasMoved()) {
                    destX = player.getMovement().lastFollowX;
                    destY = player.getMovement().lastFollowY;
                } else {
                    destX = player.getMovement().followX;
                    destY = player.getMovement().followY;
                }
                if (destX == -1 || destY == -1)
                    DumbRoute.step(npc, player, 1);
                else if (!npc.isAt(destX, destY))
                    DumbRoute.step(npc, destX, destY);
                e.delay(1);
            }
            npc.remove();
        });
    }

    public void unlock(Player player) {
        /*  Lets us do duplicate protection for pets with different forms, like the rift
            guardian or baby chinchompa */
        if (variant != null) {
            for (Pet pet : getVariantArray()) {
                if (pet.variant != variant) {
                    continue;
                }
                if (player.pet == pet || player.getInventory().hasId(pet.itemId) || player.getBank().hasId(pet.itemId)
                        || (player.getCurrentHouse().getPetContainer().getItems() != null && player.getCurrentHouse().getPetContainer().hasId(pet.itemId))) {
                    player.sendMessage("<col=FF0000>You have a funny feeling like you would have been followed...");
                    return;
                }
            }
        }
        if (player.pet == this || player.getInventory().hasId(itemId) || player.getBank().hasId(itemId)
                || (player.getCurrentHouse().getPetContainer().getItems() != null && player.getCurrentHouse().getPetContainer().hasId(itemId))) {
            player.sendMessage("<col=FF0000>You have a funny feeling like you would have been followed...");
            return;
        }
        if (player.pet == null) {
            spawn(player);
            unlockBroadcast(player);
            player.sendMessage("<col=FF0000>You have a funny feeling like you're being followed.");
            return;
        }
        if (player.getInventory().add(itemId, 1) == 1) {
            unlockBroadcast(player);
            player.sendMessage("<col=FF0000>You feel something weird sneaking into your backpack.");
            return;
        }
        if (player.getBank().add(itemId, 1) == 1) {
            unlockBroadcast(player);
            player.sendMessage("<col=FF0000>You have unlocked a new pet! It has been deposited into your bank.");
            return;
        }
    }

    private void unlockBroadcast(Player player) {
        Broadcast.WORLD.sendNews(player.getName() + " just unlocked the following pet: " + ItemDef.get(itemId).name);
    }

    static {
        for (Pet pet : values()) {
            ItemDef.get(pet.itemId).pet = pet;
            NPCDef.get(pet.npcId).occupyTiles = false;
            NPCDef.get(pet.npcId).ignoreOccupiedTiles = true;
            /**
             * Drop
             */
            ItemAction.registerInventory(pet.itemId, "drop", (player, item) -> {
                if (player.pet != null) {
                    player.sendFilteredMessage("You can only have one follower at a time.");
                    return;
                }
                item.remove();
                pet.spawn(player);
            });
            /**
             * Pickup
             */
            NPCAction.register(pet.npcId, "pick-up", (player, npc) -> pickup(player, npc, pet));
            /**
             * Metamorphosis
             */
            NPCAction.register(pet.npcId, "metamorphosis", (player, npc) -> {
                if (Config.PET_NPC_INDEX.get(player) != npc.getIndex())
                    return;
                player.faceTemp(npc);
                if (pet == BABY_CHINCHOMPA_BLACK || pet == BABY_CHINCHOMPA_GREY || pet == BABY_CHINCHOMPA_RED) {
                    if (Random.rollDie(10000, 1)) {
                        npc.transform(BABY_CHINCHOMPA_GOLD.npcId); // https://youtu.be/6d-BGPO5SLk
                        npc.face(player);
                        return;
                    } else {
                        Pet nextChin = (pet == BABY_CHINCHOMPA_BLACK ? BABY_CHINCHOMPA_GREY : pet == BABY_CHINCHOMPA_GREY ? BABY_CHINCHOMPA_RED : BABY_CHINCHOMPA_BLACK);
                        npc.transform(nextChin.npcId);
                        npc.face(player);
                        return;
                    }
                }
                if (pet == BABY_CHINCHOMPA_GOLD) {
                    player.dialogue(
                            new MessageDialogue("<col=ff0000>Warning:</col> Metamorphosis will result in you losing your<br>golden chinchompa. Are you sure you want to do this?").lineHeight(24),
                            new OptionsDialogue(
                                    new Option("Yes", () -> npc.transform(BABY_CHINCHOMPA_GREY.npcId)),
                                    new Option("No")
                            )
                    );
                    return;
                }
                if (pet == CERBERUS) {
                    if (!player.cerberusMetamorphisis) {
                        player.sendMessage("You need to use Cerberus paws on this first before you can use metamorphosis.");
                        return;
                    }
                }
                if (pet == ABYSSAL_ORPHAN) {
                    if (!player.abyssalSireMetamorphisis) {
                        player.sendMessage("You need to use a Sires tendral on this first before you can use metamorphosis.");
                        return;
                    }
                }
                if (pet == TZREK_JAD) {
                    if (!player.infernalJadMetamorphisis) {
                        player.sendMessage("You need to use Infernal shard on this first before you can use metamorphosis.");
                        return;
                    }
                }
                if (pet.metaId == -1)
                    return;
                npc.transform(pet.metaId);
                npc.face(player);
            });
            /**
             * Age (Custom for cats!!)
             */
            NPCAction.register(pet.npcId, "age", (player, npc) -> {
                if (Config.PET_NPC_INDEX.get(player) != npc.getIndex())
                    return;
                if (pet.metaId == -1)
                    return;
                player.faceTemp(npc);
                npc.transform(pet.metaId);
            });
            /**
             * Dialogue
             */
            Consumer<Player> talkAction = getTalkAction(pet);

            if (talkAction != null) {
                NPCAction.register(pet.npcId, "talk-to", (player, npc) -> talkAction.accept(player));
                NPCAction.register(pet.roamId, "talk-to", (player, npc) -> talkAction.accept(player));
            }
        }
        ItemNPCAction.register(30043, HELLPUPPY.npcId, (player, item, npc) -> {
            if (!player.cerberusMetamorphisis) {
                player.sendMessage("You've unlocked the cerberus metamorphosis for your hell puppy.");
                item.remove(1);
            } else {
                player.sendMessage("You've already unlocked the cerberus metamorphosis.");
            }
        });
        ItemNPCAction.register(30042, ABYSSAL_ORPHAN.npcId, (player, item, npc) -> {
            if (!player.abyssalSireMetamorphisis) {
                player.sendMessage("You've unlocked the abyssal sire metamorphosis for your Abyssal orphan.");
                item.remove(1);
            } else {
                player.sendMessage("You've already unlocked the abyssal sire metamorphosis.");
            }
        });
        ItemNPCAction.register(30041, TZREK_JAD.npcId, (player, item, npc) -> {
            if (!player.infernalJadMetamorphisis) {
                player.sendMessage("You've unlocked the JalTok-Jad metamorphosis for your Tzrek Jad.");
                item.remove(1);
            } else {
                player.sendMessage("You've already unlocked the JalTok-Jad metamorphosis.");
            }
        });
        //  Tangleroot recoloring handling
        for (Pet pet : TANGLEROOTS) {
            for (int index = 0; index < TANGLEROOTS.length; index++) {
                final int indexF = index;
                ItemNPCAction.register(TANGLEROOT_SEEDS[index], pet.npcId, (player, item, npc) -> {
                    if (pet != TANGLEROOTS[indexF]) {
                        player.getInventory().remove(TANGLEROOT_SEEDS[indexF], 1);
                        npc.transform(TANGLEROOTS[indexF].npcId);
                        player.sendMessage("Tangleroot eats the " + ItemDef.get(TANGLEROOT_SEEDS[indexF]).name + " and takes on a new appearance.");
                    } else {
                        player.sendMessage("Tangleroot has already consumed his daily intake of " + ItemDef.get(TANGLEROOT_SEEDS[indexF]).name + "s.");
                    }
                });
            }
        }

        //  ROCKY recoloring handling
        for (Pet pet : ROCKY_VARIANTS) {
            for (int index = 0; index < ROCKY_VARIANTS.length; index++) {
                final int indexF = index;
                ItemNPCAction.register(ROCKY_BERRIES[index], pet.npcId, (player, item, npc) -> {
                    if (pet != ROCKY_VARIANTS[indexF]) {
                        String oldName = npc.getDef().name;
                        player.getInventory().remove(ROCKY_BERRIES[indexF], 1);
                        npc.transform(ROCKY_VARIANTS[indexF].npcId);
                        player.sendMessage(oldName + " happily eats the " + ItemDef.get(ROCKY_BERRIES[indexF]).name + " and magically turns into his cousin, " + npc.getDef().name + ".");
                    } else {
                        player.sendMessage(npc.getDef().name + " doesn't want to eat any more " + ROCKY_BERRIES[indexF] + ".");
                    }
                });
            }
        }

        //  Rock golem recoloring handling
        for (Pet pet : ROCK_GOLEMS) {
            for (int index = 0; index < ROCK_GOLEMS.length; index++) {
                final int indexF = index;
                ItemNPCAction.register(ROCK_GOLEM_ORES[index], pet.npcId, (player, item, npc) -> {
                    if (pet != ROCK_GOLEMS[indexF]) {
                        player.getInventory().remove(ROCK_GOLEM_ORES[indexF], 1);
                        npc.transform(ROCK_GOLEMS[indexF].npcId);
                        player.sendMessage("Your rock golem eats the " + ItemDef.get(ROCK_GOLEM_ORES[indexF]).name + " and takes on a new appearance.");
                    } else {
                        player.sendMessage("Your rock golem has already taken that appearence.");
                    }
                });
                // Multiple types of granite
                if (ROCK_GOLEM_ORES[index] == Items.GRANITE_2KG) {
                    ItemNPCAction.register(Items.GRANITE_5KG, pet.npcId, (player, item, npc) -> {
                        if (pet != ROCK_GOLEMS[indexF]) {
                            player.getInventory().remove(Items.GRANITE_5KG, 1);
                            npc.transform(ROCK_GOLEMS[indexF].npcId);
                            player.sendMessage("Your rock golem eats the " + ItemDef.get(Items.GRANITE_5KG).name + " and takes on a new appearance.");
                        } else {
                            player.sendMessage("Your rock golem has already taken that appearence.");
                        }
                    });
                    ItemNPCAction.register(Items.GRANITE_500G, pet.npcId, (player, item, npc) -> {
                        if (pet != ROCK_GOLEMS[indexF]) {
                            player.getInventory().remove(Items.GRANITE_500G, 1);
                            npc.transform(ROCK_GOLEMS[indexF].npcId);
                            player.sendMessage("Your rock golem eats the " + ItemDef.get(Items.GRANITE_500G).name + " and takes on a new appearance.");
                        } else {
                            player.sendMessage("Your rock golem has already taken that appearence.");
                        }
                    });
                }
            }
        }
    }

    public static boolean pickup(Player player, NPC npc, Pet pet) {
        if (npc.ownerId != player.getUserId()) {
            if (!player.isAdmin()) {
                player.sendMessage("That's not your pet.");
                return false;
            } else {
                Player player2 = World.getPlayer(npc.ownerId, true);
                player.startEvent(e -> {
                    player.lock(LockType.FULL_REGULAR_DAMAGE);
                    player.faceTemp(npc);
                    player.animate(827);
                    e.delay(1);
                    npc.remove();
                    player2.pet = null;
                    Config.PET_NPC_INDEX.set(player2, -1);
                    player.getInventory().add(pet.itemId, 1);
                    player.unlock();
                    player2.animate(857);
                    player2.forceText("Huh.. Where did my pet go??");
                });

            }
        }
        if (player.getInventory().isFull()) {
            player.sendFilteredMessage("You don't have enough inventory space to do that.");
            return false;
        }
        if(player.joinedTournament) {
            player.sendFilteredMessage("You can't pickup your pet while inside a tournament!");
            return false;
        }
        player.startEvent(e -> {
            player.lock(LockType.FULL_REGULAR_DAMAGE);
            player.faceTemp(npc);
            player.animate(827); //this is custom
            e.delay(1);
            npc.remove();
            player.pet = null;
            Config.PET_NPC_INDEX.set(player, -1);
            player.getInventory().add(pet.itemId, 1);
            player.unlock();
        });
        return true;
    }

    private static Consumer<Player> getTalkAction(Pet pet) {
        switch (pet) {
            case ABYSSAL_ORPHAN: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "You killed my father."),
                        new ActionDialogue(() -> {
                            if (Random.rollDie(2, 1)) {
                                player.dialogue(
                                        new PlayerDialogue("Yeah, don't take it personally."),
                                        new NPCDialogue(pet.npcId, "In his dying moment, my father poured his last ounce of strength into my creation. " +
                                                "My being is formed from his remains."),
                                        new NPCDialogue(pet.npcId, "When your own body is consumed to nourish the Nexus, and an army of scions arises " +
                                                "from your corpse, I trust you will not take it personally either."));
                            } else {
                                if (player.getAppearance().isMale()) {
                                    player.dialogue(
                                            new PlayerDialogue("No, I am your father."),
                                            new NPCDialogue(pet.npcId, "No you're not."));
                                } else {
                                    player.dialogue(
                                            new PlayerDialogue("No, I am your father."),
                                            new NPCDialogue(pet.npcId, "Human biology may be unfamiliar to me, but nevertheless I doubt that very much."));
                                }
                            }
                        }));
            }
            case BABY_MOLE: {
                return player -> player.dialogue(
                        new PlayerDialogue("Hey, Mole. How is life above ground?"),
                        new NPCDialogue(pet.npcId, "Well, the last time I was above ground, I was having to contend with people throwing snow at some weird yellow duck in my park."),
                        new PlayerDialogue("Why were they doing that?"),
                        new NPCDialogue(pet.npcId, "No idea, I didn't stop to ask as an angry mob was closing in on them pretty quickly."),
                        new PlayerDialogue("Sounds awful."),
                        new NPCDialogue(pet.npcId, "Anyway, keep Molin'!"));
            }
            case HELLPUPPY: {
                return player -> {
                    int random = Random.get(1, 5);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("How many souls have you devoured?"),
                                new NPCDialogue(pet.npcId, "None."),
                                new PlayerDialogue("Aww p-"),
                                new NPCDialogue(pet.npcId, "Yet."),
                                new PlayerDialogue("Oh.")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("I wonder if I need to invest in a trowel when I take you out for a walk."),
                                new NPCDialogue(pet.npcId, "More like a shovel.")
                        );
                    } else if (random == 3) {
                        player.dialogue(
                                new PlayerDialogue("Why are the hot dogs shivering?"),
                                new NPCDialogue(pet.npcId, "Grrrrr..."),
                                new PlayerDialogue("Because they were served-"),
                                new NPCDialogue(pet.npcId, "GRRRRRR..."),
                                new PlayerDialogue("-with.. chilli?")
                        );
                    } else if (random == 4) {
                        player.dialogue(
                                new PlayerDialogue("Hell yeah! Such a cute puppy."),
                                new NPCDialogue(pet.npcId, "Silence mortal! OR I'll eat your soul."),
                                new PlayerDialogue("Would that go well with lemon?"),
                                new NPCDialogue(pet.npcId, "Grrr...")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("What a cute puppy, how nice to meet you."),
                                new NPCDialogue(pet.npcId, "It'd be nice to meat you too..."),
                                new PlayerDialogue("Urk... nice doggy."),
                                new NPCDialogue(pet.npcId, "Grrr...")
                        );
                    }
                };
            }
            case KALPHITE_PRINCESS:
            case KALPHITE_PRINCESS_2: {
                return player -> player.dialogue(
                        new PlayerDialogue("What is it with your kind and potato cactus?"),
                        new NPCDialogue(pet.npcId, "Truthfully?"),
                        new PlayerDialogue("Yeah, please."),
                        new NPCDialogue(pet.npcId, "Soup. We make a fine soup with it."),
                        new PlayerDialogue("Kalphites can cook?"),
                        new NPCDialogue(pet.npcId, "Nah, we just collect it and put it there because we know fools like yourself will " +
                                "come down looking for it then inevitably be killed by my mother."),
                        new PlayerDialogue("Evidently not, that's how I got you!"),
                        new NPCDialogue(pet.npcId, "Touche'")
                );
            }
            case ROCK_GOLEM: {
                return player -> player.dialogue(
                        new PlayerDialogue("So you're made entirely of rocks?"),
                        new NPCDialogue(pet.npcId, "Not quite, my body is formed mostly of minerals."),
                        new PlayerDialogue("Aren't minerals just rocks?"),
                        new NPCDialogue(pet.npcId, "No, rocks are rocks, minerals are minerals. I am formed from minerals."),
                        new PlayerDialogue("But you're a Rock Golem...")
                );
            }
            case ROCK_GOLEM_TIN: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "I feel strangely emotionless and empty. Maybe I should feel sad about it, but I can't."),
                        new PlayerDialogue("You can't feel sad?"),
                        new NPCDialogue(pet.npcId, "Correct. Now, if I only had a heart..."),
                        new PlayerDialogue("I'm not sure it works like that around here.")
                );
            }
            case ROCK_GOLEM_COPPER: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "I have an idea for a song."),
                        new PlayerDialogue("Oh?"),
                        new NPCDialogue(pet.npcId, "Copper-copper-copper Cophelia - you come and go, you come and go..."),
                        new PlayerDialogue("Mmmhmm.")
                );
            }
            case ROCK_GOLEM_IRON: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Truth is, I am Iron Golem."),
                        new PlayerDialogue("I can see that.")
                );
            }
            case ROCK_GOLEM_BLUERITE: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "I have an idea for a song."),
                        new PlayerDialogue("Oh?"),
                        new NPCDialogue(pet.npcId, "I'm blue, da-ba-dee da-ba-da."),
                        new PlayerDialogue("I can see that.")
                );
            }
            case ROCK_GOLEM_SILVER: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Oh dear, I've gone all pale."),
                        new PlayerDialogue("Are you okay?"),
                        new NPCDialogue(pet.npcId, "It must be something I ate.")
                );
            }
            case ROCK_GOLEM_DAEYALT: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Did you ever hear the tragedy of Queen Efaritay the Fair?"),
                        new PlayerDialogue("No?"),
                        new NPCDialogue(pet.npcId, "I thought not."),
                        new NPCDialogue(pet.npcId, "It's not a story the Myreque would tell you.")
                );
            }
            case ROCK_GOLEM_COAL: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "So near and yet so far... if my atoms were arranged a little differently, I could be diamond."),
                        new PlayerDialogue("Would you enjoy being a diamond?"),
                        new NPCDialogue(pet.npcId, "I expect I would, until someone tried chipping bits off me with a chisel. I'm probably safer as coal.")
                );
            }
            case ROCK_GOLEM_ELEMENTAL: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "I am so in my element right now."),
                        new PlayerDialogue("Sigh...")
                );
            }
            case ROCK_GOLEM_GOLD: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "I'm totally showing the bling today."),
                        new PlayerDialogue("That's nice for you."),
                        new NPCDialogue(pet.npcId, "A little. Though sometimes I just feel like I'm being used as a status symbol, and no-one appreciates me for me."),
                        new PlayerDialogue("Oh dear."),
                        new NPCDialogue(pet.npcId, "Don't worry. At least I look good.")
                );
            }
            case ROCK_GOLEM_GRANITE: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "No-one appreciates granite."),
                        new PlayerDialogue("Why do you say that?"),
                        new NPCDialogue(pet.npcId, "I know how it works. No-one actually wants granite. They just chop us up and throw our pieces on the floor."),
                        new PlayerDialogue("Oh, I can see that must be upsetting for you."),
                        new NPCDialogue(pet.npcId, "When you've seen your relatives cut into pieces, with their severed limbs cast aside like junk, THEN you will understand how I feel."),
                        new PlayerDialogue("I'll bear it in mind.")
                );
            }
            case ROCK_GOLEM_MITHRIL: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "I feel sad today. Very blue."),
                        new PlayerDialogue("Oh dear."),
                        new NPCDialogue(pet.npcId, "No-one understands me."),
                        new PlayerDialogue("Why not?"),
                        new NPCDialogue(pet.npcId, "Because argle gargle gooble goop."),
                        new PlayerDialogue("... I don't understand you either."),
                        new NPCDialogue(pet.npcId, "*sigh*")
                );
            }
            case ROCK_GOLEM_LOVAKITE: {
                return player -> player.dialogue(
                        new PlayerDialogue("So how do you pronounce Lovakengj?"),
                        new NPCDialogue(pet.npcId, "Silly human, it's pronounced Lova-Kane."),
                        new NPCDialogue(pet.npcId, "How did you not know that?"),
                        new PlayerDialogue("You know, it really isn't that obvious.")
                );
            }
            case ROCK_GOLEM_ADDY: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "I may be green, but I'm not an environmentalist."),
                        new PlayerDialogue("Why not?"),
                        new NPCDialogue(pet.npcId, "There's no need. Whatever you may have read, even coal is a renewable energy source - just wait a minute and the rocks respawn."),
                        new NPCDialogue(pet.npcId, "You can burn as much as you like, too, without needing to worry about it affecting the climate - we don't have a lot of weather here."),
                        new PlayerDialogue("That's handy."),
                        new NPCDialogue(pet.npcId, "Yes, I pity anyone whose world doesn't work like this one. I don't know how they can possibly cope.")
                );
            }
            case ROCK_GOLEM_RUNE: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "I'm confused. It takes incredible skill to smith anything from my ore, yet the items you'd get are terribly mediocre."),
                        new PlayerDialogue("Is that something that worries you?"),
                        new NPCDialogue(pet.npcId, "It just feels like my world doesn't make sense, sometimes. But I suppose it's always been like this, and I've got used to it.")
                );
            }
            case ROCK_GOLEM_AMETHYST: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Your world is amazing. I truly am in awe."),
                        new PlayerDialogue("Did you just make an ore joke?"),
                        new NPCDialogue(pet.npcId, "Maybe..."),
                        new PlayerDialogue("Well it was awful."),
                        new NPCDialogue(pet.npcId, "Ha! Now you're making them as well."),
                        new PlayerDialogue("Are we really doing this? Amethyst isn't an ore anyway."),
                        new NPCDialogue(pet.npcId, "I can dream, "  + player.getName() + "!")
                );
            }
            case HERON: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Hop inside my mouth if you want to live!"),
                        new PlayerDialogue("I'm not falling for that...I'm not a fish. I've got more foresight than that.")
                );
            }
            case BEAVER: {
                return player -> player.dialogue(
                        new PlayerDialogue("How much wood could a woodchuck chuck if a woodchuck could chuck wood?"),
                        new NPCDialogue(pet.npcId, "Approximately 32,768 depending on his Woodcutting level.")
                );
            }
            case DARK_CORE: {
                return player -> player.dialogue(
                        new PlayerDialogue("Got any sigils for me?"),
                        new MessageDialogue("The Core shakes its head."),
                        new PlayerDialogue("Dammit Core-al!"),
                        new PlayerDialogue("Let's bounce!")
                );
            }
            case CORPOREAL_CRITTER: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "I'm hungry!"),
                        new PlayerDialogue("How hungry?"),
                        new NPCDialogue(pet.npcId, "I'm empty to the core!"),
                        new PlayerDialogue("Would an apple do?"),
                        new NPCDialogue(pet.npcId, "What is apple?"),
                        new PlayerDialogue("Something where you eat the outside and throw the core away!")
                );
            }
            case SNAKELING_RED:
            case SNAKELING_GREEN:
            case SNAKELING_BLUE: {
                return player -> player.dialogue(
                        new PlayerDialogue("Hey little snake!"),
                        new NPCDialogue(pet.npcId, "Soon, Zulrah shall establish dominion over this plane."),
                        new PlayerDialogue("Wanna play fetch?"),
                        new NPCDialogue(pet.npcId, "Submit to the almighty Zulrah."),
                        new PlayerDialogue("Walkies? Or slidies...?"),
                        new NPCDialogue(pet.npcId, "Zulrah's wilderness as a God will soon be demonstrated!"),
                        new PlayerDialogue("I give up...")
                );
            }
            case CHAOS_ELEMENTAL: {
                return player -> player.dialogue(
                        new PlayerDialogue("Is it true a level 3 skiller caught one of your siblings?"),
                        new NPCDialogue(pet.npcId, "Yes, they killed my mummy, kidnapped my brother, smiled about it and went to sleep."),
                        new PlayerDialogue("Aww, well you have me now! I shall call you Squishy and you shall be mine and you shall be my Squishy."),
                        new PlayerDialogue("Come on, Squishy come on, little Squishy!")
                );
            }
            case PRINCE_BLACK_DRAGON: {
                return player -> player.dialogue(
                        new PlayerDialogue("Shouldn't a prince only have two heads?"),
                        new NPCDialogue(pet.npcId, "Why is that?"),
                        new PlayerDialogue("Well, a standard Black dragon has one, the King has three so in between must have two?"),
                        new NPCDialogue(pet.npcId, "You're overthinking this.")
                );
            }
            case CALLISTO_CUB: {
                return player -> player.dialogue(
                        new PlayerDialogue("Why the grizzly face?"),
                        new NPCDialogue(pet.npcId, "You're not funny..."),
                        new PlayerDialogue("You should get in the... sun more."),
                        new NPCDialogue(pet.npcId, "You're really not funny..."),
                        new PlayerDialogue("One second, let me take a picture of you with my... kodiak camera."),
                        new NPCDialogue(pet.npcId, "....."),
                        new PlayerDialogue("Feeling... blue?"),
                        new NPCDialogue(pet.npcId, "If you don't stop I'm going to leave some... brown.. at your feet, human.")
                );
            }
            case SCORPIAS_OFFSPRING: {
                return player -> player.dialogue(
                        new PlayerDialogue("At night time, if I were to hold ultraviolet light over you, would you glow?"),
                        new NPCDialogue(pet.npcId, "Two things wrong there, human."),
                        new PlayerDialogue("Oh?"),
                        new NPCDialogue(pet.npcId, "One, When has it ever been night time here?"),
                        new NPCDialogue(pet.npcId, "Two, When have you ever seen ultraviolet light around here?"),
                        new PlayerDialogue("Hm..."),
                        new NPCDialogue(pet.npcId, "In answer to your question though. Yes I, like every scorpion, would glow.")
                );
            }
            case VENENATIS_SPIDERLING: {
                return player -> player.dialogue(
                        new PlayerDialogue("It's a damn good thing I don't have arachnophobia."),
                        new NPCDialogue(pet.npcId, "We're misunderstood. Without us in your house, you'd be infested with flies and other REAL nasties."),
                        new PlayerDialogue("Thanks for that enlightening fact."),
                        new NPCDialogue(pet.npcId, "Everybody gets one.")
                );
            }
            case TZREK_JAD: {
                return player -> {
                    int random = Random.get(1, 2);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("Do you miss your people?"),
                                new NPCDialogue(pet.npcId, "Mej-TzTok-Jad Kot-Kl!"),
                                new PlayerDialogue("No.. I don't think so."),
                                new NPCDialogue(pet.npcId, "Jal-Zek Kl?"),
                                new PlayerDialogue("No, no, I wouldn't hurt you.")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Are you hungry?"),
                                new NPCDialogue(pet.npcId, "Kl-Kra!"),
                                new PlayerDialogue("Ooookay...")
                        );
                    }
                };
            }
            case DAGANNOTH_PRIME: {
                return player -> player.dialogue(
                        new PlayerDialogue("So despite there being three kings, you're clearly the leader, right?"),
                        new NPCDialogue(pet.npcId, "Definitely."),
                        new PlayerDialogue("I'm glad I got you as a pet."),
                        new NPCDialogue(pet.npcId, "Ugh. Human, I'm not a pet."),
                        new PlayerDialogue("Stop following me then."),
                        new NPCDialogue(pet.npcId, "I can't seem to stop."),
                        new PlayerDialogue("Pet.")
                );
            }
            case DAGANNOTH_REX: {
                return player -> player.dialogue(
                        new PlayerDialogue("Do you have any berserker rings?"),
                        new NPCDialogue(pet.npcId, "Nope."),
                        new PlayerDialogue("Are you sure?"),
                        new NPCDialogue(pet.npcId, "Yes?"),
                        new PlayerDialogue("So, if I tipped you upside down and shook you, you'd not drop any berserker rings?"),
                        new NPCDialogue(pet.npcId, "Nope."),
                        new PlayerDialogue("What if I endlessly killed your father for weeks on end, would I get one then?"),
                        new NPCDialogue(pet.npcId, "Been done by someone, nope.")
                );
            }
            case DAGGANOTH_SUPREME: {
                return player -> player.dialogue(
                        new PlayerDialogue("Hey, so err... I kind of own you now."),
                        new NPCDialogue(pet.npcId, "Tsssk. Next time you enter those caves, human, my father will be having words."),
                        new PlayerDialogue("Maybe next time I'll add your brothers to my collection.")
                );
            }
            case GENERAL_GRAARDOR: {
                return player -> player.dialogue(
                        new PlayerDialogue("Not sure if this is going to be worth my time but... how are you?"),
                        new NPCDialogue(pet.npcId, "SFudghoigdfpDSOPGnbSOBNfdbdnopbdnopbddfnopdfpofhdA RRRGGGGH"),
                        new PlayerDialogue("Nope. Not worth it.")
                );
            }
            case KRIL_TSUTSAROTH: {
                return player -> player.dialogue(
                        new PlayerDialogue("How's life in the light?"),
                        new NPCDialogue(pet.npcId, "Burns slightly."),
                        new PlayerDialogue("You seem much nicer than your father. He's mean."),
                        new NPCDialogue(pet.npcId, "If you were stuck in a very dark cave for centuries you'd be pretty annoyed too."),
                        new PlayerDialogue("I guess."),
                        new NPCDialogue(pet.npcId, "He's actually quite mellow really."),
                        new PlayerDialogue("Uh... yeah.")
                );
            }
            case KREEARRA: {
                return player -> player.dialogue(
                        new PlayerDialogue("Huh... that's odd... I thought that would be big news."),
                        new NPCDialogue(pet.npcId, "You thought what would be big news?"),
                        new PlayerDialogue("Well there seems to be an absence of a certain ornithological piece: a headline regarding mass awareness of a certain avian variety."),
                        new NPCDialogue(pet.npcId, "What are you talking about?"),
                        new PlayerDialogue("Oh have you not heard? It was my understanding that everyone had heard..."),
                        new NPCDialogue(pet.npcId, "Heard wha...... OH NO!!!!?!?!!?!"),
                        new PlayerDialogue("OH WELL THE BIRD, BIRD, BIRD, BIRD BIRD IS THE WORD. OH WELL THE BIRD, BIRD, BIRD, BIRD BIRD IS THE WORD."),
                        new NPCDialogue(pet.npcId, "There's a slight pause as Kree'Arra Jr. goes stiff.")
                );
            }
            case KRAKEN: {
                return player -> player.dialogue(
                        new PlayerDialogue("What's Kraken?"),
                        new NPCDialogue(pet.npcId, "Not heard that one before."),
                        new PlayerDialogue("How are you actually walking on land?"),
                        new NPCDialogue(pet.npcId, "We have another leg, just below the center of our body that we use to move" +
                                " across solid surfaces."),
                        new PlayerDialogue("That's... interesting.")
                );
            }
            case ZILYANA: {
                return player -> player.dialogue(
                        new PlayerDialogue("FIND THE GODSWORD!"),
                        new NPCDialogue(pet.npcId, "FIND THE GODSWORD!"),
                        new MessageDialogue("If the player has a Saradomin godsword"),
                        new PlayerDialogue("I FOUND THE GODSWORD!"),
                        new NPCDialogue(pet.npcId, "GOOD!!!!!")
                );
            }
            case SMOKE_DEVIL: {
                return player -> player.dialogue(
                        new PlayerDialogue("Your kind comes in three different sizes?"),
                        new NPCDialogue(pet.npcId, "Four, actually."),
                        new PlayerDialogue("Wow. Whoever created you wasn't very creative. You're just resized versions of one another!")
                );
            }
            case VETION_JR_ORANGE:
            case VETION_JR_PURPLE: {
                return player -> player.dialogue(
                        new PlayerDialogue("Who is the true load and king of the lands?"),
                        new NPCDialogue(pet.npcId, "THe mighty heir and lord of the Wilderness."),
                        new PlayerDialogue("Where is he? Why hasn't he lifted your burden?"),
                        new NPCDialogue(pet.npcId, "I have not fulfilled my purpose."),
                        new PlayerDialogue("What is your purpose?"),
                        new NPCDialogue(pet.npcId, "Not what is, what was. A great war tore this land apart and, for my failings in protecting" +
                                " this land, I carry the burden of its waste.")
                );
            }
            case BABY_CHINCHOMPA_BLACK:
            case BABY_CHINCHOMPA_GREY:
            case BABY_CHINCHOMPA_RED: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Squeak squeak!")
                );
            }
            case BABY_CHINCHOMPA_GOLD: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Squeaka squeaka!")
                );
            }
            case GIANT_SQUIRREL: {
                return player -> {
                    int random = Random.get(1, 3);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("So how come you are so agile?"),
                                new NPCDialogue(pet.npcId, "If you were so nutty about nuts, maybe you would understand the great lengths we go to!"));
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("What's up with all the squirrel fur? I guess fleas need a home too."),
                                new NPCDialogue(pet.npcId, "You're pushing your luck! Stop it or you'll face my squirrely wrath.")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Did you ever notice how big squirrels' teeth are?"),
                                new NPCDialogue(pet.npcId, "No..."),
                                new PlayerDialogue("You could land a gnome glider on those things!"),
                                new NPCDialogue(pet.npcId, "Watch it, I'll crush your nuts!")
                        );
                    }
                };
            }
            case TANGLEROOT: {
                return player -> {
                    int random = Random.get(1, 3);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("How are you doing today?"),
                                new NPCDialogue(pet.npcId, "I am tangleroot!")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("I am Tangleroot."),
                                new NPCDialogue(pet.npcId, "I am " + player.getName() + "!")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Hello there pretty plant."),
                                new NPCDialogue(pet.npcId, "I am Tangleroot!")
                        );
                    }
                };
            }
            case TANGLEROOT_CRYSTAL: {
                return player -> {
                    int random = Random.get(1, 4);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("How are you doing today?"),
                                new NPCDialogue(pet.npcId, "I am tangleroot!")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("I am Tangleroot."),
                                new NPCDialogue(pet.npcId, "I am " + player.getName() + "!")
                        );
                    } else if (random == 3) {
                        player.dialogue(
                                new PlayerDialogue("Hello there pretty plant."),
                                new NPCDialogue(pet.npcId, "I am Tangleroot!")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Everything is crystal clear now."),
                                new NPCDialogue(pet.npcId, "I am Tangleroot!")
                        );
                    }
                };
            }
            case TANGLEROOT_DRAGONFRUIT: {
                return player -> {
                    int random = Random.get(1, 4);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("How are you doing today?"),
                                new NPCDialogue(pet.npcId, "I am tangleroot!")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("I am Tangleroot."),
                                new NPCDialogue(pet.npcId, "I am " + player.getName() + "!")
                        );
                    } else if (random == 3) {
                        player.dialogue(
                                new PlayerDialogue("Hello there pretty plant."),
                                new NPCDialogue(pet.npcId, "I am Tangleroot!")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("I can't believe they made dragonfruit trees into a real thing!"),
                                new NPCDialogue(pet.npcId, "I am Tangleroot?"),
                                new PlayerDialogue("Nothing.")
                        );
                    }
                };
            }
            case TANGLEROOT_GUAM: {
                return player -> {
                    int random = Random.get(1, 4);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("How are you doing today?"),
                                new NPCDialogue(pet.npcId, "I am tangleroot!")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("I am Tangleroot."),
                                new NPCDialogue(pet.npcId, "I am " + player.getName() + "!")
                        );
                    } else if (random == 3) {
                        player.dialogue(
                                new PlayerDialogue("Hello there pretty plant."),
                                new NPCDialogue(pet.npcId, "I am Tangleroot!")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Are you related to Herbiboar now?"),
                                new NPCDialogue(pet.npcId, "I am Tangleroot!"),
                                new PlayerDialogue("I should have guessed.")
                        );
                    }
                };
            }
            case TANGLEROOT_REDWOOD: {
                return player -> {
                    int random = Random.get(1, 4);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("How are you doing today?"),
                                new NPCDialogue(pet.npcId, "I am tangleroot!")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("I am Tangleroot."),
                                new NPCDialogue(pet.npcId, "I am " + player.getName() + "!")
                        );
                    } else if (random == 3) {
                        player.dialogue(
                                new PlayerDialogue("Hello there pretty plant."),
                                new NPCDialogue(pet.npcId, "I am Tangleroot!")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Oh dear, you've gone all red!"),
                                new NPCDialogue(pet.npcId, "I am Tangleroot?"),
                                new PlayerDialogue("It must be something you ate.")
                        );
                    }
                };
            }
            case TANGLEROOT_WHITELILY: {
                return player -> {
                    int random = Random.get(1, 4);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("How are you doing today?"),
                                new NPCDialogue(pet.npcId, "I am tangleroot!")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("I am Tangleroot."),
                                new NPCDialogue(pet.npcId, "I am " + player.getName() + "!")
                        );
                    } else if (random == 3) {
                        player.dialogue(
                                new PlayerDialogue("Hello there pretty plant."),
                                new NPCDialogue(pet.npcId, "I am Tangleroot!")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("I love your new hair cut!"),
                                new NPCDialogue(pet.npcId, "I am Tangleroot!")
                        );
                    }
                };
            }
            case ROCKY: {
                return player -> {
                    int random = Random.get(1, 3);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("*Whistles*"),
                                new MessageDialogue("You slip your hand into Rocky's pocket"),
                                new NPCDialogue(pet.npcId, "OY!! You're going to have to do better than that! Sheesh, what an amateur.")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("Is there much competition between you raccoons and the magpies?"),
                                new NPCDialogue(pet.npcId, "Magpies have nothing on us! They're just interested in shinies."),
                                new NPCDialogue(pet.npcId, "Us raccoons have a finer taste, we can see the value in anything, whether it shines or not.")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Hey Rocky, do you want to commit a bank robbery with me?"),
                                new NPCDialogue(pet.npcId, "If that is the level you are at, I do not wish to participate in criminal acts with you " + player.getName() + "."),
                                new PlayerDialogue("Well what are you interested in stealing?"),
                                new NPCDialogue(pet.npcId, "The heart of a lovely raccoon called Rodney."),
                                new PlayerDialogue("I cannot really help you there I'm afraid.")
                        );
                    }
                };
            }
            case RIFT_GUARDIAN_AIR:
            case RIFT_GUARDIAN_ASTRAL:
            case RIFT_GUARDIAN_BLOOD:
            case RIFT_GUARDIAN_BODY:
            case RIFT_GUARDIAN_CHAOS:
            case RIFT_GUARDIAN_COSMIC:
            case RIFT_GUARDIAN_DEATH:
            case RIFT_GUARDIAN_EARTH:
            case RIFT_GUARDIAN_FIRE:
            case RIFT_GUARDIAN_LAW:
            case RIFT_GUARDIAN_MIND:
            case RIFT_GUARDIAN_NATURE:
            case RIFT_GUARDIAN_SOUL:
            case RIFT_GUARDIAN_WATER: {
                return player -> {
                    int random = Random.get(1, 3);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("Can you see your own right?"),
                                new NPCDialogue(pet.npcId, "No. From time to time I feel it shift and change inside me though. It is an odd feeling.")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("Where would you like me to take you today Rifty?"),
                                new NPCDialogue(pet.npcId, "Please do not call me that... we are a species of honor " + player.getName() + "."),
                                new PlayerDialogue("Sorry.")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Hey! What's that!"),
                                new MessageDialogue("You quickly poke your hand through the rift guardian's rift."),
                                new NPCDialogue(pet.npcId, "Huh, what?! Where!"),
                                new PlayerDialogue("Not the best guardian it seems.")
                        );
                    }
                };
            }
            case BLOODHOUND: {
                return player -> {
                    int random = Random.get(1, 5);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("How come I can talk to you without an amulet?"),
                                new NPCDialogue(pet.npcId, "*Woof woof bark!* Elementary, it's due to the influence of the -SQUIRREL-!")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("Walkies!"),
                                new NPCDialogue(pet.npcId, "...")
                        );
                    } else if (random == 3) {
                        player.dialogue(
                                new PlayerDialogue("Can you help me with this clue?"),
                                new NPCDialogue(pet.npcId, "*Woof! Bark yip woof!* Sure! Eliminate the impossible first."),
                                new PlayerDialogue("And then?"),
                                new NPCDialogue(pet.npcId, "*Bark! Woof bark bark.* Whatever is left, however improbable, must be the answer."),
                                new PlayerDialogue("So helpful.")
                        );
                    } else if (random == 4) {
                        player.dialogue(
                                new PlayerDialogue("I wonder if I could sell you to a vampire to track down dinner."),
                                new NPCDialogue(pet.npcId, "*Woof bark bark woof* I have teeth too you know, that joke was not funny.")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Hey boy, what's up?"),
                                new NPCDialogue(pet.npcId, "*Woof! Bark bark woof!* You smell funny."),
                                new PlayerDialogue("Err... funny strange or funny ha ha?"),
                                new NPCDialogue(pet.npcId, "*Bark bark woof!* You aren't funny.")
                        );
                    }
                };
            }
            case CHOMPY_CHICK: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "*Chirp!*")
                );
            }
            case PHOENIX: {
                return player -> {
                    int random = Random.get(1, 4);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("So... The Pyromancers, they're cool, right?"),
                                new NPCDialogue(pet.npcId, "We share a common goal.."),
                                new PlayerDialogue("Which is?"),
                                new NPCDialogue(pet.npcId, "Keeping the cinders burning and preventing the long night from swallowing us all."),
                                new PlayerDialogue("That sounds scary."),
                                new NPCDialogue(pet.npcId, "As long as we remain vigilant and praise the Sun, all will be well.")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new NPCDialogue(pet.npcId, "..."),
                                new PlayerDialogue("What are you staring at?"),
                                new NPCDialogue(pet.npcId, "The great Sol Supra."),
                                new PlayerDialogue("Is that me?"),
                                new NPCDialogue(pet.npcId, "No mortal. The Sun, as you would say."),
                                new PlayerDialogue("Do you worship it?"),
                                new NPCDialogue(pet.npcId, "It is wonderous... If only I could be so grossly incandescent.")
                        );
                    } else if (random == 3) {
                        player.dialogue(
                                new PlayerDialogue("Who's a pretty birdy?"),
                                new NPCDialogue(pet.npcId, "The Phoenix Gives you a smouldering look.")
                        );
                    } else {
                        player.dialogue(
                                new NPCDialogue(pet.npcId, "One day I will burn so hot I'll become Sacred Ash"),
                                new PlayerDialogue("Aww, but you're so rare, where would I find another?"),
                                new NPCDialogue(pet.npcId, "Do not fret, mortal, I will rise from the Sacred Ash greater than ever before."),
                                new PlayerDialogue("So you're immortal?"),
                                new NPCDialogue(pet.npcId, "As long as the Sun in the sky gives me strength."),
                                new PlayerDialogue("...Sky?")
                        );
                    }
                };
            }
            case SKOTOS: {
                return player -> {
                    int random = Random.get(1, 3);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("You look cute."),
                                new NPCDialogue(pet.npcId, "I do not thinke thou understand the depths of the darkness you have unleashed upon the world." +
                                        " To dub it in such a scintillant manner is offensive to mine being."),
                                new PlayerDialogue("So why are you following me around?"),
                                new NPCDialogue(pet.npcId, "Dark forces of which ye know nought have deemed that this is my geas."),
                                new PlayerDialogue("Your goose?"),
                                new NPCDialogue(pet.npcId, "*Sighs* Nae. But thine is well and truly cooked.")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new NPCDialogue(pet.npcId, "I am spawned of darkness. I am filled with darkness. I am darkness incarnate and to darkness I will return."),
                                new PlayerDialogue("Sounds pretty... dark."),
                                new NPCDialogue(pet.npcId, "Knowest thou not of the cursed place? Knowest thou not about the future yet to befall your puny race?"),
                                new PlayerDialogue("Oh yes, I've heard that before."),
                                new NPCDialogue(pet.npcId, "Then it is good that ye can laugh in the face of the end."),
                                new PlayerDialogue("The end has a face? Which end?"),
                                new NPCDialogue(pet.npcId, "*Sighs* The darkness giveth, and the darkness taketh.")
                        );
                    } else {
                        player.dialogue(
                                new NPCDialogue(pet.npcId, "Nothing. Ye are already tainted in my sight by the acts of light. However they may be some hope for you if you continue to aid the darkness."),
                                new PlayerDialogue("I do have a lantern around here somewhere."),
                                new NPCDialogue(pet.npcId, "Do not bring that foul and repellant thing near mine self.")
                        );
                    }
                };
            }
            case JAL_NIB_REK: {
                return player -> {
                    int random = Random.get(1, 3);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("Yo Nib, what's going on?"),
                                new NPCDialogue(pet.npcId, "Nibnib? Kl-Rek Nib?"),
                                new MessageDialogue("Jal-Nib-Rek nips you."),
                                new PlayerDialogue("What's you do that for?"),
                                new NPCDialogue(pet.npcId, "Heh Nib get you.")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("What'd you have for dinner?"),
                                new NPCDialogue(pet.npcId, "Nibblings!"),
                                new PlayerDialogue("Nibblings of what exactly?"),
                                new NPCDialogue(pet.npcId, "Nib."),
                                new PlayerDialogue("Oh no! That's horrible.")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Can you speak like a human can Nib?"),
                                new NPCDialogue(pet.npcId, "No, I most definitely can not."),
                                new PlayerDialogue("Aren't you speaking like a human right now...?"),
                                new NPCDialogue(pet.npcId, "Jal-Nib-Rek Nib Kl-Jal, Zuk is mum."),
                                new PlayerDialogue("Interesting.")
                        );
                    }
                };
            }
            case TZREK_ZUK: {
                return player -> {
                    int random = Random.get(1, 3);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("What's up Zuk?"),
                                new NPCDialogue(pet.npcId, "Feeling a bit down to be honest."),
                                new PlayerDialogue("Why's that?"),
                                new NPCDialogue(pet.npcId, "Well..."),
                                new NPCDialogue(pet.npcId, "Not so long ago, I was a big fearsome boss, Now I'm just another pet."),
                                new PlayerDialogue("Indeed, and you're going to follow me everywhere I go.")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("Why have you got lava around your feet?"),
                                new NPCDialogue(pet.npcId, "Keeps me cool."),
                                new PlayerDialogue("But... lava is hot?"),
                                new NPCDialogue(pet.npcId, "No no, I wasn't referring to the temperature."),
                                new PlayerDialogue("Ah...")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("You're a lot smaller now, I don't even need a shield."),
                                new NPCDialogue(pet.npcId, "Mere mortal, you only survived my challenge because of that convenient pile of rock."),
                                new PlayerDialogue("Well, you couldn't even break that pile of rock to get at me!"),
                                new NPCDialogue(pet.npcId, "...")
                        );
                    }
                };
            }
            case MIDNIGHT: {
                return player -> {
                    int random = Random.get(1, 3);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("Hello little other one."),
                                new NPCDialogue(pet.npcId, "Other?"),
                                new PlayerDialogue("Yes, don't you have a sister?"),
                                new NPCDialogue(pet.npcId, "I don't want to chalk about it.")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("Sometimes I'm worried you'll attack me whilst my back is turned."),
                                new NPCDialogue(pet.npcId, "Are you petrified of my tuffness?"),
                                new PlayerDialogue("Not really, but your puns are awful."),
                                new NPCDialogue(pet.npcId, "I thought they were clastic.")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("I feel like our relationship is slowly eroding away."),
                                new NPCDialogue(pet.npcId, "Geode willing.")
                        );
                    }
                };
            }
            case NOON: {
                return player -> {
                    int random = Random.get(1, 3);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("Hello little one."),
                                new NPCDialogue(pet.npcId, "I may be small but at least I'm perfectly formed.")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("What's your favourite rock?"),
                                new NPCDialogue(pet.npcId, "You're going tufa with that question. That's personal."),
                                new PlayerDialogue("Was just trying to make light conversation, not trying to aggregate you.")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("Metaphorically speaking, do you have a heart of stone?"),
                                new NPCDialogue(pet.npcId, "Yes, but you're not having it.")
                        );
                    }
                };
            }
            case HERBI: {
                return player -> {
                    int random = Random.get(1, 5);
                    if (random == 1) {
                        player.dialogue(
                                new PlayerDialogue("Are you hungry?"),
                                new NPCDialogue(pet.npcId, "That depends, what have you got?"),
                                new PlayerDialogue("I'm sure I could knock you up a decent salad."),
                                new NPCDialogue(pet.npcId, "I'm actually a insectivore."),
                                new PlayerDialogue("Oh, but your name suggests that-"),
                                new NPCDialogue(pet.npcId, "I think you'll find I didn't name myself, you humans and your silly puns."),
                                new PlayerDialogue("No need to PUNish us for our incredible wit."),
                                new NPCDialogue(pet.npcId, "Please. Stop.")
                        );
                    } else if (random == 2) {
                        player.dialogue(
                                new PlayerDialogue("Have your herbs died?"),
                                new NPCDialogue(pet.npcId, "These old things? I guess they've dried up... I'm getting old and I need caring for. I've chosen you to do that by the way."),
                                new PlayerDialogue("Oh fantastic! I guess I'll go shell out half a million coins to keep you safe then, what superb luck!"),
                                new NPCDialogue(pet.npcId, "I could try the next person if you'd prefer?"),
                                new PlayerDialogue("I'm just joking you old swine!")
                        );
                    } else if (random == 3) {
                        player.dialogue(
                                new PlayerDialogue("So you live in a hole? I would've thought Boars are surface dwelling mammals."),
                                new NPCDialogue(pet.npcId, "Well, I'm special! I bore down a little so I'm nice and cosy with my herbs exposed to the sun, it's all very interesting."),
                                new PlayerDialogue("Sounds rather... Boring!"),
                                new NPCDialogue(pet.npcId, "How very original...")
                        );
                    } else if (random == 4) {
                        player.dialogue(
                                new PlayerDialogue("Tell me... do you like Avacado?"),
                                new NPCDialogue(pet.npcId, "I'm an insectivore, but even if I wasn't I'd hate Avacado!"),
                                new PlayerDialogue("Why ever not? It's delicious!"),
                                new NPCDialogue(pet.npcId, "I don't know why people like it so much... it tastes like a ball of chewed up grass."),
                                new PlayerDialogue("Sometimes you can be such a bore...")
                        );
                    } else {
                        player.dialogue(
                                new NPCDialogue(pet.npcId, "When I was a young HERBIBOAR!!"),
                                new PlayerDialogue("I'm standing right next to you, no need to shout..."),
                                new NPCDialogue(pet.npcId, "I was trying to sing you a song...")
                        );
                    }
                };
            }
            case PENANCE_QUEEN: {
                return player -> player.dialogue(
                        new PlayerDialogue("Of all the high gamble rewards I could have won, I won you..."),
                        new NPCDialogue(pet.npcId, "Keep trying, human. You'll never win that Dragon Chainbody.")
                );
            }
            case OLMLET: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Hee hee! What shall we talk about, human?"),
                        new OptionsDialogue(
                                new Option("You look like a dragon.", () -> player.dialogue(
                                        new PlayerDialogue("You look like a dragon."),
                                        new NPCDialogue(pet.npcId, "And humans look like monkeys. Badly shaved monkeys. What's your point, human?"),
                                        new PlayerDialogue("Are you related to dragons?"),
                                        new NPCDialogue(pet.npcId, "My sire was an olm. I'm an olm. I don't go around asking you about your parents' species, do I?"),
                                        new PlayerDialogue("... no, I suppose you don't."),
                                        new NPCDialogue(pet.npcId, "Hee hee! Let's change the subject before someone gets insulted.")
                                )),
                                new Option("Where do creatures like you come from?", () -> player.dialogue(
                                        new PlayerDialogue("Where do creatures like you come from?"),
                                        new NPCDialogue(pet.npcId, "From eggs, of course! You can't make an olmlet without breaking an egg."),
                                        new PlayerDialogue("That's... informative. Thank you."),
                                        new NPCDialogue(pet.npcId, "Hee hee!")
                                )),
                                new Option("Can you tell me secrets about your home?", () -> player.dialogue(
                                        new PlayerDialogue("Can you tell me secrets about your home?"),
                                        new NPCDialogue(pet.npcId, "Ooh, it was lovely. I lived in an eggshell. I was safe in there, dreaming of the life I would lead when I hatched, and the caverns I could rule."),
                                        new NPCDialogue(pet.npcId, "Then suddenly I felt a trembling of the ground, and my shell shattered."),
                                        new NPCDialogue(pet.npcId, "Through its cracks I saw the world for the first time, just in time to watch my sire die."),
                                        new NPCDialogue(pet.npcId, "It was a terrible shock for a newly hatched olmlet, but I try not to let it affect my mood.")
                                )),
                                new Option("Maybe another time.", () -> player.dialogue(
                                        new PlayerDialogue("Maybe another time.")
                                ))
                        )
                );
            }
            case LIL_ZIK: {
                int random = Random.get(1, 4);
                if (random == 1) {
                    return player -> player.dialogue(
                            new PlayerDialogue("Hey Lil' Zik."),
                            new NPCDialogue(pet.npcId, "Stop."),
                            new NPCDialogue(pet.npcId, "Calling."),
                            new NPCDialogue(pet.npcId, "Me."),
                            new NPCDialogue(pet.npcId, "Little."),
                            new PlayerDialogue("Never!")
                    );
                }
                if(random == 2) {
                    return player -> player.dialogue(
                            new PlayerDialogue("You know... you're not like other spiders."),
                            new NPCDialogue(pet.npcId, "You know I hate it when you say that... please leave me alone."),
                            new PlayerDialogue("But I earned you fair and square at the Theatre of Blood! You're mine to keep."),
                            new NPCDialogue(pet.npcId, "...")
                    );
                }
                if(random == 3) {
                    return player -> player.dialogue(
                            new PlayerDialogue("Incy wincy Verzik climbed up the water spout..."),
                            new PlayerDialogue("Down came the rain and washed poor Verzik out..."),
                            new NPCDialogue(pet.npcId, "Out came the Vampyre to put an end to this at once. Humans deserve only one fate!"),
                            new PlayerDialogue("Wow, calm down. It's just a nursery rhyme."),
                            new NPCDialogue(pet.npcId, "I'm not in hte mood.")
                    );
                }
                if(random == 4) {
                    return player -> player.dialogue(
                            new PlayerDialogue("Hi, I'm here for my reward!"),
                            new NPCDialogue(pet.npcId, "Not again...")
                    );
                }
            }
            case KITTEN1:
            case KITTEN2:
            case KITTEN3:
            case KITTEN4:
            case KITTEN5:
            case KITTEN6:
            case HELL_KITTEN: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Meow!")
                );
            }
            case CAT1:
            case CAT2:
            case CAT3:
            case CAT4:
            case CAT5:
            case CAT6:
            case HELL_CAT: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Meow.")
                );
            }
            case OVERGROWN_CAT1:
            case OVERGROWN_CAT2:
            case OVERGROWN_CAT3:
            case OVERGROWN_CAT4:
            case OVERGROWN_CAT5:
            case OVERGROWN_CAT6:
            case HELL_OVERGROWN_CAT: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Meoooow!")
                );
            }
            case WILY_CAT1:
            case WILY_CAT2:
            case WILY_CAT3:
            case WILY_CAT4:
            case WILY_CAT5:
            case WILY_CAT6:
            case HELL_WILY_CAT: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Meooow.")
                );
            }
            case LAZY_CAT1:
            case LAZY_CAT2:
            case LAZY_CAT3:
            case LAZY_CAT4:
            case LAZY_CAT5:
            case LAZY_CAT6:
            case HELL_LAZY_CAT: {
                return player -> player.dialogue(
                        new NPCDialogue(pet.npcId, "Meow.")
                );
            }
            case VORKI: {
                return player -> {
                    int random = Random.get(1, 10);
                    player.dialogue(new PlayerDialogue("Hey Vorki, got any interesting dragon facts?"),
                            new ActionDialogue(() -> {
                                if (random == 1) {
                                    player.dialogue(new NPCDialogue(pet.npcId, "Although they have wings, dragons rarely fly. " +
                                            "This is because the animals they prey on are all ground dwelling."));
                                }
                                if(random == 2) {
                                    player.dialogue(new NPCDialogue(pet.npcId, "Unlike their creators, dragons have the ability to " +
                                            "reproduce. Like most reptiles, they are oviparous. This means that they lay eggs rather than birthing live young."));
                                }
                                if(random == 3) {
                                    player.dialogue(new NPCDialogue(pet.npcId, "Dragons have a very long lifespan and can live for thousands of years. " +
                                            "With a lifespan that long, most dragons die to combat instead of age."));
                                }
                                if(random == 4) {
                                    player.dialogue(new NPCDialogue(pet.npcId, "While very closely related, dragons and wyverns are actually different " +
                                            "species. You can easily tell the difference between them by counting the number of legs, dragons have four while wyverns have two."));
                                }
                                if(random == 5) {
                                    player.dialogue(new NPCDialogue(pet.npcId, "Metallic dragons were created by inserting molten metal into the eggs of other dragons." +
                                            " Very few eggs survived this process."));
                                }
                                if(random == 6) {
                                    player.dialogue(new NPCDialogue(pet.npcId, "The dragonkin created dragons by fusing their own lifeblood with that of a lizard. The " +
                                            "dragonkin created other species in similar ways by using different types of reptile."));
                                }
                                if(random == 7) {
                                    player.dialogue(new NPCDialogue(pet.npcId, "Dragons have the ability to speak. However, most dragons don't have the brain capacity " +
                                            "to do it very well."));
                                }
                                if(random == 8) {
                                    player.dialogue(new NPCDialogue(pet.npcId, "Dragons share their name with dragon equipment, which was also created by the dragonkin. " +
                                            "This equipment is fashioned out of Orikalkum."));
                                }
                                if(random == 9) {
                                    player.dialogue(new NPCDialogue(pet.npcId, "Although very aggressive, dragons do not typically stray from their own territory. They" +
                                            " instead make their homes in places where there is plenty of prey to be found."));
                                }
                                if(random == 10) {
                                    player.dialogue(new NPCDialogue(pet.npcId, "Dragons have a duct in their mouth from which they can expel various internally produced fluids." +
                                            " The most common of these is a fluid which ignites when it reacts with air. This is how dragons breathe fire."));
                                }
                            }));
                };
            }
            case IKKLE_HYDRA_GREEN:
            case IKKLE_HYDRA_BLUE:
            case IKKLE_HYDRA_RED:
            case IKKLE_HYDRA_BLACK: {
                return player -> {
                    int random = Random.get(1, 3);
                    if(random == 1) {
                        player.dialogue(new PlayerDialogue("So... you're alchemical; does that mean I can turn you into gold?"),
                                new NPCDialogue(pet.metaId, "No, I like my form as it is."),
                                new PlayerDialogue("But... I love gold."),
                                new NPCDialogue(pet.metaId, "I'll turn to gold when drakes fly."));
                    }
                    if(random == 2) {
                        player.dialogue(new PlayerDialogue("So how was that chemical bath?!"),
                                new NPCDialogue(pet.metaId, "Nasty! I don't want to do that again"));
                    }
                    if(random == 3) {
                        player.dialogue(new PlayerDialogue("Which creature is cooler, Drakes, Hydras or Wyrms?"),
                                new NPCDialogue(pet.metaId, "I may be biased in this situation... bit silly to ask me isn't it?"),
                                new PlayerDialogue("Alright alright... don't lose your head."));
                    }
                };
            }
            case SMOLCANO: {
                return player -> {
                    int random = Random.get(1, 2);
                    if(random == 1) {
                        player.dialogue(new PlayerDialogue("How much did you pay for your ring of stone?"),
                                new NPCDialogue(pet.npcId,  "What are you talking about?"),
                                new PlayerDialogue("They're so expensive, but so much fun!"),
                                new NPCDialogue(pet.npcId, "Right..."));
                    }
                    if(random == 2) {
                        player.dialogue(new PlayerDialogue("So why do they call you Zalcano?"),
                                new NPCDialogue(pet.npcId, "Well...."),
                                new PlayerDialogue("is it because you’re like a vol-"),
                                new NPCDialogue(pet.npcId, "Don’t say it!"));
                    }
                };
            }
            case YOUNGLLEF:
            case CORRUPTED_YOUNGLLEF :{
                return player -> {
                    int random = Random.get(1, 2);
                    if(random == 1) {
                        player.dialogue(new PlayerDialogue("I don't get it... Are you real or not?"),
                                new NPCDialogue(pet.metaId,  "I'm a crystalline formation, made by the elves."),
                                new PlayerDialogue("But, like... Can you feel it if I pinch you?"),
                                new NPCDialogue(pet.metaId, "Don't you even think about it."));
                    }
                    if(random == 2) {
                        player.dialogue(new PlayerDialogue("What actually are you? A big wolf or something?"),
                                new NPCDialogue(pet.metaId, "I suppose I might look something like that."),
                                new PlayerDialogue("That sounds like a no. What are you then?"),
                                new NPCDialogue(pet.metaId, "A hunllef."),
                                new PlayerDialogue("A what?"),
                                new NPCDialogue(pet.metaId, "Nevermind."),
                                new PlayerDialogue("You know, you can be a real nightmare sometimes."));
                    }
                };
            }
            case SRARACHA: {
                return player -> player.dialogue(new PlayerDialogue("So what kind of spider are you...?"),
                        new NPCDialogue(pet.npcId,  "The hive cluster is under attack!"),
                        new PlayerDialogue("Erm, I think the attack is over. I have already killed your queen."),
                        new NPCDialogue(pet.npcId, "Then we should spawn more overlords!"));
            }
        }
        return null;
    }

}