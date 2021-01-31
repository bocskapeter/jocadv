package eu.bopet.jocadv.core.vector;

public class Value {

    private static final double DEFAULT_TOLERANCE = Math.ulp(0.000000001);
    public static double TOLERANCE = DEFAULT_TOLERANCE;

    public static void setTOLERANCE(double TOLERANCE) {
        Value.TOLERANCE = TOLERANCE;
    }
}
