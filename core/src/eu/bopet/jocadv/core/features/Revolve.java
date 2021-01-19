package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.Axis3D;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;

import java.util.List;

public class Revolve extends Feature implements Geometry {
    private Sketch sketch;
    private Axis3D axis;
    private Value angle;
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
