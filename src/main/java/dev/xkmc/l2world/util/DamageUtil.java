package dev.xkmc.l2world.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class DamageUtil {

	public static void dealDamage(LivingEntity target, DamageSource source, float damage) {
		if (source.isFire() && target.fireImmune())
			return;
		target.hurt(source, damage);
	}

}
