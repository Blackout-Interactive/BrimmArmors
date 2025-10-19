package brimmArmors.common.network.packets;

import ema_08_.simpleNet.APacket;
import ema_08_.simpleNet.PacketDecoder;
import ema_08_.simpleNet.PacketEncoder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;

import brimmArmors.BrimmArmors;
import brimmArmors.common.recipes.Ingredient;
import brimmArmors.common.recipes.ItemRecipe;
import brimmArmors.common.recipes.RecipesManager;

public class RequestCraftItem extends APacket {

    public RecipesManager.CraftType craftType;
    public short currentIndex;

    public RequestCraftItem(RecipesManager.CraftType craftType, short currentIndex) {
        this.craftType = craftType;
        this.currentIndex = currentIndex;
    }

    @PacketEncoder(implClassName = "brimmArmors.common.network.packets.RequestCraftItem")
    private static void encode(RequestCraftItem msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.craftType.getRecipeID());
        buf.writeShort(msg.currentIndex);
    }

    @PacketDecoder(implClassName = "brimmArmors.common.network.packets.RequestCraftItem")
    private static RequestCraftItem decode(FriendlyByteBuf buf) {
        return new RequestCraftItem(
            RecipesManager.CraftType.get(buf.readUtf()),
            buf.readShort()
        );
    }

    @Override
    protected void handleClient(NetworkEvent.Context ctx) {
        throw new IllegalStateException("This should not end up here");
    }

    @Override
    protected void handleServer(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null) return;

        ArrayList<ItemRecipe> recipes = RecipesManager.getRecipe(craftType);
        if (recipes == null || currentIndex < 0 || currentIndex >= recipes.size()) {
            BrimmArmors.LOGGER.error("Someone is trying to craft something with an invalid index!");
            return;
        }

        try {
            Inventory inventory = player.getInventory();
            ItemRecipe itemRecipe = recipes.get(currentIndex);

            // Check if player has all ingredients
            boolean hasIngredients = true;
            for (Ingredient ingredient : itemRecipe.ingredients) {
                if (ingredient.count > inventory.countItem(ingredient.item)) {
                    hasIngredients = false;
                    break;
                }
            }

            if (!hasIngredients)
                return;

            // Consume ingredients
            for (Ingredient ingredient : itemRecipe.ingredients) {
                int countToRemove = ingredient.count;
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    ItemStack stack = inventory.getItem(i);
                    if (!stack.isEmpty() && stack.getItem() == ingredient.item) {
                        int stackCount = stack.getCount();
                        if (stackCount <= countToRemove) {
                            inventory.removeItem(i, stackCount);
                            countToRemove -= stackCount;
                        } else {
                            inventory.removeItem(i, countToRemove);
                            countToRemove = 0;
                        }
                        if (countToRemove <= 0) break;
                    }
                }
            }

            // Give result item
            ItemStack resultItem = new ItemStack(itemRecipe.result);
            if (!inventory.add(resultItem)) {
                player.drop(resultItem, false);
            }

        } catch (Exception e) {
            BrimmArmors.LOGGER.error("Error handling RequestCraftItem packet", e);
        }
    }
}
