package eu.bopet.jocadv.core;

import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.CSys3D;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Part implements Geometry {

    List<Feature> features;

    public Part() {
        features = new ArrayList<>();
        features.add(CSys3D.DEFAULT);
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
    public List<Vector3D> getPoints() {
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
