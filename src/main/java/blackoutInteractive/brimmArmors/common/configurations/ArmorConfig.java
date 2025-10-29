package blackoutInteractive.brimmArmors.common.configurations;

import java.util.Optional;

import javax.annotation.Nullable;

import blackoutInteractive.brimmArmors.common.items.ConcordRarity;

public record ArmorConfig(
		@Nullable MaterialOverrides materialOverrides,
		@Nullable Optional<ConcordRarity> rarityOverride
		) {
	
	public static final ArmorConfig EMPTY = new ArmorConfig(null, null);
	
}
