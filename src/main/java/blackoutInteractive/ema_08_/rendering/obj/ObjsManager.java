package blackoutInteractive.ema_08_.rendering.obj;

import java.util.concurrent.ConcurrentHashMap;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.obj.ObjLoader;
import net.minecraftforge.client.model.obj.ObjModel;
import net.minecraftforge.client.model.obj.ObjModel.ModelSettings;
import net.minecraftforge.client.model.renderable.CompositeRenderable;
import net.minecraftforge.client.model.renderable.ITextureRenderTypeLookup;
import net.minecraftforge.client.model.renderable.CompositeRenderable.Transforms;

public class ObjsManager {
	
	private static final ConcurrentHashMap<String, BakedObjModel> cache = new ConcurrentHashMap<>();
	
	private static String hash(ResourceLocation obj, ResourceLocation texture) {
		return obj+"@"+texture;
	}
	
	public static BakedObjModel loadModel(String modelName, ResourceLocation obj, ResourceLocation mtl, ResourceLocation texture) {
		ObjModel rawModel = ObjLoader.INSTANCE.loadModel(new ModelSettings(obj, true, false, true, false, mtl.toString()));
		CompositeRenderable bakedModel = rawModel.bakeRenderable(new ObjBakingContext(modelName, texture));
		return new BakedObjModel(bakedModel, modelName);
	}
	
	public static BakedObjModel getModel(String modelName, ResourceLocation obj, ResourceLocation mtl, ResourceLocation texture) {
		return cache.computeIfAbsent(hash(obj, texture), (hash)->loadModel(modelName, obj, mtl, texture));
	}
	
	public static BakedObjModel getModel(IObjModelProvider provider) {
		return getModel(provider.getModelName(), provider.getObj(), provider.getMtl(), provider.getPng());
	}
	
	public static final class BakedObjModel {
		
		private static final ITextureRenderTypeLookup renderType = RenderType::entityTranslucent;
		
		private final CompositeRenderable bakedModel;
		private final String name;
		
		private BakedObjModel(CompositeRenderable bakedModel, String name) {
			this.bakedModel = bakedModel;
			this.name = name;
		}
		
		@Override
		public String toString() {
			return "objModel:"+this.name;
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
