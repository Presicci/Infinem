package io.ruin.model.entity.player;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.cache.ItemID;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.RelicManager;
import io.ruin.model.inter.handlers.itemskeptondeath.IKOD;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.MaxCapeVariants;
import io.ruin.model.skills.hunter.creature.impl.PitfallCreature;
import io.ruin.model.skills.magic.spells.arceuus.Resurrection;
import io.ruin.utility.Color;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.combat.npc.MaxHitDummy;
import io.ruin.model.activities.pvp.leaderboard.Leaderboard;
import io.ruin.model.activities.combat.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.wilderness.BloodyChest;
import io.ruin.model.activities.wilderness.SkullingItem;
import io.ruin.model.combat.*;
import io.ruin.model.combat.npc.Vampyre;
import io.ruin.model.combat.special.Special;
import io.ruin.model.combat.special.magic.StaffOfTheDead;
import io.ruin.model.combat.special.melee.VestasSpear;
import io.ruin.model.combat.special.ranged.DragonThrownaxe;
import io.ruin.model.combat.special.ranged.ToxicBlowpipe;
import io.ruin.model.content.bestiary.perks.impl.AccuracyPerk;
import io.ruin.model.content.bestiary.perks.impl.DamagePerk;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.inter.handlers.TabCombat;
import io.ruin.model.inter.journal.toggles.TargetOverlay;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.TransformationRing;
import io.ruin.model.item.actions.impl.chargable.*;
import io.ruin.model.item.actions.impl.combine.SlayerHelm;
import io.ruin.model.item.actions.impl.jewellery.*;
import io.ruin.model.item.actions.impl.skillcapes.DefenceSkillCape;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Direction;
import io.ruin.model.map.MapArea;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.skills.magic.spells.lunar.Vengeance;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.skills.prayer.Redemption;
import io.ruin.model.skills.prayer.Retribution;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.StatType;
import io.ruin.process.tickevent.TickEventType;
import io.ruin.utility.Misc;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiPredicate;

import static io.ruin.model.inter.Interface.COMBAT_OPTIONS;

@Slf4j
public class PlayerCombat extends Combat {

    private Player player;

    public Entity lastTarget;

    private int lastTargetTimeoutTicks;

    public void init(Player player) {
        this.player = player;
        player.hitListener = new HitListener()
                //hits to this player
                .preDefend(this::preDefend)
                .postDefend(this::postDefend)
                .preDamage(this::preDamage)
                .postDamage(this::postDamage)
                //hits from this player
                .preTargetDefend(this::preTargetDefend)
                .postTargetDefend(this::postTargetDefend)
                .preTargetDamage(this::preTargetDamage)
                .postTargetDamage(this::postTargetDamage);
    }

    public void start() { //different than init because player isn't online yet when init happens
        setLevel();
        if(highRiskSkull)
            player.getAppearance().setSkullIcon(KillingSpree.overheadId(player));
        else if(skullDelay > 0)
            player.getAppearance().setSkullIcon(KillingSpree.overheadId(player));
    }

    /**
     * Target
     */

    @Override
    public void setTarget(Entity target) {
        updateLastTarget(target);
        super.setTarget(target);
        TargetOverlay.set(player, target);
        if(target.player != null)
            Config.PLAYER_PRIORITY.set(player, target.getIndex());
    }

    private void updateLastTarget(Entity target) {
        lastTarget = target;
        lastTargetTimeoutTicks = 5;
    }

    private void checkLastTarget() {
        if(lastTargetTimeoutTicks > 0 && --lastTargetTimeoutTicks == 0)
            lastTarget = null;
    }

    /**
     * Pre-attack
     */

    public void preAttack() {
        depleteTb();
        depleteCharge();
        checkLastTarget();
        checkGraniteMaul();
        if(player.dragonfireShieldSpecial)
            if(canAttack(target, true))
                DragonfireShield.specialAttack(player, target);
            else
                player.dragonfireShieldSpecial = false;
        if(target != null) {
            if(!canAttack(target, true))
                reset();
            else {
                boolean longRanged = getAttackType() == AttackType.LONG_RANGED && weaponType.maxDistance < 10;
                int attackRange = useSpell() ? 10
                        : Math.min((weaponType.maxDistance + (longRanged ? 2 : 0)), 10);
                if (target.isNpc() && player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) && getAttackStyle() != null && getAttackStyle().isRanged()) attackRange++;
                TargetRoute.set(player, target, attackRange);
            }
        }
    }

    /**
     * Attack checks
     */

    public boolean canAttack(Entity target, boolean message) {
        if(isDead())
            return false;
        if(target == null || target.isHidden())
            return false;
        if(target.getCombat() == null) {
            if(message)
                player.sendMessage("You can't attack that npc.");
            return false;
        }
        if(target.getCombat().isDead())
            return false;
        if(player.isStunned()) {
            if(message)
                player.sendMessage("You're stunned!");
            return false;
        }
        if (player.isBreakableStunned()) {
            return false;
        }
        if(!multiCheck(target, message))
            return false;
        if(target.player != null) {
            if(player.attackPlayerListener != null) {
                if(!player.attackPlayerListener.allow(player, target.player, message))
                    return false;
            } else {
                if(message)
                    player.sendMessage("You can't attack players from where you're standing.");
                return false;
            }
        } else {
            if(target.npc.getCombat().getInfo().slayer_level > 0 && player.getStats().get(StatType.Slayer).currentLevel < target.npc.getCombat().getInfo().slayer_level) {
                if(message)
                    player.sendMessage("You need a Slayer level of at least " + target.npc.getCombat().getInfo().slayer_level + " to attack this monster.");
                return false;
            }
            if (target.npc.isTaskOnly() && !Slayer.isTask(player, target.npc)) {
                if(message)
                    target.npc.getTaskOnlyNPC().sendDialogue(player);
                return false;
            }
            if(target.npc.hasTarget() && !target.npc.isTargeting(player)) {
                if(message)
                    player.sendMessage("That monster isn't after you!");
                return false;
            }
            if(target.npc.attackNpcListener != null && !target.npc.attackNpcListener.allow(player, target.npc, message))
                return false;
            if(player.attackNpcListener != null && !player.attackNpcListener.allow(player, target.npc, message))
                return false;
        }
        return true;
    }

    public boolean multiCheck(Entity target, boolean message) {
        if(target.inMulti())
            return true;
        if(target.inSinglePlus() && allowSinglePlus(player, target))
            return true;
        if(target.player != null && BloodyChest.hasBloodyKey(target.player))
            return true;
        if (lastAttacker != null && lastAttacker.isNpc() && Arrays.stream(Resurrection.Thralls.values()).anyMatch(e -> e.npcId == lastAttacker.npc.getId()))
            return true;
        //if(entity == player.getBountyHunter().target)
        //    return true;
        //^This is how RS works, but people weren't liking this.. And it's kind of bad for a smaller pk scene.
        if(!target.getCombat().allowPj(player)) {
            if(message)
                player.sendMessage("That " + (target.player != null ? "player" : "monster") + " is already in combat!");
            return false;
        }
        if(!allowPj(target)) {
            if(lastAttacker != null && (lastAttacker.getHp() == 0 || lastAttacker.getCombat().isDead()))
                return true;
            if(message)
                player.sendMessage("You are already in combat!");
            return false;
        }
        return true;
    }

    /**
     * Attacking
     */

    public void attack() {
        if(target == null || !player.getRouteFinder().targetRoute.withinDistance)
            return;
        Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (weapon != null) {
            BiPredicate<Player, Item> weaponTest = weapon.getDef().getCustomValueOrDefault("CAN_ATTACK", null);
            if (weaponTest != null && !weaponTest.test(player, weapon)) {
                target = null;
                updateLastAttack(4);
                return;
            }
        }
        if(MapArea.MAGE_ARENA.inArea(player) && attackSet.style != AttackStyle.MAGIC) {
            player.sendMessage("You can only use magic inside the arena!");
            reset();
            return;
        }
        Equipment equipment = player.getEquipment();
        if (specialActive != null &&
                (equipment.hasId(ItemID.ACCURSED_SCEPTRE) || equipment.hasId(ItemID.ACCURSED_SCEPTRE_A))) {
            handleSpecial(AttackStyle.MAGIC, AttackType.ACCURATE, 0);
            updateLastAttack(4);
            if (player.getCombat().getAttackStyle() != AttackStyle.MAGIC) {
                player.getCombat().reset();
            }
        } else if(useSpell()) {
            if(!hasAttackDelay())
                attackWithMagic();
        } else if(attackSet.style == AttackStyle.RANGED) {
            if (!hasAttackDelay() || (specialActive instanceof DragonThrownaxe && Config.SPECIAL_ENERGY.get(player) >= 250))
                attackWithRanged();
        } else if (attackSet.style == AttackStyle.MAGIC) {
            if (!hasAttackDelay())
                attackWithMagicWeapon();
        } else {
            if (target.isNpc() && target.npc.getId() == 15016) { //brutal lava dragon flying
                player.sendMessage("You can't attack this npc with melee.");
                updateLastAttack(4);
                return;
            }
            if(!specialGraniteMaul() && !hasAttackDelay()) {
                attackWithMelee();
            }
        }
    }

    private void attackWithMagicWeapon() {
        ItemDefinition weaponDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        boolean swamp = weaponDef.id == 12899 || weaponDef.id == 22292;
        if (weaponDef.id == TridentOfTheSeas.UNCHARGED || weaponDef.id == 12900 || weaponDef.id == TridentOfTheSeas.ENHANCED_UNCHARGED || weaponDef.id == 22294) {
            player.sendMessage("Your trident has no charges left!");
            updateLastAttack(4);
            return;
        }
        if (weaponDef.id == SanguinestiStaff.UNCHARGED) {
            player.sendMessage("Your Sanguinesti staff has no charges left!");
            updateLastAttack(4);
            return;
        }
        if (weaponDef.id == CorruptedStaff.UNCHARGED) {
            player.sendMessage("Your Corrupted staff has no charges left!");
            updateLastAttack(4);
            return;
        }
        if (swamp || weaponDef.id == TridentOfTheSeas.CHARGED || weaponDef.id == TridentOfTheSeas.FULLY_CHARGED || weaponDef.id == TridentOfTheSeas.ENHANCED_CHARGED) {
            if (target.player != null) {
                player.sendMessage(Color.RED.wrap("This staff's spell cannot be used against other players."));
                return;
            }
            player.animate(1167);
            player.graphics(swamp ? 665 : 1251, 92, 0);
            int duration = new Projectile(swamp ? 1040 : 1252, 30, 0, 51, 56, 10, 16, 11).send(player, target);
            Hit hit = new Hit(player, AttackStyle.MAGIC, getAttackType()).randDamage(getTridentMaxDamage(swamp)).clientDelay(duration).setAttackWeapon(weaponDef);
            int damage = target.hit(hit);
            if (damage > 0) {
                target.graphics(swamp ? 1042 : 1253, 90, duration);
                if (target.isNpc() && player.getRelicManager().hasRelicEnalbed(Relic.ARCHMAGE)) aoeAttack(damage / 2, duration, 5);
            } else {
                hit.nullify();
                target.graphics(85, 92, duration);
            }
            updateLastAttack(4);
        }
        if (weaponDef.id == SanguinestiStaff.CHARGED) {
            if (target.player != null) {
                player.sendMessage(Color.RED.wrap("This staff's spell cannot be used against other players."));
                return;
            }
            player.animate(1167);
            player.graphics(1540, 92, 0);
            int duration = new Projectile(1539, 30, 0, 51, 56, 10, 16, 11).send(player, target);
            Hit hit = new Hit(player, AttackStyle.MAGIC, getAttackType()).randDamage(34).clientDelay(duration).setAttackWeapon(weaponDef);
            int damage = target.hit(hit);
            if (damage > 0) {
                if (Random.rollDie(6, 1)) {
                    player.incrementHp(hit.damage / 2);
                    target.graphics(1542, 90, duration);
                } else {
                    target.graphics(1541, 90, duration);
                }
            } else {
                hit.nullify();
                target.graphics(85, 92, duration);
            }
            updateLastAttack(4);
        }
        // Starter staff
        if (weaponDef.id == 28557) {
            if (target.player != null) {
                player.sendMessage(Color.RED.wrap("This staff's spell cannot be used against other players."));
                return;
            }
            player.animate(1167);
            player.graphics(90, 92, 0);
            int duration = new Projectile(91, 43, 31, 51, 56, 10, 16, 64).send(player, target);
            Hit hit = new Hit(player, AttackStyle.MAGIC, getAttackType()).randDamage(2).clientDelay(duration).setAttackWeapon(weaponDef);
            int damage = target.hit(hit);
            if (damage <= 0) {
                hit.nullify();
                target.graphics(85, 92, duration);
            }
            updateLastAttack(4);
        }
        if (weaponDef.id == ItemID.THAMMARONS_SCEPTRE || weaponDef.id == ItemID.ACCURSED_SCEPTRE) {
            boolean thammaronsSceptre = weaponDef.id == ItemID.THAMMARONS_SCEPTRE;
            player.animate(1167);
            target.publicSound(1460, 0, 76);
            player.publicSound(178);
            int duration = new Projectile(thammaronsSceptre ? 2340 : 2337, 30, 23, 46, 0, 10, 10, 64, true).send(player, target);
            Hit hit = new Hit(player, AttackStyle.MAGIC, getAttackType())
                    .randDamage(getThammaronSceptreMaxDamage(thammaronsSceptre ? 8 : 6))
                    .clientDelay(duration)
                    .setAttackWeapon(weaponDef);
            int damage = target.hit(hit);
            if (damage > 0) {
                target.graphics(78, 60, duration);
                if (target.isNpc() && player.getRelicManager().hasRelicEnalbed(Relic.ARCHMAGE)) aoeAttack(damage / 2, duration, 5);
            }
            updateLastAttack(5);
        }
    }

    public int getCorruptedStaffMaxDamage() {
        int base = 24;
        return (int) Math.round((Math.max(base, base + (Math.max(0, player.getStats().get(StatType.Magic).currentLevel - 77)) / 3)) * (1 + (player.getEquipment().bonuses[EquipmentStats.MAGIC_DAMAGE] / 100.0)));
    }

    public int getTridentMaxDamage(boolean swamp) {
        int base = 20;
        if (swamp)
            base += 3;
        return (int) Math.round((Math.max(base, base + (Math.max(0, player.getStats().get(StatType.Magic).currentLevel - 75)) / 3)) * (1 + (player.getEquipment().bonuses[EquipmentStats.MAGIC_DAMAGE] / 100.0)));
    }

    public int getThammaronSceptreMaxDamage(int minus) {
        int base = (player.getStats().get(StatType.Magic).currentLevel / 3) - minus;

        return (int) Math.floor(base * (1 + (player.getEquipment().bonuses[EquipmentStats.MAGIC_DAMAGE] / 100.0)));
    }

    private void attackAnim() {
        if (attackSet.attackAnimation != null)
            player.animate(attackSet.attackAnimation);
        else
            player.animate(weaponType.attackAnimation);
        if (weaponType.attackSound != -1 || attackSet.attackSound != null) {
            if (attackSet.attackSound != null) {
                player.privateSound(attackSet.attackSound, 1, 1);
            } else {
                player.privateSound(weaponType.attackSound, 1, 1);
            }
        }
    }

    private void ScytheOfVitur() {
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int maxDamage = CombatUtils.getMaxDamage(player, style, type);

        if (player.getEquipment().contains(22325)) {
            if (target.isPlayer()) {
                player.graphics(1231, 100, 0);
            } else {
                player.graphics(1231, 100, 0);
                target.hit(new Hit(player, style, type).randDamage(maxDamage / 2).setAttackWeapon(player.getEquipment().getDef(Equipment.SLOT_WEAPON)),
                        new Hit(player, style, type).randDamage(maxDamage / 3).setAttackWeapon(player.getEquipment().getDef(Equipment.SLOT_WEAPON)));
            }
        }
    }

    /**
     * Melee
     */

    private void attackWithMelee() {
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int maxDamage = CombatUtils.getMaxDamage(player, style, type);
        updateLastAttack(weaponType.attackTicks);

        Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);

        if(handleSpecial(style, type, maxDamage))
            return;
        attackAnim();
        ScytheOfVitur();

        Hit hit = new Hit(player, style, type).randDamage(maxDamage).setAttackWeapon(player.getEquipment().getDef(Equipment.SLOT_WEAPON));
        target.hit(hit);
    }

    /**
     * Ranged
     */

    public RangedData rangedData;

    private void attackWithRanged() {
        Item wep = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if(wep == null) {
            /* obviously should never happen */
            return;
        }
        ItemDefinition wepDef = wep.getDef();
        RangedWeapon rangedWep = wepDef.rangedWeapon;
        if(rangedWep == null) {
            player.sendMessage("Unhandled ranged weapon: " + wepDef.name);
            reset();
            return;
        }
        if(rangedWep == RangedWeapon.TOXIC_BLOWPIPE) {
            attackWithBlowpipe(wep);
            return;
        }
        if (rangedWep == RangedWeapon.CORRUPTED_JAVELIN) {
            attackWithCorruptedJavelin(wep);
            return;
        }
        Item ammo = null;
        RangedAmmo rangedAmmo = null;
        boolean fireDouble = false;
        if((rangedData = rangedWep.data) != null) {
            /**
             * Crystal bow, Knifes, Darts, etc
             */
            if(rangedWep != RangedWeapon.CRYSTAL_BOW && rangedWep != RangedWeapon.CRAWS_BOW && rangedWep != RangedWeapon.STARTER_BOW && rangedWep != RangedWeapon.WEBWEAVER_BOW)
                ammo = wep;
        } else {
            /**
             * Shortbows, Longbows, Crossbows, etc
             */
            ammo = player.getEquipment().get(Equipment.SLOT_AMMO);
            if(ammo == null) {
                player.sendMessage("You are all out of ammo.");
                reset();
                return;
            }
            ItemDefinition ammoDef = ammo.getDef();
            if((rangedAmmo = ammoDef.rangedAmmo) == null || !rangedWep.allowAmmo(rangedAmmo)) {
                player.sendMessage("Your weapon can't fire that ammo.");
                reset();
                return;
            }
            if(wepDef.rangedLevel < ammoDef.rangedLevel) {
                player.sendMessage("Your weapon is not strong enough to fire that ammo.");
                reset();
                return;
            }
            rangedData = rangedAmmo.data;
            if(rangedWep == RangedWeapon.DARK_BOW && ammo.getAmount() >= 2)
                fireDouble = true;
        }
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int maxDamage = CombatUtils.getMaxDamage(player, style, type);
        updateLastAttack(type == AttackType.RAPID_RANGED ? weaponType.attackTicks - 1 : weaponType.attackTicks);
        if(handleSpecial(style, type, maxDamage))
            return;
        attackAnim();
        if(!fireDouble) {
            boolean chins = wep.getId() == 10033 || wep.getId() == 10034 || wep.getId() == 11959;
            if(rangedData.drawbackId != -1)
                player.graphics(rangedData.drawbackId, 96, 0);
            int delay = rangedData.projectiles[0].send(player, target);
            Hit hit = new Hit(player, style, type).randDamage(maxDamage).clientDelay(delay).setAttackWeapon(wepDef);
            if(ammo != null) {
                // Chins dont roll for chance of saving ammo or drop on ground so just decrement.
                if (chins) {
                    if (!player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) || Random.rollDie(2)) {
                        ammo.incrementAmount(-1);
                    }
                } else {
                    removeAmmo(ammo, hit);
                }

                hit.setRangedAmmo(ammo.getDef());
            }
            if(rangedAmmo != null && rangedAmmo.effect != null && rangedAmmo.effect.apply(target, hit))
                return;
            if (target.isNpc() && player.getRelicManager().hasRelic(Relic.DEADEYE) && RangedAmmo.procDeadeyeBoltEffect(target, hit)) {
                return;
            }

            target.hit(hit);
            if (chins) {
                target.graphics(157, 100, delay);
                aoeAttack(maxDamage, delay, 9);
            }
        } else {
            if(rangedData.doubleDrawbackId != -1)
                player.graphics(rangedData.doubleDrawbackId, 96, 0);
            int delay = rangedData.projectiles[1].send(player, target);
            rangedData.projectiles[2].send(player, target);
            Hit[] hits = {
                    new Hit(player, style, type).randDamage(maxDamage).clientDelay(delay).setRangedAmmo(ammo.getDef()).setAttackWeapon(wepDef),
                    new Hit(player, style, type).randDamage(maxDamage).clientDelay(delay).setRangedAmmo(ammo.getDef()).setAttackWeapon(wepDef),
            };
            removeAmmo(ammo, hits);
            for (Hit hit : hits) {
                if (target.isNpc() && player.getRelicManager().hasRelic(Relic.DEADEYE) && RangedAmmo.procDeadeyeBoltEffect(target, hit)) {
                    continue;
                }
                target.hit(hit);
            }
        }
    }

    private void attackWithCorruptedJavelin(Item wep) {
        if (AttributeExtensions.getCharges(wep) <= 0) {
            player.sendMessage("Your javelin isn't charged with any essence.");
            reset();
            return;
        }
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int maxDamage = CombatUtils.getMaxDamage(player, style, type);
        int attackTicks = type == AttackType.RAPID_RANGED ? weaponType.attackTicks - 1 : weaponType.attackTicks;
        if(target.npc != null)
            attackTicks--;
        updateLastAttack(attackTicks);
        Hit hit = new Hit(player, style, type)
                .setAttackWeapon(wep.getDef())
                .setRangedAmmo(ItemDefinition.get(11230));
        player.graphics(5006, 96, 0);
        attackAnim();
        Projectile proj =  Projectile.javelin(5019)[0];
        int delay = proj.send(player, target);
        hit.randDamage(maxDamage).clientDelay(delay);
        target.hit(hit);
    }

    private void attackWithBlowpipe(Item blowpipe) {
        Blowpipe.Dart dart = Blowpipe.getDart(blowpipe);
        int dartAmount = Blowpipe.getDartAmount(blowpipe);
        if (dart == Blowpipe.Dart.NONE || dartAmount == 0) {
            player.sendMessage("Your blowpipe isn't loaded with any darts.");
            reset();
            return;
        }
        int scalesAmount = Blowpipe.getScalesAmount(blowpipe);
        if (scalesAmount == 0) {
            player.sendMessage("Your blowpipe isn't charged with any scales.");
            reset();
            return;
        }
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int maxDamage = CombatUtils.getMaxDamage(player, style, type);
        int attackTicks = type == AttackType.RAPID_RANGED ? weaponType.attackTicks - 1 : weaponType.attackTicks;
        if (target.npc != null)
            attackTicks--;
        updateLastAttack(attackTicks);
        Hit hit = new Hit(player, style, type)
                .setAttackWeapon(blowpipe.getDef())
                .setRangedAmmo(ItemDefinition.get(dart.id));
        attackAnim();
        if (handleSpecial(style, type, maxDamage)) {
            int delay = ToxicBlowpipe.SIPHON_PROJECTILE.send(player, target);
            player.publicSound(2696);
            player.publicSound(800, 1, delay);
            hit.randDamage(maxDamage).boostDamage(0.50).clientDelay(delay);
            int damage = target.hit(hit);
            if (damage >= 2)
                player.incrementHp(damage / 2);
        } else {
            int delay = dart.rangedData.projectiles[0].send(player, target);
            hit.randDamage(maxDamage).clientDelay(delay);
            if (target.isNpc() && player.getRelicManager().hasRelic(Relic.DEADEYE) && RangedAmmo.procDeadeyeBoltEffect(target, hit)) {
            } else {
                target.hit(hit);
            }
        }
        if (!rollAvaAttractor()
                && !rollAvaAccumulator()
                && !rollAvaAssembler()
                && (!player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) || Random.rollDie(2)))
            dartAmount--;
        if(Random.rollDie(3, 2) && (!player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) || Random.rollDie(2)))
            scalesAmount--;
        Blowpipe.update(blowpipe, dart, dartAmount, scalesAmount);
    }

    public void removeAmmo(Item ammo, Hit... hits) {
        if(rangedData.alwaysBreak) {
            if (!player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) || Random.rollDie(2)) ammo.remove(hits.length);
            return;
        }
        for(Hit hit : hits) {
            if (hasAvaAttractor()) {
                if (rollAvaAttractor() || (player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) && Random.rollDie(2))) {
                    continue;
                } else {
                    if (Random.rollPercent(40)) {
                        ammo.incrementAmount(-1);
                        return;
                    }
                }
            } else if (hasAvaAccumulator()) {
                if (rollAvaAccumulator() || (player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) && Random.rollDie(2))) {  // Keep ammo
                    continue;
                } else {
                    if (Random.rollPercent(71)) {   // Break ammo
                        ammo.incrementAmount(-1);
                        return;
                    }
                }
            } else if (hasAvaAssembler()) {
                if (rollAvaAssembler() || (player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) && Random.rollDie(2))) {  // Keep ammo
                    continue;
                } else {
                    ammo.incrementAmount(-1);
                    return;
                }
            } else {
                if (player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) && Random.rollDie(2)) return;
                if (Random.rollPercent(20)) {   // Break ammo w/o ava
                    ammo.incrementAmount(-1);
                    return;
                }
            }
            // Drop ammo
            ammo.incrementAmount(-1);
            hit.drop(new GroundItem(ammo.getId(), 1).owner(player).position(target.getPosition()));
        }
    }

    private boolean hasAvaAttractor() {
        int capeId = player.getEquipment().getId(Equipment.SLOT_CAPE);
        return capeId == 10498;
    }

    private boolean hasAvaAccumulator() {
        int capeId = player.getEquipment().getId(Equipment.SLOT_CAPE);
        return capeId == 10499 || capeId == 9756 || capeId == 9757 || capeId == 32022;
    }

    private boolean hasAvaAssembler() {
        int capeId = player.getEquipment().getId(Equipment.SLOT_CAPE);
        return capeId == 22109 || MaxCapeVariants.wearing(player);
    }

    private boolean rollAvaAttractor() {
        if (!hasAvaAttractor()) return false;
        return Random.rollPercent(60); //60% chance according to wiki
    }

    private boolean rollAvaAccumulator() {
        if (!hasAvaAccumulator()) return false;
        return Random.rollPercent(72); //72% chance according to wiki
    }

    private boolean rollAvaAssembler() {
        if (!hasAvaAssembler()) return false;
        return Random.rollPercent(80); //80% chance according to wiki
    }

    /**
     * Magic
     */

    public TargetSpell queuedSpell, autocastSpell;

    public boolean useSpell() {
        return queuedSpell != null || autocastSpell != null;
    }

    public void queueSpell(TargetSpell spell, Entity target) {
        queuedSpell = spell;
        setTarget(target);
    }

    private void attackWithMagic() {
        TargetSpell spell;
        boolean autocast;
        if(queuedSpell == null) {
            spell = autocastSpell;
            autocast = true;
        } else {
            spell = queuedSpell;
            autocast = false;
        }
        if(!spell.cast(player, target)) {
            reset();
            return;
        }
        updateLastAttack(5);
        if(!autocast)
            reset();
    }

    /**
     * Reset
     */

    @Override
    public void reset() {
        updateLastTarget(target);
        super.reset();
        queuedSpell = null;
        rangedData = null;
        player.faceNone(!isDead());
        TargetRoute.reset(player);
    }

    /**
     * Retliate
     */

    @Override
    public boolean allowRetaliate(Entity attacker) {
        return target == null && player.getMovement().isAtDestination() && Config.AUTO_RETALIATE.get(player) == 0 && !player.isLocked() && !(attacker.isNpc() && attacker.getCombat() != null && attacker.getCombat() instanceof PitfallCreature);
    }

    @Override
    public int getDefendAnimation() {
        if(player.recentlyEquipped.isDelayed())
            return -1;
        ItemDefinition shieldDef = player.getEquipment().getDef(Equipment.SLOT_SHIELD);
        if(shieldDef != null && shieldDef.shieldType != null)
            return shieldDef.shieldType.animationId;
        return weaponType.defendAnimation;
    }

    @Override
    public int getDefendSound() {
        return -1;
    }

    /**
     * Hits to this player
     */

    private void preDefend(Hit hit) {
        for(Item item : player.getEquipment().getItems()) {
            if(item != null && item.getDef() != null) {
                item.getDef().preDefend(player, item, hit);
                if (hit.attacker != null && hit.attacker.isNpc()) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                effect.getUpgrade().preDefend(player, hit.attacker, item, hit);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private void postDefend(Hit hit) {
        if(hit.attackStyle != null && !hit.prayerIgnored) {
            if(hit.attackStyle.isMelee() || hit.attackStyle.isMagicalMelee() || hit.attackStyle.isRangedMelee()) {
                if(player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {
                    if(hit.attacker != null && hit.attacker.player != null)
                        hit.damage *= 0.60;
                    else
                        hit.damage = 0;
                }
            } else if(hit.attackStyle.isRanged() || hit.attackStyle.isMagicalRanged()) {
                if(player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
                    if(hit.attacker != null && hit.attacker.player != null)
                        hit.damage *= 0.60;
                    else
                        hit.damage = 0;
                }
            } else if(hit.attackStyle.isMagic() || hit.attackStyle.isRangedMagic()) {
                if(player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
                    if(hit.attacker != null && hit.attacker.player != null)
                        hit.damage *= 0.60;
                    else
                        hit.damage = 0;
                }
            }
        }
        if(hit.attacker != null) {
            /* elysian spirit shield */
            if(player.getEquipment().getId(Equipment.SLOT_SHIELD) == 12817 && Random.rollPercent(70)) {
                hit.damage *= 0.75;
                player.graphics(321);
            }
            /* divine spirit shield */
            if(!hit.attacker.isPlayer()) {
                if (player.getEquipment().getId(Equipment.SLOT_SHIELD) == 30191 && Random.rollPercent(70)) {
                    int drain = (int) (Math.ceil(hit.damage * 0.3) / 2);
                    if (player.getStats().get(StatType.Prayer).currentLevel > drain) {
                        player.getPrayer().drain(drain);
                        hit.damage *= 0.70;
                        player.graphics(5024);
                    }
                }
            }
            if(player.sotdDelay.isDelayed() && hit.attackStyle != null && hit.attackStyle.isMelee()) {
                hit.damage *= 0.50;
            }
        }
        if(player.vestasSpearSpecial.isDelayed() && hit.attackStyle != null && hit.attackStyle.isMelee()) {
            hit.block();
        }
    }

    private void preDamage(Hit hit) {
        TransformationRing.check(player);
    }

    private void postDamage(Hit hit) {
        Vengeance.check(player, hit);
        //checkJuggernautThorns(player, hit);
        RingOfRecoil.check(player, hit);
        RingOfSuffering.check(player, hit);
        Redemption.check(player);
        NecklaceOfFaith.check(player);
        PhoenixNecklace.check(player);
        RingOfLife.check(player);
        DefenceSkillCape.check(player);
        SetEffect.DHAROK_DAMNED.checkAndApply(player, target, hit);
        for(Item item : player.getEquipment().getItems()) {
            if(item != null && item.getDef() != null) {
                item.getDef().postDamage(player, item, hit);
                if (target != null && target.isNpc()) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                effect.getUpgrade().postPlayerDamage(player, target, item, hit);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean salveAmuletBoost(Player player, Hit hit, Entity entity) {
        if (hit.attackStyle != null && target != null && target.isNpc()) {
            Item amulet = player.getEquipment().get(Equipment.SLOT_AMULET);
            if (amulet == null) {
                return false;
            }
            if (!target.npc.getDef().hasAttribute("UNDEAD")) {
                return false;
            }
            int amuletId = amulet.getId();
            if (hit.attackStyle.isMelee()) {
                if (amuletId == Items.SALVE_AMULET || amuletId == Items.SALVE_AMULET_I) {
                    hit.boostAttack(0.1667);
                    hit.boostDamage(0.1667);
                } else if (amuletId == Items.SALVE_AMULET_E || amuletId == Items.SALVE_AMULET_EI) {
                    hit.boostAttack(0.2);
                    hit.boostDamage(0.2);
                }
                return true;
            } else {
                if (amuletId == Items.SALVE_AMULET_I) {
                    if (hit.attackStyle.isRanged()) {
                        hit.boostAttack(0.1667);
                        hit.boostDamage(0.1667);
                    } else {
                        hit.boostAttack(0.15);
                        hit.boostDamage(0.15);
                    }
                } else if (amuletId == Items.SALVE_AMULET_EI) {
                    hit.boostAttack(0.2);
                    hit.boostDamage(0.2);
                } else {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private void tierOneVampireDamageBoost(Player player, Hit hit) {
        if (hit.attackStyle != null && target != null && target.isNpc()) {
            String name = target.npc.getDef().name;
            if (!name.equalsIgnoreCase("dessous") && !name.equalsIgnoreCase("feral vampyre")
                    && !name.equalsIgnoreCase("kroy") && !name.equalsIgnoreCase("vampyre juvenile")) {
                return;
            }
            Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
            if ((weapon != null && Vampyre.isSilver(weapon.getDef().name)) || EfaritaysAid.test(player)) {
                hit.boostDamage(0.10);
            }
        }
    }

    /**
     * The Amulet of Avarice boosts the player's accuracy and damage by 20% when fighting revenants.
     * <a href="https://oldschool.runescape.wiki/w/Amulet_of_avarice">Wiki link</a>
     */
    private boolean amuletOfAvariceBoost(Player player, Hit hit, Entity entity) {
        final int amuletOfAvarice = 22557;
        if (hit.attackStyle != null && target != null && target.isNpc()) {
            Item amulet = player.getEquipment().get(Equipment.SLOT_AMULET);
            if (amulet == null || amulet.getId() != amuletOfAvarice) {
                return false;
            }
            if (!target.npc.getDef().name.toLowerCase().contains("revenant")) {
                return false;
            }
            hit.boostAttack(0.2);
            hit.boostDamage(0.2);
            return true;
        }
        return false;
    }

    /**
     * Hits from this player
     */

    private void preTargetDefend(Hit hit, Entity target) {
        if (target == null) return;
        boolean slayerHelmEffectActive = SlayerHelm.boost(player, target, hit);
        //If slayer helmet effect isn't active try salve amulet
        if (!slayerHelmEffectActive) {
            salveAmuletBoost(player, hit, target);
            amuletOfAvariceBoost(player, hit, target);
        }
        if (player.getRelicManager().hasRelic(Relic.MONSTER_HUNTER)) {
            if (hit.attackStyle != null && target.npc != null && (Slayer.isTask(player, target.npc)
                    || target.npc.getId() == 7413)) {
                hit.boostAttack(0.1);
                hit.boostDamage(0.1);
            }
        }
        SetEffect.DHAROK.checkAndApply(player, target, hit);
        SetEffect.VERAC.checkAndApply(player, target, hit);
        SetEffect.AHRIM_DAMNED.checkAndApply(player, target, hit);
        SetEffect.VOID_MAGE.checkAndApply(player, target, hit) ;
        SetEffect.VOID_RANGE.checkAndApply(player, target, hit);
        SetEffect.VOID_MELEE.checkAndApply(player, target, hit);
        SetEffect.ELITE_VOID_MAGE.checkAndApply(player, target, hit);
        SetEffect.ELITE_VOID_RANGE.checkAndApply(player, target, hit);
        SetEffect.ELITE_VOID_MELEE.checkAndApply(player, target, hit);
        SetEffect.BERSERKER_NECKLACE.checkAndApply(player, target, hit);
        SetEffect.OBSIDIAN_ARMOUR.checkAndApply(player, target, hit);

        // Bestiary damage perk handling
        if (target.isNpc()) {
            double damage = player.getBestiary().getBestiaryEntry(target.npc.getDef()).getPerkMultiplier(DamagePerk.class, 0);
            hit.boostDamage(damage);
            double accuracy = player.getBestiary().getBestiaryEntry(target.npc.getDef()).getPerkMultiplier(AccuracyPerk.class, 0);
            hit.boostAttack(accuracy);
        }

        // Relic handling
        RelicManager rm = player.getRelicManager();
        if (target.isNpc() && rm.hasRelicEnalbed(Relic.ARCHMAGE) && hit.attackStyle != null && hit.attackStyle.isMagic()) {
            hit.boostDamage(0.4);
            hit.boostAttack(0.8);
        } else if (target.isNpc() && rm.hasRelicEnalbed(Relic.DEADEYE) && hit.attackStyle != null && hit.attackStyle.isRanged()) {
            hit.boostDamage(0.2);
            hit.boostAttack(0.4);
        } else if (target.isNpc() && rm.hasRelicEnalbed(Relic.JUGGERNAUT) && hit.attackStyle != null && hit.attackStyle.isMelee()) {
            hit.boostDamage(0.2);
            hit.boostAttack(0.2);
        }

        // Efaritay's aid vs Tier 1 vampyre
        tierOneVampireDamageBoost(player, hit);

        /* twisted bow */
        if(hit.attackStyle != null && hit.attackStyle.isRanged() && (player.getEquipment().hasId(20997))) {
            double magicLevel = target.getCombat().getLevel(StatType.Magic);
            if (target.isNpc()) {
                int magicAttack = target.npc.getCombat().getInfo().magic_attack;
                if (magicAttack > magicLevel)
                    magicLevel = magicAttack;
            }
            magicLevel = Math.min(250, magicLevel);
            double accuracy = 140D + ((3 * magicLevel - 10D) / 100D) - (Math.pow((3 * magicLevel / 10D) - 100D, 2) / 100D);
            accuracy = Math.min(140, accuracy);
            double damage = 250D + ((3 * magicLevel - 14D) / 100D) - (Math.pow((3 * magicLevel / 10) - 140D, 2) / 100D);
            damage = (damage - 100) / 100;
            accuracy = (accuracy - 100) / 100;
            if (damage > 0)
                hit.boostDamage(damage);
            if (accuracy > 0)
                hit.boostAttack(accuracy);
        }

        for(Item item : player.getEquipment().getItems()) {
            if(item != null && item.getDef() != null) {
                item.getDef().preTargetDefend(player, item, hit, target);
                if (target.isNpc()) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                effect.getUpgrade().preTargetDefend(player, target, item, hit);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }

    private void postTargetDefend(Hit hit, Entity target) {
        for(Item item : player.getEquipment().getItems()) {
            if(item != null && item.getDef() != null) {
                item.getDef().postTargetDefend(player, item, hit, target);
                if (target != null && target.isNpc()) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                effect.getUpgrade().postTargetDefend(player, target, item, hit);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        if (target != null
                && target.isNpc()
                && hit.damage > 0
                && hit.attackStyle != null
                && hit.attackStyle.isMelee()
                && player.getRelicManager().hasRelicEnalbed(Relic.JUGGERNAUT)
                && Random.rollDie(10)) {
            hit.damage *= 2;
            target.graphics(659);
        }
        if (target.npc != null && ChambersOfXeric.isRaiding(player))
            ChambersOfXeric.addDamagePoints(player, target.npc, hit.damage);
        /*
         * Poison handling
         */
        if (hit.damage > 0) {
            AttackStyle style = hit.attackStyle;
            String weaponName = hit.attackWeapon != null ? hit.attackWeapon.name.toLowerCase() : "";
            String ammoName = hit.rangedAmmo != null ? hit.rangedAmmo.name.toLowerCase() : "";
            if (style != null) {
                if (style.isMelee()) {
                    if (Random.rollDie(4)) {
                        if (weaponName.contains("(p++)")) {
                            target.poison(6);
                        } else if (weaponName.contains("(p+)")) {
                            target.poison(5);
                        } else if (weaponName.contains("(p)") || weaponName.contains("abyssal tentacle")) {
                            target.poison(4);
                        } else if (weaponName.contains("(kp)")) {
                            target.poison(6);
                        }
                    }
                } else if (style.isRanged()) {
                    if (Random.rollDie(8)) {
                        if (weaponName.contains("(p++)") || ammoName.contains("(p++)")) {
                            target.poison(4);
                        } else if (weaponName.contains("(p+)") || ammoName.contains("(p+)")) {
                            target.poison(3);
                        } else if (weaponName.contains("(p)") || ammoName.contains("(p)")) {
                            target.poison(2);
                        }
                    }
                }
            }
            /*
             * Venom handling
             */
            if (hit.attacker.player != null) {
                Item helmet = hit.attacker.player.getEquipment().get(Equipment.SLOT_HAT);
                if (target.npc != null && style != null) {   // Can only venom npcs
                    int roll = 0;
                    // If player is using a venom weapon
                    if ((weaponName.contains("trident of the swamp")
                            || weaponName.contains("toxic staff of the dead")
                            || weaponName.contains("toxic blowpipe"))
                            && !style.isMelee()) {  // So you cant venom with a staff melee hit
                        roll = 4;   // 25% chance
                        // If the player has serp helm equipped, its 100% chance
                        if (helmet != null && helmet.getId() == 12931) {
                            roll = 1;
                        }
                    } else if (helmet != null && helmet.getId() == 12931) {
                        roll = 6;
                        if (style.isMelee()) {
                            if (weaponName.contains("(p++)") || weaponName.contains("(p+)")
                                    || weaponName.contains("(p)") || weaponName.contains("abyssal tentacle")
                                    || weaponName.contains("(kp)")) {
                                roll = 2;
                            }
                        } else if (style.isRanged()) {
                            if (weaponName.contains("(p++)") || ammoName.contains("(p++)")
                                    || weaponName.contains("(p+)") || ammoName.contains("(p+)")
                                    || weaponName.contains("(p)") || ammoName.contains("(p)")) {
                                roll = 2;
                            }
                        }
                    }
                    if (roll > 0 && Random.rollDie(roll)) {
                        target.envenom(6);
                    }
                }
            }
        }
    }

    private void preTargetDamage(Hit hit, Entity target) {
        TransformationRing.check(player);
    }

    private void postTargetDamage(Hit hit, Entity target) {
        //If you modify damage inside this method it's absolutely pointless. ("POST" DAMAGE)
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\\

        if(player.getPrayer().isActive(Prayer.SMITE)) {
            if(target.player != null)
                target.player.getPrayer().drain(hit.damage / 4);
        }

        boolean guthansDamnedEffectActive = SetEffect.GUTHAN_DAMNED.checkAndApply(player, target, hit);
        if (!guthansDamnedEffectActive) {
            SetEffect.GUTHAN.checkAndApply(player, target, hit);
        }
        if (hit.attackStyle == AttackStyle.CRUSH || hit.attackStyle == AttackStyle.SLASH || hit.attackStyle == AttackStyle.STAB) {
            if (hit.damage > 0 && AmuletOfBloodFury.test(player) && Random.rollDie(5)) {
                target.graphics(398,0 ,0);  // TODO get proper blood ammy gfx
                player.incrementHp((int) Math.ceil(hit.damage * 0.3));
            }
        }

        if (hit.damage > 0) {   // if the hit actually hits
            SetEffect.KARIL.checkAndApply(player, target, hit);
            SetEffect.AHRIM.checkAndApply(player, target, hit);
            SetEffect.TORAG.checkAndApply(player, target, hit);
            SetEffect.KARIL_DAMNED.checkAndApply(player, target, hit);
            deadeyeExtraHit(hit, target);
        }
        for(Item item : player.getEquipment().getItems()) {
            if(item != null && item.getDef() != null)
                item.getDef().postTargetDamage(player, item, hit, target);
        }
    }

    public void deadeyeExtraHit(Hit hit, Entity target) {
        if (target.isNpc() && player.getRelicManager().hasRelicEnalbed(Relic.DEADEYE) && Random.rollDie(10) && hit.attackStyle != null && hit.attackStyle.isRanged()) {
            if (target.getHp() <= 0 || hit.attackWeapon == null) {
                return;
            }
            Hit secondHit = new Hit(player, hit.attackStyle, hit.attackType).randDamage(hit.maxDamage).setAttackWeapon(null).keepCharges().ignoreDefence().ignorePrayer();
            RangedAmmo rangedAmmo = hit.rangedWeapon == null ? null : hit.rangedWeapon.rangedAmmo;
            if(rangedAmmo != null && rangedAmmo.effect != null && rangedAmmo.effect.apply(target, secondHit))
                return;
            if (target.isNpc() && player.getRelicManager().hasRelic(Relic.DEADEYE) && RangedAmmo.procDeadeyeBoltEffect(target, secondHit)) {
                return;
            }
            target.hit(secondHit);
        }
    }

    /**
     * Death
     */

    @Override
    public void startDeath(Hit killHit) {
        player.lock();
        setDead(true);
        if(target != null) {
            reset();
        }
        Killer killer = getKiller();
        if(player.deathStartListener != null) {
            player.deathStartListener.handle(player, killer, killHit);
        }
        player.resetActions(true, true, true);
        player.startEvent(event -> {
            event.delay(1);
            player.animate(836);
            Retribution.check(player, killer);
            event.delay(4);
            player.resetAnimation();
            setTruelyDead(true);
            if(player.deathEndListener != null) {
                player.deathEndListener.handle(player, killer, killHit);
            } else {
                Player pKiller = killer != null ? killer.player : null;

                Leaderboard.addKill(pKiller);
                Leaderboard.addDeath(player);

                /**
                 * Rewards (Keep before items lost on death!)
                 */
                if(pKiller != null) {
                    Config.PVP_DEATHS.increment(player, 1);
                    player.currentKillSpree = 0;
                }

                /**
                 * Items lost on death
                 */
                boolean useDeathStorage = pKiller == null && player.wildernessLevel == 0;
                if (useDeathStorage) {
                    player.getDeathStorage().death(killer);
                } else {
                    IKOD.forLostItem(player, killer, item -> {
                        ItemDefinition def = item.getDef();
                        if (pKiller != null && pKiller.hideFreeItems && def.free) {
                            return;
                        }
                        if (def.id == 12746 || def.id == 12748) { //tier 1-2 emblems
                            if (pKiller != null)
                                new GroundItem(12746, 1).owner(pKiller).position(player.getPosition()).spawnPrivate(); //Never want emblem to appear for anyone else.
                            return;
                        }
                        if (def.id >= 12749 && def.id <= 12756) { //tier 3-10 emblems
                            if (pKiller != null)
                                new GroundItem(def.id - 1, 1).owner(pKiller).position(player.getPosition()).spawnPrivate(); //Never want emblem to appear for anyone else.
                            return;
                        }
                        if (def.id == 21807 || def.id == 21810 || def.id == 21813) { // ancient emblem, totem and statuette
                            if (pKiller != null)
                                new GroundItem(def.id, 1).owner(pKiller).position(player.getPosition()).spawnPrivate(); //Never want emblem to appear for anyone else.
                            return;
                        }
                        if (pKiller == null || !def.tradeable)
                            new GroundItem(item).owner(player).position(player.getPosition()).spawn(60);
                        else if (player.getGameMode().isIronMan())
                            new GroundItem(item).owner(pKiller).position(player.getPosition()).droppedByIron(player).spawn();
                        else if (pKiller.getGameMode().isIronMan())
                            new GroundItem(item).owner(pKiller).position(player.getPosition()).ironPlayerKill().spawn();
                        else
                            new GroundItem(item).owner(pKiller).position(player.getPosition()).spawn();
                    });
                }
                /**
                 * Drop bones (Keep after all other item drops!)
                 */
                if(pKiller == null)
                    new GroundItem(526, 1).owner(player).position(player.getPosition()).spawn(60);
                else
                    new GroundItem(526, 1).owner(pKiller).position(player.getPosition()).spawn();
                /**
                 * Misc
                 */
                player.face(Direction.NORTH);
                player.sendMessage("Oh dear, you are dead!");
                if(player.wildernessLevel > 15)
                    player.presetDelay.delaySeconds(10);
                player.getMovement().teleport(player.getRespawnPoint().getRandomPosition());

                /**
                 * HCIM - revoke status
                 */
                if (player.getGameMode().isHardcoreIronman()) {
                    GameMode.hardcoreDeath(player, killHit);
                }
            }
            resetSkull();
            resetSkullers();
            restore();
            event.delay(1);
            setTruelyDead(false);
            setDead(false);
            player.unlock();
        });
    }

    /**
     * Restore
     */

    public void restore() {
        player.getStats().restore(true);
        player.getMovement().restoreEnergy(100);
        player.getPrayer().deactivateAll();
        player.resetFreeze();
        player.cureVenom(0, 0);
        restoreSpecial(100);
        if(specialActive != null)
            deactivateSpecial();
        TargetOverlay.reset(player);
    }

    /**
     * Level
     */

    private int level;

    private boolean updateLevel = true;

    public void updateLevel() {
        updateLevel = true;
    }

    public void checkLevel() {
        if(updateLevel) {
            updateLevel = false;
            setLevel();
        }
    }

    protected void setLevel() {
        int newLevel = CombatUtils.getCombatLevel(player);
        if(level != newLevel) {
            level = newLevel;
            player.getAppearance().update();
            player.getPacketSender().sendString(COMBAT_OPTIONS, 3, "Combat Lvl: " + level);
        }
    }

    public void updateCombatLevel() {
        player.getPacketSender().sendString(COMBAT_OPTIONS, 3, "Combat Lvl: " + level);
    }

    public int getLevel() {
        return level;
    }

    /**
     * Weapon
     */

    public WeaponType weaponType;

    private AttackSet attackSet;

    public void updateWeapon(boolean fromAutocast) {
        ItemDefinition wepDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        WeaponType newWeapon = (wepDef == null || wepDef.weaponType == null) ? WeaponType.UNARMED : wepDef.weaponType;
        boolean login = weaponType == null;
        if(weaponType != newWeapon) {
            weaponType = newWeapon;
            updateAttackSet();
            Config.WEAPON_TYPE.set(player, weaponType.config);
        }
        if(!fromAutocast)
            TabCombat.updateAutocast(player, login);
        if(specialActive != null && (wepDef == null || wepDef.special != specialActive))
            deactivateSpecial();
        player.getPacketSender().sendString(COMBAT_OPTIONS, 1, wepDef == null ? "Unarmed" : wepDef.name);
        if(player.sotdDelay.isDelayed() && (wepDef == null || !(wepDef.special instanceof StaffOfTheDead)))
            player.sotdDelay.reset();
        if(player.vestasSpearSpecial.isDelayed() && (wepDef == null || !(wepDef.special instanceof VestasSpear)))
            player.vestasSpearSpecial.reset();
    }

    private void updateAttackSet() {
        int setIndex = Config.ATTACK_SET.get(player);
        if((attackSet = weaponType.attackSets[setIndex]) == null) {
            for(int i = setIndex; i >= 0; i--) {
                if(weaponType.attackSets[i] != null) {
                    attackSet = weaponType.attackSets[i];
                    Config.ATTACK_SET.set(player, i);
                    break;
                }
            }
        }
    }

    public void changeAttackSet(int newSetIndex) {
        boolean invalidAttackSet = weaponType.attackSets[newSetIndex] == null;
        attackSet = weaponType.attackSets[invalidAttackSet ? 0 : newSetIndex];
        Config.ATTACK_SET.set(player, invalidAttackSet ? 0 : newSetIndex);
        TabCombat.resetAutocast(player);
    }

    @Override
    public AttackStyle getAttackStyle() {
        return attackSet.style;
    }

    @Override
    public AttackType getAttackType() {
        return attackSet.type;
    }

    /**
     * Special
     */

    private Special specialActive;

    private void deactivateSpecial() {
        specialActive = null;
        Config.SPECIAL_ACTIVE.set(player, 0);
    }

    public void toggleSpecial() {
        ItemDefinition wepDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        if(specialActive != null) {
            deactivateSpecial();
            if(wepDef != null && wepDef.graniteMaul)
                queueGraniteMaulSpecial();
            return;
        }
        if(wepDef != null && wepDef.special != null) {
            if(DuelRule.NO_SPECIALS.isToggled(player)) {
                player.sendMessage("Special attacks have been disabled for this duel!");
                return;
            }
            if(!wepDef.special.handleActivation(player)) {
                if(wepDef.graniteMaul)
                    queueGraniteMaulSpecial();
                specialActive = wepDef.special;
                Config.SPECIAL_ACTIVE.set(player, 1);
            }
        }
    }

    private boolean handleSpecial(AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        Special special = specialActive;
        if(special == null)
            return false;
        int amount = special.getDrainAmount();
        int energy = Config.SPECIAL_ENERGY.get(player);
        if(amount > energy / 10) {
            player.sendMessage("You need at least " + amount + "% special attack energy to use this.");
            specialActive = null;
            Config.SPECIAL_ACTIVE.set(player, 0);
            return false;
        }

        if(!special.handle(player, target, attackStyle, attackType, maxDamage)) {
            specialActive = null;
            Config.SPECIAL_ACTIVE.set(player, 0);
            return false;
        }

        if (target != null && target.player != null) {
            player.specTeleportDelay.delaySeconds(5);
        }

        boolean ignoresSpecialDrain = (target != null && target.isNpc() && target.npc != null
                && Arrays.stream(MaxHitDummy.dummyIds).anyMatch(i -> i == target.npc.getId()));
        if (!ignoresSpecialDrain) {
            Config.SPECIAL_ENERGY.set(player, energy - (amount * 10)); //drain special energy
        }

        specialActive = null;
        Config.SPECIAL_ACTIVE.set(player, 0);
        player.getTaskManager().doLookupByUUID(136);    // Perform a Special Attack
        return true;
    }

    public void restoreSpecial(int percent) {
        int energy = Config.SPECIAL_ENERGY.get(player);
        int newEnergy = Math.min(1000, energy + (percent * 10));
        if(energy != newEnergy)
            Config.SPECIAL_ENERGY.set(player, newEnergy);
    }

    public boolean useSpecialEnergy(int amount) {
        int energy = Config.SPECIAL_ENERGY.get(player);
        if(amount > energy / 10) {
            player.sendMessage("You need at least " + amount + "% special attack energy to use this.");
            return false;
        }
        Config.SPECIAL_ENERGY.set(player, energy - (amount * 10));
        return true;
    }

    /**
     * Special - Granite maul
     * player.sendMessage("Warning: Since the maul's special is an instant attack, it will be wasted when used on a first strike.");
     * ^ todo? (There's really no good place to put it..:c lol)
     */

    private int graniteMaulSpecials;

    private int graniteMaulTimeoutTicks;

    private void queueGraniteMaulSpecial() {
        graniteMaulSpecials++;
        graniteMaulTimeoutTicks = 5;
    }

    private void checkGraniteMaul() {
        if(graniteMaulTimeoutTicks > 0) {
            if(--graniteMaulTimeoutTicks == 0)
                graniteMaulSpecials = 0;
            else if(graniteMaulTimeoutTicks == 4) //1 tick less than 5 because it was subtracted
                autoAttackGraniteMaul();
        }
    }

    private void autoAttackGraniteMaul() {
        if(target != null || lastTarget == null)
            return;
        if(player.getHeight() != lastTarget.getHeight())
            return;
        int x = player.getAbsX();
        int y = player.getAbsY();
        if(lastTarget.getSize() == 1) {
            int targetX = lastTarget.getAbsX();
            int targetY = lastTarget.getAbsY();
            int diffX = Math.abs(x - targetX);
            int diffY = Math.abs(y - targetY);
            if((diffX + diffY) != 1)
                return;
        } else {
            Position closestPos = Misc.getClosestPosition(player, lastTarget);
            int targetX = closestPos.getX();
            int targetY = closestPos.getY();
            int diffX = Math.abs(x - targetX);
            int diffY = Math.abs(y - targetY);
            if(diffX > 1 || diffY > 1)
                return;
        }
        player.face(lastTarget);
        setTarget(lastTarget);
    }

    private boolean specialGraniteMaul() {
        int graniteMaulSpecials = this.graniteMaulSpecials;
        if(graniteMaulSpecials == 0)
            return false;
        this.graniteMaulSpecials = 0;

        ItemDefinition wep = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        if(wep == null || !wep.graniteMaul)
            return false;

        int energy = Config.SPECIAL_ENERGY.get(player);
        graniteMaulSpecials = Math.min(graniteMaulSpecials, energy / 500);
        if(graniteMaulSpecials == 0)
            return false;

        AttackStyle style = getAttackStyle();
        AttackType type = getAttackType();
        int maxDamage = CombatUtils.getMaxDamage(player, style, type);
        for(int i = 0; i < graniteMaulSpecials; i++) {
            wep.special.handle(player, target, style, type, maxDamage);
            if (target != null && target.player != null) {
                player.specTeleportDelay.delaySeconds(5);
            }
            energy -= 500;
        }
        Config.SPECIAL_ENERGY.set(player, energy);
        if(specialActive != null)
            deactivateSpecial();
        return true;
    }

    /**
     * Dragonfire shield special attacks
     */



    /**
     * Skulling
     */

    private HashSet<Integer> skullers;

    @Expose public int skullDelay;

    @Expose public boolean highRiskSkull;

    public void skull(Player pTarget) {
        if(player.wildernessLevel == 0 && !player.pvpAttackZone)
            return;
        if(skullers != null && skullers.contains(pTarget.getUserId()) && pTarget.getCombat().isSkulled())
            return;
        if(pTarget.getCombat().skullers == null)
            pTarget.getCombat().skullers = new HashSet<>(5);
        pTarget.getCombat().skullers.add(player.getUserId());
        if(!highRiskSkull)
            skullNormal(3000); // 30 minute skull
    }

    /**
     * Gives the player a skull for the provided amount of time in ticks.
     * @param ticks Duration of skull. 100/minute so 1000 for 10 minutes
     */
    public void skullNormal(int ticks) {
        skullDelay = ticks;
        highRiskSkull = false;
        player.getAppearance().setSkullIcon(KillingSpree.overheadId(player));
    }

    public void skullHighRisk() {
        skullDelay = 0;
        highRiskSkull = true;
        player.getAppearance().setSkullIcon(KillingSpree.overheadId(player));
        if(player.getPrayer().isActive(Prayer.PROTECT_ITEM))
            player.getPrayer().deactivate(Prayer.PROTECT_ITEM);
        player.sendMessage(Color.ORANGE_RED.wrap("Warning:") + " The Protect Item prayer is disabled when marked with a high-risk skull.");
    }

    public void tickSkull() {
        if (!SkullingItem.canSkullDeplete(player))
            return;
        if(!highRiskSkull && skullDelay > 0 && --skullDelay == 0)
            player.getAppearance().setSkullIcon(-1);
    }

    private void resetSkullers() {
        if(skullers != null)
            skullers.clear();
    }

    public void resetSkull() {
        skullDelay = 0;
        highRiskSkull = false;
        if(player.getAppearance().getSkullIcon() != -1)
            player.getAppearance().setSkullIcon(-1);
    }

    public boolean isSkulled() {
        return highRiskSkull || skullDelay > 0;
    }

    /**
     * Teleblock
     */

    @Expose public int tbTicks, tbImmunityTicks;

    public void teleblock() {
        if(player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
            tbTicks = 250;
            player.getPacketSender().sendWidget(Widget.TELEBLOCK, 150);
            player.sendMessage("<col=4f006f>A teleblock spell has been cast on you. It will expire in 2 minutes, 30 seconds.");
        } else {
            tbTicks = 500;
            player.getPacketSender().sendWidget(Widget.TELEBLOCK, 300);
            player.sendMessage("<col=4f006f>A teleblock spell has been cast on you. It will expire in 5 minutes.");
        }
    }

    private void depleteTb() {
        if(tbTicks > 0 && --tbTicks == 0) {
            player.sendMessage("<col=4f006f>The teleblock spell cast on you fades away."); //custom lul
            tbImmunityTicks = 100;
        }
        if(tbImmunityTicks > 0)
            --tbImmunityTicks;
    }

    public void resetTb() {
        tbTicks = 0;
        tbImmunityTicks = 0;
        player.getPacketSender().sendWidget(Widget.TELEBLOCK, 0);
    }

    public boolean checkTb() {
        if(tbTicks == 0)
            return false;
        long ms = Server.tickMs() * tbTicks;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(minutes);
        if(minutes == 0) {
            if(seconds <= 1)
                player.sendMessage("A teleport block has been cast on you. It should wear off in 1 second.");
            else
                player.sendMessage("A teleport block has been cast on you. It should wear off in " + seconds + " seconds.");
        } else if(minutes == 1) {
            if(seconds == 0)
                player.sendMessage("A teleport block has been cast on you. It should wear off in 1 minute.");
            else if(seconds == 1)
                player.sendMessage("A teleport block has been cast on you. It should wear off in 1 minute, 1 second.");
            else
                player.sendMessage("A teleport block has been cast on you. It should wear off in 1 minute, " + seconds + " seconds.");
        } else {
            if(seconds == 0)
                player.sendMessage("A teleport block has been cast on you. It should wear off in " + minutes + " minutes.");
            else if(seconds == 1)
                player.sendMessage("A teleport block has been cast on you. It should wear off in " + minutes + " minutes, 1 second.");
            else
                player.sendMessage("A teleport block has been cast on you. It should wear off in " + minutes + " minutes, " + seconds + " seconds.");
        }
        return true;
    }

    /**
     * Charge
     */

    @Expose public int chargeTicks;

    public boolean charge() {
        if(chargeTicks > 0) {
            player.sendMessage("You can't recast that yet, your current Charge is too strong.");
            return false;
        }
        player.animate(811);
        player.graphics(111, 130, 3);
        chargeTicks = 100;
        return true;
    }

    private void depleteCharge() {
        if(chargeTicks > 0)
            chargeTicks--;
    }

    /**
     * Misc
     */

    @Override
    public double getLevel(StatType stat) {
        return player.getStats().get(stat).currentLevel;
    }

    @Override
    public double getBonus(int bonusType) {
        int bonus = player.getEquipment().bonuses[bonusType];
        // Moved magic e void handling here so bonus is additive
        if (bonusType == EquipmentStats.MAGIC_DAMAGE && SetEffect.ELITE_VOID_MAGE.hasPieces(player)) {
            bonus += 5;
        }
        if (target != null && target.isPlayer()) return bonus;
        if (player.getRelicManager().hasRelicEnalbed(Relic.JUGGERNAUT) && bonusType >= 5 && bonusType <= 9) bonus = (int) (bonus * 1.25);
        return bonus;
    }

    @Override
    public double getDragonfireResistance() {
        int shieldId = player.getEquipment().getId(Equipment.SLOT_SHIELD);
        /**
         * Dragon fire
         */
        double absorbDamage = 0.0;
        if (player.isTickEventActive(TickEventType.SUPER_ANTIFIRE))
            absorbDamage += 1.0;
        else if(player.isTickEventActive(TickEventType.ANTIFIRE))
            absorbDamage += 0.8;

        if(player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC))
            absorbDamage += 0.2;

        if(shieldId == 1540) { // antifire shield
            absorbDamage += 0.8;
        } else if(shieldId == 11283 || shieldId == 11284 || shieldId == 21634 || shieldId == 21633 || // dragonfire shield
                shieldId == 22003 || shieldId == 22002) {
            absorbDamage += 0.8;
        }
        if ((target == null || target.isNpc()) && player.getRelicManager().hasRelicEnalbed(Relic.JUGGERNAUT)) {
            absorbDamage += 1.0;
        }
        return absorbDamage;
    }

    public int getIcyBreathResistance() {
        int shieldId = player.getEquipment().getId(Equipment.SLOT_SHIELD);
        int protection = 0;
        if (shieldId == 11283 || shieldId == 11284 || // dragonfire shield
                shieldId == 22003 || shieldId == 22002  // dragonfire ward
                || shieldId == 2890 || shieldId == 9731) {  // elemental/mind shield
            protection = 1;
        } else if (shieldId == 21634 || shieldId == 21633) {
            protection = 2;
        }
        if (player.getRelicManager().hasRelicEnalbed(Relic.JUGGERNAUT)) {
            protection = 2;
        }
        return protection;
    }

    @Override
    public void faceTarget() {
        player.face(target);
    }

    public Special specialActive() {
        return specialActive;
    }

    public boolean hasSpecialActive() {
        return specialActive != null;
    }

    private static void checkJuggernautThorns(Player player, Hit hit) {
        if (!player.getRelicManager().hasRelicEnalbed(Relic.JUGGERNAUT))
            return;
        if(hit.attacker == null || hit.attackStyle == null)
            return;
        int damage = (int) Math.max(Math.ceil(hit.damage * 0.10), 1);
        if(damage == 0)
            return;
        hit.attacker.hit(new Hit().fixedDamage(damage));
    }

    private void aoeAttack(int maxDamage, int delay, int targetCap) {
        if (!target.inMulti()) return;
        Item wep = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (wep == null) {
            /* obviously should never happen */
            return;
        }
        ItemDefinition wepDef = wep.getDef();
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int entityIndex = player.getClientIndex();
        int targetIndex = target.getClientIndex();
        int targetCount = 0;
        for (Player plr : target.localPlayers()) {
            int playerIndex = plr.getClientIndex();
            if (playerIndex == entityIndex || playerIndex == targetIndex)
                continue;
            if (!plr.getPosition().isWithinDistance(target.getPosition(), 1))
                continue;
            if (!player.getCombat().canAttack(plr, false))
                continue;
            plr.hit(new Hit(player, style, type).randDamage(maxDamage).clientDelay(delay).setAttackWeapon(wepDef).keepCharges());
            if (++targetCount >= targetCap)
                break;
        }
        for (NPC npc : target.localNpcs()) {
            int npcIndex = npc.getClientIndex();
            if (npcIndex == entityIndex || npcIndex == targetIndex)
                continue;
            if (npc instanceof Resurrection.Thrall)
                continue;
            if (targetCap == 5 && !npc.getPosition().isWithinDistance(target.getPosition(), 3)) {
                continue;
            } else if (targetCap != 5 && !npc.getPosition().isWithinDistance(target.getPosition(), 1)) {
                continue;
            }
            if (npc.getDef().ignoreMultiCheck)
                continue;
            if (!player.getCombat().canAttack(npc, false))
                continue;
            npc.hit(new Hit(player, style, type).randDamage(maxDamage).clientDelay(delay).setAttackWeapon(wepDef).keepCharges());
            if (++targetCount >= targetCap)
                break;
        }
    }
}
