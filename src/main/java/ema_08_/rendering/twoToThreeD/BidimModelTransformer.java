package ema_08_.rendering.twoToThreeD;

import java.util.concurrent.ConcurrentHashMap;

import com.mojang.blaze3d.vertex.PoseStack;

import brimmArmors.BrimmArmors;
import ema_08_.rendering.obj.ObjsManager;
import ema_08_.rendering.obj.ObjsManager.BakedObjModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.renderable.CompositeRenderable;
import net.minecraftforge.client.model.renderable.ITextureRenderTypeLookup;
import net.minecraftforge.client.model.renderable.CompositeRenderable.Transforms;

public class BidimModelTransformer {
	
	private static final ResourceLocation
		QUAD_OBJ = new ResourceLocation(BrimmArmors.MOD_ID, "models/obj/quad_base.obj"),
		QUAD_MTL = new ResourceLocation(BrimmArmors.MOD_ID, "models/mtl/quad_base.mtl");
		/*
		 * REMEMBER: THE MTL SHALL NOT DELCARE ANY map_*!
		 */
	
	private static final ConcurrentHashMap<String, Baked2dTexture> cache = new ConcurrentHashMap<>();
	
	private static String hash(ResourceLocation texture) {
		return "@"+texture;
	}
	
	public static Baked2dTexture transform(String textureName, ResourceLocation texture) {
		BakedObjModel model = ObjsManager.loadModel("bakedTexture_"+textureName, QUAD_OBJ, QUAD_MTL, texture); /*load-wo-caching*/
		return new Baked2dTexture(textureName, model);
	}
	
	public static Baked2dTexture transformOrGet(String textureName, ResourceLocation texture) {
		return cache.computeIfAbsent(hash(texture), (hash)->transform(textureName, texture));
	}
	
	public static Baked2dTexture transformOrGet(I3dTextureProvider provider) {
		return transformOrGet(provider.getTextureName(), provider.getTexturePng());
	}
	
	public static final class Baked2dTexture {
		
		private static final ITextureRenderTypeLookup renderType = RenderType::entityCutoutNoCull;
		
		private final CompositeRenderable bakedModel;
		private final String name;
		
		private Baked2dTexture(String name, BakedObjModel model) {
			this.bakedModel = BakedObjModel.getRaw(model);
			this.name = name;
		}
		
		@Override
		public String toString() {
			return "baked2dTextuer:"+this.name;
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

}
