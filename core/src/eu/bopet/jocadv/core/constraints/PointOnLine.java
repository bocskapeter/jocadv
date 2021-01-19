package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.Line3D;
import eu.bopet.jocadv.core.solver.NumericalDifferentiation;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;

public final class PointOnLine extends Const implements Constraint {

    private final Vector3D point;
    private final Line3D line;

    public PointOnLine(Vector3D point, Line3D line, short type) {
        super();
        this.point = point;
        this.line = line;
        super.setType(type);
    }

    public Vector3D getPoint() {
        return point;
    }

    public Line3D getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + ": x" + point + " ↴ " + line + "]";
    }

    @Override
    public List<Geometry> getGeometries() {
        List<Geometry> result = new ArrayList<>();
        result.add(point);
        result.add(line);
        return result;
    }

    @Override
    public List<Value> getArguments() {
        List<Value> result = new ArrayList<>();
        result.addAll(point.getValues());
        result.addAll(line.getValues());
        return result;
    }

    /**
     * Cross product length is zero.
     *
     * 0 = a²+b²+c². a=uy*vz-uz*vy. b=uz*vx-ux*vz. c=ux*vy-uy*vx.
     *
     * @return function value
     */
    @Override
    public double getValue() {
        double ux = line.getP2().getVector3D().getX().getValue() - line.getP1().getVector3D().getX().getValue();
        double uy = line.getP2().getVector3D().getY().getValue() - line.getP1().getVector3D().getY().getValue();
        double uz = line.getP2().getVector3D().getZ().getValue() - line.getP1().getVector3D().getZ().getValue();
        double vx = point.getX().getValue() - line.getP1().getVector3D().getX().getValue();
        double vy = point.getY().getValue() - line.getP1().getVector3D().getY().getValue();
        double vz = point.getZ().getValue() - line.getP1().getVector3D().getZ().getValue();
        double a = (uy * vz) - (uz * vy);
        double b = (uz * vx) - (ux * vz);
        double c = (ux * vy) - (uy * vx);
        return ((a * a) + (b * b) + (c * c));
    }

    @Override
    public List<Double> getDerivatives(double intervall) {
        return NumericalDifferentiation.getDerivatives(this, intervall);
    }

}
