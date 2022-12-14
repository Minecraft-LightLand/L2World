package dev.xkmc.l2world.content.questline.mobs.swamp;

import dev.xkmc.l2world.init.L2Foundation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public class PotionSlimeRenderer extends SlimeRenderer {

	public PotionSlimeRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Slime slime) {
		if (slime instanceof PotionSlime potion) {
			SlimeProperties.SlimeConfig config = potion.getConfig();
			if (config.id != null && config.id.length() > 0) {
				return new ResourceLocation(L2Foundation.MODID, "textures/entity/slime/" + config.id + ".png");
			}
		}
		return super.getTextureLocation(slime);
	}
}
