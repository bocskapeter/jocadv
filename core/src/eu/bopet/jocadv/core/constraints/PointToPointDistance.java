package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;

import java.util.List;

public class PointToPointDistance extends Const implements Constraint {
    private JoPoint point1;
    private JoPoint point2;
    private double distance;

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public List<Double> getDerivatives(double interval) {
        return null;
    }
}
