package blackoutInteractive.brimmArmors.common.configurations;

import java.util.Optional;

public record MaterialOverrides(
		Optional<Float> toughness,
	    Optional<Float> knockbackResistance,
	    Optional<Integer> defenseValue,
	    Optional<Integer> durabilityValue
	) {}
