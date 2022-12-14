package dev.xkmc.l2world.content.questline.mobs.cursedknight;

import dev.xkmc.l2complements.init.data.GenItem;
import dev.xkmc.l2world.content.questline.common.mobs.BaseMonster;
import dev.xkmc.l2world.content.questline.common.mobs.SimpleEquipment;
import dev.xkmc.l2world.content.questline.common.mobs.SoundPackage;
import dev.xkmc.l2world.content.questline.common.mobs.SpawnedEquipment;
import dev.xkmc.l2world.init.data.LWMats;
import dev.xkmc.l2world.init.registrate.LWEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"rawtype", "unchecked", "unsafe"})
public class CursedKnightProperties {

	public static final SoundPackage SOUND_ZOMBIE = new SoundPackage(SoundEvents.ZOMBIE_AMBIENT, SoundEvents.ZOMBIE_HURT, SoundEvents.ZOMBIE_DEATH, SoundEvents.ZOMBIE_STEP);

	private static SimpleEquipment genArmor(EquipmentSlot slot, LWMats... mat) {
		return genEquip(slot, 1, 10, 30, Arrays.stream(mat).map(e -> wrap(e.getArmor(slot), 100)).toArray(WeightedEntry.Wrapper[]::new));
	}

	private static List<SpawnedEquipment> genArmorSet(SimpleEquipment tool, LWMats... mat) {
		return List.of(genArmor(EquipmentSlot.HEAD, mat), genArmor(EquipmentSlot.CHEST, mat),
				genArmor(EquipmentSlot.LEGS, mat), genArmor(EquipmentSlot.FEET, mat), tool);
	}

	public static final Set<EntityType<?>> ALLY_TYPE = Set.of(
			LWEntities.ET_CURSED_KNIGHT.get(), LWEntities.ET_CURSED_ARCHER.get(), LWEntities.ET_CURSED_SHIELD.get()
	);

	public static final BaseMonster.EntityConfig CONFIG_KNIGHT = new BaseMonster.EntityConfig(MobType.UNDEAD, SOUND_ZOMBIE,
			genArmorSet(genEquip(EquipmentSlot.MAINHAND, 1, 10, 30,
					wrap(LWMats.KNIGHTSTEEL.getTool(GenItem.Tools.SWORD), 100)), LWMats.KNIGHTSTEEL),
			List.of(), ALLY_TYPE);

	public static final BaseMonster.EntityConfig CONFIG_ARCHER = new BaseMonster.EntityConfig(MobType.UNDEAD, SOUND_ZOMBIE,
			genArmorSet(genEquip(EquipmentSlot.MAINHAND, 1, 10, 30,
					wrap(Items.BOW, 100)), LWMats.KNIGHTSTEEL),
			List.of(), ALLY_TYPE);

	public static final BaseMonster.EntityConfig CONFIG_SHIELD = new BaseMonster.EntityConfig(MobType.UNDEAD, SOUND_ZOMBIE,
			genArmorSet(genEquip(EquipmentSlot.MAINHAND, 1, 10, 30,
							wrap(LWMats.KNIGHTSTEEL.getTool(GenItem.Tools.SWORD), 25),
							wrap(LWMats.KNIGHTSTEEL.getTool(GenItem.Tools.AXE), 50),
							wrap(LWMats.HEAVYSTEEL.getTool(GenItem.Tools.SWORD), 50),
							wrap(LWMats.HEAVYSTEEL.getTool(GenItem.Tools.AXE), 100)),
					LWMats.KNIGHTSTEEL, LWMats.HEAVYSTEEL),
			List.of(), ALLY_TYPE);

	public static final WeightedRandomList<WeightedEntry.Wrapper<MobEffectInstance>> EFFECTS = WeightedRandomList.create(
			WeightedEntry.wrap(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1), 100),
			WeightedEntry.wrap(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3), 100),
			WeightedEntry.wrap(new MobEffectInstance(MobEffects.POISON, 100, 0), 100),
			WeightedEntry.wrap(new MobEffectInstance(MobEffects.BLINDNESS, 100), 50),
			WeightedEntry.wrap(new MobEffectInstance(MobEffects.HARM, 1), 50)
	);

	public static WeightedEntry.Wrapper<Item> wrap(Item item, int weight) {
		return WeightedEntry.wrap(item, weight);
	}

	public static SimpleEquipment genEquip(EquipmentSlot slot, double chance, int lv1, int lv2, WeightedEntry.Wrapper<Item>... items) {
		return new SimpleEquipment(slot, WeightedRandomList.create(items), chance, lv1, lv2);
	}

}
