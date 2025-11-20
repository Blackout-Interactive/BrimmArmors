package blackoutInteractive.brimmArmors.common.packets;

import java.util.Objects;

import blackoutInteractive.brimmArmors.client.screens.PatchesAdjusterScreen;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayPos;
import blackoutInteractive.ema_08_.simpleNet.APacket;
import blackoutInteractive.ema_08_.simpleNet.PacketDecoder;
import blackoutInteractive.ema_08_.simpleNet.PacketEncoder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent.Context;

public class PatchesAdjusterDebugPacket extends APacket.AS2CPacket {
	
	private final String armorName;
	private final OverlayPos op;
	
    public PatchesAdjusterDebugPacket(String armorName, OverlayPos op) {
        this.armorName = Objects.requireNonNull(armorName);
        this.op = Objects.requireNonNull(op);
    }

    private PatchesAdjusterDebugPacket(FriendlyByteBuf buf) {
    	this(buf.readUtf(), OverlayPos.values()[buf.readInt()]);
    }
	
    @PacketEncoder(implClassName = "blackoutInteractive.brimmArmors.common.packets.PatchesAdjusterDebugPacket")
    private static void encode(PatchesAdjusterDebugPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.armorName);
        buf.writeInt(msg.op.ordinal());
    }

    @PacketDecoder(implClassName = "blackoutInteractive.brimmArmors.common.packets.PatchesAdjusterDebugPacket")
    private static PatchesAdjusterDebugPacket decode(FriendlyByteBuf buf) {
        return new PatchesAdjusterDebugPacket(buf);
    }

	@Override
	protected void handleClient(Context ctx) {
		openScreen(this.armorName, op);
	}
	
	 @OnlyIn(Dist.CLIENT)
	 private static void openScreen(String armorName, OverlayPos op) {
	    Minecraft.getInstance().setScreen(new PatchesAdjusterScreen(armorName, op));
	}

}
