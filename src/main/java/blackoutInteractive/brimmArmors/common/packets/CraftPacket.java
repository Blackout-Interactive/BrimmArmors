package blackoutInteractive.brimmArmors.common.packets;

import java.util.Objects;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.common.registries.BlockRegistry;
import blackoutInteractive.brimmArmors.common.workbench.Craft;
import blackoutInteractive.brimmArmors.common.workbench.CraftsManager;
import blackoutInteractive.ema_08_.simpleNet.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CraftPacket extends APacket.AC2SPacket {

    private final ResourceLocation craftId;

    public CraftPacket(ResourceLocation craftId) {
        this.craftId = craftId;
    }

    private CraftPacket(FriendlyByteBuf buf) {
        this.craftId = buf.readResourceLocation();
    }

    @PacketEncoder(implClassName = "blackoutInteractive.brimmArmors.common.packets.CraftPacket")
    private static void encode(CraftPacket msg, FriendlyByteBuf buf) {
        buf.writeResourceLocation(msg.craftId);
    }

    @PacketDecoder(implClassName = "blackoutInteractive.brimmArmors.common.packets.CraftPacket")
    private static CraftPacket decode(FriendlyByteBuf buf) {
        return new CraftPacket(buf);
    }

    @Override
    protected void handleServer(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null) return;
        BlockPos nearestWorkbench = findNearestBlock(player, BlockRegistry.workbench.get(), 3);
        if (nearestWorkbench == null || nearestWorkbench.distSqr(player.blockPosition()) > 9) {
        	BrimmArmors.LOGGER.warn("Received too distant craft packet sent by "+ctx.getSender()+" Distance : "+
        			(nearestWorkbench == null ? "unknown" : Math.sqrt(nearestWorkbench.distSqr(player.blockPosition())))+".");
            player.sendSystemMessage(Component.literal(ChatFormatting.RED + "You are too far from a workbench!"));
            return;
        }

        Craft craft = CraftsManager.byId(this.craftId);
        if (craft == null) {
        	BrimmArmors.LOGGER.warn("Received invalid craft id in craft packet sent by "+ctx.getSender()+" Invalid id: "+Objects.toString(this.craftId)+".");
            player.sendSystemMessage(Component.literal(ChatFormatting.RED+"Invalid recipe index!"));
            return;
        }
        boolean canCraft = craft.ingredients().stream().allMatch(ing ->
            player.getInventory().countItem(ing.type()) >= ing.amt()
        );
        if (!canCraft) {
            player.sendSystemMessage(
            		Component.translatable("screen." + BrimmArmors.MOD_ID + ".workbench.not_enough_items"));
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
    
    public BlockPos findNearestBlock(ServerPlayer player, Block targetBlock, int searchRadius) {
        ServerLevel world = (ServerLevel)player.level();
        BlockPos playerPos = player.blockPosition();
        BlockPos nearestPos = null;
        double nearestDistance = Double.MAX_VALUE;
        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int y = -searchRadius; y <= searchRadius; y++) {
                for (int z = -searchRadius; z <= searchRadius; z++) {
                    BlockPos checkPos = playerPos.offset(x, y, z);
                    if (world.getBlockState(checkPos).is(targetBlock)) {
                        double distance = checkPos.distSqr(playerPos);
                        if (distance < nearestDistance) {
                            nearestDistance = distance;
                            nearestPos = checkPos;
                        }
                    }
                }
            }
        }
        return nearestPos;
    }

}
