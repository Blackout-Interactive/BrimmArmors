package blackoutInteractive.brimmArmors.common.packets;

import java.util.Objects;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.common.configurations.ConfigsManager;
import blackoutInteractive.brimmArmors.server.ConfigsMatcher;
import blackoutInteractive.ema_08_.simpleNet.APacket;
import blackoutInteractive.ema_08_.simpleNet.PacketDecoder;
import blackoutInteractive.ema_08_.simpleNet.PacketEncoder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ConfigCheckPacket extends APacket {
	
	private static final String TRIGGER_HASH = "__";
	
	private final String hash;
	
    private ConfigCheckPacket(String hash) {
        this.hash = Objects.requireNonNull(hash);
    }

    public static ConfigCheckPacket trigger() {
    	return new ConfigCheckPacket(TRIGGER_HASH);
    }
    
    @PacketEncoder(implClassName = "blackoutInteractive.brimmArmors.common.packets.ConfigCheckPacket")
    private static void encode(ConfigCheckPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.hash);
    }

    @PacketDecoder(implClassName = "blackoutInteractive.brimmArmors.common.packets.ConfigCheckPacket")
    private static ConfigCheckPacket decode(FriendlyByteBuf buf) {
        return new ConfigCheckPacket(buf.readUtf());
    }

	@Override
	protected void handleServer(Context ctx) {
		ConfigsMatcher.verifyPlayer(ctx.getSender(), this.hash);
	}

	@Override
	protected void handleClient(Context ctx) {
		if (!TRIGGER_HASH.equals(this.hash))
			throw new UnsupportedOperationException("Wrong side?");
		BrimmArmors.network.sendToServer(new ConfigCheckPacket(ConfigsManager.configHash()));
	}

}
