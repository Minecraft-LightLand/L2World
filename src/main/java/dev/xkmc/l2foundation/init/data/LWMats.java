package dev.xkmc.l2foundation.init.data;

import dev.xkmc.l2complements.content.item.generic.ArmorMat;
import dev.xkmc.l2complements.content.item.generic.ExtraArmorConfig;
import dev.xkmc.l2complements.content.item.generic.ExtraToolConfig;
import dev.xkmc.l2complements.init.data.GenItem;
import dev.xkmc.l2complements.init.data.IGeneralMats;
import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2foundation.init.registrate.LFBlocks;
import dev.xkmc.l2foundation.init.registrate.LFItems;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;

import java.util.function.Supplier;

import static dev.xkmc.l2complements.init.data.GenItem.*;

public enum LWMats implements IGeneralMats {
	STEEL("steel", 2, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(500, 6, new int[]{6, 9, 4, 4, 1},
					new float[]{1.6f, 0.9f, 1f, 1.2f, 3f}, 14),
			new ArmorStats(20, new int[]{2, 5, 6, 2}, 1, 0, 9),
			TOOL_DEF, ARMOR_DEF, new ExtraToolConfig(), new ExtraArmorConfig()),
	LAYROOT("layroot", 2, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(300, 6, new int[]{6, 9, 4, 4, 1},
					new float[]{1.8f, 1.0f, 1.1f, 1.3f, 3.3f}, 18),
			new ArmorStats(20, new int[]{2, 5, 6, 2}, 0, 0, 19),
			TOOL_GEN, ARMOR_GEN, new ExtraToolConfig().repairChance(1e-2),
			new ExtraArmorConfig().repairChance(1e-2)),
	LAYLINE("layline", 2, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(500, 8, new int[]{6, 9, 4, 4, 2},
					new float[]{2.0f, 1.1f, 1.3f, 1.5f, 3.6f}, 20),
			new ArmorStats(25, new int[]{2, 5, 6, 2}, 1, 0, 22),
			TOOL_GEN, ARMOR_GEN, new ExtraToolConfig().repairChance(2e-2),
			new ExtraArmorConfig().repairChance(2e-2)),
	OLDROOT("oldroot", 3, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(700, 10, new int[]{7, 10, 4, 4, 3},
					new float[]{2.0f, 1.1f, 1.5f, 1.7f, 4f}, 22),
			new ArmorStats(30, new int[]{2, 5, 6, 2}, 2, 0, 25),
			TOOL_GEN, ARMOR_GEN, new ExtraToolConfig().repairChance(4e-2),
			new ExtraArmorConfig().repairChance(4e-2)),
	KNIGHTSTEEL("knightsteel", 3, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(500, 6, new int[]{6, 9, 4, 4, 1},
					new float[]{1.6f, 0.9f, 1f, 1.2f, 3f}, 18),
			new ArmorStats(25, new int[]{2, 5, 6, 2}, 2, 0.1f, 12),
			TOOL_DEF, ARMOR_DEF, new ExtraToolConfig(), new ExtraArmorConfig()),
	DISPELLIUM("dispellium", 2, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(250, 6, new int[]{6, 9, 4, 4, 1},
					new float[]{1.6f, 0.9f, 1f, 1.2f, 3f}, 0),
			new ArmorStats(15, new int[]{2, 5, 6, 2}, 0, 0, 0),
			TOOL_GEN, ARMOR_GEN, new ExtraToolConfig().setBypassMagic(),
			new ExtraArmorConfig().setMagicImmune(40)),
	HEAVYSTEEL("heavysteel", 3, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(700, 5, new int[]{10, 14, 4, 4, 1},
					new float[]{1f, 0.7f, 0.8f, 1f, 2f}, 14),
			new ArmorStats(30, new int[]{3, 5, 6, 3}, 3, 0.2f, 9),
			TOOL_DEF, ARMOR_DEF, new ExtraToolConfig(), new ExtraArmorConfig()),
	ETERNIUM("eternium", 2, SoundEvents.ARMOR_EQUIP_IRON,
			new ToolStats(9999, 6, new int[]{6, 9, 4, 4, 1},
					new float[]{1.6f, 0.9f, 1f, 1.2f, 3f}, 18),
			new ArmorStats(9999, new int[]{2, 5, 6, 2}, 0, 0, 19),
			TOOL_GEN, ARMOR_GEN, new ExtraToolConfig().damageChance(0).repairChance(1),
			new ExtraArmorConfig().damageChance(0).repairChance(1));

	final String id;
	final Tier tier;
	final ArmorMaterial mat;
	final ToolConfig tool_config;
	final ArmorConfig armor_config;
	final ToolStats tool_stats;
	final ExtraToolConfig tool_extra;
	final ExtraArmorConfig armor_extra;

	LWMats(String name, int level,
		   SoundEvent equip_sound, ToolStats tool, ArmorStats armor,
		   ToolConfig tool_config, ArmorConfig armor_config,
		   ExtraToolConfig tool_extra, ExtraArmorConfig armor_extra) {
		Supplier<Ingredient> ing = () -> Ingredient.of(LFItems.MAT_INGOTS[ordinal()].get());
		this.id = name;
		this.tier = new ForgeTier(level, tool.durability(), tool.speed(), 0, tool.enchant(),
				getBlockTag(level), ing);
		this.mat = new ArmorMat(armorPrefix(), armor.durability(), armor.protection(),
				armor.enchant(), equip_sound, armor.tough(), armor.kb(), ing);
		this.tool_config = tool_config;
		this.armor_config = armor_config;
		this.tool_stats = tool;
		this.tool_extra = tool_extra;
		this.armor_extra = armor_extra;
	}

	public Item getIngot() {
		return LFItems.MAT_INGOTS[ordinal()].get();
	}

	public Item getNugget() {
		return LFItems.MAT_NUGGETS[ordinal()].get();
	}

	public ItemEntry<Item>[][] getGenerated() {
		return LFItems.GEN_ITEM;
	}

	@Override
	public Block getBlock() {
		return LFBlocks.GEN_BLOCK[ordinal()].get();
	}

	public String armorPrefix() {
		return L2Foundation.MODID + ":" + id;
	}

	// --- interface ---

	public String getID() {
		return id;
	}

	@Override
	public GenItem.ArmorConfig getArmorConfig() {
		return armor_config;
	}

	@Override
	public GenItem.ToolConfig getToolConfig() {
		return tool_config;
	}

	@Override
	public GenItem.ToolStats getToolStats() {
		return tool_stats;
	}

	@Override
	public Tier getTier() {
		return tier;
	}

	@Override
	public ExtraArmorConfig getExtraArmorConfig() {
		return armor_extra;
	}

	@Override
	public ArmorMaterial getArmorMaterial() {
		return mat;
	}

	@Override
	public ExtraToolConfig getExtraToolConfig() {
		return tool_extra;
	}

}
