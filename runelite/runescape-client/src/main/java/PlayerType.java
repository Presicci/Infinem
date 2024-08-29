import net.runelite.mapping.*;

@ObfuscatedName("jx")
@Implements("PlayerType")
public enum PlayerType implements MouseWheel {
	@ObfuscatedName("n")
	@ObfuscatedSignature(
		descriptor = "Ljx;"
	)
	@Export("PlayerType_normal")
	PlayerType_normal(0, -1, true, false, true),
	@ObfuscatedName("c")
	@ObfuscatedSignature(
		descriptor = "Ljx;"
	)
	@Export("PlayerType_playerModerator")
	PlayerType_playerModerator(1, 0, true, true, true),
	@ObfuscatedName("m")
	@ObfuscatedSignature(
		descriptor = "Ljx;"
	)
	@Export("PlayerType_jagexModerator")
	PlayerType_jagexModerator(2, 1, true, true, false),
	@ObfuscatedName("k")
	@ObfuscatedSignature(
		descriptor = "Ljx;"
	)
	@Export("PlayerType_ironman")
	PlayerType_ironman(3, 2, false, false, true),
	@ObfuscatedName("o")
	@ObfuscatedSignature(
		descriptor = "Ljx;"
	)
	@Export("PlayerType_ultimateIronman")
	PlayerType_ultimateIronman(4, 3, false, false, true),
	@ObfuscatedName("g")
	@ObfuscatedSignature(
		descriptor = "Ljx;"
	)
	@Export("PlayerType_hardcoreIronman")
	PlayerType_hardcoreIronman(5, 10, false, false, true),
	@ObfuscatedName("z")
	@ObfuscatedSignature(
		descriptor = "Ljx;"
	)
	field3668(6, 41, false, false, true),

	HARDCORE_GROUP_IRONMAN(7, 42, false, false, true),

	OWNER(100, 41, false, false, true),

	SUPPORT(9, 42, false, false, true),

	YOUTUBER(10, 43, false, false, true),

	BRONZE(11, 44, false, false, true),

	IRON(12, 45, false, false, true),

	GOLD(13, 46, false, false, true),

	MITHRIL(14, 47, false, false, true),

	ADAMANT(15, 48, false, false, true),

	RUNE(16, 49, false, false, true),

	DRAGON(17, 50, false, false, true),

	DEVELOPER(99, 1, false, false, true);



		@ObfuscatedName("a")
	@ObfuscatedGetter(
		intValue = -846617789
	)
	@Export("id")
	final int id;
	@ObfuscatedName("u")
	@ObfuscatedGetter(
		intValue = -559637127
	)
	@Export("modIcon")
	public final int modIcon;
	@ObfuscatedName("e")
	@Export("isPrivileged")
	public final boolean isPrivileged;
	@ObfuscatedName("l")
	@Export("isUser")
	public final boolean isUser;

	PlayerType(int var3, int var4, boolean var5, boolean var6, boolean var7) {
		this.id = var3;
		this.modIcon = var4;
		this.isPrivileged = var6;
		this.isUser = var7;
	}

	@ObfuscatedName("c")
	@ObfuscatedSignature(
		descriptor = "(I)I",
		garbageValue = "-1665712539"
	)
	@Export("rsOrdinal")
	public int rsOrdinal() {
		return this.id;
	}

	@ObfuscatedName("n")
	public static final void method5099(long var0) {
		if (var0 > 0L) {
			if (0L == var0 % 10L) {
				WorldMapRegion.method3602(var0 - 1L);
				WorldMapRegion.method3602(1L);
			} else {
				WorldMapRegion.method3602(var0);
			}

		}
	}
}
