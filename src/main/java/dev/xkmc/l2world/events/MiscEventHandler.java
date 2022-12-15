package dev.xkmc.l2world.events;

import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2world.init.registrate.LWEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class MiscEventHandler {

	@SubscribeEvent
	public static void onTargetSet(LivingChangeTargetEvent event) {
		if (event.getNewTarget() != null && (event.getEntity().hasEffect(LWEffects.T_CLEAR.get()) ||
				event.getNewTarget().hasEffect(LWEffects.T_HIDE.get()))) {
			((Mob) event.getEntity()).setTarget(null);
		}
	}

	@SubscribeEvent
	public static void onVisibilityGet(LivingEvent.LivingVisibilityEvent event) {
		if (event.getEntity().hasEffect(LWEffects.T_HIDE.get()))
			event.modifyVisibility(0);
	}

	@SubscribeEvent
	public static void onEntityKnockBack(LivingKnockBackEvent event) {
		if (event.getEntity().hasEffect(LWEffects.NO_KB.get()))
			event.setCanceled(true);
	}


	@SubscribeEvent
	public static void onPotionTest(MobEffectEvent.Applicable event) {
		boolean flag = event.getEntity().hasEffect(LWEffects.CLEANSE.get());
		flag |= event.getEntity().hasEffect(LWEffects.DISPELL.get());
		if (flag) {
			if (event.getEffectInstance().getEffect() instanceof InherentEffect)
				return;
			if (EffectUtil.getReason() == EffectUtil.AddReason.FORCE)
				return;
			if (EffectUtil.getReason() == EffectUtil.AddReason.SELF)
				return;
			if (EffectUtil.getReason() == EffectUtil.AddReason.SKILL)
				return;
			if (event.getEffectInstance().getEffect() == LWEffects.CLEANSE.get())
				return;
			if (event.getEffectInstance().getEffect() == LWEffects.DISPELL.get())
				return;
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent
	public static void onPotionAdded(MobEffectEvent.Added event) {
		boolean flag = event.getEffectInstance().getEffect() == LWEffects.CLEANSE.get();
		flag |= event.getEffectInstance().getEffect() == LWEffects.DISPELL.get();
		if (flag) {
			List<MobEffectInstance> list = new ArrayList<>(event.getEntity().getActiveEffects());
			for (MobEffectInstance ins : list) {
				if (ins.getEffect() instanceof InherentEffect)
					continue;
				if (ins.getEffect() == LWEffects.CLEANSE.get())
					continue;
				if (ins.getEffect() == LWEffects.DISPELL.get())
					continue;
				event.getEntity().removeEffect(ins.getEffect());
			}
		}
	}

}
