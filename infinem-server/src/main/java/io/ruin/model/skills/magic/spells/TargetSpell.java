package io.ruin.model.skills.magic.spells;

import io.ruin.api.utils.Random;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.activities.wilderness.MageArena;
import io.ruin.model.combat.npc.MaxHitDummy;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.CombatUtils;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.inter.handlers.TabCombat;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.skills.magic.spells.arceuus.Resurrection;
import io.ruin.model.skills.magic.spells.modern.*;
import io.ruin.model.skills.magic.spells.modern.elementaltype.EarthSpell;
import io.ruin.model.skills.magic.spells.modern.elementaltype.FireSpell;
import io.ruin.model.skills.magic.spells.modern.elementaltype.WaterSpell;
import io.ruin.model.skills.magic.spells.modern.elementaltype.WindSpell;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class TargetSpell extends Spell {

    public static final TargetSpell[] AUTOCASTS = new TargetSpell[59];

    protected int lvlReq;

    public double baseXp;

    private int maxDamage;

    protected int animationId;

    protected int[] castGfx;
    protected int[] castSound;

    protected int[] hitGfx;

    protected int hitSoundId = -1;

    private boolean multiTarget;

    protected Projectile projectile;

    public int getLvlReq() {
        return lvlReq;
    }

    public double getBaseXp() {
        return baseXp;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getAnimationId() {
        return animationId;
    }

    public int[] getCastGfx() {
        return castGfx;
    }

    public int[] getCastSound() {
        return castSound;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Item[] getRuneItems() {
        return runeItems;
    }

    protected Item[] runeItems;

    protected BiPredicate<Entity, Entity> castCheck;

    public void setLvlReq(int lvlReq) {
        this.lvlReq = lvlReq;
    }

    public void setBaseXp(double baseXp) {
        this.baseXp = baseXp;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public void setCastGfx(int id, int height, int delay) {
        this.castGfx = new int[]{id, height, delay};
    }

    public void setCastSound(int id, int type, int delay) {
        this.castSound = new int[]{id, type, delay};
    }

    public void setHitGfx(int id, int height) {
        this.hitGfx = new int[]{id, height};
    }

    public void setHitSound(int id) {
        this.hitSoundId = id;
    }

    public void setMultiTarget() {
        this.multiTarget = true;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public void setRunes(Item... runeItems) {
        this.runeItems = runeItems;
    }

    public void setCastCheck(BiPredicate<Entity, Entity> castCheck) {
        this.castCheck = castCheck;
    }

    public void setAutoCast(int index) {
        AUTOCASTS[index] = this;
    }

    public TargetSpell() {
        entityAction = (p, e) -> p.getCombat().queueSpell(this, e);
    }

    public boolean cast(Entity entity, Entity target) {
        return cast(entity, target, -1, maxDamage, true);
    }

    private boolean cast(Entity entity, Entity target, int projectileDuration, int maxDamage, boolean primaryCast) {
        double elementalBoost = 0;
        if (primaryCast) {
            boolean saveRunes = entity.player != null && entity.player.getRelicManager().hasRelicEnalbed(Relic.ARCHMAGE) && Random.rollDie(100, 75);
            RuneRemoval r = null;
            if(entity.player != null) {
                if(!entity.player.getStats().check(StatType.Magic, lvlReq, "cast this spell"))
                    return false;
                if(DuelRule.NO_MAGIC.isToggled(entity.player)) {
                    entity.player.sendMessage("Magic has been disabled for this duel!");
                    return false;
                }
                if ((this instanceof SaradominStrike || this instanceof FlamesOfZamorak || this instanceof ClawsOfGuthix)
                        && MageArena.CONFIG.get(entity.player) < 8) {
                    entity.player.sendMessage("You need to complete the Mage Arena miniquest to cast this spell.");
                    return false;
                }
                SpellSack sack = getSpellSack();
                if ((sack == null || !sack.canCast(entity.player) || (r = RuneRemoval.get(entity.player, sack.getSack())) == null)
                        && (runeItems != null && (r = RuneRemoval.get(entity.player, runeItems)) == null)) {
                    entity.player.sendMessage("You don't have enough runes to cast this spell.");
                    TabCombat.forceResetAutocast(entity.player);
                    return false;
                }
            }
            if(castCheck != null && !castCheck.test(entity, target))
                return false;
            entity.animate(animationId);
            if(castGfx != null)
                entity.graphics(castGfx[0], castGfx[1], castGfx[2]);
            if(castSound != null)
                entity.publicSound(castSound[0], castSound[1], castSound[2]);
            if(projectile != null) //tb should be the only spell this is true for
                projectileDuration = projectile.send(entity, target);
            if (entity.isPlayer() && entity.player.getEquipment().hasId(Items.CHAOS_GAUNTLETS)
                    &&this instanceof WindBolt || this instanceof WaterBolt || this instanceof EarthBolt || this instanceof FireBolt) {
                maxDamage += 3;
            }
            if (this instanceof MagicDart && entity.isPlayer()) {
                int magicLevel = entity.player.getStats().get(StatType.Magic).currentLevel;;
                if (entity.player.getEquipment().hasId(21255) && target.npc != null && Slayer.isTask(entity.player, target.npc)) {
                    maxDamage += (magicLevel / 6) + 3;
                } else {
                    maxDamage += (magicLevel / 10);
                }
            }
            double percentageBonus = entity.getCombat().getBonus(EquipmentStats.MAGIC_DAMAGE);
            if(percentageBonus > 0) {
                maxDamage = (int) (maxDamage * (1D + (percentageBonus * 0.01)));
            }
            if (target.isNpc()) {
                npc_combat.Info combatInfo = target.npc.getDef().combatInfo;
                if (combatInfo != null && combatInfo.elemental_weakness != null) {
                    if (this instanceof FireSpell && combatInfo.elemental_weakness.equalsIgnoreCase("fire")
                            || this instanceof EarthSpell && combatInfo.elemental_weakness.equalsIgnoreCase("earth")
                            || this instanceof WaterSpell && combatInfo.elemental_weakness.equalsIgnoreCase("water")
                            || this instanceof WindSpell && combatInfo.elemental_weakness.equalsIgnoreCase("air")) {
                        elementalBoost = combatInfo.elemental_weakness_percent / 100D;
                        maxDamage += (int) (maxDamage * elementalBoost);
                    }
                }
            }
            if (r != null && !saveRunes)
                r.remove();
        }
        Hit hit = new Hit(entity, AttackStyle.MAGIC, AttackType.ACCURATE)
                .randDamage(maxDamage)
                .clientDelay(projectileDuration, 16)
                .setAttackSpell(this);
        if (primaryCast) hit.boostAttack(elementalBoost);
        if (this instanceof RootSpell && target.isNpc() && target.npc.getCombat().getInfo().easier_root)
            hit.ignoreDefence();
        if (!primaryCast) hit.keepCharges();
        hit.postDamage(t -> {
            if(hit.isBlocked()) {
                t.graphics(85, 124, 0);
                t.publicSound(227, 1, 0);
                hit.hide();
            } else {
                if(hitGfx != null)
                    t.graphics(hitGfx[0], hitGfx[1], 0);
                if(hitSoundId != -1)
                    t.publicSound(hitSoundId, 1, 0);
            }
        });
        beforeHit(hit, target);
        int damage = target.hit(hit);
        if(baseXp > 0 && entity.isPlayer())
            if (target.isPlayer() || (target.isNpc() && !Arrays.stream(MaxHitDummy.dummyIds).anyMatch(i -> i == target.npc.getId())))
                CombatUtils.addMagicXp(entity.player, baseXp, damage, target.isNpc());
        afterHit(hit, target);

        if (primaryCast && multiTarget) {
            aoeAttack(entity, target, maxDamage, projectileDuration, 9);
        } else if (primaryCast && damage > 0 && entity.isPlayer() && entity.player.getRelicManager().hasRelicEnalbed(Relic.ARCHMAGE)) {
            aoeAttack(entity, target, damage / 2, projectileDuration, 5);
        }
        return true;
    }

    private void aoeAttack(Entity entity, Entity target, int maxDamage, int delay, int targetCap) {
        if (!target.inMulti()) return;
        int entityIndex = entity.getClientIndex();
        int targetIndex = target.getClientIndex();
        int targetCount = 0;
        for(Player player : target.localPlayers()) {
            int playerIndex = player.getClientIndex();
            if(playerIndex == entityIndex || playerIndex == targetIndex)
                continue;
            if(!player.getPosition().isWithinDistance(target.getPosition(), 1))
                continue;
            if(entity.player != null) {
                if(!entity.player.getCombat().canAttack(player, false))
                    continue;
            } else {
                if(!entity.npc.getCombat().canAttack(player))
                    continue;
            }
            cast(entity, player, delay, maxDamage, false);
            if(++targetCount >= targetCap)
                break;
        }
        for(NPC npc : target.localNpcs()) {
            int npcIndex = npc.getClientIndex();
            if(npcIndex == entityIndex || npcIndex == targetIndex)
                continue;
            if (npc instanceof Resurrection.Thrall)
                continue;
            boolean archmage = entity.isPlayer() && entity.player.getRelicManager().hasRelicEnalbed(Relic.ARCHMAGE);
            if (targetCap == 5 && !npc.getPosition().isWithinDistance(target.getPosition(), 2)) {
                continue;
            } else if (targetCap != 5 && !npc.getPosition().isWithinDistance(target.getPosition(), archmage ? 3 : 1)) {
                continue;
            }
            if(npc.getDef().ignoreMultiCheck)
                continue;
            if(entity.player != null) {
                if(!entity.player.getCombat().canAttack(npc, false))
                    continue;
            } else {
                if(!entity.npc.getCombat().canAttack(npc))
                    continue;
            }
            cast(entity, npc, delay, maxDamage, false);
            if(++targetCount >= targetCap)
                break;
        }
    }

    protected void beforeHit(Hit hit, Entity target) {
        //override if needed lol
    }

    protected void afterHit(Hit hit, Entity target) {
        //override if needed lol
    }

    /**
     * Misc
     */

    protected static boolean allowHold(Entity entity, Entity target) {
        if(target.hasFreezeImmunity()) {
            if(entity.player != null) {
                if(target.isFrozen())
                    entity.player.sendMessage("Your target is already held by a magical force.");
                else
                    entity.player.sendMessage("Your target is currently immune to that spell.");
            }
            return false;
        }
        return true;
    }

    protected static void hold(Hit hit, Entity target, int seconds, boolean ice) {
        if(hit.isBlocked() || target.hasFreezeImmunity())
            return;
        target.freeze(seconds, hit.attacker);
        if(ice && target.player != null) {
            target.player.sendMessage("You have been frozen.");
            target.player.getPacketSender().sendWidget(Widget.BARRAGE, seconds);
        }
    }

}