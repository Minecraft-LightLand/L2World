package dev.xkmc.l2world.content.questline.mobs.layline.boss.flower_states;

import dev.xkmc.l2world.content.questline.common.fsm.ISignal;
import dev.xkmc.l2world.content.questline.common.fsm.IState;
import dev.xkmc.l2world.content.questline.common.fsm.StateSignal;
import net.minecraft.world.entity.LivingEntity;

public enum LayguardState implements IState<LayguardFSM, LayguardState, LayguardState>, ISignal<LayguardFSM, LayguardState, LayguardState> {
	IDLE(LayguardStateType.PROTECTED, LayguardState::stateIdle),
	BATTLE_WAIT(LayguardStateType.NORMAL, LayguardState::stateBattleWait),
	BATTLE_FIND(LayguardStateType.NORMAL, LayguardState::stateBattleFind),
	OPENING(LayguardStateType.PROTECTED, LayguardState::stateOpening),
	ATTACK_FAR(LayguardStateType.NORMAL, LayguardState::stateAttackFar),
	ATTACK_NEAR(LayguardStateType.NORMAL, LayguardState::stateAttackNear),
	SUMMON_START(LayguardStateType.NORMAL, LayguardState::stateSummonStart),
	SUMMON_WAIT(LayguardStateType.PROTECTED, LayguardState::stateSummonWait),
	SUMMON_END(LayguardStateType.NORMAL, LayguardState::stateSummonEnd),
	HIDE_START(LayguardStateType.PROTECTED, LayguardState::stateHideStart),
	HIDE_WAIT(LayguardStateType.IMMUNE, LayguardState::stateHideWait),
	HIDE_END(LayguardStateType.PROTECTED, LayguardState::stateHideEnd),
	RELEASE(LayguardStateType.PROTECTED, LayguardState::stateRelease),
	DYING(LayguardStateType.IMMUNE, LayguardState::stateDying),
	DEAD(LayguardStateType.IMMUNE, LayguardState::stateDead);

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
				if (fsm.boss.distanceTo(target) > LayguardConstants.ATTACK_DISTANCE_THRESHOLD) {
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
			if (fsm.tick >= LayguardConstants.BATTLE_FIND_TIMEOUT) {
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
			fsm.battleCooldown = LayguardConstants.ATTACK_COOLDOWN_ENTER;
		}
		return fsm.tick < LayguardConstants.ANIM_OPENING ? OPENING : BATTLE_WAIT;
	}

	private static LayguardState stateAttackFar(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.EXIT) {
			fsm.battleCooldown = LayguardConstants.ATTACK_COOLDOWN_POST;
		}
		if (sig == StateSignal.TICK) {
			if (fsm.tick == LayguardConstants.ATTACK_FAR_ACTION) {
				fsm.attackFar();
			}
		}
		return fsm.tick < LayguardConstants.ANIM_ATTACK_FAR ? ATTACK_FAR : BATTLE_WAIT;
	}

	private static LayguardState stateAttackNear(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.EXIT) {
			fsm.battleCooldown = LayguardConstants.ATTACK_COOLDOWN_POST;
		}
		if (sig == StateSignal.TICK) {
			if (fsm.tick == LayguardConstants.ATTACK_NEAR_ACTION) {
				fsm.attackNear();
			}
		}
		return fsm.tick < LayguardConstants.ANIM_ATTACK_NEAR ? ATTACK_NEAR : BATTLE_WAIT;
	}

	private static LayguardState stateSummonStart(LayguardFSM fsm, StateSignal sig) {
		return fsm.tick < LayguardConstants.ANIM_SUMMON_START ? SUMMON_START : SUMMON_WAIT;
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
			fsm.battleCooldown = LayguardConstants.ATTACK_COOLDOWN_ENTER;
		}
		return fsm.tick < LayguardConstants.ANIM_SUMMON_END ? SUMMON_END : BATTLE_WAIT;
	}

	private static LayguardState stateHideStart(LayguardFSM fsm, StateSignal sig) {
		return fsm.tick < LayguardConstants.ANIM_HIDE_START ? HIDE_START : HIDE_WAIT;
	}

	private static LayguardState stateHideWait(LayguardFSM fsm, StateSignal sig) {
		if (sig == StateSignal.TICK) {
			if (fsm.boss.hurtTime != 0) {
				fsm.tick = 0;
			}
			if (fsm.tick >= LayguardConstants.HIDE_WAIT_TIMEOUT || fsm.hasValidTarget()) {
				return HIDE_END;
			}
		}
		return HIDE_WAIT;
	}

	private static LayguardState stateHideEnd(LayguardFSM fsm, StateSignal sig) {
		return fsm.tick < LayguardConstants.ANIM_HIDE_END ? HIDE_END : IDLE;
	}

	private static LayguardState stateRelease(LayguardFSM fsm, StateSignal sig) {
		return fsm.tick < LayguardConstants.ANIM_RELEASE ? RELEASE : IDLE;
	}

	private static LayguardState stateDying(LayguardFSM fsm, StateSignal sig) {
		return fsm.tick < LayguardConstants.ANIM_DYING ? DYING : DEAD;
	}

	private static LayguardState stateDead(LayguardFSM fsm, StateSignal sig) {
		return DEAD;
	}

	private final IState<LayguardFSM, LayguardState, LayguardState> func;

	public final LayguardStateType type;

	LayguardState(LayguardStateType type, IState<LayguardFSM, LayguardState, LayguardState> func) {
		this.func = func;
		this.type = type;
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
