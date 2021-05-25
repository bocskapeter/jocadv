package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.JoLine;

import java.util.List;

public final class ParallelLines extends Const implements Constraint {
    private JoLine line1;
    private JoLine line2;

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public List<GeometryDerivatives> getDerivatives() {
        return null;
    }
}
