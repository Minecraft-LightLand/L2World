package dev.xkmc.l2world.init.registrate;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LWParticle {

	//public static final RegistryEntry<SimpleParticleType> EMERALD = L2World.REGISTRATE.simple("emerald", ForgeRegistries.Keys.PARTICLE_TYPES, () -> new SimpleParticleType(false));

	public static void register() {

	}

	@OnlyIn(Dist.CLIENT)
	public static void registerClient() {
		//Minecraft.getInstance().particleEngine.register(EMERALD.get(), new EmeraldParticle.Factory());
	}

}
