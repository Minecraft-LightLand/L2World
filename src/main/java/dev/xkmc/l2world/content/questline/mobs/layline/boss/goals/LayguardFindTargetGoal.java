package dev.xkmc.l2world.content.questline.mobs.layline.boss.goals;

import dev.xkmc.l2world.content.questline.mobs.layline.boss.LayguardEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;

public class LayguardFindTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {

	public LayguardFindTargetGoal(LayguardEntity self) {
		super(self, LivingEntity.class, 10, true, false, (e) -> e instanceof Enemy);
	}

}
