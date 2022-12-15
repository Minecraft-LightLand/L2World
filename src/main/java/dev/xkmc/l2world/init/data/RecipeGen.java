package dev.xkmc.l2world.init.data;

import dev.xkmc.l2library.base.recipe.ResultTagShapedBuilder;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.repack.registrate.util.DataIngredient;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2world.init.L2World;
import dev.xkmc.l2world.init.registrate.LWBlocks;
import dev.xkmc.l2world.init.registrate.LWItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.BiFunction;

public class RecipeGen {

	private static String currentFolder = "";

	public static void genRecipe(RegistrateRecipeProvider pvd) {

		// gen tool and storage
		{
			currentFolder = "generated_tools/";
			for (int i = 0; i < LWMats.values().length; i++) {
				genTools(pvd, i, Items.STICK);
			}

			currentFolder = "storage/";
			for (int i = 0; i < LWMats.values().length; i++) {
				storage(pvd, LWItems.MAT_NUGGETS[i], LWItems.MAT_INGOTS[i], LWBlocks.GEN_BLOCK[i]);
			}

			storage(pvd, LWItems.LEAD_NUGGET, LWItems.LEAD_INGOT, LWBlocks.LEAD_BLOCK);
		}

		currentFolder = "quest_line/";
		{
			full(pvd, LWMats.STEEL.getIngot(), LWItems.KNIGHT_SCRAP.get(), Items.GOLD_NUGGET, LWMats.HEAVYSTEEL.getIngot(), 1);
			cross(pvd, LWMats.LAYLINE.getIngot(), LWItems.OLDROOT.get(), LWMats.OLDROOT.getIngot(), 1);
			unlock(pvd, new ShapedRecipeBuilder(LWBlocks.SLIME_CARPET.get(), 8)::unlockedBy,
					LWItems.UNSTABLE_SLIME.get()).pattern("ABA")
					.define('A', Items.SLIME_BALL).define('B', LWItems.UNSTABLE_SLIME.get())
					.save(pvd, getID(LWBlocks.SLIME_CARPET.get().asItem()));
			unlock(pvd, new ShapedRecipeBuilder(LWBlocks.SLIME_VINE.get(), 1)::unlockedBy,
					LWItems.UNSTABLE_SLIME.get()).pattern("B B").pattern(" A ").pattern("B B")
					.define('A', Items.VINE).define('B', LWItems.UNSTABLE_SLIME.get())
					.save(pvd, getID(LWBlocks.SLIME_VINE.get().asItem()));
		}

		currentFolder = "medicine_effects/";
		{
			medicine(pvd, Items.BLUE_ORCHID, MobEffects.ABSORPTION, 0, 200);
			medicine(pvd, Items.AZURE_BLUET, MobEffects.DIG_SPEED, 2, 200);
			medicine(pvd, Items.LILY_OF_THE_VALLEY, MobEffects.HEAL, 0, 1);
			medicine(pvd, Items.OXEYE_DAISY, MobEffects.REGENERATION, 1, 200);
			medicine(pvd, Items.ALLIUM, MobEffects.DAMAGE_RESISTANCE, 1, 100);
			medicine(pvd, Items.DANDELION, MobEffects.SATURATION, 0, 10);
			medicine(pvd, Items.CORNFLOWER, MobEffects.MOVEMENT_SPEED, 1, 100);
			medicine(pvd, Items.POPPY, MobEffects.DAMAGE_BOOST, 1, 100);
		}

		currentFolder = "medicine_armors/";
		{
			unlock(pvd, new MedArmorBuilder(LWItems.KING_MED_LEATHER.get(), 8)::unlockedBy, LWItems.KING_LEATHER.get())
					.pattern("XXX").pattern("XOX").pattern("XXX")
					.define('X', LWItems.MEDICINE_LEATHER.get())
					.define('O', LWItems.KING_LEATHER.get())
					.save(pvd, getID(LWItems.KING_MED_LEATHER.get()));
			medArmor(pvd, LWItems.MEDICINE_LEATHER.get(), LWItems.MEDICINE_ARMOR);
			medArmor(pvd, LWItems.KING_MED_LEATHER.get(), LWItems.KING_MED_ARMOR);
		}

	}

	private static ResourceLocation getID(Item item) {
		return new ResourceLocation(L2World.MODID, currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath());
	}

	private static ResourceLocation getID(Item item, String suffix) {
		return new ResourceLocation(L2World.MODID, currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath() + suffix);
	}

	private static ResourceLocation getID(String suffix) {
		return new ResourceLocation(L2World.MODID, currentFolder + suffix);
	}

	private static void cross(RegistrateRecipeProvider pvd, Item core, Item side, Item out, int count) {
		unlock(pvd, new ShapedRecipeBuilder(out, count)::unlockedBy, core)
				.pattern(" S ").pattern("SCS").pattern(" S ")
				.define('S', side).define('C', core)
				.save(pvd, getID(out));
	}

	private static void full(RegistrateRecipeProvider pvd, Item core, Item side, Item corner, Item out, int count) {
		unlock(pvd, new ShapedRecipeBuilder(out, count)::unlockedBy, core)
				.pattern("CSC").pattern("SAS").pattern("CSC")
				.define('A', core).define('S', side).define('C', corner)
				.save(pvd, getID(out));
	}

	private static void circle(RegistrateRecipeProvider pvd, Item core, Item side, Item out, int count) {
		unlock(pvd, new ShapedRecipeBuilder(out, count)::unlockedBy, core)
				.pattern("SSS").pattern("SAS").pattern("SSS")
				.define('A', core).define('S', side)
				.save(pvd, getID(out));
	}

	private static void storage(RegistrateRecipeProvider pvd, ItemEntry<?> nugget, ItemEntry<?> ingot, BlockEntry<?> block) {
		storage(pvd, nugget::get, ingot::get);
		storage(pvd, ingot::get, block::get);
	}

	public static void storage(RegistrateRecipeProvider pvd, NonNullSupplier<ItemLike> from, NonNullSupplier<ItemLike> to) {
		unlock(pvd, new ShapedRecipeBuilder(to.get(), 1)::unlockedBy, from.get().asItem())
				.pattern("XXX").pattern("XXX").pattern("XXX").define('X', from.get())
				.save(pvd, getID(to.get().asItem()) + "_storage");
		unlock(pvd, new ShapelessRecipeBuilder(from.get(), 9)::unlockedBy, to.get().asItem())
				.requires(to.get()).save(pvd, getID(to.get().asItem()) + "_unpack");
	}

	private static void genTools(RegistrateRecipeProvider pvd, int i, Item stick) {
		Item item = LWItems.MAT_INGOTS[i].get();
		ItemEntry<?>[] arr = LWItems.GEN_ITEM[i];
		unlock(pvd, new ShapedRecipeBuilder(arr[0].get(), 1)::unlockedBy, arr[0].get())
				.pattern("A A").pattern("A A").define('A', item).save(pvd, getID(arr[0].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[1].get(), 1)::unlockedBy, arr[1].get())
				.pattern("AAA").pattern("A A").pattern("A A").define('A', item).save(pvd, getID(arr[1].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[2].get(), 1)::unlockedBy, arr[2].get())
				.pattern("A A").pattern("AAA").pattern("AAA").define('A', item).save(pvd, getID(arr[2].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[3].get(), 1)::unlockedBy, arr[3].get())
				.pattern("AAA").pattern("A A").define('A', item).save(pvd, getID(arr[3].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[4].get(), 1)::unlockedBy, arr[4].get())
				.pattern("A").pattern("A").pattern("B").define('A', item).define('B', stick).save(pvd, getID(arr[4].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[5].get(), 1)::unlockedBy, arr[5].get())
				.pattern("AA").pattern("AB").pattern(" B").define('A', item).define('B', stick).save(pvd, getID(arr[5].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[6].get(), 1)::unlockedBy, arr[6].get())
				.pattern("A").pattern("B").pattern("B").define('A', item).define('B', stick).save(pvd, getID(arr[6].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[7].get(), 1)::unlockedBy, arr[7].get())
				.pattern("AAA").pattern(" B ").pattern(" B ").define('A', item).define('B', stick).save(pvd, getID(arr[7].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[8].get(), 1)::unlockedBy, arr[8].get())
				.pattern("AA").pattern(" B").pattern(" B").define('A', item).define('B', stick).save(pvd, getID(arr[8].get()));
	}

	private static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

	/* special */

	private static void medicine(RegistrateRecipeProvider pvd, Item flower, MobEffect eff, int amp, int duration) {
		Item item = LWItems.MEDICINE_LEATHER.get();
		ItemStack stack = new ItemStack(item);
		MobEffectInstance ins = new MobEffectInstance(eff, duration, amp);
		PotionUtils.setCustomEffects(stack, List.of(ins));
		unlock(pvd, new ResultTagShapedBuilder(stack)::unlockedBy, flower)
				.pattern("FVF").pattern("FLF").pattern("FVF").define('V', Items.VINE)
				.define('F', flower).define('L', Items.LEATHER)
				.save(pvd, getID(ForgeRegistries.MOB_EFFECTS.getKey(eff).getPath()));
	}

	private static void medArmor(RegistrateRecipeProvider pvd, Item input, LWItems.ArmorItems<?> out) {
		unlock(pvd, new MedArmorBuilder(out.armors[0].get(), 1)::unlockedBy, input)
				.pattern("A A").pattern("A A").define('A', input).save(pvd, getID(out.armors[0].get()));
		unlock(pvd, new MedArmorBuilder(out.armors[1].get(), 1)::unlockedBy, input)
				.pattern("AAA").pattern("A A").pattern("A A").define('A', input).save(pvd, getID(out.armors[1].get()));
		unlock(pvd, new MedArmorBuilder(out.armors[2].get(), 1)::unlockedBy, input)
				.pattern("A A").pattern("AAA").pattern("AAA").define('A', input).save(pvd, getID(out.armors[2].get()));
		unlock(pvd, new MedArmorBuilder(out.armors[3].get(), 1)::unlockedBy, input)
				.pattern("AAA").pattern("A A").define('A', input).save(pvd, getID(out.armors[3].get()));
	}

}
