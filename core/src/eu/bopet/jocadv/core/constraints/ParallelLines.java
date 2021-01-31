package eu.bopet.jocadv.core.constraints;


import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.vector.Value;

import java.util.List;

public final class ParallelLines extends Const implements Constraint {

    @Override
    public List<Geometry> getGeometries() {
        return null;
    }

    @Override
    public List<Value> getArguments() {
        return null;
    }

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public List<Double> getDerivatives(double interval) {
        return null;
    }
}
