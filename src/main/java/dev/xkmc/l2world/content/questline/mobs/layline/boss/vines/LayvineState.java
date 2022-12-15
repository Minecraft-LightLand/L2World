package dev.xkmc.l2world.content.questline.mobs.layline.boss.vines;

import dev.xkmc.l2world.content.questline.common.fsm.ISignal;
import dev.xkmc.l2world.content.questline.common.fsm.IState;
import dev.xkmc.l2world.content.questline.common.fsm.StateSignal;

public enum LayvineState implements IState<LayvineFSM, LayvineState, LayvineState>, ISignal<LayvineFSM, LayvineState, LayvineState> {
	IDLE, DYING, DEAD;

	@Override
	public LayvineState handle(LayvineFSM fsm) {
		return this;
	}

	@Override
	public LayvineState onSignal(LayvineFSM fsm, StateSignal sig) {
		return this;
	}
}
