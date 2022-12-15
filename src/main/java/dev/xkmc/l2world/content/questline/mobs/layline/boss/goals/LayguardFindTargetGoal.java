package dev.xkmc.l2world.content.questline.mobs.layline.boss.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class LayguardFindTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {

	public LayguardFindTargetGoal(Mob self) {
		super(self, LivingEntity.class, 10, true, false, (e) -> e instanceof Mob mob && mob.getTarget() == self);
	}

}
