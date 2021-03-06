package eu.bopet.jocadv.core.geometries.datums;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.Stretchable;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.vector.JoVector;
import eu.bopet.jocadv.core.vector.Value;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class JoAxis extends Feature implements Geometry, Stretchable {

    public static final JoAxis X_AXIS = new JoAxis(new Line(Vector3D.ZERO, Vector3D.PLUS_I, Value.TOLERANCE));
    public static final JoAxis Y_AXIS = new JoAxis(new Line(Vector3D.ZERO, Vector3D.PLUS_J, Value.TOLERANCE));
    public static final JoAxis Z_AXIS = new JoAxis(new Line(Vector3D.ZERO, Vector3D.PLUS_K, Value.TOLERANCE));

    private Line line;
    private JoVector p1;
    private JoVector p2;

    public JoAxis(Line line) {
        this.line = line;
    }

    public Line getLine() {
        return line;
    }

    public JoVector getP1() {
        return p1;
    }

    public JoVector getP2() {
        return p2;
    }

    @Override
    public void stretchTo(JoVector min, JoVector max) {
        JoVector direction = new JoVector(line.getDirection());
        if (direction.getMainAbsDirection() == Vector3D.PLUS_I) {
            Vector3D vector3D = new Vector3D(max.getVector3D().getX(), 0, 0);
            Plane plane = new Plane(vector3D, Vector3D.PLUS_I, Value.TOLERANCE);
            Vector3D intersection = plane.intersection(line);
            p1 = new JoVector(intersection);
            vector3D = new Vector3D(min.getVector3D().getX(), 0, 0);
            plane = new Plane(vector3D, Vector3D.MINUS_I, Value.TOLERANCE);
            intersection = plane.intersection(line);
            p2 = new JoVector(intersection);
        }
        if (direction.getMainAbsDirection() == Vector3D.PLUS_J) {
            Vector3D vector3D = new Vector3D(0, max.getVector3D().getY(), 0);
            Plane plane = new Plane(vector3D, Vector3D.PLUS_J, Value.TOLERANCE);
            Vector3D intersection = plane.intersection(line);
            p1 = new JoVector(intersection);
            vector3D = new Vector3D(0, min.getVector3D().getY(), 0);
            plane = new Plane(vector3D, Vector3D.MINUS_J, Value.TOLERANCE);
            intersection = plane.intersection(line);
            p2 = new JoVector(intersection);
        }
        if (direction.getMainAbsDirection() == Vector3D.PLUS_K) {
            Vector3D vector3D = new Vector3D(0, 0, max.getVector3D().getZ());
            Plane plane = new Plane(vector3D, Vector3D.PLUS_K, Value.TOLERANCE);
            Vector3D intersection = plane.intersection(line);
            p1 = new JoVector(intersection);
            vector3D = new Vector3D(0, 0, min.getVector3D().getZ());
            plane = new Plane(vector3D, Vector3D.MINUS_K, Value.TOLERANCE);
            intersection = plane.intersection(line);
            p2 = new JoVector(intersection);
        }

    }

    @Override
    public double distance(Line pickingLine) {
        return line.distance(pickingLine);
    }
}
