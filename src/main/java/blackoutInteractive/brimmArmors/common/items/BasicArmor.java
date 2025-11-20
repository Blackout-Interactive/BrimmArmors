package blackoutInteractive.brimmArmors.common.items;

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
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.client.render.ConcordArmorRender;
import blackoutInteractive.brimmArmors.common.registries.ItemRegistry;
import blackoutInteractive.ema_08_.items.SimpleArmorMaterial;
import blackoutInteractive.ema_08_.rendering.geom.MatrixRTS;
import blackoutInteractive.ema_08_.rendering.geom.RTSMatricesCompound;
import blackoutInteractive.ema_08_.rendering.obj.IDefaultObjModelProvider;
import blackoutInteractive.ema_08_.rendering.obj.ModelType;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayLocation;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayPos;

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
		if (result.isEmpty()) return null;
		var item = result.get();
		if (!(item instanceof ArmorPatch))
			throw new IllegalArgumentException(key+" is the name of a "+item.getClass().getName()+", not of an armor patch");
		else
			return (ArmorPatch)item;
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
	
	public Collection<OverlayLocation> patchesPositions(ItemStack is) {
		if (is.hasTag() && is.getTag().contains("debug-patchPosOverride") && is.getTag().contains("debug-overlayPosOverride")) {
			int[] matrixSer = is.getTag().getIntArray("debug-patchPosOverride");
			OverlayPos pos = OverlayPos.values()[is.getTag().getInt("debug-overlayPosOverride")];
			return List.of(new OverlayLocation(pos, MatrixRTS
					.getMatrix(Float.intBitsToFloat(matrixSer[0]), Float.intBitsToFloat(matrixSer[1]), Float.intBitsToFloat(matrixSer[2]),
							   Float.intBitsToFloat(matrixSer[3]), Float.intBitsToFloat(matrixSer[4]), Float.intBitsToFloat(matrixSer[5]),
							   Float.intBitsToFloat(matrixSer[6]), Float.intBitsToFloat(matrixSer[7]), Float.intBitsToFloat(matrixSer[8]),
							   false)));
		} else {
			return this.patchesPositions;
		}
	}
	
	public void setPatch(ItemStack is, ArmorPatch patch) {
		is.getOrCreateTag().putString("patch", patch.getPatchName());
	}
	
	public void removePatch(ItemStack is) {
		is.getOrCreateTag().remove("patch");
	}
	
	public ArmorPatch getPatch(ItemStack is) {
		var tag = is.getOrCreateTag();
		if (!tag.contains("patch")) return null;
		var patch = patch_cache.computeIfAbsent(tag.getString("patch"), cache_computator);
		if (patch == null) {
			BrimmArmors.LOGGER.warn("Could not retrieve currently set patch named "+tag.getString("patch")+" for "+
					ForgeRegistries.ITEMS.getKey(is.getItem())+", resetting patch tag.");
			removePatch(is);
			return null;
		} else {
			return patch;
		}
	}
	
	public void setPatchesDebugOverride(ItemStack is, MatrixRTS matrix, OverlayPos pos) {
		int[] matrixSer = new int[] {
				Float.floatToIntBits(matrix.trX), Float.floatToIntBits(matrix.trY), Float.floatToIntBits(matrix.trZ),
				Float.floatToIntBits(matrix.rtX), Float.floatToIntBits(matrix.rtY), Float.floatToIntBits(matrix.rtZ),
				Float.floatToIntBits(matrix.scX), Float.floatToIntBits(matrix.scY), Float.floatToIntBits(matrix.scZ)
				
		};
		is.getOrCreateTag().putIntArray("debug-patchPosOverride", matrixSer);
		is.getOrCreateTag().putInt("debug-overlayPosOverride", pos.ordinal());
	}
	
	public void removePatchesDebugOverride(ItemStack is) {
		is.getOrCreateTag().remove("debug-patchPosOverride");
		is.getOrCreateTag().remove("debug-overlayPosOverride");
	}
	
}
