package blackoutInteractive.ema_08_.items.effectsProvidingArmors;

import net.minecraft.world.effect.MobEffect;

@Deprecated
public record StandardApplicableEffect(
		int amplifier,
		MobEffect effect
		) implements IAmplifiableApplicableEffect {}
