package dev.xkmc.l2world.content.questline.block;

import dev.xkmc.l2world.init.registrate.LFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LayrootHead extends GrowingPlantHeadBlock {

	public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public LayrootHead(Properties prop) {
		super(prop, Direction.DOWN, SHAPE, false, 0);
	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
		return 0;
	}

	public boolean isValidBonemealTarget(BlockGetter p_53900_, BlockPos p_53901_, BlockState p_53902_, boolean p_53903_) {
		return false;
	}

	@Override
	protected boolean canGrowInto(BlockState state) {
		return state.isAir();
	}

	@Override
	protected Block getBodyBlock() {
		return LFBlocks.LAYROOT_BODY.get();
	}

}
