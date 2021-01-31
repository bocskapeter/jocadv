package eu.bopet.jocadv.core.solver;

import eu.bopet.jocadv.core.constraints.Constraint;
import java.util.ArrayList;
import java.util.List;

public class NumericalDifferentiation {

    /**
     * Default constructor.
     */
    private NumericalDifferentiation() {
    }

    /**
     * Numerical differentiation with symmetric difference quotient.
     * https://en.wikipedia.org/wiki/Numerical_differentiation
     *
     * @param constraint constraint
     * @param interval interval size
     * @return derivatives
     */
    public static List<Double> getDerivatives(final Constraint constraint, double interval) {
        return symmetric(constraint, interval);
    }

    private static List<Double> symmetric(Constraint constraint, double interval) {
        List<Double> result = new ArrayList<>();
        // x
        double tempArgument;
        // f(x-h)
        double tempValue1;
        // f(x+h)
        double tempValue2;
        for (int i = 0; i < constraint.getArguments().size(); i++) {

        }
        return result;
    }
}
