
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

import net.runelite.client.config.*;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@ConfigGroup("Skills Generator")
public interface BoredConfig extends Config {

	/*@ConfigSection(
			name = "Rewards",
			description = "the current rewards you can get",
			position = 3,
			closedByDefault = true,

	)*/
	String Rewards = "Rewards";

	@ConfigItem
			(
			keyName = "hotkey",
			name = "Generate Hotkey",
			description = "Pressing this combination will generate a new task.",
			position = 0
	)
	default Keybind generateHotKey() {
		return new Keybind(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
	}

	@ConfigItem
			(
					position = 1,
					keyName = "Cengineer sound.",
					name = "C engineer completed.",
					description = "Turn the c engineer sound on or off."

			)
	default boolean CengineerC() { return false; }

	@ConfigItem
			(
					position = 2,
					keyName = "Panel",
					name = "Panel",
					description = "Enable/Disable the panel."

			)
	default boolean panel() { return true; }

	@ConfigItem
			(
					position = 3,
					keyName = "Overlay",
					name = "Overlay",
					description = "Enable/Disable the overlay."

			)
	default boolean overlay() { return true; }

	/*@ConfigItem(
			position = 1,
			keyName = "rewards",
			name = "Overheadtext",
			description = "Enable/Disable the overhead title.",
			section = Rewards
	)
	default boolean overHeadTextCheckbox() {return false;}
	@ConfigItem(
			position = 2,
			keyName = "rewards",
			name = "Reward overview",
			description = "a rewards explanation.",
			section = Rewards
	)
	default String rewardExplanation() { return
			"The current rewards are the overhead Title's\nNo points: \"Noober man\"\n" +
			"10 points: \"Cool man\"\n"+
			"20 points: \"Super cool man\"\n"+
			"30 points: \"Superior man\"\n"+
			"40 points: \"Mega Superior cool man\"\n"+
			"50 points: \"Experienced task completer man\"\n"+
			"100 points: \"No life man\"\n"+
			"2147483647 points: \"Max cash man\"\n";}
			Just ignore this piece of code right here. I might want to implements some kind of rewards and this was an idea but not for now.
			*/
}

