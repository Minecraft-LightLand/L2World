package dev.xkmc.l2world.content.questline.mobs.layline.boss.flower_states;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2world.content.questline.common.fsm.FSM;
import dev.xkmc.l2world.content.questline.mobs.layline.boss.flower.LayguardEntity;
import net.minecraft.world.entity.LivingEntity;

@SerialClass
public class LayguardFSM extends FSM<LayguardFSM, LayguardState, LayguardState> {

	public final LayguardEntity boss;

	@SerialClass.SerialField
	public int battleCooldown;
	@SerialClass.SerialField
	public int summonCooldown;

	public LayguardFSM(LayguardEntity boss) {
		super(LayguardState.values(), LayguardState.values(), LayguardState.IDLE);
		this.boss = boss;
	}

	@Override
	public LayguardState sendSignal(LayguardState signal) {
		if (!boss.level.isClientSide()) {
			boss.level.broadcastEntityEvent(boss, (byte) (100 + signal.ordinal()));
		}
		return boss.level.isClientSide() ? getState() : super.sendSignal(signal);
	}

	@Override
	public void tick() {
		if (battleCooldown > 0) {
			battleCooldown--;
		}
		if (summonCooldown > 0) {
			summonCooldown--;
		}
		if (boss.isDeadOrDying() && getState() != LayguardState.DEAD) {
			transition(LayguardState.DYING.ordinal());
		}
		super.tick();
	}

	protected boolean canSummon() {
		return summonCooldown == 0;
	}

	protected boolean hasValidTarget() {
		LivingEntity target = boss.getTarget();
		return target != null && boss.distanceTo(target) < 16;
	}

	public void tickSummon() {
		//TODO
	}

	public void attackFar() {
		//TODO
	}

	public void attackNear() {
		//TODO
	}

}
