package net.runelite.client.plugins.calculator;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CalculatorPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(CalculatorPlugin.class);
		RuneLite.main(args);
	}
}