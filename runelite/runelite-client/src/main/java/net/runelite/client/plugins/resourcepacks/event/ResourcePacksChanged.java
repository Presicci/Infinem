package net.runelite.client.plugins.resourcepacks.event;

import lombok.Value;
import net.runelite.client.plugins.resourcepacks.hub.ResourcePackManifest;

import java.util.List;

@Value
public class ResourcePacksChanged
{
	List<ResourcePackManifest> newManifest;
}
