package dev.xkmc.l2world.content.questline.common.fsm;

@FunctionalInterface
public interface ISignal<F extends FSM<F, T, E>, T extends Enum<T> & IState<F, T, E>, E extends Enum<E> & ISignal<F, T, E>> {

	T handle(F fsm);
}
