package dev.xkmc.l2world.init.data;

import dev.xkmc.l2library.base.recipe.CustomShapedBuilder;
import dev.xkmc.l2world.content.misc.recipe.MedArmorRecipe;
import dev.xkmc.l2world.init.registrate.LWRecipes;
import net.minecraft.world.level.ItemLike;

public class MedArmorBuilder extends CustomShapedBuilder<MedArmorRecipe> {

	public MedArmorBuilder(ItemLike result, int count) {
		super(LWRecipes.RSC_MED_ARMOR, result, count);
	}

}
