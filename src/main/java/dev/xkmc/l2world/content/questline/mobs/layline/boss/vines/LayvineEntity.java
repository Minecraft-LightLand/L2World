package dev.xkmc.l2world.content.questline.mobs.layline.boss.vines;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2world.content.questline.mobs.layline.boss.goals.LayguardAttackPlayerGoal;
import dev.xkmc.l2world.content.questline.mobs.layline.boss.goals.LayguardBodyRotationControl;
import dev.xkmc.l2world.content.questline.mobs.layline.boss.goals.LayguardFindTargetGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@SerialClass
public class LayvineEntity extends AbstractGolem {

	@SerialClass.SerialField
	public final LayvineFSM fsm = new LayvineFSM(this);

	protected LayvineEntity(EntityType<? extends AbstractGolem> type, Level level) {
		super(type, level);
	}

	// ------ fsm ------

	public void tick() {
		super.tick();
		fsm.tick();
	}

	@Override
	public void handleEntityEvent(byte event) {
		if (event >= 100) {
			fsm.handleSignal(event - 100);
		}
		super.handleEntityEvent(event);
	}

	@Override
	protected void tickDeath() {
		this.deathTime++;
		if (this.fsm.getState() == LayvineState.DEAD && !this.level.isClientSide()) {
			this.level.broadcastEntityEvent(this, (byte) 60);
			this.remove(RemovalReason.KILLED);
		}
	}

	// ------ init ------

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F, 0.02F, true));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(2, new LayguardAttackPlayerGoal(this));
		this.targetSelector.addGoal(3, new LayguardFindTargetGoal(this));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
	}

	// ------ data ------

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
	}

	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
	}

	// ------ no motion ------

	protected BodyRotationControl createBodyControl() {
		return new LayguardBodyRotationControl(this);
	}

	public boolean startRiding(Entity p_149773_, boolean p_149774_) {
		return false;
	}

	public Vec3 getDeltaMovement() {
		return Vec3.ZERO;
	}

	public void setDeltaMovement(Vec3 p_149804_) {
	}

	// ------ client information ------

	protected float getStandingEyeHeight(Pose pose, EntityDimensions dim) {
		return 0.5F;
	}

	public int getMaxHeadXRot() {
		return 180;
	}

	public int getMaxHeadYRot() {
		return 180;
	}

	protected MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.SILVERFISH_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.CREEPER_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource p_33457_) {
		return SoundEvents.CREEPER_HURT;
	}

}
