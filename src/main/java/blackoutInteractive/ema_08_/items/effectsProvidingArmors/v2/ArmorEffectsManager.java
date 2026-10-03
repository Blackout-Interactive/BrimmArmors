package blackoutInteractive.ema_08_.items.effectsProvidingArmors.v2;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ArmorEffectsManager {
	
	/*
	 * Removals are authoritative on additions, no matter amplification of the effect.
	 */
	
	private ArmorEffectsManager() {}
	
	
	@SubscribeEvent
	public static void onEffectApplication(MobEffectEvent.Applicable event) {
		if (!(event.getEntity() instanceof ServerPlayer player)) return;
		for (ItemStack stack : player.getInventory().armor) {
			if (stack.getItem() instanceof IEffectsProvider ep &&
				ep.getEffectsSetup().remOnWear().contains(event.getEffectInstance().getEffect())) {
				event.setResult(Result.DENY);
				return;
			}
		}
	}
	
	@SubscribeEvent
	public static void onEffectRemotion(MobEffectEvent.Remove event) {
		if (!(event.getEntity() instanceof ServerPlayer player)) return;
		MobEffectInstance effectInstance = event.getEffectInstance();
		/*Best-effort to retrieve the current instance, if still null then no effect is really being removed*/
		if (effectInstance == null) effectInstance = player.getEffect(event.getEffect());
		int amplifier;
		/*i.e. if it could be an effect given by this very manager*/
		if (effectInstance != null && effectInstance.isInfiniteDuration()
				&& (amplifier = effectInstance.getAmplifier()) >= 0 && amplifier <= 4) {
			final MobEffect effect = effectInstance.getEffect();
			final ArmorEffectsSetup.AmplifiedEffect effectAmplified =
				new ArmorEffectsSetup.AmplifiedEffect(effect, amplifier);
			boolean toCancel = false;
			for (ItemStack stack : player.getInventory().armor) {
				if (stack.getItem() instanceof IEffectsProvider ep) {
					if (ep.getEffectsSetup().remOnWear().contains(effect)) return;
					if (!toCancel && ep.getEffectsSetup().addOnWear().contains(effectAmplified)) toCancel = true;
				}
			}
			if (toCancel) event.setCanceled(true);
		}
	}
		
	@SubscribeEvent
	public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
	    if (!(event.getEntity() instanceof ServerPlayer player)
	            || event.getSlot().getType() != EquipmentSlot.Type.ARMOR) return;
	    Item f = event.getFrom().getItem();
	    Item t = event.getTo().getItem();
	    if (f == t) return;
	    if (f instanceof IEffectsProvider fromp)
	        for (var old : fromp.getEffectsSetup().addOnWear())
	            if (player.hasEffect(old.effect())) player.removeEffect(old.effect());
	    /*Don't remember why, but I'm quite sure there is a reason to adding everything back once again*/
	    for (ItemStack stack : player.getInventory().armor)
	        if (stack.getItem() instanceof IEffectsProvider ep)
	            for (var e : ep.getEffectsSetup().addOnWear())
	                player.addEffect(new MobEffectInstance(e.effect(), MobEffectInstance.INFINITE_DURATION,
	                        e.amplifier(), false, false, true));
	    if (t instanceof IEffectsProvider top)
	        for (MobEffect e : top.getEffectsSetup().remOnWear())
	            if (player.hasEffect(e)) player.removeEffect(e);
	}

}
