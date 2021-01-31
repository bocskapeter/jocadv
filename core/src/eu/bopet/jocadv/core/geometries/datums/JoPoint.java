package eu.bopet.jocadv.core.geometries.datums;

import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.vector.JoVector;

public class JoPoint extends Feature {

    public static final JoPoint ORIGIN = new JoPoint(JoVector.ZERO);

    private JoVector vector;

    public JoPoint(JoVector vector) {
        this.vector = vector;
    }

    public JoVector getVector() {
        return vector;
    }
}
