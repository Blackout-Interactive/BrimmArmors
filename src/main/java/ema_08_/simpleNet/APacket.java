package ema_08_.simpleNet;

import java.util.function.Supplier;

import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

public abstract class APacket {
	
	protected final void handle(Supplier<NetworkEvent.Context> ctx) {
		final NetworkEvent.Context c = ctx.get();
        if (c.getDirection().getReceptionSide() == LogicalSide.SERVER) c.enqueueWork(() -> handleServer(c));
        else c.enqueueWork(() -> handleClient(c));
        c.setPacketHandled(true);
	}
	
	protected abstract void handleClient(NetworkEvent.Context ctx);
	
	protected abstract void handleServer(NetworkEvent.Context ctx);
	
}
