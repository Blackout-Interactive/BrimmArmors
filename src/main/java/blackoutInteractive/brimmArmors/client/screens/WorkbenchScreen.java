package blackoutInteractive.brimmArmors.client.screens;

import java.util.List;
import java.util.Optional;

import org.joml.Vector3f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.common.packets.CraftPacket;
import blackoutInteractive.brimmArmors.common.workbench.Craft;
import blackoutInteractive.brimmArmors.common.workbench.CraftsManager;
import blackoutInteractive.ema_08_.rendering.geom.RTSMatricesCompound;
import blackoutInteractive.ema_08_.rendering.obj.ISingleObjModelProvider;
import blackoutInteractive.ema_08_.rendering.obj.IMultiObjModelProvider;
import blackoutInteractive.ema_08_.rendering.obj.ObjsManager;
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
    private CraftsManager.CraftsSectionAccessor section = CraftsManager.accessor(CraftsManager.firstSection());
    private String localisedSectionName;
    private Craft currentReceipe;
    private boolean dragging = false;

    public WorkbenchScreen() {
        super(Component.literal(I18n.get("screen." + BrimmArmors.MOD_ID + ".workbench.title")));
        currentReceipe = section.first();
        localisedSectionName = "\u00A7l"+section.section().localisedName();
    }
    
    @Override
    protected void init() {
        super.init();
        int centerX = width / 2;
        int bottomY = (height + BG_YSIZE) / 2 + 10;
        addRenderableWidget(Button.builder(
                Component.literal(ChatFormatting.BOLD + I18n.get("screen." + BrimmArmors.MOD_ID + ".workbench.craft")),
                button -> {
                    BrimmArmors.network.sendToServer(new CraftPacket(currentReceipe.id()));
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
                			Optional.ofNullable(section.prev(currentReceipe)).orElse(section.last());
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
            				Optional.ofNullable(section.next(currentReceipe)).orElse(section.first());
            		rotationX = 0;
            	    rotationY = 0;
                	mc.player.level().playSound(null, mc.player.blockPosition(),
                		    SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                })
                .pos(centerX + 50, bottomY)
                .size(20, 20)
                .build()
        );
        addRenderableWidget(Button.builder(
                Component.literal(ChatFormatting.BOLD + "<<<"),
                button -> {
                	section = CraftsManager.accessor(
                			Optional.ofNullable(CraftsManager.prevSection(section.section())).orElse(CraftsManager.lastSection()));
                	currentReceipe = section.first();
                	localisedSectionName = "\u00A7l"+section.section().localisedName();
            		rotationX = 0;
            	    rotationY = 0;
                    mc.player.level().playSound(null, mc.player.blockPosition(),
                            SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                })
                .pos(centerX - 70 - 20, bottomY + 22)
                .size(20, 20)
                .build()
        );
        addRenderableWidget(Button.builder(
                Component.literal(ChatFormatting.BOLD + ">>>"),
                button -> {
                	section = CraftsManager.accessor(
                			Optional.ofNullable(CraftsManager.nextSection(section.section())).orElse(CraftsManager.firstSection()));
                	currentReceipe = section.first();
                	localisedSectionName = "\u00A7l"+section.section().localisedName();
            		rotationX = 0;
            	    rotationY = 0;
                    mc.player.level().playSound(null, mc.player.blockPosition(),
                            SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                })
                .pos(centerX + 50 + 20, bottomY + 22)
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
        int darkColor = 0xC0101010;
        guig.fill(0, 0, width, height, darkColor);

        guig.blit(BACKGROUND_TEXTURE, guiLeft, guiTop, 0, 0, BG_XSIZE, BG_YSIZE, BG_XSIZE, BG_YSIZE);

        renderItem(guig, guig.pose(), guiLeft + BG_XSIZE / 2, guiTop + BG_YSIZE / 2);
        
        int sectionX = guiLeft + BG_XSIZE / 2 - mc.font.width(localisedSectionName) / 2;
        int sectionY = (height + BG_YSIZE) / 2 + 10 + 30;
        guig.drawString(mc.font, localisedSectionName, sectionX, sectionY, 0x555555, false);

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
        
        if (!dragging &&
        		(this.currentReceipe.result() instanceof ISingleObjModelProvider ||
        				this.currentReceipe.result() instanceof IMultiObjModelProvider) &&
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
    
    private void setupObjRendering(PoseStack poseStack, int x, int y) {
    	RenderSystem.enableBlend();
    	RenderSystem.defaultBlendFunc();
    	RenderSystem.enableDepthTest();
    	RenderSystem.setShaderLights(lightDir, lightDir);
        poseStack.translate(x, y, 150);
        poseStack.mulPose(Axis.YP.rotation(rotationY));
        poseStack.mulPose(Axis.XP.rotation(rotationX));
    }

    private void renderItem(GuiGraphics guig, PoseStack poseStack, int x, int y) {
    	
    	poseStack.pushPose();
        
        Item current = this.currentReceipe.result();
        
        if (current instanceof ISingleObjModelProvider modelRefP) {
        	setupObjRendering(poseStack, x, y);
            var modelRef = modelRefP.getModelRef();
            modelRef.modelTransforms.applyIfPresent(RTSMatricesCompound.key_workbench_render, poseStack);
            ObjsManager.getModel(modelRef).render(poseStack,
                Minecraft.getInstance().renderBuffers().bufferSource(),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, mc.getPartialTick()
            );
        } else if (current instanceof IMultiObjModelProvider modelRefsP) {
        	setupObjRendering(poseStack, x, y);
        	var modelRefs = modelRefsP.getModelRefs();
        	for (var modelRef : modelRefs) {
        		poseStack.pushPose();
        		modelRef.modelTransforms.applyIfPresent(RTSMatricesCompound.key_workbench_render, poseStack);
                ObjsManager.getModel(modelRef).render(poseStack,
                    Minecraft.getInstance().renderBuffers().bufferSource(),
                    LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, mc.getPartialTick()
                );
        		poseStack.popPose();
        	}
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
            rotationY += (float) (dx * 0.03f);
            rotationX += (float) (dy * 0.03f);
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

