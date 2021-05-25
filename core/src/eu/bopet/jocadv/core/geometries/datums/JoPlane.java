package eu.bopet.jocadv.core.geometries.datums;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.Stretchable;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.SketchGeometry;
import eu.bopet.jocadv.core.vector.JoVector;
import eu.bopet.jocadv.core.vector.Value;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.util.FastMath;

import java.util.List;

public class JoPlane extends Feature implements Geometry, Stretchable, SketchGeometry {
    public static final JoPlane XY = new JoPlane("Plane xy", new Plane(Vector3D.PLUS_K, Value.TOLERANCE));
    public static final JoPlane XZ = new JoPlane("Plane xz", new Plane(Vector3D.PLUS_J, Value.TOLERANCE));
    public static final JoPlane YZ = new JoPlane("Plane yz", new Plane(Vector3D.PLUS_I, Value.TOLERANCE));

    private final Plane plane;
    private final JoVector normal;
    private JoVector p1;
    private JoVector p2;
    private JoVector p3;
    private JoVector p4;

    public JoPlane(Plane plane) {
        this.plane = plane;
        this.normal = new JoVector(plane.getNormal());
    }

    public JoPlane(String name, Plane plane) {
        this(plane);
        setName(name);
    }

    public Plane getPlane() {
        return plane;
    }

    public JoVector getNormal() {
        return normal;
    }

    public JoVector getP1() {
        return p1;
    }

    public JoVector getP2() {
        return p2;
    }

    public JoVector getP3() {
        return p3;
    }

    public JoVector getP4() {
        return p4;
    }

    @Override
    public void stretchTo(JoVector min, JoVector max) {
        JoVector normal = new JoVector(plane.getNormal());
        Vector3D direction = normal.getMainAbsDirection();

        if (direction == Vector3D.PLUS_I) {
            Plane plane1 = new Plane(max.getVector3D(), Vector3D.PLUS_J, Value.TOLERANCE);
            Plane plane2 = new Plane(max.getVector3D(), Vector3D.PLUS_K, Value.TOLERANCE);
            Plane plane3 = new Plane(min.getVector3D(), Vector3D.MINUS_J, Value.TOLERANCE);
            Plane plane4 = new Plane(min.getVector3D(), Vector3D.MINUS_K, Value.TOLERANCE);
            Line line1 = plane.intersection(plane1);
            p1 = new JoVector(plane2.intersection(line1));
            p2 = new JoVector(plane4.intersection(line1));
            Line line2 = plane.intersection(plane3);
            p3 = new JoVector(plane2.intersection(line2));
            p4 = new JoVector(plane4.intersection(line2));
        }
        if (direction == Vector3D.PLUS_J) {
            Plane plane1 = new Plane(max.getVector3D(), Vector3D.PLUS_I, Value.TOLERANCE);
            Plane plane2 = new Plane(max.getVector3D(), Vector3D.PLUS_K, Value.TOLERANCE);
            Plane plane3 = new Plane(min.getVector3D(), Vector3D.MINUS_I, Value.TOLERANCE);
            Plane plane4 = new Plane(min.getVector3D(), Vector3D.MINUS_K, Value.TOLERANCE);
            Line line1 = plane.intersection(plane1);
            p1 = new JoVector(plane2.intersection(line1));
            p2 = new JoVector(plane4.intersection(line1));
            Line line2 = plane.intersection(plane3);
            p3 = new JoVector(plane2.intersection(line2));
            p4 = new JoVector(plane4.intersection(line2));
        }
        if (direction == Vector3D.PLUS_K) {
            Plane plane1 = new Plane(max.getVector3D(), Vector3D.PLUS_I, Value.TOLERANCE);
            Plane plane2 = new Plane(max.getVector3D(), Vector3D.PLUS_J, Value.TOLERANCE);
            Plane plane3 = new Plane(min.getVector3D(), Vector3D.MINUS_I, Value.TOLERANCE);
            Plane plane4 = new Plane(min.getVector3D(), Vector3D.MINUS_J, Value.TOLERANCE);
            Line line1 = plane.intersection(plane1);
            p1 = new JoVector(plane2.intersection(line1));
            p2 = new JoVector(plane4.intersection(line1));
            Line line2 = plane.intersection(plane3);
            p3 = new JoVector(plane2.intersection(line2));
            p4 = new JoVector(plane4.intersection(line2));
        }
    }


    @Override
    public double distance(Line pickingLine) {
        double dot = FastMath.abs(pickingLine.getDirection().dotProduct(normal.getVector3D()));
        if (dot<Value.TOLERANCE) {
            return Double.POSITIVE_INFINITY;
        }
        return 0;
    }

    @Override
    public List<Feature> getFeatures() {
        return null;
    }

    @Override
    public int length() {
        return 4;
    }
}
