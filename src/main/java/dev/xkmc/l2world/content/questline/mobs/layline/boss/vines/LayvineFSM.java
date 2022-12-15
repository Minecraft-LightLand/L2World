package dev.xkmc.l2world.content.questline.mobs.layline.boss.vines;

import dev.xkmc.l2world.content.questline.common.fsm.FSM;

public class LayvineFSM extends FSM<LayvineFSM, LayvineState, LayvineState> {

	public LayvineFSM(LayvineEntity entity) {
		super(LayvineState.values(), LayvineState.values(), LayvineState.IDLE);
	}

}
