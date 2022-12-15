package dev.xkmc.l2world.content.questline.common.fsm;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2world.init.L2World;
import net.minecraft.world.entity.LivingEntity;

@SerialClass
public abstract class EntityFSM<M extends LivingEntity,
		F extends EntityFSM<M, F, T, E>,
		T extends Enum<T> & IState<F, T, E>,
		E extends Enum<E> & ISignal<F, T, E>>
		extends FSM<F, T, E> {

	public final M entity;

	public final T dying, dead;

	private final byte offset;

	public EntityFSM(T[] states, E[] signals, T init, T dying, T dead, M entity) {
		super(states, signals, init);
		this.entity = entity;
		this.offset = (byte) (Byte.MAX_VALUE - signals.length);
		if (offset <= 62) {
			L2World.LOGGER.error("Entity FSM too many states: " + entity.getType());
		}
		this.dying = dying;
		this.dead = dead;
	}

	@Override
	public void tick() {
		if (entity.isDeadOrDying() && !isInDeadState()) {
			transition(dying.ordinal());
		}
		super.tick();
	}

	@Override
	public final T sendSignal(E signal) {
		if (!entity.level.isClientSide()) {
			entity.level.broadcastEntityEvent(entity, (byte) (offset + signal.ordinal()));
		}
		return entity.level.isClientSide() ? getState() : super.sendSignal(signal);
	}

	public final boolean isFSMEvent(byte event) {
		return event >= offset;
	}

	public final void handleFSMEvent(byte event) {
		handleSignal(event - offset);
	}

	public boolean isInDeadState() {
		return getState() == dead || getState() == dying;
	}

}
