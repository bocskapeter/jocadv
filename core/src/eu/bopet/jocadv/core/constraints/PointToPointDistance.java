package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Constraint;
import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.solver.NumericalDifferentiation;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;

public class PointToPointDistance extends Const implements Constraint {

    private Vector3D p1;
    private Vector3D p2;
    private Value d;

    public PointToPointDistance(Vector3D p1, Vector3D p2, Value d, short type) {
        super();
        this.p1 = p1;
        this.p2 = p2;
        this.d = d;
        super.setType(type);
    }

    public Vector3D getP1() {
        return p1;
    }

    public void setP1(Vector3D p1) {
        this.p1 = p1;
    }

    public Vector3D getP2() {
        return p2;
    }

    public void setP2(Vector3D p2) {
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
        result.addAll(p1.getValues());
        result.addAll(p2.getValues());
        result.add(d);
        return result;
    }

    /**
     * 0 = (x2-x1)²+(y2-y1)²+(z2-z1)²-D².
     *
     * @return function value
     */
    @Override
    public double getValue() {
        double result = (p2.getX().getValue() - p1.getX().getValue()) * (p2.getX().getValue() - p1.getX().getValue());
        result = result + (p2.getY().getValue() - p1.getY().getValue()) * (p2.getY().getValue() - p1.getY().getValue());
        result = result + (p2.getZ().getValue() - p1.getZ().getValue()) * (p2.getZ().getValue() - p1.getZ().getValue());
        result = result - (d.getValue() * d.getValue());
        return result;
    }

    @Override
    public List<Double> getDerivatives(double intervall) {
        return NumericalDifferentiation.getDerivatives(this, intervall);
    }

    @Override
    public List<Geometry> getGeometries() {
        List<Geometry> result = new ArrayList<>();
        result.add(p1);
        result.add(p2);
        return result;
    }
}
