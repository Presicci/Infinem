package net.runelite.client.plugins.Playeroutline.test.java.com.playeroutline;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import net.runelite.client.plugins.Playeroutline.main.java.com.playeroutline.PlayerOutlinePlugin;

public class PlayerOutlinePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PlayerOutlinePlugin.class);
		RuneLite.main(args);
	}
}