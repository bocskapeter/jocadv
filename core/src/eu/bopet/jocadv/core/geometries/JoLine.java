package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;

public class JoLine extends Feature {
    private JoPoint p1;
    private JoPoint p2;

    public JoLine(JoPoint p1, JoPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public JoPoint getP1() {
        return p1;
    }

    public void setP1(JoPoint p1) {
        this.p1 = p1;
    }

    public JoPoint getP2() {
        return p2;
    }

    public void setP2(JoPoint p2) {
        this.p2 = p2;
    }
}
