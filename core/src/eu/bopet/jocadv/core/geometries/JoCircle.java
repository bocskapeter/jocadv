package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.datums.JoPlane;

public class JoCircle extends Feature {
    private JoSphere sphere;
    private JoPlane plane;

    public JoCircle(JoSphere sphere, JoPlane plane) {
        this.sphere = sphere;
        this.plane = plane;
    }

    public JoSphere getSphere() {
        return sphere;
    }

    public void setSphere(JoSphere sphere) {
        this.sphere = sphere;
    }

    public JoPlane getPlane() {
        return plane;
    }

    public void setPlane(JoPlane plane) {
        this.plane = plane;
    }
}
