package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;
import eu.bopet.jocadv.core.vector.Value;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import java.util.ArrayList;
import java.util.List;

public class JoLine extends Feature implements Geometry {
    private final Line line;
    private JoPoint p1;
    private JoPoint p2;

    public JoLine(JoPoint p1, JoPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.line = new Line(p1.getVector().getVector3D(), p2.getVector().getVector3D(), Value.TOLERANCE);
    }

    public JoPoint getP1() {
        return p1;
    }

    public void setP1(JoPoint p1) {
        this.p1 = p1;
    }

    public JoPoint getP2() {
        return p2;
    }

    public void setP2(JoPoint p2) {
        this.p2 = p2;
    }

    @Override
    public double distance(Line pickingLine) {
        return line.distance(pickingLine);
    }

    @Override
    public List<Feature> getFeatures() {
        List<Feature> result = new ArrayList<>();
        result.add(p1);
        result.add(p2);
        return result;
    }
}
