package brimmArmors.client.screens;

import java.util.List;
import java.util.Optional;

import org.joml.Vector3f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import brimmArmors.BrimmArmors;
import brimmArmors.common.packets.CraftPacket;
import brimmArmors.common.workbench.Craft;
import brimmArmors.common.workbench.CraftsManager;
import ema_08_.rendering.geom.RTSMatricesCompound;
import ema_08_.rendering.obj.IObjModelProvider;
import ema_08_.rendering.obj.ObjsManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WorkbenchScreen extends Screen {
	
	private static final ResourceLocation BACKGROUND_TEXTURE =
	        new ResourceLocation(BrimmArmors.MOD_ID, "textures/gui/workbench_bg.png");
	private static final int BG_XSIZE = 192, BG_YSIZE = 129;
	private static final float ITEMICONS_SCALE = 4.0f;
	private static final Vector3f lightDir = new Vector3f(0.0F, 0.0F, 1.0F);

	private final Minecraft mc = Minecraft.getInstance();
	private final List<Component> tooltip = List.of(
			Component.literal(I18n.get("screen." + BrimmArmors.MOD_ID + ".workbench.tooltip")));
    private float rotationX = 0;
    private float rotationY = 0;
    private Craft currentReceipe = CraftsManager.first();
    private boolean dragging = false;

    public WorkbenchScreen() {
        super(Component.literal(I18n.get("screen." + BrimmArmors.MOD_ID + ".workbench.title")));
    }
    
    @Override
    protected void init() {
        super.init();
        int centerX = width / 2;
        int bottomY = (height + BG_YSIZE) / 2 + 10;
        addRenderableWidget(Button.builder(
                Component.literal(ChatFormatting.BOLD + I18n.get("screen." + BrimmArmors.MOD_ID + ".workbench.craft")),
                button -> {
                    BrimmArmors.network.sendToServer(new CraftPacket(currentReceipe.getUid()));
                	mc.player.level().playSound(null, mc.player.blockPosition(),
                		    SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                })
                .pos(centerX - 30, bottomY)
                .size(60, 20)
                .build()
        );
        addRenderableWidget(Button.builder(
                Component.literal(ChatFormatting.BOLD + "<"),
                button -> {
                	currentReceipe =
                			Optional.ofNullable(CraftsManager.prev(currentReceipe)).orElse(CraftsManager.last());
            		rotationX = 0;
            	    rotationY = 0;
                	mc.player.level().playSound(null, mc.player.blockPosition(),
                		    SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                })
                .pos(centerX - 70, bottomY)
                .size(20, 20)
                .build()
        );
        addRenderableWidget(Button.builder(
                Component.literal(ChatFormatting.BOLD + ">"),
                button -> {
                	currentReceipe =
            				Optional.ofNullable(CraftsManager.next(currentReceipe)).orElse(CraftsManager.first());
            		rotationX = 0;
            	    rotationY = 0;
                	mc.player.level().playSound(null, mc.player.blockPosition(),
                		    SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                })
                .pos(centerX + 50, bottomY)
                .size(20, 20)
                .build()
        );
    }

    @Override
    public void render(GuiGraphics guig, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guig);
        super.render(guig, mouseX, mouseY, partialTicks);

        int guiLeft = (width - BG_XSIZE) / 2;
        int guiTop = (height - BG_YSIZE) / 2;

        guig.blit(BACKGROUND_TEXTURE, guiLeft, guiTop, 0, 0, BG_XSIZE, BG_YSIZE, BG_XSIZE, BG_YSIZE);

        renderItem(guig, guig.pose(), guiLeft + BG_XSIZE / 2, guiTop + BG_YSIZE / 2);

        String itemName = currentReceipe.result().getName(new ItemStack(currentReceipe.result())).getString();
        int nameX = guiLeft + BG_XSIZE / 2 - mc.font.width(itemName) / 2;
        int nameY = guiTop - mc.font.lineHeight - 2;
        guig.drawString(mc.font, itemName, nameX, nameY, 0xFFFFFF, false);

        int ingX = guiLeft + BG_XSIZE + 10;
        int ingY = guiTop;
        for (var ing : currentReceipe.ingredients()) {
            String ingText = "• " + ing.amt() + "x " + ing.type().getName(new ItemStack(ing.type())).getString();
            guig.drawString(mc.font, ingText, ingX, ingY, 0xAAAAAA, false);
            ingY += mc.font.lineHeight + 2;
        }
        
        if (!dragging && this.currentReceipe.result() instanceof IObjModelProvider &&
                mouseX >= guiLeft && mouseX <= guiLeft + BG_XSIZE &&
                mouseY >= guiTop && mouseY <= guiTop + BG_YSIZE) {

                guig.renderTooltip(
                    mc.font,
                    tooltip,
                    Optional.empty(),
                    mouseX,
                    mouseY
                );
            }
        
    }

    private void renderItem(GuiGraphics guig, PoseStack poseStack, int x, int y) {
        poseStack.pushPose();
        
        Item current = this.currentReceipe.result();
        
        if (current instanceof IObjModelProvider model) {
        	RenderSystem.enableBlend();
        	RenderSystem.defaultBlendFunc();
        	RenderSystem.enableDepthTest();
        	RenderSystem.setShaderLights(lightDir, lightDir);
            poseStack.translate(x, y, 150);
            poseStack.mulPose(Axis.YP.rotation(rotationY));
            poseStack.mulPose(Axis.XP.rotation(rotationX));
            model.getTransformations().applyIfPresent(RTSMatricesCompound.key_workbench_render, poseStack);
            ObjsManager.getModel(model).render(poseStack,
                Minecraft.getInstance().renderBuffers().bufferSource(),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY
            );
        } else {
            ItemStack itemStack = new ItemStack(current);
            poseStack.translate(x, y, 100);
            poseStack.scale(ITEMICONS_SCALE, ITEMICONS_SCALE, 1f); 
            guig.renderItem(itemStack, -8, -8);
        }

        poseStack.popPose();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (dragging) {
            rotationY += dx * 0.03f;
            rotationX += dy * 0.03f;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dx, dy);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        dragging = true;
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (super.mouseReleased(mouseX, mouseY, button)) return true;
        dragging = false;
        rotationX = 0;
        rotationY = 0;
        return true;
    }
    
    @Override
    public void tick() {
        super.tick();
        if (!dragging) rotationY += 0.04f;
    }

}

