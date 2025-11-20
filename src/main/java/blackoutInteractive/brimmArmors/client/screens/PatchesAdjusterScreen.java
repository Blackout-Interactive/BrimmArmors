package blackoutInteractive.brimmArmors.client.screens;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import blackoutInteractive.brimmArmors.common.items.BasicArmor;
import blackoutInteractive.brimmArmors.common.registries.ItemRegistry;
import blackoutInteractive.ema_08_.rendering.geom.MatrixRTS;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;

public class PatchesAdjusterScreen extends Screen {

    private static final Vector3f lightDir = new Vector3f(0.0F, 0.0F, 1.0F);

    private final String armorItemName;
    private final OverlayPos op;
    private LivingEntity dummyEntity;
    private ItemStack wornArmor;
    private final Minecraft mc = Minecraft.getInstance();

    private float rotationX = 0f;
    private float rotationY = 0f;
    private float scale = 1.0f;
    private boolean dragging = false;
    private int draggingButton = -1;

    private float translateX = 0f;
    private float translateY = 0f;

    private final float TRANSLATE_BOUND_X = 500f;
    private final float TRANSLATE_BOUND_Y = 500f;

    private final float[] matrix = new float[9];
    private EditBox[] matrixFields = new EditBox[9];

    private boolean isMatrixUpToDate = true;
    private ColoredButton updateButton;

    public PatchesAdjusterScreen(String armorItemName, OverlayPos op) {
        super(Component.literal("Patches Adjuster"));
        this.armorItemName = armorItemName;
        this.op = op;
    }

    @Override
    protected void init() {
        super.init();

        ClientLevel clientWorld = this.mc.level;
        if (clientWorld == null) return;
        this.dummyEntity = new ArmorStand(clientWorld, 0, 0, 0);
        this.dummyEntity.setInvisible(false);
        this.dummyEntity.setNoGravity(true);

        var item = ItemRegistry.getOrThrow(this.armorItemName);
        if (!(item instanceof BasicArmor armorItem)) return;

        this.wornArmor = new ItemStack(item);
        armorItem.setPatch(this.wornArmor, ItemRegistry.DEBUG_PATCH.get());
        this.dummyEntity.setItemSlot(armorItem.getEquipmentSlot(), this.wornArmor);

        this.matrix[0] = 1; this.matrix[1] = 1; this.matrix[2] = 1;
        this.matrix[3] = 0; this.matrix[4] = 0; this.matrix[5] = 0;
        this.matrix[6] = 0; this.matrix[7] = 0; this.matrix[8] = 0;
        
        updatePatchMatrix();

        String[] LABELS = {
            "ScX:", "ScY:", "ScZ:",
            "RtX:", "RtY:", "RtZ:",
            "TrX:", "TrY:", "TrZ:"
        };

        int leftX = 10;
        int startY = 50;
        int spacing = 25;
        int labelWidth = 30;
        int padding = 5;

        for (int i = 0; i < 9; i++) {
            int boxY = startY + i * spacing;

            this.addRenderableOnly(new RenderableLabel(
                LABELS[i],
                leftX,
                boxY + 4,
                0xFFFFFF
            ));

            EditBox box = new EditBox(
                    this.font,
                    leftX + labelWidth + padding,
                    boxY,
                    80,
                    18,
                    Component.literal(LABELS[i])
            );

            box.setValue(Float.toString(this.matrix[i]));

            final int index = i;
            box.setResponder(s -> onMatrixFieldChanged(index, s));

            this.matrixFields[i] = box;
            this.addRenderableWidget(box);
        }

        int buttonX = 10;
        int buttonY = startY + 9 * spacing + 10;
        int buttonWidth = 80;
        int buttonHeight = 20;

        updateButton = new ColoredButton(buttonX, buttonY, buttonWidth, buttonHeight, Component.literal("UPDATE"), btn -> {
            updatePatchMatrix();
            isMatrixUpToDate = true;
        });
        this.addRenderableWidget(updateButton);
    }

    private void onMatrixFieldChanged(int index, String value) {
        try {
            this.matrix[index] = Float.parseFloat(value);
            this.isMatrixUpToDate = false;
        } catch (NumberFormatException ignored) {}
    }

    private void updatePatchMatrix() {
        if (!(this.wornArmor.getItem() instanceof BasicArmor armor))
            throw new IllegalStateException("Expected BasicArmor, got " + this.wornArmor.getItem().getClass().getSimpleName());

        MatrixRTS mat = MatrixRTS.getMatrix(
            this.matrix[6], this.matrix[7], this.matrix[8],
            this.matrix[3], this.matrix[4], this.matrix[5],
            this.matrix[0], this.matrix[1], this.matrix[2],
            false
        );

        armor.setPatchesDebugOverride(this.wornArmor, mat, this.op);
    }

    // MOUSE INPUT

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        if (button == 0 || button == 1) {
            this.dragging = true;
            this.draggingButton = button;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (super.mouseReleased(mouseX, mouseY, button)) return true;
        if (button == this.draggingButton) {
            this.dragging = false;
            this.draggingButton = -1;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (!this.dragging) return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);

        if (button == 0) {
            this.rotationY += dragX * 0.1f;
            this.rotationX += dragY * 0.1f;
            this.rotationX = Mth.clamp(this.rotationX, -90, 90);
            return true;
        } else if (button == 1) {
            this.translateX += dragX;
            this.translateY += dragY;
            this.translateX = Mth.clamp(this.translateX, -this.TRANSLATE_BOUND_X, this.TRANSLATE_BOUND_X);
            this.translateY = Mth.clamp(this.translateY, -this.TRANSLATE_BOUND_Y, this.TRANSLATE_BOUND_Y);
            return true;
        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        float scaleFactor = 0.1f;
        float newScale = this.scale + (float) (delta * scaleFactor);
        this.scale = Mth.clamp(newScale, 0.5f, 20f);
        return true;
    }

    // RENDER

    @Override
    public void render(GuiGraphics guig, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(guig);
        guig.fill(0, 0, this.width, this.height, 0xC0101010);

        guig.drawString(this.font, "Armor: " + this.armorItemName + "; Overlay Pos: "+this.op.name(), 10, 15, 0xFFFFFF);

        guig.pose().pushPose();
        guig.pose().translate(this.translateX, this.translateY, 0);

        int centerX = this.width / 2;
        int centerY = this.height / 2 + 30;
        renderEntityOnScreen(guig.pose(), centerX, centerY, this.scale, this.rotationX, this.rotationY, this.dummyEntity);

        guig.pose().popPose();

        super.render(guig, mouseX, mouseY, partialTicks);
    }

    private static void renderEntityOnScreen(PoseStack poseStack, int posX, int posY, float scale,
                                             float rotX, float rotY, LivingEntity entity) {
        poseStack.pushPose();
        poseStack.translate(posX, posY, 1050);
        poseStack.scale(50, -50, 50);
        poseStack.scale(scale, scale, scale);

        Quaternionf rotation = Axis.YP.rotationDegrees(rotY);
        poseStack.mulPose(rotation);

        rotation = Axis.XP.rotationDegrees(rotX);
        poseStack.mulPose(rotation);

        RenderSystem.setShaderLights(lightDir, lightDir);

        var buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        EntityRenderDispatcher renderer = Minecraft.getInstance().getEntityRenderDispatcher();
        renderer.render(entity, 0, 0, 0, 0, 1f, poseStack, buffer, 0xf000f0);
        buffer.endBatch();

        poseStack.popPose();
    }

    private static class RenderableLabel implements Renderable {

        private final Minecraft mc = Minecraft.getInstance();
        private final String text;
        private final int x, y, color;

        public RenderableLabel(String text, int x, int y, int color) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.color = color;
        }

        @Override
        public void render(GuiGraphics g, int mouseX, int mouseY, float partialTicks) {
            g.drawString(this.mc.font, text, x, y, color);
        }
    }

    private class ColoredButton extends Button {
    	
    	private final int x, y;
    	
        public ColoredButton(int x, int y, int width, int height, Component title, OnPress onPress) {
        	super(x, y, width, height, title, onPress, Button.DEFAULT_NARRATION);
        	this.x = x; this.y = y;
        }

        @Override
        public void renderWidget(GuiGraphics guig, int mouseX, int mouseY, float partialTicks) {
            super.renderWidget(guig, mouseX, mouseY, partialTicks);
            int color = isMatrixUpToDate ? 0xFF00FF00 : 0xFFFF0000;
            guig.drawCenteredString(font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
        }
    }
}
