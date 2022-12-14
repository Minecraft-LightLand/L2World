package dev.xkmc.l2world.content.questline.block;

import dev.xkmc.l2world.init.registrate.LWBlocks;
import dev.xkmc.l2world.init.registrate.LWItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class LaylineChargerBlock extends Block {

	public LaylineChargerBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		BlockState below = level.getBlockState(pos.below());
		if (below.is(LWBlocks.LAYROOT_HEAD.get())) {
			if (stack.is(LWItems.LAYLINE_ORB.get())) {
				stack.shrink(1);
				level.setBlockAndUpdate(pos.below(), LWBlocks.LAYLINE_HEAD.getDefaultState());
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

}
