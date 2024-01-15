package io.ruin.model.item.actions.impl.godbooks;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.utility.Utils;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/14/2024
 */
@Getter
public enum GodBook {
    SARADOMIN(1335, GodBookTranscript.SARADOMIN, 3839, 3840, 3827, 3828, 3829, 3830),
    GUTHIX(1337, GodBookTranscript.GUTHIX, 3843, 3844, 3835, 3836, 3837, 3838),
    ZAMORAK(1336, GodBookTranscript.ZAMORAK, 3841, 3842, 3831, 3832, 3833, 3834),
    BANDOS(7153, GodBookTranscript.BANDOS, 12607, 12608, 12613, 12614, 12615, 12616),
    ARMADYL(7154, GodBookTranscript.ARMADYL, 12609, 12610, 12617, 12618, 12619, 12620),
    ANCIENT(7155, GodBookTranscript.ZAROS, 12611, 12612, 12621, 12622, 12623, 12624);

    private final int animationId;
    private final GodBookTranscript transcript;
    private final int damagedBookId, completedBookId;
    private final int[] pages;
    public static final GodBook[] values = values();

    GodBook(final int animationId, final GodBookTranscript transcript, final int damagedBookId, final int completedBookId, final int... pages) {
        this.animationId = animationId;
        this.transcript = transcript;
        this.damagedBookId = damagedBookId;
        this.completedBookId = completedBookId;
        this.pages = pages;
    }

    private void addPage(Player player, Item page, Item book) {
        int pageIndex = -1;
        for (int index = 0; index < pages.length; index++) {
            if (page.getId() == pages[index]) {
                pageIndex = index;
                break;
            }
        }
        if (pageIndex == -1) return;    // Not a real page
        String key = name() + "_PAGES";
        int filledPages = player.getAttributeIntOrZero(key);
        int mask = 1 << pageIndex;
        if ((filledPages & mask) != 0) {
            player.sendMessage("Your damaged book already contains that page.");
            return;
        }
        player.getInventory().remove(page.getId(), 1);
        filledPages = player.incrementNumericAttribute(key, mask);
        player.sendMessage("You add the page to the book...");
        if (filledPages == 15) {
            player.sendMessage("The book is now complete!");
            player.sendMessage("You can now use it to bless holy symbols.");
            book.setId(completedBookId);
        }
    }

    private void check(Player player) {
        int pages = player.getAttributeIntOrZero(name() + "_PAGES");
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < 4; index++) {
            if ((pages & (1 << index)) != 0) {
                if (sb.length() > 0)
                    sb.append(", ");
                sb.append(index + 1);
            }
        }
        if (sb.length() == 0)
            player.dialogue(new ItemDialogue().one(completedBookId, "Your damaged book contains no pages."));
        else
            player.dialogue(new ItemDialogue().one(completedBookId, "Your damaged book contains the following pages:<br>" + sb));
    }

    private void preach(Player player) {
        player.dialogue(
                new OptionsDialogue("Select a relevant passage",
                        new Option("Wedding Ceremony", () -> preach(player, transcript.weddingCeremony)),
                        new Option("Last Rites", () -> preach(player, transcript.lastRites)),
                        new Option("Blessings", () -> preach(player, transcript.blessings)),
                        new Option("Preach", () -> preach(player, Random.get(transcript.preach)))
                )
        );
    }

    private void preach(Player player, String[] chat) {
        if (!player.getCombat().useSpecialEnergy(25)) {
            player.sendMessage("You need at least 25% special attack to do this.");
            return;
        }
        player.lock();
        player.startEvent(e -> {
            player.animate(animationId);
            player.forceText(chat[0]);
            e.delay(2);
            player.forceText(chat[1]);
            e.delay(2);
            player.forceText(chat[2]);
            player.unlock();
            e.delay(2);
            player.resetAnimation();
        });
    }

    private static GodBook get(Item item) {
        return Utils.findMatching(values,
                value -> value.completedBookId == item.getId() || value.damagedBookId == item.getId());
    }

    static {
        for (GodBook book : values()) {
            ItemAction.registerInventory(book.damagedBookId, "check", (player, item) -> book.check(player));
            ItemAction.registerInventory(book.completedBookId, "preach", (player, item) -> book.preach(player));
            ItemAction.registerEquipment(book.completedBookId, "read", (player, item) -> book.preach(player));
            for (int pageId : book.pages) {
                ItemItemAction.register(pageId, book.getDamagedBookId(), book::addPage);
                ItemItemAction.register(pageId, book.getCompletedBookId(), (player, primary, secondary) -> player.sendMessage("That book is already completed."));
            }
        }
    }
}
