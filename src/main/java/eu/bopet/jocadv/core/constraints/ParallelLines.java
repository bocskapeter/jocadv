package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Constraint;
import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.Line3D;
import eu.bopet.jocadv.core.solver.NumericalDifferentiation;
import eu.bopet.jocadv.core.vector.Value;
import java.util.ArrayList;
import java.util.List;

public final class ParallelLines extends Const implements Constraint {

    private final Line3D first;
    private final Line3D second;

    public ParallelLines(Line3D first, Line3D second, short type) {
        super();
        this.first = first;
        this.second = second;
        super.setType(type);
    }

    public Line3D getFirst() {
        return first;
    }

    public Line3D getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + ": " + first + " ∥ " + second + "]";
    }

    @Override
    public List<Geometry> getGeometries() {
        List<Geometry> result = new ArrayList<>();
        result.add(first);
        result.add(second);
        return result;
    }

    @Override
    public List<Value> getArguments() {
        List<Value> result = new ArrayList<>();
        result.addAll(first.getValues());
        result.addAll(second.getValues());
        return result;
    }

    @Override
    public double getValue() {
        double ux = first.getP2().getX().getValue() - first.getP1().getX().getValue();
        double uy = first.getP2().getY().getValue() - first.getP1().getY().getValue();
        double uz = first.getP2().getZ().getValue() - first.getP1().getZ().getValue();
        double vx = second.getP2().getX().getValue() - second.getP1().getX().getValue();
        double vy = second.getP2().getY().getValue() - second.getP1().getY().getValue();
        double vz = second.getP2().getZ().getValue() - second.getP1().getZ().getValue();
        double a = uy * vz - uz * vy;
        double b = uz * vx - ux * vz;
        double c = ux * vy - uy * vx;
        return (a + b + c);
    }

    @Override
    public List<Double> getDerivatives(double interval) {
        return NumericalDifferentiation.getDerivatives(this, interval);
    }
}
