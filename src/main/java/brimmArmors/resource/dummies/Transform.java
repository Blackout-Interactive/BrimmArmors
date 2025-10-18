package brimmArmors.resource.dummies;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import ema_08_.geom.RTSMatricesCompound;
import net.minecraft.world.item.ItemDisplayContext;


import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Dummy class, ema_08_ is indeed to lazy to rewrite all serialisation to make it work with the new transform class,
 * but it will eventually need to be done :).
 */
public class Transform {

    public Matrix ARMOR = new Matrix().identify();

    public Matrix WORKBENCH = new Matrix().identify();
    public Matrix GUI = new Matrix().identify();
    public Matrix RIGHT_HAND_3D = new Matrix().identify();
    public Matrix RIGHT_HAND = new Matrix().identify();
    public Matrix LEFT_HAND_3D = new Matrix().identify();
    public Matrix LEFT_HAND = new Matrix().identify();
    public Matrix DROP = new Matrix().identify();

    protected Transform() {
    }

    public static Transform create() {
        return new Transform();
    }

    public Transform accept(Consumer<Transform> consumer) {
        consumer.accept(this);
        return this;
    }

    public void apply(ItemDisplayContext type, PoseStack poseStack) {
        switch (type) {
            case GUI -> GUI.setup(poseStack);
            case GROUND -> DROP.setup(poseStack);
            case FIRST_PERSON_RIGHT_HAND -> RIGHT_HAND.setup(poseStack);
            case FIRST_PERSON_LEFT_HAND -> LEFT_HAND.setup(poseStack);
            case THIRD_PERSON_LEFT_HAND -> LEFT_HAND_3D.setup(poseStack);
            case THIRD_PERSON_RIGHT_HAND -> RIGHT_HAND_3D.setup(poseStack);
            default -> {
                // no-op
            }
        }
    }

    public static class Matrix {
        public static final Matrix IDENTITY = new Matrix().identify();
        public float[] translate;
        public float[] rotate;
        public float[] scale;

        public Matrix identify() {
            this.translate = new float[]{0, 0, 0};
            this.rotate = new float[]{0, 0, 0};
            this.scale = new float[]{1, 1, 1};
            return this;
        }

        public Matrix setTranslate(float x, float y, float z) {
            this.translate = new float[]{x, y, z};
            return this;
        }

        public Matrix setRotate(float x, float y, float z) {
            this.rotate = new float[]{x, y, z};
            return this;
        }

        public Matrix setScale(float x, float y, float z) {
            this.scale = new float[]{x, y, z};
            return this;
        }

        public void setup(PoseStack poseStack) {
            // Rotate around X-axis
            poseStack.mulPose(Axis.XP.rotationDegrees(rotate[0]));
            // Rotate around Y-axis
            poseStack.mulPose(Axis.YP.rotationDegrees(rotate[1]));
            // Rotate around Z-axis
            poseStack.mulPose(Axis.ZP.rotationDegrees(rotate[2]));

            // Scale
            poseStack.scale(scale[0], scale[1], scale[2]);

            // Translate
            poseStack.translate(translate[0], translate[1], translate[2]);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Matrix matrix = (Matrix) o;
            return Arrays.equals(translate, matrix.translate)
                    && Arrays.equals(rotate, matrix.rotate)
                    && Arrays.equals(scale, matrix.scale);
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(translate);
            result = 31 * result + Arrays.hashCode(rotate);
            result = 31 * result + Arrays.hashCode(scale);
            return result;
        }
    }
    
    // --- Conversion utilities --- \\

    public ema_08_.geom.RTSMatricesCompound toNewCompound() {
        ema_08_.geom.RTSMatricesCompoundBuilder builder = new ema_08_.geom.RTSMatricesCompoundBuilder();

        // Allowed keys
        final String keyArmor = RTSMatricesCompound.key_armor_render;
        final String keyWorkbench = RTSMatricesCompound.key_workbench_render;
        final String keyGUI = RTSMatricesCompound.key_gui_render;

        // Add the only valid transforms
        addIfPresent(builder, keyArmor, this.ARMOR);
        addIfPresent(builder, keyWorkbench, this.WORKBENCH);
        addIfPresent(builder, keyGUI, this.GUI);

        // Check forbidden ones — they must be identity matrices or null
        checkIsIdentity("RIGHT_HAND_3D", this.RIGHT_HAND_3D);
        checkIsIdentity("RIGHT_HAND", this.RIGHT_HAND);
        checkIsIdentity("LEFT_HAND_3D", this.LEFT_HAND_3D);
        checkIsIdentity("LEFT_HAND", this.LEFT_HAND);
        checkIsIdentity("DROP", this.DROP);

        return builder.build();
    }

    private static void addIfPresent(
            ema_08_.geom.RTSMatricesCompoundBuilder builder,
            String key,
            Matrix matrix
    ) {
        if (matrix != null) {
            builder.set(key, new ema_08_.geom.MatrixRTSBuilder()
                    .setTranslate(matrix.translate[0], matrix.translate[1], matrix.translate[2])
                    .setRotate(matrix.rotate[0], matrix.rotate[1], matrix.rotate[2])
                    .setScale(matrix.scale[0], matrix.scale[1], matrix.scale[2]));
        }
    }

    private static void checkIsIdentity(String name, Matrix m) {
        if (m == null) return;

        boolean notIdentity =
                !Arrays.equals(m.translate, new float[]{0, 0, 0}) ||
                !Arrays.equals(m.rotate, new float[]{0, 0, 0}) ||
                !Arrays.equals(m.scale, new float[]{1, 1, 1});

        if (notIdentity) {
            throw new IllegalStateException(
                "Legacy Transform contains forbidden non-identity matrix for '" + name + "'. " +
                "Only ARMOR, WORKBENCH, and GUI are allowed in the new system."
            );
        }
    }

    public static Transform fromNewCompound(ema_08_.geom.RTSMatricesCompound compound) {
        Transform t = new Transform();

        t.ARMOR         = fromNewMatrix(compound.getOrIdentity("ARMOR"));
        t.WORKBENCH     = fromNewMatrix(compound.getOrIdentity("WORKBENCH"));
        t.GUI           = fromNewMatrix(compound.getOrIdentity("GUI"));
        t.RIGHT_HAND_3D = fromNewMatrix(compound.getOrIdentity("RIGHT_HAND_3D"));
        t.RIGHT_HAND    = fromNewMatrix(compound.getOrIdentity("RIGHT_HAND"));
        t.LEFT_HAND_3D  = fromNewMatrix(compound.getOrIdentity("LEFT_HAND_3D"));
        t.LEFT_HAND     = fromNewMatrix(compound.getOrIdentity("LEFT_HAND"));
        t.DROP          = fromNewMatrix(compound.getOrIdentity("DROP"));

        return t;
    }

    private static Matrix fromNewMatrix(ema_08_.geom.MatrixRTS m) {
        return new Matrix()
                .setTranslate(m.trX, m.trY, m.trZ)
                .setRotate(m.rtX, m.rtY, m.rtZ)
                .setScale(m.scX, m.scY, m.scZ);
    }

    
}


