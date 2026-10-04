package blackoutInteractive.ema_08_.misc;

import java.util.HashMap;
import static java.util.Objects.requireNonNull;

public final class TrivialEqualityDeduper<T> {
	
	/*
	 * This tool is not thread safe nor meant for runtime speed. Rather, it was conceived as a load-time
	 * utility to de-duplicate long-lived instances holding core data such as model transforms (which last for the whole
	 * life of the JVM).
	 * 
	 * Note that an internal invariant is that if a value is present, it will never be replaced with other value equal to the original.
	 */
	
	private final HashMap<T, Data> known = new HashMap<>();
	
	private class Data {
		private int dedupAccesses;
		private final T value;
		private Data(T v) { value = v; }
		private T getForDeduping() {
			dedupAccesses++;
			return value;
		}
	}
	
	private void put0(T t) { known.put(t, new Data(t)); }
	
	private Data get0(T k) { return known.get(k); }
	
	public T dedupe(T value) {
		Data d = get0(requireNonNull(value, "Cannot deduplicate null values"));
		if (d == null) {
			put0(value);
			return value;
		} else return d.getForDeduping();
	}
	
	public boolean contains(T value) {
		return null != get0(requireNonNull(value, "Cannot check content against null values"));
	}
	
	public boolean containsExact(T value) {
		Data d = get0(requireNonNull(value, "Cannot check content against null values"));
		return d != null && d.value == value;
	}
	
	public boolean put(T value) {
		Data d = get0(requireNonNull(value, "Cannot check content against null values"));
		if (d == null) {
			put0(value);
			return true;
		} else return false;
	}
	
	public void ensurePresentExact(T value) {
		Data d = get0(requireNonNull(value, "Cannot check content against null values"));
		if (d == null) {
			put0(value);
		}
		else if (d.value != value) throw new IllegalStateException("Another equal instance is already present");
	}
	
	public int size() {
		return known.size();
	}
	
	public String dump() {
		final HashMap<Class<?>, HashMap<T, Integer>> dump = new HashMap<>();
		int dedupAccesses = 0;
		for (var entry : known.values()) {
			HashMap<T, Integer> cdump = dump.computeIfAbsent(entry.value.getClass(), (c)->{
				HashMap<T, Integer> map = new HashMap<>();
				map.put(null, 0);
				return map;
			});
			cdump.put(entry.value, entry.dedupAccesses);
			cdump.merge(null, entry.dedupAccesses, Integer::sum);
			dedupAccesses += entry.dedupAccesses;
		}
		StringBuilder dumpb =
			new StringBuilder("Total size: ").append(known.size()).append("\nDeduplication accesses: ").append(dedupAccesses);
		dumpb.append("\nContents and deduplication accesses by concrete type:");
		for (var entry : dump.entrySet()) {
			dumpb.append("\n - Type: ").append(entry.getKey().getName())
			.append(" (objects = ").append(entry.getValue().size()-1).append(") (dedup_accesses = ").append(entry.getValue().get(null))
			.append(")");
			for (var object : entry.getValue().entrySet()) {
				T k = object.getKey();
				if (k != null)
					dumpb.append("\n    - ").append(k.toString())
						.append(": ").append(object.getValue().intValue());
			}
		}
		return dumpb.toString();
	}

}
