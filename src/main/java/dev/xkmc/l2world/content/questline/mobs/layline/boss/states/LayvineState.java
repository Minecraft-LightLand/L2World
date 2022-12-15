package dev.xkmc.l2world.content.questline.mobs.layline.boss.states;

import dev.xkmc.l2world.content.questline.common.fsm.ISignal;
import dev.xkmc.l2world.content.questline.common.fsm.IState;
import dev.xkmc.l2world.content.questline.common.fsm.StateSignal;

public enum LayvineState implements IState<LayvineFSM, LayvineState, LayvineState>, ISignal<LayvineFSM, LayvineState, LayvineState> {
	SPAWN(LayvineState::stateSpawn),
	IDLE(LayvineState::stateIdle),
	ATTACK(LayvineState::stateAttack),
	DYING(LayvineState::stateDying),
	DEAD(LayvineState::stateDead);

	private static LayvineState stateSpawn(LayvineFSM fsm, StateSignal sig) {
		return fsm.getTick() < LayguardConstants.VINE_ANIM_SPAWN ? SPAWN : IDLE;
	}

	private static LayvineState stateIdle(LayvineFSM fsm, StateSignal sig) {
		if (sig == StateSignal.TICK) {
			if (fsm.battleCD.getTick() == 0 && fsm.hasValidTarget()) {
				return fsm.sendSignal(ATTACK);
			}
		}
		return IDLE;
	}

	private static LayvineState stateAttack(LayvineFSM fsm, StateSignal sig) {
		if (sig == StateSignal.TICK) {
			if (fsm.getTick() == LayguardConstants.VINE_ATTACK_ACTION) {
				fsm.attack();
			}
		}
		return fsm.getTick() < LayguardConstants.VINE_ANIM_ATTACK ? ATTACK : IDLE;
	}

	private static LayvineState stateDying(LayvineFSM fsm, StateSignal sig) {
		return fsm.getTick() < LayguardConstants.VINE_ANIM_DYING ? DYING : DEAD;
	}

	private static LayvineState stateDead(LayvineFSM fsm, StateSignal sig) {
		return DEAD;
	}

	private final IState<LayvineFSM, LayvineState, LayvineState> func;

	LayvineState(IState<LayvineFSM, LayvineState, LayvineState> func) {
		this.func = func;
	}

	@Override
	public LayvineState onSignal(LayvineFSM fsm, StateSignal sig) {
		return func.onSignal(fsm, sig);
	}

	@Override
	public LayvineState handle(LayvineFSM fsm) {
		return this;
	}

}
