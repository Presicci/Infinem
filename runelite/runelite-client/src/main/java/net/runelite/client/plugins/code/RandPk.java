
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

import java.util.Random;

public class RandPk {
    private static int xp = 1;
    private static String pkwep;


    public static String randveng() {
        Random random = new Random();
        String[] pkWeapons = {
                "Dragon halbert","Granite maul","Rod of ivandis","Dragon harpoon","Ancient crozier",
                "Steel mace","Granite longsword","Mithril crossbow","Steel crossbow","Black dagger",
                "Steel knife","Pearl fishing rod","Adamant spear","Staff of earth","Adamant longsword",
                "Armadyl godsword","Granite maul","Whip","Magic staff","Wooden spoon","Rune knife","Adamant hasta",
                "Dragon pickaxe","Leaf bladed sword","Leaf Bladed axe","Beginner wand"
        };
        pkwep = pkWeapons[random.nextInt(pkWeapons.length)];
        return pkwep;
    };

}
