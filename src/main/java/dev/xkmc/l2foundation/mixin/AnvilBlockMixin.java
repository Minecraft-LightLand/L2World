package dev.xkmc.l2foundation.mixin;

import dev.xkmc.l2foundation.init.registrate.LFBlocks;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private static void injectDamage(BlockState state, CallbackInfoReturnable<BlockState> info) {
		if (state.getBlock() == LFBlocks.ETERNAL_ANVIL.get()) {
			info.setReturnValue(state);
			info.cancel();
		}
	}

}
