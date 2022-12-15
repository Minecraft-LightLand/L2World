package dev.xkmc.l2world.content.questline.common.fsm;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.util.code.Wrappers;

@SerialClass
public class FSM<F extends FSM<F, T, E>, T extends Enum<T> & IState<F, T, E>, E extends Enum<E> & ISignal<F, T, E>> {

	private final T[] states;
	private final E[] signals;

	@SerialClass.SerialField
	public int prev, state, tick;

	public FSM(T[] states, E[] signals, T init) {
		this.states = states;
		this.signals = signals;
		this.prev = this.state = init.ordinal();
		tick = 0;
		states[state].onSignal(getThis(), StateSignal.ENTRY);
		onSignal(StateSignal.INIT);
	}

	public T getState(){
		return states[state];
	}

	public void tick() {
		tick++;
		onSignal(StateSignal.TICK);
	}

	public T sendSignal(E signal) {
		return handleSignal(signal.ordinal());
	}

	public T handleSignal(int val) {
		E signal = signals[val];
		T ans = signal.handle(getThis());
		transition(ans.ordinal());
		return ans;
	}

	public final F getThis() {
		return Wrappers.cast(this);
	}

	protected void onSignal(StateSignal signal) {
		transition(states[state].onSignal(getThis(), signal).ordinal());
	}

	protected void transition(int newState) {
		if (state == newState) return;
		states[state].onSignal(getThis(), StateSignal.EXIT);
		prev = state;
		state = newState;
		tick = 0;
		states[state].onSignal(getThis(), StateSignal.ENTRY);
		onSignal(StateSignal.INIT);
	}

}
