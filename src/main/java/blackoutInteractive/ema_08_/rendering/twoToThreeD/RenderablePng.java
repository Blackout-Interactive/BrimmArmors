package blackoutInteractive.ema_08_.rendering.twoToThreeD;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RenderablePng {

    private static final float Z = 0.001f;

    private final ResourceLocation texture;
    private final String name;
    private final float width;
    private final float height;

    public RenderablePng(ResourceLocation texture, String name, int texWidth, int texHeight, float worldWidth) {
        this.texture = texture;
        this.name = name;
        this.width = worldWidth;
        this.height = worldWidth * ((float)texHeight / texWidth);
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource,
                       int packedLight, int overlay, float partialTick) {

        var builder = bufferSource.getBuffer(RenderType.entityCutout(texture));
        Matrix4f matrix = poseStack.last().pose();

        builder.vertex(matrix, 0f, 0f, Z).color(255,255,255,255)
        	.uv(0,0).overlayCoords(overlay).uv2(packedLight).normal(0,0,1).endVertex();
        builder.vertex(matrix, width, 0f, Z).color(255,255,255,255)
        	.uv(1,0).overlayCoords(overlay).uv2(packedLight).normal(0,0,1).endVertex();
        builder.vertex(matrix, width, height, Z).color(255,255,255,255)
        	.uv(1,1).overlayCoords(overlay).uv2(packedLight).normal(0,0,1).endVertex();
        builder.vertex(matrix, 0f, height, Z).color(255,255,255,255)
        	.uv(0,1).overlayCoords(overlay).uv2(packedLight).normal(0,0,1).endVertex();

        builder.vertex(matrix, 0f, 0f, Z).color(255,255,255,255)
        	.uv(0,0).overlayCoords(overlay).uv2(packedLight).normal(0,0,-1).endVertex();
        builder.vertex(matrix, 0f, height, Z).color(255,255,255,255)
        	.uv(0,1).overlayCoords(overlay).uv2(packedLight).normal(0,0,-1).endVertex();
        builder.vertex(matrix, width, height, Z).color(255,255,255,255)
        	.uv(1,1).overlayCoords(overlay).uv2(packedLight).normal(0,0,-1).endVertex();
        builder.vertex(matrix, width, 0f, Z).color(255,255,255,255)
        	.uv(1,0).overlayCoords(overlay).uv2(packedLight).normal(0,0,-1).endVertex();
    }
    
    @Override
    public String toString() {
    	return getClass().getSimpleName()+":"+this.name;
    }
    
}
