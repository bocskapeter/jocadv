package eu.bopet.jocadv.core.vector;

import com.badlogic.gdx.math.Vector3;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class JoVector {

    public static final JoVector ZERO = new JoVector(Vector3D.ZERO);
    public static final JoVector PLUS_I = new JoVector(Vector3D.PLUS_I);
    public static final JoVector PLUS_J = new JoVector(Vector3D.PLUS_J);
    public static final JoVector PLUS_K = new JoVector(Vector3D.PLUS_K);

    private final Vector3D vector3D;

    public JoVector(Vector3D vector3D) {
        this.vector3D = vector3D;
    }

    public Vector3D getVector3D() {
        return vector3D;
    }

    public Vector3D getMainAbsDirection() {
        if (Math.abs(vector3D.getY()) >= Math.abs(vector3D.getZ())) {
            if (Math.abs(vector3D.getX()) >= Math.abs(vector3D.getY())) {
                return Vector3D.PLUS_I;
            } else {
                return Vector3D.PLUS_J;
            }
        } else {
            if (Math.abs(vector3D.getX()) >= Math.abs(vector3D.getZ())) {
                return Vector3D.PLUS_I;
            } else {
                return Vector3D.PLUS_K;
            }
        }
    }

    public Vector3 getVector3() {
        return new Vector3((float) vector3D.getX(), (float) vector3D.getY(), (float) vector3D.getZ());
    }

    @Override
    public String toString() {
        return "JoVector{" + vector3D.toString() + '}';
    }
}
