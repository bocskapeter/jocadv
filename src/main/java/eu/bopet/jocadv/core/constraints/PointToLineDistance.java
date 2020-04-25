package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Constraint;
import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.Line3D;
import eu.bopet.jocadv.core.solver.NumericalDifferentiation;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;

public final class PointToLineDistance extends Const implements Constraint {

    private final Vector3D point;

    private final Line3D line;

    private final Value d;

    public PointToLineDistance(Vector3D point, Line3D line, Value d, short type) {
        super();
        this.point = point;
        this.line = line;
        this.d = d;
        super.setType(type);
    }

    public Vector3D getPoint() {
        return point;
    }

    public Line3D getLine() {
        return line;
    }

    public Value getD() {
        return d;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + ": ⇤" + point + " -[" + d + "]- " + line + "⇥]";
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
        result.add(d);
        return result;
    }

    /**
     *
     * 3-dimensional point line distance.
     *
     * http://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html
     *
     * 0 = ( |((x0-x1) x (x0-x2))| / |x2-x1| ) - d
     *
     * @return function value
     */
    @Override
    public double getValue() {
        double ux = point.getX().getValue() - line.getP1().getX().getValue();
        double uy = point.getY().getValue() - line.getP1().getY().getValue();
        double uz = point.getZ().getValue() - line.getP1().getZ().getValue();

        double vx = point.getX().getValue() - line.getP2().getX().getValue();
        double vy = point.getY().getValue() - line.getP2().getY().getValue();
        double vz = point.getZ().getValue() - line.getP2().getZ().getValue();

        double a = uy * vz - uz * vy;
        double b = uz * vx - ux * vz;
        double c = ux * vy - uy * vx;

        return (Math.sqrt(a * a + b * b + c * c) / line.getLength()) - d.getValue();
    }

    @Override
    public List<Double> getDerivatives(double interval) {
        return NumericalDifferentiation.getDerivatives(this, interval);
    }

}
