package net.runelite.client.plugins.code;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BoredGeneratorMain
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BoredPlugin.class);
		RuneLite.main(args);
	}
}