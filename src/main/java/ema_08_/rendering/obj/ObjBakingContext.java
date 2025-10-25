package ema_08_.rendering.obj;

import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import com.mojang.math.Transformation;

import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;

public class ObjBakingContext implements IGeometryBakingContext {
	
	private final String modelName;
	private final Material texture;
	
	protected ObjBakingContext(String modelName, ResourceLocation texture) {
		Objects.requireNonNull(texture);
		this.modelName = Objects.requireNonNull(modelName);
		this.texture = new Material(texture, texture);
	}

	@Override
	public String getModelName() {
		return this.modelName;
	}

	@Override
	public boolean hasMaterial(String name) {
		return this.modelName.equals(name);
	}

	@Override
	public Material getMaterial(String name) {
	    return hasMaterial(name) ? this.texture : null;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean useBlockLight() {
		return false;
	}

	@Override
	public boolean useAmbientOcclusion() {
		return true;
	}

	@Override
	public ItemTransforms getTransforms() {
		return ItemTransforms.NO_TRANSFORMS;
	}

	@Override
	public Transformation getRootTransform() {
		return Transformation.identity();
	}

	@Override
	public @Nullable ResourceLocation getRenderTypeHint() {
		return null;
	}

	@Override
	public boolean isComponentVisible(String component, boolean fallback) {
		return fallback;
	}

}
