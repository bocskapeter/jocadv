package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.geometries.datums.JoAxis;
import eu.bopet.jocadv.core.vector.Value;

public class Revolve extends Feature implements Geometry {
    private Sketch sketch;
    private JoAxis axis;
    private Value angle;

    @Override
    public void setStatus(short status) {

    }

    @Override
    public void constrained() {

    }

    @Override
    public void restore() {

    }

}
