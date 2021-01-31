package eu.bopet.jocadv.core.geometries.datums;

import eu.bopet.jocadv.core.Stretchable;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.vector.JoVector;

public class JoCoordinateSystem extends Feature implements Stretchable {
    public static final JoCoordinateSystem DEFAULT = new JoCoordinateSystem(
            JoPoint.ORIGIN,
            JoAxis.X_AXIS, JoAxis.Y_AXIS, JoAxis.Z_AXIS,
            JoPlane.XY, JoPlane.XZ, JoPlane.YZ);

    private JoPoint origin;
    private JoAxis xAxis;
    private JoAxis yAxis;
    private JoAxis zAxis;
    private JoPlane xyPlane;
    private JoPlane xzPlane;
    private JoPlane yzPlane;

    public JoCoordinateSystem(JoPoint origin, JoAxis xAxis, JoAxis yAxis, JoAxis zAxis, JoPlane xyPlane, JoPlane xzPlane, JoPlane yzPlane) {
        this.origin = origin;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.zAxis = zAxis;
        this.xyPlane = xyPlane;
        this.xzPlane = xzPlane;
        this.yzPlane = yzPlane;
    }

    public JoPoint getOrigin() {
        return origin;
    }

    public void setOrigin(JoPoint origin) {
        this.origin = origin;
    }

    public JoAxis getxAxis() {
        return xAxis;
    }

    public void setxAxis(JoAxis xAxis) {
        this.xAxis = xAxis;
    }

    public JoAxis getyAxis() {
        return yAxis;
    }

    public void setyAxis(JoAxis yAxis) {
        this.yAxis = yAxis;
    }

    public JoAxis getzAxis() {
        return zAxis;
    }

    public void setzAxis(JoAxis zAxis) {
        this.zAxis = zAxis;
    }

    public JoPlane getXyPlane() {
        return xyPlane;
    }

    public void setXyPlane(JoPlane xyPlane) {
        this.xyPlane = xyPlane;
    }

    public JoPlane getXzPlane() {
        return xzPlane;
    }

    public void setXzPlane(JoPlane xzPlane) {
        this.xzPlane = xzPlane;
    }

    public JoPlane getYzPlane() {
        return yzPlane;
    }

    public void setYzPlane(JoPlane yzPlane) {
        this.yzPlane = yzPlane;
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
}
