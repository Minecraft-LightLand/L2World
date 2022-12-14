package dev.xkmc.l2foundation.content.misc.effect.skill;

import dev.xkmc.l2complements.content.effect.skill.SkillEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ArmorBreakerEffect extends InherentEffect implements SkillEffect<ArmorBreakerEffect> {

	public ArmorBreakerEffect(MobEffectCategory type, int color) {
		super(type, color);
	}
}
