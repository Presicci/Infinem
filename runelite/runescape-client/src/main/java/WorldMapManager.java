import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.runelite.mapping.*;

@ObfuscatedName("gd")
@Implements("WorldMapManager")
public final class WorldMapManager {
	@ObfuscatedName("n")
	@Export("loaded")
	boolean loaded;
	@ObfuscatedName("c")
	@Export("loadStarted")
	boolean loadStarted;
	@ObfuscatedName("m")
	@ObfuscatedSignature(
		descriptor = "Lgh;"
	)
	@Export("mapAreaData")
	WorldMapAreaData mapAreaData;
	@ObfuscatedName("k")
	@ObfuscatedSignature(
		descriptor = "Loc;"
	)
	@Export("compositeTextureSprite")
	SpritePixels compositeTextureSprite;
	@ObfuscatedName("o")
	@Export("icons")
	HashMap icons;
	@ObfuscatedName("g")
	@ObfuscatedSignature(
		descriptor = "[[Lfm;"
	)
	@Export("regions")
	WorldMapRegion[][] regions;
	@ObfuscatedName("z")
	@Export("scaleHandlers")
	HashMap scaleHandlers;
	@ObfuscatedName("a")
	@ObfuscatedSignature(
		descriptor = "[Loi;"
	)
	@Export("mapSceneSprites")
	IndexedSprite[] mapSceneSprites;
	@ObfuscatedName("u")
	@ObfuscatedSignature(
		descriptor = "Lkk;"
	)
	@Export("geographyArchive")
	final AbstractArchive geographyArchive;
	@ObfuscatedName("e")
	@ObfuscatedSignature(
		descriptor = "Lkk;"
	)
	@Export("groundArchive")
	final AbstractArchive groundArchive;
	@ObfuscatedName("l")
	@Export("fonts")
	final HashMap fonts;
	@ObfuscatedName("y")
	@ObfuscatedGetter(
		intValue = -1795323681
	)
	@Export("tileX")
	int tileX;
	@ObfuscatedName("v")
	@ObfuscatedGetter(
		intValue = 635578295
	)
	@Export("tileY")
	int tileY;
	@ObfuscatedName("f")
	@ObfuscatedGetter(
		intValue = 876683677
	)
	@Export("tileWidth")
	int tileWidth;
	@ObfuscatedName("s")
	@ObfuscatedGetter(
		intValue = -2031973391
	)
	@Export("tileHeight")
	int tileHeight;
	@ObfuscatedName("h")
	@ObfuscatedGetter(
		intValue = 2115232329
	)
	@Export("pixelsPerTile")
	public int pixelsPerTile;

	@ObfuscatedSignature(
		descriptor = "([Loi;Ljava/util/HashMap;Lkk;Lkk;)V"
	)
	public WorldMapManager(IndexedSprite[] var1, HashMap var2, AbstractArchive var3, AbstractArchive var4) {
		this.loaded = false;
		this.loadStarted = false;
		this.scaleHandlers = new HashMap();
		this.pixelsPerTile = 0;
		this.mapSceneSprites = var1;
		this.fonts = var2;
		this.geographyArchive = var3;
		this.groundArchive = var4;
	}

	@ObfuscatedName("n")
	@ObfuscatedSignature(
		descriptor = "(Lkk;Ljava/lang/String;ZI)V",
		garbageValue = "297189005"
	)
	@Export("load")
	public void load(AbstractArchive var1, String var2, boolean var3) {
		if (!this.loadStarted) {
			this.loaded = false;
			this.loadStarted = true;
			System.nanoTime();
			int var4 = var1.getGroupId(WorldMapCacheName.field2199.name);
			int var5 = var1.getFileId(var4, var2);
			Buffer var6 = new Buffer(var1.takeFileByNames(WorldMapCacheName.field2199.name, var2));
			Buffer var7 = new Buffer(var1.takeFileByNames(WorldMapCacheName.field2203.name, var2));
			System.nanoTime();
			System.nanoTime();
			this.mapAreaData = new WorldMapAreaData();

			try {
				this.mapAreaData.init(var6, var7, var5, var3);
			} catch (IllegalStateException var19) {
				return;
			}

			this.mapAreaData.getOriginX();
			this.mapAreaData.getOriginPlane();
			this.mapAreaData.getOriginY();
			this.tileX = this.mapAreaData.getRegionLowX() * 64;
			this.tileY = this.mapAreaData.getRegionLowY() * 64;
			this.tileWidth = (this.mapAreaData.getRegionHighX() - this.mapAreaData.getRegionLowX() + 1) * 64;
			this.tileHeight = (this.mapAreaData.getRegionHighY() - this.mapAreaData.getRegionLowY() + 1) * 64;
			int var16 = this.mapAreaData.getRegionHighX() - this.mapAreaData.getRegionLowX() + 1;
			int var9 = this.mapAreaData.getRegionHighY() - this.mapAreaData.getRegionLowY() + 1;
			System.nanoTime();
			System.nanoTime();
			LoginScreenAnimation.method2173();
			this.regions = new WorldMapRegion[var16][var9];
			Iterator var10 = this.mapAreaData.worldMapData0Set.iterator();

			while (var10.hasNext()) {
				WorldMapData_0 var11 = (WorldMapData_0)var10.next();
				int var12 = var11.regionX;
				int var13 = var11.regionY;
				int var14 = var12 - this.mapAreaData.getRegionLowX();
				int var15 = var13 - this.mapAreaData.getRegionLowY();
				this.regions[var14][var15] = new WorldMapRegion(var12, var13, this.mapAreaData.getBackGroundColor(), this.fonts);
				this.regions[var14][var15].initWorldMapData0(var11, this.mapAreaData.iconList);
			}

			for (int var17 = 0; var17 < var16; ++var17) {
				for (int var18 = 0; var18 < var9; ++var18) {
					if (this.regions[var17][var18] == null) {
						this.regions[var17][var18] = new WorldMapRegion(this.mapAreaData.getRegionLowX() + var17, this.mapAreaData.getRegionLowY() + var18, this.mapAreaData.getBackGroundColor(), this.fonts);
						this.regions[var17][var18].initWorldMapData1(this.mapAreaData.worldMapData1Set, this.mapAreaData.iconList);
					}
				}
			}

			System.nanoTime();
			System.nanoTime();
			if (var1.isValidFileName(WorldMapCacheName.field2201.name, var2)) {
				byte[] var20 = var1.takeFileByNames(WorldMapCacheName.field2201.name, var2);
				this.compositeTextureSprite = class17.convertJpgToSprite(var20);
			}

			System.nanoTime();
			var1.clearGroups();
			var1.clearFiles();
			this.loaded = true;
		}
	}

	@ObfuscatedName("c")
	@ObfuscatedSignature(
		descriptor = "(B)V",
		garbageValue = "-34"
	)
	@Export("clearIcons")
	public final void clearIcons() {
		this.icons = null;
	}

	@ObfuscatedName("m")
	@ObfuscatedSignature(
		descriptor = "(IIIIIIIIB)V",
		garbageValue = "15"
	)
	@Export("drawTiles")
	public final void drawTiles(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
		int[] var9 = Rasterizer2D.Rasterizer2D_pixels;
		int var10 = Rasterizer2D.Rasterizer2D_width;
		int var11 = Rasterizer2D.Rasterizer2D_height;
		int[] var12 = new int[4];
		Rasterizer2D.Rasterizer2D_getClipArray(var12);
		WorldMapRectangle var13 = this.createWorldMapRectangle(var1, var2, var3, var4);
		float var14 = this.getPixelsPerTile(var7 - var5, var3 - var1);
		int var15 = (int)Math.ceil((double)var14);
		this.pixelsPerTile = var15;
		if (!this.scaleHandlers.containsKey(var15)) {
			WorldMapScaleHandler var16 = new WorldMapScaleHandler(var15);
			var16.init();
			this.scaleHandlers.put(var15, var16);
		}

		int var23 = var13.width + var13.x - 1;
		int var17 = var13.y + var13.height - 1;

		int var18;
		int var19;
		for (var18 = var13.x; var18 <= var23; ++var18) {
			for (var19 = var13.y; var19 <= var17; ++var19) {
				this.regions[var18][var19].drawTile(var15, (WorldMapScaleHandler)this.scaleHandlers.get(var15), this.mapSceneSprites, this.geographyArchive, this.groundArchive);
			}
		}

		Rasterizer2D.Rasterizer2D_replace(var9, var10, var11);
		Rasterizer2D.Rasterizer2D_setClipArray(var12);
		var18 = (int)(64.0F * var14);
		var19 = this.tileX + var1;
		int var20 = var2 + this.tileY;

		for (int var21 = var13.x; var21 < var13.width + var13.x; ++var21) {
			for (int var22 = var13.y; var22 < var13.y + var13.height; ++var22) {
				this.regions[var21][var22].method3525(var5 + (this.regions[var21][var22].regionX * 64 - var19) * var18 / 64, var8 - (this.regions[var21][var22].regionY * 64 - var20 + 64) * var18 / 64, var18);
			}
		}

	}

	@ObfuscatedName("k")
	@ObfuscatedSignature(
		descriptor = "(IIIIIIIILjava/util/HashSet;Ljava/util/HashSet;IIZI)V",
		garbageValue = "-678341519"
	)
	@Export("drawElements")
	public final void drawElements(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, HashSet var9, HashSet var10, int var11, int var12, boolean var13) {
		WorldMapRectangle var14 = this.createWorldMapRectangle(var1, var2, var3, var4);
		float var15 = this.getPixelsPerTile(var7 - var5, var3 - var1);
		int var16 = (int)(var15 * 64.0F);
		int var17 = this.tileX + var1;
		int var18 = var2 + this.tileY;

		int var19;
		int var20;
		for (var19 = var14.x; var19 < var14.width + var14.x; ++var19) {
			for (var20 = var14.y; var20 < var14.height + var14.y; ++var20) {
				if (var13) {
					this.regions[var19][var20].initWorldMapIcon1s();
				}

				this.regions[var19][var20].method3526(var5 + (this.regions[var19][var20].regionX * 64 - var17) * var16 / 64, var8 - (this.regions[var19][var20].regionY * 64 - var18 + 64) * var16 / 64, var16, var9);
			}
		}

		if (var10 != null && var11 > 0) {
			for (var19 = var14.x; var19 < var14.x + var14.width; ++var19) {
				for (var20 = var14.y; var20 < var14.y + var14.height; ++var20) {
					this.regions[var19][var20].flashElements(var10, var11, var12);
				}
			}
		}

	}

	@ObfuscatedName("o")
	@ObfuscatedSignature(
		descriptor = "(IIIILjava/util/HashSet;III)V",
		garbageValue = "1824309086"
	)
	@Export("drawOverview")
	public void drawOverview(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7) {
		if (this.compositeTextureSprite != null) {
			this.compositeTextureSprite.drawScaledAt(var1, var2, var3, var4);
			if (var6 > 0 && var6 % var7 < var7 / 2) {
				if (this.icons == null) {
					this.buildIcons0();
				}

				Iterator var8 = var5.iterator();

				while (true) {
					List var10;
					do {
						if (!var8.hasNext()) {
							return;
						}

						int var9 = (Integer)var8.next();
						var10 = (List)this.icons.get(var9);
					} while(var10 == null);

					Iterator var11 = var10.iterator();

					while (var11.hasNext()) {
						AbstractWorldMapIcon var12 = (AbstractWorldMapIcon)var11.next();
						int var13 = var3 * (var12.coord2.x - this.tileX) / this.tileWidth;
						int var14 = var4 - (var12.coord2.y - this.tileY) * var4 / this.tileHeight;
						Rasterizer2D.Rasterizer2D_drawCircleAlpha(var13 + var1, var14 + var2, 2, 16776960, 256);
					}
				}
			}
		}
	}

	@ObfuscatedName("g")
	@ObfuscatedSignature(
		descriptor = "(IIIIIIIIIII)Ljava/util/List;",
		garbageValue = "-355566181"
	)
	public List method3645(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
		LinkedList var11 = new LinkedList();
		if (!this.loaded) {
			return var11;
		} else {
			WorldMapRectangle var12 = this.createWorldMapRectangle(var1, var2, var3, var4);
			float var13 = this.getPixelsPerTile(var7, var3 - var1);
			int var14 = (int)(var13 * 64.0F);
			int var15 = this.tileX + var1;
			int var16 = var2 + this.tileY;

			for (int var17 = var12.x; var17 < var12.width + var12.x; ++var17) {
				for (int var18 = var12.y; var18 < var12.y + var12.height; ++var18) {
					List var19 = this.regions[var17][var18].method3547(var5 + (this.regions[var17][var18].regionX * 64 - var15) * var14 / 64, var8 + var6 - (this.regions[var17][var18].regionY * 64 - var16 + 64) * var14 / 64, var14, var9, var10);
					if (!var19.isEmpty()) {
						var11.addAll(var19);
					}
				}
			}

			return var11;
		}
	}

	@ObfuscatedName("z")
	@ObfuscatedSignature(
		descriptor = "(IIIIB)Lgf;",
		garbageValue = "74"
	)
	@Export("createWorldMapRectangle")
	WorldMapRectangle createWorldMapRectangle(int var1, int var2, int var3, int var4) {
		WorldMapRectangle var5 = new WorldMapRectangle(this);
		int var6 = this.tileX + var1;
		int var7 = var2 + this.tileY;
		int var8 = var3 + this.tileX;
		int var9 = var4 + this.tileY;
		int var10 = var6 / 64;
		int var11 = var7 / 64;
		int var12 = var8 / 64;
		int var13 = var9 / 64;
		var5.width = var12 - var10 + 1;
		var5.height = var13 - var11 + 1;
		var5.x = var10 - this.mapAreaData.getRegionLowX();
		var5.y = var11 - this.mapAreaData.getRegionLowY();
		if (var5.x < 0) {
			var5.width += var5.x;
			var5.x = 0;
		}

		if (var5.x > this.regions.length - var5.width) {
			var5.width = this.regions.length - var5.x;
		}

		if (var5.y < 0) {
			var5.height += var5.y;
			var5.y = 0;
		}

		if (var5.y > this.regions[0].length - var5.height) {
			var5.height = this.regions[0].length - var5.y;
		}

		var5.width = Math.min(var5.width, this.regions.length);
		var5.height = Math.min(var5.height, this.regions[0].length);
		return var5;
	}

	@ObfuscatedName("a")
	@ObfuscatedSignature(
		descriptor = "(I)Z",
		garbageValue = "-44016321"
	)
	@Export("isLoaded")
	public boolean isLoaded() {
		return this.loaded;
	}

	@ObfuscatedName("u")
	@ObfuscatedSignature(
		descriptor = "(B)Ljava/util/HashMap;",
		garbageValue = "0"
	)
	@Export("buildIcons")
	public HashMap buildIcons() {
		this.buildIcons0();
		return this.icons;
	}

	@ObfuscatedName("e")
	@ObfuscatedSignature(
		descriptor = "(B)V",
		garbageValue = "-124"
	)
	@Export("buildIcons0")
	void buildIcons0() {
		if (this.icons == null) {
			this.icons = new HashMap();
		}

		this.icons.clear();

		for (int var1 = 0; var1 < this.regions.length; ++var1) {
			for (int var2 = 0; var2 < this.regions[var1].length; ++var2) {
				List var3 = this.regions[var1][var2].icons();
				Iterator var4 = var3.iterator();

				while (var4.hasNext()) {
					AbstractWorldMapIcon var5 = (AbstractWorldMapIcon)var4.next();
					if (var5.hasValidElement()) {
						int var6 = var5.getElement();
						if (!this.icons.containsKey(var6)) {
							LinkedList var7 = new LinkedList();
							var7.add(var5);
							this.icons.put(var6, var7);
						} else {
							List var8 = (List)this.icons.get(var6);
							var8.add(var5);
						}
					}
				}
			}
		}

	}

	@ObfuscatedName("l")
	@ObfuscatedSignature(
		descriptor = "(III)F",
		garbageValue = "382223653"
	)
	@Export("getPixelsPerTile")
	float getPixelsPerTile(int var1, int var2) {
		float var3 = (float)var1 / (float)var2;
		if (var3 > 8.0F) {
			return 8.0F;
		} else if (var3 < 1.0F) {
			return 1.0F;
		} else {
			int var4 = Math.round(var3);
			return Math.abs((float)var4 - var3) < 0.05F ? (float)var4 : var3;
		}
	}

	@ObfuscatedName("kb")
	@ObfuscatedSignature(
		descriptor = "(Ljava/lang/String;ZB)Ljava/lang/String;",
		garbageValue = "-55"
	)
	static String method3679(String var0, boolean var1) {
		String var2 = var1 ? "https://" : "http://";
		if (Client.gameBuild == 1) {
			var0 = var0 + "-wtrc";
		} else if (Client.gameBuild == 2) {
			var0 = var0 + "-wtqa";
		} else if (Client.gameBuild == 3) {
			var0 = var0 + "-wtwip";
		} else if (Client.gameBuild == 5) {
			var0 = var0 + "-wti";
		} else if (Client.gameBuild == 4) {
			var0 = "local";
		}

		String var3 = "";
		if (WorldMapIcon_0.field2104 != null) {
			var3 = "/p=" + WorldMapIcon_0.field2104;
		}

		String var4 = "runescape.com";
		return var2 + var0 + "." + var4 + "/l=" + class323.clientLanguage + "/a=" + KeyHandler.field145 + var3 + "/";
	}
}
