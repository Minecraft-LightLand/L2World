package dev.xkmc.l2world.content.questline.mobs.layline.boss.states;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2world.content.questline.common.fsm.EntityFSM;
import dev.xkmc.l2world.content.questline.common.fsm.Ticker;
import dev.xkmc.l2world.content.questline.mobs.layline.boss.vines.LayvineEntity;
import net.minecraft.world.entity.LivingEntity;

@SerialClass
public class LayvineFSM extends EntityFSM<LayvineEntity, LayvineFSM, LayvineState, LayvineState> {

	@SerialClass.SerialField
	public final Ticker battleCD = new Ticker(false);

	public LayvineFSM(LayvineEntity entity) {
		super(LayvineState.values(), LayvineState.values(), LayvineState.SPAWN,
				LayvineState.DYING, LayvineState.DEAD, entity);
		addTicker(battleCD);
	}

	public void attack() {

	}

	public boolean hasValidTarget() {
		LivingEntity target = entity.getTarget();
		if (target == null) return false;
		return entity.distanceTo(target) < LayguardConstants.VINE_ATTACK_REACH;
	}
}
