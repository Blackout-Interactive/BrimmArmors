package blackoutInteractive.ema_08_.rendering.obj.modelsHolders;

import java.util.Objects;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.client.model.renderable.CompositeRenderable;
import net.minecraftforge.client.model.renderable.ITextureRenderTypeLookup;
import net.minecraftforge.client.model.renderable.CompositeRenderable.Transforms;

public final class LoadedModel extends ModelMetadataHolder {
	
	private final CompositeRenderable bakedModel;
	private final ITextureRenderTypeLookup renderType;
	
	public <T> LoadedModel(CompositeRenderable bm, ITextureRenderTypeLookup rt, T m) {
		super(m);
		bakedModel = Objects.requireNonNull(bm, "Missing baked model");
		renderType = Objects.requireNonNull(rt, "Missing render type");
	}
	
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, float partialTicks) {
		this.bakedModel.render(
				poseStack,
				bufferSource,
				renderType,
				combinedLight,
				combinedOverlay,
				partialTicks,
				Transforms.EMPTY
			);
	}

}
