
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

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.code.overlay.BoredOverlay;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.HotkeyListener;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
		name = "Skills generator",
		description = "A plug-in that gives you tasks to do. An excellent plug-in when you have nothing to do.",
		tags = {"whattodo", "idk", "bored","Generator","generator","skills","boredom", "SKILLING", "PVM","PKING","training","tasks"}
)
public class  BoredPlugin extends Plugin {
	public static Image ICON;
	private BoredPanel panel;
	private NavigationButton navButton;
	long preXp = 0, loginXp = 0;
	public static long xpDrop = 0, counter = 0, toGo = BoredPanel.randomNumber;
	public static String skillValue = BoredPanel.skillTask;
	public static String preTxt;


	private static GameStateChanged gameState;

	@Inject
	public Client client;

	@Inject
	SkillMap map;

	@Inject
	private BoredConfig config;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private ItemManager itemManager;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private KeyManager keyManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private BoredOverlay overlay;

	public BoredPlugin() {

	}

	private final HotkeyListener setGenerateListener = new HotkeyListener(() -> config.generateHotKey())
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (config.generateHotKey().matches(e))
			{
				panel.generateAction();
			}
		}
	};


	@Override
	protected void startUp()
	{
		final BufferedImage panelIcon = ImageUtil.loadImageResource(getClass(), "img.png");
		panel = injector.getInstance(BoredPanel.class);
		ICON = new ImageIcon(panelIcon).getImage();


		navButton = NavigationButton.builder()
				.tooltip("Skills Generator")
				.icon(panelIcon)
				.priority(4)
				.panel(panel)
				.build();
		clientToolbar.addNavigation(navButton);
		overlayManager.add(overlay);
		keyManager.registerKeyListener(setGenerateListener);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if(config.panel())
		{
			clientToolbar.addNavigation(navButton);
		}
		else
		{
			clientToolbar.removeNavigation(navButton);
		}
	}

	@Subscribe
	private void onGameTick(GameTick tick)
	{
		long skillExperience = client.getSkillExperience(DefineSkill.defineSkill(BoredPanel.text.getText()));
		preXp = loginXp;

		if (loginXp != 0 && (skillExperience - preXp <= 0))
		{
			return;
		}
		else if (loginXp != 0)
		{
			xpDrop = (skillExperience - preXp);
			preXp = skillExperience;
			loginXp = preXp;
		}
		else
		{
			loginXp = client.getSkillExperience(DefineSkill.defineSkill(BoredPanel.text.getText()));
		}
		skillValue = BoredPanel.skillTask;

		System.out.println("prexp "+preXp+"\nloginxp :"+loginXp);


		if(preXp >=loginXp ||preXp == -1)
		{
			if(skillValue != null && xpDrop == SkillMap.skillMap.get(skillValue))
			{
				counter++;

				//no dynamic string implementation so i did this
				toGo = BoredPanel.randomNumber-counter;
				BoredPanel.explain.setText(preTxt+"\n(you only have "+toGo+" to go!)");
			}
			else if(skillValue != null && SkillMap.skillMap.get(skillValue) != client.getSkillExperience(DefineSkill.defineSkill(BoredPanel.text.getText())))
			{
				if(skillValue.equalsIgnoreCase("Wintertodt"))
				{
					int winterTodtXp = 5000;
					for(int i = 0; i < 50; i++)
					{
						winterTodtXp += 100;
						if(xpDrop == winterTodtXp)
						{
							counter++;

							toGo = BoredPanel.randomNumber-counter;
							BoredPanel.explain.setText(preTxt+"\n(you only have "+toGo+" to go!)");
							break;
						}
					}
				}
				checkHalfXp();
			}
		}

		if(toGo == 0)
		{
			panel.completed();
		}
	}




	@Subscribe
	public void onGameStateChanged(GameStateChanged event) throws Exception
	{
		switch (event.getGameState())
		{
			case LOGGED_IN:
				loginXp = client.getSkillExperience(DefineSkill.defineSkill(BoredPanel.text.getText()));
				startUp();

			case HOPPING:
			case LOGGING_IN:
			case LOGIN_SCREEN:
				loginXp = 0;
				break;
		}
	}



	private void checkHalfXp()
	{
		if(skillValue.equalsIgnoreCase("Copper/Tin ore") || skillValue.equalsIgnoreCase("Trout/Salmon")) {
			System.out.println("trout and salmon");
			counter++;

			toGo = BoredPanel.randomNumber-counter;
			BoredPanel.explain.setText(preTxt+"\n(you only have "+toGo+" to go!)");
		}
		//no duplicate values in hashmaps allowed
		if( skillValue.equalsIgnoreCase("Oak logs") || skillValue.equalsIgnoreCase("Willow logs") || skillValue.equalsIgnoreCase("Fruit stalls") || skillValue.equalsIgnoreCase("Air Battlestaves") ||  skillValue.equalsIgnoreCase("Super Restore") || skillValue.equalsIgnoreCase("Prayer Potions") || skillValue.equalsIgnoreCase("Magic long bows") || skillValue.equalsIgnoreCase("Gold bars (at the blast furnace)"))
		{
			counter++;

			toGo = BoredPanel.randomNumber+1-counter;
			BoredPanel.explain.setText(preTxt+"\n(you only have "+toGo+" to go!)");
		}

	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
		overlayManager.remove(overlay);
		keyManager.unregisterKeyListener(setGenerateListener);
	}

	@Provides
	BoredConfig provideConfig (ConfigManager configManager)
	{
		return configManager.getConfig(BoredConfig.class);
	}
}
