package brimmArmors.common.network.packets;

import brimmArmors.client.screens.WorkbenchScreen;
import brimmArmors.common.recipes.RecipesManager;
import ema_08_.simpleNet.APacket;
import ema_08_.simpleNet.PacketDecoder;
import ema_08_.simpleNet.PacketEncoder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

public class SetWorkbenchScreenS2C extends APacket {

    private RecipesManager.CraftType craftType;

    public SetWorkbenchScreenS2C(RecipesManager.CraftType type) {
        this.craftType = type;
    }

    @PacketEncoder(implClassName = "brimmArmors.common.network.packets.SetWorkbenchScreenS2C")
    private static void encode(SetWorkbenchScreenS2C msg, FriendlyByteBuf buf) {
        buf.writeEnum(msg.craftType);
    }

    @PacketDecoder(implClassName = "brimmArmors.common.network.packets.SetWorkbenchScreenS2C")
    private static SetWorkbenchScreenS2C decode(FriendlyByteBuf buf) {
        return new SetWorkbenchScreenS2C(buf.readEnum(RecipesManager.CraftType.class));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void handleClient(NetworkEvent.Context ctx) {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new WorkbenchScreen(craftType));
    }

    @Override
    protected void handleServer(NetworkEvent.Context ctx) {
    	throw new IllegalStateException("This should not end up here");
    }
}
