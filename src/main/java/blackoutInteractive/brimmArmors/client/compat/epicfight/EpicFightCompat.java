package blackoutInteractive.brimmArmors.client.compat.epicfight;

import blackoutInteractive.brimmArmors.client.compat.Compatibilities;
import net.minecraftforge.eventbus.api.IEventBus;

public final class EpicFightCompat implements Compatibilities.ICompatManager {
	
	private final boolean loaded;
	
	public EpicFightCompat(boolean l, IEventBus modBus) {
		loaded = l;
        if (!loaded) return;
        EpicFightClientHook.register(modBus);
    }

    public  boolean isLoaded() {
        return loaded;
    }
    
}