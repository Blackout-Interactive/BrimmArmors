package blackoutInteractive.brimmArmors.client.render;

import java.util.Collection;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import blackoutInteractive.brimmArmors.common.items.ArmorPatch;
import blackoutInteractive.brimmArmors.common.items.BrimmArmor;
import blackoutInteractive.ema_08_.rendering.geom.RTSMatricesCompound;
import blackoutInteractive.ema_08_.rendering.obj.ObjModelReference;
import blackoutInteractive.ema_08_.rendering.obj.ObjsManager;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayLocation;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayPos;
import blackoutInteractive.ema_08_.rendering.twoToThreeD.RenderablePngsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class BrimmArmorRender extends HumanoidModel<LivingEntity> {

    protected final BrimmArmor armor;
    protected final ItemStack is;

    public BrimmArmorRender(ModelPart root, ItemStack stack) {
        super(root);
        this.armor = (BrimmArmor) stack.getItem();
        this.is = stack;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
    	
    	var buff = Minecraft.getInstance().renderBuffers().bufferSource();
    	float ticks = Minecraft.getInstance().getPartialTick();
    	
    	ObjModelReference[] modelRefs = armor.getModelRefs();
    	
    	for (ObjModelReference modelRef : modelRefs) {
    		
    		poseStack.pushPose();
    		
    		switch(modelRef.modelType) {
    		case ARMOR_HELMET: {
        		this.head.translateAndRotate(poseStack);
        		break;
        	}
        	case ARMOR_CHESTPLATE: {
        		this.body.translateAndRotate(poseStack);
        		break;
        	}
        	default: throw new IllegalStateException("Invalid model type for armor "+armor.unlocName+": "+modelRef.modelType);
        	}
    		
    		modelRef.modelTransforms.applyIfPresent(RTSMatricesCompound.key_armor_render, poseStack);
    		
    		ObjsManager.getModel(modelRef)
        		.render(poseStack, buff, packedLight, packedOverlay, ticks);
  
    		poseStack.popPose();
    		
    	}
        
        ArmorPatch patch = armor.getPatch(is);
        Collection<OverlayLocation> locations = armor.patchesPositions(this.is);
        if (patch == null || locations.isEmpty()) return;
        
        for (OverlayLocation loc : locations) {
        	poseStack.pushPose();
        	
        	OverlayPos pos = loc.pos();
        	switch(pos) {
        	case HUMANOID_HEAD: {
        		this.head.translateAndRotate(poseStack);
        		break;
        	}
        	case HUMANOID_TORSO: {
        		this.body.translateAndRotate(poseStack);
        		break;
        	}
        	default: throw new IllegalStateException("Invalid overlay position for armor "+armor.unlocName+": "+pos);
        	}
        	loc.localTransform().apply(poseStack);
        	
        	RenderablePngsManager.transformOrGet(patch)
        		.render(poseStack, buff, packedLight, packedOverlay, ticks);
        	
        	poseStack.popPose();
        }
        
    }
    
}
