package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;

public class Arc3D implements Geometry {

    private Circle3D circle;
    private Vector3D p1;
    private Vector3D p2;

    public Arc3D(Circle3D circle, Vector3D p1, Vector3D p2) {
        super();
        this.circle = circle;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Arc3D(Plane3D plane, Vector3D center, Vector3D p1, Vector3D p2) {
        super();
        Sphere3D sp = new Sphere3D(center, new Value(center.distance(p1)));
        this.circle = new Circle3D(sp, plane);
        this.p1 = p1;
        this.p2 = p2;
    }

    public Circle3D getCircle() {
        return circle;
    }

    public void setCircle(Circle3D circle) {
        this.circle = circle;
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

    public Value getRadius() {
        return this.circle.getRadius();
    }

    public void setRadius(Value radius) {
        this.circle.setRadius(radius);
    }

    public Vector3D getCenter() {
        return this.circle.getCenter();
    }

    @Override
    public String toString() {
        return "◝[" + circle + " ⇲ " + p1 + " ⇱ " + p2 + "]";
    }

    @Override
    public List<Value> getValues() {
        List<Value> result = new ArrayList<>();
        result.addAll(circle.getValues());
        result.addAll(p1.getValues());
        result.addAll(p2.getValues());
        return result;
    }

    @Override
    public void setStatus(short status) {
        this.circle.setStatus(status);
        this.p1.setStatus(status);
        this.p2.setStatus(status);
    }

    @Override
    public void constrained() {
        this.circle.constrained();
        this.p1.constrained();
        this.p2.constrained();
    }

    @Override
    public void restore() {
        this.circle.restore();
        this.p1.restore();
        this.p2.restore();
    }

    @Override
    public List<Vector3D> getPoints() {
        List<Vector3D> result = new ArrayList<>();
        result.addAll(circle.getPoints());
        result.addAll(p1.getPoints());
        result.addAll(p2.getPoints());
        return result;
    }
}
