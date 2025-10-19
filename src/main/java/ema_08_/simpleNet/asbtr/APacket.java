package ema_08_.simpleNet.asbtr;

import java.util.function.Supplier;

import net.minecraftforge.network.NetworkEvent;

public abstract class APacket {
	
	public abstract void handle(Supplier<NetworkEvent.Context> ctx);
	
}
