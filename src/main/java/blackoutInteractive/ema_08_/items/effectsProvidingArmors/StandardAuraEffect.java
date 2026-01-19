package blackoutInteractive.ema_08_.items.effectsProvidingArmors;

import net.minecraft.world.effect.MobEffect;

public record StandardAuraEffect(
		int rangeBlocks,
		int amplifier,
		MobEffect effect
		) implements IAuraEffect {}
