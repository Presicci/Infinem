package io.ruin.model.skills.crafting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.Tool.HAMMER;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
public enum LeatherShields{
    BLACK_DHIDE_SHEILD( 83,172.0,   2509,"black dragon leather",    22266,"redwood shield" , 4824,"rune nails" ,      22284, "a black d'hide shield"),
    RED_DHIDE_SHEILD(   76,156.0,   2507,"red dragon leather",      22263,"magic shield" ,   4823,"adamant nails" ,   22281, "a red d'hide shield"),
    BLUE_DHIDE_SHEILD(  69,140.0,   2505,"blue dragon leather",     22260,"yew shield" ,     4822,"mithril nails",    22278, "a blue d'hide shield"),
    GREEN_DHIDE_SHEILD( 62,124.0,   1745,"green dragon leather",    22257,"maple shield",    1539,"steel nails" ,     22275, "a green d'hide shield"),
    SNAKESKIN_SHEILD(   56,100.0,   6289,"snakeskin",               22254,"willow shield",   4820,"iron nails",       22272, "a snakeskin shield"),
    HARD_LEATHER_SHEILD(41,70.0,    1743,"hard leather",            22251,"oak shield" ,     4819,"bronze nails",     22269, "a hard leather shield"),
    ;

    public final int levelRequirement;
    public final double exp;
    public final int leatherID;
    public final String leatherName;
    public final int shieldID;
    public final String shieldName;
    public final int nailID;
    public final String nailName;
    public final int completedShieldID;
    public final String completedShieldName;

    LeatherShields(int levelRequirement, double exp, int leatherID, String leatherName, int shieldID, String shieldName, int nailID, String nailName, int completedShieldID, String completedShieldName) {
        this.levelRequirement = levelRequirement;
        this.exp = exp;
        this.leatherID = leatherID;
        this.leatherName = leatherName;
        this.shieldID = shieldID;
        this.shieldName = shieldName;
        this.nailID = nailID;
        this.nailName = nailName;
        this.completedShieldID = completedShieldID;
        this.completedShieldName = completedShieldName;
    }



    private static void craftLeatherShield(Player player, LeatherShields armour, int makeAmount) {
        player.closeInterfaces();
        if (!player.getStats().check(StatType.Crafting, armour.levelRequirement, "make " + armour.completedShieldName))
            return;
        int hammerAmount = player.getInventory().getAmount(HAMMER);
        if (hammerAmount <= 0) {
            player.dialogue(new MessageDialogue("You need a hammer to craft " + armour.completedShieldName + "."));
            return;
        }

        int maxLeatherAmount = player.getInventory().getAmount(armour.leatherID) / 2;
        if (maxLeatherAmount <= 0){
            player.dialogue(new MessageDialogue("You need at least two " + armour.leatherName + " to craft " + armour.completedShieldName + "."));
            return;
        }

        int maxNailAmount = player.getInventory().getAmount(armour.nailID) / 15;
        if (maxNailAmount <= 0){
            player.dialogue(new MessageDialogue("You need at least 15 " + armour.nailName + " to craft " + armour.completedShieldName + "."));
            return;
        }

        int maxShieldAmount = player.getInventory().getAmount(armour.shieldID);
        if (maxShieldAmount <= 0){
            player.dialogue(new MessageDialogue("You need a " + armour.shieldName + " to craft " + armour.completedShieldName + "."));
            return;
        }
        makeAmount = Math.min(maxShieldAmount, makeAmount);
        makeAmount = Math.min(maxNailAmount, makeAmount);
        makeAmount = Math.min(maxLeatherAmount, makeAmount);
        final int amt = makeAmount;
        player.startEvent(event -> {
            int made = 0;
            while (made++ < amt) {
                if (player.getInventory().getAmount(HAMMER) <= 0) {
                    player.dialogue(new MessageDialogue("You need a hammer to craft " + armour.completedShieldName + "."));
                    return;
                }
                int checkedNailAmount = player.getInventory().getAmount(armour.nailID) / 15;
                if (checkedNailAmount <= 0) {
                    player.dialogue(new MessageDialogue("You need at least 15 " + armour.nailName + " to craft " + armour.completedShieldName + "."));
                    return;
                }
                int checkedLeatherAmount = player.getInventory().getAmount(armour.leatherID) / 2;
                if (checkedLeatherAmount <= 0){
                    player.dialogue(new MessageDialogue("You need at least two " + armour.leatherName + " to craft " + armour.completedShieldName + "."));
                    return;
                }
                int checkedShieldAmount = player.getInventory().getAmount(armour.shieldID);
                if (checkedShieldAmount <= 0){
                    player.dialogue(new MessageDialogue("You need a " + armour.shieldName + " to craft " + armour.completedShieldName + "."));
                    return;
                }
                player.getInventory().remove(armour.nailID, 15);
                player.getInventory().remove(armour.shieldID, 1);
                player.getInventory().remove(armour.leatherID, 2);
                player.getInventory().add(armour.completedShieldID, 1);
                player.animate(1249);
                player.sendFilteredMessage("You make " + armour.completedShieldName + ".");
                player.getStats().addXp(StatType.Crafting, armour.exp, true);
                event.delay(2);
            }
        });
    }

    private static final int HARD_LEATHER = 1743;
    private static final int SNAKESKIN = 6289;
    private static final int GREEN_D_LEATHER = 1745;
    private static final int BLUE_D_LEATHER = 2505;
    private static final int RED_D_LEATHER = 2507;
    private static final int BLACK_D_LEATHER = 2509;
    private static final int OAK_SHIELD = 22251;
    private static final int WILLOW_SHIELD = 22254;
    private static final int MAPLE_SHIELD = 22257;
    private static final int YEW_SHIELD = 22260;
    private static final int MAGIC_SHIELD = 22263;
    private static final int REDWOOD_SHIELD = 22266 ;


    static {

        ItemItemAction.register(HARD_LEATHER, OAK_SHIELD, (player, leather, shield) -> SkillDialogue.make(player,
                new SkillItem(HARD_LEATHER_SHEILD.completedShieldID).addAction((p, amount, event)-> craftLeatherShield(p, HARD_LEATHER_SHEILD, amount))));

        ItemItemAction.register(SNAKESKIN, WILLOW_SHIELD, (player, leather, shield) -> SkillDialogue.make(player,
                new SkillItem(SNAKESKIN_SHEILD.completedShieldID).addAction((p, amount, event)-> craftLeatherShield(p, SNAKESKIN_SHEILD, amount))));

        ItemItemAction.register(GREEN_D_LEATHER, MAPLE_SHIELD, (player, leather, shield) -> SkillDialogue.make(player,
                new SkillItem(GREEN_DHIDE_SHEILD.completedShieldID).addAction((p, amount, event)-> craftLeatherShield(p, GREEN_DHIDE_SHEILD, amount))));

        ItemItemAction.register(BLUE_D_LEATHER, YEW_SHIELD, (player, leather, shield) -> SkillDialogue.make(player,
                new SkillItem(BLUE_DHIDE_SHEILD.completedShieldID).addAction((p, amount, event)-> craftLeatherShield(p, BLUE_DHIDE_SHEILD, amount))));

        ItemItemAction.register(RED_D_LEATHER, MAGIC_SHIELD, (player, leather, shield) -> SkillDialogue.make(player,
                new SkillItem(RED_DHIDE_SHEILD.completedShieldID).addAction((p, amount, event)-> craftLeatherShield(p, RED_DHIDE_SHEILD, amount))));

        ItemItemAction.register(BLACK_D_LEATHER, REDWOOD_SHIELD, (player, leather, shield) -> SkillDialogue.make(player,
                new SkillItem(BLACK_DHIDE_SHEILD.completedShieldID).addAction((p, amount, event)-> craftLeatherShield(p, BLACK_DHIDE_SHEILD, amount))));
    }


}