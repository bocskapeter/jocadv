package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;

public class Circle3D implements Geometry {

    private Sphere3D sphere;
    private Plane3D plane;

    public Circle3D(Sphere3D sphere, Plane3D plane) {
        super();
        this.sphere = sphere;
        this.plane = plane;
    }

    public Sphere3D getSphere() {
        return sphere;
    }

    public void setSphere(Sphere3D sphere) {
        this.sphere = sphere;
    }

    public Plane3D getPlane() {
        return plane;
    }

    public Value getRadius() {
        return this.sphere.getRadius();
    }

    public void setRadius(Value radius) {
        this.sphere.setRadius(radius);
    }

    public Vector3D getCenter() {
        return this.sphere.getCenter();
    }

    public void setPlane(Plane3D plane) {
        this.plane = plane;
    }

    @Override
    public List<Value> getValues() {
        List<Value> result = new ArrayList<>();
        result.addAll(sphere.getValues());
        result.addAll(plane.getValues());
        return result;
    }

    @Override
    public void setStatus(short status) {
        this.sphere.setStatus(status);
        this.plane.setStatus(status);
    }

    @Override
    public String toString() {
        return "⌀[" + sphere + " ↴ " + plane + "]";
    }

    @Override
    public void constrained() {
        this.sphere.constrained();
        this.plane.constrained();
    }

    @Override
    public void restore() {
        this.sphere.restore();
        this.plane.restore();
    }

    @Override
    public List<Vector3D> getPoints() {
        List<Vector3D> result = new ArrayList<>();
        result.addAll(sphere.getPoints());
        return result;
    }
}
