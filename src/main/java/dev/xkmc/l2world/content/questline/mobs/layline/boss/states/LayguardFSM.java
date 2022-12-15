package dev.xkmc.l2world.content.questline.mobs.layline.boss.states;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2world.content.questline.common.fsm.EntityFSM;
import dev.xkmc.l2world.content.questline.common.fsm.Ticker;
import dev.xkmc.l2world.content.questline.mobs.layline.boss.flower.LayguardEntity;
import net.minecraft.world.entity.LivingEntity;

@SerialClass
public class LayguardFSM extends EntityFSM<LayguardEntity, LayguardFSM, LayguardState, LayguardState> {

	@SerialClass.SerialField
	public final Ticker battleCD = new Ticker(false);
	@SerialClass.SerialField
	public final Ticker summonCD = new Ticker(false);

	public LayguardFSM(LayguardEntity entity) {
		super(LayguardState.values(), LayguardState.values(), LayguardState.IDLE,
				LayguardState.DYING, LayguardState.DEAD, entity);
		addTicker(battleCD);
		addTicker(summonCD);
	}

	protected boolean canSummon() {
		return summonCD.getTick() == 0;
	}

	protected boolean hasValidTarget() {
		LivingEntity target = entity.getTarget();
		return target != null && entity.distanceTo(target) < 16;
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
