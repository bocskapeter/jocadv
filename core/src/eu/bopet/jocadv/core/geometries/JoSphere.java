package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import java.util.ArrayList;
import java.util.List;

public class JoSphere extends Feature implements Geometry {
    private JoPoint center;
    private double radius;

    public JoSphere(JoPoint center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public JoPoint getCenter() {
        return center;
    }

    public void setCenter(JoPoint center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double distance(Line pickingLine) {
        return pickingLine.distance(center.getVector().getVector3D()) - radius;
    }

    @Override
    public List<Feature> getFeatures() {
        List<Feature> result = new ArrayList<>();
        result.add(center);
        return result;
    }
}
