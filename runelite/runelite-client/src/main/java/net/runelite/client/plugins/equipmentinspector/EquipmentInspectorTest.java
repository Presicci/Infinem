package net.runelite.client.plugins.equipmentinspector;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class EquipmentInspectorTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(EquipmentInspectorPlugin.class);
		RuneLite.main(args);
	}
}