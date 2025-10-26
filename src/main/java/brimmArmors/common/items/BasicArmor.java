package brimmArmors.common.items;

import ema_08_.items.SimpleArmorMaterial;
import ema_08_.rendering.geom.RTSMatricesCompound;
import ema_08_.rendering.obj.IDefaultObjModelProvider;
import ema_08_.rendering.obj.ModelType;
import ema_08_.rendering.overlay.OverlayLocation;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class BasicArmor extends ArmorItem implements IDefaultObjModelProvider {
	
	private static final ConcurrentHashMap<String, ArmorPatch> patch_cache = new ConcurrentHashMap<>();
	private static final Function<String, ArmorPatch> cache_computator = (key) -> {
		var result = ItemRegistry.get(key);
		if (!(result instanceof ArmorPatch))
			throw new IllegalArgumentException(key+" is the name of a "+result.getClass().getName()+", not of an armor patch");
		else
			return (ArmorPatch)result;
	};

    private final ConcordRarity rarity;
    private final String unlocName;
    private final ModelType modelType;
    private final RTSMatricesCompound transformations;
    private final Collection<OverlayLocation> patchesPositions;

    public BasicArmor(String unlocName, ArmorItem.Type type, ConcordRarity rarity, SimpleArmorMaterial material,
    		RTSMatricesCompound transformations, Collection<OverlayLocation> patchesPositions) {
        super(material, type, new Properties().durability(material.durabilityValue()));
        this.rarity = rarity;
        this.unlocName = unlocName;
        this.modelType = ModelType.ofArmor(type);
        this.transformations = transformations;
        this.patchesPositions = Collections.unmodifiableCollection(patchesPositions);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(rarity.applyFormatting(super.getName(stack).getString()));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltipList, TooltipFlag flag) {
    	String tooltipRaw = I18n.get("tooltip." + BrimmArmors.MOD_ID + "." + unlocName);
    	if (!tooltipRaw.isBlank())
    		tooltipList.add(Component.literal(rarity.applyFormatting("\u00A7o" + tooltipRaw)));
        ArmorPatch patch = getPatch(stack);
        if (patch != null)
        	tooltipList.add(Component.literal(I18n.get("tooltip." + BrimmArmors.MOD_ID + ".armors.current_patch")+": "+
        			I18n.get("item." + BrimmArmors.MOD_ID + "." + patch.getPatchName())));
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
              return new ConcordArmorRender(root, stack);
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
	public RTSMatricesCompound getModelTransformations() {
		return this.transformations;
	}
	
	public Collection<OverlayLocation> patchesPositions() {
		return this.patchesPositions;
	}
	
	public void setPatch(ItemStack is, ArmorPatch patch) {
		is.getOrCreateTag().putString("patch", patch.getPatchName());
	}
	
	public void removePatch(ItemStack is) {
		is.getOrCreateTag().remove("patch");
	}
	
	public ArmorPatch getPatch(ItemStack is) {
		var tag = is.getOrCreateTag();
		return tag.contains("patch") ?
				patch_cache.computeIfAbsent(tag.getString("patch"), cache_computator) : null;
	}
	
}
