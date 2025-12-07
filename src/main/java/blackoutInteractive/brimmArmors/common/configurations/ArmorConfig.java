package blackoutInteractive.brimmArmors.common.configurations;

import java.util.Optional;

import javax.annotation.Nullable;

import blackoutInteractive.brimmArmors.common.items.BrimmRarity;

public record ArmorConfig(
		@Nullable MaterialOverrides materialOverrides,
		@Nullable Optional<BrimmRarity> rarityOverride
		) {
	
	public static final ArmorConfig EMPTY = new ArmorConfig(null, null);
	
}
