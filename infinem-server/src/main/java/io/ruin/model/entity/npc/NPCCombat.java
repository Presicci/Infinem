package io.ruin.model.entity.npc;

import io.ruin.api.utils.Random;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.World;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.combat.*;
import io.ruin.model.content.bestiary.perks.impl.RespawnPerk;
import io.ruin.model.entity.Entity;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.behavior.FightingNPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.MonkeyGreeGree;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.map.*;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class NPCCombat extends Combat {

    protected NPC npc;

    protected npc_combat.Info info;

    protected Stat[] stats;

    private int[] bonuses;

    private boolean allowRespawn = true;

    @Setter @Getter
    private boolean allowRetaliate = true;

    public final NPCCombat init(NPC npc, npc_combat.Info info) {
        this.npc = npc;
        this.info = info;
        this.stats = new Stat[] {
                new Stat(info.attack),      //0
                new Stat(info.defence),     //1
                new Stat(info.strength),    //2
                new Stat(info.hitpoints),   //3
                new Stat(info.ranged),      //4
                new Stat(1),           //5 (prayer, not used)
                new Stat(info.magic)        //6
        };
        this.bonuses = new int[] {
                /*
                 * Attack bonuses
                */
                info.stab_attack,
                info.slash_attack,
                info.crush_attack,
                info.magic_attack,
                info.ranged_attack,
                /*
                 * Defence bonuses
                 */
                info.stab_defence,
                info.slash_defence,
                info.crush_defence,
                info.magic_defence,
                info.ranged_defence,
                info.light_range_defence,
                info.heavy_range_defence,
        };
        if (info.poison > 0)
            setPoison(info.poison);
        init();
        return this;
    }

    public void updateInfo(npc_combat.Info newInfo) { // only used when transforming!
        info = newInfo;
        if (stats[0].fixedLevel != newInfo.attack) stats[0] = new Stat(newInfo.attack);
        if (stats[1].fixedLevel != newInfo.defence) stats[1] = new Stat(newInfo.defence);
        if (stats[2].fixedLevel != newInfo.strength) stats[2] = new Stat(newInfo.strength);
        if (stats[3].fixedLevel != newInfo.hitpoints) stats[3] = new Stat(newInfo.hitpoints);
        if (stats[4].fixedLevel != newInfo.ranged) stats[4] = new Stat(newInfo.ranged);
        if (stats[6].fixedLevel != newInfo.magic) stats[6] = new Stat(newInfo.magic);

        this.bonuses = new int[] { // bonuses cannot be changed so we can just set to the new ones
                /*
                 * Attack bonuses
                */
                info.stab_attack,
                info.slash_attack,
                info.crush_attack,
                info.magic_attack,
                info.ranged_attack,
                /*
                 * Defence bonuses
                */
                info.stab_defence,
                info.slash_defence,
                info.crush_defence,
                info.magic_defence,
                info.ranged_defence,
                info.light_range_defence,
                info.heavy_range_defence,
        };
    }

    /**
     * Following
     */
    public final void follow0() {
        checkAggression();
        if(target == null || npc.isLocked()) //why can an npc be locked but still have a target.. hmm..
            return;
        if(!canAttack(target)) {
            reset();
            return;
        }
        follow();
    }

    protected void follow(int distance) {
        DumbRoute.step(npc, target, distance);
    }

    protected boolean withinDistance(int distance) {
        return DumbRoute.withinDistance(npc, target, distance);
    }

    /**
     * Attacking
     */
    public final void attack0() {
        if(target == null || hasAttackDelay() || npc.isLocked() || !attack())
            return;
        updateLastAttack(info.attack_ticks);
    }

    public boolean canAttack(Entity target) {
        if(isDead() || npc.isRemoved())
            return false;
        if(target == null || target.isHidden())
            return false;
        if(target.getCombat() == null)
            return false;
        if(target.getCombat().isDead())
            return false;
        if (!multiCheck(target))
            return false;
        if(npc.targetPlayer == null) {
            if(!npc.getPosition().isWithinDistance(target.getPosition()))
                return false;
            Bounds attackBounds = npc.attackBounds;
            if(attackBounds != null && !npc.getPosition().inBounds(attackBounds)) {
                DumbRoute.route(npc, npc.spawnPosition.getX(), npc.spawnPosition.getY());
                int crabId = npc.getTemporaryAttributeIntOrZero(AttributeKey.CRAB_TRANSFORM);
                if (crabId > 0 && npc.getId() != (crabId + 1)) {
                    npc.transform(crabId + 1);
                }
                //possibly consider resetting the monster to prevent abusing this mechanic
                return false;
            }
        }
        return true;
    }

    public boolean multiCheck(Entity target) {
        Player player = World.getPlayer(npc.ownerId, true);
        if (player != null)
            return true;
        return npc.inMulti() || target.getCombat().allowPj(npc) || singlePlusCheck(target);
    }

    public boolean singlePlusCheck(Entity target) {
        Player player = World.getPlayer(npc.ownerId, true);
        if (player != null)
            return true;
        return npc.inSinglePlus() && (target.getCombat().lastAttacker != null && !target.getCombat().lastAttacker.isPlayer());
    }

    protected Hit basicAttack() {
        return basicAttack(info.attack_animation, info.attack_style, info.max_damage);
    }

    protected Hit basicAttack(int animation, AttackStyle attackStyle, int maxDamage) {
        npc.animate(animation);
        Hit hit = new Hit(npc, attackStyle, null).randDamage(maxDamage);
        if (npc.hasTemporaryAttribute("POISON")) {
            hit.postDefend((entity) -> {    // 25% chance on successful attack to poison
                if (entity.player != null && !hit.isBlocked() && hit.damage > 0 && Random.rollDie(4)) {
                    entity.player.poison(npc.getTemporaryAttributeIntOrZero("POISON"));
                }
            });
        }
        if (info.typeless)
            hit.ignorePrayer();
        target.hit(hit);
        int hitSound = info.attack_sound;
        if (hitSound > 0) {
            target.privateSound(hitSound);
        }
        return hit;
    }

    protected Hit projectileAttack(Projectile projectile, int animation, AttackStyle attackStyle, int maxDamage) {
        return projectileAttack(projectile, animation, Graphic.builder().id(-1).build(), attackStyle, maxDamage);
    }

    protected Hit projectileAttack(Projectile projectile, int animation, Graphic hitgfx, AttackStyle attackStyle, int maxDamage) {
        return projectileAttack(projectile, animation, hitgfx, Graphic.builder().id(-1).build(), attackStyle, maxDamage, false);
    }

    protected Hit projectileAttack(Projectile projectile, int animation, Graphic hitgfx, Graphic splashgfx, AttackStyle attackStyle, int maxDamage, boolean ignorePrayer) {
        if (animation != -1)
            npc.animate(animation);
        int delay = projectile.send(npc, target);
        Hit hit = new Hit(npc, attackStyle, null).randDamage(maxDamage).clientDelay(delay);
        if (npc.hasTemporaryAttribute("POISON")) {
            hit.postDefend((entity) -> {    // 25% chance on successful attack to poison
                if (entity.player != null && !hit.isBlocked() && Random.rollDie(4)) {
                    entity.player.poison(npc.getTemporaryAttributeIntOrZero("POISON"));
                }
            });
        }
        if (info.typeless)
            hit.ignorePrayer();
        hit.afterPostDamage(e -> {
            boolean splash = hit.isBlocked();
            if (target != null) {
                target.graphics(
                        splash ? splashgfx.getId() : hitgfx.getId(),
                        splash ? splashgfx.getHeight() : hitgfx.getHeight(),
                        splash ? splashgfx.getDelay() : hitgfx.getDelay()
                );
                if (splash ? splashgfx.getSoundId() != -1 : hitgfx.getSoundId() != -1) {
                    target.publicSound(
                            splash ? splashgfx.getSoundId() : hitgfx.getSoundId(),
                            splash ? splashgfx.getSoundType() : hitgfx.getSoundType(),
                            splash ? splashgfx.getSoundDelay() : hitgfx.getSoundDelay()
                    );
                }
            }
        });

        target.hit(hit);
        return hit;
    }

    /**
     * Reset
     */
    @Override
    public void reset() {
        super.reset();
        npc.faceNone(!isDead());
        TargetRoute.reset(npc);
    }

    /**
     * Death
     */
    @Override
    public void startDeath(Hit killHit) {
        setDead(true);

        if(target != null)
            reset();
        Killer killer = getKiller();
        if(npc.deathStartListener != null) {
            npc.deathStartListener.handle(npc, killer, killHit);
            if(npc.isRemoved())
                return;
        }
        npc.startEvent(event -> {
            npc.lock();
            event.delay(1);
            if (info.death_animation != -1)
                npc.animate(info.death_animation);
            if (info.death_sound != -1)
                npc.publicSound(info.death_sound, 1, 0, 3);
            if(info.death_ticks > 0)
                event.delay(info.death_ticks);
            dropItems(killer);

            if (npc.inMulti()) {
                for (Killer k : killers.values()) {
                    if (k.player != null && k != killer) k.player.getBestiary().incrementKillCount(npc.getDef());
                }
            }
            if (killer != null && killer.player != null) {
                killer.player.getBestiary().incrementKillCount(npc.getDef());
                killer.player.getTaskManager().doKillLookup(npc.getId());
                Slayer.handleNPCKilled(killer.player, npc);
                if (npc.getDef().killCounterType != null)
                    npc.getDef().killCounterType.increment(killer.player);
                if (info.pet != null) {
                    int dropAverage = info.pet.dropAverage;
                    if (npc.getId() == 6619) {  // Manual override for chaos fanatic sharing boss pet w/ chaos ele
                        dropAverage = 1000;
                    }
                    int threshold = info.pet.dropThreshold;
                    int numerator = npc.getDef().killCounterType != null ? (npc.getDef().killCounterType.getKills(killer.player) / threshold) + 1 : 1;
                    if (Random.rollDie(dropAverage, numerator)) {
                        info.pet.unlock(killer.player);
                    }
                }
            }

            if(npc.deathEndListener != null) {
                npc.deathEndListener.handle(npc, killer, killHit);
                if(npc.isRemoved())
                    return;
            } else if(info.respawn_ticks < 0) {
                npc.remove();
                return;
            }

            if (info.respawn_ticks > 0)
                npc.setHidden(true);
            if (!allowRespawn())
                return;

            int respawnTicks = info.respawn_ticks;
            if (killer != null && killer.player != null) {
                double multiplier = killer.player.getBestiary().getBestiaryEntry(npc.getDef()).getPerkMultiplier(RespawnPerk.class, 1);
                respawnTicks *= multiplier;
            }
            event.delay(respawnTicks);
            respawn();
        });
    }

    public final void respawn() {
        npc.animate(info.spawn_animation);
        npc.getPosition().copy(npc.spawnPosition);
        resetKillers();
        restore();
        setDead(false);
        npc.setHidden(false);
        if(npc.respawnListener != null)
            npc.respawnListener.onRespawn(npc);
        npc.unlock();
    }

    public void setAllowRespawn(boolean allowRespawn) {
        this.allowRespawn = allowRespawn;
    }

    public boolean allowRespawn() {
        return allowRespawn;
    }

    public void dropItems(Killer killer) {
        new NPCDrops(this).dropItems(killer);
    }

    public Position getDropPosition() {
        return npc.getPosition();
    }

    public void restore() {
        for(Stat stat : stats)
            stat.restore();
        npc.resetFreeze();
        npc.cureVenom(0, 0);
    }

    /**
     * Misc
     */
    @Override
    public boolean allowRetaliate(Entity attacker) {
        if(npc.targetPlayer != null && attacker != npc.targetPlayer) //npc has a designated target
            return false;
        if (npc.isLocked())
            return false;
        if (!allowRetaliate)
            return false;
        if(target != null && target.getCombat().getTarget() == npc) { //npc is being attacked by target
            boolean prioritizePlayer = target.npc != null && attacker.player != null; //target is an npc and attacker is a player
            if(!prioritizePlayer)
                return false;
        }
        return true;
    }

    @Override
    public AttackStyle getAttackStyle() {
        return info.attack_style;
    }

    @Override
    public AttackType getAttackType() {
        return null;
    }

    @Override
    public double getLevel(StatType statType) {
        int i = statType.ordinal();
        return i >= stats.length ? 0 : stats[i].currentLevel;
    }

    public Stat getStat(StatType statType) {
        return stats[statType.ordinal()];
    }

    public int getHitpointsAsPercentage() {
        int currentHealth = stats[StatType.Hitpoints.ordinal()].currentLevel;
        int maxHealth = stats[StatType.Hitpoints.ordinal()].fixedLevel;

        if (maxHealth == 0) {
            throw new IllegalArgumentException("Fixed level should not be zero to avoid division by zero.");
        }

        return (int) (((double) currentHealth / maxHealth) * 100);
    }

    @Override
    public double getBonus(int bonusType) {
        return bonusType >= bonuses.length ? 0 : bonuses[bonusType];
    }

    @Override
    public Killer getKiller() {
        if(npc.targetPlayer != null) {
            Killer killer = new Killer();
            killer.player = npc.targetPlayer;
            return killer;
        }
        //Player's didn't like this mechanic so we removed it.
       /* if (killers.entrySet().stream().filter(e -> e.getValue().player != null).anyMatch(e -> e.getValue().player.getGameMode().isIronMan()) // ironman did damage
                && killers.entrySet().stream().filter(e -> e.getValue().player != null).anyMatch(e -> !e.getValue().player.getGameMode().isIronMan())) { // but so did a non-ironman :(
            killers.entrySet().removeIf(e -> e.getValue().player != null && e.getValue().player.getGameMode().isIronMan()); // remove all iron men from potential killer list
        }*/
        return super.getKiller();
    }

    @Override
    public int getDefendAnimation() {
        return info.defend_animation;
    }

    @Override
    public int getDefendSound() {
        return info.defend_sound;
    }

    public int getMaxDamage() {
        return info.max_damage;
    }

    public npc_combat.Info getInfo() {
        return info;
    }

    @Override
    public double getDragonfireResistance() {
        return 0;
    }

    public final void checkAggression() {
        if (target == null && !npc.isLocked()) {
            target = findAggressionTarget();
            if (target != null) {
                int crabId = npc.getTemporaryAttributeIntOrZero(AttributeKey.CRAB_TRANSFORM);
                if (crabId > 0 && npc.getId() != crabId) {
                    npc.transform(crabId);
                }
                faceTarget();
            }
        }
    }

    protected Entity findAggressionTarget() {
        if (npc.localPlayers().isEmpty())
            return null;
        if (npc.hasTarget())
            return null;
        if (!isAggressive()) {
            return findAggressionTargetNPC();
        }
        List<Player> targets = npc.localPlayers().stream()
                .filter(this::canAggro)
                .collect(Collectors.toList()); // i don't mind if this is done in a different way as long as it picks a RANDOM target that passes the canAggro check
        if (targets.isEmpty())
            return findAggressionTargetNPC();
        return Random.get(targets);
    }

    protected Entity findAggressionTargetNPC() {
        Set<Integer> t;
        if (!npc.hasTemporaryAttribute("NPC_TARGETS")) {
            Set<Integer> targets = FightingNPC.getTargets(npc.getId());
            npc.putTemporaryAttribute("NPC_TARGETS", targets);
            t = targets;
        } else {
            t = npc.getTemporaryAttribute("NPC_TARGETS");
        }
        if (t == null)
            return null;
        List<Entity> targets = npc.localNpcs().stream()
                .filter(n -> t.contains(n.getId()))
                .filter(this::canAggro)
                .collect(Collectors.toList());
        if (targets.isEmpty())
            return null;
        return Random.get(targets);
    }

    protected int getAggressiveLevel() {
        if (npc.wildernessSpawnLevel > 0)
            return Integer.MAX_VALUE;
        else if (info.aggressive_level == -1)
            return npc.getDef().combatLevel * 2;
        else
            return info.aggressive_level;
    }

    public boolean isAggressive() {
        return getInfo().aggressive_level != 0;
    }

    protected boolean canAggro(Player player) {
        int aggroLevel = getAggressiveLevel();
        if (npc.getDef().name.toLowerCase().contains("monkey") && MonkeyGreeGree.isMonkey(player)) return false;
        return player.getCombat().getLevel() <= aggroLevel // in level range
                && playerAggroExtraCheck(player)
                && canAttack(player) // can attack
                && !player.isIdle // player isn't idle
                && (player.inMulti() || !player.getCombat().isDefending(12))
                && DumbRoute.withinDistance(npc, player, getAggressionRange()) // distance and line of sight
                && (npc.inMulti() || (StreamSupport.stream(npc.localNpcs().spliterator(), false)
                .noneMatch(n -> n.getCombat() != null && n.getCombat().getTarget() == player && !n.getCombat().isAttacking(10) && !n.getMovement().isAtDestination()))) // not 100% sure how i feel about this check, but it ensures multiple npcs don't try to go for the same player at the same time in a single-way zone since they wouldn't be able to attack upon reaching
                && (npc.aggressionImmunity == null || !npc.aggressionImmunity.test(player));
    }

    protected boolean playerAggroExtraCheck(Player player) {
        return true;
    }

    protected boolean canAggro(NPC n) {
        return canAttack(n) // can attack
                && DumbRoute.withinDistance(npc, n, getAggressionRange()) // distance and line of sight
                && (npc.aggressionImmunity == null || !npc.aggressionImmunity.test(n));

    }

    public int getAggressionRange() {
        String name = npc.getDef().name;
        if (name.equalsIgnoreCase("monkey archer") || name.equalsIgnoreCase("thrower troll"))
            return 8;
        return npc.wildernessSpawnLevel > 0 ? 2 : npc.hasTemporaryAttribute(AttributeKey.CRAB_TRANSFORM) ? 2 : 4;
    }

    public int getAttackBoundsRange() {
        return 12;
    }

    @Override
    public void faceTarget() {
        npc.face(target);
    }

    protected void setPoison(int poison) {
        npc.putTemporaryAttribute("POISON", poison);
    }

    /**
     * Handler functions
     */
    public abstract void init();

    public abstract void follow();

    public abstract boolean attack();

    public int getRandomDropCount() {
        return info.random_drop_count;
    }
}