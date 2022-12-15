package dev.xkmc.l2world.content.questline.mobs.layline.boss.states;

import dev.xkmc.l2world.content.questline.common.fsm.ISignal;
import dev.xkmc.l2world.content.questline.common.fsm.IState;
import dev.xkmc.l2world.content.questline.common.fsm.StateSignal;
import net.minecraft.world.entity.LivingEntity;

public enum LayguardState implements IState<LayguardFSM, LayguardState, LayguardState>, ISignal<LayguardFSM, LayguardState, LayguardState> {
	IDLE(LayguardState::stateIdle),
	BATTLE_WAIT(LayguardState::stateBattleWait),
	BATTLE_FIND(LayguardState::stateBattleFind),
	OPENING(LayguardState::stateOpening),
	ATTACK_FAR(LayguardState::stateAttackFar),
	ATTACK_NEAR(LayguardState::stateAttackNear),
	SUMMON_START(LayguardState::stateSummonStart),
	SUMMON_WAIT(LayguardState::stateSummonWait),
	SUMMON_END(LayguardState::stateSummonEnd),
	HIDE_START(LayguardState::stateHideStart),
	HIDE_WAIT(LayguardState::stateHideWait),
	HIDE_END(LayguardState::stateHideEnd),
	RELEASE(LayguardState::stateRelease),
	DYING(LayguardState::stateDying),
	DEAD(LayguardState::stateDead);

	private static final int ATTACK_DISTANCE_THRESHOLD = 5;
	private static final int BATTLE_FIND_TIMEOUT = 100;
	private static final int HIDE_WAIT_TIMEOUT = 40;
	private static final int ATTACK_COOLDOWN_ENTER = 40;
	private static final int ATTACK_COOLDOWN_POST = 100;
	private static final int ATTACK_FAR_ACTION = 5;
	private static final int ATTACK_NEAR_ACTION = 10;

	private static final int ANIM_OPENING = 20;
	private static final int ANIM_ATTACK_FAR = 20;
	private static final int ANIM_ATTACK_NEAR = 20;
	private static final int ANIM_SUMMON_START = 20;
	private static final int ANIM_SUMMON_END = 20;
	private static final int ANIM_HIDE_START = 20;
	private static final int ANIM_HIDE_END = 20;
	private static final int ANIM_RELEASE = 20;
	private static final int ANIM_DYING = 20;

	private static LayguardState stateIdle(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.TICK) {
			if (fsm.hasValidTarget()) {
				return fsm.sendSignal(OPENING);
			}
		}
		return IDLE;
	}

	private static LayguardState stateBattleWait(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.TICK) {
			if (!fsm.hasValidTarget()) {
				return BATTLE_FIND;
			}
			if (fsm.canSummon()) {
				return fsm.sendSignal(SUMMON_START);
			}
			LivingEntity target = fsm.boss.getTarget();
			if (target != null && fsm.battleCooldown == 0) {
				if (fsm.boss.distanceTo(target) > ATTACK_DISTANCE_THRESHOLD) {
					return fsm.sendSignal(ATTACK_FAR);
				} else {
					return fsm.sendSignal(ATTACK_NEAR);
				}
			}
		}
		return BATTLE_WAIT;
	}

	private static LayguardState stateBattleFind(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.TICK) {
			if (fsm.tick >= BATTLE_FIND_TIMEOUT) {
				return fsm.sendSignal(RELEASE);
			}
			if (fsm.hasValidTarget()) {
				return BATTLE_WAIT;
			}
		}
		return BATTLE_FIND;
	}

	private static LayguardState stateOpening(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.EXIT) {
			fsm.battleCooldown = ATTACK_COOLDOWN_ENTER;
		}
		return fsm.tick < ANIM_OPENING ? OPENING : BATTLE_WAIT;
	}

	private static LayguardState stateAttackFar(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.EXIT) {
			fsm.battleCooldown = ATTACK_COOLDOWN_POST;
		}
		if (sig == StateSignal.TICK){
			if (fsm.tick == ATTACK_FAR_ACTION){
				fsm.attackFar();
			}
		}
		return fsm.tick < ANIM_ATTACK_FAR ? ATTACK_FAR : BATTLE_WAIT;
	}

	private static LayguardState stateAttackNear(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.EXIT) {
			fsm.battleCooldown = ATTACK_COOLDOWN_POST;
		}
		if (sig == StateSignal.TICK){
			if (fsm.tick == ATTACK_NEAR_ACTION){
				fsm.attackNear();
			}
		}
		return fsm.tick < ANIM_ATTACK_NEAR ? ATTACK_NEAR : BATTLE_WAIT;
	}

	private static LayguardState stateSummonStart(LayguardFSM fsm, StateSignal sig) {
		return fsm.tick < ANIM_SUMMON_START ? SUMMON_START : SUMMON_WAIT;
	}

	private static LayguardState stateSummonWait(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.TICK) {
			if (!fsm.canSummon()) {
				return fsm.sendSignal(SUMMON_END);
			} else {
				fsm.tickSummon();
			}
		}
		return SUMMON_WAIT;
	}

	private static LayguardState stateSummonEnd(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.EXIT) {
			fsm.battleCooldown = ATTACK_COOLDOWN_ENTER;
		}
		return fsm.tick < ANIM_SUMMON_END ? SUMMON_END : BATTLE_WAIT;
	}

	private static LayguardState stateHideStart(LayguardFSM fsm, StateSignal sig) {
		return fsm.tick < ANIM_HIDE_START ? HIDE_START : HIDE_WAIT;
	}

	private static LayguardState stateHideWait(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.TICK) {
			if (fsm.boss.hurtTime != 0) {
				fsm.tick = 0;
			}
			if (fsm.tick >= HIDE_WAIT_TIMEOUT || fsm.hasValidTarget()) {
				return HIDE_END;
			}
		}
		return HIDE_WAIT;
	}

	private static LayguardState stateHideEnd(LayguardFSM fsm, StateSignal sig) {
		return fsm.tick < ANIM_HIDE_END ? HIDE_END : IDLE;
	}

	private static LayguardState stateRelease(LayguardFSM fsm, StateSignal sig) {
		return fsm.tick < ANIM_RELEASE ? RELEASE : IDLE;
	}

	private static LayguardState stateDying(LayguardFSM fsm, StateSignal sig) {
		return fsm.tick < ANIM_DYING ? DYING : DEAD;
	}

	private static LayguardState stateDead(LayguardFSM fsm, StateSignal sig) {
		return DEAD;
	}

	private final IState<LayguardFSM, LayguardState, LayguardState> func;

	LayguardState(IState<LayguardFSM, LayguardState, LayguardState> func) {
		this.func = func;
	}

	@Override
	public LayguardState onSignal(LayguardFSM fsm, StateSignal sig) {
		return func.onSignal(fsm, sig);
	}

	@Override
	public LayguardState handle(LayguardFSM fsm) {
		return this;
	}

}
