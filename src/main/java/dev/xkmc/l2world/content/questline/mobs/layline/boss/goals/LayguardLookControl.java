package dev.xkmc.l2world.content.questline.mobs.layline.boss.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

import java.util.Optional;

public class LayguardLookControl extends LookControl {

	public LayguardLookControl(Mob self) {
		super(self);
	}

	protected void clampHeadRotationToBody() {
	}

	protected Optional<Float> getXRotD() {
		return Optional.of(0.0F);
	}
}
