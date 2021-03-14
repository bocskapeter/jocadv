package eu.bopet.jocadv.core;

import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.datums.JoAxis;
import eu.bopet.jocadv.core.geometries.datums.JoCoordinateSystem;
import eu.bopet.jocadv.core.geometries.datums.JoPlane;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;
import eu.bopet.jocadv.core.vector.JoVector;
import eu.bopet.jocadv.core.vector.Value;
import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Part extends Feature implements Geometry {

    List<Feature> features;
    List<Part> parts;
    JoVector min;
    JoVector max;

    public Part(String name) {
        features = new ArrayList<>();
        parts = new ArrayList<>();
        setName(name);

        min = new JoVector(new Vector3D(-10, -11, -12));
        max = new JoVector(new Vector3D(13, 14, 15));

        addFeature(JoCoordinateSystem.DEFAULT);
        addFeature(new JoPlane(new Plane(Vector3D.PLUS_J, new Vector3D(4, 5, 6), Value.TOLERANCE)));
        addFeature(new JoAxis(new Line(new Vector3D(2, 3, 8), new Vector3D(0, 1, 9), Value.TOLERANCE)));
        addFeature(new JoPoint(new JoVector(new Vector3D(1, 2, 3))));

        stretch();
    }

    private void stretch() {
        for (Feature feature : features) {
            if (feature instanceof Stretchable) {
                ((Stretchable) feature).stretchTo(min, max);
            }
        }
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void addFeature(Feature feature) {
        if (feature instanceof Geometry) {
            List<Feature> subgeometries = ((Geometry) feature).getFeatures();
            if (subgeometries != null)
                for (Feature subFeature : subgeometries) {
                    if (!features.contains(subFeature)) features.add(subFeature);
                }
        }
        if (!features.contains(feature)) features.add(feature);
        stretch();
    }

    @Override
    public double distance(Line pickingLine) {
        return 0;
    }
}
