package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.geometries.datums.JoPlane;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.List;

public final class PointOnPlane extends Const implements Constraint {
    private JoPoint point;
    private JoPlane plane;

    @Override
    public double getValue() {
        Vector3D projectedPoint = (Vector3D) plane.getPlane().project(point.getVector().getVector3D());
        return projectedPoint.distance(point.getVector().getVector3D());
    }

    @Override
    public List<Double> getDerivatives(double interval) {
        return null;
    }
}
