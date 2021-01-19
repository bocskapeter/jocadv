package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.Plane3D;
import eu.bopet.jocadv.core.solver.NumericalDifferentiation;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;

public final class PointOnPlane extends Const implements Constraint {

    private final Vector3D point;
    private final Plane3D plane;

    public PointOnPlane(Vector3D point, Plane3D plane, short type) {
        super();
        this.point = point;
        this.plane = plane;
        super.setType(type);
    }

    public Vector3D getPoint() {
        return point;
    }

    public Plane3D getPlane() {
        return plane;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + ": x" + point + " ↴ " + plane + "]";
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
        return result;
    }

    @Override
    public double getValue() {
        return plane.getN().getX().getValue() * point.getX().getValue()
                + plane.getN().getY().getValue() * point.getY().getValue()
                + plane.getN().getZ().getValue() * point.getZ().getValue() + plane.getD().getValue();
    }

    @Override
    public List<Double> getDerivatives(double intervall) {
        return NumericalDifferentiation.getDerivatives(this, intervall);
    }
}
