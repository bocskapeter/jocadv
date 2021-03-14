package eu.bopet.jocadv.core.geometries.datums;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.Stretchable;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.vector.JoVector;
import org.apache.commons.math3.geometry.euclidean.threed.Line;

import java.util.ArrayList;
import java.util.List;

public class JoCoordinateSystem extends Feature implements Geometry, Stretchable {
    public static final JoCoordinateSystem DEFAULT = new JoCoordinateSystem( "CS default",
            JoPoint.ORIGIN,
            JoAxis.X_AXIS, JoAxis.Y_AXIS, JoAxis.Z_AXIS,
            JoPlane.XY, JoPlane.XZ, JoPlane.YZ);

    private final JoPoint origin;
    private final JoAxis xAxis;
    private final JoAxis yAxis;
    private final JoAxis zAxis;
    private final JoPlane xyPlane;
    private final JoPlane xzPlane;
    private final JoPlane yzPlane;

    public JoCoordinateSystem(JoPoint origin,
                              JoAxis xAxis, JoAxis yAxis, JoAxis zAxis,
                              JoPlane xyPlane, JoPlane xzPlane, JoPlane yzPlane) {
        this.origin = origin;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.zAxis = zAxis;
        this.xyPlane = xyPlane;
        this.xzPlane = xzPlane;
        this.yzPlane = yzPlane;
    }

    public JoCoordinateSystem(String name,
                              JoPoint origin,
                              JoAxis xAxis, JoAxis yAxis, JoAxis zAxis,
                              JoPlane xy, JoPlane xz, JoPlane yz) {
        this(origin,xAxis,yAxis,zAxis,xy,xz,yz);
        setName(name);
    }

    public JoPoint getOrigin() {
        return origin;
    }

    public JoAxis getXAxis() {
        return xAxis;
    }

    public JoAxis getYAxis() {
        return yAxis;
    }

    public JoAxis getZAxis() {
        return zAxis;
    }

    public JoPlane getXyPlane() {
        return xyPlane;
    }

    public JoPlane getXzPlane() {
        return xzPlane;
    }

    public JoPlane getYzPlane() {
        return yzPlane;
    }

    @Override
    public void stretchTo(JoVector min, JoVector max) {
        xAxis.stretchTo(min, max);
        yAxis.stretchTo(min, max);
        zAxis.stretchTo(min, max);
        xyPlane.stretchTo(min, max);
        yzPlane.stretchTo(min, max);
        xzPlane.stretchTo(min, max);
    }

    @Override
    public double distance(Line pickingLine) {
        return 0;
    }

    @Override
    public List<Feature> getFeatures() {
        List<Feature> result = new ArrayList<>();
        result.add(origin);
        result.add(xAxis);
        result.add(yAxis);
        result.add(zAxis);
        result.add(xyPlane);
        result.add(yzPlane);
        result.add(xzPlane);
        return result;
    }
}
