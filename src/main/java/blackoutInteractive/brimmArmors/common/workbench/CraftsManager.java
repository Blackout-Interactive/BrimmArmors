package blackoutInteractive.brimmArmors.common.workbench;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import blackoutInteractive.ema_08_.misc.Freezable;
import net.minecraft.resources.ResourceLocation;

public final class CraftsManager extends Freezable<Map<CraftSection, ArrayList<CraftBuilder>>, CraftsManager.BuiltState> {
	
	private static final List<CraftSection> orderedSections = List.of(
			CraftSection.PLATES,
			CraftSection.HELMETS, CraftSection.CHESTPLATES, CraftSection.LEGGINGS, CraftSection.BOOTS,
			CraftSection.PATCHES
		);
	
	protected record BuiltState(
			Map<CraftSection, List<Craft>> built,
			Map<CraftSection, CraftsSectionAccessor> accessors
		) {}
	
	private static final CraftsManager INSTANCE = new CraftsManager();
	
	private CraftsManager() {
		super(true, false, (pfd)->{
			Map<CraftSection, List<Craft>> mutableBuilt = new HashMap<>();
			for (CraftSection section : orderedSections) {
				ArrayList<CraftBuilder> crafts = pfd.get(section);
				mutableBuilt.put(section,
						crafts.stream().map(CraftBuilder::build).collect(Collectors.toUnmodifiableList()));
			}
			final Map<CraftSection, CraftsSectionAccessor> accessors = new HashMap<>();
			final Map<CraftSection, List<Craft>> built = Collections.unmodifiableMap(mutableBuilt);
			for (CraftSection section : built.keySet())
			    accessors.put(section, new CraftsSectionAccessor(section, built.get(section)));
			pfd.clear();
			return new BuiltState(built, accessors);
		}, ()->{
			final Map<CraftSection, ArrayList<CraftBuilder>> map = new HashMap<>();
			for (CraftSection section : orderedSections) map.put(section, new ArrayList<>());
			return map;
		});
	}
	
	public static void register(CraftBuilder builder, CraftSection section) {
		synchronized(INSTANCE.getPreFreezingLock()) {
			Objects.requireNonNull(INSTANCE.getPreFreezingData().get(Objects.requireNonNull(section)), "Missing section list: "+section)
				.add(Objects.requireNonNull(builder));
		}
	}
	
	public static void buildAll() {
		INSTANCE.freeze();
	}
	
	public static final class CraftsSectionAccessor {
		
		private final CraftSection section;
		private final List<Craft> cached;
		
		private CraftsSectionAccessor(CraftSection section, List<Craft> crafts) {
		    this.section = section;
		    this.cached = Objects.requireNonNull(crafts, "No data to access for "+section);
		}
		
		public Craft next(Craft craft) {
			int idx = this.cached.indexOf(craft);
			if (idx == -1) throw new IllegalArgumentException("Unregistered craft");
			return idx == this.cached.size()-1 ? null : this.cached.get(idx+1);
		}
		
		public Craft prev(Craft craft) {
			int idx = this.cached.indexOf(craft);
			if (idx == -1) throw new IllegalArgumentException("Unregistered craft");
			return idx == 0 ? null : this.cached.get(idx-1);
		}
		
		public Craft first() {
			return this.cached.isEmpty() ? null : this.cached.get(0);
		}
		
		public Craft last() {
			return this.cached.isEmpty() ? null : this.cached.get(this.cached.size()-1);
		}
		
		public CraftSection section() {
			return this.section;
		}
		
	}
	
	public static Craft byId(ResourceLocation id) {
		for (List<Craft> crafts : INSTANCE.getPostFreezingData().built.values()) {
			Craft match = crafts.stream().filter((c)->c.id().equals(id)).findFirst().orElse(null);
			if (match != null) return match;
		}
		return null;
	}
	
	public static CraftSection firstSection() {
		INSTANCE.getPostFreezingData();/*JUST TO THROW IF NOT FROZEN*/
		return orderedSections.get(0);
	}
	
	public static CraftSection lastSection() {
		INSTANCE.getPostFreezingData();/*JUST TO THROW IF NOT FROZEN*/
		return orderedSections.get(orderedSections.size()-1);
	}
	
	public static CraftSection nextSection(CraftSection current) {
		INSTANCE.getPostFreezingData();/*JUST TO THROW IF NOT FROZEN*/
		int idx = orderedSections.indexOf(current);
		if (idx == -1) throw new IllegalArgumentException("Invalid section");
		return idx == orderedSections.size()-1 ? null : orderedSections.get(idx+1);
	}
	
	public static CraftSection prevSection(CraftSection current) {
		INSTANCE.getPostFreezingData();/*JUST TO THROW IF NOT FROZEN*/
		int idx = orderedSections.indexOf(current);
		if (idx == -1) throw new IllegalArgumentException("Invalid section");
		return idx == 0 ? null : orderedSections.get(idx-1);
	}
	
	public static CraftsSectionAccessor accessor(CraftSection section) {
		return Objects.requireNonNull(INSTANCE.getPostFreezingData().accessors.get(section), "Invalid section");
	}

}
