package dev.xkmc.l2world.content.misc.effect.skill;

import dev.xkmc.l2complements.content.effect.skill.SkillEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BloodThurstEffect extends InherentEffect implements SkillEffect<BloodThurstEffect> {

	public BloodThurstEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

}
