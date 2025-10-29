package blackoutInteractive.ema_08_.simpleNet;

import java.util.List;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class SimpleChannelHandler extends AbstractChannelHandler {

	public SimpleChannelHandler(String modid, String channelName, String protVersion,
			List<Class<? extends APacket>> packets) {
		super(modid, channelName, protVersion);
		for (Class<? extends APacket> p : packets) register(p);
	}
	
	@Override
	public void sendTo(APacket message, ServerPlayer player) {
		if (message instanceof APacket.AC2SPacket) throw new IllegalArgumentException("Cannot send to client a c2s only packet");
		this.channel.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

	@Override
    public void sendToAll(APacket message) {
		if (message instanceof APacket.AC2SPacket) throw new IllegalArgumentException("Cannot send to client a c2s only packet");
		this.channel.send(PacketDistributor.ALL.noArg(), message);
    }

	@Override
    public void sendToAllAround(APacket message, PacketDistributor.TargetPoint point) {
		if (message instanceof APacket.AC2SPacket) throw new IllegalArgumentException("Cannot send to client a c2s only packet");
		this.channel.send(PacketDistributor.NEAR.with(() -> point), message);
    }

	@Override
    public void sendToServer(APacket message) {
		if (message instanceof APacket.AS2CPacket) throw new IllegalArgumentException("Cannot send to server a s2c only packet");
		this.channel.send(PacketDistributor.SERVER.noArg(), message);
    }

}
