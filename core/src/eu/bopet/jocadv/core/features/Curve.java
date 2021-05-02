package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.geometries.datums.JoPoint;

import java.util.ArrayList;
import java.util.List;

public class Curve extends Feature{
    private List<JoPoint> points;

    public Curve() {
        points = new ArrayList<>();
    }
}
