package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.geometries.datums.JoPoint;

public class Edge extends Feature {
    private JoPoint pointA;
    private JoPoint pointB;

    public Edge(JoPoint pointA, JoPoint pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public JoPoint getPointA() {
        return pointA;
    }

    public JoPoint getPointB() {
        return pointB;
    }
}
