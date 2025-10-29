package blackoutInteractive.brimmArmors.server;

import java.util.HashMap;
import java.util.Map;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.common.configurations.ConfigsManager;
import blackoutInteractive.brimmArmors.common.packets.ConfigCheckPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class ConfigsMatcher {

    private static final Map<String, Long> pendingVerifications = new HashMap<>();

    private static final long TIMEOUT_MS = 30_000L;

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        pendingVerifications.put(
        		event.getEntity().getName().getString(),
        		System.currentTimeMillis()
        	);
        BrimmArmors.network.sendTo(ConfigCheckPacket.trigger(), (ServerPlayer)event.getEntity());
    }


    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.side != LogicalSide.SERVER) return;

        long now = System.currentTimeMillis();
        pendingVerifications.entrySet().removeIf(entry -> {
            String name = entry.getKey();
            long joinTime = entry.getValue();
            if (now - joinTime > TIMEOUT_MS) {
                ServerPlayer player = getPlayerByName(name);
                if (player != null) {
                    player.connection.disconnect(Component.literal(ChatFormatting.RED+BrimmArmors.MOD_ID+": Config verification timed out."));
                    BrimmArmors.LOGGER.warn("Config verification timed out for "+name+".");
                }
                return true;
            }
            return false;
        });
    }

    public static void verifyPlayer(ServerPlayer player, String clientHash) {
        String name = player.getName().getString();
        Long joinTime = pendingVerifications.get(name);
        if (joinTime == null) {
        	BrimmArmors.LOGGER.warn("Attempted to verify "+name+"'s past the required time.");
            return;
        }
        String serverHash = ConfigsManager.configHash();
        if (!serverHash.equals(clientHash)) {
            player.connection.disconnect(Component.literal(ChatFormatting.RED+BrimmArmors.MOD_ID+": Config verification failed."));
            BrimmArmors.LOGGER.warn("Config verification failed for "+name+". Expected hash '"+serverHash+"', got '"+clientHash+"'.");
        } else {
        	BrimmArmors.LOGGER.info("Verified config for "+name+".");
        }
        pendingVerifications.remove(name);
    }

    private static ServerPlayer getPlayerByName(String name) {
        return net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer()
                .getPlayerList()
                .getPlayerByName(name);
    }

}
