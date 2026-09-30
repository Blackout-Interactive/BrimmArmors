package blackoutInteractive.ema_08_.rendering.obj;

import blackoutInteractive.ema_08_.rendering.obj.modelsHolders.ModelDeclaration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;

public final class SideSafeModelDeclarator {

	private SideSafeModelDeclarator() {}
	
	public static int decleare(ModelDeclaration declaration) {
		if (FMLEnvironment.dist.isClient()) return declare0(declaration);
		else return -1;
	}
	
	@OnlyIn(Dist.CLIENT)
	private static int declare0(ModelDeclaration declaration) {
		return ObjModelsManager.declare(declaration);
	}
	
}
