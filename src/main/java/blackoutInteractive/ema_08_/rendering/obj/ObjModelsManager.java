package blackoutInteractive.ema_08_.rendering.obj;

import java.util.ArrayList;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import com.mojang.math.Transformation;

import blackoutInteractive.ema_08_.rendering.obj.modelsHolders.LoadedModel;
import blackoutInteractive.ema_08_.rendering.obj.modelsHolders.ModelDeclaration;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.obj.ObjLoader;
import net.minecraftforge.client.model.obj.ObjModel;
import net.minecraftforge.client.model.obj.ObjModel.ModelSettings;
import net.minecraftforge.client.model.renderable.CompositeRenderable;

/*
 * Before freezing the access (declare) is to be considered thread safe.
 * Freezing operation is to be considered thread safe and not idempotent.
 * After freezing the access (get) is not to be considered thread safe, and is assumed to be called only from the render thread.
 */
public final class ObjModelsManager {
	
	private ObjModelsManager() {}
	
	private static final ArrayList<ModelDeclaration> declarations = new ArrayList<>();
	private static volatile LoadedModel[] models;
	
	public static int declare(ModelDeclaration declaration) {
		Objects.requireNonNull(declaration, "Cannot declare with a null declaration");
		synchronized (declarations) {
			if (models != null) throw new IllegalStateException("Manager is already frozen");
			declarations.add(declaration);
			return declarations.size()-1;
		}
	}//
	
	public static void freeze() {
		synchronized (declarations) {
			if (models != null) throw new IllegalStateException("Manager is already frozen");
			models = new LoadedModel[declarations.size()];
		}
	}
	
	public static LoadedModel get(int idx) {
		/* All assumptions that may be safely assumed if the caller is not a tard-ass.
		RenderSystem.assertOnRenderThread();
		if (models == null) throw new IllegalStateException("Manager is not yet frozen");
		if (idx < 0 || idx >= models.length) throw new IllegalArgumentException("Invalid model index: OOB");
		*/
		LoadedModel model = models[idx];
		if (model == null) {
			ModelDeclaration declaration = declarations.get(idx);
			CompositeRenderable baked = bake(declaration.location);
			model = new LoadedModel(baked,
					declaration.requiresTransparency ? RenderType::entityTranslucent : RenderType::entityCutout,
					declaration.getMetadata());
			models[idx] = model;
			declarations.set(idx, null);
		}
		return model;
	}
	
	// --- Bakery --- \\
	
	private static CompositeRenderable bake(ObjResourcesLocation data) {
		ObjModel rawModel = ObjLoader.INSTANCE.loadModel(new ModelSettings(data.obj(), true, false, true, false, data.mtl().toString()));
		return rawModel.bakeRenderable(new BakingContext(data.name(), data.png()));
	}
	
	private static class BakingContext implements IGeometryBakingContext {
		
		private final String modelName;
		private final Material texture;
		
		protected BakingContext(String modelName, ResourceLocation texture) {
			Objects.requireNonNull(texture);
			this.modelName = Objects.requireNonNull(modelName);
			this.texture = new Material(texture, texture);
		}

		@Override public String getModelName() { return this.modelName; }

		@Override
		public boolean hasMaterial(String name) { return this.modelName.equals(name); }

		@Override
		public Material getMaterial(String name) { return hasMaterial(name) ? this.texture : null; }

		@Override
		public boolean isGui3d() { return true; }

		@Override
		public boolean useBlockLight() { return false; }

		@Override
		public boolean useAmbientOcclusion() { return true; }

		@Override
		public ItemTransforms getTransforms() { return ItemTransforms.NO_TRANSFORMS; }

		@Override
		public Transformation getRootTransform() { return Transformation.identity(); }

		@Override
		public @Nullable ResourceLocation getRenderTypeHint() { return null; }

		@Override
		public boolean isComponentVisible(String component, boolean fallback) { return fallback; }

	}

}