package blackoutInteractive.brimmArmors.client;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.toml.TomlParser;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static blackoutInteractive.brimmArmors.BrimmArmors.*;

public final class VersionChecker {
	
	private static final AtomicReference<VersionComparison> value = new AtomicReference<>();
	
	private VersionChecker() {}
	
	private enum VersionComparison {
		
		OUTDATED("message."+MOD_ID+".version_check_outdated", ChatFormatting.YELLOW),
		UP_TO_DATE("message."+MOD_ID+".version_check_good", ChatFormatting.GREEN),
		OVERDATED("message."+MOD_ID+".version_check_overdated", ChatFormatting.YELLOW),
		FAILED("message."+MOD_ID+".version_check_error", ChatFormatting.DARK_RED);
		
		final String mess_translatable;
		final ChatFormatting color;
		
		VersionComparison(String s, ChatFormatting c) { mess_translatable = s; color = c; }
		
	}
	
	private static final String NULL_CMP_MESS = "message."+MOD_ID+".version_check_incomplete";

	private static VersionComparison getResultSync() {
		return compareVersions(getLatestVersion(), getLocalVersion());
	}
	
	private static String getLocalVersion() {
		try (InputStream stream = VersionChecker.class.getClassLoader().getResourceAsStream("META-INF/mods.toml")) {
            if (stream == null) {
            	LOGGER.error("Failed to retrieve local Brimm Armors version: META-INF/mods.toml is not present.");
                return null;
            }
            UnmodifiableConfig config = new TomlParser().parse(stream);
            List<UnmodifiableConfig> mods = config.get("mods");
            if (mods != null) {
                for (UnmodifiableConfig mod : mods) {
                    String modId = mod.get("modId");
                    if (MOD_ID.equals(modId)) {
                        String version = mod.get("version");
                        if (version != null) {
                        	return version;
                        } else {
                        	LOGGER.error("Failed to retrieve local Brimm Armors version: the mod data don't have a 'version' field.");
                            return null;
                        }
                    }
                }
                LOGGER.error("Failed to retrieve local Brimm Armors version: no mod with mod id '"+MOD_ID+"' could be found.");
                return null;
            } else {
            	LOGGER.error("Failed to retrieve local Brimm Armors version: no mods list could be found.");
            	return null;
            }
        } catch (Exception e) {
        	LOGGER.error("Failed to retrieve local Brimm Armors version: an exception has occurred.", e);
        	return null;
        }
	}
	
	private static String getLatestVersion() {
        try {
        	final HttpClient client = HttpClient.newBuilder()
    	            .connectTimeout(Duration.ofSeconds(10))
    	            .build();
    		final String rawUrl = String.format("https://raw.githubusercontent.com/%s/%s/1.20.1/github-data/version.txt",
    				"Blackout-Interactive", "BrimmArmors");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(rawUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body().trim();
            } else {
            	LOGGER.error("Failed to retrieve latest Brimm Armors version: request at "+rawUrl+" got HTTP code "+response.statusCode()+".");
            	return null;
            }
        } catch (Exception e) {
        	LOGGER.error("Failed to retrieve latest Brimm Armors version: an exception has occurred.", e);
        	return null;
        }
	}
	
	private static VersionComparison compareVersions(String expected, String actual) {
		int[] ex = splitV(expected); int[] ac = splitV(actual);
		if (ex == null || ac == null) return VersionComparison.FAILED;
		for (int i=0;i<3;i++) {
			int cmp = Integer.compare(ex[i], ac[i]);
			if (cmp > 0) return VersionComparison.OUTDATED;
			else if (cmp < 0) return VersionComparison.OVERDATED;
		}
		return VersionComparison.UP_TO_DATE;
	}
	
	private static int[] splitV(String version) {
		if (version == null) return null;
		String[] raw = version.trim().split("\\.");
		if (raw.length != 3) {
			LOGGER.error("Failed to split version literal '"+version+"': invalid fields count.");
			return null;
		}
		int[] parsed = new int[3];
		try {
			for (int i=0;i<3;i++) {
				parsed[i] = Integer.parseInt(raw[i]);
				if (parsed[i] < 0) {
					LOGGER.error("Failed to split version literal '"+version+"': at least one field is a negative integer.");
					return null;
				}
			}
			return parsed;
		} catch(NumberFormatException ignored) {
			LOGGER.error("Failed to split version literal '"+version+"': at least one field is not a valid integer.");
			return null;
		}
	}
	
	public static void checkVersionAsync() {
		Thread t = new Thread(()->{
			var res = getResultSync();
			value.set(res);
		});
		t.setDaemon(true);
		t.start();
	}
	
	@SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() && event.getEntity() instanceof Player player) {
        	VersionComparison result = value.get();
        	if (result == null) {
        		player.sendSystemMessage(Component.translatable(NULL_CMP_MESS).withStyle(ChatFormatting.YELLOW));
        		checkVersionAsync();
        	} else {
        		player.sendSystemMessage(Component.translatable(result.mess_translatable).withStyle(result.color));
        		if (result == VersionComparison.FAILED) checkVersionAsync();
        	}
        }
	}

}
