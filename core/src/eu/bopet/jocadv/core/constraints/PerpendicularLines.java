package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.Line3D;
import eu.bopet.jocadv.core.solver.NumericalDifferentiation;
import eu.bopet.jocadv.core.vector.Value;
import java.util.ArrayList;
import java.util.List;

public final class PerpendicularLines extends Const implements Constraint {

    private final Line3D first;
    private final Line3D second;

    public PerpendicularLines(Line3D first, Line3D second, short type) {
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
        return "[" + super.toString() + ": " + first + " ⟂ " + second + "]";
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

    /**
     *
     * Dot product equals to zero.
     *
     * @return function value
     */
    @Override
    public double getValue() {
        return first.dot(second);
    }

    @Override
    public List<Double> getDerivatives(double intervall) {
        return NumericalDifferentiation.getDerivatives(this, intervall);
    }

}
