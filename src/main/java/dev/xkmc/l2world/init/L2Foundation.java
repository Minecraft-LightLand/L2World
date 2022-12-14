package dev.xkmc.l2world.init;

import dev.xkmc.l2complements.init.data.GenItem;
import dev.xkmc.l2world.events.MiscEventHandler;
import dev.xkmc.l2world.init.data.ConfigGenDispatcher;
import dev.xkmc.l2world.init.data.LangData;
import dev.xkmc.l2world.init.data.RecipeGen;
import dev.xkmc.l2world.init.registrate.*;
import dev.xkmc.l2world.init.worldgenreg.StructureRegistrate;
import dev.xkmc.l2world.init.worldgenreg.WorldGenRegistrate;
import dev.xkmc.l2world.network.NetworkManager;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.base.effects.EffectSyncEvents;
import dev.xkmc.l2library.repack.registrate.providers.ProviderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(L2Foundation.MODID)
public class L2Foundation {

	public static final String MODID = "l2world";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	public static final GenItem MATS = new GenItem(MODID, REGISTRATE);

	private static void registerRegistrates() {
		ForgeMod.enableMilkFluid();
		LFBlocks.register();
		LFEntities.register();
		LFItems.register();
		LFRecipes.register();
		LFEffects.register();
		LFParticle.register();
		WorldGenRegistrate.register();
		StructureRegistrate.register();
		NetworkManager.register();
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
	}

	private static void registerForgeEvents() {
		MinecraftForge.EVENT_BUS.register(MiscEventHandler.class);
	}

	private static void registerModBusEvents(IEventBus bus) {
		bus.addListener(L2Foundation::setup);
		bus.addListener(EventPriority.LOWEST, L2Foundation::gatherData);
		bus.addListener(L2Foundation::onParticleRegistryEvent);
		bus.addListener(LFEntities::registerEntityAttributes);
	}

	public L2Foundation() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerModBusEvents(bus);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> L2FoundationClient.onCtorClient(bus, MinecraftForge.EVENT_BUS));
		registerRegistrates();
		registerForgeEvents();
	}

	private static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EffectSyncEvents.TRACKED.add(LFEffects.WATER_TRAP.get());
			LFEffects.registerBrewingRecipe();
		});
		StructureRegistrate.commonSetup(event);
	}

	public static void gatherData(GatherDataEvent event) {
		LangData.addTranslations(REGISTRATE::addRawLang);
		event.getGenerator().addProvider(true, new ConfigGenDispatcher(event.getGenerator()));
	}

	public static void onParticleRegistryEvent(RegisterParticleProvidersEvent event) {
		LFParticle.registerClient();
	}

}
