package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.solver.NumericalDifferentiation;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.JoVector;
import java.util.ArrayList;
import java.util.List;

public class PointToPointDistance extends Const implements Constraint {

    private JoVector p1;
    private JoVector p2;
    private Value d;

    public PointToPointDistance(JoVector p1, JoVector p2, Value d, short type) {
        super();
        this.p1 = p1;
        this.p2 = p2;
        this.d = d;
        super.setType(type);
    }

    public JoVector getP1() {
        return p1;
    }

    public void setP1(JoVector p1) {
        this.p1 = p1;
    }

    public JoVector getP2() {
        return p2;
    }

    public void setP2(JoVector p2) {
        this.p2 = p2;
    }

    public Value getD() {
        return d;
    }

    public void setD(Value d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + ": ⇤" + p1 + " -[" + d + "]- " + p2 + "⇥]";
    }

    @Override
    public List<Value> getArguments() {
        List<Value> result = new ArrayList<>();
        return result;
    }

    /**
     * 0 = (x2-x1)²+(y2-y1)²+(z2-z1)²-D².
     *
     * @return function value
     */
    @Override
    public double getValue() {
        double result = 0.0;
        return result;
    }

    @Override
    public List<Double> getDerivatives(double intervall) {
        return NumericalDifferentiation.getDerivatives(this, intervall);
    }

    @Override
    public List<Geometry> getGeometries() {
        List<Geometry> result = new ArrayList<>();
        return result;
    }
}
