package dev.xkmc.l2world.content.questline.mobs.layline.boss.goals;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class LayguardAttackPlayerGoal extends NearestAttackableTargetGoal<Player> {

	public LayguardAttackPlayerGoal(Mob self) {
		super(self, Player.class, true);
	}

	public boolean canUse() {
		return mob.level.getDifficulty() != Difficulty.PEACEFUL && super.canUse();
	}

}
