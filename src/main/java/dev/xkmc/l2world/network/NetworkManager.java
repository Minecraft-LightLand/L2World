package dev.xkmc.l2world.network;

import dev.xkmc.l2library.serial.config.ConfigMerger;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.PacketHandlerWithConfig;
import dev.xkmc.l2world.content.questline.mobs.swamp.SlimeProperties;
import dev.xkmc.l2world.init.L2Foundation;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.stream.Stream;

public class NetworkManager {

	static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(L2Foundation.MODID, "main"), 1, "l2world_config"
	);

	public static Stream<Map.Entry<String, BaseConfig>> getConfigs(ConfigType type) {
		return HANDLER.getConfigs(type.getID());
	}

	public static <T extends BaseConfig> T getConfig(ConfigType type) {
		return HANDLER.getCachedConfig(type.getID());
	}

	public static void register() {
		HANDLER.addCachedConfig(ConfigType.POTION_SLIME_DROP.getID(), new ConfigMerger<>(SlimeProperties.class));
	}

}
