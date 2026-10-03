package blackoutInteractive.ema_08_.items.effectsProvidingArmors.v2;

import java.util.Objects;

import blackoutInteractive.ema_08_.misc.ReadonlySetView;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.minecraft.world.effect.MobEffect;

public record ArmorEffectsSetup(
		ReadonlySetView<AmplifiedEffect> addOnWear,
		ReadonlySetView<MobEffect> remOnWear
		) {
	
	public static final ArmorEffectsSetup EMPTY;
	
	static {
		Iterator<?> iterE = new Iterator<>() {
			@Override public boolean hasNext() { return false; }
			@Override public AmplifiedEffect next() { throw new NoSuchElementException(); }
		};
		ReadonlySetView<?> rosvE = new ReadonlySetView<>() {
			@SuppressWarnings("unchecked")
			@Override public Iterator<Object> iterator() { return (Iterator<Object>) iterE; }
			@Override public boolean contains(Object obj) { return false; }
		};
		@SuppressWarnings("unchecked")
		ArmorEffectsSetup e = new ArmorEffectsSetup((ReadonlySetView<AmplifiedEffect>)rosvE, (ReadonlySetView<MobEffect>)rosvE);
		EMPTY = e;
	}
	
	public ArmorEffectsSetup {
		Objects.requireNonNull(addOnWear, "Missing addOnWear");
		Objects.requireNonNull(remOnWear, "Missing remOnWear");
	}
	
	public record AmplifiedEffect(
			MobEffect effect, int amplifier
		) {
		
		public AmplifiedEffect {
			Objects.requireNonNull(effect, "Missing effect");
			if (amplifier < 0 || amplifier > 4) throw new IllegalArgumentException("Invalid amplifier "+amplifier);
		}
		
	}


}
