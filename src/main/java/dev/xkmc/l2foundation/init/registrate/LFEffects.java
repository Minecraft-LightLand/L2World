package dev.xkmc.l2foundation.init.registrate;


import dev.xkmc.l2foundation.content.misc.effect.CleanseEffect;
import dev.xkmc.l2foundation.content.misc.effect.DispellEffect;
import dev.xkmc.l2foundation.content.misc.effect.assassin.TargetAttractedEffect;
import dev.xkmc.l2foundation.content.misc.effect.assassin.TargetAttractorEffect;
import dev.xkmc.l2foundation.content.misc.effect.assassin.TargetHideEffect;
import dev.xkmc.l2foundation.content.misc.effect.assassin.TargetRemoveEffect;
import dev.xkmc.l2foundation.content.misc.effect.force.HeavyEffect;
import dev.xkmc.l2foundation.content.misc.effect.force.WaterTrapEffect;
import dev.xkmc.l2foundation.content.misc.effect.skill.ArmorBreakerEffect;
import dev.xkmc.l2foundation.content.misc.effect.skill.BloodThurstEffect;
import dev.xkmc.l2foundation.content.misc.effect.skill.NoKnockBackEffect;
import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2library.repack.registrate.builders.NoConfigBuilder;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * handles enchantment, mob effects, and potions
 */
public class LFEffects {

	public static final RegistryEntry<WaterTrapEffect> WATER_TRAP = genEffect("water_trap", () -> new WaterTrapEffect(MobEffectCategory.HARMFUL, 0x00007f));
	public static final RegistryEntry<HeavyEffect> HEAVY = genEffect("heavy", () -> new HeavyEffect(MobEffectCategory.HARMFUL, 0x404040));

	public static final RegistryEntry<NoKnockBackEffect> NO_KB = genEffect("no_knockback", () -> new NoKnockBackEffect(MobEffectCategory.BENEFICIAL, 0xafafaf));
	public static final RegistryEntry<BloodThurstEffect> BLOOD_THURST = genEffect("blood_thirst", () -> new BloodThurstEffect(MobEffectCategory.BENEFICIAL, 0xffafaf));
	public static final RegistryEntry<ArmorBreakerEffect> ARMOR_BREAKER = genEffect("armor_breaker", () -> new ArmorBreakerEffect(MobEffectCategory.HARMFUL, 0xFFFFFF));

	public static final RegistryEntry<TargetAttractorEffect> T_SINK = genEffect("target_attractor", () -> new TargetAttractorEffect(MobEffectCategory.NEUTRAL, 0xffffff));
	public static final RegistryEntry<TargetAttractedEffect> T_SOURCE = genEffect("target_attracted", () -> new TargetAttractedEffect(MobEffectCategory.NEUTRAL, 0xffffff));
	public static final RegistryEntry<TargetRemoveEffect> T_CLEAR = genEffect("target_remove", () -> new TargetRemoveEffect(MobEffectCategory.NEUTRAL, 0xffffff));
	public static final RegistryEntry<TargetHideEffect> T_HIDE = genEffect("target_hide", () -> new TargetHideEffect(MobEffectCategory.NEUTRAL, 0xffffff));

	public static final RegistryEntry<CleanseEffect> CLEANSE = genEffect("cleanse", () -> new CleanseEffect(MobEffectCategory.NEUTRAL, 0xffffff));
	public static final RegistryEntry<DispellEffect> DISPELL = genEffect("dispell", () -> new DispellEffect(MobEffectCategory.NEUTRAL, 0x9f9f9f));


	public static final List<RegistryEntry<? extends Potion>> POTION_LIST = new ArrayList<>();

	public static final RegistryEntry<Potion> P_CLEANSE_WATER = genPotion("cleanse_water", () -> new Potion(new MobEffectInstance(CLEANSE.get(), 600)));
	public static final RegistryEntry<Potion> P_CLEANSE_WATER_L = genPotion("long_cleanse_water", () -> new Potion(new MobEffectInstance(CLEANSE.get(), 1200)));
	public static final RegistryEntry<Potion> P_HOLY_WATER = genPotion("holy_water", () -> new Potion(new MobEffectInstance(CLEANSE.get(), 600)));
	public static final RegistryEntry<Potion> P_HOLY_WATER_L = genPotion("long_holy_water", () -> new Potion(new MobEffectInstance(CLEANSE.get(), 1200)));
	public static final RegistryEntry<Potion> P_DISPELL = genPotion("dispell", () -> new Potion(new MobEffectInstance(DISPELL.get(), 600)));
	public static final RegistryEntry<Potion> P_DISPELL_S = genPotion("strong_dispell", () -> new Potion(new MobEffectInstance(DISPELL.get(), 400, 1)));
	public static final RegistryEntry<Potion> P_DISPELL_L = genPotion("long_dispell", () -> new Potion(new MobEffectInstance(DISPELL.get(), 1200)));

	public static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup) {
		return L2Foundation.REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(L2Foundation.REGISTRATE, L2Foundation.REGISTRATE, name, cb, ForgeRegistries.Keys.MOB_EFFECTS, sup))
				.lang(MobEffect::getDescriptionId).register();
	}

	public static <T extends Potion> RegistryEntry<T> genPotion(String name, NonNullSupplier<T> sup) {
		RegistryEntry<T> ans = L2Foundation.REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(L2Foundation.REGISTRATE, L2Foundation.REGISTRATE, name, cb, ForgeRegistries.Keys.POTIONS, sup)).register();
		POTION_LIST.add(ans);
		return ans;
	}

	public static void register() {

	}

	public static void registerBrewingRecipe() {
		PotionBrewing.addMix(Potions.AWKWARD, LFItems.CLEANSE_WATER_BOTTLE.get(), P_CLEANSE_WATER.get());
		PotionBrewing.addMix(P_CLEANSE_WATER.get(), Items.REDSTONE, P_CLEANSE_WATER_L.get());
		PotionBrewing.addMix(P_CLEANSE_WATER.get(), LFItems.DISPELL_DUST.get(), P_DISPELL.get());
		PotionBrewing.addMix(P_CLEANSE_WATER_L.get(), LFItems.DISPELL_DUST.get(), P_DISPELL_L.get());
		PotionBrewing.addMix(P_DISPELL.get(), Items.GLOWSTONE_DUST, P_DISPELL_S.get());
		PotionBrewing.addMix(P_DISPELL.get(), Items.REDSTONE, P_DISPELL_L.get());
	}

}
