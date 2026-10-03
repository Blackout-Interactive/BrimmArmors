package blackoutInteractive.ema_08_.items.effectsProvidingArmors;

import net.minecraft.world.effect.MobEffect;

@Deprecated
public interface IEffectProvidingArmor {
	
	IAmplifiableApplicableEffect[] getAddedOnWear();
	
	MobEffect[] getPreventedOnWear();
	
}
