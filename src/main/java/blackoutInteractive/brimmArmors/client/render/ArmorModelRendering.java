package blackoutInteractive.brimmArmors.client.render;

import java.util.Collection;

import com.mojang.blaze3d.vertex.PoseStack;

import blackoutInteractive.brimmArmors.common.items.ArmorPatch;
import blackoutInteractive.brimmArmors.common.items.BrimmArmor;
import blackoutInteractive.ema_08_.rendering.obj.ArmorModelType;
import blackoutInteractive.ema_08_.rendering.obj.ObjModelsManager;
import blackoutInteractive.ema_08_.rendering.obj.metadata.ArmorModelMetadata;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayLocation;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayPos;
import blackoutInteractive.ema_08_.rendering.twoToThreeD.RenderablePngsManager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;

public final class ArmorModelRendering {

    private ArmorModelRendering() {}
    
    @FunctionalInterface
    public static interface IPosTransformer<CTX, POS extends Enum<POS>> {
    	void apply(CTX ctx, POS pos, PoseStack poseStack);
    }

    public static <T> void renderCore(IPosTransformer<T, ArmorModelType> posTransformer, BrimmArmor armor, PoseStack poseStack, MultiBufferSource buff,
            float ticks, int packedLight, int packedOverlay, T ctx) {
        int[] modelIds = armor.getModelIds();
        for (int modelId : modelIds) {
            poseStack.pushPose();
            final var model = ObjModelsManager.get(modelId);
            final ArmorModelMetadata meta = model.getMetadata();
            posTransformer.apply(ctx, meta.type(), poseStack);
            meta.wearing_transform().apply(poseStack);
            model.render(poseStack, buff, packedLight, packedOverlay, ticks);
            poseStack.popPose();
        }
    }

    public static <T> void renderPatches(IPosTransformer<T, OverlayPos> posTransformer, BrimmArmor armor, ItemStack is, PoseStack poseStack,
    		MultiBufferSource buff, float ticks, int packedLight, int packedOverlay, T ctx) {
        ArmorPatch patch = armor.getPatch(is);
        Collection<OverlayLocation> locations = armor.patchesPositions(is);
        if (patch == null || locations.isEmpty()) return;
        for (OverlayLocation loc : locations) {
            poseStack.pushPose();
            posTransformer.apply(ctx, loc.pos(), poseStack);
            loc.localTransform().apply(poseStack);
            RenderablePngsManager.transformOrGet(patch)
                .render(poseStack, buff, packedLight, packedOverlay, ticks);

            poseStack.popPose();
        }
    }

}