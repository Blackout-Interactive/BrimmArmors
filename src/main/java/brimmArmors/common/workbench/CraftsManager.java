package brimmArmors.common.workbench;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CraftsManager {
	
	private static final List<CraftBuilder> builders = new ArrayList<>();
	
	private static List<Craft> built;
	
	public static void register(CraftBuilder builder) {
		if (built != null) throw new IllegalStateException("Crafts already built");
		builders.add(Objects.requireNonNull(builder));
	}
	
	public static void buildAll() {
		if (built != null) throw new IllegalStateException("Crafts already built");
		built = builders.stream().map(CraftBuilder::build).collect(Collectors.toUnmodifiableList());
		builders.clear();
	}
	
	public static Craft next(Craft craft) {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		int idx = built.indexOf(craft);
		if (idx == -1) throw new IllegalArgumentException("Unregistered craft");
		return idx == built.size()-1 ? null : built.get(idx+1);
	}
	
	public static Craft prev(Craft craft) {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		int idx = built.indexOf(craft);
		if (idx == -1) throw new IllegalArgumentException("Unregistered craft");
		return idx == 0 ? null : built.get(idx-1);
	}
	
	public static Craft first() {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		return built.isEmpty() ? null : built.get(0);
	}
	
	public static Craft last() {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		return built.isEmpty() ? null : built.get(built.size()-1);
	}
	
	public static Craft byUid(int uid) {
		if (built == null) throw new IllegalStateException("Crafts have not been built yet");
		return built.stream().filter((c)->uid == c.getUid()).findFirst().orElse(null);
	}

}
