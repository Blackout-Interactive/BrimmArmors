package brimmArmors.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import brimmArmors.common.items.BasicArmor;
import ema_08_.geom.models.RTSMatricesCompound;
import ema_08_.trivialForgeObjWrapper.ModelType;
import ema_08_.trivialForgeObjWrapper.ObjsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public class ConcordArmorRender extends HumanoidModel<LivingEntity> {

    protected final BasicArmor armor;

    public ConcordArmorRender(ModelPart root, BasicArmor armor) {
        super(root);
        this.armor = armor;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        poseStack.pushPose();

        if (armor.getModelType() == ModelType.ARMOR_CHESTPLATE) {
            this.body.translateAndRotate(poseStack);
        }
        if (armor.getModelType() == ModelType.ARMOR_HELMET) {
            this.head.translateAndRotate(poseStack);
        }

        armor.getTransformations().applyIfPresent(RTSMatricesCompound.key_armor_render, poseStack);
        ObjsManager.getModel(armor)
        	.render(poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), packedLight, packedOverlay);
        
        poseStack.popPose();
    }
}
