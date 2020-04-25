package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Constraint;
import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.Plane3D;
import eu.bopet.jocadv.core.solver.NumericalDifferentiation;
import eu.bopet.jocadv.core.vector.Value;
import java.util.ArrayList;
import java.util.List;

public final class ParallelPlanes extends Const implements Constraint {

    private final Plane3D first;
    private final Plane3D second;

    public ParallelPlanes(Plane3D first, Plane3D second, short type) {
        super();
        this.first = first;
        this.second = second;
        super.setType(type);
    }

    public Plane3D getFirst() {
        return first;
    }

    public Plane3D getSecond() {
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
        return this.first.getN().crossSquareLength(this.second.getN());
    }

    @Override
    public List<Double> getDerivatives(double interval) {
        return NumericalDifferentiation.getDerivatives(this, interval);
    }

}
