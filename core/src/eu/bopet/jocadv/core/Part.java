package eu.bopet.jocadv.core;

import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.CSys3D;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Part extends Feature implements Geometry {

    List<Feature> features;
    List<Part> parts;
    Vector3D min;
    Vector3D max;

    public Part() {
        features = new ArrayList<>();
        parts = new ArrayList<>();
        features.add(CSys3D.DEFAULT);
        min = new Vector3D(Value.MINUS_ONE, Value.MINUS_ONE, Value.MINUS_ONE);
        max = new Vector3D(Value.ONE, Value.ONE, Value.ONE);
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void addFeature(Feature feature) {
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

    public Vector3D getMin() {
        return min;
    }

    public void setMin(Vector3D min) {
        this.min = min;
    }

    public Vector3D getMax() {
        return max;
    }

    public void setMax(Vector3D max) {
        this.max = max;
    }
}
