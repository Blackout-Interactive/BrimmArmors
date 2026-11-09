package blackoutInteractive.brimmArmors.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import blackoutInteractive.brimmArmors.common.items.BrimmArmor;
import blackoutInteractive.ema_08_.rendering.obj.ArmorModelType;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class BrimmArmorRender extends HumanoidModel<LivingEntity> {

    protected final BrimmArmor armor;
    protected final ItemStack is;

    public static BrimmArmorRender create(ItemStack stack) {
        return new BrimmArmorRender(EmptyRoot.ROOT, stack);
    }

    private BrimmArmorRender(ModelPart root, ItemStack stack) {
        super(root);
        this.armor = (BrimmArmor) stack.getItem();
        this.is = stack;
    }

    private static final class EmptyRoot {
        static final ModelPart ROOT;
        static {
        	MeshDefinition mesh = new MeshDefinition();
            PartDefinition root = mesh.getRoot();
            CubeListBuilder none = CubeListBuilder.create();
            root.addOrReplaceChild("head", none, PartPose.ZERO);
            root.addOrReplaceChild("hat", none, PartPose.ZERO);
            root.addOrReplaceChild("body", none, PartPose.ZERO);
            root.addOrReplaceChild("right_arm", none, PartPose.offset(-5.0F, 2.0F, 0.0F));
            root.addOrReplaceChild("left_arm", none, PartPose.offset(5.0F, 2.0F, 0.0F));
            root.addOrReplaceChild("right_leg", none, PartPose.offset(-1.9F, 12.0F, 0.0F));
            root.addOrReplaceChild("left_leg", none, PartPose.offset(1.9F, 12.0F, 0.0F));
            ROOT = LayerDefinition.create(mesh, 64, 32).bakeRoot();
            
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        final var buff = Minecraft.getInstance().renderBuffers().bufferSource();
        final float ticks = Minecraft.getInstance().getPartialTick();
        ArmorModelRendering.renderCore(BrimmArmorRender::transformByPos, armor, poseStack, buff, ticks, packedLight, packedOverlay, this);
        ArmorModelRendering.renderPatches(BrimmArmorRender::transformByPos, armor, is, poseStack, buff, ticks, packedLight, packedOverlay, this);
    }

    private static void transformByPos(BrimmArmorRender renderer, ArmorModelType pos, PoseStack poseStack) {
        switch (pos) {
        case HELMET -> renderer.head.translateAndRotate(poseStack);
        case CHESTPLATE -> renderer.body.translateAndRotate(poseStack);
        case BOOTS_R, LEGGINGS_R -> renderer.rightLeg.translateAndRotate(poseStack);
        case BOOTS_L, LEGGINGS_L -> renderer.leftLeg.translateAndRotate(poseStack);
        default -> throw new IllegalStateException("Invalid model type for armor " + renderer.armor.unlocName + ": " + pos);
        }
    }

    private static void transformByPos(BrimmArmorRender renderer, OverlayPos pos, PoseStack poseStack) {
        switch (pos) {
        case HUMANOID_HEAD -> renderer.head.translateAndRotate(poseStack);
        case HUMANOID_TORSO -> renderer.body.translateAndRotate(poseStack);
        default -> throw new IllegalStateException("Invalid overlay position for armor " + renderer.armor.unlocName + ": " + pos);
        }
    }

}