package blackoutInteractive.ema_08_.simpleNet;

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
	
	public static abstract class AC2SPacket extends APacket {
		
		public AC2SPacket() { super(); }
		
		@Override
		protected final void handleClient(NetworkEvent.Context ctx) {
			throw new UnsupportedOperationException(getClass().getName()+" is a c2s packet only");
		}
		
	}
	
	public static abstract class AS2CPacket extends APacket {
		
		public AS2CPacket() { super(); }
		
		@Override
		protected final void handleServer(NetworkEvent.Context ctx) {
			throw new UnsupportedOperationException(getClass().getName()+" is a s2c packet only");
		}
		
	}
	
}
