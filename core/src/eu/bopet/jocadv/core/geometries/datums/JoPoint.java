package eu.bopet.jocadv.core.geometries.datums;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.vector.JoVector;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import java.util.List;

public class JoPoint extends Feature implements Geometry {

    public static final JoPoint ORIGIN = new JoPoint(JoVector.ZERO);

    private JoVector vector;

    public JoPoint(JoVector vector) {
        this.vector = vector;
    }

    public JoVector getVector() {
        return vector;
    }

    @Override
    public double distance(Line pickingLine) {
        return vector.getVector3D().distance(pickingLine.getOrigin().add(pickingLine.getDirection()));
    }

    @Override
    public List<Feature> getFeatures() {
        return null;
    }
}
