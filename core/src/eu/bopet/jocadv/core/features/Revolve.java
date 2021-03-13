package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.datums.JoAxis;
import eu.bopet.jocadv.core.vector.Value;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import java.util.List;

public class Revolve extends Feature implements Geometry {
    private Sketch sketch;
    private JoAxis axis;
    private Value angle;


    @Override
    public double distance(Line pickingLine) {
        return 0;
    }

    @Override
    public List<Feature> getFeatures() {
        return null;
    }
}
