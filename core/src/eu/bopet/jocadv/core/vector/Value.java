package eu.bopet.jocadv.core.vector;

public class Value {

    public static final short SYSTEM = 0;
    public static final short USER_INPUT = 1;
    // ----- Sketch control limit. ↑ above sketch can not modify.
    // ↓ below sketch can modify.
    public static final short DIVIDER = 2;

    public static final short CONSTANT = 3;
    public static final short FLEXIBLE = 4;

    private static final String[] symbols = {"🖳", "🖮", "/", "≝", "↔"};

    public static final Value ZERO = new Value(SYSTEM, 0.0);
    public static final Value ONE = new Value(SYSTEM, 1.0);
    public static final Value MINUS_ONE = new Value(SYSTEM, -1.0);

    public static final double TOLERANCE = Math.ulp(0.000000001);

    /**
     * True if this value is constant, false otherwise.
     */
    private short status;
    /**
     * The value.
     */
    private double v;
    private double constrainedValue;

    public Value(short status, double value) {
        super();
        this.status = status;
        this.v = value;
    }

    public Value(double value) {
        super();
        this.status = FLEXIBLE;
        this.v = value;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        if (this.status > Value.DIVIDER) {
            this.status = status;
        }
    }

    public double getValue() {
        return v;
    }

    public void setValue(double value) {
        if (this.status == Value.FLEXIBLE) {
            this.v = value;
        }
    }

    @Override
    public String toString() {
        return symbols[status] + v;
    }

    public void constrained() {
        this.constrainedValue = this.v;
    }

    public void restore() {
        this.v = this.constrainedValue;
    }
}
