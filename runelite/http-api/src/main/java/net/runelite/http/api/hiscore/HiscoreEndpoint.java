/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.http.api.hiscore;

import lombok.Getter;
import okhttp3.HttpUrl;

@Getter
public enum HiscoreEndpoint
{
	NORMAL("Normal", "https://www.infinem.net/highscores/index.php?gamemode=regular&api=1"),
	IRONMAN("Ironman", "https://www.infinem.net/highscores/index.php?gamemode=ironman&api=1"),
	HARDCORE_IRONMAN("Hardcore Ironman", "https://www.infinem.net/highscores/index.php?gamemode=hardcore&api=1"),
	ULTIMATE_IRONMAN("Ultimate Ironman", "https://www.infinem.net/highscores/index.php?gamemode=ultimate&api=1"),
	DEADMAN("Deadman", "https://services.runescape.com/m=hiscore_oldschool_deadman/index_lite.ws"),
	LEAGUE("Leagues", "https://services.runescape.com/m=hiscore_oldschool_seasonal/index_lite.ws"),
	TOURNAMENT("Tournament", "https://services.runescape.com/m=hiscore_oldschool_tournament/index_lite.ws");

	private final String name;
	private final HttpUrl hiscoreURL;

	HiscoreEndpoint(String name, String hiscoreURL)
	{
		this.name = name;
		this.hiscoreURL = HttpUrl.parse(hiscoreURL);
	}
}
