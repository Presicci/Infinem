package io.ruin.model.skills.herblore;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.ProductionMaster;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.impl.jewellery.AmuletOfChemistry;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;

import java.util.*;

public enum Potion {

    /**
     * Regular potions
     */
    ATTACK(1, 25.0, "attack potion", "guam potion (unf)", "eye of newt"),
    ANTIPOISON(5, 37.5, "antipoison", "marrentill potion (unf)", "unicorn horn dust"),
    RELICYMS_BALM(8, 40.0, "relicym's balm", "rogue's purse", "snake weed", "vial of water"),
    STRENGTH(12, 50.0, "strength potion", "tarromin potion (unf)", "limpwurt root"),
    SERUM_207(15, 50.0, "serum 207", "tarromin potion (unf)", "ashes"),
    GUTHIX_REST(18, 59, "guthix rest", "cup of hot water", "guam leaf", "harralander", "marrentill"),
    RESTORE(22, 62.5, "restore potion", "harralander potion (unf)", "red spiders' eggs"),
    COMPOST(22, 60, "compost potion", "harralander potion (unf)", "volcanic ash"),
    GUTHIX_BALANCE(22, 25.0, "guthix balance", "restore potion(3)", "garlic", "silver dust"),
    ENERGY(26, 67.5, "energy potion", "harralander potion (unf)", "chocolate dust"),
    DEFENCE(30, 75.0, "defence potion", "ranarr potion (unf)", "white berries"),
    AGILITY(34, 80.0, "agility potion", "toadflax potion (unf)", "toad's legs"),
    COMBAT(36, 84.0, "combat potion", "harralander potion (unf)", "goat horn dust"),
    PRAYER(38, 87.5, "prayer potion", "ranarr potion (unf)", "snape grass"),
    SUPER_ATTACK(45, 100.0, "super attack", "irit potion (unf)", "eye of newt"),
    SUPER_ANTIPOISON(48, 106.3, "superantipoison", "irit potion (unf)", "unicorn horn dust"),
    FISHING(50, 112.5, "fishing potion", "avantoe potion (unf)", "snape grass"),
    SUPER_ENERGY(52, 117.5, "super energy", "avantoe potion (unf)", "mort myre fungus"),
    HUNTER(53, 120.0, "hunter potion", "avantoe potion (unf)", "kebbit teeth dust"),
    SUPER_STRENGTH(55, 125.0, "super strength", "kwuarm potion (unf)", "limpwurt root"),
    MAGIC_ESSENCE(57, 130.0, "magic essence", "magic essence (unf)", "gorak claw powder"),
    WEAPON_POISON(60, 137.5, "weapon poison", "kwuarm potion (unf)", "dragon scale dust"),
    SUPER_RESTORE(63, 142.5, "super restore", "snapdragon potion (unf)", "red spiders' eggs"),
    SANFEW_SERUM_1(65, 57.0, "mixture - step 1", "super restore(3)", "unicorn horn dust"),
    SANFEW_SERUM_2(65, 63.0, "mixture - step 2", "mixture - step 1(3)", "snake weed"),
    SANFEW_SERUM(65, 72.0, "sanfew serum", "mixture - step 2(3)", "nail beast nails"),
    SUPER_DEFENCE(66, 150.0, "super defence", "cadantine potion (unf)", "white berries"),
    ANTIDOTE_PLUS_UNF(68, 0.0, "antidote+ (unf)", "coconut milk", "toadflax"),
    ANTIDOTE_PLUS(68, 155.0, "antidote+", "antidote+ (unf)", "yew roots"),
    ANTIFIRE(69, 157.5, "antifire potion", "lantadyme potion (unf)", "dragon scale dust"),
    RANGING(72, 162.5, "ranging potion", "dwarf weed potion (unf)", "wine of zamorak"),
    WEAPON_POISON_PLUS(73, 165.0, "weapon poison(+)", "coconut milk", "cactus spine", "red spiders' eggs"),
    MAGIC(76, 172.5, "magic potion", "lantadyme potion (unf)", "potato cactus"),
    STAMINA(77, 102.0, "stamina potion", "super energy(3)", "amylase crystal"),
    ZAMORAK_BREW(78, 175.0, "zamorak brew", "torstol potion (unf)", "jangerberries"),
    ANTIDOTE_PLUS_PLUS_UNF(79, 0.0, "antidote++ (unf)", "coconut milk", "irit leaf"),
    ANTIDOTE_PLUS_PLUS(79, 177.5, "antidote++", "antidote++ (unf)", "magic roots"),
    SARADOMIN_BREW(81, 180.0, "saradomin brew", "toadflax potion (unf)", "crushed nest"),
    WEAPON_POISON_PLUS_PLUS(82, 190.0, "weapon poison(++)", "coconut milk", "cave nightshade", "poison ivy berries"),
    EXTENDED_ANTIFIRE(84, 110.0, "extended antifire", "antifire potion(3)", "lava scale shard"),
    ANTI_VENOM(87, 120.0, "anti-venom", "antidote++(3)", "zulrah's scales"),
    SUPER_COMBAT(90, 150.0, "super combat potion", "torstol", "super attack(4)", "super strength(4)", "super defence(4)"),
    SUPER_ANTIFIRE(92, 130.0, "super antifire potion", "antifire potion(4)", "crushed superior dragon bones"),
    SUPER_ANTI_VENOM(94, 125.0, "anti-venom+", "anti-venom(4)", "torstol"),
    EXTENDED_SUPER_ANTIFIRE(98, 160.0, "extended super antifire", "super antifire potion(3)", "lava scale shard"),
    EXTENDED_SUPER_ANTIFIRE_2(92, 180.0, "extended super antifire", "extended antifire(4)", "crushed superior dragon bones"),
    DIVINE_SUPER_ATTACK(70, 1.5, "divine super attack potion", "super attack(3)", "crystal shard"),
    DIVINE_SUPER_STRENGTH(70, 1.5, "divine super strength potion", "super strength(3)", "crystal shard"),
    DIVINE_SUPER_DEFENCE(70, 1.5, "divine super defence potion", "super defence(3)", "crystal shard"),
    DIVINE_RANGING(74, 1.5, "divine ranging potion", "ranging potion(3)", "crystal shard"),
    DIVINE_MAGIC(78, 1.5, "divine magic potion", "magic potion(3)", "crystal shard"),
    DIVINE_SUPER_COMBAT(97, 1.5, "divine super combat potion", "super combat potion(3)", "crystal shard"),
    DIVINE_BATTLEMAGE(86, 1.5, "divine battlemage potion", "battlemage potion(3)", "crystal shard"),
    DIVINE_BASTION(86, 1.5, "divine bastion potion", "bastion potion(3)", "crystal shard"),

    /**
     * Blighted
     */
    BLIGHTED_SUPER_RESTORE("blighted super restore", false),

    /**
     * NMZ
     */
    OVERLOAD_NMZ("overload ", false),
    SUPER_RANGING("super ranging ", false),
    SUPER_MAGIC("super magic potion ", false),
    ABSORPTION("absorption ", false),

    /**
     * Raids potions
     */
    ELDER_MINUS("elder (-)", true),
    ELDER_REGULAR("elder potion", true),
    ELDER_PLUS("elder (+)", true),
    TWISTED_MINUS("twisted (-)", true),
    TWISTED_REGULAR("twisted potion", true),
    TWISTED_PLUS("twisted (+)", true),
    KODAI_MINUS("kodai (-)", true),
    KODAI_REGULAR("kodai potion", true),
    KODAI_PLUS("kodai (+)", true),
    REVITALISATION_MINUS("revitalisation (-)", true),
    REVITALISATION_REGULAR("revitalisation potion", true),
    REVITALISATION_PLUS("revitalisation (+)", true),
    PRAYER_ENHANCE_MINUS("prayer enhance (-)", true),
    PRAYER_ENHANCE_REGULAR("prayer enhance", true),
    PRAYER_ENHANCE_PLUS("prayer enhance (+)", true),
    XERIC_AID_MINUS("xeric's aid (-)", true),
    XERIC_AID_REGULAR("xeric's aid ", true),
    XERIC_AID_PLUS("xeric's aid (+)", true),
    ANTIPOISON_MINUS("antipoison (-)", true),
    ANTIPOISON_RAID(25758, 25759, 25760, 25761, "antipoison", true),
    ANTIPOISON_PLUS("antipoison (+)", true),
    OVERLOAD_MINUS("overload (-)", true),
    OVERLOAD_REGULAR("overload ", true),
    OVERLOAD_PLUS("overload (+)", true);

    public final int lvlReq;

    public final double xp;

    public final String potionName;

    public final String primaryName;

    public final String[] secondaryNames;

    public final int[] vialIds;

    public boolean raidsPotion;

    Potion(int lvlReq, double xp, String potionName, String primaryName, String... secondaryNames) {
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.potionName = potionName;
        this.primaryName = primaryName;
        this.secondaryNames = secondaryNames;
        this.vialIds = new int[4];
        this.raidsPotion = false;
    }

    Potion(String potionName, boolean raidsPotion) {
        this.lvlReq = -1;
        this.xp = -1;
        this.potionName = potionName;
        this.primaryName = "";
        this.secondaryNames = new String[4];
        this.vialIds = new int[4];
        this.raidsPotion = raidsPotion;
    }

    Potion(int oneDose, int twoDose, int threeDose, int fourDose, String potionName, boolean raidsPotion) {
        this.lvlReq = -1;
        this.xp = -1;
        this.potionName = potionName;
        this.primaryName = "";
        this.secondaryNames = new String[4];
        this.vialIds = new int[] {oneDose, twoDose, threeDose, fourDose};
        this.raidsPotion = raidsPotion;
    }

    private static final Set<Potion> FOUR_DOSE = new HashSet<Potion>() {{
        add(Potion.ANTIDOTE_PLUS);
        add(Potion.ANTIDOTE_PLUS_PLUS);
        add(Potion.SUPER_ANTI_VENOM);
        add(Potion.SUPER_COMBAT);
        add(Potion.SUPER_ANTIFIRE);
        add(Potion.EXTENDED_SUPER_ANTIFIRE_2);
    }};

    private void mix(Player player, Item primaryItem, List<Item> secondaryItems) {
        primaryItem.remove();
        for (Item secondaryItem : secondaryItems)
            secondaryItem.remove();
        int potion = vialIds[2];
        if (FOUR_DOSE.contains(this) || AmuletOfChemistry.test(player)) {
            potion = vialIds[3];
        }
        RandomEvent.attemptTrigger(player);
        player.getInventory().add(potion, 1);
        player.getStats().addXp(StatType.Herblore, xp, true);
        String name = ItemDefinition.get(potion).name.toLowerCase();
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.POTION, name.contains("(") ? name.substring(0, name.indexOf("(")) : name, 1, true);
        player.animate(363);
        player.privateSound(2608);
    }

    private void decant(Player player, Item fromPot, Item toPot) {
        int fromDoses = fromPot.getDef().potionDoses;
        int toDoses = toPot.getDef().potionDoses;
        int doses = Math.min(fromDoses, 4 - toDoses);
        if (doses == 0) {
            player.sendMessage("That potion is already full.");
            return;
        }
        fromDoses -= doses;
        toDoses += doses;
        if (fromDoses <= 0)
            fromPot.setId(this == Potion.GUTHIX_REST ? 1980 : raidsPotion ? 20800 : 229);
        else
            fromPot.setId(vialIds[fromDoses - 1]);
        toPot.setId(vialIds[toDoses - 1]);
        player.sendFilteredMessage("You have combined the liquid into " + toDoses + " doses.");
    }

    private void divide(Player player, Item potionItem, Item vialItem) {
        int doses = potionItem.getDef().potionDoses;
        if (doses == 1) {
            player.sendMessage("There's not enough liquid to divide.");
            return;
        }
        int fromDoses = (int) Math.floor(doses / 2D);
        int toDoses = (int) Math.ceil(doses / 2D);
        potionItem.setId(vialIds[fromDoses - 1]);
        vialItem.setId(vialIds[toDoses - 1]);
        player.sendMessage("You divide the liquid between the vessels.");
    }

    /**
     * Bulk decanting
     */

    public static void decant(Player player, int dosage) {
        HashMap<Potion, Decant> potions = new HashMap<>();
        HashMap<Potion, Decant> notedPotions = new HashMap<>();
        for (Item item : player.getInventory().getItems()) {
            if (item == null)
                continue;
            boolean noted = false;
            ItemDefinition def = item.getDef();
            if (def.isNote()) {
                noted = true;
                def = def.fromNote();
            }
            if (def.potion == null || def.potionDoses == dosage) {
                /* ignore this item */
                continue;
            }
            HashMap<Potion, Decant> map = noted ? notedPotions : potions;
            Decant decant = map.get(def.potion);
            if (decant == null)
                map.put(def.potion, decant = new Decant());
            decant.doses += (item.getAmount() * def.potionDoses);
            item.remove();
        }
        potions.forEach((p, d) -> d.decant(player, p, dosage, dosage != 4));
        notedPotions.forEach((p, d) -> d.decant(player, p, dosage, true));
        player.dialogue(new MessageDialogue("Your potions have been decanted to x" + dosage + " dose" + (dosage > 1 ? "s" : "") + "."));
    }

    private static final class Decant {

        double doses;

        void decant(Player player, Potion potion, int dosage, boolean note) {
            int desired = (int) (doses / (double) dosage);
            if (desired > 0) {
                int addId = potion.vialIds[dosage - 1];
                player.getInventory().addOrDrop(note ? ItemDefinition.get(addId).notedId : addId, desired);
                doses -= (desired * dosage);
            }
            int remainingDoses = (int) doses;
            if (remainingDoses > 0) {
                int addId = potion.vialIds[remainingDoses - 1];
                player.getInventory().addOrDrop(note ? ItemDefinition.get(addId).notedId : addId, 1);
            }
        }

    }

    /**
     * Registering
     */
    private static void register(Potion potion, int primaryId, int[] secondaryIds) {
        SkillItem item = new SkillItem(potion.vialIds[2]).addAction((player, amount, event) -> {
            int prodCount = 0;
            while (amount-- > 0) {
                Item primaryItem = player.getInventory().findItem(primaryId);
                if (primaryItem == null)
                    break;
                List<Item> secondaryItems = player.getInventory().collectOneOfEach(secondaryIds);
                if (secondaryItems == null)
                    break;
                potion.mix(player, primaryItem, secondaryItems);
                if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                    event.delay(2);
                }
                if (ProductionMaster.roll(player))
                    prodCount++;
            }
            ProductionMaster.extra(player, prodCount, potion.vialIds[2], StatType.Herblore, potion.xp * prodCount, TaskCategory.POTION);
        });
        for (int secondaryId : secondaryIds) {
            ItemItemAction.register(primaryId, secondaryId, (player, primary, secondary) -> {
                if (!player.getStats().check(StatType.Herblore, potion.lvlReq, "make this potion"))
                    return;
                if (player.getInventory().hasMultiple(secondaryIds)) {
                    SkillDialogue.make(player, item);
                    return;
                }
                List<Item> secondaries = player.getInventory().collectOneOfEach(secondaryIds);
                if (secondaries == null) {
                    player.sendMessage("You need more ingredients to make this potion.");
                    return;
                }
                potion.mix(player, primary, secondaries);
            });
        }
    }

    private static void registerUpgrade(Potion potion, int primaryId, int secondaryId, int secondaryAmtPerDose) {
        Potion primaryPotion = ItemDefinition.get(primaryId).potion;
        String secondaryName = ItemDefinition.get(secondaryId).name.toLowerCase().replace("'s", "");
        String secondaryPluralName = secondaryName + (secondaryName.endsWith("s") ? "" : "s");
        double xpPerDose = secondaryAmtPerDose == 0 ? (potion.xp / 2) / 4 : potion.xp / 4;
        for (int i = 0; i < primaryPotion.vialIds.length; i++) {
            int vialId = primaryPotion.vialIds[i];
            ItemItemAction.register(vialId, secondaryId, (player, primary, secondary) -> {
                if (!player.getStats().check(StatType.Herblore, potion.lvlReq, "make this potion"))
                    return;
                int doses = primary.getDef().potionDoses;
                int reqAmt = secondaryAmtPerDose == 0 ? 1 : doses * secondaryAmtPerDose;
                if (secondary.getAmount() < reqAmt) {
                    if (doses == 1)
                        player.sendMessage("You need at least " + reqAmt + " " + secondaryPluralName + " to upgrade 1 dose of that potion.");
                    else
                        player.sendMessage("You need at least " + reqAmt + " " + secondaryPluralName + " to upgrade " + doses + " doses of that potion.");
                    return;
                }
                double experience = secondaryAmtPerDose == 0 ? potion.xp + (xpPerDose * doses) : xpPerDose * doses;
                String name = ItemDefinition.get(potion.vialIds[doses - 1]).name.toLowerCase();
                if (player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                    int amtPrimary = player.getInventory().getAmount(primaryId);
                    int amtSecondary = player.getInventory().getAmount(secondaryId);
                    int amt = amtPrimary;
                    if (amtSecondary < amtPrimary * reqAmt)
                        amt = (int) Math.floor((double) amtSecondary / (double) reqAmt);
                    secondary.remove(reqAmt * amt);
                    player.getInventory().remove(primaryId, amt);
                    for (int index = 0; index < amt; index++) {
                        if (AmuletOfChemistry.test(player)) {
                            player.getInventory().add(potion.vialIds[3], amt);
                        } else {
                            player.getInventory().add(potion.vialIds[doses - 1], amt);
                        }
                    }
                    player.animate(363);
                    player.getStats().addXp(StatType.Herblore, experience * amt, true);
                    player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.POTION, name.substring(0, name.indexOf("(")), amt, true);
                } else {
                    secondary.remove(reqAmt);
                    primary.remove(1);
                    if (AmuletOfChemistry.test(player))
                        player.getInventory().add(potion.vialIds[3]);
                    else
                        player.getInventory().add(potion.vialIds[doses - 1]);
                    player.animate(363);
                    player.getStats().addXp(StatType.Herblore, experience, true);
                    player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.POTION, name.substring(0, name.indexOf("(")), 1, true);
                    if (reqAmt == 1)
                        player.sendFilteredMessage("You mix 1 " + secondaryName + " into your potion.");
                    else
                        player.sendFilteredMessage("You mix " + reqAmt + " " + secondaryPluralName + " into your potion.");
                }
            });
        }
    }

    static {
        for (Potion potion : values()) {
            /*
             * Get data from names
             */
            int primaryId = -1;
            int[] secondaryIds = new int[potion.secondaryNames.length];
            for (ItemDefinition def : ItemDefinition.cached.values()) {
                if (potion == ANTIPOISON_RAID) break;
                if (def == null)
                    continue;
                if (def.name.toLowerCase().startsWith(potion.potionName + "(") || def.name.toLowerCase().startsWith(potion.potionName + " (")) {
                    // Differentiates the nmz and raids overload potions
                    if (potion == Potion.OVERLOAD_NMZ && def.id > 20000) {
                        continue;
                    } else if (potion == Potion.OVERLOAD_REGULAR && def.id < 20000) {
                        continue;
                    }
                    int doses = Character.getNumericValue(def.name.charAt(def.name.lastIndexOf("(") + 1));
                    if (doses >= 1 && doses <= 4) {
                        if (potion.vialIds[doses - 1] == 0) {
                            def.potion = potion;
                            def.potionDoses = doses;
                            potion.vialIds[doses - 1] = def.id;
                        }
                        continue;
                    }
                } else if (def.name.toLowerCase().equals(potion.potionName)) { // no multi-dose (like weapon poison)
                    //todo???
                    //System.out.println(potion.potionName);
                    def.potion = potion;
                    def.potionDoses = 1;
                    potion.vialIds[2] = def.id;
                }
                if (def.name.toLowerCase().equals(potion.primaryName)) {
                    if (primaryId == -1)
                        primaryId = def.id;
                    continue;
                }
                if (secondaryIds.length > 0)
                    for (int i = 0; i < secondaryIds.length; i++) {
                        if (secondaryIds[i] == 0 && def.name.toLowerCase().equals(potion.secondaryNames[i]))
                            secondaryIds[i] = def.id;
                    }
            }

            /*
             * Antidotes have multiple (unf) ids for some reason
             */
            if (potion == ANTIDOTE_PLUS_UNF)
                potion.vialIds[2] = 5942;
            else if (potion == ANTIDOTE_PLUS_PLUS_UNF)
                potion.vialIds[2] = 5951;

            /*
             * Register mixes
             */
            if (potion == STAMINA)
                registerUpgrade(potion, primaryId, secondaryIds[0], 1);
            else if (potion == EXTENDED_ANTIFIRE)
                registerUpgrade(potion, primaryId, secondaryIds[0], 1);
            else if (potion == EXTENDED_SUPER_ANTIFIRE)
                registerUpgrade(potion, primaryId, secondaryIds[0], 1);
            else if (potion == ANTI_VENOM)
                registerUpgrade(potion, primaryId, secondaryIds[0], 5);
            else if (potion == SANFEW_SERUM || potion == SANFEW_SERUM_1 || potion == SANFEW_SERUM_2)
                registerUpgrade(potion, primaryId, secondaryIds[0], 0);
            else if (!potion.raidsPotion)
                register(potion, primaryId, secondaryIds);

            /*
             * Extra primary ingredient registration
             */
            if (potion == Potion.SUPER_COMBAT)
                register(potion, Items.TORSTOL_POTION_UNF, secondaryIds);

            /*
             * Register decant actions
             */
            if (Arrays.stream(potion.vialIds).noneMatch(id -> id == 0)) { // don't register for weapon poison and the like
                for (int id1 : potion.vialIds) {
                    for (int id2 : potion.vialIds)
                        ItemItemAction.register(id1, id2, potion::decant);
                    ItemItemAction.register(id1, potion == GUTHIX_REST ? 1980 : potion.raidsPotion ? 20800 : 229, potion::divide);
                }
            }
        }
        BarbarianMix.registerMixes();
    }
}
