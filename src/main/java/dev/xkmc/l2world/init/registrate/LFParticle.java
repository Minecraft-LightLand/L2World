package dev.xkmc.l2world.init.registrate;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LFParticle {

	//public static final RegistryEntry<SimpleParticleType> EMERALD = L2Foundation.REGISTRATE.simple("emerald", ForgeRegistries.Keys.PARTICLE_TYPES, () -> new SimpleParticleType(false));

	public static void register() {

	}

	@OnlyIn(Dist.CLIENT)
	public static void registerClient() {
		//Minecraft.getInstance().particleEngine.register(EMERALD.get(), new EmeraldParticle.Factory());
	}

}
