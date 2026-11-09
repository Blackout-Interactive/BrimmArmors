package blackoutInteractive.brimmArmors.client.compat.epicfight;

import java.util.concurrent.atomic.AtomicBoolean;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.google.common.base.Objects;
import com.mojang.blaze3d.vertex.PoseStack;

import blackoutInteractive.brimmArmors.client.render.ArmorModelRendering;
import blackoutInteractive.brimmArmors.common.items.BrimmArmor;
import blackoutInteractive.ema_08_.rendering.obj.ArmorModelType;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayPos;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.Joint;                                   
import yesman.epicfight.api.animation.Pose;                                    
import yesman.epicfight.api.model.Armature;                                    
import yesman.epicfight.api.utils.math.OpenMatrix4f;                           
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;            
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;      

public class BrimmArmorPatchedLayer<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>>
	extends PatchedLayer<E, T, M, RenderLayer<E, M>> {
	
	/*
	 * KNOWN ISSUE:
	 * Full leg-sized leggings models will not bend along with the knee during EF rendering, and will thus look quite broken.
	 * Such issue does not occur for most knee pads (or is barely noticeable), though it must be considered for future additions.
	 */
	
	/*
	 * As there are plenty of assumption around static caches (no multi-threading, no re-entrant calls, a hard fail would be best than
	 * hard to debug corruption.
	 */
	private static final AtomicBoolean state = new AtomicBoolean();

    private enum Anchor {
    	
        HEAD   ("Head",    "Head"),
        CHEST  ("Chest",   "Head"),
        THIGH_R("Thigh_R", "Thigh_R"),
        THIGH_L("Thigh_L", "Thigh_L"),
        LEG_R  ("Leg_R",   "Thigh_R"),
        LEG_L  ("Leg_L",   "Thigh_L");

    	final String follow, pivot;
    	final boolean commonJoint;
        Anchor(String follow, String pivot) { this.follow = follow; this.pivot = pivot; this.commonJoint = Objects.equal(follow, pivot); }
        
        static Anchor of(ArmorModelType t) {
            return switch (t) {
                case HELMET -> HEAD;
                case CHESTPLATE -> CHEST;
                case LEGGINGS_R -> THIGH_R;
                case LEGGINGS_L -> THIGH_L;
                case BOOTS_R -> LEG_R;
                case BOOTS_L -> LEG_L;
            };
        }

        static Anchor of(OverlayPos p) {
            return switch (p) {
                case HUMANOID_HEAD -> HEAD;
                case HUMANOID_TORSO -> CHEST;
            };
        }
        
        /*USE AS READ ONLY!*/
        static final Anchor[] VALUES = values();
        static final int COUNT = VALUES.length;
    }
	
	/*Static variables as cache assumes that resolveAnchors is called only on render thread*/
    
    private static final Matrix4f[] _anchors;
    static {
    	_anchors = new Matrix4f[Anchor.COUNT];
    	 for (int i=0;i<Anchor.COUNT;i++) _anchors[i] = new Matrix4f();
    }

    @Override
    public void renderLayer(T patch, E entity, RenderLayer<E, M> vanillaLayer,
                            PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                            OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
    	if (!state.compareAndSet(false, true))
    	    throw new IllegalStateException("Multi-thread or re-entrant call detected during brimm EF rendering");
    	try {
    		Matrix4f[] anchors = null;
            for (ItemStack stack : entity.getArmorSlots()) {
            	if (stack.getItem() instanceof BrimmArmor armor) {
            		if (anchors == null) anchors = resolveAnchors(patch, partialTicks, _anchors);
            		ArmorModelRendering.renderCore(
            				BrimmArmorPatchedLayer::transformByPos,
                            armor, poseStack, buffer, partialTicks, packedLight, OverlayTexture.NO_OVERLAY, anchors);
                    ArmorModelRendering.renderPatches(
                    		BrimmArmorPatchedLayer::transformByPos,
                            armor, stack, poseStack, buffer, partialTicks, packedLight, OverlayTexture.NO_OVERLAY, anchors);
            	}
            }
    	} finally {
    	    if (!state.compareAndSet(true, false))
    	    	throw new IllegalStateException("Multi-thread or re-entrant call detected during brimm EF rendering");
    	}
    }
    
    /*Static variables as cache assumes that resolveAnchors is called only on render thread*/
    
    private static final Matrix4f _m4f_1 = new Matrix4f(), _m4f_2 = new Matrix4f(), _m4f_3 = new Matrix4f();
    private static final Vector3f _v3f_1 = new Vector3f(), _v3f_2 = new Vector3f(), _v3f_3 = new Vector3f();
    private static final Matrix3f _m3f_1 = new Matrix3f(), _m3f_2 = new Matrix3f();
    
    private static Matrix4f[] resolveAnchors(LivingEntityPatch<?> patch, float partialTicks, Matrix4f[] out) {
        final Armature armature = patch.getArmature();
        final Pose pose = patch.getAnimator().getPose(partialTicks);
        for (int i=0;i<Anchor.COUNT;i++) {
        	Anchor anchor = Anchor.VALUES[i];
        	
        	/*Declarations of local scoped variables (from caches)*/
        	final Matrix4f fNow = _m4f_1;
        	final Matrix4f fRest = _m4f_2;
        	final Vector3f fRestPos = _v3f_1;
        	final Vector3f pRestPos = _v3f_2;
        	final Matrix3f delta = _m3f_1;
        	final Matrix4f buf_1 = _m4f_3;
        	final Matrix3f buf_2 = _m3f_2;
        	final Vector3f buf_3 = _v3f_3;
        	
        	/*
        	 * buf_* variables are assumed to be reusable within blocks at all times
        	 */
        	
        	/*Joints getting (effectively allocation free as those are getting an existing object)*/
        	final Joint follow = armature.searchJointByName(anchor.follow);
        	if (follow == null)
        		throw new RuntimeException(String.format("EF joint named %s could not be located", anchor.follow));
        	final Joint pivot = anchor.commonJoint ? follow : armature.searchJointByName(anchor.pivot);
        	if (pivot == null)
        		throw new RuntimeException(String.format("EF joint named %s could not be located", anchor.pivot));
        	
        	/*Setting fNow/fRest matrices*/
        	OpenMatrix4f.exportToMojangMatrix(armature.getBoundTransformFor(pose, follow), fNow);
        	OpenMatrix4f.exportToMojangMatrix(armature.getBoundTransformFor(Pose.EMPTY_POSE, follow), fRest);
        	
        	/*Setting *RestPos vectors*/
        	fRest.getTranslation(fRestPos);
        	OpenMatrix4f.exportToMojangMatrix(armature.getBoundTransformFor(Pose.EMPTY_POSE, pivot), buf_1)
        	.getTranslation(pRestPos);
        	
        	/*Compute delta*/
        	delta.set(fNow).mul(buf_2.set(fRest).invert());
        	
        	/*Compute and assign result*/
        	out[i].identity()
                    .translation(fNow.getTranslation(buf_3))
                    .mul(buf_1.set(delta))
                    .translate(pRestPos.sub(fRestPos, buf_3));
        }
        return out;
    }
    
    private static void transformByPos(Matrix4f[] anchors, ArmorModelType pos, PoseStack poseStack) {
    	attach(anchors, Anchor.of(pos), poseStack);
    }

    private static void transformByPos(Matrix4f[] anchors, OverlayPos pos, PoseStack poseStack) {
    	attach(anchors, Anchor.of(pos), poseStack);
    }
    
    private static final Matrix3f _m3f = new Matrix3f();

    private static void attach(Matrix4f[] anchors, Anchor a, PoseStack poseStack) {
        final Matrix4f m = anchors[a.ordinal()];
        poseStack.mulPoseMatrix(m);
        poseStack.last().normal().mul(_m3f.set(m));
        /* EF joint space (y-up) -> vanilla part space (y-down, x mirrored) that the OBJ models are made for
           (considering render matrix application).
        */
        poseStack.scale(-1.0f, -1.0f, 1.0f);
    }

}