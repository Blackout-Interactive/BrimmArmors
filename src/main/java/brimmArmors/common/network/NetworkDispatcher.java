package brimmArmors.common.network;

import brimmArmors.BrimmArmors;
import brimmArmors.common.network.packets.RequestCraftItem;
import brimmArmors.common.network.packets.SetWorkbenchScreenS2C;

public class NetworkDispatcher extends AbstractDispatcher {

    public NetworkDispatcher() {
        super(BrimmArmors.MOD_ID);
    }

    @Override
    public void register() {
        register(RequestCraftItem.class, RequestCraftItem::read, RequestCraftItem::write, RequestCraftItem::handle);
        register(SetWorkbenchScreenS2C.class, SetWorkbenchScreenS2C::encode, SetWorkbenchScreenS2C::decode, SetWorkbenchScreenS2C::handle);
    }

}