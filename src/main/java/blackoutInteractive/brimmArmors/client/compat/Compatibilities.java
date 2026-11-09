package blackoutInteractive.brimmArmors.client.compat;

import static blackoutInteractive.brimmArmors.BrimmArmors.LOGGER;

import java.util.Optional;

import blackoutInteractive.brimmArmors.client.compat.epicfight.EpicFightCompat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;

public final class Compatibilities {
	
	private Compatibilities() {}
	
	public static interface ICompatManager {
		
		boolean isLoaded();
		
	}
	
	@FunctionalInterface
	public static interface ICompatManagerProvider<T extends ICompatManager> {
		T newInstance(boolean loaded, IEventBus bus) throws Exception;
	}
	
	private static final String MOD_ID_EF = "epicfight";
	private static Optional<EpicFightCompat> efCompat;
	
	public static Optional<EpicFightCompat> getCompatEF() {
		if (efCompat == null)
			throw new IllegalStateException("This compatibility manager is not loaded yet");
		return efCompat;
	}
	
	public static void init(IEventBus bus) {
		efCompat = initCompatManagerOnDist(MOD_ID_EF, Dist.CLIENT, EpicFightCompat::new, bus);
	}
	
	private static <T extends ICompatManager> Optional<T> initCompatManagerOnDist(String targetModId, Dist where, ICompatManagerProvider<T> factory,
			IEventBus bus) {
		if (where == null || FMLEnvironment.dist == where) {
			final boolean loaded = ModList.get().isLoaded(targetModId);
			LOGGER.info(String.format("Initialising %s compatibility manager (side=%s;targetLoaded=%s).",
					targetModId, FMLEnvironment.dist, loaded));
			try {
				return Optional.of(factory.newInstance(loaded, bus));
			} catch (Throwable/*Yeah, want to catch even ClassDefNotFoundErr, you never know*/ e) {
				LOGGER.error(String.format("Failed to initialise %s compatibility manager due to an unexpected exception.", targetModId), e);
				return Optional.empty();
			}
		} else {
			LOGGER.info(String.format("Skipping %s compatibility manager loading (it is not required for side=%s).", targetModId, where));
			return Optional.empty();
		}
	}

}
