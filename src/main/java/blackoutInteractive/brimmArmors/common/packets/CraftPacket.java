package blackoutInteractive.brimmArmors.common.packets;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.common.workbench.Craft;
import blackoutInteractive.brimmArmors.common.workbench.CraftsManager;
import blackoutInteractive.ema_08_.simpleNet.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

public final class CraftPacket extends APacket.AC2SPacket {

    private final int craftIndex;

    public CraftPacket(int craftIndex) {
        this.craftIndex = craftIndex;
    }

    private CraftPacket(FriendlyByteBuf buf) {
        this.craftIndex = buf.readVarInt();
    }

    @PacketEncoder(implClassName = "brimmArmors.common.packets.CraftPacket")
    private static void encode(CraftPacket msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.craftIndex);
    }

    @PacketDecoder(implClassName = "brimmArmors.common.packets.CraftPacket")
    private static CraftPacket decode(FriendlyByteBuf buf) {
        return new CraftPacket(buf);
    }

    /*
     * NOTE: Malicious users may send packets from everywhere in the world claiming to be near
     * a workbench. It should be of use to validate the player's distance to the nearest workbench.
     */
    @Override
    protected void handleServer(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null) return;

        Craft craft = CraftsManager.byUid(this.craftIndex);
        if (craft == null) {
        	BrimmArmors.LOGGER.warn("Received invalid craft id in craft packet sent by "+ctx.getSender()+" Invalid id: "+this.craftIndex+".");
            player.sendSystemMessage(Component.literal(ChatFormatting.RED+"Invalid recipe index!"));
            return;
        }
        boolean canCraft = craft.ingredients().stream().allMatch(ing ->
            player.getInventory().countItem(ing.type()) >= ing.amt()
        );
        if (!canCraft) {
            player.sendSystemMessage(
            		Component.literal(I18n.get("screen." + BrimmArmors.MOD_ID + ".workbench.not_enough_items")));
            return;
        }
        for (var ing : craft.ingredients()) {
            int remaining = ing.amt();
            for (int i = 0; i < player.getInventory().items.size() && remaining > 0; i++) {
                ItemStack stack = player.getInventory().items.get(i);
                if (stack.is(ing.type())) {
                    int toRemove = Math.min(stack.getCount(), remaining);
                    stack.shrink(toRemove);
                    remaining -= toRemove;
                }
            }
        }
        ItemStack result = new ItemStack(craft.result());
        if (!player.addItem(result)) {
            player.drop(result, false);
        }
        player.inventoryMenu.broadcastChanges();
    }

}
