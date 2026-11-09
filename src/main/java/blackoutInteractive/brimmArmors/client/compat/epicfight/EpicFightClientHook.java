package blackoutInteractive.brimmArmors.client.compat.epicfight;

import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public final class EpicFightClientHook {

    private EpicFightClientHook() {}

    public static void register(IEventBus modBus) {
        modBus.addListener(EpicFightClientHook::onModifyRenderers);
    }

    private static void onModifyRenderers(PatchedRenderersEvent.Modify event) {
        for (EntityType<?> type : ForgeRegistries.ENTITY_TYPES) {
            if (event.get(type) instanceof PHumanoidRenderer<?,?,?,?,?> r) addLayer(r);
        }
    }

    private static <E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>>
            void addLayer(PatchedLivingEntityRenderer<E, T, M, ?, ?> renderer) {
        renderer.addCustomLayer(new BrimmArmorPatchedLayer<E, T, M>());
    }
}