package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;

public class JoSphere extends Feature {
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
}
