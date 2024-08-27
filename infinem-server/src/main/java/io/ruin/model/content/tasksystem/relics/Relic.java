package io.ruin.model.content.tasksystem.relics;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/11/2022
 */
public enum Relic {
    /**
    Tier 1 - Experience focus - 7x XP
    Eye of the Artisan: https://oldschool.runescape.wiki/w/Eye_of_the_Artisan
        Receive double the usual amount of experience in Smithing, Cooking, Firemaking, Herblore, Fletching, Crafting and Construction.
        This stacks with any existing experience multipliers.
    Gift of the Gatherer: https://oldschool.runescape.wiki/w/Gift_of_the_Gatherer
        Receive double the usual amount of experience in Mining, Fishing, Woodcutting, Hunter, Thieving, Farming and Runecraft.
        This stacks with any existing experience multipliers.
    Way of the Warrior: https://oldschool.runescape.wiki/w/Way_of_the_Warrior
        Receive double the usual amount of experience in Attack, Strength, Defence, Hitpoints, Magic, Ranged and Prayer.
        This stacks with any existing experience multipliers.

    Tier 2 - Skilling boosts - Drastically reduced run drain 9x XP
    Endless Harvest
    Prouction Master
     ANT

    Tier 3 - Teleports - Unlock dungeon hub - 11x XP
    Eternal Jeweller
    Fairy's Flight
    Dungeon Hub Premium

    Tier 4 - Combat - 13x XP
    Global rare monster drop - lets you reset this tier
     Quick Shot
        1 tick faster
        50% increased accuracy
        Ava's effect w/o avas
        consecutive attacks inc attack speed?
     Quick Cast
        1 tick faster
        50% increased accuracy
        rune pouch, increased rune pouch size
        AoE
     Fluid Strikes
        1 tick faster
        25% increased accuracy
        15% less damage from non-lethal
        x4 hp regen
        immune to poison/venom/dragonfire/icy breath

    Tier 5 - Skilling - 15x XP
     Key Master
        higher chance of key drops
        exclusive key to relic users (skeleton key)
        combination of all the chests
        Chance to drop from anything of a certain tier? Ex. Monster would have to be of a certain level'
        For skilling, you'd have to be using or gathering high tier materials
     Rare Drop Table Enhancer
        roll the rare drop table twice
        can roll while skilling
        don't need RoW
        Maybe higher chance to roll the RDT in the first place?
     Pet Rolling
        makes pets more common via thresholds
        duplicate pet rolls give a payout
        Payout in the form of what? Clue scroll loot? GP? unique loot table? Rare loot table?

    Tier 6 - Clue vs slayer vs ? - 17x XP
        Unnatural Selection
        Treasure Seeker

    Tier 7 - Combat - 19x XP
    Weapon Specialist: https://oldschool.runescape.wiki/w/Weapon_Specialist
        The player's special attack energy regenerates at a rate of 30% every 30 seconds instead of 10%.
        Special attacks that cost more than 20% energy (such as the Bandos godsword) will cost 20% instead.

     **/
    EYE_OF_THE_ARTISAN(1, 1),
    GIFT_OF_THE_GATHERER(1, 2),
    WAY_OF_THE_WARRIOR(1, 3),
    ENDLESS_HARVEST(2, 1),
    PRODUCTION_MASTER(2, 2),
    TRICKSTER(2, 3),
    FAIRYS_FLIGHT(3, 1),
    GLOBETROTTER(3, 2),
    DUNGEON_HUB_PREMIUM(3, 3),
    POISON_RELIC(4, 1),
    TANK_RELIC(4, 1)
    ;

    @Getter private final int tier, configValue;
    @Getter private final int[] itemIds;

    Relic(int tier, int configValue, int... itemIds) {
        this.tier = tier;
        this.configValue = configValue;
        this.itemIds = itemIds;
    }

    public static int tiers;

    static {
        for (Relic relic : Relic.values()) {
            if (relic.getTier() > tiers) {
                tiers = relic.getTier();
            }
        }
    }
}
