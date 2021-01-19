package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;

import java.util.List;

public class Point3D extends Feature implements Geometry {
    private Vector3D vector3D;

    public Point3D(Vector3D vector3D) {
        this.vector3D = vector3D;
    }

    public Vector3D getVector3D() {
        return vector3D;
    }

    public void setVector3D(Vector3D vector3D) {
        this.vector3D = vector3D;
    }

    @Override
    public List<Value> getValues() {
        return vector3D.getValues();
    }

    @Override
    public List<Vector3D> getPoints() {
        return vector3D.getPoints();
    }

    @Override
    public void setStatus(short status) {
        vector3D.setStatus(status);
    }

    @Override
    public void constrained() {
        vector3D.constrained();
    }

    @Override
    public void restore() {
        vector3D.restore();
    }

    @Override
    public int getDOF() {
        return vector3D.getDOF();
    }
}
