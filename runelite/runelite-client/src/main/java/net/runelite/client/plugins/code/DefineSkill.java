package net.runelite.client.plugins.code;

import net.runelite.api.Skill;

public class DefineSkill {
    public static Skill defineSkill(String skillName) {
        Skill skill;
        if(skillName.equalsIgnoreCase("Agility"))
        {
            skill = Skill.AGILITY;
        }
        else if(skillName.equalsIgnoreCase("Runecrafting"))
        {
            skill = Skill.RUNECRAFT;
        }
        else if(skillName.equalsIgnoreCase("Smithing"))
        {
            skill = Skill.SMITHING;
        }
        else if(skillName.equalsIgnoreCase("Prayer"))
        {
            skill = Skill.PRAYER;
        }
        else if(skillName.equalsIgnoreCase("Mining"))
        {
            skill = Skill.MINING;
        }
        else if(skillName.equalsIgnoreCase("Hunter"))
        {
            skill = Skill.HUNTER;
        }
        else if(skillName.equalsIgnoreCase("Cooking"))
        {
            skill = Skill.COOKING;
        }
        else if(skillName.equalsIgnoreCase("Herblore"))
        {
            skill = Skill.HERBLORE;
        }
        else if(skillName.equalsIgnoreCase("Fletching"))
        {
            skill = Skill.FLETCHING;
        }
        else if (skillName.equalsIgnoreCase("Fishing"))
        {
            skill = Skill.FISHING;
        }
        else if(skillName.equalsIgnoreCase("Construction"))
        {
            skill = Skill.CONSTRUCTION;
        }
        else if(skillName.equalsIgnoreCase("Woodcutting"))
        {
            skill = Skill.WOODCUTTING;
        }
        else if(skillName.equalsIgnoreCase("Thieving"))
        {
            skill = Skill.THIEVING;
        }
        else if(skillName.equalsIgnoreCase("Firemaking"))
        {
            skill = Skill.FIREMAKING;
        }
        else if(skillName.equalsIgnoreCase("Crafting"))
        {
            skill = Skill.CRAFTING;
        }
        else
        {
            skill = Skill.HITPOINTS; // something random to avoid errors shouldnt matter anyway
        }
        return skill;

    }
}
