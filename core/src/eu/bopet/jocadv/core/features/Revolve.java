package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.datums.JoAxis;
import eu.bopet.jocadv.core.vector.JoVector;
import eu.bopet.jocadv.core.vector.Value;

import java.util.List;

public class Revolve implements Geometry {
    private Sketch sketch;
    private JoAxis axis;
    private Value angle;
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
