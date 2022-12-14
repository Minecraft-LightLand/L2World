package dev.xkmc.l2world.content.misc.recipe;

import dev.xkmc.l2library.base.recipe.AbstractShapedRecipe;
import dev.xkmc.l2world.content.misc.equipments.MedicineItem;
import dev.xkmc.l2world.init.registrate.LWRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class MedArmorRecipe extends AbstractShapedRecipe<MedArmorRecipe> {

	public MedArmorRecipe(ResourceLocation rl, String group, int w, int h, NonNullList<Ingredient> ingredients, ItemStack result) {
		super(rl, group, w, h, ingredients, result);
	}

	@Override
	public boolean matches(CraftingContainer cont, Level level) {
		boolean match = super.matches(cont, level);
		if (!match) return false;
		ItemStack init = null;
		for (int i = 0; i < cont.getContainerSize(); i++) {
			ItemStack stack = cont.getItem(i);
			if (stack.getItem() instanceof MedicineItem) {
				if (init == null) {
					init = stack;
				} else {
					if (!MedicineItem.eq(stack, init))
						return false;
				}
			}
		}
		return true;
	}

	@Override
	public ItemStack assemble(CraftingContainer cont) {
		ItemStack result = super.assemble(cont);
		ItemStack init = null;
		for (int i = 0; i < cont.getContainerSize(); i++) {
			ItemStack stack = cont.getItem(i);
			if (stack.getItem() instanceof MedicineItem) {
				init = stack;
				break;
			}
		}
		if (init != null)
			PotionUtils.setCustomEffects(result, PotionUtils.getCustomEffects(init));
		return result;
	}

	@Override
	public Serializer<MedArmorRecipe> getSerializer() {
		return LWRecipes.RSC_MED_ARMOR.get();
	}

}
