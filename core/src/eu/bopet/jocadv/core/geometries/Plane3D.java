package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;

public class Plane3D implements Geometry {

    public static final Plane3D XY = new Plane3D(Vector3D.Z, Value.ZERO);
    public static final Plane3D XZ = new Plane3D(Vector3D.Y, Value.ZERO);
    public static final Plane3D YZ = new Plane3D(Vector3D.X, Value.ZERO);

    private Vector3D n;
    private Value d;

    public Plane3D(Vector3D n, Value d) {
        super();
        this.n = n;
        this.d = d;
    }

    public Vector3D getN() {
        return n;
    }

    public void setN(Vector3D n) {
        this.n = n;
    }

    public Value getD() {
        return d;
    }

    /**
     * Plane point distance according to:
     * http://mathworld.wolfram.com/Point-PlaneDistance.html
     * (ax0+by0+cz0+d)/sqrt(a²+b²+c²)
     *
     * @return distance
     */
    public double getDistanceToPoint(Vector3D point) {
        double result = (n.getX().getValue() * point.getX().getValue());
        result = result + (n.getY().getValue() * point.getY().getValue());
        result = result + (n.getZ().getValue() * point.getZ().getValue());
        result = result + d.getValue();
        result = result / n.getLength();
        return result;
    }

    public static Plane3D getMainPlane(int index) {
        if (index == 2) {
            return Plane3D.YZ;
        }
        if (index == 1) {
            return Plane3D.XZ;
        }
        return Plane3D.XY;
    }

    public void setD(Value d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "⏥[" + n + "," + d + "]";
    }

    @Override
    public List<Value> getValues() {
        List<Value> result = new ArrayList<>();
        result.addAll(n.getValues());
        result.add(d);
        return result;
    }

    @Override
    public void setStatus(short status) {
        n.setStatus(status);
        d.setStatus(status);
    }

    @Override
    public void constrained() {
        this.n.constrained();
        this.d.constrained();
    }

    @Override
    public void restore() {
        this.n.restore();
        this.d.restore();
    }

    @Override
    public int getDOF() {
        return n.getDOF() + 1;
    }

    @Override
    public List<Vector3D> getPoints() {
        return null;
    }
}
