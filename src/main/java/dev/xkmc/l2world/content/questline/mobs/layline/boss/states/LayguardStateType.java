package dev.xkmc.l2world.content.questline.mobs.layline.boss.states;

public enum LayguardStateType {
	NORMAL(0.2f), PROTECTED(2f), IMMUNE(4f);

	public final float healRate;

	LayguardStateType(float healRate) {
		this.healRate = healRate;
	}
}
