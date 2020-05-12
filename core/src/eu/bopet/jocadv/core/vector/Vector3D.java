package eu.bopet.jocadv.core.vector;

import eu.bopet.jocadv.core.Geometry;
import java.util.ArrayList;
import java.util.List;

public class Vector3D implements Geometry {

    public static final Vector3D ORIGIN = new Vector3D(Value.ZERO, Value.ZERO, Value.ZERO);
    public static final Vector3D X = new Vector3D(Value.ONE, Value.ZERO, Value.ZERO);
    public static final Vector3D Y = new Vector3D(Value.ZERO, Value.ONE, Value.ZERO);
    public static final Vector3D Z = new Vector3D(Value.ZERO, Value.ZERO, Value.ONE);

    private Value x;
    private Value y;
    private Value z;

    public Vector3D(double x, double y, double z) {
        super();
        this.x = new Value(Value.FLEXIBLE, x);
        this.y = new Value(Value.FLEXIBLE, y);
        this.z = new Value(Value.FLEXIBLE, z);
    }

    public Vector3D(Value x, Value y, Value z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Value getX() {
        return x;
    }

    public void setX(Value x) {
        this.x = x;
    }

    public Value getY() {
        return y;
    }

    public void setY(Value y) {
        this.y = y;
    }

    public Value getZ() {
        return z;
    }

    public void setZ(Value z) {
        this.z = z;
    }

    public void scale(double scale) {
        this.x.setValue(this.x.getValue() * scale);
        this.y.setValue(this.y.getValue() * scale);
        this.z.setValue(this.z.getValue() * scale);
    }

    /**
     *
     * @return vector of values
     */
    public List<Value> getValues() {
        List<Value> result = new ArrayList<>();
        result.add(x);
        result.add(y);
        result.add(z);
        return result;
    }

    public void setValues(List<Double> args) {
        this.x.setValue(args.get(0));
        this.y.setValue(args.get(1));
        this.z.setValue(args.get(2));
    }

    public void setValues(double x, double y, double z) {
        this.x.setValue(x);
        this.y.setValue(y);
        this.z.setValue(z);
    }

    @Override
    public String toString() {
        return "↗[" + x.toString() + "," + y.toString() + "," + z.toString() + "]";
    }

    @Override
    public void setStatus(short status) {
        this.x.setStatus(status);
        this.y.setStatus(status);
        this.z.setStatus(status);
    }

    /**
     *
     * @return length of the vector
     */
    public double getLength() {
        return Math.sqrt(x.getValue() * x.getValue() + y.getValue() * y.getValue() + z.getValue() * z.getValue());
    }

    /**
     *
     * @param other other vector
     * @return distance
     */
    public double distance(final Vector3D other) {
        return Math.sqrt(squareDistance(other));
    }

    /**
     *
     * @param other other vector
     * @return square distance
     */
    public double squareDistance(final Vector3D other) {
        double result = Math.pow(other.getX().getValue() - this.getX().getValue(), 2);
        result = result + Math.pow(other.getY().getValue() - this.getY().getValue(), 2);
        result = result + Math.pow(other.getZ().getValue() - this.getZ().getValue(), 2);
        return Math.sqrt(result);
    }

    /**
     *
     * @param other other vector
     * @return cross product
     */
    public Vector3D cross(final Vector3D other) {
        double ux = x.getValue();
        double uy = y.getValue();
        double uz = z.getValue();
        double vx = other.getX().getValue();
        double vy = other.getY().getValue();
        double vz = other.getZ().getValue();
        double a = uy * vz - uz * vy;
        double b = uz * vx - ux * vz;
        double c = ux * vy - uy * vx;
        return new Vector3D(a, b, c);
    }

    /**
     *
     * @param other other vector
     * @return cross product length
     */
    public double crossLength(final Vector3D other) {
        return Math.sqrt(crossSquareLength(other.getX().getValue(), other.getY().getValue(), other.getZ().getValue()));
    }

    /**
     *
     * @param ox other x
     * @param oy other y
     * @param oz other z
     * @return cross product length square
     */
    public double crossSquareLength(final double ox, final double oy, final double oz) {
        double a = y.getValue() * oz - z.getValue() * oy;
        double b = z.getValue() * ox - x.getValue() * oz;
        double c = x.getValue() * oy - y.getValue() * ox;
        return (a * a + b * b + c * c);
    }

    /**
     *
     * @param other other vector
     * @return cross product length square
     */
    public double crossSquareLength(final Vector3D other) {
        return crossSquareLength(other.getX().getValue(), other.getY().getValue(), other.getZ().getValue());
    }

    /**
     *
     * @param other other vector
     * @return this vector subtracted from the other vector
     */
    public Vector3D subtract(final Vector3D other) {
        return new Vector3D(this.x.getValue() - other.x.getValue(), this.y.getValue() - other.y.getValue(),
                this.z.getValue() - other.z.getValue());
    }

    /**
     *
     * @return the index of the largest value of x,y,z
     */
    public int getMainDirection() {
        return x.getValue() < y.getValue() ? (y.getValue() < z.getValue() ? 2 : 1)
                : (x.getValue() < z.getValue() ? 2 : 0);
    }

    @Override
    public void constrained() {
        this.x.constrained();
        this.y.constrained();
        this.z.constrained();
    }

    @Override
    public void restore() {
        this.x.restore();
        this.y.restore();
        this.z.restore();
    }

    @Override
    public int getDOF() {
        return 3;
    }

    @Override
    public List<Vector3D> getPoints() {
        List<Vector3D> result = new ArrayList<>();
        result.add(this);
        return result;
    }
}
