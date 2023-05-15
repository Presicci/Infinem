/*
 * Copyright (c) 2021, MakingStan
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.code;

import java.util.HashMap;

public class SkillMap {
    public static HashMap<String, Integer> skillMap = new HashMap<String, Integer>(); //the first argument is what type of material and the second type is the xp it gives

    public SkillMap() {
        //for firemaking
        skillMap.put("Normal", 40);
        skillMap.put("Oak", 60);
        skillMap.put("Willow", 90);
        skillMap.put("Wintertodt", 50000);

        //for mining
        skillMap.put("Copper/Tin ore", 17); //17.5
        skillMap.put("Iron ore", 35);
        skillMap.put("Motherlode Mine", 60);

        //for woodcutting
        skillMap.put("Normal logs", 25);
        skillMap.put("Oak logs", 37); //37.5
        skillMap.put("Willow logs", 67); //67.5
        skillMap.put("Teak logs", 85);
        skillMap.put("Magic logs", 250);
        skillMap.put("Redwoods logs", 380);

        //for agility (i am picking the xp of the end of any course, hopefully this works properly...)
        skillMap.put("Ardougne Rooftop Course", 529);
        skillMap.put("Rellekka Rooftop Course", 475);
        skillMap.put("Seers' Village Rooftop Course", 435);
        skillMap.put("Falador Rooftop Course", 180);
        skillMap.put("Canifis Rooptop Course", 175);
        skillMap.put("Varrock Rooftop Course", 125);
        skillMap.put("Al Kharid Rooftop Course", 30);
        skillMap.put("Draynor Village Rooftop Course", 79);
        skillMap.put("Gnome Stronghold Agility Course", 46);

        //for thieving
        skillMap.put("Knights of Ardougne", 84); //84.3
        skillMap.put("Fruit stalls", 28); //28.5
        skillMap.put("Bakery stalls", 16);
        skillMap.put("man/women",8);

        //for cooking
        skillMap.put("Raw Anglers", 230);
        skillMap.put("Raw Sharks", 210);
        skillMap.put("Raw karambwans", 190);
        skillMap.put("Raw Salmons", 90);
        skillMap.put("Raw Trouts", 70);
        skillMap.put("Raw Sardines",40);

        //for crafting
        skillMap.put("Air Battlestaves", 137); //137.5
        skillMap.put("Green Dragonhide Bodies", 186);
        skillMap.put("Water Battlestaves", 100);
        skillMap.put("Emeralds", 67); //67.5
        skillMap.put("Sapphires", 50);
        skillMap.put("Opals", 15);

        //for fishing
        skillMap.put("Leaping Salmon/Sturgeon", 70);
        skillMap.put("Trout/Salmon", 50); //70 for salmon im also assuming catch rate is 50%...
        skillMap.put("Shrimp/Anchovy", 10);

        //for herblore
        skillMap.put("Super Combat", 150);
        skillMap.put("Super Restore", 142); //142.5
        skillMap.put("Prayer Potions", 87);//87.5
        skillMap.put("Attack Potions", 25);

        // for fletching
        skillMap.put("Magic long bows", 91); //91.5
        skillMap.put("Yew long bows", 75);
        skillMap.put("Steel arrows", 75);
        skillMap.put("Iron darts", 4); //3.8

        // for construction
        skillMap.put("Oak Dungeon Doors", 600);
        skillMap.put("Oak Larders", 480);
        skillMap.put("Oak armchairs",180);
        skillMap.put("Oak chairs", 120);
        skillMap.put("Wooden chairs", 87);
        skillMap.put("Crude wooden chairs", 58);

        //for hunter
        skillMap.put("Black chinchompa's", 325);
        skillMap.put("Red chinchompa's", 265);
        skillMap.put("Dark/Spotted Kebbits", 132);
        skillMap.put("Spotted Kebbits", 104);
        skillMap.put("Prickly Kebbits", 204);
        skillMap.put("Crimson Swifts", 34);
        skillMap.put("Ruby Butterflies", 24);
        skillMap.put("Feldip Weasels", 48);
        skillMap.put("Polar Kebbits", 30);

        //for smithing
        skillMap.put("Gold bars (at the blast furnace)", 56); //56.2
        skillMap.put("Iron Platebodies", 125);
        skillMap.put("Iron 2h Swords", 75);

        //for prayer
        skillMap.put("Dragon bones", 252);
    }
}
