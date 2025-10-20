package ema_08_.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public record SimpleArmorMaterial(
    String name,
    int enchantmentValue,
    SoundEvent equipSound,
    Ingredient repairIngredient,
    float toughness,
    float knockbackResistance,
    int defenseValue,
    int durabilityValue,
    ArmorItem.Type type
) implements ArmorMaterial {
	
	public static SimpleArmorMaterial basic(String name, int enchantmentValue, float toughness,
			float knockbackResistance, int defenseValue, int durabilityValue, ArmorItem.Type type) {
		return new SimpleArmorMaterial(name, enchantmentValue, SoundEvents.ARMOR_EQUIP_GENERIC, null,
				toughness, knockbackResistance, defenseValue, durabilityValue, type);
	}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type t) {
    	if (type != t) throw new IllegalStateException("Unexpected armor type ("+t+") for this material");
        return defenseValue;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type t) {
    	if (type != t) throw new IllegalStateException("Unexpected armor type ("+t+") for this material");
        return durabilityValue;
    }
}
