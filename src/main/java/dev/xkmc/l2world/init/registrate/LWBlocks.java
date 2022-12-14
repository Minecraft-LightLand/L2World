package dev.xkmc.l2world.init.registrate;

import dev.xkmc.l2library.block.DelegateBlock;
import dev.xkmc.l2library.block.DelegateBlockProperties;
import dev.xkmc.l2library.repack.registrate.providers.loot.RegistrateBlockLootTables;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntry;
import dev.xkmc.l2world.content.questline.block.*;
import dev.xkmc.l2world.init.L2World;
import dev.xkmc.l2world.init.data.LWMats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * handles blocks and block entities
 */
public class LWBlocks {

	static {
		L2World.REGISTRATE.creativeModeTab(() -> LWItems.TAB_MAIN);
	}

	public static final BlockEntry<Block> LEAD_BLOCK;
	public static final BlockEntry<LayrootBody> LAYROOT_BODY;
	public static final BlockEntry<LayrootHead> LAYROOT_HEAD;
	public static final BlockEntry<LaylineHead> LAYLINE_HEAD;
	public static final BlockEntry<LaylineChargerBlock> LAYLINE_CHARGER;
	public static final BlockEntry<SlimeCarpet> SLIME_CARPET;
	public static final BlockEntry<WebBlock> SLIME_VINE;

	public static final BlockEntry<DelegateBlock> MAZE_WALL;

	public static final BlockEntry<AnvilBlock> ETERNAL_ANVIL = L2World.REGISTRATE
			.block("eternal_anvil", p -> new AnvilBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL)))
			.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.getEntry(), pvd.models().withExistingParent(ctx.getName(), "anvil")))
			.register();

	public static final BlockEntry<Block>[] GEN_BLOCK = L2World.MATS.genBlockMats(LWMats.values());

	static {
		{
			LEAD_BLOCK = L2World.REGISTRATE.block("lead_block",
							p -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK)))
					.tag(BlockTags.MINEABLE_WITH_PICKAXE)
					.defaultBlockstate().defaultLoot().defaultLang().simpleItem().register();
		}
		{
			BlockBehaviour.Properties prop_root = BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.WOOD)
					.noCollission().instabreak();
			BlockBehaviour.Properties prop_line = BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.WOOD)
					.noCollission().instabreak().randomTicks();
			BlockBehaviour.Properties prop_charger = BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK);
			LAYROOT_HEAD = L2World.REGISTRATE.block("layroot_head", p -> new LayrootHead(prop_root))
					.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.getEntry(),
							pvd.models().cross(ctx.getName(), pvd.blockTexture(ctx.getEntry())).renderType("cutout")))
					.item().model((ctx, pvd) -> pvd.generated(ctx::getEntry, pvd.modLoc("block/" + ctx.getName()))).build()
					.defaultLoot().defaultLang().tag(BlockTags.CLIMBABLE).register();
			LAYROOT_BODY = L2World.REGISTRATE.block("layroot_body", p -> new LayrootBody(prop_root))
					.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.getEntry(),
							pvd.models().cross(ctx.getName(), pvd.blockTexture(ctx.getEntry())).renderType("cutout")))
					.loot((table, block) -> table.dropOther(block, LAYROOT_HEAD.get()))
					.defaultLang().tag(BlockTags.CLIMBABLE).register();
			LAYLINE_HEAD = L2World.REGISTRATE.block("layline_head", p -> new LaylineHead(prop_line))
					.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.getEntry(),
							pvd.models().cross(ctx.getName(), pvd.blockTexture(ctx.getEntry())).renderType("cutout")))
					.item().model((ctx, pvd) -> pvd.generated(ctx::getEntry, pvd.modLoc("block/" + ctx.getName()))).build()
					.defaultLoot().defaultLang().tag(BlockTags.CLIMBABLE).register();
			LAYLINE_CHARGER = L2World.REGISTRATE.block("layline_charger", LaylineChargerBlock::new)
					.tag(BlockTags.MINEABLE_WITH_PICKAXE)
					.defaultBlockstate().simpleItem().defaultLoot().defaultLang().register();
			BlockBehaviour.Properties prop_slime = BlockBehaviour.Properties.of(Material.WEB).noCollission().strength(4.0F);
			SLIME_CARPET = L2World.REGISTRATE.block("slime_carpet", p -> new SlimeCarpet(prop_slime))
					.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.getEntry(), pvd.models().carpet(ctx.getName(), pvd.blockTexture(ctx.getEntry()))))
					.tag(BlockTags.MINEABLE_WITH_HOE)
					.simpleItem().loot(RegistrateBlockLootTables::dropWhenSilkTouch).defaultLang().register();
			SLIME_VINE = L2World.REGISTRATE.block("slime_vine", p -> new WebBlock(prop_slime))
					.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.getEntry(),
							pvd.models().cross(ctx.getName(), pvd.blockTexture(ctx.getEntry())).renderType("cutout")))
					.item().model((ctx, pvd) -> pvd.generated(ctx::getEntry, pvd.modLoc("block/" + ctx.getName()))).build()
					.tag(BlockTags.MINEABLE_WITH_HOE)
					.loot(RegistrateBlockLootTables::dropWhenSilkTouch).defaultLang().register();

		}
		{
			DelegateBlockProperties BP_METAL = DelegateBlockProperties.copy(Blocks.OBSIDIAN).make(BlockBehaviour.Properties::noLootTable);

			MAZE_WALL = L2World.REGISTRATE.block("maze_wall", p -> DelegateBlock.newBaseBlock(BP_METAL,
							MazeWallBlock.NEIGHBOR, MazeWallBlock.ALL_DIRE_STATE))
					.blockstate((ctx, pvd) -> {
						ModelFile in = pvd.models().withExistingParent("maze_block_in", "block/template_single_face")
								.texture("texture", new ResourceLocation(L2World.MODID, "block/maze_block_in"));
						ModelFile out = pvd.models().withExistingParent("maze_block_in", "block/template_single_face")
								.texture("texture", new ResourceLocation(L2World.MODID, "block/maze_block_out"));
						var builder = pvd.getMultipartBuilder(ctx.getEntry());
						BiConsumer<BooleanProperty, Function<
								ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder>,
								ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder>>> func = (prop, axis) -> {
							axis.apply(builder.part().modelFile(in)).addModel().condition(prop, true).end();
							axis.apply(builder.part().modelFile(out)).addModel().condition(prop, false).end();
						};
						func.accept(BlockStateProperties.NORTH, model -> model);
						func.accept(BlockStateProperties.EAST, model -> model.rotationY(90));
						func.accept(BlockStateProperties.SOUTH, model -> model.rotationY(180));
						func.accept(BlockStateProperties.WEST, model -> model.rotationY(270));
						func.accept(BlockStateProperties.UP, model -> model.rotationX(270));
						func.accept(BlockStateProperties.DOWN, model -> model.rotationX(90));
					}).loot((table, block) -> table.accept((rl, b) -> b.build()))
					.tag(BlockTags.WITHER_IMMUNE, BlockTags.DRAGON_IMMUNE)
					.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL)
					.defaultLang().item().model((ctx, pvd) -> pvd.withExistingParent(ctx.getName(), "block/cube_all")
							.texture("all", new ResourceLocation(L2World.MODID, "block/maze_block_out")))
					.build().register();
		}
	}

	public static void register() {
	}

}
