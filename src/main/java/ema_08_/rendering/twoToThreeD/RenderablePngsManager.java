package ema_08_.rendering.twoToThreeD;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import com.mojang.blaze3d.platform.NativeImage;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class RenderablePngsManager {
	
	private static final ConcurrentHashMap<String, RenderablePng> cache = new ConcurrentHashMap<>();
	
	private static String hash(ResourceLocation texture) {
		return "@"+texture;
	}
	
	public static RenderablePng transform(String textureName, ResourceLocation texture) {
		int[] wh = wh(texture);
		return new RenderablePng(texture, textureName, wh[0], wh[1], 1);
	}
	
	public static RenderablePng transformOrGet(String textureName, ResourceLocation texture) {
		return cache.computeIfAbsent(hash(texture), (hash)->transform(textureName, texture));
	}
	
	public static RenderablePng transformOrGet(IRenderablePngProvider provider) {
		return transformOrGet(provider.getTextureName(), provider.getTexturePng());
	}
	
	private static int[] wh(ResourceLocation loc) {
		try(InputStream in = Minecraft.getInstance().getResourceManager().getResource(loc).orElseThrow().open()) {
			NativeImage img = NativeImage.read(in);
	        return new int[] {img.getWidth(), img.getHeight()};
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not open the given texture", e);
		}
	}

}
