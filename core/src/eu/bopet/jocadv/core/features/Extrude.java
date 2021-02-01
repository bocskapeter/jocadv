package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.vector.JoVector;

public class Extrude extends Feature implements Geometry {
    private Sketch sketch;
    private JoVector direction;
    private Geometry limit;

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
