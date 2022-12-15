package dev.xkmc.l2world.content.questline.common.fsm;

import dev.xkmc.l2library.serial.SerialClass;

@SerialClass
public class Ticker {

	private final boolean countUp;

	@SerialClass.SerialField
	private int tick;

	public Ticker(boolean countUp) {
		this.countUp = countUp;
	}

	public void tick() {
		if (countUp) {
			tick++;
		} else {
			if (tick > 0) {
				tick--;
			}
		}
	}

	public void set(int val) {
		tick = val;
	}

	public int getTick() {
		return tick;
	}

}
