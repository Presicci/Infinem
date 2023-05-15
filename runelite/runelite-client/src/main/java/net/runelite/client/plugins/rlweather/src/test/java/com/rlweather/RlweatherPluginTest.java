package net.runelite.client.plugins.rlweather.src.test.java.com.rlweather;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import net.runelite.client.plugins.rlweather.src.main.java.com.rlweather.RlweatherPlugin;

public class RlweatherPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(RlweatherPlugin.class);
		RuneLite.main(args);
	}
}