import net.runelite.mapping.*;

@ObfuscatedName("kl")
@Implements("NetFileRequest")
public class NetFileRequest extends DualNode {
	@ObfuscatedName("n")
	@ObfuscatedSignature(
		descriptor = "Lkx;"
	)
	@Export("archive")
	Archive archive;
	@ObfuscatedName("c")
	@ObfuscatedGetter(
		intValue = 2088693695
	)
	@Export("crc")
	int crc;
	@ObfuscatedName("m")
	@Export("padding")
	byte padding;

	NetFileRequest() {
	}
}
