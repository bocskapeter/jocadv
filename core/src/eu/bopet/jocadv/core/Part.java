package eu.bopet.jocadv.core;

import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.datums.JoCoordinateSystem;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;
import eu.bopet.jocadv.core.vector.JoVector;
import eu.bopet.jocadv.core.vector.Value;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Part implements Geometry {

    private List<Feature> features;
    private JoVector min;
    private JoVector max;

    public Part() {
        features = new ArrayList<>();
        features.add(JoCoordinateSystem.DEFAULT);
        features.add(new JoPoint(new JoVector(new Vector3D(5,5,5))));


        min = new JoVector(new Vector3D(-11,-12,-13));
        max = new JoVector(new Vector3D(14,15,16));
        stretch();
    }

    private void stretch(){
        for (Feature feature : features){
            if (feature instanceof Stretchable) {
                ((Stretchable) feature).stretchTo(min, max);
            }
        }
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void addFeature(Feature feature){
        features.add(feature);
    }

    @Override
    public List<Value> getValues() {
        return null;
    }

    @Override
    public List<JoVector> getPoints() {
        return null;
    }

    @Override
    public void setStatus(short status) {
    }

    @Override
    public void constrained() {

    }

    @Override
    public void restore() {

    }

    @Override
    public int getDOF() {
        return 0;
    }
}
