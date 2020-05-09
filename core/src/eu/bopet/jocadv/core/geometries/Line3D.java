package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;

public class Line3D implements Geometry {

    private Vector3D p1;
    private Vector3D p2;

    public Line3D(Vector3D p1, Vector3D p2) {
        super();
        this.p1 = p1;
        this.p2 = p2;
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

    public Vector3D getVector() {
        return p2.subtract(p1);
    }

    @Override
    public String toString() {
        return "/[" + p1 + " - " + p2 + "]";
    }

    @Override
    public List<Value> getValues() {
        List<Value> result = new ArrayList<>();
        result.addAll(p1.getValues());
        result.addAll(p2.getValues());
        return result;
    }

    @Override
    public void setStatus(short status) {
        this.p1.setStatus(status);
        this.p2.setStatus(status);
    }

    public double getLength() {
        return p1.distance(p2);
    }

    public double dot(final Line3D o) {
        return (p2.getX().getValue() - p1.getX().getValue()) * (o.p2.getX().getValue() - o.p1.getX().getValue())
                + (p2.getY().getValue() - p1.getY().getValue()) * (o.p2.getY().getValue() - o.p1.getY().getValue())
                + (p2.getZ().getValue() - p1.getZ().getValue()) * (o.p2.getZ().getValue() - o.p1.getZ().getValue());
    }

    @Override
    public void constrained() {
        this.p1.constrained();
        this.p2.constrained();
    }

    @Override
    public void restore() {
        this.p1.restore();
        this.p2.restore();
    }

    @Override
    public int getDOF() {
        return p1.getDOF() + p2.getDOF();
    }

    @Override
    public List<Vector3D> getPoints() {
        List<Vector3D> result = new ArrayList<>();
        result.addAll(p1.getPoints());
        result.addAll(p2.getPoints());
        return result;
    }
}
