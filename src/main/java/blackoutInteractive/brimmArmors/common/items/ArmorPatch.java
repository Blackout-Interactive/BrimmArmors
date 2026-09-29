package blackoutInteractive.brimmArmors.common.items;

import java.util.List;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.ema_08_.rendering.twoToThreeD.IDefaultPatchesRenderablePngProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ArmorPatch extends Item implements IDefaultPatchesRenderablePngProvider {
	
	private final String unlocName;

	public ArmorPatch(String unlocName) {
		super(new Properties());
		this.unlocName = unlocName;
	}
	
	public String getPatchName() {
		return this.unlocName;
	}

	@Override
	public String getTextureName() {
		return this.unlocName;
	}
	
	@Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltipList, @NotNull TooltipFlag flag) {
    	String tooltipRaw = I18n.get("tooltip." + BrimmArmors.MOD_ID + "." + unlocName);
    	if (!tooltipRaw.isBlank())
    		tooltipList.add(Component.literal(tooltipRaw));
    }
	
}
