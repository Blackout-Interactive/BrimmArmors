package brimmArmors.common.configurations;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import brimmArmors.BrimmArmors;
import ema_08_.parsing.TrivialDomReader;
import net.minecraftforge.fml.loading.FMLPaths;

public class ConfigsManager {
	
	private static final HashMap<String, MaterialOverrides> materialConfigs = new HashMap<>();
	private static final HashSet<String> evictedMaterialConfigs = new HashSet<>();
	
	private static final String[]
			TAGS_TOUGHNESS = new String[] {"config", "toughness"},
			TAGS_KNOCKBACK_RESISTANCE = new String[] {"config", "knockback-resistance"},
			TAGS_DEFENSE = new String[] {"config", "defense"},
			TAGS_DURABILITY = new String[] {"config", "durability"};
	
	public static void init() {
		File configFolder = new File(FMLPaths.CONFIGDIR.get().toFile(), "brimm/overrides");
		if (!configFolder.isDirectory() && !configFolder.mkdirs()) {
			BrimmArmors.LOGGER.error("Failed to generate configs folder. No configurations will be loaded.");
		} else {
			for (File file : configFolder.listFiles()) {
				if (!file.isFile()) {
					BrimmArmors.LOGGER.warn("Found unexpected subdir in configs folder ("+file.getName()+"), ignoring.");
				} else if (!file.getName().endsWith(".xml")) {
					BrimmArmors.LOGGER.warn("Found unexpected file in configs folder ("+file.getName()+"), ignoring.");
				} else {
					try {
						TrivialDomReader xml = new TrivialDomReader(file);
						if (xml.getRootTag().equals("config")) {
							MaterialOverrides config = new MaterialOverrides(
									parseFloatFromXml(xml, TAGS_TOUGHNESS, 0.0f, 5.0f, false),
									parseFloatFromXml(xml, TAGS_KNOCKBACK_RESISTANCE, 0.0f, 1.0f, false),
									parseIntFromXml(xml, TAGS_DEFENSE, 0, 20, false),
									parseIntFromXml(xml, TAGS_DURABILITY, 0, 5001, true)
								);
							String name = file.getName().replace(".xml", "");
							materialConfigs.put(name, config);
							BrimmArmors.LOGGER.info("Loaded config file "+file.getName()+".");
						} else {
							BrimmArmors.LOGGER.error("Failed to load config file "+file.getName()+": invalid document tag. "
									+ "That configuration will not be loaded.");
						}
					} catch (Exception e) {
						BrimmArmors.LOGGER.error("Failed to load config file "+file.getName()+" due to an exception. "
								+ "That configuration will not be loaded.", e);
					}
				}
			}
		}
	}
	
	private static String last(String[] arr) { return arr[arr.length-1]; }
	
	private static String filename(TrivialDomReader xml) { return new File(xml.getLoadedFilePath()).getName(); }
	
	private static Optional<Integer> parseIntFromXml(TrivialDomReader xml, String[] tagspath, int min, int max, boolean exclusive) {
		try {
			if (xml.elementExists(tagspath)) {
				int value = xml.getElementValueCastInt(tagspath);
				if (exclusive ? (value <= min) : (value < min)) {
					BrimmArmors.LOGGER.warn("Invalid "+last(tagspath)+" config value in "+filename(xml)+" config file: too little number. "
							+ "This config file's entry will be skipped, although the rest of the file will be parsed.");
					return Optional.empty();
				}
				if (exclusive ? (value >= max) : (value > max)) {
					BrimmArmors.LOGGER.warn("Invalid "+last(tagspath)+" config value in "+filename(xml)+" config file: too large number. "
							+ "This config file's entry will be skipped, although the rest of the file will be parsed.");
					return Optional.empty();
				}
				return Optional.of(value);
			} else {
				return Optional.empty();
			}				
		} catch (NumberFormatException e) {
			BrimmArmors.LOGGER.warn("Invalid "+last(tagspath)+" config value in "+filename(xml)+" config file: not an integer. "
					+ "This config file's entry will be skipped, although the rest of the file will be parsed.");
			return Optional.empty();
		}
	}
	
	private static Optional<Float> parseFloatFromXml(TrivialDomReader xml, String[] tagspath, float min, float max, boolean exclusive) {
		try {
			if (xml.elementExists(tagspath)) {
				float value = (float)xml.getElementValueCastDouble(tagspath);
				if (exclusive ? (value <= min) : (value < min)) {
					BrimmArmors.LOGGER.warn("Invalid "+last(tagspath)+" config value in "+filename(xml)+" config file: too little number. "
							+ "This config file's entry will be skipped, although the rest of the file will be parsed.");
					return Optional.empty();
				}
				if (exclusive ? (value >= max) : (value > max)) {
					BrimmArmors.LOGGER.warn("Invalid "+last(tagspath)+" config value in "+filename(xml)+" config file: too large number. "
							+ "This config file's entry will be skipped, although the rest of the file will be parsed.");
					return Optional.empty();
				}
				return Optional.of(value);
			} else {
				return Optional.empty();
			}				
		} catch (NumberFormatException e) {
			BrimmArmors.LOGGER.warn("Invalid "+last(tagspath)+" config value in "+filename(xml)+" config file: not a number. "
					+ "This config file's entry will be skipped, although the rest of the file will be parsed.");
			return Optional.empty();
		}
	}
	
	public static MaterialOverrides getAndEvictMaterial(String name) {
		if (!evictedMaterialConfigs.add(name)) throw new IllegalStateException("Material config for "+name+" had been already evicted");
		return materialConfigs.remove(name);
	}

}
