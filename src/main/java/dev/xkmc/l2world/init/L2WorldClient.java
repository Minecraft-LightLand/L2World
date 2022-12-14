package dev.xkmc.l2world.init;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class L2WorldClient {

	public static void onCtorClient(IEventBus bus, IEventBus eventBus) {
		bus.addListener(L2WorldClient::clientSetup);
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		L2WorldClient.registerItemProperties();
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerItemProperties() {
	}

}
