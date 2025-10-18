package brimmArmors.common.items;

import ema_08_.geom.models.RTSMatricesCompound;
import ema_08_.trivialForgeObjWrapper.IDefaultObjModelProvider;
import ema_08_.trivialForgeObjWrapper.ModelType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import brimmArmors.BrimmArmors;
import brimmArmors.client.render.ConcordArmorRender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BasicArmor extends ArmorItem implements IRarity, IDefaultObjModelProvider {

    public final EquipmentSlot type;
    private final ConcordRarity rarity;
    private final String tooltip;
    private final String unlocName;
    private final ModelType modelType;
    private final RTSMatricesCompound transformations;

    /**
     * Defense and durability must be passed explicitly now, since ArmorMaterial no longer provides them per slot.
     */
    public BasicArmor(String unlocName, EquipmentSlot type, ConcordRarity rarity, ConcordArmorMaterial material,
                      int defense, int durability, RTSMatricesCompound transformations) {
        // Create ArmorItem.Properties with durability manually set per item
        super(material, equipmentSlotToArmorType(type), new Properties().durability(durability));
        this.type = type;
        this.rarity = rarity;
        this.tooltip = unlocName;
        this.unlocName = unlocName;
        if (type == EquipmentSlot.CHEST) {
            this.modelType = ModelType.ARMOR_CHESTPLATE;
        } else if (type == EquipmentSlot.HEAD) {
        	this.modelType = ModelType.ARMOR_HELMET;
        } else {
            throw new IllegalArgumentException("Unsupported equipment slot "+type);
        }
        this.transformations = transformations;
    }

    public static ArmorItem.Type equipmentSlotToArmorType(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> ArmorItem.Type.HELMET;
            case CHEST -> ArmorItem.Type.CHESTPLATE;
            case LEGS -> ArmorItem.Type.LEGGINGS;
            case FEET -> ArmorItem.Type.BOOTS;
            default -> throw new IllegalArgumentException("Invalid EquipmentSlot: " + slot);
        };
    }

    @Override
    public Component getName(ItemStack stack) {
        // Apply rarity color to the name
        return Component.literal(rarity.color + super.getName(stack).getString());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltipList, TooltipFlag flag) {
        tooltipList.add(Component.literal(rarity.color + "\u00A7o" + I18n.get("tooltip." + BrimmArmors.MOD_ID + "." + tooltip)));
    }

    public ConcordRarity getRarity() {
        return rarity;
    }
    
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
            @NotNull
            public HumanoidModel<?> getHumanoidArmorModel(
            		LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
              ModelPart root = new ModelPart(new ArrayList<>(),
            		  Map.of("head", defaultModel.head,
            				 "hat", defaultModel.hat,
            				 "body", defaultModel.body,
            				 "right_arm", defaultModel.rightArm,
            				 "left_arm", defaultModel.leftArm,
            				 "right_leg", defaultModel.rightLeg,
            				 "left_leg", defaultModel.leftLeg));
              return new ConcordArmorRender(root, BasicArmor.this);
            }
          }
      );
    }

	@Override
	public String getModelName() {
		return this.unlocName;
	}

	@Override
	public ModelType getModelType() {
		return this.modelType;
	}

	@Override
	public RTSMatricesCompound getTransformations() {
		return this.transformations;
	}
}
