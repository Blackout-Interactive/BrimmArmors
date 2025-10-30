package blackoutInteractive.brimmArmors;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import blackoutInteractive.brimmArmors.client.ClientProxy;
import blackoutInteractive.brimmArmors.common.CommonProxy;
import blackoutInteractive.brimmArmors.common.configurations.ConfigsManager;
import blackoutInteractive.brimmArmors.common.packets.ConfigCheckPacket;
import blackoutInteractive.brimmArmors.common.packets.CraftPacket;
import blackoutInteractive.brimmArmors.common.recipes.RecipeSerializers;
import blackoutInteractive.brimmArmors.common.registries.BlockRegistry;
import blackoutInteractive.brimmArmors.common.registries.ItemRegistry;
import blackoutInteractive.brimmArmors.common.registries.TileRegistry;
import blackoutInteractive.brimmArmors.common.workbench.CraftsManager;
import blackoutInteractive.brimmArmors.server.ServerProxy;
import blackoutInteractive.ema_08_.rendering.geom.RotQuaternionPool;
import blackoutInteractive.ema_08_.simpleNet.SimpleChannelHandler;

@Mod(BrimmArmors.MOD_ID)
public class BrimmArmors
{

    public static final String MOD_ID = "brimm";
    public static SimpleChannelHandler network;
    public static CommonProxy proxy = new CommonProxy() {}; /*placeholder, re-declared in constructor*/

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public BrimmArmors() {
    	if (FMLEnvironment.dist.isClient()) {
    		proxy = new ClientProxy();
    	} else {
    		proxy = new ServerProxy();
    	}
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        /*
         * Requires: N/A.
         */
        ConfigsManager.init();

        /*
         * Requires: configurations being loaded.
         */
        ItemRegistry.register(eventBus);
        
        /*
         * Requires: N/A.
         */
        BlockRegistry.register(eventBus);

        /*
         * Requires: blocks being initialised.
         */
        TileRegistry.register(eventBus);
        
        RecipeSerializers.register(eventBus);

        eventBus.addListener(this::preInit);
        eventBus.addListener(this::init);
        eventBus.addListener(this::server);
        eventBus.addListener(this::client);

        MinecraftForge.EVENT_BUS.register(this);
    }

	private void preInit(final FMLCommonSetupEvent event) {
        network = new SimpleChannelHandler(MOD_ID, "main", "1", List.of(
        		CraftPacket.class, ConfigCheckPacket.class
        	));
        proxy.preInit(event);
    }

    private void init(final FMLLoadCompleteEvent event) {
        proxy.init(event);
        CraftsManager.buildAll();
        RotQuaternionPool.freeAll();
    }

    private void server(final FMLDedicatedServerSetupEvent event) {
        proxy.server(event);
    }

    private void client(final FMLClientSetupEvent event) {
        proxy.client(event);
    }

}
