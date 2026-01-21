package blackoutInteractive.ema_08_.items.effectsProvidingArmors;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class EffectsProvidingArmorsManager {
	
	private static final EquipmentSlot[] slots = new EquipmentSlot[]{
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };
	
	private static final ConcurrentHashMap<String, EffectsSetup> cache = new ConcurrentHashMap<>();
	private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	public static void init() {
	    executor.scheduleAtFixedRate(() -> {
	        long now = System.currentTimeMillis();
	        long expirationMillis = 15 * 60 * 1000;
	        cache.entrySet().removeIf(entry -> (now - entry.getValue().lastUsage().get()) > expirationMillis);
	    }, 10, 10, TimeUnit.MINUTES);
	}

    @SuppressWarnings("resource")
	@SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide) return;
        
        Player player = event.player;
        
        EffectsSetup effects = cache.computeIfAbsent(getArmorEffectHash(player), (k)->{
        	Set<IAmplifiableApplicableEffect> added = new HashSet<>();
            Set<MobEffect> prevented = new HashSet<>();
            Map<MobEffect, IAuraEffect> bestAuraEffects = new HashMap<>();
            
            for (EquipmentSlot slot : slots) {
                ItemStack stack = player.getItemBySlot(slot);
                if (stack != null && stack.getItem() instanceof IEffectProvidingArmor armor) {
                	for (IAmplifiableApplicableEffect e : armor.getAddedOnWear()) added.add(e);
                	for (MobEffect e : armor.getPreventedOnWear()) prevented.add(e);
                	for (IAuraEffect aura : armor.getAuraEffects()) {
                        MobEffect effect = aura.effect();
                        if (!bestAuraEffects.containsKey(effect)) {
                            bestAuraEffects.put(effect, aura);
                        } else {
                            IAuraEffect existing = bestAuraEffects.get(effect);
                            if (aura.amplifier() > existing.amplifier() ||
                                (aura.amplifier() == existing.amplifier() && aura.rangeBlocks() > existing.rangeBlocks())) {
                                bestAuraEffects.put(effect, aura);
                            }
                        }
                    }
                }
            }
            /*added-effects-always-authoritative*/
            for (IAmplifiableApplicableEffect addedEffect : added) {
            	prevented.remove(addedEffect.effect());
            }
            
            Set<IAuraEffect> auras = new HashSet<IAuraEffect>();
            auras.addAll(bestAuraEffects.values()); /*Not holding reference to a completely useless map*/
            return new EffectsSetup(added, prevented, auras, new AtomicReference<>(System.currentTimeMillis()));
        });
        
        for (MobEffect effect : effects.prevented()) {
            player.removeEffect(effect);
        }
        																											
        for (IAmplifiableApplicableEffect addedEffect : effects.added()) {
        	MobEffect effect = addedEffect.effect();
        	int amplifier = addedEffect.amplifier();
        	MobEffectInstance current = player.getEffect(effect);
        	if (current == null || current.getAmplifier() != amplifier || current.getDuration() < 5) {
        	    player.addEffect(new MobEffectInstance(effect, 10, amplifier, false, false, true));
        	}

        }
        
        for (IAuraEffect aura : effects.auras()) {
            int range = aura.rangeBlocks();
            int amplifier = aura.amplifier();
            MobEffect effect = aura.effect();
            var nearbyPlayers = player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(range));
            for (Player nearby : nearbyPlayers) {
                if (nearby == player) continue;
                MobEffectInstance current = nearby.getEffect(effect);
                if (current == null || current.getAmplifier() < amplifier || current.getDuration() < 5) {
                    nearby.addEffect(new MobEffectInstance(effect, 10, amplifier, false, false, true));
                }
            }
        }
        
        effects.lastUsage().set(System.currentTimeMillis());
        
    }
    
    private static String getArmorEffectHash(Player player) {
        StringBuilder hash = new StringBuilder();
        for (EquipmentSlot slot : slots) {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack != null && stack.getItem() instanceof IEffectProvidingArmor) {
                Item item = stack.getItem();
                ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
                if (id != null) {
                    hash.append(id.toString()).append("|");
                }
            }
        }
        return hash.toString().intern();
    }
    
    private static record EffectsSetup(
    		Set<IAmplifiableApplicableEffect> added,
    		Set<MobEffect> prevented,
    		Set<IAuraEffect> auras,
    		AtomicReference<Long> lastUsage
    		) {}

}
