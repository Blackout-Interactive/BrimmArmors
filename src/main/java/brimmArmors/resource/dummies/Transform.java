package brimmArmors.resource.dummies;

import java.util.Arrays;

import ema_08_.geom.models.*;

/**
 * Dummy class, ema_08_ is indeed too lazy to rewrite all serialisation to make it work with the new transform class,
 * but it will eventually need to be done :).
 */
public class Transform {

    public Matrix ARMOR = new Matrix().identify();
    public Matrix WORKBENCH = new Matrix().identify();
    public Matrix GUI = new Matrix().identify();

    protected Transform() {
    }

    public static Transform create() {
        return new Transform();
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

    public ema_08_.geom.models.RTSMatricesCompound toNewCompound() {
        ema_08_.geom.models.RTSMatricesCompoundBuilder builder = new ema_08_.geom.models.RTSMatricesCompoundBuilder();

        // Allowed keys
        final String keyArmor = RTSMatricesCompound.key_armor_render;
        final String keyWorkbench = RTSMatricesCompound.key_workbench_render;
        final String keyGUI = RTSMatricesCompound.key_gui_render;

        // Add the only valid transforms
        addIfPresent(builder, keyArmor, this.ARMOR);
        addIfPresent(builder, keyWorkbench, this.WORKBENCH);
        addIfPresent(builder, keyGUI, this.GUI);

        return builder.build();
    }

    private static void addIfPresent(
            ema_08_.geom.models.RTSMatricesCompoundBuilder builder,
            String key,
            Matrix matrix
    ) {
        if (matrix != null) {
            builder.set(key, new ema_08_.geom.models.MatrixRTSBuilder()
                    .setTranslate(matrix.translate[0], matrix.translate[1], matrix.translate[2])
                    .setRotate(matrix.rotate[0], matrix.rotate[1], matrix.rotate[2])
                    .setScale(matrix.scale[0], matrix.scale[1], matrix.scale[2]));
        }
    }

    public static Transform fromNewCompound(ema_08_.geom.models.RTSMatricesCompound compound) {
        Transform t = new Transform();

        t.ARMOR         = fromNewMatrix(compound.getOrIdentity("ARMOR"));
        t.WORKBENCH     = fromNewMatrix(compound.getOrIdentity("WORKBENCH"));
        t.GUI           = fromNewMatrix(compound.getOrIdentity("GUI"));

        return t;
    }

    private static Matrix fromNewMatrix(ema_08_.geom.models.MatrixRTS m) {
    	Matrix mtx = new Matrix();
    	mtx.translate = new float[] {m.trX, m.trY, m.trZ};
    	mtx.rotate = new float[] {m.rtX, m.rtY, m.rtZ};
    	mtx.scale = new float[] {m.scX, m.scY, m.scZ};
    	return mtx;
    }

    
}


