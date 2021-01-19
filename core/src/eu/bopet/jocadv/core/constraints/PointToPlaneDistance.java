package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.Plane3D;
import eu.bopet.jocadv.core.solver.NumericalDifferentiation;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;

public final class PointToPlaneDistance extends Const implements Constraint {

    private final Vector3D point;
    private final Plane3D plane;
    private final Value d;

    public PointToPlaneDistance(Vector3D point, Plane3D plane, Value d, short type) {
        super();
        this.point = point;
        this.plane = plane;
        this.d = d;
        super.setType(type);
    }

    public Vector3D getPoint() {
        return point;
    }

    public Plane3D getPlane() {
        return plane;
    }

    public Value getD() {
        return d;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + ": ⇤" + point + " -[" + d + "]- " + plane + "⇥]";
    }

    @Override
    public List<Geometry> getGeometries() {
        List<Geometry> result = new ArrayList<>();
        result.add(point);
        result.add(plane);
        return result;
    }

    @Override
    public List<Value> getArguments() {
        List<Value> result = new ArrayList<>();
        result.addAll(point.getValues());
        result.addAll(plane.getValues());
        result.add(d);
        return result;
    }

    /**
     *
     * @return function value
     */
    @Override
    public double getValue() {
        // If the plane is XY or parallel, than the distance is in the z direction
        if (plane == Plane3D.XY) {
            return Math.abs(point.getZ().getValue()) - d.getValue();
        }
        if (Math.abs(plane.getN().getX().getValue()) < Value.TOLERANCE
                && Math.abs(plane.getN().getY().getValue()) < Value.TOLERANCE) {
            return Math.abs(point.getZ().getValue()) - d.getValue();
        }
        // If the plane is XZ or parallel, than the distance is in the y direction
        if (plane == Plane3D.XZ) {
            return Math.abs(point.getY().getValue()) - d.getValue();
        }
        if (Math.abs(plane.getN().getX().getValue()) < Value.TOLERANCE
                && Math.abs(plane.getN().getZ().getValue()) < Value.TOLERANCE) {
            return Math.abs(point.getY().getValue()) - d.getValue();
        }

        // If the plane is YZ or parallel, than the distance is in the x direction
        if (plane == Plane3D.YZ) {
            return Math.abs(point.getX().getValue()) - d.getValue();
        }
        if (Math.abs(plane.getN().getY().getValue()) < Value.TOLERANCE
                && Math.abs(plane.getN().getZ().getValue()) < Value.TOLERANCE) {
            return Math.abs(point.getX().getValue()) - d.getValue();
        }

        // For general planes the function value.
        // http://mathworld.wolfram.com/Point-PlaneDistance.html
        // ((ax0+by0+cz0+d)/sqrt(a²+b²+c²))- D = 0
        double result = (plane.getN().getX().getValue() * point.getX().getValue());
        result = result + (plane.getN().getY().getValue() * point.getY().getValue());
        result = result + (plane.getN().getZ().getValue() * point.getZ().getValue());
        result = result + plane.getD().getValue();
        result = (result / plane.getN().getLength()) - d.getValue();
        return result;
    }

    @Override
    public List<Double> getDerivatives(double intervall) {
        return NumericalDifferentiation.getDerivatives(this, intervall);
    }
}
