package io.ruin.model.skills.magic.spells.arceuus;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

import java.util.HashMap;
import java.util.Map;

public class Reanimate extends Spell {

    public enum MonsterType {
        BASIC(new Monster[]{Monster.GOBLIN, Monster.MONKEY, Monster.IMP, Monster.MINOTAUR, Monster.SCORPION, Monster.BEAR, Monster.UNICORN}, 16, 32, Rune.BODY.toItem(4), Rune.NATURE.toItem(2)),
        ADEPT(new Monster[]{Monster.DOG, Monster.CHAOS_DRUID, Monster.OGRE, Monster.GIANT, Monster.ELF, Monster.TROLL, Monster.HORROR}, 41, 80, Rune.BODY.toItem(4), Rune.NATURE.toItem(3), Rune.SOUL.toItem(1)),
        EXPERT(new Monster[]{Monster.KALPHITE, Monster.DAGANNOTH, Monster.BLOODVELD, Monster.TZHAAR, Monster.DEMON, Monster.HELLHOUND}, 72, 138, Rune.BLOOD.toItem(1), Rune.NATURE.toItem(3), Rune.SOUL.toItem(2)),
        MASTER(new Monster[]{Monster.AVIANSIE, Monster.ABYSSAL_CREATURE, Monster.DRAGON}, 90, 170, Rune.BLOOD.toItem(2), Rune.NATURE.toItem(4), Rune.SOUL.toItem(4)),
        ;

        public final Monster[] monsters;
        public final int levelReq, mageExp;
        public final Item[] runes;

        MonsterType(Monster[] monsters, int levelReq, int mageExp, Item... runes) {
            this.monsters = monsters;
            this.levelReq = levelReq;
            this.mageExp = mageExp;
            this.runes = runes;
        }
    }

    public enum Monster {
        GOBLIN(new int[]{13447, 13448}, 130.0, 7018),
        MONKEY(new int[]{13450, 13451}, 182.0, 7019),
        IMP(new int[]{13453, 13454}, 286.0, 7020),
        MINOTAUR(new int[]{13453, 13454}, 364.0, 7021),
        SCORPION(new int[]{13459, 13460}, 454.0, 7022),
        BEAR(new int[]{13462, 13463}, 480.0, 7023),
        UNICORN(new int[]{13465, 13466}, 494.0, 7024),
        DOG(new int[]{13468, 13469}, 520.0, 7025),
        CHAOS_DRUID(new int[]{13471, 13472}, 584.0, 7026),
        GIANT(new int[]{13474, 13475}, 650.0, 7027),
        OGRE(new int[]{13477, 13478}, 716.0, 7028),
        ELF(new int[]{13480, 13481}, 754.0, 7029),
        TROLL(new int[]{13483, 13484}, 780.0, 7030),
        HORROR(new int[]{13486, 13487}, 832.0, 7031),
        KALPHITE(new int[]{13489, 13490}, 884.0, 7032),
        DAGANNOTH(new int[]{13492, 13493}, 936.0, 7033),
        BLOODVELD(new int[]{13495, 13496}, 1040.0, 7034),
        TZHAAR(new int[]{13498, 13499}, 1104.0, 7035),
        DEMON(new int[]{13501, 13502}, 1170.0, 7036),
        HELLHOUND(new int[]{26996, 26997}, 1200.0, 11463),
        AVIANSIE(new int[]{13504, 13505}, 1234.0, 7037),
        ABYSSAL_CREATURE(new int[]{13507, 13508}, 1300.0, 7038),
        DRAGON(new int[]{13510, 13511}, 1560.0, 7039);

        public final int[] headIds;
        public final int reanimatedNPCId;
        public final double prayerExp;

        Monster(int[] headIds, double prayerExp, int reanimatedNPCId) {
            this.headIds = headIds;
            this.prayerExp = prayerExp;
            this.reanimatedNPCId = reanimatedNPCId;
        }
    }

    private static Bounds SPAWN_AREA = new Bounds(1703, 3874, 1745, 3904, 0);
    private static final Projectile projectile = new Projectile(1289, 40, 10, 5, 75, 15, 10, 64);

    private static void reanimate(Player player, Monster monster, Item item) {
        Position oldPosition = new Position(player.getAbsX(), player.getAbsY(), 0);
        player.startEvent(event -> {
            player.lock();
            player.getRouteFinder().routeSelf();
            event.delay(1);
            player.face(oldPosition.getX(), oldPosition.getY());
            event.delay(1);
            player.animate(7198);
            player.graphics(1288);
            event.delay(1);
            player.getInventory().remove(item.getId(), 1);
            GroundItem groundItem = new GroundItem(item.getId(), 1).owner(player).position(oldPosition).spawn();
            projectile.send(player.getAbsX(), player.getAbsY(), oldPosition.getX(), oldPosition.getY());
            event.delay(2);
            World.sendGraphics(1290, 0, 0, oldPosition);
            event.delay(1);
            groundItem.remove();
            event.delay(4);
            NPC animated = new NPC(monster.reanimatedNPCId).spawn(oldPosition.getX(), oldPosition.getY(), player.getHeight()).targetPlayer(player, true);
            animated.deathEndListener = (DeathListener.Simple) () -> {
                animated.remove();
                player.getStats().addXp(StatType.Prayer, monster.prayerExp, true);
            };
            animated.attackTargetPlayer(() -> !player.getPosition().isWithinDistance(animated.getPosition()));
            player.unlock();
        });
    }

    public Reanimate(MonsterType monsterType) {
        registerItem(monsterType.levelReq, monsterType.mageExp, true, monsterType.runes, (player, item) -> {
            Monster monster = MONSTER_MAP.get(item.getId());
            if (monster == null) {
                player.dialogue(new ItemDialogue().one(item.getId(), "This spell cannot reanimate that item."));
                return false;
            }
            if (!player.getPosition().inBounds(SPAWN_AREA)) {
                player.dialogue(new ItemDialogue().one(monster.headIds[0], "The creature cannot be reanimated here. The power<br>of the crystals by the Dark Altar will increase the<br>potency of the spell."));
                return false;
            }
            if (player.npcTarget) {
                player.sendFilteredMessage("You need to kill the monster you already have before spawning another!");
                return false;
            }
            reanimate(player, monster, item);
            return true;
        });
    }

    private static final Map<Integer, Monster> MONSTER_MAP = new HashMap<>();

    static {
        for (Monster monster : Monster.values()) {
            for (int headId : monster.headIds) {
                MONSTER_MAP.put(headId, monster);
            }
        }
    }
}
