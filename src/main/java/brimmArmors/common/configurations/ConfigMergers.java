package brimmArmors.common.configurations;

import java.util.Optional;

import brimmArmors.common.items.ConcordRarity;
import ema_08_.items.SimpleArmorMaterial;
import net.minecraft.world.item.ArmorItem;

public class ConfigMergers {
	
	private static final MaterialOverrides EMPTY_MATERIALS_OVERRIDE =
			new MaterialOverrides(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	
	public static SimpleArmorMaterial mergeBasicMaterial(String name, float toughness,
			float knockbackResistance, int defenseValue, int durabilityValue, ArmorItem.Type type, MaterialOverrides overrides) {
		overrides = overrides == null ? EMPTY_MATERIALS_OVERRIDE : overrides;
		return SimpleArmorMaterial.basic(name,
				overrides.toughness().orElse(toughness),
				overrides.knockbackResistance().orElse(knockbackResistance),
				overrides.defenseValue().orElse(defenseValue),
				overrides.durabilityValue().orElse(durabilityValue),
				type
			);
	}
	
	public static ConcordRarity mergeRarity(ConcordRarity rarity, Optional<ConcordRarity> override) {
		override = override == null ? Optional.empty() : override;
		return override.orElse(rarity);
	}

}
