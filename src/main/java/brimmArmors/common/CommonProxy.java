package brimmArmors.common;

import net.minecraftforge.fml.event.lifecycle.*;

public abstract class CommonProxy {

    public void preInit(final FMLCommonSetupEvent event) {
    	
    }

    public void init(final FMLLoadCompleteEvent event) {
        
    }

    public void client(final FMLClientSetupEvent event)
    {throw new IllegalStateException("Wrong side");}

    public void server(final FMLDedicatedServerSetupEvent event)
    {throw new IllegalStateException("Wrong side");}

}
