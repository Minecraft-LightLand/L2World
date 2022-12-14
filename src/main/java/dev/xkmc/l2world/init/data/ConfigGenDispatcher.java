package dev.xkmc.l2world.init.data;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.ConfigDataProvider;
import dev.xkmc.l2world.network.ConfigType;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class ConfigGenDispatcher extends ConfigDataProvider {

	public ConfigGenDispatcher(DataGenerator generator) {
		super(generator, "data/l2world/l2world_config/", "L2World Json Config Generator");
	}

	@Override
	public void add(Map<String, BaseConfig> map) {
		SlimeConfigGen.addPotionDrop((id, config) -> map.put(ConfigType.POTION_SLIME_DROP.getID() + "/" + id, config));
	}

}
