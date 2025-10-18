package brimmArmors;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brimmArmors.client.ClientProxy;
import brimmArmors.common.CommonProxy;
import brimmArmors.common.blocks.BlockRegistry;
import brimmArmors.common.items.ItemRegistry;
import brimmArmors.common.network.NetworkDispatcher;
import brimmArmors.common.tile.TileRegistry;
import brimmArmors.resource.JsonConfigLoader;

@Mod(BrimmArmors.MOD_ID)
public class BrimmArmors
{

    public static final String MOD_ID = "brimm";
    public static NetworkDispatcher network;
    public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public BrimmArmors() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        JsonConfigLoader.init();

        ItemRegistry.register(eventBus);

        BlockRegistry.register(eventBus);

        TileRegistry.register(eventBus);

        eventBus.addListener(this::preInit);
        eventBus.addListener(this::init);
        eventBus.addListener(this::server);
        eventBus.addListener(this::client);

        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * @param event Pre Init registry
     */
    private void preInit(final FMLCommonSetupEvent event) {
        network = new NetworkDispatcher();
        network.register();
        proxy.preInit();
    }

    /**
     * @param event EventBus registry
     */
    private void init(FMLLoadCompleteEvent event) {
        proxy.init();
    }

    /**
     * @param event Only for server.bat
     */
    private void server(final FMLDedicatedServerSetupEvent event) {
        proxy.server();
    }

    /**
     * @param event Only for client.bat
     */
    private void client(final FMLClientSetupEvent event) {
        proxy.client();
    }

}
