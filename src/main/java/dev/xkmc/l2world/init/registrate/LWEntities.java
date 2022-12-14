package dev.xkmc.l2world.init.registrate;

import dev.xkmc.l2library.repack.registrate.util.entry.EntityEntry;
import dev.xkmc.l2world.content.misc.entity.SpecialSpriteRenderer;
import dev.xkmc.l2world.content.questline.mobs.cursedknight.*;
import dev.xkmc.l2world.content.questline.mobs.layline.mobs.LaylineSkeleton;
import dev.xkmc.l2world.content.questline.mobs.layline.mobs.LaylineSkeletonRenderer;
import dev.xkmc.l2world.content.questline.mobs.layline.mobs.LaylineZombie;
import dev.xkmc.l2world.content.questline.mobs.layline.mobs.LaylineZombieRenderer;
import dev.xkmc.l2world.content.questline.mobs.swamp.*;
import dev.xkmc.l2world.init.L2World;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class LWEntities {

	public static final EntityEntry<SlimeTentacle> ET_SLIME_TENTACLE;

	static {

		ET_SLIME_TENTACLE = L2World.REGISTRATE
				.<SlimeTentacle>entity("slime_tentacle", SlimeTentacle::new, MobCategory.MISC)
				.properties(e -> e.sized(0.5F, 0.5F)
						.clientTrackingRange(4).updateInterval(20)
						.setShouldReceiveVelocityUpdates(true))
				.renderer(() -> ctx -> new SpecialSpriteRenderer<>(ctx, ctx.getItemRenderer(), false))
				.defaultLang().register();
	}

	public static final EntityEntry<LaylineZombie> ET_LAYLINE_ZOMBIE;
	public static final EntityEntry<LaylineSkeleton> ET_LAYLINE_SKELETON;
	public static final EntityEntry<CursedKnight> ET_CURSED_KNIGHT;
	public static final EntityEntry<CursedArcher> ET_CURSED_ARCHER;
	public static final EntityEntry<CursedShield> ET_CURSED_SHIELD;
	public static final EntityEntry<PotionSlime> ET_POTION_SLIME;
	public static final EntityEntry<StoneSlime> ET_STONE_SLIME;
	public static final EntityEntry<VineSlime> ET_VINE_SLIME;
	public static final EntityEntry<CarpetSlime> ET_CARPET_SLIME;
	public static final EntityEntry<BossSlime> ET_BOSS_SLIME;

	static {
		ET_LAYLINE_ZOMBIE = L2World.REGISTRATE
				.entity("layline_zombie", LaylineZombie::new, MobCategory.MONSTER)
				.properties(e -> e.sized(0.6f, 1.95f).clientTrackingRange(8))
				.renderer(() -> LaylineZombieRenderer::new).loot(LaylineZombie::loot).defaultLang().register();
		ET_LAYLINE_SKELETON = L2World.REGISTRATE
				.entity("layline_skeleton", LaylineSkeleton::new, MobCategory.MONSTER)
				.properties(e -> e.sized(0.6f, 1.95f).clientTrackingRange(8))
				.renderer(() -> LaylineSkeletonRenderer::new).loot(LaylineSkeleton::loot).defaultLang().register();
		ET_CURSED_KNIGHT = L2World.REGISTRATE
				.entity("cursed_knight", CursedKnight::new, MobCategory.MONSTER)
				.properties(e -> e.sized(0.6f, 1.95f).clientTrackingRange(8))
				.renderer(() -> CursedKnightRenderer::new).loot(BaseCursedKnight::loot).defaultLang().register();
		ET_CURSED_ARCHER = L2World.REGISTRATE
				.entity("cursed_archer", CursedArcher::new, MobCategory.MONSTER)
				.properties(e -> e.sized(0.6f, 1.95f).clientTrackingRange(8))
				.renderer(() -> CursedKnightRenderer::new).loot(BaseCursedKnight::loot).defaultLang().register();
		ET_CURSED_SHIELD = L2World.REGISTRATE
				.entity("cursed_shield", CursedShield::new, MobCategory.MONSTER)
				.properties(e -> e.sized(0.6f, 1.95f).clientTrackingRange(8))
				.renderer(() -> CursedKnightRenderer::new).loot(BaseCursedKnight::loot).defaultLang().register();

		ET_POTION_SLIME = L2World.REGISTRATE
				.entity("potion_slime", PotionSlime::new, MobCategory.MONSTER)
				.properties(e -> e.sized(2.04F, 2.04F).clientTrackingRange(10))
				.renderer(() -> PotionSlimeRenderer::new).loot(PotionSlime::loot).defaultLang().register();
		ET_STONE_SLIME = L2World.REGISTRATE
				.entity("stone_slime", StoneSlime::new, MobCategory.MONSTER)
				.properties(e -> e.sized(2.04F, 2.04F).clientTrackingRange(10))
				.renderer(() -> MaterialSlimeRenderer::new).loot(StoneSlime::loot).defaultLang().register();
		ET_VINE_SLIME = L2World.REGISTRATE
				.entity("vine_slime", VineSlime::new, MobCategory.MONSTER)
				.properties(e -> e.sized(2.04F, 2.04F).clientTrackingRange(10))
				.renderer(() -> MaterialSlimeRenderer::new).loot(VineSlime::loot).defaultLang().register();
		ET_CARPET_SLIME = L2World.REGISTRATE
				.entity("carpet_slime", CarpetSlime::new, MobCategory.MONSTER)
				.properties(e -> e.sized(2.04F, 2.04F).clientTrackingRange(10))
				.renderer(() -> MaterialSlimeRenderer::new).loot(CarpetSlime::loot).defaultLang().register();
		ET_BOSS_SLIME = L2World.REGISTRATE
				.entity("boss_slime", BossSlime::new, MobCategory.MONSTER)
				.properties(e -> e.sized(2.04F, 2.04F).clientTrackingRange(10))
				.renderer(() -> MaterialSlimeRenderer::new).loot(BossSlime::loot).defaultLang().register();
	}

	public static void register() {
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(ET_LAYLINE_ZOMBIE.get(), Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25F)
				.add(Attributes.ATTACK_DAMAGE, 4.0D)
				.add(Attributes.FOLLOW_RANGE, 35.0D).build());

		event.put(ET_LAYLINE_SKELETON.get(), Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25F)
				.add(Attributes.ATTACK_DAMAGE, 4.0D)
				.add(Attributes.FOLLOW_RANGE, 35.0D).build());


		event.put(ET_CURSED_KNIGHT.get(), Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3F)
				.add(Attributes.ATTACK_DAMAGE, 6.0D)
				.add(Attributes.FOLLOW_RANGE, 35.0D).build());


		event.put(ET_CURSED_ARCHER.get(), Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.35F)
				.add(Attributes.ATTACK_DAMAGE, 4.0D)
				.add(Attributes.FOLLOW_RANGE, 35.0D).build());

		event.put(ET_CURSED_SHIELD.get(), Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 40.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25F)
				.add(Attributes.ATTACK_DAMAGE, 4.0D)
				.add(Attributes.FOLLOW_RANGE, 35.0D).build());

		event.put(ET_POTION_SLIME.get(), Monster.createMonsterAttributes().build());
		event.put(ET_STONE_SLIME.get(), Monster.createMonsterAttributes().build());
		event.put(ET_VINE_SLIME.get(), Monster.createMonsterAttributes().build());
		event.put(ET_CARPET_SLIME.get(), Monster.createMonsterAttributes().build());
		event.put(ET_BOSS_SLIME.get(), Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 64).build());

	}

}
