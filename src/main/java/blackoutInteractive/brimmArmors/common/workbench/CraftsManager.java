package blackoutInteractive.brimmArmors.common.workbench;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public class CraftsManager {
	
	private static final Map<CraftSection, List<CraftBuilder>> builders = new HashMap<>();
	private static final List<CraftSection> orderedSections = List.of(
			CraftSection.PLATES, CraftSection.CHESTPLATES, CraftSection.HELMETS, CraftSection.PATCHES
		);
	
	static {
		for (CraftSection section : orderedSections) builders.put(section, new ArrayList<>());
	}
	
	private static Map<CraftSection, List<Craft>> built;
	private static final Map<CraftSection, CraftsSectionAccessor> accessors = new HashMap<>();
	
	public static void register(CraftBuilder builder, CraftSection section) {
		if (built != null) throw new IllegalStateException("Crafts already built");
		Objects.requireNonNull(builders.get(Objects.requireNonNull(section)), "Missing section list: "+section)
			.add(Objects.requireNonNull(builder));
	}
	
	public static void buildAll() {
		if (built != null) throw new IllegalStateException("Crafts already built");
		Map<CraftSection, List<Craft>> mutableBuilt = new HashMap<>();
		for (Entry<CraftSection, List<CraftBuilder>> sectionData : builders.entrySet())
			mutableBuilt.put(sectionData.getKey(),
				sectionData.getValue().stream().map(CraftBuilder::build).collect(Collectors.toUnmodifiableList()));
		built = Collections.unmodifiableMap(mutableBuilt);
		for (CraftSection section : built.keySet()) accessors.put(section, new CraftsSectionAccessor(section));
		builders.clear();
	}
	
	public static final class CraftsSectionAccessor {
		
		private final CraftSection section;
		private final List<Craft> cached;
		
		private CraftsSectionAccessor(CraftSection section) {
			this.section = section;
			this.cached = Objects.requireNonNull(built.get(section), "No data to access for "+section);
		}
		
		public Craft next(Craft craft) {
			if (built == null) throw new IllegalStateException("Crafts have not been built yet");
			int idx = this.cached.indexOf(craft);
			if (idx == -1) throw new IllegalArgumentException("Unregistered craft");
			return idx == this.cached.size()-1 ? null : this.cached.get(idx+1);
		}
		
		public Craft prev(Craft craft) {
			if (built == null) throw new IllegalStateException("Crafts have not been built yet");
			int idx = this.cached.indexOf(craft);
			if (idx == -1) throw new IllegalArgumentException("Unregistered craft");
			return idx == 0 ? null : this.cached.get(idx-1);
		}
		
		public Craft first() {
			if (built == null) throw new IllegalStateException("Crafts have not been built yet");
			return this.cached.isEmpty() ? null : this.cached.get(0);
		}
		
		public Craft last() {
			if (built == null) throw new IllegalStateException("Crafts have not been built yet");
			return this.cached.isEmpty() ? null : this.cached.get(this.cached.size()-1);
		}
		
		public CraftSection section() {
			return this.section;
		}
		
	}
	
	public static Craft byUid(int uid) {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		for (List<Craft> crafts : built.values()) {
			Craft match = crafts.stream().filter((c)->uid == c.getUid()).findFirst().orElse(null);
			if (match != null) return match;
		}
		return null;
	}
	
	public static CraftSection firstSection() {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		return orderedSections.get(0);
	}
	
	public static CraftSection lastSection() {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		return orderedSections.get(orderedSections.size()-1);
	}
	
	public static CraftSection nextSection(CraftSection current) {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		int idx = orderedSections.indexOf(current);
		if (idx == -1) throw new IllegalArgumentException("Invalid section");
		return idx == orderedSections.size()-1 ? null : orderedSections.get(idx+1);
	}
	
	public static CraftSection prevSection(CraftSection current) {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		int idx = orderedSections.indexOf(current);
		if (idx == -1) throw new IllegalArgumentException("Invalid section");
		return idx == 0 ? null : orderedSections.get(idx-1);
	}
	
	public static CraftsSectionAccessor accessor(CraftSection section) {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		return Objects.requireNonNull(accessors.get(section), "Invalid section");
	}

}
