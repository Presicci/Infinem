
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

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.plugins.code.Sound.SoundEngine;
import net.runelite.client.plugins.code.Sound.SoundEnum;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Slf4j
@Singleton
public class BoredPanel extends PluginPanel {
    public static int xp,randint,randomNumber = 1; //starting with 1 because it else will autocomplete the task.
    public static JButton generate, wikiButton, completed, resetPointsButton;
    public static JTextField text;
    public JCheckBox pking, everything, pvming, skilling, prif;
    public static String Chekker, url, skillTask,skilT;
    public static String[] pvmTasks, pvmg;
    public static JTextArea explain,points;
    private BufferedImage img = ImageUtil.loadImageResource(getClass(), "pix.png");
    public JLabel Image = new JLabel(new ImageIcon(img));
    private final Random random = new Random();
    private int index;

    @Inject
    Client client;

    @Inject
    BoredConfig config;

    @Inject
    SoundEngine soundEngine;

    @Inject
    CheckCombat checkCombat;

    @Inject
    public BoredPanel() throws IOException {
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setBorder(new EmptyBorder(8, 8, 8, 8));

        prif = new JCheckBox("Prifddinas");
        pvming = new JCheckBox("Pvm");
        pking = new JCheckBox("Pking");
        everything = new JCheckBox("Everything");
        skilling = new JCheckBox("Skilling");
       // explain = new JTextArea("If this plugin does not \nwork please log in :).");
      //  points = new JTextArea("Points: 0"); // cameron this caused issues with some with interface on screen
        completed = new JButton("Completed");
        wikiButton = new JButton("Wiki");
        resetPointsButton = new JButton("Reset Points");
        UserHandle.start();


        text = new JTextField("Skills Generator");
        prif.setBounds(0, 50, 100, 30);
        prif.setBackground(Color.gray);
        pvming.setBounds(10, 50, 100, 30);
        pvming.setBackground(Color.gray);
        skilling.setBounds(20, 50, 100, 30);
        skilling.setBackground(Color.gray);
        everything.setBounds(0, 80, 200, 30);
        everything.setBackground(Color.gray);
        pking.setBounds(10, 80, 200, 30);
        pking.setBackground(Color.gray);

        Image.setMaximumSize(new Dimension(400, 400));

        wikiButton.setForeground(Color.orange);

        //points.setEditable(false);
        //points.setBackground(Color.gray);

        //explain.setEditable(false);
        //explain.setBackground(Color.gray);

        text.setEditable(false);
        text.setFont(new Font("Verdana", Font.BOLD, 15));
        text.setBackground(Color.gray);

        generate = new JButton("Generate");
        generate.setForeground(Color.WHITE);
        generate.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        completed.setForeground(Color.GREEN);
        completed.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        completed.setVisible(false);

        resetPointsButton.setForeground(Color.RED);
        resetPointsButton.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        resetPointsButton.addActionListener(e -> {
            try
            {
                UserHandle.reset();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        });

        completed.addActionListener(e -> {
            completed();
        });
        wikiButton.addActionListener(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        generate.addActionListener(e -> {
            generateAction();
        });


        //add(points);
        add(resetPointsButton);
        add(prif);
        add(everything);
        add(pking);
        add(skilling);
        add(pvming);
        add(text, BorderLayout.NORTH);
        //add(explain);
        add(Image);
        add(completed);
        add(wikiButton);
        add(generate, BorderLayout.SOUTH);
    }
    public void generateAction()
    {
        generate.setText("Regenerate");
        BoredPlugin.counter = 0;
        setItUp();
        BoredPlugin.preTxt = explain.getText();
    }
    public void completed()
    {
        if (config.CengineerC()) {
            soundEngine.playClip(SoundEnum.COM);
        }
        try {
            UserHandle.handle();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        remove(Image);
        text.setText("Completed!");
        img = ImageUtil.loadImageResource(BoredPlugin.class, "/pix.png");
        Image = new JLabel(new ImageIcon(img));
        explain.setText("You have completed your task!\n" +
                "feel free to generate a new one!");
        add(Image);
    }
    private void setItUp() {
        completed.setVisible(true);
        Random random = new Random();
        boolean whatCb = checkCombat.checkCombat();
        String[] medium_lvl_pvm = {
                "Barrows", "Zulrah", "Crazy archaeologist", "Venenatis", "Deranged archaeologist",
                "Mole", "Bryophyta", "Obor", "King Black Dragon"
        };
        String[] high_lvl_pvm = {
                "Corrupted Gauntlet", "Gauntlet", "Bandos", "Armadyl", "Zamorak", "Saradomin",
                "Zulrah", "Vorkath", "Tob", "Cox", "Nightmare", "Phosani's nightmare", "Corporeal Beast",
                "Callisto", "Vet'ion", "Barrows"
        };
        String[] skilling_Activities = {
                "Agility", "Herblore", "Crafting", "Fishing",
                "Fletching", "Construction", "Hunter", "Mining",
                "Prayer", "Runecrafting", "Firemaking", "Smithing",
                "Thieving", "Woodcutting", "Cooking"
        };
        String[] skillPvm = {
                "Corrupted Gauntlet", "Gauntlet", "Agility", "Herblore", "Crafting", "Fishing",
                "Fletching", "Construction", "Hunter", "Mining",
                "Prayer", "Runecrafting", "Firemaking", "Smithing",
                "Thieving", "Woodcutting", "Cooking", "Bandos", "Armadyl", "Zamorak", "Saradomin",
                "Zulrah", "Vorkath", "Tob", "Cox", "Nightmare", "Phosani's nightmare", "Corporeal Beast",
                "Callisto", "Vet'ion", "Barrows"
        };
        String[] skillPvmLow = {
                "Agility", "Herblore", "Crafting", "Fishing",
                "Fletching", "Construction", "Hunter", "Mining",
                "Prayer", "Runecrafting", "Firemaking", "Smithing",
                "Thieving", "Woodcutting", "Cooking","Barrows", "Zulrah", "Crazy archaeologist", "Venenatis", "Deranged archaeologist",
                "Mole", "Bryophyta", "Obor", "King Black Dragon"
        };
        String[] skillLowPvmPk = {
                "Agility", "Herblore", "Crafting", "Fishing",
                "Fletching", "Construction", "Hunter", "Mining",
                "Prayer", "Runecrafting", "Firemaking", "Smithing",
                "Thieving", "Woodcutting", "Cooking","Barrows", "Zulrah", "Crazy archaeologist", "Venenatis", "Deranged archaeologist",
                "Mole", "Bryophyta", "Obor", "King Black Dragon","Venging", "Nh", "Hybrid"
        };
        String[] skillPk = {
                "Agility", "Herblore", "Crafting", "Fishing",
                "Fletching", "Construction", "Hunter", "Mining",
                "Prayer", "Runecrafting", "Firemaking", "Smithing",
                "Thieving", "Woodcutting", "Cooking","Venging", "Nh", "Hybrid"
        };
        String[] pk = {
                "Venging", "Nh", "Hybrid"
        };
        String[] Everything = {
                "Corrupted Gauntlet", "Gauntlet", "Venging", "Nh", "Hybrid", "Agility", "Herblore",
                "Crafting", "Fishing",
                "Fletching", "Construction", "Hunter", "Mining", "Prayer", "Runecrafting", "Firemaking", "Smithing",
                "Thieving", "Woodcutting", "Bandos", "Armadyl", "Zamorak", "Saradomin",
                "Zulrah", "Vorkath", "Tob", "Cox", "Nightmare", "Phosani's nightmare", "Corporeal Beast",
                "Callisto", "Vet'ion", "Barrows"
        };
        String[] everythingLow = {
                "Venging", "Nh", "Hybrid", "Agility", "Herblore",
                "Crafting", "Fishing",
                "Fletching", "Construction", "Hunter", "Mining", "Prayer", "Runecrafting", "Firemaking", "Smithing",
                "Thieving", "Woodcutting","Barrows", "Zulrah", "Crazy archaeologist", "Venenatis", "Deranged archaeologist",
                "Mole", "Bryophyta", "Obor", "King Black Dragon"
        };


            if (pvming.isSelected()&&!whatCb&& skilling.isSelected()&&pking.isSelected())
            {
                randint = random.nextInt(skillLowPvmPk.length);
                Chekker = skillLowPvmPk[randint];
                check();
                text.setText(skillLowPvmPk[randint]);
            }
            else if (skilling.isSelected()&&pking.isSelected())
            {

            randint = random.nextInt(skillPk.length);
            Chekker = skillPk[randint];
            check();
            text.setText(skillPk[randint]);
            } else if (everything.isSelected()&&!whatCb)
            {

                randint = random.nextInt(everythingLow.length);
                Chekker = everythingLow[randint];
                check();
                text.setText(everythingLow[randint]);
            }
            else if (pvming.isSelected()&&!whatCb&& skilling.isSelected())
            {

                randint = random.nextInt(skillPvmLow.length);
                Chekker = skillPvmLow[randint];
                check();
                text.setText(skillPvmLow[randint]);
            }
            else if (pvming.isSelected() && skilling.isSelected() && prif.isSelected())
            {

                randint = random.nextInt(skillPvm.length);
                Chekker = skillPvm[randint];
                check();
                text.setText(skillPvm[randint]);
            }
            else if (everything.isSelected() && prif.isSelected())
            {

                randint = random.nextInt(Everything.length);
                Chekker = Everything[randint];
                check();
                text.setText(Everything[randint]);
            }
            else if (pvming.isSelected()&&!whatCb)
            {

                randint = random.nextInt(medium_lvl_pvm.length);
                Chekker = medium_lvl_pvm[randint];
                check();
                text.setText(medium_lvl_pvm[randint]);
            }
            else if (pvming.isSelected() && prif.isSelected())
            {

                randint = random.nextInt(high_lvl_pvm.length);
                Chekker = high_lvl_pvm[randint];
                check();
                text.setText(high_lvl_pvm[randint]);
            }
            else if (pvming.isSelected() && skilling.isSelected())
            {

                randint = (int) Math.floor(Math.random() * (skillPvm.length - 1 - 2 + 1) + 2);
                Chekker = skillPvm[randint];
                check();
                text.setText(skillPvm[randint]);
            }
            else if (pvming.isSelected())
            {

                randint = (int) Math.floor(Math.random() * (high_lvl_pvm.length - 1 - 2 + 1) + 2);
                Chekker = high_lvl_pvm[randint];
                check();
                text.setText(high_lvl_pvm[randint]);
            }
            else if (skilling.isSelected())
            {

                randint = random.nextInt(skilling_Activities.length);
                Chekker = skilling_Activities[randint];
                check();
                text.setText(skilling_Activities[randint]);
            } else if (pking.isSelected()) {

                randint = random.nextInt(pk.length);
                Chekker = pk[randint];
                check();
                text.setText(pk[randint]);
            } else if (everything.isSelected()) {

                randint = (int) Math.floor(Math.random() * (Everything.length - 1 - 2 + 1) + 2);
                Chekker = Everything[randint];
                check();
                text.setText(Everything[randint]);
            } else {
                text.setText("Thats not valid.");
            }
            if(!text.equals("Thats not valid.")) {
                checkThatShit();
            }

    }

    private void check()
    {


        skillCheckTask();

        pvmTasks = new String[] {
                "Kill " + Chekker + " " + (int) Math.floor(Math.random() * (10 - 5 + 1) + 5) + " times",
                "Get any unique from \n" + Chekker + "!",
                "Kill " + Chekker + " with gear \nworth under " +
                        (int) Math.floor(Math.random() * (50 - 15 + 1) + 15)
                        + "M"
        };
        pvmg = new String[] {
                "Kill " + Chekker + " " +
                        (int) Math.floor(Math.random() * (10 - 5 + 1) + 5) + " times",
                "Get any unique from " + Chekker + "!",
                "Kill " + Chekker + " in under \n6 minutes"
        };

        remove(wikiButton);
    }
    private void checkThatShit()
    {
        index = random.nextInt(3);
        skills();
        pk();
        skillingCheck();
        pvmCheck();

        add(Image);
        add(wikiButton);
        add(generate, BorderLayout.SOUTH);
    }

    private int randomInt(int min, int max)
    {
        int randomNum = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return randomNum;
    }

    public void skillCheckTask()
    {
        if (Chekker.equals("Fishing"))
        {
            randomNumber = randomInt(100, 200);
            xp = client.getSkillExperience(Skill.FISHING);
            skillTask = SkillCheck.fishing();
            skilT = "Fish " + randomNumber + " " + skillTask + "!";
        }
        else if (Chekker.equals("Prayer"))
        {
            randomNumber = randomInt(200, 300);
            xp = client.getSkillExperience(Skill.PRAYER);
            String[] altar = {"Gilded Altar", "Chaos Altar"};
            Random random = new Random();
            int randint = random.nextInt(2);
            skillTask = SkillCheck.prayer();
            skilT = "Sacrifice " + randomNumber + " " + skillTask + "\nto the " + altar[randint] + "!";
        }
        else if (Chekker.equals("Runecrafting"))
        {
            randomNumber = randomInt(600, 800);
            xp = client.getSkillExperience(Skill.RUNECRAFT);
            skillTask = SkillCheck.runecrafting();
            if (skillTask.equals("Ourania Altar")) {
                skilT = "Craft " + randomNumber + " runes at the \n" + skillTask + "!";
            } else {
                skilT = "Craft " + randomNumber + " " + skillTask + "!";

            }
        }
        else if (Chekker.equals("Smithing"))
        {
            randomNumber = randomInt(400,600);
            xp = client.getSkillExperience(Skill.SMITHING);
            skillTask = SkillCheck.smithing();
            if (skillTask.equals("Just do the knights sword quest cmonn!")) {
                skilT = "Just do the knights sword quest cmonn!";
            } else {
                skilT = "Smith\n" + randomNumber + " " + skillTask + "!";
            }
        }
        else if (Chekker.equals("Mining"))
        {
            randomNumber = randomInt(120, 200);
            xp = client.getSkillExperience(Skill.MINING);
            skillTask = SkillCheck.mining();


            if (skillTask.equals("Motherlode Mine"))
            {
                skilT = "Mine " + randomNumber + " pay dirt\nin the " + skillTask + "!";
            }
            else if(skillTask.equals("Volcanic Mine"))
            {
                randomNumber = randomInt(120, 200);
                skilT =  "Do " + randomNumber + " " + skillTask + "rounds!";
            }
            else
            {
                skilT = "Mine " + randomNumber + " " + skillTask + "!";
            }
        }
        else if (Chekker.equals("Agility"))
        {
            randomNumber = randomInt(15, 50);
            xp = client.getSkillExperience(Skill.AGILITY);
            skillTask = SkillCheck.agility();
            skilT = "Complete " + randomNumber + " laps of the\n" + skillTask + "!";
        }
        else if (Chekker.equals("Hunter"))
        {
            randomNumber = randomInt(200, 300);
            xp = client.getSkillExperience(Skill.HUNTER);
            skillTask = SkillCheck.hunter();
            skilT = "Hunt " + randomNumber + " " + skillTask + "!";
        }
        else if (Chekker.equals("Construction"))
        {
            randomNumber = randomInt(100, 200);
            xp = client.getSkillExperience(Skill.CONSTRUCTION);
            skillTask = SkillCheck.construction();
            skilT = "Build " + randomNumber + " " + skillTask + "!";
        }
        else if (Chekker.equals("Herblore"))
        {
            randomNumber = randomInt(300, 600);
            xp = client.getSkillExperience(Skill.HERBLORE);
            skillTask = SkillCheck.herblore();
            skilT = "Make " + randomNumber + " " + skillTask + "!";
        }
        else if (Chekker.equals("Woodcutting"))
        {
            randomNumber = randomInt(100, 200);
            xp = client.getSkillExperience(Skill.WOODCUTTING);
            skillTask = SkillCheck.woodcutting();
            skilT = "Chop " + randomNumber + " " + skillTask + "!";
        }
        else if (Chekker.equals("Crafting"))
        {
            randomNumber = randomInt(100, 200);
            xp = client.getSkillExperience(Skill.CRAFTING);
            skillTask = SkillCheck.crafting();
            skilT = "Craft " + randomNumber + " " + skillTask + "!";
        }
        else if (Chekker.equals("Cooking"))
        {
            randomNumber = randomInt(100, 300);
            xp = client.getSkillExperience(Skill.COOKING);
            skillTask = SkillCheck.cooking();
            skilT = "Cook " + randomNumber + " " + skillTask + "!";
        }
        else if (Chekker.equals("Thieving"))
        {
            randomNumber = randomInt(100, 200);
            xp = client.getSkillExperience(Skill.THIEVING);
            skillTask = SkillCheck.theiving();
            skilT = "Thief " + skillTask + " " + randomNumber + " times!";
        }
        else if (Chekker.equals("Fletching")) {

            randomNumber = randomInt(200, 300);
            xp = client.getSkillExperience(Skill.FLETCHING);
            skillTask = SkillCheck.fletching();
            skilT =  "Fletch " + skillTask + " " + randomNumber + " times!";
        }
        else if (Chekker.equals("Firemaking"))
        {
            xp = client.getSkillExperience(Skill.FIREMAKING);
            skillTask = SkillCheck.firemaking();

            if (skillTask.equals("Wintertodt"))
            {
                randomNumber = randomInt(3, 10);
                skilT = "Do " + randomNumber + " " + skillTask + " rounds!";
            }
            else
            {
                randomNumber = randomInt(50, 200);
                skilT = "Burn " + randomNumber + " " + skillTask + " logs!";
            }
        }
        BoredPlugin.skillValue = skillTask;
    }
    private void skills()
    {
        remove(Image);
        if (Chekker.equals("Agility"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Agility.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Agility";
        }
        else if (Chekker.equals("Runecrafting"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Runecraft.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Runecraft";
        }
        else if (Chekker.equals("Smithing"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Smithing.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Smithing";
        }
        else if (Chekker.equals("Prayer"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Prayer.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Prayer";
        }
        else if (Chekker.equals("Mining"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Mining.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Mining";
        }
        else if (Chekker.equals("Hunter"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Hunter.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Hunter";
        }
        else if (Chekker.equals("Construction"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Construction.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Construction";
        }
        else if (Chekker.equals("Cooking"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Cooking.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Cooking";
        }
        else if (Chekker.equals("Herblore"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Herblore.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Herblore";
        }
        else if (Chekker.equals("Fletching"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Fletching.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Fletching";
        }
        else if (Chekker.equals("Fishing"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Fishing.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Fishing";

        }
        else if (Chekker.equals("Woodcutting"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Woodcutting.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Woodcutting";

        }
        else if (Chekker.equals("Thieving"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Thieving.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Thieving";
        }
        else if (Chekker.equals("Firemaking"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Firemaking.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Firemaking";
        }
        else if (Chekker.equals("Crafting"))
        {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Crafting.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Crafting";
        }
    }
    private void pk() {

        if (Chekker.equals("Venging")) {

            String pkwep = RandPk.randveng();

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Venging.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText("Kill 1 Player using a \n" + pkwep + "\nas a last hit in  Vengance pking!");
            url = "https://oldschool.runescape.wiki/w/Player_killing";
        } else if (Chekker.equals("Hybrid")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Hybrid.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText("Kill " + (int) Math.floor(Math.random() * (10 - 5 + 1) + 5) + " Players using Hybrid!");
            url = "https://oldschool.runescape.wiki/w/Player_killing";
        }
        else if (Chekker.equals("Nh")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Tribrid.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText("Kill " + (int) Math.floor(Math.random() * (10 - 5 + 1) + 5) + " Players using Tribrid!");
            url = "https://oldschool.runescape.wiki/w/Player_killing";
        }
    }
    public void skillingCheck() {
        if (Chekker.equals("Agility")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Agility.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Agility";
        }
        else if (Chekker.equals("Runecrafting")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Runecraft.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Runecraft";
        } else if (Chekker.equals("Smithing")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Smithing.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Smithing";
        } else if (Chekker.equals("Prayer")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Prayer.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Prayer";
        } else if (Chekker.equals("Mining")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Mining.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Mining";
        } else if (Chekker.equals("Hunter")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Hunter.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Hunter";
        } else if (Chekker.equals("Construction")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Construction.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Construction";
        } else if (Chekker.equals("Cooking")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Cooking.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Cooking";
        } else if (Chekker.equals("Herblore")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Herblore.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Herblore";
        } else if (Chekker.equals("Fletching")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Fletching.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Fletching";
        } else if (Chekker.equals("Fishing")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Fishing.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Fishing";

        } else if (Chekker.equals("Woodcutting")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Woodcutting.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Woodcutting";

        } else if (Chekker.equals("Thieving")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Thieving.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Thieving";
        } else if (Chekker.equals("Firemaking")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Firemaking.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Firemaking";
        } else if (Chekker.equals("Crafting")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Crafting.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(skilT);
            url = "https://oldschool.runescape.wiki/w/Crafting";
        } else if (Chekker.equals("Corrupted Gauntlet")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/CG.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmg[index]);
            url = "https://oldschool.runescape.wiki/w/The_Gauntlet#Corrupted_Gauntlet";
        }
    }
    private void pvmCheck() {
        if (Chekker.equals("Crazy archaeologist")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Crazy archaeologist.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Crazy_archaeologist";
        } else if (Chekker.equals("King Black Dragon")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/King Black Dragon.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/King_Black_Dragon";
        } else if (Chekker.equals("Obor")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Obor.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Obor";
        } else if (Chekker.equals("Venenatis")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Venenatis.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Venenatis";
        } else if (Chekker.equals("Deranged archaeologist")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Deranged archeologist.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Deranged_archaeologist";
        } else if (Chekker.equals("Mole")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Mole.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Giant_Mole";
        } else if (Chekker.equals("Bryophyta")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Bryophyta.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Bryophyta";
        } else if (Chekker.equals(("Gauntlet"))) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/G.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmg[index]);
            url = "https://oldschool.runescape.wiki/w/The_Gauntlet";
        } else if (Chekker.equals(("Corrupted Gauntlet"))) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/CG.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmg[index]);
            url = "https://oldschool.runescape.wiki/w/The_Gauntlet#Corrupted_Gauntlet";
        } else if (Chekker.equals("Bandos")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/General.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/General_Graardor";
        } else if (Chekker.equals("Armadyl")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Kree.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Kree%27arra";
        } else if (Chekker.equals("Zamorak")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Kril.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/K%27ril_Tsutsaroth";
        } else if (Chekker.equals("Saradomin")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Zilyana.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Commander_Zilyana";
        } else if (Chekker.equals("Zulrah")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Zulrah.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Zulrah";
        } else if (Chekker.equals("Vorkath")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Vorkath.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Vorkath";
        } else if (Chekker.equals("Tob")) {

            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Tob.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Theatre_of_Blood";
        } else if (Chekker.equals("Cox")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Cox.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Chambers_of_Xeric";
        } else if (Chekker.equals("Nightmare")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Nightmare.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/The_Nightmare";
        } else if (Chekker.equals("Phosani's nightmare")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/PNightmare.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Phosani%27s_Nightmare";
        } else if (Chekker.equals("Corporeal Beast")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Corp.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Corporeal_Beast";
        } else if (Chekker.equals("Callisto")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Callisto.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Callisto";
        } else if (Chekker.equals("Vet'ion")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Vet.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Callisto";
        } else if (Chekker.equals("Barrows")) {
            img = ImageUtil.loadImageResource(BoredPlugin.class, "/Barrows.png");
            Image = new JLabel(new ImageIcon(img));
            explain.setText(pvmTasks[index]);
            url = "https://oldschool.runescape.wiki/w/Barrows";
        }
    }
}
