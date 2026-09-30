package blackoutInteractive.ema_08_.rendering.obj.modelsHolders;

import java.util.Objects;

import blackoutInteractive.ema_08_.rendering.geom.MatrixRTS;
import blackoutInteractive.ema_08_.rendering.obj.ArmorModelType;
import blackoutInteractive.ema_08_.rendering.obj.ObjResourcesLocation;
import blackoutInteractive.ema_08_.rendering.obj.metadata.ArmorModelMetadata;

public final class ModelDeclaration extends ModelMetadataHolder {
	
	public final ObjResourcesLocation location;
	public final boolean requiresTransparency;
	
	private ModelDeclaration(ObjResourcesLocation l, boolean rt, Object m) {
		super(m);
		this.location = Objects.requireNonNull(l, "Missing resources location");
		this.requiresTransparency = rt;
	}
	
	@Override
	public String toString() {
		return "DeclarationOf:"+location.toString();
	}
	
	public static ModelDeclaration ofBlockEntity(String modelName, boolean rt) {
		return new ModelDeclaration(ObjResourcesLocation.ofBlock(modelName), rt, null);
	}
	
	public static ModelDeclaration ofArmor(String modelName, ArmorModelType amt, boolean rt,
			MatrixRTS wearing_transform, MatrixRTS workbench_transform) {
		return new ModelDeclaration(ObjResourcesLocation.ofArmor(amt, modelName), rt,
				new ArmorModelMetadata(amt, wearing_transform, workbench_transform)
			);
	}

}
