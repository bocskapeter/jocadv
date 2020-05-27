package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;

import java.util.List;

public class Axis3D implements Geometry, Feature {

    public static final Axis3D X = new Axis3D(Vector3D.ORIGIN, Vector3D.X);
    public static final Axis3D Y = new Axis3D(Vector3D.ORIGIN, Vector3D.Y);
    public static final Axis3D Z = new Axis3D(Vector3D.ORIGIN, Vector3D.Z);

    private Vector3D point;
    private Vector3D direction;

    public Axis3D(Vector3D point, Vector3D direction) {
        this.point = point;
        this.direction = direction;
    }

    @Override
    public List<Value> getValues() {
        return null;
    }

    @Override
    public List<Vector3D> getPoints() {
        return null;
    }

    @Override
    public void setStatus(short status) {

    }

    @Override
    public void constrained() {

    }

    @Override
    public void restore() {

    }

    @Override
    public int getDOF() {
        return 0;
    }
}
