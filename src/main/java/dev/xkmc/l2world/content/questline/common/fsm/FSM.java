package dev.xkmc.l2world.content.questline.common.fsm;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.util.code.Wrappers;

import java.util.ArrayList;
import java.util.List;

@SerialClass
public class FSM<F extends FSM<F, T, E>, T extends Enum<T> & IState<F, T, E>, E extends Enum<E> & ISignal<F, T, E>> {

	private final T[] states;
	private final E[] signals;

	@SerialClass.SerialField
	public int prev, state;

	@SerialClass.SerialField
	public Ticker ticker = new Ticker(true);

	private final List<Ticker> tickers = new ArrayList<>();

	public FSM(T[] states, E[] signals, T init) {
		this.states = states;
		this.signals = signals;
		this.prev = this.state = init.ordinal();
		resetTick();
		states[state].onSignal(getThis(), StateSignal.ENTRY);
		onSignal(StateSignal.INIT);
	}

	protected void addTicker(Ticker t) {
		tickers.add(t);
	}

	public T getState() {
		return states[state];
	}

	public void tick() {
		for (Ticker t : tickers) {
			t.tick();
		}
		onSignal(StateSignal.TICK);
	}

	public T sendSignal(E signal) {
		return handleSignal(signal.ordinal());
	}

	protected T handleSignal(int val) {
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
		resetTick();
		states[state].onSignal(getThis(), StateSignal.ENTRY);
		onSignal(StateSignal.INIT);
	}

	public int getTick() {
		return ticker.getTick();
	}

	public void resetTick() {
		ticker.set(0);
	}
}
