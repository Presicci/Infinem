import net.runelite.mapping.*;

@ObfuscatedName("ed")
@Implements("Task")
public class Task {
	@ObfuscatedName("n")
	@ObfuscatedSignature(
		descriptor = "Led;"
	)
	@Export("next")
	Task next;
	@ObfuscatedName("o")
	@Export("status")
	public volatile int status;
	@ObfuscatedName("g")
	@ObfuscatedGetter(
		intValue = -1897850885
	)
	@Export("type")
	int type;
	@ObfuscatedName("z")
	@Export("intArgument")
	public int intArgument;
	@ObfuscatedName("a")
	@Export("objectArgument")
	Object objectArgument;
	@ObfuscatedName("u")
	@Export("result")
	public volatile Object result;

	Task() {
		this.status = 0;
	}
}
