package eu.bopet.jocadv.core.geometries;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;

import java.util.List;

public class CSys3D implements Geometry {
    private static final CSys3D DEFAULT = new CSys3D(Vector3D.ORIGIN,Axis3D.X,Axis3D.Y,Axis3D.Z,Plane3D.YZ,Plane3D.XZ,Plane3D.XY);
    private Vector3D origin;
    private Axis3D xAxis;
    private Axis3D yAxis;
    private Axis3D zAxis;
    private Plane3D yzPlane;
    private Plane3D xzPlane;
    private Plane3D xyPlane;

    public CSys3D(Vector3D origin, Axis3D xAxis, Axis3D yAxis, Axis3D zAxis, Plane3D yzPlane, Plane3D xzPlane, Plane3D xyPlane) {
        this.origin = origin;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.zAxis = zAxis;
        this.yzPlane = yzPlane;
        this.xzPlane = xzPlane;
        this.xyPlane = xyPlane;
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
