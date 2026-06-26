package blackoutInteractive.brimmArmors.common.configurations;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Function;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.common.items.BrimmRarity;
import blackoutInteractive.ema_08_.parsing.TrivialDomReader;
import net.minecraftforge.fml.loading.FMLPaths;

public class ConfigsManager {
	
	private static final HashMap<String, ArmorConfig> configs = new HashMap<>();
	private static final HashSet<String> evictedConfigs = new HashSet<>();
	private static String hash;
	
	private static final String[]
			TAGS_TOUGHNESS = new String[] {"config", "toughness"},
			TAGS_KNOCKBACK_RESISTANCE = new String[] {"config", "knockback-resistance"},
			TAGS_DEFENSE = new String[] {"config", "defense"},
			TAGS_DURABILITY = new String[] {"config", "durability"},
			TAGS_RARITY = new String[] {"config", "rarity"};
	
	public static void init() {
		if (hash != null) throw new IllegalStateException("Configs manager already initialised");
		File configFolder = new File(FMLPaths.CONFIGDIR.get().toFile(), "brimm/overrides");
		if (!configFolder.isDirectory() && !configFolder.mkdirs()) {
			BrimmArmors.LOGGER.error("Failed to generate configs folder. No configurations will be loaded.");
		} else {
			MessageDigest digest = sha256();
			File[] configFiles = configFolder.listFiles();
			Arrays.sort(configFiles, Comparator.comparing(File::getName));
			for (File file : configFiles) {
				if (!file.isFile()) {
                    BrimmArmors.LOGGER.warn("Found unexpected subdir in configs folder ({}), ignoring.", file.getName());
				} else if (!file.getName().endsWith(".xml")) {
                    BrimmArmors.LOGGER.warn("Found unexpected file in configs folder ({}), ignoring.", file.getName());
				} else {
					try {
						TrivialDomReader xml = new TrivialDomReader(file);
						if (xml.getRootTag().equals("config")) {
							MaterialOverrides materialOverride = new MaterialOverrides(
									parseFloatFromXml(xml, TAGS_TOUGHNESS, 0.0f, 5.0f, false),
									parseFloatFromXml(xml, TAGS_KNOCKBACK_RESISTANCE, 0.0f, 1.0f, false),
									parseIntFromXml(xml, TAGS_DEFENSE, 0, 20, false),
									parseIntFromXml(xml, TAGS_DURABILITY, 0, 5001, true)
								);
							Optional<BrimmRarity> rarityOverride = mapOptional(
									parseStringFromXml(xml, TAGS_RARITY, (s)->{
										if (BrimmRarity.fromName(s) == null)
											return "no such rarity as '"+s+"'";
										else
											return null;
									}),
									BrimmRarity::fromName);
							String name = file.getName().replace(".xml", "");
							ArmorConfig config = new ArmorConfig(materialOverride, rarityOverride);
							digest.update(hashTrailOf(config));
							configs.put(name, config);
                            BrimmArmors.LOGGER.info("Loaded config file {}.", file.getName());
						} else {
                            BrimmArmors.LOGGER.error("Failed to load config file {}: invalid document tag. That configuration will not be loaded.", file.getName());
						}
					} catch (Exception e) {
                        BrimmArmors.LOGGER.error("Failed to load config file {} due to an exception. That configuration will not be loaded.", file.getName(), e);
					}
				}
			}
			byte[] hashBytes = digest.digest();
			StringBuilder hex = new StringBuilder();
			for (byte b : hashBytes) {
			    hex.append(String.format("%02x", b));
			}
			hash = hex.toString();
		}
	}
	
	private static <I, F> Optional<F> mapOptional(Optional<I> initial, Function<I, F> transformer) {
		return initial.isEmpty() ? Optional.empty() : Optional.of(transformer.apply(initial.get()));
	}
	
	private static String last(String[] arr) { return arr[arr.length-1]; }
	
	private static String filename(TrivialDomReader xml) { return new File(xml.getLoadedFilePath()).getName(); }
	
	private static Optional<String> parseStringFromXml(TrivialDomReader xml, String[] tagspath, Function<String, String> tester) {
		if (xml.elementExists(tagspath)) {
			String value = xml.getElementValue(tagspath);
			String error = tester.apply(value);
			if (error != null) {
                BrimmArmors.LOGGER.warn("Invalid {} config value in {} config file: {}. This config file's entry will be skipped, although the rest of the file will be parsed.", last(tagspath), filename(xml), error);
				return Optional.empty();
			} else {
				return Optional.of(value);
			}
		} else {
			return Optional.empty();
		}
	}
	
	private static Optional<Integer> parseIntFromXml(TrivialDomReader xml, String[] tagspath, int min, int max, boolean exclusive) {
		try {
			if (xml.elementExists(tagspath)) {
				int value = xml.getElementValueCastInt(tagspath);
				if (exclusive ? (value <= min) : (value < min)) {
                    BrimmArmors.LOGGER.warn("Invalid {} config value in {} config file: too little number. This config file's entry will be skipped, although the rest of the file will be parsed.", last(tagspath), filename(xml));
					return Optional.empty();
				}
				if (exclusive ? (value >= max) : (value > max)) {
                    BrimmArmors.LOGGER.warn("Invalid {} config value in {} config file: too large number. This config file's entry will be skipped, although the rest of the file will be parsed.", last(tagspath), filename(xml));
					return Optional.empty();
				}
				return Optional.of(value);
			} else {
				return Optional.empty();
			}				
		} catch (NumberFormatException e) {
            BrimmArmors.LOGGER.warn("Invalid {} config value in {} config file: not an integer. This config file's entry will be skipped, although the rest of the file will be parsed.", last(tagspath), filename(xml));
			return Optional.empty();
		}
	}
	
	private static Optional<Float> parseFloatFromXml(TrivialDomReader xml, String[] tagspath, float min, float max, boolean exclusive) {
		try {
			if (xml.elementExists(tagspath)) {
				float value = (float)xml.getElementValueCastDouble(tagspath);
				if (exclusive ? (value <= min) : (value < min)) {
                    BrimmArmors.LOGGER.warn("Invalid {} config value in {} config file: too little number. This config file's entry will be skipped, although the rest of the file will be parsed.", last(tagspath), filename(xml));
					return Optional.empty();
				}
				if (exclusive ? (value >= max) : (value > max)) {
                    BrimmArmors.LOGGER.warn("Invalid {} config value in {} config file: too large number. This config file's entry will be skipped, although the rest of the file will be parsed.", last(tagspath), filename(xml));
					return Optional.empty();
				}
				return Optional.of(value);
			} else {
				return Optional.empty();
			}				
		} catch (NumberFormatException e) {
            BrimmArmors.LOGGER.warn("Invalid {} config value in {} config file: not a number. This config file's entry will be skipped, although the rest of the file will be parsed.", last(tagspath), filename(xml));
			return Optional.empty();
		}
	}
	
	private static MessageDigest sha256() {
	    try {
	        return MessageDigest.getInstance("SHA-256");
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("SHA-256 algorithm not available", e);
	    }
	}
	
	private static byte[] hashTrailOf(ArmorConfig config) {
	    if (config == null) return new byte[0];
	    StringBuilder sb = new StringBuilder();
	    MaterialOverrides mo = config.materialOverrides();
	    if (mo == null) {
	        sb.append("null|null|null|null|");
	    } else {
	        sb.append(mo.toughness()
	                .map(v -> Integer.toString(Float.floatToIntBits(v)))
	                .orElse("null")).append('|');
	        sb.append(mo.knockbackResistance()
	                .map(v -> Integer.toString(Float.floatToIntBits(v)))
	                .orElse("null")).append('|');
	        sb.append(mo.defenseValue()
	                .map(Object::toString)
	                .orElse("null")).append('|');
	        sb.append(mo.durabilityValue()
	                .map(Object::toString)
	                .orElse("null")).append('|');
	    }
	    Optional<BrimmRarity> rarityOpt = config.rarityOverride();
	    if (rarityOpt == null || rarityOpt.isEmpty()) {
	        sb.append("null");
	    } else {
	        sb.append(rarityOpt.get().name());
	    }
	    return sb.toString().getBytes(StandardCharsets.UTF_8);
	}
	
	public static String configHash() {
		if (hash == null) throw new IllegalStateException("Configs manager not yet initialised");
		return hash;
	}

	public static ArmorConfig getAndEvict(String name) {
		if (hash == null) throw new IllegalStateException("Configs manager not yet initialised");
		if (!evictedConfigs.add(name)) throw new IllegalStateException("Material config for "+name+" had been already evicted");
		return configs.remove(name);
	}

}
