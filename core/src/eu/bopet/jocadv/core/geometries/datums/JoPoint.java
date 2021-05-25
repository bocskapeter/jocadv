package eu.bopet.jocadv.core.geometries.datums;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.SketchGeometry;
import eu.bopet.jocadv.core.vector.JoVector;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import java.io.Serializable;
import java.util.List;

public class JoPoint extends Feature implements Geometry, Serializable, SketchGeometry {

    public static final JoPoint ORIGIN = new JoPoint("Point origin", JoVector.ZERO);

    private final JoVector vector;

    public JoPoint(JoVector vector) {
        this.vector = vector;
    }

    public JoPoint(String name, JoVector vector) {
        this(vector);
        setName(name);
    }

    public JoVector getVector() {
        return vector;
    }

    @Override
    public double distance(Line pickingLine) {
        return pickingLine.distance(vector.getVector3D());
    }

    @Override
    public List<Feature> getFeatures() {
        return null;
    }

    @Override
    public int length() {
        return 3;
    }
}
