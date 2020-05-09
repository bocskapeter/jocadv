package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;

public class Sphere3D implements Geometry {

    private Vector3D center;
    private Value radius;

    public Sphere3D(Vector3D center, Value radius) {
        super();
        this.center = center;
        this.radius = radius;
    }

    public Vector3D getCenter() {
        return center;
    }

    public void setCenter(Vector3D center) {
        this.center = center;
    }

    public Value getRadius() {
        return radius;
    }

    public void setRadius(Value radius) {
        this.radius = radius;
    }

    @Override
    public List<Value> getValues() {
        List<Value> result = new ArrayList<>();
        result.addAll(center.getValues());
        result.add(radius);
        return result;
    }

    @Override
    public void setStatus(short status) {
        this.center.setStatus(status);
        this.radius.setStatus(status);
    }

    @Override
    public String toString() {
        return "○[." + center + ", R" + radius + "]";
    }

    @Override
    public void constrained() {
        this.center.constrained();
        this.radius.constrained();
    }

    @Override
    public void restore() {
        this.center.restore();
        this.radius.restore();
    }

    @Override
    public int getDOF() {
        return center.getDOF() + 1;
    }

    @Override
    public List<Vector3D> getPoints() {
        List<Vector3D> result = new ArrayList<>();
        result.addAll(center.getPoints());
        return result;
    }
}
