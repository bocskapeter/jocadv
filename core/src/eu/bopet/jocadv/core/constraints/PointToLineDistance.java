package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Geometry;

import java.util.List;

public final class PointToLineDistance extends Const implements Constraint {

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public List<GeometryDerivatives> getDerivatives() {
        return null;
    }
}
