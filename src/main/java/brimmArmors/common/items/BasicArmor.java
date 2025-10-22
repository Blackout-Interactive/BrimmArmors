package brimmArmors.common.items;

import ema_08_.geom.models.RTSMatricesCompound;
import ema_08_.items.SimpleArmorMaterial;
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

public class BasicArmor extends ArmorItem implements IDefaultObjModelProvider {

    private final ConcordRarity rarity;
    private final String unlocName;
    private final ModelType modelType;
    private final RTSMatricesCompound transformations;

    public BasicArmor(String unlocName, ArmorItem.Type type, ConcordRarity rarity, SimpleArmorMaterial material,
    		RTSMatricesCompound transformations) {
        super(material, type, new Properties().durability(material.durabilityValue()));
        this.rarity = rarity;
        this.unlocName = unlocName;
        this.modelType = ModelType.ofArmor(type);
        this.transformations = transformations;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(rarity.applyFormatting(super.getName(stack).getString()));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltipList, TooltipFlag flag) {
        tooltipList.add(Component.literal(rarity.applyFormatting("\u00A7o" + I18n.get("tooltip." + BrimmArmors.MOD_ID + "." + unlocName))));
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
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
